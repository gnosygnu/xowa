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
import gplx.types.basics.utls.IntUtl;
public class IntRef {
	private IntRef(int val) {this.val = val;}
	public int Val() {return val;} public IntRef ValSet(int v) {val = v; return this;} private int val;
	public int ValAddOne() {val++; return val;}
	public int ValAddPost() {return val++;}
	public int ValAdd(int v) {val += v; return val;}
	public IntRef ValSetZero() {val = 0; return this;}
	public IntRef ValSetNeg1() {val = -1; return this;}

	@Override public String toString() {return IntUtl.ToStr(val);}
	@Override public int hashCode() {return val;}
	@Override public boolean equals(Object obj) {return val == ((IntRef)obj).Val();}

	public static IntRef NewNeg1()    {return new IntRef(-1);}
	public static IntRef NewZero()    {return new IntRef(0);}
	public static IntRef New(int val) {return new IntRef(val);}
}
