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
package gplx.fsdb; import gplx.*;
public class Fsdb_db_tid_ {
	public static final byte Tid_cfg = 0, Tid_atr = 1, Tid_bin = 2;
	public static final String Key_cfg = "cfg", Key_atr = "atr", Key_bin = "bin";
	public static byte Xto_tid(String s) {
		if		(String_.Eq(s, Key_cfg))		return Tid_cfg;
		else if	(String_.Eq(s, Key_atr))		return Tid_atr;
		else if	(String_.Eq(s, Key_bin))		return Tid_bin;
		else									throw Err_.unhandled(s);
	}
	public static String Xto_key(byte v) {
		switch (v) {
			case Tid_cfg:	return Key_cfg;
			case Tid_atr:	return Key_atr;
			case Tid_bin:	return Key_bin;
			default:		throw Err_.unhandled(v);
		}
	}
}
