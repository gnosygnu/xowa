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
package gplx.dbs.joins; import gplx.*; import gplx.dbs.*;
import org.junit.*;
public class Joins_tdb_tst extends Joins_base_tst {
	@Override protected Db_conn provider_() {return Db_conn_fxt.Tdb("120_dbs_joins.dsv");}
	@Test  public void InnerJoin() {
		try {
			InnerJoin_hook();
		}
		catch (Exception exc) {
			if (String_.Has(Err_.Message_lang(exc), "joins not supported for tdbs")) return;
		}
		Tfds.Fail("'joins not supported for tdbs' error not thrown");
	}
}
