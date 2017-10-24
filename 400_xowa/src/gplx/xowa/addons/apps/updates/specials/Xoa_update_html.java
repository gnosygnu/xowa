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
package gplx.xowa.addons.apps.updates.specials; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.apps.*; import gplx.xowa.addons.apps.updates.*;
import gplx.xowa.addons.apps.updates.dbs.*;
import gplx.xowa.specials.*; import gplx.langs.mustaches.*; import gplx.xowa.wikis.pages.*; import gplx.xowa.wikis.pages.tags.*;
public class Xoa_update_html extends Xow_special_wtr__base {
	@Override protected Io_url Get_addon_dir(Xoa_app app)			{return app.Fsys_mgr().Http_root().GenSubDir_nest("bin", "any", "xowa", "addon", "app", "update");}
	@Override protected Io_url Get_mustache_fil(Io_url addon_dir)	{return addon_dir.GenSubFil_nest("bin", "xoa_update.mustache.html");}
	@Override protected Mustache_doc_itm Bld_mustache_root(Xoa_app app) {
		return Load(app);
	}
	private static Mustache_doc_itm Load(Xoa_app app) {
		Io_url db_url = Xoa_update_db_mgr_.Url(app);

		// get from internet
		boolean web_access_enabled = gplx.core.ios.IoEngine_system.Web_access_enabled;
		Xoa_update_db_mgr_.Download_from_inet(app, Bool_.N, db_url);

		// load from db			
		Xoa_app_version_itm[] db_itms = Xoa_update_db_mgr_.Select(db_url, Xoa_update_startup.Version_cutoff(app));

		// build root
		String check_date = app.Cfg().Get_str_app_or("xowa.app.update.startup.inet_date", null);	// CFG:Cfg__
		if (check_date == null) check_date = Datetime_now.Get().XtoStr_fmt_yyyy_MM_dd_HH_mm_ss();
		String build_date = String_.Mid(Xoa_app_.Build_date, 0, String_.FindFwd(Xoa_app_.Build_date, " "));	// remove time to show date only
		if (db_itms.length == 0) return new Xoa_update_itm__root(Xoa_app_.Version, build_date, check_date, web_access_enabled, "", "", Xoa_app_version_itm.Priority__trivial, "", "", "");

		// convert to gui itm
		Xoa_app_version_itm db_itm = db_itms[0];
		Xoa_update_itm__root root = new Xoa_update_itm__root(Xoa_app_.Version, build_date, check_date, web_access_enabled, db_itm.Name(), db_itm.Date(), db_itm.Priority(), db_itm.Summary(), db_itm.Details(), db_itm.Package_url());
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
		return new Xoa_update_itm__leaf(db_itm.Name(), db_itm.Date(), db_itm.Priority(), db_itm.Summary(), db_itm.Details(), db_itm.Package_url());
	}
	@Override protected void Bld_tags(Xoa_app app, Io_url addon_dir, Xopage_html_data page_data) {
		Xopg_tag_mgr head_tags = page_data.Head_tags();
		Xopg_tag_wtr_.Add__baselib			(head_tags, app.Fsys_mgr().Http_root());
		Xopg_tag_wtr_.Add__xocss			(head_tags, app.Fsys_mgr().Http_root());
		Xopg_tag_wtr_.Add__xohelp			(head_tags, app.Fsys_mgr().Http_root());
		Xopg_tag_wtr_.Add__xolog			(head_tags, app.Fsys_mgr().Http_root());
		Xopg_tag_wtr_.Add__gui__progbars	(head_tags, app.Fsys_mgr().Http_root());
		Xopg_tag_wtr_.Add__xoajax			(head_tags, app.Fsys_mgr().Http_root(), app);
		Xopg_tag_wtr_.Add__jquery			(head_tags, app.Fsys_mgr().Http_root());
		Xopg_tag_wtr_.Add__xonotify			(head_tags, app.Fsys_mgr().Http_root());
		Xopg_alertify_.Add_tags				(head_tags, app.Fsys_mgr().Http_root());

		head_tags.Add(Xopg_tag_itm.New_css_file(addon_dir.GenSubFil_nest("bin", "xoa_update.css")));
		head_tags.Add(Xopg_tag_itm.New_js_file(addon_dir.GenSubFil_nest("bin", "xoa_update.js")));
		head_tags.Add(Xopg_tag_itm.New_js_file(addon_dir.GenSubFil_nest("bin", "xobc.util.js")));
	}
}
