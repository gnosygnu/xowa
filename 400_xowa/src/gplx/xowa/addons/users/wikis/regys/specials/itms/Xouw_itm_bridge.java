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
package gplx.xowa.addons.users.wikis.regys.specials.itms; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.users.*; import gplx.xowa.addons.users.wikis.*; import gplx.xowa.addons.users.wikis.regys.*; import gplx.xowa.addons.users.wikis.regys.specials.*;
import gplx.langs.jsons.*;
import gplx.xowa.addons.users.wikis.regys.dbs.*;
public class Xouw_itm_bridge implements gplx.xowa.htmls.bridges.Bridge_cmd_itm {
	private Xoa_app app;
	public void Init_by_app(Xoa_app app) {
		this.app = app;
	}
	public String Exec(Json_nde data) {
		byte proc_id = proc_hash.Get_as_byte_or(data.Get_as_bry_or(Msg__proc, null), Byte_ascii.Max_7_bit);
		Json_nde args = data.Get_kv(Msg__args).Val_as_nde();
		switch (proc_id) {
			case Proc__save:					Save(args); break;
			default: throw Err_.new_unhandled_default(proc_id);
		}
		return "";
	}
	private void Save(Json_nde args) {
		Xouw_db_mgr db_mgr = new Xouw_db_mgr(app.User().User_db_mgr().Conn());
		int id = args.Get_as_int("id");
		if (id == -1) {
			id = 2;
		}
		String domain = args.Get_as_str("key");
		String url = args.Get_as_str("file");
		if (db_mgr.Tbl__wiki().Upsert(id, domain, args.Get_as_str("name"), url)) {
			gplx.xowa.wikis.nss.Xow_ns_mgr ns_mgr = gplx.xowa.wikis.nss.Xow_ns_mgr_.default_(gplx.xowa.langs.cases.Xol_case_mgr_.U8());
			Xow_wiki_.Create(app, ns_mgr, domain, Io_url_.new_fil_(url).OwnerDir());
			Xowe_wiki wiki = new Xowe_wiki((Xoae_app)app, app.Lang_mgr().Get_by_or_en(Bry_.Empty), ns_mgr, gplx.xowa.wikis.domains.Xow_domain_itm_.parse(Bry_.new_u8(domain)), Io_url_.new_fil_(url).OwnerDir());
			gplx.fsdb.Fsdb_db_mgr__v2_bldr.Make_core_file_main(wiki, Io_url_.new_fil_(url), domain, gplx.xowa.wikis.data.Xow_db_layout.Itm_all);
			wiki.Init_db_mgr();
			wiki.Init_by_wiki__force_and_mark_inited();
			wiki.Data__core_mgr().Dbs__make_by_id(1, gplx.xowa.wikis.data.Xow_db_file_.Tid__core, "", 0, ".xowa");
			wiki.Data__core_mgr().Db__core().Tbl__db().Commit_all(wiki.Data__core_mgr());
			wiki.Db_mgr().Save_mgr().Data_create(Xoa_ttl.Parse(wiki, Bry_.new_a7("Main_Page")), Bry_.new_a7("Main page created"));
		}
	}
	private static final    byte[] Msg__proc = Bry_.new_a7("proc"), Msg__args = Bry_.new_a7("args");
	private static final byte Proc__save = 0;
	private static final    Hash_adp_bry proc_hash = Hash_adp_bry.cs()
	.Add_str_byte("save"						, Proc__save)
	;

	public byte[] Key() {return BRIDGE_KEY;} public static final    byte[] BRIDGE_KEY = Bry_.new_a7("user.wiki.itm.exec");
        public static final    Xouw_itm_bridge Prototype = new Xouw_itm_bridge(); Xouw_itm_bridge() {}
}
