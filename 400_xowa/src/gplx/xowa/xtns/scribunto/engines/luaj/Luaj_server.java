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
import gplx.core.envs.*;
import org.luaj.vm2.*; import org.luaj.vm2.lib.*; import org.luaj.vm2.lib.jse.*;
import gplx.xowa.xtns.scribunto.engines.process.*;
public class Luaj_server implements Scrib_server {
	private final Luaj_server_func_recv func_recv;
	private final Luaj_server_func_dbg func_dbg;
	private LuaTable server;
	public Luaj_server(Luaj_server_func_recv func_recv, Luaj_server_func_dbg func_dbg) {
		this.func_recv = func_recv;
		this.func_dbg = func_dbg;
	}
	public Globals Luaj_globals() {return luaj_globals;} private Globals luaj_globals;
	public DebugLib Luaj_dbg() {return luaj_dbg;} private DebugLib luaj_dbg;
	public void Init(String... init_args) {
		luaj_dbg = new DebugLib();	// NOTE: needed for getfenv
		luaj_globals = JsePlatform.standardGlobals();
		luaj_globals.load(luaj_dbg);
		luaj_globals.load(new MWClient(luaj_globals, func_recv));
		luaj_globals.set("dbg", func_dbg);
		String root_str = init_args[2];
		if (Op_sys.Cur().Tid_is_wnt())
			root_str = String_.Replace(root_str, Op_sys.Wnt.Fsys_dir_spr_str(), Op_sys.Lnx.Fsys_dir_spr_str());
		LuaValue main_fil_val = LuaValue.valueOf(root_str + "engines/Luaj/mw_main.lua");
		LuaValue package_val = luaj_globals.get("package");
		package_val.rawset("path", LuaValue.valueOf(root_str + "engines/Luaj/?.lua;" + root_str + "engines/LuaCommon/lualib/?.lua"));
		server = (LuaTable)luaj_globals.get("dofile").call(main_fil_val);
	}
	public LuaTable Dispatch(LuaTable msg) {
		return (LuaTable)server.method(Val_server_recv, msg);
	}
	public int Get_id_by_closure(LuaValue closure) {
		LuaValue xchunks = server.get(Val_xchunks);
		LuaValue closure_id = xchunks.get(closure);
		int rv = -1;
		if (closure_id == LuaValue.NIL)		// new closure; add it to chunks table via addChunk (which will return new id)
			rv = ((LuaInteger)server.method("addChunk", closure)).v;
		else
			rv = ((LuaInteger)closure_id).v;
		return rv;		
	}
	public LuaValue Get_closure_by_id(int id) {
		LuaValue chunks = server.get(Val_chunks);
		return chunks.get(LuaValue.valueOf(id));
	}
	public int Server_timeout() {return server_timeout;} public Scrib_server Server_timeout_(int v) {server_timeout = v; return this;} private int server_timeout;
	public int Server_timeout_polling() {return server_timeout_polling;} public Scrib_server Server_timeout_polling_(int v) {server_timeout_polling = v; return this;} private int server_timeout_polling;
	public int Server_timeout_busy_wait() {return server_timeout_busy_wait;} public Scrib_server Server_timeout_busy_wait_(int v) {server_timeout_busy_wait = v; return this;} private int server_timeout_busy_wait;
	public byte[] Server_comm(byte[] cmd, Object[] cmd_objs) {return Bry_.Empty;}
	public void Server_send(byte[] cmd, Object[] cmd_objs) {}
	public byte[] Server_recv() {return Bry_.Empty;}
	public void Term() {}	
	private static final LuaValue
	  Val_server_recv 		= LuaValue.valueOf("server_recv")
	, Val_xchunks			= LuaValue.valueOf("xchunks")
	, Val_chunks			= LuaValue.valueOf("chunks")
	;
	class MWClient extends OneArgFunction {
		/** The implementation of the ZeroArgFunction interface.
		 * This will be called once when the library is loaded via require().
		 * @param arg LuaString containing the name used in the call to require().
		 * @return Value that will be returned in the require() call.  In this case, 
		 * it is the library itself.
		 */
		private final Globals luaj_globals;
		private final Luaj_server_func_recv func_recv;
		public MWClient(Globals luaj_globals, Luaj_server_func_recv func_recv) {
			this.luaj_globals = luaj_globals;
			this.func_recv = func_recv;
		}
		public LuaValue call(LuaValue libname) {
			LuaValue library = tableOf();
			library.set("client_recv", func_recv);
			LuaValue env = luaj_globals; 
			env.set( "MWClient", library );
			return library;
		}
	}
}
