/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2017 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.xowa.guis.urls; import gplx.*; import gplx.xowa.*; import gplx.xowa.guis.*;
import org.junit.*; import gplx.core.tests.*;
import gplx.xowa.files.origs.*;
public class Xog_url_wkr__file__tst {
	private final    Xog_url_wkr__file__fxt fxt = new Xog_url_wkr__file__fxt();
	@Test   public void Basic() {
		fxt.Test__extract("A.png", "file:///mem/xowa/file/commons.wikimedia.org/orig/7/0/A.png", 300, 200);
	}
	@Test   public void Url_encoded() {
		fxt.Test__extract("A,b.png", "file:///mem/xowa/file/commons.wikimedia.org/orig/d/6/A%2Cb.png", 300, 200);
	}
	@Test   public void Utf8() {
		fxt.Test__extract("Volcán_Chimborazo,_\"El_Taita_Chimborazo\".jpg", "file:///mem/xowa/file/commons.wikimedia.org/orig/3/c/Volc%C3%A1n_Chimborazo%2C_%22El_Taita_Chimborazo%22.jpg", 300, 200);
	}
}
class Xog_url_wkr__file__fxt {
	private final    Xowe_wiki wiki;
	private final    Bry_bfr bfr = Bry_bfr_.New();
	private final    Xog_url_wkr__file wkr;
	private final    Xof_orig_wkr__mock orig_wkr = new Xof_orig_wkr__mock();
	public Xog_url_wkr__file__fxt() {
		Xoae_app app = Xoa_app_fxt.Make__app__edit();
		wiki = Xoa_app_fxt.Make__wiki__edit(app);
		Xoa_app_fxt.repo2_(app, wiki);
		wiki.File__orig_mgr().Wkrs__add(orig_wkr);
		this.wkr = new Xog_url_wkr__file(app, wiki, Xoae_page.New_edit(wiki, wiki.Ttl_parse(Bry_.new_a7("Test_page"))));
	}
	public String Make__html(byte[] href_bry, byte[] xowa_ttl) {
		bfr.Add_str_u8_fmt("<a href=\"{0}\" class=\"@gplx.Internal protected\" title=\"test_title\" xowa_title=\"{1}\">Full resolution</a>", href_bry, xowa_ttl);
		return bfr.To_str_and_clear();
	}
	public void Make__orig(byte repo, byte[] page, int w, int h, byte[] redirect) {
		orig_wkr.Add_orig(repo, page, gplx.xowa.files.Xof_ext_.new_by_ttl_(page).Id(), w, h, redirect);
	}
	public void Exec__extract_data(byte[] href_bry, String html_doc) {
		wkr.Extract_data(html_doc, href_bry);
	}
	public void Test__extract(String file_page_db, String a_href_str, int orig_w, int orig_h) {
		byte[] a_href = Bry_.new_u8(a_href_str);
		this.Make__orig(Xof_orig_itm.Repo_wiki, Bry_.new_u8(file_page_db), orig_w, orig_h, Bry_.Empty);
		
		byte[] xowa_title_bry = gplx.xowa.htmls.core.wkrs.lnkis.htmls.Xoh_file_fmtr__basic.Escape_xowa_title(Bry_.new_u8(file_page_db));
		this.Exec__extract_data(a_href, this.Make__html(a_href, xowa_title_bry));

		Gftest.Eq__str(file_page_db	, wkr.File_page_db()							, "file_page_db");
		Gftest.Eq__bry(a_href		, wkr.File_url().To_http_file_bry()				, "file_url");
		Gftest.Eq__bry(a_href		, wkr.Fsdb().Html_view_url().To_http_file_bry()	, "fsdb.view_url");
		Gftest.Eq__int(orig_w		, wkr.Fsdb().Orig_w()							, "fsdb.orig_w");
		Gftest.Eq__int(orig_h		, wkr.Fsdb().Orig_h()							, "fsdb.orig_h");
	}
}
