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
package gplx.xowa.xtns.translates; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import org.junit.*;
public class Xop_languages_xnde_tst {
	@Before public void init() {
		Io_mgr._.InitEngine_mem();
		fxt.Reset();
		fxt.Page_ttl_("Help:A");
		fxt.Init_page_create("Help:A", "");				// create for AllPages 
	} 	private Xop_fxt fxt = new Xop_fxt();
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
