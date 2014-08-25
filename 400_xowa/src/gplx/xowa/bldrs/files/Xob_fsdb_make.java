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
package gplx.xowa.bldrs.files; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*;
import gplx.dbs.*; import gplx.xowa.dbs.*; import gplx.fsdb.*; import gplx.ios.*; import gplx.xowa.files.*; import gplx.xowa.files.bins.*; import gplx.xowa.files.fsdb.*; import gplx.xowa.dbs.tbls.*;
import gplx.xowa.bldrs.oimgs.*;
public class Xob_fsdb_make extends Xob_itm_basic_base implements Xob_cmd {
	private int select_interval = 2500, progress_interval = 1, commit_interval = 1, delete_interval = 5000;
	private int exec_count, exec_count_max = Int_.MaxValue;
	private int exec_fail, exec_fail_max = 2000; // 115 over 900k
	private boolean exec_done;
	private int page_id_bmk = -1, lnki_id_bmk = -1;
	private int page_id_val = -1, lnki_id_val = -1;
	private int page_id_end = Int_.MaxValue;
	private boolean reset_db = false, exit_after_commit = false, exit_now = false;
	private byte[] wiki_key;
	private Xobu_poll_mgr poll_mgr; private int poll_interval;
	private long time_bgn;
	private Xodb_xowa_cfg_tbl tbl_cfg; private Db_provider provider; private Db_stmt db_select_stmt;
	private Xof_bin_mgr src_mgr;
	private Xof_fsdb_mgr_sql trg_fsdb_mgr; private Fsdb_mnt_mgr trg_mnt_mgr;
	private ListAdp temp_files = ListAdp_.new_();
	private Fsdb_xtn_img_itm tmp_img_itm = new Fsdb_xtn_img_itm(); private Fsdb_xtn_thm_itm tmp_thm_itm = Fsdb_xtn_thm_itm.new_(); private Fsdb_fil_itm tmp_fil_itm = new Fsdb_fil_itm();
	private boolean app_restart_enabled = false;
	private Xof_fsdb_mgr_sql src_fsdb_mgr;
	public Xob_fsdb_make(Xob_bldr bldr, Xow_wiki wiki) {
		this.Cmd_ctor(bldr, wiki);
		trg_fsdb_mgr = new Xof_fsdb_mgr_sql(wiki);
		trg_fsdb_mgr.Init_by_wiki(wiki);
		src_fsdb_mgr = new Xof_fsdb_mgr_sql(wiki);
		src_fsdb_mgr.Init_by_wiki(wiki);
		src_mgr = src_fsdb_mgr.Bin_mgr();			
		trg_mnt_mgr = trg_fsdb_mgr.Mnt_mgr();
		trg_mnt_mgr.Insert_to_mnt_(Fsdb_mnt_mgr.Mnt_idx_main);	// NOTE: do not delete; mnt_mgr default to Mnt_idx_user; DATE:2014-04-25
		Fsdb_mnt_mgr.Patch(trg_mnt_mgr);	// NOTE: always patch again; fsdb_make may be run separately without lnki_temp; DATE:2014-04-26
		poll_mgr = new Xobu_poll_mgr(bldr.App());
	}
	public String Cmd_key() {return KEY_oimg;} public static final String KEY_oimg = "file.fsdb_make";
	public void Cmd_ini(Xob_bldr bldr) {}
	public void Cmd_bgn(Xob_bldr bldr) {
		((Xof_bin_wkr_http_wmf)src_fsdb_mgr.Bin_mgr().Get_or_new(Xof_bin_wkr_.Key_http_wmf)).Fail_timeout_(1000);	// NOTE: set Fail_timeout here; DATE:2014-06-21; NOTE: do not put in ctor, or else will be 1st wkr; DATE:2014-06-28
		this.wiki_key = wiki.Domain_bry();
		wiki.Init_assert();
		poll_interval = poll_mgr.Poll_interval();
	}
	public void Cmd_run() {Exec();}
	public void Cmd_end() {
		if (exec_done) {
			page_id_bmk = Int_.MaxValue;
			lnki_id_bmk = Int_.MaxValue;
		}
		usr_dlg.Note_many("", "", "done: ~{0} ~{1}", exec_count, DecimalAdp_.divide_safe_(exec_count, Env_.TickCount_elapsed_in_sec(time_bgn)).XtoStr("#,###.000"));
		this.Txn_save();
		tbl_cfg.Delete(Cfg_fsdb_make, Cfg_page_id_bmk); tbl_cfg.Delete(Cfg_fsdb_make, Cfg_lnki_id_bmk);	// delete bmks if future reruns are needed; DATE:2014-08-20
		trg_fsdb_mgr.Txn_save();
		trg_fsdb_mgr.Rls();	// save changes and rls all connections
		db_select_stmt.Rls();
		provider.Rls();
	}
	public void Cmd_print() {}
	private int db_reset_tries_count = 0, db_reset_tries_max = 5;
	public void Exec() {
		Init_db(true);
		if (!Init_bmk(tbl_cfg)) {usr_dlg.Note_many("", "", "make done; delete xowa_cfg to restart"); return;}
		ListAdp list = ListAdp_.new_();
		boolean loop = true;
		time_bgn = Env_.TickCount();
		usr_dlg.Note_many("", "", "total pending: ~{0}", Xob_xfer_regy_tbl.Select_total_pending(provider));
		this.Txn_open();
		while (loop) {
			byte rslt = Select_ttls(list);
			switch (rslt) {
				case Select_ttls_rslt_stop: loop = false; break;
				case Select_ttls_rslt_next_page: ++page_id_val; lnki_id_val = 0; continue;
				case Select_ttls_rslt_process: break;
			}
			if (!loop) break; // no more ttls found
			int list_count = list.Count();
			usr_dlg.Prog_many("", "", "fetched pages: ~{0}", list_count);
			for (int i = 0; i < list_count; i++) {
				Xodb_tbl_oimg_xfer_itm itm = (Xodb_tbl_oimg_xfer_itm)list.FetchAt(i);
				if (!Download_itm(itm)
					|| app_restart_enabled) {
					this.Txn_renew();
					if (db_reset_tries_count < db_reset_tries_max) {
						db_reset_tries_count++;
						usr_dlg.Note_many("", "", "restarting db: ~{0}", db_reset_tries_count);
						Init_db(false);
						i--;
						continue;
					}
					else
						return;
				}
				if (	exit_now
					||	exec_count >= exec_count_max
					||	exec_fail >= exec_fail_max
					||	page_id_val >= page_id_end
					) {
					this.Txn_renew();
					return;
				}
			}
		}
		exec_done = true;
	}
	private void Init_db(boolean chk_reset) {
		Xodb_db_file db_file = Xodb_db_file.init__file_make(wiki.Fsys_mgr().Root_dir());
		provider = db_file.Provider();
		tbl_cfg = new Xodb_xowa_cfg_tbl().Provider_(provider);
		if (reset_db && chk_reset) {
			provider.Exec_qry(Db_qry_.delete_tbl_(Xodb_xowa_cfg_tbl.Tbl_name));
		}
		db_select_stmt = Xob_xfer_regy_tbl.Select_by_page_id_stmt(provider);
	}
	private boolean Init_bmk(Xodb_xowa_cfg_tbl tbl_cfg) {
		String page_id_str = tbl_cfg.Select_val(Cfg_fsdb_make, Cfg_page_id_bmk);
		if (page_id_str == null) {	// bmks not found; new db; insert;
			tbl_cfg.Insert_str(Cfg_fsdb_make, Cfg_page_id_bmk	, Int_.XtoStr(page_id_bmk));
			tbl_cfg.Insert_str(Cfg_fsdb_make, Cfg_lnki_id_bmk	, Int_.XtoStr(lnki_id_bmk));
			if (page_id_bmk == -1)
				page_id_bmk = 0;
			if (lnki_id_bmk == -1)
				lnki_id_bmk = 0;
		}
		else {
			if (page_id_bmk == -1) {
				page_id_bmk = Int_.parse_(page_id_str);
				if (page_id_bmk == Int_.MaxValue) return false;
				usr_dlg.Note_many("", "", "restoring from bmk: page_id=~{0}", page_id_bmk);
			}
			if (lnki_id_bmk == -1) {
				lnki_id_bmk = tbl_cfg.Select_val_as_int(Cfg_fsdb_make, Cfg_lnki_id_bmk);
				usr_dlg.Note_many("", "", "restoring from bmk: lnki_id=~{0}", lnki_id_bmk);
			}
		}
		page_id_val = page_id_bmk;
		lnki_id_val = lnki_id_bmk;
		return true;
	}
	private static final byte Select_ttls_rslt_stop = 0, Select_ttls_rslt_process = 1, Select_ttls_rslt_next_page = 2;
	private byte Select_ttls(ListAdp list) {
		list.Clear();
		DataRdr rdr = DataRdr_.Null;
		boolean pages_found = false, links_found = false;
		try {
			rdr = Xob_xfer_regy_tbl.Select_by_lnki_page_id(provider, page_id_val, select_interval);
			while (rdr.MoveNextPeer()) {
				pages_found = true;	// at least one page found; set true
				Xodb_tbl_oimg_xfer_itm itm = Xodb_tbl_oimg_xfer_itm.new_rdr_(rdr);
				if (itm.Lnki_page_id() == page_id_val		// if same page_id but lnki_id < last, then ignore; needed b/c select selects by page_id, and need to be handle breaks between pages
					&& itm.Lnki_id() <= lnki_id_val)
					continue;
				links_found = true;
				list.Add(itm);
			}
		}	finally {rdr.Rls();}
		if		(pages_found && !links_found)
			return Select_ttls_rslt_next_page;
		else if (!pages_found )
			return Select_ttls_rslt_stop;
		else
			return Select_ttls_rslt_process;
	}
	private boolean Download_itm(Xodb_tbl_oimg_xfer_itm itm) {
		try {
			page_id_val = itm.Lnki_page_id();
			lnki_id_val = itm.Lnki_id();
			Download(itm);
			if ((exec_count % poll_interval) == 0)
				poll_mgr.Poll();
			if (exec_count % commit_interval == 0)
				this.Txn_renew();
			if (exec_count % delete_interval == 0)
				Delete_files();
			return true;
		}
		catch (Exception exc) {
			++exec_fail;
			String exc_message = Err_.Message_gplx_brief(exc);
			usr_dlg.Warn_many("", "", "download error; ttl=~{0} w=~{1} err=~{2}", String_.new_utf8_(itm.Lnki_ttl()), itm.Lnki_w(), exc_message);
			return !String_.Has(exc_message, "out of memory");	// hard stop if "java.sql.SQLException out of memory [java.sql.SQLException]" or "java.sql.SQLException [SQLITE_NOMEM]  A malloc() failed (out of memory) [java.sql.SQLException]"; else code will fail for a hundred or more downloads before coming to a hard stop
		}
	}
	private void Download(Xodb_tbl_oimg_xfer_itm itm) {
		byte[] wiki = itm.Orig_repo() == Xof_repo_itm.Repo_local ? wiki_key : Xow_wiki_.Domain_commons_bry;
		itm.Orig_wiki_(wiki);
		Io_stream_rdr bin_rdr = Io_stream_rdr_.Null;
		if ((exec_count % progress_interval) == 0) {
			int time_elapsed = Env_.TickCount_elapsed_in_sec(time_bgn);
			usr_dlg.Prog_many("", "", "prog: num=~{0} err=~{1} time=~{2} rate=~{3} page=~{4} lnki=~{5} ttl=~{6}", exec_count, exec_fail, time_elapsed,  Math_.Div_safe_as_int(exec_count, time_elapsed), page_id_val, lnki_id_val, String_.new_utf8_(itm.Orig_ttl()));
		}
		try {
			bin_rdr = src_mgr.Find_as_rdr(temp_files, Xof_exec_tid.Tid_wiki_page, itm);
			if (bin_rdr == Io_stream_rdr_.Null)
				Download_fail(itm);
			else {
				Download_pass(itm, bin_rdr);
			}
		}
		finally {
			bin_rdr.Rls();
		}
		++exec_count;
	}
	private void Download_fail(Xodb_tbl_oimg_xfer_itm itm) {
		++exec_fail;
		String lnki_ttl = String_.Format("[[File:{0}|{1}px]]", String_.new_utf8_(itm.Lnki_ttl()), itm.Html_w());
		usr_dlg.Warn_many("", "", "failed: ttl=~{0}", lnki_ttl);
	}
	private void Download_pass(Xodb_tbl_oimg_xfer_itm itm, Io_stream_rdr rdr) {
		int db_uid = -1;
		if (itm.File_is_orig()) {
			if (itm.Lnki_ext().Id_is_image()) {
				trg_fsdb_mgr.Img_insert(tmp_img_itm, itm.Orig_wiki(), itm.Lnki_ttl(), itm.Lnki_ext_id(), itm.Orig_w(), itm.Orig_h(), Sqlite_engine_.Date_null, Fsdb_xtn_thm_tbl.Hash_null, rdr.Len(), rdr);
				db_uid = tmp_img_itm.Id();
			}
			else {
				trg_fsdb_mgr.Fil_insert(tmp_fil_itm, itm.Orig_wiki(), itm.Lnki_ttl(), itm.Lnki_ext_id(), Sqlite_engine_.Date_null, Fsdb_xtn_thm_tbl.Hash_null, rdr.Len(), rdr);
				db_uid = tmp_fil_itm.Id();
			}
		}
		else {
			trg_fsdb_mgr.Thm_insert(tmp_thm_itm, itm.Orig_wiki(), itm.Lnki_ttl(), itm.Lnki_ext_id(), itm.Html_w(), itm.Html_h(), itm.Lnki_thumbtime(), itm.Lnki_page(), Sqlite_engine_.Date_null, Fsdb_xtn_thm_tbl.Hash_null, rdr.Len(), rdr);
			db_uid = tmp_thm_itm.Id();
		}
		if (app.Mode() == Xoa_app_.Mode_gui)
			app.Usr_dlg().Log_direct(String_.Format("download done; size={0} id={1}", rdr.Len(), db_uid));
	}
	private void Txn_renew() {
		this.Txn_save();
		this.Txn_open();
	}
	private void Txn_open() {
		tbl_cfg.Provider().Txn_mgr().Txn_bgn_if_none();
		trg_mnt_mgr.Txn_open();
	}
	private void Txn_save() {
		usr_dlg.Prog_many("", "", "committing data: count=~{0} failed=~{1}", exec_count, exec_fail);
		tbl_cfg.Update(Cfg_fsdb_make, Cfg_page_id_bmk, page_id_val);
		tbl_cfg.Update(Cfg_fsdb_make, Cfg_lnki_id_bmk, lnki_id_val);
		tbl_cfg.Provider().Txn_mgr().Txn_end_all();
		trg_mnt_mgr.Txn_save();
		if (exit_after_commit)
			exit_now = true;
	}
	private void Delete_files() {
		int len = temp_files.Count();
		for (int i = 0;i < len; i++) {
			Io_url url = (Io_url)temp_files.FetchAt(i);
			try		{Io_mgr._.DeleteFil(url);}
			catch	(Exception e)  {
				usr_dlg.Warn_many("", "", "failed to delete temp file: idx=~{0} url=~{1} exc=~{2}", i, url.Raw(), Err_.Message_gplx(e));
			}
		}
		temp_files.Clear();
	}
	@Override public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_progress_interval_))		progress_interval = m.ReadInt("v");
		else if	(ctx.Match(k, Invk_commit_interval_))		commit_interval = m.ReadInt("v");
		else if	(ctx.Match(k, Invk_select_interval_))		select_interval = m.ReadInt("v");
		else if	(ctx.Match(k, Invk_src_mgr))				return src_mgr;
		else if	(ctx.Match(k, Invk_poll_mgr))				return poll_mgr;
		else if	(ctx.Match(k, Invk_reset_db_))				reset_db = m.ReadYn("v");
		else if	(ctx.Match(k, Invk_exec_count_max_))		exec_count_max = m.ReadInt("v");
		else if	(ctx.Match(k, Invk_exec_fail_max_))			exec_fail_max = m.ReadInt("v");
		else if	(ctx.Match(k, Invk_exit_now_))				exit_now = m.ReadYn("v");
		else if	(ctx.Match(k, Invk_exit_after_commit_))		exit_after_commit = m.ReadYn("v");
		else if	(ctx.Match(k, Invk_page_id_bmk_))			page_id_bmk = m.ReadInt("v");
		else if	(ctx.Match(k, Invk_lnki_id_bmk_))			lnki_id_bmk = m.ReadInt("v");
		else if	(ctx.Match(k, Invk_delete_interval_))		delete_interval = m.ReadInt("v");
		else if	(ctx.Match(k, Invk_app_restart_enabled_))	app_restart_enabled = m.ReadBool("v");
		else if	(ctx.Match(k, Invk_db_restart_tries_max_))	db_reset_tries_max = m.ReadInt("v");
		else if	(ctx.Match(k, Invk_trg_fsdb_mgr))			return trg_fsdb_mgr;
		else	return GfoInvkAble_.Rv_unhandled;
		return this;
	}
	private static final String Cfg_fsdb_make = "bldr.fsdb_make", Cfg_page_id_bmk = "page_id_bmk", Cfg_lnki_id_bmk = "lnki_id_bmk";
	private static final String Invk_select_interval_ = "select_interval_", Invk_progress_interval_ = "progress_interval_", Invk_commit_interval_ = "commit_interval_"
	, Invk_page_id_bmk_ = "page_id_bmk_", Invk_lnki_id_bmk_ = "lnki_id_bmk_"
	, Invk_src_mgr = "src_mgr"
	, Invk_poll_mgr = "poll_mgr", Invk_reset_db_ = "reset_db_"
	, Invk_exec_count_max_ = "exec_count_max_", Invk_exec_fail_max_ = "exec_fail_max_", Invk_exit_now_ = "exit_now_", Invk_exit_after_commit_ = "exit_after_commit_"
	, Invk_delete_interval_ = "delete_interval_"
	, Invk_app_restart_enabled_ = "app_restart_enabled_"
	, Invk_db_restart_tries_max_ = "db_restart_tries_max_"		
	, Invk_trg_fsdb_mgr = "trg_fsdb_mgr"	
	;
	public static byte Status_null = 0, Status_pass = 1, Status_fail = 2;
}
class Xodb_tbl_oimg_xfer_itm extends Xof_fsdb_itm {	public int 			Lnki_id() {return lnki_id;} private int lnki_id;
	public int 			Lnki_page_id() {return lnki_page_id;} private int lnki_page_id;
	public int			Lnki_ext_id() {return lnki_ext_id;} private int lnki_ext_id;
	public static Xodb_tbl_oimg_xfer_itm new_rdr_(DataRdr rdr) {
		Xodb_tbl_oimg_xfer_itm rv = new Xodb_tbl_oimg_xfer_itm();
		rv.lnki_id			= rdr.ReadInt(Xob_xfer_regy_tbl.Fld_lnki_id);
		rv.lnki_page_id		= rdr.ReadInt(Xob_xfer_regy_tbl.Fld_lnki_page_id);
		rv.lnki_ext_id		= rdr.ReadInt(Xob_xfer_regy_tbl.Fld_lnki_ext);
		rv.Orig_repo_		 (rdr.ReadByte(Xob_xfer_regy_tbl.Fld_orig_repo));
		rv.File_is_orig_	 (rdr.ReadByte(Xob_xfer_regy_tbl.Fld_file_is_orig) == Bool_.Y_byte);
		byte[] ttl = rdr.ReadBryByStr(Xob_xfer_regy_tbl.Fld_lnki_ttl);
		rv.Orig_ttl_(ttl);
		rv.Lnki_ttl_(ttl);
		rv.Orig_size_(rdr.ReadInt(Xob_xfer_regy_tbl.Fld_orig_w), rdr.ReadInt(Xob_xfer_regy_tbl.Fld_orig_h));
		rv.Html_size_(rdr.ReadInt(Xob_xfer_regy_tbl.Fld_file_w), rdr.ReadInt(Xob_xfer_regy_tbl.Fld_file_h));	// set html_size as file_size (may try to optimize later by removing similar thumbs (EX: 220,221 -> 220))
		rv.Lnki_size_(rdr.ReadInt(Xob_xfer_regy_tbl.Fld_file_w), rdr.ReadInt(Xob_xfer_regy_tbl.Fld_file_h));	// set lnki_size; Xof_bin_mgr uses lnki_size;			
		rv.Lnki_page_			(Xof_doc_page.Db_load_int(rdr, Xob_xfer_regy_tbl.Fld_lnki_page));
		rv.Lnki_thumbtime_		(Xof_doc_thumb.Db_load_double(rdr, Xob_xfer_regy_tbl.Fld_lnki_time));
		return rv;
	}
}
