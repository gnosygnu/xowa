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
public class Xop_xnde_tag_ {
	public static final int EndNdeMode_normal = 0, EndNdeMode_inline = 1, EndNdeMode_escape = 2; // escape is for hr which does not support </hr>
	public static final int BgnNdeMode_normal = 0, BgnNdeMode_inline = 1;
	public static final byte[] Name_onlyinclude = Bry_.new_ascii_("onlyinclude");
	public static final byte[] XtnEndTag_bgn = Bry_.new_ascii_("</");//, XtnEndTag_end = Bry_.new_ascii_(">");
	public static final byte
  Tid_b = 0
, Tid_strong = 1
, Tid_i = 2
, Tid_em = 3
, Tid_cite = 4
, Tid_dfn = 5
, Tid_var = 6
, Tid_u = 7
, Tid_ins = 8
, Tid_abbr = 9
, Tid_strike = 10
, Tid_del = 11
, Tid_s = 12
, Tid_sub = 13
, Tid_sup = 14
, Tid_big = 15
, Tid_small = 16
, Tid_code = 17
, Tid_tt = 18
, Tid_kbd = 19
, Tid_samp = 20
, Tid_blockquote = 21
, Tid_pre = 22
, Tid_font = 23
, Tid_center = 24
, Tid_p = 25
, Tid_span = 26
, Tid_div = 27
, Tid_hr = 28
, Tid_br = 29
, Tid_h1 = 30
, Tid_h2 = 31
, Tid_h3 = 32
, Tid_h4 = 33
, Tid_h5 = 34
, Tid_h6 = 35
, Tid_li = 36
, Tid_dt = 37
, Tid_dd = 38
, Tid_ol = 39
, Tid_ul = 40
, Tid_dl = 41
, Tid_table = 42
, Tid_tr = 43
, Tid_td = 44
, Tid_th = 45
, Tid_thead = 46
, Tid_tfoot = 47
, Tid_tbody = 48
, Tid_caption = 49
, Tid_colgroup = 50
, Tid_col = 51
, Tid_a = 52
, Tid_img = 53
, Tid_ruby = 54
, Tid_rt = 55
, Tid_rb = 56
, Tid_rp = 57
, Tid_includeonly = 58
, Tid_noinclude = 59
, Tid_onlyinclude = 60
, Tid_nowiki = 61
, Tid_xowa_cmd = 62
, Tid_poem = 63
, Tid_math = 64
, Tid_ref = 65
, Tid_references = 66
, Tid_source = 67
, Tid_syntaxHighlight = 68
, Tid_gallery = 69
, Tid_imageMap = 70
, Tid_timeline = 71
, Tid_hiero = 72
, Tid_inputBox = 73
, Tid_pages = 74
, Tid_section = 75
, Tid_pagequality = 76
, Tid_pagelist = 77
, Tid_categoryList = 78
, Tid_categoryTree = 79
, Tid_dynamicPageList = 80
, Tid_time = 81
, Tid_input = 82
, Tid_textarea = 83
, Tid_score = 84
, Tid_button = 85
, Tid_select = 86
, Tid_option = 87
, Tid_optgroup = 88
, Tid_script = 89
, Tid_style = 90
, Tid_form = 91
, Tid_translate = 92
, Tid_languages = 93
, Tid_templateData = 94
, Tid_bdi = 95
, Tid_data = 96
, Tid_mark = 97
, Tid_wbr = 98
, Tid_bdo = 99
, Tid_listing_buy = 100
, Tid_listing_do = 101
, Tid_listing_drink = 102
, Tid_listing_eat = 103
, Tid_listing_listing = 104
, Tid_listing_see = 105
, Tid_listing_sleep = 106
, Tid_rss = 107
, Tid_xowa_html = 108
, Tid_xowa_tag_bgn = 109
, Tid_xowa_tag_end = 110
, Tid_quiz = 111
, Tid_indicator = 112
	;
	public static final int _MaxLen = 113;
	public static final Xop_xnde_tag[] Ary = new Xop_xnde_tag[_MaxLen];
	private static Xop_xnde_tag new_(int id, String name) {
		Xop_xnde_tag rv = new Xop_xnde_tag(id, name);
		Ary[id] = rv;
		return rv;
	}
	public static final Xop_xnde_tag
  Tag_b = new_(Tid_b, "b").NoInline_()
, Tag_strong = new_(Tid_strong, "strong").NoInline_()
, Tag_i = new_(Tid_i, "i").NoInline_()
, Tag_em = new_(Tid_em, "em").NoInline_()
, Tag_cite = new_(Tid_cite, "cite").NoInline_()
, Tag_dfn = new_(Tid_dfn, "dfn").NoInline_()
, Tag_var = new_(Tid_var, "var").NoInline_()
, Tag_u = new_(Tid_u, "u").NoInline_().Repeat_ends_()	// PAGE:en.b:Textbook_of_Psychiatry/Alcoholism_and_Psychoactive_Substance_Use_Disorders; DATE:2014-09-05
, Tag_ins = new_(Tid_ins, "ins").NoInline_()
, Tag_abbr = new_(Tid_abbr, "abbr").NoInline_()
, Tag_strike = new_(Tid_strike, "strike").NoInline_()
, Tag_del = new_(Tid_del, "del").NoInline_()
, Tag_s = new_(Tid_s, "s").NoInline_()
, Tag_sub = new_(Tid_sub, "sub").NoInline_()
, Tag_sup = new_(Tid_sup, "sup").NoInline_()
, Tag_big = new_(Tid_big, "big").NoInline_()
, Tag_small = new_(Tid_small, "small").NoInline_()
, Tag_code = new_(Tid_code, "code").NoInline_().Repeat_ends_()
, Tag_tt = new_(Tid_tt, "tt").NoInline_().Repeat_ends_()
, Tag_kbd = new_(Tid_kbd, "kbd").NoInline_()
, Tag_samp = new_(Tid_samp, "samp").NoInline_()
, Tag_blockquote = new_(Tid_blockquote, "blockquote").NoInline_().Repeat_mids_().Section_().Block_open_bgn_().Block_close_end_()	// NOTE: should be open_end_, but leaving for now; DATE:2014-03-11; added Repeat_mids_(); PAGE:en.w:Ring_a_Ring_o'_Roses DATE:2014-06-26
, Tag_pre = new_(Tid_pre, "pre").NoInline_().Section_().Xtn_().Raw_().Block_open_bgn_().Block_close_end_().Ignore_empty_().Xtn_skips_template_args_()
, Tag_font = new_(Tid_font, "font").NoInline_()
, Tag_center = new_(Tid_center, "center").NoInline_().Block_open_end_().Block_close_end_() // removed .Repeat_ends_(); added Nest_(); EX: w:Burr Truss; DATE:2012-12-12
, Tag_p = new_(Tid_p, "p").NoInline_().Section_().Block_open_bgn_().Block_close_end_()
, Tag_span = new_(Tid_span, "span").Section_()
, Tag_div = new_(Tid_div, "div").Section_().Block_open_end_().Block_close_end_()
, Tag_hr = new_(Tid_hr, "hr").SingleOnly_().BgnNdeMode_inline_().Inline_by_backslash_().EndNdeMode_escape_().Section_().Block_close_end_()
, Tag_br = new_(Tid_br, "br").SingleOnly_().BgnNdeMode_inline_().Inline_by_backslash_().EndNdeMode_inline_().Section_()
, Tag_h1 = new_(Tid_h1, "h1").NoInline_().Section_().Block_open_bgn_().Block_close_end_()
, Tag_h2 = new_(Tid_h2, "h2").NoInline_().Section_().Block_open_bgn_().Block_close_end_()
, Tag_h3 = new_(Tid_h3, "h3").NoInline_().Section_().Block_open_bgn_().Block_close_end_()
, Tag_h4 = new_(Tid_h4, "h4").NoInline_().Section_().Block_open_bgn_().Block_close_end_()
, Tag_h5 = new_(Tid_h5, "h5").NoInline_().Section_().Block_open_bgn_().Block_close_end_()
, Tag_h6 = new_(Tid_h6, "h6").NoInline_().Section_().Block_open_bgn_().Block_close_end_()
, Tag_li = new_(Tid_li, "li").Repeat_mids_().Empty_ignored_().Block_open_bgn_().Block_close_end_()
, Tag_dt = new_(Tid_dt, "dt").Repeat_mids_()
, Tag_dd = new_(Tid_dd, "dd").Repeat_mids_()
, Tag_ol = new_(Tid_ol, "ol").NoInline_().Block_open_bgn_().Block_close_end_()
, Tag_ul = new_(Tid_ul, "ul").NoInline_().Block_open_bgn_().Block_close_end_()
, Tag_dl = new_(Tid_dl, "dl").NoInline_()
, Tag_table = new_(Tid_table, "table").NoInline_().Block_open_bgn_().Block_close_end_()
, Tag_tr = new_(Tid_tr, "tr").TblSub_().Block_open_bgn_().Block_open_end_()
, Tag_td = new_(Tid_td, "td").TblSub_().Block_open_end_().Block_close_bgn_()
, Tag_th = new_(Tid_th, "th").TblSub_().Block_open_end_().Block_close_bgn_()
, Tag_thead = new_(Tid_thead, "thead")
, Tag_tfoot = new_(Tid_tfoot, "tfoot")
, Tag_tbody = new_(Tid_tbody, "tbody")
, Tag_caption = new_(Tid_caption, "caption").NoInline_().TblSub_()
, Tag_colgroup = new_(Tid_colgroup, "colgroup")
, Tag_col = new_(Tid_col, "col")
, Tag_a = new_(Tid_a, "a").Restricted_()
, Tag_img = new_(Tid_img, "img").Restricted_()	// NOTE: was .Xtn() DATE:2014-11-06
, Tag_ruby = new_(Tid_ruby, "ruby").NoInline_()
, Tag_rt = new_(Tid_rt, "rt").NoInline_()
, Tag_rb = new_(Tid_rb, "rb").NoInline_()
, Tag_rp = new_(Tid_rp, "rp").NoInline_()
, Tag_includeonly = new_(Tid_includeonly, "includeonly")
, Tag_noinclude = new_(Tid_noinclude, "noinclude")
, Tag_onlyinclude = new_(Tid_onlyinclude, "onlyinclude")
, Tag_nowiki = new_(Tid_nowiki, "nowiki")
, Tag_xowa_cmd = new_(Tid_xowa_cmd, "xowa_cmd").Xtn_()
, Tag_poem = new_(Tid_poem, "poem").Xtn_().Xtn_auto_close_()
, Tag_math = new_(Tid_math, "math").Xtn_()
, Tag_ref = new_(Tid_ref, "ref").Xtn_()
, Tag_references = new_(Tid_references, "references").Xtn_()
, Tag_source = new_(Tid_source, "source").Xtn_().Block_open_bgn_().Block_close_end_()	// deactivate pre; pre; PAGE:en.w:Comment_(computer_programming); DATE:2014-06-23
, Tag_syntaxHighlight = new_(Tid_syntaxHighlight, "syntaxHighlight").Xtn_().Block_open_bgn_().Block_close_end_()	// deactivate pre; pre; PAGE:en.w:Comment_(computer_programming); DATE:2014-06-23
, Tag_gallery = new_(Tid_gallery, "gallery").Xtn_().Block_open_bgn_().Block_close_end_().Xtn_auto_close_()
, Tag_imageMap = new_(Tid_imageMap, "imageMap").Xtn_()
, Tag_timeline = new_(Tid_timeline, "timeline").Xtn_()
, Tag_hiero = new_(Tid_hiero, "hiero").Xtn_()
, Tag_inputBox = new_(Tid_inputBox, "inputBox").Xtn_()
, Tag_pages = new_(Tid_pages, "pages").Xtn_()
, Tag_section = new_(Tid_section, "section").Xtn_().Langs_(Xol_lang_itm_.Id_de, "Abschnitt").Langs_(Xol_lang_itm_.Id_he, "קטע").Langs_(Xol_lang_itm_.Id_pt, "trecho") // DATE:2014-07-18
, Tag_pagequality = new_(Tid_pagequality, "pagequality").Xtn_()
, Tag_pagelist = new_(Tid_pagelist, "pagelist").Xtn_()
, Tag_categoryList = new_(Tid_categoryList, "categoryList").Xtn_()
, Tag_categoryTree = new_(Tid_categoryTree, "categoryTree").Xtn_()
, Tag_dynamicPageList = new_(Tid_dynamicPageList, "dynamicPageList").Xtn_()
, Tag_time = new_(Tid_time, "time")
, Tag_input = new_(Tid_input, "input").Restricted_()
, Tag_textarea = new_(Tid_textarea, "textarea").Restricted_()
, Tag_score = new_(Tid_score, "score").Xtn_()
, Tag_button = new_(Tid_button, "button").Restricted_()
, Tag_select = new_(Tid_select, "select").Restricted_()
, Tag_option = new_(Tid_option, "option").Restricted_()
, Tag_optgroup = new_(Tid_optgroup, "optgroup").Restricted_()
, Tag_script = new_(Tid_script, "script").Restricted_()	// NOTE: had ".Block_open_bgn_().Block_close_end_()"; PAGE:en.w:Cascading_Style_Sheets DATE:2014-06-23
, Tag_style = new_(Tid_style, "style").Restricted_()	// NOTE: had ".Block_open_bgn_().Block_close_end_()"; PAGE:en.w:Cascading_Style_Sheets DATE:2014-06-23
, Tag_form = new_(Tid_form, "form").Restricted_()		// NOTE: had ".Block_open_bgn_().Block_close_end_()"; PAGE:en.w:Cascading_Style_Sheets DATE:2014-06-23
, Tag_translate = new_(Tid_translate, "translate").Xtn_()
, Tag_languages = new_(Tid_languages, "languages").Xtn_()
, Tag_templateData = new_(Tid_templateData, "templateData").Xtn_()
, Tag_bdi = new_(Tid_bdi, "bdi")
, Tag_data = new_(Tid_data, "data")
, Tag_mark = new_(Tid_mark, "mark")
, Tag_wbr = new_(Tid_wbr, "wbr").SingleOnly_()
, Tag_bdo = new_(Tid_bdo, "bdo").NoInline_().Section_().Block_open_bgn_().Block_close_end_()
, Tag_listing_buy = new_(Tid_listing_buy, "buy").Xtn_()
, Tag_listing_do = new_(Tid_listing_do, "do").Xtn_()
, Tag_listing_drink = new_(Tid_listing_drink, "drink").Xtn_()
, Tag_listing_eat = new_(Tid_listing_eat, "eat").Xtn_()
, Tag_listing_listing = new_(Tid_listing_listing, "listing").Xtn_()
, Tag_listing_see = new_(Tid_listing_see, "see").Xtn_()
, Tag_listing_sleep = new_(Tid_listing_sleep, "sleep").Xtn_()
, Tag_rss = new_(Tid_rss, "rss").Xtn_()
, Tag_xowa_html = new_(Tid_xowa_html, "xowa_html").Xtn_()
, Tag_xowa_tag_bgn = new_(Tid_xowa_tag_bgn, "xtag_bgn").Xtn_()
, Tag_xowa_tag_end = new_(Tid_xowa_tag_end, "xtag_end").Xtn_()
, Tag_quiz = new_(Tid_quiz, "quiz").Xtn_()
, Tag_indicator = new_(Tid_indicator, "indicator").Xtn_()
	;
}
