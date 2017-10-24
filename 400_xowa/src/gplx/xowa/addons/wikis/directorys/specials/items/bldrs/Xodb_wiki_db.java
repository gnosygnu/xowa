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
package gplx.xowa.addons.wikis.directorys.specials.items.bldrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.directorys.*; import gplx.xowa.addons.wikis.directorys.specials.*; import gplx.xowa.addons.wikis.directorys.specials.items.*;
import gplx.dbs.*;
public class Xodb_wiki_db implements Db_tbl_owner {
	private final    Ordered_hash tbls = Ordered_hash_.New();
	public Xodb_wiki_db(int tid, Io_url url, Db_conn conn) {
		this.tid = tid;
		this.url = url;
		this.conn = conn;
	}
	public int Tid() {return tid;} private final    int tid;
	public Io_url Url() {return url;} private final    Io_url url;
	public Db_conn Conn() {return conn;} private final    Db_conn conn;
	public Db_tbl Tbls__get_by_key(String key) {return (Db_tbl)tbls.Get_by(key);}
	public void Tbls__add(boolean create, Db_tbl... ary) {
		int len = ary.length;
		for (int i = 0; i < len; ++i) {
			Db_tbl tbl = ary[i];
			tbls.Add(tbl.Tbl_name(), tbl);
			if (create)
				tbl.Create_tbl();
		}
	}

	public static Xodb_wiki_db Make(int tid, Io_url url) {
		Db_conn conn = Db_conn_bldr.Instance.Get_or_new(url).Conn();
		return new Xodb_wiki_db(tid, url, conn);
	}
}
