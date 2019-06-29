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
package gplx.xowa.mediawiki.includes.parsers.nbsps; import gplx.*; import gplx.xowa.*; import gplx.xowa.mediawiki.*; import gplx.xowa.mediawiki.includes.*; import gplx.xowa.mediawiki.includes.parsers.*;
import org.junit.*;
public class Xomw_nbsp_wkr__tst {
	private final    Xomw_nbsp_wkr__fxt fxt = new Xomw_nbsp_wkr__fxt();
	@Test   public void Noop()                             {fxt.Test__parse("abc"                         , "abc");}
	@Test   public void Space_lhs__colon()                 {fxt.Test__parse("a :b c"                      , "a&#160;:b c");}
	@Test   public void Space_lhs__laquo()                 {fxt.Test__parse("a »b c"                      , "a&#160;»b c");}
	@Test   public void Space_rhs()                        {fxt.Test__parse("a« b c"                      , "a«&#160;b c");}
	@Test   public void Important()                        {fxt.Test__parse("a &#160;! important b"       , "a  ! important b");}
}
class Xomw_nbsp_wkr__fxt {
	private final    Xomw_nbsp_wkr wkr = new Xomw_nbsp_wkr();
	private final    XomwParserCtx pctx = new XomwParserCtx();
	private final    XomwParserBfr pbfr = new XomwParserBfr();
	private boolean apos = true;
	public void Test__parse(String src_str, String expd) {
		byte[] src_bry = Bry_.new_u8(src_str);
		pbfr.Init(src_bry);
		wkr.doNbsp(pctx, pbfr);
		if (apos) expd = gplx.langs.htmls.Gfh_utl.Replace_apos(expd);
		Tfds.Eq_str_lines(expd, pbfr.Rslt().To_str_and_clear(), src_str);
	}
}
