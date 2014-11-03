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
public class Xodb_xowa_ns_tbl {
	public void Provider_(Db_provider provider) {this.provider = provider;} private Db_provider provider;
	public void Insert(Xow_ns_mgr ns_mgr) {
		Db_stmt stmt = Db_stmt_.Null;
		try {
			stmt = Db_stmt_.new_insert_(provider, Tbl_name, Fld_ns_id, Fld_ns_name, Fld_ns_case, Fld_ns_is_alias, Fld_ns_count);
			int len = ns_mgr.Ids_len();
			for (int i = 0; i < len; i++) {
				Xow_ns ns = ns_mgr.Ids_get_at(i);
				stmt.Clear()
					.Val_int_(ns.Id())
					.Val_str_(ns.Name_str())
					.Val_byte_(ns.Case_match())
					.Val_bool_(ns.Is_alias())
					.Val_int_(ns.Count())
					.Exec_insert();
					;
			}
		} finally {stmt.Rls();}
	}
	public void Select_all(Xow_ns_mgr ns_mgr) {
		Db_rdr rdr = Db_rdr_.Null; Db_stmt stmt = Db_stmt_.Null;
		try {
			stmt = Db_stmt_.new_select_as_rdr(provider, Db_qry__select_in_tbl.new_(Tbl_name, Db_qry__select_in_tbl.Where_flds__all, Flds__all));
			rdr = stmt.Exec_select_as_rdr();
			ns_mgr.Clear();
			while (rdr.Move_next()) {
				int ns_id			= rdr.Read_int(0);
				byte[] ns_name		= rdr.Read_bry_by_str(1);
				byte ns_case_match	= rdr.Read_byte(2);
				int ns_count		= rdr.Read_int(3);
				boolean ns_is_alias	= rdr.Read_byte(4) == Bool_.Y_byte;
				ns_mgr.Add_new(ns_id, ns_name, ns_case_match, ns_is_alias);
				if (ns_id < 0) continue;			// don't load counts for Special / Media					
				Xow_ns ns = ns_mgr.Ids_get_or_null(ns_id);
				ns.Count_(ns_count);
				if (ns_count > 0) ns.Exists_(true);	// ns has article; mark it as exists, else Talk tab won't show; DATE:2013-12-04
			}
			ns_mgr.Init();
		}	finally {rdr.Close(); stmt.Rls();}
	}
	public int Select_ns_count(int ns_id) {
		Db_qry_select qry = Db_qry_.select_val_(Tbl_name, Fld_ns_count, Db_crt_.eq_(Fld_ns_id, ns_id));
		return Int_.cast_(qry.ExecRdr_val(provider));
	}
	public void Update_ns_count(int ns_id, int ns_count) {
		provider.Exec_qry(Db_qry_.update_common_(Tbl_name
		, Db_crt_.eq_(Fld_ns_id, ns_id) 
		, KeyVal_.Ary
		(	KeyVal_.new_(Fld_ns_count, ns_count)
		)));
	}
	public static final String Tbl_name = "xowa_ns"
	, Fld_ns_id = "ns_id", Fld_ns_name = "ns_name", Fld_ns_case = "ns_case", Fld_ns_count = "ns_count", Fld_ns_is_alias = "ns_is_alias"
	;
	private static final String[] Flds__all = new String[] {Fld_ns_id, Fld_ns_name, Fld_ns_case, Fld_ns_count, Fld_ns_is_alias};
}
