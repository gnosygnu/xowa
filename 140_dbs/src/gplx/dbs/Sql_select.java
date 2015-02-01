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
import gplx.core.strings.*;
class Sql_select {
	public Sql_select_fld_list Flds() {return flds;} Sql_select_fld_list flds = Sql_select_fld_list.new_();
	public boolean Distinct() {return distinct;} public void Distinct_set(boolean v) {distinct = v;} private boolean distinct;
	public void Add(String fldName) {flds.Add(Sql_select_fld_fld.new_(Sql_select_fld_base.Tbl_null, fldName, fldName));}
	public void Add(String fldName, String alias) {flds.Add(Sql_select_fld_fld.new_(Sql_select_fld_base.Tbl_null, fldName, alias));}
	public void Add(Sql_select_fld_base fld) {flds.Add(fld);}

	public static final Sql_select All = all_(); static Sql_select all_() {Sql_select rv = new_(); rv.Add(Sql_select_fld_wild._); return rv;}
	public static Sql_select new_() {return new Sql_select();} Sql_select() {}
}
abstract class Sql_select_fld_base {
	public String Tbl() {return tbl;} public void Tbl_set(String val) {tbl = val;} private String tbl;
	public String Fld() {return fld;} public void Fld_set(String val) {fld = val;} private String fld;
	public String Alias() {return alias;} public void Alias_set(String val) {alias = val;} private String alias;
	public ClassXtn ValType() {return valType;} public void ValType_set(ClassXtn val) {valType = val;} ClassXtn valType = ObjectClassXtn._;
	public abstract Object GroupBy_eval(Object groupByVal, Object curVal, ClassXtn type);
	@gplx.Virtual public void GroupBy_type(GfoFld fld) {this.ValType_set(fld.Type());}
	@gplx.Virtual public boolean Type_fld() {return true;}
	public abstract String XtoSql();
	public static final String Tbl_null = null;
	@gplx.Internal protected void ctor_(String tbl, String fld, String alias) {
		Tbl_set(tbl); Fld_set(fld); Alias_set(alias);
	}
}
class Sql_select_fld_wild extends Sql_select_fld_base {
	@Override public Object GroupBy_eval(Object groupByVal, Object curVal, ClassXtn type) {throw Err_.new_("group by eval not allowed on *");}
	@Override public void GroupBy_type(GfoFld fld) {throw Err_.new_("group by type not allowed on *");}
	@Override public String XtoSql() {return "*";}
	public static final Sql_select_fld_wild _ = new Sql_select_fld_wild(); Sql_select_fld_wild() {this.ctor_(Tbl_null, "*", "*");}
}
class Sql_select_fld_fld extends Sql_select_fld_base {
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
	public static Sql_select_fld_fld new_(String tbl, String fld, String alias) {
		Sql_select_fld_fld rv = new Sql_select_fld_fld();
		rv.ctor_(tbl, fld, alias);
		return rv;
	}	Sql_select_fld_fld() {}
}
abstract class Sql_select_fld_func_base extends Sql_select_fld_base {
	public abstract String XtoSql_functionName();
	@Override public boolean Type_fld() {return false;}
	@Override public String XtoSql() {
		return String_.Format("{0}({1}) AS {2}", XtoSql_functionName(), Fld(), Alias());
	}
}
class Sql_select_fld_count extends Sql_select_fld_func_base {
	@Override public String XtoSql_functionName() {return "COUNT";}
	@Override public void GroupBy_type(GfoFld fld) {this.ValType_set(IntClassXtn._);}
	@Override public Object GroupBy_eval(Object groupByVal, Object curVal, ClassXtn type) {
		if (groupByVal == null) return 1;
		return Int_.cast_(groupByVal) + 1;
	}
	public static Sql_select_fld_count new_(String tbl, String fld, String alias) {
		Sql_select_fld_count rv = new Sql_select_fld_count();
		rv.ctor_(tbl, fld, alias);
		return rv;
	}	Sql_select_fld_count() {}
}
class Sql_select_fld_sum extends Sql_select_fld_func_base {
	@Override public String XtoSql_functionName() {return "SUM";}
	@Override public void GroupBy_type(GfoFld fld) {this.ValType_set(IntClassXtn._);}
	@Override public Object GroupBy_eval(Object groupByVal, Object curVal, ClassXtn type) {
		if (groupByVal == null) return Int_.cast_(curVal);
		return Int_.cast_(groupByVal) + Int_.cast_(curVal);
	}
	public static Sql_select_fld_sum new_(String tbl, String fld, String alias) {
		Sql_select_fld_sum rv = new Sql_select_fld_sum();
		rv.ctor_(tbl, fld, alias);
		return rv;
	}	Sql_select_fld_sum() {}
}
class Sql_select_fld_minMax extends Sql_select_fld_func_base {
	int compareType = CompareAble_.Less;
	@Override public String XtoSql_functionName() {return compareType == CompareAble_.Less ? "MIN" : "MAX";}
	@Override public Object GroupBy_eval(Object groupByVal, Object curVal, ClassXtn type) {
		if (groupByVal == null) return curVal;
		int compareVal = CompareAble_.Compare_obj(curVal, groupByVal);
		return compareVal * compareType > 0 ? curVal : groupByVal;
	}
	public static Sql_select_fld_minMax min_(String tbl, String fld, String alias) {return new_(CompareAble_.Less, tbl, fld, alias);}
	public static Sql_select_fld_minMax max_(String tbl, String fld, String alias) {return new_(CompareAble_.More, tbl, fld, alias);}
	static Sql_select_fld_minMax new_(int compareType, String tbl, String fld, String alias) {
		Sql_select_fld_minMax rv = new Sql_select_fld_minMax();
		rv.compareType = compareType;
		rv.ctor_(tbl, fld, alias);
		return rv;
	}	Sql_select_fld_minMax() {}
}
class Sql_select_fld_list {
	public int Count() {return hash.Count();}
	public void Add(Sql_select_fld_base fld) {hash.Add(fld.Alias(), fld);}
	public Sql_select_fld_base FetchAt(int i) {return (Sql_select_fld_base)hash.FetchAt(i);}
	public Sql_select_fld_base FetchOrNull(String k) {return (Sql_select_fld_base)hash.Fetch(k);}
	public GfoFldList XtoGfoFldLst(TdbTable tbl) {
		GfoFldList rv = GfoFldList_.new_();
		for (int i = 0; i < this.Count(); i++) {
			Sql_select_fld_base selectFld = this.FetchAt(i);
			GfoFld fld = tbl.Flds().FetchOrNull(selectFld.Fld());
			if (fld == null) throw Err_.new_("fld not found in tbl").Add("fldName", selectFld.Fld()).Add("tblName", tbl.Name()).Add("tblFlds", tbl.Flds().XtoStr());
			if (rv.Has(selectFld.Alias())) throw Err_.new_("alias is not unique").Add("fldName", selectFld.Fld()).Add("flds", rv.XtoStr());
			selectFld.GroupBy_type(fld);
			rv.Add(selectFld.Alias(), selectFld.ValType());
		}
		return rv;
	}
	public String[] To_str_ary() {
		int len = this.Count();
		String[] rv = new String[len];
		for (int i = 0; i < len; i++) {
			Sql_select_fld_base fld = this.FetchAt(i);
			rv[i] = fld.Fld();
		}
		return rv;
	}
	public String XtoStr() {
		String_bldr sb = String_bldr_.new_();
		for (int i = 0; i < this.Count(); i++) {
			Sql_select_fld_base fld = this.FetchAt(i);
			sb.Add_fmt("{0},{1}|", fld.Fld(), fld.Alias());
		}
		return sb.XtoStr();
	}
	OrderedHash hash = OrderedHash_.new_();
	public static Sql_select_fld_list new_() {return new Sql_select_fld_list();} Sql_select_fld_list() {}
}
