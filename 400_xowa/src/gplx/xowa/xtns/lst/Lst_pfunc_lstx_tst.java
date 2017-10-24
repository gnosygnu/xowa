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
package gplx.xowa.xtns.lst; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import org.junit.*;
public class Lst_pfunc_lstx_tst {
	private Lst_pfunc_lst_fxt fxt = new Lst_pfunc_lst_fxt();
	@Before public void init() {fxt.Clear();}
	@Test  public void Basic() {
		fxt.Page_txt_("a<section begin=key0/> val0<section end=key0/> b<section begin=key1/> val1<section end=key1/> c").Test_lst("{{#lstx:section_test|key0}}", "a b val1 c");
	}
	@Test  public void Replace() {
		fxt.Page_txt_("a<section begin=key0/> val0<section end=key0/> b<section begin=key1/> val1<section end=key1/> c").Test_lst("{{#lstx:section_test|key0|val3}}", "aval3 b val1 c");
	}
	@Test  public void Section_is_empty() {
		fxt.Page_txt_("a<section begin=key0/> val0<section end=key0/> b<section begin=key1/> val1<section end=key1/> c").Test_lst("{{#lstx:section_test|}}", "a val0 b val1 c");
	}
	@Test  public void Missing_bgn_end() {
		fxt.Page_txt_("a<section begin=key0/> b<section end=key0/> c").Test_lst("{{#lstx:section_test}}", "a b c");
	}
}
