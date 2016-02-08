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
package gplx.dbs.sys; import gplx.*; import gplx.dbs.*;
import gplx.core.primitives.*;
public class Db_sys_mgr {
	private final Db_conn conn;
	private final Db_sys_tbl sys_tbl;
	private boolean assert_exists = true;
	public Db_sys_mgr(Db_conn conn) {
		this.conn = conn;
		sys_tbl = new Db_sys_tbl(conn);
	}
	public int Autonum_next(String tbl, String fld) {return Autonum_next(String_.Concat(tbl, ".", fld));}
	public int Autonum_next(String key) {
		if (assert_exists) Assert_exists();
		int rv = sys_tbl.Assert_int_or(key, 1);
		sys_tbl.Update_int(key, rv + 1);
		return rv;
	}
	private void Assert_exists() {
		assert_exists = false;
		if (!conn.Meta_tbl_exists(sys_tbl.Tbl_name())) sys_tbl.Create_tbl();
	}
}
