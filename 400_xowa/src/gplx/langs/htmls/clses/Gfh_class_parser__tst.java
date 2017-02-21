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
package gplx.langs.htmls.clses; import gplx.*; import gplx.langs.*; import gplx.langs.htmls.*;
import org.junit.*;
public class Gfh_class_parser__tst {
	private final    Gfh_class_parser__fxt fxt = new Gfh_class_parser__fxt();
	@Test   public void Basic()		{fxt.Test__parse("v1"						, "v1");}
	@Test   public void Many()		{fxt.Test__parse("v1 v2"					, "v1", "v2");}
}
class Gfh_class_parser__fxt {
	private final    Gfh_class_wkr__list wkr = new Gfh_class_wkr__list();
	public void Test__parse(String src_str, String... expd) {
		byte[] src_bry = Bry_.new_u8(src_str);
		String[] actl = wkr.Parse(src_bry, 0, src_bry.length);
		Tfds.Eq_ary_str(expd, actl);
	}
}
class Gfh_class_wkr__list implements Gfh_class_parser_wkr {	
	private final    List_adp list = List_adp_.New();
	public boolean On_cls(byte[] src, int atr_idx, int atr_bgn, int atr_end, int val_bgn, int val_end) {
		String s = String_.new_u8(src, val_bgn, val_end);
		list.Add(s); // 
		return true;
	}
	public String[] Parse(byte[] src, int src_bgn, int src_end) {
		Gfh_class_parser_.Parse(src, src_bgn, src_end, this);
		return (String[])list.To_ary_and_clear(String.class);
	}
}
