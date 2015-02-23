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
import gplx.core.primitives.*; import gplx.core.btries.*;
import gplx.xowa.langs.*;
public class Xow_wiki_abrv_ {
	public static boolean parse_(Xow_wiki_abrv rv, byte[] src, int bgn, int end) {
		rv.Clear();
		if (end - bgn == 0) return false; // empty bry
		Object o = trie.Match_bgn(src, end - 1, bgn - 1); if (o == null) return false;
		byte   tid				= ((Byte_obj_val)o).Val();
		Xol_lang_itm lang_itm	= Xol_lang_itm_.Get_by_key_or_intl(src, bgn, trie.Match_pos() + 1);
		rv.Ctor_by_parse(lang_itm, tid);
		return true;
	}
	public static final byte 
	  Tid_null						= Xow_domain_.Tid_int_other
	, Tid_wikipedia					= Xow_domain_.Tid_int_wikipedia
	, Tid_wiktionary				= Xow_domain_.Tid_int_wiktionary
	, Tid_wikisource				= Xow_domain_.Tid_int_wikisource
	, Tid_wikibooks					= Xow_domain_.Tid_int_wikibooks
	, Tid_wikiversity				= Xow_domain_.Tid_int_wikiversity
	, Tid_wikiquote					= Xow_domain_.Tid_int_wikiquote
	, Tid_wikinews					= Xow_domain_.Tid_int_wikinews
	, Tid_wikivoyage				= Xow_domain_.Tid_int_wikivoyage
	, Tid_commons					= Xow_domain_.Tid_int_commons
	, Tid_species					= Xow_domain_.Tid_int_species
	, Tid_meta						= Xow_domain_.Tid_int_meta
	, Tid_incubator					= Xow_domain_.Tid_int_incubator
	, Tid_wikidata					= Xow_domain_.Tid_int_wikidata
	, Tid_mediawiki					= Xow_domain_.Tid_int_mediawiki
	, Tid_wikimediafoundation		= Xow_domain_.Tid_int_wikimediafoundation
	;
	private static final Btrie_bwd_mgr trie = Init_trie();
	private static Btrie_bwd_mgr Init_trie() {
		Btrie_bwd_mgr rv = new Btrie_bwd_mgr(false);
		Init_trie_itm(rv, "wiki"					, Tid_wikipedia);
		Init_trie_itm(rv, "wiktionary"				, Tid_wiktionary);
		Init_trie_itm(rv, "wikisource"				, Tid_wikisource);
		Init_trie_itm(rv, "wikibooks"				, Tid_wikibooks);
		Init_trie_itm(rv, "wikiversity"				, Tid_wikiversity);
		Init_trie_itm(rv, "wikiquote"				, Tid_wikiquote);
		Init_trie_itm(rv, "wikinews"				, Tid_wikinews);
		Init_trie_itm(rv, "wikivoyage"				, Tid_wikivoyage);
		Init_trie_itm(rv, "commonswiki"				, Tid_commons);
		Init_trie_itm(rv, "specieswiki"				, Tid_species);
		Init_trie_itm(rv, "metawiki"				, Tid_meta);
		Init_trie_itm(rv, "incubatorwiki"			, Tid_incubator);
		Init_trie_itm(rv, "wikidatawiki"			, Tid_wikidata);
		Init_trie_itm(rv, "mediawikiwiki"			, Tid_mediawiki);
		Init_trie_itm(rv, "foundationwiki"			, Tid_wikimediafoundation);
		return rv;
	}
	private static void Init_trie_itm(Btrie_bwd_mgr rv, String abrv_name, byte abrv_tid) {
		rv.Add(abrv_name, Byte_obj_val.new_(abrv_tid));
	}
}
