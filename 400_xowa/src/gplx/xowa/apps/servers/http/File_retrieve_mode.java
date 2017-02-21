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
package gplx.xowa.apps.servers.http; import gplx.*; import gplx.xowa.*; import gplx.xowa.apps.*; import gplx.xowa.apps.servers.*;
class File_retrieve_mode {
	public static String Xto_str(byte v) {
		switch (v) {
			case Mode_skip:				return "skip";
			case Mode_wait:				return "wait";
			case Mode_async_server:		return "async_server";
			default:					throw Err_.new_unimplemented();
		}
	}
	public static byte Xto_byte(String s) {
		if		(String_.Eq(s, "skip"))				return Mode_skip;
		else if	(String_.Eq(s, "wait"))				return Mode_wait;
		else if	(String_.Eq(s, "async_server"))		return Mode_async_server;
		else										throw Err_.new_unimplemented();
	}
	public static final byte Mode_skip = 1, Mode_wait = 2, Mode_async_server = 3;
	public static Keyval[] Options__list = Keyval_.Ary(Keyval_.new_("wait"), Keyval_.new_("skip"), Keyval_.new_("async_server", "async server"));
}
