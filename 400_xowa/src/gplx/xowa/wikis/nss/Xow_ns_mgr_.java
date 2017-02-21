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
package gplx.xowa.wikis.nss; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*;
import gplx.xowa.langs.*; import gplx.xowa.langs.cases.*; import gplx.xowa.langs.bldrs.*;
public class Xow_ns_mgr_ {
	public static final int Ordinal_max = 255;	// ASSUME: no more than 255 ns in a wiki; choosing 255 to align with byte (no particular reason why it needs to be a byte, but better than 500, 1000, etc)
	public static Xow_ns_mgr default_(Xol_case_mgr case_mgr) {	// NOTE: same as en.wikipedia.org's ns circa 2012-01 (currently omitting ns:446,447,710,711)
		Xow_ns_mgr rv = new Xow_ns_mgr(case_mgr);
		rv = rv.Add_new(-2, "Media").Add_new(-1, "Special").Add_new(0, "").Add_new(1, "Talk").Add_new(2, "User").Add_new(3, "User_talk").Add_new(4, "Wikipedia").Add_new(5, "Wikipedia_talk")
			.Add_new(6, "File").Add_new(7, "File_talk").Add_new(8, "MediaWiki").Add_new(9, "MediaWiki_talk").Add_new(10, "Template").Add_new(11, "Template_talk")
			.Add_new(12, "Help").Add_new(13, "Help_talk").Add_new(14, "Category").Add_new(15, "Category_talk").Add_new(100, "Portal").Add_new(101, "Portal_talk").Add_new(108, "Book").Add_new(109, "Book_talk")
			.Add_new(Xow_ns_.Tid__module, Xow_ns_.Key__module).Add_new(Xow_ns_.Tid__module_talk, Xow_ns_.Key__module_talk)
			.Add_defaults()
			;
		rv.Init();
		return rv;
	}
	public static void rebuild_(Xol_lang_itm lang, Xow_ns_mgr ns_mgr) {
		Xol_ns_grp ns_names = lang.Ns_names();
		int ns_names_len = ns_names.Len();
		for (int i = 0; i < ns_names_len; i++) {
			Xow_ns ns_name = ns_names.Get_at(i);
			int ns_id = ns_name.Id();
			Xow_ns ns = ns_mgr.Ids_get_or_null(ns_id);
			if (ns == null) continue; // ns_id of -2 will not be found in site_ns
			ns.Name_bry_(ns_name.Name_db());
		}
		ns_names = lang.Ns_aliases();
		ns_names_len = ns_names.Len();
		for (int i = 0; i < ns_names_len; i++) {
			Xow_ns ns_name = ns_names.Get_at(i);
			int ns_id = ns_name.Id();
			ns_mgr.Aliases_add(ns_id, ns_name.Name_db_str());
		}
		ns_mgr.Init();
	}
}
