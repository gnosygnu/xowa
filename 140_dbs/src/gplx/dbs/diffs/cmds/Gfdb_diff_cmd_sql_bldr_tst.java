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
package gplx.dbs.diffs.cmds;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.frameworks.tests.GfoTstr;
import gplx.types.basics.utls.StringUtl;
import org.junit.*;
public class Gfdb_diff_cmd_sql_bldr_tst {
	private final Gfdb_diff_cmd_sql_bldr_fxt fxt = new Gfdb_diff_cmd_sql_bldr_fxt();
	@Test public void Insert() {
		fxt.Test__insert("tbl1", StringUtl.Ary("key1", "key2"), StringUtl.Ary("fld1", "fld2"), 0, 99, StringUtl.ConcatLinesNlSkipLast
		( "INSERT  INTO db_curr.tbl1"
		, "SELECT  d.key1, d.key2, d.fld1, d.fld2"
		, "FROM    db_temp.tbl1_pkey k"
		, "        JOIN db_diff.tbl1 d ON k.key1 = d.key1 AND k.key2 = d.key2"
		, "WHERE   k.diff_type = 1"
		, "AND     k.diff_uid BETWEEN 0 AND 99;"
		));
	}
	@Test public void Update() {
		fxt.Test__update("tbl1", StringUtl.Ary("key1", "key2"), StringUtl.Ary("fld1", "fld2"), 0, 99, StringUtl.ConcatLinesNlSkipLast
		( "REPLACE INTO db_curr.tbl1"
		, "SELECT  d.key1, d.key2, d.fld1, d.fld2"
		, "FROM    db_temp.tbl1_pkey k"
		, "        JOIN db_diff.tbl1 d ON k.key1 = d.key1 AND k.key2 = d.key2"
		, "WHERE   k.diff_type = 2"
		, "AND     k.diff_uid BETWEEN 0 AND 99;"
		));
	}
	@Test public void Delete() {
		fxt.Test__delete("tbl1", StringUtl.Ary("key1", "key2"), 0, 99, StringUtl.ConcatLinesNlSkipLast
		( "DELETE  db_curr.tbl1"
		, "WHERE   key1 || '|' || key2 IN"
		, "(       SELECT  k.key1 || '|' || k.key2"
		, "        FROM    db_temp.tbl1_pkey k"
		, "                JOIN db_diff.tbl1 d ON k.key1 = d.key1 AND k.key2 = d.key2"
		, "        WHERE   k.diff_type = 0"
		, "        AND     k.diff_uid BETWEEN 0 AND 99"
		, ");"
		));
	}
}
class Gfdb_diff_cmd_sql_bldr_fxt {
	private Gfdb_diff_cmd_sql_bldr bldr = new Gfdb_diff_cmd_sql_bldr();
	private final BryWtr bfr = BryWtr.New();
	public void Test__insert(String tbl_name, String[] keys, String[] flds, int rng_bgn, int rng_end, String expd) {
		bldr.Bld_insert(bfr, tbl_name, keys, flds, 0, 99);
		GfoTstr.EqLines(expd, bfr.ToStrAndClear());
	}
	public void Test__update(String tbl_name, String[] keys, String[] flds, int rng_bgn, int rng_end, String expd) {
		bldr.Bld_update(bfr, tbl_name, keys, flds, 0, 99);
		GfoTstr.EqLines(expd, bfr.ToStrAndClear());
	}
	public void Test__delete(String tbl_name, String[] keys, int rng_bgn, int rng_end, String expd) {
		bldr.Bld_delete(bfr, tbl_name, keys, 0, 99);
		GfoTstr.EqLines(expd, bfr.ToStrAndClear());
	}
}
