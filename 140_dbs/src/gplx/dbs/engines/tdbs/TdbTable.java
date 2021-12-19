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
import gplx.types.basics.utls.ClassUtl;
import gplx.core.gfo_ndes.GfoFldList;
import gplx.core.gfo_ndes.GfoFldList_;
import gplx.core.gfo_ndes.GfoNde;
import gplx.core.gfo_ndes.GfoNdeList;
import gplx.core.gfo_ndes.GfoNdeList_;
import gplx.core.stores.DataRdr;
import gplx.core.stores.DataWtr;
import gplx.core.stores.GfoNdeRdr;
import gplx.core.stores.GfoNdeRdr_;
import gplx.langs.dsvs.DsvStoreLayout;
import gplx.types.errs.ErrUtl;
public class TdbTable {
	public int Id() {return id;} int id;
	public String Name() {return name;} private String name;
	public GfoFldList Flds() {return flds;} GfoFldList flds = GfoFldList_.new_();
	public GfoNdeList Rows() {return rows;} GfoNdeList rows = GfoNdeList_.new_();
	public TdbFile File() {return file;} TdbFile file;
	public boolean IsLoaded() {return isLoaded;} private boolean isLoaded = true;
	public boolean IsDirty() {return isDirty;} public void IsDirty_set(boolean v) {isDirty = v;} private boolean isDirty = false;

	public static TdbTable new_(int id, String name, TdbFile file) {TdbTable rv = new TdbTable(); rv.ctor(id, name, file); rv.isDirty = true; return rv;} TdbTable() {}
	public static TdbTable load_(int id, String name, TdbFile file) {TdbTable rv = new TdbTable(); rv.ctor(id, name, file); rv.isLoaded = false; return rv;}
	void ctor(int id, String name, TdbFile file) {
		this.id = id; this.name = name; this.file = file;
		layout = DsvStoreLayout.dsv_full_();
	}

	public void DataObj_Wtr(DataWtr wtr) {
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
	public void DataObj_Rdr(DataRdr rdr) {
		layout = TdbStores.FetchLayout(rdr);
		GfoNdeRdr ndeRdr = GfoNdeRdr_.as_(rdr );
		if (ndeRdr != null) {
			if (ndeRdr.UnderNde() == null) throw ErrUtl.NewArgs("ndeRdr.UnderNde is null", "name", rdr.NameOfNode());
			rows = ndeRdr.UnderNde().Subs();
			flds = ndeRdr.UnderNde().SubFlds();
		}
		else {	// XmlRdr needs to load each row again...
			throw ErrUtl.NewInvalidOp("TableLoad not implemented").ArgsAdd("rdrType", ClassUtl.NameByObj(rdr)).ArgsAdd("rdrName", rdr.NameOfNode());
		}
		isLoaded = true;
	}
	DsvStoreLayout layout;
	public static TdbTable as_(Object obj) {return obj instanceof TdbTable ? (TdbTable)obj : null;}
	public static TdbTable cast(Object obj) {try {return (TdbTable)obj;} catch(Exception exc) {throw ErrUtl.NewCast(exc, TdbTable.class, obj);}}
}
