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
public class RegxPatn_cls_ioMatch_ {
	public static final String Wildcard			= "*";
	public static final String OrDelimiter		= "|";
	public static final RegxPatn_cls_ioMatch All = RegxPatn_cls_ioMatch_.parse_(Wildcard, false);
	public static final String ImpossiblePath		= "<>";				//"<>" should be an impossible url; NOTE: do not pick * or | or : or \
	public static final RegxPatn_cls_ioMatch None = RegxPatn_cls_ioMatch_.parse_(RegxPatn_cls_ioMatch_.ImpossiblePath, false);
	public static RegxPatn_cls_ioMatch cast_(Object obj) {try {return (RegxPatn_cls_ioMatch)obj;} catch(Exception exc) {throw Err_.type_mismatch_exc_(exc, RegxPatn_cls_ioMatch.class, obj);}}
	public static RegxPatn_cls_ioMatch parse_(String raw, boolean caseSensitive) {
		String compiled = RegxPatn_cls_ioMatch_.Compile(raw);
		return new RegxPatn_cls_ioMatch(raw, compiled, caseSensitive);
	}
	@gplx.Internal protected static String Compile(String raw) {
		if (raw == ImpossiblePath) return ImpossiblePath;

		String_bldr sb = String_bldr_.new_();
		sb.Add(RegxBldr.Tkn_LineBegin);									// Char_LineBegin for exact match (else "LIKE a" would match "abc")
		int rawLen = String_.Len(raw);
		for (int i = 0; i < rawLen; i++) {
			char c = String_.CharAt(raw, i);
			if (c == '\\')
				sb.Add("\\\\");
			else if (c == '*')
				sb.Add(".").Add(RegxBldr.Tkn_Wild_0Plus);
			else if (c == '|')
				sb.Add(RegxBldr.Tkn_LineEnd).Add("|").Add(RegxBldr.Tkn_LineBegin);		// each term must be bracketed by lineBgn/lineEnd; ex: A|B -> ^A$|^B$
			else
				sb.Add(c);
		}
		sb.Add(RegxBldr.Tkn_LineEnd);
		return sb.XtoStr();
	}
	public static final String InvalidCharacters		= "|*?\"<>";		// : / \ are omitted b/c they will cause full paths to fail
	public static final String ValidCharacters		= RegxBldr.Excludes(InvalidCharacters);
}
