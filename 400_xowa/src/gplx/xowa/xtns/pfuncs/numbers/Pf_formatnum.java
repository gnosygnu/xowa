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
package gplx.xowa.xtns.pfuncs.numbers; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.pfuncs.*;
import gplx.core.btries.*; import gplx.core.intls.*;
import gplx.xowa.langs.*; import gplx.xowa.langs.kwds.*; import gplx.xowa.langs.numbers.*;
import gplx.xowa.parsers.*; import gplx.xowa.parsers.tmpls.*;
public class Pf_formatnum extends Pf_func_base {
	@Override public int Id() {return Xol_kwd_grp_.Id_str_formatnum;}
	@Override public Pf_func New(int id, byte[] name) {return new Pf_formatnum().Name_(name);}
	@Override public boolean Func_require_colon_arg() {return true;}
	@Override public void Func_evaluate(Bry_bfr bfr, Xop_ctx ctx, Xot_invk caller, Xot_invk self, byte[] src) {
		Xol_lang_itm lang = ctx.Wiki().Lang();
		int self_args_len = self.Args_len();
		byte[] argx = Eval_argx(ctx, src, caller, self);
		byte[] arg1 = Pf_func_.Eval_arg_or_empty(ctx, src, caller, self, self_args_len, 0);
		bfr.Add(Format_num(lang, argx, arg1));
	}
	public static byte[] Format_num(Xol_lang_itm lang, byte[] num, byte[] arg1) {
		Btrie_slim_mgr trie_raw = lang.Kwd_mgr().Trie_raw();
		Btrie_slim_mgr trie_nosep = lang.Kwd_mgr().Trie_nosep();
		int arg1_len = arg1.length;
		if (Bry_.Len_gt_0(arg1)) {		// argument specified
			if		(trie_raw	.Match_exact(arg1, 0, arg1_len) != null)
				return lang.Num_mgr().Raw(num);
			else if (trie_nosep	.Match_exact(arg1, 0, arg1_len) != null)
				return lang.Num_mgr().Format_num_no_separators(num);
		}
		return lang.Num_mgr().Format_num(num);
	}
}	
