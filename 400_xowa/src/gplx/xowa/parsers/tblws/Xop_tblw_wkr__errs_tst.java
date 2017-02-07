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
package gplx.xowa.parsers.tblws; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import org.junit.*;
public class Xop_tblw_wkr__errs_tst {
	private final Xop_fxt fxt = new Xop_fxt();
	@Test  public void Err_row_empty() {
		fxt.Test_parse_page_wiki("{|\n|-\n|-\n|a\n|}"
			,	fxt.tkn_tblw_tb_(0, 14).Subs_
			(		fxt.tkn_tblw_tr_(2,  5)
			,		fxt.tkn_tblw_tr_(5, 11).Subs_
			(			fxt.tkn_tblw_td_(8, 11).Subs_(fxt.tkn_txt_(10, 11), fxt.tkn_para_blank_(12))
			))
			);
	}
	@Test  public void Err_row_trailing() {
		fxt.Test_parse_page_wiki("{|\n|-\n|a\n|-\n|}"
			, fxt.tkn_tblw_tb_(0, 14).Subs_
			(	fxt.tkn_tblw_tr_(2, 8).Subs_
			(		fxt.tkn_tblw_td_(5, 8).Subs_(fxt.tkn_txt_(7, 8), fxt.tkn_para_blank_(9))
			))
			);
	}
	@Test  public void Err_caption_after_tr() {
		fxt.Test_parse_page_wiki("{|\n|-\n|+a\n|}"
			, fxt.tkn_tblw_tb_(0, 12).Caption_count_(1).Subs_
			(	fxt.tkn_tblw_tr_(2, 5)
			,	fxt.tkn_tblw_tc_(5, 9).Subs_(fxt.tkn_txt_(8, 9), fxt.tkn_para_blank_(10)))
			);
	}
	@Test  public void Err_caption_after_td() {
		fxt.Init_log_(Xop_tblw_log.Caption_after_td).Test_parse_page_wiki("{|\n|-\n|a\n|+b\n|}"
			, fxt.tkn_tblw_tb_(0, 15).Caption_count_(1).Subs_
			(	fxt.tkn_tblw_tr_(2,  8).Subs_
			(		fxt.tkn_tblw_td_(5, 8).Subs_(fxt.tkn_txt_(7, 8)))
			,	fxt.tkn_tblw_tc_(8, 12).Subs_(fxt.tkn_txt_(11, 12), fxt.tkn_para_blank_(13)))
			);
	}
	@Test  public void Err_caption_after_tc() {
		fxt.Init_log_(Xop_tblw_log.Caption_after_tc).Test_parse_page_wiki("{|\n|+a\n|+b\n|}"
			, fxt.tkn_tblw_tb_(0, 13).Caption_count_(2).Subs_
			(	fxt.tkn_tblw_tc_(2,  6).Subs_(fxt.tkn_txt_( 5, 6))
			,	fxt.tkn_tblw_tc_(6, 10).Subs_(fxt.tkn_txt_( 9, 10), fxt.tkn_para_blank_(11)))
			);
	}
	@Test  public void Err_row_auto_opened() {
		fxt.Test_parse_page_wiki("{|\n|a\n|}"
			, fxt.tkn_tblw_tb_(0, 8).Subs_
			(	fxt.tkn_tblw_tr_(2, 5).Subs_
			(		fxt.tkn_tblw_td_(2, 5).Subs_(fxt.tkn_txt_(4, 5), fxt.tkn_para_blank_(6))
			)));
	}
	@Test  public void Err_caption_auto_closed() {
		fxt.Test_parse_page_wiki("{|\n|+a\n|b\n|}"
			, fxt.tkn_tblw_tb_(0, 12).Caption_count_(1).Subs_
			(	fxt.tkn_tblw_tc_(2, 6).Subs_(fxt.tkn_txt_(5, 6))
			,	fxt.tkn_tblw_tr_(6, 9).Subs_
			(		fxt.tkn_tblw_td_(6, 9).Subs_(fxt.tkn_txt_(8, 9),fxt.tkn_para_blank_(10))
			)));
	}
	@Test  public void Err_Atrs_dumped_into_text() {	// PURPOSE: [[Prawn]] and {{Taxobox}} was dumping text
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last
			(	"{|"
			,	"|-"
			,	"|-id='a'"
			,	"|b"
			,	"|}"
			) ,	String_.Concat_lines_nl_skip_last
			(	"<table>"
			,	"  <tr id='a'>"
			,	"    <td>b"
			,	"    </td>"
			,	"  </tr>"
			,	"</table>"
			,	""
			)
			);
	}
}