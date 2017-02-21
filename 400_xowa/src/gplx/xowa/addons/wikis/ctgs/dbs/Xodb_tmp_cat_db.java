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
package gplx.xowa.addons.wikis.ctgs.dbs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.ctgs.*;
import gplx.dbs.*;
public class Xodb_tmp_cat_db {
	private final    Io_url url;
	public Xodb_tmp_cat_db(Xowe_wiki wiki) {
		this.url = wiki.Fsys_mgr().Root_dir().GenSubFil("xowa.temp.category.sqlite3");
		this.conn = Db_conn_bldr.Instance.Get_or_autocreate(true, url);
	}
	public Db_conn Conn() {return conn;} private final    Db_conn conn;
	public void Delete() {
		conn.Rls_conn();
		Io_mgr.Instance.DeleteFil(url);
	}
}
