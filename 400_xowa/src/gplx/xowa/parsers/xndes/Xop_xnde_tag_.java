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
import gplx.xowa.langs.*;
public class Xop_xnde_tag_ {
	public static final int Bgn_mode__normal = 0, Bgn_mode__inline = 1;
	public static final int End_mode__normal = 0, End_mode__inline = 1, End_mode__escape = 2; // escape is for hr which does not support </hr>
	public static final    byte[] Bry__onlyinclude = Bry_.new_a7("onlyinclude");
	public static final    byte[] Bry__end_tag_bgn = Bry_.new_a7("</");
	public static final int
  Tid__null = -1
, Tid__b = 0
, Tid__strong = 1
, Tid__i = 2
, Tid__em = 3
, Tid__cite = 4
, Tid__dfn = 5
, Tid__var = 6
, Tid__u = 7
, Tid__ins = 8
, Tid__abbr = 9
, Tid__strike = 10
, Tid__del = 11
, Tid__s = 12
, Tid__sub = 13
, Tid__sup = 14
, Tid__big = 15
, Tid__small = 16
, Tid__code = 17
, Tid__tt = 18
, Tid__kbd = 19
, Tid__samp = 20
, Tid__blockquote = 21
, Tid__pre = 22
, Tid__font = 23
, Tid__center = 24
, Tid__p = 25
, Tid__span = 26
, Tid__div = 27
, Tid__hr = 28
, Tid__br = 29
, Tid__h1 = 30
, Tid__h2 = 31
, Tid__h3 = 32
, Tid__h4 = 33
, Tid__h5 = 34
, Tid__h6 = 35
, Tid__li = 36
, Tid__dt = 37
, Tid__dd = 38
, Tid__ol = 39
, Tid__ul = 40
, Tid__dl = 41
, Tid__table = 42
, Tid__tr = 43
, Tid__td = 44
, Tid__th = 45
, Tid__thead = 46
, Tid__tfoot = 47
, Tid__tbody = 48
, Tid__caption = 49
, Tid__colgroup = 50
, Tid__col = 51
, Tid__a = 52
, Tid__img = 53
, Tid__ruby = 54
, Tid__rt = 55
, Tid__rb = 56
, Tid__rp = 57
, Tid__includeonly = 58
, Tid__noinclude = 59
, Tid__onlyinclude = 60
, Tid__nowiki = 61
, Tid__xowa_cmd = 62
, Tid__poem = 63
, Tid__math = 64
, Tid__ref = 65
, Tid__references = 66
, Tid__source = 67
, Tid__syntaxHighlight = 68
, Tid__gallery = 69
, Tid__imageMap = 70
, Tid__timeline = 71
, Tid__hiero = 72
, Tid__inputBox = 73
, Tid__pages = 74
, Tid__section = 75
, Tid__pagequality = 76
, Tid__pagelist = 77
, Tid__categoryList = 78
, Tid__categoryTree = 79
, Tid__dynamicPageList = 80
, Tid__time = 81
, Tid__input = 82
, Tid__textarea = 83
, Tid__score = 84
, Tid__button = 85
, Tid__select = 86
, Tid__option = 87
, Tid__optgroup = 88
, Tid__script = 89
, Tid__style = 90
, Tid__form = 91
, Tid__translate = 92
, Tid__languages = 93
, Tid__templateData = 94
, Tid__bdi = 95
, Tid__data = 96
, Tid__mark = 97
, Tid__wbr = 98
, Tid__bdo = 99
, Tid__listing_buy = 100
, Tid__listing_do = 101
, Tid__listing_drink = 102
, Tid__listing_eat = 103
, Tid__listing_listing = 104
, Tid__listing_see = 105
, Tid__listing_sleep = 106
, Tid__rss = 107
, Tid__xowa_html = 108
, Tid__xowa_tag_bgn = 109
, Tid__xowa_tag_end = 110
, Tid__quiz = 111
, Tid__indicator = 112
, Tid__q = 113
, Tid__graph = 114
, Tid__random_selection = 115
, Tid__tabber = 116
, Tid__tabview = 117
, Tid__xowa_wiki_setup = 118
, Tid__mapframe = 119
, Tid__maplink = 120
, Tid__meta = 121
, Tid__link = 122
	;
	public static final int Tid__len = 123;
	public static final    Xop_xnde_tag[] Ary = new Xop_xnde_tag[Tid__len];
	private static Xop_xnde_tag New(int id, String name) {
		Xop_xnde_tag rv = new Xop_xnde_tag(id, name);
		Ary[id] = rv;
		return rv;
	}
	public static final    Xop_xnde_tag
  Tag__b = New(Tid__b, "b").No_inline_()
, Tag__strong = New(Tid__strong, "strong").No_inline_()
, Tag__i = New(Tid__i, "i").No_inline_()
, Tag__em = New(Tid__em, "em").No_inline_()
, Tag__cite = New(Tid__cite, "cite").No_inline_()
, Tag__dfn = New(Tid__dfn, "dfn").No_inline_()
, Tag__var = New(Tid__var, "var").No_inline_()
, Tag__u = New(Tid__u, "u").No_inline_().Repeat_ends_()	// PAGE:en.b:Textbook_of_Psychiatry/Alcoholism_and_Psychoactive_Substance_Use_Disorders; DATE:2014-09-05
, Tag__ins = New(Tid__ins, "ins").No_inline_()
, Tag__abbr = New(Tid__abbr, "abbr").No_inline_()
, Tag__strike = New(Tid__strike, "strike").No_inline_()
, Tag__del = New(Tid__del, "del").No_inline_()
, Tag__s = New(Tid__s, "s").No_inline_()
, Tag__sub = New(Tid__sub, "sub").No_inline_()
, Tag__sup = New(Tid__sup, "sup").No_inline_()
, Tag__big = New(Tid__big, "big").No_inline_()
, Tag__small = New(Tid__small, "small").No_inline_()
, Tag__code = New(Tid__code, "code").No_inline_().Repeat_ends_()
, Tag__tt = New(Tid__tt, "tt").No_inline_().Repeat_ends_()
, Tag__kbd = New(Tid__kbd, "kbd").No_inline_()
, Tag__samp = New(Tid__samp, "samp").No_inline_()
, Tag__blockquote = New(Tid__blockquote, "blockquote").No_inline_().Repeat_mids_().Section_().Block_open_bgn_().Block_close_end_()	// NOTE: should be open_end_, but leaving for now; DATE:2014-03-11; added Repeat_mids_(); PAGE:en.w:Ring_a_Ring_o'_Roses DATE:2014-06-26
, Tag__pre = New(Tid__pre, "pre").No_inline_().Section_().Xtn_mw_().Raw_().Block_open_bgn_().Block_close_end_().Ignore_empty_().Xtn_skips_template_args_()
, Tag__font = New(Tid__font, "font").No_inline_()
, Tag__center = New(Tid__center, "center").No_inline_().Block_open_end_().Block_close_end_() // removed .Repeat_ends_(); added Nest_(); EX: w:Burr Truss; DATE:2012-12-12
, Tag__p = New(Tid__p, "p").No_inline_().Section_().Block_open_bgn_().Block_close_end_()
, Tag__span = New(Tid__span, "span").Section_()
, Tag__div = New(Tid__div, "div").Section_().Block_open_end_().Block_close_end_()
, Tag__hr = New(Tid__hr, "hr").Single_only_().Single_only_html_().Bgn_mode__inline_().Inline_by_backslash_().End_mode__escape_().Section_().Block_close_end_()
, Tag__br = New(Tid__br, "br").Single_only_().Single_only_html_().Bgn_mode__inline_().Inline_by_backslash_().End_mode__inline_().Section_()
, Tag__h1 = New(Tid__h1, "h1").No_inline_().Section_().Block_open_bgn_().Block_close_end_()
, Tag__h2 = New(Tid__h2, "h2").No_inline_().Section_().Block_open_bgn_().Block_close_end_()
, Tag__h3 = New(Tid__h3, "h3").No_inline_().Section_().Block_open_bgn_().Block_close_end_()
, Tag__h4 = New(Tid__h4, "h4").No_inline_().Section_().Block_open_bgn_().Block_close_end_()
, Tag__h5 = New(Tid__h5, "h5").No_inline_().Section_().Block_open_bgn_().Block_close_end_()
, Tag__h6 = New(Tid__h6, "h6").No_inline_().Section_().Block_open_bgn_().Block_close_end_()
, Tag__li = New(Tid__li, "li").Repeat_mids_().Empty_ignored_().Block_open_bgn_().Block_close_end_()
, Tag__dt = New(Tid__dt, "dt").Repeat_mids_()
, Tag__dd = New(Tid__dd, "dd").Repeat_mids_()
, Tag__ol = New(Tid__ol, "ol").No_inline_().Block_open_bgn_().Block_close_end_()
, Tag__ul = New(Tid__ul, "ul").No_inline_().Block_open_bgn_().Block_close_end_()
, Tag__dl = New(Tid__dl, "dl").No_inline_()
, Tag__table = New(Tid__table, "table").No_inline_().Block_open_bgn_().Block_close_end_()
, Tag__tr = New(Tid__tr, "tr").Tbl_sub_().Block_open_bgn_().Block_open_end_()
, Tag__td = New(Tid__td, "td").Tbl_sub_().Block_open_end_().Block_close_bgn_()
, Tag__th = New(Tid__th, "th").Tbl_sub_().Block_open_end_().Block_close_bgn_()
, Tag__thead = New(Tid__thead, "thead")
, Tag__tfoot = New(Tid__tfoot, "tfoot")
, Tag__tbody = New(Tid__tbody, "tbody")
, Tag__caption = New(Tid__caption, "caption").No_inline_().Tbl_sub_()
, Tag__colgroup = New(Tid__colgroup, "colgroup")
, Tag__col = New(Tid__col, "col")
, Tag__a = New(Tid__a, "a").Restricted_()
, Tag__img = New(Tid__img, "img").Single_only_html_().Restricted_()	// NOTE: was .Xtn() DATE:2014-11-06
, Tag__ruby = New(Tid__ruby, "ruby").No_inline_()
, Tag__rt = New(Tid__rt, "rt").No_inline_()
, Tag__rb = New(Tid__rb, "rb").No_inline_()
, Tag__rp = New(Tid__rp, "rp").No_inline_()
, Tag__includeonly = New(Tid__includeonly, "includeonly")
, Tag__noinclude = New(Tid__noinclude, "noinclude")
, Tag__onlyinclude = New(Tid__onlyinclude, "onlyinclude")
, Tag__nowiki = New(Tid__nowiki, "nowiki")
, Tag__xowa_cmd = New(Tid__xowa_cmd, "xowa_cmd").Xtn_()
, Tag__poem = New(Tid__poem, "poem").Xtn_mw_().Xtn_auto_close_()
, Tag__math = New(Tid__math, "math").Xtn_mw_()
, Tag__ref = New(Tid__ref, "ref").Xtn_mw_()
, Tag__references = New(Tid__references, "references").Xtn_mw_()
, Tag__source = New(Tid__source, "source").Xtn_mw_().Block_open_bgn_().Block_close_end_()	// deactivate pre; pre; PAGE:en.w:Comment_(computer_programming); DATE:2014-06-23
, Tag__syntaxHighlight = New(Tid__syntaxHighlight, "syntaxHighlight").Xtn_mw_().Block_open_bgn_().Block_close_end_()	// deactivate pre; pre; PAGE:en.w:Comment_(computer_programming); DATE:2014-06-23
, Tag__gallery = New(Tid__gallery, "gallery").Xtn_mw_().Block_open_bgn_().Block_close_end_().Xtn_auto_close_()
, Tag__imageMap = New(Tid__imageMap, "imageMap").Xtn_mw_()
, Tag__timeline = New(Tid__timeline, "timeline").Xtn_mw_()
, Tag__hiero = New(Tid__hiero, "hiero").Xtn_mw_()
, Tag__inputBox = New(Tid__inputBox, "inputBox").Xtn_mw_()
, Tag__pages = New(Tid__pages, "pages").Xtn_mw_()
, Tag__section = New(Tid__section, "section").Xtn_mw_().Langs_(Xol_lang_stub_.Id_de, "Abschnitt").Langs_(Xol_lang_stub_.Id_he, "קטע").Langs_(Xol_lang_stub_.Id_pt, "trecho") // DATE:2014-07-18
, Tag__pagequality = New(Tid__pagequality, "pagequality").Xtn_mw_()
, Tag__pagelist = New(Tid__pagelist, "pagelist").Xtn_mw_()
, Tag__categoryList = New(Tid__categoryList, "categoryList").Xtn_mw_()
, Tag__categoryTree = New(Tid__categoryTree, "categoryTree").Xtn_mw_()
, Tag__dynamicPageList = New(Tid__dynamicPageList, "dynamicPageList").Xtn_mw_()
, Tag__time = New(Tid__time, "time")
, Tag__input = New(Tid__input, "input").Restricted_()
, Tag__textarea = New(Tid__textarea, "textarea").Restricted_()
, Tag__score = New(Tid__score, "score").Xtn_mw_()
, Tag__button = New(Tid__button, "button").Restricted_()
, Tag__select = New(Tid__select, "select").Restricted_()
, Tag__option = New(Tid__option, "option").Restricted_()
, Tag__optgroup = New(Tid__optgroup, "optgroup").Restricted_()
, Tag__script = New(Tid__script, "script").Restricted_()	// NOTE: had ".Block_open_bgn_().Block_close_end_()"; PAGE:en.w:Cascading_Style_Sheets DATE:2014-06-23
, Tag__style = New(Tid__style, "style").Restricted_()	// NOTE: had ".Block_open_bgn_().Block_close_end_()"; PAGE:en.w:Cascading_Style_Sheets DATE:2014-06-23
, Tag__form = New(Tid__form, "form").Restricted_()		// NOTE: had ".Block_open_bgn_().Block_close_end_()"; PAGE:en.w:Cascading_Style_Sheets DATE:2014-06-23
, Tag__translate = New(Tid__translate, "translate").Xtn_mw_()
, Tag__languages = New(Tid__languages, "languages").Xtn_mw_()
, Tag__templateData = New(Tid__templateData, "templateData").Xtn_mw_()
, Tag__bdi = New(Tid__bdi, "bdi")
, Tag__data = New(Tid__data, "data")
, Tag__mark = New(Tid__mark, "mark")
, Tag__wbr = New(Tid__wbr, "wbr").Single_only_().Single_only_html_()
, Tag__bdo = New(Tid__bdo, "bdo").No_inline_().Section_().Block_open_bgn_().Block_close_end_()
, Tag__listing_buy = New(Tid__listing_buy, "buy").Xtn_mw_()
, Tag__listing_do = New(Tid__listing_do, "do").Xtn_mw_()
, Tag__listing_drink = New(Tid__listing_drink, "drink").Xtn_mw_()
, Tag__listing_eat = New(Tid__listing_eat, "eat").Xtn_mw_()
, Tag__listing_listing = New(Tid__listing_listing, "listing").Xtn_mw_()
, Tag__listing_see = New(Tid__listing_see, "see").Xtn_mw_()
, Tag__listing_sleep = New(Tid__listing_sleep, "sleep").Xtn_mw_()
, Tag__rss = New(Tid__rss, "rss").Xtn_mw_()
, Tag__xowa_html = New(Tid__xowa_html, "xowa_html").Xtn_()
, Tag__xowa_tag_bgn = New(Tid__xowa_tag_bgn, "xtag_bgn").Xtn_()
, Tag__xowa_tag_end = New(Tid__xowa_tag_end, "xtag_end").Xtn_()
, Tag__quiz = New(Tid__quiz, "quiz").Xtn_mw_()
, Tag__indicator = New(Tid__indicator, "indicator").Xtn_mw_()
, Tag__q = New(Tid__q, "q")
, Tag__graph = New(Tid__graph, "graph").Xtn_mw_()
, Tag__random_selection = New(Tid__random_selection, "choose").Xtn_mw_()
, Tag__tabber = New(Tid__tabber, "tabber").Xtn_mw_()
, Tag__tabview = New(Tid__tabview, "tabview").Xtn_mw_()
, Tag__xowa_wiki_setup = New(Tid__xowa_wiki_setup, "xowa_Wiki_setup").Xtn_()
, Tag__mapframe = New(Tid__mapframe, "mapframe").Xtn_mw_()
, Tag__maplink = New(Tid__maplink, "maplink").Xtn_mw_()
, Tag__meta = New(Tid__meta, "meta")
, Tag__link = New(Tid__link, "link")
	;
}
