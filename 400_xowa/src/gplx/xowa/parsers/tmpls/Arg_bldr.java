/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2017 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.xowa.parsers.tmpls; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import gplx.xowa.parsers.xndes.*; import gplx.xowa.parsers.miscs.*;
public class Arg_bldr {	// TS
	public boolean Bld(Xop_ctx ctx, Xop_tkn_mkr tkn_mkr, Xop_arg_wkr wkr, int wkr_typ, Xop_root_tkn root, Xop_tkn_itm tkn, int bgn_pos, int cur_pos, int loop_bgn, int loop_end, byte[] src) {
		boolean ws_bgn_chk = true, colon_chk = false, itm_is_static = true, key_exists = false; int ws_bgn_idx = -1, ws_end_idx = -1, cur_itm_subs_len = 0, cur_nde_idx = -1; Arg_nde_tkn cur_nde = null; Arg_itm_tkn cur_itm = null;
		int brack_count = 0;
		Xop_tkn_itm eq_pending = null;
		for (int i = loop_bgn; i < loop_end; i++) {	// loop over subs between bookends; if lnki, all tkns between [[ and ]]; if tmpl, {{ and }}
			Xop_tkn_itm sub = root.Subs_get(i);
			int sub_pos_bgn = sub.Src_bgn_grp(root, i);
			if (cur_nde == null) {
				cur_nde = tkn_mkr.ArgNde(++cur_nde_idx, sub_pos_bgn);
				brack_count = 0; key_exists = false;
			}
			if (cur_itm == null) {
				cur_itm = tkn_mkr.ArgItm(sub_pos_bgn, -1);
				itm_is_static = ws_bgn_chk = true; cur_itm_subs_len = 0; ws_bgn_idx = ws_end_idx = -1;
				if (eq_pending != null) {											// something like  "A==B" encountered; zh.w:Wikipedia:条目评选; DATE:2014-08-27
					eq_pending.Src_end_(eq_pending.Src_end() -1);					// remove an "=" EX:"A==B" -> "A","=","=B"
					cur_itm.Subs_add_grp(eq_pending, root, i); cur_itm_subs_len++;	// add the tkn to cur_itm
					eq_pending = null;
				}
			}
			switch (sub.Tkn_tid()) {
				case Xop_tkn_itm_.Tid_ignore:	// comment or *include* tkn; mark itm as non_static for tmpl (forces re-eval)
					switch (wkr_typ) {
						case Xop_arg_wkr_.Typ_tmpl:
						case Xop_arg_wkr_.Typ_prm:
							itm_is_static = false;
							break;
					}
					break; 
				case Xop_tkn_itm_.Tid_para:	// NOTE: para can appear in following: [[File:A.png| \n 40px]]; EX: w:Supreme_Court_of_the_United_States; DATE:2014-04-05
				case Xop_tkn_itm_.Tid_newLine: case Xop_tkn_itm_.Tid_space: case Xop_tkn_itm_.Tid_tab:	// whitespace
					if	(ws_bgn_chk) ws_bgn_idx = cur_itm_subs_len;							// definite ws at bgn; set ws_bgn_idx, and keep setting until text tkn reached; handles mixed sequence of \s\n\t where last tkn should be ws_bgn_idx
					else			{if (ws_end_idx == -1) ws_end_idx = cur_itm_subs_len;};	// possible ws at end; may be overriden later; see AdjustWsForTxtTkn
					break;
				case Xop_tkn_itm_.Tid_colon:
					if (wkr_typ == Xop_arg_wkr_.Typ_tmpl) {			// treat colons as text; tmpl will do its own : parsing for 1st arg; NOTE: must do ws check else 2nd colon will break; EX: "{{#ifeq: :|a|b|c}}"; DATE:2013-12-10
						if (ws_bgn_chk) ws_bgn_chk = false; else ws_end_idx = -1;		// INLINE: AdjustWsForTxtTkn
					}
					else {
						if (cur_nde_idx == 0 && !colon_chk) {		// if 1st arg, mark colon pos; needed for lnki; EX: [[Category:A]]; {{#ifeq:1}}
							colon_chk = true;
							cur_nde.Arg_colon_pos_(sub_pos_bgn);
						}
					}
					break;
				case Xop_tkn_itm_.Tid_brack_bgn: ++brack_count; if (ws_bgn_chk) ws_bgn_chk = false; else ws_end_idx = -1;	// INLINE: AdjustWsForTxtTkn
					break;
				case Xop_tkn_itm_.Tid_brack_end: --brack_count; if (ws_bgn_chk) ws_bgn_chk = false; else ws_end_idx = -1;	// INLINE: AdjustWsForTxtTkn
					break;
				case Xop_tkn_itm_.Tid_eq:
					if		(wkr_typ == Xop_arg_wkr_.Typ_tmpl && brack_count > 0) {}
					else if	(wkr_typ == Xop_arg_wkr_.Typ_prm) {}						// always ignore for prm
					else {
						if (	cur_nde_idx != 0										// if 1st arg, treat equal_tkn as txt_tkn; i.e.: eq should not be used to separate key/val
							&&	cur_nde.Eq_tkn() == Xop_tkn_null.Null_tkn				// only mark key if key is not set; handle multiple-keys; EX: {{name|key1=b=c}}; DATE:2014-02-09
							) {
							Xop_eq_tkn sub_as_eq = (Xop_eq_tkn)sub;
							int sub_as_eq_len = sub_as_eq.Eq_len();
							boolean eq_is_spr
								=	sub_as_eq_len == 1									// eq with len of 1 are considered separators; MW.REF:Preprocessor_DOM.php|preprocessToXml; "if ( $count == 1 && $findEquals )" PAGE:en.w:Wikipedia:Picture_of_the_day/June_2014; DATE:2014-07-21
								||  (	cur_itm.Subs_len() > 0							// or eq.len > 1 that occur later in itm; EX: a==b; zh.w:Wikipedia:条目评选; DATE:2014-08-27
									&&	cur_itm.Subs_get(0).Tkn_tid() != Xop_tkn_itm_.Tid_eq // and 1st tkn is not ==; EX:==a==; 2nd == should not be eq b/c 1st == "deactivates" nde; DATE:2014-08-27
									);
							if (eq_is_spr)  {
								if (sub_as_eq_len == 1)									// =.len == 1
									cur_nde.Eq_tkn_(sub);								// set as eq tkn
								else													// =.len  > 1
									eq_pending = sub;									// do not set as eq tkn; note that Eq_tkn exists for bookkeeping and is not printed out, 
								key_exists = true;
								Arg_itm_end(ctx, cur_nde, cur_itm, ws_bgn_idx, ws_end_idx, cur_itm_subs_len, sub_pos_bgn, wkr_typ, key_exists, true, itm_is_static, src, cur_nde_idx);
								cur_nde.Key_tkn_(cur_itm);
								cur_itm = null;
								continue;												// do not add tkn to cur_itm
							}
						}
						if (ws_bgn_chk) ws_bgn_chk = false; else ws_end_idx = -1;		// INLINE: AdjustWsForTxtTkn
					break;
					}
					break;
				case Xop_tkn_itm_.Tid_pipe:
					if (cur_nde_idx == 0 
						&& ws_bgn_chk 
						&& !colon_chk
						&& wkr_typ == Xop_arg_wkr_.Typ_tmpl
						)	return false;				// 1st arg, but no name; EX: "{{|a}}", "{{ }}"; disregard if lnki, since "[[|a]]" is valid
					if (wkr_typ == Xop_arg_wkr_.Typ_tmpl && brack_count > 0) {
						break;
					}
					else {
						Arg_itm_end(ctx, cur_nde, cur_itm, ws_bgn_idx, ws_end_idx, cur_itm_subs_len, sub_pos_bgn, wkr_typ, key_exists, false, itm_is_static, src, cur_nde_idx);
						cur_nde.Val_tkn_(cur_itm);
						if (!wkr.Args_add(ctx, src, tkn, cur_nde, cur_nde_idx)) return false; // NOTE: if invalid, exit now; lnki_wkr expects false if any argument is invalid; DATE:2014-06-06
						cur_nde = null; cur_itm = null; key_exists = false;			// reset
						continue;													// do not add tkn to cur_itm
					}
				case Xop_tkn_itm_.Tid_tmpl_prm:										// nested prm (3 {) or invk (2 {); mark itm_is_static = false and treat tkn as txt
				case Xop_tkn_itm_.Tid_tmpl_invk:
					itm_is_static = false;
					if (ws_bgn_chk) ws_bgn_chk = false; else ws_end_idx = -1;		// INLINE: AdjustWsForTxtTkn
					break;
				case Xop_tkn_itm_.Tid_xnde:
					Xop_xnde_tkn sub_as_xnde = (Xop_xnde_tkn)sub;
					switch (sub_as_xnde.Tag().Id()) {
						case Xop_xnde_tag_.Tid__noinclude: case Xop_xnde_tag_.Tid__includeonly: case Xop_xnde_tag_.Tid__onlyinclude:
							itm_is_static = false;
							break;
					}
					if (ws_bgn_chk) ws_bgn_chk = false; else ws_end_idx = -1;		// INLINE: AdjustWsForTxtTkn
					break;
				default:
					if (ws_bgn_chk) ws_bgn_chk = false; else ws_end_idx = -1;		// INLINE: AdjustWsForTxtTkn
					break;
			}
			cur_itm.Subs_add_grp(sub, root, i); cur_itm_subs_len++;
		}
		if (brack_count > 0) return false;
		if (cur_nde == null)	// occurs when | is last tkn; EX: {{name|a|}};
			cur_nde = tkn_mkr.ArgNde(++cur_nde_idx, bgn_pos);
		if (cur_itm == null) {	// occurs when = is last tkn; EX: {{name|a=}};
			cur_itm = tkn_mkr.ArgItm(bgn_pos, -1);
			itm_is_static = ws_bgn_chk = true; cur_itm_subs_len = 0; ws_bgn_idx = ws_end_idx = -1; key_exists = false;
		}
		Arg_itm_end(ctx, cur_nde, cur_itm, ws_bgn_idx, ws_end_idx, cur_itm_subs_len, bgn_pos, wkr_typ, key_exists, false, itm_is_static, src, cur_nde_idx);
		cur_nde.Val_tkn_(cur_itm);
		return wkr.Args_add(ctx, src, tkn, cur_nde, cur_nde_idx);
	}
	private void Arg_itm_end(Xop_ctx ctx, Arg_nde_tkn nde, Arg_itm_tkn itm, int ws_bgn_idx, int ws_end_idx, int subs_len, int lxr_bgn, int wkr_typ, boolean key_exists, boolean cur_itm_is_key, boolean itm_is_static, byte[] src, int arg_idx) {
		// PURPOSE: mark tkns Ignore; find dat_bgn, dat_end
		int dat_bgn = itm.Src_bgn(), dat_end = lxr_bgn; boolean trim = false;
		// trim ws at bgn
		boolean wkr_is_not_prm = wkr_typ != Xop_arg_wkr_.Typ_prm;
		if (ws_bgn_idx != -1) {		// ignore ws if (ws_found_at_bgn && (tmpl_arg || (lnki_arg && key))); lnki_arg && val does not ignore at bgn; EX: [[alt= a b c]] -> " a b c"
			switch (wkr_typ) {
				case Xop_arg_wkr_.Typ_prm : trim = arg_idx == 0; break;
				case Xop_arg_wkr_.Typ_tmpl: trim = key_exists || arg_idx == 0; break;
				case Xop_arg_wkr_.Typ_lnki: trim = cur_itm_is_key || !key_exists; break;	// NOTE: trim if "a= b"; skip if " a=b" or " a"
			}
			if (trim) {
				for (int i = 0; i <= ws_bgn_idx; i++) {
					Xop_tkn_itm sub_tkn = itm.Subs_get(i);				// NOTE: tknTypeId should be space, newline, or tab
					if (wkr_is_not_prm)
						sub_tkn.Ignore_y_grp_(ctx, itm, i);				// mark tkn ignore unless wkr is prm; SEE:NOTE_1
					if (i == ws_bgn_idx)
						dat_bgn = sub_tkn.Src_end_grp(itm, i);			// if last_tkn, set dat_bgn to bgn
				}
			}
		}
		// trim ws at end
		if (ws_end_idx != -1) {		// ignore ws if (ws_found_at_end && (tmpl_arg || (lnki_arg && val))); lnki_arg && key does not ignore at end; EX: [[alt =a b c]] -> unrecognized nde ("alt ")
			trim = false;
			switch (wkr_typ) {
				case Xop_arg_wkr_.Typ_prm : trim = arg_idx == 0; break; 
				case Xop_arg_wkr_.Typ_tmpl: trim = key_exists || arg_idx == 0; break;	// NOTE: never set "trim = true"; PAGE:fr.w:Histoire_de_la_marine_française_sous_Louis_XV_et_Louis_XVI DATE:2015-11-17
				case Xop_arg_wkr_.Typ_lnki: trim = !cur_itm_is_key; break;
			}
			if (trim) {
				for (int i = ws_end_idx; i < subs_len; i++) {
					Xop_tkn_itm sub_tkn = itm.Subs_get(i);				// NOTE: tknTypeId should be space, newline, or tab
					if (wkr_is_not_prm)
						sub_tkn.Ignore_y_grp_(ctx, itm, i);				// mark tkn ignore unless wkr is prm; SEE:NOTE_1
					if (i == ws_end_idx)
						dat_end = sub_tkn.Src_bgn_grp(itm, i);			// if 1st_tkn; set dat_end to bgn
				}
			}
		}
		itm.Src_end_(lxr_bgn); nde.Src_end_(lxr_bgn);	// NOTE: src_end is lxr_bgn; EX: {{a}} has src_end at 3; lxr_bgn for }}
		itm.Dat_rng_(dat_bgn, dat_end);	// always set dat, even if itm has dynamic parts; EX: {{{ a{{{1}}}b }}} has 4,13, not 3,14 (ignore ws)

//			if (itm_is_static)
//				itm.Dat_ary_(dat_end == dat_bgn ? Bry_.Empty : Bry_.Mid(src, dat_bgn, dat_end));
		itm.Itm_static_(itm_is_static);
	}
	public static final    Arg_bldr Instance = new Arg_bldr(); Arg_bldr() {}
}
/*
NOTE_1:mark tkn ignore unless wkr is prm;
need to trim ws when parsing prm_idx/prm_key, but need to preserve raw for output
EX: {{{ 1 }}}
. parsing will set prm_idx = 1 (ws is ignored)
. when calling with {{test|a}} -> "a"
. when calling with {{test}}   -> "{{{ 1 }}}"
so basically, "trim" to get Dat_bgn and Dat_end, but do not mark tkn as ignore
alternative would be to set trim = false and then have prm_idx/prm_key do trim; however that would
1) ... involve duplication of trim code
2) ... be less performant (iterating subs again)
*/