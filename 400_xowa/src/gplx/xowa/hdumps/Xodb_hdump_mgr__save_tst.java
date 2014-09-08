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
package gplx.xowa.hdumps; import gplx.*; import gplx.xowa.*;
import org.junit.*; import gplx.dbs.*; import gplx.xowa.files.*;
import gplx.xowa.hdumps.core.*; import gplx.xowa.hdumps.dbs.*; import gplx.xowa.hdumps.saves.*; import gplx.xowa.hdumps.loads.*; import gplx.xowa.hdumps.pages.*;
public class Xodb_hdump_mgr__save_tst {
	@Before public void init() {fxt.Clear();} private Xodb_hdump_mgr__save_fxt fxt = new Xodb_hdump_mgr__save_fxt();
	@Test   public void Body() {
		fxt.Test_save("abc", fxt.Make_row_body(0, "abc"));
	}
	@Test   public void Img() {
		fxt.Test_save("a[[File:A.png|test_caption]]b[[File:B.png|test_caption]]"
		, fxt.Make_row_body(2, "a<a href=\"/wiki/File:A.png\" class=\"image\" xowa_title=\"A.png\"><img id=\"xowa_file_img_0\" alt=\"test_caption\" xowa_img='0' /></a>b<a href=\"/wiki/File:B.png\" class=\"image\" xowa_title=\"B.png\"><img id=\"xowa_file_img_1\" alt=\"test_caption\" xowa_img='1' /></a>")
		, fxt.Make_row_img
			( fxt.Make_img(0, 0, 0, "A.png", "trg/orig/7/0/A.png")
			, fxt.Make_img(1, 0, 0, "B.png", "trg/orig/5/7/B.png")
			)
		);
	}
	@Test   public void Display_title() {
		fxt.Test_write("{{DISPLAYTITLE:A}}bcd", String_.Concat_lines_nl_skip_last
		( "0|0"
		, "<xo_1/>n|n|n|n|"
		, "<xo_2/>A"
		, "<xo_5/>bcd"
		));
	}
	@Test   public void Content_sub() {
		fxt.Test_write("{{#isin:A}}bcd", String_.Concat_lines_nl_skip_last
		( "0|0"
		, "<xo_1/>n|n|n|n|"
		, "<xo_3/><a href=\"/wiki/A\">A</a>"
		, "<xo_5/>bcd"
		));
	}
	@Test   public void Sidebar_div() {
		fxt.Test_write("{{#related:A}}bcd", String_.Concat_lines_nl_skip_last
		( "0|0"
		, "<xo_1/>n|n|n|n|"
		, "<xo_4/><div class=\"portal\" role=\"navigation\" id=\"p-relatedarticles\">"
		, "  <h3></h3>"
		, "  <div class=\"body\">"
		, "    <ul>"
		, "      <li class=\"interwiki-relart\"><a href=\"/wiki/A\">A</a></li>"
		, "    </ul>"
		, "  </div>"
		, "</div>"
		, "<xo_5/>bcd"
		));
	}
}
class Xodb_hdump_mgr__save_fxt extends Xodb_hdump_mgr__base_fxt {
	private int page_id = 0;
	private Bry_bfr tmp_bfr = Bry_bfr.reset_(255);
	private ListAdp expd_rows = ListAdp_.new_();
	@Override public void Clear_end() {
		hdump_mgr.Tbl_mem_();
		hdump_mgr.Text_tbl().Provider_(Hdump_text_tbl_mem.Null_provider);
	}
	public Hdump_text_row Make_row_body(int imgs_count, String body) {
		page.Hdump_data().Body_(Bry_.new_utf8_(body));
		page.Hdump_data().Data_count_imgs_(imgs_count);
		Hdump_page_body_srl.Save(tmp_bfr, page);
		return new Hdump_text_row(page_id, Hdump_text_row_tid.Tid_body, tmp_bfr.XtoAryAndClear());
	}
	public Hdump_data_img__base Make_img(int uid, int img_w, int img_h, String lnki_ttl, String img_src_rel) {return new Hdump_data_img__basic().Init_by_base(uid, img_w, img_h, Bry_.new_utf8_(lnki_ttl), Bry_.new_utf8_(img_src_rel));}
	public Hdump_text_row Make_row_img(Hdump_data_img__base... itms) {
		ListAdp tmp_list = ListAdp_.new_();
		tmp_list.AddMany((Object[])itms);
		byte[] imgs_bry = Hdump_save_mgr.Write_imgs(tmp_bfr, tmp_list);
		return new Hdump_text_row(page_id, Hdump_text_row_tid.Tid_data, imgs_bry);
	}
	public void Test_write(String raw, String expd) {
		this.Exec_write(raw);
		Hdump_page_body_srl.Save(tmp_bfr, page);
		Tfds.Eq(expd, tmp_bfr.XtoStrAndClear());
	}
	public void Test_save(String raw, Hdump_text_row... expd) {
		this.Exec_write(raw);
		hdump_mgr.Save_mgr().Update(page);
		hdump_mgr.Text_tbl().Select_by_page(expd_rows, 0);
		Hdump_text_row[] actl = (Hdump_text_row[])expd_rows.XtoAryAndClear(Hdump_text_row.class);
		Tfds.Eq_ary_str(Xto_str_ary(tmp_bfr, expd), Xto_str_ary(tmp_bfr, actl));
	}
	private static String[] Xto_str_ary(Bry_bfr bfr, Hdump_text_row[] ary) {
		int len = ary.length;
		String[] rv = new String[len];
		for (int i = 0; i < len; ++i) {
			Hdump_text_row itm = ary[i];
			bfr					.Add_int_variable(itm.Page_id())
				.Add_byte_pipe().Add_int_variable(itm.Tid())
				.Add_byte_pipe().Add(itm.Data())
				;
			rv[i] = bfr.XtoStrAndClear();
		}
		return rv;
	}
}
class Hdump_text_row_img {
	public Hdump_text_row_img(int uid, int img_w, int img_h, byte[] lnki_ttl, byte[] img_src_rel) {this.uid = uid; this.img_w = img_w; this.img_h = img_h; this.lnki_ttl = lnki_ttl; this.img_src_rel = img_src_rel;}
	public int Uid() {return uid;} private int uid;
	public int Img_w() {return img_w;} private int img_w;
	public int Img_h() {return img_h;} private int img_h;
	public byte[] Lnki_ttl() {return lnki_ttl;} private byte[] lnki_ttl;
	public byte[] Img_src_rel() {return img_src_rel;} private byte[] img_src_rel;
	// return new Hdump_text_row(page_id, Hdump_text_row_tid.Tid_img, 0, Hdump_save_mgr.Write_img(bfr, uid, img_w, img_h, Bry_.new_utf8_(lnki_ttl), Bry_.new_utf8_(img_src_rel)))
}
