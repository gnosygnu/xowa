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
package gplx.xowa.xtns.scribunto.engines.luaj; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.scribunto.*; import gplx.xowa.xtns.scribunto.engines.*;
import gplx.xowa.xtns.scribunto.procs.*;
import org.luaj.vm2.*; import org.luaj.vm2.lib.*; import org.luaj.vm2.lib.jse.*;
import gplx.xowa.xtns.scribunto.engines.process.*;
public class Luaj_engine implements Scrib_engine {
	private final Luaj_server_func_recv func_recv;
	private final Luaj_server_func_dbg func_dbg;
	private final Scrib_proc_mgr proc_mgr;
	private final Scrib_core core;	
	private Luaj_server server;
	public Luaj_engine(Xoae_app app, Scrib_core core, boolean debug_enabled) {
		this.core = core;
		this.proc_mgr = core.Proc_mgr();
		this.func_recv = new Luaj_server_func_recv(this);
		this.func_dbg = new Luaj_server_func_dbg(core);
		this.server = new Luaj_server(func_recv, func_dbg);
	}
	public Scrib_server Server() {return server;} public void Server_(Scrib_server v) {server = (Luaj_server)v;} 
	public boolean Dbg_print() {return dbg_print;} public void Dbg_print_(boolean v) {dbg_print = v;} private boolean dbg_print;
	public Scrib_lua_proc LoadString(String name, String text) {
		LuaTable msg = LuaValue.tableOf();
		msg.set("op", Val_loadString);
		msg.set("text", LuaValue.valueOf(text));
		msg.set("chunkName", LuaValue.valueOf(name));
		LuaTable rsp = server.Dispatch(msg);
		LuaTable values_tbl = Luaj_value_.Get_val_as_lua_table(rsp, "values");
		LuaInteger proc_id = (LuaInteger)values_tbl.rawget(1);
		return new Scrib_lua_proc(name, proc_id.v);
	}
	public void RegisterLibrary(Keyval[] functions) {
		LuaTable msg = LuaValue.tableOf();
		msg.set("op", Val_registerLibrary);
		msg.set("name", "mw_interface");
		msg.set("functions", Luaj_value_.Obj_to_lua_val(server, functions));
		server.Dispatch(msg);
	}
	public Keyval[] CallFunction(int id, Keyval[] args) {
		int args_len = args.length;
		LuaTable msg = LuaValue.tableOf();
		msg.set("op", Val_callFunction);
		msg.set("id", LuaValue.valueOf(id));
		msg.set("nargs", LuaValue.valueOf(args_len));
		msg.set("args", Luaj_value_.Obj_to_lua_val(server, args));
		return this.Dispatch_as_kv_ary(msg);
	}
	public Keyval[] ExecuteModule(int mod_id) {
		return this.CallFunction(core.Lib_mw().Mod().Fncs_get_id("executeModule"), Scrib_kv_utl_.base1_obj_(new Scrib_lua_proc("", mod_id)));
	}
	public void CleanupChunks(Keyval[] ids) {
		LuaTable msg = LuaValue.tableOf();
		msg.set("op", "cleanupChunks");
		msg.set("ids", Luaj_value_.Obj_to_lua_val(server, ids));
		this.Dispatch_as_kv_ary(msg);		
	}
	public Keyval[] Dispatch_as_kv_ary(LuaTable msg) {
		while (true) {
			LuaTable rsp = server.Dispatch(msg);
			String op = Luaj_value_.Get_val_as_str(rsp, "op");
			if		(String_.Eq(op, "return"))
				return Luaj_value_.Get_val_as_kv_ary(server, rsp, "values");
			else if (String_.Eq(op, "call"))
				msg = Server_recv_call(rsp);
			else if (String_.Eq(op, "error")) {
				String err = Luaj_value_.Get_val_as_str(rsp, "value");
				core.Handle_error(err);
				return Keyval_.Ary_empty;
			}
			else
				return Keyval_.Ary_empty;
		}		
	}
	public LuaTable Server_recv_call(LuaTable rsp) {
		String proc_id = Luaj_value_.Get_val_as_str(rsp, "id");
		Keyval[] args = Luaj_value_.Get_val_as_kv_ary(server, rsp, "args");
		Scrib_proc proc = proc_mgr.Get_by_key(proc_id); if (proc == null) throw Scrib_xtn_mgr.err_("could not find proc with id of {0}", proc_id);
		Scrib_proc_args proc_args = new Scrib_proc_args(args);
		Scrib_proc_rslt proc_rslt = new Scrib_proc_rslt();
		proc.Proc_exec(proc_args, proc_rslt);
		String fail_msg = proc_rslt.Fail_msg();
		if (fail_msg == null) { 
			Keyval[] cbk_rslts = proc_rslt.Ary();
			return ReturnMessage(cbk_rslts);
		}
		else {
			return ReturnFail(fail_msg);			
		}
	}
	private LuaTable ReturnMessage(Keyval[] values) {
		LuaTable msg = LuaValue.tableOf();
		msg.set("op", Val_returnMessage);
		msg.set("nvalues", LuaValue.valueOf(values.length));
		msg.set("values", Luaj_value_.Obj_to_lua_val(server, values));
		return msg;
	}
	private LuaTable ReturnFail(String fail_msg) {
		LuaTable msg = LuaValue.tableOf();
		msg.set("op", Val_error);
		msg.set("value", LuaValue.valueOf(fail_msg));
		return msg;
	}
	private static final LuaValue 
	  Val_loadString 		= LuaValue.valueOf("loadString")
	, Val_registerLibrary 	= LuaValue.valueOf("registerLibrary")
	, Val_callFunction 		= LuaValue.valueOf("call")
	, Val_returnMessage 	= LuaValue.valueOf("return")
	, Val_error 			= LuaValue.valueOf("error")
	;
}
