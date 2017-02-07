/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012 gnosygnu@gmail.com

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
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
