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
package gplx.xowa.addons.wikis.directorys.dbs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.directorys.*;
import gplx.dbs.*; import gplx.dbs.cfgs.*;
import gplx.langs.jsons.*;
class Xowdir_wiki_props_mgr__xowa extends Xowdir_wiki_props_mgr__base {
	private final    Xoa_app app;
	private final    Io_url core_db_url;
	private Db_cfg_tbl      wiki_cfg_tbl;
	private Xowdir_wiki_tbl user_reg_tbl;
	public Xowdir_wiki_props_mgr__xowa(Xoa_app app, Io_url core_db_url) {super(Gfo_usr_dlg_.Instance);
		this.app = app;
		this.core_db_url = core_db_url;
	}
	private void Wiki_cfg__assert_tbl() {
		if (wiki_cfg_tbl == null) {
			Db_conn core_db_conn = Db_conn_bldr.Instance.Get_or_noop(core_db_url);
			wiki_cfg_tbl = new Db_cfg_tbl(core_db_conn, gplx.xowa.wikis.data.Xowd_cfg_tbl_.Tbl_name);
		}
	}
	@Override public void Wiki_cfg__upsert(String key, String val) {
		Wiki_cfg__assert_tbl();
		wiki_cfg_tbl.Upsert_str(gplx.xowa.wikis.data.Xowd_cfg_key_.Grp__wiki_init, key, val);
	}
	@Override public String Wiki_cfg__select_or(String key, String or) {
		Wiki_cfg__assert_tbl();
		return wiki_cfg_tbl.Select_str_or(gplx.xowa.wikis.data.Xowd_cfg_key_.Grp__wiki_init, key, or);
	}
	private void User_reg__assert_tbl() {
		if (user_reg_tbl == null) {
			Xowdir_db_mgr user_db_mgr = new Xowdir_db_mgr(app.User().User_db_mgr().Conn());
			user_reg_tbl = user_db_mgr.Tbl__wiki();
		}
	}
	@Override public void User_reg__upsert(String domain, String val)  {
		User_reg__assert_tbl();
//			user_db_mgr.Tbl__wiki().Update_by_key_or_null(domain, val);
	}
	@Override public String User_reg__select(String domain) {
		User_reg__assert_tbl();
		Xowdir_wiki_itm user_wiki_row = user_reg_tbl.Select_by_key_or_null(domain);
		return user_wiki_row.Json().To_str(new Json_wtr());
	}
}
