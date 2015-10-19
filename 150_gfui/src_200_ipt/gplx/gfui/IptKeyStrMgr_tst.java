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
package gplx.gfui; import gplx.*;
import org.junit.*;
public class IptKeyStrMgr_tst {
	@Test  public void KeyBasic() {
		tst_XtoUiStr(IptKey_.A, "a");
		tst_XtoUiStr(IptKey_.Z, "z");
		tst_XtoUiStr(IptKey_.Shift.Add(IptKey_.A), "A");
		tst_XtoUiStr(IptKey_.Shift.Add(IptKey_.Z), "Z");
		tst_XtoUiStrShifted(IptKey_.Equal, "=", "+");
		tst_XtoUiStrShifted(IptKey_.D0, "0", ")");
		tst_XtoUiStrShifted(IptKey_.D1, "1", "!");
		tst_XtoUiStrShifted(IptKey_.D2, "2", "@");
		tst_XtoUiStrShifted(IptKey_.D3, "3", "#");
		tst_XtoUiStrShifted(IptKey_.D4, "4", "$");
		tst_XtoUiStrShifted(IptKey_.D5, "5", "%");
		tst_XtoUiStrShifted(IptKey_.D6, "6", "^");
		tst_XtoUiStrShifted(IptKey_.D7, "7", "&");
		tst_XtoUiStrShifted(IptKey_.D8, "8", "*");
		tst_XtoUiStrShifted(IptKey_.D9, "9", "(");
		tst_XtoUiStrShifted(IptKey_.Minus, "-", "_");
		tst_XtoUiStrShifted(IptKey_.Backslash, "\\", "|");
		tst_XtoUiStrShifted(IptKey_.Semicolon, ";", ":");
		tst_XtoUiStrShifted(IptKey_.Quote, "'", "\"");
		tst_XtoUiStrShifted(IptKey_.Comma, ",", "<");
		tst_XtoUiStrShifted(IptKey_.Period, ".", ">");
		tst_XtoUiStrShifted(IptKey_.Slash, "?", "/");
		tst_XtoUiStrShifted(IptKey_.Tick, "`", "~");
		tst_XtoUiStrShifted(IptKey_.OpenBracket, "[", "{");
		tst_XtoUiStrShifted(IptKey_.CloseBracket, "]", "}");
	}
	@Test  public void FetchByKeyPress() {
		tst_FetchByKeyPress('a', IptKey_.add_(IptKey_.A));
		tst_FetchByKeyPress('A', IptKey_.add_(IptKey_.A, IptKey_.Shift));
		tst_FetchByKeyPress('1', IptKey_.add_(IptKey_.D1));
		tst_FetchByKeyPress('!', IptKey_.add_(IptKey_.D1, IptKey_.Shift));
	}	void tst_FetchByKeyPress(char c, IptKey expd) {Tfds.Eq(expd.Key(), IptKeyStrMgr.Instance.FetchByKeyPress((int)c).Key());}
	void tst_XtoUiStr(IptKey key, String expd) {Tfds.Eq(expd, key.XtoUiStr());}
	void tst_XtoUiStrShifted(IptKey key, String expdNormal, String expdShifted) {
		Tfds.Eq(expdNormal, key.XtoUiStr());
		Tfds.Eq(expdShifted, IptKey_.Shift.Add(key).XtoUiStr());
	}
}
