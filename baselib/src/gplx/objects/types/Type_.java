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
package gplx.objects.types; import gplx.*; import gplx.objects.*;
public class Type_ {
	public static boolean Eq(Class<?> lhs, Class<?> rhs) {// DUPE_FOR_TRACKING: same as Object_.Eq
		if      (lhs == null && rhs == null) return true;
		else if (lhs == null || rhs == null) return false;
		else                                 return lhs.equals(rhs);
	}
}
