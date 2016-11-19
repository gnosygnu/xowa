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
package gplx.xowa.addons.apps.updates.dbs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.apps.*; import gplx.xowa.addons.apps.updates.*;
import gplx.dbs.*; import gplx.dbs.utls.*;
public class Xoa_update_db_mgr {
	public Xoa_update_db_mgr(Io_url url) {
		this.url = url;
		this.conn = Db_conn_bldr.Instance.Get_or_fail(url);
		this.tbl__app_version = new Xoa_app_version_tbl(conn);
		conn.Meta_tbl_assert(tbl__app_version);
	}
	public Io_url Url() {return url;} private final    Io_url url;
	public Db_conn Conn() {return conn;} private final    Db_conn conn;
	public Xoa_app_version_tbl Tbl__app_version() {return tbl__app_version;} private final    Xoa_app_version_tbl tbl__app_version;
}
