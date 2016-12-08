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
import gplx.xowa.addons.apps.cfgs.dbs.tbls.*;
public class Xocfg_db_usr {
	private final    Xocfg_itm_tbl tbl__itm;
	public Xocfg_db_usr(Xocfg_db_app db_app, Db_conn conn) {
		this.conn = conn;
		this.tbl__val = new Xocfg_val_tbl(conn);
		conn.Meta_tbl_assert(tbl__val);
		this.tbl__itm = db_app.Tbl__itm();
	}
	public Db_conn Conn() {return conn;} private final    Db_conn conn;
	public Xocfg_val_tbl Tbl__val() {return tbl__val;} private final    Xocfg_val_tbl tbl__val;
	public String Get_str(String ctx, String key) {
		Xocfg_itm_row itm_row = tbl__itm.Select_by_key_or_null(key);
		if (itm_row == null) throw Err_.new_wo_type("cfg not defined", "ctx", ctx, "key", key);
		Xocfg_val_row val_row = tbl__val.Select_one_or_null(ctx, key);
		return val_row == null ? itm_row.Dflt() : val_row.Val();
	}
	public void Set_str(String ctx, String key, String val) {
		tbl__val.Upsert(ctx, key, val, Datetime_now.Get().XtoUtc().XtoStr_fmt_iso_8561());
	}
	public void Del(String ctx, String key) {
		tbl__val.Delete(ctx, key);
	}
}
