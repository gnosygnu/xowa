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
import gplx.core.stores.*;
import gplx.dbs.engines.sqlite.*; import gplx.dbs.engines.mysql.*; import gplx.dbs.engines.postgres.*; import gplx.core.gfo_ndes.*;
public class Db_conn_fxt implements Rls_able {
	public Db_conn Conn() {return conn;} public Db_conn_fxt Conn_(Db_conn v) {conn = v; return this;} Db_conn conn;
	public void DmlAffectedAvailable_(boolean v) {dmlAffectedAvailable = v;} private boolean dmlAffectedAvailable = true;
	public void ini_DeleteAll(String tbl) {conn.Exec_qry(Db_qry_.delete_tbl_(tbl));}
	public void tst_ExecDml(int expd, Db_qry qry) {
		int actl = conn.Exec_qry(qry);
		if (dmlAffectedAvailable)
			Tfds.Eq(expd, actl, "Exec_qry failed: sql={0}", qry.Xto_sql());
	}
	public void tst_ExecRdrTbl(int expd, String tblName) {tst_ExecRdr(expd, Db_qry_.select_tbl_(tblName));}
	public void tst_ExecRdr(int expd, Db_qry qry) {
		DataRdr rdr = DataRdr_.Null;
		try {
			rdr = conn.Exec_qry_as_rdr(qry);
			tbl = GfoNde_.rdr_(rdr);
		}
		catch (Exception e) {Err_.Noop(e); rdr.Rls();}
		Tfds.Eq(expd, tbl.Subs().Count(), "Exec_qry_as_rdr failed: sql={0}", qry.Xto_sql());
	}	GfoNde tbl;
	public GfoNde tst_RowAry(int index, Object... expdValAry) {
		GfoNde record = tbl.Subs().FetchAt_asGfoNde(index);
		Object[] actlValAry = new Object[expdValAry.length];
		for (int i = 0; i < actlValAry.length; i++)
			actlValAry[i] = record.ReadAt(i);
		Tfds.Eq_ary(actlValAry, expdValAry);
		return record;
	}
	public void Rls() {conn.Rls_conn();}

	public static Db_conn Mysql()				{return Db_conn_pool.Instance.Get_or_new(Mysql_conn_info.new_("127.0.0.1", "unit_tests", "gplx_user", "gplx_password"));}
	public static Db_conn Tdb(String fileName)	{return Db_conn_pool.Instance.Get_or_new(Db_conn_info_.tdb_(Tfds.RscDir.GenSubDir_nest("140_dbs", "tdbs").GenSubFil(fileName)));}
	public static Db_conn Postgres()			{return Db_conn_pool.Instance.Get_or_new(Postgres_conn_info.new_("127.0.0.1", "unit_tests", "gplx_user", "gplx_password"));}
	public static Db_conn Sqlite()				{return Db_conn_pool.Instance.Get_or_new(Sqlite_conn_info.load_(Tfds.RscDir.GenSubFil_nest("140_dbs", "sqlite", "unit_tests.db")));}
	public static final boolean SkipPostgres = Tfds.SkipDb || true;
}