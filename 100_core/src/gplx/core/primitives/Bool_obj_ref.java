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
public class Bool_obj_ref {
	public boolean Val() {return val;} private boolean val;
	public boolean Val_y() {return val;}
	public boolean Val_n() {return !val;}
	public String Val_as_str_yn() {return Yn.To_str(val);}
	public Bool_obj_ref Val_y_() {val = true; return this;}
	public Bool_obj_ref Val_n_() {val = false; return this;}
	public Bool_obj_ref Val_(boolean v) {val = v; return this;}
	public Bool_obj_ref Val_toggle_() {val = !val; return this;}
	@Override public String toString() {return Bool_.To_str_lower(val);}
        public static Bool_obj_ref n_() {return new_(false);}
        public static Bool_obj_ref y_() {return new_(true);}
        public static Bool_obj_ref new_(boolean val) {
		Bool_obj_ref rv = new Bool_obj_ref();
		rv.val = val;
		return rv;
	}	Bool_obj_ref() {}
}
