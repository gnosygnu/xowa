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
package gplx.xowa.addons.wikis.ctgs.htmls.catpages; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.ctgs.*; import gplx.xowa.addons.wikis.ctgs.htmls.*;
import org.junit.*; import gplx.xowa.htmls.core.htmls.*;
import gplx.xowa.addons.wikis.ctgs.htmls.catpages.doms.*; import gplx.xowa.addons.wikis.ctgs.htmls.catpages.fmts.*;
public class Xoctg_catpage_mgr_tst {
	@Before public void init() {fxt.Clear();} private Xoh_ctg_page_fxt fxt = new Xoh_ctg_page_fxt();
	@Test   public void Page_itm() {
		fxt	.Init_itms__pages("A1")
			.Test_html_page(Xoa_ctg_mgr.Tid_page, Byte_ascii.Ltr_A, "\n            <li><a href=\"/wiki/A1\" title=\"A1\">A1</a></li>");
	}
	@Test   public void Page_itm_missing() {
		fxt	.Init_itms__pages("A1");
		fxt	.Ctg().Grp_by_tid(Xoa_ctg_mgr.Tid_page).Itms()[0].Missing_y_();
		fxt	.Test_html_page(Xoa_ctg_mgr.Tid_page, Byte_ascii.Ltr_A, "\n            <li class=\"xowa-missing-category-entry\"><span title=\"id not found: #0 might be talk/user page\">A1 (missing)</li>");
	}
	@Test   public void Visited_doesnt_work_for_space() {// PURPOSE: xowa-visited not inserted for pages with space
		byte[] page_bry = Bry_.new_a7("A 1");
		Xoa_url url = Xoa_url.New(Bry_.new_a7("en.wikipedia.org"), page_bry);
		Xoa_ttl ttl = Xoa_ttl.Parse(fxt.Wiki(), page_bry);
		fxt.Wiki().Appe().Usere().History_mgr().Add(url, ttl, page_bry);
		fxt	.Init_itms__pages("A_1").Init_grp__pages(0, 1)
			.Test_html_all(Xoa_ctg_mgr.Tid_page, String_.Concat_lines_nl_skip_last
			( ""
			, "<div id=\"mw-pages\">"
			, "  <h2>Pages in category \"Ctg_1\"</h2>"
			, "  <p>This category contains only the following page.</p>"
			, "  <div lang=\"en\" dir=\"ltr\" class=\"mw-content-ltr\">"
			, "    <table style=\"width: 100%;\">"
			, "      <tr style=\"vertical-align: top;\">"
			, "        <td style=\"width: 33%;\">"
			, "          <h3>A</h3>"
			, "          <ul>"
			, "            <li><a href=\"/wiki/A_1\" class=\"xowa-visited\" title=\"A 1\">A 1</a></li>"
			, "          </ul>"
			, "        </td>"
			, "      </tr>"
			, "    </table>"
			, "  </div>"
			, "</div>"
			));
	}
	@Test   public void Page_all() {
		fxt	.Init_itms__pages("A1").Init_grp__pages(0, 1)
			.Test_html_all(Xoa_ctg_mgr.Tid_page, String_.Concat_lines_nl_skip_last
			( ""
			, "<div id=\"mw-pages\">"
			, "  <h2>Pages in category \"Ctg_1\"</h2>"
			, "  <p>This category contains only the following page.</p>"
			, "  <div lang=\"en\" dir=\"ltr\" class=\"mw-content-ltr\">"
			, "    <table style=\"width: 100%;\">"
			, "      <tr style=\"vertical-align: top;\">"
			, "        <td style=\"width: 33%;\">"
			, "          <h3>A</h3>"
			, "          <ul>"
			, "            <li><a href=\"/wiki/A1\" title=\"A1\">A1</a></li>"
			, "          </ul>"
			, "        </td>"
			, "      </tr>"
			, "    </table>"
			, "  </div>"
			, "</div>"
			));
	}
	@Test   public void File_all() {
		fxt	.Init_itms__files("File:A1.png").Init_grp__files(0, 1)
			.Test_html_all(Xoa_ctg_mgr.Tid_file, String_.Concat_lines_nl_skip_last
			( ""
			, "<div id=\"mw-category-media\">"
			, "  <h2>Media in category \"Ctg_1\"</h2>"
			, "  <p>This category contains only the following file.</p>"
			, "  <div lang=\"en\" dir=\"ltr\" class=\"mw-content-ltr\">"
			, "    <table style=\"width: 100%;\">"
			, "      <tr style=\"vertical-align: top;\">"
			, "        <td style=\"width: 33%;\">"
			, "          <h3>A</h3>"
			, "          <ul>"
			, "            <li><a href=\"/wiki/File:A1.png\" title=\"File:A1.png\">File:A1.png</a></li>"
			, "          </ul>"
			, "        </td>"
			, "      </tr>"
			, "    </table>"
			, "  </div>"
			, "</div>"
			));
//				, "  <ul id=\"xowa_gallery_ul_0\" class=\"gallery\">"
//				, "  <li class=\"gallerybox\" style=\"width:155px;\">"
//				, "    <div style=\"width:155px;\">"
//				, "      <div class=\"thumb\" style=\"width:155px;\">"
//				, "        <div id=\"xowa_gallery_div3_-1\" style=\"margin:15px auto;\">"
//				, "          <a href=\"/wiki/File:A1.png\" class=\"image\">"
//				, "            <img id=\"xoimg_-1\" alt=\"A1.png\" src=\"\" width=\"-1\" height=\"-1\" />"
//				, "          </a>"
//				, "        </div>"
//				, "      </div>"
//				, "      <div class=\"gallerytext\">A1.png"
//				, "      </div>"
//				, "    </div>"
//				, "  </li>"
//				, ""
//				, "  </ul>"
	}
	@Test   public void Subc_all() {
		fxt	.Init_itms__subcs("Category:Subc_1").Init_grp__files(0, 1)
			.Test_html_all(Xoa_ctg_mgr.Tid_subc, String_.Concat_lines_nl_skip_last
			( ""
			, "<div id=\"mw-subcategories\">"
			, "  <h2>Subcategories</h2>"
			, "  <p>This category has only the following subcategory.</p>"
			, "  <div lang=\"en\" dir=\"ltr\" class=\"mw-content-ltr\">"
			, "    <table style=\"width: 100%;\">"
			, "      <tr style=\"vertical-align: top;\">"
			, "        <td style=\"width: 33%;\">"
			, "          <h3>S</h3>"
			, "          <ul>"
			, "            <li>"
			, "              <div class=\"CategoryTreeSection\">"
			, "                <div class=\"CategoryTreeItem\">"
			, "                  <span class=\"CategoryTreeBullet\">"
			, "                    <span class=\"CategoryTreeToggle\" style=\"display: none;\" data-ct-title=\"Subc_1\" title=\"Subc 1\" data-ct-state=\"collapsed\">"
			, "                    </span> "
			, "                  </span>"
			, "                  <a href=\"/wiki/Category:Subc_1\" class=\"CategoryTreeLabel CategoryTreeLabelNs14 CategoryTreeLabelCategory\">Subc 1"
			, "                  </a>"
			, "                  <span title=\"contains 0 subcategories, 0 pages, and 0 files\" dir=\"ltr\">"
			, "                  </span>"
			, "                </div>"
			, "                <div class=\"CategoryTreeChildren\" style=\"display:none\"></div>"
			, "              </div>"
			, "            </li>"
			, "          </ul>"
			, "        </td>"
			, "      </tr>"
			, "    </table>"
			, "  </div>"
			, "</div>"
			));
	}
	@Test   public void Page_all_cols() {
		fxt	.Init_itms__pages("A1", "A2", "A3", "B1", "C1").Init_grp__pages(0, 5)
			.Test_html_all(Xoa_ctg_mgr.Tid_page, String_.Concat_lines_nl_skip_last
			( ""
			, "<div id=\"mw-pages\">"
			, "  <h2>Pages in category \"Ctg_1\"</h2>"
			, "  <p>The following 5 pages are in this category, out of 5 total.</p>"
			, "  <div lang=\"en\" dir=\"ltr\" class=\"mw-content-ltr\">"
			, "    <table style=\"width: 100%;\">"
			, "      <tr style=\"vertical-align: top;\">"
			, "        <td style=\"width: 33%;\">"
			, "          <h3>A</h3>"
			, "          <ul>"
			, "            <li><a href=\"/wiki/A1\" title=\"A1\">A1</a></li>"
			, "            <li><a href=\"/wiki/A2\" title=\"A2\">A2</a></li>"
			, "          </ul>"
			, "        </td>"
			, "        <td style=\"width: 33%;\">"
			, "          <h3>A cont.</h3>"
			, "          <ul>"
			, "            <li><a href=\"/wiki/A3\" title=\"A3\">A3</a></li>"
			, "          </ul>"
			, "          <h3>B</h3>"
			, "          <ul>"
			, "            <li><a href=\"/wiki/B1\" title=\"B1\">B1</a></li>"
			, "          </ul>"
			, "        </td>"
			, "        <td style=\"width: 33%;\">"
			, "          <h3>C</h3>"
			, "          <ul>"
			, "            <li><a href=\"/wiki/C1\" title=\"C1\">C1</a></li>"
			, "          </ul>"
			, "        </td>"
			, "      </tr>"
			, "    </table>"
			, "  </div>"
			, "</div>"
			));
	}
	@Test   public void Title__escape_quotes() {// PURPOSE: quotes in title should be escaped; DATE:2015-12-28
		fxt	.Init_itms__pages("A\"1").Init_grp__pages(0, 1)
			.Test_html_all(Xoa_ctg_mgr.Tid_page, String_.Concat_lines_nl_skip_last
			( ""
			, "<div id=\"mw-pages\">"
			, "  <h2>Pages in category \"Ctg_1\"</h2>"
			, "  <p>This category contains only the following page.</p>"
			, "  <div lang=\"en\" dir=\"ltr\" class=\"mw-content-ltr\">"
			, "    <table style=\"width: 100%;\">"
			, "      <tr style=\"vertical-align: top;\">"
			, "        <td style=\"width: 33%;\">"
			, "          <h3>A</h3>"
			, "          <ul>"
			, "            <li><a href=\"/wiki/A%221\" title=\"A&quot;1\">A&quot;1</a></li>"
			, "          </ul>"
			, "        </td>"
			, "      </tr>"
			, "    </table>"
			, "  </div>"
			, "</div>"
			));
	}
	@Test  public void Bld_rslts_lnk() {
		fxt	.Init_itms__pages("A1").Init_grp__pages(1, 1)
			.Test_bld_rslts_lnk(Bool_.Y, "Category:Ctg_1", String_.Concat_lines_nl
		( ""
		, "  (<a href=\"/wiki/Category:Ctg_1?pageuntil=A1#mw-pages\" class=\"xowa_nav\" title=\"Category:Ctg_1\">previous 0</a>)"
		, "  (<a href=\"/wiki/Category:Ctg_1?pagefrom=A1#mw-pages\" class=\"xowa_nav\" title=\"Category:Ctg_1\">next 0</a>)"
		));
	}
	@Test  public void Bld_rslts_lnk__encoded() {	// escape quotes and spaces; DATE:2016-01-11
		fxt	.Init_itms__pages("A\" b").Init_grp__pages(1, 1)
			.Test_bld_rslts_lnk(Bool_.Y, "Category:Ctg_1", String_.Concat_lines_nl
		( ""
		, "  (<a href=\"/wiki/Category:Ctg_1?pageuntil=A%22+b#mw-pages\" class=\"xowa_nav\" title=\"Category:Ctg_1\">previous 0</a>)"
		, "  (<a href=\"/wiki/Category:Ctg_1?pagefrom=A%22+b#mw-pages\" class=\"xowa_nav\" title=\"Category:Ctg_1\">next 0</a>)"
		));
	}
	@Test   public void Calc_col_len() {
		fxt.Test__calc_col_len(10, 0, 4);	// for 10 items, col 0 has 4 items
		fxt.Test__calc_col_len(10, 1, 3);	// for 10 items, col 1 has 3 items
		fxt.Test__calc_col_len(10, 2, 3);	// for 10 items, col 2 has 3 items
		fxt.Test__calc_col_len(11, 0, 4);
		fxt.Test__calc_col_len(11, 1, 4);
		fxt.Test__calc_col_len(11, 2, 3);
		fxt.Test__calc_col_len(12, 0, 4);
		fxt.Test__calc_col_len(12, 1, 4);
		fxt.Test__calc_col_len(12, 2, 4);
	}
}
class Xoh_ctg_page_fxt {
	public Xoh_ctg_page_fxt Clear() {
		if (app == null) {
			app = Xoa_app_fxt.Make__app__edit();
			wiki = Xoa_app_fxt.Make__wiki__edit(app);
			ctg_html = wiki.Html_mgr().Ns_ctg();
		}
		ctg = new Xoctg_catpage_ctg(Bry_.new_a7("Ctg_1"));
		return this;
	}	private Xoae_app app; private Xoctg_catpage_mgr ctg_html;
	public void Test__calc_col_len(int grp_len, int col_idx, int expd) {Tfds.Eq(expd, Xoctg_fmt_itm_base.Calc_col_len(grp_len, col_idx, 3));}
	public Xowe_wiki Wiki() {return wiki;} private Xowe_wiki wiki; 
	public Xoctg_catpage_ctg Ctg() {return ctg;} private Xoctg_catpage_ctg ctg;
	public void Test_bld_rslts_lnk(boolean next, String ctg_str, String expd) {			
		Xoctg_fmt_grp.Grp__max = 0;
		byte[] actl = ctg_html.Fmt(Xoa_ctg_mgr.Tid_page).Bld_bwd_fwd(wiki, Xoa_ttl.Parse(wiki, Bry_.new_a7(ctg_str)), ctg.Grp_by_tid(Xoa_ctg_mgr.Tid_page));
		Tfds.Eq_str_lines(expd, String_.new_u8(actl));
		Xoctg_fmt_grp.Grp__max = 200;
	}
	public Xoh_ctg_page_fxt Init_grp__pages(int bgn, int count) {ctg.Pages().Init(bgn, count); return this;}
	public Xoh_ctg_page_fxt Init_grp__files(int bgn, int count) {ctg.Files().Init(bgn, count); return this;}
	public Xoh_ctg_page_fxt Init_itms__pages(String... titles) {return Init_itms(Xoa_ctg_mgr.Tid_page, titles);}
	public Xoh_ctg_page_fxt Init_itms__files(String... titles) {return Init_itms(Xoa_ctg_mgr.Tid_file, titles);}
	public Xoh_ctg_page_fxt Init_itms__subcs(String... titles) {return Init_itms(Xoa_ctg_mgr.Tid_subc, titles);}
	private Xoh_ctg_page_fxt Init_itms(byte tid, String[] ttls) {
		int len = ttls.length;
		Xoctg_catpage_grp grp = ctg.Grp_by_tid(tid);
		for (int i = 0; i < len; ++i) {
			Xoa_ttl ttl = Xoa_ttl.Parse(wiki, Bry_.new_u8(ttls[i]));
			grp.Itms__add(new Xoctg_catpage_itm(i, ttl, ttl.Page_txt()));
		}
		grp.Itms__make();
		return this;
	}
	public void Test_html_page(byte tid, byte grp_char_0, String expd) {
		Xoctg_fmt_grp list_mgr = ctg_html.Fmt(tid);
		Xoctg_fmt_itm_base itm_fmt = list_mgr.Itm_fmt();
		Xoctg_catpage_grp list = ctg.Grp_by_tid(tid);
		itm_fmt.Init_from_ltr(wiki, list);
		itm_fmt.Set_ltr_and_bgn(new byte[] {grp_char_0}, 0);
		itm_fmt.Col_end_(0, 0);
		Bry_bfr bfr = wiki.Utl__bfr_mkr().Get_b512();
		itm_fmt.Bfr_arg__add(bfr);
		Tfds.Eq_str_lines(expd, bfr.To_str_and_rls());
	}
	public void Test_html_grp(byte tid, String expd) {
		Xoctg_fmt_grp list_mgr = ctg_html.Fmt(tid);
		Xoctg_fmt_ltr fmtr_grp = new Xoctg_fmt_ltr(list_mgr.Itm_fmt());
		fmtr_grp.Init_from_grp(wiki, ctg.Grp_by_tid(tid));
		Bry_bfr bfr = wiki.Utl__bfr_mkr().Get_b512();
		fmtr_grp.Bfr_arg__add(bfr);
		Tfds.Eq_str_lines(expd, bfr.To_str_and_rls());
	}
	public void Test_html_all(byte tid, String expd) {
		Bry_bfr bfr = wiki.Utl__bfr_mkr().Get_b512();
		ctg_html.Fmt(tid).Write_catpage_grp(bfr, wiki, wiki.Lang(), ctg);
		Tfds.Eq_str_lines(expd, bfr.To_str_and_rls());
	}
}
