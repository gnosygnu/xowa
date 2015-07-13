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
class IptKeyStrMgr {
	public IptKey FetchByKeyPress(int charVal) {
		if (literals == null) Init();
		IptKey rv = charKeys[charVal];
		return (rv == null) ? IptKey_.None : rv;
	}
	public String XtoStr(IptKey key) {
		if (literals == null) Init();
		Object rv = literals.Get_by(key.Val());
		return rv == null ? String_.Empty : (String)rv;
	}
	public void XtoIptKeyAry(List_adp list) {
		if (literals == null) Init();
		for (int i = 0; i < keys.Count(); i++)
			list.Add((IptKey)keys.Get_at(i));
	}
	void Init() {// default to US style keyboard
		literals = Hash_adp_.new_();
		charKeys = new IptKey[256];
		RegLtr(IptKey_.A, 'a'); RegLtr(IptKey_.B, 'b'); RegLtr(IptKey_.C, 'c'); RegLtr(IptKey_.D, 'd'); RegLtr(IptKey_.E, 'e');
		RegLtr(IptKey_.F, 'f'); RegLtr(IptKey_.G, 'g'); RegLtr(IptKey_.H, 'h'); RegLtr(IptKey_.I, 'i'); RegLtr(IptKey_.J, 'j');
		RegLtr(IptKey_.K, 'k'); RegLtr(IptKey_.L, 'l'); RegLtr(IptKey_.M, 'm'); RegLtr(IptKey_.N, 'n'); RegLtr(IptKey_.O, 'o');
		RegLtr(IptKey_.P, 'p'); RegLtr(IptKey_.Q, 'q'); RegLtr(IptKey_.R, 'r'); RegLtr(IptKey_.S, 's'); RegLtr(IptKey_.T, 't');
		RegLtr(IptKey_.U, 'u'); RegLtr(IptKey_.V, 'v'); RegLtr(IptKey_.W, 'w'); RegLtr(IptKey_.X, 'x'); RegLtr(IptKey_.Y, 'y'); RegLtr(IptKey_.Z, 'z');
		RegSym(IptKey_.D0, '0', ')'); RegSym(IptKey_.D1, '1', '!'); RegSym(IptKey_.D2, '2', '@'); RegSym(IptKey_.D3, '3', '#'); RegSym(IptKey_.D4, '4', '$');
		RegSym(IptKey_.D5, '5', '%'); RegSym(IptKey_.D6, '6', '^'); RegSym(IptKey_.D7, '7', '&'); RegSym(IptKey_.D8, '8', '*'); RegSym(IptKey_.D9, '9', '(');
		RegSym(IptKey_.Equal, '=', '+'); RegSym(IptKey_.Minus, '-', '_'); RegSym(IptKey_.Backslash, '\\', '|'); RegSym(IptKey_.Semicolon, ';', ':');
		RegSym(IptKey_.Quote, '\'', '"'); RegSym(IptKey_.Comma, ',', '<'); RegSym(IptKey_.Period, '.', '>'); RegSym(IptKey_.Slash, '?', '/');
		RegSym(IptKey_.OpenBracket, '[', '{'); RegSym(IptKey_.CloseBracket, ']', '}'); RegSym(IptKey_.Tick, '`', '~');
		Reg(IptKey_.Space, ' ');
		Reg(IptKey_.Escape, "", 27);	// escape should be "" or else prints on grid
		Reg(IptKey_.Enter, "\n", 10);
		charKeys[13] = IptKey_.Enter;	// WORKAROUND.WINFORMS: Enter generates keypress of 13 while Shift+Enter generates keypress of 10; JAVA always sends 10
		Reg(IptKey_.CapsLock, "CapsLock", 20);
	}
	void RegLtr(IptKey lowerKey, char lowerChr) {
		IptKey upperKey = IptKey_.add_(lowerKey, IptKey_.Shift);
		char   upperChr = (char)((int)lowerChr - 32);
		Reg(lowerKey, lowerChr);	// 'a' 97 Key_.A
		Reg(upperKey, upperChr);	// 'A' 65 Key_.A+Key_.Shift
	}
	void RegSym(IptKey lowerKey, char lowerChr, char upperChr) {
		IptKey upperKey = IptKey_.add_(lowerKey, IptKey_.Shift);
		Reg(lowerKey, lowerChr);
		Reg(upperKey, upperChr);
	}
	void Reg(IptKey k, char c) {Reg(k, Char_.XtoStr(c), (int)c);}
	void Reg(IptKey k, String s, int charVal) {
		int v = k.Val();
		literals.Add(v, s);
		keys.Add(v, k);
		charKeys[charVal] = k;
	}
	IptKey[] charKeys;
	Hash_adp literals; Ordered_hash keys = Ordered_hash_.new_();
	public static final IptKeyStrMgr _ = new IptKeyStrMgr(); IptKeyStrMgr() {}
}
