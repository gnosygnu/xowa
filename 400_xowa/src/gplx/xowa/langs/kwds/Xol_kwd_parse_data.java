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
package gplx.xowa.langs.kwds; import gplx.*;
import gplx.core.primitives.*;
import gplx.objects.strings.AsciiByte;
public class Xol_kwd_parse_data {
	public static byte[] Strip(Bry_bfr tmp, byte[] raw, Byte_obj_ref rslt) {
		int raw_len = raw.length;
		boolean dirty = false;
		for (int i = 0; i < raw_len; i++) {
			byte b = raw[i];
			switch (b) {
				case AsciiByte.Dollar:
					byte prv = i == 0 ? AsciiByte.Null : raw[i - 1];
					switch (prv) {
						case AsciiByte.Backslash: {	// ignore \$
							if (dirty)	tmp.Add_byte(b);
							else		{tmp.Add_mid(raw, 0, i - 1); dirty = true;}	// i - 1 to ignore backslash
							break;
						}
						case AsciiByte.Eq: {			// assume end; EX: link=$1
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
										case AsciiByte.Num0: case AsciiByte.Num1: case AsciiByte.Num2: case AsciiByte.Num3: case AsciiByte.Num4:
										case AsciiByte.Num5: case AsciiByte.Num6: case AsciiByte.Num7: case AsciiByte.Num8: case AsciiByte.Num9:
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
