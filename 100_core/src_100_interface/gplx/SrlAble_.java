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
package gplx;
public class SrlAble_ {
	public static SrlAble as_(Object obj) {return obj instanceof SrlAble ? (SrlAble)obj : null;}
	public static String XtoStr(GfoMsg owner) {
		String_bldr sb = String_bldr_.new_();
		XtoStr(owner, sb, 0, false);
		return sb.XtoStr();
	}
	public static String XtoStr(Object o) {
		SrlAble s = SrlAble_.as_(o); if (s == null) return Object_.XtoStr_OrNullStr(o);
		GfoMsg m = GfoMsg_.new_parse_("root");
		s.Srl(m);
		return XtoStr(m);
	}
	static void XtoStr(GfoMsg owner, String_bldr sb, int depth, boolean indentOn) {
		String indent = String_.Repeat(" ", depth * 4);
		if (indentOn) sb.Add(indent);
		sb.Add(owner.Key()).Add(":");
		for (int i = 0; i < owner.Args_count(); i++) {
			if (i != 0) sb.Add(" ");
			KeyVal kv = owner.Args_getAt(i);
			sb.Add(kv.Key()).Add("=").Add("'").Add(Object_.XtoStr_OrNullStr(kv.Val())).Add("'");
		}
		int subsCount = owner.Subs_count();
		if	(subsCount == 0) {
			sb.Add(";");
			return;
		}
		else if (subsCount == 1) {
			sb.Add("{");
			XtoStr(owner.Subs_getAt(0), sb, depth + 1, false);
			sb.Add("}");
			return;
		}
		else {
			sb.Add("{");
			if (subsCount > 1) sb.Add_char_crlf();
			for (int i = 0; i < subsCount; i++) {
				GfoMsg sub = owner.Subs_getAt(i);
				XtoStr(sub, sb, depth + 1, true);
				sb.Add_char_crlf();
			}
			sb.Add(indent);
			sb.Add("}");
		}
	}
}
