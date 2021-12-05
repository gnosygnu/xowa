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
import gplx.core.tests.Gftest;
import gplx.objects.primitives.BoolUtl;
import gplx.objects.strings.AsciiByte;
import org.junit.Test;
public class Bry_split__tst {
	private final Bry_split__fxt fxt = new Bry_split__fxt();
	@Test  public void Split() {
		fxt.Test_split("a"				, AsciiByte.Pipe, BoolUtl.N, "a");					// no trim
		fxt.Test_split("a|"				, AsciiByte.Pipe, BoolUtl.N, "a");
		fxt.Test_split("|a"				, AsciiByte.Pipe, BoolUtl.N, "", "a");
		fxt.Test_split("|"				, AsciiByte.Pipe, BoolUtl.N, "");
		fxt.Test_split(""				, AsciiByte.Pipe, BoolUtl.N);
		fxt.Test_split("a|b|c"			, AsciiByte.Pipe, BoolUtl.N, "a", "b", "c");
		fxt.Test_split(" a "			, AsciiByte.Pipe, BoolUtl.Y, "a");					// trim
		fxt.Test_split(" a |"			, AsciiByte.Pipe, BoolUtl.Y, "a");
		fxt.Test_split("| a "			, AsciiByte.Pipe, BoolUtl.Y, "", "a");
		fxt.Test_split(" | "			, AsciiByte.Pipe, BoolUtl.Y, "");
		fxt.Test_split(" "				, AsciiByte.Pipe, BoolUtl.Y);
		fxt.Test_split(" a | b | c "	, AsciiByte.Pipe, BoolUtl.Y, "a", "b", "c");
		fxt.Test_split(" a b | c d "	, AsciiByte.Pipe, BoolUtl.Y, "a b", "c d");
		fxt.Test_split(" a \n b "		, AsciiByte.Nl  , BoolUtl.N, " a ", " b ");			// ws as dlm
		fxt.Test_split(" a \n b "		, AsciiByte.Nl  , BoolUtl.Y, "a", "b");				// ws as dlm; trim
		fxt.Test_split("a|extend|b"		, AsciiByte.Pipe, BoolUtl.Y, "a", "extend|b");		// extend
		fxt.Test_split("extend|a"		, AsciiByte.Pipe, BoolUtl.Y, "extend|a");			// extend
		fxt.Test_split("a|cancel|b"		, AsciiByte.Pipe, BoolUtl.Y, "a");					// cancel
	}
	@Test  public void Split__bry() {
		fxt.Test_split("a|b|c|d"		, 2, 6, "|", "b", "c");
		fxt.Test_split("a|b|c|d"		, 2, 4, "|", "b");
	}
	@Test  public void Empty() {
		fxt.Test_split("a\n\nb"         , AsciiByte.Nl, BoolUtl.N, "a", "", "b");
	}
	@Test  public void Split_w_max() {
		fxt.Test__split_w_max("a|b|c|d"              , AsciiByte.Pipe, 2, "a", "b");		// max is less
		fxt.Test__split_w_max("a"                    , AsciiByte.Pipe, 2, "a", null);		// max is more
		fxt.Test__split_w_max("|"                    , AsciiByte.Pipe, 2, "", "");		    // empty itms
	}
	@Test public void Split_ws() {
		fxt.Test__split_ws("a b", "a", "b");
		fxt.Test__split_ws(" a ", "a");
		fxt.Test__split_ws("  abc   def  ", "abc", "def");
	}
}
class Bry_split__fxt {
	private final Bry_split_wkr__example wkr = new Bry_split_wkr__example();
	public void Test_split(String raw_str, byte dlm, boolean trim, String... expd) {
		byte[] src = Bry_.new_a7(raw_str);
		Bry_split_.Split(src, 0, src.length, dlm, trim, wkr);
		byte[][] actl_ary = wkr.To_ary();
		Tfds.Eq_ary_str(expd, String_.Ary(actl_ary));
	}
	public void Test_split(String src, int src_bgn, int src_end, String dlm, String... expd) {
		Tfds.Eq_ary_str(Bry_.Ary(expd), Bry_split_.Split(Bry_.new_u8(src), src_bgn, src_end, Bry_.new_u8(dlm)));
	}
	public void Test__split_w_max(String src, byte dlm, int max, String... expd) {
		Gftest.Eq__ary(expd, String_.Ary(Bry_split_.Split_w_max(Bry_.new_u8(src), dlm, max)));
	}
	public void Test__split_ws(String raw, String... expd) {
		byte[][] actl = Bry_split_.Split_ws(Bry_.new_u8(raw));
		Gftest.Eq__ary(Bry_.Ary(expd), actl, raw);
	}
}
class Bry_split_wkr__example implements gplx.core.brys.Bry_split_wkr {
	private final List_adp list = List_adp_.New();
	public int Split(byte[] src, int itm_bgn, int itm_end) {
		byte[] bry = itm_end == itm_bgn ? Bry_.Empty : Bry_.Mid(src, itm_bgn, itm_end);
		if		(Bry_.Eq(bry, Bry_.new_a7("extend"))) return Bry_split_.Rv__extend;
		else if (Bry_.Eq(bry, Bry_.new_a7("cancel"))) return Bry_split_.Rv__cancel;
		list.Add(bry);
		return Bry_split_.Rv__ok;
	}
	public byte[][] To_ary() {
		return (byte[][])list.ToAryAndClear(byte[].class);
	}
}
