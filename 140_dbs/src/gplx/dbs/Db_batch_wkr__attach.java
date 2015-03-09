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
package gplx.dbs; import gplx.*;
public class Db_batch_wkr__attach implements Db_batch_wkr {
	private final Db_conn conn;
	private final ListAdp list = ListAdp_.new_();
	public Db_batch_wkr__attach Add(String alias, Io_url url) {list.Add(new Db_batch_wkr__attach_itm(alias, url)); return this;}
	public Db_batch_wkr__attach(Db_conn conn) {this.conn = conn;}
	public void Batch_bgn() {
		int len = list.Count();
		for (int i = 0; i < len; ++i) {
			Db_batch_wkr__attach_itm itm = (Db_batch_wkr__attach_itm)list.FetchAt(i);
			conn.Exec_env_db_attach(itm.Alias(), itm.Url());
		}
	}
	public void Batch_end() {
		int len = list.Count();
		for (int i = 0; i < len; ++i) {
			Db_batch_wkr__attach_itm itm = (Db_batch_wkr__attach_itm)list.FetchAt(i);
			conn.Exec_env_db_detach(itm.Alias());
		}
	}
}
class Db_batch_wkr__attach_itm {
	public Db_batch_wkr__attach_itm(String alias, Io_url url) {this.alias = alias; this.url = url;}
	public String Alias() {return alias;} private final String alias;
	public Io_url Url() {return url;} private final Io_url url;
}
