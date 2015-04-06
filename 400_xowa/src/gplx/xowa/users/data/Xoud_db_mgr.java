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
package gplx.xowa.users.data; import gplx.*; import gplx.xowa.*; import gplx.xowa.users.*;
import gplx.threads.*; import gplx.dbs.*; import gplx.dbs.schemas.updates.*; import gplx.dbs.schemas.*;	
public class Xoud_db_mgr {
	private final Xoud_id_mgr id_mgr;
	public Xoud_db_mgr() {
		id_mgr = new Xoud_id_mgr(cfg_mgr);
		site_mgr = new Xoud_site_mgr(id_mgr);
	}
	public Xoud_cfg_mgr			Cfg_mgr() {return cfg_mgr;} private final Xoud_cfg_mgr cfg_mgr = new Xoud_cfg_mgr();
	public Xoud_site_mgr		Site_mgr() {return site_mgr;} private final Xoud_site_mgr site_mgr;
	public Xoud_history_mgr		History_mgr() {return history_mgr;} private final Xoud_history_mgr history_mgr = new Xoud_history_mgr();
	public void Init_by_boot(Db_conn user_conn, boolean created, int user_id) {
		cfg_mgr.Conn_(user_conn, created, user_id);
		site_mgr.Conn_(user_conn, created, user_id);
		history_mgr.Conn_(user_conn, created, user_id);
	}
//		private void Init_user_db_changes(Schema_update_mgr updater) {
//			updater.Add(Schema_update_cmd_.Make_tbl_create(Xoud_regy_tbl.Tbl_name	, Xoud_regy_tbl.Tbl_sql		, Xoud_regy_tbl.Idx_core));
//			updater.Add(Schema_update_cmd_.Make_tbl_create(Xoud_history_tbl.Tbl_name, Xoud_history_tbl.Tbl_sql	, Xoud_history_tbl.Idx_core));
//			updater.Add(Schema_update_cmd_.Make_tbl_create(Xoud_site_tbl.Tbl_name	, Xoud_site_tbl.Tbl_sql));
//		}
}
