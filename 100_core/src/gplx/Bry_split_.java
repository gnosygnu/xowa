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
import gplx.core.brys.*;
public class Bry_split_ {
	private static final    Object thread_lock = new Object();
	public static byte[][] Split(byte[] src, byte dlm)				{return Split(src, dlm, false);}
	public static byte[][] Split(byte[] src, byte dlm, boolean trim)	{return src == null ? Bry_.Ary_empty : Split(src, 0, src.length, dlm, trim);}
	public static byte[][] Split(byte[] src, int bgn, int end, byte dlm, boolean trim) {
		synchronized (thread_lock) {
			Bry_split_wkr__to_ary wkr = Bry_split_wkr__to_ary.Instance;
			Split(src, bgn, end, dlm, trim, wkr);
			return wkr.To_ary();
		}
	}
	public static int Split(byte[] src, int src_bgn, int src_end, byte dlm, boolean trim, Bry_split_wkr wkr) {
		if (src == null || src_end - src_bgn < 1) return 0;
		int pos = src_bgn; 
		int itm_bgn = -1, itm_end = -1;
		int count = 0;
		while (true) {
			boolean pos_is_last = pos == src_end;
			byte b = pos_is_last ? dlm : src[pos];
			int nxt_pos = pos + 1;
			boolean process = true;
			switch (b) {
				case Byte_ascii.Space: case Byte_ascii.Tab: case Byte_ascii.Nl: case Byte_ascii.Cr: // ignore ws; assumes that flags have no ws (they are single char) and vnts have no ws (EX: zh-hans)
					if (trim && b != dlm) process = false;			// b != dlm handles cases where ws is dlm, but trim is enabled; EX: " a \n b" -> "a", "b"
					break; 
			}
			if (process) {
				if (b == dlm) {
					boolean reset = true;
					if (itm_bgn == -1) {
						if (pos_is_last) {}							// skip dlm at bgn / end; EX: "a,"
						else {wkr.Split(src, pos, pos );}			// else, process "empty" dlm; EX: ",a"
					}	
					else {
						int rv = wkr.Split(src, itm_bgn, itm_end);
						switch (rv) {
							case Rv__ok:		++count; break;
							case Rv__extend:	reset = false; break;
							case Rv__cancel:	return count;
							default:			throw Err_.new_unhandled(rv);
						}
					}
					if (reset) itm_bgn = itm_end = -1;
				}
				else {
					if (itm_bgn == -1) itm_bgn = pos;
					itm_end = nxt_pos;
				}
			}
			if (pos_is_last) break;
			pos = nxt_pos;
		}
		return count;
	}
	public static byte[][] Split(byte[] src, byte[] dlm) {return Split(src, 0, src.length, dlm);}
	public static byte[][] Split(byte[] src, int src_bgn, int src_end, byte[] dlm) {
		if (src == null) return Bry_.Ary_empty;
		int src_len = src.length;
		if (src_len == 0) return Bry_.Ary_empty;
		int cur_pos = src_bgn, dlm_len = dlm.length;
		List_adp rv = List_adp_.New();
		while (true) {
			int find_pos = Bry_find_.Find_fwd(src, dlm, cur_pos);
			if (find_pos == Bry_find_.Not_found) {
				if (cur_pos >= src_end) break;	// dlm is last sequence in src; do not create empty itm
				find_pos = src_end;			
			}
			rv.Add(Bry_.Mid(src, cur_pos, find_pos));
			cur_pos = find_pos + dlm_len;
			if (cur_pos >= src_end) break;
		}
		return (byte[][])rv.To_ary(byte[].class);
	}
	public static byte[][] Split_lines(byte[] src) {
		if (Bry_.Len_eq_0(src)) return Bry_.Ary_empty;
		int src_len = src.length, src_pos = 0, fld_bgn = 0;
		List_adp rv = List_adp_.New();
		while (true) {
			boolean last = src_pos == src_len;
			byte b = last ? Byte_ascii.Nl : src[src_pos];
			int nxt_bgn = src_pos + 1; 
			switch (b) {
				case Byte_ascii.Cr:
				case Byte_ascii.Nl:
					if (	b == Byte_ascii.Cr		// check for crlf
						&& nxt_bgn < src_len && src[nxt_bgn] == Byte_ascii.Nl) {
							++nxt_bgn;
					}
					if (last && (src_pos - fld_bgn == 0)) {}	// ignore trailing itms
					else
						rv.Add(Bry_.Mid(src, fld_bgn, src_pos));
					fld_bgn = nxt_bgn;
					break;
			}
			if (last) break;
			src_pos = nxt_bgn;
		}
		return (byte[][])rv.To_ary(byte[].class);
	}
	public static byte[][] Split_w_max(byte[] src, byte dlm, int max) {
		byte[][] rv = new byte[max][];
		int src_len = src.length;
		int rv_idx = 0;
		int itm_bgn = 0;
		int src_pos = 0;
		while (true) {
			boolean is_last = src_pos == src_len;
			byte b = is_last ? dlm : src[src_pos];
			if (b == dlm) {
				rv[rv_idx++] = Bry_.Mid(src, itm_bgn, src_pos);
				itm_bgn = src_pos + 1;
			}
			if (is_last || rv_idx == max)
				break;
			else
				src_pos++;
		}
		return rv;
	}

	public static final int Rv__ok = 0, Rv__extend = 1, Rv__cancel = 2;
}
class Bry_split_wkr__to_ary implements gplx.core.brys.Bry_split_wkr {
	private final    List_adp list = List_adp_.New();
	public int Split(byte[] src, int itm_bgn, int itm_end) {
		synchronized (list) {
			byte[] bry = itm_end == itm_bgn ? Bry_.Empty : Bry_.Mid(src, itm_bgn, itm_end);
			list.Add(bry);
			return Bry_split_.Rv__ok;
		}
	}
	public byte[][] To_ary() {
		synchronized (list) {
			return (byte[][])list.To_ary_and_clear(byte[].class);
		}
	}
        public static final    Bry_split_wkr__to_ary Instance = new Bry_split_wkr__to_ary(); Bry_split_wkr__to_ary() {}
}
