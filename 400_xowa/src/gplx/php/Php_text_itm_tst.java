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
package gplx.php; import gplx.*;
import org.junit.*;
public class Php_text_itm_tst {
	@Test  public void Basic() 		{Tst_("abcde", "abcde");}
	@Test  public void Escaped() 	{Tst_("a\\$b\\\"c\\td\\ne", "a$b\"c\td\ne");}
	@Test  public void Fmt() 		{Tst_("a$1b$2c", "a~{0}b~{1}c");}
	@Test  public void Utf16() 		{Tst_("a\\u007Cd", "a|d");}
	@Test  public void Utf8_nbsp()	{Tst_("a\\xc2\\xa0d", "a\\u00c2\\u00a0d");}
	private void Tst_(String raw_str, String expd) {
		Php_text_itm_parser parser = new Php_text_itm_parser();
		ListAdp list = ListAdp_.new_();
		byte[] raw = Bry_.new_utf8_(raw_str);
		parser.Parse(list, raw);
		Bry_bfr bfr = Bry_bfr.reset_(255);
		int list_len = list.Count();
		for (int i = 0; i < list_len; i++) {
			Php_text_itm itm = (Php_text_itm)list.FetchAt(i);
			itm.Bld(bfr, raw);
		}
		Tfds.Eq(expd, bfr.XtoStrAndClear());
	}
}
