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
package gplx.xowa.wikis.domains; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*;
import gplx.core.primitives.*;
import gplx.xowa.langs.*;
public class Xow_domain_itm_ {
	public static final    Xow_domain_itm[] Ary_empty = new Xow_domain_itm[0];
	public static Xow_domain_itm parse(byte[] raw) {
		/*
		~{type}.org				EX: wikimediafoundation
		~{type}.wikimedia.org	EX: commons; species; meta; incubator
		~{lang}.~{type}.org		EX: en.wikipedia, etc;
		~www.~{type}.org		EX: mediawiki; wikidata;
		*/
		int raw_len = raw.length;
		int dot_0 = Bry_find_.Find_fwd(raw, Byte_ascii.Dot, 0, raw_len);
		if (dot_0 == Bry_find_.Not_found) {	// 0 dots; check for "home"
			return Bry_.Eq(raw, Xow_domain_tid_.Bry__home)
				? Xow_domain_uid_.To_domain(Xow_domain_uid_.Tid_xowa)
				: new_other(raw);
		}
		int dot_1 = Bry_find_.Find_fwd(raw, Byte_ascii.Dot, dot_0 + 1, raw_len);
		if (dot_1 == Bry_find_.Not_found) {	// 1 dot; check for "wikimediafoundation.org"
			return Bry_.Match(raw, 0, dot_0, Xow_domain_tid_.Bry__wmforg)
				? Xow_domain_itm.new_(raw, Xow_domain_tid_.Tid__wmfblog, Xol_lang_stub_.Key__unknown)
				: new_other(raw);
		}
		// 2 dots
		int seg_1_tid = Xow_domain_tid_.Get_type_as_tid(raw, dot_0 + 1, dot_1);	// parse middle; EX: ".wikipedia."
		if (seg_1_tid == Xow_domain_tid_.Tid__null) return new_other(raw);			// seg_1 is unknown; return other;
		switch (seg_1_tid) {
			case Xow_domain_tid_.Tid__wikipedia: case Xow_domain_tid_.Tid__wiktionary: case Xow_domain_tid_.Tid__wikisource: case Xow_domain_tid_.Tid__wikibooks:
			case Xow_domain_tid_.Tid__wikiversity: case Xow_domain_tid_.Tid__wikiquote: case Xow_domain_tid_.Tid__wikinews: case Xow_domain_tid_.Tid__wikivoyage:	// ~{lang}.~{type}.org
				byte[] lang_orig = Bry_.Mid(raw, 0, dot_0);
				byte[] lang_actl = Get_lang_code_for_mw_messages_file(lang_orig);
				return Xow_domain_itm.new_(raw, seg_1_tid, lang_actl, lang_orig);	// NOTE: seg_tids must match wiki_tids
			case Xow_domain_tid_.Tid__wikidata: case Xow_domain_tid_.Tid__mediawiki:// ~www.~{type}.org
				return Xow_domain_itm.new_(raw, seg_1_tid, Xol_lang_stub_.Key__unknown);
			case Xow_domain_tid_.Tid__wikimedia:									// ~{type}.wikimedia.org;
				int seg_0_tid = Xow_domain_tid_.Get_type_as_tid(raw, 0, dot_0);	// try to get "incubator", "meta", etc..
				if (seg_0_tid == Xow_domain_tid_.Tid__null) {						// not a known name; try language
					byte[] lang_override = Xow_abrv_wm_override.To_lang_key_or_null(raw);	// handle "lang-like" wikimedia domains like "ar.wikimedia.org" which is actually to "Argentina Wikimedia"
					if (lang_override == null) {
						Xol_lang_stub wikimedia_lang = Xol_lang_stub_.Get_by_key_or_null(raw, 0, dot_0);
						return wikimedia_lang == null ? new_other(raw) : Xow_domain_itm.new_(raw, Xow_domain_tid_.Tid__wikimedia, wikimedia_lang.Key());
					}
					else
						return Xow_domain_itm.new_(raw, Xow_domain_tid_.Tid__wikimedia, lang_override, Bry_.Mid(raw, 0, dot_0));
				}
				switch (seg_0_tid) {
					case Xow_domain_tid_.Tid__commons: case Xow_domain_tid_.Tid__species: case Xow_domain_tid_.Tid__meta: case Xow_domain_tid_.Tid__incubator:
						return Xow_domain_itm.new_(raw, seg_0_tid, Xol_lang_stub_.Key__unknown);						// NOTE: seg_tids must match wiki_tids; NOTE: lang_key is "en" (really, "multi" but making things easier)
					default:
						return new_other(raw);
				}
			case Xow_domain_tid_.Tid__other:
			default:
				return new_other(raw);
		}
	}
	public static boolean Match_lang(Xow_domain_itm domain, String match) {
		// exit early if "*"
		if (String_.Eq(match, Lang_key__all)) return true;

		// get lang
		String cur = String_.new_u8(domain.Lang_actl_key());

		// return true if direct match; EX: "en" <-> "en"; "de" <-> "de"
		if (String_.Eq(cur, match)) return true;

		// handle special cases
		if		(String_.Eq(match, "en"))
			return String_.In(domain.Domain_str(), "simple.wikipedia.org", "species.wikimedia.org", "www.wikidata.org", "commons.wikimedia.org");
		else if (String_.Eq(match, "zh"))
			return String_.Eq(cur, "lzh");
		return false;
	}
	public static boolean Match_type(Xow_domain_itm domain, String match) {
		// exit early if "*"
		if (String_.Eq(match, Type_key__all)) return true;

		// get lang
		String cur = domain.Domain_type().Key_str();

		// return true if direct match; EX: "wiki" <-> "wiki"; "wiktionary" <-> "wiktionary"
		if (String_.Eq(cur, match)) return true;

		// handle special cases
		if		(String_.Eq(match, "wikimisc"))
			return String_.In(domain.Domain_str(), "species.wikimedia.org", "www.wikidata.org", "commons.wikimedia.org");
		return false;
	}
	public static final    String Lang_key__all = "*", Type_key__all = "*";

	private static Xow_domain_itm new_other(byte[] raw) {return Xow_domain_itm.new_(raw, Xow_domain_tid_.Tid__other, Xol_lang_stub_.Key__unknown);}
	private static byte[] Get_lang_code_for_mw_messages_file(byte[] v) {
		Object o = alt_domain__lang_by_subdomain.Get_by_bry(v);
		return o == null ? v : (byte[])o;
	}
	public static byte[] Alt_domain__get_subdomain_by_lang(byte[] lang) {
		Object o = alt_domain__subdomain_by_lang.Get_by_bry(lang);
		return o == null ? lang : (byte[])o;
	}
	private static final    Hash_adp_bry alt_domain__lang_by_subdomain = Hash_adp_bry.ci_a7()	// ASCII:lang_code
	.Add_str_obj("simple"			, Bry_.new_a7("en"))
	.Add_str_obj("zh-classical"		, Bry_.new_a7("lzh"))
	.Add_str_obj("no"				, Bry_.new_a7("nb"))
	;
	private static final    Hash_adp_bry alt_domain__subdomain_by_lang = Hash_adp_bry.ci_a7()	// ASCII:lang_code
	.Add_str_obj("lzh"				, Bry_.new_a7("zh-classical"))
	.Add_str_obj("nb"				, Bry_.new_a7("no"))
	;
	public static final String
	  Str__enwiki								= "en.wikipedia.org"
	, Str__species								= "species.wikimedia.org"
	, Str__commons								= "commons.wikimedia.org"
	, Str__wikidata								= "www.wikidata.org"
	, Str__mediawiki							= "www.mediawiki.org"
	, Str__meta									= "meta.wikimedia.org"
	, Str__incubator							= "incubator.wikimedia.org"
	, Str__wmforg								= "wikimediafoundation.org"
	, Str__home									= "home"
	;
	public static final    byte[]
	  Bry__enwiki								= Bry_.new_a7(Str__enwiki)
	, Bry__species								= Bry_.new_a7(Str__species)
	, Bry__commons								= Bry_.new_a7(Str__commons)
	, Bry__wikidata								= Bry_.new_a7(Str__wikidata)
	, Bry__mediawiki							= Bry_.new_a7(Str__mediawiki)
	, Bry__meta									= Bry_.new_a7(Str__meta)
	, Bry__incubator							= Bry_.new_a7(Str__incubator)
	, Bry__wmforg								= Bry_.new_a7(Str__wmforg)
	, Bry__home									= Bry_.new_a7(Str__home)
	, Bry__simplewiki							= Bry_.new_a7("simple.wikipedia.org")
	;
	public static final    byte[] Seg__org = Bry_.new_a7("org"), Seg__www = Bry_.new_a7("www");
}
