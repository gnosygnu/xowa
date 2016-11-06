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
	public Xocfg_itm_bldr(Xocfg_db_mgr db_mgr) {
		this.db_mgr = db_mgr;
	}
	public void Create_grp(String owner_key, String grp_key, String grp_name, String grp_help) {
		int grp_id = 1;			// select Max(id) + 1 from cfg_grp_meta;

		db_mgr.Tbl__grp_meta().Upsert(grp_id, grp_key);

		int owner_id = 1;		// select id from cfg_grp_meta where key = owner_key;
		int map_sort = 1;		// select Max(map_sort) + 1 from cfg_grp_map where map_src = owner_id;
		db_mgr.Tbl__grp_map().Upsert(owner_id, grp_id, map_sort);

		int nde_type = 0;		// 0=grp; 1=itm
		db_mgr.Tbl__nde_i18n().Upsert(nde_type, grp_id, "en", grp_name, grp_help);
	}
	public void Create_itm(String grp_key, String itm_key, String scope_id_str, String type_id_str, String itm_dflt, String itm_name, String help) {
		int grp_id = 1;			// select id from cfg_grp_meta WHERE key = grp_key;
		int itm_id = 1;			// select Max(itm_id) + 1 from cfg_itm_meta
		int itm_sort = 1;		// Select Max(itm_sort) + 1 FROM cfg_itm_meta WHERE grp_id = grp_id;
		int scope_id = 1;		// app-level,wiki-level,ns-level...
		int type_id = 1;		// boolean,int,String...

		db_mgr.Tbl__itm_meta().Upsert(grp_id, itm_id, itm_sort, scope_id, type_id, itm_key, itm_dflt);

		int nde_type = 1;		// 0=grp; 1=itm
		db_mgr.Tbl__nde_i18n().Upsert(nde_type, itm_id, "en", itm_name, help);
	}
}
