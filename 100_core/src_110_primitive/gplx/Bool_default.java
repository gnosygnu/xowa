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
package gplx;
public class Bool_default {
	public static final byte	Tid_n = 0, Tid_y = 1, Tid_i = 2;
	public static final String Str_n = "never", Str_y = "always", Str_i = "default";
	public static final byte	Chr_n = Byte_ascii.Ltr_n, Chr_y = Byte_ascii.Ltr_y, Chr_i = Byte_ascii.Ltr_i;
	public static String Xto_str(byte v) {
		switch (v) {
			case Tid_n: return Str_n;
			case Tid_y: return Str_y;
			case Tid_i: return Str_i;
			default:	throw Err_.unhandled(v);
		}
	}
	public static byte Xto_char_byte(byte v) {
		switch (v) {
			case Tid_n: return Chr_n;
			case Tid_y: return Chr_y;
			case Tid_i: return Chr_i;
			default:	throw Err_.unhandled(v);
		}
	}
	public static byte Xto_tid(String v) {
		if		(String_.Eq(v, Str_n))		return Tid_n;
		else if	(String_.Eq(v, Str_y))		return Tid_y;
		else if	(String_.Eq(v, Str_i))		return Tid_i;
		else								throw Err_.unhandled(v);
	}
}
