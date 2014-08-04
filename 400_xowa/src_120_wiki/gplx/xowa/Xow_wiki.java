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
package gplx.xowa; import gplx.*;
import gplx.xowa.apps.*;
import gplx.xowa.wikis.*; import gplx.xowa.users.*; import gplx.xowa.html.*; import gplx.xowa.users.history.*; import gplx.xowa.specials.*; import gplx.xowa.xtns.*; import gplx.xowa.dbs.*; import gplx.xowa.files.*;
import gplx.xowa.langs.vnts.*; import gplx.xowa.gui.views.*; import gplx.xowa.wikis.xwikis.*;
import gplx.xowa.setup.maints.*; import gplx.xowa.wikis.caches.*;
import gplx.xowa.bldrs.imports.*;  import gplx.xowa.xtns.pfuncs.*;
public class Xow_wiki implements GfoInvkAble {
	private Xow_html_util util;
	public Xow_wiki(Xoa_app app, Io_url wiki_dir, Xow_ns_mgr ns_mgr, Xol_lang lang) {
		this.app = app; this.ns_mgr = ns_mgr; this.lang = lang;
		domain_str = wiki_dir.NameOnly(); domain_bry = Bry_.new_utf8_(domain_str);			
		Xow_wiki_domain domain = Xow_wiki_domain_.parse_by_domain(domain_bry);
		domain_tid = domain.Tid();
		fsys_mgr = new Xow_fsys_mgr(this, wiki_dir);
		redirect_mgr = new Xop_redirect_mgr(this);
		data_mgr = new Xow_data_mgr(this);
		file_mgr = new Xow_file_mgr(this);
		utl_mgr = new Xow_utl_mgr(this);
		parser = Xop_parser.new_wiki_(this);
		cfg_parser = new Xowc_parser(this);
		ctx = Xop_ctx.new_(this);
		props.SiteName_(domain_tid).ServerName_(domain_bry);
		props.ContentLanguage_(lang.Key_bry());
		Pf_func_.Reg(lang.Func_regy(), lang);
		special_mgr = new Xows_mgr(this, lang);
		stats = new Xow_wiki_stats(this);
		xwiki_mgr = new Xow_xwiki_mgr(this);
		xwiki_mgr.Add_full(domain_bry, domain_bry);	// add full name to xwiki_mgr; needed for lookup in home ns; EX: [[en.wikipedia.org:Earth]]
		html_mgr = new Xow_html_mgr(this);
		sys_cfg = new Xow_sys_cfg(this);
		hive_mgr = new Xob_hive_mgr(this);
		util = new Xow_html_util(this);
		cfg_wiki_core = new Xow_cfg_wiki_core(this);
		import_cfg = new Xob_import_cfg(this);
		msg_mgr = new Xow_msg_mgr(this, lang);
		eval_mgr = new Bfmtr_eval_wiki(this);
		fragment_mgr = new Xow_fragment_mgr(this);
		xtn_mgr = new Xow_xtn_mgr().Ctor_by_wiki(this);
		if (domain_tid == Xow_wiki_domain_.Tid_home) {
			wdata_wiki_tid	= Xow_wiki_domain_.Tid_wikipedia;
			wdata_wiki_lang = Xol_lang_.Key_en;
		}
		else {
			wdata_wiki_tid	= domain_tid;
			wdata_wiki_lang = lang.Key_bry();
		}
		Wdata_wiki_abrv_();
		db_mgr = new gplx.xowa.dbs.Xodb_mgr_txt(this, data_mgr);
		domain_abrv = Xob_bz2_file.Build_alias(Xow_wiki_domain_.parse_by_domain(domain_bry));
		maint_mgr = new Xow_maint_mgr(this);
		cache_mgr = new Xow_cache_mgr(this);
	}
	public Xoa_app				App() {return app;} private Xoa_app app;
	public byte[]				Domain_bry() {return domain_bry;} private byte[] domain_bry; 
	public String				Domain_str() {return domain_str;} private String domain_str;
	public byte					Domain_tid() {return domain_tid;} private byte domain_tid;
	public byte[]				Domain_abrv() {return domain_abrv;} private byte[] domain_abrv;
	public Xol_lang				Lang() {return lang;} private Xol_lang lang;
	public Xow_fsys_mgr			Fsys_mgr() {return fsys_mgr;} private Xow_fsys_mgr fsys_mgr;
	public Xow_utl_mgr			Utl_mgr() {return utl_mgr;} private Xow_utl_mgr utl_mgr;
	public Xow_ns_mgr			Ns_mgr() {return ns_mgr;}  public void Ns_mgr_(Xow_ns_mgr v) {ns_mgr = v;} private Xow_ns_mgr ns_mgr;
	public Xow_gui_mgr			Gui_mgr() {return gui_mgr;} private Xow_gui_mgr gui_mgr = new Xow_gui_mgr();
	public Xow_user				User() {return user;} private Xow_user user = new Xow_user();
	public Xow_data_mgr			Data_mgr() {return data_mgr;} private Xow_data_mgr data_mgr;
	public Xodb_mgr				Db_mgr() {return db_mgr;} private Xodb_mgr db_mgr;
	public Xodb_mgr_sql			Db_mgr_as_sql() {return (Xodb_mgr_sql)db_mgr;}
	public Xows_mgr				Special_mgr() {return special_mgr;} private Xows_mgr special_mgr;
	public Xow_html_mgr			Html_mgr() {return html_mgr;} private Xow_html_mgr html_mgr;
	public Xow_xtn_mgr			Xtn_mgr() {return xtn_mgr;} private Xow_xtn_mgr xtn_mgr;
	public Xow_cache_mgr		Cache_mgr() {return cache_mgr;} private Xow_cache_mgr cache_mgr;

	public byte[] Commons_wiki_key() {return commons_wiki_key;} private byte[] commons_wiki_key = Xow_wiki_.Domain_commons_bry;
	public Xob_hive_mgr Hive_mgr() {return hive_mgr;} private Xob_hive_mgr hive_mgr;
	public Xow_msg_mgr Msg_mgr() {return msg_mgr;} private Xow_msg_mgr msg_mgr;
	public Xow_fragment_mgr Fragment_mgr() {return fragment_mgr;} private Xow_fragment_mgr fragment_mgr;
	public Bfmtr_eval_wiki Eval_mgr() {return eval_mgr;} private Bfmtr_eval_wiki eval_mgr;
	public Bry_bfr_mkr			Utl_bry_bfr_mkr() {return app.Utl_bry_bfr_mkr();}
//		public byte					Wdata_wiki_tid() {return wdata_wiki_tid;} 
	public byte[]				Wdata_wiki_lang() {return wdata_wiki_lang;} private byte[] wdata_wiki_lang;
	public void					Wdata_wiki_lang_(byte[] v) {this.wdata_wiki_lang = v; Wdata_wiki_abrv_();}	// TEST:
	public byte[]				Wdata_wiki_abrv() {return wdata_wiki_abrv;} private byte[] wdata_wiki_abrv; private byte wdata_wiki_tid;
	private void Wdata_wiki_abrv_() {
		Bry_bfr bfr = app.Utl_bry_bfr_mkr().Get_b128();
		Xob_bz2_file.Build_alias_by_lang_tid(bfr, wdata_wiki_lang, Byte_obj_ref.new_(wdata_wiki_tid));
		wdata_wiki_abrv = bfr.Mkr_rls().XtoAryAndClear();
	}

	public boolean Init_needed() {return init_needed;} public Xow_wiki Init_needed_(boolean v) {init_needed = v; return this;} private boolean init_needed = true;

	public Xop_parser Parser() {return parser;} private Xop_parser parser;
	public Xop_redirect_mgr Redirect_mgr() {return redirect_mgr;} private Xop_redirect_mgr redirect_mgr;
	public Xop_ctx Ctx() {return ctx;} private Xop_ctx ctx;
	public ListAdp Rls_list() {if (rls_list == null) rls_list = ListAdp_.new_(); return rls_list;} private ListAdp rls_list;

	public Xow_xwiki_mgr Xwiki_mgr() {return xwiki_mgr;} private Xow_xwiki_mgr xwiki_mgr;
	public Xow_file_mgr File_mgr() {return file_mgr;} private Xow_file_mgr file_mgr;
	public Xow_cfg_wiki_core Cfg_wiki_core() {return cfg_wiki_core;} private Xow_cfg_wiki_core cfg_wiki_core;
	public Xob_import_cfg		Import_cfg() {return import_cfg;} private Xob_import_cfg import_cfg;

	public Xow_wiki_stats Stats() {return stats;} private Xow_wiki_stats stats;
	public Xow_wiki_props Props() {return props;} private Xow_wiki_props props = new Xow_wiki_props();
	public Xou_history_cfg Cfg_history() {return cfg_history;} private Xou_history_cfg cfg_history = new Xou_history_cfg();
	public Xoh_cfg_gallery Cfg_gallery() {return cfg_gallery;} private Xoh_cfg_gallery cfg_gallery = new Xoh_cfg_gallery();
	public Xoh_file_page Cfg_file_page() {return cfg_file_page;} private Xoh_file_page cfg_file_page = new Xoh_file_page();
	public Xow_sys_cfg Sys_cfg() {return sys_cfg;} private Xow_sys_cfg sys_cfg;
	public Xowc_parser Cfg_parser() {return cfg_parser;} private Xowc_parser cfg_parser;
	public boolean Cfg_parser_lnki_xwiki_repos_enabled() {return cfg_parser_lnki_xwiki_repos_enabled;} public Xow_wiki Cfg_parser_lnki_xwiki_repos_enabled_(boolean v) {cfg_parser_lnki_xwiki_repos_enabled = v; return this;} private boolean cfg_parser_lnki_xwiki_repos_enabled;
	public Xoi_dump_mgr Import_mgr() {return import_mgr;} private Xoi_dump_mgr import_mgr = new Xoi_dump_mgr();
	public Xow_maint_mgr Maint_mgr() {return maint_mgr;} private Xow_maint_mgr maint_mgr;
	public void Clear_for_tests() {	// NOTE: these are structures that cache items for PERF; need to be cleared out for multiple test runs
		file_mgr.Meta_mgr().Clear();
		db_mgr.Load_mgr().Clear();
	}
	public Xoa_page GetPageByTtl(Xoa_url url, Xoa_ttl ttl)					{return GetPageByTtl(url, ttl, lang, app.Gui_mgr().Browser_win().Active_tab());}
	public Xoa_page GetPageByTtl(Xoa_url url, Xoa_ttl ttl, Xog_tab_itm tab) {return GetPageByTtl(url, ttl, lang, tab);}
	private Xoa_page GetPageByTtl(Xoa_url url, Xoa_ttl ttl, Xol_lang lang, Xog_tab_itm tab) {
		if (init_needed) Init_wiki(app.User());
		Xoa_page page = data_mgr.Get_page(url, ttl, false, false);				// get page from data_mgr
		if (page.Missing()) {													// page doesn't exist
			if (ttl.Ns().Id_file()) {
				Xow_wiki commons_wiki = app.Wiki_mgr().Get_by_key_or_null(commons_wiki_key);
				if (commons_wiki != null
					&& !Bry_.Eq(domain_bry, commons_wiki.Domain_bry())) // if file, check commons wiki; note that !Bry_.Eq is recursion guard
					return commons_wiki.GetPageByTtl(url, ttl, this.lang, tab);
			}
			else
				return page.Missing_();
		}
		if (page.Missing()) return page;									// NOTE: commons can return null page
		page.Tab_(tab);
		page.Lang_(lang);
		gplx.xowa.xtns.scribunto.Scrib_core.Core_page_changed(page);		// notify scribunto about page changed
		ParsePage(page, false);	// NOTE: do not clear page b/c reused for search
		return page;
	}
	public void ParsePage_root(Xoa_page page, boolean clear) {
		ParsePage(page, clear);
	}
	public void ParsePage(Xoa_page page, boolean clear) {
		if (init_needed && !Env_.Mode_testing()) Init_wiki(app.User());
		ctx.Cur_page_(page);
		Xop_root_tkn root = ctx.Tkn_mkr().Root(page.Data_raw());
		if (clear) {page.Clear();}
		Xoa_ttl ttl = page.Ttl();
		if (Xow_page_tid.Identify(domain_tid, ttl.Ns().Id(), ttl.Page_db()) == Xow_page_tid.Tid_wikitext)	// only parse page if wikitext; skip .js, .css, Module; DATE:2013-11-10
			parser.Parse_text_to_wdom(root, ctx, app.Tkn_mkr(), page.Data_raw(), Xop_parser_.Doc_bgn_bos);
		page.Root_(root);
		root.Data_htm_(root.Root_src());
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_files))				return file_mgr;
		else if	(ctx.Match(k, Invk_xwikis))				return xwiki_mgr;
		else if	(ctx.Match(k, Invk_stats))				return stats;
		else if	(ctx.Match(k, Invk_props))				return props;
		else if	(ctx.Match(k, Invk_cfg_gallery_))		return cfg_gallery;
		else if	(ctx.Match(k, Invk_commons_wiki_))		commons_wiki_key = m.ReadBry("v");
		else if	(ctx.Match(k, Invk_lang))				return lang;
		else if	(ctx.Match(k, Invk_lang_))				lang = app.Lang_mgr().Get_by_key_or_new(m.ReadBry("v"));
		else if	(ctx.Match(k, Invk_html))				return html_mgr;
		else if	(ctx.Match(k, Invk_gui))				return gui_mgr;
		else if	(ctx.Match(k, Invk_cfg_history))		return cfg_history;
		else if	(ctx.Match(k, Invk_user))				return user;
		else if	(ctx.Match(k, Invk_data_mgr))			return data_mgr;
		else if	(ctx.Match(k, Invk_ns_mgr))				return ns_mgr;
		else if (ctx.Match(k, Invk_sys_cfg))			return sys_cfg;
		else if (ctx.Match(k, Invk_special))			return special_mgr;
		else if (ctx.Match(k, Invk_parser))				return cfg_parser;
		else if	(ctx.Match(k, Invk_msgs))				return msg_mgr;
		else if	(ctx.Match(k, Invk_util))				return util;
		else if	(ctx.Match(k, Invk_app))				return app;
		else if	(ctx.Match(k, Invk_data_storage_format_))db_mgr.Data_storage_format_(m.ReadByte("v"));
		else if	(ctx.Match(k, Invk_db_mgr))				return db_mgr;
		else if	(ctx.Match(k, Invk_db_mgr_to_sql_))		this.Db_mgr_create_as_sql();
		else if	(ctx.Match(k, Invk_import_mgr))			return import_mgr;
		else if	(ctx.Match(k, Invk_maint))				return maint_mgr;
		else if	(ctx.Match(k, Invk_domain))				return domain_str;
		else if	(ctx.Match(k, Invk_xtns))				return xtn_mgr;
		else	return GfoInvkAble_.Rv_unhandled;
		return this;
	}
	public static final String
	  Invk_ZipDirs = "zip_dirs_", Invk_files = "files", Invk_xwikis = "xwikis", Invk_cfg_gallery_ = "cfg_gallery_", Invk_commons_wiki_ = "commons_wiki_", Invk_stats = "stats"
	, Invk_lang = "lang", Invk_lang_ = "lang_", Invk_html = "html", Invk_gui = "gui", Invk_cfg_history = "cfg_history", Invk_user = "user", Invk_data_mgr = "data_mgr", Invk_sys_cfg = "sys_cfg", Invk_ns_mgr = "ns_mgr"
	, Invk_special = "special"
	, Invk_props = "props", Invk_parser = "parser"
	, Invk_msgs = "msgs", Invk_app = "app", Invk_util = "util"
	, Invk_xtns = "xtns", Invk_data_storage_format_ = "data_storage_format_", Invk_import_mgr = "import"
	, Invk_db_mgr = "db_mgr", Invk_db_mgr_to_sql_ = "db_mgr_to_sql_"
	, Invk_domain = "domain", Invk_maint = "maint"
	;
	public Xodb_mgr_sql Db_mgr_create_as_sql() {Xodb_mgr_sql rv = new Xodb_mgr_sql(this); db_mgr = rv; return rv;}
	public Xow_wiki Init_assert() {if (init_needed) Init_wiki(app.User()); return this;}
	private boolean init_in_process = false;
	private void Init_wiki(Xou_user user) {	// NOTE: (a) one-time initialization for all wikis; (b) not called by tests
		if (init_in_process) {
			app.Usr_dlg().Log_many("", "", "wiki.init: circular call canceled: ~{0}", domain_str);
			return;	// NOTE: may be called multiple times due to "if (app.Stage() == Xoa_stage_.Tid_launch) init_needed = false;"; TODO: call this only once; DATE:2014-06-07
		}
		init_in_process = true;
		if (app.Stage() == Xoa_stage_.Tid_launch) init_needed = false;	// NOTE: only mark inited if app fully launched; otherwise statements in xowa.gfs can fire and prematurely set home to inited; DATE:2013-03-24
		Gfo_log_bfr log_bfr = app.Log_bfr();
		log_bfr.Add("wiki.init.bgn: " + domain_str);
		app.Cfg_mgr().Init(this);
		file_mgr.Cfg_download().Enabled_(app.File_mgr().Download_mgr().Enabled());	// default download to app global; can be overriden below
		Io_url sqlite_url = Xodb_mgr_sql.Find_core_url(this);
		if (sqlite_url != null) {
			Xodb_mgr_sql db_mgr_sql = this.Db_mgr_create_as_sql();
			db_mgr_sql.Init_load(gplx.dbs.Db_connect_.sqlite_(sqlite_url));
		}
		db_mgr.Load_mgr().Load_init(this);
		app.Gfs_mgr().Run_url_for(this, fsys_mgr.Cfg_wiki_core_fil());
		gplx.xowa.utls.upgrades.Xoa_upgrade_mgr.Check(this);
		app.Gfs_mgr().Run_url_for(this, fsys_mgr.Cfg_wiki_stats_fil());
		app.Gfs_mgr().Run_url_for(this, user.Fsys_mgr().Wiki_root_dir().GenSubFil_nest("#cfg", "system", domain_str + ".gfs"));		// run cfg for wiki by user ; EX: /xowa/user/anonymous/wiki/en.wikipedia.org/cfg/user_wiki.gfs
		fsys_mgr.Scan_dirs();
		if (lang.Init_by_load()) {
			if (domain_tid == Xow_wiki_domain_.Tid_wikipedia)	// NOTE: if type is wikipedia, add "Wikipedia" as an alias; PAGE:en.w:pt.wikipedia.org/wiki/Página principal which redirects to Wikipedia:Página principal
				ns_mgr.Aliases_add(Xow_ns_.Id_project, Xow_ns_.Ns_name_wikipedia);
		}
		cfg_parser.Xtns().Itm_pages().Init(ns_mgr);	// init ns_mgr for Page / Index ns just before rebuild; usually set by #cfg file
		Xow_ns_mgr_.rebuild_(lang, ns_mgr);	// always rebuild; may be changed by user_wiki.gfs; different lang will change namespaces; EX: de.wikisource.org will have Seite for File and none of {{#lst}} will work
		fragment_mgr.Evt_lang_changed(lang);
		parser.Init_by_lang(lang);
		html_mgr.Init_by_lang(lang);
		lang.Vnt_mgr().Init_by_wiki(this);
		Bry_fmtr.Null.Eval_mgr().Enabled_(false);
		app.Wiki_mgr().Scripts().Exec(this);
		Bry_fmtr.Null.Eval_mgr().Enabled_(true);
		app.Wiki_mgr().Css_installer().Install_assert(this, user.Fsys_mgr().Wiki_html_dir(domain_str));
		html_mgr.Init_by_wiki(this);
		this.Copy_cfg(app.User().Wiki());
		File_repos_assert(app, this);
		xtn_mgr.Init_by_wiki(this);
		log_bfr.Add("wiki.init.end");
		app.Log_wtr().Log_msg_to_session_direct(log_bfr.Xto_str());
		init_in_process = false;
	}
	public void Rls() {
		if (rls_list == null) return;
		int len = rls_list.Count();
		for (int i = 0; i < len; i++) {
			RlsAble rls = (RlsAble)rls_list.FetchAt(i);
			rls.Rls();
		}
	}
	private void Copy_cfg(Xow_wiki wiki) {html_mgr.Copy_cfg(wiki.Html_mgr());}
	private static void File_repos_assert(Xoa_app app, Xow_wiki wiki) {
		byte[] wiki_key = wiki.Domain_bry();
		Xoa_repo_mgr repo_mgr = app.File_mgr().Repo_mgr(); 
		Xof_repo_itm repo_itm = repo_mgr.Get_by(wiki_key);
		if (repo_itm == null) {
			repo_itm = new Xof_repo_itm(repo_mgr, wiki_key).Wiki_key_(wiki_key);
			repo_mgr.Add(repo_itm);
		}
		Xow_repo_mgr pair_mgr = wiki.File_mgr().Repo_mgr();
		if (pair_mgr.Repos_len() == 0) {
			Xof_repo_itm repo_src = repo_mgr.Get_by(File_repo_xowa_null);
			if (repo_src == null) {
				repo_itm = new Xof_repo_itm(repo_mgr, File_repo_xowa_null).Wiki_key_(Xow_wiki_domain_.Key_home_bry);
				repo_mgr.Add(repo_itm);
			}
			pair_mgr.Add_repo(File_repo_xowa_null, wiki_key);
		}
	}	static byte[] File_repo_xowa_null = Bry_.new_ascii_("xowa_repo_null");
}
