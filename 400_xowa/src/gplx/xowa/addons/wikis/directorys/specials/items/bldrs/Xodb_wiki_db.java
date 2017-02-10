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
package gplx.xowa.addons.wikis.directorys.specials.items.bldrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.directorys.*; import gplx.xowa.addons.wikis.directorys.specials.*; import gplx.xowa.addons.wikis.directorys.specials.items.*;
import gplx.dbs.*;
public class Xodb_wiki_db implements Db_tbl_owner {
	private final    Ordered_hash tbls = Ordered_hash_.New();
	public Xodb_wiki_db(int tid, Io_url url, Db_conn conn) {
		this.tid = tid;
		this.url = url;
		this.conn = conn;
	}
	public int Tid() {return tid;} private final    int tid;
	public Io_url Url() {return url;} private final    Io_url url;
	public Db_conn Conn() {return conn;} private final    Db_conn conn;
	public Db_tbl Tbls__get_by_key(String key) {return (Db_tbl)tbls.Get_by(key);}
	public void Tbls__add(boolean create, Db_tbl... ary) {
		int len = ary.length;
		for (int i = 0; i < len; ++i) {
			Db_tbl tbl = ary[i];
			tbls.Add(tbl.Tbl_name(), tbl);
			if (create)
				tbl.Create_tbl();
		}
	}

	public static Xodb_wiki_db Make(int tid, Io_url url) {
		Db_conn conn = Db_conn_bldr.Instance.Get_or_new(url).Conn();
		return new Xodb_wiki_db(tid, url, conn);
	}
}
