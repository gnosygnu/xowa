/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2021 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.types.custom.brys;
import gplx.types.basics.utls.BryLni;
import gplx.types.basics.utls.BryUtl;
import gplx.types.commons.lists.GfoListBase;
import gplx.types.basics.constants.AsciiByte;
import gplx.frameworks.tests.GfoTstr;
import gplx.types.basics.utls.BoolUtl;
import gplx.types.basics.utls.StringUtl;
import org.junit.Test;
public class BrySplitWkrTest {
	private final BrySplitWkrTstr tstr = new BrySplitWkrTstr();
	@Test public void Split() {
		tstr.TestSplit("a"              , AsciiByte.Pipe, BoolUtl.N, "a");                     // no trim
		tstr.TestSplit("a|"             , AsciiByte.Pipe, BoolUtl.N, "a");
		tstr.TestSplit("|a"             , AsciiByte.Pipe, BoolUtl.N, "", "a");
		tstr.TestSplit("|"              , AsciiByte.Pipe, BoolUtl.N, "");
		tstr.TestSplit(""               , AsciiByte.Pipe, BoolUtl.N);
		tstr.TestSplit("a|b|c"          , AsciiByte.Pipe, BoolUtl.N, "a", "b", "c");
		tstr.TestSplit(" a "            , AsciiByte.Pipe, BoolUtl.Y, "a");                     // trim
		tstr.TestSplit(" a |"           , AsciiByte.Pipe, BoolUtl.Y, "a");
		tstr.TestSplit("| a "           , AsciiByte.Pipe, BoolUtl.Y, "", "a");
		tstr.TestSplit(" | "            , AsciiByte.Pipe, BoolUtl.Y, "");
		tstr.TestSplit(" "              , AsciiByte.Pipe, BoolUtl.Y);
		tstr.TestSplit(" a | b | c "    , AsciiByte.Pipe, BoolUtl.Y, "a", "b", "c");
		tstr.TestSplit(" a b | c d "    , AsciiByte.Pipe, BoolUtl.Y, "a b", "c d");
		tstr.TestSplit(" a \n b "       , AsciiByte.Nl  , BoolUtl.N, " a ", " b ");            // ws as dlm
		tstr.TestSplit(" a \n b "       , AsciiByte.Nl  , BoolUtl.Y, "a", "b");                // ws as dlm; trim
		tstr.TestSplit("a|extend|b"     , AsciiByte.Pipe, BoolUtl.Y, "a", "extend|b");         // extend
		tstr.TestSplit("extend|a"       , AsciiByte.Pipe, BoolUtl.Y, "extend|a");              // extend
		tstr.TestSplit("a|cancel|b"     , AsciiByte.Pipe, BoolUtl.Y, "a");                     // cancel
	}
	@Test public void SplitBry() {
		tstr.TestSplit("a|b|c|d"        , 2, 6, "|", "b", "c");
		tstr.TestSplit("a|b|c|d"        , 2, 4, "|", "b");
	}
	@Test public void Empty() {
		tstr.TestSplit("a\n\nb"         , AsciiByte.Nl, BoolUtl.N, "a", "", "b");
	}
	@Test public void SplitWithMax() {
		tstr.TestSplitWithMax("a|b|c|d"              , AsciiByte.Pipe, 2, "a", "b");         // max is less
		tstr.TestSplitWithMax("a"                    , AsciiByte.Pipe, 2, "a", null);        // max is more
		tstr.TestSplitWithMax("|"                    , AsciiByte.Pipe, 2, "", "");           // empty itms
	}
	@Test public void SplitWs() {
		tstr.TestSplitWs("a b", "a", "b");
		tstr.TestSplitWs(" a ", "a");
		tstr.TestSplitWs("  abc   def  ", "abc", "def");
	}
}
class BrySplitWkrTstr {
	private final BrySplitWkrExample wkr = new BrySplitWkrExample();
	public void TestSplit(String raw_str, byte dlm, boolean trim, String... expd) {
		byte[] src = BryUtl.NewA7(raw_str);
		BrySplit.Split(src, 0, src.length, dlm, trim, wkr);
		byte[][] actl_ary = wkr.ToAry();
		GfoTstr.EqLines(expd, StringUtl.Ary(actl_ary));
	}
	public void TestSplit(String src, int src_bgn, int src_end, String dlm, String... expd) {
		GfoTstr.EqLines(BryUtl.Ary(expd), BrySplit.Split(BryUtl.NewU8(src), src_bgn, src_end, BryUtl.NewU8(dlm)));
	}
	public void TestSplitWithMax(String src, byte dlm, int max, String... expd) {
		GfoTstr.EqLines(expd, StringUtl.Ary(BrySplit.SplitWithmax(BryUtl.NewU8(src), dlm, max)));
	}
	public void TestSplitWs(String raw, String... expd) {
		byte[][] actl = BrySplit.SplitWs(BryUtl.NewU8(raw));
		GfoTstr.EqLines(BryUtl.Ary(expd), actl, raw);
	}
}
class BrySplitWkrExample implements BrySplitWkr {
	private final GfoListBase<byte[]> list = new GfoListBase<>();
	public int Split(byte[] src, int itm_bgn, int itm_end) {
		byte[] bry = itm_end == itm_bgn ? BryUtl.Empty : BryLni.Mid(src, itm_bgn, itm_end);
		if      (BryLni.Eq(bry, BryUtl.NewA7("extend"))) return BrySplit.Rv__extend;
		else if (BryLni.Eq(bry, BryUtl.NewA7("cancel"))) return BrySplit.Rv__cancel;
		list.Add(bry);
		return BrySplit.Rv__ok;
	}
	public byte[][] ToAry() {
		return list.ToAryAndClear(byte[].class);
	}
}
