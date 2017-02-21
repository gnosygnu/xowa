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
package gplx.core.logs; import gplx.*; import gplx.core.*;
public class Gfo_log_itm_wtr__csv implements Gfo_log_itm_wtr {
	private static final    byte[] Type__info = Bry_.new_a7("INFO"), Type__note = Bry_.new_a7("NOTE"), Type__warn = Bry_.new_a7("WARN");
	private String time_fmt = "yyyyMMdd_HHmmss.fff";
	public void Write(Bry_bfr bfr, Gfo_log_itm itm) {
		bfr.Add_str_a7(Int_.To_str_pad_bgn_space((int)itm.Elapsed, 6)).Add_byte_pipe();
		bfr.Add_str_a7(DateAdp_.unixtime_utc_ms_(itm.Time).XtoStr_fmt(time_fmt)).Add_byte_pipe();
		byte[] type = null;
		switch (itm.Type) {
			case Gfo_log_itm.Type__info: type = Type__info; break;
			case Gfo_log_itm.Type__note: type = Type__note; break;
			case Gfo_log_itm.Type__warn: type = Type__warn; break;
		}
		bfr.Add(type).Add_byte_pipe();
		Escape_str(bfr, itm.Msg);	bfr.Add_byte_pipe();
		Object[] args = itm.Args;
		int args_len = args.length;
		for (int i = 0; i < args_len; i += 2) {
			Object key = args[i];
			int val_idx = i + 1;
			Object val = i < val_idx ? args[val_idx] : "<<<NULL>>>";
			Escape_str(bfr, Object_.Xto_str_strict_or_null_mark(key)); bfr.Add_byte_eq();
			Escape_str(bfr, Object_.Xto_str_strict_or_null_mark(val)); bfr.Add_byte_pipe();
		}
		bfr.Add_byte_nl();
	}
	private void Escape_str(Bry_bfr bfr, String str) {
		byte[] bry = Bry_.new_u8(str);
		int len = bry.length;
		boolean dirty = false;
		for (int i = 0; i < len; ++i) {
			byte b = bry[i];
			byte escape_byte = Byte_ascii.Null;
			switch (b) {
				case Byte_ascii.Pipe:		escape_byte = Byte_ascii.Ltr_p; break;
				case Byte_ascii.Nl:			escape_byte = Byte_ascii.Ltr_n; break;
				case Byte_ascii.Tick:		escape_byte = Byte_ascii.Tick; break;
				default:
					if (dirty)
						bfr.Add_byte(b);
					break;
			}
			if (escape_byte != Byte_ascii.Null) {
				if (!dirty) {
					dirty = true;
					bfr.Add_mid(bry, 0, i);
				}
				bfr.Add_byte(Byte_ascii.Tick).Add_byte(escape_byte);
			}
		}
		if (!dirty) bfr.Add(bry);
	}
}
