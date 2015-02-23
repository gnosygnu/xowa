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
import gplx.xowa.html.modules.popups.*;
public class Popup_history_page implements Xows_page {
	public void Special_gen(Xoa_url url, Xoae_page page, Xowe_wiki wiki, Xoa_ttl ttl) {
		Xoae_page cur_page = wiki.Appe().Gui_mgr().Browser_win().Active_page(); if (cur_page == null) return;
		OrderedHash hash = cur_page.Popup_mgr().Itms();
		int len = hash.Count();
		Bry_bfr bfr = wiki.Utl_bry_bfr_mkr().Get_k004();
		for (int i = len - 1; i > -1; --i) {
			Xow_popup_itm itm = (Xow_popup_itm)hash.FetchAt(i);
			if (Ttl_chk(itm.Page_ttl())) continue;
			fmtr_main.Bld_bfr_many(bfr, itm.Page_href(), itm.Page_ttl().Full_txt());
		}
		page.Data_raw_(bfr.Trim_end(Byte_ascii.NewLine).Mkr_rls().Xto_bry_and_clear());
		page.Html_data().Html_restricted_n_();
	}
	private Bry_fmtr fmtr_main = Bry_fmtr.new_("<a href='~{href}'>~{ttl}</a>\n\n", "href", "ttl");	// NOTE: need to use anchor (as opposed to lnki or lnke) b/c xwiki will not work on all wikis
	public static final byte[] Ttl_name_bry = Bry_.new_ascii_("XowaPopupHistory");
	public static boolean Ttl_chk(Xoa_ttl ttl) {
		return	ttl.Ns().Id_special()
			&&	Bry_.Eq(ttl.Page_db(), Ttl_name_bry);
	}
}
