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
package gplx.xowa.wikis.nss; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*;
public class Xow_ns_case_ {
	public static final byte Tid__all = 0, Tid__1st = 1;
	public static final String Key__all = "case-sensitive", Key__1st = "first-letter";
	public static final byte[] Bry__all = Bry_.new_a7(Key__all), Bry__1st = Bry_.new_a7(Key__1st);
	public static byte To_tid(String s) {
		if		(String_.Eq(s, Key__1st))		return Tid__1st;
		else if	(String_.Eq(s, Key__all))		return Tid__all;
		else									throw Err_.new_unhandled(s);
	}
	public static String To_str(byte tid) {
		switch (tid) {
			case Tid__all:		return Key__all;
			case Tid__1st:		return Key__1st;
			default:			throw Err_.new_unhandled(tid);
		}
	}
}
