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
package gplx.xowa.parsers; import gplx.*; import gplx.xowa.*;
public class Xop_ctx_ {
	public static String Page_as_str(Xop_ctx ctx) {return String_.new_u8(ctx.Page().Ttl().Full_db());}
	public static String Src_limit_and_escape_nl(byte[] src, int bgn, int limit) {
		int end = bgn + limit;
		int src_len = src.length;
		if (end > src_len) end = src_len;
		byte[] rv = Bry_.Mid(src, bgn, end);
		rv = Bry_.Replace(rv, Byte_ascii.Nl, Byte_ascii.Tab); // change nl to tab so text editor will show one warning per line
		return String_.new_u8(rv);
	}
}