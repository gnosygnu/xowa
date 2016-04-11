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
import gplx.dbs.*; import gplx.xowa.users.data.*;
public class Xoud_site_mgr {
	private Xoud_site_tbl tbl;
	private final    Xoud_id_mgr id_mgr;
	public Xoud_site_mgr(Xoud_id_mgr id_mgr) {this.id_mgr = id_mgr;}
	public void Conn_(Db_conn conn, boolean created) {
		tbl = new Xoud_site_tbl(conn);
		if (created) tbl.Create_tbl();
	}
	public Xoud_site_row[] Get_all() {return tbl.Select_all();}
	public void Import(String domain, String name, String path, String xtn) {	// insert or update wiki
		Xoud_site_row[] ary = tbl.Select_by_domain(domain);
		int len = ary.length, update_id = -1, priority = 0;
		for (int i = 0; i < len; ++i) {
			Xoud_site_row itm = ary[i];
			if (priority <= itm.Priority()) priority = itm.Priority() + 1;
			if (String_.Eq(path, itm.Path())) {	// same domain and same path; change insert to update;
				update_id = itm.Id();
				break;
			}
		}
		if (update_id == -1)
			tbl.Insert(id_mgr.Get_next_and_save("xowa.user.site"), priority, domain, name, path, xtn);
		else
			tbl.Update(update_id, priority, domain, name, path, xtn);			
	}
}
