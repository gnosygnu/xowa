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
package gplx.langs.htmls.styles; import gplx.*; import gplx.langs.*; import gplx.langs.htmls.*;
import org.junit.*;
public class Gfh_style_parser__tst {
	private final    Gfh_style_parser__fxt fxt = new Gfh_style_parser__fxt();
	@Test   public void Basic() {
		fxt.Test__parse("k_0:v_0"					, fxt.Make("k_0", "v_0"));
		fxt.Test__parse("k_0:v_0;"					, fxt.Make("k_0", "v_0"));
		fxt.Test__parse("k_0:v_0;k_1:v_1"			, fxt.Make("k_0", "v_0"), fxt.Make("k_1", "v_1"));
	}
	@Test   public void Ws() {
		fxt.Test__parse(" k_0 : v_0 ;"				, fxt.Make("k_0", "v_0"));
		fxt.Test__parse(" k_0 : v_0 ; k_1 : v_1 "	, fxt.Make("k_0", "v_0"), fxt.Make("k_1", "v_1"));
		fxt.Test__parse(" k_0 : v 0 ;"				, fxt.Make("k_0", "v 0"));
	}
	@Test   public void Empty() {
		fxt.Test__parse("k_0:v_0;;"					, fxt.Make("k_0", "v_0"));
		fxt.Test__parse("k_0:v_0; ; "				, fxt.Make("k_0", "v_0"));
	}
	@Test   public void Invalid__no_semic() {
		fxt.Test__parse("k_0"						, fxt.Make("k_0", ""));
	}
	@Test   public void Invalid__dupe_colon() {
		fxt.Test__parse("a:b:c:d;"					, fxt.Make("a", "b:c:d"));
	}
}
class Gfh_style_parser__fxt {
	private final    Gfh_style_wkr__ary wkr = new Gfh_style_wkr__ary();
	public Gfh_style_itm Make(String k, String v) {return new Gfh_style_itm(-1, Bry_.new_u8(k), Bry_.new_u8(v));}
	public void Test__parse(String src_str, Gfh_style_itm... expd) {
		byte[] src_bry = Bry_.new_u8(src_str);
		Gfh_style_itm[] actl = wkr.Parse(src_bry, 0, src_bry.length);
		Tfds.Eq_ary_str(expd, actl);
	}
}
