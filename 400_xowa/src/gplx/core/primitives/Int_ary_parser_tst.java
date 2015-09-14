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
package gplx.core.primitives; import gplx.*; import gplx.core.*;
import org.junit.*;
public class Int_ary_parser_tst {
	@Test  public void Many()		{tst_ints("1,2,3,4,5"		, 0, 9, Int_.Ary(1, 2, 3, 4, 5));}
	@Test  public void One()		{tst_ints("1"				, 0, 1, Int_.Ary(1));}
	@Test  public void None()		{tst_ints(""				, 0, 0, Int_.Ary());}
	private void tst_ints(String raw, int bgn, int end, int[] expd) {
		int[] actl = Int_ary_parser._.Parse_ary(Bry_.new_a7(raw), bgn, end, Byte_ascii.Comma);
		Tfds.Eq_ary(expd, actl);
	}
}
