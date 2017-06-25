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
import gplx.core.brys.*; import gplx.core.primitives.*; import gplx.core.brys.fmtrs.*; import gplx.core.ios.*;
import gplx.xowa.apps.*; import gplx.xowa.apps.fsys.*; import gplx.xowa.apps.cfgs.*; import gplx.xowa.apps.urls.*; 
import gplx.xowa.langs.*; import gplx.xowa.langs.msgs.*; import gplx.xowa.langs.cases.*;
import gplx.xowa.wikis.*; import gplx.xowa.wikis.domains.*; import gplx.xowa.wikis.xwikis.*; import gplx.xowa.wikis.nss.*; import gplx.xowa.wikis.pages.*; import gplx.xowa.wikis.metas.*; import gplx.xowa.wikis.data.site_stats.*; import gplx.xowa.wikis.ttls.*; import gplx.xowa.wikis.data.*; import gplx.xowa.wikis.data.tbls.*; import gplx.xowa.wikis.caches.*; import gplx.xowa.wikis.fsys.*;
import gplx.xowa.users.*; import gplx.xowa.htmls.*; import gplx.xowa.users.history.*; import gplx.xowa.specials.*; import gplx.xowa.xtns.*; import gplx.xowa.wikis.dbs.*;
import gplx.xowa.files.*; import gplx.xowa.files.repos.*; import gplx.xowa.files.origs.*; import gplx.xowa.files.bins.*; import gplx.fsdb.*; import gplx.fsdb.meta.*; import gplx.xowa.files.exts.*;		
import gplx.xowa.htmls.heads.*; import gplx.xowa.htmls.core.htmls.utls.*; import gplx.xowa.htmls.core.hzips.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.ns_files.*; import gplx.xowa.htmls.bridges.dbuis.tbls.*;	import gplx.xowa.htmls.hrefs.*;
import gplx.xowa.bldrs.xmls.*; import gplx.xowa.bldrs.installs.*; import gplx.xowa.bldrs.setups.maints.*;
import gplx.xowa.parsers.*; import gplx.xowa.parsers.utils.*;
import gplx.xowa.addons.wikis.ctgs.*;
import gplx.xowa.guis.cbks.*; import gplx.xowa.guis.views.*;
import gplx.xowa.xtns.gallery.*; import gplx.xowa.xtns.pfuncs.*; 
import gplx.xowa.wikis.tdbs.*; import gplx.xowa.wikis.tdbs.hives.*;
import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.htmls.css.mgrs.*; import gplx.xowa.addons.wikis.ctgs.htmls.pageboxs.*; import gplx.xowa.addons.wikis.ctgs.htmls.catpages.*;
public class Xowe_wiki implements Xow_wiki, Gfo_invk, Gfo_evt_itm {
	private boolean init_in_process = false;
	public Xowe_wiki(Xoae_app app, Xol_lang_itm lang, Xow_ns_mgr ns_mgr, Xow_domain_itm domain_itm, Io_url wiki_dir) {
		this.ev_mgr = new Gfo_evt_mgr(this);
		this.app = app; this.lang = lang; this.ns_mgr = ns_mgr;

		this.domain_itm = domain_itm; 
		this.domain_bry = domain_itm.Domain_bry();
		this.domain_str = domain_itm.Domain_str();
		this.domain_tid = domain_itm.Domain_type_id();
		this.domain_abrv = Xow_abrv_wm_.To_abrv(domain_itm);

		this.fsys_mgr = new Xow_fsys_mgr(wiki_dir, app.Fsys_mgr().File_dir().GenSubDir(domain_str));

		this.url__parser = new Xow_url_parser(this);
		this.xwiki_mgr = new Xow_xwiki_mgr(this);
		this.html__hdump_mgr = new Xow_hdump_mgr(this);
		this.html_mgr = new Xow_html_mgr(this);
		this.page_mgr = new Xowe_page_mgr(this);
		this.sanitizer = new Xop_sanitizer(app.Parser_amp_mgr(), app.Msg_log());

		tdb_fsys_mgr = new Xotdb_fsys_mgr(wiki_dir, ns_mgr);
		redirect_mgr = new Xop_redirect_mgr(this);
		data_mgr = new Xow_page_mgr(this);
		file_mgr = new Xow_file_mgr(this);
		parser_mgr = new Xow_parser_mgr(this);
		cfg_parser = new Xowc_parser(this);
		props.Init_by_ctor(domain_tid, domain_bry);
		props.ContentLanguage_(lang.Key_bry());
		stats = new Xowd_site_stats_mgr(this);
		Pf_func_.Reg(domain_itm, lang.Func_regy(), lang);
		special_mgr = new Xow_special_mgr(this, lang);
		sys_cfg = new Xow_sys_cfg(this);
		hive_mgr = new Xob_hive_mgr(this);
		util = new Xow_html_util(this);
		import_cfg = new Xob_import_cfg(this);
		msg_mgr = new Xow_msg_mgr(this, lang);
		eval_mgr = new Bfmtr_eval_wiki(this);
		fragment_mgr = new Xow_fragment_mgr(this);
		xtn_mgr = new Xow_xtn_mgr().Ctor_by_wiki(this);
		if (domain_tid == Xow_domain_tid_.Tid__home) {
			wdata_wiki_tid	= Xow_domain_tid_.Tid__wikipedia;
			wdata_wiki_lang = Xol_lang_itm_.Key_en;
		}
		else {
			wdata_wiki_tid	= domain_tid;
			wdata_wiki_lang = domain_itm.Lang_orig_key();	// NOTE: must use orig_key for nowiki; "no" not "nb" DATE:2014-10-07
		}
		Wdata_wiki_abrv_();
		db_mgr = new gplx.xowa.wikis.dbs.Xodb_mgr_txt(this, data_mgr);
		maint_mgr = new Xow_maint_mgr(this);
		cache_mgr = new Xow_cache_mgr(this);
		/*if (Bry_.Eq(domain_bry, Xow_domain_itm_.Bry__home))*/ xwiki_mgr.Add_by_atrs(domain_bry, domain_bry);	// add full name to xwiki_mgr; needed for lookup in home ns; EX: [[home:Help/Contents]]
		this.lnki_bldr = new Xoh_lnki_bldr(app, href_wtr);
		this.ctg_catpage_mgr = new Xoctg_catpage_mgr(this);
	}
	public Gfo_evt_mgr				Evt_mgr() {return ev_mgr;} private final    Gfo_evt_mgr ev_mgr;
	public Xow_ns_mgr				Ns_mgr() {return ns_mgr;} private final    Xow_ns_mgr ns_mgr;
	public Xoa_ttl					Ttl_parse(byte[] ttl)								{return Xoa_ttl.Parse(this, ttl);}
	public Xoa_ttl					Ttl_parse(byte[] src, int src_bgn, int src_end)		{return Xoa_ttl.Parse(this, src, src_bgn, src_end);}
	public Xoa_ttl					Ttl_parse(int ns_id, byte[] ttl)					{return Xoa_ttl.Parse(this, ns_id, ttl);}
	public boolean						Type_is_edit() {return Bool_.Y;}
	public Xoa_app					App() {return app;}
	public Xol_lang_itm				Lang() {return lang;} private Xol_lang_itm lang;
	public Xol_case_mgr				Case_mgr() {return lang.Case_mgr();}
	public byte[]					Domain_bry() {return domain_bry;} private final    byte[] domain_bry; 
	public String					Domain_str() {return domain_str;} private final    String domain_str;
	public int						Domain_tid() {return domain_tid;} private final    int domain_tid;
	public byte[]					Domain_abrv() {return domain_abrv;} private final    byte[] domain_abrv;
	public Xow_domain_itm			Domain_itm() {return domain_itm;} private final    Xow_domain_itm domain_itm;
	public Xow_fsys_mgr				Fsys_mgr() {return fsys_mgr;} private final    Xow_fsys_mgr fsys_mgr;
	public Xow_db_mgr				Data__core_mgr() {if (db_mgr == null) return null; return db_mgr.Tid() == Xodb_mgr_txt.Tid_txt ? null : this.Db_mgr_as_sql().Core_data_mgr();}	// TEST:
	public Xof_fsdb_mode			File__fsdb_mode() {return file_mgr.Fsdb_mode();}
	public Fsdb_db_mgr				File__fsdb_core() {return file_mgr.Db_core();}
	public Xow_repo_mgr				File__repo_mgr() {return file_mgr.Repo_mgr();}
	public Xof_orig_mgr				File__orig_mgr() {return file_mgr.Orig_mgr();}
	public Xof_bin_mgr				File__bin_mgr() {return file_mgr.Fsdb_mgr().Bin_mgr();}
	public Fsm_mnt_mgr				File__mnt_mgr() {return file_mgr.Fsdb_mgr().Mnt_mgr();}
	public boolean						Html__hdump_enabled() {return html_mgr__hdump_enabled;}	private boolean html_mgr__hdump_enabled = Bool_.N;
	public Xoh_page_wtr_mgr			Html__wtr_mgr() {return html_mgr.Page_wtr_mgr();}
	public Xoh_lnki_bldr			Html__lnki_bldr() {return lnki_bldr;}  private final    Xoh_lnki_bldr lnki_bldr;
	public Xoh_href_wtr				Html__href_wtr() {return href_wtr;} private final    Xoh_href_wtr href_wtr = new Xoh_href_wtr();
	public Xoctg_pagebox_wtr		Ctg__pagebox_wtr() {return ctg_pagebox_wtr;} private final    Xoctg_pagebox_wtr ctg_pagebox_wtr = new Xoctg_pagebox_wtr();
	public Xoctg_catpage_mgr		Ctg__catpage_mgr() {return ctg_catpage_mgr;} private final    Xoctg_catpage_mgr ctg_catpage_mgr;
	public boolean						Html__css_installing() {return html__css_installing;} public void Html__css_installing_(boolean v) {html__css_installing = v;} private boolean html__css_installing;
	public Xow_url_parser			Utl__url_parser() {return url__parser;} private final    Xow_url_parser url__parser;
	public Xow_mw_parser_mgr		Mw_parser_mgr() {return mw_parser_mgr;} private final    Xow_mw_parser_mgr mw_parser_mgr = new Xow_mw_parser_mgr();
	public Xow_xwiki_mgr			Xwiki_mgr() {return xwiki_mgr;} private final    Xow_xwiki_mgr xwiki_mgr;
	public Xow_wiki_props			Props() {return props;} private final    Xow_wiki_props props = new Xow_wiki_props();
	public Xowd_site_stats_mgr		Stats() {return stats;} private final    Xowd_site_stats_mgr stats;
	public Xow_parser_mgr			Parser_mgr() {return parser_mgr;} private final    Xow_parser_mgr parser_mgr;
	public Xoax_addon_mgr			Addon_mgr() {return addon_mgr;} private final    Xoax_addon_mgr addon_mgr = new Xoax_addon_mgr();
	public Xog_cbk_mgr				Gui__cbk_mgr() {return gui__cbk_mgr;} private final    Xog_cbk_mgr gui__cbk_mgr = new Xog_cbk_mgr();
	public Xowe_page_mgr			Page_mgr() {return page_mgr;} private final    Xowe_page_mgr page_mgr;
	public Xop_sanitizer			Sanitizer() {return sanitizer;} private final    Xop_sanitizer sanitizer;
	public byte[]					Wtxt__expand_tmpl(byte[] src) {return parser_mgr.Main().Expand_tmpl(src);}


	public Xow_hdump_mgr			Html__hdump_mgr() {return html__hdump_mgr;} private final    Xow_hdump_mgr html__hdump_mgr;
	public Xoae_app					Appe() {return app;} private Xoae_app app;
	public Xow_user					User() {return user;} private Xow_user user = new Xow_user();
	public Xow_page_mgr				Data_mgr() {return data_mgr;} private Xow_page_mgr data_mgr;
	public Xodb_mgr					Db_mgr() {return db_mgr;} private Xodb_mgr db_mgr;
	public Xodb_mgr_sql				Db_mgr_as_sql() {return (Xodb_mgr_sql)db_mgr;}
	public Xow_special_mgr			Special_mgr() {return special_mgr;} private Xow_special_mgr special_mgr;
	public Xow_html_mgr				Html_mgr() {return html_mgr;} private Xow_html_mgr html_mgr;
	public Xow_xtn_mgr				Xtn_mgr() {return xtn_mgr;} private Xow_xtn_mgr xtn_mgr;
	public Xow_cache_mgr			Cache_mgr() {return cache_mgr;} private Xow_cache_mgr cache_mgr;

	public byte[]					Commons_wiki_key() {return commons_wiki_key;} private byte[] commons_wiki_key = Xow_domain_itm_.Bry__commons;
	public Xob_hive_mgr				Hive_mgr() {return hive_mgr;} private Xob_hive_mgr hive_mgr;
	public Xow_msg_mgr				Msg_mgr() {return msg_mgr;} private final    Xow_msg_mgr msg_mgr;
	public Xow_fragment_mgr			Fragment_mgr() {return fragment_mgr;} private Xow_fragment_mgr fragment_mgr;
	public Bfmtr_eval_wiki			Eval_mgr() {return eval_mgr;} private Bfmtr_eval_wiki eval_mgr;
	public Bry_bfr_mkr				Utl__bfr_mkr()		{return utl__bry_bfr_mkr;}	private final    Bry_bfr_mkr utl__bry_bfr_mkr = new Bry_bfr_mkr();
	public Io_stream_zip_mgr		Utl__zip_mgr()		{return utl__zip_mgr;}		private final    Io_stream_zip_mgr utl__zip_mgr = new Io_stream_zip_mgr();
	public int						Wdata_wiki_tid() {return wdata_wiki_tid;} private int wdata_wiki_tid;
	public byte[]					Wdata_wiki_lang() {return wdata_wiki_lang;} private byte[] wdata_wiki_lang;
	public void						Wdata_wiki_lang_(byte[] v) {this.wdata_wiki_lang = v; Wdata_wiki_abrv_();}	// TEST:
	public byte[]					Wdata_wiki_abrv() {return wdata_wiki_abrv;} private byte[] wdata_wiki_abrv;
	private void Wdata_wiki_abrv_() {
		Bry_bfr bfr = utl__bry_bfr_mkr.Get_b128();
		Xow_abrv_wm_.To_abrv(bfr, wdata_wiki_lang, Int_obj_ref.New(wdata_wiki_tid));
		wdata_wiki_abrv = bfr.To_bry_and_rls();
	}
	private Xow_html_util util;
	public boolean Init_needed() {return init_needed;} public Xowe_wiki Init_needed_(boolean v) {init_needed = v; return this;} private boolean init_needed = true;
	public Xop_redirect_mgr Redirect_mgr() {return redirect_mgr;} private Xop_redirect_mgr redirect_mgr;
	public List_adp Rls_list() {if (rls_list == null) rls_list = List_adp_.New(); return rls_list;} private List_adp rls_list;
	public Xow_file_mgr			File_mgr() {return file_mgr;} private Xow_file_mgr file_mgr;
	public Xob_import_cfg		Import_cfg() {return import_cfg;} private Xob_import_cfg import_cfg;
	public Xotdb_fsys_mgr		Tdb_fsys_mgr() {return tdb_fsys_mgr;} private final    Xotdb_fsys_mgr tdb_fsys_mgr;
	public Xou_history_cfg		Cfg_history() {return cfg_history;} private Xou_history_cfg cfg_history = new Xou_history_cfg();
	public Xoh_file_page_wtr	Cfg_file_page() {return cfg_file_page;} private Xoh_file_page_wtr cfg_file_page = new Xoh_file_page_wtr();
	public Xow_sys_cfg			Sys_cfg() {return sys_cfg;} private Xow_sys_cfg sys_cfg;
	public Xowc_parser			Cfg_parser() {return cfg_parser;} private Xowc_parser cfg_parser;
	public boolean					Cfg_parser_lnki_xwiki_repos_enabled() {return cfg_parser_lnki_xwiki_repos_enabled;} public Xowe_wiki Cfg_parser_lnki_xwiki_repos_enabled_(boolean v) {cfg_parser_lnki_xwiki_repos_enabled = v; return this;} private boolean cfg_parser_lnki_xwiki_repos_enabled;
	public Xow_maint_mgr		Maint_mgr() {return maint_mgr;} private Xow_maint_mgr maint_mgr;
	public void Clear_for_tests() {	// NOTE: these are structures that cache items for PERF; need to be cleared out for multiple test runs
		file_mgr.Dbmeta_mgr().Clear();
		db_mgr.Load_mgr().Clear();
	}
	public Xodb_mgr_sql				Db_mgr_create_as_sql() {Xodb_mgr_sql rv = new Xodb_mgr_sql(this); db_mgr = rv; return rv;}
	public void	Init_by_wiki__force() {init_needed = true; Init_by_wiki();}
	public void	Init_by_wiki__force_and_mark_inited() {
		init_needed = true;
		Init_by_wiki();
		init_needed = false;
	}
	public void Init_by_wiki()		{Init_assert();}
	public Xowe_wiki Init_assert()	{if (init_needed) Init_wiki(app.Usere()); return this;}
	public void Init_db_mgr() {
		Io_url core_db_url = gplx.xowa.wikis.data.Xow_db_file__core_.Find_core_fil_or_null(this);
		if (core_db_url == null) {
			tdb_fsys_mgr.Scan_dirs();
		}
		else {
			Xodb_mgr_sql db_mgr_sql = this.Db_mgr_create_as_sql();
			Xow_db_mgr.Init_by_load(this, core_db_url);
			file_mgr.Init_file_mgr_by_load(this);
			db_mgr_sql.Core_data_mgr().Tbl__page().Flds__assert();	// NOTE: must go above html_mgr.Init_by_wiki b/c Page_load will be done via messages

			// FOLDER.RENAME: handle renamed folders; EX:"/wiki/en.wikipedia.org-2016-12" DATE:2017-02-01
			try {
				byte[] cfg_domain_bry = db_mgr_sql.Core_data_mgr().Db__core().Tbl__cfg().Select_bry_or("xowa.bldr.session", "wiki_domain", domain_bry);	// NOTE: or is "wiki.domain" for user_wikis
				if (!Bry_.Eq(cfg_domain_bry, domain_bry)) {
					// set wikidata vars
					Xow_domain_itm cfg_domain_itm = Xow_domain_itm_.parse(cfg_domain_bry);
					this.wdata_wiki_tid	= cfg_domain_itm.Domain_type_id();
					this.wdata_wiki_lang = cfg_domain_itm.Lang_orig_key();			
					Bry_bfr bfr = Bry_bfr_.New();
					Xow_abrv_wm_.To_abrv(bfr, wdata_wiki_lang, Int_obj_ref.New(wdata_wiki_tid));
					this.wdata_wiki_abrv = bfr.To_bry_and_clear();

					// set lang; handles "German Wikipedia" for "de.wikipedia.org"
					this.lang = app.Lang_mgr().Get_by_or_load(cfg_domain_itm.Lang_actl_key());
					this.msg_mgr.Lang_(lang);
				}
			} catch (Exception e) {
				Gfo_usr_dlg_.Instance.Warn_many("", "", "db.init: failed to get domain from config; err=~{0}", Err_.Message_gplx_log(e));
			}
		}
	}
	private void Init_wiki(Xoue_user user) {	// NOTE: (a) one-time initialization for all wikis; (b) not called by tests
		if (init_in_process) {
			app.Usr_dlg().Log_many("", "", "wiki.init: circular call canceled: ~{0}", domain_str);
			return;	// NOTE: may be called multiple times due to "if (app.Stage() == Xoa_stage_.Tid_launch) init_needed = false;"; TODO_OLD: call this only once; DATE:2014-06-07
		}
		init_in_process = true;
		if (app.Stage() == Xoa_stage_.Tid_launch) init_needed = false;	// NOTE: only mark inited if app fully launched; otherwise statements in xowa.gfs can fire and prematurely set home to inited; DATE:2013-03-24
		Gfo_log_bfr log_bfr = app.Log_bfr(); log_bfr.Add("wiki.init.bgn: " + domain_str);
		
		app.Gfs_mgr().Run_url_for(this, tdb_fsys_mgr.Cfg_wiki_stats_fil());
		Init_db_mgr();
		if (!app.Bldr().Import_marker().Chk(this)) {app.Wiki_mgr().Del(domain_bry); init_needed = false; return;}	// NOTE: must call after Db_mgr_create_as_sql(); also, must delete wiki from mgr; DATE:2014-08-24
		db_mgr.Load_mgr().Load_init(this);
		app.Gfs_mgr().Run_url_for(this, tdb_fsys_mgr.Cfg_wiki_core_fil());
		parser_mgr.Init_by_wiki();

		// init ns_mgr
		if (lang.Init_by_load()) {
			if (domain_tid == Xow_domain_tid_.Tid__wikipedia)	// NOTE: if type is wikipedia, add "Wikipedia" as an alias; PAGE:en.w:pt.wikipedia.org/wiki/Página principal which redirects to Wikipedia:Página principal
				ns_mgr.Aliases_add(Xow_ns_.Tid__project, Xow_ns_.Alias__wikipedia);
			special_mgr.Evt_lang_changed(lang);
		}
		app.Gfs_mgr().Run_url_for(this, app.Fsys_mgr().Cfg_wiki_core_dir().GenSubFil(domain_str + ".gfs"));		// NOTE: must be run after lang.Init_by_load b/c lang will reload ns_mgr; DATE:2015-04-17run cfg for wiki by user ; EX: /xowa/user/anonymous/wiki/en.wikipedia.org/cfg/user_wiki.gfs
		cfg_parser.Xtns().Itm_pages().Init(ns_mgr);	// init ns_mgr for Page / Index ns just before rebuild; usually set by #cfg file
		Xow_ns_mgr_.rebuild_(lang, ns_mgr);	// always rebuild; may be changed by user_wiki.gfs; different lang will change namespaces; EX: de.wikisource.org will have Seite for File and none of {{#lst}} will work

		// push lang changes
		fragment_mgr.Evt_lang_changed(lang);
		parser_mgr.Main().Init_by_lang(lang);
		html_mgr.Init_by_lang(lang);

		// other init
		Bry_fmtr.Null.Eval_mgr().Enabled_(false); app.Wiki_mgr().Scripts().Exec(this); Bry_fmtr.Null.Eval_mgr().Enabled_(true);
		app.Html__css_installer().Install(this, Xowd_css_core_mgr.Key_default);
		html_mgr.Init_by_wiki(this);
		html__hdump_mgr.Init_by_db(this);
		Xow_repo_mgr_.Assert_repos(app, this);
		xtn_mgr.Init_by_wiki(this);
		log_bfr.Add("wiki.init.end");
		app.Log_wtr().Log_to_session_direct(log_bfr.Xto_str());
		init_in_process = false;
//			app.Api_root().Wikis().Get(domain_bry).Subscribe(this);
		app.Site_cfg_mgr().Load(this);
		app.Addon_mgr().Load_by_wiki(this);
		ctg_pagebox_wtr.Init_by_wiki(this);
		ctg_catpage_mgr.Init_by_wiki(this);

		file_mgr.Init_by_wiki(this);
		page_mgr.Init_by_wiki(this);

		app.Cfg().Bind_many_wiki(this, this, Cfg__variant__current);
	}
	public void Rls() {
		if (rls_list != null) {
			int len = rls_list.Count();
			for (int i = 0; i < len; i++) {
				Rls_able rls = (Rls_able)rls_list.Get_at(i);
				rls.Rls();
			}
		}
		Xow_db_mgr core_db_mgr = this.Data__core_mgr();
		if (core_db_mgr != null) core_db_mgr.Rls();
		file_mgr.Rls();
	}
	public void Init_needed_y_() {this.init_needed = true;}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_files))				return file_mgr;
		else if	(ctx.Match(k, Invk_stats))				return stats;
		else if	(ctx.Match(k, Invk_props))				return props;
		else if	(ctx.Match(k, Invk_commons_wiki_))		commons_wiki_key = m.ReadBry("v");
		else if	(ctx.Match(k, Invk_lang))				return lang;
		else if	(ctx.Match(k, Invk_lang_))				throw Err_.new_deprecated("wiki.lang_");
		else if	(ctx.Match(k, Invk_html))				return html_mgr;
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
		else if	(ctx.Match(k, Invk_maint))				return maint_mgr;
		else if	(ctx.Match(k, Invk_domain))				return domain_str;
		else if	(ctx.Match(k, Invk_xtns))				return xtn_mgr;
		else if	(ctx.Match(k, Invk_catpage_mgr))		return ctg_catpage_mgr;
		else if	(ctx.Match(k, Invk_hdump_enabled_))		this.html_mgr__hdump_enabled = m.ReadYn("v");

		else if (ctx.Match(k, Cfg__variant__current))	lang.Vnt_mgr().Cur_itm_(m.ReadBry("v"));
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}
	public static final String
	  Invk_files = "files", Invk_commons_wiki_ = "commons_wiki_", Invk_stats = "stats"
	, Invk_lang = "lang", Invk_html = "html", Invk_gui = "gui", Invk_cfg_history = "cfg_history", Invk_user = "user", Invk_data_mgr = "data_mgr", Invk_sys_cfg = "sys_cfg", Invk_ns_mgr = "ns_mgr"
	, Invk_special = "special"
	, Invk_props = "props", Invk_parser = "parser"
	, Invk_msgs = "msgs", Invk_app = "app", Invk_util = "util"
	, Invk_xtns = "xtns"
	, Invk_db_mgr_to_sql_ = "db_mgr_to_sql_"
	, Invk_domain = "domain", Invk_maint = "maint", Invk_hdump_enabled_ = "hdump_enabled_"
	, Invk_catpage_mgr = "catpage_mgr"
	;
	public static final String Cfg__variant__current = "xowa.wiki.lang.variant.current";
	public static final String	Invk_db_mgr = "db_mgr";	// SERIALIZED:000.sqlite3|xowa_cfg
	public static final String Invk_lang_ = "lang_";
}
