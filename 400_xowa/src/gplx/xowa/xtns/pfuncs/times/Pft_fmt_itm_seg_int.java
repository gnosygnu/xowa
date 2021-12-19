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
package gplx.xowa.xtns.pfuncs.times;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.basics.utls.IntUtl;
import gplx.types.basics.constants.AsciiByte;
import gplx.types.commons.GfoDate;
import gplx.types.commons.GfoDateUtl;
import gplx.xowa.*;
import gplx.xowa.langs.*;
class Pft_fmt_itm_seg_int implements Pft_fmt_itm {
	public Pft_fmt_itm_seg_int(int segIdx, int len, boolean fixed_len) {this.segIdx = segIdx; this.fixed_len = fixed_len; this.len = len;} private int segIdx, len; boolean fixed_len;
	public int TypeId() {return Pft_fmt_itm_.Tid_seg_int;}
	public int SegIdx() {return segIdx;}
	public void Fmt(BryWtr bfr, Xowe_wiki wiki, Xol_lang_itm lang, GfoDate date, Pft_func_formatdate_bldr bldr) {
		int val = date.Segment(segIdx);
		if (fixed_len)	bfr.AddIntFixed(val, len);
		else			bfr.AddIntVariable(val);
	}
}
class Pft_fmt_itm_raw implements Pft_fmt_itm {
	public int TypeId() {return Pft_fmt_itm_.Tid_raw;}
	public void Fmt(BryWtr bfr, Xowe_wiki wiki, Xol_lang_itm lang, GfoDate date, Pft_func_formatdate_bldr bldr) {
		// TODO_OLD: should flag .Raw() on bldr to skip transliterating numerals in foreign languages; DATE:2013-12-31
	}
}
class Pft_fmt_itm_seg_str implements Pft_fmt_itm {
	public Pft_fmt_itm_seg_str(int segIdx, int type) {this.segIdx = segIdx; this.type = type;} private int segIdx, type;
	public int TypeId() {return Pft_fmt_itm_.Tid_seg_str;}
	public void Fmt(BryWtr bfr, Xowe_wiki wiki, Xol_lang_itm lang, GfoDate date, Pft_func_formatdate_bldr bldr) {
		DateAdpTranslator_xapp.Translate(wiki, lang, type, date.Segment(segIdx), bfr);
	}
}
class Pft_fmt_itm_year_isLeap implements Pft_fmt_itm {
	public int TypeId() {return Pft_fmt_itm_.Tid_year_isLeap;}
	public void Fmt(BryWtr bfr, Xowe_wiki wiki, Xol_lang_itm lang, GfoDate date, Pft_func_formatdate_bldr bldr) {bfr.AddIntFixed(GfoDateUtl.IsLeapYear(date.Year()) ? 1 : 0, 1);}
}
class Pft_fmt_itm_hour_base12 implements Pft_fmt_itm {
	public int TypeId() {return Pft_fmt_itm_.Tid_hour_base12;}
	public void Fmt(BryWtr bfr, Xowe_wiki wiki, Xol_lang_itm lang, GfoDate date, Pft_func_formatdate_bldr bldr) {
		int val = date.Hour();
		switch (val) {
			case 0: val = 12; break;
			case 13: case 14: case 15: case 16: case 17: case 18: case 19: case 20: case 21: case 22: case 23: val -= 12; break;
			default: break;
		}
		if (fixed_len)	bfr.AddIntFixed(val, 2);
		else			bfr.AddIntVariable(val);
	}
	public Pft_fmt_itm_hour_base12(boolean fixed_len) {this.fixed_len = fixed_len;} private boolean fixed_len;
}
class Pft_fmt_itm_timestamp_unix implements Pft_fmt_itm {
	public int TypeId() {return Pft_fmt_itm_.Tid_timestamp_unix;}
	public void Fmt(BryWtr bfr, Xowe_wiki wiki, Xol_lang_itm lang, GfoDate date, Pft_func_formatdate_bldr bldr) {bfr.AddLongVariable(date.TimestampUnix());}
}
class Pft_fmt_itm_raw_ary implements Pft_fmt_itm {
	public int TypeId() {return Pft_fmt_itm_.Tid_raw_ary;}
	public void Fmt(BryWtr bfr, Xowe_wiki wiki, Xol_lang_itm lang, GfoDate date, Pft_func_formatdate_bldr bldr) {bfr.AddMid(src, bgn, end);}
	public Pft_fmt_itm_raw_ary(byte[] src, int bgn, int end) {this.src = src; this.bgn = bgn; this.end = end;} private byte[] src; int bgn; int end;
}
class Pft_fmt_itm_raw_byt implements Pft_fmt_itm {
	public int TypeId() {return Pft_fmt_itm_.Tid_raw_byt;}
	public void Fmt(BryWtr bfr, Xowe_wiki wiki, Xol_lang_itm lang, GfoDate date, Pft_func_formatdate_bldr bldr) {bfr.AddByte(b);}
	public Pft_fmt_itm_raw_byt(byte b) {this.b = b;} private byte b;
}
class Pft_fmt_itm_daysInMonth implements Pft_fmt_itm {
	public int TypeId() {return Pft_fmt_itm_.Tid_daysInMonth;}
	public void Fmt(BryWtr bfr, Xowe_wiki wiki, Xol_lang_itm lang, GfoDate date, Pft_func_formatdate_bldr bldr) {bfr.AddIntVariable(GfoDateUtl.DaysInMonth(date.Year(), date.Month()));}
}
class Pft_fmt_itm_dayOfYear implements Pft_fmt_itm {
	public int TypeId() {return Pft_fmt_itm_.Tid_dayOfYear;}
	public void Fmt(BryWtr bfr, Xowe_wiki wiki, Xol_lang_itm lang, GfoDate date, Pft_func_formatdate_bldr bldr) {bfr.AddIntVariable(date.DayOfYear() - IntUtl.Base1);}	// php is base1; .net/java is base0
}
class Pft_fmt_itm_am_pm implements Pft_fmt_itm {
	public int TypeId() {return Pft_fmt_itm_.Tid_AmPm;}
	public void Fmt(BryWtr bfr, Xowe_wiki wiki, Xol_lang_itm lang, GfoDate date, Pft_func_formatdate_bldr bldr) {
		boolean am = date.Hour() < 12;
		byte[] val = null;
		if (am) {
			val = lower ? Ary_am_lower : Ary_am_upper;
		}
		else {
			val = lower ? Ary_pm_lower : Ary_pm_upper;
		}
		bfr.Add(val);
	}	private static final byte[] Ary_am_upper = BryUtl.NewA7("AM"), Ary_pm_upper = BryUtl.NewA7("PM"), Ary_am_lower = BryUtl.NewA7("am"), Ary_pm_lower = BryUtl.NewA7("pm");
	public Pft_fmt_itm_am_pm(boolean lower) {this.lower = lower;} private boolean lower;
}
class Pft_fmt_itm_dow_base0 implements Pft_fmt_itm {
	public int TypeId() {return Pft_fmt_itm_.Tid_dow_base0;}
	public void Fmt(BryWtr bfr, Xowe_wiki wiki, Xol_lang_itm lang, GfoDate date, Pft_func_formatdate_bldr bldr) {
		int dow = date.DayOfWeek();
		if (dow == 0) dow = 7;
		bfr.AddIntFixed(dow, 1);
	}
}
class Pft_fmt_itm_iso_fmt implements Pft_fmt_itm {
	public Pft_fmt_itm_iso_fmt() {}
	public int TypeId() {return Pft_fmt_itm_.Tid_iso_fmt;}
	public void Fmt(BryWtr bfr, Xowe_wiki wiki, Xol_lang_itm lang, GfoDate date, Pft_func_formatdate_bldr bldr) {
		bfr.AddStrA7(date.ToStrFmt("yyyy-MM-dd"));
		bfr.AddByte(AsciiByte.Ltr_T);
		bfr.AddStrA7(date.ToStrFmt("HH:mm:ss"));
		bfr.AddStrA7(date.ToStrTz());
	}
}
class Pft_fmt_itm_rfc_5322 implements Pft_fmt_itm {
	public Pft_fmt_itm_rfc_5322() {}
	public int TypeId() {return Pft_fmt_itm_.Tid_rfc_5322;}
	public void Fmt(BryWtr bfr, Xowe_wiki wiki, Xol_lang_itm lang, GfoDate date, Pft_func_formatdate_bldr bldr) {// Mon, 02 Jan 2012 10:15:01 +0000
		int dow = date.DayOfWeek();
		DateAdpTranslator_xapp.Translate(wiki, lang, GfoDateUtl.SegIdxDayOfWeek, dow, bfr);
		bfr.AddByte(AsciiByte.Comma).AddByte(AsciiByte.Space);
		bfr.AddStrA7(date.ToStrFmt("dd MMM yyyy HH:mm:ss"));	// NOTE: always UTC time
		bfr.Add(CONST_timezone);									// NOTE: always UTC time zone
	}	private static final byte[] CONST_timezone = BryUtl.NewA7(" +0000");
}
class Pft_fmt_itm_timezone_offset implements Pft_fmt_itm {
	public Pft_fmt_itm_timezone_offset() {}
	public int TypeId() {return Pft_fmt_itm_.Tid_timezone_offset;}
	public void Fmt(BryWtr bfr, Xowe_wiki wiki, Xol_lang_itm lang, GfoDate date, Pft_func_formatdate_bldr bldr) {
		bfr.AddIntVariable(date.TimezoneOffset());
	}
}
