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
import gplx.dbs.sqls.*;
public class Db_meta_idx {
	Db_meta_idx(String tbl, String name, boolean unique, String[] flds) {this.tbl = tbl; this.name = name; this.unique = unique; this.flds = flds;}
	public String Tbl() {return tbl;} private final String tbl;
	public String Name() {return name;} private final String name;		
	public boolean Unique() {return unique;} private final boolean unique;
	public String[] Flds() {return flds;} private final String[] flds;
	public String To_sql_create() {return Db_sqlbldr__sqlite.I.Bld_create_idx(this);}
	public static Db_meta_idx new_unique_by_name(String tbl, String idx_name, String... flds)			{return new Db_meta_idx(tbl, idx_name, Bool_.Y, flds);}
	public static Db_meta_idx new_normal_by_name(String tbl, String idx_name, String... flds)			{return new Db_meta_idx(tbl, idx_name, Bool_.N, flds);}
	public static Db_meta_idx new_unique_by_tbl(String tbl, String name, String... flds)	{return new Db_meta_idx(tbl, Bld_idx_name(tbl, name), Bool_.Y, flds);}
	public static Db_meta_idx new_normal_by_tbl(String tbl, String name, String... flds)	{return new Db_meta_idx(tbl, Bld_idx_name(tbl, name), Bool_.N, flds);}
	public static Db_meta_idx new_unique_by_tbl_wo_null(String tbl, String name, String... flds)	{return new Db_meta_idx(tbl, Bld_idx_name(tbl, name), Bool_.Y, String_.Ary_wo_null(flds));}
	public static Db_meta_idx new_normal_by_tbl_wo_null(String tbl, String name, String... flds)	{return new Db_meta_idx(tbl, Bld_idx_name(tbl, name), Bool_.N, String_.Ary_wo_null(flds));}
	public static String Bld_idx_name(String tbl, String suffix) {return String_.Concat(tbl, "__", suffix);}
}
