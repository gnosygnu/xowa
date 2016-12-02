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
package gplx.xowa.addons.apps.cfgs.specials.items; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.apps.*; import gplx.xowa.addons.apps.cfgs.*; import gplx.xowa.addons.apps.cfgs.specials.*;
import gplx.langs.jsons.*;
import gplx.xowa.htmls.bridges.*;
public class Xocfg_item_bridge implements Bridge_cmd_itm {
	private Xoa_app app;
	public void Init_by_app(Xoa_app app) {
		this.app = app;
	}
	public String Exec(Json_nde data) {
		byte proc_id = proc_hash.Get_as_byte_or(data.Get_as_bry_or(Bridge_cmd_mgr.Msg__proc, null), Byte_ascii.Max_7_bit);
		Json_nde args = data.Get_kv(Bridge_cmd_mgr.Msg__args).Val_as_nde();
		switch (proc_id) {
			case Proc__upsert:					Save(args); break;
			default: throw Err_.new_unhandled_default(proc_id);
		}
		return "";
	}
	private void Save(Json_nde args) {
		String data = args.Get_as_str("data");
		gplx.xowa.addons.apps.cfgs.dbs.Xocfg_itm_bldr itm_bldr = new gplx.xowa.addons.apps.cfgs.dbs.Xocfg_itm_bldr(new gplx.xowa.addons.apps.cfgs.dbs.Xocfg_db_mgr(app.User().User_db_mgr().Conn()));
		String[] lines = String_.Split(data, "\n");
		for (String line : lines) {
			String[] ary = String_.Split(line, "|");
			if (ary.length < 4) continue;
			if (String_.Eq(ary[0], "grp")) {
				itm_bldr.Create_grp(ary[1], ary[2], ary[3], ary[4]);
			}
			else {
				itm_bldr.Create_itm(ary[1], ary[2], ary[3], ary[4], ary[5], ary[6], ary[7], ary[8]);
			}
		}
	}

	private static final byte Proc__upsert = 0;
	private static final    Hash_adp_bry proc_hash = Hash_adp_bry.cs()
	.Add_str_byte("upsert"						, Proc__upsert)
	;

	public byte[] Key() {return BRIDGE_KEY;} public static final    byte[] BRIDGE_KEY = Bry_.new_a7("cfg.maint");
        public static final    Xocfg_item_bridge Prototype = new Xocfg_item_bridge(); Xocfg_item_bridge() {}
}
