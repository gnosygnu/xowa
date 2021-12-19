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
package gplx.xowa.files.repos;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.basics.constants.AsciiByte;
public class Xof_itm_ttl_ {
	public static byte[] Remove_invalid(BryWtr bfr, byte[] ttl) {
		int ttl_len = ttl.length;
		for (int i = 0; i < ttl_len; i++) {
			byte b = ttl[i];
			if (gplx.core.envs.Op_sys_.Wnt_invalid_char(b)) b = AsciiByte.Underline;
			bfr.AddByte(b);
		}
		return bfr.ToBryAndClear();
	}
	public static byte[] Shorten(BryWtr bfr, byte[] ttl, int ttl_max, byte[] md5, byte[] ext_bry) {
		byte[] rv = ttl;
		int exceed_len = rv.length - ttl_max;               // calc exceed_len;        EX: 21 = 201 - 180
		if (exceed_len > 0) {
			if (ttl_max > 33) {
				bfr.AddMid(rv, 0, ttl_max - 33);           // add truncated title;    33=_.length + md5.length; EX: 180 - 33
			    bfr.AddByte(AsciiByte.Underline);         // add underline;          EX: "_"
			}
			bfr.Add(md5);                                   // add md5;                EX: "abcdefghijklmnopqrstuvwxyz0123456"
			bfr.AddByte(AsciiByte.Dot);                   // add dot;                EX: "."
			bfr.Add(ext_bry);                               // add ext;                EX: ".png"
			rv = bfr.ToBryAndClear();
		}
		return rv;
	}
}
