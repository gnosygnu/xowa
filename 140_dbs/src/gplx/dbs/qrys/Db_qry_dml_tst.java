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
package gplx.dbs.qrys; import gplx.*; import gplx.dbs.*;
import org.junit.*;
import gplx.core.criterias.*;
public class Db_qry_dml_tst {
	@Test  public void Delete_basic() {
		tst_XtoSql(Db_qry_delete.new_("tbl0", Db_crt_.New_eq("fld0", "val0"))
				, "DELETE FROM tbl0 WHERE fld0 = 'val0'");
	}
	@Test  public void Insert_basic() {
		tst_XtoSql(new Db_qry_insert("tbl0").Val_int("id", 0).Val_str("name", "me").Val_date("time", DateAdp_.parse_gplx("2007-12-23"))
				, "INSERT INTO tbl0 (id, name, time) VALUES (0, 'me', '2007-12-23 00:00:00.000')");
	}
	@Test  public void Update_basic() {
		Db_qry_update qry = new Db_qry_update();
		qry.From_("tbl0");
		qry.Where_(Db_crt_.New_eq("id", 0)).Val_str("name", "me");
		tst_XtoSql(qry, "UPDATE tbl0 SET name='me' WHERE id = 0");
	}
	@Test  public void Update_all() {
		Db_qry_update qry = new Db_qry_update();
		qry.From_("tbl0");
		qry.Val_int("id", 1).Val_str("name", "me").Val_date("startTime", DateAdp_.parse_gplx("2007-12-23"));
		qry.Where_(Criteria_.And(Db_crt_.New_eq("id", 0), Db_crt_.New_mt("startTime", DateAdp_.parse_gplx("2005-01-01"))));
		tst_XtoSql(qry, "UPDATE tbl0 SET id=1, name='me', startTime='2007-12-23 00:00:00.000' WHERE (id = 0 AND startTime > '2005-01-01 00:00:00.000')");
	}		
	void tst_XtoSql(Db_qry qry, String expd) {Tfds.Eq(expd, qry.To_sql__exec(gplx.dbs.sqls.Sql_qry_wtr_.New__basic()));}
}
