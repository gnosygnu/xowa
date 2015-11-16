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
package gplx.langs.htmls.parsers.clses; import gplx.*; import gplx.langs.*; import gplx.langs.htmls.*; import gplx.langs.htmls.parsers.*;
import org.junit.*;
public class Html_atr_class_parser__tst {
	private final Html_atr_class_parser__fxt fxt = new Html_atr_class_parser__fxt();
	@Test   public void Basic()		{fxt.Test__parse("v1"						, "v1");}
	@Test   public void Many()		{fxt.Test__parse("v1 v2"					, "v1", "v2");}
}
class Html_atr_class_parser__fxt {
	private final Html_atr_class_wkr__list wkr = new Html_atr_class_wkr__list();
	public void Test__parse(String src_str, String... expd) {
		byte[] src_bry = Bry_.new_u8(src_str);
		String[] actl = wkr.Parse(src_bry, 0, src_bry.length);
		Tfds.Eq_ary_str(expd, actl);
	}
}
class Html_atr_class_wkr__list implements Html_atr_class_wkr {	
	private final List_adp list = List_adp_.new_();
	public boolean On_cls(byte[] src, int atr_idx, int atr_bgn, int atr_end, int val_bgn, int val_end) {
		String s = String_.new_u8(src, val_bgn, val_end);
		list.Add(s); // 
		return true;
	}
	public String[] Parse(byte[] src, int src_bgn, int src_end) {
		Html_atr_class_parser_.Parse(src, src_bgn, src_end, this);
		return (String[])list.To_ary_and_clear(String.class);
	}
}
