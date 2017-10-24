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
import org.junit.*; import gplx.dbs.sqls.*;
public class Db_qry_select_tst {
	@Before public void setup() {
		cmd = new Db_qry__select_cmd();
	}	Db_qry__select_cmd cmd; String expd;
	@Test  public void Basic() {
		cmd.Cols_("fld0", "fld1").From_("tbl0");
		expd = "SELECT fld0, fld1 FROM tbl0";

		tst_XtoStr(cmd, expd);
	}
	@Test  public void OrderDoesNotMatter() {
		cmd.From_("tbl0").Cols_("fld0", "fld1");
		expd = "SELECT fld0, fld1 FROM tbl0";

		tst_XtoStr(cmd, expd);
	}
	@Test  public void DefaultAllFields() {
		cmd.From_("tbl0");
		expd = "SELECT * FROM tbl0";

		tst_XtoStr(cmd, expd);
	}		
	@Test  public void Where() {
		cmd.From_("tbl0").Where_(Db_crt_.New_eq("fld0", 0));
		expd = "SELECT * FROM tbl0 WHERE fld0 = 0";

		tst_XtoStr(cmd, expd);
	}
	@Test  public void Join() {
		cmd.From_("tbl0").Join_("tbl1", "t1", Db_qry_.New_join__join("fld1", "tbl0", "fld0"));
		expd = "SELECT * FROM tbl0 INNER JOIN tbl1 t1 ON tbl0.fld0 = t1.fld1";

		tst_XtoStr(cmd, expd);
	}		
	@Test  public void OrderBy() {
		cmd.From_("tbl0").Order_("fld0", true);
		expd = "SELECT * FROM tbl0 ORDER BY fld0";

		tst_XtoStr(cmd, expd);
	}		
	@Test  public void OrderByMany() {
		cmd.From_("tbl0").Order_asc_many_("fld0", "fld1");
		expd = "SELECT * FROM tbl0 ORDER BY fld0, fld1";

		tst_XtoStr(cmd, expd);
	}		
	@Test  public void Limit() {
		cmd.From_("tbl0").Limit_(10);
		expd = "SELECT * FROM tbl0 LIMIT 10";

		tst_XtoStr(cmd, expd);
	}		
//		@Test  public void GroupBy() {
//			cmd.From_("tbl0").groupBy_("fld0", "fld1");
//			expd = "SELECT fld0, fld1 FROM tbl0 GROUP BY fld0, fld1";
//			Tfds.Eq(cmd.To_str(), expd);
//		}
//		@Test  public void Union() {
//			cmd.From_("tbl0").select("fld0").union_(qry2.from("tbl1").select("fld0"));
//			cmd.From_("tbl0").select("fld0").union_().from("tbl1").select("fld0"); // feasible, but will be bad later when trying to access Db_qry__select_cmd props
//			expd = "SELECT fld0 FROM tbl0 UNION SELECT fld0 FROM tbl1";
//			Tfds.Eq(cmd.To_str(), expd);
//		}
//		@Test  public void Having() {
//			cmd.From_("tbl0").groupBy_("fld0", "fld1");
//			expd = "SELECT fld0, fld1 FROM tbl0 GROUP BY fld0, fld1 HAVING Count(fld0) > 1";
//			Tfds.Eq(cmd.To_str(), expd);
//		}
	void tst_XtoStr(Db_qry qry, String expd) {Tfds.Eq(expd, cmd.To_sql__exec(Sql_qry_wtr_.New__basic()));}
}
