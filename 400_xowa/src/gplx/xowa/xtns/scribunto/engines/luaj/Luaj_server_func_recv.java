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
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;
public class Luaj_server_func_recv extends OneArgFunction {
	private final Luaj_engine engine;
	public Luaj_server_func_recv(Luaj_engine v) {this.engine = v;}
	public LuaValue call(LuaValue tbl_val) {
		LuaTable tbl = (LuaTable)tbl_val;
		String op = Luaj_value_.Get_val_as_str(tbl, "op");
		if (!String_.Eq(op, "call")) throw Err_.new_wo_type("luaj_recvr only processes op calls");
		return engine.Server_recv_call(tbl);
	}
}
