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
package gplx.xowa.htmls.core.wkrs.hdrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.wkrs.*;
import org.junit.*; import gplx.xowa.htmls.core.hzips.*;
public class Xoh_hdr_hzip_tst {
	private final Xoh_hzip_fxt fxt = new Xoh_hzip_fxt();
	@Test   public void Same() {
		fxt.Test__bicode(String_.Concat_lines_nl_skip_last
		( "~\"'A~"
		, "a"
		), String_.Concat_lines_nl_skip_last
		( "<h6><span class='mw-headline' id='A'>A</span></h6>"
		, "a"
		));
	}
	@Test   public void Diff() {
		fxt.Test__bicode(String_.Concat_lines_nl_skip_last
		( "~\"+<i>A</i>~A~"
		, "a"
		), String_.Concat_lines_nl_skip_last
		( "<h2><span class='mw-headline' id='A'><i>A</i></span></h2>"
		, "a"
		));
	}
	@Test   public void Diff_by_underscore() {
		fxt.Test__bicode(String_.Concat_lines_nl_skip_last
		( "~\"#A 1~"
		, "a"
		), String_.Concat_lines_nl_skip_last
		( "<h2><span class='mw-headline' id='A_1'>A 1</span></h2>"
		, "a"
		));
	}
	@Test   public void Diff_by_lnki() {
		fxt.Test__bicode(String_.Concat_lines_nl_skip_last
		( "~\"+<a href=\"/wiki/Category:A\" title=\"Category:A\">Category:A</a>~Category:A~"
		, "a"
		), String_.Concat_lines_nl_skip_last
		( "<h2><span class='mw-headline' id='Category:A'><a href='/wiki/Category:A' title='Category:A'>Category:A</a></span></h2>"
		, "a"
		));
	}
	@Test   public void Same_w_underscore() {
		fxt.Test__bicode(String_.Concat_lines_nl_skip_last
		( "~\"#A_1~"
		, "a"
		), String_.Concat_lines_nl_skip_last
		( "<h2><span class='mw-headline' id='A_1'>A_1</span></h2>"
		, "a"
		));
	}
	@Test   public void Tidy__bad_end() {
		fxt.Test__bicode(
		"~\"?A~AB~B~"
		, "<h6><span class='mw-headline' id='AB'>A</span>B</h6>"
		);
	}
	@Test   public void Tidy__no_span() {	// PURPOSE.TIDY: tidy will duplicate hdr if content has center; will fail if span/div is nearby; EX: ==<center>A</center>==\n<span><div>; PAGE:en.s:On_the_Vital_Principle/Book_2/Prelude_to_Chapter_2 DATE:2016-01-21
		fxt.Test__encode(String_.Concat_lines_nl_skip_last
		( "\"+A"
		, "<center>"
		, "<h2>A</h2>"
		, "</center>"
		, "<span class=\"mw-headline\" id=\"A\"></span>"
		, "<div style=\"color:blue;\">"
		, "<p>abc</p>"
		, "</div>"
		), String_.Concat_lines_nl_skip_last
		( "<h2><span class='mw-headline' id='A'></span></h2>"
		, "<center>"
		, "<h2>A</h2>"
		, "</center>"
		, "<span class='mw-headline' id='A'></span>"
		, "<div style='color:blue;'>"
		, "<p>abc</p>"
		, "</div>"
		));
	}
}
