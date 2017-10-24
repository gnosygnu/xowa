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
public class XomwLinkHolderArrayTest {
	private final    XomwLinkHolderArrayFxt fxt = new XomwLinkHolderArrayFxt();
	@Test   public void Replace__basic() {
		fxt.Init__add("A", "a");
		fxt.Test__replace("a <!--LINK 0--> b", "a <a href='/wiki/A' title='A'>a</a> b");
	}
}
class XomwLinkHolderArrayFxt {
	private final    XomwEnv env;
	private final    XomwLinkHolderArray holders;
	private final    XomwParserBfr pbfr = new XomwParserBfr();
	private boolean apos = true;
	public XomwLinkHolderArrayFxt() {
		XomwParser parser = new XomwParser(XomwEnv.NewTest());
		this.env = parser.Env();
		this.holders = new XomwLinkHolderArray(parser);
	}
	public void Init__add(String ttl, String capt) {
		holders.Test__add(XomwTitle.newFromText(env, Bry_.new_u8(ttl)), Bry_.new_u8(capt));
	}
	public void Test__replace(String src, String expd) {
		if (apos) expd = gplx.langs.htmls.Gfh_utl.Replace_apos(expd);
		holders.replace(pbfr.Init(Bry_.new_u8(src)));
		Gftest.Eq__str(expd, pbfr.Rslt().To_str_and_clear());
	}
}
