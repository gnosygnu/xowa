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
import org.luaj.vm2.LuaThread;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
public class Luaj_server_func_dbg extends VarArgFunction {
	private final Scrib_core core;
	public Luaj_server_func_dbg(Scrib_core v) {this.core = v;}	
	public Varargs invoke(Varargs args) {
		int len = args.narg();
		Bry_bfr bfr = Bry_bfr_.New();
		bfr.Add_str_a7("<span class='xowa_dbg' style='color:red'>");
		for (int i = 1; i <= len; ++i) {
			String s = args.arg(i).toString();
			bfr.Add_str_u8(gplx.langs.htmls.Gfh_utl.Escape_html_as_str(s) + "&nbsp;");
		}
		bfr.Add_str_a7("</span><br/>");
		core.Page().Html_data().Xtn_scribunto_dbg_(bfr.To_bry_and_clear());
		return NONE;
	}
}
