/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2017 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
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
