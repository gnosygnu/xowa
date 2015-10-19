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
//namespace gplx.xowa.specials.search.quicks {
//	using gplx.dbs;
//	public class Xoa_search_tbl : RlsAble {
//		private final String tbl_name = "quick_search"; private final Db_meta_fld_list flds = Db_meta_fld_list.new_();
//		private final String fld_page_id, fld_page_title, fld_page_title_lower, fld_page_ns, fld_page_score, fld_page_len, fld_page_links, fld_page_descrip, fld_page_file_title;
//		private final Db_conn conn; // private Db_stmt stmt_insert, stmt_update, stmt_select;
//		public Db_conn Conn() {return conn;}
//		public Xoa_search_tbl(Db_conn conn) {
//			this.conn = conn;
//			fld_page_id				= flds.Add_int      ("page_id");
//			fld_page_title			= flds.Add_str		("page_title", 255);
//			fld_page_title_lower	= flds.Add_str		("page_title_lower", 255);
//			fld_page_ns				= flds.Add_int		("page_ns");
//			fld_page_score			= flds.Add_byte		("page_score");
//			fld_page_len			= flds.Add_byte		("page_len");
//			fld_page_links			= flds.Add_byte		("page_links");
//			fld_page_descrip		= flds.Add_str		("page_descrip", 1024);
//			fld_page_file_title		= flds.Add_str		("page_file_title", 255);
//			conn.Rls_reg(this);
//		}
//		public void Insert(int page_id, byte[] page_title, int page_ns, int page_score, int page_len, int page_links, byte[] page_descrip, byte[] page_file_title) {
//			Db_stmt stmt_insert = conn.Stmt_insert(tbl_name, flds);
//			stmt_insert.Clear()
//				.Val_int(fld_page_id, page_id).Val_bry_as_str(fld_page_title, page_title)
//				.Val_int(fld_page_ns, page_ns).Val_int(fld_page_len, page_len)
//				.Val_int(fld_page_links, page_links).Val_bry_as_str(fld_page_descrip, page_descrip)
//				.Val_bry_as_str(fld_page_file_title, page_file_title)
//				.Exec_insert();			
//		}
//		public void Rls() {
//		}
//	}
///*
//quick_search
//. page_id             123
//. page_title          Earth
//. page_title_lower    earth
//. page_ns             0
//. page_score          0
//. page_len            140
//. page_pagelinks      20
//. page_descrip      planet earth
//. page_file           Earth.png
//
//*/
//}
