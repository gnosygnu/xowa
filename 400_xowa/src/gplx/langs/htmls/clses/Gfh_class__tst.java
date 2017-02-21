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
package gplx.langs.htmls.clses; import gplx.*; import gplx.langs.*; import gplx.langs.htmls.*;
import org.junit.*;
public class Gfh_class__tst {
	private final Gfh_class__fxt fxt = new Gfh_class__fxt();
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
class Gfh_class__fxt {
	public void Test__has__y(String src, String... ary) {Test__has(Bool_.Y, src, ary);}
	public void Test__has__n(String src, String... ary) {Test__has(Bool_.N, src, ary);}
	public void Test__has(boolean expd, String src, String... ary) {
		byte[] src_bry = Bry_.new_u8(src);
		for (String itm : ary) {
			byte[] itm_bry = Bry_.new_u8(itm);
			Tfds.Eq_bool(expd, Gfh_class_.Has(src_bry, 0, src_bry.length, itm_bry), itm);
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
		Tfds.Eq_byte((byte)expd, Gfh_class_.Find_1st(src_bry, 0, src_bry.length, hash), src);
	}
}
