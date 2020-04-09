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
import gplx.core.brys.*;
public class XophpArrayItm implements Bry_bfr_able {
	XophpArrayItm(boolean key_is_int, int key_as_int, String key, Object val) {
		this.key_is_int = key_is_int;
		this.key_as_int = key_as_int;
		this.key = key;
		this.val = val;
	}
	public boolean Key_is_int() {return key_is_int;} private final    boolean key_is_int;
	public int Key_as_int() {return key_as_int;} private final    int key_as_int;
	public String Key() {return key;} private final    String key;
	public Object Val() {return val;} public void Val_(Object v) {this.val = v;} private Object val;

	public void To_bfr(Bry_bfr bfr) {
		bfr.Add_str_u8(key).Add_byte_eq();

		if (Type_.Type_by_obj(val) == XophpArray.class) {
			XophpArray sub_ary = (XophpArray)val;
			bfr.Add_byte_nl();
			sub_ary.To_bfr(bfr);
		}
		else {
			bfr.Add_obj(val).Add_byte_space();
		}
	}
	@Override public boolean equals(Object obj) {
		if (obj == null) return false;
		XophpArrayItm comp = (XophpArrayItm)obj;

		// compare key
		if (key_is_int) {
			if (this.key_as_int != comp.key_as_int)
				return false;
		}
		else {
			if (!String_.Eq(this.key, comp.key))
				return false;
		}

		// compare val
		if      (this.val == null && comp.val != null)
			return false;
		else if (this.val != null && comp.val == null)
			return false;
		else if (this.val == null && comp.val == null)
			return true;
		else
			return Object_.Eq(this.val, comp.val);
	}
	@Override public int hashCode() {
		int rv = 0;
		rv = (31 * rv) + (key_is_int ? key_as_int : key.hashCode());
		rv = (31 * rv) + val.hashCode();
		return rv;
	}

	public static XophpArrayItm New_int(int key, Object val)    {return new XophpArrayItm(Bool_.Y, key, Int_.To_str(key), val);}
	public static XophpArrayItm New_str(String key, Object val) {return new XophpArrayItm(Bool_.N,  -1, key             , val);}
}
