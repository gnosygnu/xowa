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
package gplx.xowa.htmls.modules.popups;
import gplx.types.basics.utls.BryLni;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.BryFind;
import gplx.types.basics.utls.IntUtl;
import gplx.types.basics.constants.AsciiByte;
import gplx.xowa.parsers.*;
class Xow_popup_anchor_finder {
	private byte[] src, find;
	private int src_len, nl_lhs;
	public int Find(byte[] src, int src_len, byte[] find, int bgn) {
		this.src = src; this.src_len = src_len; this.find = find; this.nl_lhs = bgn;
		if (bgn == Xop_parser_.Doc_bgn_bos && Find_hdr(bgn)) return Xop_parser_.Doc_bgn_bos;// handle BOS separately which won't fit "\n=" search below; EX: "BOS==A==\n"
		int lhs_bgn = bgn;
		while (true) {
			lhs_bgn = BryFind.FindFwd(src, Hdr_bgn, nl_lhs, src_len);
			if (lhs_bgn == BryFind.NotFound) break;	// "\n=" not found; exit;
			if (Find_hdr(lhs_bgn)) return lhs_bgn;
		}
		return Find_id(bgn);
	}
	private boolean Find_hdr(int lhs_bgn) {
		int nl_rhs = BryFind.FindFwd(src, AsciiByte.Nl, nl_lhs + 1, src_len);		// look for \n
		if (nl_rhs == BryFind.NotFound) nl_rhs = src_len - 1;							// no more \n; set to last idx
		nl_lhs = nl_rhs;																	// update nl_lhs for loop
		int lhs_end = BryFind.FindFwdWhile(src, lhs_bgn + 1, nl_rhs, AsciiByte.Eq);	// skip eq; EX: "\n==="; +1 to skip eq
		int rhs_end = BryFind.TrimBwdSpaceTab(src, nl_rhs, lhs_end);					// skip ws bwd; EX: "==   \n"
		int rhs_bgn = BryFind.FindBwdWhile(src, rhs_end, lhs_end, AsciiByte.Eq);		// skip eq; EX: "==\n" -> pos before =
		if (rhs_bgn < lhs_end) return false;												// eq found, but is actually lhs_eq; no rhs_eq, so exit; EX: "\n== \n"
		++rhs_bgn;																			// rhs_bgn is 1st char before eq; position at eq; neede for txt_end below
		int txt_bgn = BryFind.TrimFwdSpaceTab(src, lhs_end, nl_rhs);					// trim ws
		int txt_end = BryFind.TrimBwdSpaceTab(src, rhs_bgn, lhs_end);					// trim ws
		return BryLni.Eq(src, txt_bgn, txt_end, find);										// check for strict match
	}
	private int Find_id(int bgn) {
		byte[] quoted = BryUtl.Add(AsciiByte.QuoteBry, find, AsciiByte.QuoteBry);
		int rv = Find_id_by_quoted(bgn, quoted);
		if (rv == BryFind.NotFound) {
			quoted[0] = AsciiByte.Apos; quoted[quoted.length - 1] = AsciiByte.Apos;
			rv = Find_id_by_quoted(bgn, quoted);
		}
		return rv;
	}
	private int Find_id_by_quoted(int bgn, byte[] quoted) {
		int rv = BryFind.NotFound;
		int pos = BryFind.FindFwd(src, quoted, bgn);
		if (pos == BryFind.NotFound) return rv;
		pos = BryFind.TrimBwdSpaceTab(src, pos, bgn);
		if (src[pos - 1] != AsciiByte.Eq) return rv;
		int id_end = BryFind.TrimBwdSpaceTab(src, pos - 1, bgn);
		int id_bgn = BryFind.FindBwdWs(src, id_end - 1, bgn);
		boolean id_match = IntUtl.BoundsChk(id_bgn, id_end, src_len) && BryLni.Eq(src, id_bgn + 1, id_end, Id_bry);
		if (!id_match) return rv;
		rv = BryFind.FindBwd(src, AsciiByte.Nl, id_bgn);
		return rv == BryFind.NotFound ? 0 : rv;
	}
	private static final byte[] Hdr_bgn = BryUtl.NewA7("\n="), Id_bry = BryUtl.NewA7("id");
}
