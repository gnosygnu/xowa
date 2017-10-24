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
package gplx.xowa.apps; import gplx.*; import gplx.xowa.*;
import gplx.core.net.*; import gplx.core.log_msgs.*; import gplx.langs.jsons.*; import gplx.core.brys.*; import gplx.core.threads.*;
import gplx.core.ios.*;
import gplx.dbs.*; import gplx.xowa.apps.apis.*; import gplx.xowa.apps.fsys.*; import gplx.xowa.apps.metas.*; import gplx.xowa.parsers.amps.*; import gplx.xowa.langs.cases.*; import gplx.core.intls.*; import gplx.xowa.users.data.*;
import gplx.xowa.apps.site_cfgs.*; import gplx.xowa.apps.urls.*; import gplx.xowa.files.caches.*; import gplx.xowa.files.imgs.*;
import gplx.xowa.bldrs.css.*;
import gplx.xowa.apps.gfs.*;
import gplx.xowa.htmls.hrefs.*; import gplx.xowa.htmls.core.htmls.utls.*; import gplx.xowa.htmls.bridges.*;
import gplx.xowa.users.*;
import gplx.xowa.wikis.*; import gplx.xowa.wikis.xwikis.*; import gplx.xowa.wikis.xwikis.parsers.*; import gplx.xowa.wikis.xwikis.sitelinks.*;
import gplx.xowa.guis.cbks.*; import gplx.xowa.guis.tabs.*;
import gplx.xowa.langs.*;
import gplx.xowa.bldrs.wms.*;
import gplx.langs.htmls.encoders.*;
import gplx.xowa.bldrs.*;
import gplx.xowa.addons.*; import gplx.xowa.specials.mgrs.*;
import gplx.xowa.addons.apps.cfgs.*; import gplx.xowa.apps.miscs.*;
public class Xoav_app implements Xoa_app, Gfo_invk {
	public Xoav_app(Gfo_usr_dlg usr_dlg, Xoa_app_mode mode, Xog_tab_mgr tab_mgr, String plat_name, Io_url root_dir, Io_url file_dir, Io_url css_dir, Io_url http_root) {
		Xoa_app_.Usr_dlg_(usr_dlg); this.usr_dlg = usr_dlg; this.mode = mode;
		this.fsys_mgr = new Xoa_fsys_mgr(plat_name, root_dir, root_dir.GenSubDir("wiki"), file_dir, css_dir, http_root);
		this.gfs_mgr = new Xoa_gfs_mgr("anonymous", this, fsys_mgr);
		this.lang_mgr = new Xoa_lang_mgr(this, gfs_mgr);
		this.meta_mgr = new Xoa_meta_mgr(this);
		this.file__cache_mgr = new Xof_cache_mgr(usr_dlg, null, null);
		this.file__img_mgr = new Xof_img_mgr();
		this.wiki_mgr = new Xoav_wiki_mgr(this, utl_case_mgr);
		this.utl_msg_log = Gfo_msg_log.Test();
		this.html__bridge_mgr = new Xoh_bridge_mgr(utl__json_parser);
		this.gui__tab_mgr = tab_mgr;

		// user
		this.user = new Xouv_user(this, "anonymous", root_dir.GenSubDir_nest("user", "anonymous"));
		this.fsys_mgr.Url_finder().Init_by_user(user.Fsys_mgr());

		this.api_root = null;
		this.site_cfg_mgr = new Xoa_site_cfg_mgr(this);
		this.bldr = new Xob_bldr(null);
	}
	public boolean						Tid_is_edit()				{return Bool_.N;}
	public Xoa_app_mode				Mode()						{return mode;} private final    Xoa_app_mode mode;
	public Xou_user					User()						{return user;} private final    Xouv_user user;
	public Xoapi_root				Api_root()					{return api_root;} private final    Xoapi_root api_root;
	public Xoa_fsys_mgr				Fsys_mgr()					{return fsys_mgr;} private final    Xoa_fsys_mgr fsys_mgr;
	public Xoav_wiki_mgr			Wiki_mgr()					{return wiki_mgr;} private final    Xoav_wiki_mgr wiki_mgr;
	public Xoa_wiki_mgr				Wiki_mgri()					{return wiki_mgr;}
	public Xoa_lang_mgr				Lang_mgr()					{return lang_mgr;} private final    Xoa_lang_mgr lang_mgr;
	public Xoa_gfs_mgr				Gfs_mgr()					{return gfs_mgr;} private final    Xoa_gfs_mgr gfs_mgr;
	public Xof_cache_mgr			File__cache_mgr()			{return file__cache_mgr;} private final    Xof_cache_mgr file__cache_mgr;
	public Xof_img_mgr				File__img_mgr()				{return file__img_mgr;} private final    Xof_img_mgr file__img_mgr;
	public Io_download_fmt			File__download_fmt()		{return file__download_fmt;} private final    Io_download_fmt file__download_fmt = new Io_download_fmt();
	public Xoh_href_parser			Html__href_parser()			{return href_parser;} private final    Xoh_href_parser href_parser = new Xoh_href_parser();
	public Xoh_href_wtr				Html__href_wtr()			{return html__href_wtr;} private final    Xoh_href_wtr html__href_wtr = new Xoh_href_wtr();
	public Xoa_css_extractor		Html__css_installer()		{return html__css_installer;} private final    Xoa_css_extractor html__css_installer = new Xoa_css_extractor();
	public Xoh_bridge_mgr			Html__bridge_mgr()			{return html__bridge_mgr;} private final    Xoh_bridge_mgr html__bridge_mgr;
	public Xoa_meta_mgr				Dbmeta_mgr()				{return meta_mgr;} private final    Xoa_meta_mgr meta_mgr;
	public Gfo_inet_conn			Utl__inet_conn()			{return inet_conn;} private final    Gfo_inet_conn inet_conn = Gfo_inet_conn_.new_();
	public Xoa_site_cfg_mgr			Site_cfg_mgr()				{return site_cfg_mgr;} private final    Xoa_site_cfg_mgr site_cfg_mgr;
	public boolean						Xwiki_mgr__missing(byte[] domain)	{return wiki_mgr.Get_by_or_null(domain) == null;}
	public Xoa_sitelink_mgr			Xwiki_mgr__sitelink_mgr()	{return xwiki_mgr__sitelink_mgr;} private final    Xoa_sitelink_mgr xwiki_mgr__sitelink_mgr = new Xoa_sitelink_mgr();
	public Xow_xwiki_itm_parser		Xwiki_mgr__itm_parser()		{return xwiki_mgr__itm_parser;}	private final    Xow_xwiki_itm_parser xwiki_mgr__itm_parser = new Xow_xwiki_itm_parser();
	public Xoax_addon_mgr			Addon_mgr()					{return addon_mgr;} private final    Xoax_addon_mgr addon_mgr = new Xoax_addon_mgr();
	public Xob_bldr					Bldr()						{return bldr;} private final    Xob_bldr bldr;
	public Xoa_special_regy			Special_regy()				{return special_regy;} private final    Xoa_special_regy special_regy = new Xoa_special_regy();
	public Xog_cbk_mgr				Gui__cbk_mgr()				{return gui__cbk_mgr;} private final    Xog_cbk_mgr gui__cbk_mgr = new Xog_cbk_mgr();
	public Xog_tab_mgr				Gui__tab_mgr()				{return gui__tab_mgr;} private final    Xog_tab_mgr gui__tab_mgr;
	public Gfo_thread_mgr			Thread_mgr()				{return thread_mgr;} private final    Gfo_thread_mgr thread_mgr = new Gfo_thread_mgr();
	public Xop_amp_mgr				Parser_amp_mgr()			{return parser_amp_mgr;} private final    Xop_amp_mgr parser_amp_mgr = Xop_amp_mgr.Instance;
	public Xocfg_mgr				Cfg()						{return cfg;} private final    Xocfg_mgr cfg = new Xocfg_mgr();
	public Xoa_misc_mgr				Misc_mgr()					{return misc_mgr;} private final    Xoa_misc_mgr misc_mgr = new Xoa_misc_mgr();

	public Xowmf_mgr				Wmf_mgr()					{return wmf_mgr;} private final    Xowmf_mgr wmf_mgr = new Xowmf_mgr();
	public Gfo_usr_dlg				Usr_dlg() {return usr_dlg;} public void Usr_dlg_(Gfo_usr_dlg v) {usr_dlg = v; Xoa_app_.Usr_dlg_(usr_dlg);} private Gfo_usr_dlg usr_dlg = Gfo_usr_dlg_.Noop;
	public Bry_bfr_mkr				Utl__bfr_mkr()				{return utl__bry_bfr_mkr;}	private final    Bry_bfr_mkr utl__bry_bfr_mkr = new Bry_bfr_mkr();
	public Json_parser				Utl__json_parser()			{return utl__json_parser;} private final    Json_parser utl__json_parser = new Json_parser();
	public boolean						Bldr__running()				{return bldr__running;} public void Bldr__running_(boolean v) {this.bldr__running = v;} private boolean bldr__running;
	public Xop_amp_mgr Utl_amp_mgr() {return utl_amp_mgr;} private Xop_amp_mgr utl_amp_mgr = Xop_amp_mgr.Instance;
	public Xol_case_mgr Utl_case_mgr() {return utl_case_mgr;} private Xol_case_mgr utl_case_mgr = Xol_case_mgr_.U8();
//		public Gfo_url_encoder Utl_encoder_fsys() {return utl_encoder_fsys;} private Gfo_url_encoder utl_encoder_fsys = Gfo_url_encoder.New_fsys_lnx();
	public Gfo_msg_log Utl_msg_log() {return utl_msg_log;} private Gfo_msg_log utl_msg_log;
	public Xoav_url_parser Utl_url_parser_xo() {return utl_url_parser_xo;} private Xoav_url_parser utl_url_parser_xo = new Xoav_url_parser();
	public Gfo_url_parser Utl_url_parser_gfo() {return utl_url_parser_gfo;} private final    Gfo_url_parser utl_url_parser_gfo = new Gfo_url_parser();
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {throw Err_.new_unimplemented_w_msg("implemented for Xoa_cfg_mgr");}
	public void Init_by_app(Io_url user_db_url) {
		user.Init_db(this, wiki_mgr, user_db_url);
		this.Addon_mgr().Add_dflts_by_app(this).Run_by_app(this);
		cfg.Init_by_app(this);
		misc_mgr.Init_by_app(this);
	}
	public void Free_mem() {	// NOTE:not yet called in drd; DATE:2016-12-04
	}
	public static Xoav_app New_by_drd(gplx.xowa.drds.files.Xod_fsys_mgr fsys_mgr, Xog_tab_mgr tab_mgr) {
		// create log
		Gfo_usr_dlg__log_base log = new Gfo_usr_dlg__log_base(); log.Log_dir_(Io_url_.mem_dir_("mem/tmp"));
		Gfo_usr_dlg usr_dlg = new Gfo_usr_dlg_base(log, Gfo_usr_dlg__gui_.Console);
		Xoa_app_.Usr_dlg_(usr_dlg);

		return new Xoav_app(usr_dlg, Xoa_app_mode.Itm_gui, tab_mgr, "drd", fsys_mgr.App_root_dir(), fsys_mgr.Usr_data_dir(), fsys_mgr.Usr_data_dir().GenSubDir("temp"), Io_url_.new_any_("/android_asset/xowa/"));
	}
}
