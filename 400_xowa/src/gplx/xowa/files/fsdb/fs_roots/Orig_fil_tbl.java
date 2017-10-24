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
package gplx.xowa.files.fsdb.fs_roots; import gplx.*; import gplx.xowa.*; import gplx.xowa.files.*; import gplx.xowa.files.fsdb.*;
import gplx.dbs.*;
class Orig_fil_tbl implements Rls_able {
	private final    Db_conn conn;
	private final    Dbmeta_fld_list flds = new Dbmeta_fld_list();
	private String tbl_name = "orig_fil";
	private String fld_uid, fld_name, fld_ext_id, fld_w, fld_h, fld_dir_url;		
	private Db_stmt stmt_insert, stmt_select;
	public Orig_fil_tbl(Db_conn conn, boolean schema_is_1) {
		this.conn = conn; conn.Rls_reg(this);
		String fld_prefix = schema_is_1 ? "fil_" : "";
		fld_uid				= flds.Add_int(fld_prefix + "uid");
		fld_name			= flds.Add_str(fld_prefix + "name", 1024);
		fld_ext_id			= flds.Add_int(fld_prefix + "ext_id");
		fld_w				= flds.Add_int(fld_prefix + "w");
		fld_h				= flds.Add_int(fld_prefix + "h");
		fld_dir_url			= flds.Add_str(fld_prefix + "dir_url", 1024);	// NOTE: don't put dir in separate table; note that entire root_dir_wkr is not built to scale due to need for recursively loading all files
	}
	public void Create_tbl() {
		conn.Meta_tbl_create(Dbmeta_tbl_itm.New(tbl_name, flds
		, Dbmeta_idx_itm.new_unique_by_tbl(tbl_name, "main", fld_name)
		));
	}
	public void Rls() {
		stmt_insert = Db_stmt_.Rls(stmt_insert);
		stmt_select = Db_stmt_.Rls(stmt_select);
	}
	public Orig_fil_row Select_itm_or_null(Io_url dir, byte[] ttl) {
		if (stmt_select == null) stmt_select = conn.Stmt_select(tbl_name, flds, fld_name);
		Db_rdr rdr = stmt_select.Clear().Crt_bry_as_str(fld_name, ttl).Exec_select__rls_manual();
		try {return rdr.Move_next() ? Load_itm(rdr, dir) : Orig_fil_row.Null;}
		finally {rdr.Rls();}
	}
	private Orig_fil_row Load_itm(Db_rdr rdr, Io_url orig_root) {
		String name = rdr.Read_str(fld_name);
		String fil_orig_dir = rdr.Read_str(fld_dir_url);
		Io_url dir = String_.Has_at_bgn(fil_orig_dir, Fs_root_wkr.Url_orig_dir)
			// swap out orig_dir; EX: "~{orig_dir}7/70/" -> "/xowa/wiki/custom_wiki/file/orig/7/70/"
			? Io_url_.new_dir_(orig_root.Raw() + String_.Mid(fil_orig_dir, String_.Len(Fs_root_wkr.Url_orig_dir)))
			// load literally;    EX: "/xowa/wiki/custom_wiki/file/orig/7/70/"
			: Io_url_.new_dir_(fil_orig_dir);
		return Orig_fil_row.New_by_db
		( rdr.Read_int(fld_uid)
		, Bry_.new_u8(name)
		, rdr.Read_int(fld_ext_id)
		, rdr.Read_int(fld_w)
		, rdr.Read_int(fld_h)
		, dir
		);
	}
	public void Insert(Orig_fil_row row, String dir) {
		if (stmt_insert == null) stmt_insert = conn.Stmt_insert(tbl_name, flds);
		stmt_insert.Clear()
		.Val_int       (fld_uid        , row.Uid())
		.Val_bry_as_str(fld_name       , row.Name())
		.Val_int       (fld_ext_id     , row.Ext_id())
		.Val_int       (fld_w          , row.W())
		.Val_int       (fld_h          , row.H())
		.Val_str       (fld_dir_url    , dir)
		.Exec_insert();
	}	
}
