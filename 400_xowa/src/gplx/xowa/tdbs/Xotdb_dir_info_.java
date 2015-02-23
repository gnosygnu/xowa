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
package gplx.xowa.tdbs; import gplx.*; import gplx.xowa.*;
public class Xotdb_dir_info_ {
	public static String Tid_name(byte tid) {
		switch (tid) {
			case Xotdb_dir_info_.Tid_page:				return Xotdb_dir_info_.Name_page;
			case Xotdb_dir_info_.Tid_ttl:					return Xotdb_dir_info_.Name_title;
			case Xotdb_dir_info_.Tid_id:					return Xotdb_dir_info_.Name_id;
			case Xotdb_dir_info_.Tid_category:			return Xotdb_dir_info_.Name_category;
			case Xotdb_dir_info_.Tid_category2_link:		return Xotdb_dir_info_.Name_category2_link;
			case Xotdb_dir_info_.Tid_category2_main:		return Xotdb_dir_info_.Name_category2_main;
			case Xotdb_dir_info_.Tid_search_ttl:			return Xotdb_dir_info_.Name_search_ttl;
			default:									throw Err_.unhandled(tid);
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
	private static void regy_itm_(Xotdb_dir_info[] rv, boolean ns_root, byte id) {rv[id] = new Xotdb_dir_info(ns_root, id, Tid_name(id));}
	public static final String Ext_xdat = ".xdat", Ext_csv = ".csv", Ext_zip = ".zip"
		, Name_ns = "ns", Name_site = "site", Name_page = "page", Name_title = "title", Name_id = "id", Name_category = "category", Name_search_ttl = "search_title", Name_zip_suffix = "_zip"
		, Name_cfg = "cfg"
		, Name_reg_fil = "reg.csv", Name_category2 = "category2", Name_category2_link = "link", Name_category2_main = "main"
		;
	public static final byte[] Bry_xdat = Bry_.new_ascii_(Ext_xdat), Bry_csv = Bry_.new_ascii_(Ext_csv), Bry_zip = Bry_.new_ascii_(Ext_zip);
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
