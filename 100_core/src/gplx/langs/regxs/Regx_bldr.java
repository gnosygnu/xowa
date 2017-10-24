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
package gplx.langs.regxs; import gplx.*; import gplx.langs.*;
import gplx.core.strings.*;
public class Regx_bldr {
	public static String Includes(String characters) {return String_.Concat_any(Regx_bldr.Tkn_CharSetBegin, characters, Regx_bldr.Tkn_CharSetEnd);}
	public static String Excludes(String characters) {return String_.Concat_any(Regx_bldr.Tkn_CharSetBegin, Regx_bldr.Tkn_Not, characters, Regx_bldr.Tkn_CharSetEnd);}
	public static String WholeWord(String word) {return String_.Concat_any("(?<![A-Za-z0-9_])", EscapeAll(word), "(?![A-Za-z0-9_])");}
	public static String EscapeAll(String text) {
		String_bldr sb = String_bldr_.new_();
		int len = String_.Len(text);
		for (int i = 0; i < len; i++) {
			char c = String_.CharAt(text, i);
			if (RegxChar_chk(c))
				sb.Add(Regx_bldr.Tkn_Escape);
			sb.Add(c);
		}
		return sb.To_str();
	}
	public static boolean RegxChar_chk(char c) {
		return
			(  c == Regx_bldr.Tkn_Escape || c == Regx_bldr.Tkn_Or
			|| c == Regx_bldr.Tkn_LineBegin || c == Regx_bldr.Tkn_LineEnd
			|| c == Regx_bldr.Tkn_GroupBegin || c == Regx_bldr.Tkn_GroupEnd
			|| c == Regx_bldr.Tkn_RepBegin || c == Regx_bldr.Tkn_RepEnd
			|| c == Regx_bldr.Tkn_Wild_0Plus || c == Regx_bldr.Tkn_Wild_1Plus || c == Regx_bldr.Tkn_Wild_0or1
			|| c == Regx_bldr.Tkn_CharSetBegin || c == Regx_bldr.Tkn_CharSetEnd
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
