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
class Pft_fmt_itm_iranian {
	private static final    int[] Md__greg	= new int[] { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
	private static final    int[] Md__iran	= new int[] { 31, 31, 31, 31, 31, 31, 30, 30, 30, 30, 30, 29 };
	public static int[] Calc_date(DateAdp date) {
		int[] rv = new int[3]; // MEM:cache
		Calc_date(rv, date.Year(), date.Month(), date.Day());
		return rv;
	}
	public static boolean Calc_date(int[] rv, int greg_y, int greg_m, int greg_d) {	// REF.MW:Language.php|tsToIranian
		greg_y -= 1600;
		--greg_m;
		--greg_d;

		// Days passed from the beginning (including leap years)
		int greg_doy = 365 * greg_y
			+ (int)((greg_y + 3) / 4)
			- (int)((greg_y + 99) / 100)
			+ (int)((greg_y + 399) / 400)
			;

		// Add days of the past months of this year
		for (int i = 0; i < greg_m; ++i) {
			greg_doy += Md__greg[i];
		}

		// Leap years
		if (greg_m > 1 && ((greg_y % 4 == 0 && greg_y % 100 != 0 || (greg_y % 400 == 0))))
			greg_doy++;

		// Days passed in current month
		greg_doy += greg_d;

		int iran_doy = greg_doy - 79;

		int iran_np = (int)(iran_doy / 12053);
		iran_doy %= 12053;

		int iran_y = 979 + 33 * iran_np + 4 * (int)(iran_doy / 1461);
		iran_doy %= 1461;

		if (iran_doy >= 366) {
			iran_y += (int)((iran_doy - 1) / 365);
			iran_doy = (int)((iran_doy - 1) % 365);
		}

		int j = 0;
		for (j = 0; j < 11 && iran_doy >= Md__iran[j]; ++j)
			iran_doy -= Md__iran[j];
		
		int iran_m = j + 1;
		int iran_d = iran_doy + 1;

		rv[0] = iran_y;
		rv[1] = iran_m;
		rv[2] = iran_d;
		return true;
	}
	public static byte[] Get_month_name(Xowe_wiki wiki, DateAdp date) {
		int[] seg_ary = Calc_date(date);
		int m = seg_ary[Rslt__month] - List_adp_.Base1;
		byte[] msg_key = Month_names[m];
		return wiki.Msg_mgr().Val_by_key_obj(msg_key);
	}
	private static final    byte[][] Month_names = new byte[][] 
	{ Bry_.new_a7("iranian-calendar-m1"), Bry_.new_a7("iranian-calendar-m2"), Bry_.new_a7("iranian-calendar-m3")
	, Bry_.new_a7("iranian-calendar-m4"), Bry_.new_a7("iranian-calendar-m5"), Bry_.new_a7("iranian-calendar-m6")
	, Bry_.new_a7("iranian-calendar-m7"), Bry_.new_a7("iranian-calendar-m8"), Bry_.new_a7("iranian-calendar-m9")
	, Bry_.new_a7("iranian-calendar-m10"), Bry_.new_a7("iranian-calendar-m11"), Bry_.new_a7("iranian-calendar-m12")
	};
	public static final    int
	  Rslt__year			= 0
	, Rslt__month			= 1
	, Rslt__day				= 2
	;
}
