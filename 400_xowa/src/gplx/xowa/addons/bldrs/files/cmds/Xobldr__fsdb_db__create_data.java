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
package gplx.xowa.addons.bldrs.files.cmds; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.files.*;
import gplx.core.stores.*; import gplx.core.envs.*; import gplx.core.ios.streams.*;
import gplx.dbs.*; import gplx.dbs.cfgs.*; import gplx.dbs.engines.sqlite.*;
import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.wkrs.*;
import gplx.xowa.wikis.domains.*; import gplx.xowa.wikis.data.*; import gplx.xowa.wikis.dbs.*; import gplx.fsdb.*; import gplx.core.ios.*; import gplx.xowa.wikis.data.tbls.*;
import gplx.xowa.files.*; import gplx.xowa.files.repos.*; import gplx.xowa.files.bins.*; import gplx.xowa.files.fsdb.*;	
import gplx.fsdb.data.*; import gplx.fsdb.meta.*;
import gplx.xowa.addons.bldrs.files.dbs.*; import gplx.xowa.addons.bldrs.files.utls.*;
public class Xobldr__fsdb_db__create_data extends Xob_cmd__base implements Xob_cmd {
	private Db_conn bldr_conn; private Db_cfg_tbl bldr_cfg_tbl;
	private Xof_bin_mgr src_bin_mgr; private Xof_bin_wkr__fsdb_sql src_fsdb_wkr; private boolean src_bin_mgr__cache_enabled = Bool_.N; private String src_bin_mgr__fsdb_version; private String[] src_bin_mgr__fsdb_skip_wkrs; private boolean src_bin_mgr__wmf_enabled;
	private Fsm_mnt_itm trg_mnt_itm; private Fsm_cfg_mgr trg_cfg_mgr; private Fsm_atr_fil trg_atr_fil; private Fsm_bin_fil trg_bin_fil; private long trg_bin_db_max; private String trg_bin_mgr__fsdb_version;
	private final    Xof_bin_updater trg_bin_updater = new Xof_bin_updater(); private Xob_bin_db_mgr bin_db_mgr; private int[] ns_ids; private int prv_lnki_tier_id = -1;
	private long download_size_max = Io_mgr.Len_mb_long * 5; private int[] download_keep_tier_ids = Int_.Ary(0);
	private Xobu_poll_mgr poll_mgr; private int poll_interval; private long time_bgn;
	private int select_interval = 2500, progress_interval = 1, commit_interval = 1, delete_interval = 5000;
	private boolean exec_done, resume_enabled; private int exec_count, exec_count_max = Int_.Max_value, exec_fail, exec_fail_max = 10000; // 115 errors over 900k images		
	private int tier_id_bmk = -1, tier_id_val = -1; private int page_id_bmk = -1, page_id_val = -1, page_id_end = Int_.Max_value; private int lnki_id_bmk = -1, lnki_id_val = -1;
	private boolean exit_after_commit, exit_now;
	public Xobldr__fsdb_db__create_data(Xob_bldr bldr, Xowe_wiki wiki) {super(bldr, wiki);
		if (bldr != null) {
			this.poll_mgr = new Xobu_poll_mgr(bldr.App());
			wiki.File__fsdb_mode().Tid__v2__bld__y_();
			this.src_bin_mgr = new Xof_bin_mgr(new Fsm_mnt_mgr(), wiki.File__repo_mgr(), app.File__img_mgr().Wkr_resize_img(), app.Wmf_mgr().Download_wkr().Download_xrg().Download_fmt());
		}
	}
	@Override public void Cmd_bgn(Xob_bldr bldr) {			
		wiki.Init_assert();
		this.poll_interval = poll_mgr.Poll_interval();
		this.bin_db_mgr = new Xob_bin_db_mgr(ns_ids);
		// src_bin_mgr
		if (src_bin_mgr__fsdb_version != null) {
			this.src_fsdb_wkr = Xof_bin_wkr__fsdb_sql.new_(wiki.File__mnt_mgr());
			src_bin_mgr.Wkrs__add(src_fsdb_wkr);
			src_fsdb_wkr.Mnt_mgr().Ctor_by_load(new_src_bin_db_mgr(wiki, src_bin_mgr__fsdb_version));
			src_fsdb_wkr.Mnt_mgr().Mnts__get_main().Txn_bgn();			// NOTE: txn on atr speeds up from 50 -> 300; DATE:2015-03-21				
			if (src_bin_mgr__fsdb_skip_wkrs != null) {
				src_fsdb_wkr.Skip_mgr_init(src_fsdb_wkr.Mnt_mgr().Mnts__get_main().Cfg_mgr(), src_bin_mgr__fsdb_skip_wkrs);
			}
			if (src_bin_mgr__cache_enabled) {
				usr_dlg.Prog_many("", "", "src_bin_mgr.cache.bgn");
				src_fsdb_wkr.Mnt_mgr().Mnts__get_main().Atr_mgr().Db__core().Fil_cache_enabled_y_();
				usr_dlg.Prog_many("", "", "src_bin_mgr.cache.end");
			}
		}
		if (src_bin_mgr__wmf_enabled) {
			Xof_bin_wkr__http_wmf wmf_wkr = Xof_bin_wkr__http_wmf.new_(wiki);
			src_bin_mgr.Wkrs__add(wmf_wkr);
			wmf_wkr.Fail_timeout_(0);	// 1000; NOTE: set Fail_timeout here; DATE:2014-06-21; NOTE: do not put in ctor, or else will be 1st wkr; DATE:2014-06-28
		}
		// trg_mnt_itm
		this.trg_bin_db_max = Xobldr_cfg.Max_size__file(app);
		Io_url trg_file_dir_v1 = String_.Eq(trg_bin_mgr__fsdb_version, "v1") ? wiki.Fsys_mgr().File_dir().GenNewNameOnly(wiki.Domain_str() + "-prv") : wiki.Fsys_mgr().File_dir();	// NOTE: convoluted way of setting trg to -prv if trg_bin_mgr__fsdb_version_v1 is set; otherwise set to "en.wikipedia.org" which will noop; DATE:2015-12-02
		Fsdb_db_mgr trg_db_mgr = Fsdb_db_mgr_.new_detect(wiki, wiki.Fsys_mgr().Root_dir(), trg_file_dir_v1);
		if (trg_db_mgr == null) trg_db_mgr = Fsdb_db_mgr__v2_bldr.Get_or_make(wiki, Bool_.Y);
		Fsm_mnt_mgr trg_mnt_mgr = new Fsm_mnt_mgr(); trg_mnt_mgr.Ctor_by_load(trg_db_mgr);
		trg_mnt_mgr.Mnts__get_insert_idx_(Fsm_mnt_mgr.Mnt_idx_main);		// NOTE: do not delete; mnt_mgr default to Mnt_idx_user; DATE:2014-04-25
		this.trg_mnt_itm = trg_mnt_mgr.Mnts__get_insert();
		Fsm_mnt_mgr.Patch(trg_mnt_itm.Cfg_mgr().Tbl());						// NOTE: always patch again; fsdb_make may be run separately without lnki_temp; DATE:2014-04-26
		this.trg_atr_fil = trg_mnt_itm.Atr_mgr().Db__core();
		this.trg_cfg_mgr = trg_mnt_itm.Cfg_mgr();
		bin_db_mgr.Init_by_mnt_mgr(trg_mnt_mgr);
		trg_atr_fil.Conn().Txn_bgn("bldr__fsdb_make__trg_atr_fil");			
		if (!trg_atr_fil.Conn().Eq(trg_cfg_mgr.Tbl().Conn()))	// need to create txn for v1; DATE:2015-07-04
			trg_cfg_mgr.Tbl().Conn().Txn_bgn("bldr__fsdb_make__trg_cfg_fil");
		// bldr_db
		Xob_db_file bldr_db = Xob_db_file.New__file_make(wiki.Fsys_mgr().Root_dir());
		this.bldr_conn = bldr_db.Conn();
		this.bldr_cfg_tbl = bldr_db.Tbl__cfg();	// NOTE: cfg and atr is in same db; use it
		bldr_cfg_tbl.Conn().Txn_bgn("bldr__fsdb_make__bldr_cfg_tbl");
	}
	@Override public void Cmd_run() {
		Init_bldr_bmks();
		this.time_bgn = System_.Ticks();
		int total_pending = Xob_xfer_regy_tbl.Select_total_pending(bldr_conn);
		// if (total_pending > 250000 && src_bin_mgr__fsdb_version == null) 
		usr_dlg.Note_many("", "", "total pending: ~{0}", total_pending);
		List_adp list = List_adp_.New();
		boolean loop = true;
		while (loop) {
			byte rslt = Select_fsdb_itms(list);
			switch (rslt) {
				case Select_rv_stop:
					if (bin_db_mgr.Tier_id_is_last(tier_id_val))
						loop = false;
					else {
						++tier_id_val;
						page_id_val = 0;
						continue;
					}
					break;
				case Select_rv_next_page:	++page_id_val; lnki_id_val = 0; continue;
				case Select_rv_process:		break;
			}
			if (!loop) break; // no more ttls found
			int len = list.Count();
			usr_dlg.Prog_many("", "", "fetched pages: ~{0}", len);
			for (int i = 0; i < len; ++i) {
				Xodb_tbl_oimg_xfer_itm fsdb = (Xodb_tbl_oimg_xfer_itm)list.Get_at(i);
				Download_itm(fsdb);
				if (	exit_now
					||	exec_count	>= exec_count_max
					||	exec_fail	>= exec_fail_max
					||	page_id_val >= page_id_end
					) {
					this.Txn_sav();
					return;
				}
			}
		}
		exec_done = true;
	}
	private void Init_bldr_bmks() {
		if (!resume_enabled) // clear cfg entries if resume disabled; note that disabled by default; DATE:2014-10-24
			bldr_cfg_tbl.Delete_grp(Cfg_fsdb_make);
		Db_cfg_hash bmk_hash = bldr_cfg_tbl.Select_as_hash(Cfg_fsdb_make);
		String tier_id_str = bmk_hash.Get_by(Cfg_tier_id_bmk).To_str_or(null);
		if (tier_id_str == null) {	// bmks not found; new db;
			bldr_conn.Txn_bgn("bldr__fsdb_make__bldr_conn");
			bldr_cfg_tbl.Insert_int(Cfg_fsdb_make, Cfg_tier_id_bmk	, tier_id_bmk);
			bldr_cfg_tbl.Insert_int(Cfg_fsdb_make, Cfg_page_id_bmk	, page_id_bmk);
			bldr_cfg_tbl.Insert_int(Cfg_fsdb_make, Cfg_lnki_id_bmk	, lnki_id_bmk);
			bldr_conn.Txn_end();
			if (tier_id_bmk == -1) tier_id_bmk = 0;
			if (page_id_bmk == -1) page_id_bmk = 0;
			if (lnki_id_bmk == -1) lnki_id_bmk = 0;
		}
		else {
			if (tier_id_bmk == -1) {
				tier_id_bmk = Int_.parse(tier_id_str);
				usr_dlg.Note_many("", "", "restoring from bmk: tier_id=~{0}", tier_id_bmk);
			}
			if (page_id_bmk == -1) {
				page_id_bmk = bmk_hash.Get_by(Cfg_page_id_bmk).To_int();
				usr_dlg.Note_many("", "", "restoring from bmk: page_id=~{0}", page_id_bmk);
			}
			if (lnki_id_bmk == -1) {
				lnki_id_bmk = bmk_hash.Get_by(Cfg_lnki_id_bmk).To_int();
				usr_dlg.Note_many("", "", "restoring from bmk: lnki_id=~{0}", lnki_id_bmk);
			}
		}
		tier_id_val = tier_id_bmk;
		page_id_val = page_id_bmk;
		lnki_id_val = lnki_id_bmk;
	}
	private byte Select_fsdb_itms(List_adp list) {
		list.Clear();
		boolean pages_found = false, links_found = false;
		DataRdr rdr = Xob_xfer_regy_tbl.Select_by_tier_page(bldr_conn, tier_id_val, page_id_val, select_interval);
		try {
			while (rdr.MoveNextPeer()) {
				pages_found = true;								// at least one page found; set true
				Xodb_tbl_oimg_xfer_itm itm = Xodb_tbl_oimg_xfer_itm.new_rdr_( rdr);
				if (	itm.Lnki_page_id() == page_id_val		// same page_id 
					&&	itm.Lnki_id() <= lnki_id_val			// ... but lnki_id < last
					)
					continue;									// ... ignore; note that select is by page_id, not page_id + link_id; needed else restarts would not resume exactly at same point;
				links_found = true;
				list.Add(itm);
			}
		}	finally {rdr.Rls();}
		if		(pages_found && !links_found)	return Select_rv_next_page;		// pages found, but all links processed
		else if (!pages_found)					return Select_rv_stop;			// no more pages found
		else									return Select_rv_process;		// pages and links found
	}
	private void Download_itm(Xodb_tbl_oimg_xfer_itm fsdb) {
		try {
			tier_id_val = fsdb.Lnki_tier_id();
			page_id_val = fsdb.Lnki_page_id();
			lnki_id_val = fsdb.Lnki_id();
			fsdb.Orig_repo_name_(fsdb.Orig_repo_id() == Xof_repo_tid_.Tid__local ? wiki.Domain_bry() : Xow_domain_itm_.Bry__commons);
			Download_exec(fsdb);
			++exec_count;
			if (exec_count % progress_interval	== 0) Print_progress(fsdb);
			if (exec_count % poll_interval		== 0) poll_mgr.Poll();
			if (exec_count % commit_interval	== 0) Txn_sav();
			if (exec_count % delete_interval	== 0) Delete_files();
		}
		catch (Exception exc) {
			++exec_fail;
			usr_dlg.Warn_many("", "", "download error; ttl=~{0} w=~{1} err=~{2}", fsdb.Orig_ttl(), fsdb.Lnki_w(), Err_.Message_gplx_full(exc));
		}
	}
	private void Download_exec(Xodb_tbl_oimg_xfer_itm fsdb) {
		Io_stream_rdr src_rdr = src_bin_mgr.Find_as_rdr(Xof_exec_tid.Tid_wiki_page, fsdb);
		try {
			if (src_rdr == Io_stream_rdr_.Noop) {	// download failed
				++exec_fail;
				usr_dlg.Warn_many("", "", "failed: ttl=~{0}", String_.Format("[[File:{0}|{1}px]]", fsdb.Orig_ttl(), fsdb.Html_w()));
				Print_progress(fsdb);
			}
			else {									// download passed
				long src_rdr_len = src_rdr.Len();
				int lnki_tier_id = fsdb.Lnki_tier_id();
				if (	src_rdr_len > download_size_max
					&&	!Int_.In(lnki_tier_id, download_keep_tier_ids)) {
					usr_dlg.Warn_many("", "", "skipped; ttl=~{0} w=~{1} size=~{2} tier=~{3}", fsdb.Orig_ttl(), fsdb.Lnki_w(), src_rdr_len, lnki_tier_id);
					return;
				}
				if		(trg_bin_fil == null)									// no trg_bin_fil
					Make_trg_bin_file(Bool_.Y, fsdb, src_rdr_len);
				else if (trg_bin_fil.Bin_len() + src_rdr_len > trg_bin_db_max) 	// or trg_bin_fil is out of space
					Make_trg_bin_file(Bool_.N, fsdb, src_rdr_len);
				else if (prv_lnki_tier_id != lnki_tier_id) {					// or tier has changed
					if (	prv_lnki_tier_id != -1
						&&	!bin_db_mgr.Schema_is_1())		// do not increment dbs for v1
						Make_trg_bin_file(Bool_.Y, fsdb, src_rdr_len);
					prv_lnki_tier_id = lnki_tier_id;
				}
				trg_bin_updater.Save_bin(trg_mnt_itm, trg_atr_fil, trg_bin_fil, fsdb, src_rdr, src_rdr_len);
			}
		}
		finally {src_rdr.Rls();}
	}
	private void Make_trg_bin_file(boolean try_nth, Xodb_tbl_oimg_xfer_itm fsdb, long src_rdr_len) {
		boolean is_solo = trg_mnt_itm.Db_mgr().File__solo_file();
		boolean make = true, use_txn = !is_solo;	// solo file; should never open txn
		if (trg_bin_fil != null	&& use_txn)		// pre-existing bin_file; 
			trg_bin_fil.Conn().Txn_end();		// close txn before making new db
		int tier_id = fsdb.Lnki_tier_id();
		Xob_bin_db_itm nth_bin_db = bin_db_mgr.Get_nth_by_tier(tier_id);
		if (try_nth) {							// try_nth is true; occurs for new runs or changed tier
			if (	nth_bin_db.Id() != -1		// nth exists; 
				&&	nth_bin_db.Db_len() + src_rdr_len < trg_bin_db_max) { // if src_rdr_len exceeds
				make = false;					// do not make; use existing
			}				
		}
		if (make) {								// no nth; make it;
			int ns_id = bin_db_mgr.Get_ns_id(tier_id);
			int pt_id = bin_db_mgr.Increment_pt_id(nth_bin_db);
			String new_bin_db_name = bin_db_mgr.Gen_name(wiki.Domain_str(), ns_id, pt_id);
			this.trg_bin_fil = trg_mnt_itm.Bin_mgr().Dbs__make(new_bin_db_name);
			if (!trg_mnt_itm.Db_mgr().File__solo_file()) {
				Fsdb_db_file trg_bin_db = trg_mnt_itm.Db_mgr().File__bin_file__at(trg_mnt_itm.Id(), trg_bin_fil.Id(), new_bin_db_name);
				if (!bin_db_mgr.Schema_is_1())
					Fsdb_db_mgr__v2_bldr.Make_cfg_data(wiki, trg_atr_fil.Url_rel(), trg_bin_db, Xow_db_file_.Tid__file_data, trg_bin_fil.Id() + List_adp_.Base1);
			}
		}
		else {									// nth available; use it
			this.trg_bin_fil = trg_mnt_itm.Bin_mgr().Dbs__get_at(nth_bin_db.Id());
			trg_bin_fil.Bin_len_(nth_bin_db.Db_len());
		}
		if (use_txn)
			trg_bin_fil.Conn().Txn_bgn("bldr__fsdb_make__trg_bin_fil");
	}
	private void Txn_sav() {
		usr_dlg.Prog_many("", "", "committing data: count=~{0} failed=~{1}", exec_count, exec_fail);
		bldr_cfg_tbl.Update_int(Cfg_fsdb_make, Cfg_page_id_bmk, page_id_val);
		bldr_cfg_tbl.Update_int(Cfg_fsdb_make, Cfg_lnki_id_bmk, lnki_id_val);
		bldr_cfg_tbl.Conn().Txn_sav();
		trg_cfg_mgr.Next_id_commit();
		trg_atr_fil.Conn().Txn_sav();
		if (!trg_atr_fil.Conn().Eq(trg_cfg_mgr.Tbl().Conn()))	// need to create txn for v1
			trg_cfg_mgr.Tbl().Conn().Txn_sav();
		if (src_bin_mgr__fsdb_version != null && src_bin_mgr__fsdb_skip_wkrs != null) {
			src_fsdb_wkr.Skip_mgr().Skip_term(src_fsdb_wkr.Mnt_mgr().Mnts__get_main().Cfg_mgr());
		}
		if (!trg_mnt_itm.Db_mgr().File__solo_file())
			trg_bin_fil.Conn().Txn_sav();
		if (exit_after_commit) exit_now = true;
	}
	@Override public void Cmd_end() {
		usr_dlg.Note_many("", "", "fsdb_make.done: count=~{0} rate=~{1}", exec_count, Decimal_adp_.divide_safe_(exec_count, System_.Ticks__elapsed_in_sec(time_bgn)).To_str("#,###.000"));
		if (src_fsdb_wkr != null) {
			src_fsdb_wkr.Mnt_mgr().Mnts__get_main().Txn_end();	// NOTE: src_fsdb_wkr will be null if no src db defined
		}
		trg_atr_fil.Conn().Txn_end(); trg_atr_fil.Conn().Rls_conn();
		if (!trg_atr_fil.Conn().Eq(trg_cfg_mgr.Tbl().Conn()))	// need to create txn for v1
			trg_cfg_mgr.Tbl().Conn().Txn_end();
		trg_cfg_mgr.Tbl().Conn().Rls_conn();
		if (!trg_mnt_itm.Db_mgr().File__solo_file()) {
			if (trg_bin_fil != null) { // NOTE: trg_bin_fil is null when there are no images in the wiki; EX: bo.b; DATE:2017-03-19
				trg_bin_fil.Conn().Txn_end(); trg_bin_fil.Conn().Rls_conn();
			}
		}
		if (exec_done) {
			bldr_cfg_tbl.Delete_grp(Cfg_fsdb_make);	// delete bmks for future reruns; DATE:2014-08-20
			Io_mgr.Instance.DeleteFil_args(wiki.Fsys_mgr().Root_dir().GenSubFil("xowa.file.make.cfg.gfs")).MissingFails_off().Exec();
		}
		bldr_conn.Rls_conn();
	}
	private void Print_progress(Xodb_tbl_oimg_xfer_itm itm) {
		int time_elapsed = System_.Ticks__elapsed_in_sec(time_bgn);
		usr_dlg.Prog_many("", "", "prog: num=~{0} err=~{1} time=~{2} rate=~{3} page=~{4} lnki=~{5} ttl=~{6}", exec_count, exec_fail, time_elapsed, Math_.Div_safe_as_int(exec_count, time_elapsed), page_id_val, lnki_id_val, itm.Orig_ttl());
	}
	private void Delete_files() {}// TODO_OLD: purge /xowa/file/ dir to free up hard disk space
	@Override public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_tier_id_bmk_))					tier_id_bmk = m.ReadInt("v");
		else if	(ctx.Match(k, Invk_page_id_bmk_))					page_id_bmk = m.ReadInt("v");
		else if	(ctx.Match(k, Invk_lnki_id_bmk_))					lnki_id_bmk = m.ReadInt("v");
		else if	(ctx.Match(k, Invk_select_interval_))				select_interval = m.ReadInt("v");
		else if	(ctx.Match(k, Invk_commit_interval_))				commit_interval = m.ReadInt("v");
		else if	(ctx.Match(k, Invk_progress_interval_))				progress_interval = m.ReadInt("v");
		else if	(ctx.Match(k, Invk_delete_interval_))				delete_interval = m.ReadInt("v");
		else if	(ctx.Match(k, Invk_exec_count_max_))				exec_count_max = m.ReadInt("v");
		else if	(ctx.Match(k, Invk_exec_fail_max_))					exec_fail_max = m.ReadInt("v");
		else if	(ctx.Match(k, Invk_exit_after_commit_))				exit_after_commit = m.ReadYn("v");
		else if	(ctx.Match(k, Invk_exit_now_))						exit_now = m.ReadYn("v");
		else if	(ctx.Match(k, Invk_resume_enabled_))				resume_enabled = m.ReadYn("v");
		else if	(ctx.Match(k, Invk_ns_ids_))						ns_ids = Int_.Ary_parse(m.ReadStr("v"), "|");
		else if	(ctx.Match(k, Invk_src_bin_mgr__fsdb_version_))		src_bin_mgr__fsdb_version = m.ReadStr("v");
		else if	(ctx.Match(k, Invk_src_bin_mgr__fsdb_skip_wkrs_))	src_bin_mgr__fsdb_skip_wkrs = m.ReadStrAry("v", "|");
		else if	(ctx.Match(k, Invk_src_bin_mgr__wmf_enabled_))		src_bin_mgr__wmf_enabled = m.ReadYn("v");
		else if	(ctx.Match(k, Invk_src_bin_mgr__cache_enabled_))	src_bin_mgr__cache_enabled = m.ReadYn("v");
		else if	(ctx.Match(k, Invk_trg_bin_mgr__fsdb_version_))		trg_bin_mgr__fsdb_version = m.ReadStr("v");
		else if	(ctx.Match(k, Invk_poll_mgr))						return poll_mgr;
		else if	(ctx.Match(k, Invk_download_keep_tier_ids))			download_keep_tier_ids = Int_.Ary_parse(m.ReadStr("v"), "|");
		else if	(ctx.Match(k, Invk_download_size_max))				download_size_max = Io_size_.To_long_by_msg_mb(m, download_size_max);
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}
	private static final    String
	  Invk_tier_id_bmk_ = "tier_id_bmk_", Invk_page_id_bmk_ = "page_id_bmk_", Invk_lnki_id_bmk_ = "lnki_id_bmk_"
	, Invk_select_interval_ = "select_interval_", Invk_commit_interval_ = "commit_interval_", Invk_progress_interval_ = "progress_interval_", Invk_delete_interval_ = "delete_interval_"
	, Invk_exec_count_max_ = "exec_count_max_", Invk_exec_fail_max_ = "exec_fail_max_", Invk_exit_now_ = "exit_now_", Invk_exit_after_commit_ = "exit_after_commit_"
	, Invk_resume_enabled_ = "resume_enabled_", Invk_poll_mgr = "poll_mgr"
	, Invk_src_bin_mgr__fsdb_version_ = "src_bin_mgr__fsdb_version_", Invk_src_bin_mgr__fsdb_skip_wkrs_ = "src_bin_mgr__fsdb_skip_wkrs_"
	, Invk_src_bin_mgr__wmf_enabled_ = "src_bin_mgr__wmf_enabled_"
	, Invk_src_bin_mgr__cache_enabled_ = "src_bin_mgr__cache_enabled_", Invk_ns_ids_ = "ns_ids_"
	, Invk_trg_bin_mgr__fsdb_version_ = "trg_bin_mgr__fsdb_version_"
	, Invk_download_size_max = "download_size_max", Invk_download_keep_tier_ids = "download_keep_tier_ids"
	;

	public static final String BLDR_CMD_KEY = "file.fsdb_make";
	@Override public String Cmd_key() {return BLDR_CMD_KEY;} 
	public static final    Xob_cmd Prototype = new Xobldr__fsdb_db__create_data(null, null);
	@Override public Xob_cmd Cmd_clone(Xob_bldr bldr, Xowe_wiki wiki) {return new Xobldr__fsdb_db__create_data(bldr, wiki);}

	public static Fsdb_db_mgr new_src_bin_db_mgr(Xow_wiki wiki, String version) {
		String domain_str = wiki.Domain_str();
		Fsdb_db_mgr rv = null; Io_url url = null;
		if		(String_.Eq(version, "v1")) {
			url = wiki.Fsys_mgr().File_dir().OwnerDir().GenSubDir(domain_str + "-prv");	// v1: EX: /xowa/file/en.wikipedia.org-prv/
			rv = new Fsdb_db_mgr__v1(url);
		}
		else if (String_.Eq(version, "v2")) {
			url = wiki.Fsys_mgr().Root_dir().GenSubDir("prv");		// v2: EX: /xowa/wiki/en.wikipedia.org/prv/
			rv = Fsdb_db_mgr_.new_detect(wiki, url, url);			// note that v2 is prioritized over v1
		}
		else throw Err_.new_wo_type("fsdb.make:unknown fsdb_type", "version", version);
		if (rv == null) throw Err_.new_wo_type("fsdb.make:source fsdb not found", "version", version, "url", url.Raw());
		return rv;
	}
	private static final byte Select_rv_stop = 0, Select_rv_process = 1, Select_rv_next_page = 2;
	private static final String Cfg_fsdb_make = "bldr.fsdb_make", Cfg_tier_id_bmk = "tier_id_bmk", Cfg_page_id_bmk = "page_id_bmk", Cfg_lnki_id_bmk = "lnki_id_bmk";
	public static byte Status_null = 0, Status_pass = 1, Status_fail = 2;
}
class Xodb_tbl_oimg_xfer_itm extends Xof_fsdb_itm {	public int 			Lnki_id()		{return lnki_id;} private int lnki_id;
	public int			Lnki_tier_id()	{return lnki_tier_id;} private int lnki_tier_id;
	public int 			Lnki_page_id()	{return lnki_page_id;} private int lnki_page_id;
	public static Xodb_tbl_oimg_xfer_itm new_rdr_(DataRdr rdr) {
		Xodb_tbl_oimg_xfer_itm rv = new Xodb_tbl_oimg_xfer_itm();
		rv.lnki_id			= rdr.ReadInt(Xob_xfer_regy_tbl.Fld_lnki_id);
		rv.lnki_page_id		= rdr.ReadInt(Xob_xfer_regy_tbl.Fld_lnki_page_id);
		rv.lnki_tier_id		= rdr.ReadInt(Xob_xfer_regy_tbl.Fld_lnki_tier_id);
		rv.Init_at_fsdb_make
		( rdr.ReadBryByStr(Xob_xfer_regy_tbl.Fld_lnki_ttl)
		, rdr.ReadInt(Xob_xfer_regy_tbl.Fld_lnki_ext)
		, rdr.ReadInt(Xob_xfer_regy_tbl.Fld_file_w), rdr.ReadInt(Xob_xfer_regy_tbl.Fld_file_h)	// set lnki_size; Xof_bin_mgr uses lnki_size
		, Xof_lnki_time.Db_load_double(rdr, Xob_xfer_regy_tbl.Fld_lnki_time)
		, Xof_lnki_page.Db_load_int(rdr, Xob_xfer_regy_tbl.Fld_lnki_page)
		, rdr.ReadByte(Xob_xfer_regy_tbl.Fld_orig_repo)
		, rdr.ReadInt(Xob_xfer_regy_tbl.Fld_orig_w)
		, rdr.ReadInt(Xob_xfer_regy_tbl.Fld_orig_h)
		, Bry_.Empty
		, rdr.ReadByte(Xob_xfer_regy_tbl.Fld_file_is_orig) == Bool_.Y_byte
		);
		return rv;
	}
}
