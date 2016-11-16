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
package gplx.xowa.addons.apps.cfgs.specials.lists; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.apps.*; import gplx.xowa.addons.apps.cfgs.*; import gplx.xowa.addons.apps.cfgs.specials.*;
import gplx.langs.jsons.*;
import gplx.xowa.addons.apps.cfgs.dbs.*;
public class Xocfg_list_bridge_mgr {
	private final    Xocfg_db_mgr db_mgr;
	public Xocfg_list_bridge_mgr(Xoa_app app) {
		this.db_mgr = new Xocfg_db_mgr(app.User().User_db_mgr().Conn());
	}
	public void Save(Json_nde args) {
		String ctx = args.Get_as_str("ctx");
		String key = args.Get_as_str("key");
		String val = args.Get_as_str("val");
		db_mgr.Set_str(ctx, key, val);
	}
	public void Revert(Json_nde args) {
		String ctx = args.Get_as_str("ctx");
		String key = args.Get_as_str("key");
		db_mgr.Del(ctx, key);
	}
}
