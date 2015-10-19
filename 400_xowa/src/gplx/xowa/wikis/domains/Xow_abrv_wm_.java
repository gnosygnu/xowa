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
package gplx.xowa.wikis.domains; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*;
import gplx.core.primitives.*; import gplx.core.btries.*;
import gplx.xowa.langs.*;
public class Xow_abrv_wm_ {
	public static Xow_abrv_wm Parse_to_abrv_or_null(byte[] src) {	// EX: parse "enwiki" to abrv_itm
		if (src == null) return null;			
		int src_len = src.length; if (src_len == 0) return null;	// empty bry
		Object o = bry_trie.Match_bgn(src, src_len - 1, -1); if (o == null) return null;
		Xow_abrv_wm rv = Xow_abrv_wm_override.To_abrv_itm_or_null(src); if (rv != null) return rv;
		int domain_type = ((Int_obj_ref)o).Val();
		Xol_lang_stub lang_itm = Xol_lang_stub_.Get_by_key_or_intl(src, 0, bry_trie.Match_pos() + 1);
		return new Xow_abrv_wm(src, lang_itm.Key(), lang_itm, domain_type);
	}
	public static Xow_domain_itm Parse_to_domain_itm(byte[] src) {	// EX: parse "enwiki" to "en.wikipedia.org" itm
		if (Bry_.Has(src, Byte_ascii.Underline))	// convert "_" to "-"; note that wmf_keys have a strict format of langtype; EX: "zh_yuewiki"; DATE:2014-10-06
			src = Bry_.Replace_create(src, Byte_ascii.Underline, Byte_ascii.Dash);
		return Xow_domain_itm_.parse(Xow_abrv_wm_.Parse_to_domain_bry(src));
	}
	public static byte[] Parse_to_domain_bry(byte[] src) {			// EX: parse "enwiki" to en.wikipedia.org
		if (src == null) return null;
		int src_len = src.length; if (src_len == 0) return null;	// empty bry
		Object o = bry_trie.Match_bgn(src, src_len - 1, - 1); if (o == null) return null;
		int domain_type = -1;
		byte[] lang = null;
		Xow_abrv_wm rv = Xow_abrv_wm_override.To_abrv_itm_or_null(src);
		if (rv != null) {
			lang = rv.Lang_domain();
			domain_type = rv.Domain_type();
		}
		else {
			domain_type = ((Int_obj_ref)o).Val();
		}
		switch (domain_type) {
			case Xow_domain_tid_.Int__wmfblog:			return Xow_domain_itm_.Bry__wmforg;
			case Xow_domain_tid_.Int__wikidata:		return Xow_domain_itm_.Bry__wikidata;
			case Xow_domain_tid_.Int__mediawiki:		return Xow_domain_itm_.Bry__mediawiki;
			case Xow_domain_tid_.Int__commons:			return Xow_domain_itm_.Bry__commons;
			case Xow_domain_tid_.Int__species:			return Xow_domain_itm_.Bry__species;
			case Xow_domain_tid_.Int__meta:			return Xow_domain_itm_.Bry__meta;
			case Xow_domain_tid_.Int__incubator:		return Xow_domain_itm_.Bry__incubator;
			case Xow_domain_tid_.Int__wikipedia:
			case Xow_domain_tid_.Int__wiktionary:
			case Xow_domain_tid_.Int__wikisource:
			case Xow_domain_tid_.Int__wikibooks:
			case Xow_domain_tid_.Int__wikiversity:
			case Xow_domain_tid_.Int__wikiquote:
			case Xow_domain_tid_.Int__wikinews:
			case Xow_domain_tid_.Int__wikivoyage:
			case Xow_domain_tid_.Int__wikimedia:
				if (lang == null) {
					lang = Bry_.Mid(src, 0, bry_trie.Match_pos() + 1);	// en
					if (Bry_.Has(lang, Byte_ascii.Underline))	// convert "_" to "-"; note that wmf_keys have a strict format of langtype; EX: "zh_yuewiki"; DATE:2014-10-06
						lang = Bry_.Replace_create(lang, Byte_ascii.Underline, Byte_ascii.Dash);
				}
				return Bry_.Add
				( lang
				, Byte_ascii.Dot_bry							// .
				, Xow_domain_tid_.Get_type_as_bry(domain_type)	// wikipedia
				, Byte_ascii.Dot_bry							// .
				, Xow_domain_itm_.Seg__org						// org
				);
		}
		return null;
	}
	public static void To_abrv(Bry_bfr bfr, byte[] lang_key, Int_obj_ref domain_type) {
		byte[] suffix_bry = (byte[])int_hash.Get_by(domain_type); if (suffix_bry == null) return;
		switch (domain_type.Val()) {
			case Xow_domain_tid_.Int__commons:
			case Xow_domain_tid_.Int__species:
			case Xow_domain_tid_.Int__meta:
			case Xow_domain_tid_.Int__incubator:
			case Xow_domain_tid_.Int__wikidata:
			case Xow_domain_tid_.Int__mediawiki:
			case Xow_domain_tid_.Int__wmfblog:		bfr.Add(suffix_bry); break;
			case Xow_domain_tid_.Int__other:		break;
			default:								bfr.Add(lang_key).Add(suffix_bry); break;
		}
	}
	public static byte[] To_abrv(Xow_domain_itm domain_itm) {
		int tid = domain_itm.Domain_type_id();
		byte[] suffix = (byte[])int_hash.Get_by(Int_obj_ref.new_(tid)); if (suffix == null) return null;	
		switch (tid) {
			case Xow_domain_tid_.Int__commons:
			case Xow_domain_tid_.Int__species:
			case Xow_domain_tid_.Int__meta:
			case Xow_domain_tid_.Int__incubator:
			case Xow_domain_tid_.Int__wikidata:
			case Xow_domain_tid_.Int__mediawiki:
			case Xow_domain_tid_.Int__wmfblog:		return suffix;
			case Xow_domain_tid_.Int__wikipedia:
			case Xow_domain_tid_.Int__wiktionary:
			case Xow_domain_tid_.Int__wikisource:
			case Xow_domain_tid_.Int__wikibooks:
			case Xow_domain_tid_.Int__wikiversity:
			case Xow_domain_tid_.Int__wikiquote:
			case Xow_domain_tid_.Int__wikinews:
			case Xow_domain_tid_.Int__wikivoyage:
			case Xow_domain_tid_.Int__wikimedia:	return Bry_.Add(domain_itm.Lang_orig_key(), suffix);
			default:								throw Err_.new_unhandled(tid);
		}
	}
	private static final Btrie_bwd_mgr bry_trie = Init_trie();
	private static Hash_adp int_hash;
	private static Btrie_bwd_mgr Init_trie() {
		int_hash = Hash_adp_.new_();
		Btrie_bwd_mgr rv = new Btrie_bwd_mgr(false);
		Init_trie_itm(rv, int_hash, "wiki"					, Xow_domain_tid_.Int__wikipedia);
		Init_trie_itm(rv, int_hash, "wiktionary"			, Xow_domain_tid_.Int__wiktionary);
		Init_trie_itm(rv, int_hash, "wikisource"			, Xow_domain_tid_.Int__wikisource);
		Init_trie_itm(rv, int_hash, "wikibooks"				, Xow_domain_tid_.Int__wikibooks);
		Init_trie_itm(rv, int_hash, "wikiversity"			, Xow_domain_tid_.Int__wikiversity);
		Init_trie_itm(rv, int_hash, "wikiquote"				, Xow_domain_tid_.Int__wikiquote);
		Init_trie_itm(rv, int_hash, "wikinews"				, Xow_domain_tid_.Int__wikinews);
		Init_trie_itm(rv, int_hash, "wikivoyage"			, Xow_domain_tid_.Int__wikivoyage);
		Init_trie_itm(rv, int_hash, "wikimedia"				, Xow_domain_tid_.Int__wikimedia);
		Init_trie_itm(rv, int_hash, "commonswiki"			, Xow_domain_tid_.Int__commons);
		Init_trie_itm(rv, int_hash, "specieswiki"			, Xow_domain_tid_.Int__species);
		Init_trie_itm(rv, int_hash, "metawiki"				, Xow_domain_tid_.Int__meta);
		Init_trie_itm(rv, int_hash, "incubatorwiki"			, Xow_domain_tid_.Int__incubator);
		Init_trie_itm(rv, int_hash, "wikidatawiki"			, Xow_domain_tid_.Int__wikidata);
		Init_trie_itm(rv, int_hash, "mediawikiwiki"			, Xow_domain_tid_.Int__mediawiki);
		Init_trie_itm(rv, int_hash, "foundationwiki"		, Xow_domain_tid_.Int__wmfblog);
		return rv;
	}
	private static void Init_trie_itm(Btrie_bwd_mgr trie, Hash_adp hash, String str, int tid) {
		Int_obj_ref itm = Int_obj_ref.new_(tid);
		trie.Add(str, itm);
		hash.Add(itm, Bry_.new_u8(str));
	}
}
class Xow_abrv_wm_override {
	public static Xow_abrv_wm To_abrv_itm_or_null(byte[] abrv) {
		Object o = itm_hash.Get_by_bry(abrv);
		return o == null ? null : (Xow_abrv_wm)o;
	}
	public static byte[] To_lang_key_or_null(byte[] domain_bry) {
		Object o = lang_hash.Get_by_bry(domain_bry);
		return o == null ? null : (byte[])o;
	}
	private static final Hash_adp_bry itm_hash = itm_hash__make();
	private static Hash_adp_bry lang_hash;
	private static Hash_adp_bry itm_hash__make() {
		Hash_adp_bry rv = Hash_adp_bry.cs();
		lang_hash = Hash_adp_bry.cs();
		itm_hash__add(rv, lang_hash, "ar.wikimedia.org", "arwikimedia", "ar", Xol_lang_stub_.Id_es, Xow_domain_tid_.Int__wikimedia);	// NOTE: ar means Argentina not Arabic
		itm_hash__add(rv, lang_hash, "br.wikimedia.org", "brwikimedia", "br", Xol_lang_stub_.Id_es, Xow_domain_tid_.Int__wikimedia);	// NOTE: br means Brazil not Breto
		itm_hash__add(rv, lang_hash, "co.wikimedia.org", "cowikimedia", "co", Xol_lang_stub_.Id_es, Xow_domain_tid_.Int__wikimedia);	// NOTE: co means Columbia not Corsican
		itm_hash__add(rv, lang_hash, "ua.wikimedia.org", "ukwikimedia", "ua", Xol_lang_stub_.Id_uk, Xow_domain_tid_.Int__wikimedia);	// NOTE: ua means Ukrainian; NOTE: uk does not means United Kingdom (which redirects to https://wikimedia.org.uk)
		itm_hash__add(rv, lang_hash, "ca.wikimedia.org", "cawikimedia", "ca", Xol_lang_stub_.Id_en, Xow_domain_tid_.Int__wikimedia);	// NOTE: ca means Canada not Catalan
		itm_hash__add(rv, lang_hash, "be.wikimedia.org", "bewikimedia", "be", Xol_lang_stub_.Id_en, Xow_domain_tid_.Int__wikimedia);	// NOTE: be means Belgium not Belarusian
		itm_hash__add(rv, lang_hash, "se.wikimedia.org", "sewikimedia", "se", Xol_lang_stub_.Id_sv, Xow_domain_tid_.Int__wikimedia);	// NOTE: se means Swedish not Northern Sami
		return rv;
	}
	private static void itm_hash__add(Hash_adp_bry hash, Hash_adp_bry lang_hash, String domain, String raw, String lang_domain, int lang_actl, int domain_type) {
		byte[] abrv_bry = Bry_.new_u8(raw);
		Xol_lang_stub lang_actl_itm = Xol_lang_stub_.Get_by_id(lang_actl);
		Xow_abrv_wm itm = new Xow_abrv_wm(abrv_bry, Bry_.new_a7(lang_domain), lang_actl_itm, domain_type);
		hash.Add_bry_obj(abrv_bry, itm);
		lang_hash.Add_str_obj(domain, lang_actl_itm.Key());
	}
}
