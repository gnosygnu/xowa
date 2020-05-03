/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2020 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.xowa.mediawiki.includes.exception; import gplx.*; import gplx.xowa.*; import gplx.xowa.mediawiki.*; import gplx.xowa.mediawiki.includes.*;
import gplx.core.strings.*;
public class XomwMWException extends Err {
	public XomwMWException(String fmt, Object... args) {
		super(true, "", "", String_.Format(fmt, args));
	}
	public static Err New_by_method(Class<?> type, String method, String msg) {
		return Err_.new_wo_type(Type_.Name(type) + "." + method + ":" + msg);
	}
	public static Err New_by_method_obj(Object obj, String method, String msg) {
		return Err_.new_wo_type(Type_.Name_by_obj(obj) + "." + method + msg);
	}
}
