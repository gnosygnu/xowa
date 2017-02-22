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
package gplx.xowa.xtns.pfuncs.stringutils; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.pfuncs.*;
import org.junit.*;
public class Pfunc_rpos_tst {
	@Before public void init()						{fxt.Reset();} private final Xop_fxt fxt = Xop_fxt.new_nonwmf();
	@Test   public void Basic()						{fxt.Test_parse_template("{{#rpos:abcabc|b}}"				, "4");}
	@Test   public void Index()						{fxt.Test_parse_template("{{#rpos:abcabc|b|3}}"				, "1");}
	@Test   public void Missing()					{fxt.Test_parse_template("{{#rpos:abcabc|z}}"				, "-1");}
	@Test   public void Find_missing()				{fxt.Test_parse_template("{{#rpos:abc def}}"				, "3");}
}
