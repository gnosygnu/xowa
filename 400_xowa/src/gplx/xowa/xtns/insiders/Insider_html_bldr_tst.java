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
package gplx.xowa.xtns.insiders; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import org.junit.*;
public class Insider_html_bldr_tst {
	@Before public void init() {fxt.Clear();} private Insider_html_bldr_fxt fxt = new Insider_html_bldr_fxt();
	@Test  public void Basic() {
		fxt.Init_insider("A_1");
		fxt.Test_bld(String_.Concat_lines_nl_skip_last
		( "<div id='p-insiders' class='portal' role='navigation'>"
		, "  <h3>Docent</h3>"
		, "  <div class='body'>"
		, "    <ul>"
		, "      <li class='interwiki-insider'><a class='xowa-hover-off' href='/wiki/User:A_1'>A 1</a></li>"
		, "      <li class='interwiki-insider'><a class='xowa-hover-off' href='/wiki/Docent_page'>About Docents</a></li>"
		, "    </ul>"
		, "  </div>"
		, "</div>"
		));
	}
}
class Insider_html_bldr_fxt {
	private Xoa_app app; private Xow_wiki wiki; private Xoa_page page;
	private Insider_xtn_mgr xtn_mgr;
	private Insider_xtn_skin_itm skin_itm;
	public void Clear() {
		this.app = Xoa_app_fxt.app_();
		this.wiki = Xoa_app_fxt.wiki_tst_(app);
		Xop_fxt.Init_msg(wiki, "insider-title", "Docent");
		Xop_fxt.Init_msg(wiki, "insider-about", "About Docents");
		Xop_fxt.Init_msg(wiki, "insider-about-page", "Docent_page");
		this.xtn_mgr = wiki.Xtn_mgr().Xtn_insider();
		xtn_mgr.Enabled_y_();
		xtn_mgr.Xtn_init_by_wiki(wiki);
		this.page = wiki.Ctx().Cur_page();
		skin_itm = new Insider_xtn_skin_itm(xtn_mgr.Html_bldr());
		page.Html_data().Xtn_skin_mgr().Add(skin_itm);
	}
	public void Init_insider(String lnki_ttl) {
		skin_itm.Add(Bry_.new_utf8_(lnki_ttl));
	}
	public void Test_bld(String expd) {
		Bry_bfr tmp_bfr = Bry_bfr.reset_(255);
		skin_itm.Write(tmp_bfr, page);
		Tfds.Eq_str_lines(expd, tmp_bfr.Xto_str_and_clear());
	}
}
