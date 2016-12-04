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
import gplx.dbs.*; import gplx.dbs.utls.*;
public class Xocfg_db_mgr {
	public Xocfg_db_mgr(Db_conn conn) {
		this.conn = conn;
		this.tbl__grp_meta = new Xogrp_meta_tbl(conn);
		this.tbl__grp_map  = new Xogrp_map_tbl(conn);
		this.tbl__itm_meta = new Xoitm_meta_tbl(conn);
		this.tbl__itm_data = new Xoitm_data_tbl(conn);
		this.tbl__nde_i18n = new Xonde_i18n_tbl(conn);
		conn.Meta_tbl_assert(tbl__grp_meta, tbl__grp_map, tbl__itm_meta, tbl__itm_data, tbl__nde_i18n);
	}
	public Db_conn Conn() {return conn;} private final    Db_conn conn;
	public Xogrp_meta_tbl Tbl__grp_meta() {return tbl__grp_meta;} private final    Xogrp_meta_tbl tbl__grp_meta;
	public Xogrp_map_tbl  Tbl__grp_map()  {return tbl__grp_map ;} private final    Xogrp_map_tbl  tbl__grp_map;
	public Xoitm_meta_tbl Tbl__itm_meta() {return tbl__itm_meta;} private final    Xoitm_meta_tbl tbl__itm_meta;
	public Xoitm_data_tbl Tbl__itm_data() {return tbl__itm_data;} private final    Xoitm_data_tbl tbl__itm_data;
	public Xonde_i18n_tbl Tbl__nde_i18n() {return tbl__nde_i18n;} private final    Xonde_i18n_tbl tbl__nde_i18n;

	public String Get_str(String ctx, String key) {
		Xoitm_meta_itm meta_itm = tbl__itm_meta.Select_by_key_or_null(key);
		if (meta_itm == null) throw Err_.new_wo_type("cfg not defined", "ctx", ctx, "key", key);
		Xoitm_data_itm data_itm = tbl__itm_data.Select_one_by_id_or_null(meta_itm.Id(), ctx);
		return data_itm == null ? meta_itm.Dflt() : data_itm.Val();
	}
	public void Set_str(String ctx, String key, String val) {
		Xoitm_meta_itm meta_itm = tbl__itm_meta.Select_by_key_or_null(key);
		if (meta_itm == null) throw Err_.new_wo_type("cfg not defined", "ctx", ctx, "key", key);

		// parse val
		if (meta_itm.Gui_type() == Xoitm_gui_tid.Tid__checkbox) {
			val = String_.Eq(val, "on") ? "true" : "false";
		}
		tbl__itm_data.Upsert(meta_itm.Id(), ctx, val, Datetime_now.Get().XtoUtc().XtoStr_fmt_iso_8561());
	}
	public void Del(String ctx, String key) {
		Xoitm_meta_itm meta_itm = tbl__itm_meta.Select_by_key_or_null(key);
		if (meta_itm == null) throw Err_.new_wo_type("cfg not defined", "ctx", ctx, "key", key);
		tbl__itm_data.Delete(meta_itm.Id(), ctx);
	}
}
