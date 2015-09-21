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
package gplx.xowa.parsers.vnts; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import gplx.core.btries.*;
import gplx.xowa.langs.vnts.*;
class Xop_vnt_flag_parser {
	private final Xop_vnt_lang_bldr flag_lang_bldr;
	private final Xol_vnt_regy vnt_regy;
	public Xop_vnt_flag_parser(Xol_vnt_mgr vnt_mgr) {this.flag_lang_bldr = new Xop_vnt_lang_bldr(vnt_mgr); this.vnt_regy = vnt_mgr.Regy();}
	public int				Rslt_tkn_pos()		{return rslt_tkn_pos;} private int rslt_tkn_pos;
	public int				Rslt_pipe_last()	{return rslt_pipe_last;} private int rslt_pipe_last;
	public Xop_vnt_flag[]	Rslt_flags()		{return rslt_flags;} private Xop_vnt_flag[] rslt_flags;
	public void Parse(Xowe_wiki wiki, Xoa_url page_url, Xop_vnt_tkn vnt_tkn, int pipe_tkn_count, byte[] src) {
		flag_lang_bldr.Clear();
		int rv_idx = 0;
		int subs_len = vnt_tkn.Subs_len();
		this.rslt_flags = new Xop_vnt_flag[pipe_tkn_count];
		this.rslt_tkn_pos = 0;
		Bry_bfr flag_bfr = wiki.Utl__bfr_mkr().Get_b128();
		boolean loop = true;
		while (loop) {
			Xop_tkn_itm sub = vnt_tkn.Subs_get(rslt_tkn_pos);
			switch (sub.Tkn_tid()) {
				case Xop_tkn_itm_.Tid_space: case Xop_tkn_itm_.Tid_tab: case Xop_tkn_itm_.Tid_newLine:	break;	// skip ws
				case Xop_tkn_itm_.Tid_txt: flag_bfr.Add_mid(src, sub.Src_bgn(), sub.Src_end()); break;			// just add text
				case Xop_tkn_itm_.Tid_pipe:
					rslt_flags[rv_idx++] = Parse_flag_bry(flag_bfr.Xto_bry_and_clear());
					if (rv_idx == pipe_tkn_count) {
						loop = false;
						rslt_pipe_last = sub.Src_end();
					}
					break;						
				default:
					wiki.Appe().Usr_dlg().Log_many("", "", "unknown tkn in vnt flag; url=~{0} tid=~{1} txt=~{2}", page_url.To_str(), sub.Tkn_tid(), String_.new_u8(src, sub.Src_bgn(), sub.Src_end()));
					flag_bfr.Add_mid(src, sub.Src_bgn(), sub.Src_end());
					break;
			}
			++rslt_tkn_pos;
			if (rslt_tkn_pos == subs_len) break;
		}
		flag_bfr.Mkr_rls();
	}
	private Xop_vnt_flag Parse_flag_bry(byte[] bry) {
		int bry_len = bry.length; if (bry_len == 0) return Xop_vnt_flag_.Flag_unknown;	// EX: exit early if 0 len, else trie will fail; EX: "-{|}-"
		Object flag_obj = flag_trie.Match_exact(bry, 0, bry_len);
		return flag_obj == null
			? Parse_flag_vnts(bry, bry_len)		// unknown tid sequence; either (a) "lang" cmd ("-{zh-hans;zh-hant|a}-") or (b) invalid cmd ("-{X|a}-")
			: (Xop_vnt_flag)flag_obj;			// known flag; check that next non_ws is |
	}
	private Xop_vnt_flag Parse_flag_vnts(byte[] bry, int bry_len) {
		int vnt_pos = 0;
		boolean loop = true;
		Btrie_slim_mgr trie = vnt_regy.Trie();
		while (loop) {
			boolean last = false, valid = true;
			Object vnt_obj = trie.Match_bgn(bry, vnt_pos, bry_len);
			if (vnt_obj == null) break;						// no more vnts found; stop
			vnt_pos = trie.Match_pos();						// update pos to end of vnt
			int semic_pos = Bry_find_.Find_fwd_while_not_ws(bry, vnt_pos, bry_len);
			if (semic_pos == bry_len)						// note that Find_fwd_non_ws will return bry_len if no non-ws found;
				last = true;
			else {											// char found; make sure it is semic
				if (bry[semic_pos] != Byte_ascii.Semic)		// invalid vnt; ignore; EX: -{zh-hansx|}-
					valid = false;
				vnt_pos = semic_pos + 1;					// update pos to after semic
				if (vnt_pos == bry_len) last = true;		// EX: "a;"
			}
			if (valid)
				flag_lang_bldr.Add(((Xol_vnt_itm)vnt_obj).Key());
			else											// invalid entry clears list; EX: -{zh-hans;zh-bad}-
				flag_lang_bldr.Clear();
			if (last) break;
		}
		return flag_lang_bldr.Bld();
	}
	private static final Btrie_fast_mgr flag_trie = Xop_vnt_flag_.Trie;
}
