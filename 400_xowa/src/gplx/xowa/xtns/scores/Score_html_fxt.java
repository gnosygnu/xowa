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
package gplx.xowa.xtns.scores; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import gplx.xowa.htmls.core.htmls.*; import gplx.xowa.htmls.core.makes.tests.*;
class Score_html_fxt {
	private final    Xoh_make_fxt make_fxt = new Xoh_make_fxt();
	private final    Xop_fxt parser_fxt = new Xop_fxt();
	private boolean hdump = false;
	public void Clear() {
		parser_fxt.Reset();
		make_fxt.Clear();
	}
	public Score_html_fxt Hdump_n_() {return Hdump_(Bool_.N);}
	public Score_html_fxt Hdump_y_() {return Hdump_(Bool_.Y);}
	private Score_html_fxt Hdump_(boolean v) {
		this.hdump = v;
		return this;
	}
	public void Test__hview(String wtxt, String expd) {
		parser_fxt.Hctx_(Xoh_wtr_ctx.Basic);
		parser_fxt.Test_html_full_str(wtxt, expd);
	}
	public void Test__hdump(String wtxt, String save, String load) {
		parser_fxt.Hctx_(Xoh_wtr_ctx.Hdump);
		parser_fxt.Test_html_full_str(wtxt, save);
		make_fxt.Test__make(save, make_fxt.Page_chkr().Body_(load));
	}
	public void Exec__Fill_page() {
		make_fxt.Exec__Fill_page();
	}
	public String Basic__wtxt() {
		return "<score>\\relative c' { f d f a d f e d cis a cis e a g f e }</score>";
	}
	public String Basic__html(boolean dir_has_value) {
		String img_id = hdump && dir_has_value ? "xoimg_0" : "xowa_score_0_img";
		String img_atrs = hdump && dir_has_value ? " alt=\"\"" : " src=\"\"  ";
		return String_.Concat_lines_nl
		( ""
		, "<div id=\"xowa_score_0_pre\" class=\"xowa-score-lilypond\">"
		, "  <pre style=\"overflow:auto\">\\relative c&#39; { f d f a d f e d cis a cis e a g f e }"
		, "</pre>"
		, "</div>"
		, ""
		, "<p>"
		, "  <a id=\"xowa_score_0_a\" href=\"\" xowa_title=\"\">"
		, "    <img id=\"" + img_id + "\"" + img_atrs + "/>"
		, "  </a>"
		, "</p>"
		);
	}
}
