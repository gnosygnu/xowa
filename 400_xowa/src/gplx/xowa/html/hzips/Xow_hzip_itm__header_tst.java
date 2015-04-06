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
package gplx.xowa.html.hzips; import gplx.*; import gplx.xowa.*; import gplx.xowa.html.*;
import org.junit.*; import gplx.xowa.html.*;
public class Xow_hzip_itm__header_tst {
	@Before public void init() {fxt.Clear();} private Xow_hzip_mgr_fxt fxt = new Xow_hzip_mgr_fxt();
	@Test   public void Srl_basic() {
//			byte[][] brys = Bry_.Ary(Xow_hzip_dict.Bry_hdr_lhs, Bry_.ints_(2), Bry_.new_ascii_("A"), Xow_hzip_dict.Escape_bry);
//			fxt.Test_save(brys, "<h2><span class='mw-headline' id='A'>A<!--xo_hdr_end--></span></h2>");
//			fxt.Test_load(brys, "<h2><span class='mw-headline' id='A'>A</span></h2>");
	}
	@Test   public void Html_basic() {
//			fxt.Test_html("==A==", "<h2><span class='mw-headline' id='A'>A<!--xo_hdr_end--></span></h2>\n");
	}
	@Test   public void Srl_anchor() {
//			byte[][] brys = Bry_.Ary(Xow_hzip_dict.Bry_hdr_lhs, Bry_.ints_(2), Bry_.new_ascii_("A <a xtid='a_lnki_text_n' href=\"/wiki/B\" xowa_redlink='1'>b</a> c"), Xow_hzip_dict.Escape_bry);
//			fxt.Test_save(brys, "<h2><span class='mw-headline' id='A_b_c'>A <a xtid='a_lnki_text_n' href=\"/wiki/B\" xowa_redlink='1'>b</a> c<!--xo_hdr_end--></span></h2>");
////			fxt.Test_load(brys, "<h2><span class='mw-headline' id='A_b_c'>A <a xtid='a_lnki_text_n' href=\"/wiki/B\" xowa_redlink='1'>b</a> c<!--xo_hdr_end--></span></h2>");
	}
	@Test   public void Html_anchor() {
//			fxt.Test_html("==A [[b]] c==", "<h2><span class='mw-headline' id='A_b_c'>A <a xtid='a_lnki_text_n' href=\"/wiki/B\" xowa_redlink='1'>b</a> c<!--xo_hdr_end--></span></h2>\n");
	}
}
