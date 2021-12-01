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
package gplx.xowa.bldrs.wms.sites; import gplx.*;
import gplx.dbs.*;
class Site_library_tbl implements Db_tbl {
	private final DbmetaFldList flds = new DbmetaFldList();
	private final String fld_site_abrv, fld_name, fld_version;
	private final Db_conn conn;
	private Db_stmt stmt_select, stmt_insert, stmt_delete;
	public Site_library_tbl(Db_conn conn) {
		this.conn = conn;
		this.fld_site_abrv				= flds.AddStr("site_abrv", 255);
		this.fld_name					= flds.AddStr("name", 255);
		this.fld_version				= flds.AddStr("version", 255);
		conn.Rls_reg(this);
	}
	public String Tbl_name() {return tbl_name;} private static final String tbl_name = "site_library";
	public void Create_tbl() {conn.Meta_tbl_create(Dbmeta_tbl_itm.New(tbl_name, flds, Dbmeta_idx_itm.new_unique_by_name(tbl_name, Dbmeta_idx_itm.Bld_idx_name(tbl_name, "main"), fld_site_abrv, fld_name)));}
	public void Delete_all() {conn.Stmt_delete(tbl_name, DbmetaFldItm.StrAryEmpty).Exec_delete();}
	public void Rls() {
		stmt_select = Db_stmt_.Rls(stmt_select);
		stmt_insert = Db_stmt_.Rls(stmt_insert);
		stmt_delete = Db_stmt_.Rls(stmt_delete);
	}
	public void Select(byte[] site_abrv, Ordered_hash list) {
		if (stmt_select == null) stmt_select = conn.Stmt_select(tbl_name, flds, fld_site_abrv);
		list.Clear();
		Db_rdr rdr = stmt_select.Clear().Crt_bry_as_str(fld_site_abrv, site_abrv).Exec_select__rls_auto();
		try {
			while (rdr.Move_next()) {
				Site_library_itm itm = new Site_library_itm
				( rdr.Read_bry_by_str(fld_name)
				, rdr.Read_bry_by_str(fld_version)
				);
				list.Add(itm.Name(), itm);
			}
		}
		finally {rdr.Rls();}
	}
	public void Insert(byte[] site_abrv, Ordered_hash list) {
		if (stmt_delete == null) stmt_delete = conn.Stmt_delete(tbl_name, fld_site_abrv);
		if (stmt_insert == null) stmt_insert = conn.Stmt_insert(tbl_name, flds);
		stmt_delete.Clear().Crt_bry_as_str(fld_site_abrv, site_abrv).Exec_delete();
		int len = list.Len();
		for (int i = 0; i < len; ++i) {
			Site_library_itm itm = (Site_library_itm)list.Get_at(i);
			Insert(site_abrv, itm.Name(), itm.Version());
		}
	}
	private void Insert(byte[] site_abrv, byte[] name, byte[] version) {
		stmt_insert.Clear()
			.Val_bry_as_str(fld_site_abrv		, site_abrv)
			.Val_bry_as_str(fld_name			, name)
			.Val_bry_as_str(fld_version			, version)
			.Exec_insert();
	}		
}
