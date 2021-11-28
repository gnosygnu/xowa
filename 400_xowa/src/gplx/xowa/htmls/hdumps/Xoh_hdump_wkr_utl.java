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
package gplx.xowa.htmls.hdumps; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*;
import gplx.langs.htmls.docs.*;
public class Xoh_hdump_wkr_utl {
	public static void Write_tag_with_val_at_atr_bgn(Bry_bfr bfr, byte[] src, Gfh_tag tag, Gfh_atr atr, byte[] val) {
		// utility method to write tag, but put "val" at start of atr
		// EX: val="VAL_" tag="<img id='1' src='b.png' title='c'/>" -> "<img id='1' src='VAL_.png' title='c'/>"
		bfr.Add_mid(src, tag.Src_bgn(), atr.Val_bgn()); // add bgn of tag
		if (val != null)
			bfr.Add(val); // add "val"
		bfr.Add(atr.Val()); // add atr.val
		bfr.Add_mid(src, atr.Val_end(), tag.Src_end()); // add end of tag
	}
	public static byte[] Build_hdump_atr(byte[] key) {
		return Bry_.Add(Bry__data_xowa_hdump, key, Byte_ascii.Apos_bry);
	}
	private static final byte[] Bry__data_xowa_hdump = Bry_.new_a7("data-xowa-hdump='");
}
