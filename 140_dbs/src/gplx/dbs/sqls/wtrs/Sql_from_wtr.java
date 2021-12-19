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
package gplx.dbs.sqls.wtrs;
import gplx.dbs.sqls.itms.Sql_from_clause;
import gplx.dbs.sqls.itms.Sql_join_fld;
import gplx.dbs.sqls.itms.Sql_tbl_itm;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.basics.lists.List_adp;
import gplx.types.errs.ErrUtl;
public class Sql_from_wtr {
	public void Bld_clause_from(BryWtr bfr, Sql_from_clause from) {
		List_adp tbls = from.Tbls; int tbls_len = tbls.Len();
		for (int i = 0; i < tbls_len; ++i) {
			Sql_tbl_itm tbl = (Sql_tbl_itm)tbls.GetAt(i);
			bfr.AddByteSpace().AddStrA7(Bld_join(tbl.Join_tid)).AddByteSpace();
			if (tbl.Db != Sql_tbl_itm.Db__null && tbl.Db_enabled)
				bfr.AddStrU8(tbl.Db).AddByteDot();
			bfr.AddStrU8(tbl.Name);
			if (tbl.Alias != Sql_tbl_itm.Alias__null)
				bfr.AddByteSpace().AddStrU8(tbl.Alias);
			String tbl_alias = tbl.Alias == null ? tbl.Name : tbl.Alias;
			Sql_join_fld[] flds = tbl.Join_flds; int flds_len = flds.length;
			for (int j = 0; j < flds_len; ++j) {
				Sql_join_fld join_fld = flds[j];
				bfr.AddStrA7(j == 0 ? " ON " : " AND ");
				bfr.AddStrU8(join_fld.Src_tbl).AddByteDot().AddStrU8(join_fld.Src_fld);
				bfr.Add(Bry__join_eq);
				bfr.AddStrU8(tbl_alias).AddByteDot().AddStrU8(join_fld.Trg_fld);
			}
		}
	}
	private String Bld_join(int tid) {
		switch (tid) {
			case Sql_tbl_itm.Tid__from	: return "FROM";
			case Sql_tbl_itm.Tid__inner	: return "INNER JOIN";
			case Sql_tbl_itm.Tid__left	: return "LEFT JOIN";
			case Sql_tbl_itm.Tid__right	: return "RIGHT JOIN";
			case Sql_tbl_itm.Tid__outer	: return "OUTER JOIN";
			case Sql_tbl_itm.Tid__cross	: return "CROSS JOIN";
			default						: throw ErrUtl.NewUnhandled(tid);
		}
	}
	private static final byte[] Bry__join_eq = BryUtl.NewA7(" = ");
}
