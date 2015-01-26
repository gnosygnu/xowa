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
package gplx.xowa.hdumps.saves; import gplx.*; import gplx.xowa.*; import gplx.xowa.hdumps.*;
import gplx.dbs.*; import gplx.xowa.hdumps.core.*; import gplx.xowa.html.hzips.*;
import gplx.xowa2.gui.*;
class Hdump_stats_tbl {
	private Db_stmt stmt_insert;
	public Db_conn Conn() {return conn;} public Hdump_stats_tbl Conn_(Db_conn v) {this.Rls_all(); conn = v; return this;} private Db_conn conn;
	public Hdump_stats_tbl Create_tbl() {Sqlite_engine_.Tbl_create_and_delete(conn, Tbl_name, Tbl_sql); return this;}
	public void Insert(Xog_page hpg, Xow_hzip_stats hzip, int wtxt_len, int row_orig_len, int row_zip_len) {
		Hdump_module_mgr js_mgr = hpg.Module_mgr();
		Insert
		( hpg.Page_id(), wtxt_len, row_orig_len, row_zip_len
		, Len_or_0(hpg.Page_body()), Len_or_0(hpg.Display_ttl()), Len_or_0(hpg.Content_sub()), Len_or_0(hpg.Sidebar_div())
		, js_mgr.Math_exists(), js_mgr.Imap_exists(), js_mgr.Gallery_packed_exists(), js_mgr.Hiero_exists()
		, hzip.A_rhs(), hzip.Lnki_text_n(), hzip.Lnki_text_y()
		, hzip.Lnke_txt(), hzip.Lnke_brk_text_n(), hzip.Lnke_brk_text_y()
		, hzip.Hdr_1(), hzip.Hdr_2(), hzip.Hdr_3(), hzip.Hdr_4(), hzip.Hdr_5(), hzip.Hdr_6()
		, hzip.Img_full()
		);
	}
	private int Len_or_0(byte[] bry) {return bry == null ? 0 : bry.length;}
	public void Insert
	( int page_id, int wtxt_len, int row_orig_len, int row_zip_len, int body_len
	, int display_ttl_len, int content_sub_len, int sidebar_div_len
	, boolean js_math, boolean js_imap, boolean js_packed, boolean js_hiero
	, int a_rhs, int lnki_text_n, int lnki_text_y
	, int lnke_txt, int lnke_brk_text_n, int lnke_brk_text_y
	, int hdr_1, int hdr_2, int hdr_3, int hdr_4, int hdr_5, int hdr_6
	, int img_full
	) {
		if (stmt_insert == null) stmt_insert = Db_stmt_.new_insert_(conn, Tbl_name, Flds__all);
		try {
			stmt_insert.Clear()
			.Val_int(page_id).Val_int(wtxt_len).Val_int(row_orig_len).Val_int(row_zip_len)
			.Val_int(body_len).Val_int(display_ttl_len).Val_int(content_sub_len).Val_int(sidebar_div_len)
			.Val_bool_as_byte(js_math).Val_bool_as_byte(js_imap).Val_bool_as_byte(js_packed).Val_bool_as_byte(js_hiero)
			.Val_int(a_rhs).Val_int(lnki_text_n).Val_int(lnki_text_y)
			.Val_int(lnke_txt).Val_int(lnke_brk_text_n).Val_int(lnke_brk_text_y)
			.Val_int(hdr_1).Val_int(hdr_2).Val_int(hdr_3).Val_int(hdr_4).Val_int(hdr_5).Val_int(hdr_6)
			.Val_int(img_full)
			.Exec_insert();
		}
		catch (Exception exc) {stmt_insert = null; throw Err_.err_(exc, "stmt failed");} // must reset stmt, else next call will fail
	}
	public void Rls_all() {
		if (stmt_insert != null) {stmt_insert.Rls(); stmt_insert = null;}
		conn = null;
	}
	public static final String Tbl_name = "hdump_stats"
	, Fld_page_id = "page_id", Fld_wtxt_len = "wtxt_len", Fld_row_orig_len = "row_orig_len", Fld_row_zip_len = "row_zip_len"
	, Fld_body_len = "body_len", Fld_display_ttl_len = "display_ttl_len", Fld_content_sub_len = "content_sub_len", Fld_sidebar_div_len = "sidebar_div_len"
	, Fld_js_math = "js_math", Fld_js_imap = "js_imap", Fld_js_packed = "js_packed", Fld_js_hiero = "js_hiero"
	, Fld_a_rhs = "a_rhs", Fld_lnki_text_n = "lnki_text_n", Fld_lnki_text_y = "lnki_text_y"
	, Fld_lnke_txt = "lnke_txt", Fld_lnke_brk_text_n = "lnke_brk_text_n", Fld_lnke_brk_text_y = "lnke_brk_text_y"
	, Fld_hdr_1 = "hdr_1", Fld_hdr_2 = "hdr_2", Fld_hdr_3 = "hdr_3", Fld_hdr_4 = "hdr_4", Fld_hdr_5 = "hdr_5", Fld_hdr_6 = "hdr_6"
	, Fld_img_full = "img_full"
	;
	private static final String[] Flds__all = new String[] 
	{ Fld_page_id, Fld_wtxt_len, Fld_row_orig_len, Fld_row_zip_len
	, Fld_body_len, Fld_display_ttl_len, Fld_content_sub_len, Fld_sidebar_div_len
	, Fld_js_math, Fld_js_imap, Fld_js_packed, Fld_js_hiero
	, Fld_a_rhs, Fld_lnki_text_n, Fld_lnki_text_y
	, Fld_lnke_txt, Fld_lnke_brk_text_n, Fld_lnke_brk_text_y
	, Fld_hdr_1, Fld_hdr_2, Fld_hdr_3, Fld_hdr_4, Fld_hdr_5, Fld_hdr_6
	, Fld_img_full
	};
	public static final String Tbl_sql = String_.Concat_lines_nl
	( "CREATE TABLE IF NOT EXISTS hdump_stats"
	, "( page_id                        integer             NOT NULL      PRIMARY KEY"
	, ", wtxt_len                       integer             NOT NULL"
	, ", row_orig_len                   integer             NOT NULL"
	, ", row_zip_len                    integer             NOT NULL"
	, ", body_len                       integer             NOT NULL"
	, ", display_ttl_len                integer             NOT NULL"
	, ", content_sub_len                integer             NOT NULL"
	, ", sidebar_div_len                integer             NOT NULL"
	, ", js_math                        integer             NOT NULL"
	, ", js_imap                        integer             NOT NULL"
	, ", js_packed                      integer             NOT NULL"
	, ", js_hiero                       integer             NOT NULL"
	, ", a_rhs                          integer             NOT NULL"
	, ", lnki_text_n                    integer             NOT NULL"
	, ", lnki_text_y                    integer             NOT NULL"
	, ", lnke_txt                       integer             NOT NULL"
	, ", lnke_brk_text_n                integer             NOT NULL"
	, ", lnke_brk_text_y                integer             NOT NULL"
	, ", hdr_1							integer             NOT NULL"
	, ", hdr_2							integer             NOT NULL"
	, ", hdr_3							integer             NOT NULL"
	, ", hdr_4							integer             NOT NULL"
	, ", hdr_5							integer             NOT NULL"
	, ", hdr_6							integer             NOT NULL"
	, ", img_full						integer             NOT NULL"
	, ");"
	);
}
