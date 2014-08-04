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
//import org.luaj.vm2.LuaTable;
//import org.luaj.vm2.LuaValue;
//import org.luaj.vm2.lib.OneArgFunction;
//import org.luaj.vm2.lib.jse.JsePlatform;
//
//import gplx.xowa.xtns.scribunto.engines.luaj.Luaj_server_func_recv;
//public class MWClient extends OneArgFunction {
//	/** The implementation of the ZeroArgFunction interface.
//	 * This will be called once when the library is loaded via require().
//	 * @param arg LuaString containing the name used in the call to require().
//	 * @return Value that will be returned in the require() call.  In this case, 
//	 * it is the library itself.
//	 */
//	public LuaValue call(LuaValue libname) {
//		LuaValue library = tableOf();
//		library.set("client_recv", Luaj_server_func_recv._);
//		LuaValue env = gplx.xowa.xtns.scribunto.engines.luaj.Luaj_server.Globals_singleton; 
//		env.set( "MWClient", library );
//		return library;
//	}
//}
