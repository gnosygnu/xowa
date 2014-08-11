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
	@Test  public void Basic()					{fxt.Test("{{DISPLAYTITLE:a}}"											, "a");}
	@Test  public void Apos_italic()			{fxt.Test("{{DISPLAYTITLE:''a''}}"										, "<i>a</i>");}
	@Test  public void Strip_display()	{
		String expd_fail = "<span style='/* attempt to bypass $wgRestrictDisplayTitle */'>a</span>";
		fxt.Test("{{DISPLAYTITLE:<span style='display:none;'>a</span>}}"	, expd_fail);
		fxt.Test("{{DISPLAYTITLE:<span style='user-select:n;'>a</span>}}"	, expd_fail);
		fxt.Test("{{DISPLAYTITLE:<span style='visibility:n;'>a</span>}}"	, expd_fail);
		fxt.Test("{{DISPLAYTITLE:<span style=''>display:none</span>}}"		, "<span style=''>display:none</span>");
	}
}
class Pfunc_displaytitle_fxt {
	private Xop_fxt fxt = new Xop_fxt();
	public void Reset() {
		fxt.Reset();
	}
	public void Test(String raw, String expd) {
		fxt.Test_parse_tmpl_str_test(raw, "{{test}}", "");
		Tfds.Eq(expd, String_.new_utf8_(fxt.Page().Html_data().Display_ttl()));
	}
}
