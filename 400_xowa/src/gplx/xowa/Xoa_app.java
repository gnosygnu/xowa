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
import gplx.core.brys.*; import gplx.core.ios.*; import gplx.core.net.*;
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
public interface Xoa_app extends Gfo_invk {
	boolean					Tid_is_edit();
	Xoa_app_mode			Mode();
	Xoapi_root				Api_root();
	Xoa_fsys_mgr			Fsys_mgr();
	Xoa_wiki_mgr			Wiki_mgri();
	Xoa_lang_mgr			Lang_mgr();
	Xoa_gfs_mgr				Gfs_mgr();
	Xof_cache_mgr			File__cache_mgr();
	Xof_img_mgr				File__img_mgr();
	Io_download_fmt			File__download_fmt();
	Xoh_href_parser			Html__href_parser();
	Xoh_href_wtr			Html__href_wtr();
	Xoh_lnki_bldr			Html__lnki_bldr();
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
	Bry_bfr_mkr				Utl__bfr_mkr();
	Json_parser				Utl__json_parser();
	Gfo_inet_conn			Utl__inet_conn();
	Xoa_meta_mgr			Dbmeta_mgr();
	Xoa_site_cfg_mgr		Site_cfg_mgr();
	Xoax_addon_mgr			Addon_mgr();
	Xob_bldr				Bldr();
	Xoa_special_regy		Special_regy();
}	
