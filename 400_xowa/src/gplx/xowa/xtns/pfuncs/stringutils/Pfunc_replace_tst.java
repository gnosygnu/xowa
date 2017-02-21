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
public class Pfunc_replace_tst {
	@Before public void init()						{fxt.Reset();} private final Xop_fxt fxt = Xop_fxt.new_nonwmf();
	@Test   public void Basic()						{fxt.Test_parse_template("{{#replace:abc|b|z}}"				, "azc");}
	@Test   public void Replace_all()				{fxt.Test_parse_template("{{#replace:aaa|a|b}}"				, "bbb");}
	@Test   public void Limit()						{fxt.Test_parse_template("{{#replace:aaa|a|b|2}}"			, "bba");}
	@Test   public void Find_defaults_to_space()	{fxt.Test_parse_template("{{#replace:a b c||_}}"			, "a_b_c");}
	@Test   public void Not_found()					{fxt.Test_parse_template("{{#replace:aaa|b|c}}"				, "aaa");}
}
