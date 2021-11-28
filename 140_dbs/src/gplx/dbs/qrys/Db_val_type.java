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

import gplx.Bry_find_;
import gplx.DateAdp_;
import gplx.Decimal_adp_;
import gplx.Object_;
import gplx.String_;
import gplx.objects.lists.GfoListBase;
import gplx.objects.primitives.Bool_;
import gplx.objects.primitives.Byte_;
import gplx.objects.primitives.Char_;
import gplx.objects.primitives.Double_;
import gplx.objects.primitives.Float_;
import gplx.objects.primitives.Int_;
import gplx.objects.primitives.Long_;
import gplx.objects.primitives.Short_;
import gplx.objects.strings.bfrs.GfoStringBuilder;
import gplx.objects.types.Type_;

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
		if      (Type_.Eq(type, Int_.Cls_ref_type))         return Tid_int32;
		else if (Type_.Eq(type, String_.Cls_ref_type))      return Tid_nvarchar;
		else if (Type_.Eq(type, byte[].class))              return Tid_nvarchar;
		else if (Type_.Eq(type, Bool_.Cls_ref_type))        return Tid_bool;
		else if (Type_.Eq(type, Byte_.Cls_ref_type))        return Tid_byte;
		else if (Type_.Eq(type, Long_.Cls_ref_type))        return Tid_int64;
		else if (Type_.Eq(type, Double_.Cls_ref_type))      return Tid_double;
		else if (Type_.Eq(type, Decimal_adp_.Cls_ref_type)) return Tid_decimal;
		else if (Type_.Eq(type, DateAdp_.Cls_ref_type))     return Tid_date;
		else if (Type_.Eq(type, Float_.Cls_ref_type))       return Tid_float;
		else if (Type_.Eq(type, Short_.Cls_ref_type))       return Tid_int16;
		else if (Type_.Eq(type, Char_.Cls_ref_type))        return Tid_char;
		else                                                return Tid_unknown;
	}
	public static String ToSqlStr(String sql, GfoListBase<Object> paramList) {
		try {
			GfoStringBuilder sb = new GfoStringBuilder();
			int oldPos = 0;
			int paramIdx = 0;
			while (true) {
				int newPos = String_.FindFwd(sql, "?", oldPos);
				if (newPos == Bry_find_.Not_found) break;
				if (paramIdx == paramList.Len()) break;
				sb.AddMid(sql, oldPos, newPos);
				Object paramObj = paramList.GetAt(paramIdx++);
				String paramStr = Object_.Xto_str_loose_or(paramObj, "");
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
					sb.Add('\'');
					sb.Add(String_.Replace(paramStr, "'", "\\'"));
					sb.Add('\'');
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
