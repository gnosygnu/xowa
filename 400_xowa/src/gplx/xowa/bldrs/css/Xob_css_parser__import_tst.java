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
package gplx.xowa.bldrs.css; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*;
import org.junit.*;
public class Xob_css_parser__import_tst {
	@Before public void init() {fxt.Clear();} private Xob_css_parser__import_fxt fxt = new Xob_css_parser__import_fxt();
	@Test   public void Basic()				{fxt.Test_parse_import	(" @import url(//site/a.png)"	, " @import url('site/a.png')");}
	@Test   public void Warn_eos()			{fxt.Test_parse_warn	(" @import"						, " @import"				, "EOS");}
	@Test   public void Warn_missing()		{fxt.Test_parse_warn	(" @import ('//site/a.png')"	, " @import"				, "missing");}	// no "url("
	@Test   public void Warn_invalid()		{fxt.Test_parse_warn	(" @import url('//site')"		, " @import url('//site')"	, "invalid");}	// invalid
}
class Xob_css_parser__import_fxt extends Xob_css_parser__url_fxt {		private Xob_css_parser__import import_parser;
	@Override public void Clear() {
		super.Clear();
		this.import_parser = new Xob_css_parser__import(url_parser);
	}
	@Override protected void Exec_parse_hook() {
		this.cur_frag = import_parser.Parse(src_bry, src_bry.length, 0, 8); // 8=" @import".length
	}
	public void Test_parse_import(String src_str, String expd) {
		Exec_parse(src_str, Xob_css_tkn__base.Tid_import, expd);
	}
}	
