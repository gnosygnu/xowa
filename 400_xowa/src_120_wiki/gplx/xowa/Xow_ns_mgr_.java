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
public class Xow_ns_mgr_ {
	public static final int Ordinal_max = 255;	// ASSUME: no more than 255 ns in a wiki; choosing 255 to align with byte (no particular reason why it needs to be a byte, but better than 500, 1000, etc)
	public static Xow_ns_mgr default_() {	// NOTE: same as en.wikipedia.org's ns circa 2012-01 (currently omitting ns:446,447,710,711)
		Xow_ns_mgr rv = new Xow_ns_mgr();
		rv = rv.Add_new(-2, "Media").Add_new(-1, "Special").Add_new(0, "").Add_new(1, "Talk").Add_new(2, "User").Add_new(3, "User_talk").Add_new(4, "Wikipedia").Add_new(5, "Wikipedia_talk")
			.Add_new(6, "File").Add_new(7, "File_talk").Add_new(8, "MediaWiki").Add_new(9, "MediaWiki_talk").Add_new(10, "Template").Add_new(11, "Template_talk")
			.Add_new(12, "Help").Add_new(13, "Help_talk").Add_new(14, "Category").Add_new(15, "Category_talk").Add_new(100, "Portal").Add_new(101, "Portal_talk").Add_new(108, "Book").Add_new(109, "Book_talk")
			.Add_new(gplx.xowa.xtns.scribunto.Scrib_xtn_mgr.Ns_id_module, gplx.xowa.xtns.scribunto.Scrib_xtn_mgr.Ns_name_module).Add_new(gplx.xowa.xtns.scribunto.Scrib_xtn_mgr.Ns_id_module_talk, gplx.xowa.xtns.scribunto.Scrib_xtn_mgr.Ns_name_module_talk)
			.Add_defaults()
			;
		rv.Init();
		return rv;
	}
	public static void rebuild_(Xol_lang lang, Xow_ns_mgr ns_mgr) {
		Xol_ns_grp ns_names = lang.Ns_names();
		int ns_names_len = ns_names.Len();
		for (int i = 0; i < ns_names_len; i++) {
			Xow_ns ns_name = ns_names.Get_at(i);
			int ns_id = ns_name.Id();
			Xow_ns ns = ns_mgr.Ids_get_or_null(ns_id);
			ns.Name_bry_(ns_name.Name_bry());
		}
		ns_names = lang.Ns_aliases();
		ns_names_len = ns_names.Len();
		for (int i = 0; i < ns_names_len; i++) {
			Xow_ns ns_name = ns_names.Get_at(i);
			int ns_id = ns_name.Id();
			ns_mgr.Aliases_add(ns_id, ns_name.Name_str());
		}
		ns_mgr.Init();
	}
}
