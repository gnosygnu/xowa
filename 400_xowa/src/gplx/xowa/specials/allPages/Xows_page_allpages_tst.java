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
package gplx.xowa.specials.allPages; import gplx.*; import gplx.xowa.*; import gplx.xowa.specials.*;
import org.junit.*; import gplx.core.net.*; import gplx.core.net.qargs.*; import gplx.xowa.wikis.data.tbls.*;
import gplx.xowa.wikis.nss.*;
import gplx.xowa.wikis.tdbs.hives.*;
public class Xows_page_allpages_tst {		
	@Before public void init() {fxt.Clear();} private Xows_page_allpages_fxt fxt = new Xows_page_allpages_fxt();
	@Test  public void Build_data() {
		Xow_hive_mgr_fxt.Ttls_create_rng(fxt.Wiki(), 5, 7);
		fxt.Clear().Init_ttl_leaf("/B1").Expd_prv("A3").Expd_nxt("B6").Expd_ttls("B1", "B2", "B3", "B4", "B5").Test_build_data();	// SpecialPage:AllPages/B1
		fxt.Clear().Init_arg("from", "B1").Expd_prv("A3").Expd_nxt("B6").Expd_ttls("B1", "B2", "B3", "B4", "B5").Test_build_data();	// single file
		fxt.Clear().Init_arg("from", "A6").Expd_prv("A1").Expd_nxt("B4").Expd_ttls("A6", "B0", "B1", "B2", "B3").Test_build_data();	// overflow rhs
		fxt.Clear().Init_arg("from", "E4").Expd_prv("D6").Expd_nxt("E6").Expd_ttls("E4", "E5", "E6", null, null).Test_build_data();	// bounds rhs
		fxt.Clear().Init_arg("from", "A0").Expd_prv("A0").Expd_nxt("A5").Expd_ttls("A0", "A1", "A2", "A3", "A4").Test_build_data();	// bounds lhs	
		fxt.Clear().Init_arg("from", "B0a").Expd_prv("A3").Expd_nxt("B6").Expd_ttls("B1", "B2", "B3", "B4", "B5").Test_build_data();	// inexact match; B0a matches "hi" value
		fxt.Clear().Init_arg("from", "B1").Init_arg("hideredirects", "1").Expd_prv("A0").Expd_nxt("C4").Expd_ttls("B2", "B4", "B6", "C0", "C2").Test_build_data();	// hide redirects
		fxt.Clear().Init_arg("from", "A6").Expd_prv("A0").Expd_nxt("E1").Init_itms_per_page(23).Expd_ttls("A6", "B0", "B1", "B2", "B3", "B4", "B5", "B6", "C0", "C1", "C2", "C3", "C4", "C5", "C6", "D0", "D1", "D2", "D3", "D4", "D5", "D6", "E0").Test_build_data();	// overflow rhs x2			
	}
	@Test  public void Build_html_main() {
		Xow_hive_mgr_fxt.Ttls_create_rng(fxt.Wiki(), 5, 7);
		fxt.Clear().Init_arg("from", "B2").Init_itms_per_page(5).Test_build_html(String_.Concat_lines_nl
		(	"<table class=\"mw-allpages-table-form\">"
		,	"  <tr>"
		,	"    <td class=\"mw-allpages-nav\">"
		,	"<a href=\"/wiki/Special:AllPages?from=A4\" title=\"Special:AllPages\">Previous page (A4)</a> |"
		,	"<a href=\"/wiki/Special:AllPages?from=C0\" title=\"Special:AllPages\">Next page (C0)</a>"
		,	"    </td>"
		,	"  </tr>"
		,	"</table>"
		,	"<table class=\"mw-allpages-table-chunk\">"
		,	"<tbody>"
		,	"  <tr>"
		,	"    <td style=\"width:33%\"><a href=\"/wiki/B2\" title=\"B2\">B2</a></td>"
		,	"    <td style=\"width:33%\"><div class=\"allpagesredirect\"><a href=\"/wiki/B3\" class=\"mw-redirect\" title=\"B3\">B3</a></div></td>"
		,	"    <td style=\"width:33%\"><a href=\"/wiki/B4\" title=\"B4\">B4</a></td>"
		,	"  </tr>"
		,	"  <tr>"
		,	"    <td style=\"width:33%\"><div class=\"allpagesredirect\"><a href=\"/wiki/B5\" class=\"mw-redirect\" title=\"B5\">B5</a></div></td>"
		,	"    <td style=\"width:33%\"><a href=\"/wiki/B6\" title=\"B6\">B6</a></td>"
		,	"  </tr>"
		,	"</tbody>"
		,	"</table>"
		,	"<hr/>"
		,	"<div class=\"mw-allpages-nav\">"
		,	"<a href=\"/wiki/Special:AllPages?from=A4\" title=\"Special:AllPages\">Previous page (A4)</a> |"
		,	"<a href=\"/wiki/Special:AllPages?from=C0\" title=\"Special:AllPages\">Next page (C0)</a>"
		,	"</div>"
		));
	}
	@Test  public void Build_html_redirect() {
		Xow_hive_mgr_fxt.Ttls_create_rng(fxt.Wiki(), 1, 7);
		fxt.Clear().Init_arg("from", "A2").Init_arg("hideredirects", "1").Init_itms_per_page(2).Test_build_html(String_.Concat_lines_nl
		(	"<table class=\"mw-allpages-table-form\">"
		,	"  <tr>"
		,	"    <td class=\"mw-allpages-nav\">"
		,	"<a href=\"/wiki/Special:AllPages?from=A0&hideredirects=1\" title=\"Special:AllPages\">Previous page (A0)</a> |"
		,	"<a href=\"/wiki/Special:AllPages?from=A6&hideredirects=1\" title=\"Special:AllPages\">Next page (A6)</a>"
		,	"    </td>"
		,	"  </tr>"
		,	"</table>"
		,	"<table class=\"mw-allpages-table-chunk\">"
		,	"<tbody>"
		,	"  <tr>"
		,	"    <td style=\"width:33%\"><a href=\"/wiki/A2\" title=\"A2\">A2</a></td>"
		,	"    <td style=\"width:33%\"><a href=\"/wiki/A4\" title=\"A4\">A4</a></td>"
		,	"  </tr>"
		,	"</tbody>"
		,	"</table>"
		,	"<hr/>"
		,	"<div class=\"mw-allpages-nav\">"
		,	"<a href=\"/wiki/Special:AllPages?from=A0&hideredirects=1\" title=\"Special:AllPages\">Previous page (A0)</a> |"
		,	"<a href=\"/wiki/Special:AllPages?from=A6&hideredirects=1\" title=\"Special:AllPages\">Next page (A6)</a>"
		,	"</div>"
		));
	}
	@Test  public void Build_html_ns() {
		Xow_hive_mgr_fxt.Ttls_create_rng(fxt.Wiki(), fxt.Wiki().Ns_mgr().Ns_template(), 1, 7);
		fxt.Clear().Init_arg("from", "A2").Init_arg("namespace", "10").Init_itms_per_page(2).Test_build_html(String_.Concat_lines_nl
		(	"<table class=\"mw-allpages-table-form\">"
		,	"  <tr>"
		,	"    <td class=\"mw-allpages-nav\">"
		,	"<a href=\"/wiki/Special:AllPages?from=A0&namespace=10\" title=\"Special:AllPages\">Previous page (Template:A0)</a> |"
		,	"<a href=\"/wiki/Special:AllPages?from=A4&namespace=10\" title=\"Special:AllPages\">Next page (Template:A4)</a>"
		,	"    </td>"
		,	"  </tr>"
		,	"</table>"
		,	"<table class=\"mw-allpages-table-chunk\">"
		,	"<tbody>"
		,	"  <tr>"
		,	"    <td style=\"width:33%\"><a href=\"/wiki/Template:A2\" title=\"Template:A2\">Template:A2</a></td>"
		,	"    <td style=\"width:33%\"><div class=\"allpagesredirect\"><a href=\"/wiki/Template:A3\" class=\"mw-redirect\" title=\"Template:A3\">Template:A3</a></div></td>"
		,	"  </tr>"
		,	"</tbody>"
		,	"</table>"
		,	"<hr/>"
		,	"<div class=\"mw-allpages-nav\">"
		,	"<a href=\"/wiki/Special:AllPages?from=A0&namespace=10\" title=\"Special:AllPages\">Previous page (Template:A0)</a> |"
		,	"<a href=\"/wiki/Special:AllPages?from=A4&namespace=10\" title=\"Special:AllPages\">Next page (Template:A4)</a>"
		,	"</div>"
		));
	}
	@Test   public void Misc() {
		Xow_hive_mgr_fxt.Ttls_create_rng(fxt.Wiki(), fxt.Wiki().Ns_mgr().Ns_template(), 1, 7);
		fxt.Clear().Init_arg("from", "Template:B1").Expd_arg("from", "B1").Expd_arg("namespace", "10").Test_build_data();	// extract ns from ttl
		fxt.Clear().Init_arg("from", "Template:B1").Expd_display_ttl("All pages").Expd_address_page("Special:AllPages").Test_special_gen();	// display ttl
	}
}
class Xows_page_allpages_fxt {
	public Xows_page_allpages_fxt Clear() {
		if (app == null) {
			app = Xoa_app_fxt.Make__app__edit();
			wiki = Xoa_app_fxt.Make__wiki__edit(app);
			allpages = wiki.Special_mgr().Page_allpages();
			Gfo_invk_.Invk_by_val(allpages, Xows_page_allpages.Invk_itms_per_page_, 5);
		}
		init_ttl_leaf = "";
		expd_prv = expd_nxt = null;
		expd_ttls = null;
		expd_display_ttl = null;
		return this;
	}	private Xoae_app app;
	public Xowe_wiki Wiki() {return wiki;} private Xowe_wiki wiki; Xows_page_allpages allpages;
	public Xows_page_allpages_fxt Init_arg(String key, String val) {init_args.Add(new Gfo_qarg_itm(Bry_.new_a7(key), Bry_.new_a7(val))); return this;} private List_adp init_args = List_adp_.New();
	public Xows_page_allpages_fxt Init_ttl_leaf(String val) {init_ttl_leaf = val; return this;} private String init_ttl_leaf;
	public Xows_page_allpages_fxt Init_itms_per_page(int v) {init_itms_per_page = v; return this;} private int init_itms_per_page = 5;
	public Xows_page_allpages_fxt Expd_arg(String key, String val) {expd_args.Add(new Gfo_qarg_itm(Bry_.new_a7(key), Bry_.new_a7(val))); return this;} private List_adp expd_args = List_adp_.New();
	public Xows_page_allpages_fxt Expd_prv(String v) {expd_prv = v; return this;} private String expd_prv;
	public Xows_page_allpages_fxt Expd_nxt(String v) {expd_nxt = v; return this;} private String expd_nxt;
	public Xows_page_allpages_fxt Expd_ttls(String... v) {expd_ttls = v; return this;} private String[] expd_ttls;
	public Xows_page_allpages_fxt Expd_display_ttl(String v) {expd_display_ttl = v; return this;} private String expd_display_ttl;
	public Xows_page_allpages_fxt Expd_address_page(String v) {expd_address_page = v; return this;} private String expd_address_page;

	public static String Xto_str(Xowe_wiki wiki, Xowd_page_itm v) {
		if (v == null) return null;
		Xow_ns ns = wiki.Ns_mgr().Ids_get_or_null(v.Ns_id());
		String ns_str = ns == null ? "" : String_.new_a7(ns.Name_db_w_colon());
		return ns_str + String_.new_a7(v.Ttl_page_db());
	}
	public static String[] Xto_str_ary(Xowe_wiki wiki, Xowd_page_itm[] ary) {
		int ary_len = ary.length;
		String[] rv = new String[ary_len];
		for (int i = 0; i < ary_len; i++) {
			Xowd_page_itm itm = ary[i];
			rv[i] = Xto_str(wiki, itm);
		}
		return rv;
	}
	public static String[] Xto_str_ary(Gfo_qarg_itm[] ary) {
		int ary_len = ary.length;
		String[] rv = new String[ary_len];
		for (int i = 0; i < ary_len; i++) {
			Gfo_qarg_itm itm = ary[i];
			rv[i] = String_.new_u8(itm.Key_bry()) + "=" + String_.new_u8(itm.Val_bry());
		}
		return rv;
	}
	public Xows_page_allpages_fxt Test_special_gen() {
		init_url = app.User().Wikii().Utl__url_parser().Parse(Xow_special_meta_.Itm__all_pages.Ttl_bry());
		Xoa_ttl init_ttl = Make_init_ttl();
		allpages.Special__gen(wiki, wiki.Parser_mgr().Ctx().Page(), init_url, init_ttl);
		if (expd_display_ttl != null) Tfds.Eq(expd_display_ttl, String_.new_u8(wiki.Parser_mgr().Ctx().Page().Html_data().Display_ttl()));
		if (expd_address_page != null) Tfds.Eq(expd_address_page, String_.new_u8(init_url.Page_bry()));
		return this;
	}
	public Xows_page_allpages_fxt Test_build_data() {
		Exec_build();
		if (expd_ttls != null) Tfds.Eq_ary_str(expd_ttls, Xto_str_ary(wiki, allpages.Rslt_list_ttls()));
		if (expd_nxt != null) Tfds.Eq(expd_nxt, Xto_str(wiki, allpages.Rslt_nxt()));
		if (expd_prv != null) Tfds.Eq(expd_prv, Xto_str(wiki, allpages.Rslt_prv()));
		if (expd_args.Count() > 0) {
			Gfo_qarg_itm[] expd_args_ary = (Gfo_qarg_itm[])expd_args.To_ary(Gfo_qarg_itm.class); 
			Tfds.Eq_ary_str(Xto_str_ary(expd_args_ary), Xto_str_ary(init_url.Qargs_ary()));
		}
		return this;
	}	private Xoa_url init_url = Xoa_url.blank();
	public Xows_page_allpages_fxt Test_build_html(String expd) {
		Exec_build();
		allpages.Build_html(wiki.Parser_mgr().Ctx().Page());
		Tfds.Eq_str_lines(expd, String_.new_a7(wiki.Parser_mgr().Ctx().Page().Db().Text().Text_bry()));
		return this;
	}
	private void Exec_build() {
		if (allpages.Itms_per_page() != init_itms_per_page) allpages.Itms_per_page_(init_itms_per_page);
		init_url.Qargs_ary_((Gfo_qarg_itm[])init_args.To_ary(Gfo_qarg_itm.class));
		init_args.Clear();
		Xoa_ttl init_ttl = Make_init_ttl();
		allpages.Build_data(init_url, init_ttl);
	}
	private Xoa_ttl Make_init_ttl() {return Xoa_ttl.Parse(wiki, Bry_.new_u8(Xow_special_meta_.Itm__all_pages.Ttl_str() + init_ttl_leaf));}
}
