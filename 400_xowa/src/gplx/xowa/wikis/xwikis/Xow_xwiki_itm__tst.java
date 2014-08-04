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
public class Xow_xwiki_itm__tst {
	@Before public void init() {fxt.Clear();} 		private Xow_xwiki_itm__fxt fxt = new Xow_xwiki_itm__fxt();
	@Test   public void Commons()			{fxt.Test_new_mw_("commons.wikimedia.org/wiki/$1"	, "commons.wikimedia.org"	, "commons.wikimedia.org/wiki/~{0}"		, Xow_wiki_domain_.Tid_commons		, Xol_lang_itm_.Id__unknown);}
	@Test   public void Wiktionary()		{fxt.Test_new_mw_("fr.wiktionary.org/wiki/$1"		, "fr.wiktionary.org"		, "fr.wiktionary.org/wiki/~{0}"			, Xow_wiki_domain_.Tid_wiktionary	, Xol_lang_itm_.Id_fr);}
	@Test   public void Lang()				{fxt.Test_new_mw_("fr.wikipedia.org/wiki/$1"		, "fr.wikipedia.org"		, "fr.wikipedia.org/wiki/~{0}"			, Xow_wiki_domain_.Tid_wikipedia	, Xol_lang_itm_.Id_fr);}
}
class Xow_xwiki_itm__fxt {
	private byte cur_wiki_tid;
	private Bry_bfr tmp_bfr;
	private Gfo_url_parser url_parser;
	private Gfo_url tmp_url;
	private byte[] key;
	public void Clear() {
		cur_wiki_tid = Xow_wiki_domain_.Tid_wikipedia;
		tmp_bfr = Bry_bfr.new_(255);
		url_parser = new Gfo_url_parser();
		tmp_url = new Gfo_url();
		key = Bry_.new_ascii_("test");
	}
	public void Test_new_mw_(String url_php, String expd_domain, String expd_url_fmt, byte expd_wiki_tid, int expd_lang_tid) {
		Xow_xwiki_itm itm = Xow_xwiki_itm_.new_mw_(tmp_bfr, url_parser, tmp_url, key, Bry_.new_utf8_(url_php), key, cur_wiki_tid);
		Tfds.Eq(expd_domain			, String_.new_utf8_(itm.Domain()));
		Tfds.Eq(expd_url_fmt		, String_.new_utf8_(itm.Fmt()));
		Tfds.Eq(expd_wiki_tid		, itm.Wiki_tid(), "wiki");
		Tfds.Eq(expd_lang_tid		, itm.Lang_id(), "lang");
	}
}
