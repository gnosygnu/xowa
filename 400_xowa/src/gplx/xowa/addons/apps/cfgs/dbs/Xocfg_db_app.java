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
import gplx.dbs.*; import gplx.xowa.addons.apps.cfgs.dbs.tbls.*;
public class Xocfg_db_app {
	public Xocfg_db_app(Db_conn conn) {
		this.conn = conn;
		this.tbl__grp = new Xocfg_grp_tbl(conn);
		this.tbl__map  = new Xocfg_map_tbl(conn);
		this.tbl__itm = new Xocfg_itm_tbl(conn);
		this.tbl__txt = new Xocfg_txt_tbl(conn);
		conn.Meta_tbl_assert(tbl__grp, tbl__map, tbl__itm, tbl__txt);
	}
	public Db_conn Conn() {return conn;} private final    Db_conn conn;
	public Xocfg_grp_tbl Tbl__grp()	{return tbl__grp;} private final    Xocfg_grp_tbl tbl__grp;
	public Xocfg_map_tbl Tbl__map()	{return tbl__map;} private final    Xocfg_map_tbl tbl__map;
	public Xocfg_itm_tbl Tbl__itm() {return tbl__itm;} private final    Xocfg_itm_tbl tbl__itm;
	public Xocfg_txt_tbl Tbl__txt() {return tbl__txt;} private final    Xocfg_txt_tbl tbl__txt;

	public static Db_conn New_conn(Xoa_app app) {
		Io_url url = app.Fsys_mgr().Bin_addon_dir().GenSubFil_nest("app", "cfg", "xo.cfg.sqlite3");
		return Db_conn_bldr.Instance.Get_or_autocreate(true, url);
	}
	public static Xocfg_db_app New(Xoa_app app) {
		return new Xocfg_db_app(New_conn(app));
	}
}
