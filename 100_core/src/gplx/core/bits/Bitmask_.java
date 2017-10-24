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
package gplx.core.bits; import gplx.*; import gplx.core.*;
public class Bitmask_ {
	public static boolean		Has_int(int val, int find)	{return find == (val & find);}
	public static int		Flip_int(boolean enable, int val, int find) {
		boolean has = find == (val & find);
		return (has ^ enable) ? val ^ find : val;
	}
	public static int		Add_int(int lhs, int rhs)	{return lhs | rhs;}
	public static int		Add_int_ary(int... ary) {
		int rv = 0;
		int len = ary.length;
		for (int i = 0; i < len; ++i) {
			int itm = ary[i];
			if (rv == 0)
				rv = itm;
			else
				rv = Flip_int(true, rv, itm);
		}
		return rv;
	}
	public static boolean		Has_byte(byte val, byte find)	{return find == (val & find);}
	public static byte		Add_byte(byte flag, byte itm)	{return (byte)(flag | itm);}
}
