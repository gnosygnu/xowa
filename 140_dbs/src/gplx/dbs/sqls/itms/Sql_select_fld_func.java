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
package gplx.dbs.sqls.itms; import gplx.*; import gplx.dbs.*; import gplx.dbs.sqls.*;
import gplx.core.type_xtns.*;
abstract class Sql_select_fld_func extends Sql_select_fld {		public Sql_select_fld_func(String tbl, String fld, String alias) {super(tbl, fld, alias);}
	public abstract String XtoSql_functionName();
	@Override public String To_sql() {
		return String_.Format("{0}({1}) AS {2}", XtoSql_functionName(), Fld, Alias);
	}
}
class Sql_select_fld_count extends Sql_select_fld_func {			public Sql_select_fld_count(String tbl, String fld, String alias) {super(tbl, fld, alias);}
	@Override public String XtoSql_functionName() {return "COUNT";}
	@Override public void GroupBy_type(ClassXtn type) {this.Val_type_(IntClassXtn.Instance);}
	@Override public Object GroupBy_eval(Object groupByVal, Object curVal, ClassXtn type) {
		if (groupByVal == null) return 1;
		return Int_.cast(groupByVal) + 1;
	}
}
class Sql_select_fld_sum extends Sql_select_fld_func {			public Sql_select_fld_sum(String tbl, String fld, String alias) {super(tbl, fld, alias);}
	@Override public String XtoSql_functionName() {return "SUM";}
	@Override public void GroupBy_type(ClassXtn type) {this.Val_type_(IntClassXtn.Instance);}
	@Override public Object GroupBy_eval(Object groupByVal, Object curVal, ClassXtn type) {
		if (groupByVal == null) return Int_.cast(curVal);
		return Int_.cast(groupByVal) + Int_.cast(curVal);
	}
}
class Sql_select_fld_minMax extends Sql_select_fld_func {		private final int compareType;
	public Sql_select_fld_minMax(int compareType, String tbl, String fld, String alias) {super(tbl, fld, alias);
		this.compareType = compareType;
	}
	@Override public String XtoSql_functionName() {return compareType == CompareAble_.Less ? "MIN" : "MAX";}
	@Override public Object GroupBy_eval(Object groupByVal, Object curVal, ClassXtn type) {
		if (groupByVal == null) return curVal;
		int compareVal = CompareAble_.Compare_obj(curVal, groupByVal);
		return compareVal * compareType > 0 ? curVal : groupByVal;
	}
}
