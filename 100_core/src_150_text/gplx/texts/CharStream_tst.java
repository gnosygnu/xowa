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
public class CharStream_tst {
	@Before public void setup() {
		stream = CharStream.pos0_("abcdefgh");
	}
	@Test  public void XtoStr() {
		Tfds.Eq(stream.XtoStr(), "abcdefgh");
	}
	@Test  public void CurrentText() {
		stream.MoveNextBy(1);
		Tfds.Eq(stream.XtoStrAtCur(2), "bc");
		Tfds.Eq(stream.XtoStr(), "abcdefgh");
	}
	@Test  public void CurrentText_outOfBounds() {
		stream.MoveNextBy(7);
		Tfds.Eq(stream.XtoStrAtCur(2), "h");
	}
	@Test  public void Match() {
		stream.MoveNextBy(6);
		tst_Match(true, "g");
		tst_Match(false, "z");
		tst_Match(true, "gh");
		tst_Match(false, "gz");
		tst_Match(false, "ghi");
	}
	@Test  public void AtBounds() {
		stream.Move_to(-1);
		tst_AtBounds(true, false, false);

		stream.Move_to(0);
		tst_AtBounds(false, true, false);

		stream.Move_to(stream.Len());
		tst_AtBounds(false, false, true);
	}
	void tst_Match(boolean expd, String text) {Tfds.Eq(expd, stream.Match(text));}
	void tst_AtBounds(boolean atBgn, boolean atMid, boolean atEnd) {
		Tfds.Eq(atBgn, stream.AtBgn());
		Tfds.Eq(atMid, stream.AtMid());
		Tfds.Eq(atEnd, stream.AtEnd());
	}
	CharStream stream;
}
