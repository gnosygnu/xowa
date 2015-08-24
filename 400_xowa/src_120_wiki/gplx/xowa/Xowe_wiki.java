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
import gplx.core.primitives.*; import gplx.xowa.apps.*; import gplx.xowa.apps.fsys.*; import gplx.xowa.files.exts.*;
import gplx.xowa.users.*; import gplx.xowa.html.*; import gplx.xowa.users.history.*; import gplx.xowa.specials.*; import gplx.xowa.xtns.*; import gplx.xowa.dbs.*;
import gplx.fsdb.*;
import gplx.xowa.wikis.*; import gplx.xowa.wikis.domains.*; import gplx.xowa.wikis.ttls.*; import gplx.xowa.wikis.data.*; import gplx.xowa.wikis.data.tbls.*;
import gplx.xowa.files.*; import gplx.xowa.files.repos.*; import gplx.xowa.files.origs.*; import gplx.xowa.files.bins.*; import gplx.fsdb.meta.*;
import gplx.xowa.langs.vnts.*; import gplx.xowa.gui.views.*; import gplx.xowa.wikis.xwikis.*;
import gplx.xowa.html.wtrs.*; import gplx.xowa.html.hzips.*; import gplx.xowa.html.hdumps.*; import gplx.xowa.html.css.*; import gplx.xowa.html.ns_files.*; import gplx.xowa.html.bridges.dbuis.tbls.*;
import gplx.xowa.setup.maints.*; import gplx.xowa.wikis.caches.*;
import gplx.xowa.bldrs.xmls.*; import gplx.xowa.xtns.pfuncs.*;
import gplx.xowa.tdbs.*;
import gplx.xowa.urls.*;
public class Xowe_wiki implements Xow_wiki, GfoInvkAble, GfoEvObj {
	public Xowe_wiki(Xoae_app app, Xol_lang lang, Xow_ns_mgr ns_mgr, Xow_domain_itm domain_itm, Io_url wiki_dir) {
		this.ev_mgr = GfoEvMgr.new_(this);
		this.app = app; this.lang = lang; this.ns_mgr = ns_mgr;
		this.domain_itm = domain_itm; this.domain_str = domain_itm.Domain_str(); this.domain_bry = domain_itm.Domain_bry(); this.domain_tid = domain_itm.Domain_type_id(); this.domain_abrv = Xow_abrv_wm_.To_abrv(domain_itm);
		fsys_mgr = new Xow_fsys_mgr(wiki_dir, app.Fsys_mgr().File_dir().GenSubDir(domain_str));
		this.url__parser = new Xoa_url_parser(this);
		xwiki_mgr = new Xow_xwiki_mgr(this, url__parser.Url_parser());
		xwiki_mgr.Add_full(domain_bry, domain_bry);	// add full name to xwiki_mgr; needed for lookup in home ns; EX: [[en.wikipedia.org:Earth]]
		this.html_mgr = new Xow_html_mgr(this);
		this.html_mgr__hdump_rdr = new Xohd_hdump_rdr(app, this);
		this.html_mgr__hdump_wtr = new Xohd_hdump_wtr(app, this);

		tdb_fsys_mgr = new Xotdb_fsys_mgr(wiki_dir, ns_mgr);
		xwiki_domain_tid = Xwiki_tid(domain_tid);
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
		sys_cfg = new Xow_sys_cfg(this);
		hive_mgr = new Xob_hive_mgr(this);
		util = new Xow_html_util(this);
		cfg_wiki_core = new Xow_cfg_wiki_core(this);
		import_cfg = new Xob_import_cfg(this);
		msg_mgr = new Xow_msg_mgr(this, lang);
		eval_mgr = new Bfmtr_eval_wiki(this);
		fragment_mgr = new Xow_fragment_mgr(this);
		xtn_mgr = new Xow_xtn_mgr().Ctor_by_wiki(this);
		if (domain_tid == Xow_domain_type_.Int__home) {
			wdata_wiki_tid	= Xow_domain_type_.Int__wikipedia;
			wdata_wiki_lang = Xol_lang_.Key_en;
		}
		else {
			wdata_wiki_tid	= domain_tid;
			wdata_wiki_lang = domain_itm.Lang_orig_key();	// NOTE: must use orig_key for nowiki; "no" not "nb" DATE:2014-10-07
		}
		Wdata_wiki_abrv_();
		db_mgr = new gplx.xowa.dbs.Xodb_mgr_txt(this, data_mgr);
		maint_mgr = new Xow_maint_mgr(this);
		cache_mgr = new Xow_cache_mgr(this);
	}
	public GfoEvMgr					EvMgr() {return ev_mgr;} private final GfoEvMgr ev_mgr;
	public Xow_ns_mgr				Ns_mgr() {return ns_mgr;} private final Xow_ns_mgr ns_mgr;
	public Xoa_ttl					Ttl_parse(byte[] ttl)				{return Xoa_ttl.parse_(this, ttl);}
	public Xoa_ttl					Ttl_parse(int ns_id, byte[] ttl)	{return Xoa_ttl.parse_(this, ns_id, ttl);}
	public boolean						Type_is_edit() {return Bool_.Y;}
	public Xoa_app					App() {return app;}
	public Xol_lang					Lang() {return lang;} private final Xol_lang lang;
	public byte[]					Domain_bry() {return domain_bry;} private final byte[] domain_bry; 
	public String					Domain_str() {return domain_str;} private final String domain_str;
	public int						Domain_tid() {return domain_tid;} private final int domain_tid;
	public byte[]					Domain_abrv() {return domain_abrv;} private final byte[] domain_abrv;
	public Xow_domain_itm				Domain_itm() {return domain_itm;} private final Xow_domain_itm domain_itm;
	public Xow_fsys_mgr				Fsys_mgr() {return fsys_mgr;} private final Xow_fsys_mgr fsys_mgr;
	public Xowd_db_mgr				Data__core_mgr() {return db_mgr.Tid() == Xodb_mgr_txt.Tid_txt ? null : this.Db_mgr_as_sql().Core_data_mgr();}	// TEST:
	public Xof_fsdb_mode			File__fsdb_mode() {return file_mgr.Fsdb_mode();}
	public Fsdb_db_mgr				File__fsdb_core() {return file_mgr.Db_core();}
	public Xow_repo_mgr				File__repo_mgr() {return file_mgr.Repo_mgr();}
	public Xof_orig_mgr				File__orig_mgr() {return file_mgr.Orig_mgr();}
	public Xof_bin_mgr				File__bin_mgr() {return file_mgr.Fsdb_mgr().Bin_mgr();}
	public Fsm_mnt_mgr				File__mnt_mgr() {return file_mgr.Fsdb_mgr().Mnt_mgr();}
	public boolean						Html__hdump_enabled() {return html_mgr__hdump_enabled;}	private boolean html_mgr__hdump_enabled = Bool_.N;
	public Xow_hzip_mgr				Html__hzip_mgr() {return html_mgr.Hzip_mgr();}
	public Xohd_hdump_rdr			Html__hdump_rdr() {return html_mgr__hdump_rdr;} private final Xohd_hdump_rdr html_mgr__hdump_rdr;
	public Xoh_page_wtr_mgr_base	Html__page_wtr_mgr() {return html_mgr.Page_wtr_mgr();}
	public boolean						Html__css_installing() {return html__css_installing;} public void Html__css_installing_(boolean v) {html__css_installing = v;} private boolean html__css_installing;
	public Xoa_url_parser			Utl__url_parser() {return url__parser;} private final Xoa_url_parser url__parser;
	public Xow_xwiki_mgr			Xwiki_mgr() {return xwiki_mgr;} private final Xow_xwiki_mgr xwiki_mgr;
	public Xow_wiki_props			Props() {return props;} private final Xow_wiki_props props = new Xow_wiki_props();

	public Xohd_hdump_wtr			Html__hdump_wtr() {return html_mgr__hdump_wtr;} private final Xohd_hdump_wtr html_mgr__hdump_wtr;
	public int						Xwiki_domain_tid() {return xwiki_domain_tid;} private int xwiki_domain_tid;
	public Xoae_app					Appe() {return app;} private Xoae_app app;
	public Xow_utl_mgr				Utl_mgr() {return utl_mgr;} private Xow_utl_mgr utl_mgr;
	public Xow_gui_mgr				Gui_mgr() {return gui_mgr;} private Xow_gui_mgr gui_mgr = new Xow_gui_mgr();
	public Xow_user					User() {return user;} private Xow_user user = new Xow_user();
	public Xow_data_mgr				Data_mgr() {return data_mgr;} private Xow_data_mgr data_mgr;
	public Xodb_mgr					Db_mgr() {return db_mgr;} private Xodb_mgr db_mgr;
	public Xodb_mgr_sql				Db_mgr_as_sql() {return (Xodb_mgr_sql)db_mgr;}
	public Xows_mgr					Special_mgr() {return special_mgr;} private Xows_mgr special_mgr;
	public Xow_html_mgr				Html_mgr() {return html_mgr;} private Xow_html_mgr html_mgr;
	public Xow_xtn_mgr				Xtn_mgr() {return xtn_mgr;} private Xow_xtn_mgr xtn_mgr;
	public Xow_cache_mgr			Cache_mgr() {return cache_mgr;} private Xow_cache_mgr cache_mgr;
	public Xow_page_mgr				Page_mgr() {return page_mgr;} private Xow_page_mgr page_mgr = new Xow_page_mgr();

	public byte[]					Commons_wiki_key() {return commons_wiki_key;} private byte[] commons_wiki_key = Xow_domain_itm_.Bry__commons;
	public Xob_hive_mgr				Hive_mgr() {return hive_mgr;} private Xob_hive_mgr hive_mgr;
	public Xow_msg_mgr				Msg_mgr() {return msg_mgr;} private Xow_msg_mgr msg_mgr;
	public Xow_fragment_mgr			Fragment_mgr() {return fragment_mgr;} private Xow_fragment_mgr fragment_mgr;
	public Bfmtr_eval_wiki			Eval_mgr() {return eval_mgr;} private Bfmtr_eval_wiki eval_mgr;
	public Bry_bfr_mkr				Utl__bfr_mkr() {return app.Utl__bfr_mkr();}
	public byte[]					Wdata_wiki_lang() {return wdata_wiki_lang;} private byte[] wdata_wiki_lang;
	public void						Wdata_wiki_lang_(byte[] v) {this.wdata_wiki_lang = v; Wdata_wiki_abrv_();}	// TEST:
	public byte[]					Wdata_wiki_abrv() {return wdata_wiki_abrv;} private byte[] wdata_wiki_abrv; private int wdata_wiki_tid;
	private void Wdata_wiki_abrv_() {
		Bry_bfr bfr = app.Utl__bfr_mkr().Get_b128();
		Xow_abrv_wm_.To_abrv(bfr, wdata_wiki_lang, Int_obj_ref.new_(wdata_wiki_tid));
		wdata_wiki_abrv = bfr.To_bry_and_rls();
	}
	private Xow_html_util util;
	public boolean Init_needed() {return init_needed;} public Xowe_wiki Init_needed_(boolean v) {init_needed = v; return this;} private boolean init_needed = true;

	public Xop_parser Parser() {return parser;} private Xop_parser parser;
	public Xop_redirect_mgr Redirect_mgr() {return redirect_mgr;} private Xop_redirect_mgr redirect_mgr;
	public Xop_ctx Ctx() {return ctx;} private Xop_ctx ctx;
	public List_adp Rls_list() {if (rls_list == null) rls_list = List_adp_.new_(); return rls_list;} private List_adp rls_list;

	public Xow_file_mgr File_mgr() {return file_mgr;} private Xow_file_mgr file_mgr;
	public Xow_cfg_wiki_core Cfg_wiki_core() {return cfg_wiki_core;} private Xow_cfg_wiki_core cfg_wiki_core;
	public Xob_import_cfg		Import_cfg() {return import_cfg;} private Xob_import_cfg import_cfg;

	public Xotdb_fsys_mgr		Tdb_fsys_mgr() {return tdb_fsys_mgr;} private final Xotdb_fsys_mgr tdb_fsys_mgr;

	public Xow_wiki_stats Stats() {return stats;} private Xow_wiki_stats stats;
	public Xou_history_cfg Cfg_history() {return cfg_history;} private Xou_history_cfg cfg_history = new Xou_history_cfg();
	public Xoh_cfg_gallery Cfg_gallery() {return cfg_gallery;} private Xoh_cfg_gallery cfg_gallery = new Xoh_cfg_gallery();
	public Xoh_file_page_wtr Cfg_file_page() {return cfg_file_page;} private Xoh_file_page_wtr cfg_file_page = new Xoh_file_page_wtr();
	public Xow_sys_cfg Sys_cfg() {return sys_cfg;} private Xow_sys_cfg sys_cfg;
	public Xowc_parser Cfg_parser() {return cfg_parser;} private Xowc_parser cfg_parser;
	public boolean Cfg_parser_lnki_xwiki_repos_enabled() {return cfg_parser_lnki_xwiki_repos_enabled;} public Xowe_wiki Cfg_parser_lnki_xwiki_repos_enabled_(boolean v) {cfg_parser_lnki_xwiki_repos_enabled = v; return this;} private boolean cfg_parser_lnki_xwiki_repos_enabled;
	public Xoi_dump_mgr Import_mgr() {return import_mgr;} private Xoi_dump_mgr import_mgr = new Xoi_dump_mgr();
	public Xow_maint_mgr Maint_mgr() {return maint_mgr;} private Xow_maint_mgr maint_mgr;
	public void Clear_for_tests() {	// NOTE: these are structures that cache items for PERF; need to be cleared out for multiple test runs
		file_mgr.Meta_mgr().Clear();
		db_mgr.Load_mgr().Clear();
	}
	public Xoae_page Load_page_by_ttl(Xoa_url url, Xoa_ttl ttl)						{return Load_page_by_ttl(url, ttl, lang, app.Gui_mgr().Browser_win().Active_tab(), true);}
	public Xoae_page Load_page_by_ttl(Xoa_url url, Xoa_ttl ttl, Xog_tab_itm tab)	{return Load_page_by_ttl(url, ttl, lang, tab, true);}
	public Xoae_page Load_page_by_ttl(Xoa_url url, Xoa_ttl ttl, Xol_lang lang, Xog_tab_itm tab, boolean parse_page) {
		if (init_needed) Init_wiki(app.Usere());
		Xoae_page page = Xoae_page.new_(this, ttl); page.Tab_data().Tab_(tab);
		data_mgr.Get_page(page, url, ttl, false, false);						// get page from data_mgr
		if (page.Missing()) {													// page doesn't exist
			if (ttl.Ns().Id_file()) {
				Xowe_wiki commons_wiki = app.Wiki_mgr().Get_by_key_or_null(commons_wiki_key);
				if (commons_wiki != null) {										// commons exists
					if (!Bry_.Eq(domain_bry, commons_wiki.Domain_bry())) {		// !Bry_.Eq is recursion guard
						Xoae_page rv = commons_wiki.Load_page_by_ttl(url, ttl, this.lang, tab, true);
						if (rv.Exists()) {
							rv.Commons_mgr().Source_wiki_(this);
							return rv;
						}
						else {
							page.Missing_(false);
							page.Commons_mgr().Xowa_mockup_(true);
							return page;
						}
					}
				}
			}
			else
				return page.Missing_();
		}
		if (page.Missing()) return page;										// NOTE: commons can return null page
		page.Tab_data().Tab_(tab);
		page.Lang_(lang);
		if (parse_page)
			ParsePage(page, false);	// NOTE: do not clear page b/c reused for search
		return page;
	}
	public void ParsePage_root(Xoae_page page, boolean clear) {ParsePage(page, clear);}
	public void ParsePage(Xoae_page page, boolean clear) {
		if (init_needed && !Env_.Mode_testing()) Init_wiki(app.Usere());
		gplx.xowa.xtns.scribunto.Scrib_core.Core_page_changed(page);		// notify scribunto about page changed
		ctx.Cur_page_(page);
		Xop_root_tkn root = ctx.Tkn_mkr().Root(page.Data_raw());
		if (clear) {page.Clear();}
		Xoa_ttl ttl = page.Ttl();
		if (Xow_page_tid.Identify(domain_tid, ttl.Ns().Id(), ttl.Page_db()) == Xow_page_tid.Tid_wikitext)	// only parse page if wikitext; skip .js, .css, Module; DATE:2013-11-10
			parser.Parse_text_to_wdom(root, ctx, app.Tkn_mkr(), page.Data_raw(), Xop_parser_.Doc_bgn_bos);
		page.Root_(root);
		root.Data_htm_(root.Root_src());
	}
	public Xodb_mgr_sql Db_mgr_create_as_sql() {Xodb_mgr_sql rv = new Xodb_mgr_sql(this); db_mgr = rv; return rv;}
	public void Init_by_wiki()		{Init_assert();}
	public Xowe_wiki Init_assert()	{if (init_needed) Init_wiki(app.Usere()); return this;}
	private boolean init_in_process = false;
	public void Init_db_mgr() {
		Io_url core_db_url = gplx.xowa.wikis.Xow_fsys_mgr.Find_core_fil(this);
		if (core_db_url == null) {
			tdb_fsys_mgr.Scan_dirs();
		}
		else {
			Xodb_mgr_sql db_mgr_sql = this.Db_mgr_create_as_sql();
			db_mgr_sql.Core_data_mgr().Init_by_load(core_db_url);
			file_mgr.Init_file_mgr_by_load(this);
		}
	}
	private void Init_wiki(Xoue_user user) {	// NOTE: (a) one-time initialization for all wikis; (b) not called by tests
		if (init_in_process) {
			app.Usr_dlg().Log_many("", "", "wiki.init: circular call canceled: ~{0}", domain_str);
			return;	// NOTE: may be called multiple times due to "if (app.Stage() == Xoa_stage_.Tid_launch) init_needed = false;"; TODO: call this only once; DATE:2014-06-07
		}
		init_in_process = true;
		if (app.Stage() == Xoa_stage_.Tid_launch) init_needed = false;	// NOTE: only mark inited if app fully launched; otherwise statements in xowa.gfs can fire and prematurely set home to inited; DATE:2013-03-24
		Gfo_log_bfr log_bfr = app.Log_bfr(); log_bfr.Add("wiki.init.bgn: " + domain_str);
		app.Cfg_mgr().Init(this);
		file_mgr.Cfg_download().Enabled_(app.File_mgr().Wmf_mgr().Enabled());	// default download to app global; can be overriden below
		app.Gfs_mgr().Run_url_for(this, tdb_fsys_mgr.Cfg_wiki_stats_fil());
		Init_db_mgr();
		if (!app.Bldr().Import_marker().Chk(this)) {app.Wiki_mgr().Del(domain_bry); init_needed = false; return;}	// NOTE: must call after Db_mgr_create_as_sql(); also, must delete wiki from mgr; DATE:2014-08-24
		db_mgr.Load_mgr().Load_init(this);
		app.Gfs_mgr().Run_url_for(this, tdb_fsys_mgr.Cfg_wiki_core_fil());
		gplx.xowa.utls.upgrades.Xoa_upgrade_mgr.Check(this);
		// init ns_mgr
		if (lang.Init_by_load()) {
			if (domain_tid == Xow_domain_type_.Int__wikipedia)	// NOTE: if type is wikipedia, add "Wikipedia" as an alias; PAGE:en.w:pt.wikipedia.org/wiki/Página principal which redirects to Wikipedia:Página principal
				ns_mgr.Aliases_add(Xow_ns_.Id_project, Xow_ns_.Ns_name_wikipedia);
		}
		app.Gfs_mgr().Run_url_for(this, app.Fsys_mgr().Cfg_wiki_core_dir().GenSubFil(domain_str + ".gfs"));		// NOTE: must be run after lang.Init_by_load b/c lang will reload ns_mgr; DATE:2015-04-17run cfg for wiki by user ; EX: /xowa/user/anonymous/wiki/en.wikipedia.org/cfg/user_wiki.gfs
		cfg_parser.Xtns().Itm_pages().Init(ns_mgr);	// init ns_mgr for Page / Index ns just before rebuild; usually set by #cfg file
		Xow_ns_mgr_.rebuild_(lang, ns_mgr);	// always rebuild; may be changed by user_wiki.gfs; different lang will change namespaces; EX: de.wikisource.org will have Seite for File and none of {{#lst}} will work
		// push lang changes
		fragment_mgr.Evt_lang_changed(lang);
		parser.Init_by_lang(lang);
		html_mgr.Init_by_lang(lang);
		lang.Vnt_mgr().Init_by_wiki(this);
		// other init
		Bry_fmtr.Null.Eval_mgr().Enabled_(false); app.Wiki_mgr().Scripts().Exec(this); Bry_fmtr.Null.Eval_mgr().Enabled_(true);
		app.Html__css_installer().Install(this, Xowd_css_core_mgr.Key_default);
		Html__hdump_enabled_(html_mgr__hdump_enabled);
		html_mgr.Init_by_wiki(this);
		this.Copy_cfg(app.Usere().Wiki());
		Xow_repo_mgr_.Assert_repos(app, this);
		xtn_mgr.Init_by_wiki(this);
		log_bfr.Add("wiki.init.end");
		app.Log_wtr().Log_to_session_direct(log_bfr.Xto_str());
		init_in_process = false;
		app.Api_root().Wikis().Get(domain_bry).Subscribe(this);
	}
	private void Html__hdump_enabled_(boolean v) {
		this.html_mgr__hdump_enabled = v;
		if (html_mgr__hdump_enabled) {
			Xowd_html_tbl.Assert_col__page_html_db_id(Db_mgr_as_sql().Core_data_mgr());	// NOTE: must go above html_mgr.Init_by_wiki b/c Page_load will be done via messages
			html_mgr__hdump_rdr.Init_by_db(this.Data__core_mgr());
			html_mgr__hdump_wtr.Init_by_db(this.Data__core_mgr());
		}
	}
	public void Rls() {
		if (rls_list == null) return;
		int len = rls_list.Count();
		for (int i = 0; i < len; i++) {
			RlsAble rls = (RlsAble)rls_list.Get_at(i);
			rls.Rls();
		}
	}
	private void Copy_cfg(Xowe_wiki wiki) {html_mgr.Copy_cfg(wiki.Html_mgr());}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_files))				return file_mgr;
		else if	(ctx.Match(k, Invk_xwikis))				return xwiki_mgr;
		else if	(ctx.Match(k, Invk_stats))				return stats;
		else if	(ctx.Match(k, Invk_props))				return props;
		else if	(ctx.Match(k, Invk_cfg_gallery_))		return cfg_gallery;
		else if	(ctx.Match(k, Invk_commons_wiki_))		commons_wiki_key = m.ReadBry("v");
		else if	(ctx.Match(k, Invk_lang))				return lang;
		else if	(ctx.Match(k, Invk_lang_))				throw Err_.new_deprecated("wiki.lang_");
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
		else if	(ctx.Match(k, Invk_db_mgr))				return db_mgr;
		else if	(ctx.Match(k, Invk_db_mgr_to_sql_))		this.Db_mgr_create_as_sql();
		else if	(ctx.Match(k, Invk_import_mgr))			return import_mgr;
		else if	(ctx.Match(k, Invk_maint))				return maint_mgr;
		else if	(ctx.Match(k, Invk_domain))				return domain_str;
		else if	(ctx.Match(k, Invk_xtns))				return xtn_mgr;
		else if	(ctx.Match(k, Invk_hdump_enabled_))		this.html_mgr__hdump_enabled = m.ReadYn("v");
		else if	(ctx.Match(k, gplx.xowa.apis.xowa.wikis.langs.Xoap_lang_variants.Evt_current_changed))	lang.Vnt_mgr().Cur_vnt_(m.ReadBry("v"));
		else	return GfoInvkAble_.Rv_unhandled;
		return this;
	}
	public static final String
	  Invk_ZipDirs = "zip_dirs_", Invk_files = "files", Invk_xwikis = "xwikis", Invk_cfg_gallery_ = "cfg_gallery_", Invk_commons_wiki_ = "commons_wiki_", Invk_stats = "stats"
	, Invk_lang = "lang", Invk_html = "html", Invk_gui = "gui", Invk_cfg_history = "cfg_history", Invk_user = "user", Invk_data_mgr = "data_mgr", Invk_sys_cfg = "sys_cfg", Invk_ns_mgr = "ns_mgr"
	, Invk_special = "special"
	, Invk_props = "props", Invk_parser = "parser"
	, Invk_msgs = "msgs", Invk_app = "app", Invk_util = "util"
	, Invk_xtns = "xtns", Invk_import_mgr = "import"
	, Invk_db_mgr_to_sql_ = "db_mgr_to_sql_"
	, Invk_domain = "domain", Invk_maint = "maint", Invk_hdump_enabled_ = "hdump_enabled_"
	;
	public static final String			// SERIALIZED
		Invk_db_mgr = "db_mgr"	// SERIALIZED:000.sqlite3|xowa_cfg
	;
	public static final String Invk_lang_ = "lang_";
	private static int Xwiki_tid(int tid) {
		switch (tid) {
			case Xow_domain_type_.Int__commons:
			case Xow_domain_type_.Int__species:
			case Xow_domain_type_.Int__incubator:
			case Xow_domain_type_.Int__mediawiki:
			case Xow_domain_type_.Int__wmfblog:
			case Xow_domain_type_.Int__home:						return Xow_domain_type_.Int__wikipedia;	// set xwiki_tid to wikipedia; allows [[da:Page]] to point to da.wikipedia.org; PAGE:species:Puccinia; DATE:2014-09-14
			default:											return tid;
		}
	}
}
