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
import gplx.Int_;
import gplx.Object_;
import gplx.String_;
public class DbmetaFldItm {
	public DbmetaFldItm(String name, DbmetaFldType type) {
		this.name = name;
		this.type = type;
		this.primary = false;
		this.autonum = false;
		this.defaultVal = DefaultValNull;
	}
	public String Name() {return name;} private final String name;
	public DbmetaFldType Type() {return type;} private final DbmetaFldType type;
	public int Nullable() {return nullable;} public DbmetaFldItm NullableSet(int v) {nullable = v; return this;} private int nullable;
	public DbmetaFldItm NullableSetNull() {return NullableSet(NullableNull);}
	public boolean Primary() {return primary;} public DbmetaFldItm PrimarySetY() {primary = true; return this;} private boolean primary; public DbmetaFldItm PrimarySetN() {primary = false; return this;}
	public boolean Autonum() {return autonum;} public DbmetaFldItm AutonumSetY() {autonum = true; return this;} private boolean autonum;
	public Object DefaultVal() {return defaultVal;} public DbmetaFldItm DefaultValSet(Object v) {defaultVal = v; return this;} private Object defaultVal;
	public boolean Eq(DbmetaFldItm comp) {
		return String_.Eq(name, comp.name)
			&& type.Eq(comp.type)
			&& nullable == comp.nullable
			&& primary == comp.primary
			&& autonum == comp.autonum
			&& Object_.Eq(defaultVal, comp.defaultVal);
	}
	public static final int NullableUnspecified = 0, NullableNull = 1, NullableNotNull = 2;
	public static final Object DefaultValNull = null;
	public static final String KeyNull = null;
	public static final DbmetaFldItm[] AryEmpty = new DbmetaFldItm[0];

	public static DbmetaFldItm NewBool(String name)         {return new DbmetaFldItm(name, DbmetaFldType.ItmBool);}
	public static DbmetaFldItm NewByte(String name)         {return new DbmetaFldItm(name, DbmetaFldType.ItmByte);}
	public static DbmetaFldItm NewShort(String name)        {return new DbmetaFldItm(name, DbmetaFldType.ItmShort);}
	public static DbmetaFldItm NewInt(String name)          {return new DbmetaFldItm(name, DbmetaFldType.ItmInt);}
	public static DbmetaFldItm NewLong(String name)         {return new DbmetaFldItm(name, DbmetaFldType.ItmLong);}
	public static DbmetaFldItm NewFloat(String name)        {return new DbmetaFldItm(name, DbmetaFldType.ItmFloat);}
	public static DbmetaFldItm NewDouble(String name)       {return new DbmetaFldItm(name, DbmetaFldType.ItmDouble);}
	public static DbmetaFldItm NewText(String name)         {return new DbmetaFldItm(name, DbmetaFldType.ItmText);}
	public static DbmetaFldItm NewBry(String name)          {return new DbmetaFldItm(name, DbmetaFldType.ItmBry);}
	public static DbmetaFldItm NewStr(String name, int len) {return new DbmetaFldItm(name, DbmetaFldType.ItmStr(len));}
	public static String[] ToStrAry(DbmetaFldItm[] ary) {
		int len = ary.length;
		String[] rv = new String[len];
		for (int i = 0; i < len; ++i)
			rv[i] = ary[i].name;
		return rv;
	}

	public static final String[] StrAryEmpty = String_.Ary_empty; // marker constant; should add overrides
	public static String Make_or_null(Db_conn conn, DbmetaFldList flds, String tbl_name, int fld_type, Object fld_dflt, String fld_name) {
		boolean tbl_exists = conn.Meta_tbl_exists(tbl_name);
		boolean fld_exists = true;
		if (tbl_exists) {
			fld_exists = conn.Meta_fld_exists(tbl_name, fld_name);
			if (!fld_exists) return DbmetaFldItm.KeyNull;
		}
		DbmetaFldItm fld = null;
		switch (fld_type) {
			case DbmetaFldType.TidInt: fld = DbmetaFldItm.NewInt(fld_name); break;
		}
		if (fld_dflt != null) fld.DefaultValSet(fld_dflt);
		flds.Add(fld);
		return fld.name;
	}
	public static String ToDoubleStrByInt(int v) {return Int_.To_str(v) + ".0";} // move
}
