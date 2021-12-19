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
package gplx.xowa.langs;
import gplx.types.basics.utls.BryUtl;
import gplx.libs.files.Io_url;
import gplx.types.basics.utls.BoolUtl;
import gplx.types.basics.constants.AsciiByte;
import gplx.xowa.apps.fsys.Xoa_fsys_mgr;
import gplx.xowa.langs.kwds.Xol_kwd_grp_;
import gplx.xowa.langs.kwds.Xol_kwd_mgr;
import gplx.xowa.langs.numbers.Xol_num_mgr;
public class Xol_lang_itm_ {
	public static Io_url xo_lang_fil_(Xoa_fsys_mgr app_fsys_mgr, String lang_key) {return app_fsys_mgr.Cfg_lang_core_dir().GenSubFil(lang_key + ".gfs");}
	public static final byte Char_tid_ltr_l = 0, Char_tid_ltr_u = 1, Char_tid_num = 2, Char_tid_ws = 3, Char_tid_sym = 4, Char_tid_misc = 5;
	public static byte Char_tid(byte b) {
		switch (b) {
			case AsciiByte.Ltr_A: case AsciiByte.Ltr_B: case AsciiByte.Ltr_C: case AsciiByte.Ltr_D: case AsciiByte.Ltr_E:
			case AsciiByte.Ltr_F: case AsciiByte.Ltr_G: case AsciiByte.Ltr_H: case AsciiByte.Ltr_I: case AsciiByte.Ltr_J:
			case AsciiByte.Ltr_K: case AsciiByte.Ltr_L: case AsciiByte.Ltr_M: case AsciiByte.Ltr_N: case AsciiByte.Ltr_O:
			case AsciiByte.Ltr_P: case AsciiByte.Ltr_Q: case AsciiByte.Ltr_R: case AsciiByte.Ltr_S: case AsciiByte.Ltr_T:
			case AsciiByte.Ltr_U: case AsciiByte.Ltr_V: case AsciiByte.Ltr_W: case AsciiByte.Ltr_X: case AsciiByte.Ltr_Y: case AsciiByte.Ltr_Z:
				return Char_tid_ltr_u;
			case AsciiByte.Ltr_a: case AsciiByte.Ltr_b: case AsciiByte.Ltr_c: case AsciiByte.Ltr_d: case AsciiByte.Ltr_e:
			case AsciiByte.Ltr_f: case AsciiByte.Ltr_g: case AsciiByte.Ltr_h: case AsciiByte.Ltr_i: case AsciiByte.Ltr_j:
			case AsciiByte.Ltr_k: case AsciiByte.Ltr_l: case AsciiByte.Ltr_m: case AsciiByte.Ltr_n: case AsciiByte.Ltr_o:
			case AsciiByte.Ltr_p: case AsciiByte.Ltr_q: case AsciiByte.Ltr_r: case AsciiByte.Ltr_s: case AsciiByte.Ltr_t:
			case AsciiByte.Ltr_u: case AsciiByte.Ltr_v: case AsciiByte.Ltr_w: case AsciiByte.Ltr_x: case AsciiByte.Ltr_y: case AsciiByte.Ltr_z:
				return Char_tid_ltr_l;
			case AsciiByte.Num0: case AsciiByte.Num1: case AsciiByte.Num2: case AsciiByte.Num3: case AsciiByte.Num4:
			case AsciiByte.Num5: case AsciiByte.Num6: case AsciiByte.Num7: case AsciiByte.Num8: case AsciiByte.Num9:
				return Char_tid_num;
			case AsciiByte.Space: case AsciiByte.Nl: case AsciiByte.Tab: case AsciiByte.Cr:
				return Char_tid_ws;
			default:
				return Char_tid_misc;
		}
	}
	public static final byte[] Key_en = BryUtl.NewA7("en");
	public static Xol_lang_itm Lang_en_make(Xoa_lang_mgr lang_mgr) {
		Xol_lang_itm rv = Xol_lang_itm.New(lang_mgr, Xol_lang_itm_.Key_en);
		Xol_lang_itm_.Lang_init(rv);
		rv.Evt_lang_changed();
		return rv;
	}
	public static void Lang_init(Xol_lang_itm lang) {
		lang.Num_mgr().Separators_mgr().Set(Xol_num_mgr.Separators_key__grp, Xol_num_mgr.Separators_key__grp);
		lang.Num_mgr().Separators_mgr().Set(Xol_num_mgr.Separators_key__dec, Xol_num_mgr.Separators_key__dec);
		lang.Lnki_trail_mgr().Add_range(AsciiByte.Ltr_a, AsciiByte.Ltr_z);// REF.MW:MessagesEn.php|$linkTrail = '/^([a-z]+)(.*)$/sD';
Xol_kwd_mgr kwd_mgr = lang.Kwd_mgr();
kwd_mgr.New(BoolUtl.N, Xol_kwd_grp_.Id_redirect, "#REDIRECT");
kwd_mgr.New(BoolUtl.N, Xol_kwd_grp_.Id_notoc, "__NOTOC__");
kwd_mgr.New(BoolUtl.N, Xol_kwd_grp_.Id_nogallery, "__NOGALLERY__");
kwd_mgr.New(BoolUtl.N, Xol_kwd_grp_.Id_forcetoc, "__FORCETOC__");
kwd_mgr.New(BoolUtl.N, Xol_kwd_grp_.Id_toc, "__TOC__");
kwd_mgr.New(BoolUtl.N, Xol_kwd_grp_.Id_noeditsection, "__NOEDITSECTION__");
kwd_mgr.New(BoolUtl.N, Xol_kwd_grp_.Id_noheader, "__NOHEADER__");
kwd_mgr.New(BoolUtl.Y, Xol_kwd_grp_.Id_utc_month_int_len2, "CURRENTMONTH", "CURRENTMONTH2");
kwd_mgr.New(BoolUtl.Y, Xol_kwd_grp_.Id_utc_month_int, "CURRENTMONTH1");
kwd_mgr.New(BoolUtl.Y, Xol_kwd_grp_.Id_utc_month_name, "CURRENTMONTHNAME");
kwd_mgr.New(BoolUtl.Y, Xol_kwd_grp_.Id_utc_month_gen, "CURRENTMONTHNAMEGEN");
kwd_mgr.New(BoolUtl.Y, Xol_kwd_grp_.Id_utc_month_abrv, "CURRENTMONTHABBREV");
kwd_mgr.New(BoolUtl.Y, Xol_kwd_grp_.Id_utc_day_int, "CURRENTDAY");
kwd_mgr.New(BoolUtl.Y, Xol_kwd_grp_.Id_utc_day_int_len2, "CURRENTDAY2");
kwd_mgr.New(BoolUtl.Y, Xol_kwd_grp_.Id_utc_day_name, "CURRENTDAYNAME");
kwd_mgr.New(BoolUtl.Y, Xol_kwd_grp_.Id_utc_year, "CURRENTYEAR");
kwd_mgr.New(BoolUtl.Y, Xol_kwd_grp_.Id_utc_time, "CURRENTTIME");
kwd_mgr.New(BoolUtl.Y, Xol_kwd_grp_.Id_utc_hour, "CURRENTHOUR");
kwd_mgr.New(BoolUtl.Y, Xol_kwd_grp_.Id_lcl_month_int_len2, "LOCALMONTH", "LOCALMONTH2");
kwd_mgr.New(BoolUtl.Y, Xol_kwd_grp_.Id_lcl_month_int, "LOCALMONTH1");
kwd_mgr.New(BoolUtl.Y, Xol_kwd_grp_.Id_lcl_month_name, "LOCALMONTHNAME");
kwd_mgr.New(BoolUtl.Y, Xol_kwd_grp_.Id_lcl_month_gen, "LOCALMONTHNAMEGEN");
kwd_mgr.New(BoolUtl.Y, Xol_kwd_grp_.Id_lcl_month_abrv, "LOCALMONTHABBREV");
kwd_mgr.New(BoolUtl.Y, Xol_kwd_grp_.Id_lcl_day_int, "LOCALDAY");
kwd_mgr.New(BoolUtl.Y, Xol_kwd_grp_.Id_lcl_day_int_len2, "LOCALDAY2");
kwd_mgr.New(BoolUtl.Y, Xol_kwd_grp_.Id_lcl_day_name, "LOCALDAYNAME");
kwd_mgr.New(BoolUtl.Y, Xol_kwd_grp_.Id_lcl_year, "LOCALYEAR");
kwd_mgr.New(BoolUtl.Y, Xol_kwd_grp_.Id_lcl_time, "LOCALTIME");
kwd_mgr.New(BoolUtl.Y, Xol_kwd_grp_.Id_lcl_hour, "LOCALHOUR");
kwd_mgr.New(BoolUtl.Y, Xol_kwd_grp_.Id_num_pages, "NUMBEROFPAGES");
kwd_mgr.New(BoolUtl.Y, Xol_kwd_grp_.Id_num_articles, "NUMBEROFARTICLES");
kwd_mgr.New(BoolUtl.Y, Xol_kwd_grp_.Id_num_files, "NUMBEROFFILES");
kwd_mgr.New(BoolUtl.Y, Xol_kwd_grp_.Id_num_users, "NUMBEROFUSERS");
kwd_mgr.New(BoolUtl.Y, Xol_kwd_grp_.Id_num_users_active, "NUMBEROFACTIVEUSERS");
kwd_mgr.New(BoolUtl.Y, Xol_kwd_grp_.Id_num_edits, "NUMBEROFEDITS");
kwd_mgr.New(BoolUtl.Y, Xol_kwd_grp_.Id_num_views, "NUMBEROFVIEWS");
kwd_mgr.New(BoolUtl.Y, Xol_kwd_grp_.Id_ttl_page_txt, "PAGENAME");
kwd_mgr.New(BoolUtl.Y, Xol_kwd_grp_.Id_ttl_page_url, "PAGENAMEE");
kwd_mgr.New(BoolUtl.Y, Xol_kwd_grp_.Id_ns_txt, "NAME"+"SPACE");
kwd_mgr.New(BoolUtl.Y, Xol_kwd_grp_.Id_ns_url, "NAME"+"SPACEE");
kwd_mgr.New(BoolUtl.Y, Xol_kwd_grp_.Id_ns_talk_txt, "TALKSPACE");
kwd_mgr.New(BoolUtl.Y, Xol_kwd_grp_.Id_ns_talk_url, "TALKSPACEE");
kwd_mgr.New(BoolUtl.Y, Xol_kwd_grp_.Id_ns_subj_txt, "SUBJECTSPACE", "ARTICLESPACE");
kwd_mgr.New(BoolUtl.Y, Xol_kwd_grp_.Id_ns_subj_url, "SUBJECTSPACEE", "ARTICLESPACEE");
kwd_mgr.New(BoolUtl.Y, Xol_kwd_grp_.Id_ttl_full_txt, "FULLPAGENAME");
kwd_mgr.New(BoolUtl.Y, Xol_kwd_grp_.Id_ttl_full_url, "FULLPAGENAMEE");
kwd_mgr.New(BoolUtl.Y, Xol_kwd_grp_.Id_ttl_leaf_txt, "SUBPAGENAME");
kwd_mgr.New(BoolUtl.Y, Xol_kwd_grp_.Id_ttl_leaf_url, "SUBPAGENAMEE");
kwd_mgr.New(BoolUtl.Y, Xol_kwd_grp_.Id_ttl_base_txt, "BASEPAGENAME");
kwd_mgr.New(BoolUtl.Y, Xol_kwd_grp_.Id_ttl_base_url, "BASEPAGENAMEE");
kwd_mgr.New(BoolUtl.Y, Xol_kwd_grp_.Id_ttl_talk_txt, "TALKPAGENAME");
kwd_mgr.New(BoolUtl.Y, Xol_kwd_grp_.Id_ttl_talk_url, "TALKPAGENAMEE");
kwd_mgr.New(BoolUtl.Y, Xol_kwd_grp_.Id_ttl_subj_txt, "SUBJECTPAGENAME", "ARTICLEPAGENAME");
kwd_mgr.New(BoolUtl.Y, Xol_kwd_grp_.Id_ttl_subj_url, "SUBJECTPAGENAMEE", "ARTICLEPAGENAMEE");
kwd_mgr.New(BoolUtl.N, Xol_kwd_grp_.Id_ttl_root_txt, "ROOTPAGENAME");
kwd_mgr.New(BoolUtl.N, Xol_kwd_grp_.Id_ttl_root_url, "ROOTPAGENAMEE");
kwd_mgr.New(BoolUtl.N, Xol_kwd_grp_.Id_msg, "msg");
kwd_mgr.New(BoolUtl.N, Xol_kwd_grp_.Id_subst, "subst:");
kwd_mgr.New(BoolUtl.N, Xol_kwd_grp_.Id_safesubst, "safesubst:");
kwd_mgr.New(BoolUtl.N, Xol_kwd_grp_.Id_msgnw, "msgnw");
kwd_mgr.New(BoolUtl.Y, Xol_kwd_grp_.Id_img_thumbnail, "thumbnail", "thumb");
kwd_mgr.New(BoolUtl.Y, Xol_kwd_grp_.Id_img_manualthumb, "thumbnail", "thumb");
kwd_mgr.New(BoolUtl.Y, Xol_kwd_grp_.Id_img_framed, "framed", "enframed", "frame");
kwd_mgr.New(BoolUtl.Y, Xol_kwd_grp_.Id_img_frameless, "frameless");
kwd_mgr.New(BoolUtl.Y, Xol_kwd_grp_.Id_img_upright, "upright");
kwd_mgr.New(BoolUtl.Y, Xol_kwd_grp_.Id_img_upright_factor, "upright_factor");
kwd_mgr.New(BoolUtl.Y, Xol_kwd_grp_.Id_img_border, "border");
kwd_mgr.New(BoolUtl.Y, Xol_kwd_grp_.Id_img_align, "align");
kwd_mgr.New(BoolUtl.Y, Xol_kwd_grp_.Id_img_valign, "valign");
kwd_mgr.New(BoolUtl.Y, Xol_kwd_grp_.Id_img_alt, "alt");
kwd_mgr.New(BoolUtl.Y, Xol_kwd_grp_.Id_img_class, "class");
kwd_mgr.New(BoolUtl.Y, Xol_kwd_grp_.Id_img_caption, "caption");
kwd_mgr.New(BoolUtl.Y, Xol_kwd_grp_.Id_img_link_url, "link-url");
kwd_mgr.New(BoolUtl.Y, Xol_kwd_grp_.Id_img_link_title, "link-title");
kwd_mgr.New(BoolUtl.Y, Xol_kwd_grp_.Id_img_link_target, "link-target");
kwd_mgr.New(BoolUtl.Y, Xol_kwd_grp_.Id_img_link_none, "no-link");
kwd_mgr.New(BoolUtl.Y, Xol_kwd_grp_.Id_img_width, "px");
kwd_mgr.New(BoolUtl.Y, Xol_kwd_grp_.Id_img_page, "page");
kwd_mgr.New(BoolUtl.Y, Xol_kwd_grp_.Id_img_none, "none");
kwd_mgr.New(BoolUtl.Y, Xol_kwd_grp_.Id_img_right, "right");
kwd_mgr.New(BoolUtl.Y, Xol_kwd_grp_.Id_img_center, "center", "centre");
kwd_mgr.New(BoolUtl.Y, Xol_kwd_grp_.Id_img_left, "left");
kwd_mgr.New(BoolUtl.Y, Xol_kwd_grp_.Id_img_baseline, "baseline");
kwd_mgr.New(BoolUtl.Y, Xol_kwd_grp_.Id_img_sub, "sub");
kwd_mgr.New(BoolUtl.Y, Xol_kwd_grp_.Id_img_super, "super", "sup");
kwd_mgr.New(BoolUtl.Y, Xol_kwd_grp_.Id_img_top, "top");
kwd_mgr.New(BoolUtl.Y, Xol_kwd_grp_.Id_img_text_top, "text-top");
kwd_mgr.New(BoolUtl.Y, Xol_kwd_grp_.Id_img_middle, "middle");
kwd_mgr.New(BoolUtl.Y, Xol_kwd_grp_.Id_img_bottom, "bottom");
kwd_mgr.New(BoolUtl.Y, Xol_kwd_grp_.Id_img_text_bottom, "text-bottom");
kwd_mgr.New(BoolUtl.Y, Xol_kwd_grp_.Id_img_link, "link");
kwd_mgr.New(BoolUtl.N, Xol_kwd_grp_.Id_i18n_int, "int");
kwd_mgr.New(BoolUtl.Y, Xol_kwd_grp_.Id_site_sitename, "SITENAME");
kwd_mgr.New(BoolUtl.N, Xol_kwd_grp_.Id_url_ns, "ns");
kwd_mgr.New(BoolUtl.N, Xol_kwd_grp_.Id_url_nse, "nse");
kwd_mgr.New(BoolUtl.N, Xol_kwd_grp_.Id_url_localurl, "localurl");
kwd_mgr.New(BoolUtl.N, Xol_kwd_grp_.Id_url_localurle, "localurle");
kwd_mgr.New(BoolUtl.Y, Xol_kwd_grp_.Id_site_articlepath, "ARTICLEPATH");
kwd_mgr.New(BoolUtl.Y, Xol_kwd_grp_.Id_site_server, "SERVER");
kwd_mgr.New(BoolUtl.Y, Xol_kwd_grp_.Id_site_servername, "SERVERNAME");
kwd_mgr.New(BoolUtl.Y, Xol_kwd_grp_.Id_site_scriptpath, "SCRIPTPATH");
kwd_mgr.New(BoolUtl.Y, Xol_kwd_grp_.Id_site_stylepath, "STYLEPATH");
kwd_mgr.New(BoolUtl.N, Xol_kwd_grp_.Id_i18n_grammar, "grammar");
kwd_mgr.New(BoolUtl.N, Xol_kwd_grp_.Id_i18n_gender, "gender");
kwd_mgr.New(BoolUtl.N, Xol_kwd_grp_.Id_notitleconvert, "__NOTITLECONVERT__", "__NOTC__");
kwd_mgr.New(BoolUtl.N, Xol_kwd_grp_.Id_nocontentconvert, "__NOCONTENTCONVERT__", "__NOCC__");
kwd_mgr.New(BoolUtl.Y, Xol_kwd_grp_.Id_utc_week, "CURRENTWEEK");
kwd_mgr.New(BoolUtl.Y, Xol_kwd_grp_.Id_utc_dow, "CURRENTDOW");
kwd_mgr.New(BoolUtl.Y, Xol_kwd_grp_.Id_lcl_week, "LOCALWEEK");
kwd_mgr.New(BoolUtl.Y, Xol_kwd_grp_.Id_lcl_dow, "LOCALDOW");
kwd_mgr.New(BoolUtl.Y, Xol_kwd_grp_.Id_rev_id, "REVISIONID");
kwd_mgr.New(BoolUtl.Y, Xol_kwd_grp_.Id_rev_day_int, "REVISIONDAY");
kwd_mgr.New(BoolUtl.Y, Xol_kwd_grp_.Id_rev_day_int_len2, "REVISIONDAY2");
kwd_mgr.New(BoolUtl.Y, Xol_kwd_grp_.Id_rev_month_int_len2, "REVISIONMONTH");
kwd_mgr.New(BoolUtl.Y, Xol_kwd_grp_.Id_rev_month_int, "REVISIONMONTH1");
kwd_mgr.New(BoolUtl.Y, Xol_kwd_grp_.Id_rev_year, "REVISIONYEAR");
kwd_mgr.New(BoolUtl.Y, Xol_kwd_grp_.Id_rev_timestamp, "REVISIONTIMESTAMP");
kwd_mgr.New(BoolUtl.Y, Xol_kwd_grp_.Id_rev_user, "REVISIONUSER");
kwd_mgr.New(BoolUtl.N, Xol_kwd_grp_.Id_i18n_plural, "plural");
kwd_mgr.New(BoolUtl.N, Xol_kwd_grp_.Id_url_fullurl, "fullurl");
kwd_mgr.New(BoolUtl.N, Xol_kwd_grp_.Id_url_fullurle, "fullurle");
kwd_mgr.New(BoolUtl.N, Xol_kwd_grp_.Id_str_lcfirst, "lcfirst");
kwd_mgr.New(BoolUtl.N, Xol_kwd_grp_.Id_str_ucfirst, "ucfirst");
kwd_mgr.New(BoolUtl.N, Xol_kwd_grp_.Id_str_lc, "lc");
kwd_mgr.New(BoolUtl.N, Xol_kwd_grp_.Id_str_uc, "uc");
kwd_mgr.New(BoolUtl.N, Xol_kwd_grp_.Id_raw, "raw");
kwd_mgr.New(BoolUtl.Y, Xol_kwd_grp_.Id_page_displaytitle, "DISPLAYTITLE");
kwd_mgr.New(BoolUtl.Y, Xol_kwd_grp_.Id_str_rawsuffix, "R");
kwd_mgr.New(BoolUtl.Y, Xol_kwd_grp_.Id_newsectionlink, "__NEWSECTIONLINK__");
kwd_mgr.New(BoolUtl.Y, Xol_kwd_grp_.Id_nonewsectionlink, "__NONEWSECTIONLINK__");
kwd_mgr.New(BoolUtl.Y, Xol_kwd_grp_.Id_site_currentversion, "CURRENTVERSION");
kwd_mgr.New(BoolUtl.N, Xol_kwd_grp_.Id_url_urlencode, "urlencode");
kwd_mgr.New(BoolUtl.N, Xol_kwd_grp_.Id_url_anchorencode, "anchorencode");
kwd_mgr.New(BoolUtl.Y, Xol_kwd_grp_.Id_utc_timestamp, "CURRENTTIMESTAMP");
kwd_mgr.New(BoolUtl.Y, Xol_kwd_grp_.Id_lcl_timestamp, "LOCALTIMESTAMP");
kwd_mgr.New(BoolUtl.Y, Xol_kwd_grp_.Id_site_directionmark, "DIRECTIONMARK", "DIRMARK");
kwd_mgr.New(BoolUtl.N, Xol_kwd_grp_.Id_i18n_language, "#language");
kwd_mgr.New(BoolUtl.Y, Xol_kwd_grp_.Id_site_contentlanguage, "CONTENTLANGUAGE", "CONTENTLANG");
kwd_mgr.New(BoolUtl.Y, Xol_kwd_grp_.Id_site_pagesinnamespace, "PAGESINNAMESPACE", "PAGESINNS");
kwd_mgr.New(BoolUtl.Y, Xol_kwd_grp_.Id_num_admins, "NUMBEROFADMINS");
kwd_mgr.New(BoolUtl.N, Xol_kwd_grp_.Id_str_formatnum, "formatnum");
kwd_mgr.New(BoolUtl.N, Xol_kwd_grp_.Id_str_padleft, "padleft");
kwd_mgr.New(BoolUtl.N, Xol_kwd_grp_.Id_str_padright, "padright");
kwd_mgr.New(BoolUtl.N, Xol_kwd_grp_.Id_misc_special, "#special");
kwd_mgr.New(BoolUtl.Y, Xol_kwd_grp_.Id_page_defaultsort, "DEFAULTSORT", "DEFAULTSORTKEY", "DEFAULTCATEGORYSORT");
kwd_mgr.New(BoolUtl.N, Xol_kwd_grp_.Id_url_filepath, "filepath");
kwd_mgr.New(BoolUtl.N, Xol_kwd_grp_.Id_misc_tag, "#tag");
kwd_mgr.New(BoolUtl.Y, Xol_kwd_grp_.Id_hiddencat, "__HIDDENCAT__");
kwd_mgr.New(BoolUtl.Y, Xol_kwd_grp_.Id_site_pagesincategory, "PAGESINCATEGORY", "PAGESINCAT");
kwd_mgr.New(BoolUtl.Y, Xol_kwd_grp_.Id_rev_pagesize, "PAGESIZE");
kwd_mgr.New(BoolUtl.Y, Xol_kwd_grp_.Id_index, "__INDEX__");
kwd_mgr.New(BoolUtl.Y, Xol_kwd_grp_.Id_noindex, "__NOINDEX__");
kwd_mgr.New(BoolUtl.Y, Xol_kwd_grp_.Id_site_numberingroup, "NUMBERINGROUP", "NUMINGROUP");
kwd_mgr.New(BoolUtl.Y, Xol_kwd_grp_.Id_staticredirect, "__STATICREDIRECT__");
kwd_mgr.New(BoolUtl.Y, Xol_kwd_grp_.Id_rev_protectionlevel, "PROTECTIONLEVEL");
kwd_mgr.New(BoolUtl.N, Xol_kwd_grp_.Id_str_formatdate, "#formatdate", "#dateformat");
kwd_mgr.New(BoolUtl.N, Xol_kwd_grp_.Id_url_path, "path");
kwd_mgr.New(BoolUtl.N, Xol_kwd_grp_.Id_url_wiki, "wiki");
kwd_mgr.New(BoolUtl.N, Xol_kwd_grp_.Id_url_query, "query");
kwd_mgr.New(BoolUtl.N, Xol_kwd_grp_.Id_xtn_expr, "#expr");
kwd_mgr.New(BoolUtl.N, Xol_kwd_grp_.Id_xtn_if, "#if");
kwd_mgr.New(BoolUtl.N, Xol_kwd_grp_.Id_xtn_ifeq, "#ifeq");
kwd_mgr.New(BoolUtl.N, Xol_kwd_grp_.Id_xtn_ifexpr, "#ifexpr");
kwd_mgr.New(BoolUtl.N, Xol_kwd_grp_.Id_xtn_iferror, "#iferror");
kwd_mgr.New(BoolUtl.N, Xol_kwd_grp_.Id_xtn_switch, "#switch");
kwd_mgr.New(BoolUtl.N, Xol_kwd_grp_.Id_xtn_default, "#default");
kwd_mgr.New(BoolUtl.N, Xol_kwd_grp_.Id_xtn_ifexist, "#ifexist");
kwd_mgr.New(BoolUtl.N, Xol_kwd_grp_.Id_xtn_time, "#time");
kwd_mgr.New(BoolUtl.N, Xol_kwd_grp_.Id_xtn_timel, "#timel");
kwd_mgr.New(BoolUtl.N, Xol_kwd_grp_.Id_xtn_rel2abs, "#rel2abs");
kwd_mgr.New(BoolUtl.N, Xol_kwd_grp_.Id_xtn_titleparts, "#titleparts");
kwd_mgr.New(BoolUtl.N, Xol_kwd_grp_.Id_xowa_dbg, "#xowa_dbg");
kwd_mgr.New(BoolUtl.Y, Xol_kwd_grp_.Id_ogg_noplayer, "noplayer");
kwd_mgr.New(BoolUtl.Y, Xol_kwd_grp_.Id_ogg_noicon, "noicon");
kwd_mgr.New(BoolUtl.Y, Xol_kwd_grp_.Id_ogg_thumbtime, "thumbtime");
kwd_mgr.New(BoolUtl.N, Xol_kwd_grp_.Id_xtn_geodata_coordinates, "#coordinates");
kwd_mgr.New(BoolUtl.N, Xol_kwd_grp_.Id_url_canonicalurl, "canonicalurl");
kwd_mgr.New(BoolUtl.N, Xol_kwd_grp_.Id_url_canonicalurle, "canonicalurle");
kwd_mgr.New(BoolUtl.N, Xol_kwd_grp_.Id_lst, "#lst", "#section");
kwd_mgr.New(BoolUtl.N, Xol_kwd_grp_.Id_lstx, "#lstx", "#section-x");
kwd_mgr.New(BoolUtl.N, Xol_kwd_grp_.Id_lsth, "#lsth", "#section-h");
kwd_mgr.New(BoolUtl.N, Xol_kwd_grp_.Id_invoke, "#invoke");
kwd_mgr.New(BoolUtl.N, Xol_kwd_grp_.Id_property, "#property");
kwd_mgr.New(BoolUtl.N, Xol_kwd_grp_.Id_noexternallanglinks, "noexternallanglinks");
kwd_mgr.New(BoolUtl.N, Xol_kwd_grp_.Id_ns_num, "namespacenumber");
kwd_mgr.New(BoolUtl.N, Xol_kwd_grp_.Id_page_id, "pageid");
kwd_mgr.New(BoolUtl.N, Xol_kwd_grp_.Id_disambig, "__DISAMBIG__");
kwd_mgr.New(BoolUtl.N, Xol_kwd_grp_.Id_nocommafysuffix, "NOSEP");
kwd_mgr.New(BoolUtl.N, Xol_kwd_grp_.Id_xowa, "#xowa");
kwd_mgr.New(BoolUtl.N, Xol_kwd_grp_.Id_mapSources_deg2dd, "#deg2dd");
kwd_mgr.New(BoolUtl.N, Xol_kwd_grp_.Id_mapSources_dd2dms, "#dd2dms");
kwd_mgr.New(BoolUtl.N, Xol_kwd_grp_.Id_mapSources_geoLink, "#geolink");
kwd_mgr.New(BoolUtl.N, Xol_kwd_grp_.Id_geoCrumbs_isin, "#isin");
kwd_mgr.New(BoolUtl.N, Xol_kwd_grp_.Id_relatedArticles, "#related");
kwd_mgr.New(BoolUtl.N, Xol_kwd_grp_.Id_insider, "#insider");
kwd_mgr.New(BoolUtl.N, Xol_kwd_grp_.Id_massMessage_target, "#target");
kwd_mgr.New(BoolUtl.N, Xol_kwd_grp_.Id_cascadingSources, "CASCADINGSOURCES");
kwd_mgr.New(BoolUtl.N, Xol_kwd_grp_.Id_pendingChangeLevel, "PENDINGCHANGELEVEL");
kwd_mgr.New(BoolUtl.N, Xol_kwd_grp_.Id_pagesUsingPendingChanges, "PAGESUSINGPENDINGCHANGES");
kwd_mgr.New(BoolUtl.Y, Xol_kwd_grp_.Id_bang, "!");
kwd_mgr.New(BoolUtl.N, Xol_kwd_grp_.Id_wbreponame, "wbreponame");
kwd_mgr.New(BoolUtl.Y, Xol_kwd_grp_.Id_strx_len, "#len");
kwd_mgr.New(BoolUtl.Y, Xol_kwd_grp_.Id_strx_pos, "#pos");
kwd_mgr.New(BoolUtl.Y, Xol_kwd_grp_.Id_strx_rpos, "#rpos");
kwd_mgr.New(BoolUtl.Y, Xol_kwd_grp_.Id_strx_sub, "#sub");
kwd_mgr.New(BoolUtl.Y, Xol_kwd_grp_.Id_strx_count, "#count");
kwd_mgr.New(BoolUtl.Y, Xol_kwd_grp_.Id_strx_replace, "#replace");
kwd_mgr.New(BoolUtl.Y, Xol_kwd_grp_.Id_strx_explode, "#explode");
kwd_mgr.New(BoolUtl.Y, Xol_kwd_grp_.Id_strx_urldecode, "#urldecode");
kwd_mgr.New(BoolUtl.N, Xol_kwd_grp_.Id_pagesincategory_pages, "pagesincategory_pages", "pages");
kwd_mgr.New(BoolUtl.N, Xol_kwd_grp_.Id_pagesincategory_subcats, "pagesincategory_subcats", "subcats");
kwd_mgr.New(BoolUtl.N, Xol_kwd_grp_.Id_pagesincategory_files, "pagesincategory_files", "files");
kwd_mgr.New(BoolUtl.Y, Xol_kwd_grp_.Id_rev_revisionsize, "REVISIONSIZE");
kwd_mgr.New(BoolUtl.Y, Xol_kwd_grp_.Id_pagebanner, "PAGEBANNER");	// NOTE: must be casematch; EX: in en.v, {{pagebanner}} is actually template name which calls {{PAGEBANNER}}
kwd_mgr.New(BoolUtl.Y, Xol_kwd_grp_.Id_rev_protectionexpiry, "PROTECTIONEXPIRY");
kwd_mgr.New(BoolUtl.N, Xol_kwd_grp_.Id_new_window_link, "#NewWindowLink");
kwd_mgr.New(BoolUtl.N, Xol_kwd_grp_.Id_categorytree, "#categorytree");
kwd_mgr.New(BoolUtl.N, Xol_kwd_grp_.Id_assessment, "#assessment");
kwd_mgr.New(BoolUtl.N, Xol_kwd_grp_.Id_statements, "#statements");
kwd_mgr.New(BoolUtl.N, Xol_kwd_grp_.Id_translation, "#translation");
kwd_mgr.New(BoolUtl.Y, Xol_kwd_grp_.Id_pagelanguage, "PAGELANGUAGE");
	}
}
