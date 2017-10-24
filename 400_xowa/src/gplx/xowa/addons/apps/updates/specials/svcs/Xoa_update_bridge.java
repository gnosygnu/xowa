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
