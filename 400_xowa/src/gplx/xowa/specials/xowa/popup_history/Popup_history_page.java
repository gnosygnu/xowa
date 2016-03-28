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
package gplx.xowa.specials.xowa.popup_history; import gplx.*; import gplx.xowa.*; import gplx.xowa.specials.*; import gplx.xowa.specials.xowa.*;
import gplx.core.brys.fmtrs.*;
import gplx.xowa.htmls.modules.popups.*;
public class Popup_history_page implements Xows_page {
	public Xows_special_meta Special_meta() {return Xows_special_meta_.Itm__popup_history;}
	public void Special_gen(Xowe_wiki wiki, Xoae_page page, Xoa_url url, Xoa_ttl ttl) {
		Xoae_page cur_page = wiki.Appe().Gui_mgr().Browser_win().Active_page(); if (cur_page == null) return;
		Ordered_hash hash = cur_page.Popup_mgr().Itms();
		int len = hash.Count();
		Bry_bfr bfr = wiki.Utl__bfr_mkr().Get_k004();
		for (int i = len - 1; i > -1; --i) {
			Xow_popup_itm itm = (Xow_popup_itm)hash.Get_at(i);
			if (Ttl_chk(itm.Page_ttl())) continue;
			fmtr_main.Bld_bfr_many(bfr, itm.Page_href(), itm.Page_ttl().Full_txt_w_ttl_case());
		}
		page.Data_raw_(bfr.Trim_end(Byte_ascii.Nl).To_bry_and_rls());
		page.Html_data().Html_restricted_n_();
	}
	private Bry_fmtr fmtr_main = Bry_fmtr.new_("<a href='~{href}'>~{ttl}</a>\n\n", "href", "ttl");	// NOTE: need to use anchor (as opposed to lnki or lnke) b/c xwiki will not work on all wikis
	public static boolean Ttl_chk(Xoa_ttl ttl) {
		return	ttl.Ns().Id_is_special()
			&&	Bry_.Eq(ttl.Page_db(), Xows_special_meta_.Itm__popup_history.Key_bry());
	}
}
