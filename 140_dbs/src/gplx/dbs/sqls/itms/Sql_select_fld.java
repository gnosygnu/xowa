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
package gplx.dbs.sqls.itms; import gplx.*; import gplx.dbs.*; import gplx.dbs.sqls.*;
import gplx.core.gfo_ndes.*; import gplx.core.type_xtns.*;
public abstract class Sql_select_fld {
	public Sql_select_fld(String tbl, String fld, String alias) {
		this.Tbl = tbl; this.Fld = fld; this.Alias = alias;
	}
	public final String Tbl;				// tbl_alias;	EX: "t1."
	public final String Fld;				// fld_key;		EX: "fld"
	public final String Alias;			// alias;		EX: " AS [Field Name]"; NOTE: must be fld name if no alias defined; EX: "SELECT fld1" should have tbl='', fld='fld1' and alias='fld1'
	public abstract String To_fld_sql();	// EX: "t1.fld AS [Field Name]"
	public String To_fld_alias() {return Alias;}	// EX: "t1.fld AS [Field Name]" -> "Field Name"; "t1.fld1" -> "fld1"
	public String To_fld_key() {return Tbl == Tbl__null ? Fld : Tbl + "." + Fld;}

	public static final String Tbl__null = null, Fld__wildcard = "*";
	public static Sql_select_fld New_fld	(String tbl, String fld, String alias)		{return new Sql_select_fld_col(tbl, fld, alias);}
	public static Sql_select_fld Wildcard	= Sql_select_fld_wild.Instance;
	public static Sql_select_fld New_count	(String tbl, String fld, String alias)		{return new Sql_select_fld_count(tbl, fld, alias);}
	public static Sql_select_fld New_sum	(String tbl, String fld, String alias)		{return new Sql_select_fld_sum(tbl, fld, alias);}
	public static Sql_select_fld New_min	(String tbl, String fld, String alias)		{return new Sql_select_fld_minMax(CompareAble_.Less, tbl, fld, alias);}
	public static Sql_select_fld New_max	(String tbl, String fld, String alias)		{return new Sql_select_fld_minMax(CompareAble_.More, tbl, fld, alias);}
	public static String Bld_tbl_w_fld(String tbl, String fld) {return tbl == null ? fld : tbl + "." + fld;}

	// tdb related functions
	public ClassXtn Val_type() {return val_type;} public void Val_type_(ClassXtn val) {val_type = val;} private ClassXtn val_type = ObjectClassXtn.Instance;
	public abstract Object GroupBy_eval(Object groupByVal, Object curVal, ClassXtn type);
	@gplx.Virtual public void GroupBy_type(ClassXtn type) {this.Val_type_(type);}
}
class Sql_select_fld_wild extends Sql_select_fld {		Sql_select_fld_wild() {super(Sql_select_fld.Tbl__null, Fld__wildcard, Fld__wildcard);}
	@Override public String To_fld_sql() {return Fld__wildcard;}

	public static final Sql_select_fld_wild Instance = new Sql_select_fld_wild();

	// tdb-related functions
	@Override public Object GroupBy_eval(Object groupByVal, Object curVal, ClassXtn type) {throw Err_.new_wo_type("group by eval not allowed on *");}
	@Override public void GroupBy_type(ClassXtn type) {throw Err_.new_wo_type("group by type not allowed on *");}
}
class Sql_select_fld_col extends Sql_select_fld {			public Sql_select_fld_col(String tbl, String fld, String alias) {super(tbl, fld, alias);}
	@Override public String To_fld_sql() {
		String rv = Fld;
		if (this.Tbl != Tbl__null)
			rv = this.Tbl + "." + Fld;
		if (!String_.Eq(Alias, Fld))
			rv = rv + " AS " + Alias;
		return rv;
	}

	// tdb-related functions
	@Override public Object GroupBy_eval(Object groupByVal, Object curVal, ClassXtn type) {return curVal;}
}
