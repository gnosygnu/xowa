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
package gplx.xowa.langs.msgs; import gplx.*; import gplx.xowa.*; import gplx.xowa.langs.*;
import gplx.core.btries.*; import gplx.core.brys.fmtrs.*; import gplx.xowa.parsers.tmpls.*;
public class Xol_msg_itm_ {
public static final int
  Id_dte_dow_name_sunday = 0
, Id_dte_dow_name_monday = 1
, Id_dte_dow_name_tuesday = 2
, Id_dte_dow_name_wednesday = 3
, Id_dte_dow_name_thursday = 4
, Id_dte_dow_name_friday = 5
, Id_dte_dow_name_saturday = 6
, Id_dte_dow_abrv_sun = 7
, Id_dte_dow_abrv_mon = 8
, Id_dte_dow_abrv_tue = 9
, Id_dte_dow_abrv_wed = 10
, Id_dte_dow_abrv_thu = 11
, Id_dte_dow_abrv_fri = 12
, Id_dte_dow_abrv_sat = 13
, Id_dte_month_name_january = 14
, Id_dte_month_name_february = 15
, Id_dte_month_name_march = 16
, Id_dte_month_name_april = 17
, Id_dte_month_name_may = 18
, Id_dte_month_name_june = 19
, Id_dte_month_name_july = 20
, Id_dte_month_name_august = 21
, Id_dte_month_name_september = 22
, Id_dte_month_name_october = 23
, Id_dte_month_name_november = 24
, Id_dte_month_name_december = 25
, Id_dte_month_gen_january = 26
, Id_dte_month_gen_february = 27
, Id_dte_month_gen_march = 28
, Id_dte_month_gen_april = 29
, Id_dte_month_gen_may = 30
, Id_dte_month_gen_june = 31
, Id_dte_month_gen_july = 32
, Id_dte_month_gen_august = 33
, Id_dte_month_gen_september = 34
, Id_dte_month_gen_october = 35
, Id_dte_month_gen_november = 36
, Id_dte_month_gen_december = 37
, Id_dte_month_abrv_jan = 38
, Id_dte_month_abrv_feb = 39
, Id_dte_month_abrv_mar = 40
, Id_dte_month_abrv_apr = 41
, Id_dte_month_abrv_may = 42
, Id_dte_month_abrv_jun = 43
, Id_dte_month_abrv_jul = 44
, Id_dte_month_abrv_aug = 45
, Id_dte_month_abrv_sep = 46
, Id_dte_month_abrv_oct = 47
, Id_dte_month_abrv_nov = 48
, Id_dte_month_abrv_dec = 49
, Id_pfunc_desc = 50
, Id_pfunc_time_error = 51
, Id_pfunc_time_too_long = 52
, Id_pfunc_rel2abs_invalid_depth = 53
, Id_pfunc_expr_stack_exhausted = 54
, Id_pfunc_expr_unexpected_number = 55
, Id_pfunc_expr_preg_match_failure = 56
, Id_pfunc_expr_unrecognised_word = 57
, Id_pfunc_expr_unexpected_operator = 58
, Id_pfunc_expr_missing_operand = 59
, Id_pfunc_expr_unexpected_closing_bracket = 60
, Id_pfunc_expr_unrecognised_punctuation = 61
, Id_pfunc_expr_unclosed_bracket = 62
, Id_pfunc_expr_division_by_zero = 63
, Id_pfunc_expr_invalid_argument = 64
, Id_pfunc_expr_invalid_argument_ln = 65
, Id_pfunc_expr_unknown_error = 66
, Id_pfunc_expr_not_a_number = 67
, Id_pfunc_string_too_long = 68
, Id_pfunc_convert_dimensionmismatch = 69
, Id_pfunc_convert_unknownunit = 70
, Id_pfunc_convert_unknowndimension = 71
, Id_pfunc_convert_invalidcompoundunit = 72
, Id_pfunc_convert_nounit = 73
, Id_pfunc_convert_doublecompoundunit = 74
, Id_toc = 75
, Id_redirectedfrom = 76
, Id_sp_allpages_hdr = 77
, Id_sp_allpages_bwd = 78
, Id_sp_allpages_fwd = 79
, Id_js_tables_collapsible_collapse = 80
, Id_js_tables_collapsible_expand = 81
, Id_js_tables_sort_descending = 82
, Id_js_tables_sort_ascending = 83
, Id_ctg_tbl_hdr = 84
, Id_portal_lastmodified = 85
, Id_file_enlarge = 86
, Id_xowa_portal_exit_text = 87
, Id_xowa_portal_exit_tooltip = 88
, Id_xowa_portal_exit_accesskey = 89
, Id_xowa_portal_view_html_text = 90
, Id_xowa_portal_view_html_tooltip = 91
, Id_xowa_portal_view_html_accesskey = 92
, Id_xowa_portal_bookmarks_text = 93
, Id_xowa_portal_bookmarks_add_text = 94
, Id_xowa_portal_bookmarks_add_tooltip = 95
, Id_xowa_portal_bookmarks_add_accesskey = 96
, Id_xowa_portal_bookmarks_show_text = 97
, Id_xowa_portal_bookmarks_show_tooltip = 98
, Id_xowa_portal_bookmarks_show_accesskey = 99
, Id_xowa_portal_page_history_text = 100
, Id_xowa_portal_page_history_tooltip = 101
, Id_xowa_portal_page_history_accesskey = 102
, Id_xowa_portal_options_text = 103
, Id_xowa_portal_options_tooltip = 104
, Id_xowa_portal_options_accesskey = 105
, Id_xowa_portal_version_text = 106
, Id_xowa_portal_build_time_text = 107
, Id_page_lang_header = 108
, Id_xowa_window_go_bwd_btn_tooltip = 109
, Id_xowa_window_go_fwd_btn_tooltip = 110
, Id_xowa_window_url_box_tooltip = 111
, Id_xowa_window_url_btn_tooltip = 112
, Id_xowa_window_search_box_tooltip = 113
, Id_xowa_window_search_btn_tooltip = 114
, Id_xowa_window_find_close_btn_tooltip = 115
, Id_xowa_window_find_box_tooltip = 116
, Id_xowa_window_find_fwd_btn_tooltip = 117
, Id_xowa_window_find_bwd_btn_tooltip = 118
, Id_xowa_window_prog_box_tooltip = 119
, Id_xowa_window_info_box_tooltip = 120
, Id_scribunto_parser_error = 121
, Id_ns_blankns = 122
, Id_ctg_page_label = 123
, Id_ctg_page_header = 124
, Id_ctg_subc_header = 125
, Id_ctg_file_header = 126
, Id_ctg_empty = 127
, Id_ctg_subc_count = 128
, Id_ctg_subc_count_limit = 129
, Id_ctg_page_count = 130
, Id_ctg_page_count_limit = 131
, Id_ctg_file_count = 132
, Id_ctg_file_count_limit = 133
, Id_ctgtree_subc_counts = 134
, Id_ctgtree_subc_counts_ctg = 135
, Id_ctgtree_subc_counts_page = 136
, Id_ctgtree_subc_counts_file = 137
, Id_next_results = 138
, Id_prev_results = 139
, Id_list_continues = 140
, Id_xowa_wikidata_languages = 141
, Id_xowa_wikidata_labels = 142
, Id_xowa_wikidata_aliasesHead = 143
, Id_xowa_wikidata_descriptions = 144
, Id_xowa_wikidata_links = 145
, Id_xowa_wikidata_claims = 146
, Id_xowa_wikidata_json = 147
, Id_xowa_wikidata_language = 148
, Id_xowa_wikidata_wiki = 149
, Id_xowa_wikidata_label = 150
, Id_xowa_wikidata_aliases = 151
, Id_xowa_wikidata_description = 152
, Id_xowa_wikidata_link = 153
, Id_xowa_wikidata_property = 154
, Id_xowa_wikidata_value = 155
, Id_xowa_wikidata_references = 156
, Id_xowa_wikidata_qualifiers = 157
, Id_search_results_header = 158
, Id_edit_toolbar_bold_sample = 159
, Id_edit_toolbar_bold_tip = 160
, Id_edit_toolbar_italic_sample = 161
, Id_edit_toolbar_italic_tip = 162
, Id_edit_toolbar_link_sample = 163
, Id_edit_toolbar_link_tip = 164
, Id_edit_toolbar_headline_sample = 165
, Id_edit_toolbar_headline_tip = 166
, Id_edit_toolbar_ulist_tip = 167
, Id_edit_toolbar_ulist_sample = 168
, Id_edit_toolbar_olist_tip = 169
, Id_edit_toolbar_olist_sample = 170
, Id_xowa_portal_about_text = 171
, Id_xowa_portal_about_tooltip = 172
, Id_xowa_portal_about_accesskey = 173
, Id_symbol_catseparator = 174
, Id_symbol_semicolon_separator = 175
, Id_symbol_comma_separator = 176
, Id_symbol_colon_separator = 177
, Id_symbol_autocomment_prefix = 178
, Id_symbol_pipe_separator = 179
, Id_symbol_word_separator = 180
, Id_symbol_ellipsis = 181
, Id_symbol_percent = 182
, Id_symbol_parentheses = 183
, Id_duration_ago = 184
, Id_xowa_wikidata_novalue = 185
, Id_xowa_wikidata_somevalue = 186
, Id_xowa_wikidata_links_wiki = 187
, Id_xowa_wikidata_links_wiktionary = 188
, Id_xowa_wikidata_links_wikisource = 189
, Id_xowa_wikidata_links_wikivoyage = 190
, Id_xowa_wikidata_links_wikiquote = 191
, Id_xowa_wikidata_links_wikibooks = 192
, Id_xowa_wikidata_links_wikiversity = 193
, Id_xowa_wikidata_links_wikinews = 194
, Id_xowa_wikidata_plus = 195
, Id_xowa_wikidata_minus = 196
, Id_xowa_wikidata_plusminus = 197
, Id_xowa_wikidata_degree = 198
, Id_xowa_wikidata_minute = 199
, Id_xowa_wikidata_second = 200
, Id_xowa_wikidata_north = 201
, Id_xowa_wikidata_south = 202
, Id_xowa_wikidata_west = 203
, Id_xowa_wikidata_east = 204
, Id_xowa_wikidata_meters = 205
, Id_xowa_wikidata_julian = 206
, Id_xowa_wikidata_year = 207
, Id_xowa_wikidata_decade = 208
, Id_xowa_wikidata_century = 209
, Id_xowa_wikidata_millenium = 210
, Id_xowa_wikidata_years1e4 = 211
, Id_xowa_wikidata_years1e5 = 212
, Id_xowa_wikidata_years1e6 = 213
, Id_xowa_wikidata_years1e7 = 214
, Id_xowa_wikidata_years1e8 = 215
, Id_xowa_wikidata_years1e9 = 216
, Id_xowa_wikidata_bc = 217
, Id_xowa_wikidata_inTime = 218
, Id_ctg_tbl_hidden = 219
, Id_ctg_help_page = 220
, Id_statistics_title = 221
, Id_statistics_header_pages = 222
, Id_statistics_articles = 223
, Id_statistics_pages = 224
, Id_statistics_pages_desc = 225
, Id_statistics_header_ns = 226
, Id_wikibase_diffview_rank = 227
, Id_xowa_wikidata_deprecated = 228
, Id_xowa_wikidata_normal = 229
, Id_xowa_wikidata_preferred = 230
, Id_xowa_wikidata_links_special = 231
, Id_xowa_window_allpages_box_tooltip = 232
, Id_xowa_window_allpages_btn_tooltip = 233
;
	public static final int Id__max = 234;
	public static Xol_msg_itm new_(int id, String key, String val) {return new_(id, Bry_.new_u8(key), Bry_.new_u8(val));}
	public static Xol_msg_itm new_(int id, byte[] key, byte[] val) {
		Xol_msg_itm rv = new Xol_msg_itm(id, key);
		update_val_(rv, val);
		return rv;
	}
	private static final    Bry_fmtr tmp_fmtr = Bry_fmtr.New__tmp().Fail_when_invalid_escapes_(false);
	private static final    Bry_bfr tmp_bfr = Bry_bfr_.Reset(255);
	public static void update_val_(Xol_msg_itm itm, byte[] val) {
		synchronized (tmp_fmtr) {	// LOCK:static-objs; DATE:2016-07-07
			boolean has_fmt_arg = tmp_fmtr.Fmt_(val).Compile().Fmt_args_exist();
			boolean has_tmpl_txt = Bry_find_.Find_fwd(val, Xop_curly_bgn_lxr.Hook, 0) != -1;
			val = trie_space.Replace(tmp_bfr, val, 0, val.length);
			itm.Atrs_set(val, has_fmt_arg, has_tmpl_txt);
		}
	}
	public static final    byte[] Bry_nbsp = Byte_.Ary_by_ints(194, 160);
	private static final    Btrie_slim_mgr trie_space = Btrie_slim_mgr.cs()	// MW:cache/MessageCache.php|get|Fix for trailing whitespace, removed by textarea|DATE:2014-04-29
		.Add_bry("&#32;"	, " ")
		.Add_bry("&nbsp;"	, Bry_nbsp)
		.Add_bry("&#160;"	, Bry_nbsp)
		;
	public static Xol_msg_itm new_(int id) {
		switch (id) {
			case Xol_msg_itm_.Id_dte_dow_name_sunday: return new_(Xol_msg_itm_.Id_dte_dow_name_sunday, "sunday", "Sunday");
			case Xol_msg_itm_.Id_dte_dow_name_monday: return new_(Xol_msg_itm_.Id_dte_dow_name_monday, "monday", "Monday");
			case Xol_msg_itm_.Id_dte_dow_name_tuesday: return new_(Xol_msg_itm_.Id_dte_dow_name_tuesday, "tuesday", "Tuesday");
			case Xol_msg_itm_.Id_dte_dow_name_wednesday: return new_(Xol_msg_itm_.Id_dte_dow_name_wednesday, "wednesday", "Wednesday");
			case Xol_msg_itm_.Id_dte_dow_name_thursday: return new_(Xol_msg_itm_.Id_dte_dow_name_thursday, "thursday", "Thursday");
			case Xol_msg_itm_.Id_dte_dow_name_friday: return new_(Xol_msg_itm_.Id_dte_dow_name_friday, "friday", "Friday");
			case Xol_msg_itm_.Id_dte_dow_name_saturday: return new_(Xol_msg_itm_.Id_dte_dow_name_saturday, "saturday", "Saturday");
			case Xol_msg_itm_.Id_dte_dow_abrv_sun: return new_(Xol_msg_itm_.Id_dte_dow_abrv_sun, "sun", "Sun");
			case Xol_msg_itm_.Id_dte_dow_abrv_mon: return new_(Xol_msg_itm_.Id_dte_dow_abrv_mon, "mon", "Mon");
			case Xol_msg_itm_.Id_dte_dow_abrv_tue: return new_(Xol_msg_itm_.Id_dte_dow_abrv_tue, "tue", "Tue");
			case Xol_msg_itm_.Id_dte_dow_abrv_wed: return new_(Xol_msg_itm_.Id_dte_dow_abrv_wed, "wed", "Wed");
			case Xol_msg_itm_.Id_dte_dow_abrv_thu: return new_(Xol_msg_itm_.Id_dte_dow_abrv_thu, "thu", "Thu");
			case Xol_msg_itm_.Id_dte_dow_abrv_fri: return new_(Xol_msg_itm_.Id_dte_dow_abrv_fri, "fri", "Fri");
			case Xol_msg_itm_.Id_dte_dow_abrv_sat: return new_(Xol_msg_itm_.Id_dte_dow_abrv_sat, "sat", "Sat");
			case Xol_msg_itm_.Id_dte_month_name_january: return new_(Xol_msg_itm_.Id_dte_month_name_january, "january", "January");
			case Xol_msg_itm_.Id_dte_month_name_february: return new_(Xol_msg_itm_.Id_dte_month_name_february, "february", "February");
			case Xol_msg_itm_.Id_dte_month_name_march: return new_(Xol_msg_itm_.Id_dte_month_name_march, "march", "March");
			case Xol_msg_itm_.Id_dte_month_name_april: return new_(Xol_msg_itm_.Id_dte_month_name_april, "april", "April");
			case Xol_msg_itm_.Id_dte_month_name_may: return new_(Xol_msg_itm_.Id_dte_month_name_may, "may_long", "May");
			case Xol_msg_itm_.Id_dte_month_name_june: return new_(Xol_msg_itm_.Id_dte_month_name_june, "june", "June");
			case Xol_msg_itm_.Id_dte_month_name_july: return new_(Xol_msg_itm_.Id_dte_month_name_july, "july", "July");
			case Xol_msg_itm_.Id_dte_month_name_august: return new_(Xol_msg_itm_.Id_dte_month_name_august, "august", "August");
			case Xol_msg_itm_.Id_dte_month_name_september: return new_(Xol_msg_itm_.Id_dte_month_name_september, "september", "September");
			case Xol_msg_itm_.Id_dte_month_name_october: return new_(Xol_msg_itm_.Id_dte_month_name_october, "october", "October");
			case Xol_msg_itm_.Id_dte_month_name_november: return new_(Xol_msg_itm_.Id_dte_month_name_november, "november", "November");
			case Xol_msg_itm_.Id_dte_month_name_december: return new_(Xol_msg_itm_.Id_dte_month_name_december, "december", "December");
			case Xol_msg_itm_.Id_dte_month_gen_january: return new_(Xol_msg_itm_.Id_dte_month_gen_january, "january-gen", "January");
			case Xol_msg_itm_.Id_dte_month_gen_february: return new_(Xol_msg_itm_.Id_dte_month_gen_february, "february-gen", "February");
			case Xol_msg_itm_.Id_dte_month_gen_march: return new_(Xol_msg_itm_.Id_dte_month_gen_march, "march-gen", "March");
			case Xol_msg_itm_.Id_dte_month_gen_april: return new_(Xol_msg_itm_.Id_dte_month_gen_april, "april-gen", "April");
			case Xol_msg_itm_.Id_dte_month_gen_may: return new_(Xol_msg_itm_.Id_dte_month_gen_may, "may-gen", "May");
			case Xol_msg_itm_.Id_dte_month_gen_june: return new_(Xol_msg_itm_.Id_dte_month_gen_june, "june-gen", "June");
			case Xol_msg_itm_.Id_dte_month_gen_july: return new_(Xol_msg_itm_.Id_dte_month_gen_july, "july-gen", "July");
			case Xol_msg_itm_.Id_dte_month_gen_august: return new_(Xol_msg_itm_.Id_dte_month_gen_august, "august-gen", "August");
			case Xol_msg_itm_.Id_dte_month_gen_september: return new_(Xol_msg_itm_.Id_dte_month_gen_september, "september-gen", "September");
			case Xol_msg_itm_.Id_dte_month_gen_october: return new_(Xol_msg_itm_.Id_dte_month_gen_october, "october-gen", "October");
			case Xol_msg_itm_.Id_dte_month_gen_november: return new_(Xol_msg_itm_.Id_dte_month_gen_november, "november-gen", "November");
			case Xol_msg_itm_.Id_dte_month_gen_december: return new_(Xol_msg_itm_.Id_dte_month_gen_december, "december-gen", "December");
			case Xol_msg_itm_.Id_dte_month_abrv_jan: return new_(Xol_msg_itm_.Id_dte_month_abrv_jan, "jan", "Jan");
			case Xol_msg_itm_.Id_dte_month_abrv_feb: return new_(Xol_msg_itm_.Id_dte_month_abrv_feb, "feb", "Feb");
			case Xol_msg_itm_.Id_dte_month_abrv_mar: return new_(Xol_msg_itm_.Id_dte_month_abrv_mar, "mar", "Mar");
			case Xol_msg_itm_.Id_dte_month_abrv_apr: return new_(Xol_msg_itm_.Id_dte_month_abrv_apr, "apr", "Apr");
			case Xol_msg_itm_.Id_dte_month_abrv_may: return new_(Xol_msg_itm_.Id_dte_month_abrv_may, "may", "May");
			case Xol_msg_itm_.Id_dte_month_abrv_jun: return new_(Xol_msg_itm_.Id_dte_month_abrv_jun, "jun", "Jun");
			case Xol_msg_itm_.Id_dte_month_abrv_jul: return new_(Xol_msg_itm_.Id_dte_month_abrv_jul, "jul", "Jul");
			case Xol_msg_itm_.Id_dte_month_abrv_aug: return new_(Xol_msg_itm_.Id_dte_month_abrv_aug, "aug", "Aug");
			case Xol_msg_itm_.Id_dte_month_abrv_sep: return new_(Xol_msg_itm_.Id_dte_month_abrv_sep, "sep", "Sep");
			case Xol_msg_itm_.Id_dte_month_abrv_oct: return new_(Xol_msg_itm_.Id_dte_month_abrv_oct, "oct", "Oct");
			case Xol_msg_itm_.Id_dte_month_abrv_nov: return new_(Xol_msg_itm_.Id_dte_month_abrv_nov, "nov", "Nov");
			case Xol_msg_itm_.Id_dte_month_abrv_dec: return new_(Xol_msg_itm_.Id_dte_month_abrv_dec, "dec", "Dec");
			case Xol_msg_itm_.Id_pfunc_desc: return new_(Xol_msg_itm_.Id_pfunc_desc, "pfunc_desc", "Enhance parser with logical functions");
			case Xol_msg_itm_.Id_pfunc_time_error: return new_(Xol_msg_itm_.Id_pfunc_time_error, "pfunc_time_error", "Error: invalid time");
			case Xol_msg_itm_.Id_pfunc_time_too_long: return new_(Xol_msg_itm_.Id_pfunc_time_too_long, "pfunc_time_too_long", "Error: too many #time calls");
			case Xol_msg_itm_.Id_pfunc_rel2abs_invalid_depth: return new_(Xol_msg_itm_.Id_pfunc_rel2abs_invalid_depth, "pfunc_rel2abs_invalid_depth", "Error: Invalid depth in path: \"~{0}\" (tried to access a node above the root node)");
			case Xol_msg_itm_.Id_pfunc_expr_stack_exhausted: return new_(Xol_msg_itm_.Id_pfunc_expr_stack_exhausted, "pfunc_expr_stack_exhausted", "Expression error: Stack exhausted");
			case Xol_msg_itm_.Id_pfunc_expr_unexpected_number: return new_(Xol_msg_itm_.Id_pfunc_expr_unexpected_number, "pfunc_expr_unexpected_number", "Expression error: Unexpected number");
			case Xol_msg_itm_.Id_pfunc_expr_preg_match_failure: return new_(Xol_msg_itm_.Id_pfunc_expr_preg_match_failure, "pfunc_expr_preg_match_failure", "Expression error: Unexpected preg_match failure");
			case Xol_msg_itm_.Id_pfunc_expr_unrecognised_word: return new_(Xol_msg_itm_.Id_pfunc_expr_unrecognised_word, "pfunc_expr_unrecognised_word", "Expression error: Unrecognised word \"~{0}\"");
			case Xol_msg_itm_.Id_pfunc_expr_unexpected_operator: return new_(Xol_msg_itm_.Id_pfunc_expr_unexpected_operator, "pfunc_expr_unexpected_operator", "Expression error: Unexpected ~{0} operator");
			case Xol_msg_itm_.Id_pfunc_expr_missing_operand: return new_(Xol_msg_itm_.Id_pfunc_expr_missing_operand, "pfunc_expr_missing_operand", "Expression error: Missing operand for ~{0}");
			case Xol_msg_itm_.Id_pfunc_expr_unexpected_closing_bracket: return new_(Xol_msg_itm_.Id_pfunc_expr_unexpected_closing_bracket, "pfunc_expr_unexpected_closing_bracket", "Expression error: Unexpected closing bracket");
			case Xol_msg_itm_.Id_pfunc_expr_unrecognised_punctuation: return new_(Xol_msg_itm_.Id_pfunc_expr_unrecognised_punctuation, "pfunc_expr_unrecognised_punctuation", "Expression error: Unrecognised punctuation character \"~{0}\"");
			case Xol_msg_itm_.Id_pfunc_expr_unclosed_bracket: return new_(Xol_msg_itm_.Id_pfunc_expr_unclosed_bracket, "pfunc_expr_unclosed_bracket", "Expression error: Unclosed bracket");
			case Xol_msg_itm_.Id_pfunc_expr_division_by_zero: return new_(Xol_msg_itm_.Id_pfunc_expr_division_by_zero, "pfunc_expr_division_by_zero", "Division by zero");
			case Xol_msg_itm_.Id_pfunc_expr_invalid_argument: return new_(Xol_msg_itm_.Id_pfunc_expr_invalid_argument, "pfunc_expr_invalid_argument", "Invalid argument for ~{0}: < -1 or > 1");
			case Xol_msg_itm_.Id_pfunc_expr_invalid_argument_ln: return new_(Xol_msg_itm_.Id_pfunc_expr_invalid_argument_ln, "pfunc_expr_invalid_argument_ln", "Invalid argument for ln: <= 0");
			case Xol_msg_itm_.Id_pfunc_expr_unknown_error: return new_(Xol_msg_itm_.Id_pfunc_expr_unknown_error, "pfunc_expr_unknown_error", "Expression error: Unknown error (~{0})");
			case Xol_msg_itm_.Id_pfunc_expr_not_a_number: return new_(Xol_msg_itm_.Id_pfunc_expr_not_a_number, "pfunc_expr_not_a_number", "In ~{0}: result is not a number");
			case Xol_msg_itm_.Id_pfunc_string_too_long: return new_(Xol_msg_itm_.Id_pfunc_string_too_long, "pfunc_string_too_long", "Error: String exceeds ~{0} character limit");
			case Xol_msg_itm_.Id_pfunc_convert_dimensionmismatch: return new_(Xol_msg_itm_.Id_pfunc_convert_dimensionmismatch, "pfunc-convert-dimensionmismatch", "Error: cannot convert between units of \"~{0}\" and \"~{1}\"");
			case Xol_msg_itm_.Id_pfunc_convert_unknownunit: return new_(Xol_msg_itm_.Id_pfunc_convert_unknownunit, "pfunc-convert-unknownunit", "Error: unknown unit \"~{0}\"");
			case Xol_msg_itm_.Id_pfunc_convert_unknowndimension: return new_(Xol_msg_itm_.Id_pfunc_convert_unknowndimension, "pfunc-convert-unknowndimension", "Error: unknown dimension \"~{0}\"");
			case Xol_msg_itm_.Id_pfunc_convert_invalidcompoundunit: return new_(Xol_msg_itm_.Id_pfunc_convert_invalidcompoundunit, "pfunc-convert-invalidcompoundunit", "Error: invalid compound unit \"~{0}\"");
			case Xol_msg_itm_.Id_pfunc_convert_nounit: return new_(Xol_msg_itm_.Id_pfunc_convert_nounit, "pfunc-convert-nounit", "Error: no source unit given");
			case Xol_msg_itm_.Id_pfunc_convert_doublecompoundunit: return new_(Xol_msg_itm_.Id_pfunc_convert_doublecompoundunit, "pfunc-convert-doublecompoundunit", "Error: cannot parse double compound units like \"~{0}\"");
			case Xol_msg_itm_.Id_toc: return new_(Xol_msg_itm_.Id_toc, "toc", "Contents");
			case Xol_msg_itm_.Id_redirectedfrom: return new_(Xol_msg_itm_.Id_redirectedfrom, "redirectedfrom", "(Redirected from ~{0})");
			case Xol_msg_itm_.Id_sp_allpages_hdr: return new_(Xol_msg_itm_.Id_sp_allpages_hdr, "allpages", "All pages");
			case Xol_msg_itm_.Id_sp_allpages_bwd: return new_(Xol_msg_itm_.Id_sp_allpages_bwd, "prevpage", "Previous page (~{0})");
			case Xol_msg_itm_.Id_sp_allpages_fwd: return new_(Xol_msg_itm_.Id_sp_allpages_fwd, "nextpage", "Next page (~{0})");
			case Xol_msg_itm_.Id_js_tables_collapsible_collapse: return new_(Xol_msg_itm_.Id_js_tables_collapsible_collapse, "collapsible-collapse", "Collapse");
			case Xol_msg_itm_.Id_js_tables_collapsible_expand: return new_(Xol_msg_itm_.Id_js_tables_collapsible_expand, "collapsible-expand", "Expand");
			case Xol_msg_itm_.Id_js_tables_sort_descending: return new_(Xol_msg_itm_.Id_js_tables_sort_descending, "sort-descending", "Sort descending");
			case Xol_msg_itm_.Id_js_tables_sort_ascending: return new_(Xol_msg_itm_.Id_js_tables_sort_ascending, "sort-ascending", "Sort ascending");
			case Xol_msg_itm_.Id_ctg_tbl_hdr: return new_(Xol_msg_itm_.Id_ctg_tbl_hdr, "categories", "Categories");
			case Xol_msg_itm_.Id_portal_lastmodified: return new_(Xol_msg_itm_.Id_portal_lastmodified, "lastmodifiedat", "This page was last modified on ~{0}, at ~{1}.");
			case Xol_msg_itm_.Id_file_enlarge: return new_(Xol_msg_itm_.Id_file_enlarge, "thumbnail-more", "Enlarge");
case Xol_msg_itm_.Id_xowa_portal_exit_text: return new_(Xol_msg_itm_.Id_xowa_portal_exit_text, "xowa-portal-exit", "Exit");
case Xol_msg_itm_.Id_xowa_portal_exit_tooltip: return new_(Xol_msg_itm_.Id_xowa_portal_exit_tooltip, "tooltip-xowa-portal-exit", "Exit XOWA");
case Xol_msg_itm_.Id_xowa_portal_exit_accesskey: return new_(Xol_msg_itm_.Id_xowa_portal_exit_accesskey, "accesskey-xowa-portal-exit", "");
case Xol_msg_itm_.Id_xowa_portal_view_html_text: return new_(Xol_msg_itm_.Id_xowa_portal_view_html_text, "xowa-portal-view_html", "View HTML");
case Xol_msg_itm_.Id_xowa_portal_view_html_tooltip: return new_(Xol_msg_itm_.Id_xowa_portal_view_html_tooltip, "tooltip-xowa-portal-view_html", "View HTML source for this page");
case Xol_msg_itm_.Id_xowa_portal_view_html_accesskey: return new_(Xol_msg_itm_.Id_xowa_portal_view_html_accesskey, "accesskey-xowa-portal-view_html", "h");
case Xol_msg_itm_.Id_xowa_portal_bookmarks_text: return new_(Xol_msg_itm_.Id_xowa_portal_bookmarks_text, "xowa-portal-bookmarks", "Bookmarks");
case Xol_msg_itm_.Id_xowa_portal_bookmarks_add_text: return new_(Xol_msg_itm_.Id_xowa_portal_bookmarks_add_text, "xowa-portal-bookmarks-add", "Bookmark this page");
case Xol_msg_itm_.Id_xowa_portal_bookmarks_add_tooltip: return new_(Xol_msg_itm_.Id_xowa_portal_bookmarks_add_tooltip, "tooltip-xowa-portal-bookmarks-add", "Bookmark this page");
case Xol_msg_itm_.Id_xowa_portal_bookmarks_add_accesskey: return new_(Xol_msg_itm_.Id_xowa_portal_bookmarks_add_accesskey, "accesskey-xowa-portal-bookmarks-add", "");
case Xol_msg_itm_.Id_xowa_portal_bookmarks_show_text: return new_(Xol_msg_itm_.Id_xowa_portal_bookmarks_show_text, "xowa-portal-bookmarks-show", "Show all bookmarks");
case Xol_msg_itm_.Id_xowa_portal_bookmarks_show_tooltip: return new_(Xol_msg_itm_.Id_xowa_portal_bookmarks_show_tooltip, "tooltip-xowa-portal-bookmarks-show", "Show all bookmarks");
case Xol_msg_itm_.Id_xowa_portal_bookmarks_show_accesskey: return new_(Xol_msg_itm_.Id_xowa_portal_bookmarks_show_accesskey, "accesskey-xowa-portal-bookmarks-show", "");
case Xol_msg_itm_.Id_xowa_portal_page_history_text: return new_(Xol_msg_itm_.Id_xowa_portal_page_history_text, "xowa-portal-page_history", "Page history");
case Xol_msg_itm_.Id_xowa_portal_page_history_tooltip: return new_(Xol_msg_itm_.Id_xowa_portal_page_history_tooltip, "tooltip-xowa-portal-page_history", "View history of opened pages");
case Xol_msg_itm_.Id_xowa_portal_page_history_accesskey: return new_(Xol_msg_itm_.Id_xowa_portal_page_history_accesskey, "accesskey-xowa-portal-page_history", "");
case Xol_msg_itm_.Id_xowa_portal_options_text: return new_(Xol_msg_itm_.Id_xowa_portal_options_text, "xowa-portal-options", "Options");
case Xol_msg_itm_.Id_xowa_portal_options_tooltip: return new_(Xol_msg_itm_.Id_xowa_portal_options_tooltip, "tooltip-xowa-portal-options", "Change XOWA options");
case Xol_msg_itm_.Id_xowa_portal_options_accesskey: return new_(Xol_msg_itm_.Id_xowa_portal_options_accesskey, "accesskey-xowa-portal-options", "");
case Xol_msg_itm_.Id_xowa_portal_version_text: return new_(Xol_msg_itm_.Id_xowa_portal_version_text, "xowa-portal-version", "version");
case Xol_msg_itm_.Id_xowa_portal_build_time_text: return new_(Xol_msg_itm_.Id_xowa_portal_build_time_text, "xowa-portal-build_time", "build time");
case Xol_msg_itm_.Id_page_lang_header: return new_(Xol_msg_itm_.Id_page_lang_header, "otherlanguages", "In other languages");
case Xol_msg_itm_.Id_xowa_window_go_bwd_btn_tooltip: return new_(Xol_msg_itm_.Id_xowa_window_go_bwd_btn_tooltip, "xowa-window-go_bwd_btn-tooltip", "Go back one page");
case Xol_msg_itm_.Id_xowa_window_go_fwd_btn_tooltip: return new_(Xol_msg_itm_.Id_xowa_window_go_fwd_btn_tooltip, "xowa-window-go_fwd_btn-tooltip", "Go forward one page");
case Xol_msg_itm_.Id_xowa_window_url_box_tooltip: return new_(Xol_msg_itm_.Id_xowa_window_url_box_tooltip, "xowa-window-url_box-tooltip", "Enter address");
case Xol_msg_itm_.Id_xowa_window_url_btn_tooltip: return new_(Xol_msg_itm_.Id_xowa_window_url_btn_tooltip, "xowa-window-url_btn-tooltip", "Go to the address in the Location Bar");
case Xol_msg_itm_.Id_xowa_window_search_box_tooltip: return new_(Xol_msg_itm_.Id_xowa_window_search_box_tooltip, "xowa-window-search_box-tooltip", "Search using {{ns:Special}}:XowaSearch");
case Xol_msg_itm_.Id_xowa_window_search_btn_tooltip: return new_(Xol_msg_itm_.Id_xowa_window_search_btn_tooltip, "xowa-window-search_btn-tooltip", "Search");
case Xol_msg_itm_.Id_xowa_window_allpages_box_tooltip: return new_(Xol_msg_itm_.Id_xowa_window_allpages_box_tooltip, "xowa-window-allpages_box-tooltip", "List using {{ns:Special}}:AllPages");
case Xol_msg_itm_.Id_xowa_window_allpages_btn_tooltip: return new_(Xol_msg_itm_.Id_xowa_window_allpages_btn_tooltip, "xowa-window-allpages_btn-tooltip", "List all pages");
case Xol_msg_itm_.Id_xowa_window_find_close_btn_tooltip: return new_(Xol_msg_itm_.Id_xowa_window_find_close_btn_tooltip, "xowa-window-find_close_btn-tooltip", "Close Find bar");
case Xol_msg_itm_.Id_xowa_window_find_box_tooltip: return new_(Xol_msg_itm_.Id_xowa_window_find_box_tooltip, "xowa-window-find_box-tooltip", "Enter phrase to find on this page");
case Xol_msg_itm_.Id_xowa_window_find_fwd_btn_tooltip: return new_(Xol_msg_itm_.Id_xowa_window_find_fwd_btn_tooltip, "xowa-window-find_fwd_btn-tooltip", "Find the next occurrence of the phrase");
case Xol_msg_itm_.Id_xowa_window_find_bwd_btn_tooltip: return new_(Xol_msg_itm_.Id_xowa_window_find_bwd_btn_tooltip, "xowa-window-find_bwd_btn-tooltip", "Find the previous occurrence of the phrase");
case Xol_msg_itm_.Id_xowa_window_prog_box_tooltip: return new_(Xol_msg_itm_.Id_xowa_window_prog_box_tooltip, "xowa-window-prog_box-tooltip", "Displays progress messages");
case Xol_msg_itm_.Id_xowa_window_info_box_tooltip: return new_(Xol_msg_itm_.Id_xowa_window_info_box_tooltip, "xowa-window-info_box-tooltip", "Displays system messages");
case Xol_msg_itm_.Id_scribunto_parser_error: return new_(Xol_msg_itm_.Id_scribunto_parser_error, "scribunto-parser-error", "Script error");
case Xol_msg_itm_.Id_ns_blankns: return new_(Xol_msg_itm_.Id_ns_blankns, "blanknamespace", "(Main)");
case Xol_msg_itm_.Id_ctg_page_label: return new_(Xol_msg_itm_.Id_ctg_page_label, "pagecategories", "{{PLURAL:~{0}|Category|Categories}}");
case Xol_msg_itm_.Id_ctg_page_header: return new_(Xol_msg_itm_.Id_ctg_page_header, "category_header", "Pages in category \"~{0}\"");
case Xol_msg_itm_.Id_ctg_subc_header: return new_(Xol_msg_itm_.Id_ctg_subc_header, "subcategories", "Subcategories");
case Xol_msg_itm_.Id_ctg_file_header: return new_(Xol_msg_itm_.Id_ctg_file_header, "category-media-header", "Media in category \"~{0}\"");
case Xol_msg_itm_.Id_ctg_empty: return new_(Xol_msg_itm_.Id_ctg_empty, "category-empty", "''This category currently contains no pages or media.''");
case Xol_msg_itm_.Id_ctg_subc_count: return new_(Xol_msg_itm_.Id_ctg_subc_count, "category-subcat-count", "{{PLURAL:~{1}|This category has only the following subcategory.|This category has the following {{PLURAL:~{0}|subcategory|~{0} subcategories}}, out of ~{1} total.}}");
case Xol_msg_itm_.Id_ctg_subc_count_limit: return new_(Xol_msg_itm_.Id_ctg_subc_count_limit, "category-subcat-count-limited", "This category has the following {{PLURAL:~{0}|subcategory|~{0} subcategories}}.");
case Xol_msg_itm_.Id_ctg_page_count: return new_(Xol_msg_itm_.Id_ctg_page_count, "category-article-count", "{{PLURAL:~{1}|This category contains only the following page.|The following {{PLURAL:~{0}|page is|~{0} pages are}} in this category, out of ~{1} total.}}");
case Xol_msg_itm_.Id_ctg_page_count_limit: return new_(Xol_msg_itm_.Id_ctg_page_count_limit, "category-article-count-limited", "The following {{PLURAL:~{0}|page is|~{0} pages are}} in the current category.");
case Xol_msg_itm_.Id_ctg_file_count: return new_(Xol_msg_itm_.Id_ctg_file_count, "category-file-count", "{{PLURAL:~{1}|This category contains only the following file.|The following {{PLURAL:~{0}|file is|~{0} files are}} in this category, out of ~{1} total.}}");
case Xol_msg_itm_.Id_ctg_file_count_limit: return new_(Xol_msg_itm_.Id_ctg_file_count_limit, "category-file-count-limited", "The following {{PLURAL:~{0}|file is|~{0} files are}} in the current category.");
case Xol_msg_itm_.Id_ctgtree_subc_counts: return new_(Xol_msg_itm_.Id_ctgtree_subc_counts, "categorytree-member-counts", "contains {{PLURAL:~{0}|1 subcategory|~{0} subcategories}}, {{PLURAL:~{1}|1 page|~{1} pages}}, and {{PLURAL:~{2}|1 file|~{2} files}}");
case Xol_msg_itm_.Id_ctgtree_subc_counts_ctg: return new_(Xol_msg_itm_.Id_ctgtree_subc_counts_ctg, "categorytree-num-categories", "~{0} C");
case Xol_msg_itm_.Id_ctgtree_subc_counts_page: return new_(Xol_msg_itm_.Id_ctgtree_subc_counts_page, "categorytree-num-pages", "~{0} P");
case Xol_msg_itm_.Id_ctgtree_subc_counts_file: return new_(Xol_msg_itm_.Id_ctgtree_subc_counts_file, "categorytree-num-files", "~{0} F");
case Xol_msg_itm_.Id_prev_results: return new_(Xol_msg_itm_.Id_next_results, "prevn", "previous {{PLURAL:~{0}|~{0}}}");
case Xol_msg_itm_.Id_next_results: return new_(Xol_msg_itm_.Id_prev_results, "nextn", "next {{PLURAL:~{0}|~{0}}}");
case Xol_msg_itm_.Id_list_continues: return new_(Xol_msg_itm_.Id_list_continues, "listingcontinuesabbrev", "cont.");
case Xol_msg_itm_.Id_xowa_wikidata_languages: return new_(Xol_msg_itm_.Id_xowa_wikidata_languages, "xowa-wikidata-languages", "en");
case Xol_msg_itm_.Id_xowa_wikidata_labels: return new_(Xol_msg_itm_.Id_xowa_wikidata_labels, "xowa-wikidata-labels", "Labels");
case Xol_msg_itm_.Id_xowa_wikidata_aliasesHead: return new_(Xol_msg_itm_.Id_xowa_wikidata_aliasesHead, "xowa-wikidata-aliasesHead", "Aliases");
case Xol_msg_itm_.Id_xowa_wikidata_descriptions: return new_(Xol_msg_itm_.Id_xowa_wikidata_descriptions, "xowa-wikidata-descriptions", "Descriptions");
case Xol_msg_itm_.Id_xowa_wikidata_links: return new_(Xol_msg_itm_.Id_xowa_wikidata_links, "xowa-wikidata-links", "Links");
case Xol_msg_itm_.Id_xowa_wikidata_claims: return new_(Xol_msg_itm_.Id_xowa_wikidata_claims, "xowa-wikidata-claims", "Claims");
case Xol_msg_itm_.Id_xowa_wikidata_json: return new_(Xol_msg_itm_.Id_xowa_wikidata_json, "xowa-wikidata-json", "JSON");
case Xol_msg_itm_.Id_xowa_wikidata_language: return new_(Xol_msg_itm_.Id_xowa_wikidata_language, "xowa-wikidata-language", "language");
case Xol_msg_itm_.Id_xowa_wikidata_wiki: return new_(Xol_msg_itm_.Id_xowa_wikidata_wiki, "xowa-wikidata-wiki", "wiki");
case Xol_msg_itm_.Id_xowa_wikidata_label: return new_(Xol_msg_itm_.Id_xowa_wikidata_label, "xowa-wikidata-label", "label");
case Xol_msg_itm_.Id_xowa_wikidata_aliases: return new_(Xol_msg_itm_.Id_xowa_wikidata_aliases, "xowa-wikidata-aliases", "aliases");
case Xol_msg_itm_.Id_xowa_wikidata_description: return new_(Xol_msg_itm_.Id_xowa_wikidata_description, "xowa-wikidata-description", "description");
case Xol_msg_itm_.Id_xowa_wikidata_link: return new_(Xol_msg_itm_.Id_xowa_wikidata_link, "xowa-wikidata-link", "link");
case Xol_msg_itm_.Id_xowa_wikidata_property: return new_(Xol_msg_itm_.Id_xowa_wikidata_property, "xowa-wikidata-property", "property");
case Xol_msg_itm_.Id_xowa_wikidata_value: return new_(Xol_msg_itm_.Id_xowa_wikidata_value, "xowa-wikidata-value", "value");
case Xol_msg_itm_.Id_xowa_wikidata_references: return new_(Xol_msg_itm_.Id_xowa_wikidata_references, "xowa-wikidata-references", "references");
case Xol_msg_itm_.Id_xowa_wikidata_qualifiers: return new_(Xol_msg_itm_.Id_xowa_wikidata_qualifiers, "xowa-wikidata-qualifiers", "qualifiers");
case Xol_msg_itm_.Id_search_results_header: return new_(Xol_msg_itm_.Id_search_results_header, "showingresultsheader", "{{PLURAL:~{4}\u007CResult '''~{0}''' of '''~{2}'''\u007CResults '''~{0} - ~{1}''' of '''~{2}'''}} for '''~{3}'''");
case Xol_msg_itm_.Id_edit_toolbar_bold_sample: return new_(Xol_msg_itm_.Id_edit_toolbar_bold_sample, "bold_sample", "Bold text");
case Xol_msg_itm_.Id_edit_toolbar_bold_tip: return new_(Xol_msg_itm_.Id_edit_toolbar_bold_tip, "bold_tip", "Bold text");
case Xol_msg_itm_.Id_edit_toolbar_italic_sample: return new_(Xol_msg_itm_.Id_edit_toolbar_italic_sample, "italic_sample", "Italic text");
case Xol_msg_itm_.Id_edit_toolbar_italic_tip: return new_(Xol_msg_itm_.Id_edit_toolbar_italic_tip, "italic_tip", "Italic text");
case Xol_msg_itm_.Id_edit_toolbar_link_sample: return new_(Xol_msg_itm_.Id_edit_toolbar_link_sample, "link_sample", "Link title");
case Xol_msg_itm_.Id_edit_toolbar_link_tip: return new_(Xol_msg_itm_.Id_edit_toolbar_link_tip, "link_tip", "Internal link");
case Xol_msg_itm_.Id_edit_toolbar_headline_sample: return new_(Xol_msg_itm_.Id_edit_toolbar_headline_sample, "headline_sample", "Headline text");
case Xol_msg_itm_.Id_edit_toolbar_headline_tip: return new_(Xol_msg_itm_.Id_edit_toolbar_headline_tip, "headline_tip", "Level 2 headline");
case Xol_msg_itm_.Id_edit_toolbar_ulist_tip: return new_(Xol_msg_itm_.Id_edit_toolbar_ulist_tip, "wikieditor-toolbar-tool-ulist", "Bulleted list");
case Xol_msg_itm_.Id_edit_toolbar_ulist_sample: return new_(Xol_msg_itm_.Id_edit_toolbar_ulist_sample, "wikieditor-toolbar-tool-ulist-example", "Bulleted list item");
case Xol_msg_itm_.Id_edit_toolbar_olist_tip: return new_(Xol_msg_itm_.Id_edit_toolbar_olist_tip, "wikieditor-toolbar-tool-olist", "Numbered list");
case Xol_msg_itm_.Id_edit_toolbar_olist_sample: return new_(Xol_msg_itm_.Id_edit_toolbar_olist_sample, "wikieditor-toolbar-tool-olist-example", "Numbered list item");
case Xol_msg_itm_.Id_xowa_portal_about_text: return new_(Xol_msg_itm_.Id_xowa_portal_about_text, "xowa-portal-about", "About");
case Xol_msg_itm_.Id_xowa_portal_about_tooltip: return new_(Xol_msg_itm_.Id_xowa_portal_about_tooltip, "tooltip-xowa-portal-about", "About XOWA");
case Xol_msg_itm_.Id_xowa_portal_about_accesskey: return new_(Xol_msg_itm_.Id_xowa_portal_about_accesskey, "accesskey-xowa-portal-about", "");
case Xol_msg_itm_.Id_symbol_catseparator: return new_(Xol_msg_itm_.Id_symbol_catseparator, "catseparator", "\u007C");
case Xol_msg_itm_.Id_symbol_semicolon_separator: return new_(Xol_msg_itm_.Id_symbol_semicolon_separator, "semicolon-separator", ";&#32;");
case Xol_msg_itm_.Id_symbol_comma_separator: return new_(Xol_msg_itm_.Id_symbol_comma_separator, "comma-separator", ",&#32;");
case Xol_msg_itm_.Id_symbol_colon_separator: return new_(Xol_msg_itm_.Id_symbol_colon_separator, "colon-separator", ":&#32;");
case Xol_msg_itm_.Id_symbol_autocomment_prefix: return new_(Xol_msg_itm_.Id_symbol_autocomment_prefix, "autocomment-prefix", "-&#32;");
case Xol_msg_itm_.Id_symbol_pipe_separator: return new_(Xol_msg_itm_.Id_symbol_pipe_separator, "pipe-separator", "&#32;\u007C&#32;");
case Xol_msg_itm_.Id_symbol_word_separator: return new_(Xol_msg_itm_.Id_symbol_word_separator, "word-separator", "&#32;");
case Xol_msg_itm_.Id_symbol_ellipsis: return new_(Xol_msg_itm_.Id_symbol_ellipsis, "ellipsis", "...");
case Xol_msg_itm_.Id_symbol_percent: return new_(Xol_msg_itm_.Id_symbol_percent, "percent", "~{0}%");
case Xol_msg_itm_.Id_symbol_parentheses: return new_(Xol_msg_itm_.Id_symbol_parentheses, "parentheses", "(~{0})");
case Xol_msg_itm_.Id_duration_ago: return new_(Xol_msg_itm_.Id_duration_ago, "ago", "~{0} ago");
case Xol_msg_itm_.Id_xowa_wikidata_novalue: return new_(Xol_msg_itm_.Id_xowa_wikidata_novalue, "xowa-wikidata-novalue", "—");
case Xol_msg_itm_.Id_xowa_wikidata_somevalue: return new_(Xol_msg_itm_.Id_xowa_wikidata_somevalue, "xowa-wikidata-somevalue", "?");
case Xol_msg_itm_.Id_xowa_wikidata_links_wiki: return new_(Xol_msg_itm_.Id_xowa_wikidata_links_wiki, "xowa-wikidata-links-wiki", "Links (Wikipedia)");
case Xol_msg_itm_.Id_xowa_wikidata_links_wiktionary: return new_(Xol_msg_itm_.Id_xowa_wikidata_links_wiktionary, "xowa-wikidata-links-wiktionary", "Links (Wiktionary)");
case Xol_msg_itm_.Id_xowa_wikidata_links_wikisource: return new_(Xol_msg_itm_.Id_xowa_wikidata_links_wikisource, "xowa-wikidata-links-wikisource", "Links (Wikisource)");
case Xol_msg_itm_.Id_xowa_wikidata_links_wikivoyage: return new_(Xol_msg_itm_.Id_xowa_wikidata_links_wikivoyage, "xowa-wikidata-links-wikivoyage", "Links (Wikivoyage)");
case Xol_msg_itm_.Id_xowa_wikidata_links_wikiquote: return new_(Xol_msg_itm_.Id_xowa_wikidata_links_wikiquote, "xowa-wikidata-links-wikiquote", "Links (Wikiquote)");
case Xol_msg_itm_.Id_xowa_wikidata_links_wikibooks: return new_(Xol_msg_itm_.Id_xowa_wikidata_links_wikibooks, "xowa-wikidata-links-wikibooks", "Links (Wikibooks)");
case Xol_msg_itm_.Id_xowa_wikidata_links_wikiversity: return new_(Xol_msg_itm_.Id_xowa_wikidata_links_wikiversity, "xowa-wikidata-links-wikiversity", "Links (Wikiversity)");
case Xol_msg_itm_.Id_xowa_wikidata_links_wikinews: return new_(Xol_msg_itm_.Id_xowa_wikidata_links_wikinews, "xowa-wikidata-links-wikinews", "Links (Wikinews)");
case Xol_msg_itm_.Id_xowa_wikidata_plus: return new_(Xol_msg_itm_.Id_xowa_wikidata_plus, "xowa-wikidata-plus", "+");
case Xol_msg_itm_.Id_xowa_wikidata_minus: return new_(Xol_msg_itm_.Id_xowa_wikidata_minus, "xowa-wikidata-minus", "−");
case Xol_msg_itm_.Id_xowa_wikidata_plusminus: return new_(Xol_msg_itm_.Id_xowa_wikidata_plusminus, "xowa-wikidata-plusminus", "±");
case Xol_msg_itm_.Id_xowa_wikidata_degree: return new_(Xol_msg_itm_.Id_xowa_wikidata_degree, "xowa-wikidata-degree", "°");
case Xol_msg_itm_.Id_xowa_wikidata_minute: return new_(Xol_msg_itm_.Id_xowa_wikidata_minute, "xowa-wikidata-minute", "′");
case Xol_msg_itm_.Id_xowa_wikidata_second: return new_(Xol_msg_itm_.Id_xowa_wikidata_second, "xowa-wikidata-second", "″");
case Xol_msg_itm_.Id_xowa_wikidata_north: return new_(Xol_msg_itm_.Id_xowa_wikidata_north, "xowa-wikidata-north", "N");
case Xol_msg_itm_.Id_xowa_wikidata_south: return new_(Xol_msg_itm_.Id_xowa_wikidata_south, "xowa-wikidata-south", "S");
case Xol_msg_itm_.Id_xowa_wikidata_west: return new_(Xol_msg_itm_.Id_xowa_wikidata_west, "xowa-wikidata-west", "W");
case Xol_msg_itm_.Id_xowa_wikidata_east: return new_(Xol_msg_itm_.Id_xowa_wikidata_east, "xowa-wikidata-east", "E");
case Xol_msg_itm_.Id_xowa_wikidata_meters: return new_(Xol_msg_itm_.Id_xowa_wikidata_meters, "xowa-wikidata-meters", "&nbsp;m");
case Xol_msg_itm_.Id_xowa_wikidata_julian: return new_(Xol_msg_itm_.Id_xowa_wikidata_julian, "xowa-wikidata-julian", "<sup>jul</sup>");
case Xol_msg_itm_.Id_xowa_wikidata_year: return new_(Xol_msg_itm_.Id_xowa_wikidata_year, "xowa-wikidata-year", "~{0}");
case Xol_msg_itm_.Id_xowa_wikidata_decade: return new_(Xol_msg_itm_.Id_xowa_wikidata_decade, "xowa-wikidata-decade", "~{0}0s");
case Xol_msg_itm_.Id_xowa_wikidata_century: return new_(Xol_msg_itm_.Id_xowa_wikidata_century, "xowa-wikidata-century", "~{0}. century");
case Xol_msg_itm_.Id_xowa_wikidata_millenium: return new_(Xol_msg_itm_.Id_xowa_wikidata_millenium, "xowa-wikidata-millenium", "~{0}. millenium");
case Xol_msg_itm_.Id_xowa_wikidata_years1e4: return new_(Xol_msg_itm_.Id_xowa_wikidata_years1e4, "xowa-wikidata-years1e4", "~{0}0,000 years");
case Xol_msg_itm_.Id_xowa_wikidata_years1e5: return new_(Xol_msg_itm_.Id_xowa_wikidata_years1e5, "xowa-wikidata-years1e5", "~{0}00,000 years");
case Xol_msg_itm_.Id_xowa_wikidata_years1e6: return new_(Xol_msg_itm_.Id_xowa_wikidata_years1e6, "xowa-wikidata-years1e6", "~{0} million years");
case Xol_msg_itm_.Id_xowa_wikidata_years1e7: return new_(Xol_msg_itm_.Id_xowa_wikidata_years1e7, "xowa-wikidata-years1e7", "~{0}0 million years");
case Xol_msg_itm_.Id_xowa_wikidata_years1e8: return new_(Xol_msg_itm_.Id_xowa_wikidata_years1e8, "xowa-wikidata-years1e8", "~{0}00 million years");
case Xol_msg_itm_.Id_xowa_wikidata_years1e9: return new_(Xol_msg_itm_.Id_xowa_wikidata_years1e9, "xowa-wikidata-years1e9", "~{0} billion years");
case Xol_msg_itm_.Id_xowa_wikidata_bc: return new_(Xol_msg_itm_.Id_xowa_wikidata_bc, "xowa-wikidata-bc", "~{0} BC");
case Xol_msg_itm_.Id_xowa_wikidata_inTime: return new_(Xol_msg_itm_.Id_xowa_wikidata_inTime, "xowa-wikidata-inTime", "in ~{0}");
case Xol_msg_itm_.Id_ctg_tbl_hidden: return new_(Xol_msg_itm_.Id_ctg_tbl_hidden, "hidden-category-category", "Hidden categories");
case Xol_msg_itm_.Id_ctg_help_page: return new_(Xol_msg_itm_.Id_ctg_help_page, "pagecategorieslink", "Special:Categories");
case Xol_msg_itm_.Id_statistics_title: return new_(Xol_msg_itm_.Id_statistics_title, "statistics", "Statistics");
case Xol_msg_itm_.Id_statistics_header_pages: return new_(Xol_msg_itm_.Id_statistics_header_pages, "statistics-header-pages", "Page statistics");
case Xol_msg_itm_.Id_statistics_articles: return new_(Xol_msg_itm_.Id_statistics_articles, "statistics-articles", "Content pages");
case Xol_msg_itm_.Id_statistics_pages: return new_(Xol_msg_itm_.Id_statistics_pages, "statistics-pages", "Pages");
case Xol_msg_itm_.Id_statistics_pages_desc: return new_(Xol_msg_itm_.Id_statistics_pages_desc, "statistics-pages-desc", "All pages in the wiki, including talk pages, redirects, etc.");
case Xol_msg_itm_.Id_statistics_header_ns: return new_(Xol_msg_itm_.Id_statistics_header_ns, "statistics-header-ns", "Namespace statistics");
case Xol_msg_itm_.Id_wikibase_diffview_rank: return new_(Xol_msg_itm_.Id_wikibase_diffview_rank, "Wikibase-diffview-rank", "rank");
case Xol_msg_itm_.Id_xowa_wikidata_deprecated: return new_(Xol_msg_itm_.Id_xowa_wikidata_deprecated, "xowa-wikidata-deprecated", "deprecated");
case Xol_msg_itm_.Id_xowa_wikidata_normal: return new_(Xol_msg_itm_.Id_xowa_wikidata_normal, "xowa-wikidata-normal", "normal");
case Xol_msg_itm_.Id_xowa_wikidata_preferred: return new_(Xol_msg_itm_.Id_xowa_wikidata_preferred, "xowa-wikidata-preferred", "preferred");
case Xol_msg_itm_.Id_xowa_wikidata_links_special: return new_(Xol_msg_itm_.Id_xowa_wikidata_links_special, "xowa-wikidata-links-special", "Links (special wikis)");
			default: throw Err_.new_unhandled(id);
		}
	}
	public static byte[] eval_(Bry_bfr bfr, Xol_msg_itm tmp_msg_itm, byte[] val, Object... args) {
		synchronized (tmp_fmtr) {	// LOCK:static-objs; DATE:2016-07-07
			val = gplx.xowa.apps.gfs.Gfs_php_converter.To_gfs(bfr, val);
			update_val_(tmp_msg_itm, val);
			return tmp_fmtr.Bld_bry_many(bfr, args);
		}
	}
}
