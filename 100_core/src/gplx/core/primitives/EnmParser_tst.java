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
package gplx.core.primitives; import gplx.*; import gplx.core.*;
import org.junit.*;
public class EnmParser_tst {
	@Before public void setup() {
		parser = EnmMgr.new_();
	}
	@Test  public void Basic() { // 1,2,4,8
		parser.BitRngEnd_(8);
		run_Reg(0, "zero");
		run_Reg(1, "one");
		run_Reg(2, "two");
		run_Reg(4, "four");
		run_Reg(8, "eight");

		tst_Convert("zero", 0);
		tst_Convert("one", 1);
		tst_Convert("eight", 8);
		tst_Convert("one+eight", 9);
	}
	@Test  public void Keys() {
		parser.BitRngBgn_(65536).BitRngEnd_(262144);
		run_Reg(	65, "a");
		run_Reg( 65536, "shift");
		run_Reg(131072, "ctrl");
		run_Reg(262144, "alt");
		tst_Convert("a", 65);
		tst_Convert("shift+a", 65 + 65536);
		tst_Convert("ctrl+a", 65 + 131072);
		tst_Convert("shift+ctrl+a", 65 + 65536 + 131072);
	}
	@Test  public void Prefix() {
		parser.Prefix_("key.").BitRngBgn_(128).BitRngEnd_(128);
		run_Reg(65, "a");
		tst_Convert("key.a", 65);
	}
	void run_Reg(int i, String s) {parser.RegObj(i, s, "NULL");}
	void tst_Convert(String raw, int val) {
		int actlVal = parser.GetVal(raw);
		Tfds.Eq(val, actlVal);
		Tfds.Eq(raw, parser.GetStr(val));
	}
	EnmMgr parser;
}
