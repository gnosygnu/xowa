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
import gplx.xowa.langs.*;
public class Xow_domain_uid_ {
	public static final int
	  Tid_null						= 0
	, Tid_xowa						= 1
	, Tid_commons					= 2
	, Tid_wikidata					= 3
	, Tid_mediawiki					= 20
	, Tid_meta						= 21
	, Tid_incubator					= 22
	, Tid_wmfblog					= 23
	, Tid_species					= 24
	;
	private static final int
	  Tid_sub_wikipedia				= 0
	, Tid_sub_wiktionary			= 1
	, Tid_sub_wikisource			= 2
	, Tid_sub_wikivoyage			= 3
	, Tid_sub_wikiquote				= 4
	, Tid_sub_wikibooks				= 5
	, Tid_sub_wikiversity			= 6
	, Tid_sub_wikinews				= 7
	, Tid_sub_wikimedia				= 8
	;
	private static final int Const_system_reserved = 100, Const_lang_reserved = 20;
	public static int To_int(Xow_domain_itm domain) {
		int domain_tid = 0;
		switch (domain.Domain_type_id()) {
			case Xow_domain_tid_.Tid__home:					return Tid_xowa;
			case Xow_domain_tid_.Tid__commons:				return Tid_commons;
			case Xow_domain_tid_.Tid__wikidata:				return Tid_wikidata;
			case Xow_domain_tid_.Tid__mediawiki:			return Tid_mediawiki;
			case Xow_domain_tid_.Tid__meta:					return Tid_meta;
			case Xow_domain_tid_.Tid__incubator:			return Tid_incubator;
			case Xow_domain_tid_.Tid__wmfblog:				return Tid_wmfblog;
			case Xow_domain_tid_.Tid__species:				return Tid_species;
			case Xow_domain_tid_.Tid__wikipedia:			domain_tid = Tid_sub_wikipedia; break;
			case Xow_domain_tid_.Tid__wiktionary:			domain_tid = Tid_sub_wiktionary; break;
			case Xow_domain_tid_.Tid__wikisource:			domain_tid = Tid_sub_wikisource; break;
			case Xow_domain_tid_.Tid__wikivoyage:			domain_tid = Tid_sub_wikivoyage; break;
			case Xow_domain_tid_.Tid__wikiquote:			domain_tid = Tid_sub_wikiquote; break;
			case Xow_domain_tid_.Tid__wikibooks:			domain_tid = Tid_sub_wikibooks; break;
			case Xow_domain_tid_.Tid__wikiversity:			domain_tid = Tid_sub_wikiversity; break;
			case Xow_domain_tid_.Tid__wikinews:				domain_tid = Tid_sub_wikinews; break;
			case Xow_domain_tid_.Tid__wikimedia:			domain_tid = Tid_sub_wikimedia; break;
			default:										throw Err_.new_unhandled(domain.Domain_type_id());
		}
		return	Const_system_reserved							// reserve first 100 slots
			+	domain_tid										// domain_tid assigned above
			+	(domain.Lang_actl_uid() * Const_lang_reserved)	// reserve 20 wikis per lang
			;
	}
	public static Xow_domain_itm To_domain(int tid) {
		switch (tid) {
			case Tid_xowa:									return Xow_domain_itm.new_(Xow_domain_itm_.Bry__home, Xow_domain_tid_.Tid__home, Xol_lang_stub_.Key__unknown);
			case Tid_commons:								return Xow_domain_itm.new_(Xow_domain_itm_.Bry__commons, Xow_domain_tid_.Tid__commons, Xol_lang_stub_.Key__unknown);
			case Tid_wikidata:								return Xow_domain_itm.new_(Xow_domain_itm_.Bry__wikidata, Xow_domain_tid_.Tid__commons, Xol_lang_stub_.Key__unknown);
			case Tid_mediawiki:								return Xow_domain_itm.new_(Xow_domain_itm_.Bry__mediawiki, Xow_domain_tid_.Tid__mediawiki, Xol_lang_stub_.Key__unknown);
			case Tid_meta:									return Xow_domain_itm.new_(Xow_domain_itm_.Bry__meta, Xow_domain_tid_.Tid__meta, Xol_lang_stub_.Key__unknown);
			case Tid_incubator:								return Xow_domain_itm.new_(Xow_domain_itm_.Bry__incubator, Xow_domain_tid_.Tid__incubator, Xol_lang_stub_.Key__unknown);
			case Tid_wmfblog:								return Xow_domain_itm.new_(Xow_domain_itm_.Bry__wmforg, Xow_domain_tid_.Tid__wmfblog, Xol_lang_stub_.Key__unknown);
			case Tid_species:								return Xow_domain_itm.new_(Xow_domain_itm_.Bry__species, Xow_domain_tid_.Tid__species, Xol_lang_stub_.Key__unknown);
		}
		int tmp = tid - Const_system_reserved;
		int lang_id = tmp / 20;
		int type_id = tmp % 20;
		int tid_int = 0; byte[] tid_bry = null;
		switch (type_id) {
			case Tid_sub_wikipedia:				tid_int = Xow_domain_tid_.Tid__wikipedia;	tid_bry = Xow_domain_tid_.Bry__wikipedia; break;
			case Tid_sub_wiktionary:			tid_int = Xow_domain_tid_.Tid__wiktionary;	tid_bry = Xow_domain_tid_.Bry__wiktionary; break;
			case Tid_sub_wikisource:			tid_int = Xow_domain_tid_.Tid__wikisource;	tid_bry = Xow_domain_tid_.Bry__wikisource; break;
			case Tid_sub_wikivoyage:			tid_int = Xow_domain_tid_.Tid__wikivoyage;	tid_bry = Xow_domain_tid_.Bry__wikivoyage; break;
			case Tid_sub_wikiquote:				tid_int = Xow_domain_tid_.Tid__wikiquote;	tid_bry = Xow_domain_tid_.Bry__wikiquote; break;
			case Tid_sub_wikibooks:				tid_int = Xow_domain_tid_.Tid__wikibooks;	tid_bry = Xow_domain_tid_.Bry__wikibooks; break;
			case Tid_sub_wikiversity:			tid_int = Xow_domain_tid_.Tid__wikiversity; tid_bry = Xow_domain_tid_.Bry__wikiversity; break;
			case Tid_sub_wikinews:				tid_int = Xow_domain_tid_.Tid__wikinews;	tid_bry = Xow_domain_tid_.Bry__wikinews; break;
			case Tid_sub_wikimedia:				tid_int = Xow_domain_tid_.Tid__wikimedia;	tid_bry = Xow_domain_tid_.Bry__wikimedia; break;
			default:							throw Err_.new_unhandled(type_id);
		}
		Xol_lang_stub lang = Xol_lang_stub_.Get_by_id(lang_id);
		byte[] domain_bry = Bry_.Add(lang.Key(), Byte_ascii.Dot_bry, tid_bry, Byte_ascii.Dot_bry, Xow_domain_itm_.Seg__org);
		return Xow_domain_itm.new_(domain_bry, tid_int, lang, lang.Key());
	}
}
