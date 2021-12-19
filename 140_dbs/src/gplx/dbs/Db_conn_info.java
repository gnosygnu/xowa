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
package gplx.dbs;
import gplx.types.commons.KeyValHash;
public interface Db_conn_info {
	String Key();				// EX: "sqlite"
	String Raw();				// EX: "gplx_key=sqlite;data source=/db.sqlite3;version=3"
	String Db_api();			// EX: "data source=/db.sqlite3;version=3"
	String Database();			// EX: /db.sqlite3 -> "db" ; xowa -> "xowa"
	Db_conn_info New_self(String raw, KeyValHash hash);
}
