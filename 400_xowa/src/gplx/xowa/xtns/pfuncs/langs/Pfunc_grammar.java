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
package gplx.xowa.xtns.pfuncs.langs; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.pfuncs.*;
import gplx.xowa.langs.*; import gplx.xowa.langs.kwds.*;
import gplx.xowa.parsers.*; import gplx.xowa.parsers.tmpls.*;
public class Pfunc_grammar extends Pf_func_base {
	@Override public int Id() {return Xol_kwd_grp_.Id_i18n_grammar;}
	@Override public boolean Func_require_colon_arg() {return true;}
	@Override public Pf_func New(int id, byte[] name) {return new Pfunc_grammar().Name_(name);}	// NOTE: odmiana used as magic-word / template in pl.d; EX:pl.d:hund; DATE:2014-08-14
	@Override public void Func_evaluate(Bry_bfr bfr, Xop_ctx ctx, Xot_invk caller, Xot_invk self, byte[] src) {
		byte[] argx = Eval_argx(ctx, src, caller, self);
		byte[] word = Pf_func_.Eval_arg_or_empty(ctx, src, caller, self, self.Args_len(), 0);
		Xol_lang_itm lang = ctx.Page().Lang();
		boolean pass = false;
		try {pass = lang.Grammar().Grammar_eval(bfr, lang, word, argx);}
		catch (Exception e) {Err_.Noop(e);}
		if (!pass) Xot_invk_tkn_.Print_not_found__w_template(bfr, ctx.Wiki().Ns_mgr(), this.Name());
	}
}	
