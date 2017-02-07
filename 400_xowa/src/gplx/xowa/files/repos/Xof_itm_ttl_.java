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
package gplx.xowa.files.repos; import gplx.*; import gplx.xowa.*; import gplx.xowa.files.*;
public class Xof_itm_ttl_ {
	public static byte[] Remove_invalid(Bry_bfr bfr, byte[] ttl) {
		int ttl_len = ttl.length;
		for (int i = 0; i < ttl_len; i++) {
			byte b = ttl[i];
			if (gplx.core.envs.Op_sys_.Wnt_invalid_char(b)) b = Byte_ascii.Underline;
			bfr.Add_byte(b);
		}
		return bfr.To_bry_and_clear();
	}
	public static byte[] Shorten(Bry_bfr bfr, byte[] ttl, int ttl_max, byte[] md5, byte[] ext_bry) {
		byte[] rv = ttl;
		int exceed_len = rv.length - ttl_max;
		if (exceed_len > 0) {
			bfr.Add_mid(rv, 0, ttl_max - 33);		// add truncated title;		33=_.length + md5.length
			bfr.Add_byte(Byte_ascii.Underline);		// add underline;			EX: "_"
			bfr.Add(md5);							// add md5;					EX: "abcdefghijklmnopqrstuvwxyz0123456"
			bfr.Add_byte(Byte_ascii.Dot);			// add dot;					EX: "."
			bfr.Add(ext_bry);						// add ext;					EX: ".png"
			rv = bfr.To_bry_and_clear();
		}
		return rv;
	}
}
