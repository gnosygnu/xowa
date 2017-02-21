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
public class Xot_prm_tkn extends Xop_tkn_itm_base {
	@Override public byte Tkn_tid() {return Xop_tkn_itm_.Tid_tmpl_prm;}		
	@Override public void Tmpl_fmt(Xop_ctx ctx, byte[] src, Xot_fmtr fmtr) {fmtr.Reg_prm(ctx, src, this, prm_idx, prm_key, dflt_tkn);}
	@Override public void Tmpl_compile(Xop_ctx ctx, byte[] src, Xot_compile_data prep_data) {
		if (find_tkn != null) {	// NOTE: find_tkn defaults to null
			int subs_len = find_tkn.Subs_len();
			for (int i = 0; i < subs_len; i++) {
				Xop_tkn_itm sub = find_tkn.Subs_get(i);
				switch (sub.Tkn_tid()) {
					case Xop_tkn_itm_.Tid_tmpl_invk: case Xop_tkn_itm_.Tid_tmpl_prm: case Xop_tkn_itm_.Tid_ignore: case Xop_tkn_itm_.Tid_xnde:
						find_tkn_static = false;
						break;
				}
				sub.Tmpl_compile(ctx, src, prep_data);
			}
			if (find_tkn_static) {	// subs_are_static, so extract idx/key; EX: {{{a b}}} will have 3 subs which are all static; {{{a{{{1}}}b}}} will be dynamic
				int find_tkn_bgn = find_tkn.Dat_bgn(), find_tkn_end = find_tkn.Dat_end();
				if (find_tkn_end - find_tkn_bgn > 0) {	// NOTE: handles empty find_tkns; EX: {{{|safesubst:}}}
					prm_idx = Bry_.To_int_or(src, find_tkn_bgn, find_tkn_end, -1);			// parse as number first; note that bgn,end should not include ws; EX: " 1 " will fail
					if (prm_idx == -1) prm_key = Bry_.Mid(src, find_tkn_bgn, find_tkn_end);// not a number; parse as key
				}
			}
		}
		if (dflt_tkn != null) dflt_tkn.Tmpl_compile(ctx, src, prep_data);
	}
	@Override public boolean Tmpl_evaluate(Xop_ctx ctx, byte[] src, Xot_invk caller, Bry_bfr bfr) {
		if (!find_tkn_static) {
			int subs_len = find_tkn.Subs_len();
			Bry_bfr find_bfr = Bry_bfr_.New();
			for (int i = 0; i < subs_len; i++)
				find_tkn.Subs_get(i).Tmpl_evaluate(ctx, src, caller, find_bfr);
			prm_idx = Bry_.To_int_or__trim_ws(find_bfr.Bfr(), 0, find_bfr.Len(), -1);	// parse as number first; NOTE: trim needed to transform "{{{ 1 }}}" to "1"; it.w:Portale:Giochi_da_tavolo; DATE:2014-02-09
			if (prm_idx == -1)
				prm_key = find_bfr.To_bry_and_clear_and_trim();									// not a number; parse as key; NOTE: must trim; PAGE:en.w:William Shakespeare; {{Relatebardtree}}
		}
		Arg_nde_tkn arg_nde = null;
		if (prm_idx == -1) {	// prm is key; EX: "{{{key1}}}"
			if (prm_key != Bry_.Empty)	// NOTE: handles empty find_tkns; EX: {{{|safesubst:}}}
				arg_nde = caller.Args_get_by_key(src, prm_key);
			if (arg_nde == null) {Tmpl_write_missing(ctx, src, caller, bfr); return true;}
		}
		else {					// prm is idx; EX: "{{{1}}}"
//				int invk_args_len = caller.Args_len();
//				if (prm_idx > invk_args_len) {Tmpl_write_missing(ctx, src, caller, bfr); return true;}
			arg_nde = caller.Args_eval_by_idx(src, prm_idx - List_adp_.Base1);	// MW args are Base1; EX: {{test|a|b}}; a is {{{1}}}; b is {{{2}}}
			if (arg_nde == null) {Tmpl_write_missing(ctx, src, caller, bfr); return true;}	// EX: handles "{{{1}}}{{{2}}}" "{{test|a|keyd=b}}" -> "a{{{2}}}"
		}
		Arg_itm_tkn arg_val = arg_nde.Val_tkn();
		if (arg_val.Itm_static() == Bool_.Y_byte)
			bfr.Add_mid(src, arg_val.Dat_bgn(), arg_val.Dat_end());
		else {// compile arg if dynamic; EX: [[MESSENGER]] "{{About|the NASA space mission||Messenger (disambiguation){{!}}Messenger}}"; {{!}} causes {{{2}}} to be dynamic and its dat_ary will be an empty-String ("")
			Bry_bfr arg_val_bfr = Bry_bfr_.New();
			arg_val.Tmpl_evaluate(ctx, src, caller, arg_val_bfr);
			bfr.Add_bfr_and_clear(arg_val_bfr);
		}
		return true;
	}
	private void Tmpl_write_missing(Xop_ctx ctx, byte[] src, Xot_invk caller, Bry_bfr bfr) {
		if (dflt_tkn == null) {							// dflt absent; write orig; {{{1}}} or {{{key}}};
			bfr.Add(Xop_curly_wkr.Hook_prm_bgn);
			int subs_len = find_tkn.Subs_len();
			for (int i = 0; i < subs_len; i++)
				find_tkn.Subs_get(i).Tmpl_evaluate(ctx, src, caller, bfr);
			bfr.Add(Xop_curly_wkr.Hook_prm_end);
		} else dflt_tkn.Tmpl_evaluate(ctx, src, caller, bfr);	// dflt exists; write it
	}
	int prm_idx = -1; byte[] prm_key = Bry_.Empty; boolean find_tkn_static = true;
	public Arg_itm_tkn Find_tkn() {return find_tkn;} public Xot_prm_tkn Find_tkn_(Arg_itm_tkn v) {find_tkn = v; return this;} Arg_itm_tkn find_tkn;
	public Xot_prm_tkn Dflt_tkn_(Arg_itm_tkn v) {dflt_tkn = v; return this;} Arg_itm_tkn dflt_tkn;		
	public Xot_prm_tkn(int bgn, int end) {this.Tkn_ini_pos(false, bgn, end);}
}
