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
public class Sql_qry_wtr_tst {
	private final Sql_qry_wtr_fxt fxt = new Sql_qry_wtr_fxt();
	@Test  public void Val() {
		fxt.Test_val(null		, "NULL");
		fxt.Test_val(true		, "1");
		fxt.Test_val(false		, "0");
		fxt.Test_val(1			, "1");
		fxt.Test_val(1.1		, "1.1");
		fxt.Test_val("a"		, "'a'");
		fxt.Test_val("a'b"		, "'a''b'");
	}
	@Test  public void Where_basic() {
		fxt.Test_where(Db_crt_.eq_("id", 1), "id=1");
		fxt.Test_where(Db_crt_.eqn_("id", 1), "id!=1");
		fxt.Test_where(Db_crt_.mt_("id", 1), "id>1");
		fxt.Test_where(Db_crt_.mte_("id", 1), "id>=1");
		fxt.Test_where(Db_crt_.lt_("id", 1), "id<1");
		fxt.Test_where(Db_crt_.lte_("id", 1), "id<=1");
		fxt.Test_where(Db_crt_.between_("id", 1, 2), "id BETWEEN 1 AND 2");
		fxt.Test_where(Db_crt_.in_("id", 1, 2, 3), "id IN (1, 2, 3)");
	}
	@Test  public void Where_and() {
		fxt.Test_where(Criteria_.And(Db_crt_.eq_("id", 1), Db_crt_.eq_("name", "me")), "(id=1 AND name='me')");
		fxt.Test_where(Criteria_.Or(Db_crt_.eq_("id", 1), Db_crt_.eq_("name", "me")), "(id=1 OR name='me')");
		fxt.Test_where(Criteria_.Or(Db_crt_.eq_("id", 1), Criteria_.And(Db_crt_.eq_("name", "me"), Db_crt_.eq_("id", 1))), "(id=1 OR (name='me' AND id=1))");
	}
}
class Sql_qry_wtr_fxt {
	private final Sql_qry_wtr_ansi sql_wtr = (Sql_qry_wtr_ansi)Sql_qry_wtr_.new_ansi();
	public void Test_val(Object val, String expd) {
		String_bldr sb = String_bldr_.new_();
		Db_arg arg = new Db_arg("not needed", val);
		sql_wtr.Bld_val(sb, arg);
		Tfds.Eq(expd, sb.XtoStr());
	}
	public void Test_where(Criteria crt, String expd) {
		String_bldr sb = String_bldr_.new_();
		sql_wtr.Bld_where(sb, crt);
		Tfds.Eq(expd, sb.XtoStr());
	}
}
