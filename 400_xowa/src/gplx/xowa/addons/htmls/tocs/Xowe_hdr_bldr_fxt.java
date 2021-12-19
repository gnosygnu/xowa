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
package gplx.xowa.addons.htmls.tocs;
import gplx.frameworks.tests.GfoTstr;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.basics.utls.StringUtl;
import gplx.types.basics.utls.BoolUtl;
import gplx.xowa.Xop_fxt;
import gplx.xowa.htmls.Xoh_consts;
import gplx.xowa.htmls.core.htmls.Xoh_wtr_ctx;
public class Xowe_hdr_bldr_fxt {		
	private final BryWtr tmp = BryWtr.New();
	public Xop_fxt Fxt() {return fxt;} private final Xop_fxt fxt = new Xop_fxt();
	public void Clear() {
		fxt.Reset();
		tmp.Clear();
	}
	public void Test_html_toc(String raw, String expd) {
		fxt.Wtr_cfg().Toc__show_(BoolUtl.Y);
		String actl = Bld_page_with_toc(tmp, fxt, raw);

		// HACK: proc only tests TOC; remove <h#><span class="mw-> section; ugly hack, but this is only test code
		int span_pos = StringUtl.FindFwd(actl, "<span class=\"mw-");
		actl = StringUtl.Mid(actl, 0, span_pos - 5);

		GfoTstr.EqLines(expd, actl);
		fxt.Wtr_cfg().Toc__show_(BoolUtl.N);
	}
	public void Test_html_all(String raw, String expd) {
		expd = Xoh_consts.Escape_apos(expd);
		fxt.Wtr_cfg().Toc__show_(BoolUtl.Y);
		String actl = Bld_page_with_toc(tmp, fxt, raw);
		GfoTstr.EqLines(expd, actl);
		fxt.Wtr_cfg().Toc__show_(BoolUtl.N);
	}
	public void Test_html_frag(String raw, String frag) {
		fxt.Wtr_cfg().Toc__show_(BoolUtl.Y);
		String actl = Bld_page_with_toc(tmp, fxt, raw);
		fxt.Test_str_part_y(actl, frag);
		fxt.Wtr_cfg().Toc__show_(BoolUtl.N);
	}
	public String toc_tbl_nl_y(String... ary) {return toc_tbl(BoolUtl.Y, ary);}
	public String toc_tbl_nl_n(String... ary) {return toc_tbl(BoolUtl.N, ary);}
	public String toc_tbl(boolean nl, String... ary) {
		return StringUtl.ConcatLinesNlSkipLast
		( "<div id=\"toc\" class=\"toc\">"
		, "  <div id=\"toctitle\" class=\"toctitle\">"
		, "    <h2>Contents</h2>"
		, "  </div>"
		, StringUtl.ConcatLinesNlSkipLast(ary)
		, "</div>" + (nl ? "\n" : "")
		);
	}
	public static String Bld_page_with_toc(BryWtr bfr, Xop_fxt fxt, String raw) {
		gplx.xowa.parsers.Xop_root_tkn root = fxt.Exec_parse_page_all_as_root(BryUtl.NewU8(raw));
		fxt.Ctx().Page_data().Copy_to(fxt.Page());
		fxt.Wiki().Html_mgr().Html_wtr().Write_doc(bfr, fxt.Ctx(), fxt.Hctx(), root.Root_src(), root);

		gplx.xowa.htmls.core.wkrs.tocs.Xoh_toc_wtr.Write_toc(bfr, fxt.Page(), Xoh_wtr_ctx.Basic);
		return bfr.ToStrAndClear();
	}
}
