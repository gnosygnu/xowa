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
import gplx.core.criterias.*; import gplx.core.lists.*; /*GfoNde*/ import gplx.dbs.qrys.*; import gplx.core.gfo_ndes.*;
class TdbDeleteWkr implements Db_qryWkr {
	public Object Exec(Db_engine engineObj, Db_qry cmdObj) {
		TdbEngine engine = TdbEngine.cast(engineObj); Db_qry_delete cmd = (Db_qry_delete)cmdObj;
		TdbTable tbl = engine.FetchTbl(cmd.Base_table());
		List_adp deleted = List_adp_.New();
		int rv = 0;
		if (cmd.Where() == Db_qry_delete.Where__null) {
			rv = tbl.Rows().Count();
			tbl.Rows().Clear();
		}
		else {
			Criteria crt = cmd.Where();
			for (int i = 0; i < tbl.Rows().Count(); i++) {
				GfoNde row = tbl.Rows().FetchAt_asGfoNde(i);
				if (crt.Matches(row))
					deleted.Add(row);
			}
			for (int i = 0; i < deleted.Count(); i++) {
				GfoNde row = (GfoNde)deleted.Get_at(i);
				tbl.Rows().Del(row);
				rv++;
			}
		}
		if (rv > 0) tbl.IsDirty_set(true);
		return rv;
	}
	public static TdbDeleteWkr new_() {TdbDeleteWkr rv = new TdbDeleteWkr(); return rv;}
}
