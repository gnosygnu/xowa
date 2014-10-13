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
package gplx.xowa.wikis; import gplx.*; import gplx.xowa.*;
public class Xow_wiki_domain_ {
	public static byte[] Key_by_tid(byte tid) {return Key__ary[tid];}
	public static final byte Tid_by_key_null = Byte_.MaxValue_127;
	public static byte Tid_by_key(byte[] key) {
		Object o = key_hash.Get_by_bry(key);
		return o == null ? Tid_by_key_null : ((Byte_obj_val)o).Val();
	}
	public static byte Xwiki_tid(byte tid) {
		switch (tid) {
			case Tid_commons:
			case Tid_species:
			case Tid_incubator:
			case Tid_mediawiki:
			case Tid_wikimediafoundation:
			case Tid_home:						return Tid_wikipedia;	// set xwiki_tid to wikipedia; allows [[da:Page]] to point to da.wikipedia.org; PAGE:species:Puccinia; DATE:2014-09-14
			default:							return tid;
		}
	}
	public static Xow_wiki_domain new_other_(byte[] raw) {return new Xow_wiki_domain(raw, Xow_wiki_domain_.Tid_other, Xol_lang_itm_.Key__unknown);}
	public static Xow_wiki_domain parse_by_domain(byte[] raw) {
		/*
		~{type}.org				EX: wikimediafoundation
		~{type}.wikimedia.org	EX: commons; species; meta; incubator
		~{lang}.~{type}.org		EX: en.wikipedia, etc;
		~www.~{type}.org		EX: mediawiki; wikidata;
		*/
		int raw_len = raw.length;
		int dot_0 = Bry_finder.Find_fwd(raw, Byte_ascii.Dot, 0, raw_len);
		if (dot_0 == Bry_.NotFound) {	// 0 dots; check for "home"
			return Bry_.Eq(raw, Key_home_bry)
				? new Xow_wiki_domain(raw, Tid_home, Xol_lang_itm_.Key__unknown)
				: Xow_wiki_domain_.new_other_(raw);
		}
		int dot_1 = Bry_finder.Find_fwd(raw, Byte_ascii.Dot, dot_0 + 1, raw_len);
		if (dot_1 == Bry_.NotFound) {	// 1 dot; check for "wikimediafoundation.org"
			return Bry_.Match(raw, 0, dot_0, Key_wikimediafoundation_bry)
				? new Xow_wiki_domain(raw, Tid_wikimediafoundation, Xol_lang_itm_.Key__unknown)
				: Xow_wiki_domain_.new_other_(raw);
		}
		// 2 dots
		Object seg_1_obj = key_hash.Get_by_mid(raw, dot_0 + 1, dot_1);
		if (seg_1_obj == null) // seg_1 is unknown; should be wikimedia, or any of the other types; return other;
			return Xow_wiki_domain_.new_other_(raw);
		byte seg_1_tid = ((Byte_obj_val)seg_1_obj).Val();
		switch (seg_1_tid) {
			case Tid_wikipedia: case Tid_wiktionary: case Tid_wikisource: case Tid_wikibooks:
			case Tid_wikiversity: case Tid_wikiquote: case Tid_wikinews: case Tid_wikivoyage:	// ~{lang}.~{type}.org
				byte[] lang_orig = Bry_.Mid(raw, 0, dot_0);
				byte[] lang_actl = X_lang_to_gfs(lang_orig);
				return new Xow_wiki_domain(raw, seg_1_tid, lang_actl, lang_orig);				// NOTE: seg_tids must match wiki_tids
			case Tid_wikidata: case Tid_mediawiki:												// ~www.~{type}.org
				return new Xow_wiki_domain(raw, seg_1_tid, Xol_lang_itm_.Key__unknown);
			case Tid_wikimedia:																	// ~{type}.wikimedia.org
				Object seg_0_obj = key_hash.Get_by_mid(raw, 0, dot_0); if (seg_0_obj == null) return Xow_wiki_domain_.new_other_(raw);
				byte seg_0_tid = ((Byte_obj_val)seg_0_obj).Val();
				switch (seg_0_tid) {
					case Tid_commons: case Tid_species: case Tid_meta: case Tid_incubator:
						return new Xow_wiki_domain(raw, seg_0_tid, Xol_lang_itm_.Key__unknown);	// NOTE: seg_tids must match wiki_tids; NOTE: lang_key is "en" (really, "multi" but making things easier)
					default:
						return Xow_wiki_domain_.new_other_(raw);
				}
			default:
			case Tid_other:
				return Xow_wiki_domain_.new_other_(raw);
		}
	}
	private static final Hash_adp_bry lang_to_gfs_hash = Hash_adp_bry.ci_ascii_()	// ASCII:lang_code
	.Add_str_obj("simple"			, Bry_.new_ascii_("en"))
	.Add_str_obj("zh-classical"		, Bry_.new_ascii_("lzh"))
	.Add_str_obj("no"				, Bry_.new_ascii_("nb"))
	;
	private static byte[] X_lang_to_gfs(byte[] v) {
		Object o = lang_to_gfs_hash.Get_by_bry(v);
		return o == null ? v : (byte[])o;
	}
	public static final byte 
	  Tid_other		=  0, Tid_home       =  1
	, Tid_wikipedia =  2, Tid_wiktionary =  3, Tid_wikisource =  4, Tid_wikibooks =  5, Tid_wikiversity =  6, Tid_wikiquote = 7, Tid_wikinews = 8, Tid_wikivoyage = 9
	, Tid_commons   = 10, Tid_species    = 11, Tid_meta       = 12, Tid_incubator = 13
	, Tid_wikidata  = 14, Tid_mediawiki  = 15, Tid_wikimediafoundation = 16
	;
	private static final byte Tid_wikimedia = Tid_by_key_null;	// NOTE: wikimedia isn't a type, but for PERF, it will be placed in the key_hash
	public static final String Key_home_str = "home";
	private static final byte[] Key_other_bry = Bry_.new_ascii_("other");
	private static final String 
	  Str_wikipedia = "wikipedia", Str_wiktionary = "wiktionary", Str_wikisource = "wikisource", Str_wikivoyage = "wikivoyage"
	, Str_wikiquote = "wikiquote", Str_wikibooks = "wikibooks", Str_wikiversity = "wikiversity", Str_wikinews = "wikinews"
	; 
	public static final byte[] 
	  Key_home_bry = Bry_.new_ascii_(Key_home_str)
	, Key_wikipedia_bry = Bry_.new_ascii_(Str_wikipedia), Key_wiktionary_bry = Bry_.new_ascii_(Str_wiktionary), Key_wikisource_bry = Bry_.new_ascii_(Str_wikisource)
	, Key_wikibooks_bry = Bry_.new_ascii_(Str_wikibooks), Key_wikiversity_bry = Bry_.new_ascii_(Str_wikiversity), Key_wikiquote_bry = Bry_.new_ascii_(Str_wikiquote)
	, Key_wikinews_bry = Bry_.new_ascii_(Str_wikinews), Key_wikivoyage_bry = Bry_.new_ascii_(Str_wikivoyage)
	, Key_commons_bry = Bry_.new_ascii_("commons"), Key_species_bry = Bry_.new_ascii_("species"), Key_meta_bry = Bry_.new_ascii_("meta")
	, Key_incubator_bry = Bry_.new_ascii_("incubator"), Key_wikidata_bry = Bry_.new_ascii_("wikidata"), Key_mediawiki_bry = Bry_.new_ascii_("mediawiki")
	, Key_wikimediafoundation_bry = Bry_.new_ascii_("wikimediafoundation")			
	;
	public static final byte[]
	  Name_wikipedia_bry = Name_(Key_wikipedia_bry), Name_wiktionary_bry = Name_(Key_wiktionary_bry), Name_wikisource_bry = Name_(Key_wikisource_bry)
	, Name_wikibooks_bry = Name_(Key_wikibooks_bry), Name_wikiversity_bry = Name_(Key_wikiversity_bry), Name_wikiquote_bry = Name_(Key_wikiquote_bry)
	, Name_wikinews_bry = Name_(Key_wikinews_bry), Name_wikivoyage_bry = Name_(Key_wikivoyage_bry)
	;
	private static byte[] Name_(byte[] v) {return Bry_.Upper_1st(Bry_.Copy(v));}
	private static byte[][] Key__ary = new byte[][]
		{ Key_other_bry, Key_home_bry
		, Key_wikipedia_bry, Key_wiktionary_bry, Key_wikisource_bry, Key_wikibooks_bry, Key_wikiversity_bry, Key_wikiquote_bry, Key_wikinews_bry, Key_wikivoyage_bry
		, Key_commons_bry, Key_species_bry, Key_meta_bry, Key_incubator_bry
		, Key_wikidata_bry, Key_mediawiki_bry, Key_wikimediafoundation_bry
		};
	public static final byte[] Seg_org_bry = Bry_.new_ascii_("org"), Seg_wikimedia_bry = Bry_.new_ascii_("wikimedia"), Seg_www_bry = Bry_.new_utf8_("www");
	private static final Hash_adp_bry key_hash = Hash_adp_bry.ci_ascii_()
	.Add_bry_byte(Seg_wikimedia_bry, Tid_wikimedia)		// PERF: not a "key" but makes Parse quicker
	.Add_bry_byte(Key_home_bry, Tid_home)
	.Add_bry_byte(Key_commons_bry, Tid_commons)
	.Add_bry_byte(Key_species_bry, Tid_species)
	.Add_bry_byte(Key_meta_bry, Tid_meta)
	.Add_bry_byte(Key_incubator_bry, Tid_incubator)
	.Add_bry_byte(Key_wikimediafoundation_bry, Tid_wikimediafoundation)
	.Add_bry_byte(Key_wikidata_bry, Tid_wikidata)
	.Add_bry_byte(Key_mediawiki_bry, Tid_mediawiki)
	.Add_bry_byte(Key_wikipedia_bry, Tid_wikipedia)
	.Add_bry_byte(Key_wiktionary_bry, Tid_wiktionary)
	.Add_bry_byte(Key_wikisource_bry, Tid_wikisource)
	.Add_bry_byte(Key_wikibooks_bry, Tid_wikibooks)
	.Add_bry_byte(Key_wikiversity_bry, Tid_wikiversity)
	.Add_bry_byte(Key_wikiquote_bry, Tid_wikiquote)
	.Add_bry_byte(Key_wikinews_bry, Tid_wikinews)
	.Add_bry_byte(Key_wikivoyage_bry, Tid_wikivoyage)
	.Add_bry_byte(Key_other_bry, Tid_other)
	;
	public static final String
	  Str_wikidata								= "www.wikidata.org"
	;
	public static final byte[]
	  Url_commons								= Bry_.new_ascii_("commons.wikimedia.org")
	, Url_wikidata								= Bry_.new_ascii_(Str_wikidata)
	, Url_species								= Bry_.new_ascii_("species.wikimedia.org")
	, Url_mediawiki								= Bry_.new_ascii_("www.mediawiki.org")
	, Url_meta									= Bry_.new_ascii_("meta.wikimedia.org")
	, Url_incubator								= Bry_.new_ascii_("incubator.wikimedia.org")
	, Url_wikimediafoundation					= Bry_.new_ascii_("wikimediafoundation.org")
	;
}
