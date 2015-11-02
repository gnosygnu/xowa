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
package gplx.xowa.htmls.core.makes.tests; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.makes.*;
import gplx.xowa.htmls.sections.*;
public class Xoh_make_fxt {
	public Xoh_make_fxt() {
		parser_fxt.Wiki().Html__hdump_mgr().Init_by_db(parser_fxt.Wiki());
		parser_fxt.Wiki().Html_mgr().Html_wtr().Cfg().Lnki__id_(Bool_.Y).Lnki__title_(Bool_.Y);
	}
	public void Clear() {parser_fxt.Reset();}
	public Xoh_page_chkr Page_chkr() {return page_chkr;} private final Xoh_page_chkr page_chkr = new Xoh_page_chkr();
	public Xop_fxt Parser_fxt() {return parser_fxt;} private final Xop_fxt parser_fxt = new Xop_fxt();
	public void Test__html(String wtxt, String expd) {
		Tfds.Eq_str_lines(expd, parser_fxt.Exec__parse_to_hdump(wtxt));
	}
	public void Test__make(String html, Xoh_page_chkr chkr) {
		Xoh_page actl = new Xoh_page();
		Gfo_usr_dlg_.Instance = Gfo_usr_dlg_.Test_console();
		byte[] actl_body = parser_fxt.Wiki().Html__hdump_mgr().Load_mgr().Make_mgr().Parse(actl, Bry_.new_u8(html));
		actl.Body_(actl_body);
		Gfo_usr_dlg_.Instance = Gfo_usr_dlg_.Noop;
		chkr.Check(actl);
	}
}
