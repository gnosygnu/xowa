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
package gplx.xowa.xtns.graphs; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import gplx.core.tests.*;
import gplx.xowa.htmls.core.htmls.*; import gplx.xowa.htmls.core.makes.tests.*;
class Graph_html_fxt {
	private final    Xoh_make_fxt make_fxt = new Xoh_make_fxt();
	private final    Xop_fxt parser_fxt;
	private final    List_adp expd_lnr_list = List_adp_.New();
	private String hdump_atr;
	public Graph_html_fxt() {
		parser_fxt = make_fxt.Parser_fxt(); // NOTE: do not create multiple parser_fxts b/c of wiki registration issues;
	}
	public void Reset() {
		parser_fxt.Reset();
		expd_lnr_list.Clear();
		hdump_wkr_lnr = null;
	}
	public Xop_fxt Parser_fxt() {return parser_fxt;}
	public Graph_html_fxt Hdump_n_() {return Hdump_(Bool_.N);}
	public Graph_html_fxt Hdump_y_() {return Hdump_(Bool_.Y);}
	private Graph_html_fxt Hdump_(boolean v) {
		this.hdump_atr = v ? " " + String_.new_u8(Graph_json_load_mgr.HDUMP_ATR) : "";
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
		if (hdump_wkr_lnr != null) {
			hdump_wkr_lnr.Test();
		}
	}
	public void Test__hload(String html) {Test__hload(html, html);}
	public void Test__hload(String load, String expd) {
		make_fxt.Test__make(false, expd, make_fxt.Page_chkr().Body_(load));
	}

	private Gfo_test_lnr_base hdump_wkr_lnr = Gfo_test_lnr_base.New__keys("is_commons", "is_orig", "ttl");
	public String Wikirawupload__wtxt() {
		Graph_json_load_mgr hdump_wkr = (Graph_json_load_mgr)parser_fxt.Wiki().Html__hdump_mgr().Wkrs().Get_by(Graph_json_load_mgr.KEY);
		hdump_wkr_lnr = Gfo_test_lnr_base.New__keys("is_commons", "is_orig", "ttl");
		hdump_wkr.Test_lnr_(hdump_wkr_lnr);

		hdump_wkr_lnr.Expd().Add(Gfo_test_itm.New__expd().Add("is_commons", true).Add("is_orig", true).Add("ttl", "A.png"));
		return String_.Concat_lines_nl_skip_last
		( "<graph>"
		, "{"
		, "   \"version\":2,"
		, "   \"path\":\"wikirawupload:file:///mem/xowa/file/commons.wikimedia.org/orig/7/0/1/c/A.png\","
		, "   \"width\":300"
		, "}"
		, "</graph>"
		);
	}
	public String Wikirawupload__html(boolean dir_has_value) {
		String dir = Dir_str(dir_has_value);
		return String_.Concat_lines_nl_skip_last
		( "<div class='mw-graph' xo-graph-version=2" + hdump_atr + ">"
		, "{"
		, "   \"version\":2,"
		, "   \"path\":\"wikirawupload:" + dir + "file/commons.wikimedia.org/orig/7/0/1/c/A.png\","
		, "   \"width\":300"
		, "}"
		, "</div>"
		);
	}
	public String Literal_XOWA_ROOT__wtxt() {
		return String_.Concat_lines_nl_skip_last
		( "<graph>"
		, "{"
		, "   \"version\":2,"
		, "   \"data\":\"{XOWA_ROOT}\","
		, "   \"width\":300"
		, "}"
		, "</graph>"
		);
	}
	public String Literal_XOWA_ROOT__html(boolean dir_has_value) {
		String xowa_root = dir_has_value ? "{XOWA_ROOT}" : "{XOWA_ROOT}{XOWA_ROOT}";
		return String_.Concat_lines_nl_skip_last
		( "<div class='mw-graph' xo-graph-version=2" + hdump_atr + ">"
		, "{"
		, "   \"version\":2,"
		, "   \"data\":\"" + xowa_root + "\","
		, "   \"width\":300"
		, "}"
		, "</div>"
		);
	}
	private String Dir_str(boolean dir_has_value) {
		return dir_has_value ? "file:///mem/xowa/" : "{XOWA_ROOT}/";
	}
}
