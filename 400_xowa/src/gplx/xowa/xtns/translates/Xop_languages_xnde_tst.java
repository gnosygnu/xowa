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
package gplx.xowa.xtns.translates; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import org.junit.*;
public class Xop_languages_xnde_tst {
	@Before public void init() {
		Io_mgr.Instance.InitEngine_mem();
		fxt.Reset();
		fxt.Page_ttl_("Help:A");
		fxt.Init_page_create("Help:A", "");				// create for AllPages 
	} 	private final Xop_fxt fxt = new Xop_fxt();
	@Test   public void None() {
		fxt.Test_parse_page_all_str("<languages/>", "");	// empty
	}
	@Test   public void Many() {
		fxt.Init_page_create("Help:A/de", "");
		fxt.Init_page_create("Help:A/en", "");
		fxt.Init_page_create("Help:A/fr", "");
		fxt.Test_parse_page_all_str("<languages/>", Many_expd);
	}
	@Test   public void Many_english_implied() {
		fxt.Init_page_create("Help:A/de", "");
		fxt.Init_page_create("Help:A/fr", "");
		fxt.Test_parse_page_all_str("<languages/>", Many_expd);
	}
	@Test   public void Current_is_french() {
		fxt.Init_page_create("Help:A/de", "");
		fxt.Init_page_create("Help:A/fr", "");
		fxt.Page_ttl_("Help:A/fr");
		fxt.Test_parse_page_all_str("<languages/>", String_.Concat_lines_nl
		(	"<table>"
		,	"  <tbody>"
		,	"    <tr valign=\"top\">"
		,	"		<td class=\"mw-pt-languages-label\">Other languages:</td>"
		,	"       <td class=\"mw-pt-languages-list\">"		
		,	"         <a href=\"/wiki/Help:A/de\" title=\"Help:A/de\">Deutsch</a>&#160;•" 
		,	"         <a href=\"/wiki/Help:A\" title=\"Help:A\"><span class=\"mw-pt-languages-ui\">English</span></a>&#160;•"
		,	"         <a href=\"/wiki/Help:A/fr\" title=\"Help:A/fr\">Français</a>&#160;•"
		,	"       </td>"
		,	"    </tr>"
		,	"  </tbody>"
		,	"</table>"
		));
	}
	private static final String Many_expd = String_.Concat_lines_nl
		(	"<table>"
		,	"  <tbody>"
		,	"    <tr valign=\"top\">"
		,	"		<td class=\"mw-pt-languages-label\">Other languages:</td>"
		,	"       <td class=\"mw-pt-languages-list\">"		
		,	"         <a href=\"/wiki/Help:A/de\" title=\"Help:A/de\">Deutsch</a>&#160;•"
		,	"         <a href=\"/wiki/Help:A\" title=\"Help:A\"><span class=\"mw-pt-languages-ui\">English</span></a>&#160;•"
		,	"         <a href=\"/wiki/Help:A/fr\" title=\"Help:A/fr\">Français</a>&#160;•"
		,	"       </td>"
		,	"    </tr>"
		,	"  </tbody>"
		,	"</table>"
		); 
}
