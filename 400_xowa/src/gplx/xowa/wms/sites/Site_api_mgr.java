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
package gplx.xowa.wms.sites; import gplx.*; import gplx.xowa.*; import gplx.xowa.wms.*;
import gplx.dbs.cfgs.*;
import gplx.core.net.*;
import gplx.langs.jsons.*;
import gplx.xowa.wikis.domains.*;
public class Site_api_mgr {
	private final Json_parser json_parser = new Json_parser();
	private final Site_json_parser site_parser;
	public Site_api_mgr() {
		this.site_parser = new Site_json_parser(json_parser);
	}
	public void Load_wiki(Gfo_usr_dlg usr_dlg, Gfo_inet_conn inet_conn, String domain_str, Db_cfg_tbl cfg_tbl) {
		// Load_wiki_by_inet(usr_dlg, inet_conn, domain_str, cfg_tbl);
//			Site_meta_itm meta_itm = new Site_meta_itm();
//			site_parser.Parse_root(meta_itm, domain_str, json_text);
	}
	public void Load_all(Gfo_usr_dlg usr_dlg, Gfo_inet_conn inet_conn, Io_url db_url, String[] reqd_ary, DateAdp cutoff) {
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
		for (int i = 0; i < reqd_len; ++i) {
			String domain_str = (String)reqd_hash.Get_at(i);
			DateAdp json_date = DateAdp_.Now();
			byte[] json_text = null;
			for (int j = 0; j < 5; ++j) {
				json_text = Xowm_api_mgr.Call_api(usr_dlg, inet_conn, domain_str, "action=query&format=json&meta=siteinfo&siprop=general|namespaces|statistics|interwikimap|namespacealiases|specialpagealiases|libraries|extensions|skins|magicwords|functionhooks|showhooks|extensiontags|protocols|defaultoptions|languages");
				if (json_text == null)
					gplx.core.threads.Thread_adp_.Sleep(1000);
				else
					break;
			}
			byte[] domain_bry = Bry_.new_u8(domain_str);
			byte[] site_abrv = Xow_abrv_xo_.To_bry(domain_bry);
			json_db.Tbl__core().Insert(site_abrv, domain_bry, Bool_.N, json_date, json_text);
		}

		reqd_len = reqd_ary.length;
		for (int i = 0; i < reqd_len; ++i) {
			String domain_str = reqd_ary[i];
			byte[] site_abrv = Xow_abrv_xo_.To_bry(Bry_.new_u8(domain_str));
			Site_core_itm core_itm = json_db.Tbl__core().Select_itm(site_abrv);
			if (core_itm.Json_completed()) continue;
			Site_meta_itm meta_itm = new Site_meta_itm();
			site_parser.Parse_root(meta_itm, String_.new_u8(core_itm.Site_domain()), core_itm.Json_text());
			json_db.Save(meta_itm, site_abrv);
		}
	}
}
