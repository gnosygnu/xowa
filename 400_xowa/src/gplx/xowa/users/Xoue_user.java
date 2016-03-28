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
import gplx.core.envs.*;
import gplx.dbs.*; import gplx.core.brys.fmtrs.*;
import gplx.xowa.langs.*; import gplx.xowa.langs.msgs.*;
import gplx.xowa.wikis.*; import gplx.xowa.wikis.xwikis.*; import gplx.xowa.users.history.*; import gplx.xowa.xtns.scribunto.*; import gplx.xowa.users.data.*;
import gplx.xowa.files.*; import gplx.xowa.files.caches.*;
import gplx.xowa.langs.genders.*;
public class Xoue_user implements Xou_user, GfoEvMgrOwner, GfoInvkAble {
	public Xoue_user(Xoae_app app, Io_url user_dir) {
		this.app = app; this.key = user_dir.NameOnly();
		this.ev_mgr = GfoEvMgr.new_(this);
		this.fsys_mgr = new Xou_fsys_mgr(app, this, user_dir);
		this.user_db_mgr = new Xou_db_mgr(app);
		this.history_mgr = new Xou_history_mgr(fsys_mgr.App_data_history_fil());
		this.prefs_mgr = new gplx.xowa.users.prefs.Prefs_mgr(app);
		this.cfg_mgr = new Xou_cfg(this);
		this.session_mgr = new Xou_session(this);
	}
	public GfoEvMgr					EvMgr() {return ev_mgr;} private final    GfoEvMgr ev_mgr;
	public String					Key() {return key;} private String key;
	public Xou_db_mgr				User_db_mgr()  {return user_db_mgr;} private final    Xou_db_mgr user_db_mgr;
	public Xow_wiki					Wikii() {return this.Wiki();}
	public int						Gender() {return Xol_gender_.Tid_unknown;}
	public Xoae_app					Appe() {return app;} private final    Xoae_app app;
	public Xol_lang_itm Lang() {if (lang == null) {lang = app.Lang_mgr().Get_by_or_new(app.Sys_cfg().Lang()); lang.Init_by_load();} return lang;} private Xol_lang_itm lang;		
	public void Lang_(Xol_lang_itm v) {
		lang = v;
		this.Msg_mgr().Lang_(v);
		wiki.Msg_mgr().Clear();	// clear home wiki msgs whenever lang changes; else messages cached from old lang will not be replaced; EX:Read/Edit; DATE:2014-05-26
		GfoEvMgr_.PubVal(this, Evt_lang_changed, lang);
	}
	public Xou_fsys_mgr Fsys_mgr() {return fsys_mgr;} private Xou_fsys_mgr fsys_mgr;
	public Xowe_wiki Wiki() {if (wiki == null) wiki = Xou_user_.new_or_create_(this, app); return wiki;} private Xowe_wiki wiki;
	public Xou_history_mgr History_mgr() {return history_mgr;} private Xou_history_mgr history_mgr;
	public Xou_cfg Cfg_mgr() {return cfg_mgr;} private Xou_cfg cfg_mgr;
	public Xou_session Session_mgr() {return session_mgr;} private Xou_session session_mgr;
	public gplx.xowa.users.prefs.Prefs_mgr Prefs_mgr() {return prefs_mgr;} gplx.xowa.users.prefs.Prefs_mgr prefs_mgr;
	public Xow_msg_mgr Msg_mgr() {
		if (msg_mgr == null)
			msg_mgr = new Xow_msg_mgr(this.Wiki(), this.Lang());	// NOTE: must call this.Lang() not this.lang, else nullRef exception when using "app.shell.fetch_page"; DATE:2013-04-12
		return msg_mgr;
	}	private Xow_msg_mgr msg_mgr;
	public void Init_by_app(Xoae_app app) {
		Io_url user_system_cfg = fsys_mgr.App_data_cfg_dir().GenSubFil(Xou_fsys_mgr.Name_user_system_cfg);
		if (!Io_mgr.Instance.ExistsFil(user_system_cfg)) Xou_user_.User_system_cfg_make(app.Usr_dlg(), user_system_cfg);
		user_db_mgr.Init_by_app(Bool_.N, fsys_mgr.Root_dir().OwnerDir().GenSubFil("xowa.user." + key + ".sqlite3")); // EX: /xowa/user/xowa.user.anonymous.sqlite3
		if (!Env_.Mode_testing()) {
			this.Available_from_fsys();
			// data_mgr.Init_by_app(app);
		}
	}
	public void App_term() {
		session_mgr.Window_mgr().Save_window(app.Gui_mgr().Browser_win().Win_box());
		history_mgr.Save(app);
		if (app.Gui_mgr().Browser_win().Tab_mgr().Html_load_tid__url())
			Io_mgr.Instance.DeleteDirDeep(fsys_mgr.App_temp_html_dir());
		app.File_mgr().Cache_mgr().Db_term();
	}
	public void Bookmarks_add(byte[] wiki_domain, byte[] ttl_full_txt) {
		Bry_bfr tmp_bfr = wiki.Utl__bfr_mkr().Get_k004();
		bookmarks_add_fmtr.Bld_bfr_many(tmp_bfr, wiki_domain, ttl_full_txt);
		byte[] new_entry = tmp_bfr.To_bry_and_rls();
		Xoa_ttl bookmarks_ttl = Xoa_ttl.parse(wiki, Bry_data_bookmarks);
		Xoae_page bookmarks_page = wiki.Data_mgr().Get_page(bookmarks_ttl, false);
		byte[] new_data = Bry_.Add(bookmarks_page.Data_raw(), new_entry);
		wiki.Db_mgr().Save_mgr().Data_update(bookmarks_page, new_data);
	}	private Bry_fmtr bookmarks_add_fmtr = Bry_fmtr.new_("* [[~{wiki_key}:~{page_name}]]\n", "wiki_key", "page_name"); byte[] Bry_data_bookmarks = Bry_.new_a7("Data:Bookmarks");
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {			
		if		(ctx.Match(k, Invk_available_from_bulk))		Available_from_bulk(m.ReadBry("v"));
		else if	(ctx.Match(k, Invk_available_from_fsys))		Available_from_fsys();
		else if	(ctx.Match(k, Invk_msgs))						return this.Msg_mgr();
		else if	(ctx.Match(k, Invk_lang))						return lang;
		else if	(ctx.Match(k, Invk_bookmarks_add_fmt_))			bookmarks_add_fmtr.Fmt_(m.ReadBry("v"));
		else if	(ctx.Match(k, Invk_wiki))						return this.Wiki();	// NOTE: mass parse relies on this being this.Wiki(), not wiki
		else if	(ctx.Match(k, Invk_history))					return history_mgr;
		else if	(ctx.Match(k, Invk_fsys))						return fsys_mgr;
		else if	(ctx.Match(k, Invk_prefs))						return prefs_mgr;
		else if	(ctx.Match(k, Invk_cfg))						return cfg_mgr;
		else if	(ctx.Match(k, Invk_session))					return session_mgr;
		else if	(ctx.Match(k, "name"))							return key; //throw Err_.new_unhandled(k);	// OBSOLETE: used to return key
		else return GfoInvkAble_.Rv_unhandled;
		return this;
	}
	public static final String Invk_available_from_fsys = "available_from_fsys", Invk_available_from_bulk = "available_from_bulk", Invk_bookmarks_add_fmt_ = "bookmarks_add_fmt_"
	, Invk_wiki = "wiki", Invk_history = "history", Invk_fsys = "fsys", Invk_lang = "lang", Invk_msgs = "msgs", Invk_prefs = "prefs", Invk_cfg = "cfg", Invk_session = "session";
	public static final String Key_xowa_user = "anonymous";
	public static final String Evt_lang_changed = "lang_changed";
	public void Available_from_fsys() {
		Io_url[] dirs = Io_mgr.Instance.QueryDir_args(app.Fsys_mgr().Wiki_dir()).Recur_(false).DirOnly_().ExecAsUrlAry();
		Xowe_wiki usr_wiki = Wiki();
		int dirs_len = dirs.length;
		for (int i = 0; i < dirs_len; i++) {
			Io_url dir = dirs[i];
			String name = dir.NameOnly();
			if (String_.Eq(name, gplx.xowa.bldrs.cmds.utils.Xob_core_batch_utl.Dir_dump)) continue;	// ignore "#dump"
			byte[] dir_name_as_bry = Bry_.new_u8(name);
			Xow_xwiki_itm xwiki = Available_add(usr_wiki, dir_name_as_bry);
			if (xwiki != null)			// Add_full can return null if adding invalid lang; should not apply here, but guard against null ref
				xwiki.Offline_(true);	// mark xwiki as offline; needed for available wikis sidebar; DATE:2014-09-21
			app.Setup_mgr().Maint_mgr().Wiki_mgr().Add(dir_name_as_bry);
		}
	}
	private void Available_from_bulk(byte[] raw) {
		byte[][] wikis = Bry_split_.Split(raw, Byte_ascii.Nl);
		Xowe_wiki usr_wiki = Wiki();
		int wikis_len = wikis.length;
		for (int i = 0; i < wikis_len; i++)
			Available_add(usr_wiki, wikis[i]);
	}
	private Xow_xwiki_itm Available_add(Xowe_wiki usr_wiki, byte[] wiki_name) {
		return usr_wiki.Xwiki_mgr().Add_by_atrs(wiki_name, wiki_name);
	}
}
