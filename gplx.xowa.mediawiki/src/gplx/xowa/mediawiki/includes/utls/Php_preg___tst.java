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
package gplx.xowa.mediawiki.includes.utls; import gplx.*; import gplx.xowa.*; import gplx.xowa.mediawiki.*; import gplx.xowa.mediawiki.includes.*;
import org.junit.*; import gplx.core.tests.*;
public class Php_preg___tst {
	private final    Php_preg___fxt fxt = new Php_preg___fxt();
	@Test  public void Basic()         {fxt.Test__split("a''b''c"          , "''", Bool_.Y, "a", "''", "b", "''", "c");}
	@Test  public void Extend()        {fxt.Test__split("a'''b'''c"        , "''", Bool_.Y, "a", "'''", "b", "'''", "c");}
	@Test  public void Eos()           {fxt.Test__split("a''"              , "''", Bool_.Y, "a", "''");}
}
class Php_preg___fxt {
	private final    gplx.core.primitives.Int_list rv = new gplx.core.primitives.Int_list();
	public void Test__split(String src, String dlm, boolean extend, String... expd) {Test__split(src, 0, String_.Len(src), dlm, extend, expd);}
	public void Test__split(String src, int src_bgn, int src_end, String dlm, boolean extend, String... expd) {
		byte[][] actl = Php_preg_.Split(rv, Bry_.new_u8(src), src_bgn, src_end, Bry_.new_u8(dlm), extend);
		Gftest.Eq__ary(expd, String_.Ary(actl), "find_failed");
	}
}
