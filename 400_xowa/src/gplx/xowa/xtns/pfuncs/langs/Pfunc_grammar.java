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
public class Pfunc_grammar extends Pf_func_base {
	@Override public int Id() {return Xol_kwd_grp_.Id_i18n_grammar;}
	@Override public Pf_func New(int id, byte[] name) {return new Pfunc_grammar().Name_(name);}
	@Override public void Func_evaluate(Xop_ctx ctx, byte[] src, Xot_invk caller, Xot_invk self, Bry_bfr bfr) {
		byte[] argx = Eval_argx(ctx, src, caller, self);
		byte[] word = Pf_func_.Eval_arg_or_empty(ctx, src, caller, self, self.Args_len(), 0);
		Xol_lang lang = ctx.Cur_page().Lang();
		boolean pass = false;
		try {pass = lang.Grammar().Grammar_eval(bfr, lang, word, argx);}
		catch (Exception e) {Err_.Noop(e);}
		if (!pass) Xot_invk_tkn.Print_not_found(bfr, ctx.Wiki().Ns_mgr(), this.Name());
	}
}	
