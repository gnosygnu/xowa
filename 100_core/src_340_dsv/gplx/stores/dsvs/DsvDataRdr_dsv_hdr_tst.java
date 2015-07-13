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
package gplx.stores.dsvs; import gplx.*; import gplx.stores.*;
import org.junit.*;
public class DsvDataRdr_dsv_hdr_tst {
	@Before public void setup() {fx.Clear();} DsvDataRdr_fxt fx = DsvDataRdr_fxt.new_();
	@Test  public void Names() {
		fx.run_parse_lines_
			(	"id,name, ,\" \",@"
			,	"0,me"
			,	"1,you"
			);
		fx.tst_Flds(0, GfoFldList_.str_("id", "name"));
		fx.tst_Tbls(DsvTblBldr.NullTblName);
		fx.tst_Dat(0
			,	fx.ary_("0", "me")
			,	fx.ary_("1", "you")
			);
	}
	@Test  public void Types() {
		fx.run_parse_lines_
			(	"int," + StringClassXtn.Key_const + ", ,\" \",$"
			,	"0,me"
			,	"1,you"
			);
		fx.tst_Flds(0, GfoFldList_.new_().Add("fld0", IntClassXtn._).Add("fld1", StringClassXtn._));
		fx.tst_Dat(0
			,	fx.ary_(0, "me")
			,	fx.ary_(1, "you")
			);
	}
	@Test  public void NamesAndTypes() {
		fx.run_parse_lines_
			(	"id,name, ,\" \",@"
			,	"int," + StringClassXtn.Key_const + ", ,\" \",$"
			,	"0,me"
			,	"1,you"
			);
		fx.tst_Flds(0, GfoFldList_.new_().Add("id", IntClassXtn._).Add("name", StringClassXtn._));
		fx.tst_Dat(0
			,	fx.ary_(0, "me")
			,	fx.ary_(1, "you")
			);
	}
	@Test  public void MultipleTables_NoData() {
		fx.run_parse_lines_
			(	"persons, ,\" \",#"
			,	"id,name, ,\" \",@"
			,	"things, ,\" \",#"
			,	"id,data, ,\" \",@"
			);
		fx.tst_Tbls("persons", "things");
		fx.tst_Flds(0, GfoFldList_.str_("id", "name"));
		fx.tst_Flds(1, GfoFldList_.str_("id", "data"));
		fx.tst_Dat(0);
		fx.tst_Dat(1);
	}
	@Test  public void Comment() {
		fx.run_parse_lines_
			(	"--------------------, ,\" \",//"
			,	"tbl0, ,\" \",#"
			,	"a0,a1"
			);
		fx.tst_Tbls("tbl0");
		fx.tst_Dat(0, fx.ary_("a0", "a1"));
	}
}
