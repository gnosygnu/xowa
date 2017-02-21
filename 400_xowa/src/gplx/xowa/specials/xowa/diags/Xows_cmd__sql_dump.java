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
package gplx.xowa.specials.xowa.diags; import gplx.*; import gplx.xowa.*; import gplx.xowa.specials.*; import gplx.xowa.specials.xowa.*;
import gplx.core.ios.*; import gplx.core.primitives.*; import gplx.core.net.*; import gplx.core.net.qargs.*;
import gplx.dbs.*;
import gplx.fsdb.meta.*;
import gplx.xowa.apps.urls.*;
class Xows_cmd__sql_dump {
	public void Exec(Bry_bfr bfr, Xoa_app app, Xoa_url url, Gfo_qarg_mgr_old arg_hash) {
		Db_conn conn = null;
		byte[] sql_bry = arg_hash.Get_val_bry_or(Arg_sql, null); if (sql_bry == null) {Xoa_app_.Usr_dlg().Warn_many("", "", "special.cmd; no sql: url=~{0}", url.Raw()); return;}
		byte[] wiki_bry = arg_hash.Get_val_bry_or(Arg_wiki, null);
		if (wiki_bry == null) {
			byte[] db_file_bry = arg_hash.Get_val_bry_or(Arg_db_file, null); if (db_file_bry == null) {Xoa_app_.Usr_dlg().Warn_many("", "", "special.cmd; no db_type: url=~{0}", url.Raw()); return;}
			conn = Db_conn_bldr.Instance.Get(Io_url_.new_fil_(String_.new_u8(db_file_bry)));
		}
		else {
			byte[] db_type_bry = arg_hash.Get_val_bry_or(Arg_db_type, null); if (db_type_bry == null) {Xoa_app_.Usr_dlg().Warn_many("", "", "special.cmd; no db_type: url=~{0}", url.Raw()); return;}
			Xow_wiki wiki = app.Wiki_mgri().Get_by_or_make_init_y(wiki_bry);
			Byte_obj_val db_type_val = (Byte_obj_val)db_type_hash.Get_by_bry(db_type_bry);	if (db_type_val == null) {Xoa_app_.Usr_dlg().Warn_many("", "", "special.cmd; bad db_type: url=~{0}", url.Raw()); return;}
			switch (db_type_val.Val()) {
				case Db_type_wiki_core: conn = wiki.Data__core_mgr().Db__core().Conn(); break;
				case Db_type_fsdb_abc:	conn = wiki.File__fsdb_core().File__abc_file__at(Fsm_mnt_mgr.Mnt_idx_main).Conn(); break;
				case Db_type_fsdb_atr:	conn = wiki.File__fsdb_core().File__atr_file__at(Fsm_mnt_mgr.Mnt_idx_main).Conn(); break;
			}
		}
		Db_rdr_utl.Load_and_write(conn, String_.new_u8(sql_bry), bfr);
	}
        public static final    Xows_cmd__sql_dump Instance = new Xows_cmd__sql_dump(); Xows_cmd__sql_dump() {}
	private static final    byte[] Arg_wiki = Bry_.new_a7("wiki"), Arg_db_file = Bry_.new_a7("db_file"), Arg_db_type = Bry_.new_a7("db_type"), Arg_sql = Bry_.new_a7("sql");
	private static final byte Db_type_fsdb_abc = 1, Db_type_fsdb_atr = 2, Db_type_wiki_core = 3;
	private static final    Hash_adp_bry db_type_hash = Hash_adp_bry.cs()
	.Add_str_byte("fsdb.abc"		, Db_type_fsdb_abc)
	.Add_str_byte("fsdb.atr"		, Db_type_fsdb_atr)
	.Add_str_byte("wiki.core"		, Db_type_wiki_core)
	;
}
