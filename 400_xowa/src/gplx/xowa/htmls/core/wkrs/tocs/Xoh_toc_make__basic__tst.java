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
package gplx.xowa.htmls.core.wkrs.tocs; import gplx.*;
import gplx.core.envs.Op_sys;
import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.wkrs.*;
import org.junit.*; import gplx.xowa.htmls.core.makes.tests.*; import gplx.xowa.parsers.lnkis.*;
public class Xoh_toc_make__basic__tst {
	private final    Xoh_make_fxt fxt = new Xoh_make_fxt();
	private final    String orig = String_.Concat_lines_nl_skip_last
	( "abc"
	, "<div class=\"xo-toc\"></div>"
	, "def"
	, "<h2><span class='mw-headline' id='A'>A</span></h2>"
	, "a 1"
	, "<h2><span class='mw-headline' id='B'>B</span></h2>"
	, "b 1"
	);
	@Before public void Init() {fxt.Clear();}
	@Test  public void Enabled() {
		String expd = String_.Concat_lines_nl_skip_last
		( "abc"
		, "<div id='toc' class='toc'>"
		, "  <div id='toctitle' class='toctitle'>"
		, "    <h2>Contents</h2>"
		, "  </div>"
		, "  <ul>"
		, "    <li class='toclevel-1 tocsection-1'><a href='#A'><span class='tocnumber'>1</span> <span class='toctext'>A</span></a>"
		, "    </li>"
		, "    <li class='toclevel-1 tocsection-2'><a href='#B'><span class='tocnumber'>2</span> <span class='toctext'>B</span></a>"
		, "    </li>"
		, "  </ul>"
		, "</div>"
		, ""
		, "def"
		, "<h2><span class='mw-headline' id='A'>A</span></h2>"
		, "a 1"
		, "<h2><span class='mw-headline' id='B'>B</span></h2>"
		, "b 1"
		);
		fxt.Test__make(orig, fxt.Page_chkr().Body_(expd));
	}
	@Test  public void Disabled_if_drd() {
		int curTid = Op_sys.Cur().Tid();
		gplx.core.envs.Op_sys.Cur_(gplx.core.envs.Op_sys.Tid_drd);
		String expd = String_.Concat_lines_nl_skip_last
		( "abc"
		, ""
		, "def"
		, "<h2><span class='mw-headline' id='A'>A</span></h2>"
		, "a 1"
		, "<h2><span class='mw-headline' id='B'>B</span></h2>"
		, "b 1"
		);
		fxt.Test__make(orig, fxt.Page_chkr().Body_(expd));
		Op_sys.Cur_(curTid);
	}
}
