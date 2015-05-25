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
public class Xow_domain_type_ {
	public static final int
	  Tid_null					=  0
	, Tid_wikipedia				=  1
	, Tid_wiktionary			=  2
	, Tid_wikisource			=  3
	, Tid_wikivoyage			=  4
	, Tid_wikiquote				=  5
	, Tid_wikibooks				=  6
	, Tid_wikiversity			=  7
	, Tid_wikinews				=  8
	, Tid_wikimedia				=  9
	, Tid_species				= 10
	, Tid_commons				= 11
	, Tid_wikidata				= 12
	, Tid_mediawiki				= 13
	, Tid_meta					= 14
	, Tid_incubator				= 15
	, Tid_wmforg				= 16
	, Tid_home					= 17
	, Tid_other					= 18
	, Tid__len					= 19
	;
	public static final String	// SERIALIZED:xowa.gfs
	  Key_str_wikipedia			= "wikipedia"
	, Key_str_wiktionary		= "wiktionary"
	, Key_str_wikisource		= "wikisource"
	, Key_str_wikivoyage		= "wikivoyage"
	, Key_str_wikiquote			= "wikiquote"
	, Key_str_wikibooks			= "wikibooks"
	, Key_str_wikiversity		= "wikiversity"
	, Key_str_wikinews			= "wikinews"
	, Key_str_wikimedia			= "wikimedia"
	, Key_str_species			= "species"
	, Key_str_commons			= "commons"
	, Key_str_wikidata			= "wikidata"
	, Key_str_mediawiki			= "mediawiki"
	, Key_str_meta				= "meta"
	, Key_str_incubator			= "incubator"
	, Key_str_wmforg			= "wikimediafoundation"
	, Key_str_home				= "home"
	, Key_str_other				= "other"
	;
	public static final byte[] 
	  Key_bry_wikipedia			= Bry_.new_a7(Key_str_wikipedia)
	, Key_bry_wiktionary		= Bry_.new_a7(Key_str_wiktionary)
	, Key_bry_wikisource		= Bry_.new_a7(Key_str_wikisource)
	, Key_bry_wikivoyage		= Bry_.new_a7(Key_str_wikivoyage)
	, Key_bry_wikiquote			= Bry_.new_a7(Key_str_wikiquote)
	, Key_bry_wikibooks			= Bry_.new_a7(Key_str_wikibooks)
	, Key_bry_wikiversity		= Bry_.new_a7(Key_str_wikiversity)
	, Key_bry_wikinews			= Bry_.new_a7(Key_str_wikinews)
	, Key_bry_wikimedia			= Bry_.new_a7(Key_str_wikimedia)
	, Key_bry_species			= Bry_.new_a7(Key_str_species)
	, Key_bry_commons			= Bry_.new_a7(Key_str_commons)
	, Key_bry_wikidata			= Bry_.new_a7(Key_str_wikidata)
	, Key_bry_mediawiki			= Bry_.new_a7(Key_str_mediawiki)
	, Key_bry_meta				= Bry_.new_a7(Key_str_meta)
	, Key_bry_incubator			= Bry_.new_a7(Key_str_incubator)
	, Key_bry_wmforg			= Bry_.new_a7(Key_str_wmforg)
	, Key_bry_home				= Bry_.new_a7(Key_str_home)
	, Key_bry_other				= Bry_.new_a7(Key_str_other)
	;
	private static final Xow_domain_type[] ary = new Xow_domain_type[Tid__len];
	private static final Hash_adp_bry type_regy = Hash_adp_bry.ci_ascii_();	// LOC:must go before new_()
	private static final Hash_adp_bry abrv_regy = Hash_adp_bry.cs_();		// LOC:must go before new_()
	public static final Xow_domain_type 
	  Itm_wikipedia				= new_(Bool_.Y	, Xow_domain_type_src_.Tid_wmf	, Tid_wikipedia			, Key_bry_wikipedia			, "w"			, ".wikipedia.org")
	, Itm_wiktionary			= new_(Bool_.Y	, Xow_domain_type_src_.Tid_wmf	, Tid_wiktionary		, Key_bry_wiktionary		, "d"			, ".wiktionary.org")
	, Itm_wikisource			= new_(Bool_.Y	, Xow_domain_type_src_.Tid_wmf	, Tid_wikisource		, Key_bry_wikisource		, "s"			, ".wikisource.org")
	, Itm_wikivoyage			= new_(Bool_.Y	, Xow_domain_type_src_.Tid_wmf	, Tid_wikivoyage		, Key_bry_wikivoyage		, "v"			, ".wikivoyage.org")
	, Itm_wikiquote				= new_(Bool_.Y	, Xow_domain_type_src_.Tid_wmf	, Tid_wikiquote			, Key_bry_wikiquote			, "q"			, ".wikiquote.org")
	, Itm_wikibooks				= new_(Bool_.Y	, Xow_domain_type_src_.Tid_wmf	, Tid_wikibooks			, Key_bry_wikibooks			, "b"			, ".wikibooks.org")
	, Itm_wikiversity			= new_(Bool_.Y	, Xow_domain_type_src_.Tid_wmf	, Tid_wikiversity		, Key_bry_wikiversity		, "u"			, ".wikiversity.org")
	, Itm_wikinews				= new_(Bool_.Y	, Xow_domain_type_src_.Tid_wmf	, Tid_wikinews			, Key_bry_wikinews			, "n"			, ".wikinews.org")
	, Itm_wikimedia				= new_(Bool_.Y	, Xow_domain_type_src_.Tid_wmf	, Tid_wikimedia			, Key_bry_wikimedia			, "m"			, ".wikimedia.org")
	, Itm_species				= new_(Bool_.Y	, Xow_domain_type_src_.Tid_wmf	, Tid_species			, Key_bry_species			, "species"		, Xow_domain_.Domain_str_species)
	, Itm_commons				= new_(Bool_.N	, Xow_domain_type_src_.Tid_wmf	, Tid_commons			, Key_bry_commons			, "c"			, Xow_domain_.Domain_str_commons)
	, Itm_wikidata				= new_(Bool_.N	, Xow_domain_type_src_.Tid_wmf	, Tid_wikidata			, Key_bry_wikidata			, "wd"			, Xow_domain_.Domain_str_wikidata)
	, Itm_mediawiki				= new_(Bool_.N	, Xow_domain_type_src_.Tid_wmf	, Tid_mediawiki			, Key_bry_mediawiki			, "mw"			, Xow_domain_.Domain_str_mediawiki)
	, Itm_meta					= new_(Bool_.N	, Xow_domain_type_src_.Tid_wmf	, Tid_meta				, Key_bry_meta				, "meta"		, Xow_domain_.Domain_str_meta)
	, Itm_incubator				= new_(Bool_.N	, Xow_domain_type_src_.Tid_wmf	, Tid_incubator			, Key_bry_incubator			, "qb"			, Xow_domain_.Domain_str_incubator)
	, Itm_wmforg				= new_(Bool_.N	, Xow_domain_type_src_.Tid_wmf	, Tid_wmforg			, Key_bry_wmforg			, "wmf"			, Xow_domain_.Domain_str_wmforg)
	, Itm_home					= new_(Bool_.N	, Xow_domain_type_src_.Tid_xowa	, Tid_home				, Key_bry_home				, "home"		, Xow_domain_.Domain_str_home)
	, Itm_other					= new_(Bool_.N	, Xow_domain_type_src_.Tid_mw	, Tid_other				, Key_bry_other				, ""			, "")
	;
	private static Xow_domain_type new_(boolean multi_lang, int src, int tid, byte[] key_bry, String abrv_str, String domain_bry) {
		byte[] abrv_bry = Bry_.new_u8(abrv_str);
		Xow_domain_type rv = new Xow_domain_type(multi_lang, src, tid, key_bry, abrv_bry, Bry_.new_u8(domain_bry));
		ary[tid] = rv;
		type_regy.Add(key_bry	, rv);
		abrv_regy.Add(abrv_bry	, rv);
		return rv;
	}
	public static Xow_domain_type Get_abrv_as_itm(byte[] src, int bgn, int end) {
		return (Xow_domain_type)abrv_regy.Get_by_mid(src, bgn, end);
	}
	public static Xow_domain_type Get_type_as_itm(int tid) {return ary[tid];}
	public static byte[] Get_type_as_bry(int tid) {return ary[tid].Key_bry();}
	public static int Get_type_as_tid(byte[] src) {return Get_type_as_tid(src, 0, src.length);}
	public static int Get_type_as_tid(byte[] src, int bgn, int end) {
		Object o = type_regy.Get_by_mid(src, bgn, end);
		return o == null ? Xow_domain_type_.Tid_null : ((Xow_domain_type)o).Tid();
	}
}
class Xow_domain_type_src_ {
	public static final int 
	  Tid_wmf	= 1		// administered by wmf; wikipedia, etc.
	, Tid_wikia	= 2		// *.wikia.com
	, Tid_mw	= 3		// mediawiki installations not part of wmf, wikia
	, Tid_xowa	= 4		// xowa
	;
}
