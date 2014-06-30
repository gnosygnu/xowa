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
	public void Del(Db_connect connectInfo) {hash.Del(connectInfo.Raw_of_db_connect());}
	public Db_provider FetchOrNew(Db_connect connectInfo) {return FetchOrNew(connectInfo.Raw_of_db_connect());}
	public Db_provider FetchOrNew(String raw) {
		Db_provider rv = (Db_provider)hash.Fetch(raw);
		if (rv == null) {
			rv = Db_provider_.new_(Db_connect_.parse_(raw));
			hash.Add(raw, rv);
		}
		return rv;
	}
	public void Clear() {
		for (int i = 0; i < hash.Count(); i++) {
			Db_provider provider = (Db_provider)hash.FetchAt(i);
			provider.Rls();
		}
		hash.Clear();
	}
	OrderedHash hash = OrderedHash_.new_();
	@gplx.Internal protected static Db_provider_pool new_() {return new Db_provider_pool();} Db_provider_pool() {}
	public static final Db_provider_pool _ = new Db_provider_pool();
}
