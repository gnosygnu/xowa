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
package gplx.fsdb.data; import gplx.*; import gplx.fsdb.*;
import gplx.dbs.*; import gplx.fsdb.meta.*; import gplx.xowa.files.*;
public class Fsd_thm_tbl implements Db_tbl {
	public final    Dbmeta_fld_list flds = new Dbmeta_fld_list();
	public final    String fld_id, fld_owner_id, fld_w, fld_h, fld_time, fld_page, fld_bin_db_id, fld_size, fld_modified, fld_hash;
	public final    Db_conn conn; private Db_stmt stmt_insert, stmt_select_by_fil_exact, stmt_select_by_fil_near; private int mnt_id; private boolean schema_thm_page;
	public Fsd_thm_tbl(Db_conn conn, boolean schema_is_1, int mnt_id, boolean schema_thm_page) {
		this.conn = conn; this.mnt_id = mnt_id; this.schema_thm_page = schema_thm_page;
		this.tbl_name = schema_is_1 ? "fsdb_xtn_thm" : "fsdb_thm";
		this.fld_id				= flds.Add_int_pkey	("thm_id");
		this.fld_owner_id		= flds.Add_int		("thm_owner_id");
		this.fld_w				= flds.Add_int		("thm_w");
		this.fld_h				= flds.Add_int		("thm_h");
		if (schema_thm_page) {
			this.fld_time		= flds.Add_double	("thm_time");
			this.fld_page		= flds.Add_int		("thm_page");
		}
		else {
			this.fld_time		= flds.Add_int		("thm_thumbtime");
			this.fld_page		= Dbmeta_fld_itm.Key_null;
		}
		this.fld_bin_db_id		= flds.Add_int		("thm_bin_db_id");
		this.fld_size			= flds.Add_long		("thm_size");
		this.fld_modified		= flds.Add_str		("thm_modified", 14);		// stored as yyyyMMddHHmmss
		this.fld_hash			= flds.Add_str		("thm_hash", 40);
		conn.Rls_reg(this);
	}
	public String Tbl_name() {return tbl_name;} private final    String tbl_name;
	public void Create_tbl() {
		conn.Meta_tbl_create(Dbmeta_tbl_itm.New(tbl_name, flds
		, Dbmeta_idx_itm.new_unique_by_tbl(tbl_name, "owner", fld_owner_id, fld_id, fld_w, fld_time, fld_page)
		));
	}
	public void Insert(int id, int thm_owner_id, int width, int height, double thumbtime, int page, int bin_db_id, long size) {
		if (stmt_insert == null) stmt_insert = conn.Stmt_insert(tbl_name, flds);
		this.Insert(stmt_insert, id, thm_owner_id, width, height, thumbtime, page, bin_db_id, size, Modified_null_str, Hash_null);
	}
	public void Insert(Db_stmt stmt, int id, int thm_owner_id, int width, int height, double thumbtime, int page, int bin_db_id, long size, String modified, String hash_md5) {
		stmt.Clear()
		.Val_int(fld_id, id)
		.Val_int(fld_owner_id, thm_owner_id)
		.Val_int(fld_w, width)
		.Val_int(fld_h, height);
		if (schema_thm_page) {
			stmt.Val_double		(fld_time, Xof_lnki_time.Db_save_double(thumbtime));
			stmt.Val_int		(fld_page, Xof_lnki_page.Db_save_int(page));
		}
		else
			stmt.Val_int		(fld_time, Xof_lnki_time.Db_save_int(thumbtime));
		stmt
		.Val_int(fld_bin_db_id, bin_db_id)
		.Val_long(fld_size, size)
		.Val_str(fld_modified, modified)
		.Val_str(fld_hash, hash_md5)
		.Exec_insert();
	}
	public boolean Select_itm_by_w_exact(int dir_id, int fil_id, Fsd_thm_itm thm) {
		if (stmt_select_by_fil_exact == null) stmt_select_by_fil_exact = conn.Stmt_select(tbl_name, flds, String_.Ary_wo_null(fld_owner_id, fld_w, fld_time, fld_page));
		stmt_select_by_fil_exact.Clear().Crt_int(fld_owner_id, fil_id).Crt_int(fld_w, thm.W());
		if (schema_thm_page) {
			stmt_select_by_fil_exact.Crt_double	(fld_time, Xof_lnki_time.Db_save_double(thm.Time()));
			stmt_select_by_fil_exact.Crt_int	(fld_page, Xof_lnki_page.Db_save_int(thm.Page()));
		}
		else {
			stmt_select_by_fil_exact.Crt_int	(fld_time, Xof_lnki_time.Db_save_int(thm.Time()));
		}
		Db_rdr rdr = stmt_select_by_fil_exact.Exec_select__rls_manual();
		try {
			return rdr.Move_next()
				? Ctor_by_load(thm, rdr, dir_id)
				: false;
		}
		finally {rdr.Rls();}
	}
	public boolean Select_itm_by_w_near(int dir_id, int fil_id, Fsd_thm_itm thm) {
		if (stmt_select_by_fil_near == null) stmt_select_by_fil_near = conn.Stmt_select(tbl_name, flds, fld_owner_id);
		List_adp list = List_adp_.New();
		Db_rdr rdr = stmt_select_by_fil_near.Clear().Crt_int(fld_owner_id, fil_id).Exec_select__rls_manual();
		try {
			while (rdr.Move_next()) {
				Fsd_thm_itm itm = Fsd_thm_itm.new_();
				Ctor_by_load(itm, rdr, dir_id);
				list.Add(itm);
			}
			return Match_nearest(list, thm, schema_thm_page);
		}
		finally {rdr.Rls();}
	}
	public boolean Ctor_by_load(Fsd_thm_itm itm, Db_rdr rdr, int dir_id) {
		int thm_id = rdr.Read_int(fld_id);
		int fil_id = rdr.Read_int(fld_owner_id);
		int w = rdr.Read_int(fld_w);
		int h = rdr.Read_int(fld_h);
		long size = rdr.Read_long(fld_size);
		String modified = rdr.Read_str(fld_modified);
		String hash = rdr.Read_str(fld_hash);
		int bin_db_id = rdr.Read_int(fld_bin_db_id);
		double time = 0;
		int page = 0;
		if (schema_thm_page) {
			time = Xof_lnki_time.Db_load_double(rdr, fld_time);
			page = Xof_lnki_page.Db_load_int(rdr, fld_page);
		}
		else {
			time = Xof_lnki_time.Db_load_int(rdr, fld_time);
			page = Xof_lnki_page.Null;
		}
		itm.Ctor(mnt_id, dir_id, fil_id, thm_id, bin_db_id, w, h, time, page, size, modified, hash);
		return true;
	}
	public static final    DateAdp Modified_null = null;
	public static final String Hash_null = "", Modified_null_str = "";
	public static boolean Match_nearest(List_adp list, Fsd_thm_itm thm, boolean schema_thm_page) {
		int len = list.Count(); if (len == 0) return Bool_.N;
		list.Sort_by(Fsdb_thm_itm_sorter.Instance);
		int thm_w = thm.W(), thm_page = thm.Page(); double thm_time = thm.Time();
		Fsd_thm_itm max = null;
		for (int i = 0; i < len; ++i) {
			Fsd_thm_itm comp = (Fsd_thm_itm)list.Get_at(i);
			int comp_w = comp.W();
			int comp_page = schema_thm_page ? comp.Page() : thm_page;
			if (	thm_w			== comp_w
				&&	thm_time		== comp.Time()
				&&	thm_page		== comp_page
				) {	// exact match
				thm.Init_by_match(comp);
				return Bool_.Y;
			}
			if		(comp_w > thm_w)	max = comp;
			else if (max == null)		max = comp;
			else break;
		}
		if (max == null) return Bool_.N;
		thm.Init_by_match(max);
		return Bool_.Y;
	}
	public void Rls() {
		stmt_insert = Db_stmt_.Rls(stmt_insert);
		stmt_select_by_fil_exact = Db_stmt_.Rls(stmt_select_by_fil_exact);
		stmt_select_by_fil_near = Db_stmt_.Rls(stmt_select_by_fil_near);
	}
}
