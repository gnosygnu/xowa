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
package gplx.xowa.bldrs.cmds; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*;
import gplx.core.envs.*;
import gplx.dbs.*; import gplx.xowa.wikis.caches.*; import gplx.xowa.addons.bldrs.files.*; import gplx.xowa.files.origs.*;
import gplx.xowa.bldrs.wkrs.*;
import gplx.xowa.wikis.nss.*;
import gplx.xowa.wikis.data.*; import gplx.xowa.wikis.dbs.*; import gplx.xowa.wikis.data.tbls.*;
import gplx.xowa.addons.bldrs.files.utls.*;
import gplx.xowa.parsers.*; import gplx.xowa.parsers.tmpls.*;
public abstract class Xob_dump_mgr_base extends Xob_itm_basic_base implements Xob_cmd, Gfo_invk {
	private Xob_dump_src_id page_src;
	private Xow_db_mgr db_fsys_mgr; protected Xop_parser parser; protected Xop_ctx ctx; protected Xop_root_tkn root;
	private int[] ns_ary; private Xow_db_file[] db_ary;
	private int ns_bgn = -1, db_bgn = -1, pg_bgn = -1;
	private int ns_end = -1, db_end = -1, pg_end = Int_.Max_value;
	private int commit_interval = 1000, progress_interval = 250, cleanup_interval = 2500, select_size = 10 * Io_mgr.Len_mb;
	private int exec_count, exec_count_max = Int_.Max_value;
	private boolean reset_db = false, exit_after_commit = false, exit_now = false;
	private boolean load_tmpls;
	private Xob_dump_bmk_mgr bmk_mgr = new Xob_dump_bmk_mgr();
	private Xobu_poll_mgr poll_mgr; private int poll_interval = 5000;
	private Xob_rate_mgr rate_mgr = new Xob_rate_mgr();
	public abstract String Cmd_key();
	@Override protected void Cmd_ctor_end(Xob_bldr bldr, Xowe_wiki wiki) {
		poll_mgr = new Xobu_poll_mgr(bldr.App());	// init in ctor so gfs can invoke methods
	}
	public void Cmd_bgn(Xob_bldr bldr) {
		parser = wiki.Parser_mgr().Main();
		ctx = wiki.Parser_mgr().Ctx();
		root = ctx.Tkn_mkr().Root(Bry_.Empty);
		wiki.Init_assert();	// NOTE: must init wiki for db_mgr_as_sql

		// assert by calling Db_mgr_as_sql
		wiki.Db_mgr_as_sql().Core_data_mgr();

		// load db_mgr
		Xow_db_mgr.Init_by_load(wiki, gplx.xowa.wikis.data.Xow_db_file__core_.Find_core_fil_or_null(wiki));	// NOTE: must reinit providers as previous steps may have rls'd (and left member variable conn which is closed)

		wiki.File__orig_mgr().Wkrs__del(Xof_orig_wkr_.Tid_wmf_api);
		db_fsys_mgr = wiki.Db_mgr_as_sql().Core_data_mgr();
		db_ary = Xob_dump_mgr_base_.Init_text_files_ary(db_fsys_mgr);
		poll_interval = poll_mgr.Poll_interval();

		page_src = new Xob_dump_src_id().Init(wiki, this.Init_redirect(), select_size);
		ns_ary = Init_ns_ary();
		Db_conn conn = Init_db_file();
		Io_url wiki_dir = wiki.Fsys_mgr().Root_dir();
		bmk_mgr.Cfg_url_(wiki_dir.GenSubFil("xowa.file.make.cfg.gfs"));
		rate_mgr.Log_file_(wiki_dir.GenSubFil("xowa.file.make.log.csv"));
		if (reset_db) {
			bmk_mgr.Reset();
			Init_reset(conn);
		}
		bmk_mgr.Load(wiki.Appe(), this);
		Cmd_bgn_end();
	}
	protected abstract void Cmd_bgn_end();
	public abstract byte Init_redirect();
	public abstract int[] Init_ns_ary();
	protected abstract void Init_reset(Db_conn p);
	protected abstract Db_conn Init_db_file();
	private long time_bgn;
	public void Cmd_run() {Exec_ns_ary();}
	private void Exec_ns_ary() {
		if (pg_bgn == Int_.Max_value) return;
		if (load_tmpls) Xob_dump_mgr_base_.Load_all_tmpls(usr_dlg, wiki, page_src);
		time_bgn = System_.Ticks();
		Xob_dump_bmk dump_bmk = new Xob_dump_bmk();
		rate_mgr.Init();
		int ns_ary_len = ns_ary.length;
		for (int i = 0; i < ns_ary_len; i++) {
			int ns_id = ns_ary[i];
			if (ns_bgn != -1) {							// ns_bgn set
				if (ns_id == ns_bgn)					// ns_id is ns_bgn; null out ns_bgn and continue
					ns_bgn = -1;
				else									// ns_id is not ns_bgn; keep looking
					continue;
			}
			dump_bmk.Ns_id_(ns_id);
			Exec_db_ary(i, dump_bmk, ns_id);
			if (ns_id == ns_end) exit_now = true;		// ns_end set; exit
			if (exit_now) break;						// exit_now b/c of pg_bgn, db_bgn or something else
		}
		Exec_commit(dump_bmk.Ns_id(), dump_bmk.Db_id(), dump_bmk.Pg_id(), Bry_.Empty);
	}
	private void Exec_db_ary(int ns_ord, Xob_dump_bmk dump_bmk, int ns_id) {
		int db_ary_len = db_ary.length;
		for (int i = 0; i < db_ary_len; i++) {
			int db_id = db_ary[i].Id();
			if (db_bgn != -1) {							// db_bgn set
				if (db_id == db_bgn)					// db_id is db_bgn; null out db_bgn and continue
					db_bgn = -1;
				else									// db_id is not db_bgn; keep looking
					continue;
			}
			dump_bmk.Db_id_(db_id);
			Exec_db_itm(dump_bmk, ns_ord, ns_id, db_id);
			if (db_id == db_end) exit_now = true;		// db_end set; exit;
			if (exit_now) return;						// exit_now b/c of pg_bgn, db_bgn or something else
		}
	}
	private void Exec_db_itm(Xob_dump_bmk dump_bmk, int ns_ord, int ns_id, int db_id) {
		List_adp pages = List_adp_.New();
		Xow_ns ns = wiki.Ns_mgr().Ids_get_or_null(ns_id);
		int pg_id = pg_bgn;
		while (true) {
			page_src.Get_pages(pages, db_id, ns_id, pg_id);
			int pages_len = pages.Count();
			if (pages_len == 0) {	// no more pages in db;
				if (pg_id > pg_bgn)	// reset pg_bgn to 0 only if pg_bgn seen;
					pg_bgn = 0;
				return;	
			}
			usr_dlg.Prog_many("", "", "fetched pages: ~{0}", pages_len);
			for (int i = 0; i < pages_len; i++) {
				Xowd_page_itm page = (Xowd_page_itm)pages.Get_at(i);
				dump_bmk.Pg_id_(pg_id);
				Exec_pg_itm(ns_ord, ns, db_id, page);
				if (	pg_id		>= pg_end
					||	exec_count	>= exec_count_max) {
					exit_now = true;
				}
				if (exit_now) return;
				pg_id = page.Id();
			}
		}
	}
	private void Exec_pg_itm(int ns_ord, Xow_ns ns, int db_id, Xowd_page_itm page) {
		try {
			if ((exec_count % progress_interval) == 0)
				usr_dlg.Prog_many("", "", "parsing: ns=~{0} db=~{1} pg=~{2} count=~{3} time=~{4} rate=~{5} ttl=~{6}"
					, ns.Id(), db_id, page.Id(), exec_count
					, System_.Ticks__elapsed_in_sec(time_bgn), rate_mgr.Rate_as_str(), String_.new_u8(page.Ttl_page_db()));
			ctx.Clear_all();
			byte[] page_src = page.Text();
			if (page_src != null)	// some pages have no text; ignore them else null ref; PAGE: it.d:miercuri DATE:2015-12-05
				Exec_pg_itm_hook(ns_ord, ns, page, page_src);
			ctx.Wiki().Utl__bfr_mkr().Clear_fail_check();	// make sure all bfrs are released
			if (ctx.Wiki().Cache_mgr().Tmpl_result_cache().Count() > 50000) 
				ctx.Wiki().Cache_mgr().Tmpl_result_cache().Clear();
			++exec_count;
			rate_mgr.Increment();
			if ((exec_count % poll_interval) == 0)
				poll_mgr.Poll();
			if	((exec_count % commit_interval) == 0)
				Exec_commit(ns.Id(), db_id, page.Id(), page.Ttl_page_db());
			if ((exec_count % cleanup_interval) == 0)
				Free();
		}
		catch (Exception exc) {
			bldr.Usr_dlg().Warn_many("", "", "parse failed: wiki=~{0} ttl=~{1} err=~{2}", wiki.Domain_str(), page.Ttl_full_db(), Err_.Message_gplx_log(exc));
			ctx.Wiki().Utl__bfr_mkr().Clear();
			this.Free();
		}
	}
	public abstract void Exec_pg_itm_hook(int ns_ord, Xow_ns ns, Xowd_page_itm page, byte[] page_text);
	private void Exec_commit(int ns_id, int db_id, int pg_id, byte[] ttl) {
		usr_dlg.Prog_many("", "", "committing: ns=~{0} db=~{1} pg=~{2} count=~{3} ttl=~{4}", ns_id, db_id, pg_id, exec_count, String_.new_u8(ttl));
		Exec_commit_hook();
		bmk_mgr.Save(ns_id, db_id, pg_id);
		if (exit_after_commit) exit_now = true;
	}
	public abstract void Exec_commit_hook();
	public abstract void Exec_end_hook();
	public void Cmd_init(Xob_bldr bldr) {}
	public void Cmd_term() {}		
	public void Cmd_end() {
		if (!exit_now)
			pg_bgn = Int_.Max_value;
		Exec_commit(-1, -1, -1, Bry_.Empty);
		Exec_end_hook();
		Free();
		usr_dlg.Note_many("", "", "done: ~{0} ~{1}", exec_count, Decimal_adp_.divide_safe_(exec_count, System_.Ticks__elapsed_in_sec(time_bgn)).To_str("#,###.000"));
	}
	private void Free() {
		Xowe_wiki_.Rls_mem(wiki, true);
	}
	protected void Reset_db_y_() {this.reset_db = true;}
	@Override public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_commit_interval_))		commit_interval = m.ReadInt("v");
		else if	(ctx.Match(k, Invk_progress_interval_))		progress_interval = m.ReadInt("v");
		else if	(ctx.Match(k, Invk_rate_interval_))			rate_mgr.Reset_interval_(m.ReadInt("v"));
		else if	(ctx.Match(k, Invk_cleanup_interval_))		cleanup_interval = m.ReadInt("v");
		else if	(ctx.Match(k, Invk_select_size_))			select_size = m.ReadInt("v") * Io_mgr.Len_mb;
		else if	(ctx.Match(k, Invk_ns_bgn_))				{ns_bgn = m.ReadInt("v"); Notify_restoring("ns", ns_bgn);}
		else if	(ctx.Match(k, Invk_db_bgn_))				{db_bgn = m.ReadInt("v"); Notify_restoring("db", db_bgn);}
		else if	(ctx.Match(k, Invk_pg_bgn_))				{pg_bgn = m.ReadInt("v"); Notify_restoring("pg", pg_bgn);}
		else if	(ctx.Match(k, Invk_ns_end_))				ns_end = m.ReadInt("v");
		else if	(ctx.Match(k, Invk_db_end_))				db_end = m.ReadInt("v");
		else if	(ctx.Match(k, Invk_pg_end_))				pg_end = m.ReadInt("v");
		else if	(ctx.Match(k, Invk_load_tmpls_))			load_tmpls = m.ReadYn("v");
		else if	(ctx.Match(k, Invk_poll_mgr))				return poll_mgr;
		else if	(ctx.Match(k, Invk_reset_db_))				reset_db = m.ReadYn("v");
		else if	(ctx.Match(k, Invk_exec_count_max_))		exec_count_max = m.ReadInt("v");
		else if	(ctx.Match(k, Invk_exit_now_))				exit_now = m.ReadYn("v");
		else if	(ctx.Match(k, Invk_exit_after_commit_))		exit_after_commit = m.ReadYn("v");
		else if	(ctx.Match(k, Invk__manual_now_))			Datetime_now.Manual_and_freeze_(m.ReadDate("v"));
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}
	private void Notify_restoring(String itm, int val) {
		usr_dlg.Note_many("", "", "restoring: itm=~{0} val=~{1}", itm, val);
	}
	public static final    String 
	  Invk_progress_interval_ = "progress_interval_", Invk_commit_interval_ = "commit_interval_", Invk_cleanup_interval_ = "cleanup_interval_", Invk_rate_interval_ = "rate_interval_"
	, Invk_select_size_ = "select_size_"
	, Invk_ns_bgn_ = "ns_bgn_", Invk_db_bgn_ = "db_bgn_", Invk_pg_bgn_ = "pg_bgn_"
	, Invk_ns_end_ = "ns_end_", Invk_db_end_ = "db_end_", Invk_pg_end_ = "pg_end_"
	, Invk_load_tmpls_ = "load_tmpls_"
	, Invk_poll_mgr = "poll_mgr", Invk_reset_db_ = "reset_db_"
	, Invk_exec_count_max_ = "exec_count_max_", Invk_exit_now_ = "exit_now_", Invk_exit_after_commit_ = "exit_after_commit_"
	, Invk__manual_now_ = "manual_now_"
	;
}
class Xob_dump_mgr_base_ {
	public static void Load_all_tmpls(Gfo_usr_dlg usr_dlg, Xowe_wiki wiki, Xob_dump_src_id page_src) {
		List_adp pages = List_adp_.New();
		Xow_ns ns_tmpl = wiki.Ns_mgr().Ns_template();
		Xow_defn_cache defn_cache = wiki.Cache_mgr().Defn_cache();
		int cur_page_id = -1;
		int load_count = 0;
		usr_dlg.Note_many("", "", "tmpl_load init");
		while (true) {
			page_src.Get_pages(pages, 0, Xow_ns_.Tid__template, cur_page_id);	// 0 is always template db
			int page_count = pages.Count();
			if (page_count == 0) break;	// no more pages in db;
			Xowd_page_itm page = null;
			for (int i = 0; i < page_count; i++) {
				page = (Xowd_page_itm)pages.Get_at(i);
				Xot_defn_tmpl defn = new Xot_defn_tmpl();
				defn.Init_by_new(ns_tmpl, ns_tmpl.Gen_ttl(page.Ttl_page_db()), page.Text(), null, false);	// NOTE: passing null, false; will be overriden later when Parse is called
				defn_cache.Add(defn, ns_tmpl.Case_match());
				++load_count;
				if ((load_count % 10000) == 0) usr_dlg.Prog_many("", "", "tmpl_loading: ~{0}", load_count);
			}
			cur_page_id = page.Id();
		}
		usr_dlg.Note_many("", "", "tmpl_load done: ~{0}", load_count);
	}
	public static Xow_db_file[] Init_text_files_ary(Xow_db_mgr core_data_mgr) {
		List_adp text_files_list = List_adp_.New();
		int len = core_data_mgr.Dbs__len();
		if (len == 1) return new Xow_db_file[] {core_data_mgr.Dbs__get_at(0)};	// single file: return core; note that there are no Tid = Text
		for (int i = 0; i < len; i++) {
			Xow_db_file file = core_data_mgr.Dbs__get_at(i);
			switch (file.Tid()) {
				case Xow_db_file_.Tid__text:
				case Xow_db_file_.Tid__text_solo:
					text_files_list.Add(file);
					break;
			}
		}
		return (Xow_db_file[])text_files_list.To_ary_and_clear(Xow_db_file.class);
	}
}
class Xob_dump_bmk_mgr {
	private Bry_bfr save_bfr = Bry_bfr_.Reset(1024);
	public Io_url Cfg_url() {return cfg_url;} public Xob_dump_bmk_mgr Cfg_url_(Io_url v) {cfg_url = v; return this;} private Io_url cfg_url;
	public void Reset() {Io_mgr.Instance.DeleteFil(cfg_url);}
	public void Load(Xoae_app app, Xob_dump_mgr_base dump_mgr) {
		app.Gfs_mgr().Run_url_for(dump_mgr, cfg_url);
	}
	public void Save(int ns_id, int db_id, int pg_id) {
		Save_itm(save_bfr, Xob_dump_mgr_base.Invk_ns_bgn_, ns_id);
		Save_itm(save_bfr, Xob_dump_mgr_base.Invk_db_bgn_, db_id);
		Save_itm(save_bfr, Xob_dump_mgr_base.Invk_pg_bgn_, pg_id);
		Io_mgr.Instance.SaveFilBfr(cfg_url, save_bfr);
	}
	private void Save_itm(Bry_bfr save_bfr, String key, int val) {
		String fmt = "{0}('{1}');\n";
		String str = String_.Format(fmt, key, val);
		save_bfr.Add_str_u8(str);
	}
}
class Xob_rate_mgr {
	private long time_bgn;
	private int item_len;
	private Bry_bfr save_bfr = Bry_bfr_.Reset(255);
	public int Reset_interval() {return reset_interval;} public Xob_rate_mgr Reset_interval_(int v) {reset_interval = v; return this;} private int reset_interval = 10000;
	public Io_url Log_file_url() {return log_file;} public Xob_rate_mgr Log_file_(Io_url v) {log_file = v; return this;} private Io_url log_file;
	public void Init() {time_bgn = System_.Ticks();}
	public void Increment() {
		++item_len;
		if (item_len % reset_interval == 0) {
			long time_end = System_.Ticks();
			Save(item_len, time_bgn, time_end);
			time_bgn = time_end;
			item_len = 0;
		}
	}
	private void Save(int count, long bgn, long end) {
		int dif = (int)(end - bgn) / 1000;
		Decimal_adp rate = Decimal_adp_.divide_safe_(count, dif);
		save_bfr
			.Add_str_a7(rate.To_str("#,##0.000")).Add_byte_pipe()
			.Add_int_variable(count).Add_byte_pipe()
			.Add_int_variable(dif).Add_byte_nl()
			;
		Io_mgr.Instance.AppendFilByt(log_file, save_bfr.To_bry_and_clear());
	}
	public String Rate_as_str() {return Int_.To_str(Rate());}
	public int Rate() {
		int elapsed = System_.Ticks__elapsed_in_sec(time_bgn);
		return Math_.Div_safe_as_int(item_len, elapsed);
	}
}
class Xob_dump_bmk {
	public int Ns_id() {return ns_id;} public Xob_dump_bmk Ns_id_(int v) {ns_id = v; return this;} private int ns_id;
	public int Db_id() {return db_id;} public Xob_dump_bmk Db_id_(int v) {db_id = v; return this;} private int db_id;
	public int Pg_id() {return pg_id;} public Xob_dump_bmk Pg_id_(int v) {pg_id = v; return this;} private int pg_id;
}
