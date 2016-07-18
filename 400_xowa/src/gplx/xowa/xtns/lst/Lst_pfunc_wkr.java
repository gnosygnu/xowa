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
package gplx.xowa.xtns.lst; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import gplx.xowa.wikis.nss.*;
import gplx.xowa.parsers.*; import gplx.xowa.parsers.tmpls.*;
public class Lst_pfunc_wkr {
	private boolean mode_include = true;
	private byte[] src_ttl_bry;
	private byte[] sect_bgn, sect_end;
	private byte[] sect_exclude, sect_replace;
	public Lst_pfunc_wkr Init_include(byte[] src_ttl_bry, byte[] sect_bgn, byte[] sect_end) {
		this.mode_include = Bool_.Y; this.src_ttl_bry = src_ttl_bry; this.sect_bgn = sect_bgn; this.sect_end = sect_end; return this;
	}
	public Lst_pfunc_wkr Init_exclude(byte[] src_ttl_bry, byte[] sect_exclude, byte[] sect_replace) {
		this.mode_include = Bool_.N; this.src_ttl_bry = src_ttl_bry; this.sect_exclude = sect_exclude; this.sect_replace = sect_replace; return this;
	}
	
	public void Exec(Bry_bfr bfr, Xop_ctx ctx) {
		Xowe_wiki wiki = ctx.Wiki();
		Xoa_ttl src_ttl = Xoa_ttl.Parse(wiki, src_ttl_bry); if (src_ttl == null) return;						// {{#lst:<>}} -> ""
		Xot_defn_tmpl defn_tmpl = (Xot_defn_tmpl)wiki.Cache_mgr().Lst_cache().Get_by_key(src_ttl_bry);
		Xop_ctx sub_ctx = null;
		byte[] src = null;
		if (defn_tmpl == null) {	// cache transclusions to prevent multiple parsings; DATE:2014-02-22
			sub_ctx = Xop_ctx.New__sub__reuse_page(ctx).Ref_ignore_(true);
			byte[] src_page_bry = wiki.Cache_mgr().Page_cache().Get_or_load_as_src(src_ttl);
			if (src_page_bry == null) return; // {{#lst:missing}} -> ""
			Xoae_page page = ctx.Page();
			if (!page.Tmpl_stack_add(src_ttl.Full_db())) return;
			defn_tmpl = wiki.Parser_mgr().Main().Parse_text_to_defn_obj(sub_ctx, sub_ctx.Tkn_mkr(), src_ttl.Ns(), src_ttl_bry, src_page_bry);	// NOTE: parse as tmpl to ignore <noinclude>
			Bry_bfr tmp_bfr = wiki.Utl__bfr_mkr().Get_m001();
			page.Tmpl_stack_del();									// take template off stack; evaluate will never recurse, and will fail if ttl is still on stack; DATE:2014-03-10
			defn_tmpl.Tmpl_evaluate(sub_ctx, Xot_invk_temp.Page_is_caller, tmp_bfr);
			src = tmp_bfr.To_bry_and_rls();
			if (!page.Tmpl_stack_add(src_ttl.Full_db())) return;	// put template back on stack; 
			Xop_root_tkn root = wiki.Parser_mgr().Main().Parse_text_to_wdom(sub_ctx, src, true);	// NOTE: pass sub_ctx as old_ctx b/c entire document will be parsed, and references outside the section should be ignored;
			src = root.Data_mid();	// NOTE: must set src to root.Data_mid() which is result of parse; else <nowiki> will break text; DATE:2013-07-11
			wiki.Cache_mgr().Lst_cache().Add(defn_tmpl, Xow_ns_case_.Tid__all);
			page.Tmpl_stack_del();
			defn_tmpl.Data_mid_(src);
			defn_tmpl.Ctx_(sub_ctx);
		}
		else {
			src = defn_tmpl.Data_mid();
			sub_ctx = defn_tmpl.Ctx();
		}
		if		(mode_include)	Write_include(bfr, sub_ctx, src, sect_bgn, sect_end);
		else					Write_exclude(bfr, sub_ctx, src, sect_exclude, sect_replace);
	}
	private static final byte Include_between = 0, Include_to_eos = 1, Include_to_bos = 2;
	private static void Write_include(Bry_bfr bfr, Xop_ctx sub_ctx, byte[] src, byte[] lst_bgn, byte[] lst_end) {
		if		(lst_end == Null_arg) {		// no end arg; EX: {{#lst:page|bgn}}; NOTE: different than {{#lst:page|bgn|}}
			if	(lst_bgn == Null_arg) {		// no bgn arg; EX: {{#lst:page}}
				bfr.Add(src);				// write all and exit
				return;
			}
			else							// bgn exists; set end to bgn; EX: {{#lst:page|bgn}} is same as {{#lst:page|bgn|bgn}}; NOTE: {{#lst:page|bgn|}} means write from bgn to eos
				lst_end = lst_bgn;				
		}
		byte include_mode = Include_between;
		if		(Bry_.Len_eq_0(lst_end))
			include_mode = Include_to_eos;
		else if (Bry_.Len_eq_0(lst_bgn))
			include_mode = Include_to_bos;				
		int bgn_pos = 0; boolean bgn_found = false; int src_page_bry_len = src.length;
		Lst_section_nde_mgr section_mgr = sub_ctx.Lst_section_mgr();	// get section_mgr from Parse
		int sections_len = section_mgr.Count();
		for (int i = 0; i < sections_len; i++) {
			Lst_section_nde section = section_mgr.Get_at(i);
			byte section_tid = section.Name_tid();
			byte[] section_key = section.Section_name();
			if		(section_tid == Lst_section_nde.Xatr_bgn && Bry_.Eq(section_key, lst_bgn)) {
				int sect_bgn_rhs = section.Xnde().Tag_close_end();
				if (include_mode == Include_to_eos) {					// write from cur to eos; EX: {{#lst:page|bgn|}}
					bfr.Add_mid(src, sect_bgn_rhs, src_page_bry_len);
					return;
				}
				else {													// bgn and end
					if (!bgn_found) {									// NOTE: !bgn_found to prevent "resetting" of dupe; EX: <s begin=key0/>a<s begin=key0/>b; should start from a not b
						bgn_pos = sect_bgn_rhs;
						bgn_found = true;
					}
				}
			}
			else if (section_tid == Lst_section_nde.Xatr_end && Bry_.Eq(section_key, lst_end)) {
				int sect_end_lhs = section.Xnde().Tag_open_bgn();
				if (include_mode == Include_to_bos) {					// write from bos to cur; EX: {{#lst:page||end}}
					bfr.Add_mid(src, 0, sect_end_lhs);
					return;
				}
				else {
					if (bgn_found) {									// NOTE: bgn_found to prevent writing from bos; EX: a<s end=key0/>b should not write anything 
						bfr.Add_mid(src, bgn_pos, sect_end_lhs);
						bgn_found = false;
					}
				}
			}
		}
		if (bgn_found)	// bgn_found, but no end; write to end of page; EX: "a <section begin=key/> b" -> " b"
			bfr.Add_mid(src, bgn_pos, src_page_bry_len);
	}
	private static void Write_exclude(Bry_bfr bfr, Xop_ctx sub_ctx, byte[] src, byte[] sect_exclude, byte[] sect_replace) {
		if		(Bry_.Len_eq_0(sect_exclude)) {	// no exclude arg; EX: {{#lstx:page}} or {{#lstx:page}}
			bfr.Add(src);							// write all and exit
			return;
		}
		Lst_section_nde_mgr section_mgr = sub_ctx.Lst_section_mgr();	// get section_mgr from Parse
		int sections_len = section_mgr.Count();
		int bgn_pos = 0;
		for (int i = 0; i < sections_len; i++) {
			Lst_section_nde section = section_mgr.Get_at(i);
			byte section_tid = section.Name_tid();
			byte[] section_key = section.Section_name();
			if		(section_tid == Lst_section_nde.Xatr_bgn && Bry_.Eq(section_key, sect_exclude)) {	// exclude section found
				bfr.Add_mid(src, bgn_pos, section.Xnde().Tag_open_bgn());									// write everything from bgn_pos up to exclude
			}
			else if (section_tid == Lst_section_nde.Xatr_end && Bry_.Eq(section_key, sect_exclude)) {	// exclude end found
				if (sect_replace != null)
					bfr.Add(sect_replace);					// write replacement
				bgn_pos = section.Xnde().Tag_close_end();	// reset bgn_pos
			}
		}
		bfr.Add_mid(src, bgn_pos, src.length);
	}
	public static final    byte[] Null_arg = null;
}
