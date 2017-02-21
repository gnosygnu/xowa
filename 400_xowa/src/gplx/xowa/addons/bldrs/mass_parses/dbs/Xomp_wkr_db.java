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
package gplx.xowa.addons.bldrs.mass_parses.dbs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.mass_parses.*;
import gplx.dbs.*;
import gplx.xowa.htmls.core.dbs.*;
public class Xomp_wkr_db {
	public Xomp_wkr_db(int idx, Io_url url) {
		this.idx = idx;
		this.url = url;
		this.conn = Db_conn_bldr.Instance.Get_or_autocreate(true, url);
		this.html_tbl = new Xowd_html_tbl(conn);
		conn.Meta_tbl_assert(html_tbl);
	}
	public int Idx() {return idx;} private final    int idx;
	public Io_url Url() {return url;}  private Io_url url;
	public Db_conn Conn() {return conn;} private Db_conn conn;
	public Xowd_html_tbl Html_tbl() {return html_tbl;} private final    Xowd_html_tbl html_tbl;

	public static Xomp_wkr_db New(Io_url root_dir, int uid) {
		Io_url url = root_dir.GenSubFil_nest("xomp_" + Int_.To_str_fmt(uid, "000"), "xomp_wkr.sqlite3");
		return new Xomp_wkr_db(uid, url);
	}
}
