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
import gplx.langs.jsons.*;
import gplx.dbs.sys.*; import gplx.xowa.addons.users.wikis.regys.dbs.*; import gplx.xowa.addons.users.wikis.regys.specials.itms.bldrs.*;
import gplx.xowa.wikis.domains.*; import gplx.xowa.wikis.nss.*; import gplx.xowa.wikis.data.*;
import gplx.xowa.langs.cases.*;
class Xouw_itm_mgr {
	private final    Xoa_app app;
	public Xouw_itm_mgr(Xoa_app app) {
		this.app = app;
	}
	public void Save(Json_nde args) {
		Save(args.Get_as_int("id"), args.Get_as_str("domain"), args.Get_as_str("name"), args.Get_as_str("file"));
	}
	public void Save(int id, String domain, String name, String url_str) {
		boolean itm_is_new = false;
		// get next id if new
		if (id == -1) {
			itm_is_new = true;
			Db_sys_mgr sys_mgr = new Db_sys_mgr(app.User().User_db_mgr().Conn());
			id = sys_mgr.Autonum_next("user.wikis.id");
		}

		// insert into user_wiki
		Xouw_db_mgr db_mgr = new Xouw_db_mgr(app.User().User_db_mgr().Conn());
		db_mgr.Tbl__wiki().Upsert(id, domain, name, url_str);
		// if (itm_is_new) Xow_db_mkr.Create_wiki(app, domain, url_str);
		if (itm_is_new) Xow_db_mkr.Create_wiki2(new Xodb_wiki_data(domain, Io_url_.new_fil_(url_str)));
	}
}
