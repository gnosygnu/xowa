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
package gplx.xowa.xtns.pfuncs.ifs;
import gplx.types.basics.utls.BryUtl;
import gplx.core.envs.Env_;
import gplx.types.basics.utls.BoolUtl;
import gplx.xowa.Xoa_ttl;
import gplx.xowa.Xowe_wiki;
import gplx.xowa.wikis.data.tbls.Xowd_page_itm;
import gplx.xowa.wikis.nss.Xow_ns;
import gplx.xowa.wikis.nss.Xow_ns_;
public class Pfunc_ifexist_mgr {
	public boolean Exists(Xowe_wiki wiki, byte[] raw_bry) {
		// validate ttl; return false if invalid
		if (BryUtl.IsNullOrEmpty(raw_bry)) return false;	// return early; NOTE: {{autolink}} can pass in "" (see test)
		Xoa_ttl ttl = Xoa_ttl.Parse(wiki, raw_bry); if (ttl == null) return false;

		// try to get from cache
		byte exists = wiki.Cache_mgr().Ifexist_cache().Get_by_cache(ttl);
		if		(exists == BoolUtl.YByte) return true;
		else if (exists == BoolUtl.NByte) return false;

		// not in cache; do find
		switch (ttl.Ns().Id()) {
			case Xow_ns_.Tid__special:	return true; // NOTE: some pages call for [[Special]]; always return true for now; DATE:2014-07-17
			case Xow_ns_.Tid__media:	return Find_by_ns__media(wiki, ttl);
			default:					return Find_by_ns__other(wiki, ttl);
		}
	}
	private boolean Find_by_ns__other(Xowe_wiki wiki, Xoa_ttl ttl) {
		boolean rv = wiki.Cache_mgr().Ifexist_cache().Get_by_load(ttl);

		// handle variants
		if (	!rv
			&&	wiki.Lang().Vnt_mgr().Enabled()) {
			Xowd_page_itm page = wiki.Lang().Vnt_mgr().Convert_mgr().Convert_ttl(wiki, ttl.Ns(), ttl.Page_db());
			if (page != Xowd_page_itm.Null)
				rv = page.Exists();
		}
		return rv;
	}
	private boolean Find_by_ns__media(Xowe_wiki wiki, Xoa_ttl ttl) {
		// rarely true, but check local wiki's [[File:]] table anyway
		Xow_ns file_ns = wiki.Ns_mgr().Ns_file();
		byte[] page_db = ttl.Page_db(); // NOTE: must use Page_db; also handles Media:A.svg -> File:A.svg; EX: {{#ifexist:File:Peter & Paul fortress in SPB 03.jpg|y|n}}
		Xoa_ttl file_ttl = wiki.Ttl_parse(file_ns.Id(), page_db);
		if (file_ttl == null) return false; // NOTE: must check for NPE: PAGE:es.w:Elecciones_presidenciales_de_Venezuela_de_1998 DATE:2017-09-04

		// check cache
		byte exists = wiki.Cache_mgr().Ifexist_cache().Get_by_cache(file_ttl);
		if		(exists == BoolUtl.YByte) return true;
		else if (exists == BoolUtl.NByte) return false;

		// call Find_by_ns_other which will call load, but with "File:" instead of "Media:"
		if (Find_by_ns__other(wiki, file_ttl)) return true;

		// check commons; either (a) commons_wiki directly; or (b) tdb's file_mgr; note that (b) is obsolete
		Xowe_wiki commons_wiki = wiki.Appe().Wiki_mgr().Wiki_commons();
		boolean env_is_testing = Env_.Mode_testing();
		boolean rv = false;
		if (	commons_wiki != null														// null check
			&&	(	commons_wiki.Init_assert().Db_mgr().Tid() == gplx.xowa.wikis.dbs.Xodb_mgr_sql.Tid_sql	// make sure tid=sql; tid=txt automatically created for online images; DATE:2014-09-21
				||	env_is_testing
				)
			) {
			file_ns = commons_wiki.Ns_mgr().Ns_file();
			rv = Find_by_ns__other(commons_wiki, file_ttl);		// accurate test using page table in commons wiki (provided commons is up to date)
		}
		else {
			if (!env_is_testing)
				wiki.File_mgr().Init_file_mgr_by_load(wiki);	// NOTE: must init Fsdb_mgr (else conn == null), and with bin_wkrs (else no images will ever load); DATE:2014-09-21
			rv = wiki.File_mgr().Exists(page_db);				// less-accurate test using either (1) orig_wiki table in local wiki (v2) or (2) meta_db_mgr (v1)
		}

		// add commons result to this wiki's cache; needed b/c cache will have "false" value (b/c Page does not exist in wiki), but should be "true" (since it exists in commons) PAGE:en.w:Harstad DATE:2018-07-03
		wiki.Cache_mgr().Ifexist_cache().Add(file_ttl, rv);
		return rv;
	}
 	}