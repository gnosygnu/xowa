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
package gplx.xowa.wikis.ttls; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*;
import org.junit.*;
import gplx.xowa.langs.cases.*;
public class Xow_ttl__i18n_tst {
	@Before public void init() {fxt.Reset();} private Xow_ttl_fxt fxt = new Xow_ttl_fxt();
	@Test   public void Bidi() {
		fxt.Init_ttl("A" + String_.new_utf8_(Bry_.ints_(226, 128, 142)) + "B").Expd_page_txt("AB").Test();
		fxt.Init_ttl("A" + String_.new_utf8_(Bry_.ints_(226, 128,  97)) + "B").Expd_page_txt("A" + String_.new_utf8_(Bry_.ints_(226, 128,  97)) + "B").Test();
	}
	@Test   public void Multi_byte_char2() { // PURPOSE: multi-byte HTML entity causes array out of index error; EX: w:List_of_Unicode_characters; DATE:2013-12-25
		fxt.Init_ttl("&#x2c65;").Expd_full_txt("ⱥ").Test();
	}
	@Test   public void First_char_is_multi_byte() {	// PURPOSE: if multi-byte, uppercasing is complicated; EX: µ -> Μ; DATE:2013-11-27
		fxt.Wiki().Lang().Case_mgr_utf8_();
		fxt.Init_ttl("µ").Expd_full_txt("Μ").Test();						// NOTE: this is not an ASCII "Μ"
		fxt.Init_ttl("µab").Expd_full_txt("Μab").Test();					// check that rest of title works fine
		fxt.Init_ttl("Help:µab").Expd_full_txt("Help:Μab").Test();		// check ns
		fxt.Init_ttl("Ι").Expd_full_txt("Ι").Test();						// check that Ι is not upper-cased to COMBINING GREEK YPOGEGRAMMENI; DATE:2014-02-24
	}
	@Test   public void First_char_is_multi_byte_assymetrical() { // PURPOSE: test multi-byte asymmetry (lc is 3 bytes; uc is 2 bytes)
		fxt.Wiki().Lang().Case_mgr_utf8_();
		fxt.Init_ttl("ⱥ").Expd_full_txt("Ⱥ").Test();
		fxt.Init_ttl("ⱥab").Expd_full_txt("Ⱥab").Test();				// check that rest of title works fine
		fxt.Init_ttl("Help:ⱥab").Expd_full_txt("Help:Ⱥab").Test();	// check ns
	}
}
