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
package gplx.dbs.engines.sqlite; import gplx.*; import gplx.dbs.*; import gplx.dbs.engines.*;
public class Sqlite_url extends Db_url__base {
	@Override public String Tid() {return Tid_const;} public static final String Tid_const = "sqlite";
	public Io_url Url() {return url;} private Io_url url;
	@Override public Db_url New_self(String raw, GfoMsg m) {
		Sqlite_url rv = new Sqlite_url();
		String url = m.ReadStr("data source");
		rv.url = Io_url_.new_any_(url);
		rv.Ctor("", url, raw, BldApi(m, KeyVal_.new_("version", "3")));
		rv.database = rv.url.NameOnly();
		return rv;
	}
	public static Db_url load_(Io_url url) {
		return Db_url_.parse_(Bld_raw
		( "gplx_key"		, Tid_const
		, "data source"		, url.Xto_api()
		, "version"			, "3"
		));
	}
	public static Db_url make_(Io_url url) {
		Io_mgr._.CreateDirIfAbsent(url.OwnerDir());
		return Db_url_.parse_(Bld_raw
		( "gplx_key"		, Tid_const
		, "data source"		, url.Xto_api()
		, "version"			, "3"
			
		));
	}
	public static final Sqlite_url _ = new Sqlite_url(); Sqlite_url() {}
}