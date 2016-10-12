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
public class Xow_url_parser__ttl_tst {
	private final    Xow_url_parser_fxt tstr = new Xow_url_parser_fxt();
	@Test  public void Name() {
		tstr.Exec__parse("A").Test__wiki("en.wikipedia.org").Test__page("A");
	}
	@Test  public void Sub_1() {
		tstr.Exec__parse("A/b").Test__wiki("en.wikipedia.org").Test__page("A/b");
	}
	@Test  public void Sub_2() {
		tstr.Exec__parse("A/b/c").Test__wiki("en.wikipedia.org").Test__page("A/b/c");
	}
	@Test  public void Anch() {
		tstr.Exec__parse("A#b").Test__wiki("en.wikipedia.org").Test__page("A").Test__anch("b");
	}
	@Test   public void Anch_w_slash() {	// PURPOSE: A/b#c/d was not parsing correctly; PAGE:en.w:Enlightenment_Spain#Enlightened_despotism_.281759%E2%80%931788.29
		tstr.Exec__parse("A/b#c/d").Test__page("A/b").Test__anch("c.2Fd");
	}
	@Test  public void Ns_category() {
		tstr.Exec__parse("Category:A").Test__wiki("en.wikipedia.org").Test__page("Category:A");
	}
	@Test  public void Main_page__basic() {
		tstr.Exec__parse("en.wikipedia.org")			.Test__wiki("en.wikipedia.org").Test__page_is_main_y();
		tstr.Exec__parse("en.wikipedia.org/")			.Test__wiki("en.wikipedia.org").Test__page_is_main_y();
		tstr.Exec__parse("en.wikipedia.org/wiki")		.Test__wiki("en.wikipedia.org").Test__page_is_main_y();
		tstr.Exec__parse("en.wikipedia.org/wiki/")	.Test__wiki("en.wikipedia.org").Test__page_is_main_y();
		tstr.Exec__parse("en.wikipedia.org/wiki/A")	.Test__wiki("en.wikipedia.org").Test__page_is_main_n();
	}
	@Test  public void Ns_file__basic() {// PURPOSE: "File:A" should not be mistaken for "file:///" ns
		tstr.Exec__parse("File:A").Test__wiki("en.wikipedia.org").Test__page("File:A");
	}
	@Test  public void Ns_file__nested() {// PURPOSE: handle fictitious "File:A/B/C.png"
		tstr.Exec__parse("File:A/B/C.png").Test__wiki("en.wikipedia.org").Test__page("File:A/B/C.png");	// should not be C.png b/c of Gfo_url_parser_old
	}
	@Test  public void Anch__basic() {// DATE:2015-07-26
		tstr.Exec__parse("#A").Test__tid(Xoa_url_.Tid_anch).Test__wiki_is_missing(true).Test__page("").Test__anch("A");
	}
}
