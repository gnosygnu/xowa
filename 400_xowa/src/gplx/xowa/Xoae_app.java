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
import gplx.core.brys.*; import gplx.core.btries.*; import gplx.core.brys.fmtrs.*; import gplx.core.flds.*; import gplx.core.ios.*; import gplx.core.threads.*; import gplx.langs.jsons.*; import gplx.core.primitives.*; import gplx.core.net.*; import gplx.core.log_msgs.*; import gplx.core.envs.*;
import gplx.xowa.apps.*; import gplx.xowa.apps.fsys.*; import gplx.xowa.apps.site_cfgs.*; import gplx.xowa.apps.caches.*; import gplx.xowa.apps.apis.*; import gplx.xowa.apps.metas.*; import gplx.langs.htmls.encoders.*; import gplx.xowa.apps.progs.*; import gplx.xowa.apps.gfs.*;
import gplx.xowa.langs.*; import gplx.xowa.specials.*;
import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.css.*; import gplx.xowa.bldrs.installs.*;
import gplx.xowa.files.*; import gplx.xowa.files.caches.*; import gplx.xowa.files.imgs.*;
import gplx.xowa.guis.cbks.*; import gplx.xowa.guis.tabs.*;
import gplx.xowa.wikis.*; import gplx.xowa.users.*; import gplx.xowa.guis.*; import gplx.xowa.apps.cfgs.*; import gplx.xowa.addons.wikis.ctgs.*; import gplx.xowa.addons.htmls.tocs.*; import gplx.xowa.apps.fmtrs.*; import gplx.xowa.htmls.*; import gplx.xowa.wikis.xwikis.sitelinks.*; import gplx.xowa.wikis.xwikis.parsers.*;
import gplx.xowa.htmls.hrefs.*; import gplx.xowa.htmls.core.htmls.utls.*; import gplx.xowa.htmls.ns_files.*; import gplx.xowa.htmls.bridges.*;
import gplx.xowa.parsers.*; import gplx.xowa.parsers.amps.*; import gplx.xowa.parsers.tblws.*; import gplx.xowa.parsers.xndes.*;
import gplx.xowa.xtns.*; import gplx.xowa.xtns.scribunto.*; import gplx.xowa.xtns.math.*;
import gplx.xowa.parsers.utils.*; import gplx.xowa.parsers.logs.*; import gplx.xowa.apps.servers.tcp.*; import gplx.xowa.apps.servers.http.*;
import gplx.xowa.bldrs.wms.*;
import gplx.xowa.wikis.tdbs.*; import gplx.xowa.wikis.tdbs.hives.*; import gplx.xowa.wikis.xwikis.*;
import gplx.xowa.addons.*; import gplx.xowa.specials.mgrs.*;
import gplx.xowa.addons.apps.cfgs.*; import gplx.xowa.apps.miscs.*;
public class Xoae_app implements Xoa_app, Gfo_invk {
	public Xoae_app(Gfo_usr_dlg usr_dlg, Xoa_app_mode mode, Io_url root_dir, Io_url wiki_dir, Io_url file_dir, Io_url user_dir, Io_url css_dir, String bin_dir_name) {
		Xoa_app_.Usr_dlg_(usr_dlg);
		usr_dlg.Log_wkr().Log_to_session_fmt("app.ctor.bgn");

		this.mode = mode;
		Io_url.Http_file_str_encoder = Gfo_url_encoder_.New__fsys_lnx().Make();
		fsys_mgr = new Xoa_fsys_mgr(bin_dir_name, root_dir, wiki_dir, file_dir, css_dir, root_dir);
		log_wtr = usr_dlg.Log_wkr();
		api_root = new Xoapi_root(this);

		// user
		user = new Xoue_user(this, user_dir);
		this.fsys_mgr.Url_finder().Init_by_user(user.Fsys_mgr());

		this.meta_mgr = new Xoa_meta_mgr(this);
		url_cmd_eval = new Xoa_fsys_eval(fsys_mgr, user.Fsys_mgr());			
		fsys_mgr.Init_by_app(prog_mgr);
		log_wtr.Log_dir_(user.Fsys_mgr().App_temp_dir().GenSubDir("log"));
		this.gfs_mgr = new Xoa_gfs_mgr(user.Key(), this, fsys_mgr);
		lang_mgr = new Xoa_lang_mgr(this, gfs_mgr);
		wiki_mgr = new Xoae_wiki_mgr(this);
		gui_mgr = new Xoa_gui_mgr(this);
		this.gui__tab_mgr = new Xog_tab_mgr__swt(gui_mgr);
		bldr = new Xob_bldr(this);
		file_mgr.Ctor_by_app(this);
		user_mgr = new Xou_user_mgr(this, user);
		sys_cfg = new Xoa_sys_cfg(this);
		cur_redirect = new Xoa_cur(this);
		shell = new Xoa_shell(this);
		setup_mgr = new Xoi_setup_mgr(this);
		xtn_mgr = new Xow_xtn_mgr().Ctor_by_app(this);
		hive_mgr = new Xoa_hive_mgr(this);
		tcp_server.App_ctor(this);
		fmtr_mgr = new Xoa_fmtr_mgr(this);
		log_mgr = new Xop_log_mgr(this);
		http_server = new Http_server_mgr(this);
		html_mgr = new Xoh_html_mgr(this);
		this.html__bridge_mgr = new Xoh_bridge_mgr(utl__json_parser);
		this.site_cfg_mgr = new Xoa_site_cfg_mgr(this);

		usr_dlg.Log_wkr().Log_to_session_fmt("app.ctor.end");
	}
	public boolean						Tid_is_edit()				{return Bool_.Y;}
	public Xoa_app_mode				Mode()						{return mode;} private final    Xoa_app_mode mode;
	public Xoa_fsys_mgr				Fsys_mgr()					{return fsys_mgr;} private final    Xoa_fsys_mgr fsys_mgr;
	public Xof_cache_mgr			File__cache_mgr()			{return file_mgr.Cache_mgr();}
	public Xof_img_mgr				File__img_mgr()				{return file_mgr.Img_mgr();}
	public Io_download_fmt			File__download_fmt()		{return wmf_mgr.Download_wkr().Download_xrg().Download_fmt();}
	public Xoh_href_parser			Html__href_parser()			{return html__href_parser;} private final    Xoh_href_parser html__href_parser = new Xoh_href_parser();
	public Xoa_css_extractor		Html__css_installer()		{return html__css_installer;} private final    Xoa_css_extractor html__css_installer = new Xoa_css_extractor();
	public Xoh_bridge_mgr			Html__bridge_mgr()			{return html__bridge_mgr;} private final    Xoh_bridge_mgr html__bridge_mgr;
	public Xowmf_mgr				Wmf_mgr()					{return wmf_mgr;} private final    Xowmf_mgr wmf_mgr = new Xowmf_mgr();
	public Bry_bfr_mkr				Utl__bfr_mkr()				{return utl__bry_bfr_mkr;} private final    Bry_bfr_mkr utl__bry_bfr_mkr = new Bry_bfr_mkr();
	public Json_parser				Utl__json_parser()			{return utl__json_parser;} private final    Json_parser utl__json_parser = new Json_parser();
	public Gfo_inet_conn			Utl__inet_conn()			{return inet_conn;} private final    Gfo_inet_conn inet_conn = Gfo_inet_conn_.new_();
	public Xoa_meta_mgr				Dbmeta_mgr()				{return meta_mgr;} private final    Xoa_meta_mgr meta_mgr;
	public boolean						Bldr__running()				{return bldr__running;} public void Bldr__running_(boolean v) {this.bldr__running = v;} private boolean bldr__running;
	public Xoa_parser_mgr			Parser_mgr()				{return parser_mgr;} private final    Xoa_parser_mgr parser_mgr = new Xoa_parser_mgr();
	public Xoa_site_cfg_mgr			Site_cfg_mgr()				{return site_cfg_mgr;} private final    Xoa_site_cfg_mgr site_cfg_mgr;
	public Xoa_sitelink_mgr			Xwiki_mgr__sitelink_mgr()	{return xwiki_mgr__sitelink_mgr;} private final    Xoa_sitelink_mgr xwiki_mgr__sitelink_mgr = new Xoa_sitelink_mgr();
	public boolean						Xwiki_mgr__missing(byte[] wiki_key)	{return user.Wiki().Xwiki_mgr().Get_by_key(wiki_key) == null;} // NOTE: only the user_wiki has a full list of all wikis b/c it has xwiki objects; wiki_mgr does not, b/c it has heavier wiki objects which are loaded dynamically;
	public boolean						Xwiki_mgr__exists(byte[] wiki_key)	{return user.Wiki().Xwiki_mgr().Get_by_key(wiki_key) != null;}
	public Xow_xwiki_itm_parser		Xwiki_mgr__itm_parser()		{return xwiki_mgr__itm_parser;}	private final    Xow_xwiki_itm_parser xwiki_mgr__itm_parser = new Xow_xwiki_itm_parser();
	public Xoax_addon_mgr			Addon_mgr()					{return addon_mgr;} private final    Xoax_addon_mgr addon_mgr = new Xoax_addon_mgr();
	public Xoa_special_regy			Special_regy()				{return special_regy;} private final    Xoa_special_regy special_regy = new Xoa_special_regy();
	public Xob_bldr					Bldr()						{return bldr;} private Xob_bldr bldr;
	public Xog_cbk_mgr				Gui__cbk_mgr()				{return gui__cbk_mgr;} private final    Xog_cbk_mgr gui__cbk_mgr = new Xog_cbk_mgr();
	public Xog_tab_mgr				Gui__tab_mgr()				{return gui__tab_mgr;} private final    Xog_tab_mgr gui__tab_mgr;
	public Gfo_thread_mgr			Thread_mgr()				{return thread_mgr;} private final    Gfo_thread_mgr thread_mgr = new Gfo_thread_mgr();
	public Xocfg_mgr				Cfg()						{return cfg;} private final    Xocfg_mgr cfg = new Xocfg_mgr();
	public Xoa_misc_mgr				Misc_mgr()					{return misc_mgr;} private final    Xoa_misc_mgr misc_mgr = new Xoa_misc_mgr();
	
	public Xoae_wiki_mgr		Wiki_mgr() {return wiki_mgr;} private Xoae_wiki_mgr wiki_mgr;
	public Xoa_wiki_mgr			Wiki_mgri() {return wiki_mgr;}
	public Xou_user_mgr			User_mgr() {return user_mgr;} private Xou_user_mgr user_mgr;
	public Xof_file_mgr			File_mgr() {return file_mgr;} private Xof_file_mgr file_mgr = new Xof_file_mgr();
	public Xoa_lang_mgr			Lang_mgr() {return lang_mgr;} private Xoa_lang_mgr lang_mgr;
	public Xoa_gui_mgr			Gui_mgr() {return gui_mgr;} private Xoa_gui_mgr gui_mgr;
	public Xou_user				User() {return user;}
	public Xoue_user			Usere() {return user;} private Xoue_user user;
	public Xow_xtn_mgr			Xtn_mgr() {return xtn_mgr;} private Xow_xtn_mgr xtn_mgr;
	public Xoapi_root			Api_root() {return api_root;} private Xoapi_root api_root;
	public Gfo_usr_dlg			Usr_dlg() {return Xoa_app_.Usr_dlg();}
	public Gfo_usr_dlg__log		Log_wtr() {return log_wtr;} private Gfo_usr_dlg__log log_wtr;
	public Xoa_gfs_mgr			Gfs_mgr() {return gfs_mgr;} private final    Xoa_gfs_mgr gfs_mgr;
	public Xoa_special_mgr		Special_mgr() {return special_mgr;} private Xoa_special_mgr special_mgr = new gplx.xowa.specials.Xoa_special_mgr();
	public Xoh_html_mgr			Html_mgr() {return html_mgr;} private Xoh_html_mgr html_mgr;
	public Xop_log_mgr			Log_mgr() {return log_mgr;} private Xop_log_mgr log_mgr;
	public Xoa_shell			Shell() {return shell;} private Xoa_shell shell;
	public Xoa_thread_mgr		Thread_mgr_old() {return thread_mgr_old;} private Xoa_thread_mgr thread_mgr_old = new Xoa_thread_mgr();
	public Xoa_hive_mgr			Hive_mgr() {return hive_mgr;} private Xoa_hive_mgr hive_mgr;
	public Xoa_prog_mgr			Prog_mgr() {return prog_mgr;} private final    Xoa_prog_mgr prog_mgr = new Xoa_prog_mgr();
	public Gfo_async_mgr		Async_mgr() {return async_mgr;} private Gfo_async_mgr async_mgr = new Gfo_async_mgr();

	public Xoi_setup_mgr		Setup_mgr() {return setup_mgr;} private Xoi_setup_mgr setup_mgr;
	public Gfo_msg_log			Msg_log() {return msg_log;} private Gfo_msg_log msg_log = new Gfo_msg_log(Xoa_app_.Name);
	public Gfo_msg_log			Msg_log_null() {return msg_log_null;} private Gfo_msg_log msg_log_null = new Gfo_msg_log("null_log");

	public Xoh_ns_file_page_mgr	Ns_file_page_mgr() {return ns_file_page_mgr;} private Xoh_ns_file_page_mgr ns_file_page_mgr = new Xoh_ns_file_page_mgr();		
	public Btrie_slim_mgr		Utl_trie_tblw_ws() {return utl_trie_tblw_ws;} private Btrie_slim_mgr utl_trie_tblw_ws = Xop_tblw_ws_itm.trie_();
	public Gfo_fld_rdr			Utl_fld_rdr() {return utl_fld_rdr;} Gfo_fld_rdr utl_fld_rdr = Gfo_fld_rdr.xowa_();
	public Gfo_log_bfr			Log_bfr() {return log_bfr;} private Gfo_log_bfr log_bfr = new Gfo_log_bfr();
	public Xoa_sys_cfg			Sys_cfg() {return sys_cfg;} private Xoa_sys_cfg sys_cfg;
	public Bry_fmtr				Tmp_fmtr() {return tmp_fmtr;} Bry_fmtr tmp_fmtr = Bry_fmtr.new_("");
	public Xoa_ctg_mgr			Ctg_mgr() {return ctg_mgr;} private Xoa_ctg_mgr ctg_mgr = new Xoa_ctg_mgr();
	public Xoa_fsys_eval		Url_cmd_eval() {return url_cmd_eval;} Xoa_fsys_eval url_cmd_eval;
	public Xoa_cur				Cur_redirect() {return cur_redirect;} private Xoa_cur cur_redirect;
	public Io_stream_zip_mgr	Zip_mgr() {return zip_mgr;} Io_stream_zip_mgr zip_mgr = new Io_stream_zip_mgr();
	public Xoa_cache_mgr		Cache_mgr() {return cache_mgr;} private Xoa_cache_mgr cache_mgr = new Xoa_cache_mgr();

	public Xosrv_server			Tcp_server() {return tcp_server;} private Xosrv_server tcp_server = new Xosrv_server();
	public Http_server_mgr		Http_server() {return http_server;} private Http_server_mgr http_server;
	public Xop_amp_mgr			Parser_amp_mgr() {return parser_amp_mgr;} private final    Xop_amp_mgr parser_amp_mgr = Xop_amp_mgr.Instance;

	private Xoa_fmtr_mgr fmtr_mgr;
	public Gfo_number_parser Utl_num_parser() {return utl_num_parser;} private Gfo_number_parser utl_num_parser = new Gfo_number_parser();
	public void Init_by_app() {
		stage = Xoa_stage_.Tid_init;
		user.Init_by_app(this);
		cfg.Init_by_app(this);
		user.User_db_mgr().Cache_mgr().Init_by_app(this);
		misc_mgr.Init_by_app(this);
		user.History_mgr().Init_by_app(this);
		prog_mgr.Init_by_app(this, url_cmd_eval);
		xtn_mgr.Init_by_app(this);
		gui_mgr.Init_by_app();
		html__css_installer.Init_by_app(this);
		wiki_mgr.Init_by_app();
		gplx.xowa.bldrs.setups.upgrades.Xoa_upgrade_mgr.Check(this);
		setup_mgr.Init_by_app(this);
		thread_mgr_old.Usr_dlg_(Xoa_app_.Usr_dlg());
		html_mgr.Init_by_app(this);
		api_root.Init_by_app(this);
		wmf_mgr.Init_by_app(this);
		gplx.core.net.emails.Gfo_email_mgr_.Instance = gplx.core.net.emails.Gfo_email_mgr_.New_jre();
		special_mgr.Init_by_app(this);
		sys_cfg.Init_by_app(this);
	}
	public void Launch() {
		// guard against circular calls; probably no longer needed
		if (stage == Xoa_stage_.Tid_launch) return;
		stage = Xoa_stage_.Tid_launch;

		// run app-launch actions
		gplx.xowa.apps.setups.Xoa_setup_mgr.Launch(this);

		// init "In other langs"
		xwiki_mgr__sitelink_mgr.Init_by_app();

		// init user wiki
		user.Wiki().Init_assert();	// NOTE: must assert wiki and load langs first, else will be asserted during Portal_mgr().Init(), which will cause IndexOutOfBounds; DATE:2014-10-04
		gplx.xowa.addons.wikis.directorys.Xowdir_addon.Init(this);
	}
	public byte Stage() {return stage;} public Xoae_app Stage_(byte v) {stage = v; return this;} private byte stage = Xoa_stage_.Tid_ctor;
	public boolean Term_cbk() {
		Gfo_usr_dlg usr_dlg = Xoa_app_.Usr_dlg();
		usr_dlg.Log_many("", "", "term:bgn");
		if (setup_mgr.Cmd_mgr().Working()) {
			if (!gui_mgr.Kit().Ask_yes_no("", "", "An import is in progress. Are you sure you want to exit?")) return false;
		} 
		if (!gui_mgr.Browser_win().Tab_mgr().Tabs__pub_close_all()) return false;
		gui_mgr.Browser_win().Usr_dlg().Canceled_y_();		
		user.App_term(); usr_dlg.Log_many("", "", "term:app_term");
		log_wtr.Log_term(); usr_dlg.Log_many("", "", "term:log_wtr");
		log_mgr.Rls(); usr_dlg.Log_many("", "", "term:log_mgr");
		gplx.xowa.xtns.scribunto.Scrib_core_mgr.Term_all(this);
		wiki_mgr.Rls(); usr_dlg.Log_many("", "", "term:wiki_mgr");
		return true;
	}
	public void Reset_all() {
		this.Free_mem(true);
		gplx.xowa.xtns.scribunto.Scrib_core_mgr.Term_all(this);
		System_.Garbage_collect();
	}
	public void Free_mem(boolean clear_ctx) {
		this.Utl__bfr_mkr().Clear();
		msg_log.Clear();
		wiki_mgr.Free_mem(clear_ctx);
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_gui))					return gui_mgr;
		else if	(ctx.Match(k, Invk_api))					return api_root;
		else if	(ctx.Match(k, Invk_bldr))					return bldr;
		else if	(ctx.Match(k, Invk_wikis))					return wiki_mgr;
		else if (ctx.Match(k, Invk_fsys))					return fsys_mgr;
		else if	(ctx.Match(k, Invk_files))					return file_mgr;
		else if (ctx.Match(k, Invk_langs))					return lang_mgr;
		else if (ctx.Match(k, Invk_users))					return user_mgr;
		else if (ctx.Match(k, Invk_user))					return user;
		else if (ctx.Match(k, Invk_sys_cfg))				return sys_cfg;
		else if	(ctx.Match(k, Invk_cur))					return cur_redirect;
		else if	(ctx.Match(k, Invk_html))					return html_mgr;
		else if	(ctx.Match(k, Invk_shell))					return shell;
		else if	(ctx.Match(k, Invk_log))					return log_wtr;
		else if	(ctx.Match(k, Invk_setup))					return setup_mgr;
		else if	(ctx.Match(k, Invk_scripts))				return gfs_mgr;
		else if	(ctx.MatchPriv(k, Invk_term_cbk))			return this.Term_cbk();
		else if	(ctx.Match(k, Invk_xtns))					return xtn_mgr;
		else if	(ctx.Match(k, Invk_ctg_mgr))				return ctg_mgr;
		else if	(ctx.Match(k, Invk_usr_dlg))				return Xoa_app_.Usr_dlg();
		else if	(ctx.Match(k, Invk_specials))				return special_mgr;
		else if	(ctx.Match(k, Invk_server))					return tcp_server;
		else if	(ctx.Match(k, Invk_http_server))			return http_server;
		else if	(ctx.Match(k, Invk_app))					return this;  
		else if	(ctx.Match(k, Invk_xowa))					return this;  
		else if	(ctx.Match(k, Invk_fmtrs))					return fmtr_mgr;  
		else if	(ctx.Match(k, Invk_cfg))					return cfg;
		else if	(ctx.Match(k, Invk_xwiki_langs_load))		xwiki_mgr__sitelink_mgr.Parse(m.ReadBry("v"));  
		else return Gfo_invk_.Rv_unhandled;
		return this;
	}
	public static final String Invk_gui = "gui", Invk_bldr = "bldr", Invk_wikis = "wikis", Invk_files = "files", Invk_langs = "langs", Invk_users = "users"
	, Invk_sys_cfg = "sys_cfg", Invk_fsys = "fsys", Invk_cur = "cur", Invk_shell = "shell", Invk_log = "log"
	, Invk_setup = "setup", Invk_scripts = "scripts", Invk_user = "user", Invk_xtns = "xtns", Invk_ctg_mgr = "ctg_mgr"
	, Invk_app = "app", Invk_xowa = "xowa", Invk_usr_dlg = "usr_dlg", Invk_specials = "specials", Invk_html = "html"
	, Invk_server = "tcp_server", Invk_http_server = "http_server"
	, Invk_fmtrs = "fmtrs"
	, Invk_cfg = "cfg"
	, Invk_api = "api"
	, Invk_xwiki_langs_load = "xwiki_langs_load"
	;
	public static final String Invk_term_cbk = "term_cbk";
}
