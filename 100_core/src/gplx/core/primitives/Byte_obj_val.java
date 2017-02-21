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
public class Byte_obj_val {
	public byte Val() {return val;} private byte val;
	@Override public String toString() {return Int_.To_str(val);}
	@Override public int hashCode() {return val;}
	@Override public boolean equals(Object obj) {return obj == null ? false : val == ((Byte_obj_val)obj).Val();}
        public static Byte_obj_val new_(byte val) {
		Byte_obj_val rv = new Byte_obj_val();
		rv.val = val;
		return rv;
	}	private Byte_obj_val() {}
}
