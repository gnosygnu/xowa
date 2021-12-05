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
package gplx.xowa.addons.bldrs.centrals.utils; import gplx.*;
import gplx.objects.strings.AsciiByte;
public class Time_dhms_ {
	public static String To_str(Bry_bfr bfr, long val, boolean show_unit, int max_places) {To_bfr(bfr, val, show_unit, max_places); return bfr.To_str_and_clear();}
	public static void To_bfr(Bry_bfr bfr, long val, boolean show_unit, int max_places) {
		byte suffix = AsciiByte.Null;
		int count = 0;
		for (int i = 0; i < 4; ++i) {
			// get factor and unit
			long factor = 0;
			byte unit = AsciiByte.Null;
			switch(i) {
				case 0: unit = AsciiByte.Ltr_d; factor = 86400; break;
				case 1: unit = AsciiByte.Ltr_h; factor =  3600; break;
				case 2: unit = AsciiByte.Ltr_m; factor =    60; break;
				case 3: unit = AsciiByte.Ltr_s; factor =     1; break;
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
				bfr.Add_int_pad_bgn(AsciiByte.Num0, 2, cur);	// write cur; note that this zero-padded; also, note will write "00" if cur == 0
			}
			if (++count == max_places) break;	// stop if max-places reached; EX: 86400 should write 1:00, not 1:00:00:00
			if (i != 3)							// do not add ":" if seconds
				bfr.Add_byte_colon();
		}
		if (show_unit)	// add units; EX: " s" for seconds
			bfr.Add_byte_space().Add_byte(suffix);
        }
}
