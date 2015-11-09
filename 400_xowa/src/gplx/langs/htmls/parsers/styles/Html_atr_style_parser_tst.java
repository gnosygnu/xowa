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
package gplx.langs.htmls.parsers.styles; import gplx.*; import gplx.langs.*; import gplx.langs.htmls.*; import gplx.langs.htmls.parsers.*;
import org.junit.*;
public class Html_atr_style_parser_tst {
	private final Html_atr_style_parser_fxt fxt = new Html_atr_style_parser_fxt();
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
}
class Html_atr_style_parser_fxt {
	private final Html_atr_style_wkr__kv_list wkr = new Html_atr_style_wkr__kv_list();
	public KeyVal Make(String k, String v) {return KeyVal_.new_(k, v);}
	public void Test__parse(String src_str, KeyVal... expd) {
		byte[] src_bry = Bry_.new_u8(src_str);
		KeyVal[] actl = wkr.Parse(src_bry, 0, src_bry.length);
		Tfds.Eq_ary_str(expd, actl);
	}
}
