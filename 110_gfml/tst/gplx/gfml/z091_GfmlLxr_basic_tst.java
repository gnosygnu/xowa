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
import gplx.texts.*; /*CharStream*/
public class z091_GfmlLxr_basic_tst {
	@Before public void setup() {
		rootLxr = GfmlLxr_.general_("gfml.root", GfmlTkn_.cmd_("tkn:text", GfmlBldrCmd_.Null));
	}	GfmlLxr rootLxr;
	@Test  public void Empty() {
		tst_Fetch("");
	}
	@Test  public void General() {
		tst_Fetch("text", "text");
	}
	@Test  public void Solo() {
		ini_AddSymbol(",");
		tst_Fetch(",", ",");
		tst_Fetch(",data0", ",", "data0");
		tst_Fetch("data0,", "data0", ",");
		tst_Fetch("data0,data1", "data0", ",", "data1");
	}
	@Test  public void Range() {
		ini_AddRange(" ", "\t");
		tst_Fetch(" ", " ");
		tst_Fetch(" a", " ", "a");
		tst_Fetch("\t ", "\t ");
		tst_Fetch("\ta ", "\t", "a", " ");
	}
	void ini_AddSymbol(String symbol) {
		GfmlTkn tkn = GfmlTkn_.singleton_("tkn", symbol, symbol, GfmlBldrCmd_.Null);
		GfmlLxr lxr = GfmlLxr_.solo_(symbol, tkn);
		rootLxr.SubLxr_Add(lxr);
	}
	void ini_AddRange(String... symbols) {
		GfmlTkn tkn = GfmlTkn_.cmd_("tkn", GfmlBldrCmd_.Null);
		GfmlLxr lxr = GfmlLxr_.range_("merge", symbols, tkn, false);
		rootLxr.SubLxr_Add(lxr);
	}
	GfmlTkn tst_Fetch(String raw, String... expd) {
		CharStream stream = CharStream.pos0_(raw);
		List_adp list = List_adp_.new_();
		GfmlTkn tkn = null;
		while (true) {
			tkn = rootLxr.MakeTkn(stream, 0);
			if (tkn == GfmlTkn_.EndOfStream) break;
			list.Add(tkn.Raw());
		}
		String[] actl = (String[])list.To_ary(String.class);
		Tfds.Eq_ary(expd, actl);
		return tkn;
	}
}
