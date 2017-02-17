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
package gplx.xowa.addons.wikis.directorys.specials.lists; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.directorys.*; import gplx.xowa.addons.wikis.directorys.specials.*;
import gplx.dbs.*; import gplx.dbs.cfgs.*; import gplx.dbs.sys.*;
import gplx.langs.jsons.*;
import gplx.xowa.addons.wikis.directorys.dbs.*;
class Xowdir_list_svc {
	private final    Xoa_app app;
	private gplx.xowa.guis.cbks.Xog_cbk_trg cbk_trg = gplx.xowa.guis.cbks.Xog_cbk_trg.New(Xowdir_list_special.Prototype.Special__meta().Ttl_bry());
	public Xowdir_list_svc(Xoa_app app) {
		this.app = app;
	}
	public void Import_wiki(Json_nde args) {Import_wiki(args.Get_as_str("url"));}
	public void Import_wiki(String url) {
		// verify file is sqlite
		Io_url core_db_url = Io_url_.new_fil_(url);
		Db_conn core_db_conn = Db_conn_bldr.Instance.Get_or_noop(core_db_url);
		if (core_db_conn == Db_conn_.Noop) {
			app.Gui__cbk_mgr().Send_notify(cbk_trg, "file is not a .xowa file: file=" + url);
			return;
		}

		// verify file is a core_db
		if (!core_db_conn.Meta_tbl_exists(gplx.xowa.wikis.data.tbls.Xowd_xowa_db_tbl.TBL_NAME)) {
			app.Gui__cbk_mgr().Send_notify(cbk_trg, "file is not a .xowa file or missing xowa_db table: file=" + url);
			return;
		}
		
		// get wiki_props from core_db.xowa_cfg
		Xowdir_wiki_props_mgr wiki_props_mgr = Xowdir_wiki_props_mgr_.New_xowa(app, core_db_url);
		Xowdir_wiki_props wiki_props = wiki_props_mgr.Verify(Bool_.Y, core_db_url.NameOnly(), core_db_url);
		String domain    = wiki_props.Domain();

		// if same domain exists; return
		Xowdir_db_mgr db_mgr = new Xowdir_db_mgr(app.User().User_db_mgr().Conn());
		if (db_mgr.Tbl__wiki().Select_by_key_or_null(domain) != null) {
			app.Gui__cbk_mgr().Send_notify(cbk_trg, "wiki with same name already exists; domain=" + domain);
			return;
		}

		// add it to user_wiki
		int id = Xowdir_db_utl.Wiki_id__next(app);
		db_mgr.Tbl__wiki().Upsert(id, domain, core_db_url, new Xowdir_wiki_json(wiki_props.Name()).To_str(new Json_wtr()));

		// add it to personal wikis
		gplx.xowa.addons.wikis.directorys.specials.items.bldrs.Xow_wiki_factory.Load_personal((Xoae_app)app, Bry_.new_u8(domain), core_db_url.OwnerDir());

		// navigate to it
		app.Gui__cbk_mgr().Send_redirect(cbk_trg, "/wiki/" + String_.new_u8(cbk_trg.Page_ttl()));
	}
}
