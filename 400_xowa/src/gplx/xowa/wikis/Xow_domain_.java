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
import gplx.core.primitives.*;
import gplx.xowa.langs.*;
public class Xow_domain_ {
	public static final String
	  Domain_str_commons						= "commons.wikimedia.org"
	, Domain_str_enwiki							= "en.wikipedia.org"
	;
	public static final byte[]
	  Domain_bry_commons						= Bry_.new_ascii_(Domain_str_commons)
	, Domain_bry_enwiki							= Bry_.new_ascii_(Domain_str_enwiki)
	, Domain_bry_wikidata						= Bry_.new_ascii_("www.wikidata.org")
	;
	private static final byte Tid_int_null = Byte_.Max_value_127;
	public static final int
	  Tid_int_other		=  0, Tid_int_home       =  1
	, Tid_int_wikipedia =  2, Tid_int_wiktionary =  3, Tid_int_wikisource =  4, Tid_int_wikibooks =  5, Tid_int_wikiversity =  6, Tid_int_wikiquote = 7, Tid_int_wikinews = 8, Tid_int_wikivoyage = 9
	, Tid_int_commons   = 10, Tid_int_species    = 11, Tid_int_meta       = 12, Tid_int_incubator = 13
	, Tid_int_wikidata  = 14, Tid_int_mediawiki  = 15, Tid_int_wikimediafoundation = 16
	;
	public static final String 
	  Tid_str_home = "home"
	;
	private static final byte[]
	  Tid_bry_other = Bry_.new_ascii_("other")
	, Tid_bry_meta = Bry_.new_ascii_("meta")
	, Tid_bry_incubator = Bry_.new_ascii_("incubator"), Tid_bry_wikidata = Bry_.new_ascii_("wikidata")
	;
	public static final byte[] 
	  Tid_bry_home = Bry_.new_ascii_(Tid_str_home)
	, Tid_bry_wikipedia = Bry_.new_ascii_("wikipedia"), Tid_bry_wiktionary = Bry_.new_ascii_("wiktionary"), Tid_bry_wikisource = Bry_.new_ascii_("wikisource")
	, Tid_bry_wikibooks = Bry_.new_ascii_("wikibooks"), Tid_bry_wikiversity = Bry_.new_ascii_("wikiversity"), Tid_bry_wikiquote = Bry_.new_ascii_("wikiquote")
	, Tid_bry_wikinews = Bry_.new_ascii_("wikinews"), Tid_bry_wikivoyage = Bry_.new_ascii_("wikivoyage")
	, Tid_bry_commons = Bry_.new_ascii_("commons")
	, Tid_bry_wikimediafoundation = Bry_.new_ascii_("wikimediafoundation")
	, Tid_bry_species = Bry_.new_ascii_("species"), Tid_bry_mediawiki = Bry_.new_ascii_("mediawiki")
	;
	private static byte[][] Tid_bry__ary = new byte[][]
	{ Tid_bry_other, Tid_bry_home
	, Tid_bry_wikipedia, Tid_bry_wiktionary, Tid_bry_wikisource, Tid_bry_wikibooks, Tid_bry_wikiversity, Tid_bry_wikiquote, Tid_bry_wikinews, Tid_bry_wikivoyage
	, Tid_bry_commons, Tid_bry_species, Tid_bry_meta, Tid_bry_incubator
	, Tid_bry_wikidata, Tid_bry_mediawiki, Tid_bry_wikimediafoundation
	};
	public static final byte[] Seg_bry_org = Bry_.new_ascii_("org"), Seg_bry_wikimedia = Bry_.new_ascii_("wikimedia"), Seg_bry_www = Bry_.new_utf8_("www");
	private static final Hash_adp_bry hash_tid_by_bry = Hash_adp_bry.ci_ascii_()
	.Add_bry_int(Seg_bry_wikimedia				, Tid_int_null)		// NOTE: using Tid_int_null for "Tid_int_wikimedia"; see elsewhere in this file
	.Add_bry_int(Tid_bry_home					, Tid_int_home)
	.Add_bry_int(Tid_bry_commons				, Tid_int_commons)
	.Add_bry_int(Tid_bry_species				, Tid_int_species)
	.Add_bry_int(Tid_bry_meta					, Tid_int_meta)
	.Add_bry_int(Tid_bry_incubator				, Tid_int_incubator)
	.Add_bry_int(Tid_bry_wikimediafoundation	, Tid_int_wikimediafoundation)
	.Add_bry_int(Tid_bry_wikidata				, Tid_int_wikidata)
	.Add_bry_int(Tid_bry_mediawiki				, Tid_int_mediawiki)
	.Add_bry_int(Tid_bry_wikipedia				, Tid_int_wikipedia)
	.Add_bry_int(Tid_bry_wiktionary				, Tid_int_wiktionary)
	.Add_bry_int(Tid_bry_wikisource				, Tid_int_wikisource)
	.Add_bry_int(Tid_bry_wikibooks				, Tid_int_wikibooks)
	.Add_bry_int(Tid_bry_wikiversity			, Tid_int_wikiversity)
	.Add_bry_int(Tid_bry_wikiquote				, Tid_int_wikiquote)
	.Add_bry_int(Tid_bry_wikinews				, Tid_int_wikinews)
	.Add_bry_int(Tid_bry_wikivoyage				, Tid_int_wikivoyage)
	.Add_bry_int(Tid_bry_other					, Tid_int_other)
	;
	public static byte[]	Tid__get_bry(int tid) {return Tid_bry__ary[tid];}
	public static int		Tid__get_int(byte[] key) {
		Object o = hash_tid_by_bry.Get_by_bry(key);
		return o == null ? Tid_int_null : ((Int_obj_val)o).Val();
	}
	public static Xow_domain parse(byte[] raw) {
		/*
		~{type}.org				EX: wikimediafoundation
		~{type}.wikimedia.org	EX: commons; species; meta; incubator
		~{lang}.~{type}.org		EX: en.wikipedia, etc;
		~www.~{type}.org		EX: mediawiki; wikidata;
		*/
		int raw_len = raw.length;
		int dot_0 = Bry_finder.Find_fwd(raw, Byte_ascii.Dot, 0, raw_len);
		if (dot_0 == Bry_.NotFound) {	// 0 dots; check for "home"
			return Bry_.Eq(raw, Tid_bry_home)
				? Xow_domain.new_(raw, Tid_int_home, Xol_lang_itm_.Key__unknown)
				: new_other(raw);
		}
		int dot_1 = Bry_finder.Find_fwd(raw, Byte_ascii.Dot, dot_0 + 1, raw_len);
		if (dot_1 == Bry_.NotFound) {	// 1 dot; check for "wikimediafoundation.org"
			return Bry_.Match(raw, 0, dot_0, Tid_bry_wikimediafoundation)
				? Xow_domain.new_(raw, Tid_int_wikimediafoundation, Xol_lang_itm_.Key__unknown)
				: new_other(raw);
		}
		// 2 dots
		Object seg_1_obj = hash_tid_by_bry.Get_by_mid(raw, dot_0 + 1, dot_1);
		if (seg_1_obj == null) return new_other(raw); // seg_1 is unknown; should be wikimedia, or any of the other types; return other;
		int seg_1_tid = ((Int_obj_val)seg_1_obj).Val();
		switch (seg_1_tid) {
			case Tid_int_wikipedia: case Tid_int_wiktionary: case Tid_int_wikisource: case Tid_int_wikibooks:
			case Tid_int_wikiversity: case Tid_int_wikiquote: case Tid_int_wikinews: case Tid_int_wikivoyage:	// ~{lang}.~{type}.org
				byte[] lang_orig = Bry_.Mid(raw, 0, dot_0);
				byte[] lang_actl = Get_lang_code_for_mw_messages_file(lang_orig);
				return Xow_domain.new_orig(raw, seg_1_tid, lang_actl, lang_orig);								// NOTE: seg_tids must match wiki_tids
			case Tid_int_wikidata: case Tid_int_mediawiki:														// ~www.~{type}.org
				return Xow_domain.new_(raw, seg_1_tid, Xol_lang_itm_.Key__unknown);
			case Tid_int_null:																					// ~{type}.wikimedia.org; // NOTE: using Tid_int_null for "Tid_int_wikimedia"; see elsewhere in this file
				Object seg_0_obj = hash_tid_by_bry.Get_by_mid(raw, 0, dot_0); if (seg_0_obj == null) return new_other(raw);
				int seg_0_tid = ((Int_obj_val)seg_0_obj).Val();
				switch (seg_0_tid) {
					case Tid_int_commons: case Tid_int_species: case Tid_int_meta: case Tid_int_incubator:
						return Xow_domain.new_(raw, seg_0_tid, Xol_lang_itm_.Key__unknown);						// NOTE: seg_tids must match wiki_tids; NOTE: lang_key is "en" (really, "multi" but making things easier)
					default:
						return new_other(raw);
				}
			case Tid_int_other:
			default:
				return new_other(raw);
		}
	}
	private static Xow_domain new_other(byte[] raw) {return Xow_domain.new_(raw, Xow_domain_.Tid_int_other, Xol_lang_itm_.Key__unknown);}
	private static byte[] Get_lang_code_for_mw_messages_file(byte[] v) {
		Object o = lang_to_gfs_hash.Get_by_bry(v);
		return o == null ? v : (byte[])o;
	}
	private static final Hash_adp_bry lang_to_gfs_hash = Hash_adp_bry.ci_ascii_()	// ASCII:lang_code
	.Add_str_obj("simple"			, Bry_.new_ascii_("en"))
	.Add_str_obj("zh-classical"		, Bry_.new_ascii_("lzh"))
	.Add_str_obj("no"				, Bry_.new_ascii_("nb"))
	;
}
