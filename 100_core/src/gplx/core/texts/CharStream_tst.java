/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2021 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.core.texts;
import gplx.frameworks.tests.GfoTstr;
import org.junit.*;
public class CharStream_tst {
	@Before public void setup() {
		stream = CharStream.pos0_("abcdefgh");
	}
	@Test public void To_str() {
		GfoTstr.EqObj(stream.To_str(), "abcdefgh");
	}
	@Test public void CurrentText() {
		stream.MoveNextBy(1);
		GfoTstr.EqObj(stream.XtoStrAtCur(2), "bc");
		GfoTstr.EqObj(stream.To_str(), "abcdefgh");
	}
	@Test public void CurrentText_outOfBounds() {
		stream.MoveNextBy(7);
		GfoTstr.EqObj(stream.XtoStrAtCur(2), "h");
	}
	@Test public void Match() {
		stream.MoveNextBy(6);
		tst_Match(true, "g");
		tst_Match(false, "z");
		tst_Match(true, "gh");
		tst_Match(false, "gz");
		tst_Match(false, "ghi");
	}
	@Test public void AtBounds() {
		stream.Move_to(-1);
		tst_AtBounds(true, false, false);

		stream.Move_to(0);
		tst_AtBounds(false, true, false);

		stream.Move_to(stream.Len());
		tst_AtBounds(false, false, true);
	}
	void tst_Match(boolean expd, String text) {GfoTstr.EqObj(expd, stream.Match(text));}
	void tst_AtBounds(boolean atBgn, boolean atMid, boolean atEnd) {
		GfoTstr.EqObj(atBgn, stream.AtBgn());
		GfoTstr.EqObj(atMid, stream.AtMid());
		GfoTstr.EqObj(atEnd, stream.AtEnd());
	}
	CharStream stream;
}
