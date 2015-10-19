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
package gplx.brys; import gplx.*;
public class Bry_fmtr_arg_time implements Bry_fmtr_arg {
	public Bry_fmtr_arg_time() {
		units_len = units.length;
	}
	public long Seconds() {return seconds;} public Bry_fmtr_arg_time Seconds_(long v) {seconds = v; return this;} long seconds;
	byte[][] segs = new byte[][]
	{	Bry_.new_a7("d")
	,	Bry_.new_a7("h")
	,	Bry_.new_a7("m")
	,	Bry_.new_a7("s")
	};
	int[] units = new int[] {86400, 3600, 60, 1};
	int units_len;
	byte[] spr = new byte[] {Byte_ascii.Space};
	public void Fmt__do(Bry_bfr bfr) {
		if (seconds == 0) {	// handle 0 separately (since it will always be < than units[*]
			bfr.Add_int_fixed(0, 2).Add(segs[units_len - 1]);
			return;
		}
		long val = seconds;
		boolean dirty = false;
		for (int i = 0; i < units_len; i++) {
			long unit = units[i];
			long seg = 0;
			if (val >= unit) {				// unit has value; EX: 87000 > 86400, so unit is 1 day
				seg = val / unit;
				val = val - (seg * unit);
			}
			if (seg > 0 || dirty) {			// dirty check allows for 0 in middle units (EX: 1h 0m 1s)  
				if (dirty) bfr.Add(spr);
				if (seg < 10) bfr.Add_byte(Byte_ascii.Num_0);	// 0 pad
				bfr.Add_long_variable(seg).Add(segs[i]);
				dirty = true;
			}
		}
	}
}
