/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2017 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.xowa.bldrs; import gplx.*; import gplx.xowa.*;
import gplx.core.consoles.*; import gplx.core.envs.*;
import gplx.xowa.apps.*; import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.xmls.*; import gplx.xowa.langs.bldrs.*;
import gplx.xowa.bldrs.wkrs.*;
import gplx.langs.jsons.*;
import gplx.xowa.addons.bldrs.app_cfgs.*;
public class Xob_bldr implements Gfo_invk {
	private boolean pause_at_end = false; private long prv_prog_time; private Xob_xml_parser dump_parser;
	public Xob_bldr(Xoae_app app) {
		this.app = app;
		this.cmd_mgr = new Xob_cmd_mgr(this, cmd_regy);
		this.import_marker = new Xob_import_marker();
		this.wiki_cfg_bldr = new Xob_wiki_cfg_bldr(this);
	}
	public Xoae_app				App() {return app;} private final    Xoae_app app;
	public Xob_cmd_regy			Cmd_regy() {return cmd_regy;} private final    Xob_cmd_regy cmd_regy = new Xob_cmd_regy();
	public Xob_cmd_mgr			Cmd_mgr() {return cmd_mgr;} private final    Xob_cmd_mgr cmd_mgr;
	public Gfo_usr_dlg			Usr_dlg() {return app.Usr_dlg();}
	public int					Sort_mem_len() {return sort_mem_len;} public Xob_bldr Sort_mem_len_(int v) {sort_mem_len = v; return this;} private int sort_mem_len = 16 * Io_mgr.Len_mb;
	public int					Dump_fil_len() {return dump_fil_len;} public Xob_bldr Dump_fil_len_(int v) {dump_fil_len = v; return this;} private int dump_fil_len =  1 * Io_mgr.Len_mb;
	public int					Make_fil_len() {return make_fil_len;} public Xob_bldr Make_fil_len_(int v) {make_fil_len = v; return this;} private int make_fil_len = 64 * Io_mgr.Len_kb;
	public Xob_xml_parser		Dump_parser() {if (dump_parser == null) this.dump_parser = new Xob_xml_parser(); return dump_parser;}
	public Xob_import_marker	Import_marker() {return import_marker;} private Xob_import_marker import_marker;
	public Xob_wiki_cfg_bldr	Wiki_cfg_bldr() {return wiki_cfg_bldr;} private Xob_wiki_cfg_bldr wiki_cfg_bldr;
	public void					Pause_at_end_(boolean v) {this.pause_at_end = v;}
	public void					Print_prog_msg(long cur, long end, int pct_idx, String fmt, Object... ary) {
		long now = System_.Ticks(); if (now - prv_prog_time < 100) return;
		this.prv_prog_time = now;
		if (pct_idx > -1) ary[pct_idx] = Decimal_adp_.CalcPctStr(cur, end, "00.00");
		app.Usr_dlg().Prog_many("", "", fmt, ary);
	}
	public Xob_bldr Exec_json(String script) {
		try {
			this.cmd_mgr.Clear();
			Json_parser jdoc_parser = new Json_parser();
			Json_doc jdoc = jdoc_parser.Parse(script);
			Json_ary cmds = jdoc.Root_ary();
			int cmds_len = cmds.Len();
			for (int i = 0; i < cmds_len; ++i) {
				Json_nde cmd = cmds.Get_at_as_nde(i);
				byte[] key = cmd.Get_bry_or_null("key");
				Xob_cmd prime = cmd_regy.Get_or_null(String_.new_u8(key));
				if (prime == null) throw Err_.new_("bldr", "bldr.cmd does not exists: cmd={0}", key);
				byte[] wiki_key = cmd.Get_bry_or_null("wiki");
				Xowe_wiki wiki = wiki_key == null ? app.Usere().Wiki() : app.Wiki_mgr().Get_by_or_make(wiki_key);
				Xob_cmd clone = prime.Cmd_clone(this, wiki);
				int atrs_len = cmd.Len();
				for (int j = 0; j < atrs_len; ++j) {
					Json_kv atr_kv = cmd.Get_at_as_kv(j);
					String atr_key = atr_kv.Key_as_str();
					if	(	String_.Eq(atr_key, "key")
						||	String_.Eq(atr_key, "wiki"))	continue;
					byte[] atr_val = atr_kv.Val_as_bry();
					Gfo_invk_.Invk_by_val(clone, atr_key + Gfo_invk_.Mutator_suffix, String_.new_u8(atr_val));
				}
				cmd_mgr.Add(clone);
			}
			gplx.core.threads.Thread_adp_.Start_by_key("bldr_by_json", this, Invk_run_by_kit);
		} catch (Exception e) {
			app.Gui_mgr().Kit().Ask_ok("", "", "error: ~{0}", Err_.Message_gplx_log(e));
		}
		return this;
	}
	private void Run_by_kit() {	// same as Run, but shows exception; don't want to change backward compatibility on Run
		try {this.Run();}
		catch (Exception e) {
			String log_msg = Err_.Message_gplx_log(e);
			Xoa_app_.Usr_dlg().Log_many("", "", log_msg);
			app.Gui_mgr().Kit().Ask_ok("", "", "error: ~{0}", Err_.Message_gplx_full(e));
		}
	}
	public void Run() {
		try {
			app.Bldr__running_(true);
			app.Launch();	// HACK: bldr will be called by a gfs file which embeds "bldr.run" inside it; need to call Launch though before Run; DATE:2013-03-23
			long time_bgn = System_.Ticks();
			int cmd_mgr_len = cmd_mgr.Len();
			for (int i = 0; i < cmd_mgr_len; i++) {
				Xob_cmd cmd = cmd_mgr.Get_at(i);
				cmd.Cmd_init(this);
			}
			cmd_mgr_len = cmd_mgr.Len(); // NOTE: refresh len b/c other cmds may have added new ones in Cmd_init
			for (int i = 0; i < cmd_mgr_len; i++) {
				Xob_cmd cmd = cmd_mgr.Get_at(i);
				app.Usr_dlg().Note_many("", "", "cmd bgn: ~{0}", cmd.Cmd_key());
				long time_cur = System_.Ticks();
				try {
					cmd.Cmd_bgn(this);
					cmd.Cmd_run();
					cmd.Cmd_end();
				} catch (Exception e) {
					throw Err_.new_exc(e, "bldr", "unknown error", "key", cmd.Cmd_key());
				}
				System_.Garbage_collect();
				app.Usr_dlg().Note_many("", "", "cmd end: ~{0} ~{1}", cmd.Cmd_key(), Time_span_.from_(time_cur).XtoStrUiAbbrv());
			}
			for (int i = 0; i < cmd_mgr_len; i++) {
				Xob_cmd cmd = cmd_mgr.Get_at(i);
				cmd.Cmd_term();
			}
			app.Usr_dlg().Note_many("", "", "bldr done: ~{0}", Time_span_.from_(time_bgn).XtoStrUiAbbrv());
			cmd_mgr.Clear();
			if (pause_at_end && !Env_.Mode_testing()) {Console_adp__sys.Instance.Read_line("press enter to continue");}
		}
		catch (Exception e) {
			app.Bldr__running_(false);
			throw Err_.new_exc(e, "bldr", "unknown error");
		}
	}
	private void Cancel() {
		int cmd_mgr_len = cmd_mgr.Len();
		for (int i = 0; i < cmd_mgr_len; i++) {
			Xob_cmd cmd = cmd_mgr.Get_at(i);
			cmd.Cmd_end();
		}
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_pause_at_end_))			pause_at_end = m.ReadBoolOrTrue("val");
		else if	(ctx.Match(k, Invk_cmds))					return cmd_mgr;
		else if	(ctx.Match(k, Invk_wiki_cfg_bldr))			return wiki_cfg_bldr;
		else if	(ctx.Match(k, Invk_sort_mem_len_)) 			sort_mem_len = gplx.core.ios.Io_size_.Load_int_(m);
		else if	(ctx.Match(k, Invk_dump_fil_len_)) 			dump_fil_len = gplx.core.ios.Io_size_.Load_int_(m);
		else if	(ctx.Match(k, Invk_make_fil_len_)) 			make_fil_len = gplx.core.ios.Io_size_.Load_int_(m);
		else if	(ctx.Match(k, Invk_run)) 					Run();
		else if	(ctx.Match(k, Invk_run_by_kit)) 			Run_by_kit();
		else if	(ctx.Match(k, Invk_cancel)) 				Cancel();
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}
	private static final String 
	  Invk_cmds = "cmds", Invk_wiki_cfg_bldr = "wiki_cfg_bldr"
	, Invk_pause_at_end_ = "pause_at_end_", Invk_sort_mem_len_ = "sort_mem_len_", Invk_dump_fil_len_ = "dump_fil_len_", Invk_make_fil_len_ = "make_fil_len_"
	, Invk_cancel = "cancel"
	, Invk_run_by_kit = "run_by_kit"
	;
	public static final String Invk_run = "run";
}
/*
. make_fil_len: max size of made file; EX: /id/..../0000000001.csv will have max len of 64 KB
. dump_fil_len: max size of temp file; EX: /tmp/.../0000000001.csv will have max len of 1 MB
. sort_mem_len: max size of memory for external merge process; note the following
.. a continguous range of memory of that size will be needed: "Bry_bfr_.New(sort_mem_len)" will be called
.. large sort_mem_len will result in smaller number of merge files
... EX: 16 MB will take en.wikipedia.org's 640 MB title files and generate 40 temp files of 8 MB each
.. number of merge files is number of open file channels during merge process
... 40 is a "reasonable" number; the 1st max is 512 (for older windows OS's) and 2048 for Windows XP; Linux seems to be about 7000
.. small sort_mem_len will use smaller buffer; 16 MB / 40 files -> 400 kb buffer for each file
... do not go under max page size for a given row
... for example, a 100 b buffer will fail if a given row is > 100 b (the entire row won't be loaded in memory)
.. smaller buffer will mean more refills which will require more I/O
... EX: 400 kb buffer will require at least 20 refills to read the entire 8 MB file
*/
