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
package gplx.xowa.htmls.core.parsers; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*;
import org.junit.*;
public class Xoh_ttl_matcher_tst {
	private final Xoh_ttl_matcher_fxt fxt = new Xoh_ttl_matcher_fxt();
	@Test   public void Basic__same()			{fxt.Test__match("Abc"				, "Abc", Xoh_ttl_matcher.Tid__same);}
	@Test   public void Basic__diff()			{fxt.Test__match("Abc"				, "ABC", Xoh_ttl_matcher.Tid__diff);}
	@Test   public void Space__same()			{fxt.Test__match("A_b"				, "A b", Xoh_ttl_matcher.Tid__same);}
	@Test   public void Case__same()			{fxt.Test__match("Abc"				, "abc", Xoh_ttl_matcher.Tid__same);}
	@Test   public void Case__reverse()			{fxt.Test__match("abc"				, "Abc", Xoh_ttl_matcher.Tid__diff);}
	@Test   public void Case__disabled() {
		fxt.Wiki().Ns_mgr().Ns_main().Case_match_(gplx.xowa.wikis.nss.Xow_ns_case_.Tid__all);
		fxt.Test__match("Abcde", "abcde", Xoh_ttl_matcher.Tid__diff);
	}
	@Test   public void Ns__same()				{fxt.Test__match("Help_talk:Ab"		, "Help talk:Ab", Xoh_ttl_matcher.Tid__same);}
	@Test   public void Ns__diff()				{fxt.Test__match("Help_talk:Ab"		, "Help_talk:Ab", Xoh_ttl_matcher.Tid__diff);}
	@Test   public void Trail__same()			{fxt.Test__match("A"				, "Abc", Xoh_ttl_matcher.Tid__trail, 1);}
}
class Xoh_ttl_matcher_fxt {
	private final Xoh_ttl_matcher matcher = new Xoh_ttl_matcher();
	public Xoh_ttl_matcher_fxt() {
		Xoae_app app = Xoa_app_fxt.app_();
		this.wiki = Xoa_app_fxt.wiki_tst_(app);
	}
	public Xow_wiki Wiki() {return wiki;} private Xow_wiki wiki;
	public void Test__match(String page_str, String capt_str, int expd_tid) {Test__match(page_str, capt_str, expd_tid, -1);}
	public void Test__match(String page_str, String capt_str, int expd_tid, int expd_trail_bgn) {
		byte[] page_bry = Bry_.new_u8(page_str);
		byte[] capt_bry = Bry_.new_u8(capt_str);
		Tfds.Eq_int(expd_tid		, matcher.Match(wiki, page_bry, 0, page_bry.length, capt_bry, 0, capt_bry.length));
		Tfds.Eq_int(expd_trail_bgn	, matcher.Trail_bgn());
	}
}
