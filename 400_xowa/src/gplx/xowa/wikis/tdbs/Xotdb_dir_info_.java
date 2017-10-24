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
package gplx.xowa.wikis.tdbs; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*;
public class Xotdb_dir_info_ {
	public static String Tid_name(byte tid) {
		switch (tid) {
			case Xotdb_dir_info_.Tid_page:					return Xotdb_dir_info_.Name_page;
			case Xotdb_dir_info_.Tid_ttl:					return Xotdb_dir_info_.Name_title;
			case Xotdb_dir_info_.Tid_id:					return Xotdb_dir_info_.Name_id;
			case Xotdb_dir_info_.Tid_category:				return Xotdb_dir_info_.Name_category;
			case Xotdb_dir_info_.Tid_category2_link:		return Xotdb_dir_info_.Name_category2_link;
			case Xotdb_dir_info_.Tid_category2_main:		return Xotdb_dir_info_.Name_category2_main;
			case Xotdb_dir_info_.Tid_search_ttl:			return Xotdb_dir_info_.Name_search_ttl;
			default:										throw Err_.new_unhandled(tid);
		}
	}
	public static Xotdb_dir_info[] regy_() {
		Xotdb_dir_info[] rv = new Xotdb_dir_info[5];
		regy_itm_(rv, Bool_.Y, Tid_page);
		regy_itm_(rv, Bool_.Y, Tid_ttl);
		regy_itm_(rv, Bool_.N, Tid_id);
		regy_itm_(rv, Bool_.N, Tid_category);
		regy_itm_(rv, Bool_.N, Tid_search_ttl);
		return rv;
	}
	public static boolean Dir_name_is_tdb(String dir_name) {
		return String_.In(dir_name, Name_ns, Name_site, Name_cfg, "tmp");
	}
	private static void regy_itm_(Xotdb_dir_info[] rv, boolean ns_root, byte id) {rv[id] = new Xotdb_dir_info(ns_root, id, Tid_name(id));}
	public static final String Ext_xdat = ".xdat", Ext_csv = ".csv", Ext_zip = ".zip"
		, Name_ns = "ns", Name_site = "site", Name_page = "page", Name_title = "title", Name_id = "id", Name_category = "category", Name_search_ttl = "search_title", Name_zip_suffix = "_zip"
		, Name_cfg = "cfg"
		, Name_reg_fil = "reg.csv", Name_category2 = "category2", Name_category2_link = "link", Name_category2_main = "main"
		;
	public static final byte[] Bry_xdat = Bry_.new_a7(Ext_xdat), Bry_csv = Bry_.new_a7(Ext_csv), Bry_zip = Bry_.new_a7(Ext_zip);
	public static final byte
		Tid_page				= 0
		, Tid_ttl 				= 1
		, Tid_id 				= 2
		, Tid_category			= 3
		, Tid_search_ttl		= 4
//			, Tid_category2 		= 5
		, Tid_category2_link 	= 5
		, Tid_category2_main 	= 6
		;
	public static final byte Regy_tid_max = 7;
}
