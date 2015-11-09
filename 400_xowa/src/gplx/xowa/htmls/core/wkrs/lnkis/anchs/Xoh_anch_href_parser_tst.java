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
package gplx.xowa.htmls.core.wkrs.lnkis.anchs; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.wkrs.*; import gplx.xowa.htmls.core.wkrs.lnkis.*;
import org.junit.*; import gplx.core.brys.*; import gplx.xowa.wikis.ttls.*;
public class Xoh_anch_href_parser_tst {
	private final Xoh_anch_href_parser_fxt fxt = new Xoh_anch_href_parser_fxt();
	@Test   public void Site() {
		fxt.Test__parse("/site/A/wiki/B", "A", "B");
	}
	@Test   public void Page() {
		fxt.Test__parse("/wiki/B", "", "B");
	}
	@Test   public void Fail__1st_seg_must_be_site_or_wiki() {
		fxt.Test__parse__fail("/fail/A", "failed trie check: mid='fail/A' ctx='Main_Page' wkr='lnki.href' excerpt='/fail/A'");
	}
	@Test   public void Fail__2nd_seg_must_be_wiki() {
		fxt.Test__parse__fail("/site/A/B/C", "failed check: chk='wiki/' ctx='Main_Page' wkr='lnki.href' excerpt='/site/A/B/C'");
	}
}
class Xoh_anch_href_parser_fxt extends Xoh_itm_parser_fxt_base {
	private final Xoh_anch_href_parser parser = new Xoh_anch_href_parser();
	private final Xow_ttl_parser ttl_parser;
	public Xoh_anch_href_parser_fxt() {
		Xoae_app app = Xoa_app_fxt.app_();
		ttl_parser = Xoa_app_fxt.wiki_tst_(app);
	}
	@Override public Xoh_itm_parser Parser_get() {return parser;}
	public void Test__parse(String src_str, String expd_site, String expd_page) {
		Exec_parse(src_str);
		Tfds.Eq_str(expd_site, parser.Site_bgn() == -1 ? "" : String_.new_u8(src, parser.Site_bgn(), parser.Site_end()));
		Tfds.Eq_str(expd_page, String_.new_u8(src, parser.Page_bgn(), parser.Page_end()));
	}
	@Override public void Exec_parse_hook(Bry_rdr owner_rdr, int src_bgn, int src_end) {
		parser.Parse(owner_rdr, ttl_parser, src_bgn, src_end);
	}
}
