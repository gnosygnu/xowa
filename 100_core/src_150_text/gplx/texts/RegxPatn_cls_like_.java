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
import gplx.core.strings.*; import gplx.langs.regxs.*;
public class RegxPatn_cls_like_ {
	public static RegxPatn_cls_like parse(String regxRaw, char escape) {
		String regx = Compile(regxRaw, escape);
		return new RegxPatn_cls_like(regxRaw, regx, escape);
	}		
	static String Compile(String raw, char escape) {
		char Wildcard = '%', AnyChar = '_';
		boolean insideCharSet = false;
		String_bldr sb = String_bldr_.new_();
		sb.Add(Regx_bldr.Tkn_LineBegin);
		int rawLen = String_.Len(raw);
		for (int i = 0; i < rawLen; i++) {
			char c = String_.CharAt(raw, i);
			if (c == escape) { // escape: ignore cur, append next
				i++;
				if (i < rawLen) sb.Add(String_.CharAt(raw, i));
				else throw Err_.new_wo_type("escape cannot be last char", "raw", raw, "escape", escape, "i", i);
			}
			else if (c == Wildcard) { // % -> .*
				sb.Add(Regx_bldr.Tkn_AnyChar).Add(Regx_bldr.Tkn_Wild_0Plus);
			}
			else if (c == AnyChar) // _ -> .
				sb.Add(Regx_bldr.Tkn_AnyChar);
			else if (c == Regx_bldr.Tkn_CharSetBegin) { // toggle insideCharSet for ^
				insideCharSet = true;
				sb.Add(c);
			}
			else if (c == Regx_bldr.Tkn_CharSetEnd) { // toggle insideCharSet for ^
				insideCharSet = false;
				sb.Add(c);
			}
			else if (c == Regx_bldr.Tkn_Not && insideCharSet) { // ^ is used for Not in CharSet, but also used for LineStart; do not escape if insideCharSet
				insideCharSet = false;
				sb.Add(c);
			}
			else if (Regx_bldr.RegxChar_chk(c))
				sb.Add(Regx_bldr.Tkn_Escape).Add(c);
			else // regular text
				sb.Add(c);
		}
		sb.Add(Regx_bldr.Tkn_LineEnd);
		return sb.To_str();
	}
}
