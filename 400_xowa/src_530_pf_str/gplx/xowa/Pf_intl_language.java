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
package gplx.xowa; import gplx.*;
class Pf_intl_language extends Pf_func_base {
	@Override public int Id() {return Xol_kwd_grp_.Id_i18n_language;}
	@Override public Pf_func New(int id, byte[] name) {return new Pf_intl_language().Name_(name);}
	@Override public void Func_evaluate(Xop_ctx ctx, byte[] src, Xot_invk caller, Xot_invk self, Bry_bfr bfr) {
		byte[] argx = Eval_argx(ctx, src, caller, self);
		Hash_adp_bry regy = Xol_lang_itm_.Regy();
		if (argx.length == 0) return;	// {{#language:}} should return ""; note that byte[0] will fail in MatchAtCurExact
		Object o = regy.Get_by_bry(argx);
		if (o == null)
			bfr.Add(argx);
		else {
			Xol_lang_itm lang_itm = (Xol_lang_itm)o;
			bfr.Add(lang_itm.Canonical_name());
		}
	}
}	
