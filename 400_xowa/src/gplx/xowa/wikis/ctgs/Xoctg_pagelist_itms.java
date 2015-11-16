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
package gplx.xowa.wikis.ctgs; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*;
import gplx.core.brys.fmtrs.*;
import gplx.xowa.htmls.*; import gplx.xowa.htmls.hrefs.*; import gplx.xowa.htmls.core.wkrs.lnkis.htmls.*; import gplx.xowa.wikis.data.tbls.*;
import gplx.xowa.wikis.nss.*;
import gplx.xowa.users.history.*;
public class Xoctg_pagelist_itms extends gplx.core.brys.Bfr_arg_base {
	private Xoh_href_wtr href_wtr; private Xou_history_mgr history_mgr; private Bry_fmtr fmtr_itm;
	public void Init_app(Xoae_app app, Bry_fmtr fmtr_itm) {
		this.href_wtr = app.Html__href_wtr();
		this.history_mgr = app.Usere().History_mgr();
		this.fmtr_itm = fmtr_itm;
	} 
	public void Init_wiki(Xowe_wiki wiki) {this.wiki = wiki;} private Xowe_wiki wiki;
	public void Itms_clear() 				{itms.Clear();} private List_adp itms = List_adp_.new_();	
	public void Itms_add(Xowd_page_itm page) 	{itms.Add(page);}	
	@Override public void Bfr_arg__add(Bry_bfr bfr) {
		int len = itms.Count();
		for (int i = 0; i < len; i++) {
			Xowd_page_itm page = (Xowd_page_itm)itms.Get_at(i);
			Xoa_ttl ttl = Xoa_ttl.parse(wiki, Xow_ns_.Tid__category, page.Ttl_page_db());
			byte[] lnki_cls = Xoh_lnki_wtr.Lnki_cls_visited(history_mgr, wiki.Domain_bry(), ttl.Page_txt());	// NOTE: must be ttl.Page_txt() in order to match Xou_history_mgr.Add
			byte[] lnki_href = href_wtr.Build_to_bry(wiki, ttl);
			byte[] lnki_ttl = ttl.Full_txt();
			byte[] lnki_text = ttl.Page_txt();
			fmtr_itm.Bld_bfr_many(bfr, lnki_cls, lnki_href, lnki_ttl, lnki_text);
		}
	}
}
