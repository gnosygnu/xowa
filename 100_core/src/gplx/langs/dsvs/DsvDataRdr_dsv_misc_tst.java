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
package gplx.langs.dsvs; import gplx.*; import gplx.langs.*;
import org.junit.*; import gplx.core.gfo_ndes.*;
public class DsvDataRdr_dsv_misc_tst {
	@Before public void setup() {fx.Clear();} DsvDataRdr_fxt fx = DsvDataRdr_fxt.new_();
	@Test  public void CmdDlm_NearMatches() {
		fx.run_parse_("a, ,b");
		fx.tst_DatCsv(fx.ary_("a", " ", "b"));
		fx.Clear();

		fx.run_parse_("a,\" \",b");
		fx.tst_DatCsv(fx.ary_("a", " ", "b"));
		fx.Clear();

		fx.run_parse_("a, ,b,\" \",c");
		fx.tst_DatCsv(fx.ary_("a", " ", "b", " ", "c"));
		fx.Clear();
	}
	@Test  public void CmdDlm_DoNotSpanLines() {
		fx.run_parse_lines_
			(	"a, "
			,	"\" \",b"
			);
		fx.tst_DatCsv
			(	fx.ary_("a", " ")
			,	fx.ary_(" ", "b")
			);
	}
	@Test  public void CmdDlm_SecondFldMustBeQuoted() {
		fx.run_parse_lines_("a, , ,b");	// will fail with "invalid command: b", if second , , is interpreted as command delimiter
		fx.tst_DatCsv(fx.ary_("a", " ", " ", "b"));
	}
	@Test  public void Null_Int() {
		fx.run_parse_	// not using run_parse_lines_ b/c (a) will have extra lineBreak; (b) test will look funny;
			(	"int," + StringClassXtn.Key_const + ", ,\" \",$", String_.CrLf
			,	",val1"
			);
		fx.tst_Tbls(DsvTblBldr.NullTblName);
		fx.tst_Flds(0, GfoFldList_.new_().Add("fld0", IntClassXtn.Instance).Add("fld1", StringClassXtn.Instance));
		fx.tst_Dat(0, fx.ary_(null, "val1"));
	}
	@Test  public void Null_String() {
		fx.run_parse_	// not using run_parse_lines_ b/c (a) will have extra lineBreak; (b) test will look funny;
			(	StringClassXtn.Key_const + "," + StringClassXtn.Key_const + ", ,\" \",$", String_.CrLf
			,	",val1"
			);
		fx.tst_Tbls(DsvTblBldr.NullTblName);
		fx.tst_Flds(0, GfoFldList_.new_().Add("fld0", StringClassXtn.Instance).Add("fld1", StringClassXtn.Instance));
		fx.tst_Dat(0, fx.ary_(null, "val1"));
	}
	@Test  public void EmptyString() {
		fx.run_parse_	// not using run_parse_lines_ b/c (a) will have extra lineBreak; (b) test will look funny;
			(	StringClassXtn.Key_const + "," + StringClassXtn.Key_const + ", ,\" \",$", String_.CrLf
			,	"\"\",val1"
			);
		fx.tst_Tbls(DsvTblBldr.NullTblName);
		fx.tst_Flds(0, GfoFldList_.new_().Add("fld0", StringClassXtn.Instance).Add("fld1", StringClassXtn.Instance));
		fx.tst_Dat(0, fx.ary_("", "val1"));
	}
}
