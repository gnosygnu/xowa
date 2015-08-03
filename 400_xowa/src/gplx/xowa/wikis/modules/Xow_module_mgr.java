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
package gplx.xowa.wikis.modules; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*;
import gplx.xowa.html.heads.*; import gplx.xowa.html.modules.popups.*;
public class Xow_module_mgr implements GfoInvkAble {
	private Hash_adp_bry regy = Hash_adp_bry.cs();
	public Xow_module_mgr(Xowe_wiki wiki) {
		this.popup_mgr = new Xow_popup_mgr(wiki);
		regy.Add_bry_obj(Xoh_head_itm_.Key__top_icon		, itm__top_icon);
		regy.Add_bry_obj(Xoh_head_itm_.Key__navframe		, itm__navframe);
		regy.Add_bry_obj(Xoh_head_itm_.Key__title_rewrite	, itm__title_rewrite);
	}
	public void Init_by_wiki(Xowe_wiki wiki) {
		popup_mgr.Init_by_wiki(wiki);
	}
	public Xow_module_base		Itm__top_icon() {return itm__top_icon;} private Xow_module_base itm__top_icon = new Xow_module_base();
	public Xow_module_base		Itm__navframe() {return itm__navframe;} private Xow_module_base itm__navframe = new Xow_module_base();
	public Xow_module_base		Itm__title_rewrite() {return itm__title_rewrite;} private Xow_module_base itm__title_rewrite = new Xow_module_base();
	public Xow_popup_mgr Popup_mgr() {return popup_mgr;} private Xow_popup_mgr popup_mgr;
	public Xow_module_base Get(byte[] key) {return (Xow_module_base)regy.Get_by_bry(key);}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_get))				return Get(m.ReadBry("v"));
		else	return GfoInvkAble_.Rv_unhandled;
	}	private static final String Invk_get = "get";
}
