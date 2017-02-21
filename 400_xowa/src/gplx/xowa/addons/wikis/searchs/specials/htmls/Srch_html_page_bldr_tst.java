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
package gplx.xowa.addons.wikis.searchs.specials.htmls; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.searchs.*; import gplx.xowa.addons.wikis.searchs.specials.*;
import org.junit.*; import gplx.xowa.htmls.core.htmls.utls.*; import gplx.xowa.wikis.tdbs.*;
import gplx.xowa.wikis.domains.*;
import gplx.xowa.wikis.data.tbls.*;
import gplx.xowa.addons.wikis.searchs.searchers.*; import gplx.xowa.addons.wikis.searchs.searchers.rslts.*;
public class Srch_html_page_bldr_tst {
	@Before public void init() {fxt.Clear();} private Srch_html_page_bldr_fxt fxt = new Srch_html_page_bldr_fxt();
	@Test   public void Paging() {
		fxt.Test_paging(Bool_.Y, 1, "<a href='/site/en.wikipedia.org/wiki/Special:Search/A?fulltext=y&xowa_page_index=2' title='Next'>Next<img src='file:///mem/xowa/bin/any/xowa/file/app.general/go_fwd.png' width='16' height='16'/></a>");
		fxt.Test_paging(Bool_.N, 1, "<a href='/site/en.wikipedia.org/wiki/Special:Search/A?fulltext=y&xowa_page_index=0' title='Previous'><img src='file:///mem/xowa/bin/any/xowa/file/app.general/go_bwd.png' width='16' height='16'/>Previous</a>");
		fxt.Test_paging(Bool_.Y, 2, "<a href='/site/en.wikipedia.org/wiki/Special:Search/A?fulltext=y&xowa_page_index=3' title='Next'>Next<img src='file:///mem/xowa/bin/any/xowa/file/app.general/go_fwd.png' width='16' height='16'/></a>");
		fxt.Test_paging(Bool_.N, 0, "&#160;");
	}
	@Test   public void Rows() {
		fxt.Test_rows(new Srch_rslt_row[] {fxt.Make_row(10, "A"), fxt.Make_row(20, "B")}, String_.Concat_lines_nl_skip_last
		( ""
		, "  <tr id='w.7C1'>"
		, "    <td style='padding-right:5px; vertical-align:top; text-align:right;'>10"
		, "    </td>"
		, "    <td style='padding-left:5px; vertical-align:top;'><a href='/site/w/wiki/A' title='A'>A</a>"
		, "    </td>"
		, "  </tr>"
		, "  <tr id='w.7C2'>"
		, "    <td style='padding-right:5px; vertical-align:top; text-align:right;'>20"
		, "    </td>"
		, "    <td style='padding-left:5px; vertical-align:top;'><a href='/site/w/wiki/B' title='B'>B</a>"
		, "    </td>"
		, "  </tr>"
		));
	}
}
class Srch_html_page_bldr_fxt {
	private Xoae_app app; private Xowe_wiki wiki; private Srch_html_page_bldr html_mgr; private final    Bry_bfr tmp_bfr = Bry_bfr_.New_w_size(255);
	private int page_id;
	public Srch_html_page_bldr_fxt Clear() {
		if (app == null) {
			app = Xoa_app_fxt.Make__app__edit();
			wiki = Xoa_app_fxt.Make__wiki__edit(app);
			html_mgr = new Srch_html_page_bldr();
		}
		page_id = 0;
		return this;
	}
	public void Test_paging(boolean fwd, int slab_idx, String expd) {
		byte[] search_orig = Bry_.new_a7("A");
		Srch_search_qry qry = Srch_search_qry.New__search_page(Xow_domain_itm_.Ary_empty, wiki, app.Addon_mgr().Itms__search__special().Ns_mgr(), Bool_.N, search_orig, slab_idx, 100);
		html_mgr.Init_by_wiki(wiki, wiki.Lang().Num_mgr(), qry);
		byte[] paging_link = html_mgr.Bld_paging_link(fwd);
		Tfds.Eq(expd, String_.new_a7(paging_link));
	}
	public void Test_rows(Srch_rslt_row[] rows, String expd) {
		Srch_rslt_list rslts = new Srch_rslt_list();
		Srch_html_row_bldr row_bldr = new Srch_html_row_bldr(wiki.Html__lnki_bldr());
		row_bldr.Init(rslts, 0, rows.length);
		for (int i = 0; i < rows.length; ++i)
			rslts.Add(rows[i]);
		row_bldr.Bfr_arg__add(tmp_bfr);
		Tfds.Eq_str_lines(expd, tmp_bfr.To_str_and_clear());
	}
	public Srch_rslt_row Make_row(int len, String ttl_str) {
		byte[] wiki_bry = Bry_.new_a7("w");
		byte[] ttl_bry = Bry_.new_u8(ttl_str);
		++page_id;
		Srch_rslt_row rv = new Srch_rslt_row(Srch_rslt_row.Bld_key(wiki_bry, page_id), wiki_bry, wiki.Ttl_parse(ttl_bry), gplx.xowa.wikis.nss.Xow_ns_.Tid__main, ttl_bry, page_id, len, len, Srch_rslt_row.Page_redirect_id_null);
		rv.Page_ttl_highlight = rv.Page_ttl.Full_txt_w_ttl_case();
		return rv;
	}
}
