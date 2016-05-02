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
package gplx.xowa.addons.builds.centrals; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.builds.*;
import gplx.langs.jsons.*;
public class Xoa_dashboard_bridge implements gplx.xowa.htmls.bridges.Bridge_cmd_itm {
	public void Init_by_app(Xoa_app app) {}
	public String Exec(Json_nde data) {
		Xobc_ui_mgr dl_mgr = Xoa_dashboard_special.Download_mgr;
		byte proc_id = proc_hash.Get_as_byte_or(data.Get_as_bry_or(Msg__proc, null), Byte_ascii.Max_7_bit);
		Json_nde args = data.Get_kv(Msg__args).Val_as_nde();
		switch (proc_id) {
			case Proc__init:					dl_mgr.Init(); break;
			case Proc__todo__move_to_work:		dl_mgr.Todo__move_to_work(args.Get_as_str("job_uid")); break;
			case Proc__work__resume:			dl_mgr.Work__resume(args.Get_as_str("job_uid")); break;
			case Proc__work__pause:				dl_mgr.Work__pause(args.Get_as_str("job_uid")); break;
			case Proc__work__cancel:			dl_mgr.Work__cancel(args.Get_as_str("job_uid")); break;
			default: throw Err_.new_unhandled_default(proc_id);
		}
		return "";
	}
	private static final    byte[] Msg__proc = Bry_.new_a7("proc"), Msg__args = Bry_.new_a7("args");
	private static final byte Proc__init = 0, Proc__todo__move_to_work = 1, Proc__work__resume = 2, Proc__work__pause = 3, Proc__work__cancel = 4;
	private static final    Hash_adp_bry proc_hash = Hash_adp_bry.cs()
	.Add_str_byte("init"				, Proc__init)
	.Add_str_byte("todo__move_to_work"	, Proc__todo__move_to_work)
	.Add_str_byte("work__resume__send"	, Proc__work__resume)
	.Add_str_byte("work__pause__send"	, Proc__work__pause)
	.Add_str_byte("work__cancel__send"	, Proc__work__cancel)
	;

	public byte[] Key() {return BRIDGE_KEY;}
	public static final    byte[] BRIDGE_KEY = Bry_.new_a7("builder_central.exec");
        public static final    Xoa_dashboard_bridge Prototype = new Xoa_dashboard_bridge(); Xoa_dashboard_bridge() {}
}
