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
import org.junit.*; import gplx.xowa.wikis.*; import gplx.xowa.langs.*;
public class Xow_xwiki_itm_tst {
	@Before public void init() {fxt.Clear();} 		private Xow_xwiki_itm_fxt fxt = new Xow_xwiki_itm_fxt();
	@Test   public void Commons()			{fxt.Test_new_by_mw("commons.wikimedia.org/wiki/$1"	, "commons.wikimedia.org"	, "commons.wikimedia.org/wiki/~{0}"		, Xow_domain_.Tid_int_commons		, Xol_lang_itm_.Id__unknown);}
	@Test   public void Wiktionary()		{fxt.Test_new_by_mw("fr.wiktionary.org/wiki/$1"		, "fr.wiktionary.org"		, "fr.wiktionary.org/wiki/~{0}"			, Xow_domain_.Tid_int_wiktionary	, Xol_lang_itm_.Id_fr);}
	@Test   public void Lang()				{fxt.Test_new_by_mw("fr.wikipedia.org/wiki/$1"		, "fr.wikipedia.org"		, "fr.wikipedia.org/wiki/~{0}"			, Xow_domain_.Tid_int_wikipedia	, Xol_lang_itm_.Id_fr);}
}
class Xow_xwiki_itm_fxt {
	private Bry_bfr tmp_bfr;
	private Gfo_url_parser url_parser;
	private Gfo_url tmp_url;
	private byte[] key;
	public void Clear() {
		tmp_bfr = Bry_bfr.new_(255);
		url_parser = new Gfo_url_parser();
		tmp_url = new Gfo_url();
		key = Bry_.new_ascii_("test");
	}
	public void Test_new_by_mw(String url_php, String expd_domain, String expd_url_fmt, int expd_wiki_tid, int expd_lang_tid) {
		Xow_xwiki_itm itm = Xow_xwiki_itm.new_by_mw(tmp_bfr, url_parser, tmp_url, key, Bry_.new_utf8_(url_php), key);
		Tfds.Eq(expd_domain			, String_.new_utf8_(itm.Domain_bry()));
		Tfds.Eq(expd_url_fmt		, String_.new_utf8_(itm.Url_fmt()));
		Tfds.Eq(expd_wiki_tid		, itm.Domain_tid(), "wiki");
		Tfds.Eq(expd_lang_tid		, itm.Lang_id(), "lang");
	}
}
