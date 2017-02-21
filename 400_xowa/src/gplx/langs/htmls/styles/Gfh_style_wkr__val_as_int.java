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
public class Gfh_style_wkr__val_as_int implements Gfh_style_wkr {
	private byte[] find_key;
	private int val_bgn, val_end;
	public boolean On_atr(byte[] src, int atr_idx, int atr_val_bgn, int atr_val_end, int itm_bgn, int itm_End, int key_bgn, int key_end, int val_bgn, int val_end) {
		boolean rv = Bry_.Match(src, key_bgn, key_end, find_key);
		if (rv) {
			this.val_bgn = val_bgn;
			this.val_end = val_end;
		}
		return rv;
	}
	public int Parse(byte[] src, int src_bgn, int src_end, byte[] find_key) {
		this.find_key = find_key;
		Gfh_style_parser_.Parse(src, src_bgn, src_end, this);
		return Bry_.To_int_or__lax(src, val_bgn, val_end, -1);
	}
}
