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
package gplx.dbs.metas; import gplx.*; import gplx.dbs.*;
public class Dbmeta_idx_fld {
	public Dbmeta_idx_fld(String name, int sort_tid) {this.Name = name; this.Sort_tid = sort_tid;}
	public String Name;
	public int Sort_tid;
	public boolean Eq(Dbmeta_idx_fld comp) {
		return	String_.Eq(Name, comp.Name)
			&&	Sort_tid == comp.Sort_tid;
	}

	public static final    Dbmeta_idx_fld[] Ary_empty = new Dbmeta_idx_fld[0];
	public static final int Sort_tid__none = 0, Sort_tid__asc = 1, Sort_tid__desc = 2;
	public static boolean Ary_eq(Dbmeta_idx_fld[] lhs_ary, Dbmeta_idx_fld[] rhs_ary) {
		int lhs_len = lhs_ary.length, rhs_len = rhs_ary.length;
		if (lhs_len != rhs_len) return false;
		for (int i = 0; i < lhs_len; ++i)
			if (!lhs_ary[i].Eq(rhs_ary[i])) return false;
		return true;
	}

	public static Dbmeta_idx_fld Dsc(String name) {return new Dbmeta_idx_fld(name, Sort_tid__desc);}
}
