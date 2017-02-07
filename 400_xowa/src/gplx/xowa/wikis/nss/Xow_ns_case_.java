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
