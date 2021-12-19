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
package gplx.xowa.apps.servers.http;
import gplx.types.basics.utls.StringUtl;
import gplx.types.commons.KeyVal;
import gplx.types.commons.KeyValUtl;
import gplx.types.errs.ErrUtl;
class File_retrieve_mode {
	public static String Xto_str(byte v) {
		switch (v) {
			case Mode_skip:				return "skip";
			case Mode_wait:				return "wait";
			case Mode_async_server:		return "async_server";
			default:					throw ErrUtl.NewUnimplemented();
		}
	}
	public static byte Xto_byte(String s) {
		if		(StringUtl.Eq(s, "skip"))				return Mode_skip;
		else if	(StringUtl.Eq(s, "wait"))				return Mode_wait;
		else if	(StringUtl.Eq(s, "async_server"))		return Mode_async_server;
		else										throw ErrUtl.NewUnimplemented();
	}
	public static final byte Mode_skip = 1, Mode_wait = 2, Mode_async_server = 3;
	public static KeyVal[] Options__list = KeyValUtl.Ary(KeyVal.NewStr("wait"), KeyVal.NewStr("skip"), KeyVal.NewStr("async_server", "async server"));
}
