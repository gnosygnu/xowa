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
package gplx.dbs;
import gplx.Err_;
import gplx.Type_ids_;
import gplx.objects.strings.String_;
public class DbmetaFldType {
	public DbmetaFldType(int tid, String name, int len1, int len2) {
		this.tid = tid;
		this.name = name;
		this.len1 = len1;
		this.len2 = len2;
	}
	public int Tid()     {return tid;} private final int tid;
	public String Name() {return name;} private final String name;
	public int Len1()    {return len1;} private final int len1; // length in bytes or precision
	public int Len2()    {return len2;} private final int len2; // scaling
	public boolean Eq(DbmetaFldType comp) {
		return tid == comp.tid
			&& String_.Eq(name, comp.name)
			&& len1 == comp.len1
			&& len2 == comp.len2;
	}
	public static final int
		TidBool = 0,
		TidByte = 1,
		TidShort = 2,
		TidInt = 3,
		TidLong = 4,
		TidFloat = 5,
		TidDouble = 6,
		TidStr = 7,
		TidText = 8,
		TidBry = 9,
		TidDecimal = 10,
		TidDate = 11;
	public static final DbmetaFldType
		ItmBool = new DbmetaFldType(DbmetaFldType.TidBool, "bit", -1, -1),
		ItmByte = new DbmetaFldType(DbmetaFldType.TidByte, "tinyint", -1, -1),
		ItmShort = new DbmetaFldType(DbmetaFldType.TidShort, "smallint", -1, -1),
		ItmInt = new DbmetaFldType(DbmetaFldType.TidInt, "integer", -1, -1),
		ItmLong = new DbmetaFldType(DbmetaFldType.TidLong, "bigint", -1, -1),
		ItmFloat = new DbmetaFldType(DbmetaFldType.TidFloat, "float", -1, -1),
		ItmDouble = new DbmetaFldType(DbmetaFldType.TidDouble, "double", -1, -1),
		ItmText = new DbmetaFldType(DbmetaFldType.TidText, "text", -1, -1),
		ItmBry = new DbmetaFldType(DbmetaFldType.TidBry, "blob", -1, -1),
		ItmDate = new DbmetaFldType(DbmetaFldType.TidDate, "date", -1, -1);
	public static DbmetaFldType ItmStr(int len) {return new DbmetaFldType(DbmetaFldType.TidStr, "varchar", len, -1);}
	public static DbmetaFldType ItmDecimal(int len_1, int len_2) {return new DbmetaFldType(DbmetaFldType.TidDecimal, "decimal", len_1, len_2);}
	public static DbmetaFldType New(int tid, int len1) {
		switch (tid) {
			case DbmetaFldType.TidBool:         return ItmBool;
			case DbmetaFldType.TidByte:         return ItmByte;
			case DbmetaFldType.TidShort:        return ItmShort;
			case DbmetaFldType.TidInt:          return ItmInt;
			case DbmetaFldType.TidLong:         return ItmLong;
			case DbmetaFldType.TidFloat:        return ItmFloat;
			case DbmetaFldType.TidDouble:       return ItmDouble;
			case DbmetaFldType.TidStr:          return ItmStr(len1);
			case DbmetaFldType.TidText:         return ItmText;
			case DbmetaFldType.TidBry:          return ItmBry;
			case DbmetaFldType.TidDate:         return ItmDate;
			case DbmetaFldType.TidDecimal:      // return Itm__decimal(len1);
			default:                            throw Err_.new_unhandled(tid);
		}
	}
	public static int GetTypeIdByObj(Object o) {
		int type_id = Type_ids_.To_id_by_obj(o);
		switch (type_id) {
			case Type_ids_.Id__bool:               return DbmetaFldType.TidBool;
			case Type_ids_.Id__byte:               return DbmetaFldType.TidByte;
			case Type_ids_.Id__short:              return DbmetaFldType.TidShort;
			case Type_ids_.Id__int:                return DbmetaFldType.TidInt;
			case Type_ids_.Id__long:               return DbmetaFldType.TidLong;
			case Type_ids_.Id__float:              return DbmetaFldType.TidFloat;
			case Type_ids_.Id__double:             return DbmetaFldType.TidDouble;
			case Type_ids_.Id__str:                return DbmetaFldType.TidStr;
			case Type_ids_.Id__bry:                return DbmetaFldType.TidBry;
			case Type_ids_.Id__date:               return DbmetaFldType.TidDate;
			case Type_ids_.Id__decimal:            return DbmetaFldType.TidDecimal;
			default:                               throw Err_.new_unhandled_default(type_id);
		}
	}
}
