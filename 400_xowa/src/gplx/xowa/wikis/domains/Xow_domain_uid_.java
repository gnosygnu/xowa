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
	public static int To_int(Xow_domain domain) {
		int domain_tid = 0;
		switch (domain.Domain_tid()) {
			case Xow_domain_type_.Tid_home:					return Tid_xowa;
			case Xow_domain_type_.Tid_commons:				return Tid_commons;
			case Xow_domain_type_.Tid_wikidata:				return Tid_wikidata;
			case Xow_domain_type_.Tid_mediawiki:			return Tid_mediawiki;
			case Xow_domain_type_.Tid_meta:					return Tid_meta;
			case Xow_domain_type_.Tid_incubator:			return Tid_incubator;
			case Xow_domain_type_.Tid_wmfblog:				return Tid_wmfblog;
			case Xow_domain_type_.Tid_species:				return Tid_species;
			case Xow_domain_type_.Tid_wikipedia:			domain_tid = Tid_sub_wikipedia; break;
			case Xow_domain_type_.Tid_wiktionary:			domain_tid = Tid_sub_wiktionary; break;
			case Xow_domain_type_.Tid_wikisource:			domain_tid = Tid_sub_wikisource; break;
			case Xow_domain_type_.Tid_wikivoyage:			domain_tid = Tid_sub_wikivoyage; break;
			case Xow_domain_type_.Tid_wikiquote:			domain_tid = Tid_sub_wikiquote; break;
			case Xow_domain_type_.Tid_wikibooks:			domain_tid = Tid_sub_wikibooks; break;
			case Xow_domain_type_.Tid_wikiversity:			domain_tid = Tid_sub_wikiversity; break;
			case Xow_domain_type_.Tid_wikinews:				domain_tid = Tid_sub_wikinews; break;
			case Xow_domain_type_.Tid_wikimedia:			domain_tid = Tid_sub_wikimedia; break;
			default:										throw Err_.new_unhandled(domain.Domain_tid());
		}
		return	Const_system_reserved						// reserve first 100 slots
			+	domain_tid									// domain_tid assigned above
			+	(domain.Lang_uid() * Const_lang_reserved)	// reserve 20 wikis per lang
			;
	}
	public static Xow_domain To_domain(int tid) {
		switch (tid) {
			case Tid_xowa:									return Xow_domain.new_(Xow_domain_.Domain_bry_home, Xow_domain_type_.Tid_home, Xol_lang_itm_.Key__unknown);
			case Tid_commons:								return Xow_domain.new_(Xow_domain_.Domain_bry_commons, Xow_domain_type_.Tid_commons, Xol_lang_itm_.Key__unknown);
			case Tid_wikidata:								return Xow_domain.new_(Xow_domain_.Domain_bry_wikidata, Xow_domain_type_.Tid_commons, Xol_lang_itm_.Key__unknown);
			case Tid_mediawiki:								return Xow_domain.new_(Xow_domain_.Domain_bry_mediawiki, Xow_domain_type_.Tid_mediawiki, Xol_lang_itm_.Key__unknown);
			case Tid_meta:									return Xow_domain.new_(Xow_domain_.Domain_bry_meta, Xow_domain_type_.Tid_meta, Xol_lang_itm_.Key__unknown);
			case Tid_incubator:								return Xow_domain.new_(Xow_domain_.Domain_bry_incubator, Xow_domain_type_.Tid_incubator, Xol_lang_itm_.Key__unknown);
			case Tid_wmfblog:								return Xow_domain.new_(Xow_domain_.Domain_bry_wmforg, Xow_domain_type_.Tid_wmfblog, Xol_lang_itm_.Key__unknown);
			case Tid_species:								return Xow_domain.new_(Xow_domain_.Domain_bry_species, Xow_domain_type_.Tid_species, Xol_lang_itm_.Key__unknown);
		}
		int tmp = tid - Const_system_reserved;
		int lang_id = tmp / 20;
		int type_id = tmp % 20;
		int tid_int = 0; byte[] tid_bry = null;
		switch (type_id) {
			case Tid_sub_wikipedia:				tid_int = Xow_domain_type_.Tid_wikipedia;	tid_bry = Xow_domain_type_.Key_bry_wikipedia; break;
			case Tid_sub_wiktionary:			tid_int = Xow_domain_type_.Tid_wiktionary;	tid_bry = Xow_domain_type_.Key_bry_wiktionary; break;
			case Tid_sub_wikisource:			tid_int = Xow_domain_type_.Tid_wikisource;	tid_bry = Xow_domain_type_.Key_bry_wikisource; break;
			case Tid_sub_wikivoyage:			tid_int = Xow_domain_type_.Tid_wikivoyage;	tid_bry = Xow_domain_type_.Key_bry_wikivoyage; break;
			case Tid_sub_wikiquote:				tid_int = Xow_domain_type_.Tid_wikiquote;	tid_bry = Xow_domain_type_.Key_bry_wikiquote; break;
			case Tid_sub_wikibooks:				tid_int = Xow_domain_type_.Tid_wikibooks;	tid_bry = Xow_domain_type_.Key_bry_wikibooks; break;
			case Tid_sub_wikiversity:			tid_int = Xow_domain_type_.Tid_wikiversity;  tid_bry = Xow_domain_type_.Key_bry_wikiversity; break;
			case Tid_sub_wikinews:				tid_int = Xow_domain_type_.Tid_wikinews;		tid_bry = Xow_domain_type_.Key_bry_wikinews; break;
			case Tid_sub_wikimedia:				tid_int = Xow_domain_type_.Tid_wikimedia;	tid_bry = Xow_domain_type_.Key_bry_wikimedia; break;
			default:							throw Err_.new_unhandled(type_id);
		}
		Xol_lang_itm lang = Xol_lang_itm_.Get_by_id(lang_id);
		Bry_bfr bfr = Xoa_app_.Utl__bfr_mkr().Get_b128();
		bfr.Add(lang.Key()).Add_byte_dot().Add(tid_bry).Add_byte_dot().Add(Xow_domain_.Seg_bry_org);
		byte[] domain_bry = bfr.Xto_bry_and_clear();
		bfr.Mkr_rls();
		return Xow_domain.new_(domain_bry, tid_int, lang);
	}
}
