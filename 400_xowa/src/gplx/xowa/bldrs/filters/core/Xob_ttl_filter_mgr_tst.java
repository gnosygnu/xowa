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
package gplx.xowa.bldrs.filters.core; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.filters.*;
import org.junit.*;
public class Xob_ttl_filter_mgr_tst {
	@Before public void init() {fxt.Clear();} private final Xob_ttl_filter_mgr_fxt fxt = new Xob_ttl_filter_mgr_fxt();
	@Test  public void One() {
		fxt.Init_load_exclude("A");
		fxt.Init_load_include("B");
		fxt.Test_match_exclude_y("A");
		fxt.Test_match_exclude_n("B", "C");
		fxt.Test_match_include_y("B");
		fxt.Test_match_include_n("A", "C");
	}
}
class Xob_ttl_filter_mgr_fxt {
	private final Xob_ttl_filter_mgr mgr = new Xob_ttl_filter_mgr();
	public void Clear() {
		mgr.Clear();
	}
	public void Init_load_exclude(String itm) {mgr.Load(Bool_.Y, Bry_.new_utf8_(itm));}
	public void Init_load_include(String itm) {mgr.Load(Bool_.N, Bry_.new_utf8_(itm));}
	public void Test_match_exclude_y(String... itms) {Test_match(Bool_.Y, Bool_.Y, itms);}
	public void Test_match_exclude_n(String... itms) {Test_match(Bool_.Y, Bool_.N, itms);}
	public void Test_match_include_y(String... itms) {Test_match(Bool_.N, Bool_.Y, itms);}
	public void Test_match_include_n(String... itms) {Test_match(Bool_.N, Bool_.N, itms);}
	private void Test_match(boolean exclude, boolean expd, String... itms) {
		for (String itm : itms) {
			byte[] itm_bry = Bry_.new_utf8_(itm);
			if (exclude)
				Tfds.Eq(expd, mgr.Match_exclude(itm_bry), itm);
			else
				Tfds.Eq(expd, mgr.Match_include(itm_bry), itm);
		}
	}
} 
