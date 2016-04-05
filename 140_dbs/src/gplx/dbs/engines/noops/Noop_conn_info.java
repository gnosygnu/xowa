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
package gplx.dbs.engines.noops; import gplx.*; import gplx.dbs.*; import gplx.dbs.engines.*;
public class Noop_conn_info extends Db_conn_info__base {
	public Noop_conn_info(String raw, String db_api, String database) {super(raw, db_api, database);}
	@Override public String Key() {return Tid_const;} public static final    String Tid_const = "null_db";
	@Override public Db_conn_info New_self(String raw, Keyval_hash hash) {return this;}
	public static final    Noop_conn_info Instance = new Noop_conn_info("gplx_key=null_db", "", "");
}
