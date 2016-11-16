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
import gplx.xowa.htmls.bridges.*;
public class Xocfg_list_bridge implements Bridge_cmd_itm {
	private Xoa_app app;
	private Xocfg_list_bridge_mgr mgr;
	public void Init_by_app(Xoa_app app) {
		this.app = app;
	}
	public String Exec(Json_nde data) {
		if (mgr == null) this.mgr = new Xocfg_list_bridge_mgr(app);
		byte proc_id = proc_hash.Get_as_byte_or(data.Get_as_bry_or(Bridge_cmd_mgr.Msg__proc, null), Byte_ascii.Max_7_bit);
		Json_nde args = data.Get_kv(Bridge_cmd_mgr.Msg__args).Val_as_nde();
		switch (proc_id) {
			case Proc__save:					mgr.Save(args); break;
			case Proc__revert:					mgr.Revert(args); break;
			default: throw Err_.new_unhandled_default(proc_id);
		}
		return Bridge_cmd_mgr.Msg__ok;
	}

	private static final byte Proc__save = 0, Proc__revert = 1;
	private static final    Hash_adp_bry proc_hash = Hash_adp_bry.cs()
	.Add_str_byte("save"						, Proc__save)
	.Add_str_byte("revert"						, Proc__revert)
	;

	public byte[] Key() {return BRIDGE_KEY;} public static final    byte[] BRIDGE_KEY = Bry_.new_a7("cfg.item.exec");
        public static final    Xocfg_list_bridge Prototype = new Xocfg_list_bridge(); Xocfg_list_bridge() {}
}
