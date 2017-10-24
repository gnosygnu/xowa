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
package gplx.xowa.addons.wikis.ctgs.bldrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.ctgs.*;
import gplx.dbs.*; import gplx.xowa.addons.wikis.ctgs.dbs.*;
import gplx.xowa.addons.wikis.ctgs.enums.*;
import gplx.xowa.wikis.data.*; import gplx.xowa.wikis.data.tbls.*;
class Xob_catlink_mgr {
	private Xowe_wiki wiki;
	private Xodb_tmp_cat_db tmp_db; private Db_conn tmp_conn; private Xodb_tmp_cat_link_tbl tmp_link_tbl;
	private final    Xoctg_collation_enum collation_enum = new Xoctg_collation_enum(); private final    Xoctg_type_enum type_enum = new Xoctg_type_enum();
	private int rows = 0;
	public void On_cmd_bgn(Xowe_wiki wiki) {
		this.wiki = wiki;

		// init tmp_db, tmp_link_tbl
		this.tmp_db = new Xodb_tmp_cat_db(wiki);
		this.tmp_conn = tmp_db.Conn();
		this.tmp_link_tbl = new Xodb_tmp_cat_link_tbl(tmp_conn);
		tmp_link_tbl.Insert_bgn();
	}
	public void On_cmd_row(int page_id, byte[] ctg_ttl, byte[] sortkey_orig, byte[] timestamp_bry, byte[] sortkey_prefix, byte[] collation_bry, byte[] type_bry) {
		// convert strings to numbers
		String timestamp_str = String_.new_u8(timestamp_bry);
		long timestamp = String_.Len_eq_0(timestamp_str) ? 0 : DateAdp_.parse_fmt(timestamp_str, "YYYY-MM-dd HH:mm:ss").Timestamp_unix();
		byte collation_id = collation_enum.To_tid_or_fail(collation_bry);
		byte type_id = type_enum.To_tid_or_fail(type_bry);

		// sortkey
		byte[] sortkey_actl = sortkey_orig;
		if (collation_id != Xoctg_collation_enum.Tid__uca) {
			// sortkey; handle \n
			int nl_pos = Bry_find_.Find_fwd(sortkey_actl, Byte_ascii.Nl);
			if (nl_pos != Bry_find_.Not_found)	// some sortkeys have format of "sortkey\ntitle"; discard 2nd to conserve hard-disk space; EX: "WALES, JIMMY\nJIMMY WALES"
				sortkey_actl = Bry_.Mid(sortkey_actl, 0, nl_pos);	// NOTE: some sortkeys have space which will sort under " "; EX: ' \nART' -> " "; SEE: s.w:Category:Art
		}

		// insert to tmp; notify; commit
		tmp_link_tbl.Insert_cmd_by_batch(page_id, ctg_ttl, sortkey_actl, timestamp, sortkey_prefix, collation_id, type_id);
		if (++rows % 100000 == 0) {
			Gfo_usr_dlg_.Instance.Prog_many("", "", "parsing categorylinks sql: ~{0}", Int_.To_str_fmt(rows, "#,##0"));
			tmp_conn.Txn_sav();
		}
	}
	public void On_cmd_end() {
		tmp_link_tbl.Insert_end();

		// get cat_core conn
		tmp_link_tbl.Create_idx__sortkey();	// index should make SELECT DISTINCT faster
		Db_conn cat_core_conn = wiki.Data__core_mgr().Db__core().Conn();
		if (wiki.Data__core_mgr().Props().Layout_text().Tid_is_lot()) {
			Xow_db_file cat_core_db = wiki.Data__core_mgr().Dbs__get_by_tid_or_null(Xow_db_file_.Tid__cat_core);
			if (cat_core_db == null)
				cat_core_db = wiki.Data__core_mgr().Dbs__make_by_tid(Xow_db_file_.Tid__cat_core);
			cat_core_conn = cat_core_db.Conn();
		}

		// create tbl
		// Xodb_cat_sort_tbl cat_sort_tbl = new Xodb_cat_sort_tbl(cat_core_conn);
		// cat_core_conn.Meta_tbl_remake(cat_sort_tbl);
		// cat_sort_tbl.Insert_by_select(tmp_conn);

		// make catlink_dbs
		// cat_sort_tbl.Create_idx__key();		// index will be needed for join
		tmp_link_tbl.Create_idx();	// index will be needed for join
		Db_conn page_conn = wiki.Data__core_mgr().Db__core().Conn();
		Xob_catlink_wkr wkr = new Xob_catlink_wkr();
		try {
			wkr.Make_catlink_dbs(wiki, tmp_conn, page_conn, cat_core_conn);
		} catch (Exception e) {
			Gfo_usr_dlg_.Instance.Log_many("", "", "error while generating catlink dbs; ~{0}", Err_.Message_gplx_log(e));
			throw Err_.new_wo_type("error while generating catlink dbs", "err", Err_.Message_gplx_log(e));
		}

		// make catcore_tbl; update page!cat_db_id
		wkr.Make_catcore_tbl(wiki, tmp_conn, page_conn, cat_core_conn);
		wkr.Update_page_cat_db_id(wiki, page_conn);

		// cleanup
		// cat_sort_tbl.Delete_idx__key();		// remove idx
		tmp_db.Delete();
	}
}
