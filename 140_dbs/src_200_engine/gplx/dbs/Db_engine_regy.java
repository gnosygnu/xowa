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
public class Db_engine_regy {
	private final HashAdp hash = HashAdp_.new_();
	public Db_engine_regy() {}
	Db_engine_regy(int dflt) {
		this.Add(Db_engine_null._)
			.Add(TdbEngine._)
			.Add(Mysql_engine._)
			.Add(Postgres_engine._)
			.Add(Sqlite_engine._)
			;
	}
	public Db_engine_regy	Add(Db_engine engine) {hash.Add(engine.Key(), engine); return this;}
	public Db_engine		Get(String key) {return (Db_engine)hash.FetchOrFail(key);}
	public static final Db_engine_regy _ = new Db_engine_regy(1);
}
