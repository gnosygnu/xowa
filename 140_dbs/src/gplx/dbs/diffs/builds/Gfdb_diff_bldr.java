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
package gplx.dbs.diffs.builds; import gplx.*; import gplx.dbs.*; import gplx.dbs.diffs.*;
import gplx.dbs.*;
public class Gfdb_diff_bldr {
	private Gfdb_diff_rdr_comparer rdr_comparer = new Gfdb_diff_rdr_comparer();
	private Gfdb_diff_wkr diff_wkr;
	public void Init(Gfdb_diff_wkr diff_wkr) {this.diff_wkr = diff_wkr;}
	public void Compare(Gfdb_diff_tbl tbl, Db_conn old_conn, Db_conn new_conn) {
		Db_rdr old_rdr = tbl.Make_rdr(old_conn), new_rdr = tbl.Make_rdr(new_conn);
		rdr_comparer.Init_rdrs(tbl, old_rdr, new_rdr);
		diff_wkr.Init_rdrs(tbl, old_rdr, new_rdr);
		boolean loop = true;
		while (loop) {
			int rslt = rdr_comparer.Compare();
			switch (rslt) {
				case Gfdb_diff_rdr_comparer.Rslt__same:				diff_wkr.Handle_same(); break;
				case Gfdb_diff_rdr_comparer.Rslt__old_missing:		diff_wkr.Handle_old_missing(); break;
				case Gfdb_diff_rdr_comparer.Rslt__new_missing:		diff_wkr.Handle_new_missing(); break;
				case Gfdb_diff_rdr_comparer.Rslt__done:				loop = false; break;
			}
		}
	}
}
