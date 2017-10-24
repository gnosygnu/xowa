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
package gplx.dbs.sqls; import gplx.*; import gplx.dbs.*;
import gplx.dbs.sqls.wtrs.*;
public class Sql_qry_wtr_ {
	public static Sql_qry_wtr New__basic()		{return new Sql_core_wtr();}
	public static Sql_qry_wtr New__mysql()		{return new Sql_core_wtr__mysql();}
	public static Sql_qry_wtr New__sqlite()		{return new Sql_core_wtr__sqlite();}

	public static final byte Like_wildcard = Byte_ascii.Percent;
	public static String Quote_arg(String s) {	// only for constructing DEBUG SQL strings
		return "'" + String_.Replace(s, "'", "''") + "'";
	}
}
