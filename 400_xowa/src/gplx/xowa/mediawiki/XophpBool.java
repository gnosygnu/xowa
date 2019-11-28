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
package gplx.xowa.mediawiki; import gplx.*; import gplx.xowa.*;
public class XophpBool {
	public static boolean is_true(byte[] val) {return val != null && is_true(String_.new_u8(val));}
	public static boolean is_true(String val) {
		// REF: https://www.php.net/manual/en/language.types.boolean.php#language.types.boolean.casting
		if (String_.Len_eq_0(val)) return false; // the empty String ...
		String val_upper = String_.Upper(val);
		if (String_.Eq(val_upper, "FALSE")) { // the boolean FALSE itself
			return false;
		}
		double val_double = Double_.parse_or(val, -1); // pick "-1" as marker for invalid doubles; only care about comparing to "0"
		// the integers 0 and -0 (zero)
		// the floats 0.0 and -0.0 (zero)
		// ... and the String "0"
		if (val_double == 0) {
			return false;
		}
		// Skip these as they shouldn't render in String serialization
		// * an array with zero elements
		// * the special type NULL (including unset variables)
		// * SimpleXML objects created from empty tags
		return true;
	}
}

