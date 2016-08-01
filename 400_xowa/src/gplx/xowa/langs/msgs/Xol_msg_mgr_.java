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
package gplx.xowa.langs.msgs; import gplx.*; import gplx.xowa.*; import gplx.xowa.langs.*;
import gplx.core.brys.fmtrs.*;
import gplx.langs.phps.*; import gplx.xowa.parsers.*;
import gplx.xowa.apps.gfs.*;
public class Xol_msg_mgr_ {
//		public static String Get_msg_val_gui_or_null(Xol_lang_itm lang, byte[] pre, byte[] key, byte[] suf) {
//			String rv = Get_msg_val_gui_or_null(lang, pre, key, suf);
//			return rv == null ? "<" + String_.new_u8(Bry_.Add(pre, key, suf)) + ">" : rv;
//		}
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
	public static byte[] Get_msg_val(Xowe_wiki wiki, Xol_lang_itm lang, byte[] msg_key, byte[][] fmt_args) {
		Bry_bfr tmp_bfr = wiki.Utl__bfr_mkr().Get_b512();
		Xol_msg_itm msg_itm = Get_msg_itm(tmp_bfr, wiki, lang, msg_key);
		byte[] rv = Get_msg_val(tmp_bfr, wiki, msg_itm, fmt_args);
		tmp_bfr.Mkr_rls();
		return rv;
	}	private static final    byte[] Missing_bry = Bry_.new_a7("$"), Slash_bry = new byte[] {Byte_ascii.Slash};
	public static byte[] Get_msg_val(Bry_bfr tmp_bfr, Xowe_wiki wiki, Xol_msg_itm msg_itm, byte[][] fmt_args) {
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
			Xop_ctx sub_ctx = Xop_ctx.New__sub__reuse_page(wiki.Parser_mgr().Ctx()); Xop_tkn_mkr tkn_mkr = sub_ctx.Tkn_mkr();
			Xop_root_tkn sub_root = tkn_mkr.Root(msg_val);
			msg_val = wiki.Parser_mgr().Main().Expand_tmpl(sub_root, sub_ctx, tkn_mkr, msg_val);
		}
		return msg_val;
	}
	public static Xol_msg_itm Get_msg_itm(Bry_bfr tmp_bfr, Xowe_wiki wiki, Xol_lang_itm lang, byte[] msg_key) {
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
		Xoae_page msg_page = Get_msg_itm_from_db(wiki, lang, msg_key, msg_key_sub_root);
		byte[] msg_val = Bry_.Empty;
		if (msg_page.Db().Page().Exists_n()) {															// [[MediaWiki:key/fallback]] still not found; search "lang.gfs";
			Xol_msg_itm msg_in_lang = Get_msg_itm_from_gfs(wiki, lang, msg_key_sub_root);
			if (msg_in_lang == null) {
				msg_val = tmp_bfr.Add_byte(Byte_ascii.Lt).Add(msg_key).Add_byte(Byte_ascii.Gt).To_bry_and_clear();	// set val to <msg_key>
				msg_in_wiki.Src_(Xol_msg_itm.Src_missing);
			}
			else {
				msg_val = msg_in_lang.Val();
				msg_in_wiki.Src_(Xol_msg_itm.Src_lang);
			}
		}
		else {																				// page found; dump entire contents
			msg_val = Xoa_gfs_php_mgr.Xto_gfs(tmp_bfr, msg_page.Db().Text().Text_bry());	// note that MediaWiki msg's use php arg format ($1); xowa.gfs msgs are already converted
			msg_in_wiki.Src_(Xol_msg_itm.Src_wiki);
		}
		Xol_msg_itm_.update_val_(msg_in_wiki, msg_val);
		return msg_in_wiki;
	}
	private static Xoae_page Get_msg_itm_from_db(Xowe_wiki wiki, Xol_lang_itm lang, byte[] msg_key, byte[] msg_key_sub_root) {
		byte[] ns_bry = wiki.Ns_mgr().Ns_mediawiki().Name_db_w_colon();
		Xoa_ttl ttl = wiki.Ttl_parse(Bry_.Add(ns_bry, msg_key)); // ttl="MediaWiki:msg_key"; note that there may be "/lang"; EX:pl.d:Wikislownik:Bar/Archiwum_6 and newarticletext/pl
		Xoae_page rv = ttl == null ? Xoae_page.Empty : wiki.Data_mgr().Load_page_by_ttl_for_msg(ttl);
		if (rv.Db().Page().Exists_n()) {	// [[MediaWiki:key]] not found; search for [[MediaWiki:key/fallback]]
			byte[][] fallback_ary = lang.Fallback_bry_ary();
			int fallback_ary_len = fallback_ary.length;
			for (int i = 0; i < fallback_ary_len; i++) {
				byte[] fallback = fallback_ary[i];
				ttl = wiki.Ttl_parse(Bry_.Add(ns_bry, msg_key_sub_root, Slash_bry, fallback));	// ttl="MediaWiki:msg_key/fallback"
				rv = ttl == null ? Xoae_page.Empty : wiki.Data_mgr().Load_page_by_ttl_for_msg(ttl);
				if (rv.Db().Page().Exists()) break;
			}
		}
		return rv;
	}
	private static Xol_msg_itm Get_msg_itm_from_gfs(Xowe_wiki wiki, Xol_lang_itm lang, byte[] msg_key_sub_root) {
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
