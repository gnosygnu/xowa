/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2017 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.xowa.specials.xowa.popup_history; import gplx.*; import gplx.xowa.*; import gplx.xowa.specials.*; import gplx.xowa.specials.xowa.*;
import gplx.core.brys.fmtrs.*;
import gplx.xowa.htmls.modules.popups.*;
public class Popup_history_page implements Xow_special_page {
	public Xow_special_meta Special__meta() {return Xow_special_meta_.Itm__popup_history;}
	public void Special__gen(Xow_wiki wikii, Xoa_page pagei, Xoa_url url, Xoa_ttl ttl) {
		Xowe_wiki wiki = (Xowe_wiki)wikii; Xoae_page page = (Xoae_page)pagei;
		Xoae_page cur_page = wiki.Appe().Gui_mgr().Browser_win().Active_page(); if (cur_page == null) return;
		Ordered_hash hash = cur_page.Popup_mgr().Itms();
		int len = hash.Count();
		Bry_bfr bfr = wiki.Utl__bfr_mkr().Get_k004();
		for (int i = len - 1; i > -1; --i) {
			Xow_popup_itm itm = (Xow_popup_itm)hash.Get_at(i);
			if (Ttl_chk(itm.Page_ttl())) continue;
			fmtr_main.Bld_bfr_many(bfr, itm.Page_href(), itm.Page_ttl().Full_txt_w_ttl_case());
		}
		page.Db().Text().Text_bry_(bfr.Trim_end(Byte_ascii.Nl).To_bry_and_rls());
		page.Html_data().Html_restricted_n_();
	}
	private Bry_fmtr fmtr_main = Bry_fmtr.new_("<a href='~{href}'>~{ttl}</a>\n\n", "href", "ttl");	// NOTE: need to use anchor (as opposed to lnki or lnke) b/c xwiki will not work on all wikis
	public static boolean Ttl_chk(Xoa_ttl ttl) {
		return	ttl.Ns().Id_is_special()
			&&	Bry_.Eq(ttl.Page_db(), Xow_special_meta_.Itm__popup_history.Key_bry());
	}

	public Xow_special_page Special__clone() {return this;}
}
