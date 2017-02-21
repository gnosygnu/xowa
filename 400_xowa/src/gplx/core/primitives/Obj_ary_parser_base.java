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
package gplx.core.primitives; import gplx.*; import gplx.core.*;
public abstract class Obj_ary_parser_base {
	int pos_len = 4; int[] pos;
	protected abstract void Ary_len_(int v);
	protected abstract void Parse_itm(byte[] bry, int bgn, int end);
	protected void Parse_core(byte[] bry, int bgn, int end, byte dlm_1, byte dlm_2) {
		if (pos == null) pos = new int[pos_len];
		if (end - bgn == 0) {
			this.Ary_len_(0);
			return;
		}
		int pos_idx = 0;
		int dlm_last = -1;	// NOTE: -1 b/c dlm_2 can be 0 (Byte_ascii.Null) and need to do dlm_last != dlm_2 check below
		for (int i = bgn; i < end; i++) {
			byte b = bry[i];
			if (b == dlm_1 || b == dlm_2) {
				if (pos_idx == pos_len - 1) {	// -1 b/c pos[] will always be count_of_dlm + 1
					pos_len *= 2;
					int[] pos_new = new int[pos_len];
					Array_.Copy(pos, pos_new);
					pos = pos_new;
				} 
				pos[pos_idx++] = i;
				dlm_last = b;
			}
		}
		if (dlm_last != dlm_2)
			pos[pos_idx++] = end;
		this.Ary_len_(pos_idx);
		int parse_bgn = bgn; 
		for (int i = 0; i < pos_idx; i++) {
			int parse_end = pos[i];
			this.Parse_itm(bry, parse_bgn, parse_end);
			parse_bgn = parse_end + 1;
		}
		if (pos_len > 255) {	// reset
			pos_len = 4;
			pos = null;
		}
	}	
}