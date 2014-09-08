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
public class Db_provider_pool {
	private final OrderedHash hash = OrderedHash_.new_();
	public Db_provider Get_or_new(Db_conn_info connectInfo) {return Get_or_new(connectInfo.Str_raw());}
	public Db_provider Get_or_new(String raw) {
		Db_provider rv = (Db_provider)hash.Fetch(raw);
		if (rv == null) {
			rv = Db_provider_.new_and_open_(Db_conn_info_.parse_(raw));
			hash.Add(raw, rv);
		}
		return rv;
	}
	public void Del(Db_conn_info connectInfo) {hash.Del(connectInfo.Str_raw());}
	public void Clear() {
		int len = hash.Count();
		for (int i = 0; i < len; i++) {
			Db_provider provider = (Db_provider)hash.FetchAt(0);
			provider.Conn_term();
		}
		hash.Clear();
	}		
	public static final Db_provider_pool _ = new Db_provider_pool(); Db_provider_pool() {}
}
