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
class Xop_vnt_rules_parser {
	private byte mode;
	private Xop_vnt_tkn vnt_tkn;		
	private boolean loop_vnt_subs; private int vnt_subs_cur, vnt_subs_bgn, vnt_subs_len;
	private int rule_texts_bgn;
	private Btrie_slim_mgr trie;
	private List_adp rules_list = List_adp_.new_();
	private List_adp text_tkns_list = List_adp_.new_();
	private int text_tkns_ws_end_idx;
	private byte[] src;
	private Xop_tkn_mkr tkn_mkr;
//		private int cur_macro_bgn = -1;
	private int cur_key_bgn = -1;
	private byte[] cur_macro_bry = null;
	private byte[] cur_lang_bry = null;
	public Xop_vnt_rules_parser(Xol_vnt_mgr vnt_mgr) {
		trie = Btrie_slim_mgr.ci_ascii_();	// NOTE:ci.ascii:MW_const.en; lang variant name; EX:zh-hans
		Xol_vnt_converter[] ary = vnt_mgr.Converter_ary();
		int ary_len = ary.length;
		for (int i = 0; i < ary_len; i++) {
			Xol_vnt_converter itm = ary[i];
			byte[] itm_lang = itm.Owner_key();
			trie.Add_obj(itm_lang, Xop_vnt_rule_trie_itm.lang_(itm_lang));
		}
		trie.Add_obj(";", Xop_vnt_rule_trie_itm.Dlm_semic);
//			trie.Add("=>", Xop_vnt_rule_trie_itm.Dlm_eqgt);
	}
	public void Clear_all() {
		rules_list.Clear();
		text_tkns_list.Clear();
		cur_macro_bry = cur_lang_bry = null;
		cur_key_bgn = -1;
		text_tkns_ws_bgn = false;
		text_tkns_ws_end_idx = 0;
	}
	public Xop_vnt_rule[] Parse(Xop_ctx ctx, Xop_vnt_tkn vnt_tkn, byte[] src, int vnt_subs_bgn) {
		this.Clear_all();
		this.tkn_mkr = ctx.Tkn_mkr(); this.src = src;
		this.vnt_tkn = vnt_tkn;
		this.vnt_subs_len = vnt_tkn.Subs_len();
		this.vnt_subs_bgn = this.vnt_subs_cur = vnt_subs_bgn;
		mode = Mode_key;
		loop_vnt_subs = true;
		while (loop_vnt_subs) {
			if (vnt_subs_cur == vnt_subs_len) break;
			Parse_sub();
			++vnt_subs_cur;
		}
		Make_rule();	// make rules for any pending items; EX: "-{A|text}-"; "text" is unclosed by semic and would need to be processed
		if (mode == Mode_key && rules_list.Count() == 0)
			Make_rule_literal();
		return (Xop_vnt_rule[])rules_list.To_ary_and_clear(Xop_vnt_rule.class);
	}
	private boolean text_tkns_ws_bgn = false;
	private void Parse_sub() {
		Xop_tkn_itm sub = vnt_tkn.Subs_get(vnt_subs_cur);
		if (cur_key_bgn == -1) cur_key_bgn = sub.Src_bgn();
		boolean text_tkns_list_add = true;
		boolean sub_is_ws = false;
		switch (sub.Tkn_tid()) {
			case Xop_tkn_itm_.Tid_txt:
				Parse_key(sub, src, sub.Src_bgn(), sub.Src_end());
				if (mode == Mode_key)
					text_tkns_list_add = false;					
				break;
			case Xop_tkn_itm_.Tid_bry:
				Xop_bry_tkn bry_tkn = (Xop_bry_tkn)sub;
				byte[] bry = bry_tkn.Val();
				Parse_key(sub, bry, 0, bry.length);
				break;
			case Xop_tkn_itm_.Tid_colon:
				if (	mode == Mode_lang						// colon should only follow lang; EX: zh-hant:text
					&&	cur_lang_bry == null) {					// if pending lang, ignore; assume part of text
					cur_lang_bry = Bry_.Trim(Bry_.Mid(src, cur_key_bgn, sub.Src_bgn()));
					mode = Mode_text;
					rule_texts_bgn = vnt_subs_cur + 1;
					text_tkns_list_add = false;
					text_tkns_ws_bgn = true;
				}
				break;
			case Xop_tkn_itm_.Tid_space:
			case Xop_tkn_itm_.Tid_tab:
			case Xop_tkn_itm_.Tid_newLine:						// skip ws
				if (text_tkns_ws_bgn) text_tkns_list_add = false;
				else					sub_is_ws = true;
				break;
			case Xop_tkn_itm_.Tid_vnt_eqgt:
				if (mode == Mode_key) {
					cur_macro_bry = Bry_.Trim(Bry_.Mid(src, cur_key_bgn, sub.Src_bgn()));
					text_tkns_list_add = false;
				}
				break;
			default:
				break;
		}
		if (sub_is_ws)
			text_tkns_ws_end_idx = text_tkns_list.Count();		// update last_ws_idx
		else
			text_tkns_ws_end_idx = -1;							// set last_ws_idx to nil
		if (mode == Mode_text && text_tkns_list_add) {			// mode is text && not a "key" tkn
			text_tkns_list.Add(sub);
			text_tkns_ws_bgn = false;							// mark ws_bgn as false (handles trimming at start)
		}
	}
	private void Parse_key(Xop_tkn_itm sub, byte[] src, int src_bgn, int src_end) {
		int pos = src_bgn;
		boolean loop_key_bry = true;
//			boolean rv_add_as_text_tkn = true;
		while (loop_key_bry) {
			if (pos == src_end) break;
			if (cur_key_bgn == -1) cur_key_bgn = pos;
			byte b = src[pos];
			Object itm_obj = trie.Match_bgn_w_byte(b, src, pos, src_end);
			if (itm_obj == null) {			// not a lang, semic, or eqgt; treat rest of vnt as one rule tkn
//					if (mode == Mode_key)
//						loop_key_bry = Make_rule_literal();
//					else
				++pos;
			}
			else {
				Xop_vnt_rule_trie_itm itm = (Xop_vnt_rule_trie_itm)itm_obj;
				int new_pos = trie.Match_pos();
				switch (itm.Tid()) {
					case Xop_vnt_rule_trie_itm.Tid_lang:
						if (mode == Mode_key) {
							int next_char_pos = Bry_finder.Find_fwd_while_space_or_tab(src, new_pos, src_end);
							if (next_char_pos == src_end) {	// eos;	EX: "zh-hant  :a"
								cur_key_bgn = pos;
								mode = Mode_lang;
							}
							else
								loop_key_bry = Make_rule_literal();
							return;
						}
						break;
					case Xop_vnt_rule_trie_itm.Tid_semic:
						switch (mode) {
							case Mode_text:
								text_tkns_list.Add(tkn_mkr.Bry_raw(src_bgn, pos, Bry_.Trim(Bry_.Mid(src, src_bgn, pos))));
								Make_rule();
								cur_lang_bry = null;
								mode = Mode_key;
								cur_key_bgn = -1;
								break;
							case Mode_key:	// ignore; empty semic's; EX: "zh-hant:a;;"
								break;
						}
//							rv_add_as_text_tkn = false;
						break;
//						case Xop_vnt_rule_trie_itm.Tid_eqgt:
//							if (	mode == Mode_key) {					// if pending lang, ignore; assume part of text
//								cur_macro_bry = Bry_.Mid(src, cur_key_bgn, sub.Src_bgn());
//								cur_key_bgn = new_pos + 1;
//							}
//							break;
				}
				pos = new_pos;
			}
		}
//			if (rv_add_as_text_tkn)
//				mode = Mode_text;
//			return rv_add_as_text_tkn;
	}
	private boolean Make_rule_literal() {	// return false for loop_key_bry
		this.Clear_all();
		Xop_tkn_itm[] rule_subs = Make_subs(vnt_subs_bgn, vnt_subs_len);
		rules_list.Add(new Xop_vnt_rule(Xop_vnt_rule.Null_macro, Xop_vnt_rule.Null_lang, rule_subs));
		loop_vnt_subs = false;
		return false;
	}
	private Xop_tkn_itm[] Make_subs(int bgn, int end) {
		int len = end - bgn;
		Xop_tkn_itm[] rv = new Xop_tkn_itm[len];
		for (int i = bgn; i < end; i++)
			rv[i - bgn] = vnt_tkn.Subs_get(i);
		return rv;
	}
	private void Make_rule() {
		if (	mode == Mode_text
			&&	rule_texts_bgn < vnt_subs_len) {
			if (text_tkns_ws_end_idx != -1) {	// trailing ws
				text_tkns_list.Del_range(text_tkns_ws_end_idx, text_tkns_list.Count() - 1);
			}
			Xop_tkn_itm[] rule_subs = (Xop_tkn_itm[])text_tkns_list.To_ary_and_clear(Xop_tkn_itm.class);
			Xop_vnt_rule rule = new Xop_vnt_rule(cur_macro_bry, cur_lang_bry, rule_subs);
			rules_list.Add(rule);
		}
	}
	private static final byte Mode_key = 1, Mode_lang = 2, Mode_text = 3;//, Mode_macro = 4;
}
class Xop_vnt_rule_trie_itm {
	public Xop_vnt_rule_trie_itm(byte tid, byte[] lang) {this.tid = tid; this.lang = lang;}
	public byte Tid() {return tid;} private byte tid;
	public byte[] Lang() {return lang;} private byte[] lang;
	public static final byte Tid_semic = 1, Tid_lang = 2;
	public static Xop_vnt_rule_trie_itm lang_(byte[] lang) {return new Xop_vnt_rule_trie_itm(Tid_lang, lang);}
	public static final Xop_vnt_rule_trie_itm
	  Dlm_semic = new Xop_vnt_rule_trie_itm(Tid_semic, null)
	;
}
/*
-{flags|lang:rule}-				EX: -{A|zh-hant:a}-
-{lang:rule;lang:rule}			EX: -{zh-hans:a;zh-hant:b}-
-{lang;lang|rule}-				EX: -{zh-hans;zh-hant|XXXX}-
-{rule}-						EX: -{a}-
-{flags|from=>variant:to;}-		EX: -{H|HUGEBLOCK=>zh-cn:macro;}-
-{lang:data_0;data_1;}-			EX: -{zh-hans:<span style='border:solid;color:blue;'>;zh-hant:b}-
. where data_0 and data_1 is actually one itm since ; is not delimiter b/c data_1 must be variant_code
-{zh-hans:a-{zh-hans:b}-c}-	
*/
