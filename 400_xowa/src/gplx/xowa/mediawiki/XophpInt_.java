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
public class XophpInt_ {
	public static final int False = 0; // REF.PHP:https://www.php.net/manual/en/language.types.boolean.php
	public static boolean is_true(int val) {return val != 0;} // handles code like "if ($var)" where var is an Object;
	public static boolean is_false(int val) {return val < 0;} // handles XophpInt_.False as well as strpos.notFound (-1)
	public static String strval(int number) {
		return Int_.To_str(number);
	}
	public static int intval(String val) {
		return Int_.Parse_or(val, 0);
	}
	public static int cast(Object val) {
		return Int_.Cast(val);
	}
}
