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
package gplx.xowa.bldrs.wms.sites; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.wms.*;
import gplx.dbs.*;
class Site_statistic_tbl implements Db_tbl {
	private static final String tbl_name = "site_statistic"; private final Db_meta_fld_list flds = new Db_meta_fld_list();
	private final String fld_site_abrv, fld_pages, fld_articles, fld_edits, fld_images, fld_users, fld_activeusers, fld_admins, fld_jobs, fld_queued_massmessages;
	private final Db_conn conn;
	private Db_stmt stmt_select, stmt_insert, stmt_delete;
	public Site_statistic_tbl(Db_conn conn) {
		this.conn = conn;
		this.fld_site_abrv				= flds.Add_str("site_abrv", 255);
		this.fld_pages					= flds.Add_long("pages");
		this.fld_articles				= flds.Add_long("articles");
		this.fld_edits					= flds.Add_long("edits");
		this.fld_images					= flds.Add_long("images");
		this.fld_users					= flds.Add_long("users");
		this.fld_activeusers			= flds.Add_long("activeusers");
		this.fld_admins					= flds.Add_long("admins");
		this.fld_jobs					= flds.Add_long("jobs");
		this.fld_queued_massmessages	= flds.Add_long("queued_massmessages");
		conn.Rls_reg(this);
	}
	public void Create_tbl() {conn.Ddl_create_tbl(Db_meta_tbl.new_(tbl_name, flds, Db_meta_idx.new_unique_by_name(tbl_name, Db_meta_idx.Bld_idx_name(tbl_name, "main"), fld_site_abrv)));}
	public void Delete_all() {conn.Stmt_delete(tbl_name, Db_meta_fld.Ary_empty).Exec_delete();}
	public void Rls() {
		stmt_select = Db_stmt_.Rls(stmt_select);
		stmt_insert = Db_stmt_.Rls(stmt_insert);
		stmt_delete = Db_stmt_.Rls(stmt_delete);
	}
	public void Select(byte[] site_abrv, Site_statistic_itm itm) {
		if (stmt_select == null) stmt_select = conn.Stmt_select(tbl_name, flds, fld_site_abrv);
		Db_rdr rdr = stmt_select.Clear().Crt_bry_as_str(fld_site_abrv, site_abrv).Exec_select__rls_auto();
		try {
			if (rdr.Move_next()) {
				itm.Ctor
				( rdr.Read_long(fld_pages)
				, rdr.Read_long(fld_articles)
				, rdr.Read_long(fld_edits)
				, rdr.Read_long(fld_images)
				, rdr.Read_long(fld_users)
				, rdr.Read_long(fld_activeusers)
				, rdr.Read_long(fld_admins)
				, rdr.Read_long(fld_jobs)
				, rdr.Read_long(fld_queued_massmessages)
				);
			}
		}
		finally {rdr.Rls();}
	}
	public void Insert(byte[] site_abrv, Site_statistic_itm itm) {
		if (stmt_delete == null) stmt_delete = conn.Stmt_delete(tbl_name, fld_site_abrv);
		if (stmt_insert == null) stmt_insert = conn.Stmt_insert(tbl_name, flds);
		stmt_delete.Clear().Crt_bry_as_str(fld_site_abrv, site_abrv).Exec_delete();
		Insert(site_abrv, itm.Pages(), itm.Articles(), itm.Edits(), itm.Images(), itm.Users(), itm.Activeusers(), itm.Admins(), itm.Jobs(), itm.Queued_massmessages());
	}
	private void Insert(byte[] site_abrv, long pages, long articles, long edits, long images, long users, long activeusers, long admins, long jobs, long queued_massmessages) {
		if (stmt_insert == null) stmt_insert = conn.Stmt_insert(tbl_name, flds);
		stmt_insert.Clear()
			.Val_bry_as_str(fld_site_abrv		, site_abrv)
			.Val_long(fld_pages					, pages)
			.Val_long(fld_articles				, articles)
			.Val_long(fld_edits					, edits)
			.Val_long(fld_images				, images)
			.Val_long(fld_users					, users)
			.Val_long(fld_activeusers			, activeusers)
			.Val_long(fld_admins				, admins)
			.Val_long(fld_jobs					, jobs)
			.Val_long(fld_queued_massmessages	, queued_massmessages)
			.Exec_insert();
	}
}
