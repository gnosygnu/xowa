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
package gplx.xowa.xtns.pfuncs.times; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.pfuncs.*;
import gplx.xowa.langs.*;
class Pft_fmt_itm_seg_int implements Pft_fmt_itm {
	public Pft_fmt_itm_seg_int(int segIdx, int len, boolean fixed_len) {this.segIdx = segIdx; this.fixed_len = fixed_len; this.len = len;} private int segIdx, len; boolean fixed_len;
	public int TypeId() {return Pft_fmt_itm_.Tid_seg_int;}
	public int SegIdx() {return segIdx;}
	public void Fmt(Bry_bfr bfr, Xowe_wiki wiki, Xol_lang_itm lang, DateAdp date, Pft_func_formatdate_bldr bldr) {
		int val = date.Segment(segIdx);
		if (fixed_len)	bfr.Add_int_fixed(val, len);
		else			bfr.Add_int_variable(val);
	}
}
class Pft_fmt_itm_raw implements Pft_fmt_itm {
	public int TypeId() {return Pft_fmt_itm_.Tid_raw;}
	public void Fmt(Bry_bfr bfr, Xowe_wiki wiki, Xol_lang_itm lang, DateAdp date, Pft_func_formatdate_bldr bldr) {
		// TODO_OLD: should flag .Raw() on bldr to skip transliterating numerals in foreign languages; DATE:2013-12-31
	}
}
class Pft_fmt_itm_seg_str implements Pft_fmt_itm {
	public Pft_fmt_itm_seg_str(int segIdx, int type) {this.segIdx = segIdx; this.type = type;} private int segIdx, type;
	public int TypeId() {return Pft_fmt_itm_.Tid_seg_str;}
	public void Fmt(Bry_bfr bfr, Xowe_wiki wiki, Xol_lang_itm lang, DateAdp date, Pft_func_formatdate_bldr bldr) {
		DateAdpTranslator_xapp.Translate(wiki, lang, type, date.Segment(segIdx), bfr);
	}
}
class Pft_fmt_itm_year_isLeap implements Pft_fmt_itm {
	public int TypeId() {return Pft_fmt_itm_.Tid_year_isLeap;}
	public void Fmt(Bry_bfr bfr, Xowe_wiki wiki, Xol_lang_itm lang, DateAdp date, Pft_func_formatdate_bldr bldr) {bfr.Add_int_fixed(DateAdp_.IsLeapYear(date.Year()) ? 1 : 0, 1);}
}
class Pft_fmt_itm_hour_base12 implements Pft_fmt_itm {
	public int TypeId() {return Pft_fmt_itm_.Tid_hour_base12;}
	public void Fmt(Bry_bfr bfr, Xowe_wiki wiki, Xol_lang_itm lang, DateAdp date, Pft_func_formatdate_bldr bldr) {
		int val = date.Hour();
		switch (val) {
			case 0: val = 12; break;
			case 13: case 14: case 15: case 16: case 17: case 18: case 19: case 20: case 21: case 22: case 23: val -= 12; break;
			default: break;
		}
		if (fixed_len)	bfr.Add_int_fixed(val, 2);
		else			bfr.Add_int_variable(val);
	}
	public Pft_fmt_itm_hour_base12(boolean fixed_len) {this.fixed_len = fixed_len;} private boolean fixed_len;
}
class Pft_fmt_itm_timestamp_unix implements Pft_fmt_itm {
	public int TypeId() {return Pft_fmt_itm_.Tid_timestamp_unix;}
	public void Fmt(Bry_bfr bfr, Xowe_wiki wiki, Xol_lang_itm lang, DateAdp date, Pft_func_formatdate_bldr bldr) {bfr.Add_long_variable(date.Timestamp_unix());}
}
class Pft_fmt_itm_raw_ary implements Pft_fmt_itm {
	public int TypeId() {return Pft_fmt_itm_.Tid_raw_ary;}
	public void Fmt(Bry_bfr bfr, Xowe_wiki wiki, Xol_lang_itm lang, DateAdp date, Pft_func_formatdate_bldr bldr) {bfr.Add_mid(src, bgn, end);}
	public Pft_fmt_itm_raw_ary(byte[] src, int bgn, int end) {this.src = src; this.bgn = bgn; this.end = end;} private byte[] src; int bgn; int end;
}
class Pft_fmt_itm_raw_byt implements Pft_fmt_itm {
	public int TypeId() {return Pft_fmt_itm_.Tid_raw_byt;}
	public void Fmt(Bry_bfr bfr, Xowe_wiki wiki, Xol_lang_itm lang, DateAdp date, Pft_func_formatdate_bldr bldr) {bfr.Add_byte(b);}
	public Pft_fmt_itm_raw_byt(byte b) {this.b = b;} private byte b;
}
class Pft_fmt_itm_daysInMonth implements Pft_fmt_itm {
	public int TypeId() {return Pft_fmt_itm_.Tid_daysInMonth;}
	public void Fmt(Bry_bfr bfr, Xowe_wiki wiki, Xol_lang_itm lang, DateAdp date, Pft_func_formatdate_bldr bldr) {bfr.Add_int_variable(DateAdp_.DaysInMonth(date));}
}
class Pft_fmt_itm_dayOfYear implements Pft_fmt_itm {
	public int TypeId() {return Pft_fmt_itm_.Tid_dayOfYear;}
	public void Fmt(Bry_bfr bfr, Xowe_wiki wiki, Xol_lang_itm lang, DateAdp date, Pft_func_formatdate_bldr bldr) {bfr.Add_int_variable(date.DayOfYear() - Int_.Base1);}	// php is base1; .net/java is base0
}
class Pft_fmt_itm_am_pm implements Pft_fmt_itm {
	public int TypeId() {return Pft_fmt_itm_.Tid_AmPm;}
	public void Fmt(Bry_bfr bfr, Xowe_wiki wiki, Xol_lang_itm lang, DateAdp date, Pft_func_formatdate_bldr bldr) {
		boolean am = date.Hour() < 13;
		byte[] val = null;
		if		( am &&  lower) val = Ary_am_lower;
		else if ( am && !lower) val = Ary_am_upper;
		else if (!am &&  lower) val = Ary_pm_lower;
		else if (!am && !lower) val = Ary_pm_upper;
		bfr.Add(val);
	}	private static final    byte[] Ary_am_upper = Bry_.new_a7("AM"), Ary_pm_upper = Bry_.new_a7("PM"), Ary_am_lower = Bry_.new_a7("am"), Ary_pm_lower = Bry_.new_a7("pm");
	public Pft_fmt_itm_am_pm(boolean lower) {this.lower = lower;} private boolean lower;
}
class Pft_fmt_itm_dow_base0 implements Pft_fmt_itm {
	public int TypeId() {return Pft_fmt_itm_.Tid_dow_base0;}
	public void Fmt(Bry_bfr bfr, Xowe_wiki wiki, Xol_lang_itm lang, DateAdp date, Pft_func_formatdate_bldr bldr) {
		int dow = date.DayOfWeek();
		if (dow == 0) dow = 7;
		bfr.Add_int_fixed(dow, 1);
	}
}
class Pft_fmt_itm_iso_fmt implements Pft_fmt_itm {
	public Pft_fmt_itm_iso_fmt() {}
	public int TypeId() {return Pft_fmt_itm_.Tid_iso_fmt;}
	public void Fmt(Bry_bfr bfr, Xowe_wiki wiki, Xol_lang_itm lang, DateAdp date, Pft_func_formatdate_bldr bldr) {
		bfr.Add_str_a7(date.XtoStr_fmt("yyyy-MM-dd"));
		bfr.Add_byte(Byte_ascii.Ltr_T);
		bfr.Add_str_a7(date.XtoStr_fmt("HH:mm:ss"));
		bfr.Add_str_a7(date.XtoStr_tz());
	}
}
class Pft_fmt_itm_rfc_5322 implements Pft_fmt_itm {
	public Pft_fmt_itm_rfc_5322() {}
	public int TypeId() {return Pft_fmt_itm_.Tid_rfc_5322;}
	public void Fmt(Bry_bfr bfr, Xowe_wiki wiki, Xol_lang_itm lang, DateAdp date, Pft_func_formatdate_bldr bldr) {// Mon, 02 Jan 2012 10:15:01 +0000
		int dow = date.DayOfWeek();
		DateAdpTranslator_xapp.Translate(wiki, lang, DateAdp_.SegIdx_dayOfWeek, dow, bfr);
		bfr.Add_byte(Byte_ascii.Comma).Add_byte(Byte_ascii.Space);
		bfr.Add_str_a7(date.XtoStr_fmt("dd MMM yyyy HH:mm:ss"));	// NOTE: always UTC time 
		bfr.Add(CONST_timezone);									// NOTE: always UTC time zone
	}	private static final    byte[] CONST_timezone = Bry_.new_a7(" +0000"); 
}
class Pft_fmt_itm_timezone_offset implements Pft_fmt_itm {
	public Pft_fmt_itm_timezone_offset() {}
	public int TypeId() {return Pft_fmt_itm_.Tid_timezone_offset;}
	public void Fmt(Bry_bfr bfr, Xowe_wiki wiki, Xol_lang_itm lang, DateAdp date, Pft_func_formatdate_bldr bldr) {
		bfr.Add_int_variable(date.Timezone_offset());
	}
}
