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
package gplx.xowa.addons.bldrs.exports.splits.htmls; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.exports.*; import gplx.xowa.addons.bldrs.exports.splits.*;
import gplx.dbs.*; import gplx.xowa.htmls.core.dbs.*;
public class Xoh_page_tbl_itm {
	private final    boolean trg;
	public Xoh_page_tbl_itm(boolean trg, int db_id, Db_conn conn) {
		this.trg = trg;
		this.db_id = db_id;
		this.html_tbl = new Xowd_html_tbl(conn);
	}
	public int Db_id() {return db_id;} private final    int db_id;
	public Xowd_html_tbl Html_tbl() {return html_tbl;} private final    Xowd_html_tbl html_tbl;
	public void Rls() {
		html_tbl.Conn().Rls_conn();
		if (trg) html_tbl.Conn().Env_vacuum();
	}
}
