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
package gplx.xowa.parsers.tmpls; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import gplx.xowa.xtns.pfuncs.*;
public class Xop_func_arg_itm {
	public byte[] key;
	public byte[] val;
	public void Set(Bry_bfr bfr, Xop_ctx ctx, byte[] src, Xot_invk caller, Xot_invk self, Arg_nde_tkn arg) {
		Pf_func_.Eval_arg_or(bfr, ctx, src, caller, self, arg, null);
		key = val = Bry_.Empty;
		int len = bfr.Len(); byte[] bry = bfr.Bfr();
		int bgn = -1, end = -1; boolean mode_is_key = true;
		for (int i = 0; i < len; ++i) {
			byte b = bry[i];
			switch (b) {
				case Byte_ascii.Eq:
					if (mode_is_key) {
						mode_is_key = false;
						key = Bry_.Mid(bry, bgn, end);
						bgn = end = -1;
					}
					break;
				case Byte_ascii.Tab: case Byte_ascii.Nl: case Byte_ascii.Cr: case Byte_ascii.Space:
					break;
				default:
					if (bgn == -1) bgn = i;
					end = i + 1;
					break;
			}
			if (bgn != -1)	// guard against 'key='
				val = Bry_.Mid(bry, bgn, end);
		}
		bfr.Clear();
	}
}
