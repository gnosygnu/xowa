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
package gplx.xowa.xtns.wbases; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import org.junit.*; import gplx.core.tests.*;
import gplx.langs.jsons.*; import gplx.xowa.wikis.pages.*;
public class Wbase_doc_mgr__tst {
	@Before public void init() {fxt.Init();} private final    Wdata_wiki_mgr_fxt fxt = new Wdata_wiki_mgr_fxt();
	@Test  public void Redirect() {
		// create 2 pages; Q1 redirects to Q2
		Wdata_wiki_mgr wbase_mgr = fxt.App().Wiki_mgr().Wdata_mgr();
		fxt.Parser_fxt().Init_page_create(wbase_mgr.Wdata_wiki(), "Q1", Json_doc.Make_str_by_apos("{'entity':'q1','redirect':'Q2'}"));
		fxt.Parser_fxt().Init_page_create(wbase_mgr.Wdata_wiki(), "Q2", Json_doc.Make_str_by_apos("{'entity':'q2','links':{'enwiki':'q2_en','dewiki':'q2_de'}}"));

		// fetch Q1; assert Q2 comes back
		Wdata_doc actl = wbase_mgr.Doc_mgr.Load_wdoc_or_null(Bry_.new_u8("Q1"));
            Gftest.Eq__str("Q2", String_.new_u8(actl.Qid()));
	}
}
