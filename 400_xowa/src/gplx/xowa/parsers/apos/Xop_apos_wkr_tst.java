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
package gplx.xowa.parsers.apos; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import org.junit.*;
import gplx.xowa.parsers.lists.*;
public class Xop_apos_wkr_tst {
	private final    Xop_fxt fxt = new Xop_fxt();
	@Test  public void Basic() {
		fxt.Test_parse_page_wiki("''a''"			, fxt.tkn_apos_(Xop_apos_tkn_.Cmd_i_bgn)	, fxt.tkn_txt_(2, 3), fxt.tkn_apos_(Xop_apos_tkn_.Cmd_i_end));
		fxt.Test_parse_page_wiki("'''a'''"			, fxt.tkn_apos_(Xop_apos_tkn_.Cmd_b_bgn)	, fxt.tkn_txt_(3, 4), fxt.tkn_apos_(Xop_apos_tkn_.Cmd_b_end));
		fxt.Test_parse_page_wiki("'''''a'''''"		, fxt.tkn_apos_(Xop_apos_tkn_.Cmd_ib_bgn)	, fxt.tkn_txt_(5, 6), fxt.tkn_apos_(Xop_apos_tkn_.Cmd_bi_end));
	}
	@Test  public void Advanced() {
		fxt.Test_parse_page_wiki("''''a''''"			, fxt.tkn_apos_(Xop_apos_tkn_.Cmd_b_bgn).Apos_lit_(1)	, fxt.tkn_txt_(), fxt.tkn_apos_(Xop_apos_tkn_.Cmd_b_end).Apos_lit_(1));		// 1 apos + bold
		fxt.Test_parse_page_wiki("''''''''a''''''''"	, fxt.tkn_apos_(Xop_apos_tkn_.Cmd_ib_bgn).Apos_lit_(3)	, fxt.tkn_txt_(), fxt.tkn_apos_( Xop_apos_tkn_.Cmd_bi_end).Apos_lit_(3));	// 3 apos + dual
	}
	@Test  public void Combo() {
		fxt.Test_parse_page_wiki("''a'''b'''c''", fxt.tkn_apos_(Xop_apos_tkn_.Cmd_i_bgn), fxt.tkn_txt_(), fxt.tkn_apos_(Xop_apos_tkn_.Cmd_b_bgn), fxt.tkn_txt_(), fxt.tkn_apos_(Xop_apos_tkn_.Cmd_b_end), fxt.tkn_txt_(), fxt.tkn_apos_(Xop_apos_tkn_.Cmd_i_end));	// b{i}
		fxt.Test_parse_page_wiki("'''a''b''c'''", fxt.tkn_apos_(Xop_apos_tkn_.Cmd_b_bgn), fxt.tkn_txt_(), fxt.tkn_apos_(Xop_apos_tkn_.Cmd_i_bgn), fxt.tkn_txt_(), fxt.tkn_apos_(Xop_apos_tkn_.Cmd_i_end), fxt.tkn_txt_(), fxt.tkn_apos_(Xop_apos_tkn_.Cmd_b_end));	// i{b}
		fxt.Test_parse_page_wiki("''a''b'''c'''", fxt.tkn_apos_(Xop_apos_tkn_.Cmd_i_bgn), fxt.tkn_txt_(), fxt.tkn_apos_(Xop_apos_tkn_.Cmd_i_end), fxt.tkn_txt_(), fxt.tkn_apos_(Xop_apos_tkn_.Cmd_b_bgn), fxt.tkn_txt_(), fxt.tkn_apos_(Xop_apos_tkn_.Cmd_b_end));	// b_i
	}
	@Test  public void Assume_apos() {
		fxt.Test_parse_page_wiki("a01'''b01 '''c0 1'''d01''"		// pick c0 1, b/c it is idxNeg2
			, fxt.tkn_txt_()									, fxt.tkn_apos_(Xop_apos_tkn_.Cmd_b_bgn)
			, fxt.tkn_txt_(), fxt.tkn_space_()					, fxt.tkn_apos_(Xop_apos_tkn_.Cmd_b_end)
			, fxt.tkn_txt_(), fxt.tkn_space_(), fxt.tkn_txt_()	, fxt.tkn_apos_(Xop_apos_tkn_.Cmd_i_bgn)
			, fxt.tkn_txt_()									, fxt.tkn_apos_(Xop_apos_tkn_.Cmd_i_end));	// idx_neg2
		fxt.Test_parse_page_wiki("a01 '''b01 '''c01'''d01''"		// pick c01, b/c it is idxNone
			, fxt.tkn_txt_(), fxt.tkn_space_()					, fxt.tkn_apos_(Xop_apos_tkn_.Cmd_b_bgn)
			, fxt.tkn_txt_(), fxt.tkn_space_()					, fxt.tkn_apos_(Xop_apos_tkn_.Cmd_b_end)
			, fxt.tkn_txt_()									, fxt.tkn_apos_(Xop_apos_tkn_.Cmd_i_bgn)
			, fxt.tkn_txt_()									, fxt.tkn_apos_(Xop_apos_tkn_.Cmd_i_end));	// idx_none
		fxt.Test_parse_page_wiki("a01 '''b01 '''c01 '''d01''"		// pick a01 , b/c it is idxNeg1
			, fxt.tkn_txt_(), fxt.tkn_space_()					, fxt.tkn_apos_(Xop_apos_tkn_.Cmd_i_bgn)
			, fxt.tkn_txt_(), fxt.tkn_space_()					, fxt.tkn_apos_(Xop_apos_tkn_.Cmd_b_bgn)
			, fxt.tkn_txt_(), fxt.tkn_space_()					, fxt.tkn_apos_(Xop_apos_tkn_.Cmd_b_end)
			, fxt.tkn_txt_()									, fxt.tkn_apos_(Xop_apos_tkn_.Cmd_i_end));	// idx_neg1
		fxt.Test_parse_page_wiki("a''''b''"							// strange outlier condition
			, fxt.tkn_txt_()									, fxt.tkn_apos_(Xop_apos_tkn_.Cmd_i_bgn).Apos_lit_(2)
			, fxt.tkn_txt_()									, fxt.tkn_apos_(Xop_apos_tkn_.Cmd_i_end));	// 4 apos -> 2 apos + ital
	}
	@Test  public void Dual() {
		fxt.Test_parse_page_wiki("'''''a'''b''"		// +ib -b -i; 5apos defaults to ib
			, fxt.tkn_apos_(Xop_apos_tkn_.Cmd_ib_bgn), fxt.tkn_txt_(), fxt.tkn_apos_(Xop_apos_tkn_.Cmd_b_end), fxt.tkn_txt_(), fxt.tkn_apos_(Xop_apos_tkn_.Cmd_i_end));
		fxt.Test_parse_page_wiki("'''''a''b'''"		// +bi -i -b; change 5apos to bi
			, fxt.tkn_apos_(Xop_apos_tkn_.Cmd_bi_bgn), fxt.tkn_txt_(), fxt.tkn_apos_(Xop_apos_tkn_.Cmd_i_end), fxt.tkn_txt_(), fxt.tkn_apos_(Xop_apos_tkn_.Cmd_b_end));
		fxt.Test_parse_page_wiki("''b'''''c'''"		// 5q toggles ital n, bold y
			, fxt.tkn_apos_(Xop_apos_tkn_.Cmd_i_bgn), fxt.tkn_txt_(), fxt.tkn_apos_(Xop_apos_tkn_.Cmd_i_end__b_bgn), fxt.tkn_txt_(), fxt.tkn_apos_(Xop_apos_tkn_.Cmd_b_end));
	}
	@Test  public void Unclosed() {
		fxt.Test_parse_page_wiki("''a"
			, fxt.tkn_apos_(Xop_apos_tkn_.Cmd_i_bgn), fxt.tkn_txt_(), fxt.tkn_apos_(Xop_apos_tkn_.Cmd_i_end));
		fxt.Test_parse_page_wiki("'''a"
			, fxt.tkn_apos_(Xop_apos_tkn_.Cmd_b_bgn), fxt.tkn_txt_(), fxt.tkn_apos_(Xop_apos_tkn_.Cmd_b_end));
		fxt.Test_parse_page_wiki("'''''a"
			, fxt.tkn_apos_(Xop_apos_tkn_.Cmd_ib_bgn), fxt.tkn_txt_(), fxt.tkn_apos_(Xop_apos_tkn_.Cmd_bi_end));
	}
	@Test  public void Outliers() {
		fxt.Test_parse_page_wiki("''a'''b'''c'''"		// '''b -> ' +i b
			, fxt.tkn_apos_(Xop_apos_tkn_.Cmd_i_bgn), fxt.tkn_txt_(), fxt.tkn_apos_(Xop_apos_tkn_.Cmd_i_end).Apos_lit_(1)
			, fxt.tkn_txt_(), fxt.tkn_apos_(Xop_apos_tkn_.Cmd_b_bgn), fxt.tkn_txt_(), fxt.tkn_apos_(Xop_apos_tkn_.Cmd_b_end));
		fxt.Test_parse_page_wiki("''a'''b''c''"			// '''b -> ' +i b; double check with closing itals
			, fxt.tkn_apos_(Xop_apos_tkn_.Cmd_i_bgn), fxt.tkn_txt_(), fxt.tkn_apos_(Xop_apos_tkn_.Cmd_i_end).Apos_lit_(1)
			, fxt.tkn_txt_(), fxt.tkn_apos_(Xop_apos_tkn_.Cmd_i_bgn), fxt.tkn_txt_(), fxt.tkn_apos_(Xop_apos_tkn_.Cmd_i_end));
		fxt.Test_parse_page_wiki("''a'''b''c"			//	''c -> -bi + b
			, fxt.tkn_apos_(Xop_apos_tkn_.Cmd_i_bgn), fxt.tkn_txt_(), fxt.tkn_apos_(Xop_apos_tkn_.Cmd_b_bgn)
			, fxt.tkn_txt_(), fxt.tkn_apos_(Xop_apos_tkn_.Cmd_bi_end__b_bgn), fxt.tkn_txt_(), fxt.tkn_apos_(Xop_apos_tkn_.Cmd_b_end));
	}
	@Test  public void MultiLines() {
		fxt.Test_parse_page_wiki("a''b\nc''d"
			, fxt.tkn_txt_(), fxt.tkn_apos_(Xop_apos_tkn_.Cmd_i_bgn), fxt.tkn_txt_(3, 4), fxt.tkn_apos_(Xop_apos_tkn_.Cmd_i_end), fxt.tkn_nl_char_len1_(4)
			, fxt.tkn_txt_(5, 6), fxt.tkn_apos_(Xop_apos_tkn_.Cmd_i_bgn), fxt.tkn_txt_(), fxt.tkn_apos_(Xop_apos_tkn_.Cmd_i_end));
	}
	@Test  public void Lnki() {
		fxt.Test_parse_page_wiki_str("[[''a''']]", "<a href=\"/wiki/%27%27a%27%27%27\">''a'''</a>");
	}
	@Test  public void Dual_exceptions() {
		fxt.Test_parse_page_wiki("'''''a''b''"
			, fxt.tkn_apos_(Xop_apos_tkn_.Cmd_bi_bgn), fxt.tkn_txt_(), fxt.tkn_apos_(Xop_apos_tkn_.Cmd_i_end), fxt.tkn_txt_(), fxt.tkn_apos_(Xop_apos_tkn_.Cmd_i_bgn), fxt.tkn_apos_(Xop_apos_tkn_.Cmd_ib_end)
			);
	}
	@Test  public void Mix_list_autoClose() {
		fxt.Test_parse_page_wiki("''a\n*b"
			, fxt.tkn_apos_(Xop_apos_tkn_.Cmd_i_bgn).Src_rng_(0, 2)
			, fxt.tkn_txt_(2, 3)
			, fxt.tkn_apos_(Xop_apos_tkn_.Cmd_i_end).Src_rng_(3, 3)
			, fxt.tkn_list_bgn_(3, 5, Xop_list_tkn_.List_itmTyp_ul)
			, fxt.tkn_txt_(5, 6)
			, fxt.tkn_list_end_(6)
			);
	}
	@Test  public void Mix_hr_autoClose() {
		fxt.Test_parse_page_wiki("''a\n----"
			, fxt.tkn_apos_(Xop_apos_tkn_.Cmd_i_bgn).Src_rng_(0, 2)
			, fxt.tkn_txt_(2, 3)
			, fxt.tkn_apos_(Xop_apos_tkn_.Cmd_i_end).Src_rng_(3, 3)
			, fxt.tkn_para_blank_(3)
			, fxt.tkn_hr_(3, 8)
			);
	}
	@Test  public void Mix_hdr_autoClose() {
		fxt.Test_parse_page_wiki_str("''a\n==b==", "<i>a</i>\n\n<h2>b</h2>");
	}
	@Test  public void Apos_broken_by_tblw_th() {	// DATE:2013-04-24
		fxt.Test_parse_page_all_str("A ''[[b!!]]'' c", "A <i><a href=\"/wiki/B!!\">b!!</a></i> c");
	}
	@Test   public void Nowiki() {	// PAGE:en.w:Wiki; DATE:2013-05-13
		fxt.Test_parse_page_all_str("<nowiki>''a''</nowiki>", "''a''");
	}
	@Test  public void Lnki_multi_line() {	// PURPOSE: handle apos within multi-line lnki caption; DATE:2013-11-10
		fxt.Test_parse_page_all_str(String_.Concat_lines_nl_skip_last
		(	"[[A|b '' c"
		,	"d '' e ]]"
		)
		,	"<a href=\"/wiki/A\">b <i> c d </i> e</a>");	// NOTE: c d should be italicized, not c e (latter occurs when apos is ended on each line)
	}
	@Test  public void French() {	// PURPOSE: L'''A'' -> L'<i>A</i>; DATE:2014-01-06
		fxt.Test_parse_page_all_str("L''''A'''",	"L'<b>A</b>");
		fxt.Test_parse_page_all_str("L'''A''",	"L'<i>A</i>");
	}
//	@Test  public void Mix_lnke() {	// FUTURE: requires rewrite of apos
//		fxt.Test_parse_page_wiki("''a[irc://b c''d''e]f''"
//			, fxt.tkn_apos_(0, 2, Xop_apos_tkn_.Cmd_i_bgn)
//			, fxt.tkn_txt_(2, 3)
//			, fxt.tkn_lnke_(3, 20).Subs_add_ary
//			(	fxt.tkn_txt_(12, 13)
//			,	fxt.tkn_apos_(13, 15, Xop_apos_tkn_.Cmd_i_bgn)
//			,	fxt.tkn_txt_(15, 16)
//			,	fxt.tkn_apos_(16, 18, Xop_apos_tkn_.Cmd_i_end)
//			,	fxt.tkn_txt_(18, 19)
//			)
//			, fxt.tkn_txt_(20, 21)
//			, fxt.tkn_apos_(21, 23, Xop_apos_tkn_.Cmd_i_bgn)
//			);
//	}
}
/*
*/
