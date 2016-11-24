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
package gplx.dbs.percentiles; import gplx.*; import gplx.dbs.*;
class Log_tbl_fmtr {
	private final    Bry_bfr bfr = Bry_bfr_.New();
	private final    List_adp itms = List_adp_.New();
	private Log_fld_itm[] ary;
	public Log_tbl_fmtr Add_str(String key, int len)			{ary = null; itms.Add(new Log_fld_itm__bry(Type_adp_.Tid__bry, key, len)); return this;}
	public Log_tbl_fmtr Add_int(String key, int bgn, int end)	{ary = null; itms.Add(new Log_fld_itm__int(Type_adp_.Tid__int, key, bgn, end)); return this;}
	public void Log(Object... vals) {
		if (ary == null)
			ary = (Log_fld_itm[])itms.To_ary_and_clear(Log_fld_itm.class);
		int len = ary.length;
		for (int i = 0; i < len; ++i) {
			Log_fld_itm itm = ary[i];
			Object val = vals[i];
			if (i != 0) bfr.Add_byte_pipe();
			itm.Fmt(bfr, val);
		}
		bfr.Add_byte_nl();
	}
	public String To_str_and_clear() {return bfr.To_str_and_clear();}
}
interface Log_fld_itm {
	void Fmt(Bry_bfr bfr, Object val);
}
abstract class Log_fld_itm__base implements Log_fld_itm {
	public Log_fld_itm__base(int tid, String key, int len) {
		this.tid = tid; this.key = key; this.len = len;
	}
	public int Tid() {return tid;} private final    int tid;
	public String Key() {return key;} private final    String key;
	public int Len() {return len;} protected int len;
	public abstract void Fmt(Bry_bfr bfr, Object val);
}
class Log_fld_itm__bry extends Log_fld_itm__base {
	public Log_fld_itm__bry(int tid, String key, int len) {super(tid, key, len);}
	@Override public void Fmt(Bry_bfr bfr, Object val) {
		byte[] val_bry = Bry_.cast(val);
		int val_bry_len = val_bry.length;
		int pad_len = this.len - val_bry_len;
		bfr.Add(val_bry);
		if (pad_len > 0)
			bfr.Add_byte_repeat(Byte_ascii.Space, pad_len);
	}
}
class Log_fld_itm__int extends Log_fld_itm__base {
	public Log_fld_itm__int(int tid, String key, int bgn, int end) {super(tid, key, 0);
		this.bgn = bgn; this.end = end;
		this.len = Int_.DigitCount(end);
	}
	public int Bgn() {return bgn;} private final    int bgn;
	public int End() {return end;} private final    int end;
	@Override public void Fmt(Bry_bfr bfr, Object val) {
		int val_int = Int_.cast(val);
		String val_str = String_.PadBgn(Int_.To_str(val_int), this.Len(), " ");
		bfr.Add_str_u8(val_str);
	}
}
