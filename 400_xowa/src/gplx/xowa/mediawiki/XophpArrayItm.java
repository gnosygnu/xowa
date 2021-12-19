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
package gplx.xowa.mediawiki;

import gplx.types.basics.utls.BoolUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.basics.utls.IntUtl;
import gplx.types.basics.utls.ObjectUtl;
import gplx.types.basics.utls.StringUtl;
import gplx.types.basics.utls.ClassUtl;
import gplx.types.custom.brys.wtrs.BryBfrAble;

public class XophpArrayItm<T> implements BryBfrAble {
	XophpArrayItm(boolean keyIsInt, int keyAsInt, String key, T val) {
		this.keyIsInt = keyIsInt;
		this.keyAsInt = keyAsInt;
		this.key = key;
		this.val = val;
	}
	public boolean KeyIsInt() {return keyIsInt;} private final boolean keyIsInt;
	public int KeyAsInt() {return keyAsInt;} private final int keyAsInt;
	public String Key() {return key;} private final String key;
	public T Val() {return val;} public void Val_(T v) {this.val = v;} private T val;

	public void AddToBfr(BryWtr bfr) {
		bfr.AddStrU8(key).AddByteEq();

		if (ClassUtl.TypeByObj(val) == XophpArray.class) {
			XophpArray sub_ary = (XophpArray)val;
			bfr.AddByteNl();
			sub_ary.AddToBfr(bfr);
		}
		else {
			bfr.AddObj(val).AddByteSpace();
		}
	}
	@Override public boolean equals(Object obj) {
		if (obj == null) return false;
		XophpArrayItm comp = (XophpArrayItm)obj;

		// compare key
		if (keyIsInt) {
			if (this.keyAsInt != comp.keyAsInt)
				return false;
		}
		else {
			if (!StringUtl.Eq(this.key, comp.key))
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
			return ObjectUtl.Eq(this.val, comp.val);
	}
	@Override public int hashCode() {
		int rv = 0;
		rv = (31 * rv) + (keyIsInt ? keyAsInt : key.hashCode());
		rv = (31 * rv) + val.hashCode();
		return rv;
	}

	private static final int NULL_KEY_INT = -1;
	public static XophpArrayItm NewInt(int key, Object val)    {return new XophpArrayItm(BoolUtl.Y, key, IntUtl.ToStr(key), val);}
	public static XophpArrayItm NewStr(String key, Object val) {return new XophpArrayItm(BoolUtl.N, NULL_KEY_INT, key, val);}
}
