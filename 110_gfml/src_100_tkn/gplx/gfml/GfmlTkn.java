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
package gplx.gfml; import gplx.*;
public interface GfmlTkn extends GfmlObj {
	String TknType();
	String Raw();
	String Val();
	GfmlTkn[] SubTkns();
	GfmlBldrCmd Cmd_of_Tkn();
	GfmlTkn MakeNew(String raw, String val);
}
class GfmlTknAry_ {
	public static final GfmlTkn[] Empty = new GfmlTkn[0];
	public static GfmlTkn[] ary_(GfmlTkn... ary) {return ary;}
	@gplx.Internal protected static String XtoRaw(GfmlTkn[] ary) {
		String_bldr sb = String_bldr_.new_();
		for (GfmlTkn tkn : ary)
			sb.Add(tkn.Raw());
		return sb.XtoStr();
	}
	@gplx.Internal protected static String XtoVal(GfmlTkn[] ary) {return XtoVal(ary, 0, ary.length);}
	static String XtoVal(GfmlTkn[] ary, int bgn, int end) {
		String_bldr sb = String_bldr_.new_();
		for (int i = bgn; i < end; i++) {
			GfmlTkn tkn = ary[i];
			sb.Add(tkn.Val());
		}
		return sb.XtoStr();
	}
}
