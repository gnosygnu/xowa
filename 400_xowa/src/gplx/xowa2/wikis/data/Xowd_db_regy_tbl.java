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
package gplx.xowa2.wikis.data; import gplx.*; import gplx.xowa2.*; import gplx.xowa2.wikis.*;
import gplx.lists.*;
import gplx.dbs.*; import gplx.xowa.dbs.*;
public class Xowd_db_regy_tbl {
	private String tbl_name = "wiki_db_regy";
	private final Db_meta_fld_list flds = Db_meta_fld_list.new_();
	private String fld_id, fld_type, fld_url;
	private Db_conn conn;
	public void Conn_(Db_conn new_conn, boolean version_is_1) {
		this.conn = new_conn; flds.Clear();
		String name_prefix = "";
		if (version_is_1) {
			tbl_name			= "xowa_db";
			name_prefix			= "db_";
		}
		else {
			//fld_db_id			= flds.Add_int("db_id");
		}
		fld_id					= flds.Add_int	(name_prefix + "id");
		fld_type				= flds.Add_byte	(name_prefix + "type");
		fld_url					= flds.Add_str	(name_prefix + "url", 512);
	}
	public Xodb_file[] Select_all(Io_url wiki_root_dir) {
		Db_rdr rdr = Db_rdr_.Null;
		ListAdp list = ListAdp_.new_();
		try {
			Db_stmt stmt = conn.Stmt_select(tbl_name, flds, Db_meta_fld.Ary_empy);
			rdr = stmt.Exec_select_as_rdr();
			while (rdr.Move_next()) {
				Xodb_file db = Xodb_file.load_(rdr.Read_int(fld_id), rdr.Read_byte(fld_type), rdr.Read_str(fld_url));
				db.Url_(wiki_root_dir.GenSubFil(db.Url_rel()));
				list.Add(db);
			}
		}	finally {rdr.Rls();}
		list.SortBy(Xodb_file_sorter__id.I);
		return (Xodb_file[])list.Xto_ary(Xodb_file.class);
	}
}
class Xodb_file_sorter__id implements ComparerAble {
	public int compare(Object lhsObj, Object rhsObj) {
		Xodb_file lhs = (Xodb_file)lhsObj;
		Xodb_file rhs = (Xodb_file)rhsObj;
		return Int_.Compare(lhs.Id(), rhs.Id());
	}
	public static final Xodb_file_sorter__id I = new Xodb_file_sorter__id(); Xodb_file_sorter__id() {}
}
