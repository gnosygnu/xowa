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
package gplx.xowa.addons.apps.file_browsers; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.apps.*;
import gplx.core.net.*;
import gplx.xowa.wikis.xwikis.*;
import gplx.langs.mustaches.*;
import gplx.xowa.users.data.*; import gplx.xowa.wikis.pages.*;
class Wikis_list_wtr {
	public Xopage_html_data Write(Xoa_app app, Gfo_qarg_itm[] args, GfoInvkAble select_invkable) {
		// scan wikis
		List_adp list = List_adp_.new_();
		app.User().User_db_mgr().Init_site_mgr();
		Xoud_site_row[] site_ary = app.User().User_db_mgr().Site_mgr().Get_all();
		int len = site_ary.length;
		for (int i = 0; i < len; ++i) {
			Xoud_site_row site_itm = site_ary[i];
			if (String_.Eq(site_itm.Domain(), gplx.xowa.wikis.domains.Xow_domain_itm_.Str__home)) continue;
			list.Add(new Wikis_list_itm(Bry_.new_u8(site_itm.Domain()), Bry_.new_a7("2016-03-05")));
		}
		Wikis_list_mgr mgr = new Wikis_list_mgr(Get_root_url(), (Wikis_list_itm[])list.To_ary_and_clear(Wikis_list_itm.class));

		// write body
		Io_url addon_dir = app.Fsys_mgr().Bin_addon_dir().GenSubDir_nest("app", "wiki_list");
		byte[] template_src = Io_mgr.Instance.LoadFilBry(addon_dir.GenSubFil_nest("bin", "wiki_list.mustache.html"));
		Bry_bfr tmp_bfr = Bry_bfr.new_();
		byte[] body = gplx.langs.mustaches.Mustache_wtr_.Write_to_bry(tmp_bfr, template_src, mgr);

		// write to html
		Xopage_html_data rv = new Xopage_html_data(Wikis_list_page.Display_ttl, body);
		rv.Head_tags().Add(Xopg_tag_itm.New_css_file(addon_dir.GenSubFil_nest("bin", "wiki_list.css")));
		return rv;
	}
	private static byte[] Get_root_url() {
		byte tid = gplx.core.envs.Op_sys.Cur().Tid();
		byte[] rv = Bry_.new_a7("/");
		switch (tid) {
			case gplx.core.envs.Op_sys.Tid_wnt	: rv = Bry_.new_a7("C:\\"); break;
		}
		rv = gplx.langs.htmls.encoders.Gfo_url_encoder_.Href.Encode(rv);
		return rv;
	}
}
