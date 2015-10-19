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
package gplx.xowa.wikis.ctgs; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*;
import org.junit.*;
public class Xoctg_html_mgr_tst {
	@Before public void init() {fxt.Clear();} private Xoh_ctg_page_fxt fxt = new Xoh_ctg_page_fxt();
	@Test   public void Page_itm() {
		fxt	.Init_itm_page("A1")
			.Test_html_page(Xoa_ctg_mgr.Tid_page, Byte_ascii.Ltr_A, "\n            <li><a href=\"/wiki/A1\" title=\"A1\">A1</a></li>")
			;
	}
	@Test   public void Page_itm_missing() {
		fxt	.Init_itm_page("A1");
		fxt	.Ctg().Grp_by_tid(Xoa_ctg_mgr.Tid_page).Itms()[0].Id_missing_(true);
		fxt	.Test_html_page(Xoa_ctg_mgr.Tid_page, Byte_ascii.Ltr_A, "\n            <li class=\"xowa-missing-category-entry\"><span title=\"id not found: #0 might be talk/user page\">A1 (missing)</li>");
	}
	@Test   public void Visited_doesnt_work_for_space() {// PURPOSE: xowa-visited not inserted for pages with space
		byte[] page_bry = Bry_.new_a7("A 1");
		Xoa_url url = Xoa_url.new_(Bry_.new_a7("en.wikipedia.org"), page_bry);
		Xoa_ttl ttl = Xoa_ttl.parse(fxt.Wiki(), page_bry);
		fxt.Wiki().Appe().Usere().History_mgr().Add(url, ttl, page_bry);
		fxt	.Init_itm_page("A_1").Init_ctg_name_("Ctg_1").Init_ctg_pages_(0, 1)
			.Test_html_all(Xoa_ctg_mgr.Tid_page, String_.Concat_lines_nl_skip_last
			(	""
			,	"<div id=\"mw-pages\">"
			,	"  <h2>Pages in category \"Ctg_1\"</h2>"
			,	"  <p>The following page is in this category, out of 0 total.</p>"
			,	"  <div lang=\"en\" dir=\"ltr\" class=\"mw-content-ltr\">"
			,	"    <table style=\"width: 100%;\">"
			,	"      <tr style=\"vertical-align: top;\">"
			,	"        <td style=\"width: 33%;\">"
			,	"          <h3>A</h3>"
			,	"          <ul>"
			,	"            <li><a href=\"/wiki/A_1\" class=\"xowa-visited\" title=\"A 1\">A 1</a></li>"
			,	"          </ul>"
			,	"        </td>"
			,	"      </tr>"
			,	"    </table>"
			,	"  </div>"
			,	"</div>"
			));
	}
	@Test   public void Page_all() {
		fxt	.Init_itm_page("A1").Init_ctg_name_("Ctg_1").Init_ctg_pages_(0, 1)
			.Test_html_all(Xoa_ctg_mgr.Tid_page, String_.Concat_lines_nl_skip_last
			(	""
			,	"<div id=\"mw-pages\">"
			,	"  <h2>Pages in category \"Ctg_1\"</h2>"
			,	"  <p>The following page is in this category, out of 0 total.</p>"
			,	"  <div lang=\"en\" dir=\"ltr\" class=\"mw-content-ltr\">"
			,	"    <table style=\"width: 100%;\">"
			,	"      <tr style=\"vertical-align: top;\">"
			,	"        <td style=\"width: 33%;\">"
			,	"          <h3>A</h3>"
			,	"          <ul>"
			,	"            <li><a href=\"/wiki/A1\" title=\"A1\">A1</a></li>"
			,	"          </ul>"
			,	"        </td>"
			,	"      </tr>"
			,	"    </table>"
			,	"  </div>"
			,	"</div>"
			));
	}
	@Test   public void File_all() {
		fxt	.Init_itm_file("File:A1.png").Init_ctg_name_("Ctg_1").Init_ctg_files_(0, 1)
			.Test_html_all(Xoa_ctg_mgr.Tid_file, String_.Concat_lines_nl_skip_last
			(	""
			,	"<div id=\"mw-category-media\">"
			,	"  <h2>Media in category \"Ctg_1\"</h2>"
			,	"  <p>The following file is in this category, out of 0 total.</p>"
			,	"  <div lang=\"en\" dir=\"ltr\" class=\"mw-content-ltr\">"
			,	"    <table style=\"width: 100%;\">"
			,	"      <tr style=\"vertical-align: top;\">"
			,	"        <td style=\"width: 33%;\">"
			,	"          <h3>A</h3>"
			,	"          <ul>"
			,	"            <li><a href=\"/wiki/File:A1.png\" title=\"File:A1.png\">File:A1.png</a></li>"
			,	"          </ul>"
			,	"        </td>"
			,	"      </tr>"
			,	"    </table>"
			,	"  </div>"
			,	"</div>"
			));
//				,	"  <ul id=\"xowa_gallery_ul_0\" class=\"gallery\">"
//				,	"  <li class=\"gallerybox\" style=\"width:155px;\">"
//				,	"    <div style=\"width:155px;\">"
//				,	"      <div class=\"thumb\" style=\"width:155px;\">"
//				,	"        <div id=\"xowa_gallery_div3_-1\" style=\"margin:15px auto;\">"
//				,	"          <a href=\"/wiki/File:A1.png\" class=\"image\">"
//				,	"            <img id=\"xowa_file_img_-1\" alt=\"A1.png\" src=\"\" width=\"-1\" height=\"-1\" />"
//				,	"          </a>"
//				,	"        </div>"
//				,	"      </div>"
//				,	"      <div class=\"gallerytext\">A1.png"
//				,	"      </div>"
//				,	"    </div>"
//				,	"  </li>"
//				,	""
//				,	"  </ul>"
	}
	@Test   public void Subc_all() {
		fxt	.Init_itm_ctg("Category:Subc_1").Init_ctg_name_("Ctg_1").Init_ctg_files_(0, 1)
			.Test_html_all(Xoa_ctg_mgr.Tid_subc, String_.Concat_lines_nl_skip_last
			(	""
			,	"<div id=\"mw-subcategories\">"
			,	"  <h2>Subcategories</h2>"
			,	"  <p>This category has the following subcategory, out of 0 total.</p>"
			,	"  <div lang=\"en\" dir=\"ltr\" class=\"mw-content-ltr\">"
			,	"    <table style=\"width: 100%;\">"
			,	"      <tr style=\"vertical-align: top;\">"
			,	"        <td style=\"width: 33%;\">"
			,	"          <h3>S</h3>"
			,	"          <ul>"
			,	"            <li>"
			,	"              <div class=\"CategoryTreeSection\">"
			,	"                <div class=\"CategoryTreeItem\">"
			,	"                  <span class=\"CategoryTreeBullet\">"
			,	"                    <span class=\"CategoryTreeToggle\" style=\"display: none;\" data-ct-title=\"Subc_1\" title=\"Subc 1\" data-ct-state=\"collapsed\">"
			,	"                    </span> "
			,	"                  </span>"
			,	"                  <a href=\"/wiki/Category:Subc_1\" class=\"CategoryTreeLabel  CategoryTreeLabelNs14 CategoryTreeLabelCategory\">Subc 1"
			,	"                  </a>"
			,	"                  <span title=\"contains 0 subcategories, 0 pages, and 0 files\" dir=\"ltr\">"
			,	"                  </span>"
			,	"                </div>"
			,	"                <div class=\"CategoryTreeChildren\" style=\"display:none\"></div>"
			,	"              </div>"
			,	"            </li>"
			,	"          </ul>"
			,	"        </td>"
			,	"      </tr>"
			,	"    </table>"
			,	"  </div>"
			,	"</div>"
			));
	}
	@Test   public void Page_all_cols() {
		fxt	.Init_itm_page("A1", "A2", "A3", "B1", "C1").Init_ctg_name_("Ctg_1").Init_ctg_pages_(0, 5)
			.Test_html_all(Xoa_ctg_mgr.Tid_page, String_.Concat_lines_nl_skip_last
			(	""
			,	"<div id=\"mw-pages\">"
			,	"  <h2>Pages in category \"Ctg_1\"</h2>"
			,	"  <p>The following 5 pages are in this category, out of 0 total.</p>"
			,	"  <div lang=\"en\" dir=\"ltr\" class=\"mw-content-ltr\">"
			,	"    <table style=\"width: 100%;\">"
			,	"      <tr style=\"vertical-align: top;\">"
			,	"        <td style=\"width: 33%;\">"
			,	"          <h3>A</h3>"
			,	"          <ul>"
			,	"            <li><a href=\"/wiki/A1\" title=\"A1\">A1</a></li>"
			,	"            <li><a href=\"/wiki/A2\" title=\"A2\">A2</a></li>"
			,	"          </ul>"
			,	"        </td>"
			,	"        <td style=\"width: 33%;\">"
			,	"          <h3>A cont.</h3>"
			,	"          <ul>"
			,	"            <li><a href=\"/wiki/A3\" title=\"A3\">A3</a></li>"
			,	"          </ul>"
			,	"          <h3>B</h3>"
			,	"          <ul>"
			,	"            <li><a href=\"/wiki/B1\" title=\"B1\">B1</a></li>"
			,	"          </ul>"
			,	"        </td>"
			,	"        <td style=\"width: 33%;\">"
			,	"          <h3>C</h3>"
			,	"          <ul>"
			,	"            <li><a href=\"/wiki/C1\" title=\"C1\">C1</a></li>"
			,	"          </ul>"
			,	"        </td>"
			,	"      </tr>"
			,	"    </table>"
			,	"  </div>"
			,	"</div>"
			));
	}
	@Test  public void Bld_rslts_lnk() {
		fxt.Init_itm_page("A1").Init_ctg_name_("Ctg_1").Init_ctg_pages_(1, 1)
			.Test_bld_rslts_lnk(Bool_.Y, "Category:Ctg_1", String_.Concat_lines_nl
		(	""
		,	"  (<a href=\"/wiki/Category:Ctg_1?pageuntil=A1#mw-pages\" class=\"xowa_nav\" title=\"Category:Ctg_1\">previous 0</a>)"
		,	"  (<a href=\"/wiki/Category:Ctg_1?pagefrom=A1#mw-pages\" class=\"xowa_nav\" title=\"Category:Ctg_1\">next 0</a>)"
		));
	}
	@Test   public void Calc_col_max() {
		Tst_calc_col_max(3, 10, 0, 4);
		Tst_calc_col_max(3, 10, 1, 3);
		Tst_calc_col_max(3, 10, 2, 3);
		Tst_calc_col_max(3, 11, 0, 4);
		Tst_calc_col_max(3, 11, 1, 4);
		Tst_calc_col_max(3, 11, 2, 3);
		Tst_calc_col_max(3, 12, 0, 4);
		Tst_calc_col_max(3, 12, 1, 4);
		Tst_calc_col_max(3, 12, 2, 4);
	}	public void Tst_calc_col_max(int cols_total, int len, int col_idx, int expd) {Tfds.Eq(expd, Xoctg_fmtr_grp.Calc_col_max(cols_total, len, col_idx));}
}
class Xoh_ctg_page_fxt {
	public Xoh_ctg_page_fxt Clear() {
		if (app == null) {
			app = Xoa_app_fxt.app_();
			wiki = Xoa_app_fxt.wiki_tst_(app);
			ctg_html = wiki.Html_mgr().Ns_ctg();
			ctg = new Xoctg_view_ctg();
		}
		return this;
	}	private Xoae_app app; private Xoctg_html_mgr ctg_html;
	public Xowe_wiki Wiki() {return wiki;} private Xowe_wiki wiki; 
	public Xoctg_view_ctg Ctg() {return ctg;} private Xoctg_view_ctg ctg;
	public void Test_bld_rslts_lnk(boolean next, String ctg_str, String expd) {			
		byte[] actl = ctg_html.Fmtr(Xoa_ctg_mgr.Tid_page).Grp_max_(0).Bld_bwd_fwd(wiki, Xoa_ttl.parse(wiki, Bry_.new_a7(ctg_str)), ctg.Grp_by_tid(Xoa_ctg_mgr.Tid_page));
		Tfds.Eq_str_lines(expd, String_.new_u8(actl));
	}
	public Xoh_ctg_page_fxt Init_ctg_name_(String v) {ctg.Name_(Bry_.new_u8(v)); return this;}
	public Xoh_ctg_page_fxt Init_ctg_pages_(int bgn, int count) {ctg.Pages().Bgn_(bgn).All_(count).End_(count); return this;}
	public Xoh_ctg_page_fxt Init_ctg_files_(int bgn, int count) {ctg.Files().Bgn_(bgn).All_(count).End_(count); return this;}
	public Xoh_ctg_page_fxt Init_itm_page(String... titles) {ctg.Pages().Itms_(itms_(titles)).End_(titles.length); return this;}
	public Xoh_ctg_page_fxt Init_itm_file(String... titles) {ctg.Files().Itms_(itms_(titles)).End_(titles.length); return this;}
	public Xoh_ctg_page_fxt Init_itm_ctg(String... titles) {ctg.Subcs().Itms_(itms_(titles)).End_(titles.length); return this;}
	Xoctg_view_itm[] itms_(String... titles) {
		int len = titles.length;
		Xoctg_view_itm[] rv = new Xoctg_view_itm[len];
		for (int i = 0; i < len; i++) {
			String title = titles[i];
			byte[] title_bry = Bry_.new_u8(title);
			Xoa_ttl ttl = Xoa_ttl.parse(wiki, title_bry);
			rv[i] = new Xoctg_view_itm().Ttl_(ttl).Sortkey_(ttl.Page_txt());
		}
		return rv;
	}
	public void Test_html_page(byte tid, byte grp_char_0, String expd) {
		Xoctg_fmtr_all list_mgr = ctg_html.Fmtr(tid);
		Xoctg_fmtr_itm fmtr_itm = list_mgr.Fmtr_itm();
		Xoctg_view_grp list = ctg.Grp_by_tid(tid);
		fmtr_itm.Init_from_all(wiki, wiki.Lang(), ctg, list_mgr, list, list.Len());
		fmtr_itm.Init_from_grp(new byte[] {grp_char_0}, 0);
		fmtr_itm.Col_idx_(0, 0);
		Bry_bfr bfr = wiki.Utl__bfr_mkr().Get_b512();
		fmtr_itm.Fmt__do(bfr);
		Tfds.Eq_str_lines(expd, bfr.To_str_and_rls());
	}
	public void Test_html_grp(byte tid, String expd) {
		Xoctg_fmtr_all list_mgr = ctg_html.Fmtr(tid);
		Xoctg_fmtr_grp fmtr_grp = ctg_html.Fmtr_grp();
		fmtr_grp.Init_from_all(wiki, wiki.Lang(), ctg, list_mgr, ctg.Grp_by_tid(tid));
		Bry_bfr bfr = wiki.Utl__bfr_mkr().Get_b512();
		fmtr_grp.Fmt__do(bfr);
		Tfds.Eq_str_lines(expd, bfr.To_str_and_rls());
	}
	public void Test_html_all(byte tid, String expd) {
		Bry_bfr bfr = wiki.Utl__bfr_mkr().Get_b512();
		ctg_html.Bld_all(bfr, wiki, wiki.Lang(), ctg, tid);
		Tfds.Eq_str_lines(expd, bfr.To_str_and_rls());
	}
}
