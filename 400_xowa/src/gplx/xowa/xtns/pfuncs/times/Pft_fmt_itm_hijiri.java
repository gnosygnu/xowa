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
class Pft_fmt_itm_hijiri {
	public static int[] Calc_date(DateAdp date) {
		int[] rv = new int[3];	// MEM:cache
		Calc_date(rv, date.Year(), date.Month(), date.Day());
		return rv;
	}
	public static boolean Calc_date(int[] rv, int greg_y, int greg_m, int greg_d) {
		int hiji_d = greg_d;
		int hiji_m = greg_m;
		int hiji_y = greg_y;
		int tmp_jd = 0;
		if (
			(hiji_y > 1582) || ((hiji_y == 1582) && (hiji_m > 10)) ||
			((hiji_y == 1582) && (hiji_m == 10) && (hiji_d > 14))
			)
		{
			tmp_jd = (int)((1461 * (hiji_y + 4800 + (int)((hiji_m - 14) / 12))) / 4) +
				(int)((367 * (hiji_m - 2 - 12 * ((int)((hiji_m - 14) / 12)))) / 12) -
				(int)((3 * (int)(((hiji_y + 4900 + (int)((hiji_m - 14) / 12)) / 100))) / 4) +
				hiji_d - 32075;
		} else {
			tmp_jd = 367 * hiji_y - (int)((7 * (hiji_y + 5001 + (int)((hiji_m - 9) / 7))) / 4) +
						(int)((275 * hiji_m) / 9) + hiji_d + 1729777;
		}
		int tmp_l = tmp_jd -1948440 + 10632;
		int tmp_n = (int)((tmp_l - 1) / 10631);
		tmp_l = tmp_l - 10631 * tmp_n + 354;
		int tmp_j = ((int)((10985 - tmp_l) / 5316)) * ((int)((50 * tmp_l) / 17719)) + ((int)(tmp_l / 5670)) * ((int)((43 * tmp_l) / 15238));
		tmp_l = tmp_l - ((int)((30 - tmp_j) / 15)) * ((int)((17719 * tmp_j) / 50)) - ((int)(tmp_j / 16)) * ((int)((15238 * tmp_j) / 43)) + 29;
		hiji_m = (int)((24 * tmp_l) / 709);
		hiji_d = tmp_l - (int)((709 * hiji_m) / 24);
		hiji_y = 30 * tmp_n + tmp_j - 30;
		rv[0] = hiji_y;
		rv[1] = hiji_m;
		rv[2] = hiji_d;
		return true;
	}
	public static byte[] Get_month_name(Xowe_wiki wiki, DateAdp date) {
		int[] seg_ary = Calc_date(date);
		int m = seg_ary[Rslt__month] - List_adp_.Base1;
		byte[] msg_key = Month_names[m];
		return wiki.Msg_mgr().Val_by_key_obj(msg_key);
	}
	private static final    byte[][] Month_names = new byte[][] 
	{ Bry_.new_a7("hijiri-calendar-m1"), Bry_.new_a7("hijiri-calendar-m2"), Bry_.new_a7("hijiri-calendar-m3")
	, Bry_.new_a7("hijiri-calendar-m4"), Bry_.new_a7("hijiri-calendar-m5"), Bry_.new_a7("hijiri-calendar-m6")
	, Bry_.new_a7("hijiri-calendar-m7"), Bry_.new_a7("hijiri-calendar-m8"), Bry_.new_a7("hijiri-calendar-m9")
	, Bry_.new_a7("hijiri-calendar-m10"), Bry_.new_a7("hijiri-calendar-m11"), Bry_.new_a7("hijiri-calendar-m12")
	};
	public static final    int
	  Rslt__year			= 0
	, Rslt__month			= 1
	, Rslt__day				= 2
	;
}
