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
public class Db_conn_ {
	public static final Db_conn Null = new_and_open_(Db_url_.Null);
	public static Db_conn new_and_open_(Db_url url) {
		Db_engine prototype = Db_engine_regy._.Get(url.Tid());
		Db_engine engine = prototype.New_clone(url);
		engine.Conn_open();	// auto-open
		return new Db_conn(engine);
	}
	public static Db_conn Reg_itm(Db_conn_itm itm, Db_conn old_conn, Db_conn new_conn) {
		if (old_conn != null) old_conn.Itms_del(itm);
		new_conn.Itms_add(itm);
		return new_conn;
	}
	public static int Select_fld0_as_int_or(Db_conn p, String sql, int or) {
		DataRdr rdr = DataRdr_.Null;
		try {
			rdr = p.Exec_qry_as_rdr(Db_qry_sql.rdr_(sql));
			int rv = or;
			if (rdr.MoveNextPeer()) {
				Object rv_obj = rdr.ReadAt(0);
				if (rv_obj != null)		// Max(fil_id) will be NULL if tbl is empty
					rv = Int_.cast_or_(rv_obj, or);
			}
			return rv;
		}
		finally {
			rdr.Rls();
		}
	}
}
