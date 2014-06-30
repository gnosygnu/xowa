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
package gplx.xowa.xtns.lst; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
public class Lst_pfunc_lst extends Pf_func_base {
	@Override public int Id() {return Xol_kwd_grp_.Id_lst;}
	@Override public Pf_func New(int id, byte[] name) {return new Lst_pfunc_lst().Name_(name);}
	@Override public void Func_evaluate(Xop_ctx ctx, byte[] src, Xot_invk caller, Xot_invk self, Bry_bfr bfr) {
		byte[] src_ttl_bry = Eval_argx(ctx, src, caller, self); if (Bry_.Len_eq_0(src_ttl_bry)) return;		// {{#lst:}} -> ""
		int args_len = self.Args_len();
		byte[] sect_bgn = Pf_func_.Eval_arg_or(ctx, src, caller, self, args_len, 0, Lst_pfunc_wkr.Null_arg);
		byte[] sect_end = Pf_func_.Eval_arg_or(ctx, src, caller, self, args_len, 1, Lst_pfunc_wkr.Null_arg);
		new Lst_pfunc_wkr().Init_include(src_ttl_bry, sect_bgn, sect_end).Exec(bfr, ctx);
	}
	public static final Lst_pfunc_lst _ = new Lst_pfunc_lst(); Lst_pfunc_lst() {}
	public static Hash_adp_bry new_xatrs_(Xol_lang lang) {
		Hash_adp_bry rv = Hash_adp_bry.ci_();
		rv.Add_str_byte("name", Lst_section_nde.Xatr_name);
		Xatrs_add(rv, "begin", "end");
		switch (lang.Lang_id()) {	// NOTE: as of v315572b, i18n is done directly in code, not in magic.php; am wary of adding keywords for general words like begin/end, so adding them manually per language; DATE:2013-02-09
			case Xol_lang_itm_.Id_de: Xatrs_add(rv, "Anfang", "Ende"); break;
			case Xol_lang_itm_.Id_he: Xatrs_add(rv, "התחלה", "סוף"); break;
			case Xol_lang_itm_.Id_pt: Xatrs_add(rv, "começo", "fim"); break;
		}
		return rv;
	}
	private static void Xatrs_add(Hash_adp_bry hash, String key_begin, String key_end) {
		hash.Add_str_byte(key_begin	, Lst_section_nde.Xatr_bgn);
		hash.Add_str_byte(key_end	, Lst_section_nde.Xatr_end);
	}
}
