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
package gplx.xowa.apps.servers.http.hdocs; import gplx.*; import gplx.xowa.*; import gplx.xowa.apps.*; import gplx.xowa.apps.servers.*; import gplx.xowa.apps.servers.http.*;
import org.junit.*; import gplx.core.tests.*;
import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.wkrs.*;
import gplx.xowa.htmls.core.makes.*;
public class Xoh_hdoc_wkr__http_server__tst {
	private final    Xoh_hdoc_wkr__http_server__fxt fxt = new Xoh_hdoc_wkr__http_server__fxt();
	@Before public void init() {Gfo_usr_dlg_.Instance = Gfo_usr_dlg_.Test_console();}
	@After public void term() {Gfo_usr_dlg_.Instance = Gfo_usr_dlg_.Noop;}
	@Test  public void Wiki_quot() {
		fxt.Test
		( "<a id=\"id1\" href=\"/wiki/A\">abc</a>"
		, "<a id=\"id1\" href=\"/en.wikipedia.org/wiki/A\">abc</a>"
		);
	}
	@Test  public void Wiki_apos() {
		fxt.Test
		( "<a id=\"id1\" href='/wiki/A'>abc</a>"
		, "<a id=\"id1\" href='/en.wikipedia.org/wiki/A'>abc</a>"
		);
	}
//		@Test  public void Lnki_caption() {
//			fxt.Test
//			( "<a id=\"id1\" href='/wiki/A'>abc <a href='/wiki/B'</a>lmn</a> xyz</a>"
//			, "<a id=\"id1\" href='/en.wikipedia.org/wiki/A'>abc <a href='/en.wikipedia.org/wiki/B'</a>lmn</a> xyz</a>"
//			);
//		}
	@Test  public void Xcmd() {
		fxt.Test
		( "<a id=\"id1\" href=\"xowa-cmd:a\">abc</a>"
		, "<a id=\"id1\" href=\"/exec/a\">abc</a>"
		);
	}
	@Test  public void Site() {
		fxt.Test
		( "<a id=\"id1\" href=\"/site/en.wikipedia.org/wiki/Special:Search/A\">abc</a>"
		, "<a id=\"id1\" href=\"/en.wikipedia.org/wiki/Special:Search/A\">abc</a>"
		);
	}
	@Test  public void Action() { // NOTE: also used by Special:ItemByTitle; EX: "<form method=\"get\" action=\"//www.wikidata.org/wiki/Special:ItemByTitle\" name=\"itembytitle\" id=\"wb-itembytitle-form1\">"
		fxt.Test
		( "<form id=\"searchform\" action=\"/wiki/SearchUrl\">abc</form>"
		, "<form id=\"searchform\" action=\"/en.wikipedia.org/wiki/SearchUrl\">abc</form>"
		);
	}
	@Test  public void Fsys() {
		fxt.Test
		( "<a id=\"id1\" href=\"file:///mem/xowa/file/A.png\">abc</a>"
		, "<a id=\"id1\" href=\"/fsys/file/A.png\">abc</a>"
		);
	}
	@Test  public void Fsys_bug() { // 2019-05 enwiki embedded build machine's path
		fxt.Test
		( "<a id=\"id1\" href=\"file:////home/lnxusr/xowa/file/A.png\">abc</a>"
		, "<a id=\"id1\" href=\"/fsys/file/A.png\">abc</a>"
		);
	}
	@Test  public void Fsys_img() {
		fxt.Test
		( "<a href='/wiki/File:A.jpg' class='image'><img src=\"file:///mem/xowa/file/commons.wikimedia.org/thumb/7/0/A.png/128px.png\">abc</img></a>"
		, "<a href='/en.wikipedia.org/wiki/File:A.jpg' class='image'><img src=\"/fsys/file/commons.wikimedia.org/thumb/7/0/A.png/128px.png\">abc</img></a>"
		);
	}
	@Test  public void Fsys_div() {
		fxt.Test
		( String_.Concat_lines_nl
			( "<div class='thumb tleft'>"
			, "  <div id='xowa_file_div_2' class='thumbinner' style='width:128px;'>"
			, "    <a href='/wiki/File:A.jpg' class='image' xowa_title='A.jpg'><img id='xoimg_2' alt='' src='file:///mem/xowa/file/commons.wikimedia.org/thumb/7/0/A.png/128px.png' width='128' height='100' class='thumbimage'></a> "
			, "    <div class='thumbcaption'>"
			, "      <div class='magnify'>"
			, "        <a href='/wiki/File:A.jpg' class='@gplx.Internal protected' title='Enlarge'></a>"
			, "      </div>"
			, "      thumb_caption"
			, "    </div>"
			, "  </div>"
			, "</div>"
			)
			, String_.Concat_lines_nl
			( "<div class='thumb tleft'>"
			, "  <div id='xowa_file_div_2' class='thumbinner' style='width:128px;'>"
			, "    <a href='/en.wikipedia.org/wiki/File:A.jpg' class='image' xowa_title='A.jpg'><img id='xoimg_2' alt='' src='/fsys/file/commons.wikimedia.org/thumb/7/0/A.png/128px.png' width='128' height='100' class='thumbimage'></a> "
			, "    <div class='thumbcaption'>"
			, "      <div class='magnify'>"
			, "        <a href='/en.wikipedia.org/wiki/File:A.jpg' class='@gplx.Internal protected' title='Enlarge'></a>"
			, "      </div>"
			, "      thumb_caption"
			, "    </div>"
			, "  </div>"
			, "</div>"
			)
		);
	}
	@Test  public void Fsys_gallery() {
		fxt.Test
		( String_.Concat_lines_nl_skip_last
			( "<ul id=\"xowa_gallery_ul_0\" class=\"gallery mw-gallery-traditional\">"
			, "  <li id=\"xowa_gallery_li_0\" class=\"gallerybox\" style=\"width:235px;\">"
			, "    <div style=\"width:235px;\">"
			, "      <div class=\"thumb\" style=\"width:230px;\">"
			, "        <div style=\"margin:15px auto;\">"
			, "          <a href=\"/wiki/File:A.png\" class=\"image\" xowa_title=\"A.png\"><img id=\"xoimg_0\" alt=\"\" src=\"file:///mem/xowa/file/commons.wikimedia.org/thumb/7/0/A.png/200px.png\" width=\"200\" height=\"300\" /></a>"
			, "        </div>"
			, "      </div>"
			, "      <div class=\"gallerytext\"><p><i>a1</i>"
			, "</p>"
			, ""
			, "      </div>"
			, "    </div>"
			, "  </li>"
			, "  <li id=\"xowa_gallery_li_1\" class=\"gallerybox\" style=\"width:235px;\">"
			, "    <div style=\"width:235px;\">"
			, "      <div class=\"thumb\" style=\"width:230px;\">"
			, "        <div style=\"margin:15px auto;\">"
			, "          <a href=\"/wiki/File:B.png\" class=\"image\" xowa_title=\"B.png\"><img id=\"xoimg_1\" alt=\"\" src=\"file:///mem/xowa/file/commons.wikimedia.org/thumb/5/7/B.png/200px.png\" width=\"200\" height=\"300\" /></a>"
			, "        </div>"
			, "      </div>"
			, "      <div class=\"gallerytext\"><p><i>b1</i>"
			, "</p>"
			, ""
			, "      </div>"
			, "    </div>"
			, "  </li>"
			, "</ul>"
			)
		, String_.Concat_lines_nl_skip_last
			( "<ul id=\"xowa_gallery_ul_0\" class=\"gallery mw-gallery-traditional\">"
			, "  <li id=\"xowa_gallery_li_0\" class=\"gallerybox\" style=\"width:235px;\">"
			, "    <div style=\"width:235px;\">"
			, "      <div class=\"thumb\" style=\"width:230px;\">"
			, "        <div style=\"margin:15px auto;\">"
			, "          <a href=\"/wiki/File:A.png\" class=\"image\" xowa_title=\"A.png\"><img id=\"xoimg_0\" alt=\"\" src=\"file:///mem/xowa/file/commons.wikimedia.org/thumb/7/0/A.png/200px.png\" width=\"200\" height=\"300\" /></a>"
			, "        </div>"
			, "      </div>"
			, "      <div class=\"gallerytext\"><p><i>a1</i>"
			, "</p>"
			, ""
			, "      </div>"
			, "    </div>"
			, "  </li>"
			, "  <li id=\"xowa_gallery_li_1\" class=\"gallerybox\" style=\"width:235px;\">"
			, "    <div style=\"width:235px;\">"
			, "      <div class=\"thumb\" style=\"width:230px;\">"
			, "        <div style=\"margin:15px auto;\">"
			, "          <a href=\"/wiki/File:B.png\" class=\"image\" xowa_title=\"B.png\"><img id=\"xoimg_1\" alt=\"\" src=\"file:///mem/xowa/file/commons.wikimedia.org/thumb/5/7/B.png/200px.png\" width=\"200\" height=\"300\" /></a>"
			, "        </div>"
			, "      </div>"
			, "      <div class=\"gallerytext\"><p><i>b1</i>"
			, "</p>"
			, ""
			, "      </div>"
			, "    </div>"
			, "  </li>"
			, "</ul>"
			)
		);
	}
}
class Xoh_hdoc_wkr__http_server__fxt {
	private final    Xow_wiki wiki;
	private final    Xoh_page hpg;
	private final    Xoh_make_mgr hdoc_mgr = Xoh_make_mgr.New(new Xoh_hdoc_wkr__http_server());
	public Xoh_hdoc_wkr__http_server__fxt() {
		Xop_fxt fxt = Xop_fxt.New_app_html();
		this.wiki = fxt.Wiki();
		int page_id = 123;
		byte[] page_ttl_bry = Bry_.new_u8("Test_Page");
		Xoa_url page_url = Xoa_url.New(wiki.Domain_bry(), page_ttl_bry);
		Xoa_ttl page_ttl = wiki.Ttl_parse(page_ttl_bry);
		this.hpg = new Xoh_page();
		hpg.Ctor_by_hview(wiki, page_url, page_ttl, page_id);
	}
	public void Test(String src_str, String expd) {
		byte[] src = Bry_.new_u8(src_str);
		byte[] actl = hdoc_mgr.Parse(src, wiki, hpg);
		Gftest.Eq__ary__lines(expd, String_.new_u8(actl));
	}
}
