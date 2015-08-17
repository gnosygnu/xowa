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
public class z081_GfmlDataWtr_tst {
	@Before public void setup() {
		wtr = GfmlDataWtr.new_();
		wtr.WriteNodeBgn("root");
	}	DataWtr wtr;
	@Test  public void Basic() {
		tst_XtoStr(wtr, "root:;");
	}
	@Test  public void Atr_one() {
		wtr.WriteData("key", "data");;
		tst_XtoStr(wtr, "root:key='data';");
	}
	@Test  public void Atr_many() {
		wtr.WriteData("key1", "data1");
		wtr.WriteData("key2", "data2");
		tst_XtoStr(wtr, "root:key1='data1' key2='data2';");
	}
	@Test  public void Nde_one() {
		wtr.WriteNodeBgn("sub0");
		tst_XtoStr(wtr, "root:{sub0:;}");
	}
	@Test  public void Nde_many() {
		wtr.WriteNodeBgn("sub0");
		wtr.WriteNodeEnd();
		wtr.WriteNodeBgn("sub1");
		tst_XtoStr(wtr, "root:{sub0:;sub1:;}");
	}
	@Test  public void Nde_nested() {
		wtr.WriteNodeBgn("sub0");
		wtr.WriteNodeBgn("sub1");
		tst_XtoStr(wtr, "root:{sub0:{sub1:;}}");
	}
	@Test  public void OneAtrOneNde() {
		wtr.WriteData("key1", "data1");
		wtr.WriteNodeBgn("sub0");
		tst_XtoStr(wtr, "root:key1='data1'{sub0:;}");
	}
	@Test  public void OneAtrOneNdeOneAtr() {
		wtr.WriteData("key1", "data1");
		wtr.WriteNodeBgn("sub0");
		wtr.WriteData("key2", "data2");
		tst_XtoStr(wtr, "root:key1='data1'{sub0:key2='data2';}");
	}
	@Test  public void EscapeQuote() {
		wtr.WriteData("key", "data's");;
		tst_XtoStr(wtr, "root:key='data''s';");
	}
	void tst_XtoStr(DataWtr wtr, String expd) {
		String actl = wtr.To_str();
		Tfds.Eq(expd, actl);
	}
}
