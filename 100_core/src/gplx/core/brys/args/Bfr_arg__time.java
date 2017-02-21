/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2017 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.core.brys.args; import gplx.*; import gplx.core.*; import gplx.core.brys.*;
public class Bfr_arg__time implements Bfr_arg {
	public Bfr_arg__time() {
		units_len = units.length;
	}
	public long Seconds() {return seconds;} public Bfr_arg__time Seconds_(long v) {seconds = v; return this;} long seconds;
	byte[][] segs = new byte[][]
	{	Bry_.new_a7("d")
	,	Bry_.new_a7("h")
	,	Bry_.new_a7("m")
	,	Bry_.new_a7("s")
	};
	int[] units = new int[] {86400, 3600, 60, 1};
	int units_len;
	byte[] spr = new byte[] {Byte_ascii.Space};
	public void Bfr_arg__add(Bry_bfr bfr) {
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
