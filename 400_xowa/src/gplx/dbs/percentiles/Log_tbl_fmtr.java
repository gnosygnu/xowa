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
package gplx.dbs.percentiles;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.basics.lists.List_adp;
import gplx.types.basics.lists.List_adp_;
import gplx.types.basics.utls.IntUtl;
import gplx.types.basics.constants.AsciiByte;
import gplx.types.basics.utls.StringUtl;
import gplx.types.basics.utls.TypeIds;
class Log_tbl_fmtr {
	private final BryWtr bfr = BryWtr.New();
	private final List_adp itms = List_adp_.New();
	private Log_fld_itm[] ary;
	public Log_tbl_fmtr Add_str(String key, int len)			{ary = null; itms.Add(new Log_fld_itm__bry(TypeIds.IdBry, key, len)); return this;}
	public Log_tbl_fmtr Add_int(String key, int bgn, int end)	{ary = null; itms.Add(new Log_fld_itm__int(TypeIds.IdInt, key, bgn, end)); return this;}
	public void Log(Object... vals) {
		if (ary == null)
			ary = (Log_fld_itm[])itms.ToAryAndClear(Log_fld_itm.class);
		int len = ary.length;
		for (int i = 0; i < len; ++i) {
			Log_fld_itm itm = ary[i];
			Object val = vals[i];
			if (i != 0) bfr.AddBytePipe();
			itm.Fmt(bfr, val);
		}
		bfr.AddByteNl();
	}
	public String To_str_and_clear() {return bfr.ToStrAndClear();}
}
interface Log_fld_itm {
	void Fmt(BryWtr bfr, Object val);
}
abstract class Log_fld_itm__base implements Log_fld_itm {
	public Log_fld_itm__base(int tid, String key, int len) {
		this.tid = tid; this.key = key; this.len = len;
	}
	public int Tid() {return tid;} private final int tid;
	public String Key() {return key;} private final String key;
	public int Len() {return len;} protected int len;
	public abstract void Fmt(BryWtr bfr, Object val);
}
class Log_fld_itm__bry extends Log_fld_itm__base {
	public Log_fld_itm__bry(int tid, String key, int len) {super(tid, key, len);}
	@Override public void Fmt(BryWtr bfr, Object val) {
		byte[] val_bry = BryUtl.Cast(val);
		int val_bry_len = val_bry.length;
		int pad_len = this.len - val_bry_len;
		bfr.Add(val_bry);
		if (pad_len > 0)
			bfr.AddByteRepeat(AsciiByte.Space, pad_len);
	}
}
class Log_fld_itm__int extends Log_fld_itm__base {
	public Log_fld_itm__int(int tid, String key, int bgn, int end) {super(tid, key, 0);
		this.bgn = bgn; this.end = end;
		this.len = IntUtl.CountDigits(end);
	}
	public int Bgn() {return bgn;} private final int bgn;
	public int End() {return end;} private final int end;
	@Override public void Fmt(BryWtr bfr, Object val) {
		int val_int = IntUtl.Cast(val);
		String val_str = StringUtl.PadBgn(IntUtl.ToStr(val_int), this.Len(), " ");
		bfr.AddStrU8(val_str);
	}
}
