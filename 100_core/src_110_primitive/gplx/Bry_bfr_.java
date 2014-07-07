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
public class Bry_bfr_ {
	public static void Assert_at_end(Bry_bfr bfr, byte assert_byte) {
		int len = bfr.Len(); if (len == 0) return;
		int assert_count = 0;
		byte[] bfr_bry = bfr.Bfr();
		for (int i = len - 1; i > -1; --i) {
			byte b = bfr_bry[i];
			if (b == assert_byte)
				++assert_count;
			else
				break;
		}
		switch (assert_count) {
			case 0: bfr.Add_byte(assert_byte); break;
			case 1: break;
			default: bfr.Del_by(assert_count - 1); break;
		}
	}
}
