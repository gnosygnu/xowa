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
class TdbDbLoadMgr {
	public TdbDatabase LoadTbls(Io_url dbInfo) {
		TdbDatabase db = TdbDatabase.new_(dbInfo);
		if (!Io_mgr.Instance.ExistsFil(dbInfo)) {
			db.IsNew_set(true);
			return db;
		}
		DataRdr rdr = MakeDataRdr(dbInfo);
		LoadTblsByRdr(db, rdr);
		return db;
	}
	public void LoadTbl(TdbDatabase db, TdbTable tbl) {
		DataRdr rootRdr = MakeDataRdr(tbl.File().Path());
		LoadTblsByRdr(db, rootRdr);
	}
	void LoadTblsByRdr(TdbDatabase db, DataRdr rootRdr) {
		DataRdr rdr = rootRdr.Subs();
		while (rdr.MoveNextPeer()) {
			String name = rdr.NameOfNode();
			if		(String_.Eq(name, TdbFileList.StoreTblName))		db.Files().DataObj_Rdr(rdr);
			else if (String_.Eq(name, TdbTableList.StoreTableName))		db.Tables().DataObj_Rdr(rdr, db.Files());
			else											db.Tables().Get_by_or_fail(rdr.NameOfNode()).DataObj_Rdr(rdr);
		}
		if (db.Files().Count() == 0) throw Err_.new_wo_type("fatal error: db has no files", "connectInfo", db.DbUrl());
	}
	DataRdr MakeDataRdr(Io_url fil) {
		String text = Io_mgr.Instance.LoadFilStr(fil);
		return TdbStores.rdr_(text);
	}
	public static TdbDbLoadMgr new_() {return new TdbDbLoadMgr();} TdbDbLoadMgr() {}
}
