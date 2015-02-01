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
public class Xowd_db_regy_tbl implements Db_conn_itm {
	private String Tbl_name = "wiki_db_regy";
	private final Db_meta_fld_list Flds = Db_meta_fld_list.new_();
	private String Fld_id, Fld_type, Fld_url;
	private Db_conn conn;
	public void Conn_(Db_conn new_conn, boolean version_is_1) {
		this.conn = Db_conn_.Reg_itm(this, conn, new_conn);
		String name_prefix = "";
		if (version_is_1) {
			Tbl_name			= "xowa_db";
			name_prefix			= "db_";
		}
		else {
			//Fld_wiki_id			= Flds.Add_int("wiki_id");
		}
		Fld_id					= Flds.Add_int	(name_prefix + "id");
		Fld_type				= Flds.Add_byte	(name_prefix + "type");
		Fld_url					= Flds.Add_str	(name_prefix + "url", 512);
	}
	public void Conn_term() {}
	public Xodb_file[] Select_all(Io_url wiki_root_dir) {
		Db_rdr rdr = Db_rdr_.Null;
		ListAdp list = ListAdp_.new_();
		try {
			Db_stmt stmt = conn.New_stmt_select_all_where(Tbl_name, Flds, Db_meta_fld.Ary_empy);
			rdr = stmt.Exec_select_as_rdr();
			while (rdr.Move_next()) {
				Xodb_file db = Xodb_file.load_(rdr.Read_int(Fld_id), rdr.Read_byte(Fld_type), rdr.Read_str(Fld_url));
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
