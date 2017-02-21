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
class Pft_fmt_itm_roman implements Pft_fmt_itm {
	public int TypeId() {return Pft_fmt_itm_.Tid_roman;}
	public void Fmt(Bry_bfr bfr, Xowe_wiki wiki, Xol_lang_itm lang, DateAdp date, Pft_func_formatdate_bldr bldr) {
		int nxt_idx = bldr.Idx_cur() + 1;
		Pft_fmt_itm[] ary = bldr.Fmt_itms();
		if (nxt_idx < ary.length) {
			Pft_fmt_itm itm = (Pft_fmt_itm)ary[nxt_idx];
			if (itm.TypeId() == Pft_fmt_itm_.Tid_seg_int) {
				Pft_fmt_itm_seg_int nxt_int = (Pft_fmt_itm_seg_int)ary[nxt_idx];	// FUTURE: should check tkn type
				int v = date.Segment(nxt_int.SegIdx());
				Pfxtp_roman.ToRoman(v, bfr);
				bldr.Idx_nxt_(nxt_idx + 1);
				return;
			}
		}
		bfr.Add_str_a7("xf");
	}
}
class Pft_fmt_itm_thai implements Pft_fmt_itm {
	public int TypeId() {return Pft_fmt_itm_.Tid_thai;}
	public void Fmt(Bry_bfr bfr, Xowe_wiki wiki, Xol_lang_itm lang, DateAdp date, Pft_func_formatdate_bldr bldr) {
		bfr.Add_int_variable(date.Year() + 543);
	}
}
class Pft_fmt_itm_minguo implements Pft_fmt_itm {
	public int TypeId() {return Pft_fmt_itm_.Tid_minguo;}
	public void Fmt(Bry_bfr bfr, Xowe_wiki wiki, Xol_lang_itm lang, DateAdp date, Pft_func_formatdate_bldr bldr) {
		bfr.Add_int_variable(date.Year() - 1911);
	}
}
class Pft_fmt_itm_hebrew_year_num implements Pft_fmt_itm {
	public int TypeId() {return Pft_fmt_itm_.Tid_hebrew_year_num;}
	public void Fmt(Bry_bfr bfr, Xowe_wiki wiki, Xol_lang_itm lang, DateAdp date, Pft_func_formatdate_bldr bldr) {
		int[] hebrew_date = Pft_fmt_itm_hebrew_.Calc_hebrew_date(date);
		bfr.Add_int_variable(hebrew_date[Pft_fmt_itm_hebrew_.Rslt_year_num]);
	}
}
class Pft_fmt_itm_hebrew_month_num implements Pft_fmt_itm {
	public int TypeId() {return Pft_fmt_itm_.Tid_hebrew_month_num;}
	public void Fmt(Bry_bfr bfr, Xowe_wiki wiki, Xol_lang_itm lang, DateAdp date, Pft_func_formatdate_bldr bldr) {
		int[] hebrew_date = Pft_fmt_itm_hebrew_.Calc_hebrew_date(date);
		bfr.Add_int_variable(hebrew_date[Pft_fmt_itm_hebrew_.Rslt_month_num]);
	}
}
class Pft_fmt_itm_hebrew_day_num implements Pft_fmt_itm {
	public int TypeId() {return Pft_fmt_itm_.Tid_hebrew_day_num;}
	public void Fmt(Bry_bfr bfr, Xowe_wiki wiki, Xol_lang_itm lang, DateAdp date, Pft_func_formatdate_bldr bldr) {
		int[] hebrew_date = Pft_fmt_itm_hebrew_.Calc_hebrew_date(date);
		bfr.Add_int_variable(hebrew_date[Pft_fmt_itm_hebrew_.Rslt_day_num]);
	}
}
class Pft_fmt_itm_hebrew_month_days_count implements Pft_fmt_itm {
	public int TypeId() {return Pft_fmt_itm_.Tid_hebrew_month_days_count;}
	public void Fmt(Bry_bfr bfr, Xowe_wiki wiki, Xol_lang_itm lang, DateAdp date, Pft_func_formatdate_bldr bldr) {
		int[] hebrew_date = Pft_fmt_itm_hebrew_.Calc_hebrew_date(date);
		bfr.Add_int_variable(hebrew_date[Pft_fmt_itm_hebrew_.Rslt_month_days_count]);
	}
}
class Pft_fmt_itm_hebrew_month_name_full implements Pft_fmt_itm {
	public int TypeId() {return Pft_fmt_itm_.Tid_hebrew_month_name_full;}
	public void Fmt(Bry_bfr bfr, Xowe_wiki wiki, Xol_lang_itm lang, DateAdp date, Pft_func_formatdate_bldr bldr) {
		bfr.Add(Pft_fmt_itm_hebrew_.Get_hebrew_month_name_full(wiki, date));
	}
}
class Pft_fmt_itm_hebrew_month_name_gen implements Pft_fmt_itm {
	public int TypeId() {return Pft_fmt_itm_.Tid_hebrew_month_name_gen;}
	public void Fmt(Bry_bfr bfr, Xowe_wiki wiki, Xol_lang_itm lang, DateAdp date, Pft_func_formatdate_bldr bldr) {
		bfr.Add(Pft_fmt_itm_hebrew_.Get_hebrew_month_name_gen(wiki, date));
	}
}
class Pft_fmt_itm_hebrew_numeral implements Pft_fmt_itm {
	public int TypeId() {return Pft_fmt_itm_.Tid_hebrew_numeral;}
	public void Fmt(Bry_bfr bfr, Xowe_wiki wiki, Xol_lang_itm lang, DateAdp date, Pft_func_formatdate_bldr bldr) {
		int num_int = bfr.To_int_and_clear(-1);
		byte[] num_bry = Pft_fmt_itm_hebrew_.Calc_hebrew_numeral(num_int);
		bfr.Add(num_bry);
	}
}
class Pft_fmt_itm_iranian_year_idx implements Pft_fmt_itm {
	public int TypeId() {return Pft_fmt_itm_.Tid_iranian_year_idx;}
	public void Fmt(Bry_bfr bfr, Xowe_wiki wiki, Xol_lang_itm lang, DateAdp date, Pft_func_formatdate_bldr bldr) {
		int[] seg_ary = Pft_fmt_itm_iranian.Calc_date(date);
		bfr.Add_int_variable(seg_ary[Pft_fmt_itm_iranian.Rslt__year]);
	}
}
class Pft_fmt_itm_iranian_month_idx implements Pft_fmt_itm {
	public int TypeId() {return Pft_fmt_itm_.Tid_iranian_month_idx;}
	public void Fmt(Bry_bfr bfr, Xowe_wiki wiki, Xol_lang_itm lang, DateAdp date, Pft_func_formatdate_bldr bldr) {
		int[] seg_ary = Pft_fmt_itm_iranian.Calc_date(date);
		bfr.Add_int_variable(seg_ary[Pft_fmt_itm_iranian.Rslt__month]);
	}
}
class Pft_fmt_itm_iranian_day_idx implements Pft_fmt_itm {
	public int TypeId() {return Pft_fmt_itm_.Tid_iranian_day_idx;}
	public void Fmt(Bry_bfr bfr, Xowe_wiki wiki, Xol_lang_itm lang, DateAdp date, Pft_func_formatdate_bldr bldr) {
		int[] seg_ary = Pft_fmt_itm_iranian.Calc_date(date);
		bfr.Add_int_variable(seg_ary[Pft_fmt_itm_iranian.Rslt__day]);
	}
}
class Pft_fmt_itm_iranian_month_name implements Pft_fmt_itm {
	public int TypeId() {return Pft_fmt_itm_.Tid_iranian_month_name;}
	public void Fmt(Bry_bfr bfr, Xowe_wiki wiki, Xol_lang_itm lang, DateAdp date, Pft_func_formatdate_bldr bldr) {
		bfr.Add(Pft_fmt_itm_iranian.Get_month_name(wiki, date));
	}
}
class Pft_fmt_itm_hijiri_year_idx implements Pft_fmt_itm {
	public int TypeId() {return Pft_fmt_itm_.Tid_hijiri_year_idx;}
	public void Fmt(Bry_bfr bfr, Xowe_wiki wiki, Xol_lang_itm lang, DateAdp date, Pft_func_formatdate_bldr bldr) {
		int[] seg_ary = Pft_fmt_itm_hijiri.Calc_date(date);
		bfr.Add_int_variable(seg_ary[Pft_fmt_itm_hijiri.Rslt__year]);
	}
}
class Pft_fmt_itm_hijiri_month_idx implements Pft_fmt_itm {
	public int TypeId() {return Pft_fmt_itm_.Tid_hijiri_month_idx;}
	public void Fmt(Bry_bfr bfr, Xowe_wiki wiki, Xol_lang_itm lang, DateAdp date, Pft_func_formatdate_bldr bldr) {
		int[] seg_ary = Pft_fmt_itm_hijiri.Calc_date(date);
		bfr.Add_int_variable(seg_ary[Pft_fmt_itm_hijiri.Rslt__month]);
	}
}
class Pft_fmt_itm_hijiri_day_idx implements Pft_fmt_itm {
	public int TypeId() {return Pft_fmt_itm_.Tid_hijiri_day_idx;}
	public void Fmt(Bry_bfr bfr, Xowe_wiki wiki, Xol_lang_itm lang, DateAdp date, Pft_func_formatdate_bldr bldr) {
		int[] seg_ary = Pft_fmt_itm_hijiri.Calc_date(date);
		bfr.Add_int_variable(seg_ary[Pft_fmt_itm_hijiri.Rslt__day]);
	}
}
class Pft_fmt_itm_hijiri_month_name implements Pft_fmt_itm {
	public int TypeId() {return Pft_fmt_itm_.Tid_hijiri_month_name;}
	public void Fmt(Bry_bfr bfr, Xowe_wiki wiki, Xol_lang_itm lang, DateAdp date, Pft_func_formatdate_bldr bldr) {
		bfr.Add(Pft_fmt_itm_hijiri.Get_month_name(wiki, date));
	}
}
