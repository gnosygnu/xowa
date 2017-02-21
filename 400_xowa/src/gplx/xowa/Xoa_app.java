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
import gplx.core.brys.*; import gplx.core.ios.*; import gplx.core.net.*; import gplx.core.threads.*;
import gplx.langs.jsons.*; import gplx.langs.htmls.encoders.*;
import gplx.xowa.apps.*; import gplx.xowa.apps.fsys.*; import gplx.xowa.apps.site_cfgs.*; import gplx.xowa.apps.metas.*; import gplx.xowa.apps.apis.*;
import gplx.xowa.apps.gfs.*;
import gplx.xowa.bldrs.css.*;
import gplx.xowa.files.caches.*; import gplx.xowa.files.imgs.*;	
import gplx.xowa.guis.cbks.*; import gplx.xowa.guis.tabs.*;
import gplx.xowa.htmls.hrefs.*; import gplx.xowa.htmls.core.htmls.utls.*; import gplx.xowa.htmls.js.*; import gplx.xowa.htmls.bridges.*;
import gplx.xowa.wikis.*; import gplx.xowa.wikis.xwikis.*; import gplx.xowa.wikis.xwikis.parsers.*; import gplx.xowa.wikis.xwikis.sitelinks.*;
import gplx.xowa.langs.*; 
import gplx.xowa.bldrs.wms.*;
import gplx.xowa.users.*;
import gplx.xowa.bldrs.*;
import gplx.xowa.addons.*; import gplx.xowa.specials.mgrs.*;
import gplx.xowa.parsers.amps.*;
import gplx.xowa.addons.apps.cfgs.*; import gplx.xowa.apps.miscs.*;
public interface Xoa_app extends Gfo_invk {
	boolean					Tid_is_edit();
	Xoa_app_mode			Mode();
	Xoapi_root				Api_root();
	Xocfg_mgr				Cfg();
	Xoa_fsys_mgr			Fsys_mgr();
	Xoa_wiki_mgr			Wiki_mgri();
	Xoa_lang_mgr			Lang_mgr();
	Xoa_gfs_mgr				Gfs_mgr();
	Xof_cache_mgr			File__cache_mgr();
	Xof_img_mgr				File__img_mgr();
	Io_download_fmt			File__download_fmt();
	Xoh_href_parser			Html__href_parser();
	Xoa_css_extractor		Html__css_installer();
	Xoh_bridge_mgr			Html__bridge_mgr();
	Xog_cbk_mgr				Gui__cbk_mgr();
	Xog_tab_mgr				Gui__tab_mgr();
	Xou_user				User();
	Xowmf_mgr				Wmf_mgr();
	boolean					Xwiki_mgr__missing(byte[] domain);
	Xoa_sitelink_mgr		Xwiki_mgr__sitelink_mgr();
	Xow_xwiki_itm_parser	Xwiki_mgr__itm_parser();
	boolean					Bldr__running(); void Bldr__running_(boolean v);
	Gfo_usr_dlg				Usr_dlg();
	Json_parser				Utl__json_parser();
	Gfo_inet_conn			Utl__inet_conn();
	Xoa_meta_mgr			Dbmeta_mgr();
	Xoa_site_cfg_mgr		Site_cfg_mgr();
	Xoax_addon_mgr			Addon_mgr();
	Xob_bldr				Bldr();
	Xoa_special_regy		Special_regy();
	Gfo_thread_mgr			Thread_mgr();
	Xop_amp_mgr				Parser_amp_mgr();
	Xoa_misc_mgr			Misc_mgr();
}	
