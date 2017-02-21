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
package gplx.xowa.addons.bldrs.exports.splits.files; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.exports.*; import gplx.xowa.addons.bldrs.exports.splits.*;
import gplx.dbs.*;
import gplx.xowa.addons.bldrs.exports.splits.mgrs.*;
class Split_init__file {
	public void Exec(Split_ctx ctx, Xow_wiki wiki, Db_conn wkr_conn, Db_conn atr_conn) {
		if (!(ctx.Cfg().Force_rebuild() || !wkr_conn.Meta_tbl_exists("fsdb_img_regy"))) return;
		// get min page for each score
		Gfo_log_.Instance.Prog("creating fsdb_img_regy");
		wkr_conn.Meta_tbl_remake(Dbmeta_tbl_itm.New("fsdb_img_regy"
		, Dbmeta_fld_itm.new_int("img_uid").Primary_y_().Autonum_y_()
		, Dbmeta_fld_itm.new_byte("img_type")
		, Dbmeta_fld_itm.new_int("fil_id")
		, Dbmeta_fld_itm.new_int("thm_id")
		, Dbmeta_fld_itm.new_int("bin_db_id")
		, Dbmeta_fld_itm.new_int("page_uid")
		, Dbmeta_fld_itm.new_int("page_ns")
		, Dbmeta_fld_itm.new_int("page_score")
		, Dbmeta_fld_itm.new_int("page_id")
		));
		Db_attach_mgr attach_mgr = new Db_attach_mgr(wkr_conn
			, new Db_attach_itm("page_db", wiki.Data__core_mgr().Tbl__page().Conn())
			, new Db_attach_itm("pfm_db", gplx.xowa.bldrs.Xob_db_file.New__page_file_map(wiki).Conn())
			);
		attach_mgr.Exec_sql(String_.Concat_lines_nl	// ANSI.Y
		( "INSERT INTO fsdb_img_regy (img_type, fil_id, thm_id, bin_db_id, page_uid, page_ns, page_score, page_id)"
		, "SELECT  CASE WHEN pfm.thm_id = -1 THEN 0 ELSE 1 END, pfm.fil_id, pfm.thm_id, -1, Min(pr.page_uid), -1, -1, -1"
		, "FROM    <pfm_db>page_file_map pfm"
		, "        JOIN page_regy pr ON pfm.page_id = pr.page_id"
		, "GROUP BY CASE WHEN pfm.thm_id = -1 THEN 0 ELSE 1 END, pfm.fil_id, pfm.thm_id"
		, "ORDER BY Min(pr.page_uid)"
		));

		// update page attributes; create idx 
		Split_mgr_init.Update_page_cols(wkr_conn, "fsdb_img_regy");
		wkr_conn.Meta_idx_create("fsdb_img_regy", "fil_id", "fil_id");
		wkr_conn.Meta_idx_create("fsdb_img_regy", "page", "page_score", "page_ns", "img_type");

		// update bin_db_id
		wkr_conn.Meta_idx_create("fsdb_img_regy", "img", "img_type", "fil_id", "thm_id");
		Gfo_log_.Instance.Prog("updating bin_db_id");
		attach_mgr.Conn_links_(new Db_attach_itm("fsdb_db", atr_conn));

		// update bin_db_id.fil
		attach_mgr.Exec_sql(String_.Concat_lines_nl	// ANSI.Y
		( "UPDATE  fsdb_img_regy"
		, "SET     bin_db_id = Coalesce((SELECT f.fil_bin_db_id FROM <fsdb_db>fsdb_fil f WHERE fsdb_img_regy.fil_id = f.fil_id AND fsdb_img_regy.thm_id = -1), bin_db_id)"
		, "WHERE   img_type = 0"
		));

		// update bin_db_id.thm
		attach_mgr.Exec_sql(String_.Concat_lines_nl	// ANSI.Y
		( "UPDATE  fsdb_img_regy"
		, "SET     bin_db_id = Coalesce((SELECT t.thm_bin_db_id FROM <fsdb_db>fsdb_thm t WHERE fsdb_img_regy.fil_id = t.thm_owner_id AND fsdb_img_regy.thm_id = t.thm_id), bin_db_id)"
		, "WHERE   img_type = 1"
		));

		// promote fil if thm shows up earlier; i.e.: if thm_id=11 is score=99 and fil_id=10 is score=80, move fil_id to 99
		Gfo_log_.Instance.Prog("creating fsdb_fil_min");
		wkr_conn.Meta_tbl_remake(Dbmeta_tbl_itm.New("fsdb_fil_min"
		, Dbmeta_fld_itm.new_byte("img_uid")
		, Dbmeta_fld_itm.new_int("fil_id")
		, Dbmeta_fld_itm.new_int("thm_id")
		));

		// add fils via thms; for thms with multiple fils, use Min(img_uid)
		wkr_conn.Exec_sql(String_.Concat_lines_nl	// ANSI.Y
		( "INSERT INTO fsdb_fil_min (img_uid, fil_id, thm_id)"
		// get fils directly
		, "SELECT  f.img_uid, f.fil_id, f.thm_id"
		, "FROM    fsdb_img_regy f"
		, "WHERE   f.img_type = 0"
		, "UNION"
		// get fils from thms
		, "SELECT  Min(f.img_uid), f.fil_id, f.thm_id"
		, "FROM    fsdb_img_regy t"
		, "        JOIN fsdb_img_regy f ON t.fil_id = f.fil_id"
		, "WHERE   t.img_type = 1"
		, "GROUP BY f.fil_id, f.thm_id"
		));

		// dupes may still exist, so do one more group by
		Gfo_log_.Instance.Prog("creating fsdb_fil_min_unique");
		wkr_conn.Meta_tbl_remake(Dbmeta_tbl_itm.New("fsdb_fil_min_unique"
		, Dbmeta_fld_itm.new_byte("img_uid")
		, Dbmeta_fld_itm.new_int("fil_id")
		));
		wkr_conn.Exec_sql(String_.Concat_lines_nl	// ANSI.Y
		( "INSERT INTO fsdb_fil_min_unique (img_uid, fil_id)"
		, "SELECT  Min(f.img_uid), f.fil_id"
		, "FROM    fsdb_fil_min f"
		, "GROUP BY f.fil_id"
		));

		// create fsdb_fil_regy
		Gfo_log_.Instance.Prog("creating fsdb_fil_regy");
		wkr_conn.Meta_tbl_remake(Dbmeta_tbl_itm.New("fsdb_fil_regy"
		, Dbmeta_fld_itm.new_int("img_uid").Primary_y_()
		, Dbmeta_fld_itm.new_int("fil_id")
		, Dbmeta_fld_itm.new_int("page_ns")
		, Dbmeta_fld_itm.new_int("page_score")
		, Dbmeta_fld_itm.new_int("page_id")
		));
		wkr_conn.Exec_sql(String_.Concat_lines_nl	// ANSI.Y
		( "INSERT INTO fsdb_fil_regy (img_uid, fil_id, page_ns, page_score, page_id)"
		, "SELECT  fmu.img_uid, fmu.fil_id, fir.page_ns, fir.page_score, fir.page_id"
		, "FROM    fsdb_fil_min_unique fmu"
		, "        JOIN fsdb_img_regy fir ON fmu.img_uid = fir.img_uid"
		));

	}
}
