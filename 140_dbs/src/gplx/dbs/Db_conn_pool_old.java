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
public class Db_conn_pool_old {
	private final OrderedHash hash = OrderedHash_.new_();
	public Db_conn Get_or_new(Db_url url) {return Get_or_new(url.Xto_raw());}
	public Db_conn Get_or_new(String raw) {
		Db_conn rv = (Db_conn)hash.Fetch(raw);
		if (rv == null) {
			rv = Db_conn_.new_and_open_(Db_url_.parse_(raw));
			hash.Add(raw, rv);
		}
		return rv;
	}
	public void Del(Db_url url) {hash.Del(url.Xto_raw());}
	public void Clear() {
		int len = hash.Count();
		for (int i = 0; i < len; i++) {
			Db_conn conn = (Db_conn)hash.FetchAt(0);
			conn.Conn_term();
		}
		hash.Clear();
	}		
	public static final Db_conn_pool_old _ = new Db_conn_pool_old(); Db_conn_pool_old() {}
}
