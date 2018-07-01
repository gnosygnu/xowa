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
package gplx.xowa.parsers.lists; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import org.junit.*;
public class Xop_list_wkr_basic_tst {
	private final    Xop_fxt fxt = new Xop_fxt();
	@After public void term() {fxt.Init_para_n_();}
	@Test  public void List_1() {
		fxt.Test_parse_page_wiki("\n*a"
			,	fxt.tkn_list_bgn_(0, 2, Xop_list_tkn_.List_itmTyp_ul).List_path_(0).List_uid_(0)
			,	fxt.tkn_txt_(2, 3)
			,	fxt.tkn_list_end_(3).List_path_(0).List_uid_(0)
			);
	}
	@Test  public void Bos() {
		fxt.Test_parse_page_wiki("*a"
			,	fxt.tkn_list_bgn_(0, 1, Xop_list_tkn_.List_itmTyp_ul).List_path_(0).List_uid_(0)
			,	fxt.tkn_txt_(1, 2)
			,	fxt.tkn_list_end_(2).List_path_(0).List_uid_(0)
			);
	}
	@Test  public void List_1_2() {
		fxt.Test_parse_page_wiki("\n*a\n**b"
			,	fxt.tkn_list_bgn_(0, 2, Xop_list_tkn_.List_itmTyp_ul).List_path_(0).List_uid_(0)
			,		fxt.tkn_txt_(2, 3)
			,		fxt.tkn_list_bgn_(3, 6, Xop_list_tkn_.List_itmTyp_ul).List_path_(0, 0).List_uid_(0)
			,			fxt.tkn_txt_(6, 7)
			,		fxt.tkn_list_end_(7).List_path_(0, 0)
			,	fxt.tkn_list_end_(7).List_path_(0)
			);
	}
	@Test  public void List_1_2_2() {
		fxt.Test_parse_page_wiki("\n*a\n**b\n**c"
			,	fxt.tkn_list_bgn_(0, 2, Xop_list_tkn_.List_itmTyp_ul).List_path_(0).List_uid_(0)
			,		fxt.tkn_txt_(2, 3)
			,		fxt.tkn_list_bgn_(3, 6, Xop_list_tkn_.List_itmTyp_ul).List_path_(0, 0).List_uid_(0)
			,			fxt.tkn_txt_(6, 7)
			,		fxt.tkn_list_end_(7).List_path_(0, 0)
			,		fxt.tkn_list_bgn_(7, 10, Xop_list_tkn_.List_itmTyp_ul).List_path_(0, 1).List_uid_(0)
			,			fxt.tkn_txt_(10, 11)
			,		fxt.tkn_list_end_(11).List_path_(0, 1)
			,	fxt.tkn_list_end_(11).List_path_(0)
			);
	}
	@Test  public void List_1_2_3() {
		fxt.Test_parse_page_wiki("\n*a\n**b\n***c"
			,	fxt.tkn_list_bgn_(0, 2, Xop_list_tkn_.List_itmTyp_ul).List_path_(0).List_uid_(0)
			,		fxt.tkn_txt_(2, 3)
			,		fxt.tkn_list_bgn_(3, 6, Xop_list_tkn_.List_itmTyp_ul).List_path_(0, 0).List_uid_(0)
			,			fxt.tkn_txt_(6, 7)
			,			fxt.tkn_list_bgn_(7, 11, Xop_list_tkn_.List_itmTyp_ul).List_path_(0, 0, 0).List_uid_(0)
			,				fxt.tkn_txt_(11, 12)
			,			fxt.tkn_list_end_(12).List_path_(0, 0, 0)
			,		fxt.tkn_list_end_(12).List_path_(0, 0)
			,	fxt.tkn_list_end_(12).List_path_(0)
			);
	}
	@Test  public void List_2() {
		fxt.Test_parse_page_wiki("\n**a"
			,	fxt.tkn_list_bgn_(0,  3, Xop_list_tkn_.List_itmTyp_ul).List_path_(0).List_uid_(0)
			,		fxt.tkn_list_bgn_(0,  3, Xop_list_tkn_.List_itmTyp_ul).List_path_(0, 0).List_uid_(0)
			,		fxt.tkn_txt_(3, 4)
			,		fxt.tkn_list_end_(4).List_path_(0, 0)
			,	fxt.tkn_list_end_(4).List_path_(0)
			);
	}
	@Test  public void List_1_3() {
		fxt.Test_parse_page_wiki("\n*a\n***b"
			,	fxt.tkn_list_bgn_(0, 2, Xop_list_tkn_.List_itmTyp_ul).List_path_(0).List_uid_(0)
			,		fxt.tkn_txt_(2, 3)
			,		fxt.tkn_list_bgn_(3, 7, Xop_list_tkn_.List_itmTyp_ul).List_path_(0, 0).List_uid_(0)
			,			fxt.tkn_list_bgn_(3, 7, Xop_list_tkn_.List_itmTyp_ul).List_path_(0, 0, 0).List_uid_(0)
			,				fxt.tkn_txt_(7, 8)
			,			fxt.tkn_list_end_(8).List_path_(0, 0, 0)
			,		fxt.tkn_list_end_(8).List_path_(0, 0)
			,	fxt.tkn_list_end_(8).List_path_(0)
			);
	}
	@Test  public void List_1_2_1() {
		fxt.Test_parse_page_wiki("\n*a\n**b\n*c"
			,	fxt.tkn_list_bgn_(0, 2, Xop_list_tkn_.List_itmTyp_ul).List_path_(0).List_uid_(0)
			,		fxt.tkn_txt_(2, 3)
			,		fxt.tkn_list_bgn_(3, 6, Xop_list_tkn_.List_itmTyp_ul).List_path_(0, 0).List_uid_(0)
			,			fxt.tkn_txt_(6, 7)
			,		fxt.tkn_list_end_(7).List_path_(0, 0)
			,	fxt.tkn_list_end_(7).List_path_(0)
			,	fxt.tkn_list_bgn_(7, 9, Xop_list_tkn_.List_itmTyp_ul).List_path_(1).List_uid_(0)
			,		fxt.tkn_txt_(9, 10)
			,	fxt.tkn_list_end_(10).List_path_(1)
			);
	}
	@Test  public void List_1_1_1() {
		fxt.Test_parse_page_wiki("\n*a\n*b\n*c"
			,	fxt.tkn_list_bgn_(0, 2, Xop_list_tkn_.List_itmTyp_ul).List_path_(0).List_uid_(0)
			,		fxt.tkn_txt_(2, 3)
			,	fxt.tkn_list_end_(3).List_path_(0)
			,	fxt.tkn_list_bgn_(3, 5, Xop_list_tkn_.List_itmTyp_ul).List_path_(1).List_uid_(0)
			,		fxt.tkn_txt_(5, 6)
			,	fxt.tkn_list_end_(6).List_path_(1)
			,	fxt.tkn_list_bgn_(6, 8, Xop_list_tkn_.List_itmTyp_ul).List_path_(2).List_uid_(0)
			,		fxt.tkn_txt_(8, 9)
			,	fxt.tkn_list_end_(9).List_path_(2)
			);
	}
	@Test  public void List_1___1() {
		fxt.Test_parse_page_wiki("\n*a\n\n*b"
			, fxt.tkn_list_bgn_(0, 2, Xop_list_tkn_.List_itmTyp_ul).List_path_(0).List_uid_(0)
			, fxt.tkn_txt_(2, 3)
			, fxt.tkn_list_end_(3).List_path_(0)
			, fxt.tkn_nl_char_len1_(3)
			, fxt.tkn_list_bgn_(4, 6, Xop_list_tkn_.List_itmTyp_ul).List_path_(0).List_uid_(1)
			, fxt.tkn_txt_(6, 7)
			, fxt.tkn_list_end_(7).List_path_(0)
			);
	}
	@Test  public void List_1_3_1() {
		fxt.Test_parse_page_wiki("\n*a\n***b\n*c"
			, fxt.tkn_list_bgn_(0, 2, Xop_list_tkn_.List_itmTyp_ul).List_path_(0).List_uid_(0)
			, fxt.tkn_txt_(2, 3)
			,	fxt.tkn_list_bgn_(3, 7, Xop_list_tkn_.List_itmTyp_ul).List_path_(0, 0).List_uid_(0)
			,		fxt.tkn_list_bgn_(3, 7, Xop_list_tkn_.List_itmTyp_ul).List_path_(0, 0, 0).List_uid_(0)
			,			fxt.tkn_txt_(7, 8)
			,		fxt.tkn_list_end_(8).List_path_(0, 0, 0)
			,	fxt.tkn_list_end_(8).List_path_(0, 0)
			, fxt.tkn_list_end_(8).List_path_(0)
			, fxt.tkn_list_bgn_(8, 10, Xop_list_tkn_.List_itmTyp_ul).List_path_(1).List_uid_(0)
			, fxt.tkn_txt_(10, 11)
			, fxt.tkn_list_end_(11).List_path_(1)
			);
	}
	@Test  public void Mix_2o_2u() {
		fxt.Test_parse_page_wiki("\n**a\n##b"
			,	fxt.tkn_list_bgn_(0,  3, Xop_list_tkn_.List_itmTyp_ul).List_path_(0).List_uid_(0)
			,		fxt.tkn_list_bgn_(0,  3, Xop_list_tkn_.List_itmTyp_ul).List_path_(0, 0).List_uid_(0)
			,			fxt.tkn_txt_(3, 4)
			,		fxt.tkn_list_end_(4).List_path_(0, 0)
			,	fxt.tkn_list_end_(4).List_path_(0)
			,	fxt.tkn_list_bgn_(4,  7, Xop_list_tkn_.List_itmTyp_ol).List_path_(0).List_uid_(1)
			,		fxt.tkn_list_bgn_(4,  7, Xop_list_tkn_.List_itmTyp_ol).List_path_(0, 0).List_uid_(1)
			,			fxt.tkn_txt_(7, 8)
			,		fxt.tkn_list_end_(8).List_path_(0, 0)
			,	fxt.tkn_list_end_(8).List_path_(0)
			);
	}
	@Test  public void Dt_dd() {
		fxt.Test_parse_page_wiki(";a\n:b"
			,	fxt.tkn_list_bgn_(0,  1, Xop_list_tkn_.List_itmTyp_dt).List_path_(0).List_uid_(0)
			,		fxt.tkn_txt_(1, 2)
			,	fxt.tkn_list_end_(2).List_path_(0)
			,	fxt.tkn_list_bgn_(2,  4, Xop_list_tkn_.List_itmTyp_dd).List_path_(1).List_uid_(0)
			,		fxt.tkn_txt_(4, 5)
			,	fxt.tkn_list_end_(5).List_path_(1)
			);
	}
	@Test  public void Dt_dd_inline() {
		fxt.Test_parse_page_wiki(";a:b" // NOTE: no line break
			,	fxt.tkn_list_bgn_(0, 1, Xop_list_tkn_.List_itmTyp_dt).List_path_(0).List_uid_(0)
			,		fxt.tkn_txt_(1, 2)
			,	fxt.tkn_list_end_(2).List_path_(0)
			,	fxt.tkn_list_bgn_(2, 3, Xop_list_tkn_.List_itmTyp_dd).List_path_(1).List_uid_(0)
			,		fxt.tkn_txt_(3, 4)
			,	fxt.tkn_list_end_(4).List_path_(1)
			);
	}
	@Test  public void Mix_1dd_1ul() {
		fxt.Test_parse_page_wiki(":*a"
			,	fxt.tkn_list_bgn_(0, 2, Xop_list_tkn_.List_itmTyp_dd).List_path_(0).List_uid_(0)
			,		fxt.tkn_list_bgn_(0, 2, Xop_list_tkn_.List_itmTyp_ul).List_path_(0, 0).List_uid_(0)
			,			fxt.tkn_txt_(2, 3)
			,		fxt.tkn_list_end_(3).List_path_(0, 0)
			,	fxt.tkn_list_end_(3).List_path_(0)
			);
	}
	@Test  public void Mix_1ul__1dd_1ul() {
		fxt.Test_parse_page_wiki("*a\n:*b"
			,	fxt.tkn_list_bgn_(0, 1, Xop_list_tkn_.List_itmTyp_ul).List_path_(0).List_uid_(0)
			,		fxt.tkn_txt_(1, 2)
			,	fxt.tkn_list_end_(2).List_path_(0).List_uid_(0)
			,	fxt.tkn_list_bgn_(2, 5, Xop_list_tkn_.List_itmTyp_dd).List_path_(0).List_uid_(1)
			,		fxt.tkn_list_bgn_(2, 5, Xop_list_tkn_.List_itmTyp_ul).List_path_(0, 0).List_uid_(1)
			,			fxt.tkn_txt_(5, 6)
			,		fxt.tkn_list_end_(6).List_path_(0, 0)
			,	fxt.tkn_list_end_(6).List_path_(0)
			);
	}
	@Test  public void Mix_1dd_1ul__1dd_1ul() {
		fxt.Test_parse_page_wiki(":*a\n:*b"
			,	fxt.tkn_list_bgn_(0, 2, Xop_list_tkn_.List_itmTyp_dd).List_path_(0).List_uid_(0)
			,		fxt.tkn_list_bgn_(0, 2, Xop_list_tkn_.List_itmTyp_ul).List_path_(0, 0).List_uid_(0)
			,			fxt.tkn_txt_(2, 3)
			,		fxt.tkn_list_end_(3).List_path_(0, 0)
			,		fxt.tkn_list_bgn_(3, 6, Xop_list_tkn_.List_itmTyp_ul).List_path_(0, 1).List_uid_(0)
			,			fxt.tkn_txt_(6, 7)
			,		fxt.tkn_list_end_(7).List_path_(0, 1)
			,	fxt.tkn_list_end_(7).List_path_(0)
			);
	}
	@Test  public void Mix_1ul_1hdr() {
		fxt.Test_parse_page_wiki_str("*a\n==a==\n", String_.Concat_lines_nl_skip_last
		( "<ul>"
		, "  <li>a"
		, "  </li>"
		, "</ul>"
		, ""
		, "<h2>a</h2>"
		));
	}
	@Test  public void Mix_1ul_1hdr_1ul() {
		fxt.Test_parse_page_wiki_str("*a\n==a==\n*b", String_.Concat_lines_nl_skip_last
		( "<ul>"
		, "  <li>a"
		, "  </li>"
		, "</ul>"
		, ""
		, "<h2>a</h2>"
		, ""
		, "<ul>"
		, "  <li>b"
		, "  </li>"
		, "</ul>"
		));
	}
	@Test  public void Mix_1ol_1hr_1ol() {
		fxt.Test_parse_page_wiki("#a\n----\n#b"
			,	fxt.tkn_list_bgn_(0, 1, Xop_list_tkn_.List_itmTyp_ol).List_path_(0).List_uid_(0)
			,		fxt.tkn_txt_(1, 2)
			,	fxt.tkn_list_end_(2)
			,	fxt.tkn_para_blank_(2)
			,	fxt.tkn_hr_(2, 7)
			,	fxt.tkn_list_bgn_(7, 9, Xop_list_tkn_.List_itmTyp_ol).List_path_(0).List_uid_(1)
			,		fxt.tkn_txt_(9, 10)
			,	fxt.tkn_list_end_(10)
			);			
	}
	@Test  public void Mix_tblw() {
		fxt.Test_parse_page_wiki("::{|\n|a\n|}"
			,	fxt.tkn_list_bgn_(0, 2, Xop_list_tkn_.List_itmTyp_dd).List_path_(0).List_uid_(0)
			,	fxt.tkn_list_bgn_(0, 2, Xop_list_tkn_.List_itmTyp_dd).List_path_(0, 0).List_uid_(0)
			,	fxt.tkn_tblw_tb_(2, 10).Subs_
			(		fxt.tkn_tblw_tr_(4, 7).Subs_
			(			fxt.tkn_tblw_td_(4, 7).Subs_(fxt.tkn_txt_(6, 7), fxt.tkn_para_blank_(8)))
			
			)
			,	fxt.tkn_list_end_(10).List_path_(0, 0)
			,	fxt.tkn_list_end_(10).List_path_(0)
			);
	}
	@Test  public void Mix_tblw_w_space() {
		fxt.Test_html_full_str(": {|\n|a\n|}", String_.Concat_lines_nl_skip_last
		( "<dl>"
		, "  <dd>"
		, "    <table>"
		, "      <tr>"
		, "        <td>a"
		, "        </td>"
		, "      </tr>"
		, "    </table>"
		, "  </dd>"
		, "</dl>"
		));
	}
	@Test  public void Dif_lvls_1_3_1() {
		fxt.Test_parse_page_all_str(String_.Concat_lines_nl_skip_last
			(	"*1"
			,	"***3"
			,	"*1"
			) ,	String_.Concat_lines_nl_skip_last
			(	"<ul>"
			,	"  <li>1"
			,	"    <ul>"
			,	"      <li>"
			,	"        <ul>"
			,	"          <li>3"
			,	"          </li>"
			,	"        </ul>"
			,	"      </li>"
			,	"    </ul>"
			,	"  </li>"
			,	"  <li>1"
			,	"  </li>"
			,	"</ul>"
			));
	}
	@Test  public void Dif_lvls_1_3_2() {// uneven lists
		fxt.Test_parse_page_all_str(String_.Concat_lines_nl_skip_last
			(	"*1"
			,	"***3"
			,	"**2"
			) ,	String_.Concat_lines_nl_skip_last
			(	"<ul>"
			,	"  <li>1"
			,	"    <ul>"
			,	"      <li>"
			,	"        <ul>"
			,	"          <li>3"
			,	"          </li>"
			,	"        </ul>"
			,	"      </li>"
			,	"      <li>2"
			,	"      </li>"
			,	"    </ul>"
			,	"  </li>"
			,	"</ul>"
			));
	}
	@Test  public void New_lines() {
		fxt.Test_parse_page_all_str(String_.Concat_lines_nl_skip_last
			(	"*a"
			,	""
			,	"**b"
			,	""
			,	"**c"
			) ,	String_.Concat_lines_nl_skip_last
			(	"<ul>"
			,	"  <li>a"
			,	"  </li>"
			,	"</ul>"
			,	""
			,	"<ul>"
			,	"  <li>"
			,	"    <ul>"
			,	"      <li>b"
			,	"      </li>"
			,	"    </ul>"
			,	"  </li>"
			,	"</ul>"
			,	""
			,	"<ul>"
			,	"  <li>"
			,	"    <ul>"
			,	"      <li>c"
			,	"      </li>"
			,	"    </ul>"
			,	"  </li>"
			,	"</ul>"
			));
	}
}
