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
import gplx.core.lists.*; /*Ordered_hash_base*/ import gplx.langs.dsvs.*; /*DsvStoreLayout*/ import gplx.core.gfo_ndes.*; import gplx.core.type_xtns.*; import gplx.core.stores.*;
public class TdbTableList extends Ordered_hash_base {
	public TdbTable Get_by(String name) {return TdbTable.as_(Fetch_base(name));}
	public TdbTable Get_by_or_fail(String name) {
		TdbTable rv = TdbTable.as_(Get_by(name)); if (rv == null) throw Err_.new_wo_type("could not find table; database file may not exist", "table", name);
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
			TdbFile file = files.Get_by_or_fail(file_id);
			TdbTable table = TdbTable.load_(id, name, file);
			this.Add(table);
		}
	}
	DsvStoreLayout layout;
	public static final String StoreTableName = "_tables";
	static final String Fld_id = "id"; static final String Fld_name = "name"; static final String Fld_file_id = "file_id";
	static final GfoFldList FldList = GfoFldList_.new_().Add(Fld_id, IntClassXtn.Instance).Add(Fld_name, StringClassXtn.Instance).Add(Fld_file_id, IntClassXtn.Instance);
}
