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
package gplx.xowa.wikis.data.tbls; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*; import gplx.xowa.wikis.data.*;
import gplx.dbs.*; import gplx.xowa.wikis.metas.*;
public class Xowd_site_stats_tbl {
	private final String tbl_name = "site_stats";
	private final String fld_row_id, fld_good_articles, fld_total_pages, fld_images;
	private final Db_conn conn; private final Db_meta_fld_list flds = Db_meta_fld_list.new_();
	public Xowd_site_stats_tbl(Db_conn conn, boolean schema_is_1) {
		this.conn = conn;
		fld_row_id			= flds.Add_int_pkey("ss_row_id");
		fld_good_articles	= flds.Add_long("ss_good_articles");
		fld_total_pages		= flds.Add_long("ss_total_pages");
		fld_images			= flds.Add_int("ss_images");
	}
	public void Create_tbl() {
		conn.Ddl_create_tbl(Db_meta_tbl.new_(tbl_name, flds));
		conn.Stmt_insert(tbl_name, flds).Val_int(fld_row_id, Site_stats_row_id).Val_long(fld_good_articles, 0).Val_long(fld_total_pages, 0).Val_int(fld_images, 0).Exec_insert();
	}
	public void Update(int num_articles, int num_pages, int num_files) {
		Db_stmt stmt = conn.Stmt_update(tbl_name, String_.Ary(fld_row_id), fld_good_articles, fld_total_pages, fld_images);
		stmt.Val_long(fld_good_articles, num_articles).Val_long(fld_total_pages, num_pages).Val_int(fld_images, num_files)
			.Crt_int(fld_row_id, Site_stats_row_id)
			.Exec_update();
	}
	public void Select(Xow_wiki_stats stats) {
		Db_rdr rdr = conn.Stmt_select(tbl_name, flds, fld_row_id).Crt_int(fld_row_id, Site_stats_row_id).Exec_select__rls_auto();
		try {
			if (rdr.Move_next()) {
				stats.NumArticles_	((int)rdr.Read_long(fld_good_articles));	// #<>(int)rdr.Read_long~rdr.Read_int
				stats.NumPages_		((int)rdr.Read_long(fld_total_pages));		// #<>(int)rdr.Read_long~rdr.Read_int
				stats.NumFiles_		(rdr.Read_int(fld_images));
			}
		} finally {rdr.Rls();}
	}
	private static final int Site_stats_row_id = 1;
}
