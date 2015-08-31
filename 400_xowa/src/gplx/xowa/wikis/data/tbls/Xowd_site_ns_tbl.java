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
package gplx.xowa.wikis.data.tbls; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*; import gplx.xowa.wikis.data.*;
import gplx.dbs.*; import gplx.dbs.qrys.*;
public class Xowd_site_ns_tbl {
	private final String tbl_name; private final Db_meta_fld_list flds = Db_meta_fld_list.new_();
	private final String fld_id, fld_name, fld_case, fld_count, fld_is_alias;		
	private final Db_conn conn;
	public Xowd_site_ns_tbl(Db_conn conn, boolean schema_is_1) {
		this.conn = conn;
		this.tbl_name = schema_is_1 ? "xowa_ns" : "site_ns";
		fld_id				= flds.Add_int_pkey	("ns_id");
		fld_name			= flds.Add_str		("ns_name", 255);
		fld_case			= flds.Add_byte		("ns_case");
		fld_is_alias		= flds.Add_bool		("ns_is_alias");
		fld_count			= flds.Add_int		("ns_count");
	}
	public void Create_tbl() {conn.Ddl_create_tbl(Db_meta_tbl.new_(tbl_name, flds));}
	public void Insert(Xow_ns_mgr ns_mgr) {
		Db_stmt stmt = conn.Stmt_insert(tbl_name, flds);
		int len = ns_mgr.Ids_len();
		for (int i = 0; i < len; i++) {
			Xow_ns ns = ns_mgr.Ids_get_at(i);
			stmt.Clear()
				.Val_int(fld_id, ns.Id())
				.Val_str(fld_name, ns.Name_str())
				.Val_byte(fld_case, ns.Case_match())
				.Val_bool_as_byte(fld_is_alias, ns.Is_alias())
				.Val_int(fld_count, ns.Count())
				.Exec_insert();
				;
		}
	}
	public void Select_all(Xow_ns_mgr ns_mgr) {
		Db_rdr rdr = conn.Stmt_select(tbl_name, flds).Exec_select__rls_auto();
		try {
			ns_mgr.Clear();
			while (rdr.Move_next()) {
				int ns_id			= rdr.Read_int(fld_id);
				byte[] ns_name		= rdr.Read_bry_by_str(fld_name);
				byte ns_case_match	= rdr.Read_byte(fld_case);
				int ns_count		= rdr.Read_int(fld_count);
				boolean ns_is_alias	= rdr.Read_byte(fld_is_alias) == Bool_.Y_byte;
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
		Db_rdr rdr = conn.Stmt_select(tbl_name, flds, String_.Ary(fld_id))
				.Crt_int(fld_id, ns_id)
				.Exec_select__rls_auto();
		try {
			return rdr.Move_next() ? Int_.cast(rdr.Read_int(fld_count)) : 0;
		}	finally {rdr.Rls();}
	}
	public void Update_ns_count(int ns_id, int ns_count) {
		Db_stmt stmt = conn.Stmt_update(tbl_name, String_.Ary(fld_id), fld_count);
		stmt.Clear()
			.Val_int(fld_count, ns_count)
			.Crt_int(fld_id, ns_id)
			.Exec_update();
	}
}
