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
		return sb.To_str();
	}
	@gplx.Internal protected static String XtoVal(GfmlTkn[] ary) {return XtoVal(ary, 0, ary.length);}
	static String XtoVal(GfmlTkn[] ary, int bgn, int end) {
		String_bldr sb = String_bldr_.new_();
		for (int i = bgn; i < end; i++) {
			GfmlTkn tkn = ary[i];
			sb.Add(tkn.Val());
		}
		return sb.To_str();
	}
}
