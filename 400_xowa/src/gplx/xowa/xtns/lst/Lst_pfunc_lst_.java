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
package gplx.xowa.xtns.lst; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import gplx.xowa.parsers.*; import gplx.xowa.parsers.tmpls.*;
public class Lst_pfunc_lst_ {
	private static final byte Include_between = 0, Include_to_eos = 1, Include_to_bos = 2;
	public static void Sect_include(Bry_bfr bfr, Lst_section_nde_mgr sec_mgr, byte[] src, byte[] lst_bgn, byte[] lst_end) {
		if		(lst_end == Lst_pfunc_itm.Null_arg) {		// no end arg; EX: {{#lst:page|bgn}}; NOTE: different than {{#lst:page|bgn|}}
			if	(lst_bgn == Lst_pfunc_itm.Null_arg) {		// no bgn arg; EX: {{#lst:page}}
				bfr.Add(src);				// write all and exit
				return;
			}
			else							// bgn exists; set end to bgn; EX: {{#lst:page|bgn}} is same as {{#lst:page|bgn|bgn}}; NOTE: {{#lst:page|bgn|}} means write from bgn to eos
				lst_end = lst_bgn;				
		}
		byte include_mode = Include_between;
		if		(Bry_.Len_eq_0(lst_end))
			include_mode = Include_to_eos;
		else if (Bry_.Len_eq_0(lst_bgn))
			include_mode = Include_to_bos;				
		int bgn_pos = 0; boolean bgn_found = false; int src_page_bry_len = src.length;
		int sections_len = sec_mgr.Len();
		for (int i = 0; i < sections_len; i++) {
			Lst_section_nde section = sec_mgr.Get_at(i);
			byte section_tid = section.Name_tid();
			byte[] section_key = section.Section_name();
			if		(section_tid == Lst_section_nde.Xatr_bgn && Bry_.Eq(section_key, lst_bgn)) {
				int sect_bgn_rhs = section.Xnde().Tag_close_end();
				if (include_mode == Include_to_eos) {					// write from cur to eos; EX: {{#lst:page|bgn|}}
					bfr.Add_mid(src, sect_bgn_rhs, src_page_bry_len);
					return;
				}
				else {													// bgn and end
					if (!bgn_found) {									// NOTE: !bgn_found to prevent "resetting" of dupe; EX: <s begin=key0/>a<s begin=key0/>b; should start from a not b
						bgn_pos = sect_bgn_rhs;
						bgn_found = true;
					}
				}
			}
			else if (section_tid == Lst_section_nde.Xatr_end && Bry_.Eq(section_key, lst_end)) {
				int sect_end_lhs = section.Xnde().Tag_open_bgn();
				if (include_mode == Include_to_bos) {					// write from bos to cur; EX: {{#lst:page||end}}
					bfr.Add_mid(src, 0, sect_end_lhs);
					return;
				}
				else {
					if (bgn_found) {									// NOTE: bgn_found to prevent writing from bos; EX: a<s end=key0/>b should not write anything 
						bfr.Add_mid(src, bgn_pos, sect_end_lhs);
						bgn_found = false;
					}
				}
			}
		}
		if (bgn_found)	// bgn_found, but no end; write to end of page; EX: "a <section begin=key/> b" -> " b"
			bfr.Add_mid(src, bgn_pos, src_page_bry_len);
	}
}
