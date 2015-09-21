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
package gplx.xowa.langs.msgs; import gplx.*; import gplx.xowa.*; import gplx.xowa.langs.*;
import org.junit.*;
public class Xow_mainpage_finder_tst {
	@Before public void init() {fxt.Clear();}	private Xow_mainpage_finder_fxt fxt = new Xow_mainpage_finder_fxt();
	@Test   public void Mediawiki() {
		fxt.Init_mediawiki_page("Mainpage_by_mediawiki");
		fxt.Test_mainpage("Mainpage_by_mediawiki");
	}
	@Test   public void Lang() {
		fxt.Init_lang("Mainpage_by_lang");
		fxt.Test_mainpage("Mainpage_by_lang");
	}
	@Test   public void Siteinfo() {
		fxt.Init_siteinfo("Mainpage_by_siteinfo");
		fxt.Test_mainpage("Mainpage_by_siteinfo");
	}
	@Test   public void Lang_and_siteinfo() {
		fxt.Init_lang("Mainpage_by_lang");
		fxt.Init_siteinfo("Mainpage_by_siteinfo");
		fxt.Test_mainpage("Mainpage_by_lang");
	}
	@Test   public void Mediawiki_and_siteinfo() {
		fxt.Init_mediawiki_page("Mainpage_by_mediawiki");
		fxt.Init_siteinfo("Mainpage_by_siteinfo");
		fxt.Test_mainpage("Mainpage_by_mediawiki");
	}
	@Test   public void Mediawiki_and_lang_and_siteinfo() {
		fxt.Init_mediawiki_page("Mainpage_by_mediawiki");
		fxt.Init_lang("Mainpage_by_lang");
		fxt.Init_siteinfo("Mainpage_by_siteinfo");
		fxt.Test_mainpage("Mainpage_by_mediawiki");
	}
	@Test   public void Mediawiki_tmpl() {	// PURPOSE: de.wiktionary.org has "{{ns:project}}:Hauptseite"; DATE:2013-07-07
		fxt.Init_mediawiki_page("{{ns:project}}:Hauptseite");
		fxt.Test_mainpage("Wikipedia:Hauptseite");
	}
}
class Xow_mainpage_finder_fxt {
	public void Clear() {
		fxt.Reset_for_msgs();
	}	private Xop_fxt fxt = new Xop_fxt();
	public void Init_siteinfo(String mainpage_val) {
		fxt.Wiki().Props().Main_page_(Bry_.new_a7(mainpage_val));
	}
	public void Init_mediawiki_page(String mainpage_val) {
		fxt.Init_page_create(String_.new_a7(Ttl_mainpage), mainpage_val);
	}	private static final byte[] Ttl_mainpage = Bry_.new_a7("MediaWiki:Mainpage"); // TEST:
	public void Init_lang(String mainpage_val) {
		Xol_msg_itm msg_itm = fxt.Wiki().Lang().Msg_mgr().Itm_by_key_or_new(Xow_mainpage_finder.Msg_mainpage);
		msg_itm.Atrs_set(Bry_.new_a7(mainpage_val), false, false);
	}
	public void Test_mainpage(String expd) {
		byte[] actl = Xow_mainpage_finder.Find_or(fxt.Wiki(), fxt.Wiki().Props().Main_page());
		Tfds.Eq(expd, String_.new_a7(actl));
	}
}
