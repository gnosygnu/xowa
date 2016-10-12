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
package gplx.core.intls.ucas; import gplx.*; import gplx.core.*; import gplx.core.intls.*;
public class Uca_ltr_extractor {
	private final    boolean numeric;
	private final    byte[] numeric_heading;
	private final    Hash_adp_bry numeric_hash;
	public Uca_ltr_extractor(boolean numeric) {
		this.numeric = numeric;
		if (numeric) {
			numeric_heading = Bry_.new_a7("0-9");

			// create hash of "0", "1", "2", ...
			numeric_hash = Hash_adp_bry.cs();
			for (int i = 0; i < 10; ++i) {
				byte[] digit_bry = Bry_.new_by_int(Byte_ascii.Num_0 + i);
				numeric_hash.Add(digit_bry, digit_bry);
			}
		}
		else {
			numeric_heading = null;
			numeric_hash = null;
		}
	}
	public byte[] Get_1st_ltr(byte[] bry) {
		// NOTE: this is simplified and only does numeric logic; MW code loads up all ICU chars via first-letters-root.ser, adds custom chars, sorts them, and then does a binary search to find it; REF:IcuCollation.php!getFirstLetter
		int bry_len = bry.length;
		if (bry_len == 0) return Bry_.Empty;
		byte[] rv = gplx.core.intls.Utf8_.Get_char_at_pos_as_bry(bry, 0);
		if (numeric) {
			if (numeric_hash.Has(rv))
				rv = numeric_heading;
		}
		return rv;
	}		
}
