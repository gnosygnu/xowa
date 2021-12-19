/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2021 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.langs.dsvs;
import gplx.types.errs.ErrUtl;
public class DsvStoreLayout {
	public DsvHeaderList HeaderList() {return headerList;} DsvHeaderList headerList = DsvHeaderList.new_();
	public boolean WriteCmdSequence() {return writeCmdSequence;} public DsvStoreLayout WriteCmdSequence_(boolean val) {writeCmdSequence = val; return this;} private boolean writeCmdSequence;

	static DsvStoreLayout new_() {return new DsvStoreLayout();}
	public static DsvStoreLayout csv_dat_() {return new_();}
	public static DsvStoreLayout csv_hdr_() {
		DsvStoreLayout rv = new_();
		rv.HeaderList().Add_LeafNames();
		return rv;
	}
	public static DsvStoreLayout dsv_brief_() {
		DsvStoreLayout rv = new_();
		rv.writeCmdSequence = true;
		return rv;
	}
	public static DsvStoreLayout dsv_full_() {
		DsvStoreLayout rv = DsvStoreLayout.new_();
		rv.writeCmdSequence = true;
		rv.HeaderList().Add_BlankLine();
		rv.HeaderList().Add_BlankLine();
		rv.HeaderList().Add_Comment("================================");
		rv.HeaderList().Add_TableName();
		rv.HeaderList().Add_Comment("================================");
		rv.HeaderList().Add_LeafTypes();
		rv.HeaderList().Add_LeafNames();
		rv.HeaderList().Add_Comment("================================");
		return rv;
	}
	public static DsvStoreLayout as_(Object obj) {return obj instanceof DsvStoreLayout ? (DsvStoreLayout)obj : null;}
	public static DsvStoreLayout cast(Object obj) {try {return (DsvStoreLayout)obj;} catch(Exception exc) {throw ErrUtl.NewCast(exc, DsvStoreLayout.class, obj);}}
	public static final String Key_const = "StoreLayoutWtr";
}
