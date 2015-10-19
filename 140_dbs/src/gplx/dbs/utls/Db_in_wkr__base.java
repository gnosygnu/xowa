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
	@gplx.Virtual protected int Interval() {return gplx.dbs.engines.sqlite.Sqlite_engine_.Stmt_arg_max - 10;}	// -10 for safety's sake
	protected abstract Db_qry	Make_qry	(int bgn, int end);
	protected abstract void		Fill_stmt	(Db_stmt stmt, int bgn, int end);
	protected abstract void		Read_data	(Cancelable cancelable, Db_rdr rdr);
	@gplx.Virtual protected boolean		Show_progress() {return false;}
	public void Select_in(Cancelable cancelable, Db_conn conn, int full_bgn, int full_end) {
		int part_len = Interval();
		Gfo_usr_dlg usr_dlg = Gfo_usr_dlg_.Instance;
		boolean show_progress = this.Show_progress();
		for (int part_bgn = full_bgn; part_bgn < full_end; part_bgn += part_len) {
			int part_end = part_bgn + part_len;
			if (part_end > full_end) part_end = full_end;
			Db_stmt stmt = Db_stmt_.Null; Db_rdr rdr = Db_rdr_.Empty; 
			try {
				if (show_progress) usr_dlg.Prog_many("", "", "reading: count=~{0}", part_end);
				stmt = conn.Stmt_new(Make_qry(part_bgn, part_end));
				Fill_stmt(stmt, part_bgn, part_end);
				rdr = stmt.Exec_select__rls_manual();
				Read_data(cancelable, rdr);
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
