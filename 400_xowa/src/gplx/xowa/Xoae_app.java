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
import gplx.core.btries.*; import gplx.core.flds.*; import gplx.ios.*; import gplx.threads.*;
import gplx.xowa.apps.*; import gplx.xowa.apps.caches.*; import gplx.xowa.apps.fsys.*; import gplx.xowa.apis.*; import gplx.xowa.urls.encoders.*; import gplx.xowa.apps.progs.*;
import gplx.xowa.langs.*; import gplx.xowa.specials.*; import gplx.xowa.cfgs2.*;
import gplx.xowa.files.caches.*; import gplx.xowa.files.imgs.*;
import gplx.xowa.wikis.*; import gplx.xowa.users.*; import gplx.xowa.gui.*; import gplx.xowa.cfgs.*; import gplx.xowa.ctgs.*; import gplx.xowa.html.tocs.*; import gplx.xowa.fmtrs.*; import gplx.xowa.html.*;
import gplx.xowa.parsers.*; import gplx.xowa.parsers.amps.*; import gplx.xowa.parsers.tblws.*;
import gplx.xowa.xtns.*; import gplx.xowa.xtns.scribunto.*; import gplx.xowa.xtns.math.*;
import gplx.xowa.parsers.logs.*; import gplx.xowa.servers.tcp.*; import gplx.xowa.servers.http.*;
import gplx.xowa.wmfs.*;
public class Xoae_app implements Xoa_app, GfoInvkAble {
	public Xoae_app(Gfo_usr_dlg usr_dlg, Io_url root_dir, Io_url user_dir, String bin_dir_name) {
		Xoa_app_.Usr_dlg_(usr_dlg);			
		Io_url.Http_file_str_encoder = Xoa_app_.Utl__encoder_mgr().Fsys();
		fsys_mgr = new Xoa_fsys_mgr(bin_dir_name, root_dir);
		log_wtr = usr_dlg.Log_wtr();
		cfg_mgr = new Xoa_cfg_mgr(this);
		api_root = new Xoapi_root(this);
		user = new Xou_user(this, user_dir);
		url_cmd_eval = new Xoa_fsys_eval(fsys_mgr, user.Fsys_mgr());			
		fsys_mgr.Init_by_app(prog_mgr);
		log_wtr.Log_dir_(user.Fsys_mgr().App_temp_dir().GenSubDir("log"));
		lang_mgr = new Xoa_lang_mgr(this);
		wiki_mgr = new Xoa_wiki_mgr(this);
		gui_mgr = new Xoa_gui_mgr(this);
		bldr = new Xob_bldr(this);
		file_mgr.Ctor_by_app(this);
		href_parser = new Xoh_href_parser(Xoa_app_.Utl__encoder_mgr().Href(), url_parser.Url_parser());
		sanitizer = new Xop_sanitizer(parser_amp_mgr, msg_log);
		user_mgr = new Xou_user_mgr(this, user);
		sys_cfg = new Xoa_sys_cfg(this);
		cur_redirect = new Xoa_cur(this);
		shell = new Xoa_shell(this);
		setup_mgr = new Xoi_setup_mgr(this);
		Xoa_app_.Gfs_mgr_(new Xoa_gfs_mgr(this, fsys_mgr, user.Fsys_mgr()));
		xtn_mgr = new Xow_xtn_mgr().Ctor_by_app(this);
		hive_mgr = new Xoa_hive_mgr(this);
		tcp_server.App_ctor(this);
		fmtr_mgr = new Xoa_fmtr_mgr(this);
		log_mgr = new Xop_log_mgr(this);
		http_server = new Http_server_mgr(this);
		cfg_regy = new Xocfg_regy(this);
		html_mgr = new Xoh_html_mgr(this);
//			queue_file = new Xop_queue_mgr(this);
	}
	public byte					Mode()					{return Xoa_app_.Mode();}
	public Xoa_fsys_mgr			Fsys_mgr()				{return fsys_mgr;} private final Xoa_fsys_mgr fsys_mgr;
	public Xof_cache_mgr		File_mgr__cache_mgr()	{return file_mgr.Cache_mgr();}
	public Xof_img_mgr			File_mgr__img_mgr()		{return file_mgr.Img_mgr();}
	public Xowmf_mgr			Wmf_mgr()				{return wmf_mgr;} private final Xowmf_mgr wmf_mgr = new Xowmf_mgr();
	public Bry_bfr_mkr			Utl__bfr_mkr()			{return Xoa_app_.Utl__bfr_mkr();}
	public Url_encoder_mgr		Utl__encoder_mgr()		{return Xoa_app_.Utl__encoder_mgr();}

	
	public Xoa_css_extractor	Css_installer() {return css_installer;} private final Xoa_css_extractor css_installer = new Xoa_css_extractor();
	public Xoa_wiki_mgr			Wiki_mgr() {return wiki_mgr;} private Xoa_wiki_mgr wiki_mgr;
	public Xou_user_mgr			User_mgr() {return user_mgr;} private Xou_user_mgr user_mgr;
	public Xof_file_mgr			File_mgr() {return file_mgr;} private Xof_file_mgr file_mgr = new Xof_file_mgr();
	public Xoa_lang_mgr			Lang_mgr() {return lang_mgr;} private Xoa_lang_mgr lang_mgr;
	public Xoa_gui_mgr			Gui_mgr() {return gui_mgr;} private Xoa_gui_mgr gui_mgr;
	public Xou_user				User() {return user;} private Xou_user user;
	public Xob_bldr				Bldr() {return bldr;} private Xob_bldr bldr;
	public Xow_xtn_mgr			Xtn_mgr() {return xtn_mgr;} private Xow_xtn_mgr xtn_mgr;
	public Xoapi_root			Api_root() {return api_root;} private Xoapi_root api_root;
	public Xop_tkn_mkr			Tkn_mkr() {return tkn_mkr;} private Xop_tkn_mkr tkn_mkr = new Xop_tkn_mkr();
	public Gfo_usr_dlg			Usr_dlg() {return Xoa_app_.Usr_dlg();}
	public Gfo_log_wtr			Log_wtr() {return log_wtr;} private Gfo_log_wtr log_wtr;
	public Xoa_gfs_mgr			Gfs_mgr() {return Xoa_app_.Gfs_mgr();}
	public Xoa_special_mgr		Special_mgr() {return special_mgr;} private Xoa_special_mgr special_mgr = new gplx.xowa.specials.Xoa_special_mgr();
	public Xoh_html_mgr			Html_mgr() {return html_mgr;} private Xoh_html_mgr html_mgr;
	public Xop_log_mgr			Log_mgr() {return log_mgr;} private Xop_log_mgr log_mgr;
	public Xoa_shell			Shell() {return shell;} private Xoa_shell shell;
	public Xoa_thread_mgr		Thread_mgr() {return thread_mgr;} private Xoa_thread_mgr thread_mgr = new Xoa_thread_mgr();
	public Xoa_hive_mgr			Hive_mgr() {return hive_mgr;} private Xoa_hive_mgr hive_mgr;
	public Xoa_url_parser		Url_parser() {return url_parser;} private Xoa_url_parser url_parser = new Xoa_url_parser();
	public Xoh_href_parser		Href_parser() {return href_parser;} private Xoh_href_parser href_parser;
	public Xop_sanitizer		Sanitizer() {return sanitizer;} private Xop_sanitizer sanitizer;
	public Xop_xatr_parser		Xatr_parser() {return xatr_parser;} private Xop_xatr_parser xatr_parser = new Xop_xatr_parser();
	public Xop_xnde_tag_regy	Xnde_tag_regy() {return xnde_tag_regy;} private Xop_xnde_tag_regy xnde_tag_regy = new Xop_xnde_tag_regy();
	public Xof_math_subst_regy	Math_subst_regy() {return math_subst_regy;} private Xof_math_subst_regy math_subst_regy = new Xof_math_subst_regy();
	public Xoa_prog_mgr			Prog_mgr() {return prog_mgr;} private final Xoa_prog_mgr prog_mgr = new Xoa_prog_mgr();
	public Gfo_async_mgr		Async_mgr() {return async_mgr;} private Gfo_async_mgr async_mgr = new Gfo_async_mgr();

	public Xoi_setup_mgr		Setup_mgr() {return setup_mgr;} private Xoi_setup_mgr setup_mgr;
	public Gfo_msg_log			Msg_log() {return msg_log;} private Gfo_msg_log msg_log = new Gfo_msg_log(Xoa_app_.Name);
	public Gfo_msg_log			Msg_log_null() {return msg_log_null;} private Gfo_msg_log msg_log_null = new Gfo_msg_log("null_log");

	public Xoh_file_main_wkr	File_main_wkr() {return file_main_wkr;} private Xoh_file_main_wkr file_main_wkr = new Xoh_file_main_wkr();		
	public Btrie_slim_mgr		Utl_trie_tblw_ws() {return utl_trie_tblw_ws;} private Btrie_slim_mgr utl_trie_tblw_ws = Xop_tblw_ws_itm.trie_();
	public Gfo_fld_rdr			Utl_fld_rdr() {return utl_fld_rdr;} Gfo_fld_rdr utl_fld_rdr = Gfo_fld_rdr.xowa_();
	public Gfo_log_bfr			Log_bfr() {return log_bfr;} private Gfo_log_bfr log_bfr = new Gfo_log_bfr();
	public Xoa_sys_cfg			Sys_cfg() {return sys_cfg;} private Xoa_sys_cfg sys_cfg;
	public Bry_fmtr				Tmp_fmtr() {return tmp_fmtr;} Bry_fmtr tmp_fmtr = Bry_fmtr.new_("");
	public boolean					Xwiki_mgr__missing(byte[] wiki_key)	{return user.Wiki().Xwiki_mgr().Get_by_key(wiki_key) == null;} // NOTE: only the user_wiki has a full list of all wikis b/c it has xwiki objects; wiki_mgr does not, b/c it has heavier wiki objects which are loaded dynamically;
	public boolean					Xwiki_mgr__exists(byte[] wiki_key)	{return user.Wiki().Xwiki_mgr().Get_by_key(wiki_key) != null;}
	public Xoa_ctg_mgr			Ctg_mgr() {return ctg_mgr;} private Xoa_ctg_mgr ctg_mgr = new Xoa_ctg_mgr();
	public Xoa_fsys_eval		Url_cmd_eval() {return url_cmd_eval;} Xoa_fsys_eval url_cmd_eval;
	public Xoa_cur				Cur_redirect() {return cur_redirect;} private Xoa_cur cur_redirect;
	public Xoa_cfg_mgr			Cfg_mgr() {return cfg_mgr;} private Xoa_cfg_mgr cfg_mgr;
	public Xocfg_regy			Cfg_regy() {return cfg_regy;} private Xocfg_regy cfg_regy;
	public Io_stream_zip_mgr	Zip_mgr() {return zip_mgr;} Io_stream_zip_mgr zip_mgr = new Io_stream_zip_mgr();
	public Xoa_cache_mgr		Cache_mgr() {return cache_mgr;} private Xoa_cache_mgr cache_mgr = new Xoa_cache_mgr();

	public Xosrv_server			Tcp_server() {return tcp_server;} private Xosrv_server tcp_server = new Xosrv_server();
	public Http_server_mgr		Http_server() {return http_server;} private Http_server_mgr http_server;
	public Xop_amp_mgr			Parser_amp_mgr() {return parser_amp_mgr;} private Xop_amp_mgr parser_amp_mgr = new Xop_amp_mgr();

	private Xoa_fmtr_mgr fmtr_mgr;
	public NumberParser Utl_num_parser() {return utl_num_parser;} private NumberParser utl_num_parser = new NumberParser();
	public void Init_by_app() {
		stage = Xoa_stage_.Tid_init;
		prog_mgr.Init_by_app(url_cmd_eval);
		xtn_mgr.Init_by_app(this);
		log_wtr.Init();
		gui_mgr.Init_by_app();
		user.Init_by_app(this);
		file_mgr.Init_by_app(this);
		css_installer.Init_by_app(this);
		wiki_mgr.Init_by_app();
		gplx.xowa.utls.upgrades.Xoa_upgrade_mgr.Check(this);
		ctg_mgr.Init_by_app(this);
		setup_mgr.Init_by_app(this);
		thread_mgr.Usr_dlg_(Xoa_app_.Usr_dlg());
		html_mgr.Init_by_app(this);
		api_root.Init_by_app(this);
	}
	public boolean Launch_done() {return stage == Xoa_stage_.Tid_launch;}
	public void Launch() {
		if (Launch_done()) return;
		stage = Xoa_stage_.Tid_launch;
		user.Cfg_mgr().Setup_mgr().Setup_run_check(this); log_bfr.Add("app.upgrade.done");
		gplx.xowa.users.prefs.Prefs_converter._.Check(this);
		user.Wiki().Init_assert();	// NOTE: must assert wiki and load langs first, else will be asserted during Portal_mgr().Init(), which will cause IndexOutOfBounds; DATE:2014-10-04
	}
	public byte Stage() {return stage;} public Xoae_app Stage_(byte v) {stage = v; return this;} private byte stage = Xoa_stage_.Tid_ctor;
	public boolean Term_cbk() {
		Gfo_usr_dlg usr_dlg = Xoa_app_.Usr_dlg();
		usr_dlg.Log_many("", "", "term:bgn");
		if (setup_mgr.Cmd_mgr().Working()) {
			if (!gui_mgr.Kit().Ask_yes_no("", "", "An import is in progress. Are you sure you want to exit?")) return false;
		} 
		gui_mgr.Browser_win().Usr_dlg().Canceled_y_();		
		user.App_term(); usr_dlg.Log_many("", "", "term:app_term");
		gui_mgr.Browser_win().Tab_mgr().Tabs_close_all();
		log_wtr.Term(); usr_dlg.Log_many("", "", "term:log_wtr");
		log_mgr.Rls(); usr_dlg.Log_many("", "", "term:log_mgr");
		if (Scrib_core.Core() != null) {Scrib_core.Core().Term(); usr_dlg.Log_many("", "", "term:scrib");}
		wiki_mgr.Rls(); usr_dlg.Log_many("", "", "term:wiki_mgr");
		return true;
	}
	public void Reset_all() {
		this.Free_mem(true);
		gplx.xowa.xtns.scribunto.Scrib_core.Core_invalidate();
		Env_.GarbageCollect();
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
		else if	(ctx.Match(k, Invk_scripts))				return Xoa_app_.Gfs_mgr();
		else if	(ctx.MatchPriv(k, Invk_term_cbk))			return this.Term_cbk();
		else if	(ctx.Match(k, Invk_xtns))					return xtn_mgr;
		else if	(ctx.Match(k, Invk_ctg_mgr))				return ctg_mgr;
		else if	(ctx.Match(k, Invk_cfgs))					return cfg_mgr;
		else if	(ctx.Match(k, Invk_usr_dlg))				return Xoa_app_.Usr_dlg();
		else if	(ctx.Match(k, Invk_specials))				return special_mgr;
		else if	(ctx.Match(k, Invk_server))					return tcp_server;
		else if	(ctx.Match(k, Invk_http_server))			return http_server;
		else if	(ctx.Match(k, Invk_app))					return this;  
		else if	(ctx.Match(k, Invk_xowa))					return this;  
		else if	(ctx.Match(k, Invk_fmtrs))					return fmtr_mgr;  
		else if	(ctx.Match(k, Invk_cfg))					return cfg_regy;
		else return GfoInvkAble_.Rv_unhandled;
	}
	public static final String Invk_gui = "gui", Invk_bldr = "bldr", Invk_wikis = "wikis", Invk_files = "files", Invk_langs = "langs", Invk_users = "users"
	, Invk_sys_cfg = "sys_cfg", Invk_fsys = "fsys", Invk_cur = "cur", Invk_shell = "shell", Invk_log = "log"
	, Invk_setup = "setup", Invk_scripts = "scripts", Invk_user = "user", Invk_xtns = "xtns", Invk_ctg_mgr = "ctg_mgr"
	, Invk_cfgs = "cfgs", Invk_app = "app", Invk_xowa = "xowa", Invk_usr_dlg = "usr_dlg", Invk_specials = "specials", Invk_html = "html"
	, Invk_server = "tcp_server", Invk_http_server = "http_server"
	, Invk_fmtrs = "fmtrs"
	, Invk_cfg = "cfg"
	, Invk_api = "api"
	;
	public static final String Invk_term_cbk = "term_cbk";
}
