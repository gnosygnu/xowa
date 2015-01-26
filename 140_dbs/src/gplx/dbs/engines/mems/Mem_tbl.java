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
package gplx.dbs.engines.mems; import gplx.*; import gplx.dbs.*; import gplx.dbs.engines.*;
import gplx.criterias.*;
public class Mem_tbl {
	private final ListAdp rows = ListAdp_.new_(); private final ListAdp where_rows = ListAdp_.new_();
	public int Insert(Db_stmt__mem stmt) {
		Db_qry_insert qry = (Db_qry_insert)stmt.Qry();
		String[] cols = qry.Cols_for_insert(); int len = cols.length;
		Mem_itm itm = new Mem_itm();
		for (int i = 0; i < len; ++i) 
			itm.Set_by(cols[i], stmt.Args_get_at(i));
		rows.Add(itm);
		return 1;
	}
	public int Update(Db_stmt__mem stmt) {
		Db_qry_update qry = (Db_qry_update)stmt.Qry();
		Select_rows_where(where_rows, stmt, qry.Cols_where());
		int where_rows_len = where_rows.Count();
		String[] update_cols = qry.Cols_update(); int update_cols_len = update_cols.length;
		for (int i = 0; i < where_rows_len; ++i) {
			Mem_itm itm = (Mem_itm)where_rows.FetchAt(i);
			for (int j = 0; j < update_cols_len; ++j)
				itm.Set_by(update_cols[j], stmt.Args_get_at(j));
		}
		return where_rows_len;
	}
	public int Delete(Db_stmt__mem stmt) {
		Db_qry_delete qry = (Db_qry_delete)stmt.Qry();
		Select_rows_where(where_rows, stmt, qry.Where_cols());
		int where_rows_len = where_rows.Count();
		for (int i = 0; i < where_rows_len; ++i) {
			Mem_itm itm = (Mem_itm)where_rows.FetchAt(i);
			rows.Del(itm);
		}
		return where_rows_len;
	}
	public Db_rdr Select(Db_stmt__mem stmt) {
		Db_qry__select_in_tbl qry = (Db_qry__select_in_tbl)stmt.Qry();
		Select_rows_where(where_rows, stmt, qry.Where_flds());
		return new Db_rdr__mem(qry.Select_flds(), (Mem_itm[])where_rows.Xto_ary_and_clear(Mem_itm.class));
	}
	private void Select_rows_where(ListAdp rv, Db_stmt__mem stmt, String[] where_cols) {
		int where_len = where_cols.length;
		KeyVal[] where_kvs = Bld_where_kvs(stmt, where_cols, where_len);
		Bld_where_rows(where_rows, where_kvs, where_len);
	}
	private KeyVal[] Bld_where_kvs(Db_stmt__mem stmt, String[] keys, int keys_len) {
		KeyVal[] rv = new KeyVal[keys_len];
		for (int i = 0; i < keys_len; ++i)
			rv[i] = KeyVal_.new_(keys[i], stmt.Args_get_at(i));
		return rv;
	}
	private void Bld_where_rows(ListAdp rv, KeyVal[] where_kvs, int where_len) {
		rv.Clear();
		int rows_len = rows.Count();
		for (int i = 0; i < rows_len; ++i) {
			Mem_itm itm = (Mem_itm)rows.FetchAt(i);
			for (int j = 0; j < where_len; ++j) {
				KeyVal kv = where_kvs[j];
				Object itm_val = itm.Get_by(kv.Key());
				if (!Object_.Eq(itm_val, kv.Val())) break;
			}
			rv.Add(itm);
		}
	}
//		private void Select_rows_where(ListAdp rv, Db_stmt__mem stmt, Criteria crt) {
//			rv.Clear();
//			int rows_len = rows.Count();
//			for (int i = 0; i < rows_len; ++i) {
//				Mem_itm itm = (Mem_itm)rows.FetchAt(i);
//				if (crt.Matches(itm)) 
//					rv.Add(itm);
//			}
//		}
}
