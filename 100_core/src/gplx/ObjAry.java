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
package gplx;
public class ObjAry {
	public Object[] Ary() {return ary;} Object[] ary;
	public Object Get(int i) {return ary[i];}
	public Object Get0() {return ary[0];}
	public Object Get1() {return ary[1];}
        public static ObjAry pair_(Object val0, Object val1) {
		ObjAry rv = new ObjAry();
		rv.ary = new Object[2];
		rv.ary[0] = val0;
		rv.ary[1] = val1;
		return rv;
	}	ObjAry() {}
        public static ObjAry many_(Object... ary) {
		ObjAry rv = new ObjAry();
		rv.ary = ary;
		return rv;
	}
}
