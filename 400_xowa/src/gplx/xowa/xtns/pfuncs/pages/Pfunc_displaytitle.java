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
package gplx.xowa.xtns.pfuncs.pages; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.pfuncs.*;
public class Pfunc_displaytitle extends Pf_func_base {
	@Override public int Id() {return Xol_kwd_grp_.Id_page_displaytitle;}
	@Override public Pf_func New(int id, byte[] name) {return new Pfunc_displaytitle().Name_(name);}
	@Override public void Func_evaluate(Xop_ctx ctx, byte[] src, Xot_invk caller, Xot_invk self, Bry_bfr bfr) {
		byte[] val_dat_ary = Eval_argx(ctx, src, caller, self);
		Xow_wiki wiki = ctx.Wiki(); Xop_parser parser = wiki.Parser();
		Xop_ctx new_ctx = Xop_ctx.new_sub_(wiki);
		Xop_root_tkn new_root  = parser.Parse_text_to_wdom(new_ctx, val_dat_ary, false);
		Bry_bfr tmp_bfr = wiki.Utl_bry_bfr_mkr().Get_b512();
		wiki.Html_mgr().Html_wtr().Write_tkn(tmp_bfr, new_ctx, gplx.xowa.html.Xoh_html_wtr_ctx.Display_title, new_root.Data_mid(), new_root, 0, new_root);
		byte[] val_html = tmp_bfr.Mkr_rls().XtoAryAndClear();
		ctx.Cur_page().Html_data().Display_ttl_(val_html);
	}
	public static final Pfunc_displaytitle _ = new Pfunc_displaytitle(); Pfunc_displaytitle() {}
}	
