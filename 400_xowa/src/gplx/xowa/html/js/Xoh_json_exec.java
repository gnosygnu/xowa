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
package gplx.xowa.html.js; import gplx.*; import gplx.xowa.*; import gplx.xowa.html.*;
import gplx.core.primitives.*;
import gplx.json.*;
public class Xoh_json_exec {
	private final Xoa_app app;
	public Xoh_json_exec(Xoa_app app) {this.app = app;}
	public String Exec_json(GfoMsg m) {
		if (m.Args_count() == 0) throw Exc_.new_("xowa_exec.json; no json specified for json_exec");
		return Exec_json(m.Args_getAt(0).Val_to_bry());
	}
	public String Exec_json(byte[] jdoc_bry) {
		Json_doc jdoc = null;
		try		{jdoc = Json_doc.new_(jdoc_bry);}
		catch	(Exception e) {throw Exc_.new_exc(e, "js", "xowa_exec.json; invalid json", "json", jdoc_bry);}
		return Xoui_exec(jdoc, jdoc.Root());
	}
	private String Xoui_exec(Json_doc jdoc, Json_itm_nde nde) {
		byte[] wiki_bry = nde.Subs_get_val_by_key_as_bry(Key_wiki, null); if (wiki_bry == null) throw Exc_.new_("xoui_exec.json; no wiki specified", "json", jdoc.Src());
		byte[] proc_bry	= nde.Subs_get_val_by_key_as_bry(Key_proc, null); if (proc_bry == null) throw Exc_.new_("xoui_exec.json; no proc specified", "json", jdoc.Src());
		Xow_wiki wiki = app.Wiki_mgri().Get_by_key_or_make_init_y(wiki_bry);
		Object tid_obj = proc_hash.Get_by(proc_bry); if (tid_obj == null) throw Exc_.new_("xoui_exec.json; invalid tid", "json", jdoc.Src());
		switch (((Byte_obj_val)tid_obj).Val()) {
			case Tid_edit	: return wiki.Html__xoui_tbl_mgr().Edit(jdoc);
			case Tid_save	: return wiki.Html__xoui_tbl_mgr().Save(wiki, jdoc);
			case Tid_delete	: return wiki.Html__xoui_tbl_mgr().Del(jdoc);
		}
		throw Exc_.new_("xoui_exec.json; cmd not handled", "json", jdoc.Src());
	}
	private static final byte[] Key_wiki = Bry_.new_a7("wiki"), Key_proc = Bry_.new_a7("proc");
	private static final byte Tid_edit = 1, Tid_save = 2, Tid_delete = 3;
	private static final Hash_adp_bry proc_hash = Hash_adp_bry.cs_()
	.Add_str_byte("xowa.xoui.grid.edit"		, Tid_edit)
	.Add_str_byte("xowa.xoui.grid.save"		, Tid_save)
	.Add_str_byte("xowa.xoui.grid.delete"	, Tid_delete)
	;
}
