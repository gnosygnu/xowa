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
import gplx.core.criterias.*; import gplx.dbs.sqls.*;
public class AnsiSqlWtr_tst {
	Sql_qry_wtr sqlWtr = Sql_qry_wtr_.new_ansi();
	@Test  public void Insert() {
		tst_XtoSql
			(	Db_qry_.insert_("people").Arg_("id", 1).Arg_("name", "me")
			,	"INSERT INTO people (id, name) VALUES (1, 'me')"
			);
	}
	@Test  public void Delete() {
		Criteria crt = Db_crt_.eq_("id", 1);
		tst_XtoSql
			(	Db_qry_.delete_("people", crt)
			,	"DELETE FROM people WHERE id=1"
			);
	}
	@Test  public void Update() {
		tst_XtoSql
			(	Db_qry_.update_("people", Db_crt_.eq_("id", 1)).Arg_("name", "me")
			,	"UPDATE people SET name='me' WHERE id=1"
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
			(	Db_qry_.select_().From_("people").OrderBy_("name", false)
			,	"SELECT * FROM people ORDER BY name DESC"
			);
	}
	@Test  public void SelectWhere() {
		tst_XtoSql
			(	Db_qry_.select_().From_("people").Where_(Db_crt_.eq_("id", 1))
			,	"SELECT * FROM people WHERE id=1"
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
			(	Db_qry_.select_().From_("people", "p").Join_("roles", "r", Sql_join_itm.same_("p", "id"))
			,	"SELECT * FROM people p INNER JOIN roles r ON p.id=r.id"
			);
	}
	@Test  public void Prepare() {
		tst_XtoSql
			(	Db_qry_.insert_("people").Arg_("id", 1).Arg_("name", "me")
			,	"INSERT INTO people (id, name) VALUES (?, ?)"
			,	true
			);

		tst_XtoSql
			(	Db_qry_.delete_("people", Db_crt_.eq_("id", 1))
			,	"DELETE FROM people WHERE id=?"
			,	true
			);

		tst_XtoSql
			(	Db_qry_.update_("people", Db_crt_.eq_("id", 1)).Arg_("name", "me")
			,	"UPDATE people SET name=? WHERE id=?"
			,	true
			);
	}
	void tst_XtoSql(Db_qry cmd, String expd)				{tst_XtoSql(cmd, expd, false);}
	void tst_XtoSql(Db_qry cmd, String expd, boolean prepare)	{Tfds.Eq(expd, sqlWtr.Xto_str(cmd, prepare));}
}
