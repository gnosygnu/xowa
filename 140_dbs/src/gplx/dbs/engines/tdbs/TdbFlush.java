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
