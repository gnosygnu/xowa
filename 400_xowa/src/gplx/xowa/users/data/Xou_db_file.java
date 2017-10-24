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
package gplx.xowa.users.data; import gplx.*; import gplx.xowa.*; import gplx.xowa.users.*;
import gplx.dbs.*; import gplx.dbs.cfgs.*; import gplx.dbs.metas.*;
import gplx.xowa.files.caches.*;
import gplx.xowa.users.bmks.*; import gplx.xowa.users.history.*;
public class Xou_db_file {
	private final    Db_conn conn;
	public Xou_db_file(Db_conn conn) {
		this.conn = conn;
		this.tbl__cfg		= gplx.xowa.wikis.data.Xowd_cfg_tbl_.New(conn);
		this.tbl__site		= new Xoud_site_tbl(conn);
		this.tbl__history	= new Xoud_history_tbl(conn);
		this.tbl__cache		= new Xou_cache_tbl(conn);
	}
	public Db_cfg_tbl				Tbl__cfg()		{return tbl__cfg;}		private final    Db_cfg_tbl tbl__cfg;
	public Xoud_site_tbl			Tbl__site()		{return tbl__site;}		private final    Xoud_site_tbl tbl__site;
	public Xoud_history_tbl			Tbl__history()	{return tbl__history;}	private final    Xoud_history_tbl tbl__history;
	public Xou_cache_tbl			Tbl__cache()	{return tbl__cache;}	private final    Xou_cache_tbl tbl__cache;
	public void Init_assert() {
		if (!conn.Meta_tbl_exists(tbl__cache.Tbl_name())) {
			tbl__cfg.Create_tbl();
			tbl__cache.Create_tbl();
		}
	}
}
