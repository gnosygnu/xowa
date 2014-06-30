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
package gplx.texts; import gplx.*;
import org.junit.*;
public class BaseXXConverter_tst {
	@Test public void Base32() {
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
	@Test public void Base64() {
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
