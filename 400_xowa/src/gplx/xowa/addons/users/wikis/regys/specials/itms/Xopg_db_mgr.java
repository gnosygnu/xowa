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
package gplx.xowa.addons.users.wikis.regys.specials.itms; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.users.*; import gplx.xowa.addons.users.wikis.*; import gplx.xowa.addons.users.wikis.regys.*; import gplx.xowa.addons.users.wikis.regys.specials.*;
import gplx.dbs.*; import gplx.dbs.cfgs.*;
import gplx.xowa.wikis.dbs.*; import gplx.xowa.wikis.data.*; import gplx.xowa.wikis.data.tbls.*;	
import gplx.xowa.addons.users.wikis.regys.dbs.*;
import gplx.xowa.addons.users.wikis.regys.specials.itms.bldrs.*;
public class Xopg_db_mgr {
	public static int Create(Xodb_wiki_db core_db, int ns_id, byte[] ttl_page_db, byte[] text_raw) {
		Db_cfg_tbl cfg_tbl = Db_cfg_tbl.Get_by_key(core_db, Xowd_cfg_tbl_.Tbl_name);

		int page_id = cfg_tbl.Select_int_or("db", "page.id_next", -1);
		Xowd_page_tbl page_tbl = Xowd_page_tbl.Get_by_key(core_db);
		Xowd_text_tbl text_tbl = Xowd_text_tbl.Get_by_key(core_db);
		byte[] text_zip = text_tbl.Zip(text_raw);
		boolean redirect = Bool_.N;

		// do insert
		page_tbl.Insert_bgn();
		text_tbl.Insert_bgn();
		Xowd_site_ns_tbl ns_tbl = Xowd_site_ns_tbl.Get_by_key(core_db);
		int ns_count = ns_tbl.Select_ns_count(ns_id) + 1;
		try {
			page_tbl.Insert_cmd_by_batch(page_id, ns_id, ttl_page_db, redirect, Datetime_now.Get(), text_raw.length, ns_count, 0, -1);
			text_tbl.Insert_cmd_by_batch(page_id, text_zip);
			Xowd_site_ns_tbl.Get_by_key(core_db).Update_ns_count(ns_id, ns_count);
		} finally {
			page_tbl.Insert_end();
			text_tbl.Insert_end();
		}
		return page_id;
	}
}
