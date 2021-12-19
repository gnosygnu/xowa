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
package gplx.xowa.xtns.insiders;
import gplx.frameworks.tests.GfoTstr;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.basics.utls.StringUtl;
import gplx.xowa.*;
import org.junit.*;
public class Insider_html_bldr_tst {
	@Before public void init() {fxt.Clear();} private Insider_html_bldr_fxt fxt = new Insider_html_bldr_fxt();
	@Test public void Basic() {
		fxt.Init_insider("A_1");
		fxt.Test_bld(StringUtl.ConcatLinesNlSkipLast
		( "<div id='p-insiders' class='portal' role='navigation'>"
		, "  <h3>Docent</h3>"
		, "  <div class='body'>"
		, "    <ul>"
		, "      <li class='interwiki-insider'><a class='xowa-hover-off' href='/wiki/User:A_1'>A 1</a></li>"
		, "      <li class='interwiki-insider'><a class='xowa-hover-off' href='/wiki/Docent_page'>About Docents</a></li>"
		, "    </ul>"
		, "  </div>"
		, "</div>"
		));
	}
}
class Insider_html_bldr_fxt {
	private Xoae_app app; private Xowe_wiki wiki; private Xoae_page page;
	private Insider_xtn_mgr xtn_mgr;
	private Insider_xtn_skin_itm skin_itm;
	public void Clear() {
		this.app = Xoa_app_fxt.Make__app__edit();
		this.wiki = Xoa_app_fxt.Make__wiki__edit(app);
		Xop_fxt.Init_msg(wiki, "insider-title", "Docent");
		Xop_fxt.Init_msg(wiki, "insider-about", "About Docents");
		Xop_fxt.Init_msg(wiki, "insider-about-page", "Docent_page");
		this.xtn_mgr = wiki.Xtn_mgr().Xtn_insider();
		xtn_mgr.Enabled_y_();
		xtn_mgr.Xtn_init_by_wiki(wiki);
		this.page = wiki.Parser_mgr().Ctx().Page();
		skin_itm = new Insider_xtn_skin_itm(xtn_mgr.Html_bldr());
		page.Html_data().Xtn_skin_mgr().Add(skin_itm);
	}
	public void Init_insider(String lnki_ttl) {
		skin_itm.Add(BryUtl.NewU8(lnki_ttl));
	}
	public void Test_bld(String expd) {
		BryWtr tmp_bfr = BryWtr.NewAndReset(255);
		skin_itm.Write(tmp_bfr, page);
		GfoTstr.EqLines(expd, tmp_bfr.ToStrAndClear());
	}
}
