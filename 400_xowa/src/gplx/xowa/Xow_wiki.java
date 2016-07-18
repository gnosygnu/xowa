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
import gplx.core.primitives.*;
import gplx.xowa.langs.*; import gplx.xowa.langs.cases.*;
import gplx.xowa.wikis.*; import gplx.xowa.wikis.domains.*; import gplx.xowa.wikis.metas.*; import gplx.xowa.wikis.data.site_stats.*; import gplx.xowa.wikis.ttls.*; import gplx.xowa.wikis.data.*; import gplx.xowa.wikis.xwikis.*; import gplx.xowa.addons.*;
import gplx.xowa.files.*; import gplx.xowa.files.repos.*; import gplx.xowa.files.origs.*; import gplx.xowa.files.bins.*; import gplx.fsdb.meta.*; import gplx.fsdb.*;
import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.htmls.utls.*; import gplx.xowa.htmls.core.hzips.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.bridges.dbuis.tbls.*; import gplx.xowa.htmls.hrefs.*;
import gplx.xowa.parsers.*;
import gplx.xowa.apps.urls.*;
public interface Xow_wiki extends Xow_ttl_parser, Gfo_invk {
	boolean						Type_is_edit();
	Xoa_app						App();
	Xol_lang_itm				Lang();
	Xol_case_mgr				Case_mgr();
	byte[]						Domain_bry();			// EX: en.wikipedia.org
	String						Domain_str();
	int							Domain_tid();			// Xow_domain_tid_.Int__wikipedia
	byte[]						Domain_abrv();			// enwiki
	Xow_domain_itm				Domain_itm();
	Xow_fsys_mgr				Fsys_mgr();
	Xow_db_mgr					Data__core_mgr();
	Xof_fsdb_mode				File__fsdb_mode();
	Fsdb_db_mgr					File__fsdb_core();
	Xow_repo_mgr				File__repo_mgr();
	Xof_orig_mgr				File__orig_mgr();
	Xof_bin_mgr					File__bin_mgr();
	Fsm_mnt_mgr					File__mnt_mgr();
	boolean						Html__hdump_enabled();
	Xow_hdump_mgr				Html__hdump_mgr();
	Xoh_page_wtr_mgr			Html__wtr_mgr();
	Xoh_href_wtr				Html__href_wtr();
	boolean						Html__css_installing(); void Html__css_installing_(boolean v);
	Xow_mw_parser_mgr			Mw_parser_mgr();
	Xow_xwiki_mgr				Xwiki_mgr();
	Xow_wiki_props				Props();
	Xow_site_stats_mgr			Stats();
	void						Init_by_wiki();
	void						Init_by_wiki__force();	// HACK: force init for drd wiki
	Xow_url_parser				Utl__url_parser();
	Xoax_addon_mgr				Addon_mgr();
	void						Init_needed_y_();
	void						Rls();
}
