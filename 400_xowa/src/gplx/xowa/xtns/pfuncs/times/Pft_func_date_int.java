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
import gplx.xowa.langs.*; import gplx.xowa.langs.kwds.*;
import gplx.xowa.parsers.*; import gplx.xowa.parsers.tmpls.*; import gplx.xowa.xtns.pfuncs.times.*;
public class Pft_func_date_int extends Pf_func_base {
	public Pft_func_date_int(int id, int date_tid) {this.id = id; this.date_tid = date_tid;} private int date_tid;
	@Override public int Id() {return id;} private int id;
	@Override public Pf_func New(int id, byte[] name) {return new Pft_func_date_int(id, date_tid).Name_(name);}
	@Override public void Func_evaluate(Bry_bfr bfr, Xop_ctx ctx, Xot_invk caller, Xot_invk self, byte[] src) {
		DateAdp date = DateAdp_.MinValue;
		Xowe_wiki wiki = ctx.Wiki(); Xol_lang_itm lang = ctx.Lang();
		Pft_func_formatdate_bldr date_fmt_bldr = wiki.Parser_mgr().Date_fmt_bldr();
	    switch (date_tid) {
	        case Date_tid_lcl: date = Datetime_now.Get(); break;
	        case Date_tid_utc: date = Datetime_now.Get().XtoUtc(); break;
	        case Date_tid_rev: date = ctx.Page().Db().Page().Modified_on(); break;
			default: throw Err_.new_unhandled(date_tid);
	    }
		switch (id) {
			case Xol_kwd_grp_.Id_utc_year:
			case Xol_kwd_grp_.Id_lcl_year:
			case Xol_kwd_grp_.Id_rev_year:
				date_fmt_bldr.Format(bfr, wiki, lang, date, Pft_fmt_itm_.Year_len4);
				break;
			case Xol_kwd_grp_.Id_utc_month_int_len2:
			case Xol_kwd_grp_.Id_lcl_month_int_len2:
			case Xol_kwd_grp_.Id_rev_month_int_len2:
				date_fmt_bldr.Format(bfr, wiki, lang, date, Pft_fmt_itm_.Month_int_len2);
				break;
			case Xol_kwd_grp_.Id_utc_month_int:
			case Xol_kwd_grp_.Id_lcl_month_int:
			case Xol_kwd_grp_.Id_rev_month_int:
				date_fmt_bldr.Format(bfr, wiki, lang, date, Pft_fmt_itm_.Month_int);
				break;
			case Xol_kwd_grp_.Id_utc_day_int_len2:
			case Xol_kwd_grp_.Id_lcl_day_int_len2:
			case Xol_kwd_grp_.Id_rev_day_int_len2:
				date_fmt_bldr.Format(bfr, wiki, lang, date, Pft_fmt_itm_.Day_int_len2);
				break;
			case Xol_kwd_grp_.Id_utc_day_int:
			case Xol_kwd_grp_.Id_lcl_day_int:
			case Xol_kwd_grp_.Id_rev_day_int:
				date_fmt_bldr.Format(bfr, wiki, lang, date, Pft_fmt_itm_.Day_int);
				break;
			case Xol_kwd_grp_.Id_lcl_hour:
			case Xol_kwd_grp_.Id_utc_hour:
				date_fmt_bldr.Format(bfr, wiki, lang, date, Pft_fmt_itm_.Hour_base24_len2);
				break;
			case Xol_kwd_grp_.Id_lcl_dow:
			case Xol_kwd_grp_.Id_utc_dow:
				date_fmt_bldr.Format(bfr, wiki, lang, date, Pft_fmt_itm_.Dow_base1_int);
				break;
			case Xol_kwd_grp_.Id_lcl_week:
			case Xol_kwd_grp_.Id_utc_week:
				date_fmt_bldr.Format(bfr, wiki, lang, date, Pft_fmt_itm_.WeekOfYear_int);
				break;
			case Xol_kwd_grp_.Id_lcl_time:
			case Xol_kwd_grp_.Id_utc_time:		// 17:29
				bfr.Add_int_fixed(date.Hour(), 2).Add_byte(Byte_ascii.Colon).Add_int_fixed(date.Minute(), 2);
				break;
			case Xol_kwd_grp_.Id_lcl_timestamp:	
			case Xol_kwd_grp_.Id_utc_timestamp:
			case Xol_kwd_grp_.Id_rev_timestamp:	// 20120123172956
				bfr	.Add_int_fixed(date.Year()	, 4)
					.Add_int_fixed(date.Month()	, 2)
					.Add_int_fixed(date.Day()	, 2)
					.Add_int_fixed(date.Hour()	, 2)
					.Add_int_fixed(date.Minute(), 2)
					.Add_int_fixed(date.Second(), 2);
				break;
			default: throw Err_.new_unhandled(id);
		}
	}
	public static final int Date_tid_utc = 0, Date_tid_lcl = 1, Date_tid_rev = 2;
	public static final    Pft_func_date_int
	  Utc = new Pft_func_date_int(-1, Date_tid_utc)
	, Lcl = new Pft_func_date_int(-1, Date_tid_lcl)
	, Rev = new Pft_func_date_int(-1, Date_tid_rev);
}
