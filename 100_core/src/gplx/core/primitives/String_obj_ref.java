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
package gplx.core.primitives; import gplx.*; import gplx.core.*;
public class String_obj_ref {
	public String Val() {return val;} public String_obj_ref Val_(String v) {val = v; return this;} private String val;
	public String_obj_ref Val_null_() {return Val_(null);}
	@Override public String toString() {return val;}
	public static String_obj_ref empty_() {return new_("");}
	public static String_obj_ref null_() {return new_(null);}
        public static String_obj_ref new_(String val) {
		String_obj_ref rv = new String_obj_ref();
		rv.val = val;
		return rv;
	}	String_obj_ref() {}
}
