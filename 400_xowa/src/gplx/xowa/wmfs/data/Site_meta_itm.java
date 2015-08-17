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
package gplx.xowa.wmfs.data; import gplx.*; import gplx.xowa.*; import gplx.xowa.wmfs.*;
import gplx.xowa.wikis.domains.*;
public class Site_meta_itm {
	public Ordered_hash			General_list() {return general_list;} private final Ordered_hash general_list = Ordered_hash_.new_bry_();
	public Ordered_hash			Namespace_list() {return namespace_list;} private final Ordered_hash namespace_list = Ordered_hash_.new_();
	public Site_statistic_itm	Statistic_itm() {return statistic_itm;} private final Site_statistic_itm statistic_itm = new Site_statistic_itm();
	public Ordered_hash			Interwikimap_list() {return interwikimap_list;} private final Ordered_hash interwikimap_list = Ordered_hash_.new_bry_();
	public List_adp				Namespacealias_list() {return namespacealias_list;} private final List_adp namespacealias_list = List_adp_.new_();
	public Ordered_hash			Specialpagealias_list() {return specialpagealias_list;} private final Ordered_hash specialpagealias_list = Ordered_hash_.new_bry_();
	public Ordered_hash			Library_list() {return library_list;} private final Ordered_hash library_list = Ordered_hash_.new_bry_();
	public Ordered_hash			Extension_list() {return extension_list;} private final Ordered_hash extension_list = Ordered_hash_.new_bry_();
	public Ordered_hash			Skin_list() {return skin_list;} private final Ordered_hash skin_list = Ordered_hash_.new_bry_();
	public Ordered_hash			Magicword_list() {return magicword_list;} private final Ordered_hash magicword_list = Ordered_hash_.new_bry_();
	public Ordered_hash			Functionhook_list() {return functionhook_list;} private final Ordered_hash functionhook_list = Ordered_hash_.new_bry_();
	public Ordered_hash			Showhook_list() {return showhook_list;} private final Ordered_hash showhook_list = Ordered_hash_.new_bry_();
	public Ordered_hash			Extensiontag_list() {return extensiontag_list;} private final Ordered_hash extensiontag_list = Ordered_hash_.new_bry_();
	public Ordered_hash			Protocol_list() {return protocol_list;} private final Ordered_hash protocol_list = Ordered_hash_.new_bry_();
	public Ordered_hash			Defaultoption_list() {return defaultoption_list;} private final Ordered_hash defaultoption_list = Ordered_hash_.new_();
	public Ordered_hash			Language_list() {return language_list;} private final Ordered_hash language_list = Ordered_hash_.new_bry_();
	public static void Build_site_meta(Xowmf_mgr wmf_mgr, Io_url db_url, String[] reqd_ary, DateAdp cutoff) {
		Ordered_hash reqd_hash = Ordered_hash_.new_();
		int reqd_len = reqd_ary.length;
		for (int i = 0; i < reqd_len; ++i)
			reqd_hash.Add_as_key_and_val(reqd_ary[i]);

		Site_core_db json_db = new Site_core_db(db_url);			
		Site_core_itm[] actl_ary = json_db.Tbl__core().Select_all_downloaded(cutoff);
		int actl_len = actl_ary.length;
		for (int i = 0; i < actl_len; ++i) {	// remove items that have been completed after cutoff date
			Site_core_itm actl_itm = actl_ary[i];
			reqd_hash.Del(String_.new_u8(actl_itm.Site_domain()));
		}
		
		reqd_len = reqd_hash.Count();
		Gfo_usr_dlg usr_dlg = Gfo_usr_dlg_.Test_console();
		Xow_wmf_api_mgr api_wkr = new Xow_wmf_api_mgr();
		for (int i = 0; i < reqd_len; ++i) {
			String domain_str = (String)reqd_hash.Get_at(i);
			DateAdp json_date = DateAdp_.Now();
			byte[] json_text = null;
			for (int j = 0; j < 5; ++j) {
				json_text = api_wkr.Api_exec(usr_dlg, wmf_mgr, domain_str, "action=query&format=json&meta=siteinfo&siprop=general|namespaces|statistics|interwikimap|namespacealiases|specialpagealiases|libraries|extensions|skins|magicwords|functionhooks|showhooks|extensiontags|protocols|defaultoptions|languages");
				if (json_text == null)
					gplx.core.threads.Thread_adp_.Sleep(1000);
				else
					break;
			}
			byte[] domain_bry = Bry_.new_u8(domain_str);
			byte[] site_abrv = Xow_domain_abrv_xo_.To_bry(domain_bry);
			json_db.Tbl__core().Insert(site_abrv, domain_bry, Bool_.N, json_date, json_text);
		}

		Site_json_parser json_parser = new Site_json_parser();
		reqd_len = reqd_ary.length;
		for (int i = 0; i < reqd_len; ++i) {
			String domain_str = reqd_ary[i];
			byte[] site_abrv = Xow_domain_abrv_xo_.To_bry(Bry_.new_u8(domain_str));
			Site_core_itm core_itm = json_db.Tbl__core().Select_itm(site_abrv);
			if (core_itm.Json_completed()) continue;
			Site_meta_itm meta_itm = new Site_meta_itm();
			json_parser.Parse_root(meta_itm, String_.new_u8(core_itm.Site_domain()), core_itm.Json_text());
			json_db.Save(meta_itm, site_abrv);
		}
	}
}
