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
package gplx.dbs; import gplx.*;
public class Db_sql_ {
	public static String Make_by_fmt(String[] lines, Object... args) {
		Bry_bfr bfr = Bry_bfr_.New();
		int len = lines.length;
		for (int i = 0; i < len; ++i) {
			if (i != 0) bfr.Add_byte_nl();
			bfr.Add_str_u8(lines[i]);
		}
		String fmt = bfr.To_str_and_clear();
		return String_.Format(fmt, args);
	}
	public static byte[] Escape_arg(byte[] raw) {
		int len = raw.length;
		Bry_bfr bfr = null;
		boolean dirty = false;

		for (int i = 0; i < len; ++i) {
			byte b = raw[i];
			if (b == Byte_ascii.Apos) {
				if (bfr == null) {
					dirty = true;
					bfr = Bry_bfr_.New();
					bfr.Add_mid(raw, 0, i);
				}
				bfr.Add_byte_apos().Add_byte_apos();
			}
			else {
				if (dirty) {
					bfr.Add_byte(b);
				}
			}
		}
		return dirty ? bfr.To_bry_and_clear() : raw;
	}
}
