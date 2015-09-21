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
package gplx.xowa.xtns.pfuncs.times; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.pfuncs.*;
import gplx.xowa.langs.*;
import gplx.xowa.parsers.*; import gplx.xowa.parsers.tmpls.*;
public class Pft_func_date_int extends Pf_func_base {
	public Pft_func_date_int(int id, int date_tid) {this.id = id; this.date_tid = date_tid;} private int date_tid;
	@Override public int Id() {return id;} private int id;
	@Override public Pf_func New(int id, byte[] name) {return new Pft_func_date_int(id, date_tid).Name_(name);}
	@Override public void Func_evaluate(Xop_ctx ctx, byte[] src, Xot_invk caller, Xot_invk self, Bry_bfr bfr) {
		DateAdp date = DateAdp_.MinValue;
		Xowe_wiki wiki = ctx.Wiki(); Xol_lang lang = ctx.Lang();
	    switch (date_tid) {
	        case Date_tid_lcl: date = DateAdp_.Now(); break;
	        case Date_tid_utc: date = DateAdp_.Now().XtoUtc(); break;
	        case Date_tid_rev: date = ctx.Cur_page().Revision_data().Modified_on(); break;
			default: throw Err_.new_unhandled(date_tid);
	    }
		switch (id) {
			case Xol_kwd_grp_.Id_utc_year:
			case Xol_kwd_grp_.Id_lcl_year:
			case Xol_kwd_grp_.Id_rev_year:
				Pft_func_formatdate.Date_bldr().Format(bfr, wiki, lang, date, Pft_fmt_itm_.Year_len4);
				break;
			case Xol_kwd_grp_.Id_utc_month_int_len2:
			case Xol_kwd_grp_.Id_lcl_month_int_len2:
			case Xol_kwd_grp_.Id_rev_month_int_len2:
				Pft_func_formatdate.Date_bldr().Format(bfr, wiki, lang, date, Pft_fmt_itm_.Month_int_len2);
				break;
			case Xol_kwd_grp_.Id_utc_month_int:
			case Xol_kwd_grp_.Id_lcl_month_int:
			case Xol_kwd_grp_.Id_rev_month_int:
				Pft_func_formatdate.Date_bldr().Format(bfr, wiki, lang, date, Pft_fmt_itm_.Month_int);
				break;
			case Xol_kwd_grp_.Id_utc_day_int_len2:
			case Xol_kwd_grp_.Id_lcl_day_int_len2:
			case Xol_kwd_grp_.Id_rev_day_int_len2:
				Pft_func_formatdate.Date_bldr().Format(bfr, wiki, lang, date, Pft_fmt_itm_.Day_int_len2);
				break;
			case Xol_kwd_grp_.Id_utc_day_int:
			case Xol_kwd_grp_.Id_lcl_day_int:
			case Xol_kwd_grp_.Id_rev_day_int:
				Pft_func_formatdate.Date_bldr().Format(bfr, wiki, lang, date, Pft_fmt_itm_.Day_int);
				break;
			case Xol_kwd_grp_.Id_lcl_hour:
			case Xol_kwd_grp_.Id_utc_hour:
				Pft_func_formatdate.Date_bldr().Format(bfr, wiki, lang, date, Pft_fmt_itm_.Hour_base24_len2);
				break;
			case Xol_kwd_grp_.Id_lcl_dow:
			case Xol_kwd_grp_.Id_utc_dow:
				Pft_func_formatdate.Date_bldr().Format(bfr, wiki, lang, date, Pft_fmt_itm_.Dow_base1_int);
				break;
			case Xol_kwd_grp_.Id_lcl_week:
			case Xol_kwd_grp_.Id_utc_week:
				Pft_func_formatdate.Date_bldr().Format(bfr, wiki, lang, date, Pft_fmt_itm_.WeekOfYear_int);
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
	public static final Pft_func_date_int
	  Utc = new Pft_func_date_int(-1, Date_tid_utc)
	, Lcl = new Pft_func_date_int(-1, Date_tid_lcl)
	, Rev = new Pft_func_date_int(-1, Date_tid_rev);
}
