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
package gplx.types.basics.wrappers;
import gplx.types.commons.lists.CompareAble;
import gplx.types.basics.utls.DoubleUtl;
public class DoubleVal implements CompareAble<DoubleVal> {
	private DoubleVal(double val) {this.val = val;}
	public double Val() {return val;} private final double val;
	@Override public String toString() {return DoubleUtl.ToStr(val);}
	@Override public int hashCode() {return (int)val;}
	@Override public boolean equals(Object obj) {return obj == null ? false : val == ((DoubleVal)obj).Val();}
	@Override public int compareTo(DoubleVal comp) {return DoubleUtl.Compare(val, comp.val);}
	public static DoubleVal New(double val) {return new DoubleVal(val);}
}
