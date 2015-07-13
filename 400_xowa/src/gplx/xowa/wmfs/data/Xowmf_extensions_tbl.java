/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012 gnosygnu@gmail.com

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package gplx.xowa.wmfs.data; import gplx.*; import gplx.xowa.*; import gplx.xowa.wmfs.*;
import gplx.dbs.*;
class Xowmf_extensions_tbl implements RlsAble {
	private static final String tbl_name = "wmf_extensions"; private final Db_meta_fld_list flds = new Db_meta_fld_list();
	private final String fld_site_id, fld_type, fld_name, fld_descriptionmsg, fld_author, fld_url, fld_version
	, fld_vcs_system, fld_vcs_version, fld_vcs_url, fld_vcs_date, fld_license_name, fld_license, fld_credits;
	private final Db_conn conn;
	private Db_stmt stmt_insert;
	public Xowmf_extensions_tbl(Db_conn conn) {
		this.conn = conn;
		this.fld_site_id				= flds.Add_int("site_id");
		this.fld_type					= flds.Add_str("type", 255);
		this.fld_name					= flds.Add_str("name", 255);
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
	public void Create_tbl() {conn.Ddl_create_tbl(Db_meta_tbl.new_(tbl_name, flds, Db_meta_idx.new_unique_by_name(tbl_name, "main", fld_site_id)));}
	public void Delete_all() {conn.Stmt_delete(tbl_name, Db_meta_fld.Ary_empty).Exec_delete();}
	public void Rls() {
		stmt_insert = Db_stmt_.Rls(stmt_insert);
	}
	public void Insert(int site_id, String type, String name, String descriptionmsg, String author, String url, String version
		, String vcs_system, String vcs_version, String vcs_url, String vcs_date, String license_name, String license, String credits) {
		if (stmt_insert == null) stmt_insert = conn.Stmt_insert(tbl_name, flds);
		stmt_insert.Clear()
			.Val_int(fld_site_id				, site_id)
			.Val_str(fld_type					, type)
			.Val_str(fld_name					, name)
			.Val_str(fld_descriptionmsg			, descriptionmsg)
			.Val_str(fld_author					, author)
			.Val_str(fld_url					, url)
			.Val_str(fld_version				, version)
			.Val_str(fld_vcs_system				, vcs_system)
			.Val_str(fld_vcs_version			, vcs_version)
			.Val_str(fld_vcs_url				, vcs_url)
			.Val_str(fld_vcs_date				, vcs_date)
			.Val_str(fld_license_name			, license_name)
			.Val_str(fld_license				, license)
			.Val_str(fld_credits				, credits)
			.Exec_insert();
	}
}
