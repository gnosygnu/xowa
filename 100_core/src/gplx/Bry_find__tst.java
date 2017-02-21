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
package gplx;
import org.junit.*; import gplx.core.tests.*;
public class Bry_find__tst {
	private Bry_find__fxt fxt = new Bry_find__fxt();
	@Test  public void Find_fwd() {
		fxt.Test_Find_fwd("abcba",  "b", 0, 1);
		fxt.Test_Find_fwd("abcba",  "z", 0, -1);
		fxt.Test_Find_fwd("abcba",  "b", 1, 1);
		fxt.Test_Find_fwd("abcba",  "b", 2, 3);
		fxt.Test_Find_fwd("abcba",  "b", 4, -1);
		fxt.Test_Find_fwd("abcba", "zb", 4, -1);
		fxt.Test_Find_fwd("abcba",  "a", 6, -1);
	}
	@Test  public void Find_bwd() {
		fxt.Test_Find_bwd("abcba",  "b", 4, 3);
		fxt.Test_Find_bwd("abcba",  "z", 4, -1);
		fxt.Test_Find_bwd("abcba",  "b", 3, 1);
		fxt.Test_Find_bwd("abcba",  "b", 2, 1);
		fxt.Test_Find_bwd("abcba",  "b", 0, -1);
		fxt.Test_Find_bwd("abcba", "zb", 4, -1);
		fxt.Test_Find_fwd("abcba",  "a", -1, -1);
		fxt.Test_Find_bwd("abcba", "ab", 4, 0);
	}
	@Test  public void Find_bwd_last_ws() {
		fxt.Test_Find_bwd_1st_ws_tst("a b"			, 2, 1);					// basic
		fxt.Test_Find_bwd_1st_ws_tst("a   b"		, 3, 1);					// multiple
		fxt.Test_Find_bwd_1st_ws_tst("ab"			, 1, Bry_find_.Not_found);		// none
	}
	@Test  public void Trim_fwd_space_tab() {
		fxt.Test_Trim_fwd_space_tab(" a b"			, 1);
		fxt.Test_Trim_fwd_space_tab("\ta b"			, 1);
		fxt.Test_Trim_fwd_space_tab(" \ta b"		, 2);
		fxt.Test_Trim_fwd_space_tab("a bc"			, 0);
		fxt.Test_Trim_fwd_space_tab(""				, 0);
		fxt.Test_Trim_fwd_space_tab(" \t"			, 2);
	}
	@Test  public void Trim_bwd_space_tab() {
		fxt.Test_Trim_bwd_space_tab("a b "			, 3);
		fxt.Test_Trim_bwd_space_tab("a b\t"			, 3);
		fxt.Test_Trim_bwd_space_tab("a b\t "		, 3);
		fxt.Test_Trim_bwd_space_tab("a bc"			, 4);
		fxt.Test_Trim_bwd_space_tab(""				, 0);
		fxt.Test_Trim_bwd_space_tab(" \t"			, 0);
	}
	@Test  public void Find_fwd_while_in() {
		boolean[] while_ary = fxt.Init__find_fwd_while_in(Byte_ascii.Space, Byte_ascii.Tab, Byte_ascii.Nl);
		fxt.Test__find_fwd_while_in(" \t\na", while_ary, 3);
	}
}
class Bry_find__fxt {
	public void Test_Find_fwd(String src, String lkp, int bgn, int expd) {Tfds.Eq(expd, Bry_find_.Find_fwd(Bry_.new_u8(src), Bry_.new_u8(lkp), bgn));}
	public void Test_Find_bwd(String src, String lkp, int bgn, int expd) {Tfds.Eq(expd, Bry_find_.Find_bwd(Bry_.new_u8(src), Bry_.new_u8(lkp), bgn));}
	public void Test_Find_bwd_1st_ws_tst(String src, int pos, int expd) {Tfds.Eq(expd, Bry_find_.Find_bwd_last_ws(Bry_.new_a7(src), pos));}
	public void Test_Trim_bwd_space_tab(String raw_str, int expd) {
		byte[] raw_bry = Bry_.new_u8(raw_str);
		int actl = Bry_find_.Trim_bwd_space_tab(raw_bry, raw_bry.length, 0);
		Tfds.Eq(expd, actl, raw_str);
	}
	public void Test_Trim_fwd_space_tab(String raw_str, int expd) {
		byte[] raw_bry = Bry_.new_u8(raw_str);
		int actl = Bry_find_.Trim_fwd_space_tab(raw_bry, 0, raw_bry.length);
		Tfds.Eq(expd, actl, raw_str);
	}
	public boolean[] Init__find_fwd_while_in(byte... ary) {
		boolean[] rv = new boolean[256];
		int len = ary.length;
		for (int i = 0; i < len; i++)
			rv[ary[i]] = true;
		return rv;
	}
	public void Test__find_fwd_while_in(String src, boolean[] ary, int expd) {
		byte[] src_bry = Bry_.new_u8(src);
		Gftest.Eq__int(expd, Bry_find_.Find_fwd_while_in(src_bry, 0, src_bry.length, ary));
	}
}
