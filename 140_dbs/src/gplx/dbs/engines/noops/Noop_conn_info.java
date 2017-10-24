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
package gplx.dbs.engines.noops; import gplx.*; import gplx.dbs.*; import gplx.dbs.engines.*;
public class Noop_conn_info extends Db_conn_info__base {
	public Noop_conn_info(String raw, String db_api, String database) {super(raw, db_api, database);}
	@Override public String Key() {return Tid_const;} public static final    String Tid_const = "null_db";
	@Override public Db_conn_info New_self(String raw, Keyval_hash hash) {return this;}
	public static final    Noop_conn_info Instance = new Noop_conn_info("gplx_key=null_db", "", "");
}
