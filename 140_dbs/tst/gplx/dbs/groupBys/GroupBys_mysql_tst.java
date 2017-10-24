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
package gplx.dbs.groupBys; import gplx.*; import gplx.dbs.*;
import org.junit.*;
public class GroupBys_mysql_tst extends GroupBys_base_tst {
	@Override protected Db_conn provider_() {return Db_conn_fxt.Mysql();}
	@Test  public void GroupBy_1fld() {super.GroupBy_1fld_hook();}
	@Test  public void GroupBy_2fld() {super.GroupBy_2fld_hook();}
	@Test  public void Min() {super.MinMax_hook(true);}
	@Test  public void Max() {super.MinMax_hook(false);}
	@Test  public void Count() {super.Count_hook();}
	@Test  public void Sum() {super.Sum_hook();}
}
