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
package gplx.xowa.users.dbs; import gplx.*; import gplx.xowa.*; import gplx.xowa.users.*;
import gplx.core.primitives.*;
import gplx.dbs.*;
import gplx.xowa.dbs.tbls.*;
public class Xou_db_mgr {
	private Xoa_app app;		
	private Io_url db_url;
	private Xou_db_xtn_mgr xtn_mgr = new Xou_db_xtn_mgr();
	private ListAdp wkr_list = ListAdp_.new_();
	public Xou_db_mgr(Xoa_app app) {this.app = app;}
	public Db_conn Conn() {return conn;} private Db_conn conn;
	public void App_init() {
		db_url = app.User().Fsys_mgr().Root_dir().OwnerDir().GenSubFil_ary("xowa.user.", app.User().Key_str(), ".sqlite3");
		Bool_obj_ref created_ref = Bool_obj_ref.n_();
		conn = Sqlite_engine_.Conn_load_or_make_(db_url, created_ref);
		if (created_ref.Val()) {
			xtn_mgr.Db_when_new(conn);
			Xodb_xowa_cfg_tbl.Create_table(conn);
			Xodb_xowa_cfg_tbl.Create_index(conn);
		}
		xtn_mgr.Db_init(conn);
	}
	public void App_save() {
		try {
			int wkr_len = wkr_list.Count();
			for (int i = 0; i < wkr_len; i++) {
				Xou_db_wkr wkr = (Xou_db_wkr)wkr_list.FetchAt(i);
				wkr.Db_save(this);
			}
		} 
		catch (Exception e) {	// NOTE: can fail when two XOWA are launched (and both will have different cache ids); for now, ignore error
			app.Usr_dlg().Warn_many("", "", "error while saving to user_db; err=~{0}", Err_.Message_gplx(e));
		}
	}
	public void App_term() {
		try {
			app.Usr_dlg().Note_many("", "", "user_db.shut_down.bgn: ~{0}", conn.Url().Xto_raw());
			this.App_save();
			int wkr_len = wkr_list.Count();
			for (int i = 0; i < wkr_len; i++) {
				Xou_db_wkr wkr = (Xou_db_wkr)wkr_list.FetchAt(i);
				wkr.Db_term(this);
			}
			conn.Txn_mgr().Txn_end_all();
			conn.Conn_term();
			app.Usr_dlg().Note_many("", "", "user_db.shut_down.end");
		} 
		catch (Exception e) {
			app.Usr_dlg().Warn_many("", "", "user_db.shut_down.err; err=~{0}", Err_.Message_gplx(e));
		}
	}
	public void Wkr_reg(Xou_db_wkr wkr) {
		wkr_list.Add(wkr);
		if (!xtn_mgr.Xtn_exists(wkr.Xtn_key()))  {
			xtn_mgr.Xtn_add(wkr.Xtn_key(), wkr.Xtn_version());		// NOTE: xowa_xtn sometimes empty; add xtn before loading; if error below then xtn still added; DATE:2013-12-29
			wkr.Db_when_new(this);
			xtn_mgr.Db_save();										// always save...
			conn.Txn_mgr().Txn_end_all_bgn_if_none();			// ... and commit, b/c HTTP_Server never formally exits; DATE:2014-03-06
		}
	}
}
