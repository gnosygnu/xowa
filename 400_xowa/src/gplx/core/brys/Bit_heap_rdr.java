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
import gplx.xowa.htmls.core.hzips.*;
public class Bit_heap_rdr {
	private final byte[] hzip_int_bry = new byte[5];
	public int Cur_val()	{return cur_val;} private int cur_val;
	public int Cur_bits()	{return cur_bits;} private int cur_bits;
	public int Cur_pos()	{return cur_pos;} private int cur_pos;
	public byte[] Src()		{return src;} private byte[] src;
	public int Src_end()	{return src_end;} private int src_end;
	public void Load(byte[] src, int src_bgn, int src_end) {
		this.src = src; this.cur_pos = src_bgn; this.src_end = src_end;
		this.cur_val = 0; this.cur_bits = 0;
	}
	public boolean Get_bool() {
		Get_bgn(cur_bits);
		int old_val = cur_val;
		this.cur_val = cur_val >> 1;
		int comp_val = cur_val << 1;
		boolean rv = (old_val - comp_val) == 1;
		Get_end(1);
		return rv;
	}
	public byte Get_byte(int bits) {
		int old_bits = bits;
		int new_bits = cur_bits + bits;
		boolean again = false;
		if (new_bits > 7 && cur_bits > 0) {
			old_bits = 8 - cur_bits;
			again = true;
			new_bits -= 8;
		}
		int rv = Get_byte_private(old_bits, cur_bits);
		if (again) {
			Get_end(old_bits);
			int new_val = Get_byte_private(new_bits, 0);
			rv += new_val << old_bits;
		}
		Get_end(new_bits);
		return (byte)rv;
	}
	public int Get_int_hzip(int reqd_len) {
		int full_len = reqd_len; int bgn_idx = 0;
		byte b0 = Get_byte(8);
		switch (b0) {
			case Xoh_hzip_int.prefix__b256__2: full_len = 2; bgn_idx = 1; break;
			case Xoh_hzip_int.prefix__b256__3: full_len = 3; bgn_idx = 1; break;
			case Xoh_hzip_int.prefix__b256__4: full_len = 4; bgn_idx = 1; break;
			case Xoh_hzip_int.prefix__b256__5: full_len = 5; bgn_idx = 1; break;
		}
		hzip_int_bry[0] = b0;
		for (int i = 1; i < full_len; ++i)
			hzip_int_bry[i] = Get_byte(8);
		return Xoh_hzip_int.To_int_by_bry(hzip_int_bry, bgn_idx, full_len, Byte_.Zero, 256);
	}
	private byte Get_byte_private(int bits, int chk_bits) {
		Get_bgn(chk_bits);
		int old_val = cur_val;
		this.cur_val = cur_val >> bits;
		int comp_val = cur_val << bits;
		byte rv = (byte)(old_val - comp_val);
		return rv;
	}
	private void Get_bgn(int chk_bits) {
		if (chk_bits == 0) cur_val = src[cur_pos] & 0xFF;	// PATCH.JAVA:need to convert to unsigned byte
	}
	private void Get_end(int bits) {
		int new_bits = cur_bits + bits;
		if (new_bits == 8) {
			cur_bits = 0;
			cur_pos += 1;
		}
		else
			cur_bits = new_bits;
	}
}
