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
public class Xop_amp_mgr {
	private final    Object thread_lock_1 = new Object(), thread_lock_2 = new Object();
	private final    Bry_bfr tmp_bfr = Bry_bfr_.Reset(32);
	public Btrie_slim_mgr Amp_trie() {return amp_trie;} private final    Btrie_slim_mgr amp_trie = Xop_amp_trie.Instance;
	public int Rslt_pos() {return rslt_pos;} private int rslt_pos;
	public int Rslt_val() {return rslt_val;} private int rslt_val;
	public Xop_tkn_itm Parse_as_tkn(Xop_tkn_mkr tkn_mkr, byte[] src, int src_len, int amp_pos, int cur_pos) {
		synchronized (thread_lock_1) {
			rslt_pos = amp_pos + 1;	// default to fail pos; after amp;
			Object o = amp_trie.Match_bgn(src, cur_pos, src_len);
			cur_pos = amp_trie.Match_pos();
			if (o == null) return null;
			Xop_amp_trie_itm itm = (Xop_amp_trie_itm)o;
			switch (itm.Tid()) {
				case Xop_amp_trie_itm.Tid_name_std:
				case Xop_amp_trie_itm.Tid_name_xowa:
					rslt_pos = cur_pos;
					return tkn_mkr.Amp_txt(amp_pos, cur_pos, itm);
				case Xop_amp_trie_itm.Tid_num_hex:
				case Xop_amp_trie_itm.Tid_num_dec:
					boolean ncr_is_hex = itm.Tid() == Xop_amp_trie_itm.Tid_num_hex;
					boolean pass = Parse_as_int(ncr_is_hex, src, src_len, amp_pos, cur_pos);
					return pass ? tkn_mkr.Amp_num(amp_pos, rslt_pos, rslt_val) : null;
				default: throw Err_.new_unhandled(itm.Tid());
			}
		}
	}
	public boolean Parse_as_int(boolean ncr_is_hex, byte[] src, int src_len, int amp_pos, int int_bgn) {
		synchronized (thread_lock_2) {
			rslt_pos = amp_pos + 1;	// default to fail pos; after amp;
			rslt_val = -1;			// clear any previous setting
			int cur_pos = int_bgn, int_end = -1;
			int semic_pos = Bry_find_.Find_fwd(src, Byte_ascii.Semic, cur_pos, src_len);
			if (semic_pos == Bry_find_.Not_found) return false;
			int_end = semic_pos - 1;	// int_end = pos before semicolon
			int multiple = ncr_is_hex ? 16 : 10, val = 0, factor = 1, cur = 0;
			for (int i = int_end; i >= int_bgn; i--) {
				byte b = src[i];
				if (ncr_is_hex) {
					if		(b >=  48 && b <=  57)	cur = b - 48;
					else if	(b >=  65 && b <=  70)	cur = b - 55;
					else if	(b >=  97 && b <= 102)	cur = b - 87;
					else if((b >=  71 && b <=  90)
						||  (b >=  91 && b <= 122))	continue;	// NOTE: wiki discards letters G-Z; PAGE:en.w:Miscellaneous_Symbols "{{Unicode|&#xx26D0;}}"; NOTE 2nd x is discarded
					else							return false;
				}
				else {
					cur = b - Byte_ascii.Num_0;
					if (cur < 0 || cur > 10)		return false;
				}
				val += cur * factor;
				if (val > gplx.core.intls.Utf8_.Codepoint_max)  return false;	// fail if value > largest_unicode_codepoint
				factor *= multiple;
			}
			rslt_val = val;
			rslt_pos = semic_pos + 1;	// position after semic
			return true;
		}
	}
	public byte[] Decode_as_bry(byte[] src) {
		if (src == null) return src;
		int src_len = src.length;
		boolean dirty = false;
		int pos = 0;
		synchronized (tmp_bfr) {
			while (pos < src_len) {
				byte b = src[pos];
				if (b == Byte_ascii.Amp) {
					int nxt_pos = pos + 1;
					if (nxt_pos < src_len) {
						byte nxt_b = src[nxt_pos];
						Object amp_obj = amp_trie.Match_bgn_w_byte(nxt_b, src, nxt_pos, src_len);
						if (amp_obj != null) {
							if (!dirty) {
								tmp_bfr.Add_mid(src, 0, pos);
								dirty = true;
							}
							Xop_amp_trie_itm amp_itm = (Xop_amp_trie_itm)amp_obj;
							switch (amp_itm.Tid()) {
								case Xop_amp_trie_itm.Tid_name_std:
								case Xop_amp_trie_itm.Tid_name_xowa:
									tmp_bfr.Add(amp_itm.U8_bry());
									pos = amp_trie.Match_pos();
									break;
								case Xop_amp_trie_itm.Tid_num_hex:
								case Xop_amp_trie_itm.Tid_num_dec:
									boolean ncr_is_hex = amp_itm.Tid() == Xop_amp_trie_itm.Tid_num_hex;
									int int_bgn = amp_trie.Match_pos();
									if (Parse_as_int(ncr_is_hex, src, src_len, pos, int_bgn))
										tmp_bfr.Add_u8_int(rslt_val);
									else 
										tmp_bfr.Add_mid(src, pos, nxt_pos);
									pos = rslt_pos;
									break;
								default:
									throw Err_.new_unhandled(amp_itm.Tid());
							}
							continue;
						}
					}					
				}
				if (dirty)
					tmp_bfr.Add_byte(b);
				++pos;
			}
			return dirty ? tmp_bfr.To_bry_and_clear() : src;
		}
	}
        public static final    Xop_amp_mgr Instance = new Xop_amp_mgr(); Xop_amp_mgr() {}
}
