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
package gplx.xowa.addons.apps.cfgs.specials.maints.services; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.apps.*; import gplx.xowa.addons.apps.cfgs.*; import gplx.xowa.addons.apps.cfgs.specials.*; import gplx.xowa.addons.apps.cfgs.specials.maints.*;
import gplx.xowa.addons.apps.cfgs.dbs.*;
class Xocfg_maint_service {
	private Xoa_app app;
	public Xocfg_maint_service(Xoa_app app) {
		this.app = app;
	}
	public void Upsert(String data) {
		Xocfg_itm_bldr itm_bldr = new Xocfg_itm_bldr(new Xocfg_db_mgr(app.User().User_db_mgr().Conn()));
		String[] lines = String_.Split(data, "\n");
		for (String line : lines) {
			String[] ary = String_.Split(line, "|");
			if (ary.length < 4) continue; // ignore blank lines
			String type = String_.Trim(ary[0]);	// get 1st arg and ignore leading space
			if (String_.Eq(type, "grp")) {
				itm_bldr.Create_grp(ary[1], ary[2], ary[3], ary[4]);
			}
			else {
				itm_bldr.Create_itm(ary[1], ary[2], ary[3], Type_adp_.To_tid(ary[4]), ary[5], ary[6], ary[7], ary[8], ary[9]);
			}
		}
	}
}
