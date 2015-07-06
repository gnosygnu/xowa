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
package gplx.xowa.users.data; import gplx.*; import gplx.xowa.*; import gplx.xowa.users.*;
import gplx.dbs.*;
public class Xoud_bmk_mgr {		
	private final Xoud_bmk_hwtr hwtr = new Xoud_bmk_hwtr();
	public Xoud_bmk_tbl Tbl() {return tbl;} private Xoud_bmk_tbl tbl;
	public void Init_by_app(Xoa_app app) {
		hwtr.Init_by_app(app);
	}
	public void Conn_(Db_conn conn, boolean created) {
		this.tbl = new Xoud_bmk_tbl(conn);
		if (created) tbl.Create_tbl();
	}
	public void Add(Xoa_url url) {
		tbl.Insert(url.Page_bry(), url.Raw());
	}
	public Xoud_bmk_row[] Get_all() {return tbl.Select_all();}
}
