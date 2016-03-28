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
import gplx.core.brys.fmtrs.*;
import gplx.core.stores.*; import gplx.dbs.metas.*; import gplx.dbs.engines.*; import gplx.dbs.qrys.*; import gplx.dbs.sys.*;
public class Db_conn {
	private final    Db_engine engine; private final    List_adp rls_list = List_adp_.new_();
	private final    Bry_fmt exec_sql_fmt = Bry_fmt.New(""); private final    Bry_bfr exec_sql_bfr = Bry_bfr.new_();
	public Db_conn(Db_engine engine) {
		this.engine = engine;
		sys_mgr = new Db_sys_mgr(this);
	}
	public Db_engine		Engine()				{return engine;}
	public Db_conn_info		Conn_info()				{return engine.Conn_info();}
	public Db_sys_mgr		Sys_mgr()				{return sys_mgr;} private final    Db_sys_mgr sys_mgr;
	public boolean				Eq(Db_conn comp)		{return String_.Eq(engine.Conn_info().Db_api(), comp.Conn_info().Db_api());}
	public void				Txn_bgn(String name)	{engine.Txn_bgn(name);}
	public void				Txn_end()				{engine.Txn_end();}
	public void				Txn_cxl()				{engine.Txn_cxl();}
	public void				Txn_sav()				{engine.Txn_sav();}
	public void				Env_db_attach(String alias, Db_conn conn)										{engine.Env_db_attach(alias, conn);}
	public void				Env_db_attach(String alias, Io_url db_url)										{engine.Env_db_attach(alias, db_url);}
	public void				Env_db_detach(String alias)														{engine.Env_db_detach(alias);}
	public void				Env_vacuum()																	{Exec_sql_plog_ntx("vacuuming: url=" + this.Conn_info().Db_api(), "VACUUM;");}
	public boolean				Meta_tbl_exists(String tbl)														{return engine.Meta_tbl_exists(tbl);}
	public void				Meta_tbl_create(Dbmeta_tbl_itm meta)											{engine.Ddl_create_tbl(meta); engine.Ddl_create_idx(Gfo_usr_dlg_.Noop, meta.Idxs().To_ary());}
	public void				Meta_tbl_drop(String tbl)														{engine.Ddl_delete_tbl(tbl);}
	public void				Meta_idx_create(Dbmeta_idx_itm... idxs)									{engine.Ddl_create_idx(Gfo_usr_dlg_.Instance, idxs);}
	public void				Meta_idx_create(Gfo_usr_dlg usr_dlg, Dbmeta_idx_itm... idxs)				{engine.Ddl_create_idx(usr_dlg, idxs);}
	public boolean				Meta_fld_exists(String tbl, String fld)											{return engine.Meta_fld_exists(tbl, fld);}
	public void				Meta_fld_append(String tbl, Dbmeta_fld_itm fld)									{engine.Ddl_append_fld(tbl, fld);}
	public void				Meta_fld_assert(String tbl, String fld, Dbmeta_fld_tid tid, Object dflt)		{if (!Meta_fld_exists(tbl, fld)) this.Meta_fld_append(tbl, new Dbmeta_fld_itm(fld, tid).Default_(dflt));}
	public Dbmeta_tbl_mgr	Meta_load_all()																	{return engine.Meta_tbl_load_all();}
	public Db_stmt			Stmt_insert(String tbl, Dbmeta_fld_list flds)									{return engine.New_stmt_prep(Db_qry_insert.new_(tbl, flds.To_str_ary_wo_autonum()));}
	public Db_stmt			Stmt_insert(String tbl, String... cols)									{return engine.New_stmt_prep(Db_qry_insert.new_(tbl, cols));}
	public Db_stmt			Stmt_update(String tbl, String[] where, String... cols)					{return engine.New_stmt_prep(Db_qry_update.New(tbl, where, cols));}
	public Db_stmt			Stmt_update_exclude(String tbl, Dbmeta_fld_list flds, String... where)	{return engine.New_stmt_prep(Db_qry_update.New(tbl, where, flds.To_str_ary_exclude(where)));}
	public Db_stmt			Stmt_delete(String tbl, String... where)									{return engine.New_stmt_prep(Db_qry_delete.new_(tbl, where));}
	public Db_stmt			Stmt_select(String tbl, String[] cols, String... where)					{return engine.New_stmt_prep(Db_qry__select_in_tbl.new_(tbl, where, cols, null));}
	public Db_stmt			Stmt_select(String tbl, Dbmeta_fld_list flds, String... where)			{return engine.New_stmt_prep(Db_qry__select_in_tbl.new_(tbl, where, flds.To_str_ary(), null));}
	public Db_stmt			Stmt_select_order(String tbl, Dbmeta_fld_list flds, String[] where, String... orderbys) {return engine.New_stmt_prep(Db_qry__select_in_tbl.new_(tbl, where, flds.To_str_ary(), orderbys));}
	public Db_stmt			Stmt_select_order(String tbl, String[] flds, String[] where, String... orderbys) {return engine.New_stmt_prep(Db_qry__select_in_tbl.new_(tbl, where, flds, orderbys));}
	public Db_stmt			Stmt_new(Db_qry qry) {return engine.New_stmt_prep(qry);}
	public Db_stmt			Stmt_sql(String sql) {return engine.New_stmt_prep(Db_qry_sql.sql_(sql));}
	public int				Exec_qry(Db_qry qry)						{return Int_.cast(engine.Exec_as_obj(qry));}
	public int				Exec_sql(String sql)						{return this.Exec_qry(Db_qry_sql.dml_(sql));}
	public int				Exec_sql(String msg, String sql)			{Gfo_usr_dlg_.Instance.Plog_many("", "", msg); return this.Exec_sql(sql);}
	public Db_rdr			Exec_rdr(String sql)						{return this.Stmt_sql(sql).Exec_select__rls_auto();}
	public void				Exec_delete_all(String tbl)					{Stmt_delete(tbl).Exec_delete();}
	public int				Exec_sql_args(String sql, Object... args)	{return this.Exec_qry(Db_qry_sql.dml_(String_.Format(sql, args)));}
	public int				Exec_sql_fmt_by_keys(String sql, String[] keys, Object... args)	{return Exec_sql(exec_sql_fmt.Fmt_(sql).Keys_(Bry_.Ary(keys)).Bld_many_to_str(exec_sql_bfr, args));}
	public int				Exec_sql_plog_ntx(String msg, String sql) {return Exec_sql_plog(Bool_.N, msg, sql);}
	public int				Exec_sql_plog_txn(String msg, String sql) {return Exec_sql_plog(Bool_.Y, msg, sql);}
	public int				Exec_sql_plog(boolean txn, String msg, String sql) {
		Gfo_usr_dlg_.Instance.Plog_many("", "", msg);
		if (txn) this.Txn_bgn(msg);
		int rv = Exec_sql(sql);
		if (txn) this.Txn_end();
		Gfo_usr_dlg_.Instance.Plog_many("", "", "done:" + msg);
		return rv;
	}
	public int				Exec_select_as_int		(String sql, int    or) {Object rv = Exec_select_as_obj(sql); return rv == null ? or : Int_.cast(rv);}
	public double			Exec_select_as_double	(String sql, double or) {Object rv = Exec_select_as_obj(sql); return rv == null ? or : Double_.cast(rv);}
	private Object Exec_select_as_obj(String sql) {
		Db_rdr rdr = Exec_rdr(sql);
		try {return rdr.Move_next() ? rdr.Read_at(0) : null;}
		finally {rdr.Rls();}
	}
	public void				Rls_reg(Rls_able rls) {rls_list.Add(rls);}
	public void				Rls_conn() {
		int len = rls_list.Count();
		for (int i = 0; i < len; ++i) {
			Rls_able itm = (Rls_able)rls_list.Get_at(i);
			itm.Rls();
		}
		engine.Conn_term();
		Db_conn_pool.Instance.Del(engine.Conn_info());
	}

	public Db_stmt			Stmt_select_max(String tbl, String col, String... where) {
		Db_qry__select_in_tbl qry = new Db_qry__select_in_tbl(tbl, String_.Ary(String_.Format("Max({0}) AS {0}", col)), where, null, null, null, null);
		return engine.New_stmt_prep(qry);
	}

	public DataRdr			Exec_sql_as_old_rdr(String sql)		{return DataRdr_.cast(engine.Exec_as_obj(Db_qry_sql.rdr_(sql)));}
	public DataRdr			Exec_qry_as_old_rdr(Db_qry qry)		{return DataRdr_.cast(engine.Exec_as_obj(qry));}
}
