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
package gplx.xowa.xtns.pfuncs; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import gplx.xowa.xtns.pfuncs.ifs.*; import gplx.xowa.xtns.pfuncs.times.*; import gplx.xowa.xtns.pfuncs.numbers.*; import gplx.xowa.xtns.pfuncs.ttls.*; import gplx.xowa.xtns.pfuncs.langs.*; import gplx.xowa.xtns.pfuncs.strings.*; import gplx.xowa.xtns.pfuncs.pages.*; import gplx.xowa.xtns.pfuncs.wikis.*;
public class Pf_func_ {
	public static byte[] Eval_arg_or_empty(Xop_ctx ctx, byte[] src, Xot_invk caller, Xot_invk self, int self_args_len, int i) {return Eval_arg_or(ctx, src, caller, self, self_args_len, i, Bry_.Empty);}
	public static final byte Name_dlm = Byte_ascii.Colon;
	public static byte[] Eval_arg_or(Xop_ctx ctx, byte[] src, Xot_invk caller, Xot_invk self, int self_args_len, int i, byte[] or) {
		if (i >= self_args_len) return or;
		Bry_bfr bfr = Bry_bfr.new_();
		Arg_nde_tkn nde = self.Args_get_by_idx(i);
		nde.Key_tkn().Tmpl_evaluate(ctx, src, caller, bfr);	// NOTE: must add key b/c parser functions do not have keys and some usages pass in xml_tkns; EX: {{#if|<a href='{{{1}}}'|}}; "<a href" should not be interpreted as key
		if (nde.KeyTkn_exists()) bfr.Add_byte(Byte_ascii.Eq);
		nde.Val_tkn().Tmpl_evaluate(ctx, src, caller, bfr);
		return bfr.Xto_bry_and_clear_and_trim();
	}
	public static byte[] Eval_val_or(Xop_ctx ctx, byte[] src, Xot_invk caller, Xot_invk self, int self_args_len, int i, byte[] or) {
		if (i >= self_args_len) return or;
		Bry_bfr bfr = Bry_bfr.new_();
		Arg_nde_tkn nde = self.Args_get_by_idx(i);
		nde.Val_tkn().Tmpl_evaluate(ctx, src, caller, bfr);
		return bfr.Xto_bry_and_clear_and_trim();
	}
	public static byte[] Eval_tkn(Bry_bfr bfr, Xop_ctx ctx, byte[] src, Xot_invk caller, Xop_tkn_itm tkn) {
		tkn.Tmpl_evaluate(ctx, src, caller, bfr);
		return bfr.Xto_bry_and_clear();
	}
	private static final Number_parser lhs_parser = new Number_parser().Hex_enabled_(true), rhs_parser = new Number_parser().Hex_enabled_(true);
	public static boolean Eq_(byte[] lhs, byte[] rhs) {	// PATCH.PHP: php allows "003" == "3.0"; ASSUME: numbers are either int or int-like decimal; long, float, decimal not supported
		int lhs_len = lhs.length, rhs_len = rhs.length;
		boolean rv = true;
		if (lhs_len == rhs_len) {
			for (int i = 0; i < lhs_len; i++) {
				if (lhs[i] != rhs[i]) {
					rv = false;
					break;
				}
			}
			if (rv) return true;
		}
		else if (lhs_len == 0 || rhs_len == 0)	// one side is empty String and the other side is String; return false;
			return false;
		lhs_parser.Parse(lhs, 0, lhs_len);
		if (lhs_parser.Has_err()) return false;
		rhs_parser.Parse(rhs, 0, rhs_len);
		if (rhs_parser.Has_err()) return false;
		return lhs_parser.Has_frac() || rhs_parser.Has_frac() ? lhs_parser.Rv_as_dec().Eq(rhs_parser.Rv_as_dec()) : lhs_parser.Rv_as_int() == rhs_parser.Rv_as_int();
	}
	public static void Reg(gplx.xowa.langs.Xol_func_name_regy func_regy, Xol_lang lang) {
		Xol_kwd_mgr kwd_mgr = lang.Kwd_mgr();
		int len = Ary__.length;
		for (int i = 0; i < len; i++) {
			int id = Ary__[i];
			func_regy.Reg_defn(kwd_mgr, id, Get_prototype(id));
		}
	}
	public static final int[] Ary__ = new int[]
	{	Xol_kwd_grp_.Id_utc_year
	,	Xol_kwd_grp_.Id_utc_month_int_len2
	,	Xol_kwd_grp_.Id_utc_month_int
	,	Xol_kwd_grp_.Id_utc_day_int_len2
	,	Xol_kwd_grp_.Id_utc_day_int
	,	Xol_kwd_grp_.Id_utc_hour
	,	Xol_kwd_grp_.Id_utc_time
	,	Xol_kwd_grp_.Id_utc_timestamp
	,	Xol_kwd_grp_.Id_utc_week
	,	Xol_kwd_grp_.Id_utc_dow
	,	Xol_kwd_grp_.Id_utc_month_abrv
	,	Xol_kwd_grp_.Id_utc_month_name
	,	Xol_kwd_grp_.Id_utc_month_gen
	,	Xol_kwd_grp_.Id_utc_day_name
	,	Xol_kwd_grp_.Id_lcl_year
	,	Xol_kwd_grp_.Id_lcl_month_int_len2
	,	Xol_kwd_grp_.Id_lcl_month_int
	,	Xol_kwd_grp_.Id_lcl_day_int_len2
	,	Xol_kwd_grp_.Id_lcl_day_int
	,	Xol_kwd_grp_.Id_lcl_hour
	,	Xol_kwd_grp_.Id_lcl_time
	,	Xol_kwd_grp_.Id_lcl_timestamp
	,	Xol_kwd_grp_.Id_lcl_week
	,	Xol_kwd_grp_.Id_lcl_dow
	,	Xol_kwd_grp_.Id_lcl_month_abrv
	,	Xol_kwd_grp_.Id_lcl_month_name
	,	Xol_kwd_grp_.Id_lcl_month_gen
	,	Xol_kwd_grp_.Id_lcl_day_name
	,	Xol_kwd_grp_.Id_rev_year
	,	Xol_kwd_grp_.Id_rev_month_int_len2
	,	Xol_kwd_grp_.Id_rev_month_int
	,	Xol_kwd_grp_.Id_rev_day_int_len2
	,	Xol_kwd_grp_.Id_rev_day_int
	,	Xol_kwd_grp_.Id_rev_timestamp
	,	Xol_kwd_grp_.Id_ns_txt
	,	Xol_kwd_grp_.Id_ns_url
	,	Xol_kwd_grp_.Id_ns_subj_txt
	,	Xol_kwd_grp_.Id_ns_subj_url
	,	Xol_kwd_grp_.Id_ns_talk_txt
	,	Xol_kwd_grp_.Id_ns_talk_url
	,	Xol_kwd_grp_.Id_ttl_full_txt
	,	Xol_kwd_grp_.Id_ttl_full_url
	,	Xol_kwd_grp_.Id_ttl_page_txt
	,	Xol_kwd_grp_.Id_ttl_base_txt
	,	Xol_kwd_grp_.Id_ttl_page_url
	,	Xol_kwd_grp_.Id_ttl_base_url
	,	Xol_kwd_grp_.Id_ttl_leaf_txt
	,	Xol_kwd_grp_.Id_ttl_leaf_url
	,	Xol_kwd_grp_.Id_ttl_subj_txt
	,	Xol_kwd_grp_.Id_ttl_subj_url
	,	Xol_kwd_grp_.Id_ttl_talk_txt
	,	Xol_kwd_grp_.Id_ttl_talk_url
	,	Xol_kwd_grp_.Id_site_sitename
	,	Xol_kwd_grp_.Id_site_servername
	,	Xol_kwd_grp_.Id_site_server
	,	Xol_kwd_grp_.Id_site_articlepath
	,	Xol_kwd_grp_.Id_site_scriptpath
	,	Xol_kwd_grp_.Id_site_stylepath
	,	Xol_kwd_grp_.Id_site_contentlanguage
	,	Xol_kwd_grp_.Id_site_directionmark
	,	Xol_kwd_grp_.Id_site_currentversion
	,	Xol_kwd_grp_.Id_num_pages
	,	Xol_kwd_grp_.Id_num_articles
	,	Xol_kwd_grp_.Id_num_files
	,	Xol_kwd_grp_.Id_num_edits
	,	Xol_kwd_grp_.Id_num_views
	,	Xol_kwd_grp_.Id_num_users
	,	Xol_kwd_grp_.Id_num_users_active
	,	Xol_kwd_grp_.Id_num_admins
	,	Xol_kwd_grp_.Id_rev_id
	,	Xol_kwd_grp_.Id_rev_pagesize
	,	Xol_kwd_grp_.Id_rev_user
	,	Xol_kwd_grp_.Id_rev_protectionlevel
	,	Xol_kwd_grp_.Id_page_displaytitle
	,	Xol_kwd_grp_.Id_page_defaultsort
	,	Xol_kwd_grp_.Id_noeditsection
	,	Xol_kwd_grp_.Id_site_pagesincategory
	,	Xol_kwd_grp_.Id_url_ns
	,	Xol_kwd_grp_.Id_url_nse
	,	Xol_kwd_grp_.Id_url_urlencode
	,	Xol_kwd_grp_.Id_str_lc
	,	Xol_kwd_grp_.Id_str_lcfirst
	,	Xol_kwd_grp_.Id_str_uc
	,	Xol_kwd_grp_.Id_str_ucfirst
	,	Xol_kwd_grp_.Id_str_padleft
	,	Xol_kwd_grp_.Id_str_padright
	,	Xol_kwd_grp_.Id_str_formatnum
	,	Xol_kwd_grp_.Id_str_formatdate
	,	Xol_kwd_grp_.Id_url_localurl
	,	Xol_kwd_grp_.Id_url_localurle
	,	Xol_kwd_grp_.Id_url_fullurl
	,	Xol_kwd_grp_.Id_url_fullurle
	,	Xol_kwd_grp_.Id_url_filepath
	,	Xol_kwd_grp_.Id_url_anchorencode
	,	Xol_kwd_grp_.Id_i18n_plural
	,	Xol_kwd_grp_.Id_misc_tag
	,	Xol_kwd_grp_.Id_i18n_language
	,	Xol_kwd_grp_.Id_i18n_int
	,	Xol_kwd_grp_.Id_i18n_grammar
	,	Xol_kwd_grp_.Id_xtn_expr
	,	Xol_kwd_grp_.Id_xtn_if
	,	Xol_kwd_grp_.Id_xtn_ifeq
	,	Xol_kwd_grp_.Id_xtn_iferror
	,	Xol_kwd_grp_.Id_xtn_ifexpr
	,	Xol_kwd_grp_.Id_xtn_ifexist
	,	Xol_kwd_grp_.Id_xtn_rel2abs
	,	Xol_kwd_grp_.Id_xtn_switch
	,	Xol_kwd_grp_.Id_xtn_time
	,	Xol_kwd_grp_.Id_xtn_timel
	,	Xol_kwd_grp_.Id_xtn_titleparts
	,	Xol_kwd_grp_.Id_subst
	,	Xol_kwd_grp_.Id_safesubst
	,	Xol_kwd_grp_.Id_raw
	,	Xol_kwd_grp_.Id_msg
	,	Xol_kwd_grp_.Id_msgnw
	,	Xol_kwd_grp_.Id_xowa_dbg
	,	Xol_kwd_grp_.Id_xtn_geodata_coordinates
	,	Xol_kwd_grp_.Id_url_canonicalurl
	,	Xol_kwd_grp_.Id_url_canonicalurle
	,	Xol_kwd_grp_.Id_lst
	,	Xol_kwd_grp_.Id_lstx
	,	Xol_kwd_grp_.Id_invoke
	,	Xol_kwd_grp_.Id_property
	,	Xol_kwd_grp_.Id_noexternallanglinks
	,	Xol_kwd_grp_.Id_wbreponame
	,	Xol_kwd_grp_.Id_ns_num
	,	Xol_kwd_grp_.Id_page_id
	,	Xol_kwd_grp_.Id_xowa
	,	Xol_kwd_grp_.Id_mapSources_deg2dd
	,	Xol_kwd_grp_.Id_mapSources_dd2dms
	,	Xol_kwd_grp_.Id_mapSources_geoLink
	,	Xol_kwd_grp_.Id_geoCrumbs_isin
	,	Xol_kwd_grp_.Id_relatedArticles
	,	Xol_kwd_grp_.Id_insider
	,	Xol_kwd_grp_.Id_massMessage_target
	,	Xol_kwd_grp_.Id_cascadingSources
	,	Xol_kwd_grp_.Id_pendingChangeLevel
	,	Xol_kwd_grp_.Id_pagesUsingPendingChanges
	,	Xol_kwd_grp_.Id_bang
	};
	public static Xot_defn Get_prototype(int id) {
		switch (id) {
			case Xol_kwd_grp_.Id_utc_year:
			case Xol_kwd_grp_.Id_utc_month_int_len2:
			case Xol_kwd_grp_.Id_utc_month_int:
			case Xol_kwd_grp_.Id_utc_day_int_len2:
			case Xol_kwd_grp_.Id_utc_day_int:
			case Xol_kwd_grp_.Id_utc_hour:
			case Xol_kwd_grp_.Id_utc_time:
			case Xol_kwd_grp_.Id_utc_timestamp:
			case Xol_kwd_grp_.Id_utc_week:
			case Xol_kwd_grp_.Id_utc_dow:						return Pft_func_date_int.Utc;
			case Xol_kwd_grp_.Id_utc_month_abrv:				return new Pft_func_date_name(-1, Pft_func_date_int.Date_tid_utc, DateAdp_.SegIdx_month, Xol_msg_itm_.Id_dte_month_abrv_jan - Int_.Base1);
			case Xol_kwd_grp_.Id_utc_month_name:				return new Pft_func_date_name(-1, Pft_func_date_int.Date_tid_utc, DateAdp_.SegIdx_month, Xol_msg_itm_.Id_dte_month_name_january - Int_.Base1);
			case Xol_kwd_grp_.Id_utc_month_gen:					return new Pft_func_date_name(-1, Pft_func_date_int.Date_tid_utc, DateAdp_.SegIdx_month, Xol_msg_itm_.Id_dte_month_gen_january - Int_.Base1);
			case Xol_kwd_grp_.Id_utc_day_name:					return new Pft_func_date_name(-1, Pft_func_date_int.Date_tid_utc, DateAdp_.SegIdx_dayOfWeek, Xol_msg_itm_.Id_dte_dow_name_sunday);

			case Xol_kwd_grp_.Id_lcl_year:
			case Xol_kwd_grp_.Id_lcl_month_int_len2:
			case Xol_kwd_grp_.Id_lcl_month_int:
			case Xol_kwd_grp_.Id_lcl_day_int_len2:
			case Xol_kwd_grp_.Id_lcl_day_int:
			case Xol_kwd_grp_.Id_lcl_hour:
			case Xol_kwd_grp_.Id_lcl_time:
			case Xol_kwd_grp_.Id_lcl_timestamp:
			case Xol_kwd_grp_.Id_lcl_week:
			case Xol_kwd_grp_.Id_lcl_dow:						return Pft_func_date_int.Lcl;
			case Xol_kwd_grp_.Id_lcl_month_abrv:				return new Pft_func_date_name(-1, Pft_func_date_int.Date_tid_lcl, DateAdp_.SegIdx_month, Xol_msg_itm_.Id_dte_month_abrv_jan - Int_.Base1);
			case Xol_kwd_grp_.Id_lcl_month_name:				return new Pft_func_date_name(-1, Pft_func_date_int.Date_tid_lcl, DateAdp_.SegIdx_month, Xol_msg_itm_.Id_dte_month_name_january - Int_.Base1);
			case Xol_kwd_grp_.Id_lcl_month_gen:					return new Pft_func_date_name(-1, Pft_func_date_int.Date_tid_lcl, DateAdp_.SegIdx_month, Xol_msg_itm_.Id_dte_month_gen_january - Int_.Base1);
			case Xol_kwd_grp_.Id_lcl_day_name:					return new Pft_func_date_name(-1, Pft_func_date_int.Date_tid_lcl, DateAdp_.SegIdx_dayOfWeek, Xol_msg_itm_.Id_dte_dow_name_sunday);

			case Xol_kwd_grp_.Id_rev_year:
			case Xol_kwd_grp_.Id_rev_month_int_len2:
			case Xol_kwd_grp_.Id_rev_month_int:
			case Xol_kwd_grp_.Id_rev_day_int_len2:
			case Xol_kwd_grp_.Id_rev_day_int:
			case Xol_kwd_grp_.Id_rev_timestamp:					return Pft_func_date_int.Rev;

			case Xol_kwd_grp_.Id_ns_num:
			case Xol_kwd_grp_.Id_ns_txt:
			case Xol_kwd_grp_.Id_ns_url:
			case Xol_kwd_grp_.Id_ns_subj_txt:
			case Xol_kwd_grp_.Id_ns_subj_url:
			case Xol_kwd_grp_.Id_ns_talk_txt:
			case Xol_kwd_grp_.Id_ns_talk_url:
			case Xol_kwd_grp_.Id_ttl_full_txt:
			case Xol_kwd_grp_.Id_ttl_full_url:
			case Xol_kwd_grp_.Id_ttl_page_txt:
			case Xol_kwd_grp_.Id_ttl_base_txt:
			case Xol_kwd_grp_.Id_ttl_page_url:
			case Xol_kwd_grp_.Id_ttl_base_url:
			case Xol_kwd_grp_.Id_ttl_leaf_txt:
			case Xol_kwd_grp_.Id_ttl_leaf_url:
			case Xol_kwd_grp_.Id_ttl_subj_txt:
			case Xol_kwd_grp_.Id_ttl_subj_url:
			case Xol_kwd_grp_.Id_ttl_talk_txt:
			case Xol_kwd_grp_.Id_ttl_talk_url:					return Pfunc_ttl._;

			case Xol_kwd_grp_.Id_site_sitename:
			case Xol_kwd_grp_.Id_site_servername:
			case Xol_kwd_grp_.Id_site_server:
			case Xol_kwd_grp_.Id_site_articlepath:
			case Xol_kwd_grp_.Id_site_scriptpath:
			case Xol_kwd_grp_.Id_site_stylepath:
			case Xol_kwd_grp_.Id_site_contentlanguage:
			case Xol_kwd_grp_.Id_site_directionmark:
			case Xol_kwd_grp_.Id_site_currentversion:			return Pfunc_wiki_props._;

			case Xol_kwd_grp_.Id_num_pages:
			case Xol_kwd_grp_.Id_num_articles:
			case Xol_kwd_grp_.Id_num_files:
			case Xol_kwd_grp_.Id_num_edits:
			case Xol_kwd_grp_.Id_num_views:
			case Xol_kwd_grp_.Id_num_users:
			case Xol_kwd_grp_.Id_num_users_active:
			case Xol_kwd_grp_.Id_num_admins:					return Pfunc_wiki_stats._;

			case Xol_kwd_grp_.Id_page_id:
			case Xol_kwd_grp_.Id_rev_id:
			case Xol_kwd_grp_.Id_rev_pagesize:
			case Xol_kwd_grp_.Id_rev_user:
			case Xol_kwd_grp_.Id_rev_protectionlevel:			return Pfunc_rev_props._;
			case Xol_kwd_grp_.Id_page_displaytitle:				return Pfunc_displaytitle._;
			case Xol_kwd_grp_.Id_page_defaultsort:				return Pfunc_defaultsort._;
			case Xol_kwd_grp_.Id_noeditsection:					return Pfunc_noeditsection._;
			case Xol_kwd_grp_.Id_site_pagesincategory:			return Pfunc_pagesincategory._;

			case Xol_kwd_grp_.Id_url_ns:						return new Pfunc_ns(false);
			case Xol_kwd_grp_.Id_url_nse:						return new Pfunc_ns(true);
			case Xol_kwd_grp_.Id_url_urlencode:					return new Pfunc_urlencode();
			case Xol_kwd_grp_.Id_str_lc:						return new Pfunc_case(Xol_lang.Tid_lower, false);
			case Xol_kwd_grp_.Id_str_lcfirst:					return new Pfunc_case(Xol_lang.Tid_lower, true);
			case Xol_kwd_grp_.Id_str_uc:						return new Pfunc_case(Xol_lang.Tid_upper, false);
			case Xol_kwd_grp_.Id_str_ucfirst:					return new Pfunc_case(Xol_lang.Tid_upper, true);
			case Xol_kwd_grp_.Id_str_padleft:					return new Pfunc_pad(Xol_kwd_grp_.Id_str_padleft, false);
			case Xol_kwd_grp_.Id_str_padright:					return new Pfunc_pad(Xol_kwd_grp_.Id_str_padright, true);
			case Xol_kwd_grp_.Id_str_formatnum:					return new Pf_formatnum();
			case Xol_kwd_grp_.Id_str_formatdate:				return new Pft_func_formatdate();
			case Xol_kwd_grp_.Id_url_localurl:					return new Pfunc_urlfunc(Xol_kwd_grp_.Id_url_localurl, Pfunc_urlfunc.Tid_local, false);
			case Xol_kwd_grp_.Id_url_localurle:					return new Pfunc_urlfunc(Xol_kwd_grp_.Id_url_localurle, Pfunc_urlfunc.Tid_local, true);
			case Xol_kwd_grp_.Id_url_fullurl:					return new Pfunc_urlfunc(Xol_kwd_grp_.Id_url_fullurl, Pfunc_urlfunc.Tid_full, false);
			case Xol_kwd_grp_.Id_url_fullurle:					return new Pfunc_urlfunc(Xol_kwd_grp_.Id_url_fullurle, Pfunc_urlfunc.Tid_full, true);
			case Xol_kwd_grp_.Id_url_canonicalurl:				return new Pfunc_urlfunc(Xol_kwd_grp_.Id_url_canonicalurl, Pfunc_urlfunc.Tid_canonical, false);
			case Xol_kwd_grp_.Id_url_canonicalurle:				return new Pfunc_urlfunc(Xol_kwd_grp_.Id_url_canonicalurle, Pfunc_urlfunc.Tid_canonical, false);
			case Xol_kwd_grp_.Id_url_filepath:					return new Pfunc_filepath();
			case Xol_kwd_grp_.Id_url_anchorencode:				return new Pfunc_anchorencode();

			case Xol_kwd_grp_.Id_i18n_plural:					return new Pfunc_plural();
			case Xol_kwd_grp_.Id_i18n_language:					return new Pfunc_language();
			case Xol_kwd_grp_.Id_i18n_int:						return new Pfunc_int();
			case Xol_kwd_grp_.Id_i18n_grammar:					return new Pfunc_grammar();
			case Xol_kwd_grp_.Id_misc_tag:						return new Pfunc_tag();

			case Xol_kwd_grp_.Id_xtn_expr:						return new gplx.xowa.xtns.pfuncs.exprs.Pfunc_expr();
			case Xol_kwd_grp_.Id_xtn_if:						return new Pfunc_if();
			case Xol_kwd_grp_.Id_xtn_ifeq:						return new Pfunc_ifeq();
			case Xol_kwd_grp_.Id_xtn_iferror:					return new Pfunc_iferror();
			case Xol_kwd_grp_.Id_xtn_ifexpr:					return new Pfunc_ifexpr();
			case Xol_kwd_grp_.Id_xtn_ifexist:					return new Pfunc_ifexist();
			case Xol_kwd_grp_.Id_xtn_rel2abs:					return new Pfunc_rel2abs();
			case Xol_kwd_grp_.Id_xtn_switch:					return new Pfunc_switch();
			case Xol_kwd_grp_.Id_xtn_time:						return Pft_func_time._Utc;
			case Xol_kwd_grp_.Id_xtn_timel:						return Pft_func_time._Lcl;
			case Xol_kwd_grp_.Id_xtn_titleparts:				return new Pfunc_titleparts();

			case Xol_kwd_grp_.Id_subst:
			case Xol_kwd_grp_.Id_safesubst:
			case Xol_kwd_grp_.Id_msg:
			case Xol_kwd_grp_.Id_msgnw:
			case Xol_kwd_grp_.Id_raw:							return new Xot_defn_subst((byte)id, Bry_.Empty);

			case Xol_kwd_grp_.Id_xowa_dbg:						return new Xop_xowa_dbg();
			case Xol_kwd_grp_.Id_xowa:							return new gplx.xowa.xtns.xowa_cmds.Xop_xowa_func();
			case Xol_kwd_grp_.Id_xtn_geodata_coordinates:		return gplx.xowa.xtns.geodata.Geo_coordinates_func._;
			case Xol_kwd_grp_.Id_lst:							return gplx.xowa.xtns.lst.Lst_pfunc_lst._;
			case Xol_kwd_grp_.Id_lstx:							return gplx.xowa.xtns.lst.Lst_pfunc_lstx._;
			case Xol_kwd_grp_.Id_invoke:						return new gplx.xowa.xtns.scribunto.Scrib_invoke_func();

			case Xol_kwd_grp_.Id_property:						return new gplx.xowa.xtns.wdatas.pfuncs.Wdata_pf_property();
			case Xol_kwd_grp_.Id_noexternallanglinks:			return new gplx.xowa.xtns.wdatas.pfuncs.Wdata_pf_noExternalLangLinks();
			case Xol_kwd_grp_.Id_wbreponame:					return new gplx.xowa.xtns.wdatas.pfuncs.Wdata_pf_wbreponame();

			case Xol_kwd_grp_.Id_mapSources_deg2dd:				return gplx.xowa.xtns.mapSources.Map_deg2dd_func._;
			case Xol_kwd_grp_.Id_mapSources_dd2dms:				return gplx.xowa.xtns.mapSources.Map_dd2dms_func._;
			case Xol_kwd_grp_.Id_mapSources_geoLink:			return gplx.xowa.xtns.mapSources.Map_geolink_func._;

			case Xol_kwd_grp_.Id_geoCrumbs_isin:				return gplx.xowa.xtns.geoCrumbs.Geoc_isin_func._;

			case Xol_kwd_grp_.Id_relatedArticles:				return gplx.xowa.xtns.relatedArticles.Articles_func._;
			case Xol_kwd_grp_.Id_insider:						return gplx.xowa.xtns.insiders.Insider_func._;

			case Xol_kwd_grp_.Id_massMessage_target:			return gplx.xowa.xtns.massMessage.Message_target_func._;

			case Xol_kwd_grp_.Id_pendingChangeLevel:			return gplx.xowa.xtns.flaggedRevs.Pending_change_level_func._;
			case Xol_kwd_grp_.Id_pagesUsingPendingChanges:		return gplx.xowa.xtns.flaggedRevs.Pages_using_pending_changes_func._;

			case Xol_kwd_grp_.Id_cascadingSources:
																return new Pf_func_noop(id);
			case Xol_kwd_grp_.Id_bang:							return Pf_func_bang._;
			default:											throw Err_mgr._.unhandled_(id);
		}
	}
}
class Pf_func_noop extends Pf_func_base {
	public Pf_func_noop(int id) {this.id = id;} private int id;
	@Override public int Id() {return id;}
	@Override public void Func_evaluate(Xop_ctx ctx, byte[] src, Xot_invk caller, Xot_invk self, Bry_bfr bfr) {}
	@Override public Pf_func New(int id, byte[] name) {return new Pf_func_noop(id).Name_(name);}
}
class Pf_func_bang extends Pf_func_base {
	@Override public int Id() {return Xol_kwd_grp_.Id_bang;}
	@Override public void Func_evaluate(Xop_ctx ctx, byte[] src, Xot_invk caller, Xot_invk self, Bry_bfr bfr) {bfr.Add_byte_pipe();}
	@Override public Pf_func New(int id, byte[] name) {return this;}
	public static final Pf_func_bang _ = new Pf_func_bang();
	Pf_func_bang() {this.Name_(Byte_ascii.Bang_bry);}
}
