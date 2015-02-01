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
package gplx.xowa.xtns.dynamicPageList; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import org.junit.*; import gplx.core.strings.*;
public class Dpl_xnde_tst {
	private Dpl_xnde_fxt fxt = new Dpl_xnde_fxt();
	@Before public void init() {fxt.Clear();}
	@Test   public void Ctg() {
		fxt.Ctg_create("Ctg_0", "B", "A");
		fxt.Ul_pages("<DynamicPageList>category=Ctg_0</DynamicPageList>", fxt.Ul(Itm_html_null, "B", "A"));
	}
	@Test   public void Ctg_multiple() {
		fxt.Ctg_create_pages("Ctg_0", Dpl_page_mok.new_(101, "A"), Dpl_page_mok.new_(102, "B"));
		fxt.Ctg_create_pages("Ctg_1", Dpl_page_mok.new_(101, "A"));
		fxt.Ul_pages(String_.Concat_lines_nl
		(	"<DynamicPageList>"
		,	"category=Ctg_0"
		,	"category=Ctg_1"
		,	"</DynamicPageList>"
		), fxt.Ul(Itm_html_null, "A"));
	}
	@Test   public void Ctg_multiple_none() {	// PURPOSE: page must be in both categories
		fxt.Ctg_create("Ctg_0", "A");
		fxt.Ctg_create("Ctg_1", "B");
		fxt.Ul_pages(String_.Concat_lines_nl
		(	"<DynamicPageList>"
		,	"category=Ctg_0"
		,	"category=Ctg_1"
		,	"</DynamicPageList>"
		),  "No pages meet these criteria.");
	}
	@Test   public void Notcategory() {
		fxt.Ctg_create_pages("Ctg_0", Dpl_page_mok.new_(101, "A"), Dpl_page_mok.new_(102, "B"));
		fxt.Ctg_create_pages("Ctg_1", Dpl_page_mok.new_(101, "A"));
		fxt.Ul_pages(String_.Concat_lines_nl
			(	"<DynamicPageList>"
			,	"category=Ctg_0"
			,	"notcategory=Ctg_1"
			,	"</DynamicPageList>"
			), fxt.Ul(Itm_html_null, "B"));
	}
	@Test  public void Ctg_ascending() {
		fxt.Ctg_create("Ctg_0", "B", "A");
		fxt.Ul_pages(String_.Concat_lines_nl_skip_last
		(	"<DynamicPageList>"
		,	"category=Ctg_0"
		,	"order=ascending"
		,	"</DynamicPageList>"), fxt.Ul(Itm_html_null, "A", "B"));
	}
	@Test  public void Ctg_ws() {
		fxt.Ctg_create("Ctg_0", "B", "A");
		fxt.Ul_pages(String_.Concat_lines_nl_skip_last
		(	"<DynamicPageList>"
		,	"  category  =  Ctg_0  "
		,	"  order  =  ascending  "
		,	"</DynamicPageList>"), fxt.Ul(Itm_html_null, "A", "B"));
	}
	@Test  public void Ctg_descending() {
		fxt.Ctg_create("Ctg_0", "A", "B");
		fxt.Ul_pages(String_.Concat_lines_nl_skip_last
		(	"<DynamicPageList>"
		,	"category=Ctg_0"
		,	"order=descending"
		,	"</DynamicPageList>"), fxt.Ul(Itm_html_null, "B", "A"));
	}
	@Test  public void Nofollow() {
		fxt.Ctg_create("Ctg_0", "A", "B");
		fxt.Ul_pages(String_.Concat_lines_nl_skip_last
		(	"<DynamicPageList>"
		,	"category=Ctg_0"
		,	"nofollow=true"
		,	"</DynamicPageList>"), fxt.Ul(" rel=\"nofollow\"", "A", "B"));
	}
	@Test  public void Invalid_key() {
		fxt.Ctg_create("Ctg_0", "A", "B");
		fxt.Warns("unknown_key: page=Test page key=invalid_key");
		fxt.Ul_pages(String_.Concat_lines_nl_skip_last
		(	"<DynamicPageList>"
		,	"invalid_key=invalid_val"
		,	"category=Ctg_0"
		,	"</DynamicPageList>"), fxt.Ul(Itm_html_null, "A", "B"));
	}
	@Test  public void No_results() {
		fxt.Ul_pages(String_.Concat_lines_nl_skip_last
		(	"<DynamicPageList>"
		,	"category=Ctg_0"
		,	"</DynamicPageList>"), "No pages meet these criteria.");
	}
	@Test  public void Suppress_errors() {
		fxt.Ul_pages(String_.Concat_lines_nl_skip_last
		(	"<DynamicPageList>"
		,	"category=Ctg_0"
		,	"suppresserrors=true"
		,	"</DynamicPageList>"), "");
	}
	@Test  public void Count() {
		fxt.Ctg_create("Ctg_0", "A", "B", "C");
		fxt.Ul_pages(String_.Concat_lines_nl_skip_last
		(	"<DynamicPageList>"
		,	"category=Ctg_0"
		,	"count=2"
		,	"</DynamicPageList>"), fxt.Ul(Itm_html_null, "A", "B"));
	}
	@Test  public void Offset() {
		fxt.Ctg_create("Ctg_0", "A", "B", "C");
		fxt.Ul_pages(String_.Concat_lines_nl_skip_last
		(	"<DynamicPageList>"
		,	"category=Ctg_0"
		,	"offset=2"
		,	"count=2"
		,	"</DynamicPageList>"), fxt.Ul(Itm_html_null, "C"));
	}
	@Test  public void Ns() {
		fxt.Ctg_create("Ctg_0", "Talk:A", "B");
		fxt.Ul_pages(String_.Concat_lines_nl_skip_last
		(	"<DynamicPageList>"
		,	"category=Ctg_0"
		,	"namespace=Talk"
		,	"</DynamicPageList>"), fxt.Ul(Itm_html_null, "A"));
	}
	@Test  public void Showns() {
		fxt.Ctg_create("Ctg_0", "Talk:A");
		fxt.Ul_pages(String_.Concat_lines_nl_skip_last
		(	"<DynamicPageList>"
		,	"category=Ctg_0"
		,	"shownamespace=true"
		,	"</DynamicPageList>"), fxt.Ul(Itm_html_null, "Talk:A"));
		fxt.Ul_pages(String_.Concat_lines_nl_skip_last
		(	"<DynamicPageList>"
		,	"category=Ctg_0"
		,	"shownamespace=false"
		,	"</DynamicPageList>"), fxt.Ul(Itm_html_null, "A"));
	}
	@Test  public void Comment() {	// PURPOSE: comment should be ignored; en.n:Portal:Federally_Administered_Tribal_Areas; DATE:2014-01-18
		fxt.Ctg_create("Ctg_0", "B", "A");
		fxt.Ul_pages(String_.Concat_lines_nl
		(	"<DynamicPageList>"
		,	"category=Ctg_0"
		,	"<!--category=Ctg_0-->"
		,	"</DynamicPageList>"
		), fxt.Ul(Itm_html_null, "B", "A"));
	}
	@Test  public void Error_skip_line() {	// PURPOSE: error should skip rest of line; was failing with array out of bounds; en.n:Portal:Austria/Wikipedia; DATE:2014-01-18
		fxt.Warns("unknown_key: page=Test page key=Ctg_0 order");	// ignore warning message
		fxt.Ul_pages("<DynamicPageList> category=Ctg_0 order=descending</DynamicPageList>", "No pages meet these criteria.");
	}
	@Test  public void Atr_has_template() {	// PURPOSE: attribute also has template; DATE:2014-01-31
		fxt.Ctg_create("Test_page", "B", "A");
		fxt.Ul_pages(String_.Concat_lines_nl
		(	"<DynamicPageList>"
		,	"category={{PAGENAME}}"
		,	"</DynamicPageList>"
		), fxt.Ul(Itm_html_null, "B", "A"));
	}
	@Test  public void Err_page_ns_doesnt_exist() {// PURPOSE: check that <dpl> is not enabled if wiki does not have Page / Index ns; PAGE:fr.w:Wikipedia:Le_Bistro/novembre_2006 DATE:2014-11-28
		fxt.Wiki().Ns_mgr_(Xow_ns_mgr_.default_(gplx.xowa.langs.cases.Xol_case_mgr_.Ascii()));
		fxt.Wiki().Cfg_parser().Xtns().Itm_pages().Reset();	// must reset to clear cached valid ns_page from previous tests
		fxt.Fxt().Test_parse_page_wiki_str("<dynamicpagelist>category=a</dynamicpagelist>", "No pages meet these criteria.");
		fxt.Wiki().Cfg_parser().Xtns().Itm_pages().Reset();	// must reset to clear cached invalid ns_page for next tests
	}
	private static final String Itm_html_null = null;
}
class Dpl_page_mok {
	public Dpl_page_mok(int id, String ttl) {this.id = id; this.ttl = ttl;}
	public int Id() {return id;} private int id;
	public String Ttl() {return ttl;} private String ttl;
	public static Dpl_page_mok new_(int id, String ttl) {return new Dpl_page_mok(id, ttl);}
}
class Dpl_xnde_fxt {	
	private Xop_fxt fxt = new Xop_fxt();
	private int next_id;
	public void Clear() {
		next_id = 100;
		fxt.Reset();
		warns = String_.Ary_empty;
		fxt.App().Usr_dlg().Ui_wkr().Clear();
		fxt.Wiki().Hive_mgr().Clear();
		fxt.Wiki().Db_mgr().Save_mgr().Clear();	// NOTE: must clear to reset next_id to 0; Ctg_create assumes clean slate from 0
		fxt.Wiki().Xtn_mgr().Xtn_proofread().Enabled_y_();
		fxt.Wiki().Db_mgr().Load_mgr().Clear(); // must clear; otherwise fails b/c files get deleted, but wiki.data_mgr caches the Xowd_regy_mgr (the .reg file) in memory;
		fxt.Wiki().Ns_mgr().Add_new(Xowc_xtn_pages.Ns_page_id_default, "Page").Add_new(Xowc_xtn_pages.Ns_index_id_default, "Index").Init();
		Io_mgr._.InitEngine_mem();
	}
	public Xow_wiki Wiki() {return fxt.Wiki();}
	public Xop_fxt Fxt() {return fxt;}
	public void Warns(String... v) {warns = v;} private String[] warns;
	public void Page_create(String page) {fxt.Init_page_create(page);}
	public void Ctg_create(String ctg, String... ttls) {
		int len = ttls.length;
		Dpl_page_mok[] ary = new Dpl_page_mok[len];
		for (int i = 0; i < len; i++)
			ary[i] = Dpl_page_mok.new_(++next_id, ttls[i]);
		Ctg_create_pages(ctg, ary);
	}
	public void Ctg_create_pages(String ctg, Dpl_page_mok... pages) {
		int pages_len = pages.length;
		int[] page_ids = new int[pages_len];
		for (int i = 0; i < pages_len; i++) {
			Dpl_page_mok page = pages[i];
			int id = page.Id();
			String ttl = page.Ttl();
			Xoa_ttl page_ttl = Xoa_ttl.parse_(fxt.Wiki(), Bry_.new_utf8_(ttl));
			Xoa_page page_obj = fxt.Wiki().Data_mgr().Get_page(page_ttl, false);
			if (page_obj.Missing()) {
				fxt.Init_page_create(ttl);
				fxt.Init_id_create (id, 0, 0, false, 5, Xow_ns_.Id_main, ttl);
			}
			page_ids[i] = id;
		}
		fxt.Init_ctg_create(ctg, page_ids);
	}
	public String Ul(String itm_html, String... pages) {
		bfr.Add("<ul>").Add_char_nl();
		int pages_len = pages.length;
		for (int i = 0; i < pages_len; i++) {
			String page = pages[i];
			bfr.Add("  <li><a href=\"/wiki/").Add(page).Add("\" title=\"").Add(page).Add("\"");
			if (itm_html != null) bfr.Add(itm_html);
			bfr.Add(">").Add(page).Add("</a></li>").Add_char_nl();
		}
		bfr.Add("</ul>").Add_char_nl();
		return bfr.Xto_str_and_clear();
	}
	public void Ul_pages(String raw, String expd) {
		fxt.Test_parse_page_wiki_str(raw, expd);
		fxt.tst_Warn(warns);
	}	String_bldr bfr = String_bldr_.new_();
}
