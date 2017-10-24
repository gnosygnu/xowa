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
package gplx.xowa.htmls.core.htmls; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*;
import org.junit.*;
public class Xoh_html_wtr_tst {
	private final    Xop_fxt fxt = new Xop_fxt();
	@After public void term() {fxt.Init_para_n_(); fxt.Reset();}
	@Test  public void Hr_basic()					{fxt.Test_parse_page_wiki_str("----"				, "<hr/>");}
	@Test  public void Hr_extended()				{fxt.Test_parse_page_wiki_str("--------"			, "<hr/>");}
	@Test  public void Lnki_basic()					{fxt.Test_parse_page_wiki_str("[[a]]"				, "<a href=\"/wiki/A\">a</a>");}
	@Test  public void Lnki_caption()				{fxt.Test_parse_page_wiki_str("[[a|b]]"				, "<a href=\"/wiki/A\">b</a>");}
	@Test  public void Lnki_caption_fmt()			{fxt.Test_parse_page_wiki_str("[[a|''b'']]"			, "<a href=\"/wiki/A\"><i>b</i></a>");}
	@Test  public void Lnki_tail_trg()				{fxt.Test_parse_page_wiki_str("[[a]]b"				, "<a href=\"/wiki/A\">ab</a>");}
	@Test  public void Lnki_tail_caption()			{fxt.Test_parse_page_wiki_str("[[a|b]]c"			, "<a href=\"/wiki/A\">bc</a>");}
	@Test   public void Lnki_title() {
		fxt.Wtr_cfg().Lnki__title_(true);
		fxt.Test_parse_page_wiki_str("[[a|b]]", "<a href=\"/wiki/A\" title=\"A\">b</a>");
		fxt.Wtr_cfg().Lnki__title_(false);
	}
	@Test   public void Lnki_title_page_text() {
		fxt.Wtr_cfg().Lnki__title_(true);
		fxt.Test_parse_page_wiki_str("[[a_b]]", "<a href=\"/wiki/A_b\" title=\"A b\">a_b</a>");
		fxt.Wtr_cfg().Lnki__title_(false);
	}
	@Test  public void Lnki_category()				{fxt.Test_parse_page_wiki_str("[[Category:A]]"		, "");}	// NOTE: Category does not get written in main page bfr
	@Test  public void Lnki_category_force()		{fxt.Test_parse_page_wiki_str("[[:Category:A]]"		, "<a href=\"/wiki/Category:A\">Category:A</a>");}
	@Test  public void Lnki_matches_page()			{fxt.Test_parse_page_wiki_str("[[test page|t1]]", "<b>t1</b>");}	// NOTE: "Test page" is hardcoded to be the test page name
	@Test  public void Lnki_matches_page_but_has_anchor()	{fxt.Test_parse_page_wiki_str("[[Test page#a|test 1]]", "<a href=\"/wiki/Test_page#a\">test 1</a>");}	// NOTE: "Test page" is hardcoded to be the test page name
	@Test  public void Lnki_anchor()				{fxt.Test_parse_page_wiki_str("[[A#b]]"				, "<a href=\"/wiki/A#b\">A#b</a>");}
//		@Test  public void Img_invalid_wnt_char() {
//			fxt.Test_parse_page_wiki_str
//			(	"[[File:A*b.png]]"
//			,	"<div class=\"floatnone\"><a href=\"File:A.png\" class=\"image\"><img alt=\"\" src=\"\" width=\"20\" height=\"30\" /></a></div>"
//			);
//		}
//		@Test  public void Img_alt() {	// FUTURE: enable; WHEN: after fixing xnde to handle bad xnde; EX: France
//			fxt.Test_parse_page_wiki_str("[[File:A.png|none|9x8px|alt=a<b>b</b>\"c\"d]]", Xop_fxt.html_img_none("File:A.png", "ab&quot;c&quot;d"));
//		}
	@Test  public void Url_encode()					{fxt.Test_parse_page_wiki_str("[[a;@$!*(),/ _^b|z]]"		, "<a href=\"/wiki/A;@$!*(),/_%5Eb\">z</a>");}	// NOTE: was "a" instead of "A"; "__" instead of "_" DATE:2014-09-07
	@Test  public void Url_encode_space()			{fxt.Test_parse_page_wiki_str("[[a _b|z]]"					, "<a href=\"/wiki/A_b\">z</a>");}
	@Test  public void Apos_i()						{fxt.Test_parse_page_wiki_str("''a''"						, "<i>a</i>");}
	@Test  public void Apos_b()						{fxt.Test_parse_page_wiki_str("'''a'''"						, "<b>a</b>");}
	@Test  public void Apos_ib()					{fxt.Test_parse_page_wiki_str("'''''a'''''"					, "<i><b>a</b></i>");}
	@Test  public void Html_ent()					{fxt.Test_parse_page_wiki_str("&#33;"						, "&#33;");}	// PURPOSE:ncrs should be literal, not decoded (!);  DATE:2014-11-06
	@Test  public void Html_ref()					{fxt.Test_parse_page_wiki_str("&gt;"						, "&gt;");}
	@Test  public void List_1_itm()	{
		fxt.Test_parse_page_wiki_str("*a", String_.Concat_lines_nl_skip_last
			( "<ul>"
			, "  <li>a"
			, "  </li>"
			, "</ul>"
			));
	}
	@Test  public void List_2_itms()	{
		fxt.Test_parse_page_wiki_str("*a\n*b", String_.Concat_lines_nl_skip_last
			( "<ul>"
			, "  <li>a"
			, "  </li>"
			, "  <li>b"
			, "  </li>"
			, "</ul>"
			));
	}
	@Test  public void List_nest_ul()	{
		fxt.Test_parse_page_wiki_str("*a\n**b", String_.Concat_lines_nl_skip_last
			( "<ul>"
			, "  <li>a"
			, "    <ul>"
			, "      <li>b"
			, "      </li>"
			, "    </ul>"
			, "  </li>"
			, "</ul>"
			));
	}
	@Test  public void List_dt_dd()	{
		fxt.Test_parse_page_wiki_str(";a:b", String_.Concat_lines_nl_skip_last
			( "<dl>"
			, "  <dt>a"
			, "  </dt>"
			, "  <dd>b"
			, "  </dd>"
			, "</dl>"
			));
	}
	@Test  public void List_dd_nest2()	{
		fxt.Test_parse_page_wiki_str("::a", String_.Concat_lines_nl_skip_last
			( "<dl>"
			, "  <dd>"
			, "    <dl>"
			, "      <dd>a"
			, "      </dd>"
			, "    </dl>"
			, "  </dd>"
			, "</dl>"
			));
	}
	@Test  public void Tblw_basic() {
		fxt.Test_parse_page_wiki_str("{|\n|+a\n!b||c\n|-\n|d||e\n|}", String_.Concat_lines_nl
			( "<table>"
			, "  <caption>a"
			, "  </caption>"
			, "  <tr>"
			, "    <th>b"
			, "    </th>"
			, "    <th>c"
			, "    </th>"
			, "  </tr>"
			, "  <tr>"
			, "    <td>d"
			, "    </td>"
			, "    <td>e"
			, "    </td>"
			, "  </tr>"
			, "</table>"
			));
	}
	@Test  public void Tblw_atrs() {
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last
			(	"{|style='z'"
			,	"|+a"
			,	"!style='y'|b||style='x'|c"
			,	"|-style='w'"
			,	"|style='v'|d||style='u'|e"
			,	"|}"
			), String_.Concat_lines_nl
			(	"<table style='z'>"
			,	"  <caption>a"
			,	"  </caption>"
			,	"  <tr>"
			,	"    <th style='y'>b"
			,	"    </th>"
			,	"    <th style='x'>c"
			,	"    </th>"
			,	"  </tr>"
			,	"  <tr style='w'>"
			,	"    <td style='v'>d"
			,	"    </td>"
			,	"    <td style='u'>e"
			,	"    </td>"
			,	"  </tr>"
			,	"</table>"
			));
	}
	@Test  public void Para_hdr_list() {
		fxt.Init_para_y_();
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last
			( "==a=="
			, ""
			, "*b"
			, "*c"
			), String_.Concat_lines_nl_skip_last
			( "<h2>a</h2>"
			, ""
			, "<ul>"
			, "  <li>b"
			, "  </li>"
			, "  <li>c"
			, "  </li>"
			, "</ul>"
			));
		fxt.Init_para_n_();
	}
	@Test  public void Para_nl_is_space() {
		fxt.Init_para_y_();
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last
			( "a"
			, "b"
			), String_.Concat_lines_nl_skip_last
			( "<p>a"
			, "b"
			, "</p>"
			, ""
			));
		fxt.Init_para_n_();
	}
	@Test  public void Para_nl_2_2() {
		fxt.Init_para_y_();
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last
			( "a"
			, ""
			, "b"
			, ""
			, "c"
			), String_.Concat_lines_nl_skip_last
			( "<p>a"
			, "</p>"
			, ""
			, "<p>b"
			, "</p>"
			, ""
			, "<p>c"
			, "</p>"
			, ""
			));
		fxt.Init_para_n_();
	}
	@Test  public void Div_2() {	// WP:[[Air]]#Density of air
		fxt.Init_para_y_();
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last
			(	"<div>a</div>"
			,	""
			,	"<div>b</div>"
			), String_.Concat_lines_nl_skip_last
			(	"<div>a</div>"
			,	"<div>b</div>"
			));
		fxt.Init_para_n_();
	}
	@Test  public void Tblw() {
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl
			(	"{|"
			,	"|-"
			,	"|a"
			,	"|b"
			,	"|-"
			,	"|c"
			,	"|d"
			,	"|}"
			)
			, String_.Concat_lines_nl
			( "<table>"
			, "  <tr>"
			, "    <td>a"
			, "    </td>"
			, "    <td>b"
			, "    </td>"
			, "  </tr>"
			, "  <tr>"
			, "    <td>c"
			, "    </td>"
			, "    <td>d"
			, "    </td>"
			, "  </tr>"
			, "</table>"
			, ""
			));
	}
	@Test  public void Tblw_lnki_bang() {
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl
			(	"{|"
			,	"|-"
			,	"|[[a|!]]"
			,	"|}"
			)
			, String_.Concat_lines_nl
			( "<table>"
			, "  <tr>"
			, "    <td><a href=\"/wiki/A\">!</a>"
			, "    </td>"
			, "  </tr>"
			, "</table>"
			, ""
			));
	}
	@Test  public void Tr_inside_tblw_td() {	// WP:[[Earth]]
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl
			(	"{|"
			,	"|-"
			,	"<tr><td>a</td></tr>"
			,	"|}"
			)
			, String_.Concat_lines_nl
			(	"<table>"
			,	"  <tr>"
			,	"    <td>a"
			,	"    </td>"
			,	"  </tr>"
			,	"</table>"
			, ""
			));
	}
	@Test  public void Tblw_tr_with_newlines() {// WP:[[John Adams]] Infobox Officeholder
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl
			(	"{|"
			,	"|-"
			,	""
			,	""
			,	""
			,	"|a"
			,	"|}"
			)
			, String_.Concat_lines_nl
			( "<table>"
			, "  <tr>"
			, "    <td>a"
			, "    </td>"
			, "  </tr>"
			, "</table>"
			, ""
			));
	}
	@Test  public void Bang_doesnt_force_tbl() {
		fxt.Init_para_y_();
		fxt.Test_parse_page_wiki_str("a! b! c", "<p>a! b! c\n</p>\n");
		fxt.Init_para_n_();
	}
	@Test  public void Err_nlOnly() {
		fxt.Test_parse_page_wiki_str("{{\n}}", "{{\n}}");	// NOTE: was  {{}}
	}
	@Test  public void Xnde_inline() {
		fxt.Test_parse_page_wiki_str("<div/>", "<div></div>");
	}
	@Test  public void Xnde_id_encode() { // PURPOSE: id should be url-encoded; DATE: 2013-11-13;
		fxt.Test_parse_page_wiki_str("<div id='a*'></div>", "<div id='a.2A'></div>");
		fxt.Test_parse_page_wiki_str("<div id='a b'></div>", "<div id='a_b'></div>");
	}
	@Test  public void Timeline() {// PURPOSE: embed timeline contents in pre; DATE:2014-05-22
		fxt.Test_parse_page_wiki_str("<timeline>a</timeline>", "<pre class='xowa-timeline'>a</pre>");
	}
	@Test  public void Amp_ncr_should_not_be_rendered_as_bytes() {	// PURPOSE: &#160; should be rendered as &#160; not as literal bytes {192,160}; DATE:2013-12-09
		fxt.Test_parse_page_wiki_str("a&#160;b", "a&#160;b");
	}

	//		@Test  public void Fix_PositionAbsolute_stripped() {
//			fxt.Test_parse_page_wiki_str("<span style=\"position:absolute;\"></span>", "<span style=\";\"></span>");
//		}
//		@Test  public void Xnde_nl()	{
//			fxt.Test_parse_page_wiki_str("<div id='a'\nclass='b'>c</div>", String_.Concat_lines_nl_skip_last
//					( "<div id='a' class='b'>c</div>"
//					));
//		}
//		@Test  public void Tblw()	{
//			fxt.Test_parse_page_wiki_str("{|\n|}", String_.Concat_lines_nl
//				( "<table>"
//				, "  <tr>"
//				, "    <td>a"
//				, "    </td>"
//				, "    <td>b"
//				, "    </td>"
//				, "  </tr>"
//				, "</table>"
//				));
//		}
}			
