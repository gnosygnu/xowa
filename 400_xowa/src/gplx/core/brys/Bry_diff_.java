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
package gplx.core.brys; import gplx.*; import gplx.core.*;
public class Bry_diff_ {
	public static byte[][] Diff_1st_line(byte[] lhs, byte[] rhs) {return Diff_1st(lhs, 0, lhs.length, rhs, 0, rhs.length, Byte_ascii.Nl_bry, Byte_ascii.Angle_bgn_bry, 255);}
	public static byte[][] Diff_1st(byte[] lhs, int lhs_bgn, int lhs_end, byte[] rhs, int rhs_bgn, int rhs_end, byte[] stop, byte[] show, int diff_max) {
		int lhs_len = lhs_end - lhs_bgn;
		int rhs_len = rhs_end - rhs_bgn;
		int len = lhs_len < rhs_len ? lhs_len : rhs_len;
		int lhs_idx = -1, rhs_idx = -1;
		for (int i = 0; i < len; ++i) {
			byte lhs_byte = lhs[i + lhs_bgn];
			byte rhs_byte = rhs[i + rhs_bgn];
			if (lhs_byte != rhs_byte) {lhs_idx = rhs_idx = i; break;}		// diff; stop iterating
		}
		if (lhs_idx == -1 && rhs_idx == -1) {
			switch (Int_.Compare(lhs_len, rhs_len)) {
				case CompareAble_.Same: return null;
				case CompareAble_.Less: lhs_idx = rhs_idx = lhs_len; break;
				case CompareAble_.More: lhs_idx = rhs_idx = rhs_len; break;
			}
		}
		byte[] lhs_diff = Get_1st(stop, show, lhs, lhs_idx, lhs_len, diff_max);
		byte[] rhs_diff = Get_1st(stop, show, rhs, rhs_idx, rhs_len, diff_max);
		return new byte[][] {lhs_diff, rhs_diff};
	}
	private static byte[] Get_1st(byte[] stop, byte[] show, byte[] src, int bgn, int end, int diff_max) {
		if (bgn == end) return Bry__eos;
		int prv_show = Bry_find_.Find_bwd(src, show, bgn		, 0); if (prv_show == Bry_find_.Not_found) prv_show = 0;
		int prv_stop = Bry_find_.Find_bwd(src, stop, bgn		, 0); prv_stop = (prv_stop == Bry_find_.Not_found) ? 0 : prv_stop + 1;
		int prv = prv_show > prv_stop ? prv_show : prv_stop;
		int nxt = Bry_find_.Find_fwd(src, stop, bgn		, end);	if (nxt == Bry_find_.Not_found) nxt = end;
		if (nxt - prv > 255) nxt = prv + diff_max;
		return Bry_.Mid(src, prv, nxt);
	}
	private static final byte[] Bry__eos = Bry_.new_a7("<<EOS>>");
}
