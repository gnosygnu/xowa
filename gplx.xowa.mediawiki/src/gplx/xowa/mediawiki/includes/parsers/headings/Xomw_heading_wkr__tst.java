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
package gplx.xowa.mediawiki.includes.parsers.headings; import gplx.*; import gplx.xowa.*; import gplx.xowa.mediawiki.*; import gplx.xowa.mediawiki.includes.*; import gplx.xowa.mediawiki.includes.parsers.*;
import org.junit.*;
public class Xomw_heading_wkr__tst {
	private final    Xomw_heading_wkr__fxt fxt = new Xomw_heading_wkr__fxt();
	@Test  public void Basic()		{
		fxt.Test__parse("==A=="					, "<h2>A</h2>");
		fxt.Test__parse("abc\n==A==\ndef"		, "abc\n<h2>A</h2>\ndef");

		fxt.Test__parse("abc"					, "abc");
		fxt.Test__parse("abc\ndef"				, "abc\ndef");
		fxt.Test__parse("abc\n=="				, "abc\n<h1></h1>");
	}
}
class Xomw_heading_wkr__fxt {
	private final    Xomw_heading_wkr wkr = new Xomw_heading_wkr();
	private final    Xomw_heading_cbk__html cbk = new Xomw_heading_cbk__html().Bfr_(Bry_bfr_.New());
	private final    XomwParserCtx pctx = new XomwParserCtx();
	
	public void Test__parse(String src_str, String expd) {
		byte[] src_bry = Bry_.new_u8(src_str);
		wkr.Parse(pctx, src_bry, -1, src_bry.length, cbk);
		Tfds.Eq_str_lines(expd, cbk.Bfr().To_str_and_clear(), src_str);
	}
}
