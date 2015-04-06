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
	private final ListAdp rls_list = ListAdp_.new_(); private final Db_engine engine;
	public Db_conn(Db_engine engine) {this.engine = engine;}
	public Db_conn_info		Conn_info()				{return engine.Conn_info();}
	public void				Txn_bgn()				{engine.Txn_bgn("");}
	public void				Txn_bgn(String name)	{engine.Txn_bgn(name);}
	public void				Txn_end()				{engine.Txn_end();}
	public void				Txn_cxl()				{engine.Txn_cxl();}
	public void				Txn_sav()				{engine.Txn_sav();}
	public Db_stmt			Stmt_insert(String tbl, Db_meta_fld_list flds)							{return engine.New_stmt_prep(Db_qry_insert.new_(tbl, flds.To_str_ary_wo_autonum()));}
	public Db_stmt			Stmt_insert(String tbl, String... cols)							{return engine.New_stmt_prep(Db_qry_insert.new_(tbl, cols));}
	public Db_stmt			Stmt_update(String tbl, String[] where, String... cols)			{return engine.New_stmt_prep(Db_qry_update.new_(tbl, where, cols));}
	public Db_stmt			Stmt_update_exclude(String tbl, Db_meta_fld_list flds, String... where) {return engine.New_stmt_prep(Db_qry_update.new_(tbl, where, flds.To_str_ary_exclude(where)));}
	public Db_stmt			Stmt_delete(String tbl, String... where)							{return engine.New_stmt_prep(Db_qry_delete.new_(tbl, where));}
	public Db_stmt			Stmt_select(String tbl, String[] cols, String... where)			{return engine.New_stmt_prep(Db_qry__select_in_tbl.new_(tbl, where, cols, null));}
	public Db_stmt			Stmt_select(String tbl, Db_meta_fld_list flds, String... where)	{return engine.New_stmt_prep(Db_qry__select_in_tbl.new_(tbl, where, flds.To_str_ary(), null));}
	public Db_stmt			Stmt_select_order(String tbl, Db_meta_fld_list flds, String[] where, String... orderbys) {return engine.New_stmt_prep(Db_qry__select_in_tbl.new_(tbl, where, flds.To_str_ary(), orderbys));}
	public Db_stmt			Stmt_select_order(String tbl, String[] flds, String[] where, String... orderbys) {return engine.New_stmt_prep(Db_qry__select_in_tbl.new_(tbl, where, flds, orderbys));}
	public Db_stmt			Stmt_new(Db_qry qry) {return engine.New_stmt_prep(qry);}
	public void				Env_db_attach(String alias, Io_url db_url)							{engine.Env_db_attach(alias, db_url);}
	public void				Env_db_detach(String alias)											{engine.Env_db_detach(alias);}
	public void				Env_vacuum()														{Exec_sql_plog_ntx("vacuuming: url=" + this.Conn_info().Xto_api(), "VACUUM;");}
	public void				Ddl_create_tbl(Db_meta_tbl meta)									{engine.Ddl_create_tbl(meta); engine.Ddl_create_idx(Gfo_usr_dlg_.Null, meta.Idxs());}
	public void				Ddl_create_idx(Db_meta_idx... idxs)							{engine.Ddl_create_idx(Gfo_usr_dlg_.I, idxs);}
	public void				Ddl_create_idx(Gfo_usr_dlg usr_dlg, Db_meta_idx... idxs)		{engine.Ddl_create_idx(usr_dlg, idxs);}
	public void				Ddl_append_fld(String tbl, Db_meta_fld fld)							{engine.Ddl_append_fld(tbl, fld);}
	public void				Ddl_delete_tbl(String tbl)											{engine.Ddl_delete_tbl(tbl);}
	public void				Rls_reg(RlsAble rls) {rls_list.Add(rls);}
	public void				Rls_conn() {
		int len = rls_list.Count();
		for (int i = 0; i < len; ++i) {
			RlsAble itm = (RlsAble)rls_list.FetchAt(i);
			itm.Rls();
		}
		engine.Conn_term();
		Db_conn_pool.I.Del(engine.Conn_info());
	}
	public int				Exec_sql(String sql)			{return this.Exec_qry(Db_qry_sql.dml_(sql));}
	public Db_rdr			Exec_sql_as_rdr2(String sql)	{return this.Stmt_new(Db_qry_sql.dml_(sql)).Exec_select__rls_auto();}
	public int				Exec_sql_plog_ntx(String msg, String sql) {return Exec_sql_plog(Bool_.N, msg, sql);}
	public int				Exec_sql_plog_txn(String msg, String sql) {return Exec_sql_plog(Bool_.Y, msg, sql);}
	public int				Exec_sql_plog(boolean txn, String msg, String sql) {
		Gfo_usr_dlg_.I.Plog_many("", "", "bgn: " + msg);
		if (txn) this.Txn_bgn();
		int rv = Exec_sql(sql);
		if (txn) this.Txn_end();
		Gfo_usr_dlg_.I.Plog_many("", "", "end: " + msg);
		return rv;
	}
	public int				Exec_qry(Db_qry qry)		{return Int_.cast_(engine.Exec_as_obj(qry));}
	public DataRdr			Exec_qry_as_rdr(Db_qry qry)	{return DataRdr_.cast_(engine.Exec_as_obj(qry));}
	public int				Exec_sql_args(String sql, Object... args)	{return this.Exec_qry(Db_qry_sql.dml_(String_.Format(sql, args)));}
	public DataRdr			Exec_sql_as_rdr(String sql)						{return this.Exec_qry_as_rdr(Db_qry_sql.rdr_(sql));}
}
