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
import gplx.dbs.*;
import gplx.core.ios.streams.*;
import gplx.xowa.wikis.*; import gplx.xowa.wikis.data.*; import gplx.xowa.wikis.nss.*;
import gplx.xowa.bldrs.infos.*;
import gplx.xowa.langs.*;
public class Xow_wiki_ {
	public static void Create(Xoa_app app, Xow_ns_mgr ns_mgr, String domain, Io_url core_url) {
		Xowd_core_db_props props = new Xowd_core_db_props( 1, Xow_db_layout.Itm_all, Xow_db_layout.Itm_all, Xow_db_layout.Itm_all, Io_stream_tid_.Tid__raw, Io_stream_tid_.Tid__raw, Bool_.N, Bool_.N);
		Xow_db_file core_db = Xow_db_file__core_.Make_core_db(props, Xob_info_session.new_(app.User().Key(), domain, domain), core_url, domain);
		core_db.Tbl__text().Create_tbl();
		core_db.Tbl__site_stats().Update(0, 0, 0);	// save page stats
		core_db.Tbl__ns().Insert(ns_mgr); // save ns
		core_db.Tbl__cfg().Insert_str(Xow_cfg_consts.Grp__wiki_init, Xow_cfg_consts.Key__init__main_page, "Main_Page");
		core_db.Tbl__cfg().Insert_str(Xow_cfg_consts.Grp__wiki_init, Xow_cfg_consts.Key__init__modified_latest, Datetime_now.Get().XtoStr_fmt(DateAdp_.Fmt_iso8561_date_time));
	}
	public static void Create_sql_backend(Xow_wiki wiki, gplx.xowa.wikis.data.Xowd_core_db_props core_db_props, gplx.xowa.bldrs.infos.Xob_info_session session) {
		if (wiki.Type_is_edit()) {
			Xowe_wiki wikie = (Xowe_wiki)wiki;
			wikie.Db_mgr_create_as_sql();	// edit-wikis are created with text-backend; convert to sql
			wiki.Data__core_mgr().Init_by_make(core_db_props, session);	// make core_db			
		}
		else {
			Xowv_wiki wikiv = (Xowv_wiki)wiki;
			wikiv.Init_by_make(core_db_props, session);	// make core_db			
		}
	}
}
