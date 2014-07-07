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
public class Xop_lnki_wkr_basic_tst {
	private Xop_fxt fxt = new Xop_fxt();
	@Before public void init() {fxt.Reset(); fxt.Init_para_n_();}
	@Test  public void Basic() {
		fxt.Test_parse_page_wiki("[[a]]", fxt.tkn_lnki_().Trg_tkn_(fxt.tkn_arg_val_txt_(2, 3)));
	}
	@Test  public void HtmlRef() {
		fxt.Test_parse_page_wiki("[[a&amp;b]]", fxt.tkn_lnki_().Trg_tkn_(fxt.tkn_arg_nde_().Val_tkn_(fxt.tkn_arg_itm_(fxt.tkn_txt_(2, 3), fxt.tkn_html_ref_("&amp;"), fxt.tkn_txt_(8, 9)))));
	}
	@Test  public void Url_encode() {	// PURPOSE:title should automatically do url decoding; DATE:2013-08-26
		fxt.Test_parse_page_all_str("[[A%20b]]", "<a href=\"/wiki/A_b\">A b</a>");
	}
	@Test  public void Url_encode_plus() {	// PURPOSE:do not decode plus; DATE:2013-08-26
		fxt.Test_parse_page_all_str("[[A+b]]", "<a href=\"/wiki/A%2Bb\">A+b</a>");
	}
	@Test  public void Caption() {
		fxt.Test_parse_page_wiki("[[a|b]]"	, fxt.tkn_lnki_().Trg_tkn_(fxt.tkn_arg_val_txt_(2, 3)).Caption_tkn_(fxt.tkn_arg_val_txt_(4, 5)));
		fxt.Test_parse_page_wiki("[[a|b:c]]", fxt.tkn_lnki_().Trg_tkn_(fxt.tkn_arg_val_txt_(2, 3)).Caption_tkn_(fxt.tkn_arg_val_(fxt.tkn_txt_(4, 5), fxt.tkn_colon_(5), fxt.tkn_txt_(6, 7))));
	}
	@Test  public void Caption_equal() {	// should ignore = if only caption arg (2 args)
		fxt.Test_parse_page_wiki("[[a|=]]", fxt.tkn_lnki_().Trg_tkn_(fxt.tkn_arg_val_txt_(2, 3)).Caption_tkn_(fxt.tkn_arg_val_(fxt.tkn_eq_(4))));
	}
	@Test  public void Caption_ampersand() {fxt.Test_parse_page_wiki_str("[[A|a & b]]", "<a href=\"/wiki/A\">a &amp; b</a>");}
	@Test  public void Tail() {
		fxt.Test_parse_page_wiki("[[a|b]]c" , fxt.tkn_lnki_(0, 8).Tail_bgn_(7).Tail_end_(8));
		fxt.Test_parse_page_wiki("[[a|b]] c", fxt.tkn_lnki_(0, 7).Tail_bgn_(-1), fxt.tkn_space_(7, 8), fxt.tkn_txt_(8, 9));
		fxt.Test_parse_page_wiki("[[a|b]]'c", fxt.tkn_lnki_(0, 7).Tail_bgn_(-1), fxt.tkn_txt_(7, 9));
	}
	@Test  public void Tail_number()	{fxt.Test_parse_page_wiki("[[a|b]]1" , fxt.tkn_lnki_(0, 7).Tail_bgn_(-1), fxt.tkn_txt_(7, 8));}	
	@Test  public void Tail_upper()		{fxt.Test_parse_page_wiki("[[a|b]]A" , fxt.tkn_lnki_(0, 7).Tail_bgn_(-1), fxt.tkn_txt_(7, 8));}	// make sure trie is case-insensitive
	@Test  public void Tail_image()		{fxt.Test_parse_page_wiki("[[Image:a|b]]c", fxt.tkn_lnki_(0, 13).Tail_bgn_(-1).Tail_end_(-1), fxt.tkn_txt_(13, 14));}	// NOTE: this occurs on some pages;
	@Test  public void Image() {
		fxt.Test_parse_page_wiki("[[Image:a]]"				, fxt.tkn_lnki_().Ns_id_(Xow_ns_.Id_file).Trg_tkn_(fxt.tkn_arg_val_(fxt.tkn_txt_(2, 7), fxt.tkn_colon_(7), fxt.tkn_txt_(8, 9))));
		fxt.Test_parse_page_wiki("[[Image:a|border]]"		, fxt.tkn_lnki_().Border_(Bool_.Y_byte));
		fxt.Test_parse_page_wiki("[[Image:a|thumb]]"			, fxt.tkn_lnki_().ImgType_(Xop_lnki_type.Id_thumb));
		fxt.Test_parse_page_wiki("[[Image:a|left]]"			, fxt.tkn_lnki_().HAlign_(Xop_lnki_align_h.Left));
		fxt.Test_parse_page_wiki("[[Image:a|top]]"			, fxt.tkn_lnki_().VAlign_(Xop_lnki_align_v.Top));
		fxt.Test_parse_page_wiki("[[Image:a|10px]]"			, fxt.tkn_lnki_().Width_(10).Height_(-1));
		fxt.Test_parse_page_wiki("[[Image:a|20x30px]]"		, fxt.tkn_lnki_().Width_(20).Height_(30));
		fxt.Test_parse_page_wiki("[[Image:a|alt=b]]"			, fxt.tkn_lnki_().Alt_tkn_(fxt.tkn_arg_nde_().Key_tkn_(fxt.tkn_arg_itm_(fxt.tkn_txt_(10, 13))).Val_tkn_(fxt.tkn_arg_itm_(fxt.tkn_txt_(14, 15)))));
		fxt.Test_parse_page_wiki("[[Image:a|link=a]]"		, fxt.tkn_lnki_().Link_tkn_(fxt.tkn_arg_nde_().Key_tkn_(fxt.tkn_arg_itm_(fxt.tkn_txt_(10, 14))).Val_tkn_(fxt.tkn_arg_itm_(fxt.tkn_txt_(15, 16)))));
		fxt.Test_parse_page_wiki("[[Image:a|thumb|alt=b|c d]]"
			, fxt.tkn_lnki_().Ns_id_(Xow_ns_.Id_file)
			.Trg_tkn_(fxt.tkn_arg_nde_().Val_tkn_(fxt.tkn_arg_itm_(fxt.tkn_txt_(2, 7), fxt.tkn_colon_(7), fxt.tkn_txt_(8, 9))))
			.Alt_tkn_(fxt.tkn_arg_nde_().Key_tkn_(fxt.tkn_arg_itm_(fxt.tkn_txt_(16, 19))).Val_tkn_(fxt.tkn_arg_itm_(fxt.tkn_txt_(20, 21))))
			.Caption_tkn_(fxt.tkn_arg_nde_(22, 25).Val_tkn_(fxt.tkn_arg_itm_(fxt.tkn_txt_(22, 23), fxt.tkn_space_(23, 24), fxt.tkn_txt_(24, 25)))))
			;
	}
	@Test  public void Thumbtime() {
		fxt.Test_parse_page_wiki("[[File:A.ogv|thumbtime=123]]", fxt.tkn_lnki_().Thumbtime_(123));
		fxt.Test_parse_page_wiki("[[File:A.ogv|thumbtime=1:23]]", fxt.tkn_lnki_().Thumbtime_(83));
		fxt.Test_parse_page_wiki("[[File:A.ogv|thumbtime=1:01:01]]", fxt.tkn_lnki_().Thumbtime_(3661));
		fxt.Init_log_(Xop_lnki_log.Upright_val_is_invalid).Test_parse_page_wiki("[[File:A.ogv|thumbtime=a]]", fxt.tkn_lnki_().Thumbtime_(-1));
	}
	@Test  public void Width_ws() {
		fxt.Test_parse_page_wiki("[[Image:a| 123 px]]"		, fxt.tkn_lnki_().Width_(123));
	}
	@Test  public void Height() {
		fxt.Test_parse_page_wiki("[[Image:a|x40px]]"			, fxt.tkn_lnki_().Height_(40).Width_(-1));
	}
	@Test  public void Size_double_px_ignored() {
		fxt.Test_parse_page_wiki("[[Image:a|40pxpx]]"		, fxt.tkn_lnki_().Width_(40).Height_(-1));
	}
	@Test  public void Size_px_px_ignored() {
		fxt.Test_parse_page_wiki("[[Image:a|40px20px]]"		, fxt.tkn_lnki_().Width_(-1).Height_(-1));
	}
	@Test  public void Size_double_px_ws_allowed() {
		fxt.Test_parse_page_wiki("[[Image:a|40pxpx  ]]"		, fxt.tkn_lnki_().Width_(40).Height_(-1));
	}
	@Test  public void Size_double_px_ws_allowed_2() {	// PURPOSE: handle ws between px's; EX:sv.w:Drottningholms_slott; DATE:2014-03-01
		fxt.Test_parse_page_wiki("[[Image:a|40px px]]"		, fxt.tkn_lnki_().Width_(40).Height_(-1));
	}
	@Test  public void Size_large_numbers() {	// PURPOSE: perf code identified large sizes as caption; DATE:2014-02-15
		fxt.Test_parse_page_wiki("[[Image:a|1234567890x1234567890px]]"		, fxt.tkn_lnki_().Width_(1234567890).Height_(1234567890));
	}
	@Test  public void Size_dangling_xnde() {	// PURPOSE: dangling xnde should not eat rest of lnki; PAGE:sr.w:Сићевачка_клисура DATE:2014-07-03
		fxt.Init_log_(Xop_xnde_log.Dangling_xnde).Test_parse_page_wiki("[[Image:a.png|<b>c|40px]]"	, fxt.tkn_lnki_().Width_(40).Height_(-1));
	}
	@Test   public void Size_ws_para() {	// PURPOSE: <p> in arg_bldr causes parse to fail; EX: w:Supreme_Court_of_the_United_States; DATE:2014-04-05
		fxt.Init_para_y_();
		fxt.Test_parse_page_all("[[File:A.png| \n 40px]]"
		, fxt.tkn_para_bgn_para_(0)
		, fxt.tkn_lnki_().Width_(40).Height_(-1)
		, fxt.tkn_para_end_pre_(22));	// NOTE: this is wrong, but can be ignored b/c </pre> is not processed inside lnki
		fxt.Init_para_n_();
	}

	@Test  public void Image_upright() {
		fxt.Test_parse_page_wiki("[[Image:a|upright=.123]]"	, fxt.tkn_lnki_().Upright_(.123));
		fxt.Test_parse_page_wiki("[[Image:a|upright]]"		, fxt.tkn_lnki_().Upright_(1));		// no eq tokn
		fxt.Test_parse_page_wiki("[[Image:a|upright=.42190046219457]]", fxt.tkn_lnki_().Upright_(.42190046219457));	// many decimal places breaks upright
		fxt.Init_log_(Xop_lnki_log.Upright_val_is_invalid)
		  .Test_parse_page_wiki("[[Image:a|upright=y]]"		, fxt.tkn_lnki_().Upright_(-1));		// invalid
	}
	@Test  public void Recurse() {
		fxt.Test_parse_page_wiki("[[Image:a|b-[[c]]-d]]"
			, fxt.tkn_lnki_().Ns_id_(Xow_ns_.Id_file)
			.Trg_tkn_(fxt.tkn_arg_nde_().Val_tkn_(fxt.tkn_arg_itm_(fxt.tkn_txt_(2, 7), fxt.tkn_colon_(7), fxt.tkn_txt_(8, 9))))
			.Caption_tkn_(fxt.tkn_arg_nde_(10, 19).Val_tkn_(fxt.tkn_arg_itm_(fxt.tkn_txt_(10, 12), fxt.tkn_lnki_(12, 17), fxt.tkn_txt_(17, 19))))
			);
	}
	@Test  public void Outliers() {
		fxt.Test_parse_page_wiki("[[Image:a=b.svg|thumb|10px]]", fxt.tkn_lnki_().Ns_id_(Xow_ns_.Id_file).Trg_tkn_(fxt.tkn_arg_nde_().Val_tkn_(fxt.tkn_arg_itm_(fxt.tkn_txt_(2, 7), fxt.tkn_colon_(7), fxt.tkn_txt_(8, 9), fxt.tkn_eq_(9), fxt.tkn_txt_(10, 15)))));
		fxt.Test_parse_page_wiki("[[Category:a|b]]", fxt.tkn_lnki_().Ns_id_(Xow_ns_.Id_category));
	}
	@Test  public void Exc_caption_has_eq() {
		fxt.Test_parse_page_wiki("[[Image:a.svg|thumb|a=b]]", fxt.tkn_lnki_().Ns_id_(Xow_ns_.Id_file)
			.Caption_tkn_(fxt.tkn_arg_nde_(20, 23).Val_tkn_(fxt.tkn_arg_itm_(fxt.tkn_txt_(20, 21), fxt.tkn_eq_(21), fxt.tkn_txt_(22, 23)))));
	}
	@Test  public void Border_with_space_should_not_be_caption() {// happens with {{flag}}; EX: [[Image:Flag of Argentina.svg|22x20px|border |alt=|link=]]
		Xop_root_tkn root = fxt.Test_parse_page_wiki_root("[[Image:a.svg|22x20px|border |alt=|link=]]");
		Xop_lnki_tkn lnki = (Xop_lnki_tkn)root.Subs_get(0);
		Tfds.Eq(Xop_tkn_itm_.Tid_null, lnki.Caption_tkn().Tkn_tid());
	}
	@Test  public void Ws_key_bgn() {
		fxt.Test_parse_page_wiki("[[Image:a| alt=b]]", fxt.tkn_lnki_()
			.Alt_tkn_(fxt.tkn_arg_nde_()
			.	Key_tkn_(fxt.tkn_arg_itm_(fxt.tkn_space_(10, 11).Ignore_y_(), fxt.tkn_txt_(11, 14)))
			.	Val_tkn_(fxt.tkn_arg_itm_(fxt.tkn_txt_(15, 16)))
			));
	}
	@Test  public void Ws_key_end() {
		fxt.Test_parse_page_wiki("[[Image:a|alt =b]]", fxt.tkn_lnki_()
			.Caption_tkn_(fxt.tkn_arg_nde_(10, 16)
			.	Val_tkn_(fxt.tkn_arg_itm_(fxt.tkn_txt_(10, 13), fxt.tkn_space_(13, 14), fxt.tkn_eq_(14), fxt.tkn_txt_(15, 16)))
			));
	}
	@Test  public void Ws_val_bgn() {
		fxt.Test_parse_page_wiki("[[Image:a|alt= b]]", fxt.tkn_lnki_()
			.Alt_tkn_(fxt.tkn_arg_nde_()
			.	Key_tkn_(fxt.tkn_arg_itm_(fxt.tkn_txt_(10, 13)))
			.	Val_tkn_(fxt.tkn_arg_itm_(fxt.tkn_space_(14, 15), fxt.tkn_txt_(15, 16)))
			));
	}
	@Test  public void Ws_val_end() {
		fxt.Test_parse_page_wiki("[[Image:a|alt=b ]]", fxt.tkn_lnki_()
			.Alt_tkn_(fxt.tkn_arg_nde_()
			.	Key_tkn_(fxt.tkn_arg_itm_(fxt.tkn_txt_(10, 13)))
			.	Val_tkn_(fxt.tkn_arg_itm_(fxt.tkn_txt_(14, 15), fxt.tkn_space_(15, 16).Ignore_y_()))
			));
	}
	@Test  public void Ws_val_only() {	// simpler variation of Ws_val_size
		fxt.Test_parse_page_wiki("[[Image:a| b ]]", fxt.tkn_lnki_()
			.Caption_tkn_(fxt.tkn_arg_nde_()
			.	Val_tkn_(fxt.tkn_arg_itm_(fxt.tkn_space_(10, 11), fxt.tkn_txt_(11, 12), fxt.tkn_space_(12, 13)))
			));
	}
	@Test  public void Ws_val_size() {
		fxt.Test_parse_page_wiki("[[Image:a| 20x30px ]]"	, fxt.tkn_lnki_().Width_(20).Height_(30));
	}
	@Test  public void Nl_pipe() {		// PURPOSE: "\n|" triggers tblw; PAGE:fr.w:France; [[Fichier:Carte demographique de la France.svg
		fxt.Test_parse_page_wiki("[[Image:A.png|thumb\n|alt=test]]", fxt.tkn_lnki_()
			.Alt_tkn_(fxt.tkn_arg_nde_()
			.	Key_tkn_(fxt.tkn_arg_itm_(fxt.tkn_txt_(21, 24)))
			.	Val_tkn_(fxt.tkn_arg_itm_(fxt.tkn_txt_(25, 29)))
			));
	}
	@Test  public void Exc_empty_caption() {
		fxt.Test_parse_page_wiki("[[a|]]", fxt.tkn_lnki_().Trg_tkn_(fxt.tkn_arg_val_txt_(2, 3)));
	}
	@Test  public void Exc_empty() {
		fxt.Init_log_(Xop_ttl_log.Len_0, Xop_lnki_log.Invalid_ttl);
		fxt.Test_parse_page_wiki("[[]]", fxt.tkn_txt_(0, 2), fxt.tkn_txt_(2, 4));
		fxt.Init_log_(Xop_ttl_log.Len_0, Xop_lnki_log.Invalid_ttl);
		fxt.Test_parse_page_wiki("[[ ]]", fxt.tkn_txt_(0, 2), fxt.tkn_space_(2, 3), fxt.tkn_txt_(3, 5));
	}
	@Test  public void Exc_invalid_utf8() {	// PURPOSE: "%DO" is an invalid UTF-8 sequence (requires 2 bytes, not just %D0); DATE:2013-11-11
		fxt.Ctx().Lang().Case_mgr_utf8_();						// NOTE: only occurs during Universal
		fxt.Test_parse_page_all_str("[[%D0]]", "[[%D0]]");		// invalid titles render literally
	}
	@Test  public void Ex_eq() {	// make sure that eq is not evaluated for kv delimiter
		fxt.Test_parse_page_wiki("[[=]]", fxt.tkn_lnki_(0, 5));
		fxt.Test_parse_page_wiki("[[a|=]]", fxt.tkn_lnki_(0, 7));
	}
	@Test  public void Unclosed() {	// PURPOSE: unclosed lnki skips rendering of next table; EX.WP:William Penn (Royal Navy officer)
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last
			(	"a [[b|c]"
			,	""
			,	"{|"
			,	"|-"
			,	"|d"
			,	"|}"
			),String_.Concat_lines_nl_skip_last
			(	"a [[b|c] "	// NOTE: \n is converted to \s b/c caption does not allow \n
			,	"<table>"
			,	"  <tr>"
			,	"    <td>d"
			,	"    </td>"
			,	"  </tr>"
			,	"</table>"
			,	""
			));
	}
	@Test  public void Caption_nl() {	// PURPOSE: \n in caption should be rendered as space; EX.WP:Schwarzschild radius; and the stellar [[Velocity dispersion|velocity\ndispersion]]
		fxt.Init_para_y_();
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last
			(	"a [[b|c"
			,	""
			,	""
			,	"d]]"
			),	String_.Concat_lines_nl_skip_last
			(	"<p>a <a href=\"/wiki/B\">c   d</a>"	// NOTE: this depends on html viewer to collapse multiple spaces into 1
			,	"</p>"
			,	""
			));
		fxt.Init_para_n_();
	}
	@Test  public void Caption_nl_2() {	// PURPOSE: unclosed lnki breaks paragraph unexpectedly; EX.WP:Oldsmobile;
		fxt.Init_para_y_();
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last
			(	"a"
			,	""
			,	"b [[c"
			,	""	// NOTE: this new line is needed to produce strange behavior
			), String_.Concat_lines_nl_skip_last
			(	"<p>a"
			,	"</p>"
			,	""
			,	"<p>b [[c"	// NOTE: \n is converted to \s b/c caption does not allow \n; NOTE: removed \s after "c" due to new lnki invalidation;DATE:2014-04-03
			,	"</p>"
			,	""
			));
		fxt.Init_para_n_();
	}
	@Test  public void Caption_ref() {	// PURPOSE: <ref> not handled in lnki; EX.WP:WWI
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last
			(	"[[File:A.png|thumb|b <ref>c</ref>]]"
			), String_.Concat_lines_nl_skip_last
			(	"<div class=\"thumb tright\">"
			,	"  <div id=\"xowa_file_div_0\" class=\"thumbinner\" style=\"width:220px;\">"
			,	"    <a href=\"/wiki/File:A.png\" class=\"image\" xowa_title=\"A.png\"><img id=\"xowa_file_img_0\" alt=\"\" src=\"file:///mem/wiki/repo/trg/thumb/7/0/A.png/220px.png\" width=\"0\" height=\"0\" /></a>"
			,	"    <div class=\"thumbcaption\">"
			,	"      <div class=\"magnify\">"
			,	"        <a href=\"/wiki/File:A.png\" class=\"inte" +"rnal\" title=\"Enlarge\">"
			,	"          <img src=\"file:///mem/xowa/user/test_user/app/img/file/magnify-clip.png\" width=\"15\" height=\"11\" alt=\"\" />"
			,	"        </a>"
			,	"      </div>"
			,	"      b <sup id=\"cite_ref-0\" class=\"reference\"><a href=\"#cite_note-0\">[1]</a></sup>"
			,	"    </div>"
			,	"  </div>"
			,	"</div>"
			,	""
			));
	}
//		@Test   public void Subpage_parent() {	// DISABLED: MW does not allow ../ to go past root; DATE:2014-01-02
//			fxt.Page_ttl_("A");
//			fxt.Test_parse_page_wiki_str
//				(	"[[b/../c]]"
//				,	"<a href=\"/wiki/C\">c</a>"
//				);
//		}
	@Test  public void Subpage_disabled() {	// PURPOSE: slash being interpreted as subpage; EX.WP:[[/dev/null]]
		fxt.Wiki().Ns_mgr().Ids_get_or_null(Xow_ns_.Id_main).Subpages_enabled_(false);
		fxt.Test_parse_page_all_str("[[/dev/null]]", "<a href=\"/wiki//dev/null\">/dev/null</a>");
		fxt.Wiki().Ns_mgr().Ids_get_or_null(Xow_ns_.Id_main).Subpages_enabled_(true);
	}
	@Test  public void Subpage_false_match() {// EX.WP:en.wiktionary.org/wiki/Wiktionary:Requests for cleanup/archive/2006
		fxt.Test_parse_page_wiki_str
			(	"[[.../compare ...]]"
			,	"<a href=\"/wiki/.../compare_...\">.../compare ...</a>"
			);
	}
	@Test  public void Subpage_owner() {	// PURPOSE: ../c does "A/c", not "c"; DATE:2014-01-02
		fxt.Page_ttl_("A/b");
		fxt.Test_parse_page_wiki_str
			(	"[[../c]]"
			,	"<a href=\"/wiki/A/c\">A/c</a>"
			);
	}
	@Test  public void Subpage_owner_w_slash() {	// PURPOSE: ../c/ does "c", not "A/c"; DATE:2014-01-02
		fxt.Page_ttl_("A/b");
		fxt.Test_parse_page_wiki_str
			(	"[[../c/]]"
			,	"<a href=\"/wiki/A/c\">c</a>"
			);
	}
	@Test  public void Subpage_slash() {	// PURPOSE: /B should show /B, not A/B; DATE:2014-01-02
		fxt.Page_ttl_("A");
		fxt.Test_parse_page_wiki_str
			(	"[[/B]]", String_.Concat_lines_nl_skip_last
			(	"<a href=\"/wiki/A/B\">/B</a>"
			));
	}
	@Test  public void Subpage_slash_w_slash() {	// PURPOSE: /B/ should show B, not /B; DATE:2014-01-02
		fxt.Page_ttl_("A");
		fxt.Test_parse_page_wiki_str
			(	"[[/B/]]", String_.Concat_lines_nl_skip_last
			(	"<a href=\"/wiki/A/B\">B</a>"
			));
	}
	@Test  public void Link() {
		fxt.Test_parse_page_wiki_str
			(	"[[File:A.png|12x10px|link=File:B.png|c]]", String_.Concat_lines_nl_skip_last
			(	"<a href=\"/wiki/File:B.png\" class=\"image\" xowa_title=\"A.png\"><img id=\"xowa_file_img_0\" alt=\"c\" src=\"file:///mem/wiki/repo/trg/thumb/7/0/A.png/12px.png\" width=\"12\" height=\"10\" /></a>"
			));
	}
	@Test  public void Link_blank() {
		fxt.Test_parse_page_wiki_str
			(	"[[File:A.png|12x10px|link=|c]]", String_.Concat_lines_nl_skip_last
			(	"<a href=\"/wiki/File:A.png\" class=\"image\" xowa_title=\"A.png\"><img id=\"xowa_file_img_0\" alt=\"c\" src=\"file:///mem/wiki/repo/trg/thumb/7/0/A.png/12px.png\" width=\"12\" height=\"10\" /></a>"
			));
	}
	@Test  public void Link_external() {
		fxt.Test_parse_page_wiki_str
			(	"[[File:A.png|12x10px|link=http://www.b.org|c]]", String_.Concat_lines_nl_skip_last
			(	"<a href=\"http://www.b.org\" rel=\"nofollow\" xowa_title=\"A.png\"><img id=\"xowa_file_img_0\" alt=\"c\" src=\"file:///mem/wiki/repo/trg/thumb/7/0/A.png/12px.png\" width=\"12\" height=\"10\" /></a>"
			));
	}
	@Test  public void Link_file_system() {
		fxt.Test_parse_page_wiki_str
			(	"[[File:A.png|12x10px|link=file:///C/B.png|c]]", String_.Concat_lines_nl_skip_last
			(	"<a href=\"file:///C/B.png\" class=\"image\" xowa_title=\"B.png\"><img id=\"xowa_file_img_0\" alt=\"c\" src=\"file:///mem/wiki/repo/trg/thumb/7/0/A.png/12px.png\" width=\"12\" height=\"10\" /></a>"
			));
	}
	@Test  public void Link_file_ns() {
		fxt.Test_parse_page_wiki_str
			(	"[[File:A.png|12x10px|link=File:B.png|c]]", String_.Concat_lines_nl_skip_last
			(	"<a href=\"/wiki/File:B.png\" class=\"image\" xowa_title=\"A.png\"><img id=\"xowa_file_img_0\" alt=\"c\" src=\"file:///mem/wiki/repo/trg/thumb/7/0/A.png/12px.png\" width=\"12\" height=\"10\" /></a>"
			));
	}
	@Test  public void Link_external_relative() {
		fxt.Test_parse_page_wiki_str
			(	"[[File:A.png|12x10px|link=//fr.wikipedia.org/wiki/|c]]", String_.Concat_lines_nl_skip_last
			(	"<a href=\"http://fr.wikipedia.org/wiki/\" rel=\"nofollow\" xowa_title=\"A.png\"><img id=\"xowa_file_img_0\" alt=\"c\" src=\"file:///mem/wiki/repo/trg/thumb/7/0/A.png/12px.png\" width=\"12\" height=\"10\" /></a>"
			));
	}
	@Test  public void Link_external_absolute() {
		fxt.Test_parse_page_wiki_str
			(	"[[File:A.png|12x10px|link=http://fr.wikipedia.org/wiki/|c]]", String_.Concat_lines_nl_skip_last
			(	"<a href=\"http://fr.wikipedia.org/wiki/\" rel=\"nofollow\" xowa_title=\"A.png\"><img id=\"xowa_file_img_0\" alt=\"c\" src=\"file:///mem/wiki/repo/trg/thumb/7/0/A.png/12px.png\" width=\"12\" height=\"10\" /></a>"
			));
	}
	@Test  public void Link_external_double_http() {// PURPOSE.fix: link=http://a.org?b=http://c.org breaks lnki; DATE:2013-02-03
		fxt.Test_parse_page_wiki_str
			(	"[[File:A.png|12x10px|link=//a.org?b=http://c.org]]", String_.Concat_lines_nl_skip_last
			(	"<a href=\"http://a.org?b=http://c.org\" rel=\"nofollow\" xowa_title=\"A.png\"><img id=\"xowa_file_img_0\" alt=\"\" src=\"file:///mem/wiki/repo/trg/thumb/7/0/A.png/12px.png\" width=\"12\" height=\"10\" /></a>"
			));
	}
	@Test  public void Link_xwiki_relative() {	// NOTE: changed href to return "wiki/" instead of "wiki"; DATE:2013-02-18
		fxt.Init_xwiki_add_user_("fr.wikipedia.org");
		fxt.Test_parse_page_wiki_str
			(	"[[File:A.png|12x10px|link=//fr.wikipedia.org/wiki/|c]]", String_.Concat_lines_nl_skip_last
			(	"<a href=\"/site/fr.wikipedia.org/wiki/\" rel=\"nofollow\" xowa_title=\"A.png\"><img id=\"xowa_file_img_0\" alt=\"c\" src=\"file:///mem/wiki/repo/trg/thumb/7/0/A.png/12px.png\" width=\"12\" height=\"10\" /></a>"
			));
	}
	@Test  public void Link_xwiki_relative_domain_only() {	// lnki_wtr fails if link is only domain; EX: wikimediafoundation.org; [[Image:Wikispecies-logo.png|35px|link=//species.wikimedia.org]]; // NOTE: changed href to return "/wiki/" instead of ""; DATE:2013-02-18
		fxt.Init_xwiki_add_user_("fr.wikipedia.org");
		fxt.Test_parse_page_wiki_str
			(	"[[File:A.png|12x10px|link=//fr.wikipedia.org]]", String_.Concat_lines_nl_skip_last
			(	"<a href=\"/site/fr.wikipedia.org/wiki/\" rel=\"nofollow\" xowa_title=\"A.png\"><img id=\"xowa_file_img_0\" alt=\"\" src=\"file:///mem/wiki/repo/trg/thumb/7/0/A.png/12px.png\" width=\"12\" height=\"10\" /></a>"
			));
	}
	@Test  public void Link_xwiki_absolute() {	// NOTE: changed href to return "wiki/" instead of "wiki"; DATE:2013-02-18
		fxt.Init_xwiki_add_user_("fr.wikipedia.org");
		fxt.Test_parse_page_wiki_str
			(	"[[File:A.png|12x10px|link=http://fr.wikipedia.org/wiki/|c]]", String_.Concat_lines_nl_skip_last
			(	"<a href=\"/site/fr.wikipedia.org/wiki/\" rel=\"nofollow\" xowa_title=\"A.png\"><img id=\"xowa_file_img_0\" alt=\"c\" src=\"file:///mem/wiki/repo/trg/thumb/7/0/A.png/12px.png\" width=\"12\" height=\"10\" /></a>"
			));
	}
	@Test  public void Link_xwiki_absolute_upload() {	// PURPOSE: link to upload.wikimedia.org omits /wiki/; EX: wikimediafoundation.org: [[File:Page1-250px-WMF_AR11_SHIP_spreads_15dec11_72dpi.png|right|125px|border|2010–2011 Annual Report|link=https://upload.wikimedia.org/wikipedia/commons/4/48/WMF_AR11_SHIP_spreads_15dec11_72dpi.pdf]]
		fxt.Init_xwiki_add_user_("commons.wikimedia.org");
		fxt.Test_parse_page_wiki_str
			(	"[[File:A.png|12x10px|link=http://upload.wikimedia.org/wikipedia/commons/7/70/A.png|c]]", String_.Concat_lines_nl_skip_last
			(	"<a href=\"/site/commons.wikimedia.org/wiki/File:A.png\" rel=\"nofollow\" xowa_title=\"A.png\"><img id=\"xowa_file_img_0\" alt=\"c\" src=\"file:///mem/wiki/repo/trg/thumb/7/0/A.png/12px.png\" width=\"12\" height=\"10\" /></a>"
			));
	}
	@Test  public void Link_xwiki_relative_deep() {
		fxt.Init_xwiki_add_user_("fr.wikipedia.org");
		fxt.Test_parse_page_wiki_str
			(	"[[File:A.png|12x10px|link=//fr.wikipedia.org/wiki/A/b|c]]", String_.Concat_lines_nl_skip_last
			(	"<a href=\"/site/fr.wikipedia.org/wiki/A/b\" rel=\"nofollow\" xowa_title=\"A.png\"><img id=\"xowa_file_img_0\" alt=\"c\" src=\"file:///mem/wiki/repo/trg/thumb/7/0/A.png/12px.png\" width=\"12\" height=\"10\" /></a>"
			));
	}
	@Test  public void Link_xwiki_alias() {	// [[File:Commons-logo.svg|25x25px|link=http://en.wikipedia.org/wiki/commons:Special:Search/Earth|alt=|Search Commons]]
		fxt.Init_xwiki_add_wiki_and_user_("commons", "commons.wikimedia.org");
		fxt.Test_parse_page_wiki_str
			(	"[[File:A.png|12x10px|link=http://en.wikipedia.org/wiki/commons:B|c]]", String_.Concat_lines_nl_skip_last
			(	"<a href=\"/site/commons.wikimedia.org/wiki/B\" rel=\"nofollow\" xowa_title=\"A.png\"><img id=\"xowa_file_img_0\" alt=\"c\" src=\"file:///mem/wiki/repo/trg/thumb/7/0/A.png/12px.png\" width=\"12\" height=\"10\" /></a>"
			));
		fxt.Init_xwiki_clear();
	}
	@Test  public void Link_xwiki_alias_sub_page() {	// same as above, but for sub-page; [[File:Commons-logo.svg|25x25px|link=http://en.wikipedia.org/wiki/commons:Special:Search/Earth|alt=|Search Commons]]
		fxt.Init_xwiki_add_wiki_and_user_("commons", "commons.wikimedia.org");
		fxt.Test_parse_page_wiki_str
			(	"[[File:A.png|12x10px|link=http://en.wikipedia.org/wiki/commons:Special:Search/B|c]]", String_.Concat_lines_nl_skip_last
			(	"<a href=\"/site/commons.wikimedia.org/wiki/Special:Search/B\" rel=\"nofollow\" xowa_title=\"A.png\"><img id=\"xowa_file_img_0\" alt=\"c\" src=\"file:///mem/wiki/repo/trg/thumb/7/0/A.png/12px.png\" width=\"12\" height=\"10\" /></a>"
			));
		fxt.Init_xwiki_clear();
	}
	@Test  public void Link_xwiki_alias_only() {	// only alias; [[File:Commons-logo.svg|25x25px|link=commons:Special:Search/Earth]]; fictitious example; DATE:2013-02-18
		fxt.Init_xwiki_add_wiki_and_user_("commons", "commons.wikimedia.org");
		fxt.Test_parse_page_wiki_str
			(	"[[File:A.png|12x10px|link=commons:Special:Search/B|c]]", String_.Concat_lines_nl_skip_last
			(	"<a href=\"/site/commons.wikimedia.org/wiki/Special:Search/B\" class=\"image\" xowa_title=\"A.png\"><img id=\"xowa_file_img_0\" alt=\"c\" src=\"file:///mem/wiki/repo/trg/thumb/7/0/A.png/12px.png\" width=\"12\" height=\"10\" /></a>"
			));
		fxt.Init_xwiki_clear();
	}
	@Test  public void Link_xwiki_alias_only_colon() {	// only alias, but prepended with ":"; [[File:Wikipedia-logo.svg|40px|link=:w:|Wikipedia]]; DATE:2013-05-06
		fxt.Init_xwiki_add_wiki_and_user_("commons", "commons.wikimedia.org");
		fxt.Test_parse_page_wiki_str
			(	"[[File:A.png|12x10px|link=:commons:A/B|c]]", String_.Concat_lines_nl_skip_last
			(	"<a href=\"/site/commons.wikimedia.org/wiki/A/B\" class=\"image\" xowa_title=\"A.png\"><img id=\"xowa_file_img_0\" alt=\"c\" src=\"file:///mem/wiki/repo/trg/thumb/7/0/A.png/12px.png\" width=\"12\" height=\"10\" /></a>"
			));
		fxt.Init_xwiki_clear();
	}
	@Test  public void Xwiki_file() {	// PURPOSE: if xwiki and File, ignore xwiki (hackish); DATE:2013-12-22
		Reg_xwiki_alias("test", "test.wikimedia.org");													// must register xwiki, else ttl will not parse it
		fxt.Wiki().Cfg_parser().Lnki_cfg().Xwiki_repo_mgr().Add_or_mod(Bry_.new_ascii_("test"));	// must add to xwiki_repo_mgr
		fxt.Test_parse_page_wiki_str
			(	"[[test:File:A.png|12x10px]]", String_.Concat_lines_nl_skip_last
			(	"<a href=\"/wiki/File:A.png\" class=\"image\" xowa_title=\"A.png\"><img id=\"xowa_file_img_0\" alt=\"\" src=\"file:///mem/wiki/repo/trg/thumb/7/0/A.png/12px.png\" width=\"12\" height=\"10\" /></a>"
			));
		fxt.Wiki().Cfg_parser_lnki_xwiki_repos_enabled_(false);	// disable for other tests
	}
	@Test  public void Xwiki_anchor() {
		Reg_xwiki_alias("test", "test.wikimedia.org");
		fxt.Test_parse_page_wiki_str
			(	"[[test:A#b]]", String_.Concat_lines_nl_skip_last
			(	"<a href=\"/site/test.wikimedia.org/wiki/A#b\">test:A#b</a>"
			));
	}
	@Test  public void Xwiki_empty() {
		Reg_xwiki_alias("test", "test.wikimedia.org");
		fxt.Test_parse_page_wiki_str
			(	"[[test:]]", String_.Concat_lines_nl_skip_last
			(	"<a href=\"/site/test.wikimedia.org/wiki/\">test:</a>"
			));
	}
	@Test  public void Xwiki_empty_literal() {
		Reg_xwiki_alias("test", "test.wikimedia.org");
		fxt.Test_parse_page_wiki_str
			(	"[[:test:]]", String_.Concat_lines_nl_skip_last
			(	"<a href=\"/site/test.wikimedia.org/wiki/\">test:</a>"
			));
	}
	@Test  public void Xwiki_not_registered() {
		fxt.App().User().Wiki().Xwiki_mgr().Clear();
		fxt.Wiki().Xwiki_mgr().Add_full(Bry_.new_ascii_("test"), Bry_.new_ascii_("test.wikimedia.org"));	// register alias only, but not in user_wiki
		fxt.Test_parse_page_wiki_str
			(	"[[test:A|A]]", String_.Concat_lines_nl_skip_last
			(	"<a href=\"http://test.wikimedia.org/wiki/A\">A</a>"
			));
	}
	@Test  public void Literal_lang() {
		Reg_xwiki_alias("fr", "fr.wikipedia.org");
		fxt.Test_parse_page_wiki_str
			(	"[[:fr:A]]", String_.Concat_lines_nl_skip_last
			(	"<a href=\"/site/fr.wikipedia.org/wiki/A\">A</a>"
			));
		Tfds.Eq(0, fxt.Page().Xwiki_langs().Count());
	}
	@Test  public void Html_ent_pound() {
		fxt.Test_parse_page_wiki_str
			(	"[[A&#35;b|c]]", String_.Concat_lines_nl_skip_last
			(	"<a href=\"/wiki/A#b\">c</a>"
			));
	}
	@Test  public void Html_ent_ltr_a() {
		fxt.Test_parse_page_wiki_str
			(	"[[A&#98;|c]]", String_.Concat_lines_nl_skip_last
			(	"<a href=\"/wiki/Ab\">c</a>"
			));
	}
	@Test  public void Pipe_trick() {
		fxt.Test_parse_page_wiki_str("[[Page1|]]"		, "<a href=\"/wiki/Page1\">Page1</a>");
		fxt.Test_parse_page_wiki_str("[[Help:Page1|]]"	, "<a href=\"/wiki/Help:Page1\">Page1</a>");
	}
	private void Reg_xwiki_alias(String alias, String domain) {
		Xop_fxt.Reg_xwiki_alias(fxt.Wiki(), alias, domain);
	}
	@Test  public void Thumb_first_align_trumps_all() {	// PURPOSE: if there are multiple alignment instructions, take the first EX:[[File:A.png|thumb|center|left]] DATE:20121226
		fxt.Test_parse_page_wiki_str("[[File:A.png|thumb|right|center]]"	// NOTE: right trumps center
			, String_.Concat_lines_nl_skip_last
			(	Xop_para_wkr_basic_tst.File_html("File", "A.png", "7/0", "")
			,	""
			));
	}
	@Test  public void Eq() {
		fxt.Test_parse_page_all_str("[[B|=]]", "<a href=\"/wiki/B\">=</a>");		
	}
	@Test  public void Href_encode_anchor() {	// PURPOSE: test separate encoding for ttl (%) and anchor (.)
		fxt.Test_parse_page_all_str("[[^#^]]", "<a href=\"/wiki/%5E#.5E\">^#^</a>");
	}
	@Test  public void Href_question() {	// PURPOSE.fix: ttl with ? at end should not be considered qarg; DATE:2013-02-08
		fxt.Test_parse_page_all_str("[[A?]]", "<a href=\"/wiki/A%3F\">A?</a>");
	}
	@Test  public void Href_question_2() {	// PURPOSE: ?action=edit should be encoded; DATE:2013-02-10
		fxt.Test_parse_page_all_str("[[A?action=edit]]", "<a href=\"/wiki/A%3Faction%3Dedit\">A?action=edit</a>");
	}
	@Test  public void Href_question_3() {	// PURPOSE.fix:  DATE:2014-01-16
		fxt.Test_parse_page_all_str("[[A?b]]", "<a href=\"/wiki/A%3Fb\">A?b</a>");
	}
	@Test  public void Encoded_url() {	// PURPOSE.fix: url-encoded characters broke parser when embedded in link; DATE:2013-03-01
		fxt.Init_xwiki_add_user_("commons.wikimedia.org");
		fxt.Test_parse_page_wiki_str("[[File:A.png|link=//commons.wikimedia.org/wiki/%D0%97%D0%B0%D0%B3%D0%BB%D0%B0%D0%B2%D0%BD%D0%B0%D1%8F_%D1%81%D1%82%D1%80%D0%B0%D0%BD%D0%B8%D1%86%D0%B0?uselang=ru|b]]"
		, "<a href=\"/site/commons.wikimedia.org/wiki/Заглавная_страница?uselang=ru\" rel=\"nofollow\" xowa_title=\"A.png\"><img id=\"xowa_file_img_0\" alt=\"b\" src=\"file:///mem/wiki/repo/trg/orig/7/0/A.png\" width=\"0\" height=\"0\" /></a>");
	}
	@Test  public void Link_invalid() {	// PURPOSE.fix: do not render invalid text; EX: link={{{1}}}; [[Fil:Randers_-_Hadsund_railway.png|120x160px|link={{{3}}}|Randers-Hadsund Jernbane]]; DATE:2013-03-04
		fxt.Test_parse_page_wiki_str
			(	"[[File:A.png|12x10px|link={{{1}}}|c]]", String_.Concat_lines_nl_skip_last
			(	"<a href=\"/wiki/File:A.png\" class=\"image\" xowa_title=\"A.png\"><img id=\"xowa_file_img_0\" alt=\"c\" src=\"file:///mem/wiki/repo/trg/thumb/7/0/A.png/12px.png\" width=\"12\" height=\"10\" /></a>"
			));
	}
	@Test  public void Href_anchor_leading_space() {	// PURPOSE: ?action=edit should be encoded; DATE:2013-02-10
		fxt.Test_parse_page_all_str("[[A #b]]", "<a href=\"/wiki/A#b\">A #b</a>");
	}
	@Test   public void Anchor_not_shown_for_wikipedia_ns() {	// PURPOSE: Help:A#b was omitting anchor; showing text of "Help:A"; DATE:2013-06-21
		fxt.Test_parse_page_all_str("[[Help:A#b]]", "<a href=\"/wiki/Help:A#b\">Help:A#b</a>");
	}
	@Test   public void Anchor_shown_for_main_ns() {	// PURPOSE: counterpart to Anchor_not_shown_for_wikipedia_ns; DATE:2013-06-21
		fxt.Test_parse_page_all_str("[[A#b]]", "<a href=\"/wiki/A#b\">A#b</a>");
	}
	@Test   public void Trail_en() {
		fxt.Test_parse_page_all_str("[[Ab]]cd e", "<a href=\"/wiki/Ab\">Abcd</a> e");
	}
	@Test   public void Trail_fr() {
		byte[] ltr_c_in_french = Bry_.new_utf8_("ç");
		Xol_lnki_trail_mgr lnki_trail_mgr = fxt.Wiki().Lang().Lnki_trail_mgr();
		lnki_trail_mgr.Add(ltr_c_in_french);
		fxt.Test_parse_page_all_str("[[Ab]]çd e", "<a href=\"/wiki/Ab\">Abçd</a> e");
		lnki_trail_mgr.Del(ltr_c_in_french);
	}
	@Test  public void Link_html_ent() {// PURPOSE:html entities should be converted to chars; EX:&nbsp; -> _; DATE:2013-12-16
		fxt.Test_parse_page_wiki_str
			(	"[[File:A.png|link=b&nbsp;c]]", String_.Concat_lines_nl_skip_last
			(	"<a href=\"/wiki/B_c\" class=\"image\" xowa_title=\"A.png\"><img id=\"xowa_file_img_0\" alt=\"\" src=\"file:///mem/wiki/repo/trg/orig/7/0/A.png\" width=\"0\" height=\"0\" /></a>"
			));
	}
	@Test  public void Page() {
		fxt.Test_parse_page_wiki("[[File:A.pdf|page=12]]"		, fxt.tkn_lnki_().Page_(12));
	}
	@Test  public void Visited() { // PURPOSE: show redirected titles as visited; EX:fr.w:Alpes_Pennines; DATE:2014-02-28
		Xow_wiki wiki = fxt.Wiki();
		Xoa_ttl ttl = Xoa_ttl.parse_(wiki, Bry_.new_ascii_("Src"));		// simulate requrest for "Src" page
		Xoa_page previous_page = Xoa_page.test_(wiki, ttl);
		previous_page.Redirect_list().Add(Bry_.new_ascii_("Src"));		// simulate redirect from "Src"
		fxt.App().User().History_mgr().Add(previous_page);					// simulate "Src" already being clicked once; this is the key call
		fxt.Wtr_cfg().Lnki_visited_(true);
		fxt.Test_parse_page_all_str("[[Src]]"		, "<a href=\"/wiki/Src\" class=\"xowa-visited\">Src</a>");	// show [[Src]] as visited since it exists in history
		fxt.Test_parse_page_all_str("[[Other]]"		, "<a href=\"/wiki/Other\">Other</a>");						// show other pages as not visited
	}
}

