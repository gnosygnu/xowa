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
package gplx.xowa.addons.wikis.searchs.dbs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.searchs.*;
import gplx.dbs.*;
public class Srch_temp_tbl {
	public final    String tbl_name = "search_temp"; 
	private final    Dbmeta_fld_list flds = new Dbmeta_fld_list();
	private final    String fld_word_id, fld_page_id, fld_word_text;
	public final    Db_conn conn; private Db_stmt stmt_insert;		
	public Srch_temp_tbl(Db_conn conn) {
		this.conn = conn;
		flds.Add_int_pkey_autonum("word_uid");
		fld_word_id			= flds.Add_int("word_id");
		fld_page_id			= flds.Add_int("page_id");
		fld_word_text		= flds.Add_str("word_text", 255);
	}
	public void Insert_bgn() {
		conn.Meta_tbl_create(Dbmeta_tbl_itm.New(tbl_name, flds));
		stmt_insert = conn.Stmt_insert(tbl_name, flds);
		conn.Txn_bgn("schema__search_temp__insert");
	}
	public void Insert_cmd_by_batch(int word_id, int page_id, byte[] word) {
		stmt_insert.Clear().Val_int(fld_word_id, word_id).Val_int(fld_page_id, page_id).Val_bry_as_str(fld_word_text, word).Exec_insert();
	}
	public void Insert_end() {
		conn.Txn_end();
		stmt_insert = Db_stmt_.Rls(stmt_insert);			
		// Srch_db_mgr.Optimize_unsafe_(conn, Bool_.Y);	// NOTE: fails in multi-db due to transaction
		conn.Meta_idx_create(Xoa_app_.Usr_dlg(), Dbmeta_idx_itm.new_normal_by_tbl(tbl_name, "word_text__word_id", fld_word_text, fld_word_id));
		// conn.Meta_idx_create(Xoa_app_.Usr_dlg(), Dbmeta_idx_itm.new_normal_by_tbl(tbl_name, "page_id", fld_page_id));
		// Srch_db_mgr.Optimize_unsafe_(conn, Bool_.N);
	}
}
