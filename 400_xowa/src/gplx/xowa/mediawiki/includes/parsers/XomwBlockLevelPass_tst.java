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
package gplx.xowa.mediawiki.includes.parsers;
import gplx.frameworks.tests.GfoTstr;
import gplx.types.basics.utls.BryUtl;
import gplx.types.basics.utls.StringUtl;
import org.junit.*;
public class XomwBlockLevelPass_tst {
	private final XomwBlockLevelPass_fxt fxt = new XomwBlockLevelPass_fxt();
	@Test public void Basic() {
		fxt.Test__do_block_levels(StringUtl.ConcatLinesNlSkipLast
		( "a"
		), StringUtl.ConcatLinesNlSkipLast
		( "<p>a"
		, "</p>"
		));
	}
}
class XomwBlockLevelPass_fxt {
	private final XomwBlockLevelPass block_level_pass = new XomwBlockLevelPass();
	private final XomwParserCtx pctx = new XomwParserCtx();
	private final XomwParserBfr pbfr = new XomwParserBfr();
	private boolean apos = true;
	public void Test__do_block_levels(String src, String expd) {
		if (apos) expd = gplx.langs.htmls.Gfh_utl.Replace_apos(expd);
		block_level_pass.doBlockLevels(pctx, pbfr.Init(BryUtl.NewU8(src)), true);
		GfoTstr.Eq(expd, pbfr.Rslt().ToStrAndClear());
	}
}
