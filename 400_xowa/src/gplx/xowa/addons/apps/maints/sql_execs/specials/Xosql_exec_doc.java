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
package gplx.xowa.addons.apps.maints.sql_execs.specials; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.apps.*; import gplx.xowa.addons.apps.maints.*; import gplx.xowa.addons.apps.maints.sql_execs.*;
import gplx.langs.mustaches.*;
public class Xosql_exec_doc implements Mustache_doc_itm {
	private final    String domain, db, sql;
	public Xosql_exec_doc(String domain, String db, String sql) {
		this.domain = domain;
		this.db = db;
		this.sql = sql;
	}
	public boolean Mustache__write(String key, Mustache_bfr bfr) {
		if		(String_.Eq(key, "domain"))			bfr.Add_str_u8(domain);
		else if	(String_.Eq(key, "db"))				bfr.Add_str_u8(db);
		else if	(String_.Eq(key, "sql"))			bfr.Add_str_u8(sql);
		else										return false;
		return true;
	}
	public Mustache_doc_itm[] Mustache__subs(String key) {
		return Mustache_doc_itm_.Ary__empty;
	}

	public static final    Xosql_exec_doc[] Ary_empty = new Xosql_exec_doc[0];
}
