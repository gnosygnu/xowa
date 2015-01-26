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
import gplx.dbs.*; import gplx.xowa.*;
public class Xow_ns_regy_tbl implements Db_conn_itm {
	public Xow_ns_regy_tbl() {Ctor_for_meta();}
	public void Conn_term() {}
	public Db_conn Conn() {return conn;} public Xow_ns_regy_tbl Conn_(Db_conn v) {conn = Db_conn_.Reg_itm(this, conn, v); return this;} private Db_conn conn;
	public void Insert(Xow_ns_mgr ns_mgr) {
		Db_stmt stmt = Db_stmt_.Null;
		try {
			stmt = conn.New_stmt_insert(tbl_name, Flds.Xto_str_ary());
			int len = ns_mgr.Ids_len();
			for (int i = 0; i < len; i++) {
				Xow_ns ns = ns_mgr.Ids_get_at(i);
				stmt.Clear().Val_int(ns.Id()).Val_str(ns.Name_str()).Val_byte(ns.Case_match()).Val_bool_as_byte(ns.Is_alias()).Val_int(ns.Count())
					.Exec_insert();
			}
		} finally {stmt.Rls();}
	}
	public void Select_all(Xow_ns_mgr ns_mgr) {
		Db_rdr rdr = Db_rdr_.Null; Db_stmt stmt = Db_stmt_.Null;
		try {
			stmt = conn.New_stmt_select_all_where(tbl_name, Flds.Xto_str_ary(), Db_qry__select_in_tbl.Where_flds__all);
			rdr = stmt.Exec_select_as_rdr();
			ns_mgr.Clear();
			while (rdr.Move_next()) {
				int ns_id			= rdr.Read_int(Fld_ns_id);
				byte[] ns_name		= rdr.Read_bry_by_str(Fld_ns_name);
				byte ns_case_match	= rdr.Read_byte(Fld_ns_case);
				int ns_count		= rdr.Read_int(Fld_ns_count);
				boolean ns_is_alias	= rdr.Read_bool_by_byte(Fld_ns_is_alias);
				ns_mgr.Add_new(ns_id, ns_name, ns_case_match, ns_is_alias);
				if (ns_id < 0) continue;			// don't load counts for Special / Media					
				Xow_ns ns = ns_mgr.Ids_get_or_null(ns_id);
				ns.Count_(ns_count);
				if (ns_count > 0) ns.Exists_(true);	// ns has article; mark it as exists, else Talk tab won't show; DATE:2013-12-04
			}
			ns_mgr.Init();
		}	finally {rdr.Rls(); stmt.Rls();}
	}
//		public int Select_ns_count(int ns_id) {
//			Db_qry_select qry = conn.New_stmt_select_all_where(tbl_name, String_.Ary(Fld_ns_count), Db_crt_.eq_(Fld_ns_id, ns_id));
//			return Int_.cast_(qry.ExecRdr_val(conn));
//		}
	public void Update_ns_count(int ns_id, int ns_count) {
		Db_stmt stmt = conn.New_stmt_update(tbl_name, String_.Ary(Fld_ns_id), Fld_ns_count);
		stmt.Clear().Val_int(ns_count).Val_int(ns_id).Exec_update();
	}
	private String tbl_name = "wiki_ns_regy";
	private Db_meta_fld_list Flds = new Db_meta_fld_list();
	private String Fld_ns_id, Fld_ns_name, Fld_ns_case, Fld_ns_is_alias, Fld_ns_count;
	private void Ctor_for_meta() {
		Fld_ns_id				= Flds.Add_int_pkey("ns_id");
		Fld_ns_name				= Flds.Add_str("ns_name", 255);
		Fld_ns_case				= Flds.Add_byte("ns_case");
		Fld_ns_is_alias			= Flds.Add_bool("ns_is_alias");
		Fld_ns_count			= Flds.Add_int("ns_count");
	}
	public Db_meta_tbl Meta_tbl() {
		return Db_meta_tbl.new_(tbl_name, Flds.Xto_fld_ary());
	} 
}
