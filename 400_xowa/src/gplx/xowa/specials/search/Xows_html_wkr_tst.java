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
package gplx.xowa.specials.search; import gplx.*; import gplx.xowa.*; import gplx.xowa.specials.*;
import org.junit.*; import gplx.xowa.html.wtrs.*; import gplx.xowa.tdbs.*;
import gplx.xowa.wikis.domains.*;
import gplx.xowa.wikis.data.tbls.*;
public class Xows_html_wkr_tst {
	@Before public void init() {fxt.Clear();} private Xows_html_wkr_fxt fxt = new Xows_html_wkr_fxt();
	@Test   public void Paging() {
		fxt.Test_paging(Bool_.Y, 1, "<a href='/site/en.wikipedia.org/wiki/Special:Search/A%3Ffulltext%3Dy%26xowa_page_index%3D2' title='Next'>Next<img src='file:///mem/xowa/user/anonymous/app/img/window/paging/go_fwd.png' width='16' height='16'/></a>");
		fxt.Test_paging(Bool_.N, 1, "<a href='/site/en.wikipedia.org/wiki/Special:Search/A%3Ffulltext%3Dy%26xowa_page_index%3D0' title='Previous'><img src='file:///mem/xowa/user/anonymous/app/img/window/paging/go_bwd.png' width='16' height='16'/>Previous</a>");
		fxt.Test_paging(Bool_.Y, 2, "<a href='/site/en.wikipedia.org/wiki/Special:Search/A%3Ffulltext%3Dy%26xowa_page_index%3D3' title='Next'>Next<img src='file:///mem/xowa/user/anonymous/app/img/window/paging/go_fwd.png' width='16' height='16'/></a>");
		fxt.Test_paging(Bool_.N, 0, "&#160;");
	}
	@Test   public void Rows() {
		fxt.Test_rows(Xows_db_row.Ary(fxt.Make_row(10, "A"), fxt.Make_row(20, "B")), String_.Concat_lines_nl_skip_last
		( ""
		, "  <tr id='w.7CA'>"
		, "    <td style='padding-right:5px; vertical-align:top; text-align:right;'>10"
		, "    </td>"
		, "    <td style='padding-left:5px; vertical-align:top;'><a href='/site/w/wiki/A' title='A'>A</a>"
		, "    </td>"
		, "  </tr>"
		, "  <tr id='w.7CB'>"
		, "    <td style='padding-right:5px; vertical-align:top; text-align:right;'>20"
		, "    </td>"
		, "    <td style='padding-left:5px; vertical-align:top;'><a href='/site/w/wiki/B' title='B'>B</a>"
		, "    </td>"
		, "  </tr>"
		));
	}
}
class Xows_html_wkr_fxt {
	private Xoae_app app; private Xowe_wiki wiki; private Xows_html_wkr html_mgr; private final Bry_bfr tmp_bfr = Bry_bfr.new_(255);
	public Xows_html_wkr_fxt Clear() {
		if (app == null) {
			app = Xoa_app_fxt.app_();
			wiki = Xoa_app_fxt.wiki_tst_(app);
			html_mgr = new Xows_html_wkr();
		}
		return this;
	}
	public void Test_paging(boolean fwd, int paging_idx, String expd) {
		Xows_ui_qry qry = new Xows_ui_qry(Bry_.new_a7("A"), paging_idx, 100, Xosrh_rslt_itm_sorter.Tid_len_dsc, new Xows_ns_mgr(), true, new Xow_domain_itm[] {Xow_domain_itm_.parse(wiki.Domain_bry())});
		qry.Page_max_(2);
		html_mgr.Init_by_wiki(wiki, wiki.Lang().Num_mgr(), qry);
		byte[] paging_link = html_mgr.Paging_link(fwd);
		Tfds.Eq(expd, String_.new_a7(paging_link));
	}
	public void Test_rows(Xows_db_row[] rows, String expd) {
		Xows_ui_rslt rslt = new Xows_ui_rslt();
		Xows_html_row html_row = new Xows_html_row(wiki.App().Html__lnki_bldr());
		html_row.Init(rslt);
		for (int i = 0; i < rows.length; ++i)
			rslt.Add(rows[i]);
		html_row.XferAry(tmp_bfr, 0);
		Tfds.Eq_str_lines(expd, tmp_bfr.Xto_str_and_clear());
	}
	public Xows_db_row Make_row(int len, String ttl_str) {
		byte[] ttl_bry = Bry_.new_u8(ttl_str);
		return new Xows_db_row(Bry_.new_a7("w"), wiki.Ttl_parse(ttl_bry), 1, len);
	}
}
