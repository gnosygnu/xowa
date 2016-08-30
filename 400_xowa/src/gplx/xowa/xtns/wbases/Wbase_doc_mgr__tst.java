/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012 gnosygnu@gmail.com

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
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
