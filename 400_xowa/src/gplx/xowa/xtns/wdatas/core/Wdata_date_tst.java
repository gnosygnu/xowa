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
package gplx.xowa.xtns.wdatas.core; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.wdatas.*;
import org.junit.*;
import gplx.json.*; import gplx.xowa.xtns.wdatas.core.*; import gplx.xowa.xtns.wdatas.parsers.*;
public class Wdata_date_tst {
	@Before public void init() {} private Wdata_date_fxt fxt = new Wdata_date_fxt();
	@Test   public void Parse() {
		fxt.Test_parse("+00000002001-02-03T04:05:06Z",          2001, 2, 3, 4, 5, 6);
		fxt.Test_parse("-98765432109-02-03T04:05:06Z", -98765432109L, 2, 3, 4, 5, 6);
	}
}
class Wdata_date_fxt {
	public void Test_parse(String raw, long expd_y, int expd_m, int expd_d, int expd_h, int expd_n, int expd_s) {
		Wdata_date actl_date = Wdata_date.parse(Bry_.new_ascii_(raw));
		Tfds.Eq(expd_y, actl_date.Year());
		Tfds.Eq(expd_m, actl_date.Month());
		Tfds.Eq(expd_d, actl_date.Day());
		Tfds.Eq(expd_h, actl_date.Hour());
		Tfds.Eq(expd_n, actl_date.Minute());
		Tfds.Eq(expd_s, actl_date.Second());
	}
}
