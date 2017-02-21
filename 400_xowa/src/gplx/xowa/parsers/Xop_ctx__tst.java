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
package gplx.xowa.parsers; import gplx.*; import gplx.xowa.*;
import org.junit.*;
public class Xop_ctx__tst {
	@Before public void init() {fxt.Clear();} private Xop_ctx__fxt fxt = new Xop_ctx__fxt();
	@Test  public void Src_limit_and_escape_nl() {
		fxt.Test_Src_limit_and_escape_nl("abcdefg", 4, 3, "efg");	// PURPOSE: bug fix; outOfBounds thrown; DATE:2014-03-31
	}
}
class Xop_ctx__fxt {
	public void Clear() {
	}
	public void Test_Src_limit_and_escape_nl(String src, int bgn, int limit, String expd) {
		String actl = Xop_ctx_.Src_limit_and_escape_nl(Bry_.new_u8(src), bgn, limit);
		Tfds.Eq(expd, actl);
	}
}
