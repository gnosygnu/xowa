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
import gplx.core.threads.*; import gplx.dbs.*; import gplx.dbs.metas.updates.*; import gplx.dbs.metas.*;
import gplx.xowa.files.caches.*;
import gplx.xowa.users.bmks.*; import gplx.xowa.users.history.*;
public class Xou_db_mgr {
	private final    Xoa_app app;
	private final    Xoud_id_mgr id_mgr;		
	public Xou_db_mgr(Xoa_app app) {
		this.app = app;
		this.id_mgr = new Xoud_id_mgr(cfg_mgr);
		this.site_mgr = new Xoud_site_mgr(id_mgr);
	}
	public Db_conn				Conn()		{return conn;} private Db_conn conn;
	public Xou_db_file			Db_file() {return db_file;} private Xou_db_file db_file;
	public Xoud_cfg_mgr			Cfg_mgr() {return cfg_mgr;} private final    Xoud_cfg_mgr cfg_mgr = new Xoud_cfg_mgr();
	public Xoud_site_mgr		Site_mgr() {return site_mgr;} private final    Xoud_site_mgr site_mgr;
	public Xoud_history_mgr		History_mgr() {return history_mgr;} private final    Xoud_history_mgr history_mgr = new Xoud_history_mgr();
	public Xoud_bmk_mgr			Bmk_mgr() {return bmk_mgr;} private final    Xoud_bmk_mgr bmk_mgr = new Xoud_bmk_mgr();
	public Xou_cache_mgr		Cache_mgr() {return cache_mgr;} private Xou_cache_mgr cache_mgr;
	public Xou_file_itm_finder	File__xfer_itm_finder() {return xfer_itm_finder;} private Xou_file_itm_finder xfer_itm_finder;
	public void Init_by_app(boolean drd, Io_url db_url) {
		Db_conn_bldr_data db_conn_bldr = Db_conn_bldr.Instance.Get_or_new(db_url);
		this.conn = db_conn_bldr.Conn(); boolean created = db_conn_bldr.Created();
		this.db_file = new Xou_db_file(conn); db_file.Init_assert();
		this.cache_mgr = new Xou_cache_mgr(app.Wiki_mgri(), app.Fsys_mgr().File_dir(), db_file);
		this.xfer_itm_finder = new Xou_file_itm_finder(cache_mgr);
		this.bmk_mgr.Conn_(conn, created);
		// this.history_mgr.Conn_(conn, created);
		if (drd) {
			this.Init_site_mgr();
//				history_mgr.Conn_(user_conn, created);
		}
	}
	private boolean init_site_mgr;
	public void Init_site_mgr() {
		if (init_site_mgr) return; init_site_mgr = true;
		cfg_mgr.Conn_ (conn, !conn.Meta_tbl_exists(Xoud_cfg_mgr.Tbl_name));
		site_mgr.Conn_(conn, !conn.Meta_tbl_exists(Xoud_site_tbl.Tbl_name));
	}
}
