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
package gplx.xowa.htmls.core.wkrs.xndes.styles; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.wkrs.*; import gplx.xowa.htmls.core.wkrs.xndes.*;
import gplx.langs.htmls.*;
class Xohz_style_regy {
	public Xohz_style_regy Itms__add_enm(int uid, byte[] key, byte[]... val_ary) {
		return this;
	}
}
class Xohz_style_regy_ {
	public static Xohz_style_regy New_dflt() {
		Xohz_style_regy rv = new Xohz_style_regy();
		// initial, inherit, unset
		rv
			.Itms__add_enm(Uid__display			, Bry__display				, Bry_.Ary("inline", "block", "flex", "inline-block", "inline-flex", "inline-table", "list-item", "run-in", "table", "table-caption", "table-column-group", "table-header-group", "table-footer-group", "table-row-group", "table-cell", "table-column", "table-row"))
			.Itms__add_enm(Uid__text_align		, Bry__text_align			, Bry_.Ary("left", "right", "center", "justify"))
			.Itms__add_enm(Uid__float			, Bry__float				, Bry_.Ary("none", "left", "right"))
			.Itms__add_enm(Uid__clear			, Bry__clear				, Bry_.Ary("none", "left", "right", "both"))
			//background-color
			//padding:0.1em 0;line-height:1.2em;
			//font-size
			//.Itms__add_len(Uid__width			, Bry__width) // 1em, 1ex, 1ch, 1rem, 1vh, 1vw, 1vmin, 1vmax, 1px, 1mm, 1cm, 1in, 1pt, 1pc, 1mozmm, 1%
			;
		return rv;
	}
	public static final int
	  Uid__display						=   1
	, Uid__text_align					=   2
	, Uid__float						=   3
	, Uid__clear						=   4
	;
	public static byte[]
	  Bry__display						= Bry_.new_a7("display")
	, Bry__text_align					= Bry_.new_a7("text-align")
	, Bry__float						= Bry_.new_a7("float")
	, Bry__clear						= Bry_.new_a7("clear")
	;
}
