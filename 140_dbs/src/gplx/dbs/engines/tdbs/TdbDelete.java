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
import gplx.core.criterias.*; import gplx.lists.*; /*GfoNde*/ import gplx.dbs.qrys.*; import gplx.core.gfo_ndes.*;
class TdbDeleteWkr implements Db_qryWkr {
	public Object Exec(Db_engine engineObj, Db_qry cmdObj) {
		TdbEngine engine = TdbEngine.cast(engineObj); Db_qry_delete cmd = (Db_qry_delete)cmdObj;
		TdbTable tbl = engine.FetchTbl(cmd.Base_table());
		List_adp deleted = List_adp_.new_();
		int rv = 0;
		if (cmd.Where() == Db_qry_.WhereAll) {
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
