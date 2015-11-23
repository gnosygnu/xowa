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
import org.junit.*; import gplx.core.brys.*; import gplx.langs.htmls.parsers.*;
public class Xoh_anch_capt_parser_tst {
	private final Xoh_anch_capt_parser_fxt fxt = new Xoh_anch_capt_parser_fxt();
	@Test   public void Basic__same()			{fxt.Test__match("Abc"				, "Abc", Xoh_anch_capt_parser.Tid__href);}
	@Test   public void Basic__diff()			{fxt.Test__match("Abc"				, "ABC", Xoh_anch_capt_parser.Tid__capt);}
	@Test   public void Space__same()			{fxt.Test__match("A_b"				, "A b", Xoh_anch_capt_parser.Tid__href);}
	@Test   public void Case__same()			{fxt.Test__match("Abc"				, "abc", Xoh_anch_capt_parser.Tid__href);}
	@Test   public void Case__reverse()			{fxt.Test__match("abc"				, "Abc", Xoh_anch_capt_parser.Tid__capt);}
	@Test   public void Case__disabled() {
		fxt.Wiki().Ns_mgr().Ns_main().Case_match_(gplx.xowa.wikis.nss.Xow_ns_case_.Tid__all);
		fxt.Test__match("Abcde", "abcde", Xoh_anch_capt_parser.Tid__capt);
	}
	@Test   public void Ns__href()				{fxt.Test__match("Help_talk:Ab"		, "Help talk:Ab"	, Xoh_anch_capt_parser.Tid__href);}
	@Test   public void Capt_trail()			{fxt.Test__match("A"				, "Abc"	, Xoh_anch_capt_parser.Tid__href_trail, 1);}
	@Test   public void Href_trail()			{fxt.Test__match("Ab"				, "A"	, Xoh_anch_capt_parser.Tid__capt_short, 1);}
}
class Xoh_anch_capt_parser_fxt {
	private final Xoh_anch_capt_parser matcher = new Xoh_anch_capt_parser();
	private final Bry_rdr rdr = new Bry_rdr();
	public Xoh_anch_capt_parser_fxt() {
		Xoae_app app = Xoa_app_fxt.app_();
		this.wiki = Xoa_app_fxt.wiki_tst_(app);
	}
	public Xow_wiki Wiki() {return wiki;} private Xow_wiki wiki;
	public void Test__match(String page_str, String capt_str, int expd_tid) {Test__match(page_str, capt_str, expd_tid, -1);}
	public void Test__match(String page_str, String capt_str, int expd_tid, int expd_trail_bgn) {
		byte[] page_bry = Bry_.new_u8(page_str);
		byte[] capt_bry = Bry_.new_u8(capt_str);
		Xoa_ttl href_ttl = wiki.Ttl_parse(page_bry);
		boolean cs_tid_1st = href_ttl.Ns().Case_match() == gplx.xowa.wikis.nss.Xow_ns_case_.Tid__1st;
		Tfds.Eq_int(expd_tid		, matcher.Parse(rdr.Init_by_page(Bry_.Empty, page_bry, page_bry.length), Bool_.Y, cs_tid_1st, page_bry, 0, page_bry.length, capt_bry, 0, capt_bry.length));
		Tfds.Eq_int(expd_trail_bgn	, matcher.Split_pos());
	}
}
