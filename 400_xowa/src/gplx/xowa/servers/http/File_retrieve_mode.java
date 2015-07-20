/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012 gnosygnu@gmail.com

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package gplx.xowa.servers.http; import gplx.*; import gplx.xowa.*; import gplx.xowa.servers.*;
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
	public static KeyVal[] Options__list = KeyVal_.Ary(KeyVal_.new_("wait"), KeyVal_.new_("skip"), KeyVal_.new_("async_server", "async server"));
}
