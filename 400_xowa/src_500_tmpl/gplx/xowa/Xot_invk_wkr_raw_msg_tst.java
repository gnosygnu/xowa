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
package gplx.xowa; import gplx.*;
import org.junit.*;
public class Xot_invk_wkr_raw_msg_tst {
	@Before		public void init() {fxt.Reset();} private Xop_fxt fxt = new Xop_fxt();
	@After	public void term() {fxt.Init_defn_clear();}
	@Test  public void Raw() { // PURPOSE: {{raw:A}} is same as {{A}}; EX.WIKT:android; {{raw:ja/script}}
		fxt.Init_defn_clear();
		fxt.Init_defn_add("Test 1", "{{#if:|y|{{{1}}}}}");
		fxt.Test_parse_tmpl_str("{{raw:Test 1|a}}", "a");
		fxt.Init_defn_clear();
	}
	@Test  public void Raw_spanish() { // PURPOSE.fix: {{raw}} should not fail; EX:es.s:Carta_a_Silvia; DATE:2014-02-11
		fxt.Test_parse_tmpl_str("{{raw}}", "[[:Template:raw]]");	// used to fail; now tries to get Template:Raw which doesn't exist
	}
	@Test  public void Special() { // PURPOSE: {{Special:Whatlinkshere}} is same as [[:Special:Whatlinkshere]]; EX.WIKT:android; isValidPageName
		fxt.Test_parse_page_tmpl_str("{{Special:Whatlinkshere}}", "[[:Special:Whatlinkshere]]");
	}
	@Test  public void Special_arg() { // PURPOSE: make sure Special still works with {{{1}}}
		fxt.Init_defn_clear();
		fxt.Init_defn_add("Test1", "{{Special:Whatlinkshere/{{{1}}}}}");
		fxt.Test_parse_tmpl_str("{{Test1|-1}}", "[[:Special:Whatlinkshere/-1]]");
		fxt.Init_defn_clear();
	}
	@Test  public void Raw_special() { // PURPOSE: {{raw:A}} is same as {{A}}; EX.WIKT:android; {{raw:ja/script}}
		fxt.Test_parse_tmpl_str("{{raw:Special:Whatlinkshere}}", "[[:Special:Whatlinkshere]]");
		fxt.Init_defn_clear();
	}
	@Test  public void Msg() {
		fxt.Init_defn_add("CURRENTMONTH", "a");
		fxt.Test_parse_tmpl_str("{{msg:CURRENTMONTH}}", "a");
	}
}
