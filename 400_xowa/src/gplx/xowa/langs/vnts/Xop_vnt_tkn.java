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
import gplx.xowa.html.*;
public class Xop_vnt_tkn extends Xop_tkn_itm_base {
	public Xop_vnt_tkn(int bgn, int end) {
		this.Tkn_ini_pos(false, bgn, end);
		vnt_pipe_idx_last = bgn + Xop_vnt_lxr_.Hook_bgn.length;	// default last pipe to pos after -{
	}
	@Override public byte Tkn_tid() {return Xop_tkn_itm_.Tid_vnt;}
	public int Vnt_pipe_tkn_count() {return vnt_pipe_tkn_count;}
	public Xop_vnt_tkn Vnt_pipe_tkn_count_add_() {++vnt_pipe_tkn_count; return this;} private int vnt_pipe_tkn_count;
	public int Vnt_pipe_idx_last() {return vnt_pipe_idx_last;} public Xop_vnt_tkn Vnt_pipe_idx_last_(int v) {vnt_pipe_idx_last = v; return this;} private int vnt_pipe_idx_last = -1;
	public Xop_vnt_flag[] Vnt_flags() {return vnt_flags;} public Xop_vnt_tkn Vnt_flags_(Xop_vnt_flag[] v) {vnt_flags = v; return this;} private Xop_vnt_flag[] vnt_flags;
	public Xop_vnt_rule[] Vnt_rules() {return vnt_rules;} public Xop_vnt_tkn Vnt_rules_(Xop_vnt_rule[] v) {vnt_rules = v; return this;} private Xop_vnt_rule[] vnt_rules;
	public byte Vnt_cmd() {return vnt_cmd;} private byte vnt_cmd;
	public void Vnt_cmd_calc(Xowe_wiki wiki, Xoae_page page, Xop_ctx ctx, byte[] src) {
		int flags_len = vnt_flags.length;
		int rules_len = vnt_rules.length;
		if		(flags_len == 0) {	// no flags; either literal ("-{A}-") or bidi ("-{zh-hans:A;zh-hant:B}-");
			if (rules_len == 0)		vnt_cmd = Xop_vnt_html_wtr.Cmd_empty;
			else {
				Xop_vnt_rule rule_0 = vnt_rules[0];
				if (	rules_len == 1									// only one rule
					&&	rule_0.Rule_lang() == Xop_vnt_rule.Null_lang	// no lang; EX: -{A}-
					)
					vnt_cmd = Xop_vnt_html_wtr.Cmd_literal;
				else													// bidi: either one rule which has lang ("-{zh-hans:A}-") or more than one rule (which can't be literal)
					vnt_cmd = Xop_vnt_html_wtr.Cmd_bidi;
			}
		}
		else if (flags_len == 1){	// 1 flag; common case
			Xop_vnt_flag flag_0 = vnt_flags[0];
			switch (flag_0.Tid()) {
				case Xop_vnt_flag_.Tid_lang		: vnt_cmd = Xop_vnt_html_wtr.Cmd_lang; break;
				case Xop_vnt_flag_.Tid_raw		: vnt_cmd = Xop_vnt_html_wtr.Cmd_raw; break;
				case Xop_vnt_flag_.Tid_descrip	: vnt_cmd = Xop_vnt_html_wtr.Cmd_descrip; break;
				case Xop_vnt_flag_.Tid_unknown	: vnt_cmd = Xop_vnt_html_wtr.Cmd_literal; break;	// flag is unknown; output text as literal; EX: "-{|a}-"; "-{X|a}-"
				case Xop_vnt_flag_.Tid_macro	: vnt_cmd = Xop_vnt_html_wtr.Cmd_empty; break;		// TODO: implement macro; ignore for now; DATE:2014-05-03
				case Xop_vnt_flag_.Tid_title: {	// title; same as {{DISPLAYTITLE}} but variant aware; PAGE:zh.w:Help:進階字詞轉換處理 DATE:2014-08-29
					vnt_cmd = Xop_vnt_html_wtr.Cmd_title;
					byte[] cur_lang_vnt = wiki.Lang().Vnt_mgr().Cur_vnt();
					Xop_vnt_rule rule = Xop_vnt_html_wtr.Get_rule_by_key(vnt_rules, vnt_rules.length, cur_lang_vnt);
					if (rule != null) {
						Bry_bfr tmp_bfr = wiki.Utl__bfr_mkr().Get_b512();
						wiki.Html_mgr().Html_wtr().Write_tkn_ary(tmp_bfr, ctx, Xoh_wtr_ctx.Alt, src, rule.Rule_subs());
						byte[] display_ttl = tmp_bfr.Mkr_rls().Xto_bry_and_clear();
						page.Html_data().Display_ttl_vnt_(display_ttl);
					}
					break;
				}
			}
		}
	}
}
