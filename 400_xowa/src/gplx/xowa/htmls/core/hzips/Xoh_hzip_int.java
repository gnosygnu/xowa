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
package gplx.xowa.htmls.core.hzips; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*;
import gplx.core.primitives.*; import gplx.core.encoders.*;
public class Xoh_hzip_int {
	private boolean mode_is_b256; private byte pad_byte; private byte[] prefix_ary;
	public Xoh_hzip_int Mode_is_b256_(boolean v) {
		mode_is_b256 = v;
		if (mode_is_b256) {
			pad_byte		= Byte_.Zero;
			prefix_ary		= prefix_ary__b256;
		}
		else {
			pad_byte		= Byte_ascii.Bang;
			prefix_ary		= prefix_ary__b085;
		}
		return this;
	}
	public void Encode(int reqd_len, Bry_bfr bfr, int val) {
		int calc_len = Calc_len(mode_is_b256, val);
		int full_len = Full_len(mode_is_b256, val, calc_len, reqd_len, B256__pow__ary);
		int hdr_adj = full_len == calc_len || full_len == reqd_len ? 0 : 1;
		int bfr_len = bfr.Len();
		bfr.Add_byte_repeat(pad_byte, full_len);			// fill with 0s; asserts that underlying array will be large enough for following write
		byte[] bfr_bry = bfr.Bfr();							// NOTE: set bry reference here b/c Add_byte_repeat may create a new one
		if (mode_is_b256)
			Set_bry(val, bfr_bry, bfr_len + hdr_adj, reqd_len, calc_len, pad_byte, B256__pow__ary);
		else
			Base85_.Set_bry(val, bfr_bry, bfr_len + hdr_adj, reqd_len);		// calc base85 val for val; EX: 7224 -> "uu"
		if (hdr_adj == 1)
			bfr_bry[bfr_len] = prefix_ary[full_len];			// write the hdr_byte; EX: 256 -> 253, 1, 0 where 253 is the hdr_byte
	}
	public int Decode(int reqd_len, byte[] src, int src_len, int src_bgn, Int_obj_ref pos_ref) {
		int radix = 256; byte offset = Byte_.Zero;
		boolean hdr_byte_exists = false;
		int full_len = 1;	// default to 1
		byte b0 = src[src_bgn];
		if (mode_is_b256) {
			switch (b0) {
				case prefix__b256__2: full_len = 2; hdr_byte_exists = true; break;
				case prefix__b256__3: full_len = 3; hdr_byte_exists = true; break;
				case prefix__b256__4: full_len = 4; hdr_byte_exists = true; break;
				case prefix__b256__5: full_len = 5; hdr_byte_exists = true; break;
			}
		}
		else {
			radix = 85; offset = Byte_ascii.Bang;				
			switch (b0) {
				case Byte_ascii.Curly_bgn	: full_len = 3; hdr_byte_exists = true; break;
				case Byte_ascii.Pipe		: full_len = 4; hdr_byte_exists = true; break;
				case Byte_ascii.Curly_end	: full_len = 5; hdr_byte_exists = true; break;
				case Byte_ascii.Tilde		: full_len = 6; hdr_byte_exists = true; break;
			}
		}
		if (full_len < reqd_len) full_len = reqd_len;	// len should be padded
		int src_end = src_bgn + full_len;
		pos_ref.Val_(src_end);
		if (hdr_byte_exists) ++src_bgn;
		return To_int_by_bry(src, src_bgn, src_end, offset, radix);
	}
	private static int Calc_len(boolean mode_is_b256, int v) {
		if (mode_is_b256) {
			if		(v < B256__max__expd__1)	return 1;
			else if (v < B256__max__expd__2)	return 2;
			else if (v < B256__max__expd__3)	return 3;
			else								return 4;
		}
		else
			return Base85_.Bry_len(v);
	}
	private static int Full_len(boolean mode_is_b256, int v, int calc_len, int reqd_len, int[] pow_ary) {
		int reqd_max = v;
		if (mode_is_b256) {
			reqd_max = B256__pow__ary[reqd_len];	// EX: if reqd_len = 2, then reqd_max = 65536
			int hdr_byte_adj = 1;					// default to hdr_byte
			if (	calc_len == reqd_len			// only do this check if calc_len == reqd_len; i.e.: reqd_len = 2; only want to check values that would be represented with two digits where 1st digit might be 252-255; EX: 64512 is "252, 0" but 252 is reserverd; instead "253, 252, 0"
				&&	v < (reqd_max - (4 * B256__pow__ary[calc_len - 1]))	// calculates if current value will produce a 252-255 range in the 1st byte; note that 4 is for 255-252
				) {				
				hdr_byte_adj = 0;
			}
			return calc_len + hdr_byte_adj;
		}
		else {
			reqd_max = Base85_.Pow85[reqd_len];
			if (v < reqd_max) return reqd_len;
			if		(v < Base85_.Pow85[2])	return 3;
			else if (v < Base85_.Pow85[3])	return 4;
			else if (v < Base85_.Pow85[4])	return 5;
			else							return 6;
		}
	}
	private static void Set_bry(int val, byte[] src, int src_bgn, int reqd_len, int calc_len, byte pad_byte, int[] pow_ary) {
		int val_len = -1, pad_len = -1;
		boolean pad = calc_len < reqd_len;
		if (pad) {
			val_len = reqd_len;
			pad_len = reqd_len - calc_len;
		}
		else {
			val_len = calc_len;
			pad_len = 0;
		}
		if (pad) {
			for (int i = 0; i < pad_len; i++)		// fill src with pad_len
				src[i + src_bgn] = pad_byte;
		}
		for (int i = val_len - pad_len; i > 0; --i) {
			int div = pow_ary[i - 1];
			byte tmp = (byte)(val / div);
			src[src_bgn + val_len - i] = (byte)(tmp + pad_byte);
			val -= tmp * div;
		}
	}
	public static int To_int_by_bry(byte[] src, int bgn, int end, byte offset, int radix) {
		int rv = 0, factor = 1;
		for (int i = end - 1; i >= bgn; --i) {
			rv += ((src[i] & 0xFF) - offset) * factor;	// PATCH.JAVA:need to convert to unsigned byte
			factor *= radix;
		}
		return rv;
	}
	private static final int 
	  B256__max__expd__1 =      256				//           256
	, B256__max__expd__2 =    65536				//        65,536
	, B256__max__expd__3 = 16777216				//    16,777,216
	;
	private static final int[] B256__pow__ary = new int[] {1, B256__max__expd__1, B256__max__expd__2, B256__max__expd__3, Int_.Max_value};
	public static final byte prefix__b256__2 = (byte)(252 & 0xFF), prefix__b256__3 = (byte)(253 & 0xFF), prefix__b256__4 = (byte)(254 & 0xFF), prefix__b256__5 = (byte)(255 & 0xFF);
	private static final byte[] 
	  prefix_ary__b256 = new byte[] {0, 0, prefix__b256__2, prefix__b256__3, prefix__b256__4, prefix__b256__5}
	, prefix_ary__b085 = new byte[] {0, 0, 0, Byte_ascii.Curly_bgn, Byte_ascii.Pipe, Byte_ascii.Curly_end, Byte_ascii.Tilde}
	;
}
