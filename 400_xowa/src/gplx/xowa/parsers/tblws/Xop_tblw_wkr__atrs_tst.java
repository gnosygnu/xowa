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
package gplx.xowa.parsers.tblws; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import org.junit.*;
public class Xop_tblw_wkr__atrs_tst {
	private final Xop_fxt fxt = new Xop_fxt();
	@Test  public void Tr() {
		fxt.Test_parse_page_wiki(String_.Concat_lines_nl_skip_last
		( "{|"
		, "|-style='a'"
		, "|b"
		, "|}"
		), fxt.tkn_tblw_tb_(0, 20).Subs_
		( fxt.tkn_tblw_tr_(2, 17).Atrs_rng_(5, 14).Subs_
		( 	fxt.tkn_tblw_td_(14, 17).Subs_(fxt.tkn_txt_(16, 17), fxt.tkn_para_blank_(18))
		))
		);
	}
	@Test  public void Td() {
		fxt.Test_parse_page_wiki(String_.Concat_lines_nl_skip_last
		( "{|"
		, "|-"
		, "|style='a'|b"
		, "|}"
		), fxt.tkn_tblw_tb_(0, 21).Subs_
		( fxt.tkn_tblw_tr_(2, 18).Subs_
		( 	fxt.tkn_tblw_td_(5, 18).Atrs_rng_(7, 16).Subs_(fxt.tkn_txt_(17, 18), fxt.tkn_para_blank_(19))
		))
		);
	}
	@Test  public void Td_mult() {
		fxt.Init_para_y_();
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last
		( "{|"
		, "|-"
		, "|"
		, "  {|"
		, "  |-"
		, "  |  id='1'|"
		, "  |  id='2'|a"
		, "  |  id='3'|"
		, "  |}"
		, "|}"
		)
		, String_.Concat_lines_nl_skip_last
		( "<table>"
		, "  <tr>"
		, "    <td>"
		, "      <table>"
		, "        <tr>"
		, "          <td id='1'>"
		, "          </td>"
		, "          <td id='2'>a"
		, "          </td>"
		, "          <td id='3'>"
		, "          </td>"
		, "        </tr>"
		, "      </table>"
		, "    </td>"
		, "  </tr>"
		, "</table>"
		, ""
		)
		);
		fxt.Init_para_n_();
	}
	@Test  public void Tc() {	// PAGE:en.w:1920_Palm_Sunday_tornado_outbreak
		fxt.Init_para_y_();
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last
		( "{|id='1'"
		, "|+id='2'|a"
		, "|}"
		)
		, String_.Concat_lines_nl_skip_last
		( "<table id='1'>"
		, "  <caption id='2'>a"
		, "  </caption>"
		, "</table>"
		, ""
		)
		);
		fxt.Init_para_n_();
	}
	@Test  public void Td_mixed() {
		fxt.Test_parse_page_wiki(String_.Concat_lines_nl_skip_last
		( "{|"
		, "|-"
		, "|style='a'|b||c"
		, "|}"
		), fxt.tkn_tblw_tb_(0, 24).Subs_
		( fxt.tkn_tblw_tr_(2, 21).Subs_
		( 	fxt.tkn_tblw_td_( 5, 18).Atrs_rng_(7, 16).Subs_(fxt.tkn_txt_(17, 18), fxt.tkn_para_blank_(19))
		, 	fxt.tkn_tblw_td_(18, 21).Subs_(fxt.tkn_txt_(20, 21), fxt.tkn_para_blank_(22))
		))
		);
	}
	@Test  public void Th() {
		fxt.Test_parse_page_wiki(String_.Concat_lines_nl_skip_last
		( "{|"
		, "|-"
		, "!style='a'|b"
		, "|}"
		), fxt.tkn_tblw_tb_(0, 21).Subs_
		( fxt.tkn_tblw_tr_(2, 18).Subs_
		( 	fxt.tkn_tblw_th_(5, 18).Atrs_rng_(7, 16).Subs_(fxt.tkn_txt_(17, 18), fxt.tkn_para_blank_(19))
		))
		);
	}
	@Test  public void Skip_hdr() {
		fxt.Test_parse_page_wiki(String_.Concat_lines_nl_skip_last
		( "{|"
		, "|+b"
		, "!style='a'|b"
		, "|}"
		), fxt.tkn_tblw_tb_(0, 22).Caption_count_(1).Subs_
		( fxt.tkn_tblw_tc_(2, 6).Subs_(fxt.tkn_txt_( 5, 6))
		, fxt.tkn_tblw_tr_(6, 19).Subs_
		( 	fxt.tkn_tblw_th_(6, 19).Atrs_rng_(8, 17).Subs_(fxt.tkn_txt_(18, 19), fxt.tkn_para_blank_(20))
		)				
		));
	}
	@Test  public void Td_bg_color() {	// PURPOSE: atr_parser should treat # as valid character in unquoted val; PAGE:en.w:UTF8; |bgcolor=#eeeeee|<small>Indic</small><br/><small>0800*</small><br/>'''''224'''''
		fxt.Init_para_y_();
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last
		( "{|"
		, "|bgcolor=#eeeeee|a"
		, "|}"
		)
		, String_.Concat_lines_nl_skip_last
		( "<table>"
		, "  <tr>"
		, "    <td bgcolor=\"#eeeeee\">a"
		, "    </td>"
		, "  </tr>"
		, "</table>"
		, ""
		)
		);
		fxt.Init_para_n_();
	}
	@Test  public void Xnde_tb() {	// PURPOSE: xnde should close any open xatrs; PAGE:en.w:Western_Front_(World_War_I); stray > after == Dramatizations ==
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last
		( "{|id='1'<p></p>"
		, "|a"
		, "|}"), String_.Concat_lines_nl_skip_last
		( "<table id='1'><p></p>"
		, "  <tr>"
		, "    <td>a"
		, "    </td>"
		, "  </tr>"
		, "</table>"
		, ""
		));
	}
	@Test   public void Xnde_tr() { // PURPOSE: xnde should disable all tkns; PAGE:en.w:A DATE:2014-07-16
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last
		( "{|"
		, "|-<b>c</b>id='d'<br/>"	// note that id='d' should not show up since <b> invalidates entire line
		, "|a"
		, "|}"
		), String_.Concat_lines_nl_skip_last
		( "<table>"
		, "  <tr>"
		, "    <td>a"
		, "    </td>"
		, "  </tr>"
		, "</table>"
		));
	}
	@Test  public void Xnde_mix_tblw_tblx() {	// PURPOSE: issue with </tr> somehow rolling up everything after <td>; PAGE:en.w:20th_century; {{Decades and years}}
		fxt.Init_para_y_();
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last
		( "<table><tr><td>a"
		, "{|id=1"
		, "|-"
		, "|b"
		, "|}</td></tr></table>"
		)
		, String_.Concat_lines_nl_skip_last
		( "<table>"
		, "  <tr>"
		, "    <td>a"
		, "      <table id=\"1\">"
		, "        <tr>"
		, "          <td>b"
		, "          </td>"
		, "        </tr>"
		, "      </table>"
		, "    </td>"
		, "  </tr>"
		, "</table>"
		, ""
		)
		);
		fxt.Init_para_n_();
	}
}