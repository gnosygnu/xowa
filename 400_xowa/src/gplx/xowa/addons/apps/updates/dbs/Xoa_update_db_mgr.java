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
package gplx.xowa.addons.apps.updates.dbs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.apps.*; import gplx.xowa.addons.apps.updates.*;
import gplx.dbs.*; import gplx.dbs.utls.*;
public class Xoa_update_db_mgr {
	public Xoa_update_db_mgr(Io_url url) {
		this.url = url;
		this.conn = Db_conn_bldr.Instance.Get_or_autocreate(true, url);
		this.tbl__app_version = new Xoa_app_version_tbl(conn);
		conn.Meta_tbl_assert(tbl__app_version);
	}
	public Io_url Url() {return url;} private final    Io_url url;
	public Db_conn Conn() {return conn;} private final    Db_conn conn;
	public Xoa_app_version_tbl Tbl__app_version() {return tbl__app_version;} private final    Xoa_app_version_tbl tbl__app_version;
}
