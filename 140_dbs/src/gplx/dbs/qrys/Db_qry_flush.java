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
package gplx.dbs.qrys; import gplx.dbs.*;
import gplx.dbs.sqls.*;
import gplx.types.errs.ErrUtl;
public class Db_qry_flush implements Db_qry {
	public int			Tid() {return Db_qry_.Tid_flush;}
	public boolean ReturnsRdr() {return false;}
	public String BaseTable() {return tableNames[0];}
	public String ToSqlExec(SqlQryWtr wtr) {return wtr.ToSqlStr(this, false);}
	public int Exec_qry(Db_conn conn) {return conn.Exec_qry(this);}

	public String[] TableNames() {return tableNames;} private String[] tableNames;
	

	public static Db_qry_flush as_(Object obj) {return obj instanceof Db_qry_flush ? (Db_qry_flush)obj : null;}
	public static Db_qry_flush cast(Object obj) {try {return (Db_qry_flush)obj;} catch(Exception exc) {throw ErrUtl.NewCast(exc, Db_qry_flush.class, obj);}}
	public static Db_qry_flush new_(String... ary) {
		Db_qry_flush rv = new Db_qry_flush();
		rv.tableNames = ary;
		return rv;
	}	Db_qry_flush() {}
}
