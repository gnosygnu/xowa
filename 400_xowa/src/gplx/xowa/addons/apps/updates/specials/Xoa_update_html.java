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
		return Load(app);
	}
	private static Mustache_doc_itm Load(Xoa_app app) {
		Io_url update_db_url = app.Fsys_mgr().Root_dir().GenSubFil_nest("user", "app", "update", "xoa_update.sqlite3");

		// get from internet
		boolean web_access_enabled = gplx.core.ios.IoEngine_system.Web_access_enabled;
		if (web_access_enabled) {
			// check text file to see if version changed
			Io_url trg_summary_fil = update_db_url.OwnerDir().GenSubFil("xoa_update.txt");
			int trg_summary_version = Int_.parse_or(Io_mgr.Instance.LoadFilStr(trg_summary_fil), -1);
			// String src_summary_server = "http://xowa.org";
			String src_summary_server = "http://localhost/xowa.org";
			int src_summary_version = Bry_.To_int(Io_mgr.Instance.DownloadFil_args("", Io_url_.Empty).Exec_as_bry(src_summary_server + "/admin/app_update/xoa_update.txt"));

			// download database
			if (src_summary_version > trg_summary_version) {
				String src_details_url = src_summary_server + "/admin/app_update/xoa_update.sqlite3";
				Io_url trg_details_url = update_db_url;
				Io_mgr.Instance.DownloadFil(src_details_url, trg_details_url);
				Io_mgr.Instance.SaveFilStr(trg_summary_fil, Int_.To_str(src_summary_version));
			}
		}

		// load from db
		Xoa_update_db_mgr db_mgr = new Xoa_update_db_mgr(update_db_url);
		String check_date = app.User().User_db_mgr().Cfg().Get_app_str_or("app.updater.last_check", null);
		if (check_date == null) check_date = Datetime_now.Get().XtoStr_fmt_yyyy_MM_dd_HH_mm_ss();
		Xoa_app_version_itm[] db_itms = db_mgr.Tbl__app_version().Select_by_date(Xoa_app_.Build_date);
		String build_date = String_.Mid(Xoa_app_.Build_date, 0, String_.FindFwd(Xoa_app_.Build_date, " "));
		if (db_itms.length == 0) return new Xoa_update_itm__root(Xoa_app_.Version, build_date, "", check_date, web_access_enabled, "", "", Xoa_app_version_itm.Priority__trivial, "", "");

		// convert to gui itm
		Xoa_app_version_itm db_itm = db_itms[0];
		Xoa_update_itm__root root = new Xoa_update_itm__root(Xoa_app_.Version, build_date, db_itm.Package_url(), check_date, web_access_enabled, db_itm.Name(), db_itm.Date(), db_itm.Priority(), db_itm.Summary(), db_itm.Details());
		root.Itms_(To_gui_itm(db_itms));
		return root;
	}
	private static Xoa_update_itm__leaf[] To_gui_itm(Xoa_app_version_itm[] db_itms) {
		int len = db_itms.length;
		Xoa_update_itm__leaf[] rv = new Xoa_update_itm__leaf[len];
		for (int i = 0; i < len; i++) {
			rv[i] = To_gui_itm(db_itms[i]);
		}
		return rv;
	}
	private static Xoa_update_itm__leaf To_gui_itm(Xoa_app_version_itm db_itm) {
		return new Xoa_update_itm__leaf(db_itm.Name(), db_itm.Date(), db_itm.Priority(), db_itm.Summary(), db_itm.Details());
	}

	@Override protected void Bld_tags(Xoa_app app, Io_url addon_dir, Xopage_html_data page_data) {
		Xopg_tag_mgr head_tags = page_data.Head_tags();
		Xopg_tag_wtr_.Add__core				(head_tags, app.Fsys_mgr().Http_root());
		Xopg_tag_wtr_.Add__xocss			(head_tags, app.Fsys_mgr().Http_root());
		Xopg_tag_wtr_.Add__xohelp			(head_tags, app.Fsys_mgr().Http_root());
		Xopg_tag_wtr_.Add__xolog			(head_tags, app.Fsys_mgr().Http_root());
		Xopg_tag_wtr_.Add__gui__progbars	(head_tags, app.Fsys_mgr().Http_root());
		Xopg_tag_wtr_.Add__xoajax			(head_tags, app.Fsys_mgr().Http_root(), app);
		Xopg_alertify_.Add_tags				(head_tags, app.Fsys_mgr().Http_root());

		head_tags.Add(Xopg_tag_itm.New_css_file(addon_dir.GenSubFil_nest("bin", "xoa_update.css")));
		head_tags.Add(Xopg_tag_itm.New_js_file(addon_dir.GenSubFil_nest("bin", "xoa_update.js")));
		head_tags.Add(Xopg_tag_itm.New_js_file(addon_dir.GenSubFil_nest("bin", "xobc.util.js")));
	}
}
