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
package gplx.xowa.parsers.paras; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import org.junit.*; import gplx.xowa.parsers.lists.*;
public class Xop_para_wkr_basic_tst {
	private final    Xop_fxt fxt = new Xop_fxt(); String raw;
	@Before public void init() {fxt.Reset(); fxt.Init_para_y_();}
	@After public void teardown() {fxt.Init_para_n_();}
	@Test  public void Nl_0() {
		raw = "a";
		fxt.Test_parse_page_wiki_str(raw, String_.Concat_lines_nl_skip_last
			( "<p>a"
			, "</p>"
			));
		fxt.Test_parse_page_wiki(raw
			,	fxt.tkn_para_bgn_para_(0)													//    x0: <bos> -> <p>			1=blank
			,	fxt.tkn_txt_(0, 1), fxt.tkn_para_end_para_(1)								// t2/x1: a     -> a\n</p>		1=bgn    2=blank
			);
	}
	@Test  public void Nl_1() {
		raw = String_.Concat_lines_nl_skip_last
			( "a"
			, "b"
			);
		fxt.Test_parse_page_wiki_str(raw, String_.Concat_lines_nl_skip_last
			( "<p>a"
			, "b"			// NOTE: depend on html editor removing \n between a and b
			, "</p>"
			));
		fxt.Test_parse_page_wiki(raw
			,	fxt.tkn_para_bgn_para_(0)													//    x0: <bos> -> <p>			1=blank
			,	fxt.tkn_txt_(0, 1), fxt.tkn_para_blank_(2)									// t2/x1: a\n   -> a\n<-p>		1=bgn    2=blank
			,	fxt.tkn_txt_(2, 3), fxt.tkn_para_end_para_(3)								// t3/x1: b     -> b\n</p>		2=blank  3=blank
			);
	}
	@Test  public void Nl_2() {
		raw = String_.Concat_lines_nl_skip_last
			( "a"
			, ""
			, "b"
			);
		fxt.Test_parse_page_wiki_str(raw, String_.Concat_lines_nl_skip_last
			( "<p>a"
			, "</p>"
			, ""
			, "<p>b"
			, "</p>"
			));
		fxt.Test_parse_page_wiki(raw
			,	fxt.tkn_para_bgn_para_(0)													//    x0: <bos> -> <p>			1=blank
			,	fxt.tkn_txt_(0, 1), fxt.tkn_para_blank_(2)									// t2/x1: a\n   -> a\n<-p>		1=bgn
			,	fxt.tkn_para_mid_para_(3)													// n3   : \n    -> </p><p>      3=mid    NOTE: </p><p> doesn't get converted until t1
			,	fxt.tkn_txt_(3, 4), fxt.tkn_para_end_para_(4)								// t1/x1: b     -> b\n</p>		2=mid    3=blank
			);
	}
	@Test  public void Nl_3() {
		raw = String_.Concat_lines_nl_skip_last
			( "a"
			, ""
			, ""
			, "b"
			);
		fxt.Test_parse_page_wiki_str(raw, String_.Concat_lines_nl_skip_last
			( "<p>a"
			, "</p>"
			, ""
			, "<p><br/>"	// NOTE: this looks strange, but it emulates MW
			, "b"
			, "</p>"
			));
		fxt.Test_parse_page_wiki(raw
			,	fxt.tkn_para_bgn_para_(0)							//    x0: <bos> -> <p>			1=blank
			,	fxt.tkn_txt_(0, 1), fxt.tkn_para_blank_(2)			// t2/x1: a\n   -> a\n<-p>		1=bgn 
			,	fxt.tkn_para_mid_para_(3)							// n3   : \n    -> </p><p>      3=mid    NOTE: </p><p> doesn't get converted until n1
			,	fxt.tkn_xnde_br_(3), fxt.tkn_para_blank_(4)			// n1/x1: \n	-> <br/>\n<-p>	2=mid    3=blank
			,	fxt.tkn_txt_(4, 5), fxt.tkn_para_end_para_(5)		// t1/x1: b     -> b\n</p>		4=blank
			);
	}
	@Test  public void Nl_4() {
		raw = String_.Concat_lines_nl_skip_last
			( "a"
			, ""
			, "b"
			, ""
			, "c"
			);
		fxt.Test_parse_page_wiki_str(raw, String_.Concat_lines_nl_skip_last
			( "<p>a"
			, "</p>"
			, ""
			, "<p>b"
			, "</p>"
			, ""
			, "<p>c"
			, "</p>"
			));
		fxt.Test_parse_page_wiki(raw
			,	fxt.tkn_para_bgn_para_(0)							//    x0: <bos> -> <p>			1=blank
			,	fxt.tkn_txt_(0, 1), fxt.tkn_para_blank_(2)			// t2/x1: a\n   -> a\n<-p>		1=bgn
			,	fxt.tkn_para_mid_para_(3)							// n3   : \n    -> </p><p>      3=mid    NOTE: </p><p> doesn't get converted until n1
			,	fxt.tkn_txt_(3, 4), fxt.tkn_para_blank_(5)
			,	fxt.tkn_para_mid_para_(6)
			,	fxt.tkn_txt_(6, 7), fxt.tkn_para_end_para_(7)
			);
	}
	@Test  public void Nl_5() {
		raw = String_.Concat_lines_nl_skip_last
			( "<p>a"
			, "b"
			, "</p>"
			);
		fxt.Test_parse_page_wiki_str(raw, String_.Concat_lines_nl_skip_last
			( "<p>a"
			, "b"
			, "</p>"
			));
	}
	@Test  public void Pre() {
		raw = String_.Concat_lines_nl_skip_last
			( "a"
			, " b"
			, "c"
			);
		fxt.Test_parse_page_wiki_str(raw, String_.Concat_lines_nl_skip_last
			( "<p>a"
			, "</p>"
			, ""
			, "<pre>b"
			, "</pre>"
			, ""
			, "<p>c"
			, "</p>"
			));			
		fxt.Test_parse_page_wiki(raw
			,	fxt.tkn_para_bgn_para_(0)
			,	fxt.tkn_txt_(0, 1), fxt.tkn_para_end_para_bgn_pre_(2)
			,	fxt.tkn_txt_(3, 4),	fxt.tkn_para_end_pre_bgn_para_(5)
			,	fxt.tkn_txt_(5, 6), fxt.tkn_para_end_para_(6)
			);
	}
	@Test  public void Pre_2() {
		raw = String_.Concat_lines_nl_skip_last
			( "a"
			, " b"
			, " c"
			, "d"
			);
		fxt.Test_parse_page_wiki_str(raw, String_.Concat_lines_nl_skip_last
			( "<p>a"
			, "</p>"
			, ""
			, "<pre>b"
			, "c"
			, "</pre>"
			, ""
			, "<p>d"
			, "</p>"
			));			
	}
	@Test  public void Pre_3() {
		raw = String_.Concat_lines_nl_skip_last
			( "a"
			, ""
			, "b"
			, " c"
			);
		fxt.Test_parse_page_wiki_str(raw, String_.Concat_lines_nl_skip_last
			( "<p>a"
			, "</p>"
			, ""
			, "<p>b"
			, "</p>"
			, ""
			, "<pre>c"
			, "</pre>"
			));			
	}
	@Test  public void Pre_4() {
		raw = String_.Concat_lines_nl_skip_last
			( "a"
			, ""
			, " b"
			, ""
			, "c"
			);
		fxt.Test_parse_page_wiki_str(raw, String_.Concat_lines_nl_skip_last
			( "<p>a"
			, "</p>"
			, ""
			, "<pre>b"
			, "</pre>"
			, ""
			, "<p>c"
			, "</p>"
			));			
	}
	@Test  public void Pre_5() {	// PAGE:en.w:SHA-2
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last
			( "a"
			, " b"
			, ""
			, " c"
			), String_.Concat_lines_nl_skip_last
			( "<p>a"
			, "</p>"
			, ""
			, "<pre>b"
			, "</pre>"
			, ""
			, "<pre>c"
			, "</pre>"
			));			
	}
	@Test  public void Pre_6() {	// PURPOSE: close list if open; PAGE:en.w:SHA-2
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last
			( "*a"
			, " b"
			), String_.Concat_lines_nl_skip_last
			( "<ul>"
			, "  <li>a"
			, "  </li>"
			, "</ul>"
			, ""
			, "<pre>b"
			, "</pre>"
			));			
	}
	@Test  public void Pre_init() {
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last
			( " a"
			), String_.Concat_lines_nl_skip_last
			( "<pre>a"
			, "</pre>"
			));
	}
	@Test  public void Pre_leading_ws() {	// PURPOSE: preserve leading ws; PAGE:en.w:Merge sort
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last
			( " a"
			, "   b"
			, "     c"
			), String_.Concat_lines_nl_skip_last
			( "<pre>a"
			, "  b"
			, "    c"
			, "</pre>"
			));			
	}
	@Test  public void Pre_newLine() {	// PURPOSE: "\n \n" inside pre should be ignored; trims to ""
		raw = String_.Concat_lines_nl_skip_last
			( "a"
			, " b"
			, " "
			, " c"
			, "d"
			);
		fxt.Test_parse_page_wiki_str(raw, String_.Concat_lines_nl_skip_last
			( "<p>a"
			, "</p>"
			, ""
			, "<pre>b"
			, ""
			, "c"
			, "</pre>"
			, ""
			, "<p>d"
			, "</p>"
			));
	}
	@Test  public void Pre_xnde() {	// PURPOSE: <div> and other xndes should invalidate pre on same line; EX: "\n a<div>b"; SEE: any {{refimprove}} article
		raw = String_.Concat_lines_nl_skip_last
			( "a"
			, " b<div>c</div>d"
			, "e"
			);
		fxt.Test_parse_page_wiki_str(raw, String_.Concat_lines_nl_skip_last
			( "<p>a"
			, "</p>"
			, " b<div>c</div>d"
			, ""
			, "<p>e"
			, "</p>"
			));
	}
	@Test  public void Pre_lnki_in_td() {	// PURPOSE: ]] in td causes strange parsing; SEE: any {{Commons category}} article; updated test; DATE:2015-03-31
		raw = String_.Concat_lines_nl_skip_last
			( "<table>"
			, "<tr>"
			, "<td>[[File:A.png|alt="
			, " ]]</td>"
			, "<td>b"
			, "</td>"
			, "</tr>"
			, "</table>"
			);
		fxt.Test_parse_page_wiki_str(raw, String_.Concat_lines_nl_skip_last
			( "<table>"
			, "  <tr>"
			, "    <td><a href=\"/wiki/File:A.png\" class=\"image\" xowa_title=\"A.png\"><img id=\"xoimg_0\" alt=\"  \" src=\"file:///mem/wiki/repo/trg/orig/7/0/A.png\" width=\"0\" height=\"0\" /></a>"
			, "    </td>"
			, "    <td>b"
			, "    </td>"
			, "  </tr>"
			, "</table>"
			));
	}
	@Test  public void Pre_trailing_end() {	// PURPOSE: "\n " at end was failing
		raw = String_.Concat_lines_nl_skip_last
			( "a"
			, " "
			);
		fxt.Test_parse_page_wiki_str(raw, String_.Concat_lines_nl_skip_last
			( "<p>a"
			, "</p>"
			));
	}
	@Test  public void Pre_xnde_code() {	// PAGE:en.w:cURL
		raw = String_.Concat_lines_nl_skip_last
			( "a"
			, " <code>b</code>"
			, ""
			, "c"
			);
		fxt.Test_parse_page_wiki_str(raw, String_.Concat_lines_nl_skip_last
			( "<p>a"
			, "</p>"
			, ""
			, "<pre><code>b</code>"
			, "</pre>"
			, ""
			, "<p>c"
			, "</p>"
			));
	}
	@Test  public void Pre_xnde_pre() {
		raw = String_.Concat_lines_nl_skip_last
			( "<ul>"
			, "<li>"
			, "<pre>"
			, "*a"
			, "</pre>"
			, "</li>"
			, "</ul>"
			);
		fxt.Test_parse_page_wiki_str(raw, String_.Concat_lines_nl_skip_last
			( "<ul>"
			, "<li>"
			, "<pre>"
			, "*a"
			, "</pre>"
			, "</li>"
			, "</ul>"
			));
	}
	@Test  public void Pre_td() {	// PURPOSE: EX: "\n a</td>"; </td> deactivates pre; PAGE:en.w:AGPLv3
		raw = String_.Concat_lines_nl_skip_last
			( "<table>"
			, "<tr>"
			, "<td>"
			, " [[File:A.png|30x30px]]</td>"
			, "</tr>"
			, "</table>"
			);
		fxt.Test_parse_page_wiki_str(raw, String_.Concat_lines_nl_skip_last
			( "<table>"
			, "  <tr>"
			, "    <td>"
			, " <a href=\"/wiki/File:A.png\" class=\"image\" xowa_title=\"A.png\"><img id=\"xoimg_0\" alt=\"\" src=\"file:///mem/wiki/repo/trg/thumb/7/0/A.png/30px.png\" width=\"30\" height=\"30\" /></a>"
			, "    </td>"
			, "  </tr>"
			, "</table>"
			));
	}
	@Test  public void Nl_2_2_space() {	// test trim
		raw = String_.Concat_lines_nl_skip_last
			( "a"
			, ""
			, "b"
			, " "
			, "c"
			);
		fxt.Test_parse_page_wiki_str(raw, String_.Concat_lines_nl_skip_last
			( "<p>a"
			, "</p>"
			, ""
			, "<p>b"
			, "</p>"		// NOTE: do not capture " "; confirmed against MW; DATE:2014-02-19
			, ""
			, "<p>c"
			, "</p>"
			));
	}
	@Test  public void File_1() {
		raw = String_.Concat_lines_nl_skip_last
			( "a"
			, "[[Image:Test.png|thumb|caption]]"
			);
		fxt.Test_parse_page_wiki_str(raw, String_.Concat_lines_nl_skip_last
			( "<p>a"
			, "</p>"
			, ""
			,	File_html()
			, ""
			));
		fxt.Test_parse_page_wiki(raw
			,	fxt.tkn_para_bgn_para_(0)
			,	fxt.tkn_txt_(0, 1), fxt.tkn_para_end_para_(2)
			,	fxt.tkn_lnki_(2, 34)
			,	fxt.tkn_para_blank_(34)
			);
	}
	@Test  public void File_2() {
		raw = String_.Concat_lines_nl_skip_last
			( "a"
			, "[[Image:Test.png|thumb|caption]]"
			, "b"
			);
		fxt.Test_parse_page_wiki_str(raw, String_.Concat_lines_nl_skip_last
			( "<p>a"
			, "</p>"
			, ""
			,	File_html()
			, ""
			, "<p>b"
			, "</p>"
			, ""
			));
		fxt.Test_parse_page_wiki(raw
			,	fxt.tkn_para_bgn_para_(0)													//    x0: <bos> -> <p>			1=blank
			,	fxt.tkn_txt_(0, 1), fxt.tkn_para_end_para_(2)
			,	fxt.tkn_lnki_(2, 34)
			,	fxt.tkn_para_bgn_para_(35)
			,	fxt.tkn_txt_(35, 36), fxt.tkn_para_end_para_(36)
			);
	}
	@Test  public void File_3() {
		raw = String_.Concat_lines_nl_skip_last
			( "[[Image:Test.png|thumb|caption]]"
			, ""
			, "c"
			);
		fxt.Test_parse_page_wiki_str(raw, String_.Concat_lines_nl_skip_last
			( File_html()
			, ""
			, "<p>c"
			, "</p>"
			, ""
			));
		fxt.Test_parse_page_wiki(raw
			,	fxt.tkn_para_blank_(0)													//    x0: <bos> -> <p>			1=blank
			,	fxt.tkn_lnki_(0, 32)
			,	fxt.tkn_para_blank_(33)
			,	fxt.tkn_para_bgn_para_(34)
			,	fxt.tkn_txt_(34, 35), fxt.tkn_para_end_para_(35)
			);
	}
	@Test  public void File_4() {
		raw = String_.Concat_lines_nl_skip_last
			( "a"
			, ""
			, "[[Image:Test.png|thumb|caption]]"
			, ""
			, "c"
			);
		fxt.Test_parse_page_wiki_str(raw, String_.Concat_lines_nl_skip_last
			( "<p>a"
			, "</p>"
			, ""
			,	File_html()
			, ""
			, "<p>c"
			, "</p>"
			, ""
			));
		fxt.Test_parse_page_wiki(raw
			,	fxt.tkn_para_bgn_para_(0)													//    x0: <bos> -> <p>			1=blank
			,	fxt.tkn_txt_(0, 1), fxt.tkn_para_blank_(2)
			,	fxt.tkn_para_end_para_(3)
			,	fxt.tkn_lnki_(3, 35)
			,	fxt.tkn_para_blank_(36)
			,	fxt.tkn_para_bgn_para_(37)
			,	fxt.tkn_txt_(37, 38), fxt.tkn_para_end_para_(38)
			);
	}
	@Test  public void File_5() {	// PURPOSE: \n in caption should not force paragraph
		raw = String_.Concat_lines_nl_skip_last
			( "[[Image:Test.png|thumb|"
			, "caption]]"
			, "a"
			);
		fxt.Test_parse_page_wiki_str(raw, String_.Concat_lines_nl_skip_last
			( File_html()
			, ""
			, "<p>a"
			, "</p>"
			, ""
			));
		fxt.Test_parse_page_wiki(raw
			,	fxt.tkn_para_blank_(0)
			,	fxt.tkn_lnki_(0, 33)
			,	fxt.tkn_para_bgn_para_(34)
			,	fxt.tkn_txt_(34, 35), fxt.tkn_para_end_para_(35)
			);
	}
	@Test  public void File_6() {	// PAGE:en.w:Pyotr Ilyich Tchaikovsky
		raw = String_.Concat_lines_nl_skip_last
			( "a"
			, " [[Image:Test.png|thumb|caption]]"
			, ""
			, "b"
			);
		fxt.Test_parse_page_wiki_str(raw, String_.Concat_lines_nl_skip_last
			( "<p>a"
			, "</p>"
			, ""
			,	File_html()
			, ""
			, "<p>b"
			, "</p>"
			, ""
			));
	}
	@Test  public void List() {
		raw = String_.Concat_lines_nl_skip_last
			( "a"
			, "*b"
			);
		fxt.Test_parse_page_wiki_str(raw, String_.Concat_lines_nl_skip_last
			( "<p>a"
			, "</p>"
			, ""
			, "<ul>"
			, "  <li>b"
			, "  </li>"
			, "</ul>"
			));
		fxt.Test_parse_page_wiki(raw
			,	fxt.tkn_para_bgn_para_(0)												//    x0: <bos> -> <p>			1=blank
			,	fxt.tkn_txt_(0, 1), fxt.tkn_para_end_para_(2)	// t2/x1: a\n   -> a\n</p>		1=bgn    2=blank
			,	fxt.tkn_list_bgn_(1, 3, Xop_list_tkn_.List_itmTyp_ul)
			,	fxt.tkn_txt_(3, 4)
			,	fxt.tkn_list_end_(4)
			,	fxt.tkn_para_blank_(4)
			);
	}
	@Test  public void Hdr_1() {
		raw = String_.Concat_lines_nl_skip_last
			( "a"
			, "==b=="
			);
		fxt.Test_parse_page_wiki_str(raw, String_.Concat_lines_nl_skip_last
			( "<p>a"
			, "</p>"
			, ""
			, "<h2>b</h2>"
			, ""
			));
	}
	@Test  public void Hdr_2() {
		raw = String_.Concat_lines_nl_skip_last
			( "a"
			, ""
			, "==b=="
			);
		fxt.Test_parse_page_wiki_str(raw, String_.Concat_lines_nl_skip_last
			( "<p>a"
			, "</p>"
			, ""
			, "<h2>b</h2>"
			, ""
			));
	}
	@Test  public void Hdr_list() {
		raw = String_.Concat_lines_nl_skip_last
			( "==a=="
			, "*b"
			);
		fxt.Test_parse_page_wiki_str(raw, String_.Concat_lines_nl_skip_last
			( "<h2>a</h2>"
			, ""
			, "<ul>"
			, "  <li>b"
			, "  </li>"
			, "</ul>"
			));
	}
	@Test  public void Hdr_list_multi() {
		raw = String_.Concat_lines_nl_skip_last
			( "==a=="
			, ""
			, ""
			, "*b"
			, "*c"
			);
		fxt.Test_parse_page_wiki_str(raw, String_.Concat_lines_nl_skip_last
			( "<h2>a</h2>"
			, ""
			, "<ul>"
			, "  <li>b"
			, "  </li>"
			, "  <li>c"
			, "  </li>"
			, "</ul>"
			));
	}
	@Test  public void Para_hdr() {
		raw = String_.Concat_lines_nl_skip_last
			( "a"
			, ""
			, "b"
			, ""
			, "==c=="
			, "d"
			);
		fxt.Test_parse_page_wiki_str(raw, String_.Concat_lines_nl_skip_last
			( "<p>a"
			, "</p>"
			, ""
			, "<p>b"
			, "</p>"
			, ""
			, "<h2>c</h2>"
			, ""
			, "<p>d"
			, "</p>"
			, ""
			));
	}
	@Test  public void Div() {	// PURPOSE: <div> should not go into para
		raw = String_.Concat_lines_nl_skip_last
			( "a"
			, ""
			, "==b=="
			, "<div>c</div>"
			, "d"
			);
		fxt.Test_parse_page_wiki_str(raw, String_.Concat_lines_nl_skip_last
			( "<p>a"
			, "</p>"
			, ""
			, "<h2>b</h2>"
			, "<div>c</div>"
			, ""
			, "<p>d"
			, "</p>"
			, ""
			));
	}	
	@Test  public void Div_para() {	// PURPOSE: </p> inserted between closing divs
		raw = String_.Concat_lines_nl_skip_last
			( "<div>"
			, "<div>"
			, "a"
			, "</div>"
			, "</div>"
			);
		fxt.Test_parse_page_wiki_str(raw, String_.Concat_lines_nl_skip_last
			( "<div>"
			, "<div>"
			, ""
			, "<p>a"
			, "</p>"
			, "</div>"
			, "</div>"
			));
	}
	@Test  public void Tbl() {
		raw = String_.Concat_lines_nl_skip_last
			( "<table>"
			, "<tr>"
			, "<td>a"
			, "</td>"
			, "<td>b"
			, "</td>"
			, "</tr>"
			, "</table>"
			);
		fxt.Test_parse_page_wiki_str(raw, String_.Concat_lines_nl_skip_last
			( "<table>"
			, "  <tr>"
			, "    <td>a"
			, "    </td>"
			, "    <td>b"
			, "    </td>"
			, "  </tr>"
			, "</table>"
			, ""
			));
	}
	@Test  public void Tbl_leading_ws() { // REF: [[Corneal dystrophy (human)]]
		raw = String_.Concat_lines_nl_skip_last
			( " {|"
			, " |-"
			, " |a"
			, " |}"
			);
		fxt.Test_parse_page_wiki_str(raw, String_.Concat_lines_nl_skip_last
			( "<table>"
			, "  <tr>"
			, "    <td>a"
			, "    </td>"
			, "  </tr>"
			, "</table>"
			, ""
			));
	}
	@Test  public void Bos_pipe() {	// PURPOSE: | is interpreted as a tblw dlm and calls a different section of code
		fxt.Test_parse_page_wiki_str("|", String_.Concat_lines_nl_skip_last
			( "<p>|"
			, "</p>"
			, ""
			));
	}
	@Test   public void Ws_mistakenly_ignored() {// PURPOSE: ws before ''' somehow gets ignored; EX: en.w:Vacuum tube; {{Unreferenced section|date=July 2010|reason=date taken from existing cn}}
		fxt.Test_parse_page_all_str(String_.Concat_lines_nl_skip_last
			( "<table><tr><td><span>a"
			, " '''b'''</span></td></tr></table>"
			) ,	String_.Concat_lines_nl_skip_last
			( "<table>"
			, "  <tr>"
			, "    <td><span>a"
			, " <b>b</b></span>"
			, "    </td>"
			, "  </tr>"
			, "</table>"
			, ""
			));
	}
	@Test   public void Pre_7() {	// PURPOSE: alternating pres; EX:w:World Wide Web
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last
		( "a"
		, " b"
		, "c"
		, " d"
		, "e"
		), String_.Concat_lines_nl_skip_last
		( 	"<p>a"
		, "</p>"
		, ""
		, "<pre>b"
		, "</pre>"
		, ""
		, "<p>c"
		, "</p>"
		, ""
		, "<pre>d"
		, "</pre>"
		, ""
		, "<p>e"
		, "</p>"
		, ""
		));
	}
	@Test  public void Nowiki() {
		fxt.Test_parse_page_all_str(String_.Concat_lines_nl_skip_last
		( "a"
		, " <nowiki>b</nowiki>"
		, "c"
		), String_.Concat_lines_nl_skip_last
		( 	"<p>a"
		, "</p>"
		, ""
		, "<pre>b"
		, "</pre>"
		, ""
		, "<p>c"
		, "</p>"
		, ""
		));
	}
	@Test  public void Nowiki_tbl() {	// EX: de.wikipedia.org/wiki/Hilfe:Vorlagenprogrammierung
		fxt.Test_parse_page_all_str(String_.Concat_lines_nl_skip_last
		( "a"
		, " <nowiki>b</nowiki>c"
		, ""
		, "{|"
		, "|-"
		, "|d"
		, "|}"
		), String_.Concat_lines_nl_skip_last
		( 	"<p>a"
		, "</p>"
		, ""
		, "<pre>bc"
		, "</pre>"
//			, ""
		, "<table>"
		, "  <tr>"
		, "    <td>d"
		, "    </td>"
		, "  </tr>"
		, "</table>"
		, ""
		));
	}
	@Test  public void Pre_at_end_should_autoclose() { // PURPOSE: as per proc_name
		fxt.Test_parse_page_all_str(String_.Concat_lines_nl_skip_last
		( "a"
		, " b"
		, ""
		), String_.Concat_lines_nl_skip_last
		( 	"<p>a"
		, "</p>"
		, ""
		, "<pre>b"
		, "</pre>"
		, ""
		));
	}
	@Test  public void Pre_xtn() {	// PURPOSE: <ref> (and other xtn) was unnecessarily canceling pre; PAGE:en.w:MD5_Hash
		fxt.Test_parse_page_all_str(String_.Concat_lines_nl_skip_last
		( " <span>a</span>"
		, " <ref>b</ref>"
		, " c"
		, ""
		, "d"
		), String_.Concat_lines_nl_skip_last
		( 	"<pre><span>a</span>"
		, "<sup id=\"cite_ref-0\" class=\"reference\"><a href=\"#cite_note-0\">[1]</a></sup>"
		, "c"
		, "</pre>"
		, ""
		, "<p>d"
		, "</p>"
		, ""
		));
	}
	@Test   public void Pre_tbl() {	// PURPOSE: pre was being garbled by tables b/c table was ignoring whitespace; DATE:2013-02-11
		fxt.Test_parse_page_all_str(String_.Concat_lines_nl_skip_last
		( "{|"
		, "|a"
		, "|}"
		, ""
		, "c"
		, ""
		, " d"
		, ""
		, "{|"
		, "| e"
		, "|}"
		, ""
		, "f"
		), String_.Concat_lines_nl_skip_last
		( 	"<table>"
		, "  <tr>"
		, "    <td>a"
		, "    </td>"
		, "  </tr>"
		, "</table>"
		, ""
		, "<p>c"
		, "</p>"
		, ""
		, "<pre>d"
		, "</pre>"
		, "<table>"
		, "  <tr>"
		, "    <td> e"
		, "    </td>"
		, "  </tr>"
		, "</table>"
		, ""
		, "<p>f"
		, "</p>"
		, ""
		));
	}
	@Test  public void Nowiki_tbl2() {	// EX: de.wikipedia.org/wiki/Hilfe:Vorlagenprogrammierung
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last
		( "{|"
		, "|a"
		, "|}"
		, ""
		, "b"
		, ""
		, " <nowiki>c</nowiki> d"
		, ""
		, "{|"
		, "|e"
		, "|}"
		), String_.Concat_lines_nl
		( 	"<table>"
		, "  <tr>"
		, "    <td>a"
		, "    </td>"
		, "  </tr>"
		, "</table>"
		, ""
		, "<p>b"
		, "</p>"
		, ""
		, "<pre>c d"
		, "</pre>"
		, "<table>"
		, "  <tr>"
		, "    <td>e"
		, "    </td>"
		, "  </tr>"
		, "</table>"
		));
	}
	@Test  public void Pre_nowiki() {	// PURPOSE: nowikis inside pre should be ignored; DATE:2013-03-30
		fxt.Test_parse_page_all_str("\n a<nowiki>&lt;</nowiki>b"	, "\n<pre>a&lt;b\n</pre>\n");										// basic
	}
	@Test  public void Para_bos_tblw() {
		raw = String_.Concat_lines_nl_skip_last
			( "{|"
			, "|-"
			, "|a"
			, "|}"
			);
		fxt.Test_parse_page_all_str(raw, String_.Concat_lines_nl_skip_last
			( "<table>"
			, "  <tr>"
			, "    <td>a"
			, "    </td>"
			, "  </tr>"
			, "</table>"
			, ""
			));
	}
	@Test  public void Para_bos_tblx() {
		raw = String_.Concat_lines_nl_skip_last
			( "<table>"
			, "<tr>"
			, "<td>a"
			, "</td>"
			, "</tr>"
			, "</table>"
			);
		fxt.Test_parse_page_all_str(raw, String_.Concat_lines_nl_skip_last
			( "<table>"
			, "  <tr>"
			, "    <td>a"
			, "    </td>"
			, "  </tr>"
			, "</table>"
			, ""
			));
	}
	@Test   public void Pre_tblw_td() {
		raw = String_.Concat_lines_nl_skip_last
			( "a"
			, " b"
			, "|"
			, "c"
			);
		fxt.Test_parse_page_wiki_str(raw, String_.Concat_lines_nl_skip_last
			( "<p>a"
			, "</p>"
			, ""
			, "<pre>b"
			, "</pre>"
			, ""
			, "<p>|"
			, "c"
			, "</p>"
			, ""
			));
	}
	@Test   public void Pre_tblw_tr() {
		raw = String_.Concat_lines_nl_skip_last
			( "a"
			, " b"
			, "|-"
			, "c"
			);
		fxt.Test_parse_page_wiki_str(raw, String_.Concat_lines_nl_skip_last
			( "<p>a"
			, "</p>"
			, ""
			, "<pre>b"
			, "</pre>"
			, ""
			, "<p>|-"
			, "c"
			, "</p>"
			, ""
			));
	}
	@Test   public void Pre_tblw_te() {
		raw = String_.Concat_lines_nl_skip_last
			( "a"
			, " b"
			, "|}"
			, "c"
			);
		fxt.Test_parse_page_wiki_str(raw, String_.Concat_lines_nl_skip_last
			( "<p>a"
			, "</p>"
			, ""
			, "<pre>b"
			, "</pre>"
			, ""
			, "<p>|}"
			, "c"
			, "</p>"
			, ""
			));
	}
	private String File_html() {return File_html("Image", "Test.png", "d/9", "caption");}
	public static String File_html(String ns, String ttl, String md5, String caption) {
		return String_.Concat_lines_nl_skip_last
			( "<div class=\"thumb tright\">"
			, "  <div id=\"xowa_file_div_0\" class=\"thumbinner\" style=\"width:220px;\">"
			, "    <a href=\"/wiki/" + ns + ":" + ttl + "\" class=\"image\" xowa_title=\"" + ttl + "\"><img id=\"xoimg_0\" alt=\"\" src=\"file:///mem/wiki/repo/trg/thumb/" + md5 + "/" + ttl + "/220px.png\" width=\"0\" height=\"0\" /></a>"
			, "    <div class=\"thumbcaption\">"
			,       "<div class=\"magnify\"><a href=\"/wiki/" + ns + ":" + ttl + "\" class=\"internal\" title=\"Enlarge\"></a></div>" + caption + ""
			, "    </div>"
			, "  </div>"
			, "</div>"
			);
	}
}
