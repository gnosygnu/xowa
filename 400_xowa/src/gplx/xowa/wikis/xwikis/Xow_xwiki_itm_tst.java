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
package gplx.xowa.wikis.xwikis; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*;
import org.junit.*; import gplx.xowa.langs.*; import gplx.xowa.wikis.domains.*;
public class Xow_xwiki_itm_tst {
	@Before public void init() {fxt.Clear();} 	private Xow_xwiki_itm_fxt fxt = new Xow_xwiki_itm_fxt();
	@Test   public void Commons()				{fxt.Test_bld("commons.wikimedia.org/wiki/$1"	, "commons.wikimedia.org"	, "commons.wikimedia.org/wiki/~{0}"		, Xow_domain_type_.Int__commons		, Xol_lang_itm_.Id__unknown);}
	@Test   public void Wiktionary()			{fxt.Test_bld("fr.wiktionary.org/wiki/$1"		, "fr.wiktionary.org"		, "fr.wiktionary.org/wiki/~{0}"			, Xow_domain_type_.Int__wiktionary	, Xol_lang_itm_.Id_fr);}
	@Test   public void Lang()					{fxt.Test_bld("fr.wikipedia.org/wiki/$1"		, "fr.wikipedia.org"		, "fr.wikipedia.org/wiki/~{0}"			, Xow_domain_type_.Int__wikipedia	, Xol_lang_itm_.Id_fr);}
}
class Xow_xwiki_itm_fxt {
	private Xow_domain_itm domain_itm;
	public void Clear() {
		domain_itm = Xow_domain_itm.new_(Bry_.new_a7("en.wikivoyage.org"), Xow_domain_type_.Int__wikivoyage, Xol_lang_.Key_en);	// NOTE: use "en.wikivoyage.org" to domain_name; needed for "Related sites"
	}
	public void Test_bld(String url, String expd_domain, String expd_url_fmt, int expd_wiki_tid, int expd_lang_tid) {
		Xow_xwiki_itm itm = Xow_xwiki_itm_bldr.I.Bld(domain_itm, domain_itm.Domain_bry(), Bry_.new_u8(url), null);
		Tfds.Eq(expd_domain			, String_.new_u8(itm.Domain_bry()));
		Tfds.Eq(expd_url_fmt		, String_.new_u8(itm.Url_fmt()));
		Tfds.Eq(expd_wiki_tid		, itm.Domain_tid(), "wiki");
		Tfds.Eq(expd_lang_tid		, itm.Lang_id(), "lang");
	}
}
