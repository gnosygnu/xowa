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
import org.junit.*; import gplx.xowa.langs.*;
public class Xow_wiki_abrv_tst {
	@Before public void init() {fxt.Clear();} private Xow_wiki_abrv_fxt fxt = new Xow_wiki_abrv_fxt();
	@Test  public void Parse() {
		fxt.Parse("foundationwiki"			, Xol_lang_itm_.Id__intl	, Xow_wiki_abrv_.Tid_wmforg);
		fxt.Parse("wikidatawiki"			, Xol_lang_itm_.Id__intl	, Xow_wiki_abrv_.Tid_wikidata);
		fxt.Parse("mediawikiwiki"			, Xol_lang_itm_.Id__intl	, Xow_wiki_abrv_.Tid_mediawiki);
		fxt.Parse("commonswiki"				, Xol_lang_itm_.Id__intl	, Xow_wiki_abrv_.Tid_commons);
		fxt.Parse("specieswiki"				, Xol_lang_itm_.Id__intl	, Xow_wiki_abrv_.Tid_species);
		fxt.Parse("metawiki"				, Xol_lang_itm_.Id__intl	, Xow_wiki_abrv_.Tid_meta);
		fxt.Parse("incubatorwiki"			, Xol_lang_itm_.Id__intl	, Xow_wiki_abrv_.Tid_incubator);
		fxt.Parse("enwiki"					, Xol_lang_itm_.Id_en		, Xow_wiki_abrv_.Tid_wikipedia);
		fxt.Parse("enwiktionary"			, Xol_lang_itm_.Id_en		, Xow_wiki_abrv_.Tid_wiktionary);
		fxt.Parse("enwikisource"			, Xol_lang_itm_.Id_en		, Xow_wiki_abrv_.Tid_wikisource);
		fxt.Parse("enwikibooks"				, Xol_lang_itm_.Id_en		, Xow_wiki_abrv_.Tid_wikibooks);
		fxt.Parse("enwikiversity"			, Xol_lang_itm_.Id_en		, Xow_wiki_abrv_.Tid_wikiversity);
		fxt.Parse("enwikiquote"				, Xol_lang_itm_.Id_en		, Xow_wiki_abrv_.Tid_wikiquote);
		fxt.Parse("enwikinews"				, Xol_lang_itm_.Id_en		, Xow_wiki_abrv_.Tid_wikinews);
		fxt.Parse("enwikivoyage"			, Xol_lang_itm_.Id_en		, Xow_wiki_abrv_.Tid_wikivoyage);
		fxt.Parse("frwiki"					, Xol_lang_itm_.Id_fr		, Xow_wiki_abrv_.Tid_wikipedia);
		fxt.Parse("unknown"					, Xol_lang_itm_.Id__unknown	, Xow_wiki_abrv_.Tid_null);
	}
}
class Xow_wiki_abrv_fxt {
	private Xow_wiki_abrv abrv = new Xow_wiki_abrv();
	public void Clear() {}
	public void Parse(String raw, int expd_lang_id, byte expd_domain_tid) {
		byte[] raw_bry = Bry_.new_a7(raw);
		Xow_wiki_abrv_.parse_(abrv, raw_bry, 0, raw_bry.length);
		Xol_lang_itm actl_lang_itm = abrv.Lang_itm();
		Tfds.Eq(expd_lang_id	, actl_lang_itm == null ? Xol_lang_itm_.Id__unknown :  actl_lang_itm.Id());
		Tfds.Eq(expd_domain_tid	, abrv.Domain_tid());
	}
}
