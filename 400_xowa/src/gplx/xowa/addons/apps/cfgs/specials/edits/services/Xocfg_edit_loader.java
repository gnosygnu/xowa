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
package gplx.xowa.addons.apps.cfgs.specials.edits.services; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.apps.*; import gplx.xowa.addons.apps.cfgs.*; import gplx.xowa.addons.apps.cfgs.specials.*; import gplx.xowa.addons.apps.cfgs.specials.edits.*;
import gplx.dbs.*;
import gplx.xowa.addons.apps.cfgs.dbs.*; import gplx.xowa.addons.apps.cfgs.dbs.tbls.*;
import gplx.xowa.addons.apps.cfgs.specials.edits.objs.*;
public class Xocfg_edit_loader {
	private final    Xocfg_db_app db_app;
	private final    Xocfg_db_usr db_usr;
	public Xocfg_edit_loader(Xocfg_db_app db_app, Xocfg_db_usr db_usr) {
		this.db_app = db_app;
		this.db_usr = db_usr;
	}
	public Xoedit_root Load_root(String grp_key, String ctx, String lang) {
		// create lists
		Ordered_hash grp_temp = Ordered_hash_.New();
		Xoedit_nde_hash grp_list = new Xoedit_nde_hash();
		Xoedit_nde_hash itm_list = new Xoedit_nde_hash();

		// get root_itm
		Xocfg_grp_row grp_meta = db_app.Tbl__grp().Select_by_key_or_null(grp_key);
		if (grp_meta == null) throw Err_.new_wo_type("cfg:grp not found", "grp", grp_key);
		Xoedit_grp owner = new Xoedit_grp(grp_meta.Id(), 0, grp_meta.Key());
		grp_temp.Add(grp_meta.Id(), owner);
		grp_list.Add(owner);

		// load tree by selecting subs until no more grps
		while (grp_temp.Count() > 0) {
			owner = (Xoedit_grp)grp_temp.Get_at(0);
			grp_temp.Del(owner.Id());
			Load_subs(grp_list, itm_list, grp_temp, owner);
		}

		// load itms and i18n
		Load_itm_meta(itm_list);
		Load_itm_data(itm_list, ctx);
		Load_i18n(grp_list, itm_list, lang);

		grp_list.Delete_container_grps();
		return new Xoedit_root(Load_nav_mgr(grp_key), (Xoedit_grp[])grp_list.To_grp_ary_and_clear());
	}
	private Xoedit_nav_mgr Load_nav_mgr(String grp_key) {
		// get grp_id
		String sql = Db_sql_.Make_by_fmt(String_.Ary
		( "SELECT  grp_id"
		, "FROM    cfg_grp"
		, "WHERE   grp_key = '{0}'"
		), grp_key
		);
		int grp_id = -1;
		Db_rdr rdr = db_app.Conn().Stmt_sql(sql).Exec_select__rls_auto();
		try		{grp_id = rdr.Move_next() ? rdr.Read_int("grp_id") : -1;}
		finally {rdr.Rls();}

		// get owner_id
		sql = Db_sql_.Make_by_fmt(String_.Ary
		( "SELECT  map_src"
		, "FROM    cfg_map"
		, "WHERE   map_trg = {0}"
		), grp_id
		);
		int owner_id = -1;
		rdr = db_app.Conn().Stmt_sql(sql).Exec_select__rls_auto();
		try		{owner_id = rdr.Move_next() ? rdr.Read_int("map_src") : -1;}
		finally {rdr.Rls();}

		// get peers
		sql = Db_sql_.Make_by_fmt(String_.Ary
		( "SELECT  m.map_trg"
		, ",       m.map_sort"
		, ",       n.nde_name"
		, ",       t.grp_key"
		, "FROM    cfg_map m"
		, "        LEFT JOIN cfg_grp t ON m.map_trg = t.grp_id"
		, "        LEFT JOIN cfg_txt n ON m.map_trg = n.nde_id"
		, "WHERE   m.map_src = {0}"
		, "ORDER BY m.map_sort"
		), owner_id
		);
		List_adp list = List_adp_.New();
		try {
			rdr = db_app.Conn().Stmt_sql(sql).Exec_select__rls_auto();
			while (rdr.Move_next()) {
				String nav_key = rdr.Read_str("grp_key");
				String nav_text = rdr.Read_str("nde_name");
				list.Add(new Xoedit_nav_itm(String_.Eq(grp_key, nav_key), nav_key, nav_text));
			}
		}
		finally {
			rdr.Rls();
		}
		return new Xoedit_nav_mgr((Xoedit_nav_itm[])list.To_ary_and_clear(Xoedit_nav_itm.class));
	}
	private void Load_subs(Xoedit_nde_hash grp_list, Xoedit_nde_hash itm_list, Ordered_hash grp_temp, Xoedit_grp owner) {
		String sql = Db_sql_.Make_by_fmt(String_.Ary
		( "SELECT  m.map_trg"
		, ",       m.map_sort"
		, ",       Coalesce(g.grp_key, i.itm_key) AS key"
		, ",       i.itm_dflt"
		, "FROM    cfg_map m"
		, "        LEFT JOIN cfg_grp g ON m.map_trg = g.grp_id"
		, "        LEFT JOIN cfg_itm i ON m.map_trg = i.itm_id"
		, "WHERE   m.map_src = {0}"
		), owner.Id()
		);
		Db_rdr rdr = db_app.Conn().Stmt_sql(sql).Exec_select__rls_auto();
		List_adp itms_list = List_adp_.New();
		while (rdr.Move_next()) {
			String grp_key = rdr.Read_str("key");
			// nde is grp
			if (rdr.Read_str("itm_dflt") == null) {
				Xoedit_grp gui_grp = new Xoedit_grp(rdr.Read_int("map_trg"), rdr.Read_int("map_sort"), grp_key);
				owner.Grps__add(gui_grp);
				grp_list.Add(gui_grp);
				grp_temp.Add(gui_grp.Id(), gui_grp);
			}
			// nde is itm
			else {
				Xoedit_itm gui_itm = new Xoedit_itm(rdr.Read_int("map_trg"), rdr.Read_int("map_sort"), grp_key);
				itms_list.Add(gui_itm);
				itm_list.Add(gui_itm);
			}
		}
		owner.Itms_((Xoedit_itm[])itms_list.To_ary_and_clear(Xoedit_itm.class));
	}
	private void Load_itm_meta(Xoedit_nde_hash itm_list) {
		Xogui_nde_iter iter = Xogui_nde_iter.New_sql(itm_list);
		while (iter.Move_next()) {
			String sql = Db_sql_.Make_by_fmt(String_.Ary
			( "SELECT  t.itm_id"
			, ",       t.itm_key"
			, ",       t.itm_scope_id"
			, ",       t.itm_gui_type"
			, ",       t.itm_gui_args"
			, ",       t.itm_dflt"
			, "FROM    cfg_itm t"
			, "WHERE   t.itm_key IN ({0})"
			), iter.To_sql_in_key()
			);

			Db_rdr rdr = db_app.Conn().Stmt_sql(sql).Exec_select__rls_auto();
			while (rdr.Move_next()) {
				Xoedit_itm gui_itm = (Xoedit_itm)itm_list.Get_by_or_fail(rdr.Read_str("itm_key"));
				gui_itm.Load_by_meta(rdr.Read_str("itm_key"), rdr.Read_int("itm_scope_id"), rdr.Read_int("itm_gui_type"), rdr.Read_str("itm_gui_args"), rdr.Read_str("itm_dflt"));
			}
		}
	}
	private void Load_itm_data(Xoedit_nde_hash itm_list, String... ctxs) {
		Xoedit_nde_hash cur_regy = new Xoedit_nde_hash().Merge(itm_list);

		// loop ctxs where later ctxs are more general defaults; EX: ["en.w", "en.*", "*.w", "app"]
		int ctxs_len = ctxs.length;
		for (int i = 0; i < ctxs_len; i++) {
			// create iter for cur_regy; note that iter will be reduced as items are found below
			Xogui_nde_iter cur_iter = Xogui_nde_iter.New_sql(cur_regy);
			while (cur_iter.Move_next()) {
				// get all data by ids and ctx
				String sql = Db_sql_.Make_by_fmt(String_.Ary
				( "SELECT  d.itm_key"
				, ",       d.itm_ctx"
				, ",       d.itm_val"
				, ",       d.itm_date"
				, "FROM    cfg_val d"
				, "WHERE   d.itm_key IN ({0})"
				, "AND     d.itm_ctx = '{1}'"
				), cur_iter.To_sql_in_key(), ctxs[i]
				);

				// read and set data
				Db_rdr rdr = db_usr.Conn().Stmt_sql(sql).Exec_select__rls_auto();
				while (rdr.Move_next()) {
					Xoedit_itm gui_itm = (Xoedit_itm)cur_regy.Get_by_or_fail(rdr.Read_str("itm_key"));
					gui_itm.Load_by_data(rdr.Read_str("itm_ctx"), rdr.Read_str("itm_val"), rdr.Read_str("itm_date"));
					cur_regy.Deleted__add(gui_itm);
				}
			}
			cur_regy.Deleted__commit();
		}

		// loop over remaining items and set to dflts
		int cur_len = cur_regy.Len();
		for (int i = 0; i < cur_len; i++) {
			Xoedit_itm itm = (Xoedit_itm)cur_regy.Get_at(i);
			itm.Set_data_by_dflt();
		}
	}
	private void Load_i18n(Xoedit_nde_hash grp_list, Xoedit_nde_hash itm_list, String... langs) {
		Xoedit_nde_hash cur_regy = new Xoedit_nde_hash().Merge(grp_list).Merge(itm_list);

		// loop langs where later langs are fallbacks; EX: ["de", "en"]
		int langs_len = langs.length;
		for (int i = 0; i < langs_len; i++) {
			// create iter for cur_regy; note that iter will be reduced as items are found below
			Xogui_nde_iter cur_iter = Xogui_nde_iter.New_sql(cur_regy);
			while (cur_iter.Move_next()) {
				// get all i18n for itms and lang
				String sql = Db_sql_.Make_by_fmt(String_.Ary
				( "SELECT  h.nde_id"
				, ",       h.nde_name"
				, ",       h.nde_help"
				, ",       h.nde_lang"
				, "FROM    cfg_txt h"
				, "WHERE   h.nde_id IN ({0})"
				, "AND     h.nde_lang = '{1}'"
				), cur_iter.To_sql_in()
				, langs[i]
				);

				// read and set i18n
				Db_rdr rdr = db_app.Conn().Stmt_sql(sql).Exec_select__rls_auto();
				while (rdr.Move_next()) {
					Xoedit_nde gui_itm = (Xoedit_nde)cur_regy.Get_by_or_fail(rdr.Read_int("nde_id"));
					gui_itm.Load_by_i18n(rdr.Read_str("nde_lang"), rdr.Read_str("nde_name"), rdr.Read_str("nde_help"));
					cur_regy.Deleted__add(gui_itm);
				}
			}
			cur_regy.Deleted__commit();
		}
	}
	public static Xocfg_edit_loader New(Xoa_app app) {
		Xocfg_db_app db_app = Xocfg_db_app.New(app);
		return new Xocfg_edit_loader(db_app, new Xocfg_db_usr(db_app, app.User().User_db_mgr().Conn()));
	}
}
