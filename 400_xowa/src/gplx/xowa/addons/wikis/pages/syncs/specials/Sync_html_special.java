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
package gplx.xowa.addons.wikis.pages.syncs.specials; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.pages.*; import gplx.xowa.addons.wikis.pages.syncs.*;
import gplx.core.net.qargs.*;
import gplx.xowa.specials.*; import gplx.xowa.wikis.nss.*;
import gplx.xowa.htmls.*;
public class Sync_html_special implements Xow_special_page {
	public void Special__gen(Xow_wiki wiki, Xoa_page page, Xoa_url url, Xoa_ttl ttl) {
		Gfo_qarg_mgr url_args = new Gfo_qarg_mgr().Init(url.Qargs_ary());

		// get args
		byte[] redirect_bry = url_args.Read_bry_or(Bry_.new_a7("page"), null);
		Xoa_ttl redirect_ttl = wiki.Ttl_parse(redirect_bry);
		// Xoa_url redirect_url = wiki.Utl__url_parser().Parse(redirect_bry);

		// update
		gplx.xowa.apps.wms.apis.parses.Wm_page_updater updater = new gplx.xowa.apps.wms.apis.parses.Wm_page_updater();
		updater.Init_by_app(wiki.App());
		Xoh_page hpg = new Xoh_page();
		updater.Init_by_page(wiki, hpg);
		updater.Update(wiki.App().Wmf_mgr().Download_wkr(), wiki, redirect_ttl);
		((Xowe_wiki)wiki).Data_mgr().Redirect((Xoae_page)page, redirect_bry);	// HACK: should call page.Redirect_trail() below, but need to handle Display_ttl
		// page.Redirect_trail().Itms__add__article(redirect_url, redirect_ttl, null);
	}

	public static final String SPECIAL_KEY = "XowaSyncHtml";	// NOTE: needs to match lang.gfs
	public static final    byte[] Display_ttl = Bry_.new_a7("Sync HTML");
	public Xow_special_meta Special__meta() {return new Xow_special_meta(Xow_special_meta_.Src__mw, SPECIAL_KEY);}
	public static final    Xow_special_page Prototype = new Sync_html_special();
	public Xow_special_page Special__clone() {return this;}
}
