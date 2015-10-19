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
package gplx.xowa.parsers.xndes; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import gplx.xowa.langs.*;
public class Xop_xnde_tag_ {
	public static final int End_nde_mode_normal = 0, End_nde_mode_inline = 1, End_nde_mode_escape = 2; // escape is for hr which does not support </hr>
	public static final int Bgn_nde_mode_normal = 0, Bgn_nde_mode_inline = 1;
	public static final byte[] Name_onlyinclude = Bry_.new_a7("onlyinclude");
	public static final byte[] Xtn_end_tag_bgn = Bry_.new_a7("</");//, Xtn_end_tag_end = Bry_.new_a7(">");
	public static final int
  Tid__null = -1
, Tid_b = 0
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
, Tid_q = 113
, Tid_graph = 114
	;
	public static final int Tid__len = 115;
	public static final Xop_xnde_tag[] Ary = new Xop_xnde_tag[Tid__len];
	private static Xop_xnde_tag new_(int id, String name) {
		Xop_xnde_tag rv = new Xop_xnde_tag(id, name);
		Ary[id] = rv;
		return rv;
	}
	public static final Xop_xnde_tag
  Tag_b = new_(Tid_b, "b").No_inline_()
, Tag_strong = new_(Tid_strong, "strong").No_inline_()
, Tag_i = new_(Tid_i, "i").No_inline_()
, Tag_em = new_(Tid_em, "em").No_inline_()
, Tag_cite = new_(Tid_cite, "cite").No_inline_()
, Tag_dfn = new_(Tid_dfn, "dfn").No_inline_()
, Tag_var = new_(Tid_var, "var").No_inline_()
, Tag_u = new_(Tid_u, "u").No_inline_().Repeat_ends_()	// PAGE:en.b:Textbook_of_Psychiatry/Alcoholism_and_Psychoactive_Substance_Use_Disorders; DATE:2014-09-05
, Tag_ins = new_(Tid_ins, "ins").No_inline_()
, Tag_abbr = new_(Tid_abbr, "abbr").No_inline_()
, Tag_strike = new_(Tid_strike, "strike").No_inline_()
, Tag_del = new_(Tid_del, "del").No_inline_()
, Tag_s = new_(Tid_s, "s").No_inline_()
, Tag_sub = new_(Tid_sub, "sub").No_inline_()
, Tag_sup = new_(Tid_sup, "sup").No_inline_()
, Tag_big = new_(Tid_big, "big").No_inline_()
, Tag_small = new_(Tid_small, "small").No_inline_()
, Tag_code = new_(Tid_code, "code").No_inline_().Repeat_ends_()
, Tag_tt = new_(Tid_tt, "tt").No_inline_().Repeat_ends_()
, Tag_kbd = new_(Tid_kbd, "kbd").No_inline_()
, Tag_samp = new_(Tid_samp, "samp").No_inline_()
, Tag_blockquote = new_(Tid_blockquote, "blockquote").No_inline_().Repeat_mids_().Section_().Block_open_bgn_().Block_close_end_()	// NOTE: should be open_end_, but leaving for now; DATE:2014-03-11; added Repeat_mids_(); PAGE:en.w:Ring_a_Ring_o'_Roses DATE:2014-06-26
, Tag_pre = new_(Tid_pre, "pre").No_inline_().Section_().Xtn_mw_().Raw_().Block_open_bgn_().Block_close_end_().Ignore_empty_().Xtn_skips_template_args_()
, Tag_font = new_(Tid_font, "font").No_inline_()
, Tag_center = new_(Tid_center, "center").No_inline_().Block_open_end_().Block_close_end_() // removed .Repeat_ends_(); added Nest_(); EX: w:Burr Truss; DATE:2012-12-12
, Tag_p = new_(Tid_p, "p").No_inline_().Section_().Block_open_bgn_().Block_close_end_()
, Tag_span = new_(Tid_span, "span").Section_()
, Tag_div = new_(Tid_div, "div").Section_().Block_open_end_().Block_close_end_()
, Tag_hr = new_(Tid_hr, "hr").Single_only_().Single_only_html_().Bgn_nde_mode_inline_().Inline_by_backslash_().End_nde_mode_escape_().Section_().Block_close_end_()
, Tag_br = new_(Tid_br, "br").Single_only_().Single_only_html_().Bgn_nde_mode_inline_().Inline_by_backslash_().End_nde_mode_inline_().Section_()
, Tag_h1 = new_(Tid_h1, "h1").No_inline_().Section_().Block_open_bgn_().Block_close_end_()
, Tag_h2 = new_(Tid_h2, "h2").No_inline_().Section_().Block_open_bgn_().Block_close_end_()
, Tag_h3 = new_(Tid_h3, "h3").No_inline_().Section_().Block_open_bgn_().Block_close_end_()
, Tag_h4 = new_(Tid_h4, "h4").No_inline_().Section_().Block_open_bgn_().Block_close_end_()
, Tag_h5 = new_(Tid_h5, "h5").No_inline_().Section_().Block_open_bgn_().Block_close_end_()
, Tag_h6 = new_(Tid_h6, "h6").No_inline_().Section_().Block_open_bgn_().Block_close_end_()
, Tag_li = new_(Tid_li, "li").Repeat_mids_().Empty_ignored_().Block_open_bgn_().Block_close_end_()
, Tag_dt = new_(Tid_dt, "dt").Repeat_mids_()
, Tag_dd = new_(Tid_dd, "dd").Repeat_mids_()
, Tag_ol = new_(Tid_ol, "ol").No_inline_().Block_open_bgn_().Block_close_end_()
, Tag_ul = new_(Tid_ul, "ul").No_inline_().Block_open_bgn_().Block_close_end_()
, Tag_dl = new_(Tid_dl, "dl").No_inline_()
, Tag_table = new_(Tid_table, "table").No_inline_().Block_open_bgn_().Block_close_end_()
, Tag_tr = new_(Tid_tr, "tr").Tbl_sub_().Block_open_bgn_().Block_open_end_()
, Tag_td = new_(Tid_td, "td").Tbl_sub_().Block_open_end_().Block_close_bgn_()
, Tag_th = new_(Tid_th, "th").Tbl_sub_().Block_open_end_().Block_close_bgn_()
, Tag_thead = new_(Tid_thead, "thead")
, Tag_tfoot = new_(Tid_tfoot, "tfoot")
, Tag_tbody = new_(Tid_tbody, "tbody")
, Tag_caption = new_(Tid_caption, "caption").No_inline_().Tbl_sub_()
, Tag_colgroup = new_(Tid_colgroup, "colgroup")
, Tag_col = new_(Tid_col, "col")
, Tag_a = new_(Tid_a, "a").Restricted_()
, Tag_img = new_(Tid_img, "img").Single_only_html_().Restricted_()	// NOTE: was .Xtn() DATE:2014-11-06
, Tag_ruby = new_(Tid_ruby, "ruby").No_inline_()
, Tag_rt = new_(Tid_rt, "rt").No_inline_()
, Tag_rb = new_(Tid_rb, "rb").No_inline_()
, Tag_rp = new_(Tid_rp, "rp").No_inline_()
, Tag_includeonly = new_(Tid_includeonly, "includeonly")
, Tag_noinclude = new_(Tid_noinclude, "noinclude")
, Tag_onlyinclude = new_(Tid_onlyinclude, "onlyinclude")
, Tag_nowiki = new_(Tid_nowiki, "nowiki")
, Tag_xowa_cmd = new_(Tid_xowa_cmd, "xowa_cmd").Xtn_()
, Tag_poem = new_(Tid_poem, "poem").Xtn_mw_().Xtn_auto_close_()
, Tag_math = new_(Tid_math, "math").Xtn_mw_()
, Tag_ref = new_(Tid_ref, "ref").Xtn_mw_()
, Tag_references = new_(Tid_references, "references").Xtn_mw_()
, Tag_source = new_(Tid_source, "source").Xtn_mw_().Block_open_bgn_().Block_close_end_()	// deactivate pre; pre; PAGE:en.w:Comment_(computer_programming); DATE:2014-06-23
, Tag_syntaxHighlight = new_(Tid_syntaxHighlight, "syntaxHighlight").Xtn_mw_().Block_open_bgn_().Block_close_end_()	// deactivate pre; pre; PAGE:en.w:Comment_(computer_programming); DATE:2014-06-23
, Tag_gallery = new_(Tid_gallery, "gallery").Xtn_mw_().Block_open_bgn_().Block_close_end_().Xtn_auto_close_()
, Tag_imageMap = new_(Tid_imageMap, "imageMap").Xtn_mw_()
, Tag_timeline = new_(Tid_timeline, "timeline").Xtn_mw_()
, Tag_hiero = new_(Tid_hiero, "hiero").Xtn_mw_()
, Tag_inputBox = new_(Tid_inputBox, "inputBox").Xtn_mw_()
, Tag_pages = new_(Tid_pages, "pages").Xtn_mw_()
, Tag_section = new_(Tid_section, "section").Xtn_mw_().Langs_(Xol_lang_stub_.Id_de, "Abschnitt").Langs_(Xol_lang_stub_.Id_he, "קטע").Langs_(Xol_lang_stub_.Id_pt, "trecho") // DATE:2014-07-18
, Tag_pagequality = new_(Tid_pagequality, "pagequality").Xtn_mw_()
, Tag_pagelist = new_(Tid_pagelist, "pagelist").Xtn_mw_()
, Tag_categoryList = new_(Tid_categoryList, "categoryList").Xtn_mw_()
, Tag_categoryTree = new_(Tid_categoryTree, "categoryTree").Xtn_mw_()
, Tag_dynamicPageList = new_(Tid_dynamicPageList, "dynamicPageList").Xtn_mw_()
, Tag_time = new_(Tid_time, "time")
, Tag_input = new_(Tid_input, "input").Restricted_()
, Tag_textarea = new_(Tid_textarea, "textarea").Restricted_()
, Tag_score = new_(Tid_score, "score").Xtn_mw_()
, Tag_button = new_(Tid_button, "button").Restricted_()
, Tag_select = new_(Tid_select, "select").Restricted_()
, Tag_option = new_(Tid_option, "option").Restricted_()
, Tag_optgroup = new_(Tid_optgroup, "optgroup").Restricted_()
, Tag_script = new_(Tid_script, "script").Restricted_()	// NOTE: had ".Block_open_bgn_().Block_close_end_()"; PAGE:en.w:Cascading_Style_Sheets DATE:2014-06-23
, Tag_style = new_(Tid_style, "style").Restricted_()	// NOTE: had ".Block_open_bgn_().Block_close_end_()"; PAGE:en.w:Cascading_Style_Sheets DATE:2014-06-23
, Tag_form = new_(Tid_form, "form").Restricted_()		// NOTE: had ".Block_open_bgn_().Block_close_end_()"; PAGE:en.w:Cascading_Style_Sheets DATE:2014-06-23
, Tag_translate = new_(Tid_translate, "translate").Xtn_mw_()
, Tag_languages = new_(Tid_languages, "languages").Xtn_mw_()
, Tag_templateData = new_(Tid_templateData, "templateData").Xtn_mw_()
, Tag_bdi = new_(Tid_bdi, "bdi")
, Tag_data = new_(Tid_data, "data")
, Tag_mark = new_(Tid_mark, "mark")
, Tag_wbr = new_(Tid_wbr, "wbr").Single_only_().Single_only_html_()
, Tag_bdo = new_(Tid_bdo, "bdo").No_inline_().Section_().Block_open_bgn_().Block_close_end_()
, Tag_listing_buy = new_(Tid_listing_buy, "buy").Xtn_mw_()
, Tag_listing_do = new_(Tid_listing_do, "do").Xtn_mw_()
, Tag_listing_drink = new_(Tid_listing_drink, "drink").Xtn_mw_()
, Tag_listing_eat = new_(Tid_listing_eat, "eat").Xtn_mw_()
, Tag_listing_listing = new_(Tid_listing_listing, "listing").Xtn_mw_()
, Tag_listing_see = new_(Tid_listing_see, "see").Xtn_mw_()
, Tag_listing_sleep = new_(Tid_listing_sleep, "sleep").Xtn_mw_()
, Tag_rss = new_(Tid_rss, "rss").Xtn_mw_()
, Tag_xowa_html = new_(Tid_xowa_html, "xowa_html").Xtn_()
, Tag_xowa_tag_bgn = new_(Tid_xowa_tag_bgn, "xtag_bgn").Xtn_()
, Tag_xowa_tag_end = new_(Tid_xowa_tag_end, "xtag_end").Xtn_()
, Tag_quiz = new_(Tid_quiz, "quiz").Xtn_mw_()
, Tag_indicator = new_(Tid_indicator, "indicator").Xtn_mw_()
, Tag_q = new_(Tid_q, "q")
, Tag_graph = new_(Tid_graph, "graph").Xtn_mw_()
	;
}
