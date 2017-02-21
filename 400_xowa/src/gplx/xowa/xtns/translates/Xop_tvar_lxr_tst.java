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
package gplx.xowa.xtns.translates; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import org.junit.*;
	public class Xop_tvar_lxr_tst {
	@Before public void init() {fxt.Reset();} private final Xop_fxt fxt = new Xop_fxt();
	@Test  public void Basic() {
		fxt.Test_parse_page_all_str("<tvar|1>''a''</>", "<i>a</i>");
	}
	@Test  public void Missing_end() {
		fxt.Test_parse_page_all_str("<tvar|1>''a''</tvar>", "&lt;tvar|1&gt;<i>a</i>&lt;/tvar&gt;");
	}
	@Test  public void Templates() {
		fxt.Init_defn_add("A", "a");
		fxt.Test_parse_page_all_str("<tvar|1>{{A}}</>", "a");
	}
}
