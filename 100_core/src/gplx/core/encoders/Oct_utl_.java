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
public class Oct_utl_ {
	public static int Parse_or(byte[] src, int or) {return Parse_or(src, 0, src.length, or);}
	public static int Parse_or(byte[] src, int bgn, int end, int or) {
		int rv = 0; int factor = 1;
		byte b = Byte_.Max_value_127;
		for (int i = end - 1; i >= bgn; i--) {
			switch (src[i]) {
				case Byte_ascii.Num_0: b =  0; break; case Byte_ascii.Num_1: b =  1; break; case Byte_ascii.Num_2: b =  2; break; case Byte_ascii.Num_3: b =  3; break; case Byte_ascii.Num_4: b =  4; break;
				case Byte_ascii.Num_5: b =  5; break; case Byte_ascii.Num_6: b =  6; break; case Byte_ascii.Num_7: b =  7; break;
				default: b = Byte_.Max_value_127; break;
			}
			if (b == Byte_.Max_value_127) return or;
			rv += b * factor;
			factor *= 8;
		}
		return rv;
	}
}
