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
package gplx.xowa.htmls.portal; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*;
public class Xoh_rtl_utl {		
	private static final    int[] tmp_ary = new int[32];	// support no more than 16 items
	private static Bry_bfr bfr = Bry_bfr_.Reset(32);
	public static byte[] Reverse_li(byte[] src) {
		int src_len = src.length;
		int pos = 0;
		while (true) {
			int ul_bgn = Bry_find_.Find_fwd(src, Ul_bgn, pos, src_len);
			if (ul_bgn == Bry_find_.Not_found) break;	// no more <ul
			int ul_end = Bry_find_.Find_fwd(src, Ul_end, ul_bgn + Ul_bgn.length, src_len);
			if (ul_end == Bry_find_.Not_found) break;	// no more </ul>
			bfr.Add_mid(src, pos, ul_bgn);					// add pos -> <ul
			ul_end += Ul_end.length;
			Reverse_li_src(bfr, src, ul_bgn, ul_end, tmp_ary);
			pos = ul_end;
		}
		bfr.Add_mid(src, pos, src_len);			// add rest of String
		return bfr.To_bry_and_clear();			// exit
	}
	private static void Reverse_li_src(Bry_bfr bfr, byte[] src, int ul_bgn, int ul_end, int[] tmp_ary) {
		int pos = ul_bgn;
		int tmp_idx = 0;
		while (true) {
			int li_bgn = Bry_find_.Find_fwd(src, Li_bgn, pos, ul_end);
			if (li_bgn == Bry_find_.Not_found) break;	// no more <li
			int li_end = Bry_find_.Find_fwd(src, Li_end, pos, ul_end);
			if (li_end == Bry_find_.Not_found) break;	// no more </li>
			tmp_ary[tmp_idx++] = li_bgn;
			tmp_ary[tmp_idx++] = li_end + Li_end.length;
			pos = li_end + Li_end.length;
		}
		int ul_bgn_rhs = Bry_find_.Find_fwd(src, Byte_ascii.Gt, ul_bgn);
		if (tmp_idx < 3	// 0 or 1 li; add everything between ul
			|| ul_bgn_rhs == Bry_find_.Not_found
			) {
			bfr.Add_mid(src, ul_bgn, ul_end);
			return;
		}
		int li_n_end = tmp_ary[tmp_idx - 1];
		++ul_bgn_rhs;
		bfr.Add_mid(src, ul_bgn, ul_bgn_rhs);			// add from "<ul" -> to 1st ">"
		for (int i = tmp_idx - 1; i > -1; i -= 2) {
			int li_end = tmp_ary[i];
			int prv_pos = i < 2 ? ul_bgn_rhs : tmp_ary[i - 2];
			bfr.Add_mid(src, prv_pos, li_end);			// add from "<li" -> to "</li>"
		}
		bfr.Add_mid(src, li_n_end, ul_end);				// add from nth "</li>" -> "</ul>"
	}
	private static final    byte[]
	  Ul_bgn = Bry_.new_a7("<ul")
	, Ul_end = Bry_.new_a7("</ul>")
	, Li_bgn = Bry_.new_a7("<li")
	, Li_end = Bry_.new_a7("</li>")
	;
}
