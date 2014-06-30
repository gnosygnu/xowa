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
public class Sql_cmd_wtr_ansi_tst {
	Sql_cmd_wtr whereWtr = Sql_cmd_wtr_ansi_.default_();
	@Test  public void Basic() {
		tst_XtoSql_where(Db_crt_.eq_("id", 1), "id=1");
		tst_XtoSql_where(Db_crt_.eqn_("id", 1), "id!=1");
		tst_XtoSql_where(Db_crt_.mt_("id", 1), "id>1");
		tst_XtoSql_where(Db_crt_.mte_("id", 1), "id>=1");
		tst_XtoSql_where(Db_crt_.lt_("id", 1), "id<1");
		tst_XtoSql_where(Db_crt_.lte_("id", 1), "id<=1");
		tst_XtoSql_where(Db_crt_.between_("id", 1, 2), "id BETWEEN 1 AND 2");
		tst_XtoSql_where(Db_crt_.in_("id", 1, 2, 3), "id IN (1, 2, 3)");
	}
	@Test  public void AndOr() {
		tst_XtoSql_where(Criteria_.And(Db_crt_.eq_("id", 1), Db_crt_.eq_("name", "me")), "(id=1 AND name='me')");
		tst_XtoSql_where(Criteria_.Or(Db_crt_.eq_("id", 1), Db_crt_.eq_("name", "me")), "(id=1 OR name='me')");
		tst_XtoSql_where(Criteria_.Or(Db_crt_.eq_("id", 1), Criteria_.And(Db_crt_.eq_("name", "me"), Db_crt_.eq_("id", 1))), "(id=1 OR (name='me' AND id=1))");
	}
	void tst_XtoSql_where(Criteria crt, String expd) {
		String_bldr sb = String_bldr_.new_();
		whereWtr.BldWhere(sb, crt);
		Tfds.Eq(expd, sb.XtoStr());
	}
}
