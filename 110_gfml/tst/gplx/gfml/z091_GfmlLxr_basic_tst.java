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
import gplx.core.texts.*; /*CharStream*/
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
		List_adp list = List_adp_.New();
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
