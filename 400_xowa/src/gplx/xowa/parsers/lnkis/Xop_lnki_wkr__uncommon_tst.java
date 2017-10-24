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
package gplx.xowa.parsers.lnkis; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import org.junit.*;
import gplx.xowa.langs.*; import gplx.xowa.langs.cases.*;
public class Xop_lnki_wkr__uncommon_tst {
	@Before public void init() {fxt.Reset(); fxt.Init_para_n_();} private final Xop_fxt fxt = new Xop_fxt();
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
	@Test  public void Triple_bracket_with_lnke_lnki() {	// PURPOSE: handle [http://a.org [[File:A.png|123px]]]; PAGE:ar.w:محمد DATE:2014-08-20
		fxt.Test_parse_page_all_str("[http://a.org [[File:A.png|123px]]]"
		,  "<a href=\"http://a.org\" rel=\"nofollow\" class=\"external text\"><a href=\"/wiki/File:A.png\" class=\"image\" xowa_title=\"A.png\"><img id=\"xoimg_0\" alt=\"\" src=\"file:///mem/wiki/repo/trg/thumb/7/0/A.png/123px.png\" width=\"123\" height=\"0\" /></a></a>"
		);
	}
	@Test  public void Multiple_captions() {	// PURPOSE: multiple captions should be concatenated (used to only take first); EX:zh.d:维基词典:Unicode字符索引/0000–0FFF; DATE:2014-05-05
		fxt.Test_parse_page_all_str("[[A|B|C|D]]"		, "<a href=\"/wiki/A\">B|C|D</a>");
	}
	@Test  public void Multiple_captions_file() {	// PURPOSE: multiple captions should take last; EX:none; DATE:2014-05-05
		fxt.Test_html_wiki_frag("[[File:A|B|C|D|thumb]]"		, "</div>D\n    </div>");
	}
	@Test  public void Multiple_captions_pipe() {	// PURPOSE.fix: multiple captions caused multiple pipes; PAGE:w:Wikipedia:Administrators'_noticeboard/IncidentArchive24; DATE:2014-06-08
		fxt.Test_parse_page_all_str("[[a|b|c d e]]", "<a href=\"/wiki/A\">b|c d e</a>");	// was b|c| |d| |e
	}
	@Test  public void Toc_fails() {	// PURPOSE: null ref when writing hdr with lnki inside xnde; EX:pl.d:head_sth_off;DATE:2014-05-07
		fxt.Test_parse_page_all_str("== <i>[[A]]</i> ==", "<h2> <i><a href=\"/wiki/A\">A</a></i> </h2>\n");
	}
	@Test  public void Upright_is_large() {	// PURPOSE: handle large upright which overflows int; PAGE:de.w:Feuerland DATE:2015-02-03
		fxt.Test_html_wiki_frag("[[File:A.png|upright=1.333333333333333333333333333333333333333333333333333333333333333333333]]", " width=\"0\" height=\"0\"");	// failure would print out original lnki
	}
	@Test  public void Persian() {	// PURPOSE: handle il8n nums; EX:[[پرونده:Shahbazi 3.jpg|۲۰۰px]] -> 200px; PAGE:fa.w:فهرست_آثار_علیرضا_شاپور_شهبازی; DATE:2015-07-18
		Xol_lang_itm lang = fxt.Wiki().Lang();
		fxt.App().Gfs_mgr().Run_str_for(lang, gplx.xowa.xtns.pfuncs.numbers.Pf_formatnum_fa_tst.Persian_numbers_gfs);
		lang.Evt_lang_changed();	// force rebuild of size_trie
		fxt.Test_html_wiki_frag("[[File:A.png|۲۰۰px]]", " width=\"200\" height=\"0\"");
	}
}

