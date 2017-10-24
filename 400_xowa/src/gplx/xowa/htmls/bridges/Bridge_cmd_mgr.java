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
package gplx.xowa.htmls.bridges; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*;
import gplx.langs.jsons.*;
public class Bridge_cmd_mgr {
	private final    Json_parser parser;
	private final    Hash_adp_bry cmd_hash = Hash_adp_bry.cs();
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
		Bridge_cmd_itm cmd = (Bridge_cmd_itm)cmd_hash.Get_by_bry(key_bry); if (cmd == null) throw Err_.new_("bridge.cmds", "unknown cmd", "key", key_bry);
		try		{return cmd.Exec(msg.Get(Key_data));} 
		catch	(Exception e) {
			Xoa_app_.Usr_dlg().Warn_many("", "", "exec json failed: ~{0}", "json", jdoc_bry);
			throw Err_.new_exc(e, "bridge.cmds", "exec json failed", "json", jdoc_bry);
		}
	}
	private static final    byte[] Key_cmd = Bry_.new_a7("cmd"), Key_data = Bry_.new_a7("data");
	public static final    byte[] Msg__proc = Bry_.new_a7("proc"), Msg__args = Bry_.new_a7("args");
	public static String Msg__ok = String_.Empty;
}
