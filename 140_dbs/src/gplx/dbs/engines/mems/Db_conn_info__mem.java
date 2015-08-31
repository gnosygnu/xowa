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
package gplx.dbs.engines.mems; import gplx.*; import gplx.dbs.*; import gplx.dbs.engines.*;
public class Db_conn_info__mem extends Db_conn_info__base {
	@Override public String Tid() {return Tid_const;} public static final String Tid_const = "mem";
	@Override public Db_conn_info New_self(String raw, GfoMsg m) {
		Db_conn_info__mem rv = new Db_conn_info__mem();
		rv.Ctor("", m.ReadStr("database"), raw, raw);
		return rv;
	}
	public static Db_conn_info new_(String database) {
		return Db_conn_info_.parse(Bld_raw
		( "gplx_key", Tid_const
		, "database", database
		));
	}
        public static final Db_conn_info__mem I = new Db_conn_info__mem(); Db_conn_info__mem() {}
}
