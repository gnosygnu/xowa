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
package gplx.xowa.xtns.geoCrumbs; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import gplx.xowa.xtns.pfuncs.*;
public class Geoc_isin_func extends Pf_func_base {
	@Override public int Id() {return Xol_kwd_grp_.Id_geoCrumbs_isin;}
	@Override public Pf_func New(int id, byte[] name) {return new Geoc_isin_func().Name_(name);}
	@Override public void Func_evaluate(Xop_ctx ctx, byte[] src, Xot_invk caller, Xot_invk self, Bry_bfr bfr) {
		byte[] ttl_bry = Eval_argx(ctx, src, caller, self);
		Xow_wiki wiki = ctx.Wiki();
		Xoa_ttl ttl = Xoa_ttl.parse_(wiki, ttl_bry); if (ttl == null) return;
		byte[] lnki_ttl = Bry_.Add(Xop_tkn_.Lnki_bgn, ttl_bry, Xop_tkn_.Lnki_end);		// make "[[ttl]]"
		Bry_bfr tmp_bfr = wiki.Utl_bry_bfr_mkr().Get_b128();
		wiki.Parser().Parse_text_to_html(tmp_bfr, ctx.Cur_page(), false, lnki_ttl);
		ctx.Cur_page().Html_data().Content_sub_(tmp_bfr.Mkr_rls().Xto_bry_and_clear());
	}
	public static final Geoc_isin_func _ = new Geoc_isin_func();
}
