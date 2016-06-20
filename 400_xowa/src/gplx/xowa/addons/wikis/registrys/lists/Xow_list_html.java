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
package gplx.xowa.addons.wikis.registrys.lists; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.registrys.*;
import gplx.xowa.specials.*; import gplx.langs.mustaches.*; import gplx.xowa.wikis.pages.*; import gplx.xowa.wikis.pages.tags.*;
import gplx.xowa.wikis.xwikis.*;
import gplx.xowa.users.data.*; 
class Xow_list_html extends Xow_special_wtr__base {
	@Override protected Io_url Get_addon_dir(Xoa_app app)			{return app.Fsys_mgr().Http_root().GenSubDir_nest("bin", "any", "xowa", "addon", "wiki", "registry", "list");}
	@Override protected Io_url Get_mustache_fil(Io_url addon_dir)	{return addon_dir.GenSubFil_nest("bin", "xow_list.mustache.html");}
	@Override protected Mustache_doc_itm Bld_mustache_root(Xoa_app app) {
		// make list_mgr based on site_wikis
		List_adp list = List_adp_.New();
		app.User().User_db_mgr().Init_site_mgr();
		Xoud_site_row[] site_ary = app.User().User_db_mgr().Site_mgr().Get_all();
		int len = site_ary.length;
		for (int i = 0; i < len; ++i) {
			Xoud_site_row site_itm = site_ary[i];
			if (String_.Eq(site_itm.Domain(), gplx.xowa.wikis.domains.Xow_domain_itm_.Str__home)) continue;
			list.Add(new Xow_list_doc_wiki(Bry_.new_u8(site_itm.Domain()), site_itm.Date()));
		}
		return new Xow_list_doc(Get_root_url(), (Xow_list_doc_wiki[])list.To_ary_and_clear(Xow_list_doc_wiki.class));
	}
	@Override protected void Bld_tags(Xoa_app app, Io_url addon_dir, Xopage_html_data page_data) {
		Xopg_tag_mgr head_tags = page_data.Head_tags();
		Xopg_tag_wtr_.Add__xocss	(head_tags, app.Fsys_mgr().Http_root());
		Xopg_tag_wtr_.Add__xohelp	(head_tags, app.Fsys_mgr().Http_root());
		head_tags.Add(Xopg_tag_itm.New_css_file(addon_dir.GenSubFil_nest("bin", "xow_list.css")));
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
