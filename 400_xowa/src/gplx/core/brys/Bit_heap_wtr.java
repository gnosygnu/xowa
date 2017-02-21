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
package gplx.core.brys; import gplx.*; import gplx.core.*;
public class Bit_heap_wtr {
	private final    gplx.xowa.htmls.core.hzips.Xoh_hzip_int hzip_int = new gplx.xowa.htmls.core.hzips.Xoh_hzip_int().Mode_is_b256_(true);
	private final    Bry_bfr hzip_int_bfr = Bry_bfr_.Reset(5);
	public int Cur()		{return cur;} private int cur;
	public int Cur_bits()	{return cur_bits;} private int cur_bits;
	public Bry_bfr Heap()	{return heap;} private final    Bry_bfr heap = Bry_bfr_.New();
	public void Clear()		{heap.Clear(); cur = 0; cur_bits = 0;}
	public void Add_bool(boolean v) {
		if (v)
			cur += Pow_ary[cur_bits];
		if (cur_bits == 7) {
			heap.Add_byte((byte)cur);
			cur = 0;
			cur_bits = 0;
		}
		else
			++cur_bits;
	}
	public void Add_byte(byte val_byte) {
		int val_int = val_byte & 0xFF;	// PATCH.JAVA:need to convert to unsigned byte
		if (cur_bits == 0)
			heap.Add_byte(val_byte);
		else {
			heap.Add_byte((byte)(cur + (val_int << cur_bits)));
			this.cur = val_int >> cur_bits;
		}
	}
	public void Add_byte(int val_bits, int val) {
		int total_bits = cur_bits + val_bits;
		int new_val = cur + (val << cur_bits);
		if (total_bits < 8) {
			this.cur_bits = total_bits;
			this.cur = new_val;
		}
		else {
			heap.Add_byte((byte)new_val);
			this.cur = val >> (8 - cur_bits);
			this.cur_bits = total_bits - 8;
		}
	}
	public void Add_int_hzip(int reqd_len, int val) {
		hzip_int.Encode(reqd_len, hzip_int_bfr, val);
		int len = hzip_int_bfr.Len();
		byte[] hzip_bry = hzip_int_bfr.Bfr();
		for (int i = 0; i < len; ++i) {
			byte b = hzip_bry[i];
			Add_byte(8, b & 0xFF);	// PATCH.JAVA:need to convert to unsigned byte
		}
		hzip_int_bfr.Clear();
	}
	public void Save(Bry_bfr bfr) {}
	public static final    int[] Pow_ary = new int[] {1, 2, 4, 8, 16, 32, 64, 128, 256};
}
