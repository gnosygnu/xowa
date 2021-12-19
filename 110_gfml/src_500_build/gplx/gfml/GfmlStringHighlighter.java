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
package gplx.gfml;
import gplx.types.basics.lists.List_adp;
import gplx.types.basics.lists.List_adp_;
import gplx.types.basics.utls.IntUtl;
import gplx.types.basics.utls.StringUtl;
import gplx.types.commons.String_bldr;
import gplx.types.commons.String_bldr_;
class GfmlStringHighlighter {
	public String Raw() {return raw;} public GfmlStringHighlighter Raw_(String v) {raw = v; return this;} private String raw;
	public int ExcerptLen() {return excerptLen;} public GfmlStringHighlighter ExcerptLen_(int v) {excerptLen = v; return this;} int excerptLen = 40;
	public GfmlStringHighlighter Mark_(int pos, char c, String msg) {
		marks.Add(new GfmlStringHighlighterMarker().Pos_(pos).Sym_(c).Msg_(msg));
		return this;
	}
	int XtoBgnPos(int pos, int prvEndPos) {
		int rv = pos - excerptLen;
		if (rv < prvEndPos) rv = prvEndPos;	 // ensure bgnPos is not < prev end pos; ex: marks of 5,12; at 12, bgnPos = 2 which is less than 5; make 5
		return rv;
	}
	public String[] Gen() {
		String_bldr posBfr = String_bldr_.new_(), rawBfr = String_bldr_.new_(), symBfr = String_bldr_.new_();
		List_adp symList = List_adp_.New();
		int bgnPos = 0, endPos = 0;
		int rawLen = StringUtl.Len(raw); int rawLenDigits = IntUtl.CountDigits(rawLen);
		int rawBfrBgn = -1, marksLastIdx = marks.IdxLast();
		for (int i = 0; i < marks.Len(); i++) {
			GfmlStringHighlighterMarker curMark = (GfmlStringHighlighterMarker)marks.GetAt(i);
			GfmlStringHighlighterMarker nxtMark = i == marksLastIdx ? GfmlStringHighlighterMarker.Null : (GfmlStringHighlighterMarker)marks.GetAt(i + 1);
			// bgnPos
			bgnPos = XtoBgnPos(curMark.Pos(), endPos);
			if (i == 0) rawBfrBgn = bgnPos;

			// endPos
			int nxtMarkPos = nxtMark == GfmlStringHighlighterMarker.Null ? IntUtl.MaxValue : nxtMark.Pos();
			endPos = curMark.Pos() + excerptLen;
			if (endPos >= nxtMarkPos) endPos = nxtMarkPos;
			if (endPos > rawLen ) endPos = rawLen + 1;

			// build bfrs
			for (int j = bgnPos; j < endPos; j++) {
				char rawChar = j == rawLen ? ' ' : StringUtl.CharAt(raw, j);
				if		(rawChar == '\t') {posBfr.Add("t"); rawBfr.Add(" ");}
				else if (rawChar == '\n') {posBfr.Add("n"); rawBfr.Add(" ");}
				else				{
					char posChar = j == rawLen ? '>' : ' ';
					posBfr.Add(posChar);
					rawBfr.Add(rawChar);
				}
				char symChar = j == curMark.Pos() ? curMark.Sym() : ' ';
				symBfr.Add(symChar);
			}

			// gap
			int nxtMarkBgn = XtoBgnPos(nxtMark.Pos(), endPos);
			int gap = nxtMarkBgn - endPos;
			if (gap > 0) {
				int gapDigits = IntUtl.CountDigits(gap);
				posBfr.AddFmt("[{0}]", IntUtl.ToStrPadBgnZero(gap, gapDigits));
				rawBfr.AddFmt("[{0}]", StringUtl.Repeat(".", gapDigits));
				symBfr.AddFmt(" {0} ", StringUtl.Repeat(" ", gapDigits));
			}
			if (curMark.Sym() != ' ')
				symList.Add(StringUtl.Format("[{0}] {1} {2}", IntUtl.ToStrPadBgnZero(curMark.Pos(), rawLenDigits), curMark.Sym(), curMark.Msg()));
		}
		if (rawBfrBgn == 0) {
			posBfr.AddAt(0, "<");
			rawBfr.AddAt(0, " ");
			symBfr.AddAt(0, " ");
		}
		List_adp rv = List_adp_.New();
		rv.Add(posBfr.ToStr());
		rv.Add(rawBfr.ToStr());
		rv.Add(symBfr.ToStr());
		if (symList.Len() > 0)
			rv.Add("");
		for (int i = 0; i < symList.Len(); i++)
			rv.Add((String)symList.GetAt(i));
		return rv.ToStrAry();
	}
	List_adp marks = List_adp_.New();
        public static GfmlStringHighlighter new_() {
		GfmlStringHighlighter rv = new GfmlStringHighlighter();
		return rv;
	}	GfmlStringHighlighter() {}
}
class GfmlStringHighlighterMarker {
	public int Pos() {return pos;} public GfmlStringHighlighterMarker Pos_(int v) {pos = v; return this;} int pos;
	public char Sym() {return sym;} public GfmlStringHighlighterMarker Sym_(char v) {sym = v; return this;} char sym;
	public String Msg() {return msg;} public GfmlStringHighlighterMarker Msg_(String v) {msg = v; return this;} private String msg;
	public static final GfmlStringHighlighterMarker Null = new GfmlStringHighlighterMarker().Pos_(-1);
}
