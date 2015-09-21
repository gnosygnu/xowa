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
package gplx.xowa.xtns.pfuncs.langs; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.pfuncs.*;
import gplx.xowa.langs.*;
import gplx.xowa.parsers.*; import gplx.xowa.parsers.tmpls.*;
public class Pfunc_plural extends Pf_func_base {
	@Override public boolean Func_require_colon_arg() {return true;}
	@Override public void Func_evaluate(Xop_ctx ctx, byte[] src, Xot_invk caller, Xot_invk self, Bry_bfr bfr) {// REF.MW: CoreParserFunctions.php
		byte[] number = Eval_argx(ctx, src, caller, self);
		int self_args_len = self.Args_len();
		int arg_idx = Pf_func_.Eq_(number, Ary_Num_1) ? 0 : 1;
		if (arg_idx == 1 && self_args_len == 1) arg_idx = 0;	// number is plural, but plural_arg not present; use singular; see test
		byte[] word = Pf_func_.Eval_arg_or_empty(ctx, src, caller, self, self_args_len, arg_idx);
		bfr.Add(word);
	}	static final byte[] Ary_Num_1 = new byte[] {Byte_ascii.Num_1};
	@Override public int Id() {return Xol_kwd_grp_.Id_i18n_plural;}
	@Override public Pf_func New(int id, byte[] name) {return new Pfunc_plural().Name_(name);}
}	
