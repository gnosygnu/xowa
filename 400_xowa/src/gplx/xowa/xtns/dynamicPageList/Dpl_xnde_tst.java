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
package gplx.xowa.xtns.dynamicPageList; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import org.junit.*; import gplx.core.strings.*; import gplx.xowa.apps.cfgs.*; import gplx.xowa.wikis.nss.*; import gplx.langs.htmls.*;
import gplx.xowa.addons.wikis.ctgs.htmls.catpages.*; import gplx.xowa.addons.wikis.ctgs.htmls.catpages.doms.*;
public class Dpl_xnde_tst {
	private final    Dpl_xnde_fxt fxt = new Dpl_xnde_fxt();
	@Before public void init() {fxt.Clear();}
	@Test   public void Ctg() {
		fxt.Init__create_ctg("Ctg_0", "B", "A");
		fxt.Exec__parse("<DynamicPageList>category=Ctg_0</DynamicPageList>");
		fxt.Test__html(fxt.Make__html__itms__null("B", "A"));
	}
	@Test   public void Ctg_multiple() {
		fxt.Init__create_ctg_pages("Ctg_0", Dpl_page_mok.new_(101, "A"), Dpl_page_mok.new_(102, "B"));
		fxt.Init__create_ctg_pages("Ctg_1", Dpl_page_mok.new_(101, "A"));
		fxt.Exec__parse
		( "<DynamicPageList>"
		, "category=Ctg_0"
		, "category=Ctg_1"
		, "</DynamicPageList>"
		);
		fxt.Test__html(fxt.Make__html__itms__null("A"));
	}
	@Test   public void Ctg_multiple_none() {	// PURPOSE: page must be in both categories
		fxt.Init__create_ctg("Ctg_0", "A");
		fxt.Init__create_ctg("Ctg_1", "B");
		fxt.Exec__parse
		( "<DynamicPageList>"
		, "category=Ctg_0"
		, "category=Ctg_1"
		, "</DynamicPageList>"
		);
		fxt.Test__html("No pages meet these criteria.");
	}
	@Test   public void Ctg_multiple_ignore_invalid() {	// PURPOSE: ignore invalid category titles; PAGE:en.n:Category:Egypt DATE:2016-10-18
		fxt.Init__create_ctg_pages("Ctg_0", Dpl_page_mok.new_(101, "A"));
		fxt.Exec__parse
		( "<DynamicPageList>"
		, "category=Ctg_0"
		, "category={{{2}}}"	// ignore invalid titles
		, "</DynamicPageList>"
		);
		fxt.Test__html(fxt.Make__html__itms__null("A"));	// should not return nothing
	}
	@Test   public void Notcategory() {
		fxt.Init__create_ctg_pages("Ctg_0", Dpl_page_mok.new_(101, "A"), Dpl_page_mok.new_(102, "B"));
		fxt.Init__create_ctg_pages("Ctg_1", Dpl_page_mok.new_(101, "A"));
		fxt.Exec__parse
		( "<DynamicPageList>"
		, "category=Ctg_0"
		, "notcategory=Ctg_1"
		, "</DynamicPageList>"
		);
		fxt.Test__html(fxt.Make__html__itms__null("B"));
	}
	@Test  public void Ctg_ascending() {
		fxt.Init__create_ctg("Ctg_0", "B", "A");
		fxt.Exec__parse
		( "<DynamicPageList>"
		, "category=Ctg_0"
		, "order=ascending"
		, "</DynamicPageList>"
		);
		fxt.Test__html(fxt.Make__html__itms__null("A", "B"));
	}
	@Test  public void Ctg_ws() {
		fxt.Init__create_ctg("Ctg_0", "B", "A");
		fxt.Exec__parse
		( "<DynamicPageList>"
		, "  category  =  Ctg_0  "
		, "  order  =  ascending  "
		, "</DynamicPageList>"
		);
		fxt.Test__html(fxt.Make__html__itms__null("A", "B"));
	}
	@Test  public void Ctg_descending() {
		fxt.Init__create_ctg("Ctg_0", "A", "B");
		fxt.Exec__parse
		( "<DynamicPageList>"
		, "category=Ctg_0"
		, "order=descending"
		, "</DynamicPageList>"
		);
		fxt.Test__html(fxt.Make__html__itms__null("B", "A"));
	}
	@Test  public void Nofollow() {
		fxt.Init__create_ctg("Ctg_0", "A", "B");
		fxt.Exec__parse
		( "<DynamicPageList>"
		, "category=Ctg_0"
		, "nofollow=true"
		, "</DynamicPageList>"
		);
		fxt.Test__html(fxt.Make__html(" rel=\"nofollow\"", "A", "B"));
	}
	@Test  public void Invalid_key() {
		fxt.Init__create_ctg("Ctg_0", "A", "B");
		fxt.Init__warns("dynamic_page_list:unknown_key: page=Test page key=invalid_key");
		fxt.Exec__parse
		( "<DynamicPageList>"
		, "invalid_key=invalid_val"
		, "category=Ctg_0"
		, "</DynamicPageList>"
		);
		fxt.Test__html(fxt.Make__html__itms__null("A", "B"));
	}
	@Test  public void No_results() {
		fxt.Exec__parse
		( "<DynamicPageList>"
		, "category=Ctg_0"
		, "</DynamicPageList>"
		);
		fxt.Test__html("No pages meet these criteria.");
	}
	@Test  public void Suppress_errors() {
		fxt.Exec__parse
		( "<DynamicPageList>"
		, "category=Ctg_0"
		, "suppresserrors=true"
		, "</DynamicPageList>"
		);
		fxt.Test__html("");
	}
	@Test  public void Count() {
		fxt.Init__create_ctg("Ctg_0", "A", "B", "C");
		fxt.Exec__parse
		( "<DynamicPageList>"
		, "category=Ctg_0"
		, "count=2"
		, "</DynamicPageList>"
		);
		fxt.Test__html(fxt.Make__html__itms__null("A", "B"));
	}
	@Test  public void Offset() {
		fxt.Init__create_ctg("Ctg_0", "A", "B", "C");
		fxt.Exec__parse
		( "<DynamicPageList>"
		, "category=Ctg_0"
		, "offset=2"
		, "count=2"
		, "</DynamicPageList>"
		);
		fxt.Test__html(fxt.Make__html__itms__null("C"));
	}
	@Test  public void Ns() {
		fxt.Init__create_ctg("Ctg_0", "Talk:A B", "B");
		fxt.Exec__parse
		( "<DynamicPageList>"
		, "category=Ctg_0"
		, "namespace=Talk"
		, "</DynamicPageList>"
		);
		fxt.Test__html(Gfh_utl.Replace_apos_concat_lines
		( "<ul>"
		, "  <li><a href='/wiki/Talk:A_B' title='Talk:A B'>A B</a></li>"
		,  "</ul>"
		));
	}
	@Test  public void Show_ns() {
		fxt.Init__create_ctg("Ctg_0", "Talk:A");
		fxt.Exec__parse
		( "<DynamicPageList>"
		, "category=Ctg_0"
		, "shownamespace=true"
		, "</DynamicPageList>"
		);
		fxt.Test__html(Gfh_utl.Replace_apos_concat_lines
		( "<ul>"
		, "  <li><a href='/wiki/Talk:A' title='Talk:A'>Talk:A</a></li>"
		,  "</ul>"
		));

		fxt.Exec__parse
		( "<DynamicPageList>"
		, "category=Ctg_0"
		, "shownamespace=false"
		, "</DynamicPageList>"
		);
		fxt.Test__html(Gfh_utl.Replace_apos_concat_lines
		( "<ul>"
		, "  <li><a href='/wiki/Talk:A' title='Talk:A'>A</a></li>"
		,  "</ul>"
		));
	}
	@Test  public void Comment() {	// PURPOSE: comment should be ignored; en.n:Portal:Federally_Administered_Tribal_Areas; DATE:2014-01-18
		fxt.Init__create_ctg("Ctg_0", "B", "A");
		fxt.Exec__parse
		( "<DynamicPageList>"
		, "category=Ctg_0"
		, "<!--category=Ctg_0-->"
		, "</DynamicPageList>"
		);
		fxt.Test__html(fxt.Make__html__itms__null("B", "A"));
	}
	@Test  public void Error_skip_line() {	// PURPOSE: error should skip rest of line; was failing with array out of bounds; en.n:Portal:Austria/Wikipedia; DATE:2014-01-18
		fxt.Init__warns("dynamic_page_list:unknown_key: page=Test page key=Ctg_0 order");	// ignore warning message
		fxt.Exec__parse("<DynamicPageList> category=Ctg_0 order=descending</DynamicPageList>");
		fxt.Test__html("No pages meet these criteria.");
	}
	@Test  public void Atr_has_template() {	// PURPOSE: attribute also has template; DATE:2014-01-31
		fxt.Init__create_ctg("Test_page", "B", "A");
		fxt.Exec__parse
		( "<DynamicPageList>"
		, "category={{PAGENAME}}"
		, "</DynamicPageList>"
		);
		fxt.Test__html(fxt.Make__html__itms__null("B", "A"));
	}
	@Test  public void Err_page_ns_doesnt_exist() {// PURPOSE: check that <dpl> is not enabled if wiki does not have Page / Index ns; PAGE:fr.w:Wikipedia:Le_Bistro/novembre_2006 DATE:2014-11-28
		// reset categories to (a) remove ns for Page / Index; (b) add back Category (else null error)
		fxt.Wiki().Ns_mgr().Clear();
		fxt.Wiki().Ns_mgr().Add_new(14, "Category").Init();

		fxt.Wiki().Cfg_parser().Xtns().Itm_pages().Reset();	// must reset to clear cached valid ns_page from previous tests
		fxt.Fxt().Test_parse_page_wiki_str("<dynamicpagelist>category=a</dynamicpagelist>", "No pages meet these criteria.");
		fxt.Wiki().Cfg_parser().Xtns().Itm_pages().Reset();	// must reset to clear cached invalid ns_page for next tests

		// reset categories for rest of tests
		fxt.Wiki().Ns_mgr().Add_new(0, "").Init();	// call .Clear() to remove ns for Page / Index
	}
	@Test  public void Ordermethod__invalid() {	// PURPOSE: do not fail if ordermethod is invalid; PAGE:sr.d:Викиречник:Википројекат_1001_арапска_реч/Списак_уноса; DATE:2015-10-16
		fxt.Init__create_ctg("Ctg_0", "A", "B", "C");
		fxt.Exec__parse
		( "<DynamicPageList>"
		, "category=Ctg_0"
		, "ordermethod=ascending"	// should not throw error
		, "ordermethod=sortkey"
		, "</DynamicPageList>"
		);
		fxt.Test__html(fxt.Make__html__itms__null("A", "B", "C"));
	}
	@Test  public void Encode_spaces() {// PURPOSE:encode spaces in href; PAGE:en.q:Wikiquote:Speedy_deletions DATE:2016-01-19
		fxt.Init__create_ctg("Ctg_0", "A B");
		fxt.Exec__parse
		( "<DynamicPageList>"
		, "category=Ctg_0"
		, "nofollow=true"
		, "</DynamicPageList>"
		);
		fxt.Test__html(Gfh_utl.Replace_apos_concat_lines
		( "<ul>"
		, "  <li><a href='/wiki/A_B' title='A B' rel='nofollow'>A B</a></li>"	// "/wiki/A_B" not "/wiki/A B"
		,  "</ul>"
		));
	}
	@Test  public void Encode_quotes() {// PURPOSE:encode quotes; PAGE:en.b:Wikibooks:Alphabetical_classification/All_Books; DATE:2016-01-21
		fxt.Init__create_ctg("Ctg_0", "A\"B");
		fxt.Exec__parse
		( "<DynamicPageList>"
		, "category=Ctg_0"
		, "nofollow=true"
		, "</DynamicPageList>"
		);
		fxt.Test__html(Gfh_utl.Replace_apos_concat_lines
		( "<ul>"
		, "  <li><a href='/wiki/A%22B' title='A&quot;B' rel='nofollow'>A&quot;B</a></li>"	// "/wiki/A_B" not "/wiki/A B"
		,  "</ul>"
		));
	}
	@Test   public void Err__bad_key_causes_out_of_bound() {	// PURPOSE: bad key causes out of bounds error; PAGE:de.n:Portal:Brandenburg DATE:2016-04-21
		fxt.Exec__parse
		( "<DynamicPageList>"
		, "<DynamicPageList>category=A</DynamicPageList>a=b c=d"
		, "<DynamicPageList>category=B</DynamicPageList>"
		);
		fxt.Test__html(String_.Concat_lines_nl_skip_last
		( "&lt;DynamicPageList&gt;"
		, "No pages meet these criteria.a=b c=d"
		, "No pages meet these criteria."
		));
	}
}
class Dpl_page_mok {
	public Dpl_page_mok(int id, String ttl) {this.id = id; this.ttl = ttl;}
	public int Id() {return id;} private int id;
	public String Ttl() {return ttl;} private String ttl;
	public static Dpl_page_mok new_(int id, String ttl) {return new Dpl_page_mok(id, ttl);}
}
class Dpl_xnde_fxt {	
	private final    Xop_fxt fxt = new Xop_fxt();
	private final    Bry_bfr bfr = Bry_bfr_.New();
	private String exec__raw;
	private int next_id;
	public void Clear() {
		next_id = 100;
		fxt.Reset();
		this.exec__raw = String_.Empty;
		this.expd_warns = String_.Ary_empty;
		fxt.App().Usr_dlg().Gui_wkr().Clear();
		fxt.Wiki().Hive_mgr().Clear();
		fxt.Wiki().Db_mgr().Save_mgr().Clear();	// NOTE: must clear to reset next_id to 0; Init__create_ctg assumes clean slate from 0
		fxt.Wiki().Xtn_mgr().Xtn_proofread().Enabled_y_();
		fxt.Wiki().Db_mgr().Load_mgr().Clear(); // must clear; otherwise fails b/c files get deleted, but wiki.data_mgr caches the Xowd_regy_mgr (the .reg file) in memory;
		fxt.Wiki().Ns_mgr().Add_new(Xowc_xtn_pages.Ns_page_id_default, "Page").Add_new(Xowc_xtn_pages.Ns_index_id_default, "Index").Init();
		Io_mgr.Instance.InitEngine_mem();
	}
	public Xowe_wiki Wiki() {return fxt.Wiki();}
	public Xop_fxt Fxt() {return fxt;}
	public void Init__warns(String... v) {expd_warns = v;} private String[] expd_warns;
	public void Init__create_ctg(String ctg, String... ttls) {
		int len = ttls.length;
		Dpl_page_mok[] ary = new Dpl_page_mok[len];
		for (int i = 0; i < len; i++)
			ary[i] = Dpl_page_mok.new_(++next_id, ttls[i]);
		Init__create_ctg_pages(ctg, ary);
	}
	public void Init__create_ctg_pages(String ctg_ttl, Dpl_page_mok... pages) {
		Xoctg_catpage_ctg ctg = new Xoctg_catpage_ctg(1, Bry_.new_u8(ctg_ttl));
		int pages_len = pages.length;
		Xoctg_catpage_tmp tmp = new Xoctg_catpage_tmp();
		for (int i = 0; i < pages_len; i++) {
			Dpl_page_mok page = pages[i];
			int id = page.Id();
			String ttl_str = page.Ttl();
			Xoa_ttl ttl = fxt.Wiki().Ttl_parse(Bry_.new_u8(ttl_str));
			Xoae_page page_obj = fxt.Wiki().Data_mgr().Load_page_by_ttl(ttl);
			if (page_obj.Db().Page().Exists_n()) {
				fxt.Init_page_create(ttl_str, ttl_str);
				fxt.Init_id_create (id, 0, 0, false, 5, Xow_ns_.Tid__main, ttl_str);
			}
			byte tid = gplx.xowa.addons.wikis.ctgs.Xoa_ctg_mgr.Tid__page;
			Xoctg_catpage_itm catpage_itm = Xoctg_catpage_itm.New_by_ttl(tid, page.Id(), ttl);
			tmp.Add(catpage_itm);
		}
		tmp.Make_by_ctg(ctg);
		Xoctg_catpage_mgr catpage_mgr = fxt.Wiki().Ctg__catpage_mgr();
		catpage_mgr.Cache__add(Bry_.new_u8("Category:" + ctg_ttl), ctg);
	}
	public String Make__html__itms__null(String... pages) {return this.Make__html(null, pages);}
	public String Make__html(String itm_html, String... pages) {
		bfr.Add_str_a7("<ul>").Add_byte_nl();
		int pages_len = pages.length;
		for (int i = 0; i < pages_len; i++) {
			String page = pages[i];
			bfr.Add_str_a7("  <li><a href=\"/wiki/").Add_str_a7(page).Add_str_a7("\" title=\"").Add_str_a7(page).Add_str_a7("\"");
			if (itm_html != null) bfr.Add_str_a7(itm_html);
			bfr.Add_str_a7(">").Add_str_a7(page).Add_str_a7("</a></li>").Add_byte_nl();
		}
		bfr.Add_str_a7("</ul>").Add_byte_nl();
		return bfr.To_str_and_clear();
	}
	public void Exec__parse(String... raw_lines) {this.exec__raw = String_.Concat_lines_nl_skip_last(raw_lines);}
	public void Test__html(String expd) {
		fxt.Test_parse_page_wiki_str(exec__raw, expd);
		fxt.tst_Warn(expd_warns);
	}
}
