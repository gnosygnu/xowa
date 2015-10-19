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
import gplx.core.criterias.*;
public class Db_crt_tst {
	@Before public void setup() {
		row = GfoNde_.vals_(GfoFldList_.new_().Add("id", IntClassXtn.Instance).Add("name", StringClassXtn.Instance), Object_.Ary(1, "me"));
	}
	@Test  public void EqualTest() {
		crt = Db_crt_.eq_("id", 1);
		tst_Match(true, row, crt);
	}
	@Test  public void EqualFalseTest() {
		crt = Db_crt_.eq_("id", 2);
		tst_Match(false, row, crt);
	}
	@Test  public void AndCompositeTest() {
		crt = Criteria_.And(Db_crt_.eq_("id", 1), Db_crt_.eq_("name", "me"));
		tst_Match(true, row, crt);

		crt = Criteria_.And(Db_crt_.eq_("id", 1), Db_crt_.eq_("name", "you"));
		tst_Match(false, row, crt);
	}
	@Test  public void OrCompositeTest() {
		crt = Criteria_.Or(Db_crt_.eq_("id", 1), Db_crt_.eq_("name", "you"));
		tst_Match(true, row, crt);

		crt = Criteria_.Or(Db_crt_.eq_("id", 2), Db_crt_.eq_("name", "you"));
		tst_Match(false, row, crt);
	}

	void tst_Match(boolean epxd, GfoNde row, Criteria crt) {
		boolean actl = crt.Matches(row);
		Tfds.Eq(epxd, actl);
	}
	GfoNde row; Criteria crt;
}
