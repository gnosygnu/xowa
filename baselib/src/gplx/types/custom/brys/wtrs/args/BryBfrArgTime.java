/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2021 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.types.custom.brys.wtrs.args;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.basics.constants.AsciiByte;
public class BryBfrArgTime implements BryBfrArg {
	private byte[][] segs = new byte[][]
	{ BryUtl.NewA7("d")
	, BryUtl.NewA7("h")
	, BryUtl.NewA7("m")
	, BryUtl.NewA7("s")
	};
	private int[] units = new int[]
	{ 86400
	,  3600
	,    60
	,     1
	};
	private int units_len;
	private byte[] spr = new byte[] {AsciiByte.Space};
	public BryBfrArgTime() {
		units_len = units.length;
	}
	public long Seconds() {return seconds;} public BryBfrArgTime SecondsSet(long v) {seconds = v; return this;} private long seconds;
	@Override public void AddToBfr(BryWtr bfr) {
		if (seconds == 0) {    // handle 0 separately (since it will always be < than units[*]
			bfr.AddIntFixed(0, 2).Add(segs[units_len - 1]);
			return;
		}
		long val = seconds;
		boolean dirty = false;
		for (int i = 0; i < units_len; i++) {
			long unit = units[i];
			long seg = 0;
			if (val >= unit) {                // unit has value; EX: 87000 > 86400, so unit is 1 day
				seg = val / unit;
				val = val - (seg * unit);
			}
			if (seg > 0 || dirty) {            // dirty check allows for 0 in middle units (EX: 1h 0m 1s)  
				if (dirty) bfr.Add(spr);
				if (seg < 10) bfr.AddByte(AsciiByte.Num0);    // 0 pad
				bfr.AddLongVariable(seg).Add(segs[i]);
				dirty = true;
			}
		}
	}
}
