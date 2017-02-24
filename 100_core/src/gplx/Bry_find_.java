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
package gplx;
public class Bry_find_ {
	public static final    int Not_found = -1;
	public static int Find_fwd(byte[] src, byte lkp)								{return Find_fwd(src, lkp, 0, src.length);}
	public static int Find_fwd(byte[] src, byte lkp, int cur)						{return Find_fwd(src, lkp, cur, src.length);}
	public static int Find_fwd(byte[] src, byte lkp, int cur, int end) {
		for (int i = cur; i < end; i++)
			if (src[i] == lkp) return i;
		return Bry_find_.Not_found;
	}
	public static int Find_fwd_or(byte[] src, byte lkp, int cur, int end, int or) {
		int rv = Find_fwd(src, lkp, cur, end);
		return rv == Bry_find_.Not_found ? or : rv;
	}
	public static int Find_fwd_or(byte[] src, byte[] lkp, int cur, int end, int or) {
		int rv = Find_fwd(src, lkp, cur, end);
		return rv == Bry_find_.Not_found ? or : rv;
	}
	public static int Find_bwd(byte[] src, byte lkp)								{return Find_bwd(src, lkp, src.length, 0);}
	public static int Find_bwd(byte[] src, byte lkp, int cur)						{return Find_bwd(src, lkp, cur		 , 0);}
	public static int Find_bwd(byte[] src, byte lkp, int cur, int end) {
		--cur;	// always subtract 1 from cur; allows passing in src_len or cur_pos without forcing caller to subtract - 1; DATE:2014-02-11
		--end;
		for (int i = cur; i > end; i--)
			if (src[i] == lkp) return i;
		return Bry_find_.Not_found;
	}
	public static int Move_fwd(byte[] src, byte lkp, int cur, int end) {
		int rv = Find_fwd(src, lkp, cur, src.length);
		return rv == Bry_find_.Not_found ? rv : rv + 1;
	}
	public static int Move_fwd(byte[] src, byte[] lkp, int cur) {return Move_fwd(src, lkp, cur, src.length);}
	public static int Move_fwd(byte[] src, byte[] lkp, int cur, int end) {
		int rv = Find_fwd(src, lkp, cur, src.length);
		return rv == Bry_find_.Not_found ? rv : rv + lkp.length;
	}
	public static int Find_fwd(byte[] src, byte[] lkp)								{return Find(src, lkp, 0	, src.length, true);}
	public static int Find_fwd(byte[] src, byte[] lkp, int cur)						{return Find(src, lkp, cur  , src.length, true);}
	public static int Find_fwd(byte[] src, byte[] lkp, int cur, int end)			{return Find(src, lkp, cur	,        end, true);}
	private static final int OffsetCompare = 1;// handle srcPos >= 1 -> srcPosChk > 0
	public static int Find(byte[] src, byte[] lkp, int src_bgn, int src_end, boolean fwd) {
		if (src_bgn < 0 || src.length == 0) return Bry_find_.Not_found;
		int dif, lkp_len = lkp.length, lkp_bgn, lkp_end, src_end_chk; 
		if (fwd) {
			if (src_bgn > src_end) return Bry_find_.Not_found; 
			dif =  1; lkp_bgn = 0;				lkp_end = lkp_len;	src_end_chk = src_end - OffsetCompare;
		}
		else {
			if (src_bgn < src_end) return Bry_find_.Not_found; 
			dif = -1; lkp_bgn = lkp_len - 1;	lkp_end = -1;		src_end_chk = src.length - OffsetCompare;	// src_end_chk needed when going bwd, b/c lkp_len may be > 1
		}
		while (src_bgn != src_end) {									// while src is not done;
			int lkp_cur = lkp_bgn;
			while (lkp_cur != lkp_end) {								// while lkp is not done
				int pos = src_bgn + lkp_cur;
				if (	pos > src_end_chk								// outside bounds; occurs when lkp_len > 1
					||	src[pos] != lkp[lkp_cur])						// srcByte doesn't match lkpByte 
					break;
				else
					lkp_cur += dif;
			}
			if (lkp_cur == lkp_end) return src_bgn;						// lkp matches src; exit
			src_bgn += dif;
		}
		return Bry_find_.Not_found;
	}
	public static int Find_bwd(byte[] src, byte[] lkp, int cur)				{return Find_bwd(src, lkp, cur		, 0);}
	public static int Find_bwd(byte[] src, byte[] lkp, int cur, int end)	{
		if (cur < 1) return Bry_find_.Not_found;
		--cur;	// always subtract 1 from cur; allows passing in src_len or cur_pos without forcing caller to subtract - 1; DATE:2014-02-11
		--end;
		int src_len = src.length;
		int lkp_len = lkp.length;
		for (int i = cur; i > end; i--) {
			if (i + lkp_len > src_len) continue;	// lkp too small for pos; EX: src=abcde; lkp=bcd; pos=4
			boolean match = true;
			for (int j = 0; j < lkp_len; j++) {
				if (lkp[j] != src[i + j]) {
					match = false;
					break;
				} 
			}
			if (match) return i;
		}
		return Bry_find_.Not_found;
	}
	public static int Find_bwd_last_ws(byte[] src, int cur) {
		if (cur < 1) return Bry_find_.Not_found;
		--cur;
		int rv = Bry_find_.Not_found;
		for (int i = cur; i > -1; i--) {
			byte b = src[i];
			switch (b) {
				case Byte_ascii.Space: case Byte_ascii.Tab: case Byte_ascii.Nl: case Byte_ascii.Cr:
					rv = i;
					break;
				default:
					i = -1;
					break;
			}
		}
		return rv;
	}
	public static int Find_bwd_ws(byte[] src, int cur, int end)	{
		for (int i = cur; i > -1; --i) {
			byte b = src[i];
			switch (b) {
				case Byte_ascii.Space: case Byte_ascii.Tab: case Byte_ascii.Nl: case Byte_ascii.Cr:
					return i;
			}
		}
		return Bry_find_.Not_found;
	}
	public static int Find_fwd_last_ws(byte[] src, int cur)	{
		int end = src.length;
		if (cur >= end) return Bry_find_.Not_found;
		int rv = Bry_find_.Not_found;
		for (int i = cur; i < end; i++) {
			byte b = src[i];
			switch (b) {
				case Byte_ascii.Space: case Byte_ascii.Tab: case Byte_ascii.Nl: case Byte_ascii.Cr:
					rv = i;
					break;
				default:
					i = -1;
					break;
			}
		}
		return rv;
	}
	public static int Find_bwd_non_ws_or_not_found(byte[] src, int cur, int end) { // get pos of 1st char that is not ws; 
		if (cur >= src.length) return Bry_find_.Not_found;
		for (int i = cur; i >= end; i--) {
			byte b = src[i];
			switch (b) {
				case Byte_ascii.Space: case Byte_ascii.Tab: case Byte_ascii.Nl: case Byte_ascii.Cr:
					break;
				default:
					return i;
			}
		}
		return Bry_find_.Not_found;
	}
	public static int Find_bwd__while_space_or_tab(byte[] src, int cur, int end) { // get pos of 1st char that is not \t or \s 
		if (cur < 0 || cur > src.length) return Bry_find_.Not_found;
		for (int i = cur - 1; i >= end; i--) {
			byte b = src[i];
			switch (b) {
				case Byte_ascii.Space: case Byte_ascii.Tab:
					break;
				default:
					return i + 1;
			}
		}
		return Bry_find_.Not_found;
	}
	public static int Find_bwd_non_ws_or_end(byte[] src, int cur, int end) {
		if (cur >= src.length) return Bry_find_.Not_found;
		for (int i = cur; i >= end; i--) {
			byte b = src[i];
			switch (b) {
				case Byte_ascii.Space: case Byte_ascii.Tab: case Byte_ascii.Nl: case Byte_ascii.Cr:
					break;
				default:
					return i;
			}
		}
		return end;
	}
	public static int Find_bwd__skip_ws(byte[] src, int end, int bgn) {
		int src_len = src.length;
		if (end == src_len) return end;
		if (end > src_len || end < 0) return Bry_find_.Not_found;
		int pos = end - 1;	// start from end - 1; handles situations where len is passed in
		for (int i = pos; i >= bgn; --i) {
			switch (src[i]) {
				case Byte_ascii.Space: case Byte_ascii.Tab: case Byte_ascii.Nl: case Byte_ascii.Cr:
					break;
				default:
					return i + 1;
			}
		}
		return bgn;
	}
	public static int Find_bwd__skip(byte[] src, int end, int bgn, byte skip) {
		int src_len = src.length; // if (end == src_len) return end;
		if (end > src_len || end < 0) return Bry_find_.Not_found;
		int pos = end - 1;	// start from end - 1; handles situations where len is passed in
		for (int i = pos; i >= bgn; --i) {
			if (src[i] != skip)
				return i + 1;
		}
		return bgn;
	}
	public static int Find_bwd__skip(byte[] src, int end, int bgn, byte[] skip) {
		int src_len = src.length;
		if (end > src_len || end < 0) return Bry_find_.Not_found;
		int skip_len = skip.length;
		int pos = end - skip_len;	// start from end - 1; handles situations where len is passed in
		for (int i = pos; i >= bgn; --i) {
			if (!Bry_.Eq(src, i, i + skip_len, skip))
				return i + skip_len;
		}
		return bgn;
	}
	public static int Find_bwd_while(byte[] src, int cur, int end, byte while_byte) {
		--cur;
		while (true) {
			if (	cur < end
				||	src[cur] != while_byte) return cur;
			--cur;
		}
	}
	public static int Find_bwd_while_v2(byte[] src, int cur, int end, byte while_byte) {
		--cur;
		while (true) {
			if (	cur < end
				||	src[cur] != while_byte) return cur + 1;
			--cur;
		}
	}
	public static int Find_fwd_while(byte[] src, int cur, int end, byte while_byte) {
		while (true) {
			if (	cur == end
				||	src[cur] != while_byte) return cur;
			cur++;
		}
	}
	public static int Find_fwd_while(byte[] src, int cur, int end, byte[] while_bry) {
		int while_len = while_bry.length;
		while (true) {
			if (cur == end) return cur;
			for (int i = 0; i < while_len; i++) {
				if (while_bry[i] != src[i + cur]) return cur;
			}
			cur += while_len;
		}
	}
	public static int Find_fwd_while_in(byte[] src, int cur, int end, boolean[] while_ary) {
		while (cur < end) {
			if (cur == end || !while_ary[src[cur]]) return cur;
			cur++;
		}
		return end;
	}
	public static int Find_fwd_until(byte[] src, int cur, int end, byte until_byte) {
		while (true) {
			if (	cur == end
				||	src[cur] == until_byte) return cur;
			cur++;
		}
	}
	public static int Find_fwd_until_space_or_tab(byte[] src, int cur, int end) {
		while (true) {
			if (cur == end) return Bry_find_.Not_found;
			switch (src[cur])  {
				case Byte_ascii.Space: case Byte_ascii.Tab:
					return cur;
				default: 
					++cur;
					break;
			}
		}
	}
	public static int Find_fwd_until_ws(byte[] src, int cur, int end) {
		while (true) {
			if (cur == end) return Bry_find_.Not_found;
			switch (src[cur])  {
				case Byte_ascii.Space: case Byte_ascii.Tab: case Byte_ascii.Nl: case Byte_ascii.Cr:
					return cur;
				default: 
					++cur;
					break;
			}
		}
	}
	public static int Find_fwd_while_space_or_tab(byte[] src, int cur, int end) {
		while (true) {
			if (cur == end) return cur;
			switch (src[cur]) {
				case Byte_ascii.Space: case Byte_ascii.Tab:		++cur; break;
				default:										return cur; 
			}
		}
	}
	public static int Trim_fwd_space_tab(byte[] src, int cur, int end) {
		while (true) {
			if (cur == end) return cur;
			switch (src[cur]) {
				case Byte_ascii.Space: case Byte_ascii.Tab:		++cur; break;
				default:										return cur; 
			}
		}
	}
	public static int Trim_bwd_space_tab(byte[] src, int cur, int bgn) {
		while (true) {
			int prv_cur = cur - 1;				// check byte before cur; EX: "a b " will have len of 4, and pass cur=4;
			if (prv_cur < bgn) return cur;		// checking byte before prv; exit;
			switch (src[prv_cur]) {
				case Byte_ascii.Space: case Byte_ascii.Tab:		--cur; break;
				default:										return cur; 
			}
		}
	}
	public static int Find_fwd_while_ws(byte[] src, int cur, int end) {
		while (true) {
			if (cur == end) return cur;
			try {
				switch (src[cur]) {
					case Byte_ascii.Nl: case Byte_ascii.Cr:
					case Byte_ascii.Space: case Byte_ascii.Tab:		++cur; break;
					default:										return cur; 
				}
			} catch (Exception e) {throw Err_.new_exc(e, "core", "idx is invalid", "cur", cur, "src", src);}
		}
	}
	public static int Find_fwd_while_letter(byte[] src, int cur, int end) {
		while (cur < end) {
			switch (src[cur]) {
				case Byte_ascii.Ltr_A: case Byte_ascii.Ltr_B: case Byte_ascii.Ltr_C: case Byte_ascii.Ltr_D: case Byte_ascii.Ltr_E:
				case Byte_ascii.Ltr_F: case Byte_ascii.Ltr_G: case Byte_ascii.Ltr_H: case Byte_ascii.Ltr_I: case Byte_ascii.Ltr_J:
				case Byte_ascii.Ltr_K: case Byte_ascii.Ltr_L: case Byte_ascii.Ltr_M: case Byte_ascii.Ltr_N: case Byte_ascii.Ltr_O:
				case Byte_ascii.Ltr_P: case Byte_ascii.Ltr_Q: case Byte_ascii.Ltr_R: case Byte_ascii.Ltr_S: case Byte_ascii.Ltr_T:
				case Byte_ascii.Ltr_U: case Byte_ascii.Ltr_V: case Byte_ascii.Ltr_W: case Byte_ascii.Ltr_X: case Byte_ascii.Ltr_Y: case Byte_ascii.Ltr_Z:
				case Byte_ascii.Ltr_a: case Byte_ascii.Ltr_b: case Byte_ascii.Ltr_c: case Byte_ascii.Ltr_d: case Byte_ascii.Ltr_e:
				case Byte_ascii.Ltr_f: case Byte_ascii.Ltr_g: case Byte_ascii.Ltr_h: case Byte_ascii.Ltr_i: case Byte_ascii.Ltr_j:
				case Byte_ascii.Ltr_k: case Byte_ascii.Ltr_l: case Byte_ascii.Ltr_m: case Byte_ascii.Ltr_n: case Byte_ascii.Ltr_o:
				case Byte_ascii.Ltr_p: case Byte_ascii.Ltr_q: case Byte_ascii.Ltr_r: case Byte_ascii.Ltr_s: case Byte_ascii.Ltr_t:
				case Byte_ascii.Ltr_u: case Byte_ascii.Ltr_v: case Byte_ascii.Ltr_w: case Byte_ascii.Ltr_x: case Byte_ascii.Ltr_y: case Byte_ascii.Ltr_z:
					break;
				default:
					return cur;
			}
			++cur;
		}
		return cur;
	}
	public static int Find_fwd_while_num(byte[] src) {return Find_fwd_while_num(src, 0, src.length);}
	public static int Find_fwd_while_num(byte[] src, int cur, int end) {
		while (cur < end) {
			if (!Byte_ascii.Is_num(src[cur]))
				return cur;
			++cur;
		}
		return cur;
	}
	public static int Find_fwd_while_not_ws(byte[] src, int cur, int end) {
		while (true) {
			if (cur == end) return cur;
			switch (src[cur]) {
				case Byte_ascii.Space:
				case Byte_ascii.Nl:
				case Byte_ascii.Tab:
				case Byte_ascii.Cr:
					++cur;
					break;
				default:
					return cur;
			}
		}
	}
	public static int Find_bwd_while_alphanum(byte[] src, int cur) {return Find_bwd_while_alphanum(src, cur, -1);}
	public static int Find_bwd_while_alphanum(byte[] src, int cur, int end) {
		--cur;
		while (cur > end) {
			switch (src[cur]) {
				case Byte_ascii.Num_0: case Byte_ascii.Num_1: case Byte_ascii.Num_2: case Byte_ascii.Num_3: case Byte_ascii.Num_4:
				case Byte_ascii.Num_5: case Byte_ascii.Num_6: case Byte_ascii.Num_7: case Byte_ascii.Num_8: case Byte_ascii.Num_9:
				case Byte_ascii.Ltr_A: case Byte_ascii.Ltr_B: case Byte_ascii.Ltr_C: case Byte_ascii.Ltr_D: case Byte_ascii.Ltr_E:
				case Byte_ascii.Ltr_F: case Byte_ascii.Ltr_G: case Byte_ascii.Ltr_H: case Byte_ascii.Ltr_I: case Byte_ascii.Ltr_J:
				case Byte_ascii.Ltr_K: case Byte_ascii.Ltr_L: case Byte_ascii.Ltr_M: case Byte_ascii.Ltr_N: case Byte_ascii.Ltr_O:
				case Byte_ascii.Ltr_P: case Byte_ascii.Ltr_Q: case Byte_ascii.Ltr_R: case Byte_ascii.Ltr_S: case Byte_ascii.Ltr_T:
				case Byte_ascii.Ltr_U: case Byte_ascii.Ltr_V: case Byte_ascii.Ltr_W: case Byte_ascii.Ltr_X: case Byte_ascii.Ltr_Y: case Byte_ascii.Ltr_Z:
				case Byte_ascii.Ltr_a: case Byte_ascii.Ltr_b: case Byte_ascii.Ltr_c: case Byte_ascii.Ltr_d: case Byte_ascii.Ltr_e:
				case Byte_ascii.Ltr_f: case Byte_ascii.Ltr_g: case Byte_ascii.Ltr_h: case Byte_ascii.Ltr_i: case Byte_ascii.Ltr_j:
				case Byte_ascii.Ltr_k: case Byte_ascii.Ltr_l: case Byte_ascii.Ltr_m: case Byte_ascii.Ltr_n: case Byte_ascii.Ltr_o:
				case Byte_ascii.Ltr_p: case Byte_ascii.Ltr_q: case Byte_ascii.Ltr_r: case Byte_ascii.Ltr_s: case Byte_ascii.Ltr_t:
				case Byte_ascii.Ltr_u: case Byte_ascii.Ltr_v: case Byte_ascii.Ltr_w: case Byte_ascii.Ltr_x: case Byte_ascii.Ltr_y: case Byte_ascii.Ltr_z:
					--cur;
					break;
				default:
					return cur;
			}
		}
		return 0;	// always return a valid index
	}
}
