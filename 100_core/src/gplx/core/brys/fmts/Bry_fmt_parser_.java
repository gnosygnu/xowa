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
package gplx.core.brys.fmts; import gplx.*; import gplx.core.*; import gplx.core.brys.*;
public class Bry_fmt_parser_ {
	public static Bry_fmt_itm[] Parse(byte escape, byte grp_bgn, byte grp_end, Bfr_fmt_arg[] args, byte[][] keys, byte[] src) {
		int src_len = src.length;
		int pos = 0;
		int txt_bgn = -1;
		int key_idx = -1;
		Hash_adp_bry keys_hash = Hash_adp_bry.cs(); 
		List_adp list = List_adp_.New();
		while (true) {
			boolean is_last = pos == src_len;
			byte b = is_last ? escape : src[pos];
			if (b == escape) {
				if (txt_bgn != -1) list.Add(new Bry_fmt_itm(Bry_fmt_itm.Tid__txt, txt_bgn, pos));
				if (is_last) break;
				++pos;
				if (pos == src_len) throw Err_.new_("bry_fmtr", "fmt cannot end with escape", "escape", Byte_ascii.To_str(escape), "raw", src);
				b = src[pos];
				if		(b == escape) {
					list.Add(new Bry_fmt_itm(Bry_fmt_itm.Tid__txt, pos, pos + 1));
					++pos;
				}
				else if (b == grp_bgn) {
					++pos;
					int grp_end_pos = Bry_find_.Find_fwd(src, grp_end, pos); if (grp_end_pos == Bry_find_.Not_found) throw Err_.new_("bry_fmtr", "grp_end missing", "grp_bgn", Byte_ascii.To_str(grp_bgn), "grp_end", Byte_ascii.To_str(grp_end), "raw", src);
					byte[] key_bry = Bry_.Mid(src, pos, grp_end_pos);
					Bry_fmt_itm key_itm = (Bry_fmt_itm)keys_hash.Get_by_bry(key_bry);
					if (key_itm == null) {
						key_itm = new Bry_fmt_itm(Bry_fmt_itm.Tid__key, pos - 2, grp_end_pos + 1);	// -2 to get "~{"; +1 to get "}"
						key_itm.Key_idx = ++key_idx;
						keys_hash.Add(key_bry, key_itm);
					}
					list.Add(key_itm);
					pos = grp_end_pos + 1;
				}
				else throw Err_.new_("bry_fmtr", "escape must be followed by escape or group_bgn", "escape", Byte_ascii.To_str(escape), "group_bgn", Byte_ascii.To_str(escape), "raw", src);
				txt_bgn = -1;
			}
			else {
				if (txt_bgn == -1) txt_bgn = pos;
				++pos;
			}
		}
		Bry_fmt_itm[] rv = (Bry_fmt_itm[])list.To_ary_and_clear(Bry_fmt_itm.class);
		int len = args.length;
		for (int i = 0; i < len; ++i) {
			Bfr_fmt_arg arg = args[i];
			Bry_fmt_itm key_itm = (Bry_fmt_itm)keys_hash.Get_by(arg.Key); if (key_itm == null) continue;
			key_itm.Tid = Bry_fmt_itm.Tid__arg;
			key_itm.Arg = arg.Arg;
		}
		len = keys.length;
		for (int i = 0; i < len; ++i) {
			byte[] key = keys[i];
			Bry_fmt_itm key_itm = (Bry_fmt_itm)keys_hash.Get_by(key); if (key_itm == null) continue; // NOTE: ignore missing keys; EX: fmt=a~{b}c keys=b,d; do not fail b/c ~{d} is not in fmt; allows redefining from tests
			key_itm.Key_idx = i;
		}
		return rv;
	}
	public static byte[][] Parse_keys(byte[] src) {
		Ordered_hash list = Ordered_hash_.New_bry();
		int src_len = src.length;
		int pos = -1;
		while (pos < src_len) {
			int lhs_pos = Bry_find_.Move_fwd(src, Bry_arg_lhs, pos + 1, src_len);
			if (lhs_pos == Bry_find_.Not_found) break;	// no more "~{"
			int rhs_pos = Bry_find_.Find_fwd(src, Byte_ascii.Curly_end, lhs_pos, src_len);
			if (rhs_pos == Bry_find_.Not_found) throw Err_.new_("bry_fmt", "unable to find closing }", "src", src);
			if (rhs_pos - lhs_pos == 0) throw Err_.new_("bry_fmt", "{} will result in empty key", "src", src);
			byte[] key = Bry_.Mid(src, lhs_pos, rhs_pos);
			if (!list.Has(key)) list.Add(key, key);
			pos = rhs_pos;	// NOTE: auto-increment done at top of loop
		}
		return (byte[][])list.To_ary(byte[].class);
	}	private static final    byte[] Bry_arg_lhs = Bry_.new_a7("~{");
}
