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
package gplx.dbs.schemas; import gplx.*; import gplx.dbs.*;
import gplx.dbs.schemas.updates.*;
public class Schema_db_mgr {
	public Schema_loader_mgr Loader() {return loader;} public void Loader_(Schema_loader_mgr v) {loader = v;} private Schema_loader_mgr loader;
	public Schema_update_mgr Updater() {return updater;} private final Schema_update_mgr updater = new Schema_update_mgr();
	public Schema_tbl_mgr Tbl_mgr() {return tbl_mgr;} private final Schema_tbl_mgr tbl_mgr = new Schema_tbl_mgr();
	public void Init(Db_conn conn) {
		loader.Load(this, conn);
		updater.Update(this, conn);
	}
}
