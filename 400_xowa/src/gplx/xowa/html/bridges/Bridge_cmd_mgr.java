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
package gplx.xowa.html.bridges; import gplx.*; import gplx.xowa.*; import gplx.xowa.html.*;
import gplx.langs.jsons.*;
public class Bridge_cmd_mgr {
	private final Json_parser parser;
	private final Hash_adp_bry cmd_hash = Hash_adp_bry.cs();
	public Bridge_cmd_mgr(Json_parser parser) {this.parser = parser;}
	public void Add(Bridge_cmd_itm cmd) {cmd_hash.Add_bry_obj(cmd.Key(), cmd);}
	public String Exec(GfoMsg m) {
		if (m.Args_count() == 0) throw Err_.new_("bridge.cmds", "no json specified for json_exec");
		return Exec(m.Args_getAt(0).Val_to_bry());
	}
	public String Exec(byte[] jdoc_bry) {
		Json_doc jdoc = null;
		try		{jdoc = parser.Parse(jdoc_bry);}
		catch	(Exception e) {throw Err_.new_exc(e, "bridge.cmds", "invalid json", "json", jdoc_bry);}
		Json_nde msg = jdoc.Root_nde();
		byte[] key_bry = msg.Get_bry(Key_cmd);
		Bridge_cmd_itm cmd = (Bridge_cmd_itm)cmd_hash.Get_by_bry(key_bry); if (cmd == null) throw Err_.new_("bridge.cmds", "unknown cmd", "cmd", cmd);
		try		{return cmd.Exec(msg.Get(Key_data));} 
		catch	(Exception e) {throw Err_.new_exc(e, "bridge.cmds", "exec json failed", "json", jdoc_bry);}
	}
	private static final byte[] Key_cmd = Bry_.new_a7("cmd"), Key_data = Bry_.new_a7("data");
}
