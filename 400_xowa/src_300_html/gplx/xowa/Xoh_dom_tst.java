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
public class Xoh_dom_tst {
	@Test  public void Find_atr() {
		String src = "a <nde0 atr0=\"val0\" atr1=\"val1\"/> b <nde1 atr0=\"val3\" atr1=\"val4\"/> c";
		tst_Find(src, "nde0", "atr0", "val0", "atr1", "val1");	// match nde0
		tst_Find(src, "nde1", "atr0", "val3", "atr1", "val4");	// match nde1
		tst_Find(src, "nde0", "atr0", "val5", "atr1", null);	// wrong val
		tst_Find(src, "nde0", "atr2", "val0", "atr1", null);	// wrong key
		tst_Find(src, "nde2", "atr0", "val0", "atr1", null);	// wrong nde
	}
	@Test  public void Title_by_href() {// PURPOSE: handle content-editable=n and finding file directly for download
		Title_by_href_tst("/wiki/File:Bazille,_Frédéric_~_Le_Petit_Jardinier_(The_Little_Gardener),_c1866-67_oil_on_canvas_Museum_of_Fine_Arts,_Houston.jpg"
			, "<a href=\"lure\" xowa_title=\"wrong\"></a><a href=\"/wiki/File:Bazille,_Fr%C3%A9d%C3%A9ric_%7E_Le_Petit_Jardinier_%28The_Little_Gardener%29,_c1866-67_oil_on_canvas_Museum_of_Fine_Arts,_Houston.jpg\" xowa_title=\"find_me\"></a>"
			, "find_me"
			);
	}
	private void tst_Find(String src, String where_nde, String where_key, String where_val, String select_key, String expd) {
		Xoh_find rv = new Xoh_find();
		byte[] actl = Xoh_dom_.Query_val_by_where(rv, Bry_.new_utf8_(src), Bry_.new_utf8_(where_nde), Bry_.new_utf8_(where_key), Bry_.new_utf8_(where_val), Bry_.new_utf8_(select_key), 0);
		Tfds.Eq(expd, String_.new_utf8_(actl));
	}
	private void Title_by_href_tst(String href, String html_src, String expd) {
		Bry_bfr bfr = Bry_bfr.reset_(255);
		String actl = Xoh_dom_.Title_by_href(encoder, bfr, Bry_.new_utf8_(href), Bry_.new_utf8_(html_src));
		Tfds.Eq(expd, actl);
	}	static final Url_encoder encoder = Url_encoder.url_comma();
}
