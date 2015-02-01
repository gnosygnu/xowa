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
package gplx.xowa; import gplx.*;
import org.junit.*; import gplx.core.strings.*;
public class Xof_meta_thumb_parser_tst {
	Xof_meta_thumb_parser parser = new Xof_meta_thumb_parser();
	@Test  public void Exists_y()		{Tst_parse("1?45,40", itm_y_(45, 40));}
	@Test  public void Exists_n()		{Tst_parse("0?45,40", itm_n_(45, 40));}
	@Test  public void Many()			{Tst_parse("1?45,40;0?90,80", itm_y_(45, 40), itm_n_(90, 80));}
	@Test  public void Seek()			{Tst_parse("1?45,40@2,3,4", itm_y_(45, 40, 2, 3, 4));}
	private void Tst_parse(String raw_str, Xof_meta_thumb... expd) {
		byte[] raw = Bry_.new_ascii_(raw_str);
		parser.Parse_ary(raw, 0, raw.length);
		Tfds.Eq_str_lines(Xto_str(expd, 0, expd.length), Xto_str(parser.Ary(), 0, parser.Len()));
	}
	String Xto_str(Xof_meta_thumb[] ary, int bgn, int end) {
		for (int i = bgn; i < end; i++) {
			Xof_meta_thumb itm = ary[i];
			sb	.Add(itm.Exists()).Add(":")
				.Add(itm.Width()).Add(",")
				.Add(itm.Height());
			int seeks_len = itm.Seeks().length;
			for (int j = 0; j < seeks_len; j++) {
				int seek = itm.Seeks()[j];
				sb.Add(i == 0 ? "@" : ",");
				sb.Add(seek);
			}
			sb.Add_char_nl();
		}
		return sb.Xto_str_and_clear();
	}	String_bldr sb = String_bldr_.new_();
//		Xof_meta_img_chkr img_(int w, int h, params int[] seeks) {return new Xof_meta_img_chkr().Width_(w).Height_(h).Seeks_(seeks);}
	Xof_meta_thumb itm_y_(int w, int h, int... seeks) {return new Xof_meta_thumb(Xof_meta_itm.Exists_y, w, h, seeks);}
	Xof_meta_thumb itm_n_(int w, int h, int... seeks) {return new Xof_meta_thumb(Xof_meta_itm.Exists_n, w, h, seeks);}
}
