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
package gplx.xowa.apps.urls; import gplx.*; import gplx.xowa.*; import gplx.xowa.apps.*;
import org.junit.*;
public class Xoa_url_parser__ttl_tst {
	private final Xoa_url_parser_fxt tstr = new Xoa_url_parser_fxt();
	@Test  public void Name() {
		tstr.Run_parse("A").Chk_wiki("en.wikipedia.org").Chk_page("A");
	}
	@Test  public void Sub_1() {
		tstr.Run_parse("A/b").Chk_wiki("en.wikipedia.org").Chk_page("A/b");
	}
	@Test  public void Sub_2() {
		tstr.Run_parse("A/b/c").Chk_wiki("en.wikipedia.org").Chk_page("A/b/c");
	}
	@Test  public void Anch() {
		tstr.Run_parse("A#b").Chk_wiki("en.wikipedia.org").Chk_page("A").Chk_anch("b");
	}
	@Test   public void Anch_w_slash() {	// PURPOSE: A/b#c/d was not parsing correctly; PAGE:en.w:Enlightenment_Spain#Enlightened_despotism_.281759%E2%80%931788.29
		tstr.Run_parse("A/b#c/d").Chk_page("A/b").Chk_anch("c.2Fd");
	}
	@Test  public void Ns_category() {
		tstr.Run_parse("Category:A").Chk_wiki("en.wikipedia.org").Chk_page("Category:A");
	}
	@Test  public void Main_page__basic() {
		tstr.Run_parse("en.wikipedia.org")			.Chk_wiki("en.wikipedia.org").Chk_page_is_main_y();
		tstr.Run_parse("en.wikipedia.org/")			.Chk_wiki("en.wikipedia.org").Chk_page_is_main_y();
		tstr.Run_parse("en.wikipedia.org/wiki")		.Chk_wiki("en.wikipedia.org").Chk_page_is_main_y();
		tstr.Run_parse("en.wikipedia.org/wiki/")	.Chk_wiki("en.wikipedia.org").Chk_page_is_main_y();
		tstr.Run_parse("en.wikipedia.org/wiki/A")	.Chk_wiki("en.wikipedia.org").Chk_page_is_main_n();
	}
	@Test  public void Ns_file__basic() {// PURPOSE: "File:A" should not be mistaken for "file:///" ns
		tstr.Run_parse("File:A").Chk_wiki("en.wikipedia.org").Chk_page("File:A");
	}
	@Test  public void Ns_file__nested() {// PURPOSE: handle fictitious "File:A/B/C.png"
		tstr.Run_parse("File:A/B/C.png").Chk_wiki("en.wikipedia.org").Chk_page("File:A/B/C.png");	// should not be C.png b/c of Gfo_url_parser_old
	}
	@Test  public void Anch__basic() {// DATE:2015-07-26
		tstr.Run_parse("#A").Chk_tid(Xoa_url_.Tid_anch).Chk_wiki_is_missing(true).Chk_page("").Chk_anch("A");
	}
}
