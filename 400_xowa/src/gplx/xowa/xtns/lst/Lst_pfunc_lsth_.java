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
import gplx.xowa.wikis.pages.wtxts.*;
import gplx.xowa.parsers.*; import gplx.xowa.parsers.hdrs.*;
class Lst_pfunc_lsth_ {
	public static void Hdr_include(Bry_bfr bfr, byte[] src, Xopg_toc_mgr toc_mgr, byte[] lhs_hdr, byte[] rhs_hdr) {// REF.MW:LabeledSectionTransclusion.class.php|pfuncIncludeHeading; MW does regex on text; XO uses section_itms
		// get <hdr> idxs
		int len = toc_mgr.Len();
		int lhs_idx = Match_or_neg1(toc_mgr, len, src, lhs_hdr, 0)			; if (lhs_idx == -1) return;
		int rhs_idx = Match_or_neg1(toc_mgr, len, src, rhs_hdr, lhs_idx + 1);

		// get snip_bgn
		Xop_hdr_tkn lhs_tkn = toc_mgr.Get_at(lhs_idx);
		int snip_bgn = lhs_tkn.Src_end();

		// get snip_end
		int snip_end = -1;
		if (rhs_idx == -1) {		// rhs_idx missing or not supplied
			rhs_idx = lhs_idx + 1;
			if (rhs_idx < len) {	// next hdr after lhs_hdr exists; try to get next "matching" hdr; EX: h2 should match next h2; PAGE:en.w:10s_BC; DATE:2016-08-13
				for (int i = rhs_idx; i < len; ++i) {
					Xop_hdr_tkn rhs_tkn = toc_mgr.Get_at(i);
					if (rhs_tkn.Num() == lhs_tkn.Num()) {
						snip_end = rhs_tkn.Src_bgn();
						break;
					}
				}
			}
			if (snip_end == -1)		// no matching rhs exists, or rhs is last; get till EOS
				snip_end = src.length;
		}
		else {
			Xop_hdr_tkn rhs_tkn = toc_mgr.Get_at(rhs_idx);
			snip_end = rhs_tkn.Src_bgn();
		}
		bfr.Add_mid(src, snip_bgn, snip_end);
	}
	private static int Match_or_neg1(Xopg_toc_mgr toc_mgr, int hdrs_len, byte[] src, byte[] match, int hdrs_bgn) {
		for (int i = hdrs_bgn; i < hdrs_len; ++i) {
			Xop_hdr_tkn hdr = toc_mgr.Get_at(i);
			int txt_bgn = hdr.Src_bgn() + hdr.Num();	// skip "\n=="; 1=leading \n
			if (hdr.Src_bgn() != Xop_parser_.Doc_bgn_char_0)
				++txt_bgn;

			// get txt_end; note that this needs to handle multiple trailing \n which is included in hdr.Src_end()
			int txt_end = Bry_find_.Find_fwd(src, Bry__hdr_end, txt_bgn);				// find "=\n"
			txt_end = Bry_find_.Find_bwd__skip(src, txt_end, txt_bgn, Byte_ascii.Eq);	// skip bwd to get to pos before 1st "="; EX: "===\n" -> find "=="

			// remove ws
			txt_bgn = Bry_find_.Find_fwd_while_ws(src, txt_bgn, txt_end);
			txt_end = Bry_find_.Find_bwd__skip_ws(src, txt_end, txt_bgn);
			if (Bry_.Eq(src, txt_bgn, txt_end, match)) return i;
		}
		return -1;
	}
	private static final    byte[] Bry__hdr_end = Bry_.new_a7("=\n");
}
