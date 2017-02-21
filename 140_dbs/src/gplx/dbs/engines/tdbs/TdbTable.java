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
import gplx.*; /*GfoNdeList*/ import gplx.core.stores.*; import gplx.langs.dsvs.*; /*DsvStoreLayout*/ import gplx.core.gfo_ndes.*;
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
