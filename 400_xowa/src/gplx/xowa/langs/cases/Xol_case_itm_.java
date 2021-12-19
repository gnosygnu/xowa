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
package gplx.xowa.langs.cases;
import gplx.types.basics.lists.List_adp;
import gplx.types.basics.lists.List_adp_;
import gplx.types.basics.lists.Ordered_hash;
import gplx.types.basics.lists.Ordered_hash_;
import gplx.types.basics.utls.BryLni;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.BryFind;
import gplx.types.errs.ErrUtl;
import gplx.types.basics.constants.AsciiByte;
import gplx.types.basics.utls.BoolUtl;
import gplx.types.basics.utls.ByteUtl;
import gplx.types.basics.utls.StringUtl;
import gplx.xowa.langs.parsers.Xol_csv_parser;
public class Xol_case_itm_ {
	public static final byte Tid_both = 0, Tid_upper = 1, Tid_lower = 2;
	public static Xol_case_itm new_(int tid, String src_str, String trg_str) {return new_((byte)tid, BryUtl.NewU8(src_str), BryUtl.NewU8(trg_str));}
	public static Xol_case_itm new_(byte tid, byte[] src, byte[] trg) {
		if (src.length == 1 && trg.length == 1)
			return new Xol_case_itm_byt(tid, src[0], trg[0]);
		else
			return new Xol_case_itm_bry(tid, src, trg);
	}
	public static Xol_case_itm[] parse_xo_(byte[] src) {
		List_adp list = List_adp_.New();
		int src_len = src.length, src_pos = 0, fld_bgn = 0, fld_idx = 0;
		byte cur_cmd = ByteUtl.Zero;
		byte[] cur_lhs = null;
		Xol_csv_parser csv_parser = Xol_csv_parser.Instance;
		while (true) {
			boolean last = src_pos == src_len;
			byte b = last ? AsciiByte.Nl : src[src_pos];
			switch (b) {
				case AsciiByte.Pipe:
					switch (fld_idx) {
						case 0:
							boolean fail = true;
							if (src_pos - fld_bgn == 1) {
								byte cmd_byte = src[src_pos - 1];
								cur_cmd = ByteUtl.Zero;
								switch (cmd_byte) {
									case AsciiByte.Num0:	cur_cmd = Xol_case_itm_.Tid_both; fail = false; break;
									case AsciiByte.Num1:	cur_cmd = Xol_case_itm_.Tid_upper; fail = false; break;
									case AsciiByte.Num2:	cur_cmd = Xol_case_itm_.Tid_lower; fail = false; break;
								}
							}
							if (fail) throw ErrUtl.NewArgs("cmd is invalid", "cmd", StringUtl.NewU8(src, fld_bgn, src_pos));
							break;
						case 1: cur_lhs = csv_parser.Load(src, fld_bgn, src_pos); break;
					}
					++fld_idx;
					fld_bgn = src_pos + 1;
					break;
				case AsciiByte.Nl:
					if (!(fld_idx == 0 && fld_bgn == src_pos)) {
						byte[] cur_rhs = csv_parser.Load(src, fld_bgn, src_pos);
						Xol_case_itm itm = Xol_case_itm_.new_(cur_cmd, cur_lhs, cur_rhs);
						list.Add(itm);
					}
					cur_cmd = ByteUtl.Zero;
					cur_lhs = null;
					fld_idx = 0;
					fld_bgn = src_pos + 1;
					break;
			}
			if (last) break;
			++src_pos;
		}
		return (Xol_case_itm[])list.ToAry(Xol_case_itm.class);
	}
	public static Xol_case_itm[] parse_mw_(byte[] raw) {
		Ordered_hash hash = Ordered_hash_.New_bry();
		int pos = 0;
		pos = parse_mw_grp(hash, raw, BoolUtl.Y, pos);
		pos = parse_mw_grp(hash, raw, BoolUtl.N, pos);
		return (Xol_case_itm[])hash.ToAry(Xol_case_itm.class);
	}
	private static int parse_mw_grp(Ordered_hash hash, byte[] raw, boolean section_is_upper, int find_bgn) {
		byte[] find = section_is_upper ? parse_mw_upper : parse_mw_lower;
		int raw_len = raw.length;
		int pos = BryFind.FindFwd(raw, find, find_bgn);					if (pos == BryFind.NotFound) throw ErrUtl.NewArgs("could not find section name", "name", StringUtl.NewU8(find));
		pos = BryFind.FindFwd(raw, AsciiByte.CurlyBgn, pos, raw_len);	if (pos == BryFind.NotFound) throw ErrUtl.NewArgs("could not find '{' after section name", "name", StringUtl.NewU8(find));
		int itm_bgn = 0;
		boolean quote_off = true, itm_is_first = true;
		byte[] cur_lhs = BryUtl.Empty;
		boolean loop = true;
		while (loop) {
			if (pos >= raw_len) break;
			byte b = raw[pos];
			switch (b) {
				case AsciiByte.Quote:
					if (quote_off) {
						itm_bgn = pos + 1;
						quote_off = false;
					}
					else {
						if (itm_is_first) {
							cur_lhs = BryLni.Mid(raw, itm_bgn, pos);
							itm_is_first = false;
						}
						else {
							byte[] cur_rhs = BryLni.Mid(raw, itm_bgn, pos);
							byte[] upper = null, lower = null; byte tid = ByteUtl.Zero, rev_tid = ByteUtl.Zero;
							if (section_is_upper) {
								upper = cur_rhs;
								lower = cur_lhs;
								tid = Xol_case_itm_.Tid_upper;
								rev_tid = Xol_case_itm_.Tid_lower;
							}
							else {
								upper = cur_lhs;
								lower = cur_rhs;
								tid = Xol_case_itm_.Tid_lower;
								rev_tid = Xol_case_itm_.Tid_upper;
							}
							Xol_case_itm_bry itm = (Xol_case_itm_bry)hash.GetByOrNull(upper);
							if (itm == null) {
								itm = new Xol_case_itm_bry(tid, upper, lower);
								hash.Add(upper, itm);
							}
							else {
								if (itm.Tid() == rev_tid && BryLni.Eq(itm.Src_ary(), upper) && BryLni.Eq(itm.Trg_ary(), lower))
									itm.Tid_(Xol_case_itm_.Tid_both);
								else {
									itm = new Xol_case_itm_bry(tid, cur_lhs, cur_rhs);
									byte[] add_key = BryUtl.Add(section_is_upper ? Bry_upper : Bry_lower, Bry_pipe, upper, Bry_pipe, lower);
									hash.Add(add_key, itm);
								}
							}
							itm_is_first = true;
						}
						quote_off = true;
					}
					break;
				case AsciiByte.CurlyEnd:
					loop = false;
					break;
			}
			++pos;
		}
		return pos;
	}	private static final byte[] parse_mw_upper= BryUtl.NewA7("wikiUpperChars"), parse_mw_lower= BryUtl.NewA7("wikiLowerChars"), Bry_upper = BryUtl.NewA7("upper"), Bry_lower = BryUtl.NewA7("lower"), Bry_pipe = BryUtl.NewA7("|");
	static final String GRP_KEY = "xowa.langs.case_parser";
}
