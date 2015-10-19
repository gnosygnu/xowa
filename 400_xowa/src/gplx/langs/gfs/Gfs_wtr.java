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
package gplx.langs.gfs; import gplx.*; import gplx.langs.*;
public class Gfs_wtr {		
	public byte Quote_char() {return quote_char;} public Gfs_wtr Quote_char_(byte v) {quote_char = v; return this;} private byte quote_char = Byte_ascii.Apos;
	public Bry_bfr Bfr() {return bfr;} private Bry_bfr bfr = Bry_bfr.reset_(255);
	public void Add_grp_bgn(byte[] key) {
		bfr.Add(key);							// key
		bfr.Add_byte(Byte_ascii.Curly_bgn);		// {
	}
	public void Add_grp_end(byte[] key) {
		bfr.Add_byte(Byte_ascii.Curly_end);		// }
	}
	public void Add_set_eq(byte[] key, byte[] val) {
		bfr.Add(key);							// key
		bfr.Add_byte_eq();						// =
		bfr.Add_byte(quote_char);				// '
		Write_val(val);
		bfr.Add_byte(quote_char);				// '
		bfr.Add_byte(Byte_ascii.Semic);			// ;
	}
	private void Write_val(byte[] bry) {
		int bry_len = bry.length;
		for (int i = 0; i < bry_len; i++) {
			byte b = bry[i];
			if (b == quote_char)	// byte is quote
				bfr.Add_byte(b);	// double up
			bfr.Add_byte(b);
		}
	}
}
