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
package gplx.xowa.mediawiki;
import gplx.types.basics.utls.DoubleUtl;
public class XophpFloat_ {
	// REF.PHP:https://www.php.net/manual/en/language.types.float.php
	public static double floatval(String val) {
		return DoubleUtl.Parse(val); // NOTE:PHP float has roughly 14 decimal digits of precision which is more similar to Java's double than float
	}
}
