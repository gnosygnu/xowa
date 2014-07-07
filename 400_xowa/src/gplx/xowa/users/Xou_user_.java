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
package gplx.xowa.users; import gplx.*; import gplx.xowa.*;
import gplx.xowa.langs.cases.*; import gplx.xowa.wikis.*; import gplx.xowa.xtns.scribunto.*;
class Xou_user_ {
	public static Xow_wiki new_or_create_(Xou_user user, Xoa_app app) {
		Io_url wiki_dir = user.Fsys_mgr().Home_wiki_dir().GenSubDir_nest("wiki", Xow_wiki_domain_.Key_home_str);
		Xol_lang lang = app.Lang_mgr().Get_by_key_or_new(app.Lang_mgr().Default_lang());
		lang.Init_by_load();	// NOTE: lang.Load() must occur before new Xow_wiki b/c wiki will create parsers based on lang
		Xow_wiki rv = new Xow_wiki(app, wiki_dir, ns_home_(lang.Case_mgr()), lang);
		app.Wiki_mgr().Add(rv);
		rv.Sys_cfg().Xowa_cmd_enabled_(true);
		rv.Sys_cfg().Xowa_proto_enabled_(true);
		return rv;
	}
	public static void User_system_cfg_make(Gfo_usr_dlg usr_dlg, Io_url cfg_fil) {
		usr_dlg.Log_many(GRP_KEY, "user_system_cfg.create", "creating user_system_cfg.gfs: ~{0}", cfg_fil.Raw());
		Io_mgr._.SaveFilStr(cfg_fil, User_system_cfg_text);
	}
	public static void Bookmarks_make(Xoa_app app, Xow_wiki home_wiki) {
		app.Usr_dlg().Log_many(GRP_KEY, "bookmarks.create", "creating bookmarks page");
		home_wiki.Db_mgr().Save_mgr().Data_create(Xoa_ttl.parse_(home_wiki, Bry_.new_ascii_("Data:Bookmarks")), Bry_.new_ascii_(Bookmarks_text));
	}
	public static final String User_system_cfg_text = String_.Concat_lines_nl
		(	"app.scripts.txns.get('user.prefs.general').version_('" + Xoa_app_.Version + "').bgn();"
		,	"app.files.download.enabled_('n');"
		,	"app.files.math.enabled_('y');"
		,	"app.files.math.renderer_('mathjax');"
		,	"app.scripts.txns.get('user.prefs.general').end();\n"
		);
	public static final String Bookmarks_text = String_.Concat_lines_nl
		(	"Bookmarks are added automatically to the bottom of the page. All other text is not modified."
		,	""
		,	"Please delete bookmarks by editing this page."
		);
	private static Xow_ns_mgr ns_home_(Xol_case_mgr case_mgr) {
		Xow_ns_mgr rv = new Xow_ns_mgr(case_mgr);
		rv = rv.Add_new(-2, "Media").Add_new(-1, "Special").Add_new(0, "").Add_new(1, "Talk").Add_new(2, "User").Add_new(3, "User talk").Add_new(4, "Wikipedia").Add_new(5, "Wikipedia talk")
			.Add_new(6, "File").Add_new(7, "File talk").Add_new(8, "MediaWiki").Add_new(9, "MediaWiki talk").Add_new(10, "Template").Add_new(11, "Template talk")
			.Add_new(12, "Help").Add_new(13, "Help talk").Add_new(14, "Category").Add_new(15, "Category talk").Add_new(100, "Portal").Add_new(101, "Portal talk")
			.Add_new(gplx.xowa.xtns.wdatas.Wdata_wiki_mgr.Ns_property, gplx.xowa.xtns.wdatas.Wdata_wiki_mgr.Ns_property_name)
			.Add_new(730, "Data").Add_new(731, "Data talk")
			.Add_new(Scrib_xtn_mgr.Ns_id_module, Scrib_xtn_mgr.Ns_name_module).Add_new(Scrib_xtn_mgr.Ns_id_module_talk, Scrib_xtn_mgr.Ns_name_module_talk)
			.Add_defaults()
			;
		rv.Init();
		return rv;
	}
	static final String GRP_KEY = "xowa.user_";
}
