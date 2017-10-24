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
package gplx.dbs; import gplx.*;
import gplx.dbs.metas.*;
public class Dbmeta_fld_itm {
	public Dbmeta_fld_itm(String name, Dbmeta_fld_tid type) {
		this.name = name; this.type = type;
		this.primary = false; this.autonum = false; this.default_val = Default_value_null;
	}
	public String			Name()			{return name;} private final    String name;
	public Dbmeta_fld_tid	Type()			{return type;} private final    Dbmeta_fld_tid type;
	public int				Nullable_tid()	{return nullable_tid;} public Dbmeta_fld_itm Nullable_tid_(int v) {nullable_tid = v; return this;} private int nullable_tid;
	public Dbmeta_fld_itm	Nullable_y_()	{return Nullable_tid_(Nullable_null);} 
	public boolean				Primary()		{return primary;} public Dbmeta_fld_itm Primary_y_() {primary = true; return this;} private boolean primary; public Dbmeta_fld_itm Primary_n_() {primary = false; return this;}
	public boolean				Autonum()		{return autonum;} public Dbmeta_fld_itm Autonum_y_() {autonum = true; return this;} private boolean autonum;
	public Object			Default()		{return default_val;} public Dbmeta_fld_itm Default_(Object v) {default_val = v; return this;} private Object default_val;
	public boolean Eq(Dbmeta_fld_itm comp) {
		return	String_.Eq(name, comp.name)
			&&	type.Eq(comp.type)
			&&	nullable_tid == comp.nullable_tid
			&&	primary == comp.primary
			&&	autonum == comp.autonum
			&& Object_.Eq(default_val, comp.default_val);
	}

	public static final int Nullable_unknown = 0, Nullable_null = 1, Nullable_not_null = 2;
	public static final    Object Default_value_null = null;
	public static final String Key_null = null;
	public static final    String[] Str_ary_empty = String_.Ary_empty;
	public static final    Dbmeta_fld_itm[] Ary_empty = new Dbmeta_fld_itm[0];

	public static Dbmeta_fld_itm new_bool(String name)			{return new Dbmeta_fld_itm(name, Dbmeta_fld_tid.Itm__bool);}
	public static Dbmeta_fld_itm new_byte(String name)			{return new Dbmeta_fld_itm(name, Dbmeta_fld_tid.Itm__byte);}
	public static Dbmeta_fld_itm new_short(String name)			{return new Dbmeta_fld_itm(name, Dbmeta_fld_tid.Itm__short);}
	public static Dbmeta_fld_itm new_int(String name)			{return new Dbmeta_fld_itm(name, Dbmeta_fld_tid.Itm__int);}
	public static Dbmeta_fld_itm new_long(String name)			{return new Dbmeta_fld_itm(name, Dbmeta_fld_tid.Itm__long);}
	public static Dbmeta_fld_itm new_float(String name)			{return new Dbmeta_fld_itm(name, Dbmeta_fld_tid.Itm__float);}
	public static Dbmeta_fld_itm new_double(String name)		{return new Dbmeta_fld_itm(name, Dbmeta_fld_tid.Itm__double);}
	public static Dbmeta_fld_itm new_text(String name)			{return new Dbmeta_fld_itm(name, Dbmeta_fld_tid.Itm__text);}
	public static Dbmeta_fld_itm new_bry(String name)			{return new Dbmeta_fld_itm(name, Dbmeta_fld_tid.Itm__bry);}
	public static Dbmeta_fld_itm new_str(String name, int len)	{return new Dbmeta_fld_itm(name, Dbmeta_fld_tid.Itm__str(len));}
	public static String[] To_str_ary(Dbmeta_fld_itm[] ary) {
		int len = ary.length;
		String[] rv = new String[len];
		for (int i = 0; i < len; ++i)
			rv[i] = ary[i].name;
		return rv;
	}

	public static String Make_or_null(Db_conn conn, Dbmeta_fld_list flds, String tbl_name, int fld_type, Object fld_dflt, String fld_name) {
		boolean tbl_exists = conn.Meta_tbl_exists(tbl_name);
		boolean fld_exists = true;
		if (tbl_exists) {
			fld_exists = conn.Meta_fld_exists(tbl_name, fld_name);
			if (!fld_exists) return Dbmeta_fld_itm.Key_null;
		}
		Dbmeta_fld_itm fld = null;
		switch (fld_type) {
			case Dbmeta_fld_tid.Tid__int: fld = Dbmeta_fld_itm.new_int(fld_name); break;
		}
		if (fld_dflt != null) fld.Default_(fld_dflt);
		flds.Add(fld);
		return fld.name;
	}
	public static String To_double_str_by_int(int v) {return Int_.To_str(v) + ".0";}
}
