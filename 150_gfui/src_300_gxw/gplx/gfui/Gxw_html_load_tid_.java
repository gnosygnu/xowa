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
package gplx.gfui; import gplx.*;
public class Gxw_html_load_tid_ {
	public static final byte Tid_mem = 0, Tid_url = 1;
	public static final String Key_mem = "mem", Key_url = "url";
	public static String Xto_key(byte v) {
		switch (v) {
			case Tid_mem:							return Key_mem;
			case Tid_url:							return Key_url;
			default:								throw Err_.new_unimplemented();
		}
	}
	public static byte Xto_tid(String s) {
		if		(String_.Eq(s, Key_mem))			return Tid_mem;
		else if	(String_.Eq(s, Key_url))			return Tid_url;
		else										throw Err_.new_unimplemented();
	}
	public static KeyVal[] Options__list = KeyVal_.Ary(KeyVal_.new_(Key_mem), KeyVal_.new_(Key_url));
}
