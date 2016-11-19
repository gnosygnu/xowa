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
package gplx.xowa.addons.apps.updates.specials; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.apps.*; import gplx.xowa.addons.apps.updates.*;
import gplx.xowa.addons.apps.updates.dbs.*;
import gplx.xowa.specials.*; import gplx.langs.mustaches.*; import gplx.xowa.wikis.pages.*; import gplx.xowa.wikis.pages.tags.*;
class Xoa_update_html extends Xow_special_wtr__base {
	@Override protected Io_url Get_addon_dir(Xoa_app app)			{return app.Fsys_mgr().Http_root().GenSubDir_nest("bin", "any", "xowa", "addon", "app", "update");}
	@Override protected Io_url Get_mustache_fil(Io_url addon_dir)	{return addon_dir.GenSubFil_nest("bin", "xoa_update.mustache.html");}
	@Override protected Mustache_doc_itm Bld_mustache_root(Xoa_app app) {
		Io_url update_db_url = Get_addon_dir(app).GenSubFil("xowa_update_info.sqlite3");
		Xoa_update_db_mgr db_mgr = new Xoa_update_db_mgr(update_db_url);
		return Load(db_mgr);
	}
	private static Mustache_doc_itm Load(Xoa_update_db_mgr db_mgr) {
		// get from internet
		if (gplx.core.ios.IoEngine_system.Web_access_enabled) {
			Io_url trg_url = db_mgr.Url();
			String src_url = "http://xowa.org/admin/app_update/xowa_update_info.sqlite3";
			Io_mgr.Instance.DownloadFil(src_url, trg_url);
		}

		// load from db
		Xoa_app_version_itm[] db_itms = db_mgr.Tbl__app_version().Select_by_date(Xoa_app_.Build_date);
		if (db_itms.length == 0) return new Xoa_update_itm("up-to-date", "", "", "", "", "");

		// convert to gui itm
		Xoa_update_itm head = To_gui_itm(db_itms[0], Find_major(db_itms));
		head.Itms_(To_gui_itm(db_itms));
		return head;
	}
	private static String Find_major(Xoa_app_version_itm[] db_itms) {
		int len = db_itms.length;
		Xoa_app_version_itm major = null;
		for (int i = 0; i < len; i++) {
			Xoa_app_version_itm db_itm = db_itms[i];
			if (db_itm.Priority() >= Xoa_app_version_itm.Priority__major)
				major = db_itm;
		}
		return major == null ? "" : String_.Format("({0}) {1}", major.Priority_str(), major.Id());
	}
	private static Xoa_update_itm[] To_gui_itm(Xoa_app_version_itm[] db_itms) {
		int len = db_itms.length;
		Xoa_update_itm[] rv = new Xoa_update_itm[len];
		for (int i = 0; i < len; i++) {
			rv[i] = To_gui_itm(db_itms[i], "");
		}
		return rv;
	}
	private static Xoa_update_itm To_gui_itm(Xoa_app_version_itm db_itm, String major) {
		return new Xoa_update_itm(db_itm.Id(), db_itm.Date(), db_itm.Priority_str(), db_itm.Summary(), db_itm.Details(), major);
	}

	@Override protected void Bld_tags(Xoa_app app, Io_url addon_dir, Xopage_html_data page_data) {
		Xopg_tag_mgr head_tags = page_data.Head_tags();
		Xopg_tag_wtr_.Add__xocss	(head_tags, app.Fsys_mgr().Http_root());
		Xopg_tag_wtr_.Add__xohelp	(head_tags, app.Fsys_mgr().Http_root());
		Xopg_tag_wtr_.Add__xolog	(head_tags, app.Fsys_mgr().Http_root());
		Xopg_tag_wtr_.Add__xoajax	(head_tags, app.Fsys_mgr().Http_root(), app);

		head_tags.Add(Xopg_tag_itm.New_css_file(addon_dir.GenSubFil_nest("bin", "xoa_update.css")));
		head_tags.Add(Xopg_tag_itm.New_js_file(addon_dir.GenSubFil_nest("bin", "xoa_update.js")));
	}
}
