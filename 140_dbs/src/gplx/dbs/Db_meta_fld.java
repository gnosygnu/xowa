/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012 gnosygnu@gmail.com

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package gplx.dbs; import gplx.*;
public class Db_meta_fld {
	public Db_meta_fld(String name, int tid, int len) {
		this.name = name; this.tid = tid; this.len = len;
		this.nullable = false; this.primary = false; this.autonum = false; this.default_val = Default_value_null;
	}
	public int Tid() {return tid;} private final int tid;
	public String Name() {return name;} private final String name;
	public int Len() {return len;} private final int len;
	public boolean Nullable() {return nullable;} public Db_meta_fld Nullable_y_() {nullable = true; return this;} private boolean nullable;
	public boolean Primary() {return primary;} public Db_meta_fld Primary_y_() {primary = true; return this;} private boolean primary;
	public boolean Autonum() {return autonum;} public Db_meta_fld Autonum_y_() {autonum = true; return this;} private boolean autonum;
	public Object Default() {return default_val;} public Db_meta_fld Default_(Object v) {default_val = v; return this;} private Object default_val;

	public static final int Tid_bool = 0, Tid_byte = 1, Tid_short = 2, Tid_int = 3, Tid_long = 4, Tid_float = 5, Tid_double = 6, Tid_str = 7, Tid_text = 8, Tid_bry = 9, Tid_decimal = 10, Tid_date = 11;
	public static final String Key_null = null;
	public static final int Len_null = -1;
	public static final Object Default_value_null = null;
	public static final String[] Ary_empty = String_.Ary_empty;
	public static Db_meta_fld new_bool(String name)			{return new Db_meta_fld(name, Tid_bool	, Len_null);}
	public static Db_meta_fld new_byte(String name)			{return new Db_meta_fld(name, Tid_byte	, Len_null);}
	public static Db_meta_fld new_short(String name)		{return new Db_meta_fld(name, Tid_short	, Len_null);}
	public static Db_meta_fld new_int(String name)			{return new Db_meta_fld(name, Tid_int	, Len_null);}
	public static Db_meta_fld new_long(String name)			{return new Db_meta_fld(name, Tid_long	, Len_null);}
	public static Db_meta_fld new_float(String name)		{return new Db_meta_fld(name, Tid_float	, Len_null);}
	public static Db_meta_fld new_double(String name)		{return new Db_meta_fld(name, Tid_double, Len_null);}
	public static Db_meta_fld new_text(String name)			{return new Db_meta_fld(name, Tid_text	, Len_null);}
	public static Db_meta_fld new_bry(String name)			{return new Db_meta_fld(name, Tid_bry	, Len_null);}
	public static Db_meta_fld new_str(String name, int len)	{return new Db_meta_fld(name, Tid_str	, len);}
}
