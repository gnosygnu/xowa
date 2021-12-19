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
import gplx.core.gfo_ndes.GfoFldList;
import gplx.core.gfo_ndes.GfoNde;
import gplx.core.gfo_ndes.GfoNde_;
import gplx.core.stores.DataRdr;
import gplx.dbs.Db_qry;
import gplx.dbs.engines.Db_engine;
import gplx.dbs.qrys.Db_arg;
import gplx.dbs.qrys.Db_qry_insert;
import gplx.dbs.sqls.itms.Sql_select_fld;
import gplx.dbs.sqls.itms.Sql_select_fld_list;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.errs.ErrUtl;
import gplx.types.commons.KeyVal;
import gplx.types.basics.utls.StringUtl;
class TdbInsertWkr implements Db_qryWkr {
	public Object Exec(Db_engine engineObj, Db_qry cmdObj) {
		TdbEngine engine = TdbEngine.cast(engineObj); Db_qry_insert cmd = (Db_qry_insert)cmdObj;

		TdbTable tbl = engine.FetchTbl(cmd.BaseTable());
		tbl.IsDirty_set(true);
		return cmd.Select() == null
			? InsertRowsByVals(engine, tbl, cmd)
			: InsertRowsBySelect(engine, tbl, cmd);
	}
	int InsertRowsBySelect(TdbEngine engine, TdbTable tbl, Db_qry_insert insert) {
		int count = 0;
		DataRdr rdr = (DataRdr)TdbSelectWkr.Instance.Exec(engine, insert.Select());
		Sql_select_fld_list insertFlds = insert.Cols(); int insertFldsCount = insertFlds.Len();
		GfoFldList selectFldsForNewRow = null;
		try {selectFldsForNewRow = TdbSelectWkr.To_GfoFldLst(tbl, insertFlds);}
		catch (Exception e) {throw ErrUtl.NewArgs(e, "failed to generate flds for new row");}
		if (insertFldsCount > selectFldsForNewRow.Count()) throw ErrUtl.NewArgs("insert flds cannot exceed selectFlds", "insertFlds", To_str(insertFlds), "selectFlds", selectFldsForNewRow.To_str());
		while (rdr.MoveNextPeer()) {
			count++;
			GfoNde row = GfoNde_.vals_(selectFldsForNewRow, new Object[insertFldsCount]);
			for (int i = 0; i < insertFldsCount; i++) {
				row.WriteAt(i, rdr.ReadAt(i));	// NOTE: SELECT and INSERT flds are in same order; ex: INSERT INTO (a, b) SELECT (b, a)
			}
			tbl.Rows().Add(row);
		}
		return count;
	}
	int InsertRowsByVals(TdbEngine engine, TdbTable tbl, Db_qry_insert insert) {
		GfoNde row = GfoNde_.vals_(tbl.Flds(), new Object[tbl.Flds().Count()]);
		for (int i = 0; i < insert.Args().Len(); i++) {
			KeyVal kv = insert.Args().GetAt(i);
			Db_arg arg = (Db_arg)kv.Val();
			row.Write(kv.KeyToStr(), arg.Val);
		}
		tbl.Rows().Add(row);
		return 1;
	}
	private String To_str(Sql_select_fld_list flds) {
		BryWtr bfr = BryWtr.New();
		for (int i = 0; i < flds.Len(); i++) {
			Sql_select_fld fld = flds.Get_at(i);
			bfr.AddStrU8(StringUtl.Format("{0},{1}|", fld.Fld, fld.Alias));
		}
		return bfr.ToStr();
	}
	public static TdbInsertWkr new_() {TdbInsertWkr rv = new TdbInsertWkr(); return rv;}
}
