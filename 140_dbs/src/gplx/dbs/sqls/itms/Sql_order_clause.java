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
public class Sql_order_clause {
	private final    List_adp list = List_adp_.New();
	private Sql_order_fld[] ary;
	public void Flds__add(Sql_order_fld fld) {list.Add(fld);}
	public Sql_order_fld[] Flds() {
		if (ary == null) ary = (Sql_order_fld[])list.To_ary_and_clear(Sql_order_fld.class);
		return ary;
	}
}
