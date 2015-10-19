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
package gplx.xowa.htmls.hzips; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*;
import gplx.core.primitives.*;
public class Xow_hzip_int_ {
	private static final int	Base_255_int = 255;
	private static final byte	Base_255_byte = (byte)255;
	public static byte[] Save_bin_int_abrv(int val_int) {
		Bry_bfr bfr = Bry_bfr.reset_(10);
		Save_bin_int_abrv(bfr, val_int);
		return bfr.To_bry_and_clear();
	}
	public static void Save_bin_int_abrv(Bry_bfr bfr, int val_int) {	// save int in binary little endian form; range from -2,080,766,977 to 2,147,483,648; 255^4 or 4,228,250,625
		if (val_int == 0) {bfr.Add_byte(Byte_ascii.Null); return;}
		long val = val_int;
		if (val < 0) val = Int_.Max_value + -val;
		int count = 0;
		while (val > 0) {
			byte mod = (byte)(val % Base_255_int);
			int adj = 0;
			if (mod == 0) {mod = Base_255_byte; adj = 1;}	// if 0, then set byte to 255; also set adj to 1 to properly decrement value
			bfr.Add_byte(mod);
			++count;
			val = (val - adj) / Base_255_int;
		}
		if (count < 4) bfr.Add_byte(Byte_ascii.Null);
	}
	public static int Load_bin_int_abrv(byte[] bry, int bry_len, int bgn, Int_obj_ref count_ref) {
		int end = bgn + 4;	// read no more than 4 bytes
		int count = 0;
		long rv = 0; int mult = 1;
		for (int i = bgn; i < end; ++i) {
			if (i == bry_len) break;				
			else {
				++count;
				int b = bry[i] & 0xFF; // PATCH.JAVA:need to convert to unsigned byte
				if (b == 0) break;
				rv += (b * mult);
				mult *= Base_255_int;
			}
		}
		if (rv > Int_.Max_value) {
			rv -= Int_.Max_value;
			rv *= -1;
		}
		count_ref.Val_(count);
		return (int)rv;
	}
}
