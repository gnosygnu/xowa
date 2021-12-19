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
package gplx.types.basics.strings.unicodes;
import gplx.frameworks.tests.GfoTstr;
import gplx.types.basics.utls.BryUtl;
import gplx.types.basics.wrappers.IntRef;
import org.junit.Test;
public class Utf16UtlTest {
	private Utf16UtlTstr tstr = new Utf16UtlTstr();
	@Test public void EncodeDecode() {
		tstr.TestEncodeDecode(162, 194, 162);                // cent; ¢
		tstr.TestEncodeDecode(8364, 226, 130, 172);        // euro; €
		tstr.TestEncodeDecode(150370, 240, 164, 173, 162);    // example from [[UTF-8]]; should be encoded as two bytes; 𤭢
		tstr.TestEncodeDecode(143489, 240, 163, 130, 129); // EX: 駣𣂁脁 DATE:2017-04-22; 𣂁
	}
	@Test public void Surrogate() {
		tstr.TestSurrogate(0x64321, 0xD950, 0xDF21);    // example from w:UTF-16
		tstr.TestSurrogate(66643, 55297, 56403);        // example from d:Boomerang
	}
}
class Utf16UtlTstr {
	private IntRef hiRef = IntRef.NewNeg1(), loRef = IntRef.NewNeg1();
	public void TestEncodeDecode(int expd_c_int, int... expd_int) {
		byte[] expd = BryUtl.NewByInts(expd_int);
		byte[] bfr = new byte[10];
		int bfr_len = Utf16Utl.EncodeInt(expd_c_int, bfr, 0);
		byte[] actl = BryUtl.MidByLen(bfr, 0, bfr_len);
		GfoTstr.Eq(expd, actl);
		int actl_c_int = Utf16Utl.DecodeToInt(bfr, 0);
		GfoTstr.Eq(expd_c_int, actl_c_int);
	}
	public void TestSurrogate(int v, int hi, int lo) {
		GfoTstr.Eq(v, Utf16Utl.SurrogateMerge((char)hi, (char)lo));
		Utf16Utl.SurrogateSplit(v, hiRef, loRef);
		GfoTstr.Eq(hi, hiRef.Val());
		GfoTstr.Eq(lo, loRef.Val());
	}
}
