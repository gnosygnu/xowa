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
package gplx.xowa.addons.bldrs.exports.splits.archives; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.exports.*; import gplx.xowa.addons.bldrs.exports.splits.*;
import gplx.dbs.*; import gplx.dbs.qrys.*; import gplx.dbs.metas.*;
import gplx.xowa.wikis.data.*; import gplx.xowa.wikis.data.tbls.*; import gplx.xowa.htmls.core.dbs.*;
import gplx.xowa.addons.bldrs.exports.splits.htmls.*;
class Reindex_html_dbs_cmd {
	private Db_conn core_conn;
	private Xowd_page_tbl page_tbl;
	private Xoh_src_tbl_mgr src_tbl_mgr;
	private Xoh_trg_tbl_mgr trg_tbl_mgr;
	private String tbl_page, fld_page_id, fld_page_ns, fld_page_len, fld_page_score, fld_page_html_db_id;
	private final    String Idx_name = "page__repack";
	public void Exec(Xowe_wiki wiki, long trg_db_size_max) {
		// init
		wiki.Init_assert();
		Xow_db_mgr db_mgr = wiki.Data__core_mgr();
		this.page_tbl = db_mgr.Tbl__page();
		this.core_conn = page_tbl.Conn();
		this.src_tbl_mgr = new Xoh_src_tbl_mgr(wiki);
		this.trg_tbl_mgr = new Xoh_trg_tbl_mgr(wiki);
		this.tbl_page = page_tbl.Tbl_name();
		this.fld_page_id = page_tbl.Fld_page_id();
		this.fld_page_ns = page_tbl.Fld_page_ns();
		this.fld_page_len = page_tbl.Fld_page_len();
		this.fld_page_score = page_tbl.Fld_page_score();
		this.fld_page_html_db_id = page_tbl.Fld_html_db_id();

		Create_repack_idx_on_page();
		Create_repack_tbl();
		Insert_repack_rows();
		Move_html_data(trg_db_size_max);
		Cleanup_dbs(wiki);
		core_conn.Meta_idx_delete(Idx_name);
		core_conn.Meta_tbl_delete("repack");
		core_conn.Env_vacuum();
	}
	private void Create_repack_idx_on_page() {
		// add idx: page (page_ns DESC, page_score DESC, page_len DESC)
		if (!core_conn.Meta_idx_exists(Idx_name)) {
			core_conn.Meta_idx_create(Dbmeta_idx_itm.new_normal_by_name(tbl_page, Idx_name
				, Dbmeta_idx_fld.Dsc(fld_page_ns)
				, Dbmeta_idx_fld.Dsc(fld_page_score)
				, Dbmeta_idx_fld.Dsc(fld_page_len)));
		}
	}
	private void Create_repack_tbl() {
		core_conn.Meta_tbl_remake(Dbmeta_tbl_itm.New("repack"
			, Dbmeta_fld_itm.new_int("sort_idx").Autonum_y_().Primary_y_()
			, Dbmeta_fld_itm.new_int("page_id")
			, Dbmeta_fld_itm.new_int("page_ns")
			, Dbmeta_fld_itm.new_int("page_score")
			, Dbmeta_fld_itm.new_int("page_len")
			, Dbmeta_fld_itm.new_int("src_db")
			));
	}
	private void Insert_repack_rows() {
		Gfo_usr_dlg_.Instance.Prog_many("", "", "inserting rows into repack table");
		Db_qry__select_cmd select_qry = (Db_qry__select_cmd)Db_qry_.select_
			( tbl_page, fld_page_id, fld_page_ns, fld_page_len, fld_page_score, fld_page_html_db_id)
			.Order_(fld_page_ns, Bool_.Y)
			.Order_(fld_page_score, Bool_.N)
			.Order_(fld_page_len, Bool_.N)
			.Where_(Db_crt_.New_eq_not(fld_page_html_db_id, -1));
		Db_qry_.insert_("repack").Cols_("page_id", "page_ns", "page_len", "page_score", "src_db")
			.Select_(select_qry)
			.Exec_qry(core_conn);
	}
	private void Move_html_data(long trg_db_size_max) {
		// read rows and move
		Db_rdr rdr = core_conn.Stmt_select_order("repack", String_.Ary("sort_idx", "page_id", "page_ns", "page_len", "src_db"), String_.Ary_empty, "sort_idx").Exec_select__rls_auto();
		Db_stmt stmt_update = core_conn.Stmt_update(tbl_page, String_.Ary(fld_page_id), fld_page_html_db_id);
		Xoh_page_tbl_itm trg_html_tbl = null; 
		try {
			Xowd_html_row src_html_row = new Xowd_html_row();
			long trg_db_size = 0;
			int ns_cur = -1, part_id = 0;
			int trg_db = -1;
			core_conn.Txn_bgn("update page");
			while (rdr.Move_next()) {
				// check if ns changed
				int page_ns = rdr.Read_int("page_ns");
				boolean ns_changed = false;
				if (ns_cur != page_ns ) {	// ns changed
					ns_cur = page_ns;
					part_id = 0;			// reset part_id; note that 1st part will be base_1
					ns_changed = true;
				}

				Xoh_page_tbl_itm src_html_tbl = src_tbl_mgr.Get_or_load(rdr.Read_int("src_db"));
				int page_id = rdr.Read_int("page_id");
				if (!src_html_tbl.Html_tbl().Select_as_row(src_html_row, page_id)) throw Err_.new_wo_type("could not find html", "page_id", page_id);				

				// check if new file needed
				int page_size = src_html_row.Body().length;
				trg_db_size += page_size;
				if (	trg_html_tbl == null				// will be null for 1st pass
					||	ns_changed							// ns_changed
					||	trg_db_size > trg_db_size_max		// file filled
					) {
					if (trg_html_tbl != null) {				// close trg_db if open
						trg_html_tbl.Html_tbl().Insert_end();
						trg_html_tbl.Rls();
					}
					trg_html_tbl = trg_tbl_mgr.Make_new(ns_cur, ++part_id);
					trg_html_tbl.Html_tbl().Insert_bgn();
					trg_db = trg_html_tbl.Db_id();
					trg_db_size = page_size;
				}

				// move row
				trg_html_tbl.Html_tbl().Insert(src_html_row.Page_id(), src_html_row.Head_flag(), src_html_row.Body_flag()
					, src_html_row.Display_ttl(), src_html_row.Content_sub(), src_html_row.Sidebar_div()
					, src_html_row.Body());

				// update page_html_db_id
				stmt_update.Clear().Val_int(fld_page_html_db_id, trg_db).Crt_int(fld_page_id, page_id).Exec_update();
			}
		} finally {
			rdr.Rls();
			if (trg_html_tbl != null) {
				trg_html_tbl.Html_tbl().Insert_end();
				trg_html_tbl.Rls();
			}
			core_conn.Txn_end();
			stmt_update.Rls();
			src_tbl_mgr.Cleanup();
		}
	}
	private void Cleanup_dbs(Xowe_wiki wiki) {
		// delete old dbs
		wiki.Data__core_mgr().Rls();
		String repack_suffix = Xoh_trg_tbl_mgr.Repack_suffix;
		Db_stmt delete_stmt = core_conn.Stmt_delete(Xowd_xowa_db_tbl.TBL_NAME, Xowd_xowa_db_tbl.Fld_id);
		Db_rdr rdr = core_conn.Stmt_select(Xowd_xowa_db_tbl.TBL_NAME, String_.Ary(Xowd_xowa_db_tbl.Fld_id, Xowd_xowa_db_tbl.Fld_type, Xowd_xowa_db_tbl.Fld_url)).Exec_select__rls_auto();
		while (rdr.Move_next()) {
			byte file_tid = rdr.Read_byte(Xowd_xowa_db_tbl.Fld_type);
			if (file_tid != Xow_db_file_.Tid__html_data) continue;
			String file_url = rdr.Read_str(Xowd_xowa_db_tbl.Fld_url);
			if (String_.Has(file_url, repack_suffix)) continue;				
			delete_stmt.Clear().Crt_int(Xowd_xowa_db_tbl.Fld_id, rdr.Read_int(Xowd_xowa_db_tbl.Fld_id)).Exec_delete();
			Io_mgr.Instance.DeleteFil(wiki.Fsys_mgr().Root_dir().GenSubFil(file_url));
		}
		rdr.Rls();
		delete_stmt.Rls();

		// update new dbs
		Db_stmt update_stmt = core_conn.Stmt_update(Xowd_xowa_db_tbl.TBL_NAME, String_.Ary(Xowd_xowa_db_tbl.Fld_id), Xowd_xowa_db_tbl.Fld_url);
		rdr = core_conn.Stmt_select(Xowd_xowa_db_tbl.TBL_NAME, String_.Ary(Xowd_xowa_db_tbl.Fld_id, Xowd_xowa_db_tbl.Fld_type, Xowd_xowa_db_tbl.Fld_url)).Exec_select__rls_auto();
		while (rdr.Move_next()) {
			byte file_tid = rdr.Read_byte(Xowd_xowa_db_tbl.Fld_type);
			if (file_tid != Xow_db_file_.Tid__html_data) continue;
			Io_url old_url = wiki.Fsys_mgr().Root_dir().GenSubFil(rdr.Read_str(Xowd_xowa_db_tbl.Fld_url));
			String old_raw = old_url.Raw();
			Io_url new_url = Io_url_.new_fil_(String_.Replace(old_raw, repack_suffix, ""));
			if (!String_.Has(old_raw, repack_suffix)) throw Err_.new_wo_type("html db should be repack", "db_name", old_raw);
			update_stmt.Clear().Val_str(Xowd_xowa_db_tbl.Fld_url, new_url.NameAndExt()).Crt_int(Xowd_xowa_db_tbl.Fld_id, rdr.Read_int(Xowd_xowa_db_tbl.Fld_id)).Exec_update();
			Io_mgr.Instance.MoveFil(old_url, new_url);
		}
		rdr.Rls();
		update_stmt.Rls();
	}
}
