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
package gplx.xowa.mediawiki.includes.htmls; import gplx.*; import gplx.xowa.*; import gplx.xowa.mediawiki.*; import gplx.xowa.mediawiki.includes.*;
import org.junit.*; import gplx.core.tests.*;
public class Xomw_html_utl__expand_attributes__tst {
	private final    Xomw_html_utl__expand_attributes__fxt fxt = new Xomw_html_utl__expand_attributes__fxt();
	@Test   public void Basic()							{fxt.Test__expand_attributes(" a=\"b\"", "a", "b");}
}
class Xomw_html_utl__expand_attributes__fxt {
	private final    Xomw_html_utl utl = new Xomw_html_utl();
	private final    Bry_bfr bfr = Bry_bfr_.New();
	public void Test__expand_attributes(String expd, String... kvs) {
		Xomw_atr_mgr atrs = new Xomw_atr_mgr();
		int kvs_len = kvs.length;
		for (int i = 0; i < kvs_len; i += 2) {
			byte[] key = Bry_.new_a7(kvs[i]);
			byte[] val = Bry_.new_a7(kvs[i + 1]);
			Xomw_atr_itm itm = new Xomw_atr_itm(-1, key, val);
			atrs.Add(itm);
		}
		utl.Expand_attributes(bfr, atrs);
		Gftest.Eq__str(expd, bfr.To_str_and_clear());
	}
}
