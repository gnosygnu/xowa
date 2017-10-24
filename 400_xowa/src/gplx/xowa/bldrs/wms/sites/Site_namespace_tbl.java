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
package gplx.xowa.bldrs.wms.sites; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.wms.*;
import gplx.dbs.*;
public class Site_namespace_tbl implements Db_tbl {
	private final    Dbmeta_fld_list flds = new Dbmeta_fld_list();
	private final    String fld_site_abrv, fld_id, fld_case_tid, fld_canonical, fld_localized, fld_subpages, fld_content, fld_defaultcontentmodel;
	private final    Db_conn conn;
	private Db_stmt stmt_select, stmt_insert, stmt_delete;
	public Site_namespace_tbl(Db_conn conn) {
		this.conn = conn;
		this.fld_site_abrv				= flds.Add_str("site_abrv", 255);
		this.fld_id						= flds.Add_int("id");
		this.fld_case_tid				= flds.Add_str("case_tid", 255);
		this.fld_canonical				= flds.Add_str("canonical", 255);
		this.fld_localized				= flds.Add_str("localized", 255);
		this.fld_subpages				= flds.Add_byte("subpages");
		this.fld_content				= flds.Add_byte("content");
		this.fld_defaultcontentmodel	= flds.Add_str("defaultcontentmodel", 255);
		conn.Rls_reg(this);
	}
	public String Tbl_name() {return tbl_name;} private static final String tbl_name = "site_namespace";
	public void Create_tbl() {conn.Meta_tbl_create(Dbmeta_tbl_itm.New(tbl_name, flds, Dbmeta_idx_itm.new_unique_by_name(tbl_name, Dbmeta_idx_itm.Bld_idx_name(tbl_name, "main"), fld_site_abrv, fld_id)));}
	public void Delete_all() {conn.Stmt_delete(tbl_name, Dbmeta_fld_itm.Str_ary_empty).Exec_delete();}
	public void Rls() {
		stmt_select = Db_stmt_.Rls(stmt_select);
		stmt_insert = Db_stmt_.Rls(stmt_insert);
		stmt_delete = Db_stmt_.Rls(stmt_delete);
	}
	public void Select(byte[] site_abrv, Ordered_hash list) {
		if (stmt_select == null) stmt_select = conn.Stmt_select(tbl_name, flds, fld_site_abrv);
		list.Clear();
		Db_rdr rdr = stmt_select.Clear().Crt_bry_as_str(fld_site_abrv, site_abrv).Exec_select__rls_manual();
		try {
			while (rdr.Move_next()) {
				Site_namespace_itm itm = new Site_namespace_itm
				( rdr.Read_int			(fld_id)
				, rdr.Read_bry_by_str	(fld_case_tid)
				, rdr.Read_bry_by_str	(fld_canonical)
				, rdr.Read_bry_by_str	(fld_localized)
				, rdr.Read_bool_by_byte	(fld_subpages)
				, rdr.Read_bool_by_byte	(fld_content)
				, rdr.Read_bry_by_str	(fld_defaultcontentmodel)
				);
				list.Add(itm.Id(), itm);
			}
		}
		finally {rdr.Rls();}
	}
	public void Insert(byte[] site_abrv, Ordered_hash list) {
		if (stmt_delete == null) stmt_delete = conn.Stmt_delete(tbl_name, fld_site_abrv);
		stmt_delete.Clear().Crt_bry_as_str(fld_site_abrv, site_abrv).Exec_delete();
		int len = list.Count();
		for (int i = 0; i < len; ++i) {
			Site_namespace_itm itm = (Site_namespace_itm)list.Get_at(i);
			Insert(site_abrv, itm.Id(), itm.Case_tid(), itm.Canonical(), itm.Localized(), itm.Subpages(), itm.Content(), itm.Defaultcontentmodel());
		}
	}
	public void Insert(byte[] site_abrv, int id, byte[] case_tid, byte[] canonical, byte[] localized, boolean subpages, boolean content, byte[] defaultcontentmodel) {
		if (stmt_insert == null) stmt_insert = conn.Stmt_insert(tbl_name, flds);
		stmt_insert.Clear()
			.Val_bry_as_str		(fld_site_abrv				, site_abrv)
			.Val_int			(fld_id						, id)
			.Val_bry_as_str		(fld_case_tid				, case_tid)
			.Val_bry_as_str		(fld_canonical				, canonical)
			.Val_bry_as_str		(fld_localized				, localized)
			.Val_bool_as_byte	(fld_subpages				, subpages)
			.Val_bool_as_byte	(fld_content				, content)
			.Val_bry_as_str		(fld_defaultcontentmodel	, defaultcontentmodel)
			.Exec_insert();
	}
}
