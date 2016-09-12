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
package gplx.xowa.addons.wikis.ctgs.dbs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.ctgs.*;
import gplx.dbs.*; import gplx.xowa.wikis.data.*; import gplx.xowa.wikis.data.tbls.*; import gplx.xowa.wikis.dbs.*;
public class Xodb_cat_db_ {
	public static Xowd_cat_core_tbl Get_cat_core_or_fail(Xow_db_mgr db_mgr) {
		Xow_db_file cat_core_db = db_mgr.Dbs__get_by_tid_or_core(Xow_db_file_.Tid__cat_core);
		Xowd_cat_core_tbl cat_core_tbl = new Xowd_cat_core_tbl(cat_core_db.Conn(), db_mgr.Props().Schema_is_1());
		return cat_core_tbl;
	}
}
