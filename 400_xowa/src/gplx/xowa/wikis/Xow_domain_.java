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
import gplx.xowa.wikis.domains.*;
import gplx.core.primitives.*;
import gplx.xowa.langs.*;
public class Xow_domain_ {
	public static final String
	  Domain_str_enwiki							= "en.wikipedia.org"
	, Domain_str_species						= "species.wikimedia.org"
	, Domain_str_commons						= "commons.wikimedia.org"
	, Domain_str_wikidata						= "www.wikidata.org"
	, Domain_str_mediawiki						= "www.mediawiki.org"
	, Domain_str_meta							= "meta.wikimedia.org"
	, Domain_str_incubator						= "incubator.wikimedia.org"
	, Domain_str_wmforg							= "wikimediafoundation.org"
	, Domain_str_home							= "home"
	;
	public static final byte[]
	  Domain_bry_enwiki							= Bry_.new_a7(Domain_str_enwiki)
	, Domain_bry_species						= Bry_.new_a7(Domain_str_species)
	, Domain_bry_commons						= Bry_.new_a7(Domain_str_commons)
	, Domain_bry_wikidata						= Bry_.new_a7(Domain_str_wikidata)
	, Domain_bry_mediawiki						= Bry_.new_a7(Domain_str_mediawiki)
	, Domain_bry_meta							= Bry_.new_a7(Domain_str_meta)
	, Domain_bry_incubator						= Bry_.new_a7(Domain_str_incubator)
	, Domain_bry_wmforg							= Bry_.new_a7(Domain_str_wmforg)
	, Domain_bry_home							= Bry_.new_a7(Domain_str_home)
	;
	public static final byte[] Seg_bry_org = Bry_.new_a7("org"), Seg_bry_www = Bry_.new_a7("www");
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
			return Bry_.Eq(raw, Xow_domain_type_.Key_bry_home)
				? Xow_domain_uid_.To_domain(Xow_domain_uid_.Tid_xowa)
				: new_other(raw);
		}
		int dot_1 = Bry_finder.Find_fwd(raw, Byte_ascii.Dot, dot_0 + 1, raw_len);
		if (dot_1 == Bry_.NotFound) {	// 1 dot; check for "wikimediafoundation.org"
			return Bry_.Match(raw, 0, dot_0, Xow_domain_type_.Key_bry_wmforg)
				? Xow_domain.new_(raw, Xow_domain_type_.Tid_wmfblog, Xol_lang_itm_.Key__unknown)
				: new_other(raw);
		}
		// 2 dots
		int seg_1_tid = Xow_domain_type_.Get_type_as_tid(raw, dot_0 + 1, dot_1);	// parse middle; EX: ".wikipedia."
		if (seg_1_tid == Xow_domain_type_.Tid_null) return new_other(raw);	// seg_1 is unknown; return other;
		switch (seg_1_tid) {
			case Xow_domain_type_.Tid_wikipedia: case Xow_domain_type_.Tid_wiktionary: case Xow_domain_type_.Tid_wikisource: case Xow_domain_type_.Tid_wikibooks:
			case Xow_domain_type_.Tid_wikiversity: case Xow_domain_type_.Tid_wikiquote: case Xow_domain_type_.Tid_wikinews: case Xow_domain_type_.Tid_wikivoyage:	// ~{lang}.~{type}.org
				byte[] lang_orig = Bry_.Mid(raw, 0, dot_0);
				byte[] lang_actl = Get_lang_code_for_mw_messages_file(lang_orig);
				return Xow_domain.new_orig(raw, seg_1_tid, lang_actl, lang_orig);								// NOTE: seg_tids must match wiki_tids
			case Xow_domain_type_.Tid_wikidata: case Xow_domain_type_.Tid_mediawiki:														// ~www.~{type}.org
				return Xow_domain.new_(raw, seg_1_tid, Xol_lang_itm_.Key__unknown);
			case Xow_domain_type_.Tid_wikimedia:								// ~{type}.wikimedia.org;
				int seg_0_tid = Xow_domain_type_.Get_type_as_tid(raw, 0, dot_0);	// try to get "incubator", "meta", etc..
				if (seg_0_tid == Xow_domain_type_.Tid_null) {
					Xol_lang_itm wikimedia_lang = Xol_lang_itm_.Get_by_key_or_null(raw, 0, dot_0);
					return wikimedia_lang == null ? new_other(raw) : Xow_domain.new_(raw, Xow_domain_type_.Tid_wikimedia, wikimedia_lang.Key());
				}
				switch (seg_0_tid) {
					case Xow_domain_type_.Tid_commons: case Xow_domain_type_.Tid_species: case Xow_domain_type_.Tid_meta: case Xow_domain_type_.Tid_incubator:
						return Xow_domain.new_(raw, seg_0_tid, Xol_lang_itm_.Key__unknown);						// NOTE: seg_tids must match wiki_tids; NOTE: lang_key is "en" (really, "multi" but making things easier)
					default:
						return new_other(raw);
				}
			case Xow_domain_type_.Tid_other:
			default:
				return new_other(raw);
		}
	}
	private static Xow_domain new_other(byte[] raw) {return Xow_domain.new_(raw, Xow_domain_type_.Tid_other, Xol_lang_itm_.Key__unknown);}
	private static byte[] Get_lang_code_for_mw_messages_file(byte[] v) {
		Object o = lang_to_gfs_hash.Get_by_bry(v);
		return o == null ? v : (byte[])o;
	}
	private static final Hash_adp_bry lang_to_gfs_hash = Hash_adp_bry.ci_a7()	// ASCII:lang_code
	.Add_str_obj("simple"			, Bry_.new_a7("en"))
	.Add_str_obj("zh-classical"		, Bry_.new_a7("lzh"))
	.Add_str_obj("no"				, Bry_.new_a7("nb"))
	;
}
