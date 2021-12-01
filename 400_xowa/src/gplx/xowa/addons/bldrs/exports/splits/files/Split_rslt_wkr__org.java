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
package gplx.xowa.addons.bldrs.exports.splits.files;
import gplx.dbs.*;
import gplx.xowa.addons.bldrs.exports.splits.rslts.*;
class Split_rslt_wkr__org extends Split_rslt_wkr__objs__base {
	@Override public byte Tid() {return Split_rslt_tid_.Tid__fsdb_org;}
	@Override public String Tbl_name() {return "rslt_fsdb_org";}
	@Override public DbmetaFldItm[] Pkey_flds() {
		return new DbmetaFldItm[] {DbmetaFldItm.NewStr("orig_ttl", 255), DbmetaFldItm.NewByte("orig_repo")};
	}
}
