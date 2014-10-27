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
import gplx.dbs.*; import gplx.xowa.files.*; import gplx.xowa.hdumps.dbs.*; import gplx.xowa.hdumps.srls.*;
import gplx.xowa.hdumps.core.*; import gplx.xowa.hdumps.pages.*; import gplx.xowa.pages.*; import gplx.xowa.pages.skins.*; import gplx.xowa.hdumps.loads.*;
public class Hdump_save_mgr {
	private Bry_bfr tmp_bfr = Bry_bfr.reset_(1 * Io_mgr.Len_mb);
	public Xodb_wiki_page_html_tbl Tbl() {return text_tbl;} public void Tbl_(Xodb_wiki_page_html_tbl v) {text_tbl = v;} private Xodb_wiki_page_html_tbl text_tbl;
	public void Update(Xoa_page page) {
		int page_id = page.Revision_data().Id();
		text_tbl.Delete(page_id);
		this.Insert(page);
	}
	public void Hdump_stats_enable_y_(Db_provider p) {hdump_stats_tbl = new Hdump_stats_tbl().Provider_(p).Create_tbl();} private Hdump_stats_tbl hdump_stats_tbl;
	public void Insert(Xoa_page page) {
		int page_id = page.Revision_data().Id();
		Insert_body(page, page_id);
		byte[] redlinks_bry = Write_redlinks(tmp_bfr, page.Hdump_data().Redlink_mgr());
		if (redlinks_bry != null)	text_tbl.Insert(page_id, Hdump_data_tid.Tid_redlink, redlinks_bry);
		byte[] imgs_bry = Write_imgs(tmp_bfr, page.Hdump_data().Data());
		if (imgs_bry != null)		text_tbl.Insert(page_id, Hdump_data_tid.Tid_img, imgs_bry);
	}
	public int Insert_body(Xoa_page page, int page_id) {
		hpg.Init(tmp_bfr, page);
		srl_mgr.Save(hpg, tmp_bfr);
		byte[] body_bry = tmp_bfr.Xto_bry_and_clear();
		int insert_len = text_tbl.Insert(page_id, Xodb_wiki_page_html_row.Tid_page, body_bry);
		if (hdump_stats_tbl != null) hdump_stats_tbl.Insert(hpg, page.Root().Root_src().length, body_bry.length, insert_len);
		return insert_len;
	}	private Hpg_srl_mgr srl_mgr = Hpg_srl_mgr._i_; private Hdump_page hpg = new Hdump_page();
	public static byte[] Write_imgs(Bry_bfr bfr, ListAdp imgs) {
		int len = imgs.Count(); if (len == 0) return null; // no images; exit early, else will write blank String
		for (int i = 0; i < len; ++i) {
			Hdump_data_itm itm = (Hdump_data_itm)imgs.FetchAt(i);
			itm.Data_write(bfr);
		}
		return bfr.Xto_bry_and_clear();
	}
	public static byte[] Write_redlinks(Bry_bfr bfr, Xopg_redlink_mgr redlink_mgr) {
		int len = redlink_mgr.Len(); if (len == 0) return null;
		bfr.Add_int_variable(redlink_mgr.Max());
		for (int i = 0; i < len; ++i) {
			bfr.Add_byte_pipe().Add_int_variable(redlink_mgr.Get_at(i));
		}
		return bfr.Xto_bry_and_clear();
	}
}
class Hdump_stats_tbl {
	private Db_stmt stmt_insert;
	public Db_provider Provider() {return provider;} public Hdump_stats_tbl Provider_(Db_provider v) {this.Rls_all(); provider = v; return this;} private Db_provider provider;
	public Hdump_stats_tbl Create_tbl() {Sqlite_engine_.Tbl_create_and_delete(provider, Tbl_name, Tbl_sql); return this;}
	public void Insert(Hdump_page hpg, int wtxt_len, int row_orig_len, int row_zip_len) {
		Hdump_module_mgr js_mgr = hpg.Module_mgr();
		Insert
		( hpg.Page_id(), wtxt_len, row_orig_len, row_zip_len
		, Len_or_0(hpg.Page_body()), Len_or_0(hpg.Display_ttl()), Len_or_0(hpg.Content_sub()), Len_or_0(hpg.Sidebar_div())
		, js_mgr.Math_exists(), js_mgr.Imap_exists(), js_mgr.Gallery_packed_exists(), js_mgr.Hiero_exists()
		);
	}
	private int Len_or_0(byte[] bry) {return bry == null ? 0 : bry.length;}
	public void Insert(int page_id, int wtxt_len, int row_orig_len, int row_zip_len, int body_len, int display_ttl_len, int content_sub_len, int sidebar_div_len, boolean js_math, boolean js_imap, boolean js_packed, boolean js_hiero) {
		if (stmt_insert == null) stmt_insert = Db_stmt_.new_insert_(provider, Tbl_name, Flds__all);
		try {
			stmt_insert.Clear()
				.Val_int_(page_id).Val_int_(wtxt_len).Val_int_(row_orig_len).Val_int_(row_zip_len).Val_int_(body_len).Val_int_(display_ttl_len).Val_int_(content_sub_len).Val_int_(sidebar_div_len)
				.Val_byte_by_bool_(js_math).Val_byte_by_bool_(js_imap).Val_byte_by_bool_(js_packed).Val_byte_by_bool_(js_hiero)
				.Exec_insert();
		}
		catch (Exception exc) {stmt_insert = null; throw Err_.err_(exc, "stmt failed");} // must reset stmt, else next call will fail
	}
	public void Rls_all() {
		if (stmt_insert != null) {stmt_insert.Rls(); stmt_insert = null;}
		provider = null;
	}
	public static final String Tbl_name = "hdump_stats"
	, Fld_page_id = "page_id", Fld_wtxt_len = "wtxt_len", Fld_row_orig_len = "row_orig_len", Fld_row_zip_len = "row_zip_len"
	, Fld_body_len = "body_len", Fld_display_ttl_len = "display_ttl_len", Fld_content_sub_len = "content_sub_len", Fld_sidebar_div_len = "sidebar_div_len"
	, Fld_js_math = "js_math", Fld_js_imap = "js_imap", Fld_js_packed = "js_packed", Fld_js_hiero = "js_hiero"
	;
	private static final String[] Flds__all = new String[] {Fld_page_id, Fld_wtxt_len, Fld_row_orig_len, Fld_row_zip_len, Fld_body_len, Fld_display_ttl_len, Fld_content_sub_len, Fld_sidebar_div_len, Fld_js_math, Fld_js_imap, Fld_js_packed, Fld_js_hiero};
	public static final String Tbl_sql = String_.Concat_lines_nl
	( "CREATE TABLE IF NOT EXISTS hdump_stats"
	, "( page_id                integer             NOT NULL      PRIMARY KEY"
	, ", wtxt_len               integer             NOT NULL"
	, ", row_orig_len           integer             NOT NULL"
	, ", row_zip_len            integer             NOT NULL"
	, ", body_len               integer             NOT NULL"
	, ", display_ttl_len        integer             NOT NULL"
	, ", content_sub_len        integer             NOT NULL"
	, ", sidebar_div_len        integer             NOT NULL"
	, ", js_math                integer             NOT NULL"
	, ", js_imap                integer             NOT NULL"
	, ", js_packed              integer             NOT NULL"
	, ", js_hiero               integer             NOT NULL"
	, ");"
	);
}
