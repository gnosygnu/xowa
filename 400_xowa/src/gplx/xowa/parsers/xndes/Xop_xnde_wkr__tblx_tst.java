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
package gplx.xowa.parsers.xndes; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import org.junit.*;
public class Xop_xnde_wkr__tblx_tst {
	private final Xop_fxt fxt = new Xop_fxt();
	@After public void term() {fxt.Init_para_n_();}
	@Test  public void Table() {
		fxt.Test_parse_page_wiki("a<table><tr><td>b</td></tr></table>c"
			, fxt.tkn_txt_ ( 0,  1)
			, fxt.tkn_tblw_tb_(1, 35).Subs_
			(	fxt.tkn_tblw_tr_(8, 27).Subs_
			(		fxt.tkn_tblw_td_(12, 22).Subs_(fxt.tkn_txt_(16, 17))
			)
			)
			, fxt.tkn_txt_ (35, 36)
			);
	}
	@Test  public void Ws_bgn() {	// PURPOSE: some templates return leading ws; PAGE:en.w:UK
		fxt.Init_para_y_();
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last
			(	"  <table>"
			,	"    <tr>"
			,	"      <td>a"
			,	"      </td>"
			,	"    </tr>"
			,	"  </table>"
			), String_.Concat_lines_nl_skip_last
			(	"<table>"
			,	"  <tr>"
			,	"    <td>a"
			,	"    </td>"
			,	"  </tr>"
			,	"</table>"
			,   ""
			));
		fxt.Init_para_n_();
	}
	@Test  public void Td_in_lnki_should_be_ignored() {// PURPOSE: \n| inside lnki should not be interpreted as table cell; EX: uk.w:Дніпро; DATE:2014-03-11
		fxt.Init_para_y_();
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last
		( "<table><tr><td>"
		, "[[File:A.png|150px"
		, "|B]]</td></tr></table>"
		), String_.Concat_lines_nl_skip_last
		( "<table>"
		, "  <tr>"
		, "    <td>"
		, "<a href=\"/wiki/File:A.png\" class=\"image\" xowa_title=\"A.png\"><img id=\"xoimg_0\" alt=\"B\" src=\"file:///mem/wiki/repo/trg/thumb/7/0/A.png/150px.png\" width=\"150\" height=\"0\" /></a>"
		, "    </td>"
		, "  </tr>"
		, "</table>"
		));
		fxt.Init_para_n_();
	}
	@Test  public void Nl() {
		fxt.Init_para_y_();
		fxt.Test_parse_page_wiki_str
			(	"<table>\n\n\n\n\n</table>"
			,	"<table>\n"
			+	"</table>\n"
			);
		fxt.Init_para_n_();
	}
}
