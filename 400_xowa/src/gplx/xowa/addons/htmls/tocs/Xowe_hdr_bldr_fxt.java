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
package gplx.xowa.addons.htmls.tocs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.htmls.*;
import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.htmls.*;
public class Xowe_hdr_bldr_fxt {		
	private final    Bry_bfr tmp = Bry_bfr_.New();
	public Xop_fxt Fxt() {return fxt;} private final    Xop_fxt fxt = new Xop_fxt();
	public void Clear() {
		fxt.Reset();
		tmp.Clear();
	}
	public void Test_html_toc(String raw, String expd) {
		fxt.Wtr_cfg().Toc__show_(Bool_.Y);
		String actl = Bld_page_with_toc(tmp, fxt, raw);

		// HACK: proc only tests TOC; remove <h#><span class="mw-> section; ugly hack, but this is only test code
		int span_pos = String_.FindFwd(actl, "<span class=\"mw-");
		actl = String_.Mid(actl, 0, span_pos - 5);

		Tfds.Eq_str_lines(expd, actl);
		fxt.Wtr_cfg().Toc__show_(Bool_.N);
	}
	public void Test_html_all(String raw, String expd) {
		expd = Xoh_consts.Escape_apos(expd);
		fxt.Wtr_cfg().Toc__show_(Bool_.Y);
		String actl = Bld_page_with_toc(tmp, fxt, raw);
		Tfds.Eq(expd, actl);
		fxt.Wtr_cfg().Toc__show_(Bool_.N);
	}
	public void Test_html_frag(String raw, String frag) {
		fxt.Wtr_cfg().Toc__show_(Bool_.Y);
		String actl = Bld_page_with_toc(tmp, fxt, raw);
		fxt.Test_str_part_y(actl, frag);
		fxt.Wtr_cfg().Toc__show_(Bool_.N);
	}
	public String toc_tbl_nl_y(String... ary) {return toc_tbl(Bool_.Y, ary);}
	public String toc_tbl_nl_n(String... ary) {return toc_tbl(Bool_.N, ary);}
	public String toc_tbl(boolean nl, String... ary) {
		return String_.Concat_lines_nl_skip_last
		( "<div id=\"toc\" class=\"toc\">"
		, "  <div id=\"toctitle\">"
		, "    <h2>Contents</h2>"
		, "  </div>"
		, String_.Concat_lines_nl_skip_last(ary)
		, "</div>" + (nl ? "\n" : "")
		);
	}
	public static String Bld_page_with_toc(Bry_bfr bfr, Xop_fxt fxt, String raw) {
		String rv = fxt.Exec_parse_page_all_as_str(raw);
		bfr.Add_str_u8(rv);
		gplx.xowa.htmls.core.wkrs.tocs.Xoh_toc_wtr.Write_toc(bfr, fxt.Page(), Xoh_wtr_ctx.Basic);
		return bfr.To_str_and_clear();
	}
}
