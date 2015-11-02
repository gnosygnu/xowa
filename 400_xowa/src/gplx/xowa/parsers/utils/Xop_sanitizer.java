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
package gplx.xowa.parsers.utils; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import gplx.core.btries.*; import gplx.xowa.parsers.amps.*; import gplx.core.log_msgs.*;
public class Xop_sanitizer {
	private Btrie_slim_mgr trie = Btrie_slim_mgr.cs(), amp_trie;
	private Xop_amp_mgr amp_mgr;
	private Bry_bfr tmp_bfr = Bry_bfr.reset_(255);
	public Xop_sanitizer(Xop_amp_mgr amp_mgr, Gfo_msg_log msg_log) {
		this.amp_mgr = amp_mgr; this.amp_trie = amp_mgr.Amp_trie();
		trie_add("&"	, Tid_amp);
		trie_add(" "	, Tid_space);
		trie_add("%3A"	, Tid_colon);
		trie_add("%3a"	, Tid_colon);
		trie_add("%"	, Tid_percent);
	}
	private void trie_add(String hook, byte tid) {trie.Add_stub(hook, tid);}
	public byte[] Escape_id(byte[] src) {
		boolean dirty = Escape_id(src, 0, src.length, tmp_bfr);
		return dirty ? tmp_bfr.To_bry_and_clear() : src;
	}
	public boolean Escape_id(byte[] src, int bgn, int end, Bry_bfr bfr) {
		boolean dirty = false;
		int pos = bgn;
		boolean loop = true;
		while (loop) {
			if (pos == end) break;
			byte b = src[pos];
			Object o = trie.Match_bgn_w_byte(b, src, pos, end);
			if (o == null) {
				if (dirty) bfr.Add_byte(b);
				++pos;
			}
			else {
				if (!dirty) {
					bfr.Add_mid(src, bgn, pos);
					dirty = true;
				}
				Btrie_itm_stub stub = (Btrie_itm_stub)o;
				switch (stub.Tid()) {
					case Tid_space:		bfr.Add_byte(Byte_ascii.Underline)	; ++pos		; break;
					case Tid_percent:	bfr.Add_byte(Byte_ascii.Percent)	; ++pos		; break;
					case Tid_colon:		bfr.Add_byte(Byte_ascii.Colon)		; pos += 3	; break;
					case Tid_amp:
						++pos;
						if (pos == end) {
							bfr.Add_byte(Byte_ascii.Amp);
							loop = false;
							continue;
						}
						b = src[pos];
						Object amp_obj = amp_trie.Match_bgn_w_byte(b, src, pos, end);
						if (amp_obj == null) {
							bfr.Add_byte(Byte_ascii.Amp);
							bfr.Add_byte(b);
							++pos;
						}
						else {
							Xop_amp_trie_itm itm = (Xop_amp_trie_itm)amp_obj;
							byte itm_tid = itm.Tid();
							switch (itm_tid) {
								case Xop_amp_trie_itm.Tid_name_std:
								case Xop_amp_trie_itm.Tid_name_xowa:
									bfr.Add(itm.U8_bry());
									pos += itm.Key_name_len() + 1;	// 1 for trailing ";"; EX: for "&nbsp; ", (a) pos is at "&", (b) "nbsp" is Key_name_len, (c) ";" needs + 1 
									break;
								case Xop_amp_trie_itm.Tid_num_dec:
								case Xop_amp_trie_itm.Tid_num_hex:
									boolean pass = amp_mgr.Parse_as_int(itm_tid == Xop_amp_trie_itm.Tid_num_hex, src, end, pos - 1, pos + itm.Xml_name_bry().length);
									if (pass)
										bfr.Add_u8_int(amp_mgr.Rslt_val());
									else
										bfr.Add_byte(Byte_ascii.Amp);
									pos = amp_mgr.Rslt_pos();
									break;
							}
						}
						break;
				}
			}
		}
		return dirty;
	}
	static final byte Tid_amp = 1, Tid_space = 2, Tid_colon = 3, Tid_percent = 4;
}
/*
NOTE: original escapeID does following
1:	' ' -> _
2:	decode_html_ent();
3:	'%3A' -> :
4:	% -> .
for performance
. combine 1,2,3,4: this will fail in cases like &#32; which will become " " instead of "_".
. if that happens, go to 2 passes and move 2: to 1 (this should have no side-effect)
*/