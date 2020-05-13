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
package gplx.dbs.wkrs.randoms;

import gplx.dbs.Db_conn;
import gplx.dbs.wkrs.SqlWkrMgr;

import java.util.ArrayList;
import java.util.List;

public class TestRandomWkr implements SqlRandomWkr {
    private final List<Object[]> list = new ArrayList<>();
    private int index = 0;
    public void AddRow(Object... ary) {
        list.add(ary);
    }
    public void Clear() {
        list.clear();
        index = 0;
    }
    public String SelectRandomRowSelect() {return selectRandomRowSelect;} private String selectRandomRowSelect;
    public String SelectRandomRowFrom() {return selectRandomRowFrom;} private String selectRandomRowFrom;
    public String SelectRandomRowWhere() {return selectRandomRowWhere;} private String selectRandomRowWhere;

    @Override public String Key() {return SqlWkrMgr.WKR_RANDOM;}
    @Override public Object[] SelectRandomRow(Db_conn conn, String select, String from, String where) {
        this.selectRandomRowSelect = select;
        this.selectRandomRowFrom = from;
        this.selectRandomRowWhere = where;
        return list.get(index++);
    }

    public static TestRandomWkr New(Db_conn conn) {
        TestRandomWkr wkr = new TestRandomWkr();
        conn.WkrMgr().Set(wkr);
        return wkr;
    }
}
