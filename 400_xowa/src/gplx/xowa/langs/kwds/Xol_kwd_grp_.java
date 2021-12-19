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
package gplx.xowa.langs.kwds;
import gplx.types.basics.utls.BryUtl;
import gplx.types.basics.lists.Hash_adp_bry;
import gplx.types.basics.utls.IntUtl;
import gplx.types.basics.wrappers.IntVal;
import gplx.types.errs.ErrUtl;
public class Xol_kwd_grp_ {
public static final int
  Id_redirect = 0
, Id_notoc = 1
, Id_nogallery = 2
, Id_forcetoc = 3
, Id_toc = 4
, Id_noeditsection = 5
, Id_noheader = 6
, Id_utc_month_int_len2 = 7
, Id_utc_month_int = 8
, Id_utc_month_name = 9
, Id_utc_month_gen = 10
, Id_utc_month_abrv = 11
, Id_utc_day_int = 12
, Id_utc_day_int_len2 = 13
, Id_utc_day_name = 14
, Id_utc_year = 15
, Id_utc_time = 16
, Id_utc_hour = 17
, Id_lcl_month_int_len2 = 18
, Id_lcl_month_int = 19
, Id_lcl_month_name = 20
, Id_lcl_month_gen = 21
, Id_lcl_month_abrv = 22
, Id_lcl_day_int = 23
, Id_lcl_day_int_len2 = 24
, Id_lcl_day_name = 25
, Id_lcl_year = 26
, Id_lcl_time = 27
, Id_lcl_hour = 28
, Id_num_pages = 29
, Id_num_articles = 30
, Id_num_files = 31
, Id_num_users = 32
, Id_num_users_active = 33
, Id_num_edits = 34
, Id_num_views = 35
, Id_ttl_page_txt = 36
, Id_ttl_page_url = 37
, Id_ns_txt = 38
, Id_ns_url = 39
, Id_ns_talk_txt = 40
, Id_ns_talk_url = 41
, Id_ns_subj_txt = 42
, Id_ns_subj_url = 43
, Id_ttl_full_txt = 44
, Id_ttl_full_url = 45
, Id_ttl_leaf_txt = 46
, Id_ttl_leaf_url = 47
, Id_ttl_base_txt = 48
, Id_ttl_base_url = 49
, Id_ttl_talk_txt = 50
, Id_ttl_talk_url = 51
, Id_ttl_subj_txt = 52
, Id_ttl_subj_url = 53
, Id_msg = 54
, Id_subst = 55
, Id_safesubst = 56
, Id_msgnw = 57
, Id_img_thumbnail = 58
, Id_img_manualthumb = 59
, Id_img_framed = 60
, Id_img_frameless = 61
, Id_img_upright = 62
, Id_img_upright_factor = 63
, Id_img_border = 64
, Id_img_align = 65
, Id_img_valign = 66
, Id_img_alt = 67
, Id_img_class = 68
, Id_img_caption = 69
, Id_img_link_url = 70
, Id_img_link_title = 71
, Id_img_link_target = 72
, Id_img_link_none = 73
, Id_img_width = 74
, Id_img_page = 75
, Id_img_none = 76
, Id_img_right = 77
, Id_img_center = 78
, Id_img_left = 79
, Id_img_baseline = 80
, Id_img_sub = 81
, Id_img_super = 82
, Id_img_top = 83
, Id_img_text_top = 84
, Id_img_middle = 85
, Id_img_bottom = 86
, Id_img_text_bottom = 87
, Id_img_link = 88
, Id_i18n_int = 89
, Id_site_sitename = 90
, Id_url_ns = 91
, Id_url_nse = 92
, Id_url_localurl = 93
, Id_url_localurle = 94
, Id_site_articlepath = 95
, Id_site_server = 96
, Id_site_servername = 97
, Id_site_scriptpath = 98
, Id_site_stylepath = 99
, Id_i18n_grammar = 100
, Id_i18n_gender = 101
, Id_notitleconvert = 102
, Id_nocontentconvert = 103
, Id_utc_week = 104
, Id_utc_dow = 105
, Id_lcl_week = 106
, Id_lcl_dow = 107
, Id_rev_id = 108
, Id_rev_day_int = 109
, Id_rev_day_int_len2 = 110
, Id_rev_month_int_len2 = 111
, Id_rev_month_int = 112
, Id_rev_year = 113
, Id_rev_timestamp = 114
, Id_rev_user = 115
, Id_i18n_plural = 116
, Id_url_fullurl = 117
, Id_url_fullurle = 118
, Id_str_lcfirst = 119
, Id_str_ucfirst = 120
, Id_str_lc = 121
, Id_str_uc = 122
, Id_raw = 123
, Id_page_displaytitle = 124
, Id_str_rawsuffix = 125
, Id_newsectionlink = 126
, Id_nonewsectionlink = 127
, Id_site_currentversion = 128
, Id_url_urlencode = 129
, Id_url_anchorencode = 130
, Id_utc_timestamp = 131
, Id_lcl_timestamp = 132
, Id_site_directionmark = 133
, Id_i18n_language = 134
, Id_site_contentlanguage = 135
, Id_site_pagesinnamespace = 136
, Id_num_admins = 137
, Id_str_formatnum = 138
, Id_str_padleft = 139
, Id_str_padright = 140
, Id_misc_special = 141
, Id_page_defaultsort = 142
, Id_url_filepath = 143
, Id_misc_tag = 144
, Id_hiddencat = 145
, Id_site_pagesincategory = 146
, Id_rev_pagesize = 147
, Id_index = 148
, Id_noindex = 149
, Id_site_numberingroup = 150
, Id_staticredirect = 151
, Id_rev_protectionlevel = 152
, Id_str_formatdate = 153
, Id_url_path = 154
, Id_url_wiki = 155
, Id_url_query = 156
, Id_xtn_expr = 157
, Id_xtn_if = 158
, Id_xtn_ifeq = 159
, Id_xtn_ifexpr = 160
, Id_xtn_iferror = 161
, Id_xtn_switch = 162
, Id_xtn_default = 163
, Id_xtn_ifexist = 164
, Id_xtn_time = 165
, Id_xtn_timel = 166
, Id_xtn_rel2abs = 167
, Id_xtn_titleparts = 168
, Id_xowa_dbg = 169
, Id_ogg_noplayer = 170
, Id_ogg_noicon = 171
, Id_ogg_thumbtime = 172
, Id_xtn_geodata_coordinates = 173
, Id_url_canonicalurl = 174
, Id_url_canonicalurle = 175
, Id_lst = 176
, Id_lstx = 177
, Id_invoke = 178
, Id_property = 179
, Id_noexternallanglinks = 180
, Id_ns_num = 181
, Id_page_id = 182
, Id_disambig = 183
, Id_nocommafysuffix = 184
, Id_xowa = 185
, Id_mapSources_deg2dd = 186
, Id_mapSources_dd2dms = 187
, Id_mapSources_geoLink = 188
, Id_geoCrumbs_isin = 189
, Id_relatedArticles = 190
, Id_insider = 191
, Id_massMessage_target = 192
, Id_cascadingSources = 193
, Id_pendingChangeLevel = 194
, Id_pagesUsingPendingChanges = 195
, Id_bang = 196
, Id_wbreponame = 197
, Id_strx_len = 198
, Id_strx_pos = 199
, Id_strx_rpos = 200
, Id_strx_sub = 201
, Id_strx_count = 202
, Id_strx_replace = 203
, Id_strx_explode = 204
, Id_strx_urldecode = 205
, Id_pagesincategory_pages = 206
, Id_pagesincategory_subcats = 207
, Id_pagesincategory_files = 208
, Id_rev_revisionsize = 209
, Id_pagebanner = 210
, Id_rev_protectionexpiry = 211
, Id_new_window_link = 212
, Id_categorytree = 213
, Id_lsth = 214
, Id_assessment = 215
, Id_ttl_root_txt = 216
, Id_ttl_root_url = 217
, Id_statements = 218
, Id_translation = 219
, Id_pagelanguage = 220
;
public static final int Id__max = 221;

	private static byte[] ary_itm_(int id) {
		switch (id) {
case Xol_kwd_grp_.Id_redirect: return BryUtl.NewA7("redirect");
case Xol_kwd_grp_.Id_notoc: return BryUtl.NewA7("notoc");
case Xol_kwd_grp_.Id_nogallery: return BryUtl.NewA7("nogallery");
case Xol_kwd_grp_.Id_forcetoc: return BryUtl.NewA7("forcetoc");
case Xol_kwd_grp_.Id_toc: return BryUtl.NewA7("toc");
case Xol_kwd_grp_.Id_noeditsection: return BryUtl.NewA7("noeditsection");
case Xol_kwd_grp_.Id_noheader: return BryUtl.NewA7("noheader");
case Xol_kwd_grp_.Id_utc_month_int_len2: return BryUtl.NewA7("currentmonth");
case Xol_kwd_grp_.Id_utc_month_int: return BryUtl.NewA7("currentmonth1");
case Xol_kwd_grp_.Id_utc_month_name: return BryUtl.NewA7("currentmonthname");
case Xol_kwd_grp_.Id_utc_month_gen: return BryUtl.NewA7("currentmonthnamegen");
case Xol_kwd_grp_.Id_utc_month_abrv: return BryUtl.NewA7("currentmonthabbrev");
case Xol_kwd_grp_.Id_utc_day_int: return BryUtl.NewA7("currentday");
case Xol_kwd_grp_.Id_utc_day_int_len2: return BryUtl.NewA7("currentday2");
case Xol_kwd_grp_.Id_utc_day_name: return BryUtl.NewA7("currentdayname");
case Xol_kwd_grp_.Id_utc_year: return BryUtl.NewA7("currentyear");
case Xol_kwd_grp_.Id_utc_time: return BryUtl.NewA7("currenttime");
case Xol_kwd_grp_.Id_utc_hour: return BryUtl.NewA7("currenthour");
case Xol_kwd_grp_.Id_lcl_month_int_len2: return BryUtl.NewA7("localmonth");
case Xol_kwd_grp_.Id_lcl_month_int: return BryUtl.NewA7("localmonth1");
case Xol_kwd_grp_.Id_lcl_month_name: return BryUtl.NewA7("localmonthname");
case Xol_kwd_grp_.Id_lcl_month_gen: return BryUtl.NewA7("localmonthnamegen");
case Xol_kwd_grp_.Id_lcl_month_abrv: return BryUtl.NewA7("localmonthabbrev");
case Xol_kwd_grp_.Id_lcl_day_int: return BryUtl.NewA7("localday");
case Xol_kwd_grp_.Id_lcl_day_int_len2: return BryUtl.NewA7("localday2");
case Xol_kwd_grp_.Id_lcl_day_name: return BryUtl.NewA7("localdayname");
case Xol_kwd_grp_.Id_lcl_year: return BryUtl.NewA7("localyear");
case Xol_kwd_grp_.Id_lcl_time: return BryUtl.NewA7("localtime");
case Xol_kwd_grp_.Id_lcl_hour: return BryUtl.NewA7("localhour");
case Xol_kwd_grp_.Id_num_pages: return BryUtl.NewA7("numberofpages");
case Xol_kwd_grp_.Id_num_articles: return BryUtl.NewA7("numberofarticles");
case Xol_kwd_grp_.Id_num_files: return BryUtl.NewA7("numberoffiles");
case Xol_kwd_grp_.Id_num_users: return BryUtl.NewA7("numberofusers");
case Xol_kwd_grp_.Id_num_users_active: return BryUtl.NewA7("numberofactiveusers");
case Xol_kwd_grp_.Id_num_edits: return BryUtl.NewA7("numberofedits");
case Xol_kwd_grp_.Id_num_views: return BryUtl.NewA7("numberofviews");
case Xol_kwd_grp_.Id_ttl_page_txt: return BryUtl.NewA7("pagename");
case Xol_kwd_grp_.Id_ttl_page_url: return BryUtl.NewA7("pagenamee");
case Xol_kwd_grp_.Id_ns_txt: return BryUtl.NewA7("namespace");
case Xol_kwd_grp_.Id_ns_url: return BryUtl.NewA7("namespacee");
case Xol_kwd_grp_.Id_ns_talk_txt: return BryUtl.NewA7("talkspace");
case Xol_kwd_grp_.Id_ns_talk_url: return BryUtl.NewA7("talkspacee");
case Xol_kwd_grp_.Id_ns_subj_txt: return BryUtl.NewA7("subjectspace");
case Xol_kwd_grp_.Id_ns_subj_url: return BryUtl.NewA7("subjectspacee");
case Xol_kwd_grp_.Id_ttl_full_txt: return BryUtl.NewA7("fullpagename");
case Xol_kwd_grp_.Id_ttl_full_url: return BryUtl.NewA7("fullpagenamee");
case Xol_kwd_grp_.Id_ttl_leaf_txt: return BryUtl.NewA7("subpagename");
case Xol_kwd_grp_.Id_ttl_leaf_url: return BryUtl.NewA7("subpagenamee");
case Xol_kwd_grp_.Id_ttl_base_txt: return BryUtl.NewA7("basepagename");
case Xol_kwd_grp_.Id_ttl_base_url: return BryUtl.NewA7("basepagenamee");
case Xol_kwd_grp_.Id_ttl_talk_txt: return BryUtl.NewA7("talkpagename");
case Xol_kwd_grp_.Id_ttl_talk_url: return BryUtl.NewA7("talkpagenamee");
case Xol_kwd_grp_.Id_ttl_subj_txt: return BryUtl.NewA7("subjectpagename");
case Xol_kwd_grp_.Id_ttl_subj_url: return BryUtl.NewA7("subjectpagenamee");
case Xol_kwd_grp_.Id_ttl_root_txt: return BryUtl.NewU8("rootpagename");
case Xol_kwd_grp_.Id_ttl_root_url: return BryUtl.NewU8("rootpagenamee");
case Xol_kwd_grp_.Id_msg: return BryUtl.NewA7("msg");
case Xol_kwd_grp_.Id_subst: return BryUtl.NewA7("subst");
case Xol_kwd_grp_.Id_safesubst: return BryUtl.NewA7("safesubst");
case Xol_kwd_grp_.Id_msgnw: return BryUtl.NewA7("msgnw");
case Xol_kwd_grp_.Id_img_thumbnail: return BryUtl.NewA7("img_thumbnail");
case Xol_kwd_grp_.Id_img_manualthumb: return BryUtl.NewA7("img_manualthumb");
case Xol_kwd_grp_.Id_img_framed: return BryUtl.NewA7("img_framed");
case Xol_kwd_grp_.Id_img_frameless: return BryUtl.NewA7("img_frameless");
case Xol_kwd_grp_.Id_img_upright: return BryUtl.NewA7("img_upright");
case Xol_kwd_grp_.Id_img_upright_factor: return BryUtl.NewA7("img_upright_factor");
case Xol_kwd_grp_.Id_img_border: return BryUtl.NewA7("img_border");
case Xol_kwd_grp_.Id_img_align: return BryUtl.NewA7("img_align");
case Xol_kwd_grp_.Id_img_valign: return BryUtl.NewA7("img_valign");
case Xol_kwd_grp_.Id_img_alt: return BryUtl.NewA7("img_alt");
case Xol_kwd_grp_.Id_img_class: return BryUtl.NewA7("img_class");
case Xol_kwd_grp_.Id_img_caption: return BryUtl.NewA7("img_caption");
case Xol_kwd_grp_.Id_img_link_url: return BryUtl.NewA7("img_link_url");
case Xol_kwd_grp_.Id_img_link_title: return BryUtl.NewA7("img_link_title");
case Xol_kwd_grp_.Id_img_link_target: return BryUtl.NewA7("img_link_target");
case Xol_kwd_grp_.Id_img_link_none: return BryUtl.NewA7("img_link_none");
case Xol_kwd_grp_.Id_img_width: return BryUtl.NewA7("img_width");
case Xol_kwd_grp_.Id_img_page: return BryUtl.NewA7("img_page");
case Xol_kwd_grp_.Id_img_none: return BryUtl.NewA7("img_none");
case Xol_kwd_grp_.Id_img_right: return BryUtl.NewA7("img_right");
case Xol_kwd_grp_.Id_img_center: return BryUtl.NewA7("img_center");
case Xol_kwd_grp_.Id_img_left: return BryUtl.NewA7("img_left");
case Xol_kwd_grp_.Id_img_baseline: return BryUtl.NewA7("img_baseline");
case Xol_kwd_grp_.Id_img_sub: return BryUtl.NewA7("img_sub");
case Xol_kwd_grp_.Id_img_super: return BryUtl.NewA7("img_super");
case Xol_kwd_grp_.Id_img_top: return BryUtl.NewA7("img_top");
case Xol_kwd_grp_.Id_img_text_top: return BryUtl.NewA7("img_text_top");
case Xol_kwd_grp_.Id_img_middle: return BryUtl.NewA7("img_middle");
case Xol_kwd_grp_.Id_img_bottom: return BryUtl.NewA7("img_bottom");
case Xol_kwd_grp_.Id_img_text_bottom: return BryUtl.NewA7("img_text_bottom");
case Xol_kwd_grp_.Id_img_link: return BryUtl.NewA7("img_link");
case Xol_kwd_grp_.Id_i18n_int: return BryUtl.NewA7("int");
case Xol_kwd_grp_.Id_site_sitename: return BryUtl.NewA7("sitename");
case Xol_kwd_grp_.Id_url_ns: return BryUtl.NewA7("ns");
case Xol_kwd_grp_.Id_url_nse: return BryUtl.NewA7("nse");
case Xol_kwd_grp_.Id_url_localurl: return BryUtl.NewA7("localurl");
case Xol_kwd_grp_.Id_url_localurle: return BryUtl.NewA7("localurle");
case Xol_kwd_grp_.Id_site_articlepath: return BryUtl.NewA7("articlepath");
case Xol_kwd_grp_.Id_site_server: return BryUtl.NewA7("server");
case Xol_kwd_grp_.Id_site_servername: return BryUtl.NewA7("servername");
case Xol_kwd_grp_.Id_site_scriptpath: return BryUtl.NewA7("scriptpath");
case Xol_kwd_grp_.Id_site_stylepath: return BryUtl.NewA7("stylepath");
case Xol_kwd_grp_.Id_i18n_grammar: return BryUtl.NewA7("grammar");
case Xol_kwd_grp_.Id_i18n_gender: return BryUtl.NewA7("gender");
case Xol_kwd_grp_.Id_notitleconvert: return BryUtl.NewA7("notitleconvert");
case Xol_kwd_grp_.Id_nocontentconvert: return BryUtl.NewA7("nocontentconvert");
case Xol_kwd_grp_.Id_utc_week: return BryUtl.NewA7("currentweek");
case Xol_kwd_grp_.Id_utc_dow: return BryUtl.NewA7("currentdow");
case Xol_kwd_grp_.Id_lcl_week: return BryUtl.NewA7("localweek");
case Xol_kwd_grp_.Id_lcl_dow: return BryUtl.NewA7("localdow");
case Xol_kwd_grp_.Id_rev_id: return BryUtl.NewA7("revisionid");
case Xol_kwd_grp_.Id_rev_day_int: return BryUtl.NewA7("revisionday");
case Xol_kwd_grp_.Id_rev_day_int_len2: return BryUtl.NewA7("revisionday2");
case Xol_kwd_grp_.Id_rev_month_int_len2: return BryUtl.NewA7("revisionmonth");
case Xol_kwd_grp_.Id_rev_month_int: return BryUtl.NewA7("revisionmonth1");
case Xol_kwd_grp_.Id_rev_year: return BryUtl.NewA7("revisionyear");
case Xol_kwd_grp_.Id_rev_timestamp: return BryUtl.NewA7("revisiontimestamp");
case Xol_kwd_grp_.Id_rev_user: return BryUtl.NewA7("revisionuser");
case Xol_kwd_grp_.Id_i18n_plural: return BryUtl.NewA7("plural");
case Xol_kwd_grp_.Id_url_fullurl: return BryUtl.NewA7("fullurl");
case Xol_kwd_grp_.Id_url_fullurle: return BryUtl.NewA7("fullurle");
case Xol_kwd_grp_.Id_str_lcfirst: return BryUtl.NewA7("lcfirst");
case Xol_kwd_grp_.Id_str_ucfirst: return BryUtl.NewA7("ucfirst");
case Xol_kwd_grp_.Id_str_lc: return BryUtl.NewA7("lc");
case Xol_kwd_grp_.Id_str_uc: return BryUtl.NewA7("uc");
case Xol_kwd_grp_.Id_raw: return BryUtl.NewA7("raw");
case Xol_kwd_grp_.Id_page_displaytitle: return BryUtl.NewA7("displaytitle");
case Xol_kwd_grp_.Id_str_rawsuffix: return BryUtl.NewA7("rawsuffix");
case Xol_kwd_grp_.Id_newsectionlink: return BryUtl.NewA7("newsectionlink");
case Xol_kwd_grp_.Id_nonewsectionlink: return BryUtl.NewA7("nonewsectionlink");
case Xol_kwd_grp_.Id_site_currentversion: return BryUtl.NewA7("currentversion");
case Xol_kwd_grp_.Id_url_urlencode: return BryUtl.NewA7("urlencode");
case Xol_kwd_grp_.Id_url_anchorencode: return BryUtl.NewA7("anchorencode");
case Xol_kwd_grp_.Id_utc_timestamp: return BryUtl.NewA7("currenttimestamp");
case Xol_kwd_grp_.Id_lcl_timestamp: return BryUtl.NewA7("localtimestamp");
case Xol_kwd_grp_.Id_site_directionmark: return BryUtl.NewA7("directionmark");
case Xol_kwd_grp_.Id_i18n_language: return BryUtl.NewA7("language");
case Xol_kwd_grp_.Id_site_contentlanguage: return BryUtl.NewA7("contentlanguage");
case Xol_kwd_grp_.Id_site_pagesinnamespace: return BryUtl.NewA7("pagesinnamespace");
case Xol_kwd_grp_.Id_num_admins: return BryUtl.NewA7("numberofadmins");
case Xol_kwd_grp_.Id_str_formatnum: return BryUtl.NewA7("formatnum");
case Xol_kwd_grp_.Id_str_padleft: return BryUtl.NewA7("padleft");
case Xol_kwd_grp_.Id_str_padright: return BryUtl.NewA7("padright");
case Xol_kwd_grp_.Id_misc_special: return BryUtl.NewA7("special");
case Xol_kwd_grp_.Id_page_defaultsort: return BryUtl.NewA7("defaultsort");
case Xol_kwd_grp_.Id_url_filepath: return BryUtl.NewA7("filepath");
case Xol_kwd_grp_.Id_misc_tag: return BryUtl.NewA7("tag");
case Xol_kwd_grp_.Id_hiddencat: return BryUtl.NewA7("hiddencat");
case Xol_kwd_grp_.Id_site_pagesincategory: return BryUtl.NewA7("pagesincategory");
case Xol_kwd_grp_.Id_rev_pagesize: return BryUtl.NewA7("pagesize");
case Xol_kwd_grp_.Id_index: return BryUtl.NewA7("index");
case Xol_kwd_grp_.Id_noindex: return BryUtl.NewA7("noindex");
case Xol_kwd_grp_.Id_site_numberingroup: return BryUtl.NewA7("numberingroup");
case Xol_kwd_grp_.Id_staticredirect: return BryUtl.NewA7("staticredirect");
case Xol_kwd_grp_.Id_rev_protectionlevel: return BryUtl.NewA7("protectionlevel");
case Xol_kwd_grp_.Id_str_formatdate: return BryUtl.NewA7("formatdate");
case Xol_kwd_grp_.Id_url_path: return BryUtl.NewA7("url_path");
case Xol_kwd_grp_.Id_url_wiki: return BryUtl.NewA7("url_wiki");
case Xol_kwd_grp_.Id_url_query: return BryUtl.NewA7("url_query");
case Xol_kwd_grp_.Id_xtn_expr: return BryUtl.NewA7("expr");
case Xol_kwd_grp_.Id_xtn_if: return BryUtl.NewA7("if");
case Xol_kwd_grp_.Id_xtn_ifeq: return BryUtl.NewA7("ifeq");
case Xol_kwd_grp_.Id_xtn_ifexpr: return BryUtl.NewA7("ifexpr");
case Xol_kwd_grp_.Id_xtn_iferror: return BryUtl.NewA7("iferror");
case Xol_kwd_grp_.Id_xtn_switch: return BryUtl.NewA7("switch");
case Xol_kwd_grp_.Id_xtn_default: return BryUtl.NewA7("default");
case Xol_kwd_grp_.Id_xtn_ifexist: return BryUtl.NewA7("ifexist");
case Xol_kwd_grp_.Id_xtn_time: return BryUtl.NewA7("time");
case Xol_kwd_grp_.Id_xtn_timel: return BryUtl.NewA7("timel");
case Xol_kwd_grp_.Id_xtn_rel2abs: return BryUtl.NewA7("rel2abs");
case Xol_kwd_grp_.Id_xtn_titleparts: return BryUtl.NewA7("titleparts");
case Xol_kwd_grp_.Id_xowa_dbg: return BryUtl.NewA7("xowa_dbg");
case Xol_kwd_grp_.Id_ogg_noplayer: return BryUtl.NewA7("noplayer");
case Xol_kwd_grp_.Id_ogg_noicon: return BryUtl.NewA7("noicon");
case Xol_kwd_grp_.Id_ogg_thumbtime: return BryUtl.NewA7("thumbtime");
case Xol_kwd_grp_.Id_xtn_geodata_coordinates: return BryUtl.NewA7("coordinates");
case Xol_kwd_grp_.Id_url_canonicalurl: return BryUtl.NewA7("canonicalurl");
case Xol_kwd_grp_.Id_url_canonicalurle: return BryUtl.NewA7("canonicalurle");
case Xol_kwd_grp_.Id_lst: return BryUtl.NewA7("lst");
case Xol_kwd_grp_.Id_lstx: return BryUtl.NewA7("lstx");
case Xol_kwd_grp_.Id_lsth: return BryUtl.NewU8("lsth");
case Xol_kwd_grp_.Id_invoke: return BryUtl.NewA7("invoke");
case Xol_kwd_grp_.Id_property: return BryUtl.NewA7("property");
case Xol_kwd_grp_.Id_noexternallanglinks: return BryUtl.NewA7("noexternallanglinks");
case Xol_kwd_grp_.Id_ns_num: return BryUtl.NewA7("namespacenumber");
case Xol_kwd_grp_.Id_page_id: return BryUtl.NewA7("pageid");
case Xol_kwd_grp_.Id_disambig: return BryUtl.NewA7("disambiguation");
case Xol_kwd_grp_.Id_nocommafysuffix: return BryUtl.NewA7("nosep");
case Xol_kwd_grp_.Id_xowa: return BryUtl.NewA7("xowa");
case Xol_kwd_grp_.Id_mapSources_deg2dd: return BryUtl.NewA7("deg2dd");
case Xol_kwd_grp_.Id_mapSources_dd2dms: return BryUtl.NewA7("dd2dms");
case Xol_kwd_grp_.Id_mapSources_geoLink: return BryUtl.NewA7("geolink");
case Xol_kwd_grp_.Id_geoCrumbs_isin: return BryUtl.NewA7("isin");
case Xol_kwd_grp_.Id_relatedArticles: return BryUtl.NewA7("relatedArticles");
case Xol_kwd_grp_.Id_insider: return BryUtl.NewA7("insider");
case Xol_kwd_grp_.Id_massMessage_target: return BryUtl.NewA7("target");
case Xol_kwd_grp_.Id_cascadingSources: return BryUtl.NewA7("cascadingSources");
case Xol_kwd_grp_.Id_pendingChangeLevel: return BryUtl.NewA7("pendingChangeLevel");
case Xol_kwd_grp_.Id_pagesUsingPendingChanges: return BryUtl.NewA7("pagesUsingPendingChanges");
case Xol_kwd_grp_.Id_bang: return BryUtl.NewA7("!");
case Xol_kwd_grp_.Id_wbreponame: return BryUtl.NewA7("wbreponame");
case Xol_kwd_grp_.Id_strx_len: return BryUtl.NewA7("len");
case Xol_kwd_grp_.Id_strx_pos: return BryUtl.NewA7("pos");
case Xol_kwd_grp_.Id_strx_rpos: return BryUtl.NewA7("rpos");
case Xol_kwd_grp_.Id_strx_sub: return BryUtl.NewA7("sub");
case Xol_kwd_grp_.Id_strx_count: return BryUtl.NewA7("count");
case Xol_kwd_grp_.Id_strx_replace: return BryUtl.NewA7("replace");
case Xol_kwd_grp_.Id_strx_explode: return BryUtl.NewA7("explode");
case Xol_kwd_grp_.Id_strx_urldecode: return BryUtl.NewA7("urldecode");
case Xol_kwd_grp_.Id_pagesincategory_pages: return BryUtl.NewU8("pagesincategory_pages");
case Xol_kwd_grp_.Id_pagesincategory_subcats: return BryUtl.NewU8("pagesincategory_subcats");
case Xol_kwd_grp_.Id_pagesincategory_files: return BryUtl.NewU8("pagesincategory_files");
case Xol_kwd_grp_.Id_rev_revisionsize: return BryUtl.NewU8("revisionsize");
case Xol_kwd_grp_.Id_pagebanner: return BryUtl.NewU8("pagebanner");
case Xol_kwd_grp_.Id_rev_protectionexpiry: return BryUtl.NewU8("protectionexpiry");
case Xol_kwd_grp_.Id_new_window_link: return BryUtl.NewU8("newwindowlink");
case Xol_kwd_grp_.Id_categorytree: return BryUtl.NewU8("categorytree");
case Xol_kwd_grp_.Id_assessment: return BryUtl.NewU8("assessment");
case Xol_kwd_grp_.Id_statements: return BryUtl.NewU8("statements");
case Xol_kwd_grp_.Id_translation: return BryUtl.NewU8("translation");
case Xol_kwd_grp_.Id_pagelanguage: return BryUtl.NewU8("pagelanguage");
default: throw ErrUtl.NewUnhandled(id);
		}
	}
	public static byte[] Bry_by_id(int id) {
		if (Bry__ == null) Bry_init();
		return Bry__[id];
	}	private static byte[][] Bry__;
	public static int Id_by_bry(byte[] find) {
		if (hash == null) {
			hash = Hash_adp_bry.ci_a7();	// ASCII: all MW kwds appear to be ASCII; EX: "redirect", "toc", "currentmont", etc.
			if (Bry__ == null) Bry_init();
			int len = Bry__.length;
			for (int i = 0; i < len; i++) {
				byte[] bry = Bry__[i];
				hash.Add(bry, new IntVal(i));
			}
		}
		Object o = hash.Get_by_bry(find);
		return o == null? IntUtl.Neg1 : ((IntVal)o).Val();
	}	private static Hash_adp_bry hash;
	private static void Bry_init() {
		Bry__ = new byte[Id__max][];
		for (int i = 0; i < Id__max; i++)
			Bry__[i] = ary_itm_(i);
	}
}

