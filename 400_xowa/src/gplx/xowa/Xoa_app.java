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
import gplx.ios.*; import gplx.core.net.*;
import gplx.langs.jsons.*;
import gplx.xowa.apps.*; import gplx.xowa.apps.fsys.*; import gplx.xowa.apps.metas.*; import gplx.xowa.apis.*;
import gplx.xowa.bldrs.css.*;
import gplx.xowa.files.caches.*; import gplx.xowa.files.imgs.*;
import gplx.langs.htmls.encoders.*;
import gplx.xowa.wikis.*;
import gplx.xowa.wms.*;
import gplx.xowa.html.hrefs.*; import gplx.xowa.html.wtrs.*; import gplx.xowa.html.js.*; import gplx.xowa.html.bridges.*;
import gplx.xowa.users.*;
public interface Xoa_app {
	Xoa_app_type			App_type();
	Xoapi_root				Api_root();
	Xoa_fsys_mgr			Fsys_mgr();
	Xoa_wiki_mgr			Wiki_mgri();
	Xof_cache_mgr			File__cache_mgr();
	Xof_img_mgr				File__img_mgr();
	Io_download_fmt			File__download_fmt();
	Xoh_href_parser			Html__href_parser();
	Xoh_href_wtr			Html__href_wtr();
	Xoh_lnki_bldr			Html__lnki_bldr();
	Xoa_css_extractor		Html__css_installer();
	Xoh_bridge_mgr			Html__bridge_mgr();
	Xoa_meta_mgr			Meta_mgr();
	Xou_user				User();
	Xowmf_mgr				Wmf_mgr();
	boolean					Xwiki_mgr__missing(byte[] domain);
	boolean					Bldr__running(); void Bldr__running_(boolean v);
	Gfo_usr_dlg				Usr_dlg();
	Bry_bfr_mkr				Utl__bfr_mkr();
	Url_encoder_mgr			Utl__encoder_mgr();
	Json_parser				Utl__json_parser();
	Gfo_inet_conn			Utl__inet_conn();
}	
