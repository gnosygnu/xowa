/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012 gnosygnu@gmail.com

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package gplx.dbs; import gplx.*;
import org.junit.*;
import gplx.criterias.*;
public class Db_qry_dml_tst {
	@Test  public void Delete_basic() {
		tst_XtoSql(Db_qry_delete.new_("tbl0", Db_crt_.eq_("fld0", "val0"))
				, "DELETE FROM tbl0 WHERE fld0='val0'");
	}
	@Test  public void Insert_basic() {
		tst_XtoSql(new Db_qry_insert("tbl0").Arg_("id", 0).Arg_("name", "me").Arg_("time", DateAdp_.parse_gplx("2007-12-23"))
				, "INSERT INTO tbl0 (id, name, time) VALUES (0, 'me', '2007-12-23 00:00:00.000')");
	}
	@Test  public void Update_basic() {
		Db_qry_update qry = Db_qry_update.new_();
		qry.From_("tbl0");
		qry.Where_(Db_crt_.eq_("id", 0)).Arg_("name", "me");
		tst_XtoSql(qry, "UPDATE tbl0 SET name='me' WHERE id=0");
	}
	@Test  public void Update_all() {
		Db_qry_update qry = Db_qry_update.new_();
		qry.From_("tbl0");
		qry.Arg_("id", 1).Arg_("name", "me").Arg_("startTime", DateAdp_.parse_gplx("2007-12-23"));
		qry.Where_(Criteria_.And(Db_crt_.eq_("id", 0), Db_crt_.mt_("startTime", DateAdp_.parse_gplx("2005-01-01"))));
		tst_XtoSql(qry, "UPDATE tbl0 SET id=1, name='me', startTime='2007-12-23 00:00:00.000' WHERE (id=0 AND startTime>'2005-01-01 00:00:00.000')");
	}		
	void tst_XtoSql(Db_qry qry, String expd) {Tfds.Eq(expd, qry.Xto_sql());}
}
