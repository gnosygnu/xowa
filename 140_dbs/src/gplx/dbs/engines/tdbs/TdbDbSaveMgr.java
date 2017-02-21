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
package gplx.dbs.engines.tdbs; import gplx.*; import gplx.dbs.*; import gplx.dbs.engines.*;
import gplx.core.stores.*;
class TdbDbSaveMgr {
	public void SaveDb(TdbDatabase db) {
		for (Object filObj : db.Files()) {
			TdbFile fil = (TdbFile)filObj;
			SaveFile(db, fil);
		}
	}
	public void SaveFile(TdbDatabase db, TdbFile fil) {
		List_adp tbls = FetchTablesWithSamePath(db, fil.Path());
		boolean isSaveNeeded = db.IsNew();
		for (Object tblObj : tbls) {
			TdbTable tbl = (TdbTable)tblObj;
			if (tbl.IsDirty()) {
				isSaveNeeded = true;
				break;
			}
		}
		if (isSaveNeeded) {
			SaveTblsToFile(db, fil, tbls);
			db.IsNew_set(false);
		}
	}
	void SaveTblsToFile(TdbDatabase db, TdbFile fil, List_adp tbls) {
		DataWtr wtr = TdbStores.wtr_();
		if (fil.Id() == TdbFile.MainFileId) {				// if MainFile, save critical Files and Tables data
			db.Files().DataObj_Wtr(wtr);
			db.Tables().DataObj_Wtr(wtr);
		}
		for (Object tblObj : tbls) {
			TdbTable tbl = (TdbTable)tblObj;
			tbl.DataObj_Wtr(wtr);
		}
		Io_mgr.Instance.SaveFilStr(fil.Path(), wtr.To_str());
	}
	List_adp FetchTablesWithSamePath(TdbDatabase db, Io_url filPath) {
		List_adp list = List_adp_.New();
		for (Object tblObj : db.Tables()) {
			TdbTable tbl = (TdbTable)tblObj;
			if (tbl.File().Path().Eq (filPath))
				list.Add(tbl);
		}
		return list;
	}
	public static TdbDbSaveMgr new_() {return new TdbDbSaveMgr();} TdbDbSaveMgr() {}
}
