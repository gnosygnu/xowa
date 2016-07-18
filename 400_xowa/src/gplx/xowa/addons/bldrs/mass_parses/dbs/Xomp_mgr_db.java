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
package gplx.xowa.addons.bldrs.mass_parses.dbs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.mass_parses.*;
import gplx.dbs.*;
public class Xomp_mgr_db {
	public Xomp_mgr_db(Io_url url) {
		this.url = url;
		this.conn = Db_conn_bldr.Instance.Get_or_autocreate(true, url);
		this.page_tbl = new Xomp_page_tbl(conn);
	}
	public Io_url Url() {return url;}  private Io_url url;
	public Db_conn Conn() {return conn;} private Db_conn conn;
	public Xomp_page_tbl Page_tbl() {return page_tbl;} private Xomp_page_tbl page_tbl;
}
