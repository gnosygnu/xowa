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
public class Xodb_xowa_db_tbl {
	private String tbl_name = "wiki_db_regy"; private final Db_meta_fld_list flds = Db_meta_fld_list.new_();
	private String fld_id, fld_type, fld_url;
	private Db_conn conn; private final Db_stmt_bldr stmt_bldr = new Db_stmt_bldr();
	public void Conn_(Db_conn new_conn, boolean created, boolean version_is_1) {
		this.conn = new_conn; flds.Clear();
		String fld_prefix = "";
		if (version_is_1) {
			tbl_name		= "xowa_db";
			fld_prefix		= "db_";
		}
		fld_id				= flds.Add_int_pkey(fld_prefix + "id");
		fld_type			= flds.Add_byte(fld_prefix + "type");			// 1=core;2=wikidata;3=data
		fld_url				= flds.Add_str(fld_prefix + "url", 512);
		if (created) {
			Db_meta_tbl meta = Db_meta_tbl.new_(tbl_name, flds);
			conn.Exec_create_tbl_and_idx(meta);
		}
		stmt_bldr.Conn_(conn, tbl_name, flds, fld_id);
	}
	public void Update_url(int id, String url) {
		Db_stmt stmt = conn.Stmt_update(tbl_name, String_.Ary(fld_id), fld_url);
		stmt.Clear().Val_str(fld_url, url).Crt_int(fld_id, id).Exec_update();
	}
	public void Commit_all(Xodb_fsys_mgr db_fs) {this.Commit_all(db_fs.Files_ary());}
	public void Commit_all(Xodb_file[] ary) {
		stmt_bldr.Batch_bgn();
		try {
			int len = ary.length;
			for (int i = 0; i < len; i++)
				Commit_itm(ary[i]);
		}	finally {stmt_bldr.Batch_end();}
	}
	private void Commit_itm(Xodb_file itm) {
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
	public Xodb_file[] Select_all() {
		Db_rdr rdr = Db_rdr_.Null;
		ListAdp list = ListAdp_.new_();
		try {
			Db_qry qry = Db_qry_.select_tbl_(tbl_name).OrderBy_asc_(fld_id);
			rdr = conn.Stmt_new(qry).Exec_select_as_rdr();
			while (rdr.Move_next()) {
				Xodb_file itm = Xodb_file.load_(rdr.Read_int(fld_id), rdr.Read_byte(fld_type), rdr.Read_str(fld_url));
				list.Add(itm);
			}
		} finally {rdr.Rls();}
		Xodb_file[] rv = (Xodb_file[])list.Xto_ary(Xodb_file.class);
		Chk_sequential(rv);
		return rv;
	}
	private void Chk_sequential(Xodb_file[] ary) {
		int len = ary.length;
		int expd_id = 0;
		for (int i = 0; i < len; ++i) {
			Xodb_file itm = ary[i];
			int actl_id = itm.Id();
			if (expd_id != actl_id) throw Err_.new_fmt_("database ids are not sequential; expd={0} actl={1}", expd_id, actl_id);
			++expd_id;
		}
	}
}
class Xodb_db_sorter implements gplx.lists.ComparerAble {
	public int compare(Object lhsObj, Object rhsObj) {
		Xodb_file lhs = (Xodb_file)lhsObj;
		Xodb_file rhs = (Xodb_file)rhsObj;
		return Int_.Compare(lhs.Id(), rhs.Id());
	}
	public static final Xodb_db_sorter _ = new Xodb_db_sorter(); 
}
