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
package gplx.xowa.parsers.miscs; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import org.junit.*;
public class Xop_space_lxr_tst {
	private final Xop_fxt fxt = new Xop_fxt();
	@Before public void init() {fxt.Reset();}
	@After public void term() {fxt.Init_para_n_();}
	@Test   public void Toc_basic() {	// PURPOSE: make sure nbsp char is not converted to space; PAGE:en.w:Macedonian–Carthaginian_Treaty; DATE:2014-06-07
		fxt.Init_para_y_();
		fxt.Test_parse_page_all_str("     a", String_.Concat_lines_nl_skip_last	// NOTE: ws is actually nbsp;
		( "<p>     a"	// should be <p> not <pre>
		, "</p>"
		, ""
		));	
	}
}
