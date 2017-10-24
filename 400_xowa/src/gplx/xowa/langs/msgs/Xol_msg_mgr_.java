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
import gplx.core.brys.fmtrs.*;
import gplx.langs.phps.*; import gplx.xowa.parsers.*;
import gplx.xowa.apps.gfs.*;
import gplx.xowa.htmls.*;
import gplx.xowa.wikis.*; import gplx.xowa.wikis.pages.dbs.*;	
public class Xol_msg_mgr_ {
	public static String Get_msg_val_gui_or_empty(Xoa_lang_mgr lang_mgr, Xol_lang_itm lang, byte[] pre, byte[] key, byte[] suf) {	// get from lang, else get from en; does not use get_msg_val to skip db lookups; should only be used for gui; DATE:2014-05-28
		String rv = Get_msg_val_gui_or_null(lang_mgr, lang, pre, key, suf);
		return rv == null ? "" : rv;
	}
	public static String Get_msg_val_gui_or(Xoa_lang_mgr lang_mgr, Xol_lang_itm lang, byte[] pre, byte[] key, byte[] suf, String or) {
		String rv = Get_msg_val_gui_or_null(lang_mgr, lang, pre, key, suf);
		return rv == null ? or : rv;
	}
	public static String Get_msg_val_gui_or_null(Xoa_lang_mgr lang_mgr, Xol_lang_itm lang, byte[] pre, byte[] key, byte[] suf) {	// get from lang, else get from en; does not use get_msg_val to skip db lookups; should only be used for gui; DATE:2014-05-28
		byte[] msg_key = Bry_.Add(pre, key, suf);
		Xol_msg_itm msg_itm = lang.Msg_mgr().Itm_by_key_or_null(msg_key);
		if (msg_itm == null)
			msg_itm = lang_mgr.Lang_en().Msg_mgr().Itm_by_key_or_null(msg_key);			
		return msg_itm == null ? null : String_.new_u8(msg_itm.Val());
	}
	public static byte[] Get_msg_val(Xow_wiki wiki, Xol_lang_itm lang, byte[] msg_key, byte[][] fmt_args) {
		Bry_bfr tmp_bfr = wiki.Utl__bfr_mkr().Get_b512();
		Xol_msg_itm msg_itm = Get_msg_itm(tmp_bfr, wiki, lang, msg_key);
		byte[] rv = (msg_itm.Defined_in_none())
			? tmp_bfr.Add_byte(Byte_ascii.Lt).Add(msg_key).Add_byte(Byte_ascii.Gt).To_bry_and_clear()	// NOTE: do not use key from msg_itm; msg_itms are case-insensitive, and val should match key exactly; EX: missing should return <missing> not <Missing> DATE:2016-08-01
			: Get_msg_val(tmp_bfr, wiki, msg_itm, fmt_args);
		tmp_bfr.Mkr_rls();
		return rv;
	}	private static final    byte[] Missing_bry = Bry_.new_a7("$"), Slash_bry = new byte[] {Byte_ascii.Slash};
	public static byte[] Get_msg_val(Bry_bfr tmp_bfr, Xow_wiki wiki, Xol_msg_itm msg_itm, byte[][] fmt_args) {
		byte[] msg_val = msg_itm.Val();
		boolean has_fmt = msg_itm.Has_fmt_arg(), has_tmpl = msg_itm.Has_tmpl_txt();
		if (!has_fmt && !has_tmpl)		// no fmt or tmpl; just add val
			return msg_val;
		if (has_fmt) {					// fmt exists; fmt first (before tmpl text); EX: Expression error: Unrecognised word "~{0}"
			Bry_fmtr tmp_fmtr = Bry_fmtr.New__tmp().Missing_bgn_(Missing_bry).Missing_end_(Bry_.Empty).Missing_adj_(1);
			tmp_fmtr.Fmt_(msg_val);
			tmp_fmtr.Bld_bfr(tmp_bfr, fmt_args);
			msg_val = tmp_bfr.To_bry_and_clear();
		}
		if (has_tmpl) {
			if (wiki.Type_is_edit()) {
				Xowe_wiki wikie = (Xowe_wiki)wiki;
				Xop_ctx sub_ctx = Xop_ctx.New__sub__reuse_page(wikie.Parser_mgr().Ctx()); Xop_tkn_mkr tkn_mkr = sub_ctx.Tkn_mkr();
				Xop_root_tkn sub_root = tkn_mkr.Root(msg_val);
				msg_val = wikie.Parser_mgr().Main().Expand_tmpl(sub_root, sub_ctx, tkn_mkr, msg_val);
			}
		}
		return msg_val;
	}
	public static Xol_msg_itm Get_msg_itm(Bry_bfr tmp_bfr, Xow_wiki wiki, Xol_lang_itm lang, byte[] msg_key) {
		byte[] msg_key_sub_root = msg_key;
		int slash_pos = Bry_find_.Find_bwd(msg_key, Byte_ascii.Slash);
		if (slash_pos != Bry_find_.Not_found) {	// key is of format "key/lang"; EX: "January/en"
			int msg_key_len = msg_key.length;
			if (slash_pos != msg_key_len) {		// get text after slash; EX: "en"
				Object o = Xol_lang_stub_.Regy().Get_by_mid(msg_key, slash_pos + 1, msg_key_len);
				if (o != null) {				// text is known lang_code;
					Xol_lang_stub lang_itm = (Xol_lang_stub)o;
					lang = wiki.App().Lang_mgr().Get_by_or_new(lang_itm.Key());		// set lang
				}
				msg_key_sub_root = Bry_.Mid(msg_key, 0, slash_pos);					// set msg to "a" (discarding "/b")
			}
		}			
		Xol_msg_itm msg_in_wiki = wiki.Msg_mgr().Get_or_null(msg_key);						// check wiki; used to be check lang, but Search_mediawiki should never be toggled on lang; DATE:2014-05-13
		if (msg_in_wiki != null) return msg_in_wiki;										// NOTE: all new msgs will Search_mediawiki once; EX: de.w:{{int:Autosumm-replace}}; DATE:2013-01-25
		msg_in_wiki = wiki.Msg_mgr().Get_or_make(msg_key);
		byte[] msg_db = Get_msg_from_db_or_null(wiki, lang, msg_key, msg_key_sub_root);
		byte[] msg_val = Bry_.Empty;
		if (msg_db == null) {															// [[MediaWiki:key/fallback]] still not found; search "lang.gfs";
			Xol_msg_itm msg_in_lang = Get_msg_itm_from_gfs(wiki, lang, msg_key_sub_root);
			if (msg_in_lang == null) {
				msg_val = tmp_bfr.Add_byte(Byte_ascii.Lt).Add(msg_key).Add_byte(Byte_ascii.Gt).To_bry_and_clear();	// set val to <msg_key>
				msg_in_wiki.Defined_in_(Xol_msg_itm.Defined_in__none);
			}
			else {
				msg_val = msg_in_lang.Val();
				msg_in_wiki.Defined_in_(Xol_msg_itm.Defined_in__lang);
			}
		}
		else {																				// page found; dump entire contents
			msg_val = Gfs_php_converter.To_gfs(tmp_bfr, msg_db);	// note that MediaWiki msg's use php arg format ($1); xowa.gfs msgs are already converted
			msg_in_wiki.Defined_in_(Xol_msg_itm.Defined_in__wiki);
		}
		Xol_msg_itm_.update_val_(msg_in_wiki, msg_val);
		return msg_in_wiki;
	}
	private static byte[] Get_msg_from_db_or_null(Xow_wiki wiki, Xol_lang_itm lang, byte[] msg_key, byte[] msg_key_sub_root) {
		byte[] ns_bry = wiki.Ns_mgr().Ns_mediawiki().Name_db_w_colon();
		Xoa_ttl ttl = wiki.Ttl_parse(Bry_.Add(ns_bry, msg_key)); // ttl="MediaWiki:msg_key"; note that there may be "/lang"; EX:pl.d:Wikislownik:Bar/Archiwum_6 and newarticletext/pl
		byte[] rv = null;
		if (ttl != null)
			rv = Load_msg_from_db_or_null(wiki, ttl);
		if (rv == null) {// [[MediaWiki:key]] not found; search for [[MediaWiki:key/fallback]]
			byte[][] fallback_ary = lang.Fallback_bry_ary();
			int fallback_ary_len = fallback_ary.length;
			for (int i = 0; i < fallback_ary_len; i++) {
				byte[] fallback = fallback_ary[i];
				ttl = wiki.Ttl_parse(Bry_.Add(ns_bry, msg_key_sub_root, Slash_bry, fallback));	// ttl="MediaWiki:msg_key/fallback"
				if (ttl != null)
					rv = Load_msg_from_db_or_null(wiki, ttl);
				if (rv != null) break;
			}
		}
		return rv;
	}
	private static byte[] Load_msg_from_db_or_null(Xow_wiki wiki, Xoa_ttl ttl) {
		Xoa_page pg = null;
		if (wiki.Type_is_edit())	// NOTE: this check only works when loading pages directly (EX:en.wikipedia.org/wiki/MediaWiki:Sidebar); however, due to way msgs load, wiki is always edit, even if html_dump; DATE:2016-09-17
			pg = ((Xowe_wiki)wiki).Data_mgr().Load_page_by_ttl_for_msg(ttl);

		// HACK: handle htmp_dump wikis when loading messages such as sidebar; DATE:2016-09-17
		if (	!wiki.Type_is_edit()							// app is drd; DATE:2016-09-23
			||	(	pg.Db().Page().Exists()						// page exists
				&&	Bry_.Len_eq_0(pg.Db().Text().Text_bry())	// but text is empty -> check html_dump
				)
			) {
			Xoh_page hpg = new Xoh_page();
			pg = hpg;				
			hpg.Ctor_by_hview(wiki, Xoa_url.New(wiki, ttl), ttl, -1);
			wiki.Html__hdump_mgr().Load_mgr().Load_by_xowh(hpg, ttl, Bool_.N);
			pg.Db().Text().Text_bry_(pg.Db().Html().Html_bry());
		}
		return pg.Db().Page().Exists() ? pg.Db().Text().Text_bry() : null;
	}
	private static Xol_msg_itm Get_msg_itm_from_gfs(Xow_wiki wiki, Xol_lang_itm lang, byte[] msg_key_sub_root) {
		Xol_msg_itm rv = lang.Msg_mgr().Itm_by_key_or_null(msg_key_sub_root);	// NOTE: should always be msg_key_sub_root; EX: "msg/lang" will never be in lang.gfs
		if (rv == null) {														// msg not found; check fallbacks; note that this is different from MW b/c when MW constructs a lang, it automatically adds all fallback msgs to the current lang
			byte[][] fallback_ary = lang.Fallback_bry_ary();
			int fallback_ary_len = fallback_ary.length;
			Xoa_lang_mgr lang_mgr = wiki.App().Lang_mgr();
			for (int i = 0; i < fallback_ary_len; i++) {
				byte[] fallback = fallback_ary[i];
				Xol_lang_itm fallback_lang = lang_mgr.Get_by(fallback);
				if (fallback_lang == null) continue;	// NOTE: en has fallback of "false"; ignore bad fallbacks;
				rv = fallback_lang.Msg_mgr().Itm_by_key_or_null(msg_key_sub_root);
				if (rv != null) break;
			}
		}
		return rv;
	}
}
