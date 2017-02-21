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
package gplx.xowa.parsers.amps; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import gplx.core.btries.*;
import gplx.langs.htmls.entitys.*;
public class Xop_amp_mgr {	// TS
	private static final    Btrie_rv trv = new Btrie_rv();
	public Btrie_slim_mgr Amp_trie() {return amp_trie;} private final    Btrie_slim_mgr amp_trie = Gfh_entity_trie.Instance;
	public Xop_amp_mgr_rslt Parse_tkn(Xop_tkn_mkr tkn_mkr, byte[] src, int src_len, int amp_pos, int bgn) {
		int fail_pos = amp_pos + 1;	// default to fail pos which is after &

		// check amp_trie; EX: 'lt'
		Xop_amp_mgr_rslt rv = new Xop_amp_mgr_rslt();
		Gfh_entity_itm itm; int cur;
		synchronized (trv) {
			itm = (Gfh_entity_itm)amp_trie.Match_at(trv, src, bgn, src_len);
			cur = trv.Pos();
		}

		if (itm == null) {
			rv.Pass_n_(fail_pos);
			return rv;
		}

		// check itm
		switch (itm.Tid()) {
			// letters; EX: '&lt;'
			case Gfh_entity_itm.Tid_name_std:
			case Gfh_entity_itm.Tid_name_xowa:
				rv.Pos_(cur);
				rv.Tkn_(tkn_mkr.Amp_txt(amp_pos, cur, itm));
				return rv;
			// numbers; EX: '&#123;' '&#x123'
			case Gfh_entity_itm.Tid_num_hex:
			case Gfh_entity_itm.Tid_num_dec:
				boolean ncr_is_hex = itm.Tid() == Gfh_entity_itm.Tid_num_hex;
				boolean pass = Parse_ncr(rv, ncr_is_hex, src, src_len, amp_pos, cur);
				if (pass) {	// NOTE: do not set rv.Pos_(); will be set by Parse_ncr
					rv.Tkn_(tkn_mkr.Amp_num(amp_pos, rv.Pos(), rv.Val()));
					return rv;
				}
				else {
					rv.Pass_n_(fail_pos);
					return rv;
				}
			default: throw Err_.new_unhandled_default(itm.Tid());
		}
	}
	public boolean Parse_ncr(Xop_amp_mgr_rslt rv, boolean ncr_is_hex, byte[] src, int src_len, int amp_pos, int num_bgn) {
		int fail_pos = amp_pos + 1;	// default to fail pos; after amp;

		// find semic; fail if none found
		int semic_pos = Bry_find_.Find_fwd(src, Byte_ascii.Semic, num_bgn, src_len);
		if (semic_pos == Bry_find_.Not_found) return rv.Pass_n_(fail_pos);
		int num_end = semic_pos - 1;	// num_end = pos before semicolon

		// calc amp_val; EX: &#x3A3; -> 931; &#931; -> 931;
		int multiple = ncr_is_hex ? 16 : 10, val = 0, factor = 1, cur = 0;
		for (int i = num_end; i >= num_bgn; i--) {
			byte b = src[i];
			if (ncr_is_hex) {
				if		(b >=  48 && b <=  57)	cur = b - 48;
				else if	(b >=  65 && b <=  70)	cur = b - 55;
				else if	(b >=  97 && b <= 102)	cur = b - 87;
				else if((b >=  71 && b <=  90)
					||  (b >=  91 && b <= 122))	continue;	// NOTE: wiki discards letters G-Z; PAGE:en.w:Miscellaneous_Symbols "{{Unicode|&#xx26D0;}}"; NOTE 2nd x is discarded
				else							return rv.Pass_n_(fail_pos);
			}
			else {
				cur = b - Byte_ascii.Num_0;
				if (cur < 0 || cur > 10)		return rv.Pass_n_(fail_pos);
			}
			val += cur * factor;
			if (val > gplx.core.intls.Utf8_.Codepoint_max) return rv.Pass_n_(fail_pos);	// fail if value > largest_unicode_codepoint
			factor *= multiple;
		}
		return rv.Pass_y_(semic_pos + 1, val);	// +1 to position after semic
	}
	public byte[] Decode_as_bry(byte[] src) {
		if (src == null) return src;
		boolean dirty = false;
		int end = src.length;
		int pos = 0;
		Xop_amp_mgr_rslt amp_rv = null;
		Bry_bfr bfr = null;
		Btrie_rv trv = null;

		// scan for &
		while (pos < end) {
			byte b = src[pos];
			if (b == Byte_ascii.Amp) {	// & found
				int nxt_pos = pos + 1;
				if (nxt_pos < end) {	// check & is not eos
					byte nxt_b = src[nxt_pos];

					if (trv == null) trv = new Btrie_rv();						
					Object amp_obj = amp_trie.Match_at_w_b0(trv, nxt_b, src, nxt_pos, end);
					int amp_pos = trv.Pos();

					if (amp_obj != null) {
						if (!dirty) {	// 1st amp found; add preceding String to bfr
							if (bfr == null) {
								bfr = Bry_bfr_.Get();
								dirty = true;
							}
							bfr.Add_mid(src, 0, pos);
						}
						Gfh_entity_itm amp_itm = (Gfh_entity_itm)amp_obj;
						switch (amp_itm.Tid()) {
							case Gfh_entity_itm.Tid_name_std:
							case Gfh_entity_itm.Tid_name_xowa:
								bfr.Add(amp_itm.U8_bry());
								pos = amp_pos;
								break;
							case Gfh_entity_itm.Tid_num_hex:
							case Gfh_entity_itm.Tid_num_dec:
								boolean ncr_is_hex = amp_itm.Tid() == Gfh_entity_itm.Tid_num_hex;
								int int_bgn = amp_pos;
								if (amp_rv == null)
									amp_rv = new Xop_amp_mgr_rslt();
								boolean pass = Parse_ncr(amp_rv, ncr_is_hex, src, end, pos, int_bgn);
								if (pass)
									bfr.Add_u8_int(amp_rv.Val());
								else 
									bfr.Add_mid(src, pos, nxt_pos);
								pos = amp_rv.Pos();
								break;
							default:
								throw Err_.new_unhandled_default(amp_itm.Tid());
						}
						continue;
					}
				}					
			}
			if (dirty)
				bfr.Add_byte(b);
			++pos;
		}
		return dirty ? bfr.To_bry_and_clear_and_rls() : src;
	}
        public static final    Xop_amp_mgr Instance = new Xop_amp_mgr(); Xop_amp_mgr() {}
}
