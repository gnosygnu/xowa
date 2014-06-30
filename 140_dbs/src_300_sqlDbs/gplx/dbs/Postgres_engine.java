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
import gplx.stores.*;
import java.sql.*; 
class Postgres_engine extends Db_engine_sql_base {
	@Override public String Key() {return Db_connect_postgres.KeyDef;}
	@Override public Sql_cmd_wtr SqlWtr() {return Sql_cmd_wtr_.BackslashSensitive;}
	@Override public Db_engine MakeEngine(Db_connect connectInfo) {
		Postgres_engine rv = new Postgres_engine();
		rv.ctor_SqlEngineBase(connectInfo);
		return rv;
	}
	@Override public DataRdr NewDataRdr(ResultSet rdr, String commandText) {return Db_data_rdr_.new_(rdr, commandText);}
		@gplx.Internal @Override protected Connection NewDbCon() {
		Db_connect_postgres connUrl = (Db_connect_postgres)dbInfo; 
		return NewDbCon("jdbc:" + connUrl.Key_of_db_connect() + "://localhost/" + connUrl.Database(), connUrl.Uid(), connUrl.Pwd());
	}
		@gplx.Internal protected static final Postgres_engine _ = new Postgres_engine(); Postgres_engine() {}
}
