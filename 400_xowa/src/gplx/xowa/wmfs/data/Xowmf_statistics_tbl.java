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
class Xowmf_statistics_tbl implements RlsAble {
	private static final String tbl_name = "wmf_statistics"; private final Db_meta_fld_list flds = new Db_meta_fld_list();
	private final String fld_site_id, fld_pages, fld_articles, fld_edits, fld_images, fld_users, fld_activeusers, fld_admins, fld_queued_massmessages;
	private final Db_conn conn;
	private Db_stmt stmt_insert;
	public Xowmf_statistics_tbl(Db_conn conn) {
		this.conn = conn;
		this.fld_site_id				= flds.Add_int("site_id");
		this.fld_pages					= flds.Add_long("pages");
		this.fld_articles				= flds.Add_long("articles");
		this.fld_edits					= flds.Add_long("edits");
		this.fld_images					= flds.Add_long("images");
		this.fld_users					= flds.Add_long("users");
		this.fld_activeusers			= flds.Add_long("activeusers");
		this.fld_admins					= flds.Add_long("admins");
		this.fld_queued_massmessages	= flds.Add_long("queued_massmessages");
		conn.Rls_reg(this);
	}
	public void Create_tbl() {conn.Ddl_create_tbl(Db_meta_tbl.new_(tbl_name, flds, Db_meta_idx.new_unique_by_name(tbl_name, "main", fld_site_id)));}
	public void Delete_all() {conn.Stmt_delete(tbl_name, Db_meta_fld.Ary_empty).Exec_delete();}
	public void Rls() {
		stmt_insert = Db_stmt_.Rls(stmt_insert);
	}
	public void Insert(int site_id, long pages, long articles, long edits, long images, long users, long activeusers, long admins, long queued_massmessages) {
		if (stmt_insert == null) stmt_insert = conn.Stmt_insert(tbl_name, flds);
		stmt_insert.Clear()
			.Val_int(fld_site_id				, site_id)
			.Val_long(fld_pages					, pages)
			.Val_long(fld_articles				, articles)
			.Val_long(fld_edits					, edits)
			.Val_long(fld_images				, images)
			.Val_long(fld_users					, users)
			.Val_long(fld_activeusers			, activeusers)
			.Val_long(fld_admins				, admins)
			.Val_long(fld_queued_massmessages	, queued_massmessages)
			.Exec_insert();
	}
}
