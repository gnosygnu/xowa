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
class Site_extension_tbl implements Db_tbl {
	private final    Dbmeta_fld_list flds = new Dbmeta_fld_list();
	private final    String fld_site_abrv, fld_type, fld_name, fld_namemsg, fld_description, fld_descriptionmsg, fld_author, fld_url, fld_version
	, fld_vcs_system, fld_vcs_version, fld_vcs_url, fld_vcs_date, fld_license_name, fld_license, fld_credits;
	private final    Db_conn conn;
	private Db_stmt stmt_select, stmt_insert, stmt_delete;
	public Site_extension_tbl(Db_conn conn) {
		this.conn = conn;
		this.fld_site_abrv				= flds.Add_str("site_abrv", 255);
		this.fld_type					= flds.Add_str("type", 255);
		this.fld_name					= flds.Add_str("name", 255);
		this.fld_namemsg				= flds.Add_str("namemsg", 255);
		this.fld_description			= flds.Add_str("description", 255);
		this.fld_descriptionmsg			= flds.Add_str("descriptionmsg", 255);
		this.fld_author					= flds.Add_str("author", 255);
		this.fld_url					= flds.Add_str("url", 255);
		this.fld_version				= flds.Add_str("version", 255);
		this.fld_vcs_system				= flds.Add_str("vcs_system", 255);
		this.fld_vcs_version			= flds.Add_str("vcs_version", 255);
		this.fld_vcs_url				= flds.Add_str("vcs_url", 255);
		this.fld_vcs_date				= flds.Add_str("vcs_date", 255);
		this.fld_license_name			= flds.Add_str("license_name", 255);
		this.fld_license				= flds.Add_str("license", 255);
		this.fld_credits				= flds.Add_str("credits", 255);
		conn.Rls_reg(this);
	}
	public String Tbl_name() {return tbl_name;} private static final String tbl_name = "site_extension";
	public void Create_tbl() {conn.Meta_tbl_create(Dbmeta_tbl_itm.New(tbl_name, flds, Dbmeta_idx_itm.new_unique_by_name(tbl_name, Dbmeta_idx_itm.Bld_idx_name(tbl_name, "main"), fld_site_abrv, fld_type, fld_name)));}
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
				Site_extension_itm itm = new Site_extension_itm
				( rdr.Read_bry_by_str(fld_type)
				, rdr.Read_bry_by_str(fld_name)
				, rdr.Read_bry_by_str(fld_namemsg)
				, rdr.Read_bry_by_str(fld_description)
				, rdr.Read_bry_by_str(fld_descriptionmsg)
				, rdr.Read_bry_by_str(fld_author)
				, rdr.Read_bry_by_str(fld_url)
				, rdr.Read_bry_by_str(fld_version)
				, rdr.Read_bry_by_str(fld_vcs_system)
				, rdr.Read_bry_by_str(fld_vcs_version)
				, rdr.Read_bry_by_str(fld_vcs_url)
				, rdr.Read_bry_by_str(fld_vcs_date)
				, rdr.Read_bry_by_str(fld_license_name)
				, rdr.Read_bry_by_str(fld_license)
				, rdr.Read_bry_by_str(fld_credits)
				);
				list.Add(itm.Key(), itm);
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
			Site_extension_itm itm = (Site_extension_itm)list.Get_at(i);
			Insert(site_abrv, itm.Type(), itm.Name(), itm.Namemsg(), itm.Description(), itm.Descriptionmsg(), itm.Author(), itm.Url(), itm.Version()
				, itm.Vcs_system(), itm.Vcs_version(), itm.Vcs_url(), itm.Vcs_date(), itm.License_name(), itm.License(), itm.Credits());
		}
	}
	private void Insert(byte[] site_abrv, byte[] type, byte[] name, byte[] namemsg, byte[] description, byte[] descriptionmsg, byte[] author, byte[] url, byte[] version
		, byte[] vcs_system, byte[] vcs_version, byte[] vcs_url, byte[] vcs_date, byte[] license_name, byte[] license, byte[] credits) {
		stmt_insert.Clear()
			.Val_bry_as_str(fld_site_abrv				, site_abrv)
			.Val_bry_as_str(fld_type					, type)
			.Val_bry_as_str(fld_name					, name)
			.Val_bry_as_str(fld_namemsg					, namemsg)
			.Val_bry_as_str(fld_description				, description)
			.Val_bry_as_str(fld_descriptionmsg			, descriptionmsg)
			.Val_bry_as_str(fld_author					, author)
			.Val_bry_as_str(fld_url						, url)
			.Val_bry_as_str(fld_version					, version)
			.Val_bry_as_str(fld_vcs_system				, vcs_system)
			.Val_bry_as_str(fld_vcs_version				, vcs_version)
			.Val_bry_as_str(fld_vcs_url					, vcs_url)
			.Val_bry_as_str(fld_vcs_date				, vcs_date)
			.Val_bry_as_str(fld_license_name			, license_name)
			.Val_bry_as_str(fld_license					, license)
			.Val_bry_as_str(fld_credits					, credits)
			.Exec_insert();
	}
}
