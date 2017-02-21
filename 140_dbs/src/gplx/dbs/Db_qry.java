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
public interface Db_qry {
	int			Tid();
	boolean		Exec_is_rdr();
	String		Base_table();
	String		To_sql__exec(gplx.dbs.sqls.Sql_qry_wtr wtr);
}
class Db_qry__noop implements Db_qry {
	public int	Tid() {return Db_qry_.Tid_noop;}
	public boolean	Exec_is_rdr() {return false;}
	public String Base_table() {return "";}
	public String To_sql__exec(gplx.dbs.sqls.Sql_qry_wtr wtr) {return "";}
}
