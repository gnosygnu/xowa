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
import gplx.xowa.xtns.pfuncs.*;
import gplx.xowa.langs.*; import gplx.xowa.langs.kwds.*;
import gplx.xowa.parsers.*; import gplx.xowa.parsers.tmpls.*;
public class Lst_pfunc_lst extends Pf_func_base {
	@Override public int Id() {return Xol_kwd_grp_.Id_lst;}
	@Override public Pf_func New(int id, byte[] name) {return new Lst_pfunc_lst().Name_(name);}
	@Override public void Func_evaluate(Bry_bfr bfr, Xop_ctx ctx, Xot_invk caller, Xot_invk self, byte[] src) {
		// get args
		byte[] page_ttl = Eval_argx(ctx, src, caller, self); if (Bry_.Len_eq_0(page_ttl)) return;		// {{#lst:}} -> ""
		int args_len = self.Args_len();
		byte[] sect_bgn = Pf_func_.Eval_arg_or(ctx, src, caller, self, args_len, 0, Lst_pfunc_itm.Null_arg);
		byte[] sect_end = Pf_func_.Eval_arg_or(ctx, src, caller, self, args_len, 1, Lst_pfunc_itm.Null_arg);
		
		// parse
		Lst_pfunc_itm itm = Lst_pfunc_itm.New_sect_or_null(ctx, page_ttl); if (itm == null) return;
		Lst_pfunc_lst_.Sect_include(bfr, itm.Sec_mgr(), itm.Itm_src(), sect_bgn, sect_end);
	}

	public static final    Lst_pfunc_lst Prime = new Lst_pfunc_lst(); Lst_pfunc_lst() {}
}
