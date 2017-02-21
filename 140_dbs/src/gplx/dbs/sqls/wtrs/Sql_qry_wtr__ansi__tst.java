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
package gplx.dbs.sqls.wtrs; import gplx.*; import gplx.dbs.*; import gplx.dbs.sqls.*;
import org.junit.*;
import gplx.core.criterias.*; import gplx.dbs.sqls.*;
public class Sql_qry_wtr__ansi__tst {
	Sql_qry_wtr sqlWtr = Sql_qry_wtr_.New__basic();
	@Test  public void Insert() {
		tst_XtoSql
			(	Db_qry_.insert_("people").Val_int("id", 1).Val_str("name", "me")
			,	"INSERT INTO people (id, name) VALUES (1, 'me')"
			);
	}
	@Test  public void Delete() {
		Criteria crt = Db_crt_.New_eq("id", 1);
		tst_XtoSql
			(	Db_qry_.delete_("people", crt)
			,	"DELETE FROM people WHERE id = 1"
			);
	}
	@Test  public void Update() {
		tst_XtoSql
			(	Db_qry_.update_("people", Db_crt_.New_eq("id", 1)).Val_str("name", "me")
			,	"UPDATE people SET name='me' WHERE id = 1"
			);
	}
	@Test  public void SelectAll() {
		tst_XtoSql
			(	Db_qry_.select_().From_("people")
			,	"SELECT * FROM people"
			);
	}
	@Test  public void SelectFlds() {
		tst_XtoSql
			(	Db_qry_.select_().Cols_("id", "name").From_("people")
			,	"SELECT id, name FROM people"
			);
	}
	@Test  public void SelectOrderBy() {
		tst_XtoSql
			(	Db_qry_.select_().From_("people").Order_("name", false)
			,	"SELECT * FROM people ORDER BY name DESC"
			);
	}
	@Test  public void SelectWhere() {
		tst_XtoSql
			(	Db_qry_.select_().From_("people").Where_(Db_crt_.New_eq("id", 1))
			,	"SELECT * FROM people WHERE id = 1"
			);
	}
	@Test  public void Select_From_Alias() {
		tst_XtoSql
			(	Db_qry_.select_().From_("people", "p")
			,	"SELECT * FROM people p"
			);
	}
	@Test  public void Select_Join_Alias() {
		tst_XtoSql
			(	Db_qry_.select_().From_("people", "p").Join_("roles", "r", Db_qry_.New_join__same("p", "id"))
			,	"SELECT * FROM people p INNER JOIN roles r ON p.id = r.id"
			);
	}
	@Test  public void Prepare() {
		tst_XtoSql
			(	Db_qry_.insert_("people").Val_int("id", 1).Val_str("name", "me")
			,	"INSERT INTO people (id, name) VALUES (?, ?)"
			,	true
			);

		tst_XtoSql
			(	Db_qry_.delete_("people", Db_crt_.New_eq("id", 1))
			,	"DELETE FROM people WHERE id = ?"
			,	true
			);

		tst_XtoSql
			(	Db_qry_.update_("people", Db_crt_.New_eq("id", 1)).Val_str("name", "me")
			,	"UPDATE people SET name=? WHERE id = ?"
			,	true
			);
	}
	void tst_XtoSql(Db_qry cmd, String expd)				{tst_XtoSql(cmd, expd, false);}
	void tst_XtoSql(Db_qry cmd, String expd, boolean prepare)	{Tfds.Eq(expd, sqlWtr.To_sql_str(cmd, prepare));}
}
