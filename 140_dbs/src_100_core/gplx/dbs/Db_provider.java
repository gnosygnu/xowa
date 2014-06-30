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
public class Db_provider implements RlsAble {
	@gplx.Internal protected Db_provider(Db_engine engine) {
		this.engine = engine;
		txn_mgr = new Db_txn_mgr_base(engine);
	}
	@gplx.Internal protected Db_engine Engine()					{return engine;} Db_engine engine;
	public Db_connect ConnectInfo()				{return engine.ConnectInfo();}
	public Db_stmt Prepare(Db_qry qry)			{return engine.New_db_stmt(this, qry);}
	public int Exec_qry(Db_qry qry)				{txn_mgr.Txn_count_(txn_mgr.Txn_count() + 1); return Int_.cast_(engine.Execute(qry));}
	public DataRdr Exec_qry_as_rdr(Db_qry qry)	{return DataRdr_.cast_(engine.Execute(qry));}
	public int Exec_sql(String sql)				{return this.Exec_qry(Db_qry_sql.dml_(sql));}
	public DataRdr Exec_sql_as_rdr(String sql)	{return this.Exec_qry_as_rdr(Db_qry_sql.rdr_(sql));}
	public Db_txn_mgr Txn_mgr()					{return txn_mgr;} Db_txn_mgr txn_mgr;
	public void Rls() {
		engine.Rls();
		Db_provider_pool._.Del(this.ConnectInfo());	// remove from pool, else rls'd instance will be cached and fail upon next use
	}
}
