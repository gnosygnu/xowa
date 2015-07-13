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
package gplx.gfml; import gplx.*;
import org.junit.*;
public class z082_GfmlDataWtrOpts_tst {
	@Before public void setup() {
		wtr = GfmlDataWtr.new_();
		wtr.WriteNodeBgn("root");
	}	DataWtr wtr;
	@Test  public void KeyedSpr() {
		wtr.InitWtr(GfmlDataWtrOpts.Key_const, GfmlDataWtrOpts.new_().KeyedSeparator_("\t"));
		wtr.WriteData("key1", "data1");
		wtr.WriteData("key2", "data2");
		tst_XtoStr(wtr, "root:key1='data1'\tkey2='data2';");
	}
	@Test  public void IndentNamesOn() {
		wtr.InitWtr(GfmlDataWtrOpts.Key_const, GfmlDataWtrOpts.new_().IndentNodesOn_());
		wtr.WriteNodeBgn("nde1");
		wtr.WriteNodeBgn("nde2");
		wtr.WriteNodeEnd();
		tst_XtoStr(wtr, String_.Concat_lines_crlf
			(	"root:{"
			,	"	nde1:{"
			,	"		nde2:;"
			,	"	}"
			,	"}"
			));
	}
	@Test  public void IgnoreNullNamesOn() {
		wtr.InitWtr(GfmlDataWtrOpts.Key_const, GfmlDataWtrOpts.new_().IgnoreNullNamesOn_());
		wtr.WriteNodeBgn("");
		wtr.WriteData("key1", "data1");
		tst_XtoStr(wtr, String_.Concat("root:{key1='data1';}"));			
	}
	void tst_XtoStr(DataWtr wtr, String expd) {
		String actl = wtr.XtoStr();
		Tfds.Eq(expd, actl);
	}
}
