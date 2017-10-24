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
public class Pfunc_explode extends Pf_func_base {
	@Override public int Id() {return Xol_kwd_grp_.Id_strx_explode;}
	@Override public Pf_func New(int id, byte[] name) {return new Pfunc_explode().Name_(name);}
	@Override public boolean Func_require_colon_arg() {return true;}
	@Override public void Func_evaluate(Bry_bfr bfr, Xop_ctx ctx, Xot_invk caller, Xot_invk self, byte[] src) {
		// 	 * {{#explode:String | delimiter | position | limit}}
		byte[] s = Eval_argx(ctx, src, caller, self);
		int args_len = self.Args_len();
		byte[] dlm = Byte_ascii.Space_bry; int idx = 0, limit = -1;
		if (args_len > 0) {
			dlm = Pf_func_.Eval_arg_or(ctx, src, caller, self, args_len, 0, null);
			if (Bry_.Len_eq_0(dlm)) dlm = Byte_ascii.Space_bry;	// handle empty String; EX: {{#explode:a b||1}}
			if (args_len > 1) {
				byte[] pos_bry = Pf_func_.Eval_arg_or(ctx, src, caller, self, args_len, 1, null);
				if (pos_bry != null) idx = Bry_.To_int_or(pos_bry, 0);
				if (args_len > 2) {
					byte[] limit_bry = Pf_func_.Eval_arg_or(ctx, src, caller, self, args_len, 2, null);
					if (limit_bry != null) limit = Bry_.To_int_or(pos_bry, -1);
				}
			}
		}
		if (idx < 0) {
			int count = Pfunc_count.Count(s, dlm);
			idx = count + idx;
		}
		byte[] rv = Split_and_get_by_idx(s, dlm, idx, limit);
		bfr.Add(rv);
	}
	private static byte[] Split_and_get_by_idx(byte[] src, byte[] dlm, int idx, int limit) {
		int src_len = src.length; int dlm_len = dlm.length;
		int pos = 0; int found = 0;
		while (true) {
			int find_pos = Bry_find_.Find_fwd(src, dlm, pos);
			if (find_pos == Bry_find_.Not_found) break;
			if (found == idx) return Bry_.Mid(src, pos, find_pos);
			pos = find_pos + dlm_len;
			++found;
		}
		return found == idx ? Bry_.Mid(src, pos, src_len) : Bry_.Empty;
	}
}	
