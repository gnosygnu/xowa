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
import gplx.dbs.*; import gplx.xowa.ctgs.*; 
public class Xodb_categorylinks_tbl {
	public void Delete_all(Db_conn p) {p.Exec_qry(Db_qry_.delete_tbl_(Tbl_name));}
	public Db_stmt Insert_stmt(Db_conn p) {return Db_stmt_.new_insert_(p, Tbl_name, Fld_cl_from, Fld_cl_to_id, Fld_cl_sortkey, Fld_cl_timestamp, Fld_cl_type_id);}
	public void Insert(Db_stmt stmt, int page_id, int ctg_page_id, byte[] sortkey, int timestamp, byte ctg_tid) {
		stmt.Clear()
			.Val_int(page_id)
			.Val_int(ctg_page_id)
			.Val_str(String_.new_utf8_(sortkey))
			.Val_int(timestamp)
			.Val_byte(ctg_tid)
			.Exec_insert()
			;
	}
	Xoctg_idx_mgr Grp_by_tid(Xoctg_data_ctg ctg, byte tid) {
		Xoctg_idx_mgr ctg_grp = ctg.Grp_by_tid(tid);
		if (ctg_grp == null) {
			ctg_grp = new Xoctg_idx_mgr();
			ctg.Grp_mgrs()[tid] = ctg_grp;
		}
		return ctg_grp;
	}
	public int Select_by_type(Db_conn p, ListAdp list, int cat_page_id, byte arg_tid, byte[] arg_sortkey, boolean arg_is_from, int limit) {
		String arg_sortkey_str = arg_sortkey == null ? "" : String_.new_utf8_(arg_sortkey);
		gplx.core.criterias.Criteria comp_crt = !arg_is_from 
			? Db_crt_.mte_(Fld_cl_sortkey, arg_sortkey_str)		// from:  sortkey >= 'val'
			: Db_crt_.lte_(Fld_cl_sortkey, arg_sortkey_str);	// until: sortkey <= 'val'
		Db_qry_select qry = Db_qry_.select_().Cols_(Fld_cl_from, Fld_cl_sortkey).From_(Tbl_name)
			.Where_(gplx.core.criterias.Criteria_.And_many(Db_crt_.eq_(Fld_cl_to_id, -1), Db_crt_.eq_(Fld_cl_type_id, arg_tid), comp_crt))
			.OrderBy_(Fld_cl_sortkey, !arg_is_from)
			.Limit_(limit + 1);									// + 1 to get last_plus_one for next page / previous page
		Db_stmt stmt = Db_stmt_.Null;
		DataRdr rdr = DataRdr_.Null;
		int count = 0;
		try {
			stmt = p.New_stmt(qry);
			rdr = stmt.Val_int(cat_page_id).Val_byte(arg_tid).Val_str(arg_sortkey_str).Exec_select();
			while (rdr.MoveNextPeer()) {
				int itm_page_id = rdr.ReadInt(Fld_cl_from);
				byte[] itm_sortkey = rdr.ReadBryByStr(Fld_cl_sortkey);				
				Xodb_page itm = new Xodb_page().Id_(itm_page_id).Xtn_(new Xoctg_page_xtn(arg_tid, itm_sortkey));
				list.Add(itm);
				++count;
			}
		}	finally {rdr.Rls(); stmt.Rls();}
		list.SortBy(Xodb_page_sorter.Ctg_tid_sortkey_asc);
		return count;
	}
	public static final String Tbl_name = "categorylinks", Fld_cl_from = "cl_from", Fld_cl_to = "cl_to", Fld_cl_to_id = "cl_to_id", Fld_cl_sortkey = "cl_sortkey", Fld_cl_timestamp = "cl_timestamp", Fld_cl_type_id = "cl_type_id";
}
