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
import gplx.fsdb.meta.*; import gplx.fsdb.data.*;
import gplx.xowa.addons.bldrs.exports.splits.metas.*; import gplx.xowa.addons.bldrs.exports.splits.rslts.*;
class Split_meta_wkr__bin extends Split_meta_wkr_base {
	private final    Fsm_bin_mgr bin_mgr;
	private final    Split_rslt_wkr__bin rslt_wkr = new Split_rslt_wkr__bin();
	private Fsd_bin_tbl tbl; private Db_stmt stmt;
	public Split_meta_wkr__bin(Split_ctx ctx, Fsm_bin_mgr bin_mgr) {
		this.bin_mgr = bin_mgr;
		ctx.Rslt_mgr().Reg_wkr(rslt_wkr);
	}
	@Override public byte Tid() {return Split_page_list_type_.Tid__fsdb_bin;}
	@Override public void On_nth_new(Split_ctx ctx, Db_conn trg_conn) {
		this.tbl = new Fsd_bin_tbl(trg_conn, Bool_.N);
		Dbmeta_fld_list trg_flds = Make_flds_for_split(tbl.Flds());
		trg_conn.Meta_tbl_create(Dbmeta_tbl_itm.New(tbl.Tbl_name(), trg_flds));
		this.stmt = trg_conn.Stmt_insert(tbl.Tbl_name(), trg_flds);
	}
	@Override public void On_nth_rls(Split_ctx ctx, Db_conn trg_conn) {
		this.stmt = Db_stmt_.Rls(stmt);
		trg_conn.Meta_idx_create(tbl.Tbl_name(), "merge", "trg_db_id", "bin_owner_id");
		trg_conn.Meta_idx_create(tbl.Tbl_name(), "blob" , "trg_db_id", "blob_len", "bin_owner_id");
	}
	@Override protected String Load_sql(Db_attach_mgr attach_mgr, int ns_id, int score_bgn, int score_end) {
		return String_.Concat_lines_nl
		( "SELECT  img_type"
		, ",       -1 AS owner_id"
		, ",       fil_id AS bin_id"
		, ",       bin_db_id"
		, ",       page_id"
		, "FROM    fsdb_img_regy"
		, "WHERE   page_score >= {0}"
		, "AND     page_score <  {1}"
		, "AND     page_ns = {2}"
		, "AND     img_type = 0"
		, "UNION"
		, "SELECT  img_type"
		, ",       fil_id AS owner_id"
		, ",       thm_id AS bin_id"
		, ",       bin_db_id"
		, ",       page_id"
		, "FROM    fsdb_img_regy"
		, "WHERE   page_score >= {0}"
		, "AND     page_score <  {1}"
		, "AND     page_ns = {2}"
		, "AND     img_type = 1"
		, "ORDER BY page_id"
		);
	}
	@Override protected Object Load_itm(Db_rdr rdr) {
		return new Bin_meta_itm(rdr.Read_byte("img_type"), rdr.Read_int("owner_id"), rdr.Read_int("bin_id"), rdr.Read_int("bin_db_id"));
	}
	@Override protected void Save_itm(Split_ctx ctx, Split_rslt_mgr rslt_mgr, Object itm_obj) {
		// load data
		Bin_meta_itm itm = (Bin_meta_itm)itm_obj;
		Fsm_bin_fil src_db	= bin_mgr.Dbs__get_at(itm.Bin_db_id());
		int bin_id = itm.Bin_id();
		Fsd_bin_itm src_itm	= src_db.Select_as_itm(bin_id);
		byte[] bin_data = src_itm.Bin_data(); if (bin_data == null) bin_data = Bry_.Empty; // NOTE: bin_data can be NULL
		int blob_len = bin_data.length;	
		String bin_data_url = src_itm.Bin_data_url();

		// calc db_idx based on db_size
		int db_row_size = Fsd_bin_itm.Db_row_size_fixed + blob_len + String_.Len(bin_data_url);
		int trg_db_id = ctx.File_size_calc().Size_cur_add_(db_row_size);

		// do insert
		stmt.Clear()
			.Val_int	("bin_owner_id"	, bin_id)
			.Val_int	("trg_db_id"	, trg_db_id)
			.Val_int	("blob_len"		, blob_len)
			.Val_byte	("bin_owner_tid", src_itm.Bin_owner_tid())
			.Val_int	("bin_part_id"	, src_itm.Bin_part_id())
			.Val_str	("bin_data_url"	, bin_data_url)
			.Val_bry	("bin_data"		, bin_data)
			.Exec_insert();
		rslt_wkr.On__nth__itm(db_row_size, bin_id);
	}
	private static Dbmeta_fld_list Make_flds_for_split(Dbmeta_fld_list flds) {
		Dbmeta_fld_list rv = new Dbmeta_fld_list();
		rv.Add(flds.Get_by("bin_owner_id"));
		rv.Add_int("trg_db_id");
		rv.Add_int("blob_len");
		rv.Add(flds.Get_by("bin_owner_tid"));
		rv.Add(flds.Get_by("bin_part_id"));
		rv.Add(flds.Get_by("bin_data_url"));
		rv.Add(flds.Get_by("bin_data"));
		return rv;
	}
}
