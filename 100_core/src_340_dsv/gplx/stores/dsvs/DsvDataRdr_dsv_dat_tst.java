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
public class DsvDataRdr_dsv_dat_tst {
	@Before public void setup() {fx.Clear();} DsvDataRdr_fxt fx = DsvDataRdr_fxt.new_();
	@Test  public void NameOnly() {
		fx.run_parse_("tableName, ,\" \",#");
		fx.tst_Tbls("tableName");
		fx.tst_Dat(0);
	}
	@Test  public void Rows_N() {
		fx.run_parse_lines_
			(	"numbers, ,\" \",#"
			,	"1,2,3"
			,	"4,5,6"
			);
		fx.tst_Tbls("numbers");
		fx.tst_Dat(0
			,	fx.ary_("1", "2", "3")
			,	fx.ary_("4", "5", "6")
			);
	}
	@Test  public void Tbls_N() {
		fx.run_parse_lines_
			(	"letters, ,\" \",#"
			,	"a,b,c"
			,	"numbers, ,\" \",#"
			,	"1,2,3"
			,	"4,5,6"
			);
		fx.tst_Tbls("letters", "numbers");
		fx.tst_Dat(0, fx.ary_("a", "b", "c"));
		fx.tst_Dat(1, fx.ary_("1", "2", "3"), fx.ary_("4", "5", "6"));
	}
	@Test  public void IgnoreTrailingBlankRow() {
		fx.run_parse_lines_
			(	"letters, ,\" \",#"
			,	"a,b,c"
			,	""									// ignored
			);
		fx.tst_Tbls("letters");
		fx.tst_Dat(0, fx.ary_("a", "b", "c"));
	}
	@Test  public void AllowCommentsDuringData() {
		fx.run_parse_lines_
			(	"letters, ,\" \",#"
			,	"a,b,c"
			,	"//	letters omitted, ,\" \",//"		// these comments are not preserved
			,	"x,y,z"
			);
		fx.tst_Tbls("letters");
		fx.tst_Dat(0, fx.ary_("a", "b", "c"), fx.ary_("x", "y", "z"));
	}		
}
