/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2017 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.gfml; import gplx.*;
import org.junit.*;
public class z016_GfmlScopeList_tst {
	@Before public void setup() {
		list = GfmlScopeList.new_("test");
	}	GfmlScopeList list;
	@Test  public void None() {
		tst_Itm(list, GfmlDocPos_.Root, null);
	}
	@Test  public void One() {
		run_Add(list, var_("val1"));
		tst_Itm(list, GfmlDocPos_.Root, "val1");
	}
	@Test  public void ByPos() {
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
		GfmlVarItm itm = (GfmlVarItm)list.Get_by(pos);
		String actl = itm == null ? null : itm.TknVal();
		Tfds.Eq(expd, actl);
	}
}
