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
import gplx.core.btries.*; import gplx.xowa.wikis.*;
public class Xow_wiki_alias {
	public Io_url Fil() {return fil;} public Xow_wiki_alias Fil_(Io_url v) {fil = v; return this;} Io_url fil;
	public byte Tid() {return tid;} private byte tid;
	public byte[] Domain() {return domain;} private byte[] domain;
	public String Date() {return date;} private String date;	// needs to be String to handle "latest"
	public Xow_wiki_alias Parse(String fil_name) {return Parse(Bry_.new_ascii_(fil_name));}
	public Xow_wiki_alias Parse(byte[] fil_name) {
		int fil_name_len = fil_name.length;
		int dash_0 = Bry_finder.Find_fwd(fil_name, Byte_ascii.Dash, 0			, fil_name_len); if (dash_0 == Bry_.NotFound) throw Err_mgr._.parse_obj_(this, fil_name);
		int dash_1 = Bry_finder.Find_fwd(fil_name, Byte_ascii.Dash, dash_0 + 1	, fil_name_len); if (dash_1 == Bry_.NotFound) throw Err_mgr._.parse_obj_(this, fil_name);
		domain = Parse__domain_name(fil_name, 0, dash_0);
		date = String_.new_ascii_(fil_name, dash_0 + 1, dash_1);
		tid = Parse__tid(fil_name, dash_1 + 1, fil_name_len);
		return this;
	}
	public static int Extract_lang(byte[] src) {
		if (alias_bry_trie == null) Init_aliases();
		Object o = alias_bry_trie.Match_bgn(src, src.length - 1, 0 - 1);
		return (o == null) ? -1 : alias_bry_trie.Match_pos();
	}
	public static byte[] Build_alias(Xow_wiki_domain wiki_type) {
		if (alias_bry_trie == null) Init_aliases();
		byte tid = wiki_type.Wiki_tid();
		byte[] alias = (byte[])alias_val_hash.Fetch(Byte_obj_ref.new_(tid));
		if (alias == null) return null;	
		switch (tid) {
			case Xow_wiki_domain_.Tid_commons:
			case Xow_wiki_domain_.Tid_species:
			case Xow_wiki_domain_.Tid_meta:
			case Xow_wiki_domain_.Tid_incubator:
			case Xow_wiki_domain_.Tid_wikidata:
			case Xow_wiki_domain_.Tid_mediawiki:
			case Xow_wiki_domain_.Tid_wikimediafoundation:
				return alias;
			case Xow_wiki_domain_.Tid_wikipedia:
			case Xow_wiki_domain_.Tid_wiktionary:
			case Xow_wiki_domain_.Tid_wikisource:
			case Xow_wiki_domain_.Tid_wikibooks:
			case Xow_wiki_domain_.Tid_wikiversity:
			case Xow_wiki_domain_.Tid_wikiquote:
			case Xow_wiki_domain_.Tid_wikinews:
			case Xow_wiki_domain_.Tid_wikivoyage:
				return Bry_.Add(wiki_type.Lang_orig_key(), alias);
			default:
				throw Err_mgr._.unhandled_(tid);
		}
	}
  	public static byte[] Parse_wmf_key(byte[] src) {return Parse__domain_name(src, 0, src.length);}
	public static byte[] Parse__domain_name_null = null;
	public static byte[] Parse__domain_name(byte[] src, int bgn, int end) {
		if (end - bgn == 0) return null; // empty bry;
		if (alias_bry_trie == null) Init_aliases();
		Object o = alias_bry_trie.Match_bgn(src, end - 1, bgn - 1); if (o == null) return Parse__domain_name_null;
//																			 throw Err_mgr._.parse_(typeof(Xow_wiki_alias), src);
		byte domain_tid = ((Byte_obj_ref)o).Val();
		Bry_bfr bfr = Bry_bfr.reset_(255);
		switch (domain_tid) {
			case Domain_wikimediafoundation:	return bfr.Add(Xow_wiki_domain_.Key_wikimediafoundation_bry).Add_byte(Byte_ascii.Dot).Add(Xow_wiki_domain_.Seg_org_bry).Xto_bry_and_clear();
			case Domain_wikidata:
			case Domain_mediawiki:				return bfr.Add(Xow_wiki_domain_.Seg_www_bry).Add_byte(Byte_ascii.Dot).Add(Xow_wiki_domain_.Key_by_tid(domain_tid)).Add_byte(Byte_ascii.Dot).Add(Xow_wiki_domain_.Seg_org_bry).Xto_bry_and_clear();
			case Domain_commons:
			case Domain_species:
			case Domain_meta:
			case Domain_incubator:				return bfr.Add(Xow_wiki_domain_.Key_by_tid(domain_tid)).Add_byte(Byte_ascii.Dot).Add(Xow_wiki_domain_.Seg_wikimedia_bry).Add_byte(Byte_ascii.Dot).Add(Xow_wiki_domain_.Seg_org_bry).Xto_bry_and_clear();
			case Domain_wikipedia:
			case Domain_wiktionary:
			case Domain_wikisource:
			case Domain_wikibooks:
			case Domain_wikiversity:
			case Domain_wikiquote:
			case Domain_wikinews:
			case Domain_wikivoyage:
				bfr.Add_mid(src, 0, alias_bry_trie.Match_pos() + 1);
				bfr.Add_byte(Byte_ascii.Dot);
				return bfr.Add(Xow_wiki_domain_.Key_by_tid(domain_tid)).Add_byte(Byte_ascii.Dot).Add(Xow_wiki_domain_.Seg_org_bry).Xto_bry_and_clear();
		}
		return null;
	}
	// TODO: swap out above with below
//		public byte[] Domain() {if (domain == null) domain = Domain_init(lang_itm, domain_tid); return domain;} private byte[] domain;
//		private static byte[] Domain_init(Xol_lang_itm lang_itm, byte domain_tid) {
//			switch (domain_tid) {
//				case Xow_wiki_abrv_.Tid_wikimediafoundation:	return Xow_wiki_domain_.Url_wikimediafoundation;
//				case Xow_wiki_abrv_.Tid_wikidata:				return Xow_wiki_domain_.Url_wikidata;
//				case Xow_wiki_abrv_.Tid_mediawiki:				return Xow_wiki_domain_.Url_mediawiki;
//				case Xow_wiki_abrv_.Tid_commons:				return Xow_wiki_domain_.Url_commons;
//				case Xow_wiki_abrv_.Tid_species:				return Xow_wiki_domain_.Url_species;
//				case Xow_wiki_abrv_.Tid_meta:					return Xow_wiki_domain_.Url_meta;
//				case Xow_wiki_abrv_.Tid_incubator:				return Xow_wiki_domain_.Url_incubator;
//				case Xow_wiki_abrv_.Tid_wikipedia:
//				case Xow_wiki_abrv_.Tid_wiktionary:
//				case Xow_wiki_abrv_.Tid_wikisource:
//				case Xow_wiki_abrv_.Tid_wikibooks:
//				case Xow_wiki_abrv_.Tid_wikiversity:
//				case Xow_wiki_abrv_.Tid_wikiquote:
//				case Xow_wiki_abrv_.Tid_wikinews:
//				case Xow_wiki_abrv_.Tid_wikivoyage:
//					return Bry_.Add
//					( lang_itm.Key()								// en
//					, Byte_ascii.Dot_bry							// .
//					, Xow_wiki_domain_.Key_by_tid(domain_tid)		// wikipedia
//					, Byte_ascii.Dot_bry							// .
//					, Xow_wiki_domain_.Seg_org_bry					// org
//					);
//				default:
//					return Bry_.Empty;
//			}
//		}
	public static byte Parse__tid(String src) {return Parse__tid(Bry_.new_utf8_(src));}
	public static byte Parse__tid(byte[] src) {return Parse__tid(src, 0, src.length);}
	public static byte Parse__tid(byte[] src, int bgn, int end) {
		if (tid_bry_trie == null) Init_tids();
		Object o = tid_bry_trie.Match_bgn(src, bgn, end); if (o == null) throw Err_mgr._.parse_(Xow_wiki_alias.class, src);
		return ((Byte_obj_ref)o).Val();
	}
	public static void Build_alias_by_lang_tid(Bry_bfr bfr, byte[] lang_key, Byte_obj_ref wiki_tid) {
		if (alias_bry_trie == null) Init_aliases();
		byte[] domain_suffix = (byte[])alias_val_hash.Fetch(wiki_tid);
		if (domain_suffix == null) return;
		switch (wiki_tid.Val()) {
			case Xow_wiki_domain_.Tid_commons:
			case Xow_wiki_domain_.Tid_species:
			case Xow_wiki_domain_.Tid_meta:
			case Xow_wiki_domain_.Tid_incubator:
			case Xow_wiki_domain_.Tid_wikidata:
			case Xow_wiki_domain_.Tid_mediawiki:
			case Xow_wiki_domain_.Tid_wikimediafoundation:
				bfr.Add(domain_suffix);
				break;
			case Xow_wiki_domain_.Tid_other:
				break;
			default:
				bfr.Add(lang_key).Add(domain_suffix);
				break;
		}
	}
	public static Xow_wiki_domain parse_by_wmf_key(byte[] wmf_key) {
		if (Bry_.Has(wmf_key, Byte_ascii.Underline))	// convert "_" to "-"; note that wmf_keys have a strict format of langtype; EX: "zh_yuewiki"; DATE:2014-10-06
			wmf_key = Bry_.Replace_create(wmf_key, Byte_ascii.Underline, Byte_ascii.Dash);
		byte[] domain_bry = Parse__domain_name(wmf_key, 0, wmf_key.length);
		Xow_wiki_domain rv = Xow_wiki_domain_.parse_by_domain(domain_bry);
		rv.Wmf_key_(wmf_key);
		return rv;
	}
	public static final byte Tid_null = 0, Tid_pages_articles = 1, Tid_pages_meta_current = 2, Tid_categorylinks = 3, Tid_page_props = 4, Tid_image = 5;
	public static final String Key_null = ""
	, Key_pages_articles = "pages-articles", Key_pages_meta_current = "pages-meta-current"
	, Key_categorylinks = "categorylinks", Key_page_props = "page_props"
	, Key_image = "image"
	;
	private static final byte 
	  Domain_wikipedia = Xow_wiki_domain_.Tid_wikipedia
	, Domain_wiktionary = Xow_wiki_domain_.Tid_wiktionary
	, Domain_wikisource = Xow_wiki_domain_.Tid_wikisource
	, Domain_wikibooks = Xow_wiki_domain_.Tid_wikibooks
	, Domain_wikiversity = Xow_wiki_domain_.Tid_wikiversity
	, Domain_wikiquote = Xow_wiki_domain_.Tid_wikiquote
	, Domain_wikinews = Xow_wiki_domain_.Tid_wikinews
	, Domain_wikivoyage = Xow_wiki_domain_.Tid_wikivoyage
	, Domain_commons = Xow_wiki_domain_.Tid_commons
	, Domain_species = Xow_wiki_domain_.Tid_species
	, Domain_meta = Xow_wiki_domain_.Tid_meta
	, Domain_incubator = Xow_wiki_domain_.Tid_incubator
	, Domain_wikidata = Xow_wiki_domain_.Tid_wikidata
	, Domain_mediawiki = Xow_wiki_domain_.Tid_mediawiki
	, Domain_wikimediafoundation = Xow_wiki_domain_.Tid_wikimediafoundation;
	private static void Init_aliases() {
		alias_bry_trie = new Btrie_bwd_mgr(false);
		alias_val_hash = HashAdp_.new_();
		Init_alias("wiki"					, Domain_wikipedia);
		Init_alias("wiktionary"				, Domain_wiktionary);
		Init_alias("wikisource"				, Domain_wikisource);
		Init_alias("wikibooks"				, Domain_wikibooks);
		Init_alias("wikiversity"			, Domain_wikiversity);
		Init_alias("wikiquote"				, Domain_wikiquote);
		Init_alias("wikinews"				, Domain_wikinews);
		Init_alias("wikivoyage"				, Domain_wikivoyage);
		Init_alias("commonswiki"			, Domain_commons);
		Init_alias("specieswiki"			, Domain_species);
		Init_alias("metawiki"				, Domain_meta);
		Init_alias("incubatorwiki"			, Domain_incubator);
		Init_alias("wikidatawiki"			, Domain_wikidata);
		Init_alias("mediawikiwiki"			, Domain_mediawiki);
		Init_alias("foundationwiki"			, Domain_wikimediafoundation);
	}
	private static void Init_alias(String alias_str, byte domain) {
		byte[] alias_bry = Bry_.new_ascii_(alias_str);
		Byte_obj_ref domain_bval = Byte_obj_ref.new_(domain);
		alias_bry_trie.Add(alias_bry, domain_bval);
		alias_val_hash.Add(domain_bval, alias_bry);
	}
	private static void Init_tids() {
		tid_bry_trie = Btrie_slim_mgr.ci_ascii_();	// NOTE:ci.ascii:MW_const.en
		tid_val_hash = HashAdp_.new_();
		Init_tid(Key_pages_articles		, Tid_pages_articles);
		Init_tid(Key_pages_meta_current	, Tid_pages_meta_current);
		Init_tid(Key_categorylinks		, Tid_categorylinks);
		Init_tid(Key_page_props			, Tid_page_props);
		Init_tid(Key_image				, Tid_image);
	}
	public static String Tid_to_str(byte v) {
		switch (v) {
			case Tid_pages_articles			: return Key_pages_articles;
			case Tid_pages_meta_current		: return Key_pages_meta_current;
			case Tid_categorylinks			: return Key_categorylinks;
			case Tid_page_props				: return Key_page_props;
			case Tid_image					: return Key_image;
			default							: throw Err_.unhandled(v);
		}
	}
	private static void Init_tid(String tid_str, byte tid) {
		byte[] tid_bry = Bry_.new_ascii_(tid_str);
		Byte_obj_ref tid_val = Byte_obj_ref.new_(tid);
		tid_bry_trie.Add_obj(tid_bry, tid_val);
		tid_val_hash.Add(tid_val, tid_bry);
	}
	private static HashAdp alias_val_hash;
	private static Btrie_bwd_mgr alias_bry_trie;
	private static HashAdp tid_val_hash;
	private static Btrie_slim_mgr tid_bry_trie;
}
