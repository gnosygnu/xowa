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
public class CharStream {
	public char[] Ary() {return ary;} char[] ary;
	public int Len() {return len;} int len;
	public int Pos() {return pos;} int pos = BgnPos; static final int BgnPos = -1;
	public boolean AtBgn() {return pos <= BgnPos;}
	public boolean AtEnd() {return pos >= len;}
	public boolean AtMid() {return pos > BgnPos && pos < len;}
	public char Cur() {try {return ary[pos];} catch (Exception exc) {Err_.Noop(exc); throw Err_.new_missing_idx(pos, this.Len());}}
	public void MoveNext() {pos++;}
	public void MoveNextBy(int offset) {pos += offset;}
	public void MoveBack() {pos--;}
	public void MoveBackBy(int offset) {pos -= offset;}
	public void Move_to(int val) {pos = val;}
	public boolean Match(String match) {
		int matchLen = String_.Len(match);
		for (int i = 0; i < matchLen; i++) {
			int cur = pos + i;
			if	(cur >= len || ary[cur] != String_.CharAt(match, i)) return false;
		}
		return true;
	}
	public boolean MatchAndMove(String match) {
		int matchLen = String_.Len(match);
		boolean rv = Match(match);
		if (rv) MoveNextBy(matchLen);
		return rv;
	}
	public boolean MatchAndMove(char match) {
		boolean rv = ary[pos] == match;
		if (rv) pos++;
		return rv;
	}
	public String XtoStr() {return Char_.XtoStr(ary, 0, len);}
	public String XtoStrAtCur(int length) {
		length = (pos + length > len) ? len - pos : length;
		return Char_.XtoStr(ary, pos, length);
	}
	public String Xto_str_by_pos(int bgn, int end) {
		if (bgn < 0) bgn = 0; if (end > len - 1) end = len - 1;
		return Char_.XtoStr(ary, bgn, end - bgn + 1);
	}
	public static CharStream pos0_(String text) {
		CharStream rv = new CharStream();
		rv.ary = String_.XtoCharAry(text);
		rv.len = Array_.Len(rv.ary);
		rv.MoveNext(); // bgn at pos=0
		return rv;
	}	CharStream(){}
}
