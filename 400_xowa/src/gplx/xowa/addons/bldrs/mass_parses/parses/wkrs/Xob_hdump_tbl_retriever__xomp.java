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
package gplx.xowa.addons.bldrs.mass_parses.parses.wkrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.mass_parses.*; import gplx.xowa.addons.bldrs.mass_parses.parses.*;
import gplx.dbs.*;
import gplx.xowa.wikis.nss.*; import gplx.xowa.htmls.core.bldrs.*; import gplx.xowa.htmls.core.dbs.*;
class Xob_hdump_tbl_retriever__xomp implements Xob_hdump_tbl_retriever {
	private final    Db_conn conn;
	private final    Xowd_html_tbl tbl;
	public Xob_hdump_tbl_retriever__xomp(Xowd_html_tbl tbl) {
		this.tbl = tbl;
		this.conn = tbl.Conn();
	}
	public Xowd_html_tbl Get_html_tbl(Xow_ns ns, int prv_row_len) {
		return tbl;
	}
	public void Commit() {conn.Txn_sav();}
	public void Rls_all() {conn.Txn_sav(); conn.Rls_conn();}
}
