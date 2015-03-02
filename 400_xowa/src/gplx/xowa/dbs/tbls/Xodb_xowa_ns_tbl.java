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
public class Xodb_xowa_ns_tbl {
	private String tbl_name = "wiki_ns_regy"; private final Db_meta_fld_list flds = Db_meta_fld_list.new_();
	private String fld_db_id, fld_ns_id, fld_ns_name, fld_ns_case, fld_ns_count, fld_ns_is_alias;		
	private Db_conn conn; private int db_id;
	public void Conn_(Db_conn new_conn, boolean created, boolean schema_is_1, int db_id) {
		this.conn = new_conn; flds.Clear(); this.db_id = db_id;
		if (schema_is_1) {
			tbl_name			= "xowa_ns";
			fld_db_id			= Db_meta_fld.Key_null;
		}
		else {
			fld_db_id			= flds.Add_int("db_id");
		}
		fld_ns_id				= flds.Add_int("ns_id");
		fld_ns_name				= flds.Add_str("ns_name", 255);
		fld_ns_case				= flds.Add_byte("ns_case");
		fld_ns_is_alias			= flds.Add_bool("ns_is_alias");
		fld_ns_count			= flds.Add_int("ns_count");
		if (created) {
			Db_meta_tbl meta_tbl = Db_meta_tbl.new_(tbl_name, flds
			, Db_meta_idx.new_unique_by_tbl_wo_null(tbl_name, "pkey"		, fld_db_id, fld_ns_id)
			);
			conn.Exec_create_tbl_and_idx(meta_tbl);
		}
	}
	public void Insert(Xow_ns_mgr ns_mgr) {
		Db_stmt stmt = conn.Stmt_insert(tbl_name, flds);
		int len = ns_mgr.Ids_len();
		for (int i = 0; i < len; i++) {
			Xow_ns ns = ns_mgr.Ids_get_at(i);
			stmt.Clear()
				.Val_int(fld_db_id, db_id)
				.Val_int(fld_ns_id, ns.Id())
				.Val_str(fld_ns_name, ns.Name_str())
				.Val_byte(fld_ns_case, ns.Case_match())
				.Val_bool_as_byte(fld_ns_is_alias, ns.Is_alias())
				.Val_int(fld_ns_count, ns.Count())
				.Exec_insert();
				;
		}
	}
	public void Select_all(Xow_ns_mgr ns_mgr) {
		Db_rdr rdr = Db_rdr_.Null;
		try {
			rdr = conn.Stmt_select(tbl_name, flds, String_.Ary_wo_null(fld_db_id)).Crt_int(fld_db_id, db_id).Exec_select_as_rdr();
			ns_mgr.Clear();
			while (rdr.Move_next()) {
				int ns_id			= rdr.Read_int(fld_ns_id);
				byte[] ns_name		= rdr.Read_bry_by_str(fld_ns_name);
				byte ns_case_match	= rdr.Read_byte(fld_ns_case);
				int ns_count		= rdr.Read_int(fld_ns_count);
				boolean ns_is_alias	= rdr.Read_byte(fld_ns_is_alias) == Bool_.Y_byte;
				ns_mgr.Add_new(ns_id, ns_name, ns_case_match, ns_is_alias);
				if (ns_id < 0) continue;			// don't load counts for Special / Media					
				Xow_ns ns = ns_mgr.Ids_get_or_null(ns_id);
				ns.Count_(ns_count);
				if (ns_count > 0) ns.Exists_(true);	// ns has article; mark it as exists, else Talk tab won't show; DATE:2013-12-04
			}
			ns_mgr.Init();
		}	finally {rdr.Rls();}
	}
	public int Select_ns_count(int ns_id) {
		Db_rdr rdr = Db_rdr_.Null;
		try {
			rdr = conn.Stmt_select(tbl_name, flds, String_.Ary_wo_null(fld_db_id, fld_ns_id))
				.Crt_int(fld_db_id, db_id)
				.Crt_int(fld_ns_id, ns_id)
				.Exec_select_as_rdr();
			return rdr.Move_next() ? Int_.cast_(rdr.Read_int(fld_ns_count)) : 0;
		}	finally {rdr.Rls();}
	}
	public void Update_ns_count(int ns_id, int ns_count) {
		Db_stmt stmt = conn.Stmt_update(tbl_name, String_.Ary_wo_null(fld_db_id, fld_ns_id), fld_ns_count);
		stmt.Clear()
			.Val_int(fld_ns_count, ns_count)
			.Crt_int(fld_db_id, db_id)
			.Crt_int(fld_ns_id, ns_id)
			.Exec_update();
	}
}
