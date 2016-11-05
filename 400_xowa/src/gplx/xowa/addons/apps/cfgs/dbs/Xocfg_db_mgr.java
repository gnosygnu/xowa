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
import gplx.dbs.*;
public class Xocfg_db_mgr {
	public Xocfg_db_mgr(Db_conn conn) {
		this.tbl__grp_meta = new Xocfg_grp_meta(conn);
		this.tbl__grp_map  = new Xocfg_grp_map(conn);
		this.tbl__itm_meta = new Xocfg_itm_meta(conn);
		this.tbl__itm_data = new Xocfg_itm_data(conn);
		this.tbl__nde_i18n = new Xocfg_nde_i18n(conn);
		conn.Meta_tbl_assert(tbl__grp_meta, tbl__grp_map, tbl__itm_meta, tbl__itm_data, tbl__nde_i18n);
	}
	public Xocfg_grp_meta Tbl__grp_meta() {return tbl__grp_meta;} private final    Xocfg_grp_meta tbl__grp_meta;
	public Xocfg_grp_map  Tbl__grp_map()  {return tbl__grp_map ;} private final    Xocfg_grp_map  tbl__grp_map;
	public Xocfg_itm_meta Tbl__itm_meta() {return tbl__itm_meta;} private final    Xocfg_itm_meta tbl__itm_meta;
	public Xocfg_itm_data Tbl__itm_data() {return tbl__itm_data;} private final    Xocfg_itm_data tbl__itm_data;
	public Xocfg_nde_i18n Tbl__nde_i18n() {return tbl__nde_i18n;} private final    Xocfg_nde_i18n tbl__nde_i18n;
}
