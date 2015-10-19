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
package gplx.xowa.xtns.insiders; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import gplx.xowa.xtns.pfuncs.*; import gplx.xowa.wikis.pages.skins.*;
import gplx.xowa.langs.*; import gplx.xowa.langs.kwds.*;
import gplx.xowa.parsers.*; import gplx.xowa.parsers.tmpls.*;
public class Insider_func extends Pf_func_base {
	@Override public int Id() {return Xol_kwd_grp_.Id_insider;}
	@Override public Pf_func New(int id, byte[] name) {return new Insider_func().Name_(name);}
	@Override public void Func_evaluate(Xop_ctx ctx, byte[] src, Xot_invk caller, Xot_invk self, Bry_bfr bfr) {
		byte[] val = Eval_argx(ctx, src, caller, self);
		Xopg_xtn_skin_mgr skin_mgr = ctx.Cur_page().Html_data().Xtn_skin_mgr();
		Insider_xtn_skin_itm skin_itm = (Insider_xtn_skin_itm)skin_mgr.Get_or_null(Insider_xtn_skin_itm.KEY);
		if (skin_itm == null) {
			skin_itm = new Insider_xtn_skin_itm(ctx.Wiki().Xtn_mgr().Xtn_insider().Html_bldr());
			skin_mgr.Add(skin_itm);
		}
		skin_itm.Add(val);
	}
	public static final Insider_func Instance = new Insider_func(); Insider_func() {}
}
