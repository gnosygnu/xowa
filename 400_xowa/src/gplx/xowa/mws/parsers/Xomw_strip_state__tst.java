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
package gplx.xowa.mws.parsers; import gplx.*; import gplx.xowa.*; import gplx.xowa.mws.*;
import org.junit.*; import gplx.core.tests.*;
public class Xomw_strip_state__tst {
	private final    Xomw_strip_state__fxt fxt = new Xomw_strip_state__fxt();
	@Test   public void Basic() {
		fxt.Init__add    (Xomw_strip_state.Tid__general, "\u007f'\"`UNIQ-key-1-QINU`\"'\u007f", "val-1");
		fxt.Test__nostrip(Xomw_strip_state.Tid__nowiki , "a \u007f'\"`UNIQ-key-1-QINU`\"'\u007f b");
		fxt.Test__unstrip(Xomw_strip_state.Tid__general, "a \u007f'\"`UNIQ-key-1-QINU`\"'\u007f b", "a val-1 b");
		fxt.Test__unstrip(Xomw_strip_state.Tid__both   , "a \u007f'\"`UNIQ-key-1-QINU`\"'\u007f b", "a val-1 b");
	}
	@Test   public void Recurse() {
		fxt.Init__add    (Xomw_strip_state.Tid__general, "\u007f'\"`UNIQ-key-1-QINU`\"'\u007f", "val-1");
		fxt.Init__add    (Xomw_strip_state.Tid__general, "\u007f'\"`UNIQ-key-2-QINU`\"'\u007f", "\u007f'\"`UNIQ-key-1-QINU`\"'\u007f");
		fxt.Test__unstrip(Xomw_strip_state.Tid__general, "a \u007f'\"`UNIQ-key-2-QINU`\"'\u007f b", "a val-1 b");
	}
}
class Xomw_strip_state__fxt {
	private final    Xomw_strip_state strip_state = new Xomw_strip_state();
	public void Init__add(byte tid, String marker, String val) {
		strip_state.Add_item(tid, Bry_.new_u8(marker), Bry_.new_u8(val));
	}
	public void Test__nostrip(byte tid, String src) {Test__unstrip(tid, src, src);}
	public void Test__unstrip(byte tid, String src, String expd) {
		byte[] actl = strip_state.Unstrip(tid, Bry_.new_u8(src));
		Gftest.Eq__str(expd, String_.new_u8(actl));
	}
}
