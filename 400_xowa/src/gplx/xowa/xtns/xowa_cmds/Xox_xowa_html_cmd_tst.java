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
package gplx.xowa.xtns.xowa_cmds; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import org.junit.*;
import gplx.xowa.html.*; import gplx.xowa.gui.*; import gplx.xowa.pages.*;
public class Xox_xowa_html_cmd_tst {
	@Before public void init() {fxt.Clear();} private Xox_xowa_html_cmd_fxt fxt = new Xox_xowa_html_cmd_fxt();
	@Test  public void Head_end() {
		fxt.Test_parse_w_skin
		( "<xowa_html pos='head.end'>test</xowa_html>"
		, "<html><head>test</head><body></body></html>"
		);
	}
	@Test  public void Html_end() {
		fxt.Test_parse_w_skin
		( "<xowa_html pos='html.end'>test</xowa_html>"
		, "<html><head></head><body></body>test</html>"
		);
	}
}
class Xox_xowa_html_cmd_fxt {
	private Bry_bfr bfr = Bry_bfr.reset_(16);
	private Xop_fxt fxt = new Xop_fxt();
	private Xowe_wiki wiki; private Xow_html_mgr html_mgr;
	public void Clear() {
		this.wiki = fxt.Wiki();
		html_mgr = wiki.Html_mgr();			
		wiki.Sys_cfg().Xowa_cmd_enabled_(true);
		fxt.Page().Html_data().Module_mgr().Itm_css().Enabled_(false);
		html_mgr.Page_wtr_mgr().Page_read_fmtr().Fmt_("<html><head></head><body>~{page_data}</body></html>");
	}
	public void Test_parse_w_skin(String raw, String expd) {
		byte[] raw_bry = Bry_.new_utf8_(raw);
		Xop_root_tkn root = fxt.Exec_parse_page_all_as_root(raw_bry);
		fxt.Page().Root_(root);
		html_mgr.Html_wtr().Write_all(bfr, fxt.Ctx(), raw_bry, root);
		byte[] actl = html_mgr.Page_wtr_mgr().Wkr(Xopg_view_mode.Tid_read).Write(html_mgr.Page_wtr_mgr(), fxt.Page(), fxt.Ctx(), bfr);
		Tfds.Eq_str_lines(expd, String_.new_utf8_(actl));
	}
}
