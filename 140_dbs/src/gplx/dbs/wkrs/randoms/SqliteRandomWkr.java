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

import gplx.RandomAdp_;
import gplx.dbs.Db_conn;
import gplx.dbs.Db_rdr;
import gplx.dbs.Db_rdr_;
import gplx.dbs.wkrs.SqlWkrMgr;

public class SqliteRandomWkr implements SqlRandomWkr {
    @Override public String Key() {return SqlWkrMgr.WKR_RANDOM;}

    // NOTE: selects only 1 random row to simplify method signature
    // * uses COUNT, LIMIT, OFFSET, so should also work for MySQL
    // * to return many rows, look at https://stackoverflow.com/questions/4114940/select-random-rows-in-sqlite
    // EX: SELECT * FROM table WHERE id IN (SELECT id FROM table ORDER BY RANDOM() LIMIT x)
    // Also, note that SQLite does not support TYPE_FORWARD_ONLY so can't jump back and forth thru ResultSet
    @Override public Object[] SelectRandomRow(Db_conn conn, String select, String from, String where) {
        Db_rdr rdr = Db_rdr_.Empty;
        try {
            String sqlSuffix = ("FROM " + from + " WHERE " + where);

            // get rowCount of resultSet
            rdr = conn.Stmt_sql("SELECT COUNT(*) AS RowCount " + sqlSuffix).Exec_select__rls_auto();
            int rowCount = rdr.Read_int("RowCount");

            // get random row
            int random = RandomAdp_.new_().Next(rowCount);
            rdr = conn.Stmt_sql("SELECT " + select + " " + sqlSuffix + " LIMIT 1 OFFSET " + random).Exec_select__rls_auto();

            // return result
            int fldsLen = rdr.Fld_len();
            Object[] rv = new Object[fldsLen];
            for (int i = 0; i < fldsLen; i++) {
                rv[i] = rdr.Read_at(i);
            }
            return rv;
        }
        catch (Exception exc) { // for debugging; should log
            throw exc;
        }
        finally {
            rdr.Rls();
        }
    }
}
