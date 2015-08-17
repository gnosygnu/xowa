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
package gplx.xowa.parsers.utils; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import org.junit.*;
public class Xop_util_tst {
	private final Xop_util_fxt fxt = new Xop_util_fxt();
	@Test  public void Basic() {
		fxt.Init_random_int_ary(Int_.Ary(240563374, 22728940, 1451248133));
		fxt.Test_uniq_bry_new("UNIQE56B4AE15AD0EC68");

		fxt.Init_random_int_ary(Int_.Ary(1363621437, 426295411, 421041101));
		fxt.Test_uniq_bry_new("UNIQ147363D968C07391");
	}
}
class Xop_util_fxt {
	private final Xop_util util = new Xop_util();
	public Xop_util_fxt Init_random_int_ary(int... v) {util.Random_int_ary_(v); return this;}
	public void Test_uniq_bry_new(String expd) {
		Tfds.Eq_str(expd, String_.new_a7(util.Uniq_bry_new()), "unique_bry");
	}
}
