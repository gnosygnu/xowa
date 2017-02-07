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
package gplx.xowa.parsers; import gplx.*; import gplx.xowa.*;
import org.junit.*;
public class Xop_ctx__tst {
	@Before public void init() {fxt.Clear();} private Xop_ctx__fxt fxt = new Xop_ctx__fxt();
	@Test  public void Src_limit_and_escape_nl() {
		fxt.Test_Src_limit_and_escape_nl("abcdefg", 4, 3, "efg");	// PURPOSE: bug fix; outOfBounds thrown; DATE:2014-03-31
	}
}
class Xop_ctx__fxt {
	public void Clear() {
	}
	public void Test_Src_limit_and_escape_nl(String src, int bgn, int limit, String expd) {
		String actl = Xop_ctx_.Src_limit_and_escape_nl(Bry_.new_u8(src), bgn, limit);
		Tfds.Eq(expd, actl);
	}
}
