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
public class Db_conn_info__sqlite extends Db_conn_info__base {
	@Override public String Key() {return Key_const;} public static final String Key_const = "sqlite";
	public Io_url Url() {return url;} private Io_url url;
	public static Db_conn_info load_(Io_url url) {
		return Db_conn_info_.parse_(BldRaw(GfoMsg_.new_cast_("Db_conn_info")
			.Add("gplx_key", Key_const)
			.Add("data source", url.Xto_api())
			.Add("version", 3)
			));
	}
	public static Db_conn_info make_(Io_url url) {
		Io_mgr._.CreateDirIfAbsent(url.OwnerDir());
		return Db_conn_info_.parse_(BldRaw(GfoMsg_.new_cast_("Db_conn_info")
			.Add("gplx_key", Key_const)
			.Add("data source", url.Xto_api())
			.Add("version", 3)	
			));
	}
	@Override public Db_conn_info Make_new(String raw, GfoMsg m) {
		Db_conn_info__sqlite rv = new Db_conn_info__sqlite();
		String url = m.ReadStr("data source");
		rv.url = Io_url_.new_any_(url);
		rv.Ctor_of_db_connect("", url, raw, BldApi(m, KeyVal_.new_("version", "3")));
		return rv;
	}
	public static final Db_conn_info__sqlite _ = new Db_conn_info__sqlite(); Db_conn_info__sqlite() {}
}