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
package gplx.fsdb; import gplx.*;
import gplx.dbs.*; import gplx.dbs.cfgs.*;
public class Fsdb_db_file {
	public Fsdb_db_file(Io_url url, Db_conn conn) {
		this.url = url; this.conn = conn;
		this.tbl__core_cfg = gplx.xowa.wikis.data.Xowd_cfg_tbl_.New(conn);
	}
	public Io_url				Url()			{return url;}				private final    Io_url url;
	public Db_conn				Conn()			{return conn;}				private final    Db_conn conn;		
	public Db_cfg_tbl			Tbl__cfg()		{return tbl__core_cfg;}		private final    Db_cfg_tbl	tbl__core_cfg;
}
