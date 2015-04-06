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
import gplx.dbs.qrys.*;
public class Db_conn_ {
	public static final Db_conn Empty = Db_conn_pool.I.Get_or_new(Db_conn_info_.Null);
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
