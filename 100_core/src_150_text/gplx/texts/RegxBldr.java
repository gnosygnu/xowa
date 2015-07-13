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
public class RegxBldr {
	public static String Includes(String characters) {return String_.Concat_any(RegxBldr.Tkn_CharSetBegin, characters, RegxBldr.Tkn_CharSetEnd);}
	public static String Excludes(String characters) {return String_.Concat_any(RegxBldr.Tkn_CharSetBegin, RegxBldr.Tkn_Not, characters, RegxBldr.Tkn_CharSetEnd);}
	public static String WholeWord(String word) {return String_.Concat_any("(?<![A-Za-z0-9_])", EscapeAll(word), "(?![A-Za-z0-9_])");}
	public static String EscapeAll(String text) {
		String_bldr sb = String_bldr_.new_();
		int len = String_.Len(text);
		for (int i = 0; i < len; i++) {
			char c = String_.CharAt(text, i);
			if (RegxChar_chk(c))
				sb.Add(RegxBldr.Tkn_Escape);
			sb.Add(c);
		}
		return sb.XtoStr();
	}
	public static boolean RegxChar_chk(char c) {
		return
			(  c == RegxBldr.Tkn_Escape || c == RegxBldr.Tkn_Or
			|| c == RegxBldr.Tkn_LineBegin || c == RegxBldr.Tkn_LineEnd
			|| c == RegxBldr.Tkn_GroupBegin || c == RegxBldr.Tkn_GroupEnd
			|| c == RegxBldr.Tkn_RepBegin || c == RegxBldr.Tkn_RepEnd
			|| c == RegxBldr.Tkn_Wild_0Plus || c == RegxBldr.Tkn_Wild_1Plus || c == RegxBldr.Tkn_Wild_0or1
			|| c == RegxBldr.Tkn_CharSetBegin || c == RegxBldr.Tkn_CharSetEnd
			);
	}
	public static final char 
		  Tkn_LineBegin		= '^'
		, Tkn_LineEnd		= '$'
		, Tkn_AnyChar		= '.'		// except newline
		, Tkn_Wild_0Plus	= '*'
		, Tkn_Wild_1Plus	= '+'
		, Tkn_Wild_0or1		= '?'
		, Tkn_CharSetBegin	= '['
		, Tkn_CharSetEnd	= ']'
		, Tkn_GroupBegin	= '('
		, Tkn_GroupEnd		= ')'
		, Tkn_RepBegin		= '{'
		, Tkn_RepEnd		= '}'
		, Tkn_Not			= '^'
		, Tkn_Or			= '|'
		, Tkn_Escape		= '\\'
	;
}
