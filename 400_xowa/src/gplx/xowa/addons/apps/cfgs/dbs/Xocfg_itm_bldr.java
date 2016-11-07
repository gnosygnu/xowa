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
package gplx.xowa.addons.apps.cfgs.dbs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.apps.*; import gplx.xowa.addons.apps.cfgs.*;
public class Xocfg_itm_bldr {
	private final    Xocfg_db_mgr db_mgr;
	private final    String Lang__dflt = "en";
	public Xocfg_itm_bldr(Xocfg_db_mgr db_mgr) {
		this.db_mgr = db_mgr;
	}
	public void Create_grp(String owner_key, String grp_key, String grp_name, String grp_help) {
		// insert grp_meta
		int grp_id = db_mgr.Conn().Sys_mgr().Autonum_next("cfg_grp_meta.grp_id");
		db_mgr.Tbl__grp_meta().Upsert(grp_id, grp_key);

		// insert grp_map
		int owner_id = String_.Len_eq_0(owner_key) ? Xogrp_meta_itm.Id__root : db_mgr.Tbl__grp_meta().Select_id_by_key_or_fail(owner_key);
		int map_sort = db_mgr.Tbl__grp_map().Select_next_sort(owner_id);
		db_mgr.Tbl__grp_map().Upsert(owner_id, grp_id, map_sort);

		// insert nde_i18n
		db_mgr.Tbl__nde_i18n().Upsert(Xonde_i18n_nde_tid.Tid__grp, grp_id, Lang__dflt, grp_name, grp_help);
	}
	public void Create_itm(String grp_key, String itm_key, String scope_id_str, String type_id_str, String itm_dflt, String itm_name, String help) {
		// insert itm_meta
		int grp_id = db_mgr.Tbl__grp_meta().Select_id_by_key_or_fail(grp_key);
		int itm_id = db_mgr.Conn().Sys_mgr().Autonum_next("cfg_itm_meta.itm_id");
		int scope_id = Xoitm_meta_scope_tid.To_int(scope_id_str);
		int type_id = 1;		// boolean,int:40,list:a|b|c,String:40,note:50,70;file;button?;label?
		db_mgr.Tbl__itm_meta().Upsert(itm_id, scope_id, type_id, itm_key, itm_dflt);

		// insert grp_map
		int itm_sort = db_mgr.Tbl__grp_map().Select_next_sort(grp_id);
		db_mgr.Tbl__grp_map().Upsert(grp_id, itm_id, itm_sort);

		// insert nde_i18n
		db_mgr.Tbl__nde_i18n().Upsert(Xonde_i18n_nde_tid.Tid__itm, itm_id, Lang__dflt, itm_name, help);
	}
}
