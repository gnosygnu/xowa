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
package gplx.dbs.sqls.itms; import gplx.*; import gplx.dbs.*; import gplx.dbs.sqls.*;
public class Sql_tbl_itm {
	public Sql_tbl_itm(int join_tid, String db, String name, String alias, Sql_join_fld[] join_flds) {
		this.Join_tid = join_tid;
		this.Db = db;
		this.Name = name;
		this.Alias = alias;
		this.Join_flds = join_flds;
	}
	public final int Join_tid;
	public final String Db;
	public final String Name;
	public final String Alias;
	public boolean Db_enabled = true;
	public final Sql_join_fld[] Join_flds;

	public static final String Alias__null = String_.Null;
	public static final String Db__null = String_.Null;
	public static final int
	  Tid__from		= 0 // "FROM"
	, Tid__inner	= 1 // "INNER JOIN"
	, Tid__left		= 2 // "LEFT JOIN"
	, Tid__right	= 3 // "RIGHT JOIN"
	, Tid__outer	= 4	// "OUTER JOIN"
	, Tid__cross	= 5 // "CROSS JOIN"
	;
}
