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
import gplx.dbs.*; import gplx.dbs.cfgs.*;
import gplx.xowa.addons.bldrs.mass_parses.parses.pools.*; import gplx.xowa.addons.bldrs.mass_parses.parses.locks.*;
public class Xomp_mgr_db {
	public Xomp_mgr_db(Io_url url) {
		this.url = url;
		this.conn = Db_conn_bldr.Instance.Get_or_autocreate(true, url);
		this.page_tbl = new Xomp_page_tbl(conn);
		this.wkr_tbl = new Xomp_wkr_tbl(conn);
		this.cfg_tbl = new Db_cfg_tbl(conn, "xowa_cfg");
		// this.lock_mgr = new Xomp_lock_mgr__db(conn, 5000);
		this.lock_mgr = new Xomp_lock_mgr__fsys(5000, this.Dir());
	}
	public Db_conn			Conn() {return conn;} private final    Db_conn conn;
	public Io_url			Url() {return url;}  private final    Io_url url;
	public Io_url			Dir() {return url.OwnerDir();}
	public Xomp_page_tbl	Tbl__page() {return page_tbl;} private final    Xomp_page_tbl page_tbl;
	public Xomp_wkr_tbl		Tbl__wkr() {return wkr_tbl;} private final    Xomp_wkr_tbl wkr_tbl;
	public Db_cfg_tbl		Tbl__cfg() {return cfg_tbl;} private final    Db_cfg_tbl cfg_tbl;
	public Xomp_lock_mgr	Lock_mgr() {return lock_mgr;} private final    Xomp_lock_mgr lock_mgr;

	public void Remake() {
		conn.Meta_tbl_remake_many(page_tbl, wkr_tbl, cfg_tbl);
		lock_mgr.Remake();
	}

	public static Io_url New__url(Xowe_wiki wiki) {return  wiki.Fsys_mgr().Root_dir().GenSubDir_nest("tmp", "xomp");}
	public static Xomp_mgr_db New__make(Xowe_wiki wiki) {
		Io_url root_dir = New__url(wiki);
		Io_mgr.Instance.DeleteDirDeep(root_dir);
		return new Xomp_mgr_db(root_dir.GenSubFil("xomp.sqlite3"));
	}
	public static Xomp_mgr_db New__load(Xowe_wiki wiki) {return New__load(wiki.Fsys_mgr().Root_dir().GenSubDir_nest("tmp", "xomp"));}
	public static Xomp_mgr_db New__load(Io_url root_dir) {
		return new Xomp_mgr_db(root_dir.GenSubFil("xomp.sqlite3"));
	}
}
