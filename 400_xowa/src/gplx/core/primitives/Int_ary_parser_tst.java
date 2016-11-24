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
import org.junit.*; import gplx.core.tests.*;
public class Int_ary_parser_tst {
	private final    Int_ary_parser_fxt fxt = new Int_ary_parser_fxt();
	@Test  public void Many()		{fxt.Test__Parse_ary("1,2,3,4,5"		, 0, 9, Int_.Ary(1, 2, 3, 4, 5));}
	@Test  public void One()		{fxt.Test__Parse_ary("1"				, 0, 1, Int_.Ary(1));}
	@Test  public void None()		{fxt.Test__Parse_ary(""					, 0, 0, Int_.Ary());}
}
class Int_ary_parser_fxt {
	public void Test__Parse_ary(String raw, int bgn, int end, int[] expd) {
		Gftest.Eq__ary(expd, new Int_ary_parser().Parse_ary(Bry_.new_a7(raw), bgn, end, Byte_ascii.Comma), "parse_ary failed");
	}
}
