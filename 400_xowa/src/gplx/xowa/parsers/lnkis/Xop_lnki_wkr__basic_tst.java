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
import gplx.xowa.langs.*; import gplx.xowa.langs.cases.*; import gplx.xowa.langs.funcs.*; import gplx.xowa.langs.lnki_trails.*;
import gplx.xowa.wikis.nss.*;
import gplx.xowa.parsers.paras.*; import gplx.xowa.wikis.ttls.*;
public class Xop_lnki_wkr__basic_tst {
	@Before public void init() {fxt.Reset(); fxt.Init_para_n_();} private final    Xop_fxt fxt = new Xop_fxt();
	@Test  public void Basic() {
		fxt.Test_parse_page_wiki("[[a]]", fxt.tkn_lnki_().Trg_tkn_(fxt.tkn_arg_val_txt_(2, 3)));
	}
	@Test  public void HtmlRef() {
		fxt.Test_parse_page_wiki_str("[[a&amp;b]]"
		, "<a href=\"/wiki/A%26b\">a&amp;b</a>"
		);
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
		fxt.Test_parse_page_wiki("[[Image:a]]"				, fxt.tkn_lnki_().Ns_id_(Xow_ns_.Tid__file).Trg_tkn_(fxt.tkn_arg_val_(fxt.tkn_txt_(2, 7), fxt.tkn_colon_(7), fxt.tkn_txt_(8, 9))));
		fxt.Test_parse_page_wiki("[[Image:a|border]]"		, fxt.tkn_lnki_().Border_(Bool_.Y_byte));
		fxt.Test_parse_page_wiki("[[Image:a|thumb]]"		, fxt.tkn_lnki_().ImgType_(Xop_lnki_type.Id_thumb));
		fxt.Test_parse_page_wiki("[[Image:a|left]]"			, fxt.tkn_lnki_().HAlign_(Xop_lnki_align_h_.Left));
		fxt.Test_parse_page_wiki("[[Image:a|top]]"			, fxt.tkn_lnki_().VAlign_(Xop_lnki_align_v_.Top));
		fxt.Test_parse_page_wiki("[[Image:a|10px]]"			, fxt.tkn_lnki_().Width_(10).Height_(-1));
		fxt.Test_parse_page_wiki("[[Image:a|20x30px]]"		, fxt.tkn_lnki_().Width_(20).Height_(30));
		fxt.Test_parse_page_wiki("[[Image:a|alt=b]]"			, fxt.tkn_lnki_().Alt_tkn_(fxt.tkn_arg_nde_().Key_tkn_(fxt.tkn_arg_itm_(fxt.tkn_txt_(10, 13))).Val_tkn_(fxt.tkn_arg_itm_(fxt.tkn_txt_(14, 15)))));
		fxt.Test_parse_page_wiki("[[Image:a|link=a]]"		, fxt.tkn_lnki_().Link_tkn_(fxt.tkn_arg_nde_().Key_tkn_(fxt.tkn_arg_itm_(fxt.tkn_txt_(10, 14))).Val_tkn_(fxt.tkn_arg_itm_(fxt.tkn_txt_(15, 16)))));
		fxt.Test_parse_page_wiki("[[Image:a|thumb|alt=b|c d]]"
			, fxt.tkn_lnki_().Ns_id_(Xow_ns_.Tid__file)
			.Trg_tkn_(fxt.tkn_arg_nde_().Val_tkn_(fxt.tkn_arg_itm_(fxt.tkn_txt_(2, 7), fxt.tkn_colon_(7), fxt.tkn_txt_(8, 9))))
			.Alt_tkn_(fxt.tkn_arg_nde_().Key_tkn_(fxt.tkn_arg_itm_(fxt.tkn_txt_(16, 19))).Val_tkn_(fxt.tkn_arg_itm_(fxt.tkn_txt_(20, 21))))
			.Caption_tkn_(fxt.tkn_arg_nde_(22, 25).Val_tkn_(fxt.tkn_arg_itm_(fxt.tkn_txt_(22, 23), fxt.tkn_space_(23, 24), fxt.tkn_txt_(24, 25)))))
			;
	}
	@Test  public void Image_upright() {
		fxt.Test_parse_page_wiki("[[Image:a|upright=.123]]"	, fxt.tkn_lnki_().Upright_(.123));
		fxt.Test_parse_page_wiki("[[Image:a|upright]]"		, fxt.tkn_lnki_().Upright_(gplx.xowa.files.Xof_img_size.Upright_default_marker));		// no eq tokn
		fxt.Test_parse_page_wiki("[[Image:a|upright=.42190046219457]]", fxt.tkn_lnki_().Upright_(.42190046219457));	// many decimal places breaks upright
		fxt.Init_log_(Xop_lnki_log.Upright_val_is_invalid)
		  .Test_parse_page_wiki("[[Image:a|upright=y]]"		, fxt.tkn_lnki_().Upright_(-1));		// invalid
	}
	@Test  public void Recurse() {
		fxt.Test_parse_page_wiki("[[Image:a|b-[[c]]-d]]"
			, fxt.tkn_lnki_().Ns_id_(Xow_ns_.Tid__file)
			.Trg_tkn_(fxt.tkn_arg_nde_().Val_tkn_(fxt.tkn_arg_itm_(fxt.tkn_txt_(2, 7), fxt.tkn_colon_(7), fxt.tkn_txt_(8, 9))))
			.Caption_tkn_(fxt.tkn_arg_nde_(10, 19).Val_tkn_(fxt.tkn_arg_itm_(fxt.tkn_txt_(10, 12), fxt.tkn_lnki_(12, 17), fxt.tkn_txt_(17, 19))))
		);
	}
	@Test  public void Outliers() {
		fxt.Test_parse_page_wiki("[[Image:a=b.svg|thumb|10px]]", fxt.tkn_lnki_().Ns_id_(Xow_ns_.Tid__file).Trg_tkn_(fxt.tkn_arg_nde_().Val_tkn_(fxt.tkn_arg_itm_(fxt.tkn_txt_(2, 7), fxt.tkn_colon_(7), fxt.tkn_txt_(8, 9), fxt.tkn_eq_(9), fxt.tkn_txt_(10, 15)))));
		fxt.Test_parse_page_wiki("[[Category:a|b]]", fxt.tkn_lnki_().Ns_id_(Xow_ns_.Tid__category));
	}
	@Test  public void Exc_caption_has_eq() {
		fxt.Test_parse_page_wiki("[[Image:a.svg|thumb|a=b]]", fxt.tkn_lnki_().Ns_id_(Xow_ns_.Tid__file)
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
		fxt.Init_log_(Xop_lnki_log.Invalid_ttl);
		fxt.Test_parse_page_wiki("[[]]", fxt.tkn_txt_(0, 2), fxt.tkn_txt_(2, 4));
		fxt.Init_log_(Xop_lnki_log.Invalid_ttl);
		fxt.Test_parse_page_wiki("[[ ]]", fxt.tkn_txt_(0, 2), fxt.tkn_space_(2, 3), fxt.tkn_txt_(3, 5));
	}
	@Test  public void Exc_invalid_u8() {	// PURPOSE: "%DO" is an invalid UTF-8 sequence (requires 2 bytes, not just %D0); DATE:2013-11-11
		fxt.Ctx().Lang().Case_mgr_u8_();						// NOTE: only occurs during Universal
		fxt.Test_parse_page_all_str("[[%D0]]", "[[%D0]]");		// invalid titles render literally
	}
	@Test  public void Ex_eq() {	// make sure that eq is not evaluated for kv delimiter
		fxt.Test_parse_page_wiki("[[=]]", fxt.tkn_lnki_(0, 5));
		fxt.Test_parse_page_wiki("[[a|=]]", fxt.tkn_lnki_(0, 7));
	}
	@Test  public void Unclosed() {	// PURPOSE: unclosed lnki skips rendering of next table; PAGE:en.w:William Penn (Royal Navy officer)
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last
		( "a [[b|c]"
		, ""
		, "{|"
		, "|-"
		, "|d"
		, "|}"
		),String_.Concat_lines_nl_skip_last
		( "a [[b|c] "	// NOTE: \n is converted to \s b/c caption does not allow \n
		, "<table>"
		, "  <tr>"
		, "    <td>d"
		, "    </td>"
		, "  </tr>"
		, "</table>"
		, ""
		));
	}
	@Test  public void Caption_nl() {	// PURPOSE: \n in caption should be rendered as space; PAGE:en.w:Schwarzschild radius; and the stellar [[Velocity dispersion|velocity\ndispersion]]
		fxt.Init_para_y_();
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last
		( "a [[b|c"
		, ""
		, ""
		, "d]]"
		),	String_.Concat_lines_nl_skip_last
		( "<p>a <a href=\"/wiki/B\">c   d</a>"	// NOTE: this depends on html viewer to collapse multiple spaces into 1
		, "</p>"
		, ""
		));
		fxt.Init_para_n_();
	}
	@Test  public void Caption_nl_2() {	// PURPOSE: unclosed lnki breaks paragraph unexpectedly; PAGE:en.w:Oldsmobile;
		fxt.Init_para_y_();
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last
		( "a"
		, ""
		, "b [[c"
		, ""	// NOTE: this new line is needed to produce strange behavior
		), String_.Concat_lines_nl_skip_last
		( "<p>a"
		, "</p>"
		, ""
		, "<p>b [[c"	// NOTE: \n is converted to \s b/c caption does not allow \n; NOTE: removed \s after "c" due to new lnki invalidation;DATE:2014-04-03
		, "</p>"
		, ""
		));
		fxt.Init_para_n_();
	}
	@Test  public void Caption_ref() {	// PURPOSE: <ref> not handled in lnki; PAGE:en.w:WWI
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last
		( "[[File:A.png|thumb|b <ref>c</ref>]]"
		), String_.Concat_lines_nl_skip_last
		( "<div class=\"thumb tright\">"
		, "  <div id=\"xowa_file_div_0\" class=\"thumbinner\" style=\"width:220px;\">"
		, "    <a href=\"/wiki/File:A.png\" class=\"image\" xowa_title=\"A.png\"><img id=\"xoimg_0\" alt=\"\" src=\"file:///mem/wiki/repo/trg/thumb/7/0/A.png/220px.png\" width=\"0\" height=\"0\" /></a>"
		, "    <div class=\"thumbcaption\">"
		,       "<div class=\"magnify\"><a href=\"/wiki/File:A.png\" class=\"inte" +"rnal\" title=\"Enlarge\"></a></div>b <sup id=\"cite_ref-0\" class=\"reference\"><a href=\"#cite_note-0\">[1]</a></sup>"
		, "    </div>"
		, "  </div>"
		, "</div>"
		, ""
		));
	}
	@Test  public void Html_ent_pound() {
		fxt.Test_parse_page_wiki_str
		( "[[A&#35;b|c]]", String_.Concat_lines_nl_skip_last
		( "<a href=\"/wiki/A#b\">c</a>"
		));
	}
	@Test  public void Html_ent_ltr_a() {
		fxt.Test_parse_page_wiki_str
		( "[[A&#98;|c]]", String_.Concat_lines_nl_skip_last
		( "<a href=\"/wiki/Ab\">c</a>"
		));
	}
	@Test  public void Pipe_trick() {
		fxt.Test_parse_page_wiki_str("[[Page1|]]"		, "<a href=\"/wiki/Page1\">Page1</a>");
		fxt.Test_parse_page_wiki_str("[[Help:Page1|]]"	, "<a href=\"/wiki/Help:Page1\">Page1</a>");
	}
	@Test  public void Thumb_first_align_trumps_all() {	// PURPOSE: if there are multiple alignment instructions, take the first EX:[[File:A.png|thumb|center|left]] DATE:20121226
		fxt.Test_parse_page_wiki_str("[[File:A.png|thumb|right|center]]"	// NOTE: right trumps center
			, String_.Concat_lines_nl_skip_last
		( Xop_para_wkr_basic_tst.File_html("File", "A.png", "7/0", "")
		, ""
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
		, "<a href=\"/site/commons.wikimedia.org/wiki/%D0%97%D0%B0%D0%B3%D0%BB%D0%B0%D0%B2%D0%BD%D0%B0%D1%8F_%D1%81%D1%82%D1%80%D0%B0%D0%BD%D0%B8%D1%86%D0%B0?uselang=ru\" xowa_title=\"A.png\"><img id=\"xoimg_0\" alt=\"b\" src=\"file:///mem/wiki/repo/trg/orig/7/0/A.png\" width=\"0\" height=\"0\" /></a>");
	}
	@Test  public void Link__invalid() {	// PURPOSE.fix: do not render invalid text; EX: link={{{1}}}; [[Fil:Randers_-_Hadsund_railway.png|120x160px|link={{{3}}}|Randers-Hadsund Jernbane]]; DATE:2013-03-04
		fxt.Test_parse_page_wiki_str
		( "[[File:A.png|12x10px|link={{{1}}}|c]]", String_.Concat_lines_nl_skip_last
		( "<a href=\"/wiki/File:A.png\" class=\"image\" xowa_title=\"A.png\"><img id=\"xoimg_0\" alt=\"c\" src=\"file:///mem/wiki/repo/trg/thumb/7/0/A.png/12px.png\" width=\"12\" height=\"10\" /></a>"
		));
	}
	@Test  public void Link__html_ent() {// PURPOSE:html entities should be converted to chars; EX:&nbsp; -> _; DATE:2013-12-16
		fxt.Test_parse_page_wiki_str
		( "[[File:A.png|link=b&nbsp;c]]", String_.Concat_lines_nl_skip_last
		( "<a href=\"/wiki/B_c\" class=\"image\" xowa_title=\"A.png\"><img id=\"xoimg_0\" alt=\"\" src=\"file:///mem/wiki/repo/trg/orig/7/0/A.png\" width=\"0\" height=\"0\" /></a>"
		));
	}
	@Test  public void Link__encode() {// PURPOSE:spaces should become underscore; DATE:2015-11-27
		fxt.Test_parse_page_wiki_str
		( "[[File:A.png|link=File:A b ç.ogg]]", String_.Concat_lines_nl_skip_last
		( "<a href=\"/wiki/File:A_b_%C3%A7.ogg\" class=\"image\" xowa_title=\"A.png\"><img id=\"xoimg_0\" alt=\"\" src=\"file:///mem/wiki/repo/trg/orig/7/0/A.png\" width=\"0\" height=\"0\" /></a>"
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
		byte[] ltr_c_in_french = Bry_.new_u8("ç");
		Xol_lnki_trail_mgr lnki_trail_mgr = fxt.Wiki().Lang().Lnki_trail_mgr();
		lnki_trail_mgr.Add(ltr_c_in_french);
		fxt.Test_parse_page_all_str("[[Ab]]çd e", "<a href=\"/wiki/Ab\">Abçd</a> e");
		lnki_trail_mgr.Del(ltr_c_in_french);
	}
	@Test  public void Page() {
		fxt.Test_parse_page_wiki("[[File:A.pdf|page=12]]"		, fxt.tkn_lnki_().Page_(12));
	}
	@Test  public void Visited() { // PURPOSE: show redirected titles as visited; EX:fr.w:Alpes_Pennines; DATE:2014-02-28
		Xowe_wiki wiki = fxt.Wiki();
		Xoa_ttl ttl = Xoa_ttl.Parse(wiki, Bry_.new_a7("Src"));								// simulate requrest for "Src" page
		Xoae_page previous_page = Xoae_page.New_test(wiki, ttl);
		previous_page.Redirect_trail().Itms__add__article(previous_page.Url(), ttl, null);	// simulate redirect from "Src"
		fxt.App().Usere().History_mgr().Add(previous_page);									// simulate "Src" already being clicked once; this is the key call
		fxt.Wtr_cfg().Lnki__visited_y_();
		fxt.Test_parse_page_all_str("[[Src]]"		, "<a href=\"/wiki/Src\" class=\"xowa-visited\">Src</a>");	// show [[Src]] as visited since it exists in history
		fxt.Test_parse_page_all_str("[[Other]]"		, "<a href=\"/wiki/Other\">Other</a>");						// show other pages as not visited
	}
}
