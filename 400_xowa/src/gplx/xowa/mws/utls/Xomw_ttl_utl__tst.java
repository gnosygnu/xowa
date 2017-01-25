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
package gplx.xowa.mws.utls; import gplx.*; import gplx.xowa.*; import gplx.xowa.mws.*;
import org.junit.*; import gplx.core.tests.*;
public class Xomw_ttl_utl__tst {
	private final    Xomw_ttl_utl__fxt fxt = new Xomw_ttl_utl__fxt();
	@Test  public void Alphanum()           {fxt.Test__find_fwd_while_title("0aB"             , 3);}
	@Test  public void Angle()              {fxt.Test__find_fwd_while_title("0a<"             , 2);}
}
class Xomw_ttl_utl__fxt {
	public void Test__find_fwd_while_title(String src_str, int expd) {
		byte[] src_bry = Bry_.new_u8(src_str);
		Gftest.Eq__int(expd, Xomw_ttl_utl.Find_fwd_while_title(src_bry, 0, src_bry.length, Xomw_ttl_utl.Title_chars_valid()));
	}
}
