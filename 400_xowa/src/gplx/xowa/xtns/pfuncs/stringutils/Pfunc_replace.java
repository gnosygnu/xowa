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
public class Pfunc_replace extends Pf_func_base {
	@Override public int Id() {return Xol_kwd_grp_.Id_strx_replace;}
	@Override public Pf_func New(int id, byte[] name) {return new Pfunc_replace().Name_(name);}
	@Override public boolean Func_require_colon_arg() {return true;}
	@Override public void Func_evaluate(Xop_ctx ctx, byte[] src, Xot_invk caller, Xot_invk self, Bry_bfr trg) {
		byte[] str = Eval_argx(ctx, src, caller, self);
		int self_args_len = self.Args_len();
		byte[] find = Pf_func_.Eval_arg_or(ctx, src, caller, self, self_args_len, 0, null);
		if (Bry_.Len_eq_0(find)) find = Byte_ascii.Space_bry;	// NOTE: MW defaults empty finds to space (" "); note that leaving it as "" would cause Replace to loop infinitely
		byte[] repl = Pf_func_.Eval_arg_or(ctx, src, caller, self, self_args_len, 1, Bry_.Empty);
		byte[] limit_bry = Pf_func_.Eval_arg_or(ctx, src, caller, self, self_args_len, 2, null);
		int limit = limit_bry == null ? Int_.Max_value : Bry_.To_int_or_neg1(limit_bry);
		Bry_bfr tmp_bfr = Xoa_app_.Utl__bfr_mkr().Get_b128();
		byte[] rv = Bry_.Replace(tmp_bfr, str, find, repl, 0, str.length, limit);
		tmp_bfr.Mkr_rls();
		trg.Add(rv);
	}
}	
