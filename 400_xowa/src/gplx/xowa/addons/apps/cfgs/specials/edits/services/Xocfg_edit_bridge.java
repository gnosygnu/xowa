/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2017 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.xowa.addons.apps.cfgs.specials.edits.services; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.apps.*; import gplx.xowa.addons.apps.cfgs.*; import gplx.xowa.addons.apps.cfgs.specials.*; import gplx.xowa.addons.apps.cfgs.specials.edits.*;
import gplx.langs.jsons.*; import gplx.xowa.htmls.bridges.*;
public class Xocfg_edit_bridge implements Bridge_cmd_itm {
	private Xocfg_edit_svc svc;
	public void Init_by_app(Xoa_app app) {
		this.svc = new Xocfg_edit_svc(app);
	}
	public String Exec(Json_nde data) {
		byte proc_id = proc_hash.Get_as_byte_or(data.Get_as_bry_or(Bridge_cmd_mgr.Msg__proc, null), Byte_ascii.Max_7_bit);
		Json_nde args = data.Get_kv(Bridge_cmd_mgr.Msg__args).Val_as_nde();
		switch (proc_id) {
			case Proc__update:					svc.Update(args); break;
			case Proc__delete:					svc.Delete(args); break;
			case Proc__select:					svc.Select(args); break;
			default: throw Err_.new_unhandled_default(proc_id);
		}
		return Bridge_cmd_mgr.Msg__ok;
	}

	private static final byte Proc__update = 0, Proc__delete = 1, Proc__select = 2;
	private static final    Hash_adp_bry proc_hash = Hash_adp_bry.cs()
	.Add_str_byte("update"						, Proc__update)
	.Add_str_byte("delete"						, Proc__delete)
	.Add_str_byte("select"						, Proc__select)
	;

	public byte[] Key() {return BRIDGE_KEY;} public static final    byte[] BRIDGE_KEY = Bry_.new_a7("xo.cfg_edit");
        public static final    Xocfg_edit_bridge Prototype = new Xocfg_edit_bridge(); Xocfg_edit_bridge() {}
}
