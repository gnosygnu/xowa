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
package gplx.xowa.xtns.relatedArticles; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import org.junit.*; import gplx.xowa.wikis.pages.skins.*;
public class Articles_func_tst {
	@Before public void init()				{fxt.Reset();} private Articles_func_fxt fxt = new Articles_func_fxt();
	@Test  public void Basic() {
		fxt.Test_parse("{{#related:A1 && A 1}}{{#related:B1 && B_1}}{{#related:C_1}}", String_.Concat_lines_nl_skip_last	// C1 handles no caption; PAGE:de.v:W�rzburg; DATE:2014-08-06
		(  "<div class=\"portal\" role=\"navigation\" id=\"p-relatedarticles\">"
		, "  <h3>Related articles</h3>"
		, "  <div class=\"body\">"
		, "    <ul>"
		, "      <li class=\"interwiki-relart\"><a href=\"/wiki/A1\">A 1</a></li>"
		, "      <li class=\"interwiki-relart\"><a href=\"/wiki/B1\">B 1</a></li>"
		, "      <li class=\"interwiki-relart\"><a href=\"/wiki/C_1\">C 1</a></li>"
		, "    </ul>"
		, "  </div>"
		, "</div>"
		));	
	}
}
class Articles_func_fxt {
	private final    Xop_fxt fxt = new Xop_fxt();
	public void Reset() {
		fxt.Wiki().Lang().Msg_mgr().Itm_by_key_or_new("relatedarticles-title", "Related articles");
		fxt.Reset();
	}
	public void Test_parse(String raw, String expd) {
		fxt.Test_parse_page_all_str(raw, "");
		Xopg_xtn_skin_fmtr_arg fmtr_arg = new Xopg_xtn_skin_fmtr_arg(fxt.Page(), Xopg_xtn_skin_itm_tid.Tid_sidebar);
		Bry_bfr bfr = Bry_bfr_.New();
		fmtr_arg.Bfr_arg__add(bfr);
		Tfds.Eq_str_lines(expd, bfr.To_str_and_clear());
	}
}
