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
package gplx.xowa.dbs.tbls; import gplx.*; import gplx.xowa.*; import gplx.xowa.dbs.*;
import gplx.dbs.*;
public abstract class Xodb_in_wkr_base {
	public abstract int Interval();
	public abstract void Fill_stmt(Db_stmt stmt, int bgn, int end);
	public abstract Db_qry Build_qry(int bgn, int end);
	public abstract void Eval_rslts(Cancelable cancelable, DataRdr rdr);
	public void Select_in(Db_provider provider, Cancelable cancelable, int full_bgn, int full_end) {
		DataRdr rdr = DataRdr_.Null; 
		Db_stmt stmt = Db_stmt_.Null;
		int interval = Interval();
		for (int i = full_bgn; i < full_end; i += interval) {
			int part_end = i + interval;
			if (part_end > full_end) part_end = full_end;
			try {
				stmt = provider.Prepare(Build_qry(i, part_end));
				Fill_stmt(stmt, i, part_end);
				rdr = stmt.Exec_select();
				Eval_rslts(cancelable, rdr);
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
