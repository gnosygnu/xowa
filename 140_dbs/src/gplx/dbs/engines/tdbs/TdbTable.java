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
import gplx.*; /*GfoNdeList*/ import gplx.stores.*; import gplx.langs.dsvs.*; /*DsvStoreLayout*/
public class TdbTable {
	public int Id() {return id;} int id;
	public String Name() {return name;} private String name;
	public GfoFldList Flds() {return flds;} GfoFldList flds = GfoFldList_.new_();
	public GfoNdeList Rows() {return rows;} GfoNdeList rows = GfoNdeList_.new_();
	@gplx.Internal protected TdbFile File() {return file;} TdbFile file;
	@gplx.Internal protected boolean IsLoaded() {return isLoaded;} private boolean isLoaded = true;
	@gplx.Internal protected boolean IsDirty() {return isDirty;} public void IsDirty_set(boolean v) {isDirty = v;} private boolean isDirty = false;

	public static TdbTable new_(int id, String name, TdbFile file) {TdbTable rv = new TdbTable(); rv.ctor(id, name, file); rv.isDirty = true; return rv;} TdbTable() {}
	public static TdbTable load_(int id, String name, TdbFile file) {TdbTable rv = new TdbTable(); rv.ctor(id, name, file); rv.isLoaded = false; return rv;}
	void ctor(int id, String name, TdbFile file) {
		this.id = id; this.name = name; this.file = file;
		layout = DsvStoreLayout.dsv_full_();
	}

	@gplx.Internal protected void DataObj_Wtr(DataWtr wtr) {
		wtr.InitWtr(DsvStoreLayout.Key_const, layout);
		wtr.WriteTableBgn(name, flds);
		for (int rowIdx = 0; rowIdx < rows.Count(); rowIdx++) {
			GfoNde drow = rows.FetchAt_asGfoNde(rowIdx);
			wtr.WriteLeafBgn("row");
			for (int i = 0; i < drow.Flds().Count(); i++)
				wtr.WriteData(drow.Flds().Get_at(i).Key(), drow.ReadAt(i));
			wtr.WriteLeafEnd();					
		}
		wtr.WriteNodeEnd();
		isDirty = false;
	}
	@gplx.Internal protected void DataObj_Rdr(DataRdr rdr) {
		layout = TdbStores.FetchLayout(rdr);
		GfoNdeRdr ndeRdr = GfoNdeRdr_.as_(rdr );
		if (ndeRdr != null) {
			if (ndeRdr.UnderNde() == null) throw Err_.new_wo_type("ndeRdr.UnderNde is null", "name", rdr.NameOfNode());
			rows = ndeRdr.UnderNde().Subs();
			flds = ndeRdr.UnderNde().SubFlds();
		}
		else {	// XmlRdr needs to load each row again...
			throw Err_.new_invalid_op("TableLoad not implemented").Args_add("rdrType", Type_adp_.NameOf_obj(rdr), "rdrName", rdr.NameOfNode());
		}
		isLoaded = true;
	}
	DsvStoreLayout layout;
	public static TdbTable as_(Object obj) {return obj instanceof TdbTable ? (TdbTable)obj : null;}
	public static TdbTable cast(Object obj) {try {return (TdbTable)obj;} catch(Exception exc) {throw Err_.new_type_mismatch_w_exc(exc, TdbTable.class, obj);}}
}
