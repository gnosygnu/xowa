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
package gplx.core.criterias; import gplx.*; import gplx.core.*;
import org.junit.*;
public class Criteria_tst {
	@Test  public void Equal() {
		Criteria crt = Criteria_.eq_(true);
		fx.tst_Matches(crt, true);
		fx.tst_MatchesNot(crt, false);
		fx.tst_MatchesFail(crt, "true");

		fx.tst_Matches(Criteria_.eq_(1), 1);
		fx.tst_Matches(Criteria_.eq_("equal"), "equal");
		fx.tst_Matches(Criteria_.eq_(date), date);
	}
	@Test  public void Not() {
		Criteria crt = Criteria_.eqn_(true);
		fx.tst_Matches(crt, false);
		fx.tst_MatchesNot(crt, true);
		fx.tst_MatchesFail(crt, "false");

		fx.tst_Matches(Criteria_.eqn_(1), -1);
		fx.tst_Matches(Criteria_.eqn_("equal"), "not equal");
		fx.tst_Matches(Criteria_.eqn_(date), date.Add_minute(1));
	}
	@Test  public void MoreThan() {
		Criteria crt = Criteria_.mt_(0);
		fx.tst_Matches(crt, 1, 2);
		fx.tst_MatchesNot(crt, 0, -1);
		fx.tst_MatchesFail(crt, "1");

		fx.tst_Matches(Criteria_.mt_(0), 1);
		fx.tst_Matches(Criteria_.mt_("a"), "b");
		fx.tst_Matches(Criteria_.mt_(date), date.Add_minute(1));
		fx.tst_Matches(Criteria_.mt_(false), true);		// MISC: thus truth is greater than falsehood
	}
	@Test  public void MoreThanEq() {
		Criteria crt = Criteria_.mte_(0);
		fx.tst_Matches(crt, 0);
	}
	@Test  public void Less() {
		Criteria crt = Criteria_.lt_(0);
		fx.tst_Matches(crt, -1, -2);
		fx.tst_MatchesNot(crt, 0, 1);
		fx.tst_MatchesFail(crt, "-1");
	}
	@Test  public void LessEq() {
		Criteria crt = Criteria_.lte_(0);
		fx.tst_Matches(crt, 0);
	}
	@Test  public void Between() {
		Criteria crt = Criteria_.between_(-1, 1);
		fx.tst_Matches(crt, 0, 1, -1);
		fx.tst_MatchesNot(crt, -2, 2);
		fx.tst_MatchesFail(crt, "0");

		fx.tst_Matches(Criteria_.between_(1, -1), 0);		// reverse range
		fx.tst_Matches(Criteria_.between_("a", "c"), "b");
	}
	@Test  public void In() {
		Criteria crt = Criteria_.in_(0, 1, 2);
		fx.tst_Matches(crt, 0, 1, 2);
		fx.tst_MatchesNot(crt, 3, -1);
		fx.tst_MatchesFail(crt, "0");
	}
	CriteriaFxt fx = new CriteriaFxt();
	DateAdp date = DateAdp_.parse_gplx("2001-01-01");
}
class CriteriaFxt {
	public void tst_Matches(Criteria crt, Object... ary) {for (Object val : ary) Tfds.Eq(true, crt.Matches(val));}
	public void tst_MatchesNot(Criteria crt, Object... ary) {for (Object val : ary) Tfds.Eq(false, crt.Matches(val));}
	public void tst_MatchesFail(Criteria crt, Object val) {
		try {crt.Matches(val);}
		catch(Exception exc) {Err_.Noop(exc); return;}
		Tfds.Fail_expdError();
	}
}
