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
public class Xoh_hzip_decimal {
	private final Gfo_decimal_parser decimal_parser = new Gfo_decimal_parser();
	public boolean Encode(Bry_bfr bfr, byte[] src, int src_bgn, int src_end) {
		if (!decimal_parser.Parse(src, src_bgn, src_end)) return false;
//			if (positive && dot_pos == -1 && num_val < 128) {
//				bfr.Add_byte((byte)(num_val + 128));
//			}
		return true;
/*
0.001000

1:size_is_1
1:exp.neg
6:exp.val
5:exp.val

1:num.neg
7:num.val; 111-127 for extended bits
8:num.val
8:num.val
127
125 2
126 3
127 4
*/
	}
}
