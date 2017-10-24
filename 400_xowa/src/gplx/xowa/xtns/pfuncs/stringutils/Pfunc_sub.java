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
package gplx.xowa.xtns.pfuncs.stringutils; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.pfuncs.*;
import gplx.xowa.langs.*; import gplx.xowa.langs.kwds.*;
import gplx.xowa.parsers.*; import gplx.xowa.parsers.tmpls.*;
public class Pfunc_sub extends Pf_func_base {
	@Override public int Id() {return Xol_kwd_grp_.Id_strx_sub;}
	@Override public Pf_func New(int id, byte[] name) {return new Pfunc_sub().Name_(name);}
	@Override public boolean Func_require_colon_arg() {return true;}
	@Override public void Func_evaluate(Bry_bfr bfr, Xop_ctx ctx, Xot_invk caller, Xot_invk self, byte[] src) {
		byte[] s = Eval_argx(ctx, src, caller, self);
		int self_args_len = self.Args_len();
		int bgn = 0, len = Int_.Min_value;
		if (self_args_len > 0) {
			byte[] bgn_bry = Pf_func_.Eval_arg_or(ctx, src, caller, self, self_args_len, 0, null);
			if (bgn_bry != null) bgn = Bry_.To_int_or(bgn_bry, 0);
			if (self_args_len > 1) {
				byte[] len_bry = Pf_func_.Eval_arg_or(ctx, src, caller, self, self_args_len, 1, null);
				if (len_bry != null) len = Bry_.To_int_or(len_bry, Int_.Min_value);
			}
		}
		int s_len = s.length;
		if (bgn < 0) bgn = s_len + bgn;
		if (len == Int_.Min_value) len = s_len - bgn;
		if (len < 0) len = s_len - bgn + len;	// neg len should remove letters from end; EX: {{#sub:abcde|2|-1}} -> "cd"
		if (bgn < 0 || len < 0) return;			// if still negative, return blank; EX: {{#sub:abcde|2|-5}} -> ""
		byte[] mid = Bry_.Mid(s, bgn, bgn + len);
		bfr.Add(mid);
	}
}	
