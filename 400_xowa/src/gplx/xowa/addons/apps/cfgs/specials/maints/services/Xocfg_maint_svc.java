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
package gplx.xowa.addons.apps.cfgs.specials.maints.services; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.apps.*; import gplx.xowa.addons.apps.cfgs.*; import gplx.xowa.addons.apps.cfgs.specials.*; import gplx.xowa.addons.apps.cfgs.specials.maints.*;
import gplx.xowa.addons.apps.cfgs.dbs.*; import gplx.xowa.addons.apps.cfgs.dbs.tbls.*;
import gplx.xowa.addons.apps.cfgs.enums.*;
public class Xocfg_maint_svc {
	private Xoa_app app;
	public Xocfg_maint_svc(Xoa_app app) {
		this.app = app;
	}
	public void Upsert(String raw) {
		// get wikitext parser
		Xowe_wiki wiki = (Xowe_wiki)app.User().Wikii();
		gplx.xowa.parsers.Xow_parser_mgr parser_mgr = wiki.Parser_mgr();

		// parse raw
		Xocfg_maint_parser maint_parser = new Xocfg_maint_parser();
		Xocfg_maint_nde[] ndes = maint_parser.Parse(raw);

		// exec
		Xocfg_db_app db_app = Xocfg_db_app.New(app);
		db_app.Conn().Txn_bgn("xo__cfg_maint__upsert");
		byte[] anch_find_bry = Bry_.new_a7("<a "), anch_repl_bry = Bry_.new_a7("<a tabindex=\"-1\" ");
		for (Xocfg_maint_nde nde : ndes) {
			byte[] help = parser_mgr.Main().Parse_text_to_html(parser_mgr.Ctx(), Bry_.new_u8(nde.Help()));
			help = Bry_.Replace(help, anch_find_bry, anch_repl_bry);	// replace "<a " with "<a tabindex=-1 " else tabbing will go to hidden anchors in help text
			if (nde.Type_is_grp()) {
				Xocfg_maint_svc.Create_grp(db_app, nde.Id(), nde.Key(), nde.Owner(), nde.Name(), String_.new_u8(help));
			}
			else {
				Xocfg_maint_itm itm = (Xocfg_maint_itm)nde;
				Xocfg_maint_svc.Create_itm(db_app, nde.Id(), nde.Key(), nde.Owner(), nde.Name(), String_.new_u8(help), itm.Scope(), itm.Db_type(), itm.Dflt(), itm.Gui_type(), itm.Gui_args(), itm.Gui_cls());
			}
		}
		db_app.Conn().Txn_end();
	}
	public static void Create_grp(Xocfg_db_app db_app, int grp_id, String key, String owner, String name, String help) {
		// insert grp_meta
		db_app.Tbl__grp().Upsert(grp_id, key);

		// insert grp_map
		int owner_id = String_.Len_eq_0(owner) ? Xocfg_grp_row.Id__root : db_app.Tbl__grp().Select_id_by_key_or_fail(owner);
		int map_sort = db_app.Tbl__map().Select_sort_or_next(owner_id, grp_id);
		db_app.Tbl__map().Upsert(owner_id, grp_id, map_sort);

		// insert nde_i18n
		db_app.Tbl__txt().Upsert(grp_id, Xoitm_lang_tid.Lang__dflt, name, help);
	}
	public static void Create_itm(Xocfg_db_app db_app, int itm_id, String key, String owner, String name, String help, String scope, String db_type, String dflt, String gui_type, String gui_args, String gui_cls) {
		// insert itm_meta
		int grp_id = db_app.Tbl__grp().Select_id_by_key_or_fail(owner);
		int scope_id = Xoitm_scope_tid.To_int(scope);
		int gui_type_id = Xoitm_gui_tid.To_uid(gui_type);
		db_app.Tbl__itm().Upsert(itm_id, scope_id, db_type, gui_type_id, gui_args, gui_cls, key, dflt);

		// insert grp_map
		int itm_sort = db_app.Tbl__map().Select_sort_or_next(grp_id, itm_id);
		db_app.Tbl__map().Upsert(grp_id, itm_id, itm_sort);

		// insert nde_i18n
		db_app.Tbl__txt().Upsert(itm_id, Xoitm_lang_tid.Lang__dflt, name, help);
	}
}
