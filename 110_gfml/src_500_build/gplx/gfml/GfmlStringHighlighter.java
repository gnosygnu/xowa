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
package gplx.gfml; import gplx.*;
import gplx.core.strings.*;
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
		int rawLen = String_.Len(raw); int rawLenDigits = Int_.DigitCount(rawLen);
		int rawBfrBgn = -1, marksLastIdx = marks.Idx_last();
		for (int i = 0; i < marks.Count(); i++) {
			GfmlStringHighlighterMarker curMark = (GfmlStringHighlighterMarker)marks.Get_at(i);
			GfmlStringHighlighterMarker nxtMark = i == marksLastIdx ? GfmlStringHighlighterMarker.Null : (GfmlStringHighlighterMarker)marks.Get_at(i + 1);
			// bgnPos
			bgnPos = XtoBgnPos(curMark.Pos(), endPos);
			if (i == 0) rawBfrBgn = bgnPos;

			// endPos
			int nxtMarkPos = nxtMark == GfmlStringHighlighterMarker.Null ? Int_.Max_value : nxtMark.Pos();
			endPos = curMark.Pos() + excerptLen;
			if (endPos >= nxtMarkPos) endPos = nxtMarkPos;
			if (endPos > rawLen ) endPos = rawLen + 1;

			// build bfrs
			for (int j = bgnPos; j < endPos; j++) {
				char rawChar = j == rawLen ? ' ' : String_.CharAt(raw, j);
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
				int gapDigits = Int_.DigitCount(gap);
				posBfr.Add_fmt("[{0}]", Int_.To_str_pad_bgn_zero(gap, gapDigits));
				rawBfr.Add_fmt("[{0}]", String_.Repeat(".", gapDigits));
				symBfr.Add_fmt(" {0} ", String_.Repeat(" ", gapDigits));
			}
			if (curMark.Sym() != ' ')
				symList.Add(String_.Format("[{0}] {1} {2}", Int_.To_str_pad_bgn_zero(curMark.Pos(), rawLenDigits), curMark.Sym(), curMark.Msg()));
		}
		if (rawBfrBgn == 0) {
			posBfr.Add_at(0, "<");
			rawBfr.Add_at(0, " ");
			symBfr.Add_at(0, " ");
		}
		List_adp rv = List_adp_.New();
		rv.Add(posBfr.To_str());
		rv.Add(rawBfr.To_str());
		rv.Add(symBfr.To_str());
		if (symList.Count() > 0)
			rv.Add("");
		for (int i = 0; i < symList.Count(); i++)
			rv.Add((String)symList.Get_at(i));
		return rv.To_str_ary();
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
	public static final    GfmlStringHighlighterMarker Null = new GfmlStringHighlighterMarker().Pos_(-1);
}
