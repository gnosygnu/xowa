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
package gplx.xowa.addons.users.wikis.regys.specials.itms; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.users.*; import gplx.xowa.addons.users.wikis.*; import gplx.xowa.addons.users.wikis.regys.*; import gplx.xowa.addons.users.wikis.regys.specials.*;
import gplx.langs.jsons.*;
public class Xouw_itm_bridge implements gplx.xowa.htmls.bridges.Bridge_cmd_itm {
	public void Init_by_app(Xoa_app app) {}
	public String Exec(Json_nde data) {
		byte proc_id = proc_hash.Get_as_byte_or(data.Get_as_bry_or(Msg__proc, null), Byte_ascii.Max_7_bit);
		//Json_nde args = data.Get_kv(Msg__args).Val_as_nde();
		switch (proc_id) {
			case Proc__save:					break;
			default: throw Err_.new_unhandled_default(proc_id);
		}
		return "";
	}
	private static final    byte[] Msg__proc = Bry_.new_a7("proc"); //, Msg__args = Bry_.new_a7("args");
	private static final byte Proc__save = 0;
	private static final    Hash_adp_bry proc_hash = Hash_adp_bry.cs()
	.Add_str_byte("save"						, Proc__save)
	;

	public byte[] Key() {return BRIDGE_KEY;} public static final    byte[] BRIDGE_KEY = Bry_.new_a7("user.wiki.itm.exec");
        public static final    Xouw_itm_bridge Prototype = new Xouw_itm_bridge(); Xouw_itm_bridge() {}
}
