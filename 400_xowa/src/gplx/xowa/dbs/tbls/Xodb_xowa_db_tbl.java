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
import gplx.dbs.*; import gplx.dbs.qrys.*;
import gplx.xowa.wikis.data.*;
public class Xodb_xowa_db_tbl {
	private String tbl_name = "wiki_db_regy"; private final Db_meta_fld_list flds = Db_meta_fld_list.new_();
	private String fld_id, fld_type, fld_url;
	private Db_conn conn; private final Db_stmt_bldr stmt_bldr = new Db_stmt_bldr();
	public void Conn_(Db_conn new_conn, boolean created, boolean schema_is_1) {
		this.conn = new_conn; flds.Clear();
		String fld_prefix = "";
		if (schema_is_1) {
			tbl_name		= "xowa_db";
			fld_prefix		= "db_";
		}
		fld_id				= flds.Add_int_pkey	(fld_prefix + "id");
		fld_type			= flds.Add_byte		(fld_prefix + "type");			// 1=core;2=wikidata;3=data
		fld_url				= flds.Add_str		(fld_prefix + "url", 512);
		if (created) {
			Db_meta_tbl meta = Db_meta_tbl.new_(tbl_name, flds);
			conn.Exec_create_tbl_and_idx(meta);
		}
		stmt_bldr.Conn_(conn, tbl_name, flds, fld_id);
	}
	public Xowd_db_file[] Select_all(Io_url wiki_root_dir) {
		Db_rdr rdr = Db_rdr_.Null;
		ListAdp list = ListAdp_.new_();
		try {
			rdr = conn.Stmt_select(tbl_name, flds, Db_meta_fld.Ary_empy).Exec_select_as_rdr();
			while (rdr.Move_next()) {
				Xowd_db_file db = Xowd_db_file.load_(rdr.Read_int(fld_id), rdr.Read_byte(fld_type), rdr.Read_str(fld_url));
				db.Url_(wiki_root_dir.GenSubFil(db.Url_rel()));
				list.Add(db);
			}
		}	finally {rdr.Rls();}
		list.SortBy(Xodb_file_sorter__id.I);
		return (Xowd_db_file[])list.Xto_ary(Xowd_db_file.class);
	}
	public void Commit_all(Xowe_core_data_mgr core_data_mgr) {
		stmt_bldr.Batch_bgn();
		try {
			int len = core_data_mgr.Dbs__len();
			for (int i = 0; i < len; i++)
				Commit_itm(core_data_mgr.Dbs__get_at(i));
		}	finally {stmt_bldr.Batch_end();}
	}
	private void Commit_itm(Xowd_db_file itm) {
		Db_stmt stmt = stmt_bldr.Get(itm.Cmd_mode());
		switch (itm.Cmd_mode()) {
			case Db_cmd_mode.Tid_create:	stmt.Clear().Val_int(fld_id, itm.Id())	.Val_byte(fld_type, itm.Tid()).Val_str(fld_url, itm.Url_rel()).Exec_insert(); break;
			case Db_cmd_mode.Tid_update:	stmt.Clear()							.Val_byte(fld_type, itm.Tid()).Val_str(fld_url, itm.Url_rel()).Crt_int(fld_id, itm.Id()).Exec_update(); break;
			case Db_cmd_mode.Tid_delete:	stmt.Clear().Crt_int(fld_id, itm.Id()).Exec_delete();	break;
			case Db_cmd_mode.Tid_ignore:	break;
			default:						throw Err_.unhandled(itm.Cmd_mode());
		}
		itm.Cmd_mode_(Db_cmd_mode.Tid_ignore);
	}
}
class Xodb_file_sorter__id implements gplx.lists.ComparerAble {
	public int compare(Object lhsObj, Object rhsObj) {
		Xowd_db_file lhs = (Xowd_db_file)lhsObj;
		Xowd_db_file rhs = (Xowd_db_file)rhsObj;
		return Int_.Compare(lhs.Id(), rhs.Id());
	}
	public static final Xodb_file_sorter__id I = new Xodb_file_sorter__id(); Xodb_file_sorter__id() {}
}
