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
import gplx.types.basics.utls.ByteUtl;
import gplx.types.basics.utls.IntUtl;
public class ByteRef {
	private ByteRef(byte val) {this.val = val;}
	public byte Val() {return val;} private byte val;
	public ByteRef ValSet(byte v) {val = v; return this;}
	@Override public int hashCode() {return val;}
	@Override public boolean equals(Object obj) {return obj == null ? false : val == ((ByteRef)obj).Val();}
	@Override public String toString() {return IntUtl.ToStr(val);}
	public static ByteRef NewZero() {return New(ByteUtl.Zero);}
	public static ByteRef New(byte val) {return new ByteRef(val);}
}
