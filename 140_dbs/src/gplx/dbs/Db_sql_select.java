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
import gplx.core.criterias.*;
interface Db_sql_qry {
	String Tbl_main();
}
class Db_sql_qry__select {
	public Db_sql_qry__select(String from) {this.from = from;}
	public String			From() {return from;} private final    String from;
	public Db_sql_col[]		Select() {return select;} private Db_sql_col[] select;
//		public Criteria			Where() {return where;} private Criteria where;
//		public Db_sql_col[]		Group_bys() {return group_bys;} private Db_sql_col[] group_bys;
//		public Db_sql_col[]		Order_bys() {return order_bys;} private Db_sql_col[] order_bys;
//		public int				Limit() {return limit;} private int limit;
//		public int				Offset() {return offset;} private int offset;
	public Db_sql_qry__select Select_all_()						{this.select = Db_sql_col_.Ary(new Db_sql_col__all(0, from)); return this;}
	public Db_sql_qry__select Select_flds_(String... ary)	{this.select = Db_sql_col_bldr.Instance.new_fld_many(ary); return this;}
	public static Db_sql_qry__select new_(String from) {return new Db_sql_qry__select(from);}
}
class Db_sql_bldr {
	public void Test() {
//			Db_sql_qry__select qry = null;
//			qry = Db_sql_qry__select.new_("tbl").Select_all_();
//			qry = Db_sql_qry__select.new_("tbl").Select_flds_("fld1", "fld2");
//			qry = Db_sql_qry__select.new_("tbl").Select_flds_("fld1", "fld2").Where_("fld3");
//				, String_.Ary("col1", "col2"), String_.Ary("col3")).Limit_(10).;
//			Db_sql_qry__select qry = Db_sql_qry__select_.new_("tbl").Cols_("col1", "col2").Where_eq_one("col3").Limit_(10);
	}
}
interface Db_sql_col {
	int Ord();
	String Alias();
}
class Db_sql_col_ {
	public static Db_sql_col[] Ary(Db_sql_col... v) {return v;}
}
class Db_sql_col_bldr {
	private final    List_adp tmp_list = List_adp_.New();
	public Db_sql_col[] new_fld_many(String[] ary) {
		tmp_list.Clear();
		int ord = -1;
		for (int i = 0; i < ary.length; ++i) {
			String fld_key = ary[i];
			if (fld_key == Dbmeta_fld_itm.Key_null) continue;
			Db_sql_col__name fld = new Db_sql_col__name(++ord, fld_key);
			tmp_list.Add(fld);
		}
		return (Db_sql_col[])tmp_list.To_ary_and_clear(Db_sql_col.class);
	}
        public static final    Db_sql_col_bldr Instance = new Db_sql_col_bldr(); Db_sql_col_bldr() {}
}
class Db_sql_col__name {
	public Db_sql_col__name(int ord, String key) {this.ord = ord; this.key = key;}
	public int Ord() {return ord;} private final    int ord;
	public String Key() {return key;} private final    String key;
}
class Db_sql_col__all implements Db_sql_col {
	public Db_sql_col__all(int ord, String tbl) {this.ord = ord; this.tbl = tbl;}
	public int Ord() {return ord;} private final    int ord;
        public String Tbl() {return tbl;} private final    String tbl;
	public String Alias() {return "*";}
}
