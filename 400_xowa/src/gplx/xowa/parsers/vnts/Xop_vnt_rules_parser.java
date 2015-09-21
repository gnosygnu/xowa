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
import gplx.xowa.parsers.miscs.*;
class Xop_vnt_rules_parser {
	private final Btrie_slim_mgr trie;
	private final List_adp rules_list = List_adp_.new_(), text_tkns = List_adp_.new_();
	private int tmp_area; private byte[] tmp_macro, tmp_lang; private int tmp_key_bgn = -1;
	private int text_tkns__last_text_tkn; private boolean check_for_ws_at_bgn = false; private int semic_pos;
	private Xop_tkn_mkr tkn_mkr;
	public Xop_vnt_rules_parser(Xol_vnt_mgr vnt_mgr) {this.trie = Xop_vnt_rules_parser_.new_trie(vnt_mgr.Regy());}
	public void Clear() {
		rules_list.Clear(); text_tkns.Clear();
		this.tmp_area = Area_key;
		this.tmp_macro = tmp_lang = null;
		this.tmp_key_bgn = -1;
		this.text_tkns__last_text_tkn = 0;
		this.check_for_ws_at_bgn = false;
		this.semic_pos = -1;
	}
	public Xop_vnt_rule_tkn[] Parse(Xop_ctx ctx, Xop_vnt_tkn vnt_tkn, byte[] src, int subs_bgn) {// parse for macro;lang;text; EX: -{macro1=>lang1:text1;macro2=>lang2:text2;}
		synchronized (rules_list) {
			this.Clear();
			this.tkn_mkr = ctx.Tkn_mkr();
			int subs_len = vnt_tkn.Subs_len(); int subs_idx = subs_bgn;
			boolean valid = true;
			while (subs_idx < subs_len) {
				if (!Parse_tkn(vnt_tkn, src, subs_idx)) {
					valid = false;
					break;
				}
				++subs_idx;
			}
			if (valid)
				Make_rule();				// make rules for any pending items; EX: "-{A|text}-"; "text" is unclosed by semic and would need to be processed
			if (rules_list.Count() == 0) {	// no rules made; assume text is just literal; EX: "-{Abc}-"
				Xop_tkn_itm[] rule_subs = Xop_vnt_rules_parser_.Get_subs_as_ary(vnt_tkn, subs_bgn, subs_len);
				rules_list.Add(new Xop_vnt_rule_tkn(Xop_vnt_rule_tkn.Null_macro, Xop_vnt_rule_tkn.Null_lang, rule_subs));
			}
			return (Xop_vnt_rule_tkn[])rules_list.To_ary_and_clear(Xop_vnt_rule_tkn.class);
		}
	}
	private boolean Parse_tkn(Xop_vnt_tkn vnt_tkn, byte[] src, int subs_idx) {
		Xop_tkn_itm sub = vnt_tkn.Subs_get(subs_idx);
		boolean add_to_text_tkns = true, sub_is_middle_ws = false;
		switch (sub.Tkn_tid()) {
			case Xop_tkn_itm_.Tid_colon:
				switch (tmp_area) {
					case Area_lang_found:								// colon should only follow lang; EX: -{zh-hant:text}-
						if (tmp_lang == null) {							// if tmp_lang already exists, ignore colon; treat as part of text; EX: -{zh-hant:text1:text2}-
							tmp_lang = Bry_.Mid_w_trim(src, tmp_key_bgn, sub.Src_bgn());
							tmp_area = Area_text;
							add_to_text_tkns = false;					// do not add ":"
							check_for_ws_at_bgn = true;
						}
						break;
					default:										// colon found after unknown lang; EX: "-{zh-hant:a;zh-x:b}-"
						if (rules_list.Count() == 0) return false;	// invalid lang at start renders entire rule invalid;
						Xop_vnt_rule_tkn rule = (Xop_vnt_rule_tkn)List_adp_.Pop(rules_list);	// invalid rule in middle just adds text to previous rule
						Xop_tkn_itm[] text_tkns_ary = rule.Rule_subs();
						for (Xop_tkn_itm itm : text_tkns_ary)
							text_tkns.Add(itm);
						tmp_macro = rule.Rule_macro();
						tmp_lang = rule.Rule_lang();
						text_tkns.Add(tkn_mkr.Bry_raw(semic_pos, sub.Src_end(), Bry_.Mid_w_trim(src, semic_pos, sub.Src_end())));
						tmp_area = Area_text;
						add_to_text_tkns = false;
						break;
				}
				break;
			case Xop_tkn_itm_.Tid_space: case Xop_tkn_itm_.Tid_tab: case Xop_tkn_itm_.Tid_newLine:	// skip ws
				if (check_for_ws_at_bgn)	add_to_text_tkns = false;	// prevents ws at bgn from being added to text_tkns; EX: " a"
				else						sub_is_middle_ws = true;
				break;
			case Xop_tkn_itm_.Tid_vnt_eqgt:
				if (tmp_area == Area_key) {
					tmp_macro = Bry_.Mid_w_trim(src, tmp_key_bgn, sub.Src_bgn());
					add_to_text_tkns = false;
				}
				break;
			default:
				break;
			case Xop_tkn_itm_.Tid_txt:
				add_to_text_tkns = Parse_bry(sub, src, sub.Src_bgn(), sub.Src_end());
				break;
			case Xop_tkn_itm_.Tid_bry:
				byte[] bry = ((Xop_bry_tkn)sub).Val();
				add_to_text_tkns = Parse_bry(sub, bry, 0, bry.length);
				break;
		}
		if (sub_is_middle_ws)
			text_tkns__last_text_tkn = text_tkns.Count();		// ws found in middle or end; update text_tkns__last_text_tkn; EX: "a "
		else
			text_tkns__last_text_tkn = -1;						// not a middle_ws; set to -1; EX: "a b"
		if (tmp_area == Area_text && add_to_text_tkns) {		// tmp_area is text && not ":", "=>", or leading "\s"
			text_tkns.Add(sub);
			check_for_ws_at_bgn = false;						// stop checking for ws at bgn
		}
		return true;
	}
	private boolean Parse_bry(Xop_tkn_itm sub, byte[] src, int src_bgn, int src_end) {// NOTE: parse byte-per-byte b/c ";" is not a tkn; note that class is designed around this behavior, particulary tmp_key_bgn
		int cur_pos = src_bgn;
		while (cur_pos < src_end) {
			if (tmp_key_bgn == -1) tmp_key_bgn = cur_pos;
			byte b = src[cur_pos];
			Object itm_obj = trie.Match_bgn_w_byte(b, src, cur_pos, src_end);
			if (itm_obj == null)							// not a lang or semic; ignore
				++cur_pos;
			else {
				Xop_vnt_rule_trie_itm itm = (Xop_vnt_rule_trie_itm)itm_obj;
				int new_pos = trie.Match_pos();
				switch (itm.Tid()) {
					case Xop_vnt_rule_trie_itm.Tid_lang:	// lang; EX: "zh-hant"; only process inside Area_key;
						if (tmp_area == Area_key) {
							int nxt_pos = Bry_find_.Find_fwd_while_space_or_tab(src, new_pos, src_end);	// skip any ws at end
							if (nxt_pos == src_end) {		// eos for tkn;	valid; EX: "zh-hant", "zh-hant  "
								tmp_key_bgn = cur_pos;
								tmp_area = Area_lang_found;
							}
							return false;
						}
						break;
					case Xop_vnt_rule_trie_itm.Tid_semic:	// delimiter for multiple langs; EX: -{zh-hans:A;zh-cn:B}-
						semic_pos = cur_pos;
						switch (tmp_area) {
							case Area_text:					// in Area_text; make rule;
								text_tkns.Add(tkn_mkr.Bry_raw(src_bgn, cur_pos, Bry_.Mid_w_trim(src, src_bgn, cur_pos)));	// add everything up to ";"; EX: "text1;zh-hant"; add "text1"
								Make_rule();
								break;
							case Area_key:					// ignore; empty semic's; EX: "zh-hant:a;;"
								break;
						}
						break;
				}
				cur_pos = new_pos;
			}
		}
		return tmp_area == Area_text;
	}
	private void Make_rule() {// called after ";" or before "}-"
		int text_tkns_len = text_tkns.Count();
		if (	tmp_area != Area_text		// stil in key area; EX: -{text;}-
			||	text_tkns_len == 0			// no text tkns; EX: -{zh-hans:;}
			) return;
		if (text_tkns__last_text_tkn != -1)	text_tkns.Del_range(text_tkns__last_text_tkn, text_tkns.Count() - 1); // ignore trailing ws tkns
		Xop_tkn_itm[] rule_subs = (Xop_tkn_itm[])text_tkns.To_ary_and_clear(Xop_tkn_itm.class);
		Xop_vnt_rule_tkn rule = new Xop_vnt_rule_tkn(tmp_macro, tmp_lang, rule_subs);
		rules_list.Add(rule);
		tmp_macro = null;
		tmp_lang = null;
		tmp_area = Area_key;
		tmp_key_bgn = -1;
	}
	private static final int Area_key = 1, Area_lang_found = 2, Area_text = 3;
}
