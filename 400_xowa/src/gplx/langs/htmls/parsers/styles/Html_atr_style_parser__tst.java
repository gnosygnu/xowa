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
public class Html_atr_style_parser__tst {
	private final Html_atr_style_parser__fxt fxt = new Html_atr_style_parser__fxt();
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
class Html_atr_style_parser__fxt {
	private final Html_atr_style_wkr__kv_list wkr = new Html_atr_style_wkr__kv_list();
	public KeyVal Make(String k, String v) {return KeyVal_.new_(k, v);}
	public void Test__parse(String src_str, KeyVal... expd) {
		byte[] src_bry = Bry_.new_u8(src_str);
		KeyVal[] actl = wkr.Parse(src_bry, 0, src_bry.length);
		Tfds.Eq_ary_str(expd, actl);
	}
}
class Html_atr_style_wkr__kv_list implements Html_atr_style_wkr {
	private final List_adp list = List_adp_.new_();
	public boolean On_atr(byte[] src, int atr_idx, int atr_bgn, int atr_end, int key_bgn, int key_end, int val_bgn, int val_end) {
		KeyVal kv = KeyVal_.new_(String_.new_u8(src, key_bgn, key_end), String_.new_u8(src, val_bgn, val_end));
		list.Add(kv);
		return true;
	}
	public KeyVal[] Parse(byte[] src, int src_bgn, int src_end) {
		Html_atr_style_parser_.Parse(src, src_bgn, src_end, this);
		return (KeyVal[])list.To_ary_and_clear(KeyVal.class);
	}
}
