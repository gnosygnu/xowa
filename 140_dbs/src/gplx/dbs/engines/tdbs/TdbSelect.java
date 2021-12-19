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
package gplx.dbs.engines.tdbs;
import gplx.types.basics.lists.List_adp;
import gplx.types.basics.lists.Ordered_hash;
import gplx.types.basics.lists.Ordered_hash_;
import gplx.core.criterias.Criteria;
import gplx.core.gfo_ndes.GfoFld;
import gplx.core.gfo_ndes.GfoFldList;
import gplx.core.gfo_ndes.GfoFldList_;
import gplx.core.gfo_ndes.GfoNde;
import gplx.core.gfo_ndes.GfoNdeList;
import gplx.core.gfo_ndes.GfoNdeList_;
import gplx.core.gfo_ndes.GfoNde_;
import gplx.core.stores.GfoNdeRdr_;
import gplx.dbs.Db_qry;
import gplx.dbs.engines.Db_engine;
import gplx.dbs.qrys.Db_qry__select_cmd;
import gplx.dbs.sqls.itms.Sql_order_fld_sorter;
import gplx.dbs.sqls.itms.Sql_select_fld;
import gplx.dbs.sqls.itms.Sql_select_fld_list;
import gplx.dbs.sqls.itms.Sql_where_clause;
import gplx.types.errs.ErrUtl;
import gplx.types.commons.lists.ComparerAble;
class TdbSelectWkr implements Db_qryWkr {
	public Object Exec(Db_engine engineObj, Db_qry cmdObj) {
		TdbEngine engine = TdbEngine.cast(engineObj); Db_qry__select_cmd cmd = (Db_qry__select_cmd)cmdObj;
		if (cmd.From().Tbls.Len() > 1) throw ErrUtl.NewArgs("joins not supported for tdbs", "sql", cmd.ToSqlExec(engineObj.Sql_wtr()));

		TdbTable tbl = engine.FetchTbl(cmd.From().Base_tbl.Name);
		GfoNdeList rv = (cmd.Where_itm() == Sql_where_clause.Where__null && cmd.Limit() == Db_qry__select_cmd.Limit__disabled) ? rv = tbl.Rows() : FilterRecords(tbl, cmd.Where_itm().Root, cmd.Limit());
		if (cmd.GroupBy() != null)
			rv = TdbGroupByWkr.GroupByExec(cmd, rv, tbl);
		if (cmd.Order() != null) {	// don't use null pattern here; if null ORDER BY, then don't call .Sort on GfoNdeList
			ComparerAble comparer = Sql_order_fld_sorter.new_(cmd.Order().Flds());
			rv.Sort_by(comparer);
		}
		return GfoNdeRdr_.peers_(rv, false);
	}
	GfoNdeList FilterRecords(TdbTable tbl, Criteria crt, int limit) {
		GfoNdeList rv = GfoNdeList_.new_();
		int count = 0;
		for (int i = 0; i < tbl.Rows().Count(); i++) {
			GfoNde row = (GfoNde)tbl.Rows().FetchAt_asGfoNde(i);
			if (crt.Matches(row)) rv.Add(row);
			++count;
			if (count == limit) break;
		}
		return rv;
	}
	public static GfoFldList To_GfoFldLst(TdbTable tbl, Sql_select_fld_list flds) {
		GfoFldList rv = GfoFldList_.new_();
		int len = flds.Len();
		for (int i = 0; i < len; ++i) {
			Sql_select_fld selectFld = flds.Get_at(i);
			GfoFld fld = tbl.Flds().FetchOrNull(selectFld.Fld);
			if (fld == null) throw ErrUtl.NewArgs("fld not found in tbl", "fldName", selectFld.Fld, "tblName", tbl.Name(), "tblFlds", tbl.Flds().To_str());
			if (rv.Has(selectFld.Alias)) throw ErrUtl.NewArgs("alias is not unique", "fldName", selectFld.Fld, "flds", rv.To_str());
			selectFld.GroupBy_type(fld.Type());
			rv.Add(selectFld.Alias, selectFld.Val_type());
		}
		return rv;
	}
	public static final TdbSelectWkr Instance = new TdbSelectWkr(); TdbSelectWkr() {}
}
class TdbGroupByWkr {
	public static GfoNdeList GroupByExec(Db_qry__select_cmd select, GfoNdeList selectRows, TdbTable tbl) {
		GfoNdeList rv = GfoNdeList_.new_();
		Ordered_hash groupByHash = Ordered_hash_.New();
		List_adp groupByFlds = select.GroupBy().Flds();
		GfoFldList selectFldsForNewRow = TdbSelectWkr.To_GfoFldLst(tbl, select.Cols().Flds);
		Sql_select_fld_list selectFlds = select.Cols().Flds;
		for (int rowIdx = 0; rowIdx < selectRows.Count(); rowIdx++) {
			GfoNde selectRow = selectRows.FetchAt_asGfoNde(rowIdx);
			GfoNde groupByRow = FindOrNew(selectFldsForNewRow, groupByFlds, selectRow, groupByHash, rv);
			int len = selectFlds.Len();
			for (int i = 0; i < len; ++i) {
				Sql_select_fld selectFld = selectFlds.Get_at(i);
				Object val = groupByRow.Read(selectFld.Alias);	// groupByRow is keyed by Alias; EX: Count(Id) AS CountOf
				groupByRow.WriteAt(i, selectFld.GroupBy_eval(val, selectRow.Read(selectFld.Fld), selectFld.Val_type()));
			}
		}
		return rv;
	}
	static GfoNde FindOrNew(GfoFldList selectFldsForNewRow, List_adp groupByFlds, GfoNde selectRow, Ordered_hash groupByRows, GfoNdeList rslt) {
		int len = groupByFlds.Len();
		Ordered_hash curHash = groupByRows;
		GfoNde rv = null;
		for (int i = 0; i < len; i++) {
			String fld = (String)groupByFlds.GetAt(i);
			boolean last = i == len - 1;
			Object val = selectRow.Read(fld);
			Object o = curHash.GetByOrNull(val);
			if (last) {
				if (o == null) {
					Object[] valAry = new Object[selectFldsForNewRow.Count()];
					rv = GfoNde_.vals_(selectFldsForNewRow, valAry);
					curHash.Add(val, rv);
					rslt.Add(rv);
				}
				else
					rv = GfoNde_.as_(o);
			}
			else {
				if (o == null) {
					Ordered_hash nextHash = Ordered_hash_.New();
					curHash.Add(val, nextHash);
					curHash = nextHash;
				}
				else {
					curHash = (Ordered_hash)o;
				}
			}
		}
		return rv;
	}
}
