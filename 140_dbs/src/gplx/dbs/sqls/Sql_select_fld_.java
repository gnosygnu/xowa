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
package gplx.dbs.sqls; import gplx.*; import gplx.dbs.*;
public class Sql_select_fld_ {
	public static Sql_select_fld_base new_fld(String tbl, String fld, String alias)		{return new Sql_select_fld_fld(tbl, fld, alias);}
	public static Sql_select_fld_base new_count(String tbl, String fld, String alias)	{return new Sql_select_fld_count(tbl, fld, alias);}
	public static Sql_select_fld_base new_sum(String tbl, String fld, String alias)		{return new Sql_select_fld_sum(tbl, fld, alias);}
	public static Sql_select_fld_base new_min(String tbl, String fld, String alias)		{return new Sql_select_fld_minMax(CompareAble_.Less, tbl, fld, alias);}
	public static Sql_select_fld_base new_max(String tbl, String fld, String alias)		{return new Sql_select_fld_minMax(CompareAble_.More, tbl, fld, alias);}
}
class Sql_select_fld_fld extends Sql_select_fld_base {
	public Sql_select_fld_fld(String tbl, String fld, String alias) {this.ctor_(tbl, fld, alias);}
	@Override public Object GroupBy_eval(Object groupByVal, Object curVal, ClassXtn type) {return curVal;}
	@Override public void GroupBy_type(GfoFld fld) {this.ValType_set(fld.Type());}
	@Override public String XtoSql() {
		String rv = Fld();
		if (Tbl() != Tbl_null)
			rv = Tbl() + "." + Fld();
		if (!String_.Eq(Alias(), Fld()))
			rv = rv + " AS " + Alias();
		return rv;
	}
}
class Sql_select_fld_count extends Sql_select_fld_func_base {
	public Sql_select_fld_count(String tbl, String fld, String alias) {this.ctor_(tbl, fld, alias);}
	@Override public String XtoSql_functionName() {return "COUNT";}
	@Override public void GroupBy_type(GfoFld fld) {this.ValType_set(IntClassXtn._);}
	@Override public Object GroupBy_eval(Object groupByVal, Object curVal, ClassXtn type) {
		if (groupByVal == null) return 1;
		return Int_.cast_(groupByVal) + 1;
	}
}
class Sql_select_fld_sum extends Sql_select_fld_func_base {
	public Sql_select_fld_sum(String tbl, String fld, String alias) {this.ctor_(tbl, fld, alias);}
	@Override public String XtoSql_functionName() {return "SUM";}
	@Override public void GroupBy_type(GfoFld fld) {this.ValType_set(IntClassXtn._);}
	@Override public Object GroupBy_eval(Object groupByVal, Object curVal, ClassXtn type) {
		if (groupByVal == null) return Int_.cast_(curVal);
		return Int_.cast_(groupByVal) + Int_.cast_(curVal);
	}
}
class Sql_select_fld_minMax extends Sql_select_fld_func_base {
	private final int compareType;
	public Sql_select_fld_minMax(int compareType, String tbl, String fld, String alias) {
		this.compareType = compareType;
		this.ctor_(tbl, fld, alias);
	}
	@Override public String XtoSql_functionName() {return compareType == CompareAble_.Less ? "MIN" : "MAX";}
	@Override public Object GroupBy_eval(Object groupByVal, Object curVal, ClassXtn type) {
		if (groupByVal == null) return curVal;
		int compareVal = CompareAble_.Compare_obj(curVal, groupByVal);
		return compareVal * compareType > 0 ? curVal : groupByVal;
	}
}
