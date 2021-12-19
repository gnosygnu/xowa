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
package gplx.dbs.sqls;
import gplx.types.basics.constants.AsciiByte;
import gplx.types.basics.utls.StringUtl;
import gplx.dbs.sqls.wtrs.Sql_core_wtr;
import gplx.dbs.sqls.wtrs.Sql_core_wtr__mysql;
import gplx.dbs.sqls.wtrs.Sql_core_wtr__sqlite;
public class SqlQryWtrUtl {
	public static SqlQryWtr NewBasic()  {return new Sql_core_wtr();}
	public static SqlQryWtr NewMysql()  {return new Sql_core_wtr__mysql();}
	public static SqlQryWtr NewSqlite() {return new Sql_core_wtr__sqlite();}

	public static final byte Like_wildcard = AsciiByte.Percent;
	public static String QuoteArg(String s) {    // only for constructing DEBUG SQL strings
		return "'" + StringUtl.Replace(s, "'", "''") + "'";
	}
}
