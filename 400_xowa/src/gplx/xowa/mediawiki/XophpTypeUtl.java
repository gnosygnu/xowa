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
public class XophpTypeUtl {
	// REF.PHP: https://www.php.net/manual/en/function.is-scalar.php
	public static boolean is_scalar(Object obj) {
		if (obj == null) return false;
		int type_id = Type_ids_.To_id_by_type(obj.getClass());
		switch (type_id) {
			case Type_ids_.Id__int:
			case Type_ids_.Id__float:
			case Type_ids_.Id__str:
			case Type_ids_.Id__bool:
				return true;
			default:
				return false;
		}
	}
}
