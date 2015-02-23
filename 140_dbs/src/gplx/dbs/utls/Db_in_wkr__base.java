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
package gplx.dbs.utls; import gplx.*; import gplx.dbs.*;
public abstract class Db_in_wkr__base {
	protected abstract int Interval();
	protected abstract Db_qry	Make_qry	(Object db_ctx, int bgn, int end);
	protected abstract void		Fill_stmt	(Db_stmt stmt, int bgn, int end);
	protected abstract void		Read_data	(Cancelable cancelable, Object db_ctx, Db_rdr rdr);
	public void Select_in(Cancelable cancelable, Object db_ctx, Db_conn conn, int full_bgn, int full_end) {
		Db_rdr rdr = Db_rdr_.Null; Db_stmt stmt = Db_stmt_.Null;
		int part_len = Interval();
		for (int part_bgn = full_bgn; part_bgn < full_end; part_bgn += part_len) {
			int part_end = part_bgn + part_len;
			if (part_end > full_end) part_end = full_end;
			try {
				stmt = conn.Stmt_new(Make_qry(db_ctx, part_bgn, part_end));
				Fill_stmt(stmt, part_bgn, part_end);
				rdr = stmt.Exec_select_as_rdr();
				Read_data(cancelable, db_ctx, rdr);
			}
			finally {rdr.Rls(); stmt.Rls();}
		}
	}
	public static Object[] In_ary(int len) {
		Object[] rv = new Object[len];
		for (int i = 0; i < len; i++)
			rv[i] = "";
		return rv;
	}
}
