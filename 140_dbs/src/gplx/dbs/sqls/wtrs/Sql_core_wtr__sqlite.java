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
package gplx.dbs.sqls.wtrs; import gplx.*; import gplx.dbs.*; import gplx.dbs.sqls.*;
public class Sql_core_wtr__sqlite extends Sql_core_wtr {	@Override protected Sql_val_wtr			Make__val_wtr()							{return new Sql_val_wtr_sqlite();}
	@Override protected Sql_select_wtr		Make__select_wtr(Sql_core_wtr qry_wtr)	{return new Sql_select_wtr_sqlite(qry_wtr);}
}
