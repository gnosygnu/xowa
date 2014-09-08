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
public class Db_provider_fxt implements RlsAble {
	public Db_provider Provider() {return provider;} public Db_provider_fxt Provider_(Db_provider v) {provider = v; return this;} Db_provider provider;
	public void DmlAffectedAvailable_(boolean v) {dmlAffectedAvailable = v;} private boolean dmlAffectedAvailable = true;
	public void ini_DeleteAll(String tbl) {provider.Exec_qry(Db_qry_.delete_tbl_(tbl));}
	public void tst_ExecDml(int expd, Db_qry qry) {
		int actl = provider.Exec_qry(qry);
		if (dmlAffectedAvailable)
			Tfds.Eq(expd, actl, "Exec_qry failed: sql={0}", qry.XtoSql());
	}
	public void tst_ExecRdrTbl(int expd, String tblName) {tst_ExecRdr(expd, Db_qry_.select_tbl_(tblName));}
	public void tst_ExecRdr(int expd, Db_qry qry) {
		DataRdr rdr = DataRdr_.Null;
		try {
			rdr = provider.Exec_qry_as_rdr(qry);
			tbl = GfoNde_.rdr_(rdr);
		}
		catch (Exception e) {Err_.Noop(e); rdr.Rls();}
		Tfds.Eq(expd, tbl.Subs().Count(), "Exec_qry_as_rdr failed: sql={0}", qry.XtoSql());
	}	GfoNde tbl;
	public GfoNde tst_RowAry(int index, Object... expdValAry) {
		GfoNde record = tbl.Subs().FetchAt_asGfoNde(index);
		Object[] actlValAry = new Object[expdValAry.length];
		for (int i = 0; i < actlValAry.length; i++)
			actlValAry[i] = record.ReadAt(i);
		Tfds.Eq_ary(actlValAry, expdValAry);
		return record;
	}
	public void Rls() {provider.Conn_term();}

	public static Db_provider Mysql()				{return Db_provider_.new_and_open_(Db_conn_info__mysql.new_("127.0.0.1", "unit_tests", "gplx_user", "gplx_password"));}
	public static Db_provider Tdb(String fileName)	{return Db_provider_.new_and_open_(Db_conn_info_.tdb_(Tfds.RscDir.GenSubDir_nest("140_dbs", "tdbs").GenSubFil(fileName)));}
	public static Db_provider Postgres()			{return Db_provider_.new_and_open_(Db_conn_info__postgres.new_("127.0.0.1", "unit_tests", "gplx_user", "gplx_password"));}
	public static Db_provider Sqlite()				{return Db_provider_.new_and_open_(Db_conn_info__sqlite.load_(Tfds.RscDir.GenSubFil_nest("140_dbs", "sqlite", "unit_tests.db")));}
//		public static Db_provider Mssql() {return MssqlConnectUrl.WindowsAuth(".", "unit_tests");
	public static final boolean SkipPostgres = Tfds.SkipDb || true;
}