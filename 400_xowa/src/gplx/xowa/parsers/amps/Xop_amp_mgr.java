/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012 gnosygnu@gmail.com

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package gplx.xowa.parsers.amps; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import gplx.core.btries.*;
public class Xop_amp_mgr {	// TS
	public Btrie_slim_mgr Amp_trie() {return amp_trie;} private final    Btrie_slim_mgr amp_trie = Xop_amp_trie.Instance;
	public Xop_amp_mgr_rslt Parse_tkn(Xop_tkn_mkr tkn_mkr, byte[] src, int src_len, int amp_pos, int bgn) {
		int fail_pos = amp_pos + 1;	// default to fail pos which is after &

		// check amp_trie; EX: 'lt'
		Xop_amp_mgr_rslt rv = new Xop_amp_mgr_rslt();
		Btrie_rv match = amp_trie.Match_at(src, bgn, src_len);
		Xop_amp_trie_itm itm = (Xop_amp_trie_itm)match.Obj();
		int cur = match.Pos();
		match.Pool__rls();
		if (itm == null) {
			rv.Pass_n_(fail_pos);
			return rv;
		}

		// check itm
		switch (itm.Tid()) {
			// letters; EX: '&lt;'
			case Xop_amp_trie_itm.Tid_name_std:
			case Xop_amp_trie_itm.Tid_name_xowa:
				rv.Pos_(cur);
				rv.Tkn_(tkn_mkr.Amp_txt(amp_pos, cur, itm));
				return rv;
			// numbers; EX: '&#123;' '&#x123'
			case Xop_amp_trie_itm.Tid_num_hex:
			case Xop_amp_trie_itm.Tid_num_dec:
				boolean ncr_is_hex = itm.Tid() == Xop_amp_trie_itm.Tid_num_hex;
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

		// scan for &
		while (pos < end) {
			byte b = src[pos];
			if (b == Byte_ascii.Amp) {	// & found
				int nxt_pos = pos + 1;
				if (nxt_pos < end) {	// check & is not eos
					byte nxt_b = src[nxt_pos];

					Btrie_rv trie_rv = amp_trie.Match_at_w_b0(nxt_b, src, nxt_pos, end);
					Object amp_obj = trie_rv.Obj();
					int amp_pos = trie_rv.Pos();
					trie_rv.Pool__rls();
					if (amp_obj != null) {
						if (!dirty) {	// 1st amp found; add preceding String to bfr
							if (bfr == null) {
								bfr = Bry_bfr_.Get();
								dirty = true;
							}
							bfr.Add_mid(src, 0, pos);
						}
						Xop_amp_trie_itm amp_itm = (Xop_amp_trie_itm)amp_obj;
						switch (amp_itm.Tid()) {
							case Xop_amp_trie_itm.Tid_name_std:
							case Xop_amp_trie_itm.Tid_name_xowa:
								bfr.Add(amp_itm.U8_bry());
								pos = amp_pos;
								break;
							case Xop_amp_trie_itm.Tid_num_hex:
							case Xop_amp_trie_itm.Tid_num_dec:
								boolean ncr_is_hex = amp_itm.Tid() == Xop_amp_trie_itm.Tid_num_hex;
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
//		private Xop_tkn_itm Parse_as_tkn_old(Xop_tkn_mkr tkn_mkr, byte[] src, int src_len, int amp_pos, int cur_pos) {
//			synchronized (thread_lock_1) {
//				rv_pos = amp_pos + 1;	// default to fail pos; after amp;
//				Object o = amp_trie.Match_bgn(src, cur_pos, src_len);
//				cur_pos = amp_trie.Match_pos();
//				if (o == null) return null;
//				Xop_amp_trie_itm itm = (Xop_amp_trie_itm)o;
//				switch (itm.Tid()) {
//					case Xop_amp_trie_itm.Tid_name_std:
//					case Xop_amp_trie_itm.Tid_name_xowa:
//						rv_pos = cur_pos;
//						return tkn_mkr.Amp_txt(amp_pos, cur_pos, itm);
//					case Xop_amp_trie_itm.Tid_num_hex:
//					case Xop_amp_trie_itm.Tid_num_dec:
//						boolean ncr_is_hex = itm.Tid() == Xop_amp_trie_itm.Tid_num_hex;
//						Xop_amp_mgr_rslt rv = Parse_as_int2(ncr_is_hex, src, src_len, amp_pos, cur_pos);
//						return rv.Pass() ? tkn_mkr.Amp_num(amp_pos, rv_pos, rslt_val) : null;
//					default: throw Err_.new_unhandled(itm.Tid());
//				}
//			}
//		}
//		private boolean Parse_as_int_old(boolean ncr_is_hex, byte[] src, int src_len, int amp_pos, int int_bgn) {
//			synchronized (thread_lock_2) {
//				rv_pos = amp_pos + 1;	// default to fail pos; after amp;
//				rslt_val = -1;			// clear any previous setting
//				int cur_pos = int_bgn, int_end = -1;
//				int semic_pos = Bry_find_.Find_fwd(src, Byte_ascii.Semic, cur_pos, src_len);
//				if (semic_pos == Bry_find_.Not_found) return false;
//				int_end = semic_pos - 1;	// int_end = pos before semicolon
//				int multiple = ncr_is_hex ? 16 : 10, val = 0, factor = 1, cur = 0;
//				for (int i = int_end; i >= int_bgn; i--) {
//					byte b = src[i];
//					if (ncr_is_hex) {
//						if		(b >=  48 && b <=  57)	cur = b - 48;
//						else if	(b >=  65 && b <=  70)	cur = b - 55;
//						else if	(b >=  97 && b <= 102)	cur = b - 87;
//						else if((b >=  71 && b <=  90)
//							||  (b >=  91 && b <= 122))	continue;	// NOTE: wiki discards letters G-Z; PAGE:en.w:Miscellaneous_Symbols "{{Unicode|&#xx26D0;}}"; NOTE 2nd x is discarded
//						else							return false;
//					}
//					else {
//						cur = b - Byte_ascii.Num_0;
//						if (cur < 0 || cur > 10)		return false;
//					}
//					val += cur * factor;
//					if (val > gplx.core.intls.Utf8_.Codepoint_max)  return false;	// fail if value > largest_unicode_codepoint
//					factor *= multiple;
//				}
//				rslt_val = val;
//				rv_pos = semic_pos + 1;	// position after semic
//				return true;
//			}
//		}
}
