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
package gplx.xowa.addons.wikis.searchs.searchers.wkrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.searchs.*; import gplx.xowa.addons.wikis.searchs.searchers.*;
import org.junit.*; import gplx.xowa.addons.wikis.searchs.parsers.*; import gplx.xowa.addons.wikis.searchs.searchers.crts.*; import gplx.xowa.addons.wikis.searchs.searchers.crts.visitors.*;
public class Srch_page_tbl_wkr_tst {
	private final    Srch_page_tbl_wkr_fxt fxt = new Srch_page_tbl_wkr_fxt();
	@Test   public void Word__one()				{fxt.Test__to_bry_or_null("a"					, "a");}
	@Test   public void Word__many()			{fxt.Test__to_bry_or_null("a b c"				, "a b c");}
	@Test   public void Wild__end()				{fxt.Test__to_bry_or_null("a*"					, "a");}
	@Test   public void Wild__both()			{fxt.Test__to_bry_or_null("a*b*"				, null);}
	@Test   public void Quote()					{fxt.Test__to_bry_or_null("\"a b\""				, "a b");}
	@Test   public void Quote__mixed()			{fxt.Test__to_bry_or_null("a \"b \"\" c\" d"	, "a b \" c d");}
	@Test   public void Escape()				{fxt.Test__to_bry_or_null("a\\+"				, "a+");}
	@Test   public void Not()					{fxt.Test__to_bry_or_null("a -b"				, null);}
	@Test   public void And()					{fxt.Test__to_bry_or_null("a + b"				, null);}
	@Test   public void Or()					{fxt.Test__to_bry_or_null("a , b"				, null);}
	@Test   public void Parens()				{fxt.Test__to_bry_or_null("(a)"					, null);}
}
class Srch_page_tbl_wkr_fxt {
	private final    Srch_crt_parser crt_parser;
	private final    Bry_bfr tmp_bfr = Bry_bfr_.New();
	public Srch_page_tbl_wkr_fxt() {
		crt_parser = new Srch_crt_parser(Srch_crt_scanner_syms.Dflt);
		Srch_text_parser text_parser = new Srch_text_parser();
		text_parser.Init_for_ttl(gplx.xowa.langs.cases.Xol_case_mgr_.A7());
	}
	public void Test__to_bry_or_null(String src_str, String expd) {
		byte[] src_bry = Bry_.new_a7(src_str);
		Srch_crt_mgr crt_mgr = crt_parser.Parse_or_invalid(src_bry);
		Tfds.Eq(expd, String_.new_u8(Srch_page_tbl_wkr.To_bry_or_null(tmp_bfr, Srch_search_addon.Wildcard__star, crt_mgr)));
	}
}
