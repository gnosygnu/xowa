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
import gplx.xowa.langs.*; import gplx.xowa.langs.funcs.*;
public class Xot_invk_wkr implements Xop_ctx_wkr, Xop_arg_wkr {
	public void Ctor_ctx(Xop_ctx ctx) {}
	public void Page_bgn(Xop_ctx ctx, Xop_root_tkn root) {this.tkn_mkr = ctx.Tkn_mkr();} private Xop_tkn_mkr tkn_mkr;
	public void Page_end(Xop_ctx ctx, Xop_root_tkn root, byte[] src, int src_len) {}
	public void AutoClose(Xop_ctx ctx, Xop_tkn_mkr tkn_mkr, Xop_root_tkn root, byte[] src, int src_len, int lxr_bgn_pos, int lxr_cur_pos, Xop_tkn_itm tkn) {}
	private static Arg_bldr arg_bldr = Arg_bldr.Instance;
	public int Make_tkn(Xop_ctx ctx, Xop_root_tkn root, byte[] src, int lxr_cur_pos, int lxr_end_pos, Xop_curly_bgn_tkn bgn_tkn, int keep_curly_bgn) {
		Xot_invk_tkn invk = tkn_mkr.Tmpl_invk(bgn_tkn.Src_bgn(), lxr_end_pos);
		int loop_bgn = bgn_tkn.Tkn_sub_idx() + 1, loop_end = root.Subs_len();

		// handle empty template; EX: "{{}}"
		if (loop_bgn == loop_end) {
			root.Subs_del_after(bgn_tkn.Tkn_sub_idx());						// del invk_bgn_tkn
			root.Subs_add(tkn_mkr.Txt(bgn_tkn.Src_bgn(), lxr_end_pos));		// add txt tkn
			return lxr_cur_pos;
		}

		// make arguments
		boolean made = arg_bldr.Bld(ctx, tkn_mkr, this, Xop_arg_wkr_.Typ_tmpl, root, invk, lxr_cur_pos, lxr_end_pos, loop_bgn, loop_end, src);
		Arg_itm_tkn key_tkn = invk.Name_tkn().Key_tkn();
		if (	!made	// invalid args;
			||	(key_tkn.Dat_bgn() == key_tkn.Dat_end() && key_tkn.Dat_bgn() != -1)) {	// key_tkn is entirely whitespace; EX: {{\n}}
			invk.Tkn_tid_to_txt();
			ctx.Subs_add(root, tkn_mkr.Txt(lxr_cur_pos, lxr_end_pos));
			return lxr_cur_pos;
		}
		root.Subs_del_after(bgn_tkn.Tkn_sub_idx() + keep_curly_bgn);	// all tkns should now be converted; delete everything in root
		root.Subs_add(invk);
		return lxr_cur_pos;
	}
	public boolean Args_add(Xop_ctx ctx, byte[] src, Xop_tkn_itm tkn, Arg_nde_tkn nde, int nde_idx) {
		Xot_invk_tkn invk = (Xot_invk_tkn)tkn;
		if (nde_idx == 0)	// 1st arg; name_tkn
			AddNameArg(ctx, src, invk, nde);
		else
			invk.Args_add(ctx, nde);
		return true;
	}
	private static void AddNameArg(Xop_ctx ctx, byte[] src, Xot_invk_tkn invk, Arg_nde_tkn nde) {
		// make valTkn into a keyTkn; note that argBldr will only generate a valTkn
		Arg_itm_tkn key_tkn = nde.Val_tkn(), val_tkn = nde.Key_tkn();
		nde.Key_tkn_(key_tkn).Val_tkn_(val_tkn);
		invk.Name_tkn_(nde);

		if (key_tkn.Itm_static() != Bool_.Y_byte) return;	// dynamic tkn; can't identify func/name
		int colon_pos = -1, txt_bgn = key_tkn.Dat_bgn(), txt_end = key_tkn.Dat_end();

		Xol_func_itm finder = new Xol_func_itm();	// TS.MEM: DATE:2016-07-12
		ctx.Wiki().Lang().Func_regy().Find_defn(finder, src, txt_bgn, txt_end);
		Xot_defn finder_func = finder.Func();
		byte finder_tid = finder.Tid();
		int finder_colon_pos = finder.Colon_pos();
		int finder_subst_bgn = finder.Subst_bgn();
		int finder_subst_end = finder.Subst_end();

		switch (finder_tid) {
			case Xot_defn_.Tid_func:				// func
				colon_pos = finder_colon_pos;
				break;
			case Xot_defn_.Tid_subst:			// subst/safesubst; mark invk_tkn
			case Xot_defn_.Tid_safesubst:
				int subst_bgn = finder_subst_bgn, subst_end = finder_subst_end;
				invk.Tmpl_subst_props_(finder_tid, subst_bgn, subst_end);
				if ((ctx.Parse_tid() == Xop_parser_tid_.Tid__defn && finder_tid == Xot_defn_.Tid_subst)	// NOTE: if subst, but in tmpl stage, do not actually subst; PAGE:en.w:Unreferenced; DATE:2013-01-31
					|| ctx.Page().Ttl().Ns().Id_is_tmpl()) {												// also, if on tmpl page, never evaluate (questionable, but seems to be needed)
				}	
				else {
					key_tkn.Dat_rng_ary_(src, subst_end, txt_end);	// redo txt_rng to ignore subst
					key_tkn.Dat_ary_had_subst_y_();
				}
				if (finder_func != Xot_defn_.Null) {
					colon_pos = finder_colon_pos;
					txt_bgn = subst_end;
				}
				break;
		}
		if (colon_pos == -1) return; // no func
		invk.Tmpl_defn_(finder_func);
		// split key_subs into val based on ":"; note that this does some of the same ws_at_bgn logic as arg_bldr
		val_tkn = ctx.Tkn_mkr().ArgItm(colon_pos + 1, key_tkn.Src_end());
		nde.Val_tkn_(val_tkn);

		int del_idx = -1; int val_txt_bgn = - 1; 
		for (int i = 0; i < key_tkn.Subs_len(); i++) {
			Xop_tkn_itm sub = key_tkn.Subs_get(i);
			int sub_bgn = sub.Src_bgn();
			if		(sub_bgn < colon_pos && !sub.Tkn_immutable())	{}
			else if (sub_bgn == colon_pos)	{del_idx = i; nde.Eq_tkn_(sub);}
			else {
				if (val_txt_bgn == -1) {	// txt_sub not found yet
					switch (sub.Tkn_tid()) {
						case Xop_tkn_itm_.Tid_space: case Xop_tkn_itm_.Tid_tab: case Xop_tkn_itm_.Tid_newLine:
							sub.Ignore_y_grp_(ctx, key_tkn, i);
							break;
						default:			// txt_sub found
							val_txt_bgn = sub_bgn;
							break;
					}
				}
				val_tkn.Subs_add_grp(sub, key_tkn, i);
			}
		}
		if (val_txt_bgn == -1) val_txt_bgn = txt_end; // default to txt_end to handle empty; EX: {{padleft: |}}
		key_tkn.Subs_del_after(del_idx);
		key_tkn.Dat_rng_ary_(src, txt_bgn, colon_pos);
		val_tkn.Dat_rng_ary_(src, val_txt_bgn, txt_end);
	}
}
