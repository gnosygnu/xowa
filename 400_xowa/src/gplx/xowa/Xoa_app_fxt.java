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
package gplx.xowa; import gplx.*;
import gplx.core.envs.*;
import gplx.gfui.kits.core.*;
import gplx.langs.gfs.*;
import gplx.dbs.*;
import gplx.xowa.bldrs.*;
import gplx.xowa.langs.*;
import gplx.xowa.wikis.*; import gplx.xowa.wikis.nss.*;
import gplx.xowa.apps.*; import gplx.xowa.files.exts.*;
import gplx.xowa.wikis.domains.*;
import gplx.xowa.guis.tabs.*;
public class Xoa_app_fxt {
	public static Xoae_app Make__app__edit() {
		Io_mgr.Instance.InitEngine_mem();
		Db_conn_bldr.Instance.Reg_default_mem();
		return Make__app__edit("linux", Io_url_.mem_dir_("mem/xowa/"));
	}
	public static Xoae_app Make__app__edit(String op_sys, Io_url root_dir) {
		Io_url user_dir = root_dir.GenSubDir_nest("user", "test_user");
		Gfo_usr_dlg__log_base.Instance.Log_dir_(user_dir.GenSubDir_nest("tmp", "current"));			
		Xoae_app app = new Xoae_app(Gfo_usr_dlg_.Test(), Xoa_app_mode.Itm_cmd, root_dir, root_dir.GenSubDir("wiki"), root_dir.GenSubDir("file"), user_dir, root_dir.GenSubDir_nest("user", "anonymous", "wiki"), op_sys);
		GfsCore.Instance.Clear();							// NOTE: must clear
		GfsCore.Instance.AddCmd(app, Xoae_app.Invk_app);	// NOTE: must add app to GfsCore; app.Gfs_mgr() always adds current app to GfsCore; note this causes old test to leave behind GfsCore for new test
		GfsCore.Instance.AddCmd(app, Xoae_app.Invk_xowa);	// add alias for app; DATE:2014-06-09
		return app;
	}
	public static Xoav_app Make__app__view() {
		Io_mgr.Instance.InitEngine_mem();
		Db_conn_bldr.Instance.Reg_default_mem();
		return Make__app__view("linux", Io_url_.mem_dir_("mem/xowa/"));
	}
	public static Xoav_app Make__app__view(String op_sys, Io_url root_dir) {
		Io_url user_dir = root_dir.GenSubDir_nest("user", "test_user");
		Gfo_usr_dlg__log_base.Instance.Log_dir_(user_dir.GenSubDir_nest("tmp", "current"));			
		Xoav_app rv = new Xoav_app(Gfo_usr_dlg_.Test(), Xoa_app_mode.Itm_gui, Xog_tab_mgr_.New_mem(), op_sys, root_dir, root_dir.GenSubDir("file"), root_dir.GenSubDir("css"), root_dir);
		rv.Init_by_app(user_dir);
		rv.Wiki_mgr().Add(new Xowv_wiki(rv, Xow_domain_itm_.Bry__home, user_dir));
		return rv;
	}
	public static Xowv_wiki Make__wiki__view(Xoa_app app) {return Make__wiki__view(app, "en.wikipedia.org");}
	public static Xowv_wiki Make__wiki__view(Xoa_app app, String domain_str) {
		byte[] domain_bry = Bry_.new_u8(domain_str);
		Io_url wiki_dir = app.Fsys_mgr().Wiki_dir().GenSubDir(domain_str);
		Xowv_wiki rv = new Xowv_wiki((Xoav_app)app, domain_bry, wiki_dir);
		((Xoav_wiki_mgr)app.Wiki_mgri()).Add(rv);
		return rv;
	}
	public static Xowe_wiki Make__wiki__edit__nonwmf(Xoae_app app, String key) {
		Xol_lang_itm lang = new Xol_lang_itm(app.Lang_mgr(), Xol_lang_itm_.Key_en).Kwd_mgr__strx_(true);
		Xol_lang_itm_.Lang_init(lang);
		return Make__wiki__edit(app, key, lang);
	}
	public static Xowe_wiki Make__wiki__edit(Xoae_app app) {return Make__wiki__edit(app, "en.wikipedia.org");}
	public static Xowe_wiki Make__wiki__edit(Xoae_app app, String key) {return Make__wiki__edit(app, key, app.Lang_mgr().Lang_en());}
	public static Xowe_wiki Make__wiki__edit(Xoae_app app, String key, Xol_lang_itm lang) {
		Io_url wiki_dir = app.Fsys_mgr().Wiki_dir().GenSubDir(key);
		Xowe_wiki rv = new Xowe_wiki(app, lang, Xow_ns_mgr_.default_(lang.Case_mgr()), Xow_domain_itm_.parse(Bry_.new_u8(key)), wiki_dir);
		rv.File_mgr().Dbmeta_mgr().Depth_(2);					// TEST: written for 2 depth
		rv.Props().Main_page_(Xoa_page_.Main_page_bry);		// TEST: default to Main Page (nothing tests loading Main Page from wiki.gfs)			
		rv.Ns_mgr().Ids_get_or_null(Xow_ns_.Tid__main).Subpages_enabled_(true);
		app.Wiki_mgr().Add(rv);
		return rv;
	}
	public static void repo_(Xoae_app app, Xowe_wiki wiki) {
		app.File_mgr().Repo_mgr().Set("src:wiki", "mem/wiki/repo/src/", wiki.Domain_str()).Ext_rules_(Xof_rule_grp.Grp_app_default).Dir_depth_(2);
		app.File_mgr().Repo_mgr().Set("trg:wiki", "mem/wiki/repo/trg/", wiki.Domain_str()).Ext_rules_(Xof_rule_grp.Grp_app_default).Dir_depth_(2).Primary_(true);
		wiki.File_mgr().Repo_mgr().Add_repo(Bry_.new_a7("src:wiki"), Bry_.new_a7("trg:wiki"));
		app.File_mgr().Repo_mgr().Set("src:c", "mem/wiki/repo/src/", "commons.wikimedia.org").Ext_rules_(Xof_rule_grp.Grp_app_default).Dir_depth_(2);
		app.File_mgr().Repo_mgr().Set("trg:c", "mem/wiki/repo/trg/", "commons.wikimedia.org").Ext_rules_(Xof_rule_grp.Grp_app_default).Dir_depth_(2).Primary_(true);
		wiki.File_mgr().Repo_mgr().Add_repo(Bry_.new_a7("src:c"), Bry_.new_a7("trg:c"));
	}
	public static void repo2_(Xoae_app app, Xowe_wiki wiki) {
		app.File_mgr().Repo_mgr().Set("src:wiki", "mem/http/en.wikipedia.org/"				, wiki.Domain_str()).Ext_rules_(Xof_rule_grp.Grp_app_default).Dir_depth_(2);
		app.File_mgr().Repo_mgr().Set("trg:wiki", "mem/xowa/file/en.wikipedia.org/"			, wiki.Domain_str()).Ext_rules_(Xof_rule_grp.Grp_app_default).Dir_depth_(2).Primary_(true);
		wiki.File_mgr().Repo_mgr().Add_repo(Bry_.new_a7("src:wiki"), Bry_.new_a7("trg:wiki"));
		app.File_mgr().Repo_mgr().Set("src:comm", "mem/http/commons.wikimedia.org/"			, "commons.wikimedia.org").Ext_rules_(Xof_rule_grp.Grp_app_default).Dir_depth_(2);
		app.File_mgr().Repo_mgr().Set("trg:comm", "mem/xowa/file/commons.wikimedia.org/"	, "commons.wikimedia.org").Ext_rules_(Xof_rule_grp.Grp_app_default).Dir_depth_(2).Primary_(true);
		wiki.File_mgr().Repo_mgr().Add_repo(Bry_.new_a7("src:comm"), Bry_.new_a7("trg:comm"));
	}
	public static void Init_gui(Xoae_app app, Xowe_wiki wiki) {
		app.Gui_mgr().Browser_win().Init_by_kit(Mem_kit.Instance);
		app.Gui_mgr().Browser_win().Tab_mgr().Tabs_new_init(wiki, Xoae_page.Empty);
	}
	public static Xob_bldr bldr_(Xoae_app app) {
		Xob_bldr rv = new Xob_bldr(app);
		rv.Sort_mem_len_(Io_mgr.Len_kb).Dump_fil_len_(Io_mgr.Len_kb).Make_fil_len_(Io_mgr.Len_kb);
		return rv;
	}
	public static final    Io_url Root_dir = Op_sys.Cur().Tid_is_lnx() ? Io_url_.new_dir_("/xowa/") : Io_url_.new_dir_("C:\\xowa\\");
}
