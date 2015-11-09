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
package gplx.langs.htmls.parsers; import gplx.*; import gplx.langs.*; import gplx.langs.htmls.*;
import org.junit.*;
public class Html_atr_cls__tst {
	private final Html_atr_cls__fxt fxt = new Html_atr_cls__fxt();
	@Test   public void Has() {
		fxt.Test__has__y("a b c", "a", "b", "c");
		fxt.Test__has__n("a b c", "d");
		fxt.Test__has__n("ab", "a");
	}
	@Test   public void Cls__has__hash() {
		Hash_adp_bry hash = fxt.Make_hash("x", "y", "z");
		fxt.Test__find_1st(hash,					0, "x");
		fxt.Test__find_1st(hash,					2, "z");
		fxt.Test__find_1st(hash,					0, "a x b");
		fxt.Test__find_1st(hash,					0, "a b x");
		fxt.Test__find_1st(hash,  Byte_.Max_value_127, "a");
		fxt.Test__find_1st(hash,  Byte_.Max_value_127, "xyz");
	}
}
class Html_atr_cls__fxt {
	public void Test__has__y(String src, String... ary) {Test__has(Bool_.Y, src, ary);}
	public void Test__has__n(String src, String... ary) {Test__has(Bool_.N, src, ary);}
	public void Test__has(boolean expd, String src, String... ary) {
		byte[] src_bry = Bry_.new_u8(src);
		for (String itm : ary) {
			byte[] itm_bry = Bry_.new_u8(itm);
			Tfds.Eq_bool(expd, Html_atr_cls_.Has(src_bry, 0, src_bry.length, itm_bry), itm);
		}
	}
	public Hash_adp_bry Make_hash(String... ary) {
		Hash_adp_bry rv = Hash_adp_bry.ci_a7();
		int len = ary.length;
		for (int i = 0; i < len; ++i)
			rv.Add_bry_byte(Bry_.new_u8(ary[i]), (byte)i);
		return rv;
	}
	public void Test__find_1st(Hash_adp_bry hash, int expd, String src) {
		byte[] src_bry = Bry_.new_u8(src);
		Tfds.Eq_byte((byte)expd, Html_atr_cls_.Find_1st(src_bry, 0, src_bry.length, hash), src);
	}
}
