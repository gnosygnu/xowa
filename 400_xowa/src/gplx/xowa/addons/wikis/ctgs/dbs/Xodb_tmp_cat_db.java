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
package gplx.xowa.addons.wikis.ctgs.dbs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.ctgs.*;
import gplx.dbs.*;
public class Xodb_tmp_cat_db {
	private final    Io_url url;
	public Xodb_tmp_cat_db(Xowe_wiki wiki) {
		this.url = wiki.Fsys_mgr().Root_dir().GenSubFil("xowa.temp.category.sqlite3");
		this.conn = Db_conn_bldr.Instance.Get_or_autocreate(true, url);
	}
	public Db_conn Conn() {return conn;} private final    Db_conn conn;
	public void Delete() {
		conn.Rls_conn();
		Io_mgr.Instance.DeleteFil(url);
	}
}
