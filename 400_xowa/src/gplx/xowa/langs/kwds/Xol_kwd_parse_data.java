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
package gplx.xowa.langs.kwds; import gplx.*; import gplx.xowa.*; import gplx.xowa.langs.*;
import gplx.core.primitives.*;
public class Xol_kwd_parse_data {
	public static byte[] Strip(Bry_bfr tmp, byte[] raw, Byte_obj_ref rslt) {
		int raw_len = raw.length;
		boolean dirty = false;
		for (int i = 0; i < raw_len; i++) {
			byte b = raw[i];
			switch (b) {
				case Byte_ascii.Dollar:
					byte prv = i == 0 ? Byte_ascii.Null : raw[i - 1];
					switch (prv) {
						case Byte_ascii.Backslash: {	// ignore \$
							if (dirty)	tmp.Add_byte(b);
							else		{tmp.Add_mid(raw, 0, i - 1); dirty = true;}	// i - 1 to ignore backslash
							break;
						}
						case Byte_ascii.Eq: {			// assume end; EX: link=$1
							dirty = true;
							tmp.Add_mid(raw, 0, i - 1);	// - 1 to ignore =
							rslt.Val_(Strip_end);
							i = raw_len;
							break;
						}
						default: {
							if (i == 0) {
								rslt.Val_(Strip_bgn);
								dirty = true;
								int txt_bgn = 1;
								for (int j = 1; j < raw_len; j++) {
									b = raw[j];
									switch (b) {
										case Byte_ascii.Num_0: case Byte_ascii.Num_1: case Byte_ascii.Num_2: case Byte_ascii.Num_3: case Byte_ascii.Num_4:
										case Byte_ascii.Num_5: case Byte_ascii.Num_6: case Byte_ascii.Num_7: case Byte_ascii.Num_8: case Byte_ascii.Num_9:
											break;
										default:
											txt_bgn = j;
											j = raw_len;
											break;
									}
								}
								tmp.Add_mid(raw, txt_bgn, raw_len);
							}
							else {
								dirty = true;
								tmp.Add_mid(raw, 0, i);
								rslt.Val_(Strip_end);
								i = raw_len;
							}
							i = raw_len;
							break;
						}
					}
					break;
				default:
					if (dirty) tmp.Add_byte(b);
					break;
			}
		}
		return dirty ? tmp.To_bry_and_clear() : raw;
	}
	public static final byte Strip_none = 0, Strip_bgn = 1, Strip_end = 2;
}
