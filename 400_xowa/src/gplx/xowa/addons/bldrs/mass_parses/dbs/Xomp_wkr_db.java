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
import gplx.xowa.htmls.core.dbs.*;
public class Xomp_wkr_db {
	public Xomp_wkr_db(int idx, Io_url url) {
		this.idx = idx;
		this.url = url;
		this.conn = Db_conn_bldr.Instance.Get_or_autocreate(true, url);
		this.html_tbl = new Xowd_html_tbl(conn);
		conn.Meta_tbl_assert(html_tbl);
	}
	public int Idx() {return idx;} private final    int idx;
	public Io_url Url() {return url;}  private Io_url url;
	public Db_conn Conn() {return conn;} private Db_conn conn;
	public Xowd_html_tbl Html_tbl() {return html_tbl;} private final    Xowd_html_tbl html_tbl;

	public static Xomp_wkr_db New(Io_url root_dir, int uid) {
		Io_url url = root_dir.GenSubFil_nest("xomp_" + Int_.To_str_fmt(uid, "000"), "xomp_wkr.sqlite3");
		return new Xomp_wkr_db(uid, url);
	}
}
