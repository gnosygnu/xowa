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
package gplx.xowa.mediawiki.includes.parsers.doubleunders;
import gplx.frameworks.tests.GfoTstr;
import gplx.types.basics.utls.BryUtl;
import gplx.types.basics.utls.BoolUtl;
import gplx.xowa.Xoa_app_fxt;
import gplx.xowa.Xoae_app;
import gplx.xowa.Xowe_wiki;
import gplx.xowa.mediawiki.includes.parsers.XomwParserBfr;
import gplx.xowa.mediawiki.includes.parsers.XomwParserCtx;
import org.junit.Test;
public class Xomw_doubleunder_wkr__tst {
	private final Xomw_doubleunder_wkr__fxt fxt = new Xomw_doubleunder_wkr__fxt();
	@Test public void No_match()        {fxt.Test__parse("a b c"                                    , "a b c");}
	@Test public void Force_toc()       {fxt.Test__parse("a __FORCETOC__ b"                         , "a  b").Test__prop_y(fxt.data.force_toc);}
	@Test public void Toc()             {fxt.Test__parse("a __TOC__ b __TOC__ c"                    , "a <!--MWTOC--> b  c").Test__prop_y(fxt.data.toc, fxt.data.show_toc, fxt.data.force_toc_position);}
	@Test public void Notoc_only()      {fxt.Test__parse("a __NOTOC__ b"                            , "a  b").Test__prop_y(fxt.data.no_toc).Test__prop_n(fxt.data.show_toc);}	// show_toc is false
	@Test public void Notoc_w_toc()     {fxt.Test__parse("a __TOC__ b __NOTOC__ c"                  , "a <!--MWTOC--> b  c").Test__prop_y(fxt.data.toc, fxt.data.show_toc, fxt.data.force_toc_position);} // show_toc is true
	@Test public void Case_match()      {fxt.Test__parse("a __index__ b"                            , "a __index__ b");}
}
class Xomw_doubleunder_wkr__fxt {
	private final XomwParserCtx pctx = new XomwParserCtx();
	private final XomwParserBfr pbfr = new XomwParserBfr();
	private final Xomw_doubleunder_wkr wkr = new Xomw_doubleunder_wkr();
	public Xomw_doubleunder_data data = new Xomw_doubleunder_data();
	public Xomw_doubleunder_wkr__fxt() {
		Xoae_app app = Xoa_app_fxt.Make__app__edit();
		Xowe_wiki wiki = Xoa_app_fxt.Make__wiki__edit(app);
		wkr.Init_by_wiki(data, wiki.Lang());
	}
	public Xomw_doubleunder_wkr__fxt Test__parse(String src_str, String expd) {
		byte[] src_bry = BryUtl.NewU8(src_str);
		wkr.doDoubleUnderscore(pctx, pbfr.Init(src_bry));
		GfoTstr.Eq(expd, pbfr.Rslt().ToStrAndClear(), src_str);
		return this;
	}
	public  Xomw_doubleunder_wkr__fxt Test__prop_y(boolean... ary) {return Test__prop(BoolUtl.Y, ary);}
	public  Xomw_doubleunder_wkr__fxt Test__prop_n(boolean... ary) {return Test__prop(BoolUtl.N, ary);}
	private Xomw_doubleunder_wkr__fxt Test__prop(boolean expd, boolean... ary) {
		for (boolean v : ary)
			GfoTstr.Eq(expd, v);
		return this;
	}
}
