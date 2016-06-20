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
package gplx.xowa.addons.bldrs.centrals.utils; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.centrals.*;
public class Time_dhms_ {
	public static String To_str(Bry_bfr bfr, long val, boolean show_unit, int max_places) {To_bfr(bfr, val, show_unit, max_places); return bfr.To_str_and_clear();}
	public static void To_bfr(Bry_bfr bfr, long val, boolean show_unit, int max_places) {
		byte suffix = Byte_ascii.Null;
		int count = 0;
		for (int i = 0; i < 4; ++i) {
			// get factor and unit
			long factor = 0;
			byte unit = Byte_ascii.Null;
			switch(i) {
				case 0: unit = Byte_ascii.Ltr_d; factor = 86400; break;
				case 1: unit = Byte_ascii.Ltr_h; factor =  3600; break;
				case 2: unit = Byte_ascii.Ltr_m; factor =    60; break;
				case 3: unit = Byte_ascii.Ltr_s; factor =     1; break;
			}
			// calc cur and update val; EX: 3690 -> cur:1,mod:90
			int cur = (int)(val / factor);
			val %= factor;
			if (count == 0) {					// no str yet
				if (	cur == 0				// cur is 0; EX: 3690 and factor = 86400 -> 0 days; don't write anything
					&&	i != 3)					// unless it is the seconds place; need to handle "0 s" specifically
					continue;
				suffix = unit;					// set suffix
				bfr.Add_int_variable(cur);		// write cur; note that this is not zero-padded
			}
			else {								// str exists
				bfr.Add_int_pad_bgn(Byte_ascii.Num_0, 2, cur);	// write cur; note that this zero-padded; also, note will write "00" if cur == 0
			}
			if (++count == max_places) break;	// stop if max-places reached; EX: 86400 should write 1:00, not 1:00:00:00
			if (i != 3)							// do not add ":" if seconds
				bfr.Add_byte_colon();
		}
		if (show_unit)	// add units; EX: " s" for seconds
			bfr.Add_byte_space().Add_byte(suffix);
        }
}
