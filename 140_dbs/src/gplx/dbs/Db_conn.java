/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2017 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.dbs; import gplx.*;
import gplx.core.stores.*; import gplx.dbs.metas.*; import gplx.dbs.engines.*; import gplx.dbs.qrys.*; import gplx.dbs.sys.*; import gplx.dbs.conn_props.*; import gplx.dbs.qrys.bats.*;
public class Db_conn {
	private final    List_adp rls_list = List_adp_.New();
	public Db_conn(Db_engine engine) {
		this.engine = engine;
		this.sys_mgr = new Db_sys_mgr(this);
	}
	public Db_conn_info			Conn_info()				{return engine.Conn_info();}
	public Db_conn_props_mgr	Props()					{return engine.Props();}
	public Db_batch_mgr			Batch_mgr()				{return engine.Batch_mgr();}
	public Db_engine			Engine()				{return engine;} private final    Db_engine engine;
	public Db_sys_mgr			Sys_mgr()				{return sys_mgr;} private final    Db_sys_mgr sys_mgr;	// autonum and other functions
	public boolean				Eq(Db_conn comp)		{return String_.Eq(engine.Conn_info().Db_api(), comp.Conn_info().Db_api());}
	public void					Txn_bgn(String name)	{engine.Txn_bgn(name);}
	public void					Txn_end()				{engine.Txn_end();}
	public void					Txn_cxl()				{engine.Txn_cxl();}
	public void					Txn_sav()				{engine.Txn_sav();}
	public void					Env_db_attach(String alias, Db_conn conn)										{engine.Env_db_attach(alias, conn);}
	public void					Env_db_attach(String alias, Io_url db_url)										{engine.Env_db_attach(alias, db_url);}
	public void					Env_db_detach(String alias)														{engine.Env_db_detach(alias);}
	public void					Env_vacuum()																	{Exec_sql_plog_ntx("vacuuming: url=" + this.Conn_info().Db_api(), "VACUUM;");}
	public void					Meta_tbl_create(Dbmeta_tbl_itm meta)											{engine.Meta_tbl_create(meta); engine.Meta_idx_create(Gfo_usr_dlg_.Noop, meta.Idxs().To_ary());}
	public void					Meta_tbl_delete(String tbl)														{engine.Meta_tbl_delete(tbl);}
	public void					Meta_tbl_remake_many(Db_tbl... tbls)										{for (Db_tbl tbl : tbls) Meta_tbl_remake(tbl);}
	public void					Meta_tbl_remake(Db_tbl tbl)														{engine.Meta_tbl_delete(tbl.Tbl_name()); tbl.Create_tbl();}
	public void					Meta_tbl_remake(Dbmeta_tbl_itm meta)											{engine.Meta_tbl_delete(meta.Name()); engine.Meta_tbl_create(meta);}
	public void					Meta_idx_assert(String tbl, String suffix, String... flds)				{if (engine.Meta_idx_exists(tbl + "__" + suffix)) return; this.Meta_idx_create(tbl, suffix, flds);}
	public void					Meta_idx_assert(String tbl, String suffix, Dbmeta_idx_fld... flds)		{if (engine.Meta_idx_exists(tbl + "__" + suffix)) return; this.Meta_idx_create(tbl, suffix, flds);}
	public void					Meta_idx_create(String tbl, String suffix, String... flds)				{engine.Meta_idx_create(Gfo_usr_dlg_.Instance, Dbmeta_idx_itm.new_normal_by_tbl(tbl, suffix, flds));}
	public void					Meta_idx_create(String tbl, String suffix, Dbmeta_idx_fld... flds)		{engine.Meta_idx_create(Gfo_usr_dlg_.Instance, Dbmeta_idx_itm.new_normal_by_tbl(tbl, suffix, flds));}
	public void					Meta_idx_create(Dbmeta_idx_itm... idxs)									{engine.Meta_idx_create(Gfo_usr_dlg_.Instance, idxs);}
	public void					Meta_idx_create(Gfo_usr_dlg usr_dlg, Dbmeta_idx_itm... idxs)				{engine.Meta_idx_create(usr_dlg, idxs);}
	public void					Meta_idx_delete(String idx)														{engine.Meta_idx_delete(idx);}
	public void					Meta_idx_delete(String tbl, String suffix)										{engine.Meta_idx_delete(tbl + "__" + suffix);}
	public void					Meta_fld_append(String tbl, Dbmeta_fld_itm fld)									{engine.Meta_fld_append(tbl, fld);}
	public void					Meta_fld_assert(String tbl, String fld, Dbmeta_fld_tid tid, Object dflt)		{if (!Meta_fld_exists(tbl, fld)) this.Meta_fld_append(tbl, new Dbmeta_fld_itm(fld, tid).Default_(dflt));}
	public boolean				Meta_tbl_exists(String tbl)														{return engine.Meta_tbl_exists(tbl);}
	public boolean				Meta_fld_exists(String tbl, String fld)											{return engine.Meta_fld_exists(tbl, fld);}
	public boolean				Meta_idx_exists(String idx)														{return engine.Meta_idx_exists(idx);}
	public boolean				Meta_idx_exists(String tbl, String suffix)										{return engine.Meta_idx_exists(tbl + "__" + suffix);}
	public void					Meta_tbl_assert(Db_tbl... tbls) {
		boolean dirty = false;
		for (Db_tbl tbl : tbls) {
			if (!Meta_tbl_exists(tbl.Tbl_name())) {
				tbl.Create_tbl();
				dirty = true;
			}
		}
		if (dirty) this.Meta_mgr().Load_all();
	}
	public String Meta_fld_append_if_missing(String tbl_name, Dbmeta_fld_list flds, Dbmeta_fld_itm fld) {
		String fld_name = fld.Name();
		// if fld doesn't exist, add it; NOTE: need to check if tbl exists first else meta_fld not available
		if (	this.Meta_tbl_exists(tbl_name)
			&& !this.Meta_fld_exists(tbl_name, fld_name)) {
			try {this.Meta_fld_append(tbl_name, fld);}
			catch (Exception e) {
				Gfo_log_.Instance.Warn("failed to append fld", "conn", this.Conn_info().Db_api(), "tbl", tbl_name, "fld", fld_name, "err", Err_.Message_gplx_log(e));
				fld_name = Dbmeta_fld_itm.Key_null;
			}
		}
		// if fld does exist, or tbl doesn't exist, just add fld to collection
		else {
			if (!flds.Has(fld.Name()))	// NOTE: need to check if it already exists; DATE:2016-09-22
				fld_name = flds.Add(fld);
		}
		return fld_name;
	}
	public Dbmeta_tbl_mgr		Meta_mgr()																		{return engine.Meta_mgr();}
	public Db_stmt				Stmt_insert(String tbl, Dbmeta_fld_list flds)									{return engine.Stmt_by_qry(Db_qry_insert.new_(tbl, flds.To_str_ary_wo_autonum()));}
	public Db_stmt				Stmt_insert(String tbl, String... cols)									{return engine.Stmt_by_qry(Db_qry_insert.new_(tbl, cols));}
	public Db_stmt				Stmt_update(String tbl, String[] where, String... cols)					{return engine.Stmt_by_qry(Db_qry_update.New(tbl, where, cols));}
	public Db_stmt				Stmt_update_exclude(String tbl, Dbmeta_fld_list flds, String... where)	{return engine.Stmt_by_qry(Db_qry_update.New(tbl, where, flds.To_str_ary_exclude(where)));}
	public Db_stmt				Stmt_delete(String tbl, String... where)									{return engine.Stmt_by_qry(Db_qry_delete.new_(tbl, where));}
	public Db_stmt				Stmt_select_all(String tbl, Dbmeta_fld_list flds)								{return engine.Stmt_by_qry(Db_qry__select_in_tbl.new_(tbl, String_.Ary_empty, flds.To_str_ary(), null));}
	public Db_stmt				Stmt_select(String tbl, String[] cols, String... where)					{return engine.Stmt_by_qry(Db_qry__select_in_tbl.new_(tbl, where, cols, null));}
	public Db_stmt				Stmt_select(String tbl, Dbmeta_fld_list flds, String... where)			{return engine.Stmt_by_qry(Db_qry__select_in_tbl.new_(tbl, where, flds.To_str_ary(), null));}
	public Db_stmt				Stmt_select_order(String tbl, Dbmeta_fld_list flds, String[] where, String... orderbys) {return engine.Stmt_by_qry(Db_qry__select_in_tbl.new_(tbl, where, flds.To_str_ary(), orderbys));}
	public Db_stmt				Stmt_select_order(String tbl, String[] flds, String[] where, String... orderbys) {return engine.Stmt_by_qry(Db_qry__select_in_tbl.new_(tbl, where, flds, orderbys));}
	public Db_stmt				Stmt_new(Db_qry qry) {return engine.Stmt_by_qry(qry);}
	public Db_stmt				Stmt_sql(String sql) {return engine.Stmt_by_qry(Db_qry_sql.sql_(sql));}
	public int					Exec_qry(Db_qry qry)										{return Int_.cast(engine.Exec_as_obj(qry));}
	public int					Exec_sql_concat(String... ary)						{return this.Exec_qry(Db_qry_sql.dml_(String_.Concat_lines_nl_skip_last(ary)));}
	public int					Exec_sql_concat_w_msg(String msg, String... ary)		{Gfo_usr_dlg_.Instance.Plog_many("", "", msg); return Exec_sql_concat(ary);}
	public int					Exec_sql(String sql)										{return this.Exec_qry(Db_qry_sql.dml_(sql));}
	public int					Exec_sql(String msg, String sql)							{Gfo_usr_dlg_.Instance.Plog_many("", "", msg); return this.Exec_sql(sql);}
	public Db_rdr				Exec_rdr(String sql)										{return this.Stmt_sql(sql).Exec_select__rls_auto();}
	public void					Exec_delete_all(String tbl)									{Stmt_delete(tbl).Exec_delete();}
	public int					Exec_sql_args(String sql, Object... args)	{return this.Exec_qry(Db_qry_sql.dml_(String_.Format(sql, args)));}
	public int					Exec_sql_plog_ntx(String msg, String sql) {return Exec_sql_plog(Bool_.N, msg, sql);}
	public int					Exec_sql_plog_txn(String msg, String sql) {return Exec_sql_plog(Bool_.Y, msg, sql);}
	public int					Exec_sql_plog(boolean txn, String msg, String sql) {			
		Gfo_usr_dlg_.Instance.Plog_many("", "", msg);
		if (txn) this.Txn_bgn(msg);
		int rv = Exec_sql(sql);
		if (txn) this.Txn_end();
		Gfo_usr_dlg_.Instance.Plog_many("", "", "done:" + msg);
		return rv;
	}
	public int					Exec_select_max_as_int	(String tbl_name, String fld_name, int or) {
		Object rv = Exec_select_as_obj(String_.Format("SELECT Max({0}) FROM {1}", fld_name, tbl_name));
		return rv == null ? or : Int_.cast(rv);
	}
	public int					Exec_select_count_as_int(String tbl_name, int or) {
		Object rv = Exec_select_as_obj(String_.Format("SELECT Count(*) FROM {0}", tbl_name));
		return rv == null ? or : Int_.cast(rv);
	}
	public int					Exec_select_as_int		(String sql, int    or) {Object rv = Exec_select_as_obj(sql); return rv == null ? or : Int_.cast(rv);}
	public double				Exec_select_as_double	(String sql, double or) {Object rv = Exec_select_as_obj(sql); return rv == null ? or : Double_.cast(rv);}
	private Object				Exec_select_as_obj(String sql) {
		Db_rdr rdr = Exec_rdr(sql);
		try {return rdr.Move_next() ? rdr.Read_at(0) : null;}
		finally {rdr.Rls();}
	}
	public void					Rls_reg(Rls_able rls) {
		if (this == Db_conn_.Noop) return;	// ignore Db_conn_.Noop, else memory leak
		rls_list.Add(rls);
	}
	public void					Rls_conn() {
		int len = rls_list.Count();
		for (int i = 0; i < len; ++i) {
			Rls_able itm = (Rls_able)rls_list.Get_at(i);
			itm.Rls();
		}
		// rls_list.Clear(); // TOMBSTONE: do not clear rls_list, else tables which self-registered won't get called again; DATE:2016-07-06
		engine.Conn_term();
		Db_conn_pool.Instance.Del(engine.Conn_info());
	}
	public void Reopen_conn() {
		engine.Conn_open();
		Db_conn_pool.Instance.Add_existing(this);
	}
	public Db_stmt				Stmt_select_max(String tbl, String col, String... where) {
		Db_qry__select_in_tbl qry = new Db_qry__select_in_tbl(tbl, String_.Ary(String_.Format("Max({0}) AS {0}", col)), where, null, null, null, null);
		qry.Where_(where.length == 0 ? Db_crt_.Wildcard : Db_crt_.eq_many_(where));
		return engine.Stmt_by_qry(qry);
	}

	public DataRdr				Exec_sql_as_old_rdr(String sql)		{return DataRdr_.cast(engine.Exec_as_obj(Db_qry_sql.rdr_(sql)));}
	public DataRdr				Exec_qry_as_old_rdr(Db_qry qry)		{return DataRdr_.cast(engine.Exec_as_obj(qry));}
}
