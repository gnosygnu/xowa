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
class Site_interwikimap_tbl implements Db_tbl {
	private final    Dbmeta_fld_list flds = new Dbmeta_fld_list();
	private final    String fld_site_abrv, fld_prefix, fld_local, fld_extralanglink, fld_linktext, fld_sitename, fld_language, fld_localinterwiki, fld_url, fld_protorel;
	private final    Db_conn conn;
	private Db_stmt stmt_select, stmt_insert, stmt_delete;
	public Site_interwikimap_tbl(Db_conn conn) {
		this.conn = conn;
		this.fld_site_abrv				= flds.Add_str("site_abrv", 255);
		this.fld_prefix					= flds.Add_str("prefix", 255);
		this.fld_local					= flds.Add_bool("local");
		this.fld_extralanglink			= flds.Add_bool("extralanglink");
		this.fld_linktext				= flds.Add_str("linktext", 255);
		this.fld_sitename				= flds.Add_str("sitename", 255);
		this.fld_language				= flds.Add_str("language", 255);
		this.fld_localinterwiki			= flds.Add_bool("localinterwiki");
		this.fld_url					= flds.Add_str("url", 255);
		this.fld_protorel				= flds.Add_bool("protorel");
		conn.Rls_reg(this);
	}
	public String Tbl_name() {return tbl_name;} private static final String tbl_name = "site_interwikimap";
	public void Create_tbl() {conn.Meta_tbl_create(Dbmeta_tbl_itm.New(tbl_name, flds, Dbmeta_idx_itm.new_unique_by_name(tbl_name, Dbmeta_idx_itm.Bld_idx_name(tbl_name, "main"), fld_site_abrv, fld_prefix)));}
	public void Delete_all() {conn.Stmt_delete(tbl_name, Dbmeta_fld_itm.Str_ary_empty).Exec_delete();}
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
				Site_interwikimap_itm itm = new Site_interwikimap_itm
				( rdr.Read_bry_by_str(fld_prefix)
				, rdr.Read_bool_by_byte(fld_local)
				, rdr.Read_bool_by_byte(fld_extralanglink)
				, rdr.Read_bry_by_str(fld_linktext)
				, rdr.Read_bry_by_str(fld_sitename)
				, rdr.Read_bry_by_str(fld_language)
				, rdr.Read_bool_by_byte(fld_localinterwiki)
				, rdr.Read_bry_by_str(fld_url)
				, rdr.Read_bool_by_byte(fld_protorel)
				);
				list.Add(itm.Prefix, itm);
			}
		}
		finally {rdr.Rls();}
	}
	public void Insert(byte[] site_abrv, Ordered_hash list) {
		if (stmt_delete == null) stmt_delete = conn.Stmt_delete(tbl_name, fld_site_abrv);
		if (stmt_insert == null) stmt_insert = conn.Stmt_insert(tbl_name, flds);
		stmt_delete.Clear().Crt_bry_as_str(fld_site_abrv, site_abrv).Exec_delete();
		int len = list.Count();
		for (int i = 0; i < len; ++i) {
			Site_interwikimap_itm itm = (Site_interwikimap_itm)list.Get_at(i);
			Insert(site_abrv, itm.Prefix, itm.Local, itm.Extralanglink, itm.Linktext, itm.Sitename, itm.Language, itm.Localinterwiki, itm.Url, itm.Protorel);
		}
	}
	private void Insert(byte[] site_abrv, byte[] prefix, boolean local, boolean extralanglink, byte[] linktext, byte[] sitename, byte[] language, boolean localinterwiki, byte[] url, boolean protorel) {
		stmt_insert.Clear()
			.Val_bry_as_str(fld_site_abrv			, site_abrv)
			.Val_bry_as_str(fld_prefix				, prefix)
			.Val_bool_as_byte(fld_local				, local)
			.Val_bool_as_byte(fld_extralanglink		, extralanglink)
			.Val_bry_as_str(fld_linktext			, linktext)
			.Val_bry_as_str(fld_sitename			, sitename)
			.Val_bry_as_str(fld_language			, language)
			.Val_bool_as_byte(fld_localinterwiki	, localinterwiki)
			.Val_bry_as_str(fld_url					, url)
			.Val_bool_as_byte(fld_protorel			, protorel)
			.Exec_insert();
	}
}
