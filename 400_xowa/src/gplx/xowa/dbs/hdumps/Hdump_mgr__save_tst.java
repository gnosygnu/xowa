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
package gplx.xowa.dbs.hdumps; import gplx.*; import gplx.xowa.*; import gplx.xowa.dbs.*;
import org.junit.*; import gplx.dbs.*; import gplx.xowa.files.*;
public class Hdump_mgr__save_tst {
	@Before public void init() {fxt.Clear();} private Hdump_mgr__save_fxt fxt = new Hdump_mgr__save_fxt();
	@Test   public void Body() {
		fxt.Test_save("abc", fxt.Make_itm_body("abc"));
	}
	@Test   public void Img() {
		fxt.Test_save("a[[File:A.png|test_caption]]b"
		, fxt.Make_itm_body("a<a href=\"/wiki/File:A.png\" class=\"image\" xowa_title=\"A.png\"><img id=\"xowa_file_img_0\" alt=\"test_caption\"~<img|0> /></a>b")
		, fxt.Make_itm_img(0, 0, 0, "A.png", "orig/7/0/A.png")
		);
	}
	@Test   public void Display_title() {
		fxt.Test_save("{{DISPLAYTITLE:A}}bcd", fxt.Make_itm_body("bcd"), fxt.Make_itm_display_title("A"));
	}
	@Test   public void Content_sub() {
		fxt.Test_save("{{#isin:A}}bcd", fxt.Make_itm_body("bcd"), fxt.Make_itm_content_sub("<a href=\"/wiki/A\">A</a>"));
	}
	@Test   public void Sidebar_div() {
		fxt.Test_save("{{#related:A}}bcd", fxt.Make_itm_body("bcd"), fxt.Make_itm_sidebar_div(String_.Concat_lines_nl_skip_last
		( "<div class=\"portal\" role=\"navigation\" id=\"p-relatedarticles\">"
		, "  <h3></h3>"
		, "  <div class=\"body\">"
		, "    <ul>"
		, "      <li class=\"interwiki-relart\"><a href=\"/wiki/A\">A</a></li>"
		, "    </ul>"
		, "  </div>"
		, "</div>"
		)));
	}
}
class Hdump_mgr__save_fxt extends Hdump_mgr__base_fxt {
	private int page_id = 0;
	private ListAdp text_itms = ListAdp_.new_();
	@Override public void Clear_end() {
		db_mgr.Mode_mem_();
		db_mgr.Text_tbl().Provider_(Hdump_text_tbl_mem.Null_provider);
	}
	public Hdump_text_row Make_itm_body(String v) {return new Hdump_text_row(page_id, Hdump_text_row_tid.Tid_body, 0, 0, Bry_.new_utf8_(v));}
	public Hdump_text_row Make_itm_img(int uid, int img_w, int img_h, String lnki_ttl, String img_src_rel) {return new Hdump_text_row(page_id, Hdump_text_row_tid.Tid_img, 0, 0, Hdump_text_row.data_img_(bfr, uid, img_w, img_h, Bry_.new_utf8_(lnki_ttl), Bry_.new_utf8_(img_src_rel)));}
	public Hdump_text_row Make_itm_display_title(String v) {return new Hdump_text_row(page_id, Hdump_text_row_tid.Tid_display_ttl, 0, 0, Bry_.new_utf8_(v));}
	public Hdump_text_row Make_itm_content_sub(String v) {return new Hdump_text_row(page_id, Hdump_text_row_tid.Tid_content_sub, 0, 0, Bry_.new_utf8_(v));}
	public Hdump_text_row Make_itm_sidebar_div(String v) {return new Hdump_text_row(page_id, Hdump_text_row_tid.Tid_sidebar_div, 0, 0, Bry_.new_utf8_(v));}
	public void Test_save(String raw, Hdump_text_row... expd) {
		this.Exec_write(raw);
		hdump_mgr.Save_mgr().Update(page);
		db_mgr.Text_tbl().Select_by_page(text_itms, 0);
		Hdump_text_row[] actl = (Hdump_text_row[])text_itms.XtoAryAndClear(Hdump_text_row.class);
		Tfds.Eq_ary_str(Xto_str_ary(expd), Xto_str_ary(actl));
	}
	private static String[] Xto_str_ary(Hdump_text_row[] ary) {
		int len = ary.length;
		String[] rv = new String[len];
		Bry_bfr bfr = Bry_bfr.new_();
		for (int i = 0; i < len; ++i) {
			Hdump_text_row itm = ary[i];
			bfr					.Add_int_variable(itm.Page_id())
				.Add_byte_pipe().Add_int_variable(itm.Tid())
				.Add_byte_pipe().Add_int_variable(itm.Idx())
				.Add_byte_pipe().Add_int_variable(itm.Version_id())
				.Add_byte_pipe().Add(itm.Data())
				;
			rv[i] = bfr.XtoStrAndClear();
		}
		return rv;
	}
}
