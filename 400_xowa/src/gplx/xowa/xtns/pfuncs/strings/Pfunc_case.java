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
package gplx.xowa.xtns.pfuncs.strings; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.pfuncs.*;
import gplx.xowa.langs.*; import gplx.xowa.langs.kwds.*;
import gplx.xowa.parsers.*; import gplx.xowa.parsers.tmpls.*;
public class Pfunc_case extends Pf_func_base {	// EX: {{lc:A}} -> a
	private boolean first; private int case_type;
	public Pfunc_case(int case_type, boolean first) {this.case_type = case_type; this.first = first;}
	@Override public int Id() {return Xol_kwd_grp_.Id_str_lc;}
	@Override public Pf_func New(int id, byte[] name) {return new Pfunc_case(case_type, first).Name_(name);}
	@Override public boolean Func_require_colon_arg() {return true;}
	@Override public void Func_evaluate(Bry_bfr bfr, Xop_ctx ctx, Xot_invk caller, Xot_invk self, byte[] src) {
		byte[] argx = Eval_argx(ctx, src, caller, self); if (argx == Bry_.Empty) return;
		int argx_len = argx.length; if (argx_len == 0) return; // nothing to uc / lc; just return
		Xol_lang_itm lang = ctx.Wiki().Lang();
		boolean upper = case_type == Xol_lang_itm.Tid_upper;
		if (first) {
			Bry_bfr tmp_bfr = ctx.Wiki().Utl__bfr_mkr().Get_b512();
			argx = lang.Case_mgr().Case_build_1st(tmp_bfr, upper, argx, 0, argx_len);
			tmp_bfr.Mkr_rls();
		}
		else
			argx = lang.Case_mgr().Case_build(upper, argx, 0, argx_len);
		bfr.Add(argx);
	}
}	
