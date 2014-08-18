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
package gplx.xowa.dbs.hdumps.htmls; import gplx.*; import gplx.xowa.*; import gplx.xowa.dbs.*; import gplx.xowa.dbs.hdumps.*;
import org.junit.*;
public class Hdump_html_mgr_tst {
	@Before public void init() {
		fxt.Clear();
		fxt.Init_img(0, 220, 110, "A.png", "commons.wikimedia.org/thumb/7/0/A.png/220.png");
	}	private Hdump_html_mgr_fxt fxt = new Hdump_html_mgr_fxt();
	@Test  public void Img() {
		fxt	.Init_body("~<img|0>")
			.Test_html(" src='file:///mem/xowa/file/commons.wikimedia.org/thumb/7/0/A.png/220.png' width='220' height='110'");
	}
	@Test  public void Img_w() {
		fxt	.Init_body("~<img.w|0>")
			.Test_html("220");
	}
	@Test  public void Mda_info() {
		fxt	.Init_body("~<mda.info|0>")
			.Test_html(String_.Concat_lines_nl_skip_last
			( ""
			, "      <div>"
			, "        <a href=\"/wiki/File:A.png\" class=\"image\" title=\"About this file\">"
			, "          <img src=\"file:///mem/xowa/user/test_user/app/img/file/info.png\" width=\"22\" height=\"22\" />"
			, "        </a>"
			, "      </div>"
			));
	}
	@Test  public void Mda_mgnf() {
		fxt	.Init_body("~<mda.mgnf|0>")
			.Test_html(String_.Concat_lines_nl_skip_last
			( ""
			, "      <div class=\"magnify\">"
			, "        <a href=\"/wiki/File:A.png\" class=\"internal\" title=\"A.png\">"
			, "          <img src=\"file:///mem/xowa/user/test_user/app/img/file/magnify-clip.png\" width=\"15\" height=\"11\" alt=\"\" />"
			, "        </a>"
			, "      </div>"
			));
	}
	@Test  public void Mda_play() {
		fxt	.Init_body("~<mda.play|0>")
			.Test_html(String_.Concat_lines_nl_skip_last
			( ""
			, "      <div>"
			, "        <a id=\"xowa_file_play_0\" href=\"/wiki/File:A.png\" xowa_title=\"A.png\" class=\"xowa_anchor_button\" style=\"width:220px;max-width:1024px;\">"
			, "          <img src=\"file:///mem/xowa/user/test_user/app/img/file/play.png\" width=\"22\" height=\"22\" alt=\"Play sound\" />"
			, "        </a>"
			, "      </div>"
			));
	}
}
class Hdump_html_mgr_fxt {
	private Hdump_html_mgr html_mgr = new Hdump_html_mgr();
	private Hdump_page_itm page = new Hdump_page_itm();
	private Bry_bfr bfr = Bry_bfr.reset_(255);
	private Bry_fmtr skin_fmtr = Bry_fmtr.new_("~{display_ttl}~{content_sub}~{sidebar_divs}~{body_html}", "display_ttl", "content_sub", "sidebar_divs", "body_html");
	private ListAdp img_list = ListAdp_.new_();
	private Xow_wiki wiki;
	public void Clear() {
		html_mgr.Init_by_app(Gfo_usr_dlg_.Null, Bry_.new_ascii_("file:///mem/xowa/file/"));
		Xoa_app app = Xoa_app_fxt.app_();
		wiki = Xoa_app_fxt.wiki_tst_(app);
	}
	public Hdump_html_mgr_fxt Init_body(String body) {page.Page_body_(Bry_.new_utf8_(body)); return this;}
	public Hdump_html_mgr_fxt Init_img(int id, int w, int h, String ttl, String src) {img_list.Add(new Hdump_img_itm(id, w, h, Bry_.new_utf8_(ttl), Bry_.new_utf8_(src))); return this;}
	public Hdump_html_mgr_fxt Test_html(String expd) {
		if (img_list.Count() > 0) page.Img_itms_((Hdump_img_itm[])img_list.XtoAryAndClear(Hdump_img_itm.class));
		html_mgr.Write(bfr, wiki, skin_fmtr, page);
		Tfds.Eq_str_lines(expd, bfr.XtoStrAndClear());
		return this;
	}
}
