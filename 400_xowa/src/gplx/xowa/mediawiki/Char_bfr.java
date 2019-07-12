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
package gplx.xowa.mediawiki; import gplx.*; import gplx.xowa.*;
public class Char_bfr {
	private char[] ary;
	private int ary_len;
	private int ary_max, ary_max_reset;
	public Char_bfr(int ary_max) {
		this.ary_max = ary_max;
		ary = new char[ary_max];
		ary_max_reset = ary_max;
	}
	public Char_bfr Add_char(char val) {
		int new_len = ary_len + 1;
		if (new_len > ary_len) Resize(ary_len * 2);
		ary[ary_len] = val;
		ary_len = new_len;
		return this;
	}
	public Char_bfr Add_chry(char[] val) {
		int val_len = val.length;
		if (ary_len + val_len > ary_max) Resize((ary_max + val_len) * 2);
		Copy_to(val, 0, val_len, ary, ary_len);
		ary_len += val_len;
		return this;
	}
	public Char_bfr Add_bry(byte[] val) {return Add_str(String_.new_u8(val));}
	public Char_bfr Add_str(String val) {
		int val_len = String_.Len(val);
		if (ary_len + val_len > ary_max) Resize((ary_max + val_len) * 2);
		Copy_to(val, 0, val_len, ary, ary_len);
		ary_len += val_len;
		return this;
	}
	private void Resize(int new_max) {
		ary_max = new_max;
		ary = Resize(ary, 0, new_max);
	}
	public String To_str_and_clear() {
		String rv = String_.new_charAry_(ary, 0, ary_len);
		ary_len = 0;
		ary_max = ary_max_reset;
		return rv;
	}
	public byte[] To_bry_and_clear() {
		return Bry_.new_u8(To_str_and_clear());
	}
	private static char[] Resize(char[] src, int src_bgn, int trg_len) {
		char[] trg = new char[trg_len];
		int src_len = src.length; if (src_len > trg_len) src_len = trg_len;	// trg_len can be less than src_len
		Copy_to(src, src_bgn, src_len, trg, 0);
		return trg;
	}
	private static void Copy_to(char[] src, int src_bgn, int src_end, char[] trg, int trg_bgn) {
		int trg_adj = trg_bgn - src_bgn;
		for (int i = src_bgn; i < src_end; i++)
			trg[i + trg_adj] = src[i];
	}
	private static void Copy_to(String src, int src_bgn, int src_end, char[] trg, int trg_bgn) {
		int trg_adj = trg_bgn - src_bgn;
		for (int i = src_bgn; i < src_end; i++)
			trg[i + trg_adj] = String_.CharAt(src, i);
	}
}
