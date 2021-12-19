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
package gplx.dbs;
import gplx.types.basics.utls.ArrayUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.basics.constants.AsciiByte;
import gplx.types.basics.utls.StringUtl;
public class Db_sql_ {
	public static String Make_by_fmt(String[] lines, Object... args) {
		BryWtr bfr = BryWtr.New();
		int len = lines.length;
		for (int i = 0; i < len; ++i) {
			if (i != 0) bfr.AddByteNl();
			bfr.AddStrU8(lines[i]);
		}
		String fmt = bfr.ToStrAndClear();
		return StringUtl.Format(fmt, args);
	}
	public static byte[] Escape_arg(byte[] raw) {
		int len = raw.length;
		BryWtr bfr = null;
		boolean dirty = false;

		for (int i = 0; i < len; ++i) {
			byte b = raw[i];
			if (b == AsciiByte.Apos) {
				if (bfr == null) {
					dirty = true;
					bfr = BryWtr.New();
					bfr.AddMid(raw, 0, i);
				}
				bfr.AddByteApos().AddByteApos();
			}
			else {
				if (dirty) {
					bfr.AddByte(b);
				}
			}
		}
		return dirty ? bfr.ToBryAndClear() : raw;
	}
	public static String Prep_in_from_ary(Object ary) {	
		BryWtr bfr = BryWtr.New();
		int len = ArrayUtl.Len(ary);
		for (int i = 0; i < len; i++) {
			if (i != 0) bfr.AddByte(AsciiByte.Comma);
			bfr.AddByte(AsciiByte.Question);
		}
		return bfr.ToStrAndClear();
	}
}
