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
package gplx.xowa.wikis.ttls; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*;
import org.junit.*;
public class Xow_ttl__qarg_tst {
	@Before public void init() {fxt.Reset();} private Xow_ttl_fxt fxt = new Xow_ttl_fxt();
	@Test   public void Base_txt_wo_qarg() {
		fxt.Init_ttl("Special:Search/A?b=c").Expd_base_txt_wo_qarg("Search").Expd_qarg_txt("b=c").Test();
	}
	@Test   public void Leaf_txt_wo_qarg() {
		fxt.Init_ttl("Special:Search/A?b=c").Expd_leaf_txt_wo_qarg("A").Expd_qarg_txt("b=c").Test();
	}
	@Test   public void Ttl_has_question_mark() {	// PURPOSE: handle titles that have both question mark and leaf; PAGE:en.w:Portal:Organized_Labour/Did_You_Know?/1 DATE:2014-06-08
		fxt.Init_ttl("A/B?/1").Expd_page_txt("A/B?/1").Expd_base_txt("A/B?").Expd_leaf_txt("1").Expd_leaf_txt_wo_qarg("1").Expd_qarg_txt(null).Test();
	}
}
