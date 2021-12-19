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
package gplx.xowa.parsers.utils;
import gplx.frameworks.tests.GfoTstr;
import gplx.types.basics.utls.BryUtl;
import gplx.types.basics.utls.StringUtl;
import gplx.types.basics.utls.BoolUtl;
import gplx.xowa.Xoa_ttl;
import gplx.xowa.Xoa_url;
import gplx.xowa.Xop_fxt;
import gplx.xowa.langs.kwds.Xol_kwd_grp_;
import gplx.xowa.parsers.tmpls.Xot_defn_tmpl;
import org.junit.Before;
import org.junit.Test;
public class Xop_redirect_mgr_tst {		
	@Before public void init() {fxt.Clear();} private Xop_redirect_mgr_fxt fxt = new Xop_redirect_mgr_fxt();
	@Test public void Basic()					{fxt.Test_redirect("#REDIRECT [[a]]", "A");}
	@Test public void Basic_colon()			{fxt.Test_redirect("#REDIRECT:[[a]]", "A");}
	@Test public void Ns_help()				{fxt.Test_redirect("#REDIRECT [[Help:a]]", "Help:A");}
	@Test public void First()					{fxt.Test_redirect("#REDIRECT [[a]] [[b]]", "A");}
	@Test public void Exc_false_match()		{fxt.Test_redirect("#REDIRECTA [[a]]", "");}
	@Test public void Exc_lnki_not_found()		{fxt.Test_redirect("#REDIRECT test", "");}
	@Test public void Ws()						{fxt.Test_redirect("\n#REDIRECT [[a]]", "A");}	// PAGE:en.w:Germany; {{Template group}} -> \n#REDIRECT [[Template:Navboxes]]
	@Test public void U8() {
		fxt.Init_u8();
		fxt.Init_kwds(BoolUtl.N, "#REDIRECT", "#перенаправление");
		fxt.Test_redirect("#REDIRECT [[A]]", "A");
		fxt.Test_redirect("#reDirect [[A]]", "A");
		fxt.Test_redirect("#перенаправление [[A]]", "A");
		fxt.Test_redirect("#ПЕРЕНАПРАВЛЕНИЕ [[A]]", "A");
	}
	@Test public void Url_decode()				{fxt.Test_redirect("#REDIRECT [[A%28B%29]]"	, "A(B)");}		// PURPOSE: url-decode links; PAGE:en.w:Watcher_(Buffy_the_Vampire_Slayer); DATE:2014-08-18
	@Test public void Url_decode_plus()		{fxt.Test_redirect("#REDIRECT [[A%28B%29+]]", "A(B)+");}	// PURPOSE: do not url-decode +; PAGE:en.w:Template:Positionskarte+; DATE:2014-08-22
	@Test public void Amp()					{fxt.Test_redirect("#REDIRECT [[A &amp; B]]", "A & B");}	// PURPOSE: &amp; -> &; PAGE:en.w:Amadou Bagayoko?redirect=n; DATE:2014-09-23
	@Test public void Frame_ttl() {	// PURPOSE: redirect should set invk frame title to redirect_trg, not original; PAGE:en.w:Statutory_city DATE:2014-08-22
		fxt.Test_frame_ttl("Template:A", "#REDIRECT [[Template:B]]", "Template:B", "Template:B");
	}
	@Test public void State_collapsed() {	// PURPOSE: state=collapsed broke redirects; PAGE:da.w:Middelaldercentret; DATE:2015-11-06
		fxt.Test_redirect("#REDIRECT [[Template:A|state=collapsed]]", "Template:A");
	}
	@Test public void Parse_1st_link_only() {	// PURPOSE: do not take pipe from 2nd lnki; PAGE:en.w:Template:pp-semi; DATE:2015-11-14
		fxt.Test_redirect("#REDIRECT [[Template:A]][[Category:B|b]]", "Template:A");
	}
	@Test public void Redirected_html() {
		// PURPOSE: "Redirected from" message was using "_" instead of " "; PAGE:en.w:Summer_Solstice; DATE:2015-12-29
		fxt.Test__redirected_html("A_B", "<span class=\"mw-redirectedfrom\">(Redirected from <a href=\"/wiki/A_B?redirect=no\" class=\"mw-redirect\" title=\"A B\">A B</a>)</span>");

		// PURPOSE: Title should be href_encoding; %5E instead of ^
		fxt.Test__redirected_html("^", "<span class=\"mw-redirectedfrom\">(Redirected from <a href=\"/wiki/%5E?redirect=no\" class=\"mw-redirect\" title=\"^\">^</a>)</span>");
	}
}
class Xop_redirect_mgr_fxt {
	private final Xop_fxt fxt = new Xop_fxt();
	public void Clear() {
		fxt.Reset();
	}
	public void Init_kwds(boolean case_match, String... kwds) {fxt.Init_lang_kwds(Xol_kwd_grp_.Id_redirect, case_match, kwds);}
	public void Init_u8() {
		fxt.Wiki().Lang().Case_mgr_u8_();
	}
	public void Test_frame_ttl(String tmpl_ttl_str, String tmpl_wtxt_str, String redirect_ttl, String expd_frame_ttl) {
		fxt.Init_page_create(tmpl_ttl_str, tmpl_wtxt_str);								// create redirect_src
		fxt.Init_page_create(redirect_ttl, "test");										// create redirect_trg
		fxt.Test_parse_page_tmpl_tkn("{{" + tmpl_ttl_str + "}}");						// parse {{redirect_src}}
		Xoa_ttl tmpl_ttl = Xoa_ttl.Parse(fxt.Wiki(), BryUtl.NewU8(tmpl_ttl_str));
		Xot_defn_tmpl defn_tmpl = (Xot_defn_tmpl)fxt.Wiki().Cache_mgr().Defn_cache().Get_by_key(tmpl_ttl.Page_db());	// get defn (which parse should have created)
		GfoTstr.EqObj(expd_frame_ttl, StringUtl.NewU8(defn_tmpl.Frame_ttl()));				// check frame_ttl
	}
	public void Test_redirect(String raw_str, String expd_str) {
		Xop_redirect_mgr redirect_mgr = fxt.Ctx().Wiki().Redirect_mgr();
		redirect_mgr.Clear();
		byte[] raw_bry = BryUtl.NewU8(raw_str);
		Xoa_ttl actl_ttl = redirect_mgr.Extract_redirect(raw_bry);
		byte[] actl_bry = actl_ttl == null ? BryUtl.Empty : actl_ttl.Full_txt();
		GfoTstr.EqObj(expd_str, StringUtl.NewU8(actl_bry));
	}
	public void Test__redirected_html(String page_str, String expd_str) {
		gplx.xowa.wikis.pages.redirects.Xopg_redirect_mgr redirect_mgr = new gplx.xowa.wikis.pages.redirects.Xopg_redirect_mgr();
		Xoa_ttl ttl = fxt.Wiki().Ttl_parse(BryUtl.NewU8(page_str));
		Xoa_url url = Xoa_url.New(fxt.Wiki(), ttl);
		redirect_mgr.Itms__add__article(url, ttl, BryUtl.Empty);
		byte[] actl_bry = Xop_redirect_mgr.Bld_redirect_msg(fxt.App(), fxt.Wiki(), redirect_mgr);
		GfoTstr.Eq(expd_str, StringUtl.NewU8(actl_bry));
	}
}
