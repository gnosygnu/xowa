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
package gplx.xowa.wikis.domains;
import gplx.types.basics.utls.BryLni;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.BryFind;
import gplx.types.basics.constants.AsciiByte;
import gplx.types.basics.lists.Hash_adp_bry;
import gplx.types.basics.utls.StringUtl;
import gplx.xowa.langs.*;
public class Xow_domain_itm_ {
	public static final Xow_domain_itm[] Ary_empty = new Xow_domain_itm[0];
	public static Xow_domain_itm parse(byte[] raw) {
		/*
		~{type}.org				EX: wikimediafoundation
		~{type}.wikimedia.org	EX: commons; species; meta; incubator
		~{lang}.~{type}.org		EX: en.wikipedia, etc;
		~www.~{type}.org		EX: mediawiki; wikidata;
		*/
		int raw_len = raw.length;

		// find 1st dot
		int dot_0 = BryFind.FindFwd(raw, AsciiByte.Dot, 0, raw_len);

		// 0 dots; check for "home"
		if (dot_0 == BryFind.NotFound) {
			return BryLni.Eq(raw, Xow_domain_tid_.Bry__home)
				? Xow_domain_uid_.To_domain(Xow_domain_uid_.Tid_xowa)
				: new_other(raw);
		}

		// find 2nd dot
		int dot_1 = BryFind.FindFwd(raw, AsciiByte.Dot, dot_0 + 1, raw_len);

		// 1 dot only -> return "wikisource.org" or other
		if (dot_1 == BryFind.NotFound) {
			// wikisource.org
			if (BryLni.Eq(raw, Xow_domain_itm_.Bry__wikisource_org)) {
				return Xow_domain_itm.new_(raw, Xow_domain_tid_.Tid__wikisource_org, Xol_lang_stub_.Key__unknown);
			}
			else {
				return new_other(raw);
			}
		}

		// 2 dots
		// seg_1 is type?; EX: ".wikipedia."
		int seg_1_tid = Xow_domain_tid_.Get_type_as_tid(raw, dot_0 + 1, dot_1);

		// seg_1 is not a type -> return other
		if (seg_1_tid == Xow_domain_tid_.Tid__null) {
			return new_other(raw);
		}

		// seg_1 is known
		switch (seg_1_tid) {
			// ~{lang}.~{type}.org
			case Xow_domain_tid_.Tid__wikipedia: case Xow_domain_tid_.Tid__wiktionary: case Xow_domain_tid_.Tid__wikisource: case Xow_domain_tid_.Tid__wikibooks:
			case Xow_domain_tid_.Tid__wikiversity: case Xow_domain_tid_.Tid__wikiquote: case Xow_domain_tid_.Tid__wikinews: case Xow_domain_tid_.Tid__wikivoyage:
				byte[] lang_orig = BryLni.Mid(raw, 0, dot_0);
				byte[] lang_actl = Get_lang_code_for_mw_messages_file(lang_orig);
				return Xow_domain_itm.new_(raw, seg_1_tid, lang_actl, lang_orig); // NOTE: seg_tids must match wiki_tids

			// ~www.~{type}.org
			case Xow_domain_tid_.Tid__wikidata: case Xow_domain_tid_.Tid__mediawiki:
				return Xow_domain_itm.new_(raw, seg_1_tid, Xol_lang_stub_.Key__unknown);

			// ~{type}.wikimedia.org;
			case Xow_domain_tid_.Tid__wikimedia:					
				// seg_0 is type?; EX: "incubator", "meta", etc..
				int seg_0_tid = Xow_domain_tid_.Get_type_as_tid(raw, 0, dot_0);

				// seg_0 is not a type
				if (seg_0_tid == Xow_domain_tid_.Tid__null) {
					// seg_0 is language?; handles "lang-like" wikimedia domains like "ar.wikimedia.org" which is actually to "Argentina Wikimedia"
					byte[] lang_override = Xow_abrv_wm_override.To_lang_key_or_null(raw);

					// seg_0 is not a language
					if (lang_override == null) {
						Xol_lang_stub wikimedia_lang = Xol_lang_stub_.Get_by_key_or_null(raw, 0, dot_0);
						return wikimedia_lang == null ? new_other(raw) : Xow_domain_itm.new_(raw, Xow_domain_tid_.Tid__wikimedia, wikimedia_lang.Key());
					}
					// seg_0 is a language; use language override; EX: "ar.wikimedia.org"
					else
						return Xow_domain_itm.new_(raw, Xow_domain_tid_.Tid__wikimedia, lang_override, BryLni.Mid(raw, 0, dot_0));
				}

				// seg_0 is a type
				switch (seg_0_tid) {
					case Xow_domain_tid_.Tid__commons: case Xow_domain_tid_.Tid__species: case Xow_domain_tid_.Tid__meta:
					case Xow_domain_tid_.Tid__incubator: case Xow_domain_tid_.Tid__wikimania: case Xow_domain_tid_.Tid__wikisource_org:
					case Xow_domain_tid_.Tid__wmfblog:
						return Xow_domain_itm.new_(raw, seg_0_tid, Xol_lang_stub_.Key__unknown); // NOTE: seg_tids must match wiki_tids; NOTE: lang_key is "en" (really, "multi" but making things easier)
					default:
						return new_other(raw);
				}

			// unknown type
			case Xow_domain_tid_.Tid__other:
			default:
				return new_other(raw);
		}
	}
	public static boolean Match_lang(Xow_domain_itm domain, String match) {
		// exit early if "*"
		if (StringUtl.Eq(match, Lang_key__all)) return true;

		// get lang
		String cur = StringUtl.NewU8(domain.Lang_actl_key());

		// return true if direct match; EX: "en" <-> "en"; "de" <-> "de"
		if (StringUtl.Eq(cur, match)) return true;

		// handle special cases
		if		(StringUtl.Eq(match, "en"))
			return StringUtl.In(domain.Domain_str(), "simple.wikipedia.org", "species.wikimedia.org", "www.wikidata.org", "commons.wikimedia.org");
		else if (StringUtl.Eq(match, "zh"))
			return StringUtl.Eq(cur, "lzh");
		return false;
	}
	public static boolean Match_type(Xow_domain_itm domain, String match) {
		// exit early if "*"
		if (StringUtl.Eq(match, Type_key__all)) return true;

		// get lang
		String cur = domain.Domain_type().Key_str();

		// return true if direct match; EX: "wiki" <-> "wiki"; "wiktionary" <-> "wiktionary"
		if (StringUtl.Eq(cur, match)) return true;

		// handle special cases
		if		(StringUtl.Eq(match, "wikimisc"))
			return StringUtl.In(domain.Domain_str(), "species.wikimedia.org", "www.wikidata.org", "commons.wikimedia.org");
		return false;
	}
	public static final String Lang_key__all = "*", Type_key__all = "*";

	private static Xow_domain_itm new_other(byte[] raw) {return Xow_domain_itm.new_(raw, Xow_domain_tid_.Tid__other, Xol_lang_stub_.Key__unknown);}
	private static byte[] Get_lang_code_for_mw_messages_file(byte[] v) {
		Object o = alt_domain__lang_by_subdomain.Get_by_bry(v);
		return o == null ? v : (byte[])o;
	}
	public static byte[] Alt_domain__get_subdomain_by_lang(byte[] lang) {
		Object o = alt_domain__subdomain_by_lang.Get_by_bry(lang);
		return o == null ? lang : (byte[])o;
	}
	private static final Hash_adp_bry alt_domain__lang_by_subdomain = Hash_adp_bry.ci_a7()	// ASCII:lang_code
	.Add_str_obj("simple"			, BryUtl.NewA7("en"))
	.Add_str_obj("zh-classical"		, BryUtl.NewA7("lzh"))
	.Add_str_obj("no"				, BryUtl.NewA7("nb"))
	;
	private static final Hash_adp_bry alt_domain__subdomain_by_lang = Hash_adp_bry.ci_a7()	// ASCII:lang_code
	.Add_str_obj("lzh"				, BryUtl.NewA7("zh-classical"))
	.Add_str_obj("nb"				, BryUtl.NewA7("no"))
	;
	public static final String
	  Str__enwiki								= "en.wikipedia.org"
	, Str__species								= "species.wikimedia.org"
	, Str__commons								= "commons.wikimedia.org"
	, Str__wikidata								= "www.wikidata.org"
	, Str__mediawiki							= "www.mediawiki.org"
	, Str__meta									= "meta.wikimedia.org"
	, Str__incubator							= "incubator.wikimedia.org"
	, Str__wikimania							= "wikimania.wikimedia.org"
	, Str__wikisource_org						= "wikisource.org"
	, Str__wmforg								= "foundation.wikimedia.org"
	, Str__home									= "home"
	;
	public static final byte[]
	  Bry__enwiki								= BryUtl.NewA7(Str__enwiki)
	, Bry__species								= BryUtl.NewA7(Str__species)
	, Bry__commons								= BryUtl.NewA7(Str__commons)
	, Bry__wikidata								= BryUtl.NewA7(Str__wikidata)
	, Bry__mediawiki							= BryUtl.NewA7(Str__mediawiki)
	, Bry__meta									= BryUtl.NewA7(Str__meta)
	, Bry__incubator							= BryUtl.NewA7(Str__incubator)
	, Bry__wikimania							= BryUtl.NewA7(Str__wikimania)
	, Bry__wikisource_org						= BryUtl.NewA7(Str__wikisource_org)
	, Bry__wmforg								= BryUtl.NewA7(Str__wmforg)
	, Bry__home									= BryUtl.NewA7(Str__home)
	, Bry__simplewiki							= BryUtl.NewA7("simple.wikipedia.org")
	;
	public static final byte[] Seg__org = BryUtl.NewA7("org"), Seg__www = BryUtl.NewA7("www");
}
