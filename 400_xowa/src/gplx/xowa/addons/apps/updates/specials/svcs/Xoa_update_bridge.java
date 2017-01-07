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
package gplx.xowa.addons.apps.updates.specials.svcs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.apps.*; import gplx.xowa.addons.apps.updates.*; import gplx.xowa.addons.apps.updates.specials.*;
import gplx.langs.jsons.*; import gplx.xowa.htmls.bridges.*;
public class Xoa_update_bridge implements Bridge_cmd_itm {
	private Xoa_app app;
	public void Init_by_app(Xoa_app app) {
		this.app = app;
	}
	public String Exec(Json_nde data) {
		byte proc_id = proc_hash.Get_as_byte_or(data.Get_as_bry_or(Bridge_cmd_mgr.Msg__proc, null), Byte_ascii.Max_7_bit);
		Json_nde args = data.Get_kv(Bridge_cmd_mgr.Msg__args).Val_as_nde();

		Xoa_update_svc svc = new Xoa_update_svc(app);
		switch (proc_id) {
			case Proc__install:		svc.Install(args.Get_as_str("version"));break;
			case Proc__skip:		svc.Skip(args.Get_as_str("version")); break;
			default:				throw Err_.new_unhandled_default(proc_id);
		}
		return "";
	}

	private static final byte Proc__install = 0, Proc__skip = 1;
	private static final    Hash_adp_bry proc_hash = Hash_adp_bry.cs()
	.Add_str_byte("install"						, Proc__install)
	.Add_str_byte("skip"						, Proc__skip)
	;

	public byte[] Key() {return BRIDGE_KEY;} public static final    byte[] BRIDGE_KEY = Bry_.new_a7("app.updater");
        public static final    Xoa_update_bridge Prototype = new Xoa_update_bridge(); Xoa_update_bridge() {}
}
