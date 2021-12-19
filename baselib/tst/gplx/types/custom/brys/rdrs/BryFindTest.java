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
package gplx.types.custom.brys.rdrs;
import gplx.types.basics.utls.BryUtl;
import gplx.types.basics.constants.AsciiByte;
import gplx.frameworks.tests.GfoTstr;
import gplx.types.custom.brys.BryFind;
import org.junit.Test;
public class BryFindTest {
	private BryFindTstr tstr = new BryFindTstr();
	@Test public void FindFwd() {
		tstr.TestFindFwd("abcba",  "b", 0, 1);
		tstr.TestFindFwd("abcba",  "z", 0, -1);
		tstr.TestFindFwd("abcba",  "b", 1, 1);
		tstr.TestFindFwd("abcba",  "b", 2, 3);
		tstr.TestFindFwd("abcba",  "b", 4, -1);
		tstr.TestFindFwd("abcba", "zb", 4, -1);
		tstr.TestFindFwd("abcba",  "a", 6, -1);
	}
	@Test public void FindBwd() {
		tstr.TestFindBwd("abcba",  "b", 4, 3);
		tstr.TestFindBwd("abcba",  "z", 4, -1);
		tstr.TestFindBwd("abcba",  "b", 3, 1);
		tstr.TestFindBwd("abcba",  "b", 2, 1);
		tstr.TestFindBwd("abcba",  "b", 0, -1);
		tstr.TestFindBwd("abcba", "zb", 4, -1);
		tstr.TestFindFwd("abcba",  "a", -1, -1);
		tstr.TestFindBwd("abcba", "ab", 4, 0);
	}
	@Test public void FindBwdLastWs() {
		tstr.TestFindBwdLastWs("a b"            , 2, 1);                    // basic
		tstr.TestFindBwdLastWs("a   b"        , 3, 1);                    // multiple
		tstr.TestFindBwdLastWs("ab"            , 1, BryFind.NotFound);        // none
	}
	@Test public void TrimFwdSpaceTab() {
		tstr.TestTrimFwdSpaceTab(" a b"            , 1);
		tstr.TestTrimFwdSpaceTab("\ta b"            , 1);
		tstr.TestTrimFwdSpaceTab(" \ta b"        , 2);
		tstr.TestTrimFwdSpaceTab("a bc"            , 0);
		tstr.TestTrimFwdSpaceTab(""                , 0);
		tstr.TestTrimFwdSpaceTab(" \t"            , 2);
	}
	@Test public void TrimBwdSpaceTab() {
		tstr.TestTrimBwdSpaceTab("a b "            , 3);
		tstr.TestTrimBwdSpaceTab("a b\t"            , 3);
		tstr.TestTrimBwdSpaceTab("a b\t "        , 3);
		tstr.TestTrimBwdSpaceTab("a bc"            , 4);
		tstr.TestTrimBwdSpaceTab(""                , 0);
		tstr.TestTrimBwdSpaceTab(" \t"            , 0);
	}
	@Test public void FindFwdWhileIn() {
		boolean[] while_ary = tstr.InitFindFwdWhileIn(AsciiByte.Space, AsciiByte.Tab, AsciiByte.Nl);
		tstr.TestFindFwdWhileIn(" \t\na", while_ary, 3);
	}
}
class BryFindTstr {
	public void TestFindFwd(String src, String lkp, int bgn, int expd) {GfoTstr.Eq(expd, BryFind.FindFwd(BryUtl.NewU8(src), BryUtl.NewU8(lkp), bgn));}
	public void TestFindBwd(String src, String lkp, int bgn, int expd) {GfoTstr.Eq(expd, BryFind.FindBwd(BryUtl.NewU8(src), BryUtl.NewU8(lkp), bgn));}
	public void TestFindBwdLastWs(String src, int pos, int expd) {GfoTstr.Eq(expd, BryFind.FindBwdLastWs(BryUtl.NewA7(src), pos));}
	public void TestTrimBwdSpaceTab(String raw_str, int expd) {
		byte[] raw_bry = BryUtl.NewU8(raw_str);
		int actl = BryFind.TrimBwdSpaceTab(raw_bry, raw_bry.length, 0);
		GfoTstr.Eq(expd, actl, raw_str);
	}
	public void TestTrimFwdSpaceTab(String raw_str, int expd) {
		byte[] raw_bry = BryUtl.NewU8(raw_str);
		int actl = BryFind.TrimFwdSpaceTab(raw_bry, 0, raw_bry.length);
		GfoTstr.Eq(expd, actl, raw_str);
	}
	public boolean[] InitFindFwdWhileIn(byte... ary) {
		boolean[] rv = new boolean[256];
		int len = ary.length;
		for (int i = 0; i < len; i++)
			rv[ary[i]] = true;
		return rv;
	}
	public void TestFindFwdWhileIn(String src, boolean[] ary, int expd) {
		byte[] src_bry = BryUtl.NewU8(src);
		GfoTstr.Eq(expd, BryFind.FindFwdWhileIn(src_bry, 0, src_bry.length, ary));
	}
}
