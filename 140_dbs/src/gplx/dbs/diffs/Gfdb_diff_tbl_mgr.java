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
package gplx.dbs.diffs; import gplx.*; import gplx.dbs.*;
class Gfdb_diff_tbl_mgr {
	private final Ordered_hash hash = Ordered_hash_.New();
	public int Len() {return hash.Count();}
	public Gfdb_diff_tbl Get_at(int idx) {return (Gfdb_diff_tbl)hash.Get_at(idx);}
	public Gfdb_diff_tbl Get_by(String key) {return (Gfdb_diff_tbl)hash.Get_by(key);}
}
class Gfdb_diff_tbl_mgr__sqlite {
	public void Fill(Gfdb_diff_tbl_mgr tbl_mgr, Db_conn conn) {
		// String schema_str = ""; // conn.Get_schema();
	}
	public void Fill(Gfdb_diff_tbl_mgr tbl_mgr, String schema_str) {
		/*
		Db_conn conn = null;
		conn.Meta_get_tbls(Gfdb_diff_tbl_mgr tbl_mgr, "");
		*/
	}
}	
