/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012 gnosygnu@gmail.com

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package gplx.xowa; import gplx.*;
import gplx.xowa.bldrs.*; import gplx.xowa.apps.*; import gplx.xowa.bldrs.xmls.*;
import gplx.xowa.bldrs.cfgs.*;
public class Xob_bldr implements GfoInvkAble {
	private boolean pause_at_end = false;
	public Xob_bldr(Xoa_app app) {
		this.app = app;
		cmd_mgr = new Xob_cmd_mgr(this);
		wiki_cfg_bldr = new Xob_wiki_cfg_bldr(this);
	}
	public Xoa_app				App() {return app;} private Xoa_app app;
	public Gfo_usr_dlg			Usr_dlg() {return app.Usr_dlg();}
	public Xob_cmd_mgr			Cmd_mgr() {return cmd_mgr;} private Xob_cmd_mgr cmd_mgr;
	public int					Sort_mem_len() {return sort_mem_len;} public Xob_bldr Sort_mem_len_(int v) {sort_mem_len = v; return this;} private int sort_mem_len = 16 * Io_mgr.Len_mb;
	public int					Dump_fil_len() {return dump_fil_len;} public Xob_bldr Dump_fil_len_(int v) {dump_fil_len = v; return this;} private int dump_fil_len =  1 * Io_mgr.Len_mb;
	public int					Make_fil_len() {return make_fil_len;} public Xob_bldr Make_fil_len_(int v) {make_fil_len = v; return this;} private int make_fil_len = 64 * Io_mgr.Len_kb;
	public Xob_xml_parser		Parser() {if (parser == null) parser = new Xob_xml_parser(); return parser;} private Xob_xml_parser parser;
	public Xob_wiki_cfg_bldr	Wiki_cfg_bldr() {return wiki_cfg_bldr;} private Xob_wiki_cfg_bldr wiki_cfg_bldr;
	public void StatusMgr_prog_fmt(long cur, long end, int pct_idx, String fmt, Object... ary) {
		long now = Env_.TickCount(); if (now - StatusMgr_prog_prv < 100) return;
		StatusMgr_prog_prv = now;
		if (pct_idx > -1) ary[pct_idx] = DecimalAdp_.CalcPctStr(cur, end, "00.00");
		app.Usr_dlg().Prog_many(GRP_KEY, "prog", fmt, ary);
	}	long StatusMgr_prog_prv;
	public void Pause_at_end_(boolean v) {pause_at_end = v;}
	public void Run() {
		try {
			app.Launch();	// HACK: bldr will be called by a gfs file which embeds "bldr.run" inside it; need to call Launch though before Run; DATE:2013-03-23
			long time_bgn = Env_.TickCount();
			int cmd_mgr_len = cmd_mgr.Len();
			for (int i = 0; i < cmd_mgr_len; i++) {
				Xob_cmd cmd = cmd_mgr.Get_at(i);
				cmd.Cmd_ini(this);
			}
			cmd_mgr_len = cmd_mgr.Len(); // NOTE: refresh len b/c other cmds may have other added
			for (int i = 0; i < cmd_mgr_len; i++) {
				Xob_cmd cmd = cmd_mgr.Get_at(i);
				app.Usr_dlg().Note_many(GRP_KEY, "cmd_bgn", "cmd bgn: ~{0}", cmd.Cmd_key());
				long time_cur = Env_.TickCount();
				cmd.Cmd_bgn(this);
				cmd.Cmd_run();
				cmd.Cmd_end();
				Env_.GarbageCollect();
				app.Usr_dlg().Note_many(GRP_KEY, "cmd_end", "cmd end: ~{0} ~{1}", cmd.Cmd_key(), TimeSpanAdp_.from_(time_cur).XtoStrUiAbbrv());
			}
			for (int i = 0; i < cmd_mgr_len; i++)
				cmd_mgr.Get_at(i).Cmd_print();
			app.Usr_dlg().Note_many(GRP_KEY, "bldr_done", "bldr done: ~{0}", TimeSpanAdp_.from_(time_bgn).XtoStrUiAbbrv());
			cmd_mgr.Clear();
			if (pause_at_end && !Env_.Mode_testing()) {ConsoleAdp._.ReadLine("press enter to continue");}
		}	catch (Exception e) {throw Err_.err_(e, "error during build: ~{0}", Err_.Message_gplx(e));}
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
		else if	(ctx.Match(k, Invk_sort_mem_len_)) 			sort_mem_len = gplx.ios.Io_size_.Load_int_(m);
		else if	(ctx.Match(k, Invk_dump_fil_len_)) 			dump_fil_len = gplx.ios.Io_size_.Load_int_(m);
		else if	(ctx.Match(k, Invk_make_fil_len_)) 			make_fil_len = gplx.ios.Io_size_.Load_int_(m);
		else if	(ctx.Match(k, Invk_run)) 					Run();
		else if	(ctx.Match(k, Invk_cancel)) 				Cancel();
		else	return GfoInvkAble_.Rv_unhandled;
		return this;
	}
	private static final String 
	  Invk_cmds = "cmds", Invk_wiki_cfg_bldr = "wiki_cfg_bldr"
	, Invk_pause_at_end_ = "pause_at_end_", Invk_sort_mem_len_ = "sort_mem_len_", Invk_dump_fil_len_ = "dump_fil_len_", Invk_make_fil_len_ = "make_fil_len_"
	, Invk_run = "run", Invk_cancel = "cancel"
	;
	static final String GRP_KEY = "xowa.bldr";
}
/*
. make_fil_len: max size of made file; EX: /id/..../0000000001.csv will have max len of 64 KB
. dump_fil_len: max size of temp file; EX: /tmp/.../0000000001.csv will have max len of 1 MB
. sort_mem_len: max size of memory for external merge process; note the following
.. a continguous range of memory of that size will be needed: "Bry_bfr.new_(sort_mem_len)" will be called
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
