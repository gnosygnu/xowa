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
package gplx.xowa.parsers.miscs; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import org.junit.*;
public class Xop_hr_lxr_basic_tst {
	@Before public void init() {fxt.Reset();} private final Xop_fxt fxt = new Xop_fxt();
	@Test   public void Basic()					{fxt.Test_parse_page_wiki("----"				, fxt.tkn_hr_(0, 4));}
	@Test   public void Basic_w_nl()			{fxt.Test_parse_page_wiki("\n----a"				, fxt.tkn_para_blank_(0), fxt.tkn_hr_(0, 5), fxt.tkn_txt_(5, 6));}
	@Test   public void Many()					{fxt.Test_parse_page_wiki("---------"			, fxt.tkn_hr_(0, 9).Hr_len_(9));}
	@Test   public void Exc_short()				{fxt.Test_parse_page_wiki("---"					, fxt.tkn_txt_(0, 3));}
	@Test   public void Exc_interrupt()			{fxt.Test_parse_page_wiki("\na----"				, fxt.tkn_nl_char_len1_(0), fxt.tkn_txt_(1, 6));}
	@Test   public void Html_basic()			{fxt.Test_parse_page_wiki_str("----"			, "<hr/>");}
	@Test   public void Html_extended()			{fxt.Test_parse_page_wiki_str("------"			, "<hr/>");}
	@Test   public void Nl_bgn()				{fxt.Test_parse_page_wiki_str("a\n----"			, "a\n<hr/>");}
	@Test   public void Nl_end()				{fxt.Test_parse_page_wiki_str("----\na"			, "<hr/>\na");}
}
