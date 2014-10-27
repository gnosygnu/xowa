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
package gplx.xowa.hdumps.loads; import gplx.*; import gplx.xowa.*; import gplx.xowa.hdumps.*;
import org.junit.*; import gplx.xowa.files.*; import gplx.xowa.hdumps.dbs.*; import gplx.xowa.hdumps.core.*; import gplx.xowa.hdumps.saves.*; import gplx.xowa.hdumps.pages.*; import gplx.xowa.dbs.*;
public class Hdump_load_mgr_tst {
	@Before public void init() {fxt.Clear();} private Hdump_load_mgr_fxt fxt = new Hdump_load_mgr_fxt();
//		@Test  public void Body() {
//			fxt.Init_row_body("<body/>", null, null, null);
//			fxt.Expd_body("<body/>");
//			fxt.Test_load(0);
//		}
//		@Test  public void Body_all() {
//			fxt.Init_row_body("<body/>", "test_display_ttl", "test_content_sub", "test_sidebar_div");
//			fxt.Expd_body("<body/>");
//			fxt.Expd_display_ttl("test_display_ttl");
//			fxt.Expd_content_sub("test_content_sub");
//			fxt.Expd_sidebar_div("test_sidebar_div");
//			fxt.Test_load(0);
//		}
	@Test  public void Img() {
		fxt.Init_row_img
		( fxt.Make_img("A.png", 0, 220, 110)
		, fxt.Make_img("B.png", 1, 200, 100)
		);
		fxt.Expd_img(fxt.Make_img("A.png", 0, 220, 110));
		fxt.Expd_img(fxt.Make_img("B.png", 1, 200, 100));
		fxt.Test_load(0);
	}
}
class Hdump_load_mgr_fxt {
	private Hdump_load_mgr load_mgr;
	private Hdump_page hpg = new Hdump_page();
	private ListAdp init_rows = ListAdp_.new_();
	private String expd_body, expd_display_ttl, expd_content_sub, expd_sidebar_div;
	private ListAdp expd_imgs = ListAdp_.new_();
	private int page_id = 0; private Xoa_url page_url;
	public void Clear() {
		load_mgr = new Hdump_load_mgr();
		load_mgr.Zip_tid_(gplx.ios.Io_stream_.Tid_file);
		init_rows.Clear();
		expd_body = expd_display_ttl = expd_content_sub = expd_sidebar_div = null;
		expd_imgs.Clear();
		page_url = Xoa_url.new_(Bry_.new_ascii_("enwiki"), Bry_.new_ascii_("Page_1"));
	}
	public Hdump_data_img__base Make_img(String lnki_ttl, int html_uid, int html_w, int html_h) {
		return new Hdump_data_img__basic().Init_by_base(Bry_.new_utf8_(lnki_ttl), html_uid, html_w, html_h, Hdump_data_img__base.File_repo_id_commons, Xof_ext_.Id_png, Bool_.N, html_w, Xof_doc_thumb.Null, Xof_doc_page.Null);
	}
	public void Init_row_img(Hdump_data_img__base... itms) {
		ListAdp tmp_list = ListAdp_.new_();
		Bry_bfr bfr = Bry_bfr.new_(255);
		tmp_list.AddMany((Object[])itms);
		byte[] imgs_bry = Hdump_save_mgr.Write_imgs(bfr, tmp_list);
		init_rows.Add(new Xodb_wiki_page_html_row(0, Xodb_wiki_page_html_row.Tid_data, imgs_bry));
	}
	public Hdump_load_mgr_fxt Init_row_body(String body, String display_ttl, String content_sub, String sidebar_div)	{
		Bry_bfr tmp_bfr = Bry_bfr.reset_(255);
		Xoa_app app = Xoa_app_fxt.app_();
		Xow_wiki wiki = Xoa_app_fxt.wiki_tst_(app);
		Xoa_page page = Xoa_page.new_(wiki, Xoa_ttl.parse_(wiki, Bry_.new_ascii_("Page_1")));
		if (display_ttl != null) page.Html_data().Display_ttl_(Bry_.new_utf8_(display_ttl));
		if (content_sub != null) page.Html_data().Content_sub_(Bry_.new_utf8_(content_sub));
		if (sidebar_div != null) page.Html_data().Xtn_skin_mgr().Add(new Xopg_xtn_skin_itm_mock(Bry_.new_utf8_(sidebar_div)));
		page.Hdump_data().Body_(Bry_.new_utf8_(body));
		Hdump_page_body_srl.Save(tmp_bfr, page);
		init_rows.Add(new Xodb_wiki_page_html_row(page_id, Xodb_wiki_page_html_row.Tid_page, tmp_bfr.Xto_bry_and_clear()));
		return this;
	}
	public Hdump_load_mgr_fxt Init_row_img (String data)	{init_rows.Add(new Xodb_wiki_page_html_row(page_id, Xodb_wiki_page_html_row.Tid_data, Bry_.new_utf8_(data))); return this;}
	public Hdump_load_mgr_fxt Expd_body(String v) {this.expd_body = v; return this;}
	public Hdump_load_mgr_fxt Expd_display_ttl(String v) {this.expd_display_ttl = v; return this;}
	public Hdump_load_mgr_fxt Expd_content_sub(String v) {this.expd_content_sub = v; return this;}
	public Hdump_load_mgr_fxt Expd_sidebar_div(String v) {this.expd_sidebar_div = v; return this;}
	public Hdump_load_mgr_fxt Expd_img(Hdump_data_img__base img) {expd_imgs.Add(img); return this;}
	public Hdump_load_mgr_fxt Test_load(int page_id) {
		load_mgr.Load_rows(hpg, page_id, page_url, Xoa_ttl.Null, init_rows);
		if (expd_body != null)			Tfds.Eq(expd_body, String_.new_utf8_(hpg.Page_body()));
		if (expd_display_ttl != null)	Tfds.Eq(expd_display_ttl, String_.new_utf8_(hpg.Display_ttl()));
		if (expd_content_sub != null)	Tfds.Eq(expd_content_sub, String_.new_utf8_(hpg.Content_sub()));
		if (expd_sidebar_div != null)	Tfds.Eq(expd_sidebar_div, String_.new_utf8_(hpg.Sidebar_div()));
		if (expd_imgs.Count() != 0)		Tfds.Eq_ary_str((Hdump_data_img__base[])expd_imgs.Xto_ary_and_clear(Hdump_data_img__base.class), hpg.Img_itms());
		return this;
	}
}
