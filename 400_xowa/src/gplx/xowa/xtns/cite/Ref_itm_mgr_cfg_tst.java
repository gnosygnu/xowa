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
package gplx.xowa.xtns.cite; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import org.junit.*;
public class Ref_itm_mgr_cfg_tst {		
	@Before public void init() {fxt.Clear();} private Ref_itm_mgr_cfg_fxt fxt = new Ref_itm_mgr_cfg_fxt();
	@Test  public void Ref_backlabels_xby_bry()	{
		fxt.Test_Ref_backlabels_xby_bry("a b c"			, "a", "b", "c");		// basic
		fxt.Test_Ref_backlabels_xby_bry("  a b c"		, "a", "b", "c");		// leading ws
		fxt.Test_Ref_backlabels_xby_bry("a b c   "		, "a", "b", "c");		// trailing ws
		fxt.Test_Ref_backlabels_xby_bry("a   b    c"	, "a", "b", "c");		// redundant ws
	}
}
class Ref_itm_mgr_cfg_fxt {
	public void Clear() {}
	public void Test_Ref_backlabels_xby_bry(String raw, String... expd) {
		byte[][] actl = Ref_html_wtr_cfg.Ref_backlabels_xby_bry(Bry_.new_utf8_(raw));
		Tfds.Eq_ary_str(expd, String_.Ary(actl));
	}
}
