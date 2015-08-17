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
package gplx.xowa.langs.vnts; import gplx.*; import gplx.xowa.*; import gplx.xowa.langs.*;
import gplx.core.btries.*;
class Xop_vnt_flag_parser {
	private Xop_vnt_flag_lang_bldr flag_lang_bldr;
	public Xop_vnt_flag_parser(Xol_vnt_mgr vnt_mgr) {flag_lang_bldr = new Xop_vnt_flag_lang_bldr(vnt_mgr);}
	private void Clear() {
		flag_lang_bldr.Clear();
	}
	public int Rslt_tkn_pos() {return rslt_tkn_pos;} private int rslt_tkn_pos;
	public int Rslt_pipe_last() {return rslt_pipe_last;} private int rslt_pipe_last;
	public Xop_vnt_flag[] Rslt_flags() {return rslt_flags;} private Xop_vnt_flag[] rslt_flags;
	public void Parse(Xowe_wiki wiki, Xop_vnt_tkn vnt_tkn, int pipe_tkn_count, byte[] src) {
		this.Clear();
		rslt_flags = new Xop_vnt_flag[pipe_tkn_count];
		int rv_idx = 0;
		int subs_len = vnt_tkn.Subs_len();
		Bry_bfr flag_bfr = wiki.Utl__bfr_mkr().Get_b128();
		rslt_tkn_pos = 0;
		boolean loop = true;
		while (true) {
			Xop_tkn_itm sub = vnt_tkn.Subs_get(rslt_tkn_pos);
			switch (sub.Tkn_tid()) {
				case Xop_tkn_itm_.Tid_txt:
					flag_bfr.Add_mid(src, sub.Src_bgn(), sub.Src_end());
					break;
				case Xop_tkn_itm_.Tid_pipe:
					rslt_flags[rv_idx++] = Parse_flag_bry(flag_bfr.Xto_bry_and_clear());
					if (rv_idx == pipe_tkn_count) {
						loop = false;
						rslt_pipe_last = sub.Src_end();
					}
					break;
				case Xop_tkn_itm_.Tid_space:
				case Xop_tkn_itm_.Tid_tab:
				case Xop_tkn_itm_.Tid_newLine:	// skip ws
					break;
				default:
					wiki.Appe().Usr_dlg().Log_many("", "", "unknown tkn in vnt flag; tid=~{0} txt=~{1}", sub.Tkn_tid(), String_.new_u8(src, sub.Src_bgn(), sub.Src_end()));
					flag_bfr.Add_mid(src, sub.Src_bgn(), sub.Src_end());
					break;
			}
			++rslt_tkn_pos;
			if (rslt_tkn_pos == subs_len) break;
			if (!loop) break;
		}
		flag_bfr.Mkr_rls();
	}
	private Xop_vnt_flag Parse_flag_bry(byte[] bry) {
		int bry_len = bry.length;
		if (bry_len == 0) return Xop_vnt_flag_.Flag_unknown;	// EX: exit early if 0 len, else trie will fail; EX: "-{|}-"
		Object flag_obj = flag_trie.Match_exact(bry, 0, bry_len);
		return flag_obj == null
			? Parse_flag_vnts(bry, bry_len)		// unknown tid sequence; either (a) "lang" cmd ("-{zh-hans;zh-hant|a}-") or (b) invalid cmd ("-{X|a}-")
			: (Xop_vnt_flag)flag_obj;			// known flag; check that next non_ws is |
	}
	private Xop_vnt_flag Parse_flag_vnts(byte[] bry, int bry_len) {
		boolean loop = true;
		int vnt_pos = 0;
		Btrie_slim_mgr trie = flag_lang_bldr.Trie();
		while (loop) {
			boolean last = false;
			boolean valid = true;
			Object vnt_obj = trie.Match_bgn(bry, vnt_pos, bry_len);
			if (vnt_obj == null) break;						// no more vnts found; stop
			vnt_pos = trie.Match_pos();						// update pos to end of vnt
			int semic_pos = Bry_finder.Find_fwd_while_not_ws(bry, vnt_pos, bry_len);
			if (semic_pos == bry_len)						// note that Find_fwd_non_ws will return bry_len if no non-ws found;
				last = true;
			else {											// char found; make sure it is semic
				if (bry[semic_pos] != Byte_ascii.Semic) {	// invalid vnt; ignore; EX: -{zh-hansx|}-
					valid = false;
				}
				vnt_pos = semic_pos + 1;					// update pos to after semic
				if (vnt_pos == bry_len) last = true;		// EX: "a;"
			}
			if (valid)
				flag_lang_bldr.Add((Xop_vnt_flag_lang_itm)vnt_obj);
			else	// invalid entry clears list; EX: -{zh-hans;zh-bad}-
				flag_lang_bldr.Clear();
			if (last) break;
		}
		return flag_lang_bldr.Bld();
	}
	private static Btrie_fast_mgr flag_trie = Xop_vnt_flag_.Trie;
//		private static final byte Dlm_tid_bgn = 0, Dlm_tid_end = 1, Dlm_tid_pipe = 2, Dlm_tid_colon = 3, Dlm_tid_semic = 4, Dlm_tid_kv = 5;
//		private static Btrie_fast_mgr dlm_trie = Btrie_fast_mgr.cs()
//		.Add_bry_byte(Xop_vnt_lxr_.Hook_bgn	, Dlm_tid_bgn)
//		.Add_bry_byte(Xop_vnt_lxr_.Hook_end		, Dlm_tid_end)
//		.Add_bry_byte(Byte_ascii.Pipe				, Dlm_tid_pipe)
//		.Add_bry_byte(Byte_ascii.Colon				, Dlm_tid_colon)
//		.Add_bry_byte(Byte_ascii.Semic				, Dlm_tid_semic)
//		.Add_bry_byte(Bry_.new_a7("=>")		, Dlm_tid_kv)
//		;
}
