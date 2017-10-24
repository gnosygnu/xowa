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
package gplx.xowa.addons.wikis.directorys.specials.items; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.directorys.*; import gplx.xowa.addons.wikis.directorys.specials.*;
import gplx.langs.jsons.*;
import gplx.xowa.addons.wikis.directorys.dbs.*;
import gplx.xowa.htmls.bridges.*;
public class Xowdir_item_bridge implements Bridge_cmd_itm {
	private Xowdir_item_mgr itm_mgr;
	public void Init_by_app(Xoa_app app) {
		this.itm_mgr = new Xowdir_item_mgr(app);
	}
	public String Exec(Json_nde data) {
		byte proc_id = proc_hash.Get_as_byte_or(data.Get_as_bry_or(Bridge_cmd_mgr.Msg__proc, null), Byte_ascii.Max_7_bit);
		Json_nde args = data.Get_kv(Bridge_cmd_mgr.Msg__args).Val_as_nde();
		switch (proc_id) {
			case Proc__save:					itm_mgr.Save(args); break;
			case Proc__delete:					itm_mgr.Delete(args); break;
			case Proc__reindex_search:			itm_mgr.Reindex_search(args); break;
			default: throw Err_.new_unhandled_default(proc_id);
		}
		return "";
	}

	private static final byte Proc__save = 0, Proc__delete = 1, Proc__reindex_search = 2;
	private static final    Hash_adp_bry proc_hash = Hash_adp_bry.cs()
	.Add_str_byte("save"						, Proc__save)
	.Add_str_byte("delete"						, Proc__delete)
	.Add_str_byte("reindex_search"				, Proc__reindex_search)
	;

	public byte[] Key() {return BRIDGE_KEY;} public static final    byte[] BRIDGE_KEY = Bry_.new_a7("wiki.directory.item");
        public static final    Xowdir_item_bridge Prototype = new Xowdir_item_bridge(); Xowdir_item_bridge() {}
}
