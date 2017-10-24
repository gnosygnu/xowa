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
package gplx.dbs.qrys; import gplx.*; import gplx.dbs.*;
import gplx.core.criterias.*; import gplx.dbs.sqls.*;
public class Db_qry_delete implements Db_qry {
	Db_qry_delete(String base_table, Criteria where) {this.base_table = base_table; this.where = where;}
	public int			Tid()							{return Db_qry_.Tid_delete;}
	public boolean			Exec_is_rdr()					{return Bool_.N;}
	public String		Base_table()					{return base_table;} private final String base_table;
	public String		To_sql__exec(Sql_qry_wtr wtr)	{return wtr.To_sql_str(this, false);}
	public Criteria		Where()							{return where;} private final Criteria where;
	public int			Exec_qry(Db_conn conn)			{return conn.Exec_qry(this);}
	public static Db_qry_delete new_all_(String tbl)						{return new Db_qry_delete(tbl, Criteria_.All);}
	public static Db_qry_delete new_(String tbl, String... where)		{return new Db_qry_delete(tbl, Db_crt_.eq_many_(where));}
	public static Db_qry_delete new_(String tbl, Criteria where)			{return new Db_qry_delete(tbl, where);}
	public static final Criteria Where__null = null;
}
