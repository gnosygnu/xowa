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
package gplx.core.ios; import gplx.*; import gplx.core.*;
public class Io_url_obj_ref {
	public Io_url Val() {return val;} public Io_url_obj_ref Val_(Io_url v) {val = v; return this;} private Io_url val;
	public String Val_as_str() {return val.Raw();}
	@Override public String toString() {return val.Raw();}
	@Override public int hashCode() {return val.hashCode();}
	@Override public boolean equals(Object obj) {return String_.Eq(val.Raw(), ((Io_url_obj_ref)obj).val.Raw());}
        public static Io_url_obj_ref new_(Io_url val) {
		Io_url_obj_ref rv = new Io_url_obj_ref();
		rv.val = val;
		return rv;
	}	Io_url_obj_ref() {}
}
