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
package gplx.xowa.xtns.scribunto.engines;
import gplx.types.basics.utls.StringUtl;
import gplx.types.commons.KeyVal;
import gplx.types.commons.KeyValUtl;
import gplx.types.errs.ErrUtl;
public class Scrib_engine_type {
	public static String Xto_str(byte v) {
		switch (v) {
			case Type_lua:				return "lua";
			case Type_luaj:				return "luaj";
			default:					throw ErrUtl.NewUnimplemented();
		}
	}
	public static byte Xto_byte(String s) {
		if		(StringUtl.Eq(s, "lua"))				return Type_lua;
		else if	(StringUtl.Eq(s, "luaj"))				return Type_luaj;
		else										throw ErrUtl.NewUnimplemented();
	}
	public static final byte Type_lua = 0, Type_luaj = 1;
	public static KeyVal[] Options__list = KeyValUtl.Ary(KeyVal.NewStr("luaj"), KeyVal.NewStr("lua"));
}
