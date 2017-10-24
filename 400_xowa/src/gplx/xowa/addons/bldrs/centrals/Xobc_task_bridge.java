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
package gplx.xowa.addons.bldrs.centrals; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*;
import gplx.langs.jsons.*;
import gplx.xowa.htmls.bridges.*;
import gplx.xowa.addons.bldrs.centrals.cmds.*;
public class Xobc_task_bridge implements Bridge_cmd_itm {
	private Xoa_app app;
	public void Init_by_app(Xoa_app app) {this.app = app;}
	public String Exec(Json_nde data) {
		Xobc_task_mgr task_mgr = Xobc_task_special.Task_mgr(app);
		byte proc_id = proc_hash.Get_as_byte_or(data.Get_as_bry_or(Bridge_cmd_mgr.Msg__proc, null), Byte_ascii.Max_7_bit);
		Json_nde args = data.Get_kv(Bridge_cmd_mgr.Msg__args).Val_as_nde();
		switch (proc_id) {
			case Proc__reload:					task_mgr.Reload(); break;
			case Proc__add_work:				task_mgr.Todo_mgr().Add_work(args.Get_as_int("task_id")); break;
			case Proc__del_work:				task_mgr.Work_mgr().Del_work(args.Get_as_int("task_id")); break;
			case Proc__del_done:				task_mgr.Done_mgr().Del_done(args.Get_as_int("task_id")); break;
			case Proc__del_todo:				task_mgr.Todo_mgr().Del_todo(args.Get_as_int("task_id")); break;
			case Proc__run_next:				task_mgr.Work_mgr().Run_next(); break;
			case Proc__stop_cur:				task_mgr.Work_mgr().Stop_cur(); break;
			case Proc__redo_cur:				task_mgr.Work_mgr().Redo_cur(); break;
			case Proc__filter_todo:				task_mgr.Filter_todo(args.Get_as_str("lang_key"), args.Get_as_str("type_key")); break;
			case Proc__download_db:				gplx.xowa.addons.bldrs.centrals.dbs.Xobc_data_db_upgrader.Check_for_updates(task_mgr); break;
			default: throw Err_.new_unhandled_default(proc_id);
		}
		return "";
	}
	private static final byte 
	  Proc__reload = 0, Proc__add_work = 1, Proc__del_work = 2, Proc__del_done = 3
	, Proc__run_next = 4, Proc__stop_cur = 5, Proc__redo_cur = 6, Proc__download_db = 7, Proc__filter_todo = 8
	, Proc__del_todo = 9
	;
	private static final    Hash_adp_bry proc_hash = Hash_adp_bry.cs()
	.Add_str_byte("reload"						, Proc__reload)
	.Add_str_byte("add_work"					, Proc__add_work)
	.Add_str_byte("del_work"					, Proc__del_work)
	.Add_str_byte("del_done"					, Proc__del_done)
	.Add_str_byte("run_next"					, Proc__run_next)
	.Add_str_byte("stop_cur"					, Proc__stop_cur)
	.Add_str_byte("redo_cur"					, Proc__redo_cur)
	.Add_str_byte("download_db"					, Proc__download_db)
	.Add_str_byte("filter_todo"					, Proc__filter_todo)
	.Add_str_byte("del_todo"					, Proc__del_todo)
	;

	public byte[] Key() {return BRIDGE_KEY;} public static final    byte[] BRIDGE_KEY = Bry_.new_a7("builder_central.exec");
        public static final    Xobc_task_bridge Prototype = new Xobc_task_bridge(); Xobc_task_bridge() {}
}
