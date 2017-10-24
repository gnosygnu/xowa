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
package gplx.xowa.users; import gplx.*; import gplx.xowa.*;
import gplx.xowa.langs.*; import gplx.xowa.langs.cases.*; import gplx.xowa.wikis.*;
import gplx.xowa.wikis.nss.*;
import gplx.xowa.wikis.domains.*;
class Xou_user_ {
	public static Xowe_wiki new_or_create_(Xoue_user user, Xoae_app app) {
		Io_url wiki_dir = app.Fsys_mgr().Home_wiki_dir();
		Xol_lang_itm lang = app.Lang_mgr().Get_by_or_new(Xol_lang_itm_.Key_en);
		lang.Init_by_load();	// NOTE: lang.Load() must occur before Xowe_wiki.new() b/c wiki will create parsers based on lang
		Xowe_wiki rv = new Xowe_wiki(app, lang, ns_home_(lang.Case_mgr()), Xow_domain_uid_.To_domain(Xow_domain_uid_.Tid_xowa), wiki_dir);
		app.Wiki_mgr().Add(rv);
		rv.Sys_cfg().Xowa_cmd_enabled_(true);
		rv.Sys_cfg().Xowa_proto_enabled_(true);
		return rv;
	}
	public static void User_system_cfg_make(Gfo_usr_dlg usr_dlg, Io_url cfg_fil) {
		usr_dlg.Log_many(GRP_KEY, "user_system_cfg.create", "creating user_system_cfg.gfs: ~{0}", cfg_fil.Raw());
		Io_mgr.Instance.SaveFilStr(cfg_fil, User_system_cfg_text);
	}
	public static void Bookmarks_make(Xoae_app app, Xowe_wiki home_wiki) {
		app.Usr_dlg().Log_many(GRP_KEY, "bookmarks.create", "creating bookmarks page");
		home_wiki.Db_mgr().Save_mgr().Data_create(Xoa_ttl.Parse(home_wiki, Bry_.new_a7("Data:Bookmarks")), Bry_.new_a7(Bookmarks_text));
	}
	public static final    String User_system_cfg_text = String_.Concat_lines_nl
	( "app.scripts.txns.get('user.prefs.general').version_('" + Xoa_app_.Version + "').bgn();"
	, "app.files.download.enabled_('y');"	// default to true; DATE:2015-01-05
	, "app.files.math.enabled_('y');"
	, "app.files.math.renderer_('mathjax');"
	, "app.scripts.txns.get('user.prefs.general').end();\n"
	);
	public static final    String Bookmarks_text = String_.Concat_lines_nl
	( "Bookmarks are added automatically to the bottom of the page. All other text is not modified."
	, ""
	, "Please delete bookmarks by editing this page."
	);
	private static Xow_ns_mgr ns_home_(Xol_case_mgr case_mgr) {
		Xow_ns_mgr rv = new Xow_ns_mgr(case_mgr);
		rv = rv.Add_new(-2, "Media").Add_new(-1, "Special").Add_new(0, "").Add_new(1, "Talk").Add_new(2, "User").Add_new(3, "User talk").Add_new(4, "Wikipedia").Add_new(5, "Wikipedia talk")
			.Add_new(6, "File").Add_new(7, "File talk").Add_new(8, "MediaWiki").Add_new(9, "MediaWiki talk").Add_new(10, "Template").Add_new(11, "Template talk")
			.Add_new(12, "Help").Add_new(13, "Help talk").Add_new(14, "Category").Add_new(15, "Category talk").Add_new(100, "Portal").Add_new(101, "Portal talk")
			.Add_new(gplx.xowa.xtns.wbases.Wdata_wiki_mgr.Ns_property, gplx.xowa.xtns.wbases.Wdata_wiki_mgr.Ns_property_name)
			.Add_new(730, "Data").Add_new(731, "Data talk")
			.Add_new(Xow_ns_.Tid__module, Xow_ns_.Key__module).Add_new(Xow_ns_.Tid__module_talk, Xow_ns_.Key__module_talk)
			.Add_defaults()
			;
		rv.Init();
		return rv;
	}
	static final String GRP_KEY = "xowa.user_";
}
