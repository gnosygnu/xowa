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
import gplx.xowa.wikis.*; import gplx.xowa.wikis.ttls.*; import gplx.xowa.wikis.data.*;
import gplx.xowa.files.*; import gplx.xowa.files.repos.*; import gplx.xowa.files.origs.*; import gplx.xowa.files.bins.*; import gplx.fsdb.meta.*;
import gplx.xowa.html.wtrs.*; import gplx.xowa.html.hzips.*; import gplx.xowa.html.hdumps.*;
public interface Xow_wiki extends Xow_ttl_parser {
	byte[]						Domain_bry();			// EX: en.wikipedia.org
	String						Domain_str();
	int							Domain_tid();			// Xow_domain_.Tid_int_wikipedia
	byte[]						Domain_abrv();			// enwiki
	Xow_domain					Domain_itm();
	Xow_fsys_mgr				Fsys_mgr();
	Xoa_app						App();
	Xol_lang					Lang();
	Xof_fsdb_mode				File_mgr__fsdb_mode();
	Xow_repo_mgr				File_mgr__repo_mgr();
	Xof_orig_mgr				File_mgr__orig_mgr();
	Xof_bin_mgr					File_mgr__bin_mgr();
	Fsm_mnt_mgr					File_mgr__mnt_mgr();
	Xowd_db_mgr					Data_mgr__core_mgr();
	boolean						Html_mgr__hdump_enabled();
	Xow_hzip_mgr				Html_mgr__hzip_mgr();
	Xohd_hdump_rdr				Html_mgr__hdump_rdr();
	Xoh_lnki_wtr_utl			Html_mgr__lnki_wtr_utl();
}
