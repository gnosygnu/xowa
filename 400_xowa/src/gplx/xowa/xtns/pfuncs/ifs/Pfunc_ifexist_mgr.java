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
package gplx.xowa.xtns.pfuncs.ifs; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.pfuncs.*;
import gplx.core.envs.*; import gplx.core.caches.*;
import gplx.xowa.apps.wms.apis.*; import gplx.xowa.wikis.data.tbls.*;
import gplx.xowa.wikis.nss.*;
public class Pfunc_ifexist_mgr {
	public boolean Exists(Xowe_wiki wiki, byte[] raw_bry) {
		if (Bry_.Len_eq_0(raw_bry)) return false;	// return early; NOTE: {{autolink}} can pass in "" (see test)
		Xoa_ttl ttl = Xoa_ttl.Parse(wiki, raw_bry); if (ttl == null) return false;

		// try to get from cache
		byte exists = wiki.Cache_mgr().Ifexist_cache().Get_by_mem(ttl);
		if		(exists == Bool_.Y_byte) return true;
		else if (exists == Bool_.N_byte) return false;

		byte[] page_db = ttl.Page_db();				// NOTE: must use Page_db; EX: {{#ifexist:File:Peter & Paul fortress in SPB 03.jpg|y|n}}
		Xow_ns ns = ttl.Ns();
		boolean rv = false;
		switch (ns.Id()) {
			case Xow_ns_.Tid__special:	rv = true; break; // NOTE: some pages call for [[Special]]; always return true for now; DATE:2014-07-17
			case Xow_ns_.Tid__media:	rv = Find_by_ns__media(wiki, ns, page_db); break;
			default:					rv = Find_by_ns(wiki, ttl, ns, page_db); break;
		}
		return rv;
	}
	private boolean Find_by_ns(Xowe_wiki wiki, Xoa_ttl ttl, Xow_ns ns, byte[] ttl_bry) {
		boolean rv = wiki.Cache_mgr().Ifexist_cache().Load(ttl);

		// handle variants
		if (	!rv
			&&	wiki.Lang().Vnt_mgr().Enabled()) {
			Xowd_page_itm page = wiki.Lang().Vnt_mgr().Convert_mgr().Convert_ttl(wiki, ns, ttl_bry);
			if (page != Xowd_page_itm.Null)
				rv = page.Exists();
		}
		return rv;
	}
	private boolean Find_by_ns__media(Xowe_wiki wiki, Xow_ns ns, byte[] page_db) {
		// rarely true, but check local wiki's [[File:]] table anyway
		Xow_ns file_ns = wiki.Ns_mgr().Ns_file();
		Xoa_ttl file_ttl = wiki.Ttl_parse(file_ns.Id(), page_db);

		byte exists = wiki.Cache_mgr().Ifexist_cache().Get_by_mem(file_ttl);
		if		(exists == Bool_.Y_byte) return true;
		else if (exists == Bool_.N_byte) return false;

		if (Find_by_ns(wiki, file_ttl, file_ns, page_db)) return true;

		// check commons; either (a) commons_wiki directly; or (b) tdb's file_mgr; note that (b) is obsolete
		Xowe_wiki commons_wiki = wiki.Appe().Wiki_mgr().Wiki_commons();
		boolean env_is_testing = Env_.Mode_testing();
		if (	commons_wiki != null														// null check
			&&	(	commons_wiki.Init_assert().Db_mgr().Tid() == gplx.xowa.wikis.dbs.Xodb_mgr_sql.Tid_sql	// make sure tid=sql; tid=txt automatically created for online images; DATE:2014-09-21
				||	env_is_testing
				)
			) {
			file_ns = commons_wiki.Ns_mgr().Ns_file();
			return Find_by_ns(commons_wiki, file_ttl, file_ns, page_db);					// accurate test using page table in commons wiki (provided commons is up to date)
		}
		else {
			if (!env_is_testing)
				wiki.File_mgr().Init_file_mgr_by_load(wiki);								// NOTE: must init Fsdb_mgr (else conn == null), and with bin_wkrs (else no images will ever load); DATE:2014-09-21
			return wiki.File_mgr().Exists(page_db);											// less-accurate test using either (1) orig_wiki table in local wiki (v2) or (2) meta_db_mgr (v1)
		}
	}
 	}