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
package gplx.dbs; import gplx.*;
import gplx.core.criterias.*;
import gplx.lists.*; /*GfoNde*/
class TdbUpdateWkr implements Db_qryWkr {
	public Object Exec(Db_engine engineObj, Db_qry cmdObj) {
		TdbEngine engine = TdbEngine.cast_(engineObj); Db_qry_update cmd = (Db_qry_update)cmdObj;

		int rv = 0;
		TdbTable tbl = engine.FetchTbl(cmd.Base_table());
		Criteria crt = cmd.Where();
		for (int i = 0; i < tbl.Rows().Count(); i++) {
			GfoNde row = (GfoNde)tbl.Rows().FetchAt_asGfoNde(i);
			if (crt.Matches(row)) {
				UpdateRow(cmd, row);
				rv++;
			}
		}
		if (rv > 0) tbl.IsDirty_set(true);
		return rv;
	}
	void UpdateRow(Db_qry_update cmd, GfoNde row) {
		for (int i = 0; i < cmd.Args().Count(); i++) {
			KeyVal p = (KeyVal)cmd.Args().FetchAt(i);
			Db_arg prm = (Db_arg)p.Val();
			row.Write(p.Key(), prm.Val());
		}
	}
	public static TdbUpdateWkr new_() {TdbUpdateWkr rv = new TdbUpdateWkr(); return rv;}
}
