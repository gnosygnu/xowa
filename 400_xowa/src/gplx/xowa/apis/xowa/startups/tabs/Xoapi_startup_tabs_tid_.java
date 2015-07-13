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
package gplx.xowa.apis.xowa.startups.tabs; import gplx.*; import gplx.xowa.*; import gplx.xowa.apis.*; import gplx.xowa.apis.xowa.*; import gplx.xowa.apis.xowa.startups.*;
public class Xoapi_startup_tabs_tid_ {
	public static final byte Tid_blank = 0, Tid_xowa = 1, Tid_previous = 2, Tid_custom = 3;
	public static final String Key_blank = "blank", Key_xowa = "xowa", Key_previous = "previous", Key_custom = "custom";
	public static String Xto_key(byte v) {
		switch (v) {
			case Tid_blank:							return Key_blank;
			case Tid_xowa:							return Key_xowa;
			case Tid_previous:						return Key_previous;
			case Tid_custom:						return Key_custom;
			default:								throw Exc_.new_unimplemented();
		}
	}
	public static byte Xto_tid(String s) {
		if		(String_.Eq(s, Key_blank))			return Tid_blank;
		else if	(String_.Eq(s, Key_xowa))			return Tid_xowa;
		else if	(String_.Eq(s, Key_previous))		return Tid_previous;
		else if	(String_.Eq(s, Key_custom))			return Tid_custom;
		else										throw Exc_.new_unimplemented();
	}
	public static KeyVal[] Options__list = KeyVal_.Ary(KeyVal_.new_(Key_blank), KeyVal_.new_(Key_xowa), KeyVal_.new_(Key_previous), KeyVal_.new_(Key_custom));
}
