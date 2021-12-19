/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2021 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.types.basics.arrays;
public class ObjAry {
	private ObjAry(Object[] ary) {
		this.ary = ary;
	}
	public Object[] Ary() {return ary;} private final Object[] ary;
	public Object GetAt(int i) {return ary[i];}
	public Object GetAt0() {return ary[0];}
	public Object GetAt1() {return ary[1];}
	public static ObjAry New(Object... ary)                {return new ObjAry(ary);}
	public static ObjAry NewPair(Object val0, Object val1) {return new ObjAry(new Object[] {val0, val1});}
}
