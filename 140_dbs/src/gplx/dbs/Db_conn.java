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
package gplx.dbs; import gplx.*;
import gplx.dbs.engines.*; import gplx.dbs.qrys.*;
public class Db_conn {
	private final Db_engine engine;
	private final ListAdp rls_list = ListAdp_.new_();
	public Db_conn(Db_engine engine) {
		this.engine = engine;
		this.txn_mgr = new Db_txn_mgr(engine);
	}
	public Db_url			Url()						{return engine.Url();}
	public Db_txn_mgr		Txn_mgr()					{return txn_mgr;} private final Db_txn_mgr txn_mgr;
	public Db_stmt			Stmt_insert(String tbl, Db_meta_fld_list flds)							{return engine.New_stmt_prep(Db_qry_insert.new_(tbl, flds.To_str_ary()));}
	public Db_stmt			Stmt_insert(String tbl, String... cols)							{return engine.New_stmt_prep(Db_qry_insert.new_(tbl, cols));}
	public Db_stmt			Stmt_update(String tbl, String[] where, String... cols)			{return engine.New_stmt_prep(Db_qry_update.new_(tbl, where, cols));}
	public Db_stmt			Stmt_update_exclude(String tbl, Db_meta_fld_list flds, String... where) {return engine.New_stmt_prep(Db_qry_update.new_(tbl, where, flds.To_str_ary_exclude(where)));}
	public Db_stmt			Stmt_delete(String tbl, String... where)							{return engine.New_stmt_prep(Db_qry_delete.new_(tbl, where));}
	public Db_stmt			Stmt_select(String tbl, String[] cols, String... where)			{return engine.New_stmt_prep(Db_qry__select_in_tbl.new_(tbl, where, cols));}
	public Db_stmt			Stmt_select(String tbl, Db_meta_fld_list flds, String... where)	{return engine.New_stmt_prep(Db_qry__select_in_tbl.new_(tbl, where, flds.To_str_ary()));}
	public void				Exec_create_tbl_and_idx(Db_meta_tbl meta) {
		engine.Exec_ddl_create_tbl(meta);
		engine.Exec_ddl_create_idx(Gfo_usr_dlg_.Null, meta.Idxs());
	}
	public void				Exec_create_idx(Gfo_usr_dlg usr_dlg, Db_meta_idx... idxs)		{engine.Exec_ddl_create_idx(usr_dlg, idxs);}
	public void				Exec_ddl_append_fld(String tbl, Db_meta_fld fld)					{engine.Exec_ddl_append_fld(tbl, fld);}
	public Db_stmt			Rls_reg(Db_stmt stmt)		{rls_list.Add(stmt); return stmt;}
	public void				Conn_term() {
		int len = rls_list.Count();
		for (int i = 0; i < len; ++i) {
			RlsAble itm = (RlsAble)rls_list.FetchAt(i);
			itm.Rls();
		}
		engine.Conn_term();
//			Db_conn_pool.I.Del(this.Url());	// remove from pool, else rls'd instance will be cached and fail upon next use
	}
	public Db_stmt			Stmt_new(Db_qry qry)		{return engine.New_stmt_prep(qry);}
	public int				Exec_qry(Db_qry qry)		{txn_mgr.Txn_count_(txn_mgr.Txn_count() + 1); return Int_.cast_(engine.Exec_as_obj(qry));}
	public DataRdr			Exec_qry_as_rdr(Db_qry qry)	{return DataRdr_.cast_(engine.Exec_as_obj(qry));}
	public int				Exec_sql(String sql)		{return this.Exec_qry(Db_qry_sql.dml_(sql));}
	public DataRdr			Exec_sql_as_rdr(String sql)	{return this.Exec_qry_as_rdr(Db_qry_sql.rdr_(sql));}
}
