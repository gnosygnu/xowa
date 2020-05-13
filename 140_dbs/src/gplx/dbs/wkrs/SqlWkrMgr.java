/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2020 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.dbs.wkrs;

import gplx.dbs.Db_conn;
import gplx.dbs.wkrs.randoms.SqlRandomWkr;

import java.util.HashMap;
import java.util.Map;

public class SqlWkrMgr {
    private final Map<String, SqlWkr> map = new HashMap<>();
    private final Db_conn conn;
    public SqlWkrMgr(Db_conn conn) {
        this.conn = conn;
    }
    public SqlWkr Get(String key) {
        return map.get(key);
    }
    public void Set(SqlWkr wkr) {
        map.put(wkr.Key(), wkr);
    }

    public Object ExecRandomObj(String select, String from, String where) {
		SqlRandomWkr wkr = (SqlRandomWkr)map.get(SqlWkrMgr.WKR_RANDOM);
		return wkr.SelectRandomRow(conn, select, from, where)[0];
    }

    public static final String WKR_RANDOM = "random";
}
