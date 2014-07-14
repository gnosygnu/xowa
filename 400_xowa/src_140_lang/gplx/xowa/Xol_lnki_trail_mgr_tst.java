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
package gplx.xowa; import gplx.*;
import org.junit.*;
public class Xol_lnki_trail_mgr_tst {
	@Before public void init() {fxt.Clear();} private Xol_lnki_trail_mgr_fxt fxt = new Xol_lnki_trail_mgr_fxt();
	@Test   public void Add_bulk() {
		fxt.Test_add_bulk("äöüß", "ä", "ö", "ü", "ß");
	}
}
class Xol_lnki_trail_mgr_fxt {
	public void Clear() {
		app = Xoa_app_fxt.app_();
		lang = new Xol_lang(app, Bry_.new_utf8_("fr"));
		lnki_trail_mgr = lang.Lnki_trail_mgr();
	}	private Xoa_app app; Xol_lang lang; Xol_lnki_trail_mgr lnki_trail_mgr;
	public void Test_add_bulk(String raw, String... expd_ary) {
		lnki_trail_mgr.Add_bulk(Bry_.new_utf8_(raw));
		int expd_len = expd_ary.length;
		Tfds.Eq(expd_len, lang.Lnki_trail_mgr().Count());
		for (int i = 0; i < expd_len; i++) {
			byte[] expd_bry = Bry_.new_utf8_(expd_ary[i]);
			byte[] actl_bry = (byte[])lnki_trail_mgr.Trie().Match_bgn(expd_bry, 0, expd_bry.length);
			Tfds.Eq_bry(expd_bry, actl_bry);
		}
	}
}
