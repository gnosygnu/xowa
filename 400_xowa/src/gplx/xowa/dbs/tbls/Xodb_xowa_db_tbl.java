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
public class Xodb_xowa_db_tbl {
	public void Provider_(Db_provider provider) {this.provider = provider;} Db_provider provider;
	public void Update_url(Db_provider provider, int db_id, String db_url) {
		Db_stmt stmt = Db_stmt_.Null;
		try {
			stmt = Db_stmt_.new_update_(provider, Tbl_name, String_.Ary(Fld_db_id), Fld_db_url);
			stmt.Clear()
				.Val_str_(db_url)
				.Val_int_(db_id)
				.Exec_update();
				;
		} finally {stmt.Rls();}
	}
	public void Commit_all(Xodb_fsys_mgr db_fs) {this.Commit_all(db_fs.Provider_core(), db_fs.Files_ary());}
	public void Commit_all(Db_provider provider, Xodb_file[] ary) {
		stmt_bldr.Init(provider);
		try {
			int len = ary.length;
			for (int i = 0; i < len; i++)
				Commit_itm(ary[i]);
			stmt_bldr.Commit();
		}	finally {stmt_bldr.Rls();}
	}
	private void Commit_itm(Xodb_file itm) {
		Db_stmt stmt = stmt_bldr.Get(itm.Cmd_mode());
		switch (itm.Cmd_mode()) {
			case Db_cmd_mode.Create:	stmt.Clear().Val_int_(itm.Id())	.Val_byte_(itm.Tid()).Val_str_(itm.Url_rel()).Exec_insert(); break;
			case Db_cmd_mode.Update:	stmt.Clear()					.Val_byte_(itm.Tid()).Val_str_(itm.Url_rel()).Val_int_(itm.Id()).Exec_update(); break;
			case Db_cmd_mode.Delete:	stmt.Clear().Val_int_(itm.Id()).Exec_delete();	break;
			case Db_cmd_mode.Ignore:	break;
			default:					throw Err_.unhandled(itm.Cmd_mode());
		}
		itm.Cmd_mode_(Db_cmd_mode.Ignore);
	}
	public Xodb_file[] Select_all(Db_provider provider) {
		DataRdr rdr = DataRdr_.Null;
		ListAdp list = ListAdp_.new_();
		try {
			Db_qry qry = Db_qry_.select_tbl_(Tbl_name).OrderBy_asc_(Fld_db_id);
			rdr = provider.Exec_qry_as_rdr(qry);
			while (rdr.MoveNextPeer()) {
				Xodb_file db = Xodb_file.load_(rdr.ReadInt(Fld_db_id), rdr.ReadByte(Fld_db_type), rdr.ReadStr(Fld_db_url));
				list.Add(db);
			}
		} finally {rdr.Rls();}
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
	public static final String Tbl_name = "xowa_db", Fld_db_id = "db_id", Fld_db_type = "db_type", Fld_db_url = "db_url";
	Db_stmt_bldr stmt_bldr = new Db_stmt_bldr(Tbl_name, String_.Ary(Fld_db_id), Fld_db_type, Fld_db_url);
}
class Xodb_db_sorter implements gplx.lists.ComparerAble {
	public int compare(Object lhsObj, Object rhsObj) {
		Xodb_file lhs = (Xodb_file)lhsObj;
		Xodb_file rhs = (Xodb_file)rhsObj;
		return Int_.Compare(lhs.Id(), rhs.Id());
	}
	public static final Xodb_db_sorter _ = new Xodb_db_sorter(); 
}
