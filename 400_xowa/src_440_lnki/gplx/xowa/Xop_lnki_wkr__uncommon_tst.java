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
package gplx.xowa; import gplx.*;
import org.junit.*;
import gplx.xowa.langs.cases.*;
public class Xop_lnki_wkr__uncommon_tst {
	@Before public void init() {fxt.Reset(); fxt.Init_para_n_();} private Xop_fxt fxt = new Xop_fxt();
	@Test  public void Double_bracket() {	// PURPOSE: handle [[[[A]]]] constructions; PAGE:ru.w:Меркатале_ин_Валь_ди_Песа; DATE:2014-02-04
		fxt.Test_parse_page_all_str("[[[[Test_1]]]]"		, "[[<a href=\"/wiki/Test_1\">Test_1</a>]]");
	}
	@Test  public void Single_bracket() {		// PURPOSE: handle [[A|[b]]] PAGE:en.w:Aubervilliers DATE:2014-06-25
		fxt.Test_html_full_str("[[A|[B]]]", "<a href=\"/wiki/A\">[B]</a>");
	}
	@Test  public void Triple_bracket() {	// PURPOSE: "]]]" shouldn't invalidate tkn; PAGE:en.w:Tall_poppy_syndrome; DATE:2014-07-23
		fxt.Test_parse_page_all_str("[[A]]]"	,  "<a href=\"/wiki/A\">A</a>]");	// title only
		fxt.Test_parse_page_all_str("[[A|B]]]"	,  "<a href=\"/wiki/A\">B]</a>");	// title + caption; note that ] should be outside </a> b/c MW has more logic that says "if caption starts with '['", but leaving as is; DATE:2014-07-23
	}
	@Test  public void Multiple_captions() {	// PURPOSE: multiple captions should be concatenated (used to only take first); EX:zh.d:维基词典:Unicode字符索引/0000–0FFF; DATE:2014-05-05
		fxt.Test_parse_page_all_str("[[A|B|C|D]]"		, "<a href=\"/wiki/A\">B|C|D</a>");
	}
	@Test  public void Multiple_captions_file() {	// PURPOSE: multiple captions should take last; EX:none; DATE:2014-05-05
		fxt.Test_html_wiki_frag("[[File:A|B|C|D|thumb]]"		, "</div>\n      D\n    </div>");
	}
	@Test  public void Multiple_captions_pipe() {	// PURPOSE.fix: multiple captions caused multiple pipes; PAGE:w:Wikipedia:Administrators'_noticeboard/IncidentArchive24; DATE:2014-06-08
		fxt.Test_parse_page_all_str("[[a|b|c d e]]", "<a href=\"/wiki/A\">b|c d e</a>");	// was b|c| |d| |e
	}
	@Test  public void Toc_fails() {	// PURPOSE: null ref when writing hdr with lnki inside xnde; EX:pl.d:head_sth_off;DATE:2014-05-07
		fxt.Test_parse_page_all_str("== <i>[[A]]</i> ==", "<h2> <i><a href=\"/wiki/A\">A</a></i> </h2>\n");
	}
	@Test  public void Large_size() {	// PURPOSE: size larger than int should be discard, not be Int_.MaxValue: PAGE:id.w:Baho; DATE:2014-06-10
		fxt.Test_html_wiki_frag("[[File:A.png|9999999999x30px]]", " width=\"0\" height=\"30\"");	// width should not be Int_.MaxValue
	}
}

