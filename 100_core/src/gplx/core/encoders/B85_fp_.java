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
package gplx.core.encoders; import gplx.*; import gplx.core.*;
public class B85_fp_ {
	public static byte[] To_bry(double v) {
		String str = Double_.To_str(v);
		byte[] bry = Bry_.new_a7(str); int len = bry.length;
		int num_len = len; boolean neg = false;
		int bgn = 0; int dot = -1;
		if (bry[0] == Byte_ascii.Dash) {neg = true; bgn = 1; --num_len;}
		boolean skip_zeros = true;
		for (int i = bgn; i < len; ++i) {
			byte b = bry[i];
			switch (b) {
				case Byte_ascii.Num_0:
					if (skip_zeros)
						--num_len;
					break;
				case Byte_ascii.Dot:
					skip_zeros = false;
					dot = i;
					--num_len;
					break;
				default:
					skip_zeros = false;
					break;
			}
		}
		int pow = 0;
		if (dot != -1) {
			pow = len - (dot + 1);
			for (int i = 0; i < pow; ++i)
				v *= 10;
		}
		int num = (int)v;
		byte[] rv = new byte[num_len + 1];
		rv[0] = (byte)(Byte_ascii.Dot + pow + (neg ? 45 : 0));
		Base85_.Set_bry(num, rv, 1, 1);
		return rv;
	}
}
