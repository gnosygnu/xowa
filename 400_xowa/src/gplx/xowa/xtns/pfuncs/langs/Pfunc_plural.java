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
package gplx.xowa.xtns.pfuncs.langs;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.basics.utls.StringUtl;
import gplx.types.basics.utls.ClassUtl;
import gplx.xowa.xtns.pfuncs.*;
import gplx.xowa.langs.kwds.*;
import gplx.xowa.parsers.*; import gplx.xowa.parsers.tmpls.*;
import gplx.xowa.mediawiki.*;
public class Pfunc_plural extends Pf_func_base {
	@Override public int Id() {return Xol_kwd_grp_.Id_i18n_plural;}
	@Override public Pf_func New(int id, byte[] name) {return new Pfunc_plural().Name_(name);}
	@Override public boolean Func_require_colon_arg() {return true;}
	// EX: {{plural:1|one|many}}
	@Override public void Func_evaluate(BryWtr bfr, Xop_ctx ctx, Xot_invk caller, Xot_invk self, byte[] src) {// REF.MW: CoreParserFunctions.php
		// convert xo_tmpl to mw_ary
		XophpArray forms = Pf_func_.Convert_xo_tmpl_to_mw_ary(ctx, caller, self, src);

		// get number from argx (EX: 1)
		String number_str = StringUtl.NewU8(Eval_argx(ctx, src, caller, self));

		// if number matches explicit key, use it; EX: {{plural:3|2=two|3=three|one|many}} -> three
		Object result = ctx.Lang().Mw_lang().handleExplicitPluralForms(number_str, forms);
		if (ClassUtl.EqByObj(String.class, result)) {
			bfr.AddStrU8((String)result);
			return;
		}

		// no match for explicit key; take results (which has removed all explicit keys) and get plural rule index; EX: {{plural:1|2=two|3=three|one|many}} -> {{plural:?|one|many}}
		XophpArray resultArray = (XophpArray)result;
		int idx = ctx.Lang().Mw_lang().getPluralRuleIndexNumber(number_str);
		if (idx >= XophpArray.count(resultArray)) // bound-check; EX: {{plural:2|wiki}} -> idx = 1 -> idx = 0
			idx = XophpArray.count(resultArray) - 1;
		bfr.AddStrU8(resultArray.Get_at_str(idx));
	}
}
