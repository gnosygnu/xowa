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
package gplx.xowa.xtns.pfuncs.langs; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.pfuncs.*;
import org.junit.*;
public class Pfunc_plural_tst {
	private final Xop_fxt fxt = new Xop_fxt();
	@Before public void init()					{fxt.Reset();}
	@Test  public void Singular()				{fxt.Test_parse_tmpl_str_test("{{plural:1|wiki|wikis}}"				, "{{test}}"	, "wiki");}
	@Test  public void Plural()					{fxt.Test_parse_tmpl_str_test("{{plural:2|wiki|wikis}}"				, "{{test}}"	, "wikis");}
	@Test  public void Plural_but_one_arg()		{fxt.Test_parse_tmpl_str_test("{{plural:2|wiki}}"					, "{{test}}"	, "wiki");}
	@Test  public void Null()					{fxt.Test_parse_tmpl_str_test("{{plural:|wiki|wikis}}"				, "{{test}}"	, "wikis");}
}
