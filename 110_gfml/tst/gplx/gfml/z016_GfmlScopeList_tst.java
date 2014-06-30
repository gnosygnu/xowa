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
public class z016_GfmlScopeList_tst {
	@Before public void setup() {
		list = GfmlScopeList.new_("test");
	}	GfmlScopeList list;
	@Test public void None() {
		tst_Itm(list, GfmlDocPos_.Root, null);
	}
	@Test public void One() {
		run_Add(list, var_("val1"));
		tst_Itm(list, GfmlDocPos_.Root, "val1");
	}
	@Test public void ByPos() {
		run_Add(list, var_("val1").DocPos_(docPos_(0, 0)));
		run_Add(list, var_("val2").DocPos_(docPos_(0, 0, 0)));
		tst_Itm(list, docPos_(0, 0), "val1");
		tst_Itm(list, docPos_(0, 0, 0), "val2");
		tst_Itm(list, docPos_(0, 1), "val1");
		tst_Itm(list, docPos_(0), null);
	}
	GfmlVarItm var_(String val) {return GfmlVarItm.new_("key", GfmlTkn_.raw_(val), GfmlVarCtx_.DefaultKey);}
	GfmlDocPos docPos_(int... ary) {
		int last = ary.length - 1;
		int idx = ary[last];
		int[] levels = (int[])Array_.Resize(ary, last);
		return new GfmlDocPos(levels, idx);
	}
	void run_Add(GfmlScopeList list, GfmlScopeItm... ary) {
		for (GfmlScopeItm itm : ary)
			list.Add(itm);
	}
	void tst_Itm(GfmlScopeList list, GfmlDocPos pos, String expd) {
		GfmlVarItm itm = (GfmlVarItm)list.Fetch(pos);
		String actl = itm == null ? null : itm.TknVal();
		Tfds.Eq(expd, actl);
	}
}
