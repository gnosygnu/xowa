/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2021 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.dbs.qrys;

import gplx.types.custom.brys.BryFind;
import gplx.types.basics.utls.StringUtl;
import gplx.types.commons.GfoDateUtl;
import gplx.types.commons.GfoDecimalUtl;
import gplx.types.basics.utls.ObjectUtl;
import gplx.types.commons.lists.GfoListBase;
import gplx.types.basics.utls.BoolUtl;
import gplx.types.basics.utls.ByteUtl;
import gplx.types.basics.utls.CharUtl;
import gplx.types.basics.utls.DoubleUtl;
import gplx.types.basics.utls.FloatUtl;
import gplx.types.basics.utls.IntUtl;
import gplx.types.basics.utls.LongUtl;
import gplx.types.basics.utls.ShortUtl;
import gplx.types.basics.strings.bfrs.GfoStringBldr;
import gplx.types.basics.utls.ClassUtl;

public class Db_val_type {
	public static final byte // not serialized
	  Tid_null      = 0
	, Tid_bool      = 1
	, Tid_byte      = 2
	, Tid_int32     = 3
	, Tid_int64     = 4
	, Tid_date      = 5
	, Tid_decimal   = 6
	, Tid_float     = 7
	, Tid_double    = 8
	, Tid_bry       = 9
	, Tid_varchar   = 10
	, Tid_nvarchar  = 11
	, Tid_rdr       = 12
	, Tid_text      = 13
	, Tid_int16     = 14
	, Tid_char      = 15
	, Tid_unknown   = 16
	;
	public static int ToTypeId(Object o) {
		Class<?> type = o.getClass();
		if      (ClassUtl.Eq(type, IntUtl.ClsRefType))         return Tid_int32;
		else if (ClassUtl.Eq(type, StringUtl.ClsRefType))      return Tid_nvarchar;
		else if (ClassUtl.Eq(type, byte[].class))              return Tid_nvarchar;
		else if (ClassUtl.Eq(type, BoolUtl.ClsRefType))        return Tid_bool;
		else if (ClassUtl.Eq(type, ByteUtl.ClsRefType))        return Tid_byte;
		else if (ClassUtl.Eq(type, LongUtl.ClsRefType))        return Tid_int64;
		else if (ClassUtl.Eq(type, DoubleUtl.ClsRefType))      return Tid_double;
		else if (ClassUtl.Eq(type, GfoDecimalUtl.Cls_ref_type)) return Tid_decimal;
		else if (ClassUtl.Eq(type, GfoDateUtl.ClsRefType))     return Tid_date;
		else if (ClassUtl.Eq(type, FloatUtl.ClsRefType))       return Tid_float;
		else if (ClassUtl.Eq(type, ShortUtl.ClsRefType))       return Tid_int16;
		else if (ClassUtl.Eq(type, CharUtl.ClsRefType))        return Tid_char;
		else                                                return Tid_unknown;
	}
	public static String ToSqlStr(String sql, GfoListBase<Object> paramList) {
		try {
			GfoStringBldr sb = new GfoStringBldr();
			int oldPos = 0;
			int paramIdx = 0;
			while (true) {
				int newPos = StringUtl.FindFwd(sql, "?", oldPos);
				if (newPos == BryFind.NotFound) break;
				if (paramIdx == paramList.Len()) break;
				sb.AddMid(sql, oldPos, newPos);
				Object paramObj = paramList.GetAt(paramIdx++);
				String paramStr = ObjectUtl.ToStrLooseOr(paramObj, "");
				boolean quote = false;
				switch (ToTypeId(paramObj)) {
					case Tid_char:
					case Tid_bry:
					case Tid_varchar:
					case Tid_nvarchar:
					case Tid_date:
						quote = true;
						break;
				}
				if (quote) {
					sb.AddChar('\'');
					sb.Add(StringUtl.Replace(paramStr, "'", "\\'"));
					sb.AddChar('\'');
				} else {
					sb.Add(paramStr);
				}
				oldPos = newPos + 1;
			}
			sb.AddMid(sql, oldPos);
			return sb.ToStr();
		} catch (Exception exc) {
			return "FAILED TO INTERPOLATE:" + sql;
		}
	}
}
