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
package gplx.texts; import gplx.*;
import org.junit.*;
public class StringTableBldr_tst {
	@Before public void setup() {
		bldr = StringTableBldr.new_();
	}	StringTableBldr bldr;
	@Test  public void TwoCols() {
		bldr.Add("a", "aa")
			.Add("bb", "b");
		tst_XtoStr
			( "a  aa"
			, "bb b "
			, ""
			);
	}
	@Test  public void RightAlign() {
		bldr.Add("a", "aa")
			.Add("bb", "b");
		bldr.FetchAtOrNew(0).Halign_(StringTableColAlign.Right);
		bldr.FetchAtOrNew(1).Halign_(StringTableColAlign.Right);
		tst_XtoStr
			( " a aa"
			, "bb  b"
			, ""
			);
	}
	@Test  public void CenterAlign() {
		bldr.Add("aaaa", "a")
			.Add("b", "bbbb");
		bldr.FetchAtOrNew(0).Halign_(StringTableColAlign.Mid);
		bldr.FetchAtOrNew(1).Halign_(StringTableColAlign.Mid);
		tst_XtoStr
			( "aaaa  a  "
			, " b   bbbb"
			, ""
			);
	}
	void tst_XtoStr(String... expdLines) {
		String expd = String_.ConcatWith_any(String_.CrLf, (Object[])expdLines);
		Tfds.Eq(expd, bldr.XtoStr());
	}
}
