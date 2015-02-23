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
package gplx.dbs.engines.tdbs; import gplx.*; import gplx.dbs.*; import gplx.dbs.engines.*;
import gplx.core.criterias.*; import gplx.dbs.qrys.*; import gplx.dbs.sqls.*;
import gplx.lists.*; /*ComparerAble*/ import gplx.stores.*; /*GfoNdeRdr*/
class TdbSelectWkr implements Db_qryWkr {
	public Object Exec(Db_engine engineObj, Db_qry cmdObj) {
		TdbEngine engine = TdbEngine.cast_(engineObj); Db_qry_select cmd = (Db_qry_select)cmdObj;
		if (cmd.From().Tbls().Count() > 1) throw Err_.new_key_("gplx.tdbs", "joins not supported for tdbs").Add("sql", cmd.Xto_sql());

		TdbTable tbl = engine.FetchTbl(cmd.From().BaseTable().TblName());
		GfoNdeList rv = (cmd.Where() == Db_qry_.WhereAll && cmd.Limit() == Db_qry_select.Limit_disabled) ? rv = tbl.Rows() : FilterRecords(tbl, cmd.Where(), cmd.Limit());
		if (cmd.GroupBy() != null)
			rv = TdbGroupByWkr.GroupByExec(cmd, rv, tbl);
		if (cmd.OrderBy() != null) {	// don't use null pattern here; if null ORDER BY, then don't call .Sort on GfoNdeList
			ComparerAble comparer = Sql_order_by_sorter.new_((Sql_order_by_itm[])cmd.OrderBy().Flds().Xto_ary(Sql_order_by_itm.class));
			rv.SortBy(comparer);
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
	public static final TdbSelectWkr _ = new TdbSelectWkr(); TdbSelectWkr() {}
}
class TdbGroupByWkr {
	public static GfoNdeList GroupByExec(Db_qry_select select, GfoNdeList selectRows, TdbTable tbl) {
		GfoNdeList rv = GfoNdeList_.new_();
		OrderedHash groupByHash = OrderedHash_.new_();
		ListAdp groupByFlds = select.GroupBy().Flds();
		GfoFldList selectFldsForNewRow = select.Cols().Flds().XtoGfoFldLst(tbl);
		Sql_select_fld_list selectFlds = select.Cols().Flds();
		for (int rowIdx = 0; rowIdx < selectRows.Count(); rowIdx++) {
			GfoNde selectRow = selectRows.FetchAt_asGfoNde(rowIdx);
			GfoNde groupByRow = FindOrNew(selectFldsForNewRow, groupByFlds, selectRow, groupByHash, rv);
			for (int i = 0; i < selectFlds.Count(); i++) {
				Sql_select_fld_base selectFld = selectFlds.FetchAt(i);
				Object val = groupByRow.Read(selectFld.Alias());	// groupByRow is keyed by Alias; EX: Count(Id) AS CountOf
				groupByRow.WriteAt(i, selectFld.GroupBy_eval(val, selectRow.Read(selectFld.Fld()), selectFld.ValType()));
			}
		}
		return rv;
	}
	static GfoNde FindOrNew(GfoFldList selectFldsForNewRow, ListAdp groupByFlds, GfoNde selectRow, OrderedHash groupByRows, GfoNdeList rslt) {
		int len = groupByFlds.Count();
		OrderedHash curHash = groupByRows;
		GfoNde rv = null;
		for (int i = 0; i < len; i++) {
			String fld = (String)groupByFlds.FetchAt(i);
			boolean last = i == len - 1;
			Object val = selectRow.Read(fld);
			Object o = curHash.Fetch(val);
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
					OrderedHash nextHash = OrderedHash_.new_();
					curHash.Add(val, nextHash);
					curHash = nextHash;
				}
				else {
					curHash = (OrderedHash)o;
				}
			}
		}
		return rv;
	}
}
