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
package gplx.xowa.wikis.data; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*;
import gplx.dbs.*;
import gplx.xowa.wikis.domains.*; import gplx.xowa.bldrs.infos.*;
public class Xow_db_file__core_ {
	public static Io_url Find_core_fil_or_null(Xow_wiki wiki) {return Find_core_fil_or_null(wiki.Fsys_mgr().Root_dir(), wiki.Domain_str());}
	public static Io_url Find_core_fil_or_null(Io_url wiki_root_dir, String domain_str) {
		Io_url[] ary = Io_mgr.Instance.QueryDir_fils(wiki_root_dir);
		int ary_len = ary.length; if (ary.length == 0) return null;
		Io_url rv = Find_core_fil__xowa(ary, ary_len, domain_str);
		return rv == null ? Find_core_fil__sqlite3(wiki_root_dir, ary, ary_len, domain_str) : rv;
	}
	private static Io_url Find_core_fil__xowa(Io_url[] ary, int ary_len, String domain_str) {
		// search for ALL ("en.wikipedia.org") or FEW / LOT ("en.wikipedia.org-core")
		for (int i = 0; i < ary_len; i++) {
			Io_url itm = ary[i];
			if (!String_.Eq(itm.Ext(), ".xowa")) continue;
			if	(	String_.Eq(itm.NameOnly(), domain_str)				// EX: "en.wikipedia.org"
				||	String_.Eq(itm.NameOnly(), domain_str + "-core")	// EX: "en.wikipedia.org-core"
				) {
				Xoa_app_.Usr_dlg().Log_many("", "", "wiki.db_core.v2.standard: url=~{0}", itm.Raw());
				return itm;
			}
		}

		// handle old "FEW" layout where "-text" existed, but not "-core"; DB.FEW: DATE:2016-06-07
		for (int i = 0; i < ary_len; i++) {
			Io_url itm = ary[i];
			if (!String_.Eq(itm.Ext(), ".xowa")) continue;
			if	(String_.Eq(itm.NameOnly(), domain_str + "-text")) {	// EX: "en.wikipedia.org-text"
				Xoa_app_.Usr_dlg().Log_many("", "", "wiki.db_core.v2.old_few: url=~{0}", itm.Raw());
				return itm;
			}
		}

		// handle renamed directories; EX: "en.wikipedia.org" -> "en.wikipedia.org-201609"; DATE:2016-10-13
		for (int i = 0; i < ary_len; i++) {
			Io_url itm = ary[i];
			if (!String_.Eq(itm.Ext(), ".xowa")) continue;
			if	(String_.Has_at_end(itm.NameOnly(), "-core")){	// only check "-core" databases. note that this can also include "en.wikipedia.org-file-core.xowa"
				Db_conn core_conn = Db_conn_bldr.Instance.Get_or_fail(itm);

				// if db has "xowa_db" then assume that it is the core
				if (core_conn.Meta_tbl_exists(gplx.xowa.wikis.data.tbls.Xowd_xowa_db_tbl.TBL_NAME)) {
					Xoa_app_.Usr_dlg().Log_many("", "", "wiki.db_core.v2.renamed: url=~{0}", itm.Raw());
					return itm;
				}
			}
		}
		return null;
	}
	private static Io_url Find_core_fil__sqlite3(Io_url wiki_root_dir, Io_url[] ary, int ary_len, String domain_str) {
		Io_url rv = null;
		String v0_str = domain_str + ".000";
		for (int i = 0; i < ary_len; i++) {
			Io_url itm = ary[i];
			if (!String_.Eq(itm.Ext(), ".sqlite3")) continue;
			if	(String_.Eq(itm.NameOnly(), v0_str)) {					// EX: "en.wikipedia.org.000"
				Xoa_app_.Usr_dlg().Log_many("", "", "wiki.db_core.v1: url=~{0}", itm.Raw());
				return itm;
			}
			if (ary_len == 1) {
				Xoa_app_.Usr_dlg().Log_many("", "", "wiki.db_core.custom: url=~{0}", itm.Raw());
				return rv;												// 1 folder and 1 sqlite file; return it; custom wikis?
			}
		}
		Xoa_app_.Usr_dlg().Log_many("", "", "wiki.db_core.none: dir=~{0}", wiki_root_dir.Raw());
		return rv;
	}

	public static boolean Is_core_fil_name(String domain_name, String fil_name) {
		Xow_domain_itm domain_itm = Xow_domain_itm_.parse(Bry_.new_u8(domain_name));
		if (domain_itm.Domain_type_id() == Xow_domain_tid_.Tid__other) {
			return String_.Has_at_end(fil_name, ".xowa");
		}
		String domain_str = domain_itm.Domain_str();
		return	(	String_.Eq(fil_name, domain_str + "-text.xowa")
				||	String_.Eq(fil_name, domain_str + "-core.xowa")
				||	String_.Eq(fil_name, domain_str + ".xowa")
				);
	}

	public static Xow_db_file Make_core_db(Xowd_core_db_props props, Xob_info_session info_session, Io_url wiki_root_dir, String domain_str) {
		String core_file_name = Xow_db_file__core_.Core_file_name(props.Layout_text(), domain_str);
		byte core_db_tid = Xow_db_file__core_.Core_db_tid(props.Layout_text());
		Io_url core_db_url = wiki_root_dir.GenSubFil(core_file_name);
		Db_conn core_conn = Db_conn_bldr.Instance.New(core_db_url);

		// make tbls
		Xow_db_file rv = Xow_db_file.Make(info_session, props, Xow_db_file_.Uid__core, core_db_tid, core_db_url, Xob_info_file.Ns_ids_empty, Xob_info_file.Part_id_1st, core_file_name, core_conn);
		rv.Tbl__db().Create_tbl();
		rv.Tbl__ns().Create_tbl();
		rv.Tbl__site_stats().Create_tbl();
		rv.Tbl__page().Create_tbl();
		if (props.Layout_text().Tid_is_all_or_few()) {	// create in advance else will fail for v2; import wiki -> wiki loads and tries to load categories; v2 category processes and builds tbl; DATE:2015-03-22
			rv.Tbl__cat_core().Create_tbl();
			rv.Tbl__cat_link().Create_tbl();
		}
		return rv;
	}
	private static String Core_file_name(Xow_db_layout layout, String domain_name) {
		switch (layout.Tid()) {
			case Xow_db_layout.Tid__all:		return domain_name + ".xowa";		// EX: en.wikipedia.org.xowa
			case Xow_db_layout.Tid__few:		//return domain_name + "-text.xowa";	// EX: en.wikipedia.org-text.xowa	// DB.FEW: DATE:2016-06-07
			case Xow_db_layout.Tid__lot:		return domain_name + "-core.xowa";	// EX: en.wikipedia.org-core.xowa
			default: 							throw Err_.new_unimplemented();
		}
	}
	public static byte Core_db_tid(Xow_db_layout layout) {
		switch (layout.Tid()) {
			case Xow_db_layout.Tid__all:		return Xow_db_file_.Tid__wiki_solo;
			case Xow_db_layout.Tid__few:		// return Xow_db_file_.Tid__core;	// DB.FEW: DATE:2016-06-07
			case Xow_db_layout.Tid__lot:		return Xow_db_file_.Tid__core;
			default:							throw Err_.new_unimplemented();
		}
	}
}
