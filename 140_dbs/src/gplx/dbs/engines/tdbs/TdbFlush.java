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
import gplx.core.lists.*; /*GfoNde*/ import gplx.dbs.qrys.*;
class TdbFlushWkr implements Db_qryWkr {
	public Object Exec(Db_engine engineObj, Db_qry cmdObj) {
		TdbEngine engine = TdbEngine.cast(engineObj); Db_qry_flush cmd = Db_qry_flush.cast(cmdObj);
		if (Array_.Len(cmd.TableNames()) == 0)
			engine.FlushAll();
		else {
			for (String tblName : cmd.TableNames()) {
				TdbTable tbl = engine.FetchTbl(tblName);
				if (tbl.IsDirty()) engine.FlushTbl(tbl);
			}
		}
		return 1;
	}
	public static TdbFlushWkr new_() {TdbFlushWkr rv = new TdbFlushWkr(); return rv;}
}
