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
package gplx.xowa.xtns.insiders; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import org.junit.*;
public class Insider_func_tst {
	@Before public void init()				{fxt.Reset();} private Insider_func_fxt fxt = new Insider_func_fxt();
	@Test  public void Basic() {
		fxt.Test_parse("{{#insider:A}}x{{#insider:B}}", "x", "A", "B");
	}
}
class Insider_func_fxt {
	private Xop_fxt fxt = new Xop_fxt();
	public void Reset() {
		fxt.Reset();
	}
	public void Test_parse(String raw, String expd, String... insiders) {
		fxt.Test_parse_tmpl_str_test(raw, "{{test}}", expd);
		Insider_xtn_skin_itm skin_itm = (Insider_xtn_skin_itm)fxt.Page().Html_data().Xtn_skin_mgr().Get_or_null(Insider_xtn_skin_itm.KEY);
		ListAdp list = skin_itm.Itms();
		byte[][] brys = (byte[][])list.Xto_ary(byte[].class);
		Tfds.Eq_ary_str(insiders, String_.Ary(brys));
	}
}
