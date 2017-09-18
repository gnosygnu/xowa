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
package gplx.xowa.wikis.tdbs.metas; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*; import gplx.xowa.wikis.tdbs.*;
import org.junit.*; import gplx.core.strings.*;
public class Xof_meta_thumb_parser_tst {
	Xof_meta_thumb_parser parser = new Xof_meta_thumb_parser();
	@Test  public void Exists_y()		{Tst_parse("1?45,40", itm_y_(45, 40));}
	@Test  public void Exists_n()		{Tst_parse("0?45,40", itm_n_(45, 40));}
	@Test  public void Many()			{Tst_parse("1?45,40;0?90,80", itm_y_(45, 40), itm_n_(90, 80));}
	@Test  public void Seek()			{Tst_parse("1?45,40@2,3,4", itm_y_(45, 40, 2, 3, 4));}
	private void Tst_parse(String raw_str, Xof_meta_thumb... expd) {
		byte[] raw = Bry_.new_a7(raw_str);
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
		return sb.To_str_and_clear();
	}	String_bldr sb = String_bldr_.new_();
//		Xof_meta_img_chkr img_(int w, int h, params int[] seeks) {return new Xof_meta_img_chkr().Width_(w).Height_(h).Seeks_(seeks);}
	Xof_meta_thumb itm_y_(int w, int h, int... seeks) {return new Xof_meta_thumb(Xof_meta_itm.Exists_y, w, h, seeks);}
	Xof_meta_thumb itm_n_(int w, int h, int... seeks) {return new Xof_meta_thumb(Xof_meta_itm.Exists_n, w, h, seeks);}
}
