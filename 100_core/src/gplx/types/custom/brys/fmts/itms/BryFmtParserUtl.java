/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2021 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.types.custom.brys.fmts.itms;
import gplx.types.basics.utls.BryLni;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.BryFind;
import gplx.types.errs.ErrUtl;
import gplx.types.commons.lists.GfoListBase;
import gplx.types.basics.constants.AsciiByte;
import gplx.types.basics.lists.Hash_adp_bry;
import gplx.types.basics.lists.Ordered_hash;
import gplx.types.basics.lists.Ordered_hash_;
public class BryFmtParserUtl {
	public static BryFmtItm[] Parse(byte escape, byte grpBgn, byte grpEnd, BfrFmtArg[] args, byte[][] keys, byte[] src) {
		int srcLen = src.length;
		int pos = 0;
		int txtBgn = -1;
		int keyIdx = -1;
		Hash_adp_bry keysHash = Hash_adp_bry.cs();
		GfoListBase<BryFmtItm> list = new GfoListBase<>();
		while (true) {
			boolean isLast = pos == srcLen;
			byte b = isLast ? escape : src[pos];
			if (b == escape) {
				if (txtBgn != -1) list.Add(new BryFmtItm(BryFmtItm.Tid__txt, txtBgn, pos));
				if (isLast) break;
				++pos;
				if (pos == srcLen) throw ErrUtl.NewArgs("fmt cannot end with escape", "escape", AsciiByte.ToStr(escape), "raw", src);
				b = src[pos];
				if        (b == escape) {
					list.Add(new BryFmtItm(BryFmtItm.Tid__txt, pos, pos + 1));
					++pos;
				}
				else if (b == grpBgn) {
					++pos;
					int grp_end_pos = BryFind.FindFwd(src, grpEnd, pos); if (grp_end_pos == BryFind.NotFound) throw ErrUtl.NewArgs("grpEnd missing", "grpBgn", AsciiByte.ToStr(grpBgn), "grpEnd", AsciiByte.ToStr(grpEnd), "raw", src);
					byte[] key_bry = BryLni.Mid(src, pos, grp_end_pos);
					BryFmtItm key_itm = (BryFmtItm)keysHash.Get_by_bry(key_bry);
					if (key_itm == null) {
						key_itm = new BryFmtItm(BryFmtItm.Tid__key, pos - 2, grp_end_pos + 1);    // -2 to get "~{"; +1 to get "}"
						key_itm.Key_idx = ++keyIdx;
						keysHash.Add(key_bry, key_itm);
					}
					list.Add(key_itm);
					pos = grp_end_pos + 1;
				}
				else throw ErrUtl.NewArgs("escape must be followed by escape or groupBgn", "escape", AsciiByte.ToStr(escape), "groupBgn", AsciiByte.ToStr(escape), "raw", src);
				txtBgn = -1;
			}
			else {
				if (txtBgn == -1) txtBgn = pos;
				++pos;
			}
		}
		BryFmtItm[] rv = list.ToAry(BryFmtItm.class);
		int len = args.length;
		for (int i = 0; i < len; ++i) {
			BfrFmtArg arg = args[i];
			BryFmtItm key_itm = (BryFmtItm)keysHash.GetByOrNull(arg.Key); if (key_itm == null) continue;
			key_itm.Tid = BryFmtItm.Tid__arg;
			key_itm.Arg = arg.Arg;
		}
		len = keys.length;
		for (int i = 0; i < len; ++i) {
			byte[] key = keys[i];
			BryFmtItm key_itm = (BryFmtItm)keysHash.GetByOrNull(key); if (key_itm == null) continue; // NOTE: ignore missing keys; EX: fmt=a~{b}c keys=b,d; do not fail b/c ~{d} is not in fmt; allows redefining from tests
			key_itm.Key_idx = i;
		}
		return rv;
	}
	public static byte[][] ParseKeys(byte[] src) {
		Ordered_hash list = Ordered_hash_.New_bry();
		int src_len = src.length;
		int pos = -1;
		while (pos < src_len) {
			int lhs_pos = BryFind.MoveFwd(src, BryArgLhs, pos + 1, src_len);
			if (lhs_pos == BryFind.NotFound) break;    // no more "~{"
			int rhs_pos = BryFind.FindFwd(src, AsciiByte.CurlyEnd, lhs_pos, src_len);
			if (rhs_pos == BryFind.NotFound) throw ErrUtl.NewArgs("unable to find closing }", "src", src);
			if (rhs_pos - lhs_pos == 0) throw ErrUtl.NewArgs("{} will result in empty key", "src", src);
			byte[] key = BryLni.Mid(src, lhs_pos, rhs_pos);
			if (!list.Has(key)) list.Add(key, key);
			pos = rhs_pos;    // NOTE: auto-increment done at top of loop
		}
		return (byte[][])list.ToAry(byte[].class);
	}
	private static final byte[] BryArgLhs = BryUtl.NewA7("~{");
}
