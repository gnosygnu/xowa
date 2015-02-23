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
import gplx.lists.*; /*OrderedHash_base*/ import gplx.stores.dsvs.*; /*DsvStoreLayout*/
public class TdbTableList extends OrderedHash_base {
	public TdbTable Fetch(String name) {return TdbTable.as_(Fetch_base(name));}
	public TdbTable FetchOrFail(String name) {
		TdbTable rv = TdbTable.as_(Fetch(name)); if (rv == null) throw Err_.new_("could not find table; database file may not exist").Add("table", name);
		return rv;
	}
	public void Add(TdbTable dataTable) {Add_base(dataTable.Name(), dataTable);}

	public static TdbTableList new_(Io_url dbInfo) {
		TdbTableList rv = new TdbTableList();
		rv.layout = DsvStoreLayout.dsv_full_();
		return rv;
	}
	@gplx.Internal protected void DataObj_Wtr(DataWtr wtr) {
		wtr.InitWtr(DsvStoreLayout.Key_const, layout);
		wtr.WriteTableBgn(StoreTableName, FldList);
		for (Object tblObj : this) {
			TdbTable tbl = (TdbTable)tblObj;
			wtr.WriteLeafBgn("tbl");
			wtr.WriteData(Fld_id, tbl.Id());
			wtr.WriteData(Fld_name, tbl.Name());
			wtr.WriteData(Fld_file_id, tbl.File().Id());
			wtr.WriteLeafEnd();
		}
		wtr.WriteNodeEnd();
	}
	@gplx.Internal protected void DataObj_Rdr(DataRdr rdr, TdbFileList files) {
		layout = TdbStores.FetchLayout(rdr);
		DataRdr subRdr = rdr.Subs();
		this.Clear();
		while (subRdr.MoveNextPeer()) {
			int id = subRdr.ReadInt(Fld_id);
			String name = subRdr.ReadStr(Fld_name);
			int file_id = subRdr.ReadInt(Fld_file_id);
			TdbFile file = files.FetchOrFail(file_id);
			TdbTable table = TdbTable.load_(id, name, file);
			this.Add(table);
		}
	}
	DsvStoreLayout layout;
	public static final String StoreTableName = "_tables";
	static final String Fld_id = "id"; static final String Fld_name = "name"; static final String Fld_file_id = "file_id";
	static final GfoFldList FldList = GfoFldList_.new_().Add(Fld_id, IntClassXtn._).Add(Fld_name, StringClassXtn._).Add(Fld_file_id, IntClassXtn._);
}
