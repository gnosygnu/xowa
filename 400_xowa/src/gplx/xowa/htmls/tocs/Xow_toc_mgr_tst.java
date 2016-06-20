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
package gplx.xowa.htmls.tocs; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*;
import org.junit.*; import gplx.xowa.parsers.*; import gplx.xowa.htmls.core.htmls.*;
public class Xow_toc_mgr_tst {		
	@Before public void init() {fxt.Clear();} private final    Xow_toc_mgr_fxt fxt = new Xow_toc_mgr_fxt();
	@Test   public void Basic() {
		fxt.Test_html_toc(String_.Concat_lines_nl_skip_last
		( "==a=="
		, "==b=="
		, "==c=="
		, "==d=="
		), fxt.toc_tbl_nl_y
		( "  <ul>"
		, "    <li class=\"toclevel-1 tocsection-1\"><a href=\"#a\"><span class=\"tocnumber\">1</span> <span class=\"toctext\">a</span></a>"
		, "    </li>"
		, "    <li class=\"toclevel-1 tocsection-2\"><a href=\"#b\"><span class=\"tocnumber\">2</span> <span class=\"toctext\">b</span></a>"
		, "    </li>"
		, "    <li class=\"toclevel-1 tocsection-3\"><a href=\"#c\"><span class=\"tocnumber\">3</span> <span class=\"toctext\">c</span></a>"
		, "    </li>"
		, "    <li class=\"toclevel-1 tocsection-4\"><a href=\"#d\"><span class=\"tocnumber\">4</span> <span class=\"toctext\">d</span></a>"
		, "    </li>"
		, "  </ul>"
		)
		);
	}
	@Test   public void Hier_down() {
		fxt.Test_html_toc(String_.Concat_lines_nl_skip_last
		( "==a=="
		, "===b==="
		, "====c===="
		, "=====d====="
		), fxt.toc_tbl_nl_y
		( "  <ul>"
		, "    <li class=\"toclevel-1 tocsection-1\"><a href=\"#a\"><span class=\"tocnumber\">1</span> <span class=\"toctext\">a</span></a>"
		, "    <ul>"
		, "      <li class=\"toclevel-2 tocsection-2\"><a href=\"#b\"><span class=\"tocnumber\">1.1</span> <span class=\"toctext\">b</span></a>"
		, "      <ul>"
		, "        <li class=\"toclevel-3 tocsection-3\"><a href=\"#c\"><span class=\"tocnumber\">1.1.1</span> <span class=\"toctext\">c</span></a>"
		, "        <ul>"
		, "          <li class=\"toclevel-4 tocsection-4\"><a href=\"#d\"><span class=\"tocnumber\">1.1.1.1</span> <span class=\"toctext\">d</span></a>"
		, "          </li>"
		, "        </ul>"
		, "        </li>"
		, "      </ul>"
		, "      </li>"
		, "    </ul>"
		, "    </li>"
		, "  </ul>"
		));
	}
	@Test   public void Hier_up() {
		fxt.Test_html_toc(String_.Concat_lines_nl_skip_last
		( "==a=="
		, "===b==="
		, "===c==="
		, "==d=="
		), fxt.toc_tbl_nl_y
		( "  <ul>"
		, "    <li class=\"toclevel-1 tocsection-1\"><a href=\"#a\"><span class=\"tocnumber\">1</span> <span class=\"toctext\">a</span></a>"
		, "    <ul>"
		, "      <li class=\"toclevel-2 tocsection-2\"><a href=\"#b\"><span class=\"tocnumber\">1.1</span> <span class=\"toctext\">b</span></a>"
		, "      </li>"
		, "      <li class=\"toclevel-2 tocsection-3\"><a href=\"#c\"><span class=\"tocnumber\">1.2</span> <span class=\"toctext\">c</span></a>"
		, "      </li>"
		, "    </ul>"
		, "    </li>"
		, "    <li class=\"toclevel-1 tocsection-4\"><a href=\"#d\"><span class=\"tocnumber\">2</span> <span class=\"toctext\">d</span></a>"
		, "    </li>"
		, "  </ul>"
		));
	}
	@Test   public void Down_up() {
		fxt.Test_html_toc(String_.Concat_lines_nl_skip_last
		( "==a=="
		, "===b==="
		, "==c=="
		, "===d==="
		), fxt.toc_tbl_nl_y
		( "  <ul>"
		, "    <li class=\"toclevel-1 tocsection-1\"><a href=\"#a\"><span class=\"tocnumber\">1</span> <span class=\"toctext\">a</span></a>"
		, "    <ul>"
		, "      <li class=\"toclevel-2 tocsection-2\"><a href=\"#b\"><span class=\"tocnumber\">1.1</span> <span class=\"toctext\">b</span></a>"
		, "      </li>"
		, "    </ul>"
		, "    </li>"
		, "    <li class=\"toclevel-1 tocsection-3\"><a href=\"#c\"><span class=\"tocnumber\">2</span> <span class=\"toctext\">c</span></a>"
		, "    <ul>"
		, "      <li class=\"toclevel-2 tocsection-4\"><a href=\"#d\"><span class=\"tocnumber\">2.1</span> <span class=\"toctext\">d</span></a>"
		, "      </li>"
		, "    </ul>"
		, "    </li>"
		, "  </ul>"
		));
	}
	@Test   public void D1_D1_D1_U2() {
		fxt.Test_html_toc(String_.Concat_lines_nl_skip_last
		( "==a=="
		, "===b==="
		, "====c===="
		, "==d=="
		), fxt.toc_tbl_nl_y
		( "  <ul>"
		, "    <li class=\"toclevel-1 tocsection-1\"><a href=\"#a\"><span class=\"tocnumber\">1</span> <span class=\"toctext\">a</span></a>"
		, "    <ul>"
		, "      <li class=\"toclevel-2 tocsection-2\"><a href=\"#b\"><span class=\"tocnumber\">1.1</span> <span class=\"toctext\">b</span></a>"
		, "      <ul>"
		, "        <li class=\"toclevel-3 tocsection-3\"><a href=\"#c\"><span class=\"tocnumber\">1.1.1</span> <span class=\"toctext\">c</span></a>"
		, "        </li>"
		, "      </ul>"
		, "      </li>"
		, "    </ul>"
		, "    </li>"
		, "    <li class=\"toclevel-1 tocsection-4\"><a href=\"#d\"><span class=\"tocnumber\">2</span> <span class=\"toctext\">d</span></a>"
		, "    </li>"
		, "  </ul>"
		));
	}
	@Test   public void Err() {	// PURPOSE: models strange case wherein jumping down does not work
		fxt.Test_html_toc(String_.Concat_lines_nl_skip_last
		( "==a=="
		, "====b===="
		, "===c==="
		, "====d===="
		), fxt.toc_tbl_nl_y
		( "  <ul>"
		, "    <li class=\"toclevel-1 tocsection-1\"><a href=\"#a\"><span class=\"tocnumber\">1</span> <span class=\"toctext\">a</span></a>"
		, "    <ul>"
		, "      <li class=\"toclevel-2 tocsection-2\"><a href=\"#b\"><span class=\"tocnumber\">1.1</span> <span class=\"toctext\">b</span></a>"
		, "      </li>"
		, "      <li class=\"toclevel-2 tocsection-3\"><a href=\"#c\"><span class=\"tocnumber\">1.2</span> <span class=\"toctext\">c</span></a>"
		, "      <ul>"
		, "        <li class=\"toclevel-3 tocsection-4\"><a href=\"#d\"><span class=\"tocnumber\">1.2.1</span> <span class=\"toctext\">d</span></a>"
		, "        </li>"
		, "      </ul>"
		, "      </li>"
		, "    </ul>"
		, "    </li>"
		, "  </ul>"
		));
	}
	@Test   public void Repeat_name() {
		fxt.Test_html_toc(String_.Concat_lines_nl_skip_last
		( "==a=="
		, "==a=="
		, "==a=="
		, "==a=="
		), fxt.toc_tbl_nl_y
		( "  <ul>"
		, "    <li class=\"toclevel-1 tocsection-1\"><a href=\"#a\"><span class=\"tocnumber\">1</span> <span class=\"toctext\">a</span></a>"
		, "    </li>"
		, "    <li class=\"toclevel-1 tocsection-2\"><a href=\"#a_2\"><span class=\"tocnumber\">2</span> <span class=\"toctext\">a</span></a>"
		, "    </li>"
		, "    <li class=\"toclevel-1 tocsection-3\"><a href=\"#a_3\"><span class=\"tocnumber\">3</span> <span class=\"toctext\">a</span></a>"
		, "    </li>"
		, "    <li class=\"toclevel-1 tocsection-4\"><a href=\"#a_4\"><span class=\"tocnumber\">4</span> <span class=\"toctext\">a</span></a>"
		, "    </li>"
		, "  </ul>"
		));
	}
	@Test   public void Encode() {
		fxt.Test_html_toc(String_.Concat_lines_nl_skip_last
		( "__FORCETOC__"
		, "==a+b=="
		), fxt.toc_tbl_nl_y
		( "  <ul>"
		, "    <li class=\"toclevel-1 tocsection-1\"><a href=\"#a.2Bb\"><span class=\"tocnumber\">1</span> <span class=\"toctext\">a+b</span></a>"
		, "    </li>"
		, "  </ul>"
		));
	}
	@Test   public void Ws() {
		fxt.Test_html_all(String_.Concat_lines_nl_skip_last
		( "__FORCETOC__"
		, "== a b =="
		)
		, String_.Concat_lines_nl
		( fxt.toc_tbl_nl_n
		( "  <ul>"
		, "    <li class=\"toclevel-1 tocsection-1\"><a href=\"#a_b\"><span class=\"tocnumber\">1</span> <span class=\"toctext\">a b</span></a>"
		, "    </li>"
		, "  </ul>"
		)
		, "<h2><span class='mw-headline' id='a_b'> a b </span></h2>"
		));
	}
	@Test   public void Apos_italic() {
		fxt.Test_html_all(String_.Concat_lines_nl_skip_last
		( "__FORCETOC__"
		, "==''a''=="
		)
		, String_.Concat_lines_nl_skip_last
		( fxt.toc_tbl_nl_n
		( "  <ul>"
		, "    <li class=\"toclevel-1 tocsection-1\"><a href=\"#a\"><span class=\"tocnumber\">1</span> <span class=\"toctext\"><i>a</i></span></a>"
		, "    </li>"
		, "  </ul>"
		)
		, "<h2><span class='mw-headline' id='a'><i>a</i></span></h2>"
		, ""
		));
	}
	@Test   public void Xnde_italic() {
		fxt.Test_html_all(String_.Concat_lines_nl_skip_last
		( "__FORCETOC__"
		, "==<i>a</i>=="
		)
		, String_.Concat_lines_nl
		( fxt.toc_tbl_nl_n
		( "  <ul>"
		, "    <li class=\"toclevel-1 tocsection-1\"><a href=\"#a\"><span class=\"tocnumber\">1</span> <span class=\"toctext\"><i>a</i></span></a>"
		, "    </li>"
		, "  </ul>"
		)
		, "<h2><span class='mw-headline' id='a'><i>a</i></span></h2>"
		));
	}
	@Test   public void Xnde_small() {
		fxt.Test_html_all(String_.Concat_lines_nl_skip_last
		( "__FORCETOC__"
		, "==<small>a</small>=="
		)
		, String_.Concat_lines_nl
		( fxt.toc_tbl_nl_n
		( "  <ul>"
		, "    <li class=\"toclevel-1 tocsection-1\"><a href=\"#a\"><span class=\"tocnumber\">1</span> <span class=\"toctext\">a</span></a>"
		, "    </li>"
		, "  </ul>"
		)
		, "<h2><span class='mw-headline' id='a'><small>a</small></span></h2>"
		));
	}
	@Test   public void Xnde_dangling() {	// PURPOSE: do not render dangling xndes; EX: Casualties_of_the_Iraq_War; ===<small>Iraqi Health Ministry<small>===
		fxt.Test_html_all(String_.Concat_lines_nl_skip_last
		( "__FORCETOC__"
		, "==<small>a<small>=="
		)
		, String_.Concat_lines_nl
		( fxt.toc_tbl_nl_n
		( "  <ul>"
		, "    <li class=\"toclevel-1 tocsection-1\"><a href=\"#a\"><span class=\"tocnumber\">1</span> <span class=\"toctext\">a</span></a>"
		, "    </li>"
		, "  </ul>"
		)
		, "<h2><span class='mw-headline' id='a'><small>a<small></small></small></span></h2>"
		));
	}
	@Test   public void Xnde_nest_xnde() {
		fxt.Test_html_all(String_.Concat_lines_nl_skip_last
		( "__FORCETOC__"
		, "==a <sup>b<small>c</small>d</sup> e=="
		)
		, String_.Concat_lines_nl
		( fxt.toc_tbl_nl_n
		( "  <ul>"
		, "    <li class=\"toclevel-1 tocsection-1\"><a href=\"#a_bcd_e\"><span class=\"tocnumber\">1</span> <span class=\"toctext\">a b<small>c</small>d e</span></a>"
		, "    </li>"
		, "  </ul>"
		)
		, "<h2><span class='mw-headline' id='a_bcd_e'>a <sup>b<small>c</small>d</sup> e</span></h2>"
		));
	}
	@Test   public void Xnde_nest_lnki() {
		fxt.Test_html_all(String_.Concat_lines_nl_skip_last
		( "__FORCETOC__"
		, "==<small>[[a|b]]</small>=="
		)
		, String_.Concat_lines_nl
		( fxt.toc_tbl_nl_n
		( "  <ul>"
		, "    <li class=\"toclevel-1 tocsection-1\"><a href=\"#b\"><span class=\"tocnumber\">1</span> <span class=\"toctext\">b</span></a>"
		, "    </li>"
		, "  </ul>"
		)
		, "<h2><span class='mw-headline' id='b'><small><a href=\"/wiki/A\">b</a></small></span></h2>"
		));
	}
	@Test   public void Xnde_nest_inline() {	// PURPOSE: do not render inline xndes; EX: Magnetic_resonance_imaging
		fxt.Test_html_all(String_.Concat_lines_nl_skip_last
		( "__FORCETOC__"
		, "==a<span id=\"b\">b<br/></span>=="
		)
		, String_.Concat_lines_nl
		( fxt.toc_tbl_nl_n
		( "  <ul>"
		, "    <li class=\"toclevel-1 tocsection-1\"><a href=\"#ab\"><span class=\"tocnumber\">1</span> <span class=\"toctext\">ab</span></a>"
		, "    </li>"
		, "  </ul>"
		)
		, "<h2><span class='mw-headline' id='ab'>a<span id='b'>b<br/></span></span></h2>"
		));
	}
	@Test   public void Lnki_link() {
		fxt.Test_html_all(String_.Concat_lines_nl_skip_last
		( "__FORCETOC__"
		, "==[[a]]=="
		)
		, String_.Concat_lines_nl
		( fxt.toc_tbl_nl_n
		( "  <ul>"
		, "    <li class=\"toclevel-1 tocsection-1\"><a href=\"#a\"><span class=\"tocnumber\">1</span> <span class=\"toctext\">a</span></a>"
		, "    </li>"
		, "  </ul>"
		)
		, "<h2><span class='mw-headline' id='a'><a href=\"/wiki/A\">a</a></span></h2>"
		));
	}
	@Test   public void Lnki_caption() {
		fxt.Test_html_all(String_.Concat_lines_nl_skip_last
		( "__FORCETOC__"
		, "==[[a|b]]=="
		)
		, String_.Concat_lines_nl
		( fxt.toc_tbl_nl_n
		( "  <ul>"
		, "    <li class=\"toclevel-1 tocsection-1\"><a href=\"#b\"><span class=\"tocnumber\">1</span> <span class=\"toctext\">b</span></a>"
		, "    </li>"
		, "  </ul>"
		)
		, "<h2><span class='mw-headline' id='b'><a href=\"/wiki/A\">b</a></span></h2>"
		));
	}
	@Test   public void Lnki_caption_nest() {
		fxt.Test_html_all(String_.Concat_lines_nl_skip_last
		( "__FORCETOC__"
		, "==[[a|b<i>c</i>d]]=="
		)
		, String_.Concat_lines_nl
		( fxt.toc_tbl_nl_n
		( "  <ul>"
		, "    <li class=\"toclevel-1 tocsection-1\"><a href=\"#bcd\"><span class=\"tocnumber\">1</span> <span class=\"toctext\">b<i>c</i>d</span></a>"
		, "    </li>"
		, "  </ul>"
		)
		, "<h2><span class='mw-headline' id='bcd'><a href=\"/wiki/A\">b<i>c</i>d</a></span></h2>"
		));
	}
	@Test   public void Html_ncr() {
		fxt.Test_html_all(String_.Concat_lines_nl_skip_last
		( "__FORCETOC__"
		, "==&#91;a&#93;=="
		)
		, String_.Concat_lines_nl
		( fxt.toc_tbl_nl_n
		( "  <ul>"
		, "    <li class=\"toclevel-1 tocsection-1\"><a href=\"#.5Ba.5D\"><span class=\"tocnumber\">1</span> <span class=\"toctext\">&#91;a&#93;</span></a>"
		, "    </li>"
		, "  </ul>"
		)
		, "<h2><span class='mw-headline' id='.5Ba.5D'>&#91;a&#93;</span></h2>"
		));
	}
	@Test    public void Fix_large_before_small() {	// PURPOSE.fix: "===a===\n===b===\n" followed by "==c==" causes improper formatting; DATE:2013-05-16
		fxt.Test_html_toc(String_.Concat_lines_nl_skip_last
		( "===a==="
		, "===b==="
		, "==c=="
		, "==d=="
		), fxt.toc_tbl_nl_y	// NOTE: should all be level 2
		( "  <ul>"
		, "    <li class=\"toclevel-1 tocsection-1\"><a href=\"#a\"><span class=\"tocnumber\">1</span> <span class=\"toctext\">a</span></a>"
		, "    </li>"
		, "    <li class=\"toclevel-1 tocsection-2\"><a href=\"#b\"><span class=\"tocnumber\">2</span> <span class=\"toctext\">b</span></a>"
		, "    </li>"
		, "    <li class=\"toclevel-1 tocsection-3\"><a href=\"#c\"><span class=\"tocnumber\">3</span> <span class=\"toctext\">c</span></a>"
		, "    </li>"
		, "    <li class=\"toclevel-1 tocsection-4\"><a href=\"#d\"><span class=\"tocnumber\">4</span> <span class=\"toctext\">d</span></a>"
		, "    </li>"
		, "  </ul>"
		));
	}
	@Test    public void Fix_large_before_small_2() {	// PURPOSE.fix: similar to above, but has h3 after h2; PAGE:https://en.wikipedia.org/wiki/Wikipedia:Articles_for_creation/2006-08-27 DATE:2014-06-09
		fxt.Test_html_toc(String_.Concat_lines_nl_skip_last
		( "===a_0==="
		, "==b_0=="
		, "===b_1==="
		, "==c_0=="
		), fxt.toc_tbl_nl_y
		( "  <ul>"
		, "    <li class=\"toclevel-1 tocsection-1\"><a href=\"#a_0\"><span class=\"tocnumber\">1</span> <span class=\"toctext\">a_0</span></a>"
		, "    </li>"
		, "    <li class=\"toclevel-1 tocsection-2\"><a href=\"#b_0\"><span class=\"tocnumber\">2</span> <span class=\"toctext\">b_0</span></a>"
		, "    <ul>"
		, "      <li class=\"toclevel-2 tocsection-3\"><a href=\"#b_1\"><span class=\"tocnumber\">2.1</span> <span class=\"toctext\">b_1</span></a>"
		, "      </li>"
		, "    </ul>"
		, "    </li>"
		, "    <li class=\"toclevel-1 tocsection-4\"><a href=\"#c_0\"><span class=\"tocnumber\">3</span> <span class=\"toctext\">c_0</span></a>"
		, "    </li>"
		, "  </ul>"
		));
	}
	@Test    public void Translate_and_comment() {	// PURPOSE: <translate> is an xtn and parses its innerText separately; meanwhile, toc_mgr defaults to using the innerText to build toc; EX:Wikidata:Introduction; DATE:2013-07-16
		fxt.Test_html_toc(String_.Concat_lines_nl_skip_last
		( "__FORCETOC__"
		, "==<translate><!--b-->ac</translate>=="
		), fxt.toc_tbl_nl_y
		( "  <ul>"
		, "    <li class=\"toclevel-1 tocsection-1\"><a href=\"#.3C.21--b--.3Eac\"><span class=\"tocnumber\">1</span> <span class=\"toctext\">ac</span></a>"
		, "    </li>"
		, "  </ul>"
		));
	}
	@Test   public void Ref() { // PURPOSE: ref contents should not print in TOC; DATE:2013-07-23
		fxt.Test_html_all(String_.Concat_lines_nl_skip_last
		( "__FORCETOC__"
		, "==a<ref>b</ref>=="
		)
		, String_.Concat_lines_nl
		( fxt.toc_tbl_nl_n
		( "  <ul>"
		, "    <li class=\"toclevel-1 tocsection-1\"><a href=\"#ab\"><span class=\"tocnumber\">1</span> <span class=\"toctext\">a</span></a>"
		, "    </li>"
		, "  </ul>"
		)
		, "<h2><span class='mw-headline' id='ab'>a<sup id=\"cite_ref-0\" class=\"reference\"><a href=\"#cite_note-0\">[1]</a></sup></span></h2>"
		));
	}
	@Test   public void Category() { // PURPOSE: Category should not show in in TOC; DATE:2013-12-09
		fxt.Test_html_all(String_.Concat_lines_nl_skip_last
		( "__FORCETOC__"
		, "==A[[Category:B]]=="
		)
		, String_.Concat_lines_nl
		( fxt.toc_tbl_nl_n
		( "  <ul>"
		, "    <li class=\"toclevel-1 tocsection-1\"><a href=\"#A\"><span class=\"tocnumber\">1</span> <span class=\"toctext\">A</span></a>"
		, "    </li>"
		, "  </ul>"
		)
		, "<h2><span class='mw-headline' id='A'>A</span></h2>"
		));
	}
	@Test   public void Category_literal() { // PURPOSE: literal Category should show in in TOC; EX: de.w:1234; DATE:2014-01-21
		fxt.Test_html_all(String_.Concat_lines_nl_skip_last
		( "__FORCETOC__"
		, "==A[[:Category:B]]=="
		)
		, String_.Concat_lines_nl
		( fxt.toc_tbl_nl_n
		( "  <ul>"
		, "    <li class=\"toclevel-1 tocsection-1\"><a href=\"#ACategory:B\"><span class=\"tocnumber\">1</span> <span class=\"toctext\">ACategory:B</span></a>"
		, "    </li>"
		, "  </ul>"
		)
		, "<h2><span class='mw-headline' id='ACategory:B'>A<a href=\"/wiki/Category:B\">Category:B</a></span></h2>"
		));
	}
	@Test   public void File() { // PURPOSE: file should show in in TOC; EX: tr.w:D�nya_Miraslari; DATE:2014-06-06
		fxt.Test_html_all(String_.Concat_lines_nl_skip_last
		( "__FORCETOC__"
		, "==[[File:A.png]] b=="
		)
		, String_.Concat_lines_nl
		( fxt.toc_tbl_nl_n
		( "  <ul>"
		, "    <li class=\"toclevel-1 tocsection-1\"><a href=\"#File:A.png_b\"><span class=\"tocnumber\">1</span> <span class=\"toctext\"> b</span></a>"
		, "    </li>"
		, "  </ul>"
		)
		, "<h2><span class='mw-headline' id='File:A.png_b'><a href=\"/wiki/File:A.png\" class=\"image\" xowa_title=\"A.png\"><img id=\"xoimg_0\" alt=\"\" src=\"file:///mem/wiki/repo/trg/orig/7/0/A.png\" width=\"0\" height=\"0\" /></a> b</span></h2>"
		));
	}
	@Test   public void Lnki_invalid() { // PURPOSE: invalid lnki was causing null ref; DATE:2014-02-07
		fxt.Test_html_all(String_.Concat_lines_nl_skip_last
		( "__FORCETOC__"
		, "==[[]]=="
		)
		, String_.Concat_lines_nl
		( fxt.toc_tbl_nl_n
		( "  <ul>"
		, "    <li class=\"toclevel-1 tocsection-1\"><a href=\"#.5B.5B.5D.5D\"><span class=\"tocnumber\">1</span> <span class=\"toctext\">[[]]</span></a>"
		, "    </li>"
		, "  </ul>"
		)
		, "<h2><span class='mw-headline' id='.5B.5B.5D.5D'>[[]]</span></h2>"
		));
	}
	@Test   public void File_in_tbl() { // PURPOSE: two issues (a) don't show file if in tbl; (b) if v2, file inside tbl fails; PAGE:en.w:Holmes County,_Mississippi; DATE:2014-06-22
		fxt.Test_html_frag(String_.Concat_lines_nl_skip_last
		( "__FORCETOC__"
		, "==a <table><tr><td>[[File:A.png]]b</td></tr></table> c=="
		)
		, "<span class=\"toctext\">a b c</span>"	// note that "b" inside tbl shows
		);
	}
}
class Xow_toc_mgr_fxt {		
	private Xow_toc_mgr toc_mgr = new Xow_toc_mgr();
	private Bry_bfr tmp = Bry_bfr_.New();
	public Xop_fxt Fxt() {return fxt;} private final    Xop_fxt fxt = new Xop_fxt();
	public void Clear() {
		fxt.Reset();
		toc_mgr.Clear();
		tmp.Clear();
	}
	public void Test_html_toc(String raw, String expd) {
		toc_mgr.Clear();
		byte[] raw_bry = Bry_.new_u8(raw);
		Xop_root_tkn root = fxt.Ctx().Tkn_mkr().Root(raw_bry);
		fxt.Parser().Parse_page_all_clear(root, fxt.Ctx(), fxt.Ctx().Tkn_mkr(), raw_bry);
		toc_mgr.Html(fxt.Page(), Xoh_wtr_ctx.Basic, raw_bry, tmp, true);
		Tfds.Eq_ary(String_.SplitLines_nl(expd), String_.SplitLines_nl(tmp.To_str_and_clear()), raw);
	}
	public void Test_html_all(String raw, String expd) {
		expd = Xoh_consts.Escape_apos(expd);
		fxt.Wtr_cfg().Toc__show_(Bool_.Y);
		toc_mgr.Clear();
		fxt.Test_parse_page_all_str(raw, expd);
		fxt.Wtr_cfg().Toc__show_(Bool_.N);
	}
	public void Test_html_frag(String raw, String frag) {
		fxt.Wtr_cfg().Toc__show_(Bool_.Y);
		toc_mgr.Clear();
		fxt.Test_html_full_frag(raw, frag);
		fxt.Wtr_cfg().Toc__show_(Bool_.N);
	}
	public String toc_tbl_nl_y(String... ary) {return toc_tbl(Bool_.Y, ary);}
	public String toc_tbl_nl_n(String... ary) {return toc_tbl(Bool_.N, ary);}
	public String toc_tbl(boolean nl, String... ary) {
		return String_.Concat_lines_nl_skip_last
		( "<div id=\"toc\" class=\"toc\">"
		, "  <div id=\"toctitle\">"
		, "    <h2>Contents</h2>"
		, "  </div>"
		, String_.Concat_lines_nl_skip_last(ary)
		, "</div>" + (nl ? "\n" : "")
		);
	}
}
