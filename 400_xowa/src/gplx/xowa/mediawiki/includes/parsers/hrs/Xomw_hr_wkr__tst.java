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
package gplx.xowa.mediawiki.includes.parsers.hrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.mediawiki.*; import gplx.xowa.mediawiki.includes.*; import gplx.xowa.mediawiki.includes.parsers.*;
import org.junit.*;
public class Xomw_hr_wkr__tst {
	private final    Xomw_hr_wkr__fxt fxt = new Xomw_hr_wkr__fxt();
	@Test  public void Basic()       {fxt.Test__parse("a\n-----b"                         , "a\n<hr />b");}
	@Test  public void Extend()      {fxt.Test__parse("a\n------b"                        , "a\n<hr />b");}
	@Test  public void Not_found()   {fxt.Test__parse("a\n----b"                          , "a\n----b");}
	@Test  public void Bos()         {fxt.Test__parse("-----a"                            , "<hr />a");}
	@Test  public void Bos_and_mid() {fxt.Test__parse("-----a\n-----b"                    , "<hr />a\n<hr />b");}
}
class Xomw_hr_wkr__fxt {
	private final    XomwParserBfr pbfr = new XomwParserBfr();
	private final    Xomw_hr_wkr wkr = new Xomw_hr_wkr();
	public void Test__parse(String src_str, String expd) {
		byte[] src_bry = Bry_.new_u8(src_str);
		wkr.replaceHrs(new XomwParserCtx(), pbfr.Init(src_bry));
		Tfds.Eq_str_lines(expd, pbfr.Rslt().To_str_and_clear(), src_str);
	}
}
