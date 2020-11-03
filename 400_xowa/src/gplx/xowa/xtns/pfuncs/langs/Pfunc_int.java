/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2020 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.xowa.xtns.pfuncs.langs;

import gplx.Bry_;
import gplx.Bry_bfr;
import gplx.xowa.Xoa_ttl;
import gplx.xowa.Xowe_wiki;
import gplx.xowa.langs.Xol_lang_itm;
import gplx.xowa.langs.kwds.Xol_kwd_grp_;
import gplx.xowa.langs.msgs.Xol_msg_mgr_;
import gplx.xowa.parsers.Xop_ctx;
import gplx.xowa.parsers.tmpls.Xot_invk;
import gplx.xowa.xtns.pfuncs.Pf_func;
import gplx.xowa.xtns.pfuncs.Pf_func_;
import gplx.xowa.xtns.pfuncs.Pf_func_base;

public class Pfunc_int extends Pf_func_base {
	@Override public int Id() {return Xol_kwd_grp_.Id_i18n_int;}
	@Override public boolean Func_require_colon_arg() {return true;}
	@Override public void Func_evaluate(Bry_bfr bfr, Xop_ctx ctx, Xot_invk caller, Xot_invk self, byte[] src) {
		byte[] msg_key = Eval_argx(ctx, src, caller, self);
		msg_key = Xoa_ttl.Replace_spaces(msg_key); // 2020-11-02|ISSUE#:817|handle keys with spaces
		Xowe_wiki wiki = ctx.Wiki();
		Xol_lang_itm page_lang = ctx.Page().Lang();
		byte[][] args_ary = Bry_.Ary_empty;
		int args_len = self.Args_len();
		if (args_len > 0) {
			args_ary = new byte[args_len][];
			for (int i = 0; i < args_len; i++)
				args_ary[i] = Pf_func_.Eval_arg_or_empty(ctx, src, caller, self, self.Args_len(), i);
		}
		byte[] msg_val = Xol_msg_mgr_.Get_msg_val(wiki, page_lang, msg_key, args_ary);
		bfr.Add(msg_val);
	}
	@Override public Pf_func New(int id, byte[] name) {return new Pfunc_int().Name_(name);}
}
