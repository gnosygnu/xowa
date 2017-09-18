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
package gplx.xowa.xtns.wbases.pfuncs; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.wbases.*;
import org.junit.*; import gplx.xowa.parsers.*; import gplx.xowa.parsers.tmpls.*;
public class Wdata_pf_statements__basic__tst {
	@Before public void init() {fxt.Init();} private final    Wdata_wiki_mgr_fxt fxt = new Wdata_wiki_mgr_fxt();
	@Test   public void String() {
		fxt.Init_links_add("enwiki", "Test_page", "q1");
		fxt.Init__docs__add(fxt.doc_("q1", fxt.Make_claim_string(1, "a")));
		fxt.Test_parse("{{#statements:p1}}", "a");
	}
	@Test   public void Entity() {
		fxt.Init_links_add("enwiki", "Test_page", "q1");
		fxt.Init__docs__add(fxt.Wdoc_bldr("q2").Add_label("en", "b").Xto_wdoc());
		fxt.Init__docs__add(fxt.doc_("q1", fxt.Make_claim_entity_qid(1, 2)));
		fxt.Test_parse("{{#statements:p1}}", "[[b]]");
	}
}
