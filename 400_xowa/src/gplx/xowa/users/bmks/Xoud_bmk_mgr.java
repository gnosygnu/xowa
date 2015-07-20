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
package gplx.xowa.users.bmks; import gplx.*; import gplx.xowa.*; import gplx.xowa.users.*;
import gplx.dbs.*;
public class Xoud_bmk_mgr {		
	public Xoud_bmk_itm_tbl Tbl__itm() {return tbl__itm;} private Xoud_bmk_itm_tbl tbl__itm;
	public Xoud_bmk_dir_tbl Tbl__dir() {return tbl__dir;} private Xoud_bmk_dir_tbl tbl__dir;
	public void Conn_(Db_conn conn, boolean created) {
		this.tbl__dir = new Xoud_bmk_dir_tbl(conn);
		this.tbl__itm = new Xoud_bmk_itm_tbl(conn);
		// if (!conn.Meta_tbl_exists(tbl__dir.Tbl_name())) tbl__dir.Create_tbl();	// bmk_v2
		if (!conn.Meta_tbl_exists(tbl__itm.Tbl_name())) tbl__itm.Create_tbl();
	}
	public void Itms__add(int owner, Xoa_url url) {
		tbl__itm.Insert(owner, tbl__itm.Select_sort_next(owner), Xoa_ttl.Replace_unders(url.Page_bry()), url.Wiki_bry(), url.Raw(), Bry_.Empty);
	}
	public static final int Owner_root = -1;
}
