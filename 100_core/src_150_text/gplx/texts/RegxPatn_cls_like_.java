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
import gplx.core.strings.*;
public class RegxPatn_cls_like_ {
	public static RegxPatn_cls_like parse_(String regxRaw, char escape) {
		String regx = Compile(regxRaw, escape);
		return new RegxPatn_cls_like(regxRaw, regx, escape);
	}		
	static String Compile(String raw, char escape) {
		char Wildcard = '%', AnyChar = '_';
		boolean insideCharSet = false;
		String_bldr sb = String_bldr_.new_();
		sb.Add(RegxBldr.Tkn_LineBegin);
		int rawLen = String_.Len(raw);
		for (int i = 0; i < rawLen; i++) {
			char c = String_.CharAt(raw, i);
			if (c == escape) { // escape: ignore cur, append next
				i++;
				if (i < rawLen) sb.Add(String_.CharAt(raw, i));
				else throw Err_.new_("escape cannot be last char").Add("raw", raw).Add("escape", escape).Add("i", i);
			}
			else if (c == Wildcard) { // % -> .*
				sb.Add(RegxBldr.Tkn_AnyChar).Add(RegxBldr.Tkn_Wild_0Plus);
			}
			else if (c == AnyChar) // _ -> .
				sb.Add(RegxBldr.Tkn_AnyChar);
			else if (c == RegxBldr.Tkn_CharSetBegin) { // toggle insideCharSet for ^
				insideCharSet = true;
				sb.Add(c);
			}
			else if (c == RegxBldr.Tkn_CharSetEnd) { // toggle insideCharSet for ^
				insideCharSet = false;
				sb.Add(c);
			}
			else if (c == RegxBldr.Tkn_Not && insideCharSet) { // ^ is used for Not in CharSet, but also used for LineStart; do not escape if insideCharSet
				insideCharSet = false;
				sb.Add(c);
			}
			else if (RegxBldr.RegxChar_chk(c))
				sb.Add(RegxBldr.Tkn_Escape).Add(c);
			else // regular text
				sb.Add(c);
		}
		sb.Add(RegxBldr.Tkn_LineEnd);
		return sb.XtoStr();
	}
}
