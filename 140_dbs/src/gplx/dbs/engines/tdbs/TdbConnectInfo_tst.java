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
package gplx.dbs.engines.tdbs; import gplx.*; import gplx.dbs.*; import gplx.dbs.engines.*;
import org.junit.*;
public class TdbConnectInfo_tst {
	@Test  public void Full() {
		Db_conn_info connectInfo = Db_conn_info_.parse("gplx_key=tdb;url=C:\\dir\\xmpl.tdb;format=dsv;");
		tst_Parse(connectInfo, Io_url_.new_any_("C:\\dir\\xmpl.tdb"), "dsv");
	}
	@Test  public void DefaultFormat() {
		Db_conn_info connectInfo = Db_conn_info_.parse("gplx_key=tdb;url=C:\\dir\\xmpl.tdb");	// dsv Format inferred
		tst_Parse(connectInfo, Io_url_.new_any_("C:\\dir\\xmpl.tdb"), "dsv");
	}
	void tst_Parse(Db_conn_info connectInfo, Io_url url, String format) {
		Tfds.Eq(((Tdb_conn_info)connectInfo).Url(), url);
	}
}
