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
package gplx.xowa.addons.apps.searchs.searchers.wkrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.apps.*; import gplx.xowa.addons.apps.searchs.*; import gplx.xowa.addons.apps.searchs.searchers.*;
import org.junit.*; import gplx.xowa.addons.apps.searchs.parsers.*; import gplx.xowa.addons.apps.searchs.searchers.crts.*; import gplx.xowa.addons.apps.searchs.searchers.crts.visitors.*;
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
	private final    Bry_bfr tmp_bfr = Bry_bfr.new_();
	public Srch_page_tbl_wkr_fxt() {
		crt_parser = new Srch_crt_parser(Srch_crt_scanner_syms.Dflt);
		Srch_text_parser text_parser = new Srch_text_parser();
		text_parser.Init_for_ttl(gplx.xowa.langs.cases.Xol_case_mgr_.A7());
	}
	public void Test__to_bry_or_null(String src_str, String expd) {
		byte[] src_bry = Bry_.new_a7(src_str);
		Srch_crt_mgr crt_mgr = crt_parser.Parse_or_invalid(src_bry, Bool_.N);
		Tfds.Eq(expd, String_.new_u8(Srch_page_tbl_wkr.To_bry_or_null(tmp_bfr, Srch_search_addon.Wildcard__star, crt_mgr)));
	}
}
