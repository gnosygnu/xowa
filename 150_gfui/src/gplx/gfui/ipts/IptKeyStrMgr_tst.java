/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2017 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.gfui.ipts; import gplx.*; import gplx.gfui.*;
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
