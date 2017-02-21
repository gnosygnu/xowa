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
import gplx.xowa.apps.apis.xowa.bldrs.imports.*; import gplx.xowa.wikis.data.*; import gplx.xowa.bldrs.infos.*;
public class Xowe_wiki_ {
	public static void Create(Xowe_wiki wiki, long src_fil_len, String src_fil_name) {
		wiki.Db_mgr_create_as_sql(); // create db_mgr as sql
		Xoapi_import import_api = wiki.Appe().Api_root().Bldr().Wiki().Import();
		Xowd_core_db_props db_mgr_props = gplx.xowa.bldrs.Xobldr_cfg.New_props(wiki.App(), wiki.Domain_str(), src_fil_len);
		Xob_info_session info_session = Xob_info_session.new_(import_api.User_name(), wiki.Domain_str(), src_fil_name);
		wiki.Data__core_mgr().Init_by_make(db_mgr_props, info_session);	// make core_db			
	}
	public static void Rls_mem_if_needed(Xowe_wiki wiki) {
		Xoae_app app = wiki.Appe();
		if (gplx.core.envs.Runtime_.Memory_free() < app.Sys_cfg().Free_mem_when())	// check if low in memory
			Xowe_wiki_.Rls_mem(wiki, false);	// clear caches (which will clear bry_bfr_mkr)
		else									// not low in memory
			wiki.Utl__bfr_mkr().Clear();		// clear bry_bfr_mkr only; NOTE: call before page parse, not when page is first added, else threading errors; DATE:2014-05-30
	}
	public static void Rls_mem(Xowe_wiki wiki, boolean clear_ctx) {
		wiki.Appe().Free_mem(clear_ctx);
		wiki.Cache_mgr().Free_mem__all();
		wiki.Parser_mgr().Scrib().Core_term();
	}
}
