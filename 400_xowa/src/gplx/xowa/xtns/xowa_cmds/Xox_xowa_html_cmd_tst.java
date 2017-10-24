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
package gplx.xowa.xtns.xowa_cmds; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import org.junit.*;
import gplx.xowa.htmls.*; import gplx.xowa.guis.*; import gplx.xowa.wikis.pages.*; import gplx.xowa.wikis.pages.tags.*;
import gplx.xowa.parsers.*;
public class Xox_xowa_html_cmd_tst {
	@Before public void init() {Xopg_tag_wtr.Loader_as_script_static = Bool_.N; fxt.Clear();} private Xox_xowa_html_cmd_fxt fxt = new Xox_xowa_html_cmd_fxt();
	@Test   public void term() {Xopg_tag_wtr.Loader_as_script_static = Bool_.Y;}
	@Test  public void Head_end() {
		fxt.Test_parse_w_skin
		( "<xowa_html pos='head_end'>test</xowa_html>"
		, "<html><head><style data-source=\"xowa\" type=\"text/css\">\ntest\n</style>\n</head><body></body></html>"
		);
	}
	@Test  public void Tail() {
		fxt.Test_parse_w_skin
		( "<xowa_html pos='tail'>test</xowa_html>"
		, "<html><head></head><body></body><style data-source=\"xowa\" type=\"text/css\">\ntest\n</style>\n</html>"
		);
	}
}
class Xox_xowa_html_cmd_fxt {
	private Bry_bfr bfr = Bry_bfr_.Reset(16);
	private final    Xop_fxt fxt = new Xop_fxt();
	private Xowe_wiki wiki; private Xow_html_mgr html_mgr;
	public void Clear() {
		this.wiki = fxt.Wiki();
		html_mgr = wiki.Html_mgr();			
		wiki.Sys_cfg().Xowa_cmd_enabled_(true);
		fxt.Page().Html_data().Head_mgr().Itm__css().Enabled_(false);
		html_mgr.Page_wtr_mgr().Page_read_fmtr().Fmt_("<html><head></head><body>~{page_data}</body></html>");
	}
	public void Test_parse_w_skin(String raw, String expd) {
		byte[] raw_bry = Bry_.new_u8(raw);
		Xop_root_tkn root = fxt.Exec_parse_page_all_as_root(raw_bry);
		fxt.Page().Root_(root);
		html_mgr.Html_wtr().Write_doc(bfr, fxt.Ctx(), raw_bry, root);
		html_mgr.Page_wtr_mgr().Wkr(Xopg_page_.Tid_read).Write_page(bfr, fxt.Page(), fxt.Ctx(), Xoh_page_html_source_.Wtr);
		Tfds.Eq_str_lines(expd, bfr.To_str_and_clear());
	}
}
