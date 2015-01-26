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
package gplx.xowa.users.dbs; import gplx.*; import gplx.xowa.*; import gplx.xowa.users.*;
import gplx.dbs.*;
class Xou_db_xtn_mgr {
	private Xou_db_xtn_tbl xtn_tbl = new Xou_db_xtn_tbl();
	private OrderedHash xtn_hash = OrderedHash_.new_();
	public void Db_init(Db_conn conn) {
		xtn_tbl.Db_init(conn);
		xtn_tbl.Select_all(xtn_hash);
	}
	public void Db_when_new(Db_conn conn) {
		xtn_tbl.Db_when_new(conn);
	}
	public void Db_save() {
		int len = xtn_hash.Count();
		for (int i = 0; i < len; i++) {
			Xou_db_xtn_itm xtn_itm = (Xou_db_xtn_itm)xtn_hash.FetchAt(i);
			xtn_tbl.Db_save(xtn_itm);
		}			
	}
	public void Db_term() {
		xtn_tbl.Db_term();
	}
	public boolean Xtn_exists(String xtn_key) {return xtn_hash.Has(xtn_key);}
	public void Xtn_add(String xtn_key, String xtn_version) {
		Xou_db_xtn_itm xtn_itm = new Xou_db_xtn_itm();
		xtn_itm.Init_by_make(xtn_key, xtn_version);
		xtn_hash.Add(xtn_key, xtn_itm);
	}
}
