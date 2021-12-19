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
package gplx.types.basics.encoders;
import gplx.frameworks.tests.GfoTstr;
import gplx.types.basics.utls.BryUtl;
import gplx.types.basics.utls.ByteUtl;
import gplx.types.basics.utls.IntUtl;
import gplx.types.basics.utls.StringUtl;
import org.junit.Test;
public class HexUtlTest {
	private final HexUtlTstr tstr = new HexUtlTstr();
	@Test public void To_int() {
		tstr.TestParse("0"        , 0);
		tstr.TestParse("F"        , 15);
		tstr.TestParse("0F"        , 15);
		tstr.TestParse("10"        , 16);
		tstr.TestParse("20"        , 32);
		tstr.TestParse("FF"        , 255);
		tstr.TestParse("100"        , 256);
		tstr.TestParse("0a"        , 10);
		tstr.TestParse("7FFFFFFF"    , IntUtl.MaxValue);
		tstr.TestParseOr("100"    , 256);
	}
	@Test public void To_str() {
		tstr.TestToStr(0            , "0");
		tstr.TestToStr(15            , "F");
		tstr.TestToStr(16            , "10");
		tstr.TestToStr(32            , "20");
		tstr.TestToStr(255        , "FF");
		tstr.TestToStr(IntUtl.MaxValue, "7FFFFFFF");

		tstr.TestToStr(15, 2        , "0F");
		tstr.TestToStr(15, 3        , "00F");
	}
	@Test public void Write() {
		tstr.TestWrite("[00000000]", 1, 9,  15, "[0000000F]");
		tstr.TestWrite("[00000000]", 1, 9, 255, "[000000FF]");
	}
	@Test public void Encode() {
		tstr.TestParseHexToBry("E2A7BC", 226, 167, 188);
	}
}
class HexUtlTstr {
	public void TestWrite(String s, int bgn, int end, int val, String expd) {
		byte[] bry = BryUtl.NewA7(s);
		HexUtl.Write(bry, bgn, end, val);
		GfoTstr.Eq(expd, StringUtl.NewA7(bry));
	}
	public void TestParse(String raw, int expd) {
		int actl = HexUtl.Parse(raw);
		GfoTstr.Eq(expd, actl);
	}
	public void TestParseOr(String raw, int expd) {GfoTstr.Eq(expd, HexUtl.ParseOr(BryUtl.NewA7(raw), -1));}
	public void TestToStr(int val, String expd) {TestToStr(val, 0, expd);}
	public void TestToStr(int val, int pad, String expd) {
		String actl = HexUtl.ToStr(val, pad);
		GfoTstr.Eq(expd, actl);
	}
	public void TestParseHexToBry(String val, int... expd) {
		byte[] actl = HexUtl.ParseHexToBry(BryUtl.NewU8(val));
		GfoTstr.EqAry(ByteUtl.AryByInts(expd), actl, "encode");
	}
}
