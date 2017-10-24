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
package gplx.xowa.addons.wikis.ctgs.htmls.catpages; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.ctgs.*; import gplx.xowa.addons.wikis.ctgs.htmls.*;
import org.junit.*; import gplx.xowa.htmls.core.htmls.*; import gplx.core.intls.ucas.*;
import gplx.xowa.addons.wikis.ctgs.htmls.catpages.doms.*; import gplx.xowa.addons.wikis.ctgs.htmls.catpages.fmts.*;
public class Xoctg_catpage_mgr__basic__tst {
	@Before public void init() {fxt.Clear();} private Xoctg_catpage_mgr_fxt fxt = new Xoctg_catpage_mgr_fxt();
	@Test   public void Page_itm() {
		fxt	.Init_itms__pages("A1")
			.Test__html__page(Xoa_ctg_mgr.Tid__page, Byte_ascii.Ltr_A, "\n            <li><a href=\"/wiki/A1\" title=\"A1\">A1</a></li>");
	}
	@Test   public void Page_itm_missing() {
		fxt.Init_itms__pages("A1");
		Xoctg_catpage_itm itm = fxt.Ctg().Grp_by_tid(Xoa_ctg_mgr.Tid__page).Itms__get_at(0);
		itm.Page_ttl_(Xoa_ttl.Null);
		itm.Sortkey_handle_make(Bry_bfr_.New(), Bry_.Empty);
		fxt.Test__html__page(Xoa_ctg_mgr.Tid__page, Byte_ascii.Ltr_A, "\n            <li class=\"xowa-missing-category-entry\"><span title=\"id not found: #0 might be talk/user page\">missing page (0)</li>");
	}
	@Test   public void Visited_doesnt_work_for_space() {// PURPOSE: xowa-visited not inserted for pages with space
		byte[] page_bry = Bry_.new_a7("A 1");
		Xoa_url url = Xoa_url.New(Bry_.new_a7("en.wikipedia.org"), page_bry);
		Xoa_ttl ttl = Xoa_ttl.Parse(fxt.Wiki(), page_bry);
		fxt.Wiki().Appe().Usere().History_mgr().Add(fxt.Wiki().App(), url, ttl, page_bry);
		fxt	.Init_itms__pages("A_1")
			.Test__html__all(Xoa_ctg_mgr.Tid__page, String_.Concat_lines_nl_skip_last
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
		fxt	.Init_itms__pages("A1")
			.Test__html__all(Xoa_ctg_mgr.Tid__page, String_.Concat_lines_nl_skip_last
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
		fxt	.Init_itms__files("File:A1.png")
			.Test__html__all(Xoa_ctg_mgr.Tid__file, String_.Concat_lines_nl_skip_last
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
	}
	@Test   public void Subc_all() {
		fxt	.Init_itms__subcs("Category:Subc_1")
			.Test__html__all(Xoa_ctg_mgr.Tid__subc, String_.Concat_lines_nl_skip_last
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
		fxt.Init_itms__pages("A1", "A2", "A3", "B1", "C1");
		fxt.Init__grp_max_(6);
		fxt.Test__html__all(Xoa_ctg_mgr.Tid__page, String_.Concat_lines_nl_skip_last
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
	@Test   public void Page__numeric() {	// PURPOSE: check numeric sorting; 0, 9, 10; not 0, 10, 9; DATE:2016-10-09
		fxt.Init_itms__pages("0", "9", "10");
		fxt.Init__grp_max_(6);
		fxt.Test__html__all(Xoa_ctg_mgr.Tid__page, String_.Concat_lines_nl_skip_last
		( ""
		, "<div id=\"mw-pages\">"
		, "  <h2>Pages in category \"Ctg_1\"</h2>"
		, "  <p>The following 3 pages are in this category, out of 3 total.</p>"
		, "  <div lang=\"en\" dir=\"ltr\" class=\"mw-content-ltr\">"
		, "    <table style=\"width: 100%;\">"
		, "      <tr style=\"vertical-align: top;\">"
		, "        <td style=\"width: 33%;\">"
		, "          <h3>0-9</h3>"
		, "          <ul>"
		, "            <li><a href=\"/wiki/0\" title=\"0\">0</a></li>"
		, "          </ul>"
		, "        </td>"
		, "        <td style=\"width: 33%;\">"
		, "          <h3>0-9 cont.</h3>"
		, "          <ul>"
		, "            <li><a href=\"/wiki/9\" title=\"9\">9</a></li>"
		, "          </ul>"
		, "        </td>"
		, "        <td style=\"width: 33%;\">"
		, "          <h3>0-9 cont.</h3>"
		, "          <ul>"
		, "            <li><a href=\"/wiki/10\" title=\"10\">10</a></li>"
		, "          </ul>"
		, "        </td>"
		, "      </tr>"
		, "    </table>"
		, "  </div>"
		, "</div>"
		));
	}
	@Test   public void Title__escape_quotes() {// PURPOSE: quotes in title should be escaped; DATE:2015-12-28
		fxt	.Init_itms__pages("A\"1")
			.Test__html__all(Xoa_ctg_mgr.Tid__page, String_.Concat_lines_nl_skip_last
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
class Xoctg_catpage_mgr_fxt {
	private int grp_max;
	private Uca_ltr_extractor ltr_extractor = new Uca_ltr_extractor(true);
	public Xoctg_catpage_mgr_fxt Clear() {
		if (app == null) {
			app = Xoa_app_fxt.Make__app__edit();
			wiki = Xoa_app_fxt.Make__wiki__edit(app);
			ctg_html = wiki.Ctg__catpage_mgr();
		}
		ctg = new Xoctg_catpage_ctg(1, Bry_.new_a7("Ctg_1"));
		grp_max = 3;	// default to 3 paer page
		return this;
	}	private Xoae_app app; private Xoctg_catpage_mgr ctg_html;
	public void Test__calc_col_len(int grp_len, int col_idx, int expd) {Tfds.Eq(expd, Xoctg_fmt_itm_base.Calc_col_len(grp_len, col_idx, 3));}
	public Xowe_wiki Wiki() {return wiki;} private Xowe_wiki wiki; 
	public Xoctg_catpage_ctg Ctg() {return ctg;} private Xoctg_catpage_ctg ctg;
	public void Init__grp_max_(int v) {this.grp_max = v;}
	public void Init__prev_hide_y_(byte tid) {ctg.Grp_by_tid(tid).Prev_disable_(true);}
	public void Init__next_sortkey_(byte tid, String v) {ctg.Grp_by_tid(tid).Next_sortkey_(Bry_.new_u8(v));}
	public void Test__navlink(boolean next, String ctg_str, String expd) {			
		byte[] actl = ctg_html.Fmt(Xoa_ctg_mgr.Tid__page).Bld_bwd_fwd(wiki, Xoa_ttl.Parse(wiki, Bry_.new_a7(ctg_str)), ctg.Grp_by_tid(Xoa_ctg_mgr.Tid__page), grp_max);
		Tfds.Eq_str_lines(expd, String_.new_u8(actl));
	}
	public Xoctg_catpage_mgr_fxt Init_itms__pages(String... titles) {return Init_itms(Xoa_ctg_mgr.Tid__page, titles);}
	public Xoctg_catpage_mgr_fxt Init_itms__files(String... titles) {return Init_itms(Xoa_ctg_mgr.Tid__file, titles);}
	public Xoctg_catpage_mgr_fxt Init_itms__subcs(String... titles) {return Init_itms(Xoa_ctg_mgr.Tid__subc, titles);}
	private Xoctg_catpage_mgr_fxt Init_itms(byte tid, String[] ttls) {
		int len = ttls.length;
		Xoctg_catpage_tmp tmp = new Xoctg_catpage_tmp();
		Xoctg_catpage_grp grp = ctg.Grp_by_tid(tid);
		grp.Count_all_(len);
		for (int i = 0; i < len; ++i) {
			byte[] page_ttl_bry = Bry_.new_u8(ttls[i]);
			Xoa_ttl ttl = Xoa_ttl.Parse(wiki, page_ttl_bry);
			Xoctg_catpage_itm itm = Xoctg_catpage_itm.New_by_ttl(tid, i, ttl);
			tmp.Add(itm);
		}
		tmp.Make_by_grp(grp);
		return this;
	}
	public void Test__html__page(byte tid, byte grp_char_0, String expd) {
		Xoctg_fmt_grp list_mgr = ctg_html.Fmt(tid);
		Xoctg_fmt_itm_base itm_fmt = list_mgr.Itm_fmt();
		Xoctg_catpage_grp list = ctg.Grp_by_tid(tid);
		itm_fmt.Init_from_ltr(wiki, list, ltr_extractor);
		itm_fmt.Set_ltr_and_bgn(new byte[] {grp_char_0}, 0);
		itm_fmt.Col_end_(0, 0);
		Bry_bfr bfr = wiki.Utl__bfr_mkr().Get_b512();
		itm_fmt.Bfr_arg__add(bfr);
		Tfds.Eq_str_lines(expd, bfr.To_str_and_rls());
	}
	public void Test__html_grp(byte tid, String expd) {
		Xoctg_fmt_grp list_mgr = ctg_html.Fmt(tid);
		Xoctg_fmt_ltr fmtr_grp = new Xoctg_fmt_ltr(list_mgr.Itm_fmt());
		fmtr_grp.Init_from_grp(wiki, ctg.Grp_by_tid(tid), ltr_extractor);
		Bry_bfr bfr = wiki.Utl__bfr_mkr().Get_b512();
		fmtr_grp.Bfr_arg__add(bfr);
		Tfds.Eq_str_lines(expd, bfr.To_str_and_rls());
	}
	public void Test__html__all(byte tid, String expd) {
		Bry_bfr bfr = wiki.Utl__bfr_mkr().Get_b512();
		ctg_html.Fmt(tid).Write_catpage_grp(bfr, wiki, wiki.Lang(), ltr_extractor, ctg, grp_max);
		Tfds.Eq_str_lines(expd, bfr.To_str_and_rls());
	}
}
