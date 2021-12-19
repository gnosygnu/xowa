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
import gplx.types.basics.utls.ArrayUtl;
import gplx.types.errs.ErrUtl;
import gplx.types.basics.utls.CharUtl;
import gplx.types.basics.utls.StringUtl;
public class CharStream {
	public char[] Ary() {return ary;} char[] ary;
	public int Len() {return len;} int len;
	public int Pos() {return pos;} int pos = BgnPos; static final int BgnPos = -1;
	public boolean AtBgn() {return pos <= BgnPos;}
	public boolean AtEnd() {return pos >= len;}
	public boolean AtMid() {return pos > BgnPos && pos < len;}
	public char Cur() {try {return ary[pos];} catch (Exception exc) {throw ErrUtl.NewMissingIdx(pos, this.Len());}}
	public void MoveNext() {pos++;}
	public void MoveNextBy(int offset) {pos += offset;}
	public void MoveBack() {pos--;}
	public void MoveBackBy(int offset) {pos -= offset;}
	public void Move_to(int val) {pos = val;}
	public boolean Match(String match) {
		int matchLen = StringUtl.Len(match);
		for (int i = 0; i < matchLen; i++) {
			int cur = pos + i;
			if    (cur >= len || ary[cur] != StringUtl.CharAt(match, i)) return false;
		}
		return true;
	}
	public boolean MatchAndMove(String match) {
		int matchLen = StringUtl.Len(match);
		boolean rv = Match(match);
		if (rv) MoveNextBy(matchLen);
		return rv;
	}
	public boolean MatchAndMove(char match) {
		boolean rv = ary[pos] == match;
		if (rv) pos++;
		return rv;
	}
	public String To_str() {return CharUtl.ToStr(ary, 0, len);}
	public String XtoStrAtCur(int length) {
		length = (pos + length > len) ? len - pos : length;
		return CharUtl.ToStr(ary, pos, length);
	}
	public String To_str_by_pos(int bgn, int end) {
		if (bgn < 0) bgn = 0; if (end > len - 1) end = len - 1;
		return CharUtl.ToStr(ary, bgn, end - bgn + 1);
	}
	public static CharStream pos0_(String text) {
		CharStream rv = new CharStream();
		rv.ary = StringUtl.ToCharAry(text);
		rv.len = ArrayUtl.Len(rv.ary);
		rv.MoveNext(); // bgn at pos=0
		return rv;
	}   CharStream(){}
}
