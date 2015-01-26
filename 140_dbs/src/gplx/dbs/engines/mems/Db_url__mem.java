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
public class Db_url__mem extends Db_url__base {
	@Override public String Tid() {return Tid_const;} public static final String Tid_const = "mem";
	@Override public Db_url New_self(String raw, GfoMsg m) {
		Db_url__mem rv = new Db_url__mem();
		rv.Ctor("", m.ReadStr("database"), raw, raw);
		return rv;
	}
	public static Db_url new_(String database) {
		return Db_url_.parse_(Bld_raw
		( "gplx_key", Tid_const
		, "database", database
		));
	}
        public static final Db_url__mem I = new Db_url__mem(); Db_url__mem() {}
}
