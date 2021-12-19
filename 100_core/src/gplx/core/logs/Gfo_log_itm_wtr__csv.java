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
package gplx.core.logs;
import gplx.types.basics.utls.ObjectUtl;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.basics.utls.IntUtl;
import gplx.types.basics.constants.AsciiByte;
import gplx.types.commons.GfoDateUtl;
public class Gfo_log_itm_wtr__csv implements Gfo_log_itm_wtr {
	private static final byte[] Type__info = BryUtl.NewA7("INFO"), Type__note = BryUtl.NewA7("NOTE"), Type__warn = BryUtl.NewA7("WARN");
	private String time_fmt = "yyyyMMdd_HHmmss.fff";
	public void Write(BryWtr bfr, Gfo_log_itm itm) {
		bfr.AddStrA7(IntUtl.ToStrPadBgnSpace((int)itm.Elapsed, 6)).AddBytePipe();
		bfr.AddStrA7(GfoDateUtl.NewUnixtimeUtcMs(itm.Time).ToStrFmt(time_fmt)).AddBytePipe();
		byte[] type = null;
		switch (itm.Type) {
			case Gfo_log_itm.Type__info: type = Type__info; break;
			case Gfo_log_itm.Type__note: type = Type__note; break;
			case Gfo_log_itm.Type__warn: type = Type__warn; break;
		}
		bfr.Add(type).AddBytePipe();
		Escape_str(bfr, itm.Msg);    bfr.AddBytePipe();
		Object[] args = itm.Args;
		int args_len = args.length;
		for (int i = 0; i < args_len; i += 2) {
			Object key = args[i];
			int val_idx = i + 1;
			Object val = i < val_idx ? args[val_idx] : "<<<NULL>>>";
			Escape_str(bfr, ObjectUtl.ToStrOrNullMark(key)); bfr.AddByteEq();
			Escape_str(bfr, ObjectUtl.ToStrOrNullMark(val)); bfr.AddBytePipe();
		}
		bfr.AddByteNl();
	}
	private void Escape_str(BryWtr bfr, String str) {
		byte[] bry = BryUtl.NewU8(str);
		int len = bry.length;
		boolean dirty = false;
		for (int i = 0; i < len; ++i) {
			byte b = bry[i];
			byte escape_byte = AsciiByte.Null;
			switch (b) {
				case AsciiByte.Pipe:        escape_byte = AsciiByte.Ltr_p; break;
				case AsciiByte.Nl:            escape_byte = AsciiByte.Ltr_n; break;
				case AsciiByte.Tick:        escape_byte = AsciiByte.Tick; break;
				default:
					if (dirty)
						bfr.AddByte(b);
					break;
			}
			if (escape_byte != AsciiByte.Null) {
				if (!dirty) {
					dirty = true;
					bfr.AddMid(bry, 0, i);
				}
				bfr.AddByte(AsciiByte.Tick).AddByte(escape_byte);
			}
		}
		if (!dirty) bfr.Add(bry);
	}
}
