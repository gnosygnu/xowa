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
public class Xop_vnt_html_wtr {
	public static void Write(Xoh_html_wtr html_wtr, Xop_ctx ctx, Xoh_wtr_ctx opts, Bry_bfr bfr, byte[] src, Xop_vnt_tkn vnt) {
		byte[] cur_lang_vnt = ctx.Wiki().Lang().Vnt_mgr().Cur_vnt();
		Xop_vnt_rule[] rules = vnt.Vnt_rules(); if (rules == null) return;	// shouldn't happen, but guard anyway
		int rules_len = rules.length;
		switch (vnt.Vnt_cmd()) {
			case Xop_vnt_html_wtr.Cmd_empty:	break;	// nothing: ""
			case Xop_vnt_html_wtr.Cmd_error:			// original token; "-{A}-"
				bfr.Add_mid(src, vnt.Src_bgn(), vnt.Src_end());
				break;
			case Xop_vnt_html_wtr.Cmd_literal: {		// val only; "A"
				Xop_vnt_rule rule_0 = rules[0];		// Cmd_calc guarantees there will always be 1 item
				html_wtr.Write_tkn_ary(bfr, ctx, opts, src, rule_0.Rule_subs());
				break;
			}
			case Xop_vnt_html_wtr.Cmd_bidi:				// matching rule: "A" if zh-hans; -{zh-hans:A}-
				for (int i = 0; i < rules_len; i++) {
					Xop_vnt_rule rule = rules[i];
					if (Bry_.Eq(rule.Rule_lang(), cur_lang_vnt)) {
						html_wtr.Write_tkn_ary(bfr, ctx, opts, src, rule.Rule_subs());
						break;
					}
				}
				break;
			case Xop_vnt_html_wtr.Cmd_lang: {				// matching lang: "A" if zh-hans; -{zh-hans|A}-
				Xop_vnt_rule rule_0 = rules[0];				// Cmd_calc guarantees there will always be 1 rule
				Xop_vnt_flag flag_0 = vnt.Vnt_flags()[0];	// parse guarantees there will always be 1 flag
				byte[][] langs = flag_0.Langs();
				int flags_len = langs.length;
				for (int i = 0; i < flags_len; i++) {
					byte[] lang = langs[i];
					if (Bry_.Eq(lang, cur_lang_vnt)) {
						html_wtr.Write_tkn_ary(bfr, ctx, opts, src, rule_0.Rule_subs());
						break;
					}
				}
				break;
			}
			case Xop_vnt_html_wtr.Cmd_raw: {				// raw; everything between last flag and }-: "-{R|zh-hans:A;zh-hant:B}-  -> "zh-hans:A;zh-hant:B"
				bfr.Add_mid(src, vnt.Vnt_pipe_idx_last(), vnt.Src_end() - 2);
				break;
			}
			case Xop_vnt_html_wtr.Cmd_descrip: {			// descrip; similar to raw, but use localized lang
//					bfr.Add_mid(src, vnt.Vnt_pipe_idx_last(), vnt.Src_end() - 2);
				break;
			}
		}
	}
	public static final byte
	  Cmd_error			= 0		// eror -> output literal;		EX: "-{some_unknown_error}-" -> "-{some_unknown_error}-"
	, Cmd_empty			= 1		// empty -> output nothing;		EX: "-{}-" -> ""
	, Cmd_literal		= 2		// literal						EX: "-{A}-" -> "A"
	, Cmd_bidi			= 3		// bidi							EX: "-{zh-hans:A;zh-hant:B}-"	-> "A" if zh-hans; "B" if zh-hant
	, Cmd_lang			= 4		// lang							EX: "-{zh-hans|A}-"				-> "A" if zh-hans; "" if zh-hant
	, Cmd_raw			= 5		// raw; text in -{}-			EX: "-{R|zh-hans:A;zh-hant:B}-  -> "zh-hans:A;zh-hant:B"
	, Cmd_descrip		= 6		// describe; output rules		EX: "-{D|zh-hans:A;zh-hant:B}-  -> "简体：A；繁體：B；"
	;
}
