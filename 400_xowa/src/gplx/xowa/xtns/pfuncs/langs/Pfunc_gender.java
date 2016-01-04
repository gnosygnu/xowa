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
import gplx.core.primitives.*; import gplx.xowa.users.*;
import gplx.xowa.langs.*; import gplx.xowa.langs.kwds.*; import gplx.xowa.langs.genders.*;
import gplx.xowa.parsers.*; import gplx.xowa.parsers.tmpls.*;
public class Pfunc_gender extends Pf_func_base {
	@Override public int Id() {return Xol_kwd_grp_.Id_i18n_gender;}
	@Override public boolean Func_require_colon_arg() {return true;}
	@Override public Pf_func New(int id, byte[] name) {return new Pfunc_gender().Name_(name);}
	@Override public void Func_evaluate(Bry_bfr bfr, Xop_ctx ctx, Xot_invk caller, Xot_invk self, byte[] src) {
		byte[] user_name = Eval_argx(ctx, src, caller, self);
		byte[] when_m = Bry_.Empty, when_f = Bry_.Empty, when_u = Bry_.Empty;
		int self_args_len = self.Args_len();
		if (self_args_len == 0) return;	// per MW: EX: {{gender:name}} -> ""
		else {
			if (self_args_len > 0) {
				when_m = when_u = Pf_func_.Eval_arg_or_empty(ctx, src, caller, self, self_args_len, 0);	// default when_u to when_m
				if (self_args_len > 1) {
					when_f = Pf_func_.Eval_arg_or_empty(ctx, src, caller, self, self_args_len, 1);
					if (self_args_len > 2) {
						when_u = Pf_func_.Eval_arg_or_empty(ctx, src, caller, self, self_args_len, 2);
					}
				}
			}
		}
		if (self_args_len == 1) {bfr.Add(when_m); return;}	// per MW: EX: {{gender:name|a}} -> "a"
		int gender = Get_gender(ctx.App().User(), user_name);
		Xol_lang_itm lang = ctx.Cur_page().Lang();
		bfr.Add(lang.Gender().Gender_eval(gender, when_m, when_f, when_u));
	}
	private static int Get_gender(Xou_user user, byte[] user_name) {
		int user_name_len = user_name.length;
		switch (user_name_len) {
			case 0:		return Xol_gender_.Tid_unknown;												// EX: {{gender:|m|f}}
			case 1:		if (user_name[0] == Byte_ascii.Dot) return Xol_gender_.Tid_unknown; break;	// EX: {{gender:.|m|f}}; TODO: should define default gender for wiki
		}
		Object o = gender_cache.Get_by_bry(user_name);
		return o == null ? user.Gender() : ((Int_obj_val)o).Val();
	}
	private static final Hash_adp_bry gender_cache = Hash_adp_bry.cs()	// for tests
	.Add_str_int("xowa_male"	, Xol_gender_.Tid_male)
	.Add_str_int("xowa_female"	, Xol_gender_.Tid_female)
	;
}	
