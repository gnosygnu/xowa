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
class TdbDbSaveMgr {
	public void SaveDb(TdbDatabase db) {
		for (Object filObj : db.Files()) {
			TdbFile fil = (TdbFile)filObj;
			SaveFile(db, fil);
		}
	}
	public void SaveFile(TdbDatabase db, TdbFile fil) {
		ListAdp tbls = FetchTablesWithSamePath(db, fil.Path());
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
	void SaveTblsToFile(TdbDatabase db, TdbFile fil, ListAdp tbls) {
		DataWtr wtr = TdbStores.wtr_();
		if (fil.Id() == TdbFile.MainFileId) {				// if MainFile, save critical Files and Tables data
			db.Files().DataObj_Wtr(wtr);
			db.Tables().DataObj_Wtr(wtr);
		}
		for (Object tblObj : tbls) {
			TdbTable tbl = (TdbTable)tblObj;
			tbl.DataObj_Wtr(wtr);
		}
		Io_mgr._.SaveFilStr(fil.Path(), wtr.XtoStr());
	}
	ListAdp FetchTablesWithSamePath(TdbDatabase db, Io_url filPath) {
		ListAdp list = ListAdp_.new_();
		for (Object tblObj : db.Tables()) {
			TdbTable tbl = (TdbTable)tblObj;
			if (tbl.File().Path().Eq (filPath))
				list.Add(tbl);
		}
		return list;
	}
	public static TdbDbSaveMgr new_() {return new TdbDbSaveMgr();} TdbDbSaveMgr() {}
}
