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
public class XophpType {
	private final    int type_id;
	public XophpType(int type_id) {
		this.type_id = type_id;
	}
	public boolean is_string() {
		return type_id == Type_ids_.Id__str;
	}
	public boolean is_array() {
		return type_id == Type_ids_.Id__array;
	}
	public static XophpType New(Object o) {			
		return new XophpType(Type_ids_.To_id_by_obj(o));
	}
}
