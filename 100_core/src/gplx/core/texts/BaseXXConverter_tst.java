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
package gplx.core.texts; import gplx.*; import gplx.core.*;
import org.junit.*;
public class BaseXXConverter_tst {
	@Test  public void Base32() {
		tst_Base32("", "");
		tst_Base32("f", "MY");
		tst_Base32("fo", "MZXQ");
		tst_Base32("foo", "MZXW6");
		tst_Base32("foob", "MZXW6YQ");
		tst_Base32("fooba", "MZXW6YTB");
		tst_Base32("foobar", "MZXW6YTBOI");
		tst_Base32("A", "IE");
		tst_Base32("a", "ME");
		tst_Base32("ABCDEFGHIJKLMNOPQRSTUVWXYZ234567", "IFBEGRCFIZDUQSKKJNGE2TSPKBIVEU2UKVLFOWCZLIZDGNBVGY3Q");
	}
	@Test  public void Base64() {
		tst_Base64("", "");
		tst_Base64("f", "Zg==");
		tst_Base64("fo", "Zm8=");
		tst_Base64("foo", "Zm9v");
		tst_Base64("foob", "Zm9vYg==");
		tst_Base64("fooba", "Zm9vYmE=");
		tst_Base64("foobar", "Zm9vYmFy");
//			tst_Base64("A", "IE");
//			tst_Base64("a", "ME");
//			tst_Base64("ABCDEFGHIJKLMNOPQRSTUVWXYZ234567", "IFBEGRCFIZDUQSKKJNGE2TSPKBIVEU2UKVLFOWCZLIZDGNBVGY3Q");
	}
	void tst_Base32(String orig, String expd) {
		String actl = Base32Converter.EncodeString(orig);
		Tfds.Eq(expd, actl);
		String decode = Base32Converter.DecodeString(actl);
		Tfds.Eq(orig, decode);
	}
	void tst_Base64(String orig, String expd) {
		String actl = Base64Converter.EncodeString(orig);
		Tfds.Eq(expd, actl);
		String decode = Base64Converter.DecodeString(actl);
		Tfds.Eq(orig, decode);
	}
}
//http://tools.ietf.org/html/rfc4648: test vectors for "foobar"
