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
package gplx.xowa.xtns.pfuncs.pages; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.pfuncs.*;
import org.junit.*;
public class Pfunc_displaytitle_tst {
	@Before public void init() {fxt.Reset();} private Pfunc_displaytitle_fxt fxt = new Pfunc_displaytitle_fxt();
	@Test  public void Basic()						{fxt.Init_restrict(Bool_.N).Test("{{DISPLAYTITLE:B A}}"					, "B A");}
	@Test  public void Apos_italic()				{fxt.Init_restrict(Bool_.N).Test("{{DISPLAYTITLE:''B A''}}"				, "<i>B A</i>");}
	@Test  public void Restrict_skip()				{fxt.Init_restrict(Bool_.Y).Test("{{DISPLAYTITLE:B A}}"					, null);}	// PURPOSE: skip if text does not match title; PAGE:de.b:Kochbuch/_Druckversion; DATE:2014-08-18
	@Test  public void Restrict_keep_ci()			{fxt.Init_restrict(Bool_.Y).Test("{{DISPLAYTITLE:a B}}"					, "a B");}	// PURPOSE: keep b/c case-insensitive match; DATE:2014-08-18
	@Test  public void Restrict_keep_underscore()	{fxt.Init_restrict(Bool_.Y).Test("{{DISPLAYTITLE:a_b}}"					, "a_b");}	// PURPOSE: keep b/c underscores should match spaces; PAGE:de.w:Mod_qos DATE:2014-11-06
	@Test  public void Restrict_keep_tags()			{fxt.Init_restrict(Bool_.Y).Test("{{DISPLAYTITLE:<b>a</b> <i>B</i>}}"	, "<b>a</b> <i>B</i>");}// PURPOSE: keep b/c text match (tags ignored); DATE:2014-08-18
	@Test  public void Strip_display()	{
		String expd_fail = "<span style='/* attempt to bypass $wgRestrictDisplayTitle */'>A b</span>";
		fxt.Init_restrict(Bool_.Y);
		fxt.Test("{{DISPLAYTITLE:<span style='display:none;'>A b</span>}}"	, expd_fail);
		fxt.Test("{{DISPLAYTITLE:<span style='user-select:n;'>A b</span>}}"	, expd_fail);
		fxt.Test("{{DISPLAYTITLE:<span style='visibility:n;'>A b</span>}}"	, expd_fail);
		fxt.Test("{{DISPLAYTITLE:<span style=''>display:none</span>}}"		, null);
	}
}
class Pfunc_displaytitle_fxt {
	private Xop_fxt fxt = new Xop_fxt();
	public void Reset() {
		fxt.Reset();
		fxt.Page_ttl_("A b");
	}
	public Pfunc_displaytitle_fxt Init_restrict(boolean v) {fxt.Wiki().Cfg_parser().Display_title_restrict_(v); return this;}
	public void Test(String raw, String expd) {
		fxt.Page().Html_data().Display_ttl_(null);	// TEST: always reset; needed for Strip_display which calls multiple times
		fxt.Test_parse_tmpl_str_test(raw, "{{test}}", "");
		Tfds.Eq(expd, String_.new_utf8_(fxt.Page().Html_data().Display_ttl()));
	}
}
