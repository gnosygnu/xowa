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
import gplx.core.envs.*;
import gplx.dbs.*; import gplx.core.brys.fmtrs.*;
import gplx.xowa.langs.*; import gplx.xowa.langs.msgs.*;
import gplx.xowa.wikis.*; import gplx.xowa.wikis.xwikis.*; import gplx.xowa.users.history.*; import gplx.xowa.xtns.scribunto.*; import gplx.xowa.users.data.*;
import gplx.xowa.files.*; import gplx.xowa.files.caches.*;
import gplx.xowa.langs.genders.*;
public class Xoue_user implements Xou_user, Gfo_evt_mgr_owner, Gfo_invk {
	public Xoue_user(Xoae_app app, Io_url user_dir) {
		this.app = app; this.key = user_dir.NameOnly();
		this.ev_mgr = new Gfo_evt_mgr(this);
		this.fsys_mgr = new Xou_fsys_mgr(user_dir);
		this.user_db_mgr = new Xou_db_mgr(app);
		this.history_mgr = new Xou_history_mgr(fsys_mgr.App_data_history_fil());
	}
	public Gfo_evt_mgr				Evt_mgr() {return ev_mgr;} private final    Gfo_evt_mgr ev_mgr;
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
		Gfo_evt_mgr_.Pub_val(this, Evt_lang_changed, lang);
	}
	public Xou_fsys_mgr Fsys_mgr() {return fsys_mgr;} private Xou_fsys_mgr fsys_mgr;
	public Xowe_wiki Wiki() {if (wiki == null) wiki = Xou_user_.new_or_create_(this, app); return wiki;} private Xowe_wiki wiki;
	public Xou_history_mgr History_mgr() {return history_mgr;} private Xou_history_mgr history_mgr;
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
		gplx.xowa.guis.views.Xog_startup_win_.Shutdown(app, app.Gui_mgr().Browser_win().Win_box());	// save window position
		history_mgr.Save(app);
		if (app.Gui_mgr().Browser_win().Tab_mgr().Page_load_mode_is_url())
			Io_mgr.Instance.DeleteDirDeep(fsys_mgr.App_temp_html_dir());
		app.File_mgr().Cache_mgr().Db_term();
	}
	public void Bookmarks_add(byte[] wiki_domain, byte[] ttl_full_txt) {
		Bry_bfr tmp_bfr = wiki.Utl__bfr_mkr().Get_k004();
		bookmarks_add_fmtr.Bld_bfr_many(tmp_bfr, wiki_domain, ttl_full_txt);
		byte[] new_entry = tmp_bfr.To_bry_and_rls();
		Xoa_ttl bookmarks_ttl = Xoa_ttl.Parse(wiki, Bry_data_bookmarks);
		Xoae_page bookmarks_page = wiki.Data_mgr().Load_page_by_ttl(bookmarks_ttl);
		byte[] new_data = Bry_.Add(bookmarks_page.Db().Text().Text_bry(), new_entry);
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
		else if	(ctx.Match(k, "name"))							return key; //throw Err_.new_unhandled(k);	// OBSOLETE: used to return key
		else return Gfo_invk_.Rv_unhandled;
		return this;
	}
	public static final String Invk_available_from_fsys = "available_from_fsys", Invk_available_from_bulk = "available_from_bulk", Invk_bookmarks_add_fmt_ = "bookmarks_add_fmt_"
	, Invk_wiki = "wiki", Invk_history = "history", Invk_fsys = "fsys", Invk_lang = "lang", Invk_msgs = "msgs";
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
