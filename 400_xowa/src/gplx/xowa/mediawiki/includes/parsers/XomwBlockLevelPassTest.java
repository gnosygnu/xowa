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
package gplx.xowa.mediawiki.includes.parsers; import gplx.*; import gplx.xowa.*; import gplx.xowa.mediawiki.*; import gplx.xowa.mediawiki.includes.*;
import org.junit.*; import gplx.core.tests.*;
import gplx.xowa.mediawiki.includes.linkers.*;
public class XomwBlockLevelPassTest {
	private final    XomwBlockLevelPassFxt fxt = new XomwBlockLevelPassFxt();
	@Test   public void Basic() {
		fxt.Test__do_block_levels(String_.Concat_lines_nl_skip_last
		( "a"
		), String_.Concat_lines_nl_skip_last
		( "<p>a"
		, "</p>"
		));
	}
}
class XomwBlockLevelPassFxt {
	private final    XomwBlockLevelPass block_level_pass = new XomwBlockLevelPass();
	private final    XomwParserCtx pctx = new XomwParserCtx();
	private final    XomwParserBfr pbfr = new XomwParserBfr();
	private boolean apos = true;
	public void Test__do_block_levels(String src, String expd) {
		if (apos) expd = gplx.langs.htmls.Gfh_utl.Replace_apos(expd);
		block_level_pass.doBlockLevels(pctx, pbfr.Init(Bry_.new_u8(src)), true);
		Gftest.Eq__str(expd, pbfr.Rslt().To_str_and_clear());
	}
}
