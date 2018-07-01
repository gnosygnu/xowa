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
		Tfds.Eq_str_lines(expd, actl);
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
		, "  <div id=\"toctitle\" class=\"toctitle\">"
		, "    <h2>Contents</h2>"
		, "  </div>"
		, String_.Concat_lines_nl_skip_last(ary)
		, "</div>" + (nl ? "\n" : "")
		);
	}
	public static String Bld_page_with_toc(Bry_bfr bfr, Xop_fxt fxt, String raw) {
		gplx.xowa.parsers.Xop_root_tkn root = fxt.Exec_parse_page_all_as_root(Bry_.new_u8(raw));
		fxt.Ctx().Page_data().Copy_to(fxt.Page());
		fxt.Wiki().Html_mgr().Html_wtr().Write_doc(bfr, fxt.Ctx(), fxt.Hctx(), root.Root_src(), root);

		gplx.xowa.htmls.core.wkrs.tocs.Xoh_toc_wtr.Write_toc(bfr, fxt.Page(), Xoh_wtr_ctx.Basic);
		return bfr.To_str_and_clear();
	}
}
