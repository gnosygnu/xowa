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
package gplx.xowa.dbs.tbls; import gplx.*; import gplx.xowa.*; import gplx.xowa.dbs.*;
import gplx.dbs.*;
public class Xodb_site_stats_tbl {
	public void Conn_(Db_conn conn) {this.conn = conn;} Db_conn conn;
	public void Update(int num_articles, int num_pages, int num_files) {
		Db_stmt stmt = Db_stmt_.Null;
		try {
			stmt = Db_stmt_.new_update_(conn, Tbl_name, String_.Ary(Fld_ss_row_id), Fld_ss_good_articles, Fld_ss_total_pages, Fld_ss_images);
			stmt.Val_int(num_articles)
				.Val_int(num_pages)
				.Val_int(num_files)
				.Val_int(1)
				.Exec_update();
				;
		} finally {stmt.Rls();}
	}
	public void Select(Xow_wiki wiki) {
		Xow_wiki_stats stats = wiki.Stats();		
		DataRdr rdr = DataRdr_.Null;
		Db_stmt stmt = Db_stmt_.Null;
		try {
			stmt = Db_stmt_.new_select_all_(conn, Tbl_name);
			rdr = stmt.Exec_select();
			if (rdr.MoveNextPeer()) {
				stats.NumArticles_	(rdr.ReadInt(Fld_ss_good_articles));	
				stats.NumPages_		(rdr.ReadInt(Fld_ss_total_pages));	
				stats.NumFiles_		(rdr.ReadInt(Fld_ss_images));
			}
		} finally {rdr.Rls(); stmt.Rls();}
	}
	public static final String Tbl_name = "site_stats", Fld_ss_row_id = "ss_row_id", Fld_ss_good_articles = "ss_good_articles", Fld_ss_total_pages = "ss_total_pages", Fld_ss_images = "ss_images";
}
