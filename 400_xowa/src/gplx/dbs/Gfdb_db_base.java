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
import gplx.dbs.schemas.*;
public class Gfdb_db_base {
	public Db_conn Conn() {return conn;} private Db_conn conn;
	public Schema_db_mgr Schema() {return schema;} private Schema_db_mgr schema = new Schema_db_mgr();
	public void Init(Db_conn conn) {
		this.conn = conn;
		schema.Init(conn);
	}
}
