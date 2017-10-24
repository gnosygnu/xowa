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
import gplx.core.gfo_ndes.*; import gplx.core.type_xtns.*; import gplx.core.stores.*;
import gplx.core.lists.*; /*Ordered_hash_base*/ import gplx.langs.dsvs.*; /*DsvStoreLayout*/
public class TdbFileList extends Ordered_hash_base {
	public TdbFile Get_by_or_fail(int id) {return TdbFile.as_(Get_by_or_fail_base(id));}
	public void Add(TdbFile src) {Add_base(src.Id(), src);}

	Io_url dbInfo;
	public static TdbFileList new_(Io_url dbInfo, TdbFile mainFile) {
		TdbFileList rv = new TdbFileList();
		rv.dbInfo = dbInfo;
		rv.Add(mainFile);
		rv.layout = DsvStoreLayout.dsv_full_();
		return rv;
	}	TdbFileList() {}

	@gplx.Internal protected void DataObj_Wtr(DataWtr wtr) {
		wtr.InitWtr(DsvStoreLayout.Key_const, layout);
		wtr.WriteTableBgn(StoreTblName, FldList);
		for (Object filObj : this) {
			TdbFile fil = (TdbFile)filObj;
			wtr.WriteLeafBgn("fil");
			wtr.WriteData(Fld_id, fil.Id());
			wtr.WriteData(Fld_path, fil.Path());
			wtr.WriteData("format", "dsv");
			wtr.WriteLeafEnd();
		}
		wtr.WriteNodeEnd();
	}
	@gplx.Internal protected void DataObj_Rdr(DataRdr rdr) {
		layout = TdbStores.FetchLayout(rdr);
		this.Clear();
		DataRdr subRdr = rdr.Subs();
		while (subRdr.MoveNextPeer()) {
			int id = subRdr.ReadInt(Fld_id);
			String url = subRdr.ReadStrOr(Fld_path, dbInfo.Raw());
			TdbFile file = (id == TdbFile.MainFileId)
				? TdbFile.new_(TdbFile.MainFileId, dbInfo)	// not redundant; prevents error of MainFile in different url/format than connectInfo
				: TdbFile.new_(id, Io_url_.new_any_(url));
			this.Add(file);
		}
	}
	DsvStoreLayout layout;
	public static final String StoreTblName = "_files";
	static final String Fld_id = "id"; static final String Fld_path = "url";
	static final GfoFldList FldList = GfoFldList_.new_().Add(Fld_id, IntClassXtn.Instance).Add(Fld_path, StringClassXtn.Instance).Add("format", StringClassXtn.Instance);
}
