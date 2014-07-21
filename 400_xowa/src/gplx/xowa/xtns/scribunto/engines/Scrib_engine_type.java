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
package gplx.xowa.xtns.scribunto.engines; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.scribunto.*;
public class Scrib_engine_type {
	public static String Xto_str(byte v) {
		switch (v) {
			case Type_lua:				return "lua";
			case Type_luaj:				return "luaj";
			default:					throw Err_.not_implemented_();
		}
	}
	public static byte Xto_byte(String s) {
		if		(String_.Eq(s, "lua"))				return Type_lua;
		else if	(String_.Eq(s, "luaj"))				return Type_luaj;
		else										throw Err_.not_implemented_();
	}
	public static final byte Type_lua = 0, Type_luaj = 1;
	public static KeyVal[] Options__list = KeyVal_.Ary(KeyVal_.new_("luaj"), KeyVal_.new_("lua"));
}
