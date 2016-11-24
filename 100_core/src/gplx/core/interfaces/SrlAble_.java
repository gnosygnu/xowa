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
package gplx.core.interfaces; import gplx.*; import gplx.core.*;
import gplx.core.strings.*;
public class SrlAble_ {
	public static SrlAble as_(Object obj) {return obj instanceof SrlAble ? (SrlAble)obj : null;}
	public static String To_str(GfoMsg owner) {
		String_bldr sb = String_bldr_.new_();
		To_str(owner, sb, 0, false);
		return sb.To_str();
	}
	public static String To_str(Object o) {
		SrlAble s = SrlAble_.as_(o); if (s == null) return Object_.Xto_str_strict_or_null_mark(o);
		GfoMsg m = GfoMsg_.new_parse_("root");
		s.Srl(m);
		return To_str(m);
	}
	static void To_str(GfoMsg owner, String_bldr sb, int depth, boolean indentOn) {
		String indent = String_.Repeat(" ", depth * 4);
		if (indentOn) sb.Add(indent);
		sb.Add(owner.Key()).Add(":");
		for (int i = 0; i < owner.Args_count(); i++) {
			if (i != 0) sb.Add(" ");
			Keyval kv = owner.Args_getAt(i);
			sb.Add(kv.Key()).Add("=").Add("'").Add(Object_.Xto_str_strict_or_null_mark(kv.Val())).Add("'");
		}
		int subsCount = owner.Subs_count();
		if	(subsCount == 0) {
			sb.Add(";");
			return;
		}
		else if (subsCount == 1) {
			sb.Add("{");
			To_str(owner.Subs_getAt(0), sb, depth + 1, false);
			sb.Add("}");
			return;
		}
		else {
			sb.Add("{");
			if (subsCount > 1) sb.Add_char_crlf();
			for (int i = 0; i < subsCount; i++) {
				GfoMsg sub = owner.Subs_getAt(i);
				To_str(sub, sb, depth + 1, true);
				sb.Add_char_crlf();
			}
			sb.Add(indent);
			sb.Add("}");
		}
	}
}
