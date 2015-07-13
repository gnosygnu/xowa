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
package gplx.xowa.xtns.scribunto.engines.luaj; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.scribunto.*; import gplx.xowa.xtns.scribunto.engines.*;
import org.luaj.vm2.*; import org.luaj.vm2.lib.*; import org.luaj.vm2.lib.jse.*;
import gplx.xowa.xtns.scribunto.engines.process.*;
public class Luaj_server implements Scrib_server {
	private LuaTable server;
	public Luaj_server(Scrib_core core, boolean debug_enabled) {
	}
	public static Globals Globals_singleton;
	public void Init(String... init_args) {
		Globals_singleton = JsePlatform.standardGlobals();
		Globals_singleton.load(new DebugLib());
		Globals_singleton.load(new MWClient());
		Globals_singleton.set("dbg", Luaj_server_func_dbg._);
		String root_str = init_args[2];
		if (Op_sys.Cur().Tid_is_wnt())
			root_str = String_.Replace(root_str, Op_sys.Wnt.Fsys_dir_spr_str(), Op_sys.Lnx.Fsys_dir_spr_str());
		LuaValue main_fil_val = LuaValue.valueOf(root_str + "engines/Luaj/mw_main.lua");
		LuaValue package_val = Globals_singleton.get("package");
		package_val.rawset("path", LuaValue.valueOf(root_str + "engines/Luaj/?.lua;" + root_str + "engines/LuaCommon/lualib/?.lua"));
		server = (LuaTable)Globals_singleton.get("dofile").call(main_fil_val);
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
		public LuaValue call(LuaValue libname) {
			LuaValue library = tableOf();
			library.set("client_recv", Luaj_server_func_recv._);
			LuaValue env = gplx.xowa.xtns.scribunto.engines.luaj.Luaj_server.Globals_singleton; 
			env.set( "MWClient", library );
			return library;
		}
	}
}
