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
package gplx.xowa2.wikis.data.tbls; import gplx.*; import gplx.xowa2.*; import gplx.xowa2.wikis.*; import gplx.xowa2.wikis.data.*;
import gplx.dbs.*; import gplx.xowa.dbs.*;
public class Xodata_db_tbl {
	public static final String Tbl_name = "xowa_db", Fld_db_id = "db_id", Fld_db_type = "db_type", Fld_db_url = "db_url";
	private static final String[] Flds__all = new String[] {Fld_db_id, Fld_db_type, Fld_db_url};
	public Xodb_file[] Select_all(Db_conn conn, Io_url wiki_root_dir) {
		Db_rdr rdr = Db_rdr_.Null;
		ListAdp list = ListAdp_.new_();
		try {
			Db_qry__select_in_tbl qry = Db_qry__select_in_tbl.new_(Tbl_name, Db_qry__select_in_tbl.Where_flds__all, Flds__all).Order_by_sql_(Fld_db_id);
			Db_stmt stmt = Db_stmt_.new_select_as_rdr(conn, qry);
			rdr = stmt.Exec_select_as_rdr();
			while (rdr.Move_next()) {
				Xodb_file db = Xodb_file.load_(rdr.Read_int(0), rdr.Read_byte(1), rdr.Read_str(2));
				db.Url_(wiki_root_dir.GenSubFil(db.Url_rel()));
				list.Add(db);
			}
		}	finally {rdr.Rls();}
		Xodb_file[] rv = (Xodb_file[])list.Xto_ary(Xodb_file.class);
		Chk_sequential(rv);
		return rv;
	}
	private void Chk_sequential(Xodb_file[] ary) {
		int len = ary.length;
		int expd_id = 0;
		for (int i = 0; i < len; i++) {
			Xodb_file itm = ary[i];
			int actl_id = itm.Id();
			if (expd_id != actl_id) throw Err_.new_fmt_("database ids are not sequential; expd={0} actl={1}", expd_id, actl_id);
			++expd_id;
		}
	}
	//Db_stmt_bldr stmt_bldr = new Db_stmt_bldr(Tbl_name, String_.Ary(Fld_db_id), Fld_db_type, Fld_db_url);
}
