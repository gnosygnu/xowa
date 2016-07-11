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
		Xoa_app_fxt.repo2_(parser_fxt.App(), parser_fxt.Wiki());	// needed else will be old "mem/wiki/repo/trg/thumb/" instead of standard "mem/file/en.wikipedia.org/thumb/"
		parser_fxt.Wiki().Html__hdump_mgr().Init_by_db(parser_fxt.Wiki());
		parser_fxt.Wiki().Html_mgr().Html_wtr().Cfg().Lnki__id_(Bool_.Y).Lnki__title_(Bool_.Y);
	}
	public void Clear() {
		parser_fxt.Reset();
		page_chkr.Clear();
	}
	public Xoh_page_chkr Page_chkr() {return page_chkr;} private final    Xoh_page_chkr page_chkr = new Xoh_page_chkr();
	public Xop_fxt Parser_fxt() {return parser_fxt;} private final    Xop_fxt parser_fxt = new Xop_fxt();
	public void Init_img_cache
		( String wiki_abrv, String lnki_ttl, byte lnki_type, double lnki_upright, int lnki_w, int lnki_h, double lnki_time, int lnki_page
		, boolean repo_is_commons, String html_ttl, int html_w, int html_h, double html_time, int html_page
		) {
		// fxt.Init_cache("en.wikipedia.org", "A.png", 0, 220, 110, 0.5, -1, -1, Bool_.Y, "B.png", 330, 110, -1, -1);
	}
	public void Test__html(String wtxt, String expd) {
		expd = String_.Replace(expd, "'", "\"");
            String actl = parser_fxt.Exec__parse_to_hdump(wtxt);
		Tfds.Eq_str_lines(expd, actl);
	}
	public void Test__make(String html, Xoh_page_chkr chkr) {
		html = String_.Replace(html, "'", "\"");
		Xoh_page actl = new Xoh_page();
		actl.Init(parser_fxt.Wiki(), Xoa_url.blank(), parser_fxt.Wiki().Ttl_parse(Xoa_page_.Main_page_bry), 1);
		Gfo_usr_dlg_.Instance = Gfo_usr_dlg_.Test_console();
		Xoh_make_mgr make_mgr = parser_fxt.Wiki().Html__hdump_mgr().Load_mgr().Make_mgr();			
		byte[] actl_body = make_mgr.Parse(Bry_.new_u8(html), actl, parser_fxt.Wiki());
		actl.Db().Html().Html_bry_(actl_body);
		Gfo_usr_dlg_.Instance = Gfo_usr_dlg_.Noop;
		chkr.Check(actl);
	}
}
