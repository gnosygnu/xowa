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
package gplx.xowa.addons.bldrs.exports.merges; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.exports.*;
import gplx.dbs.*; import gplx.xowa.addons.bldrs.exports.utls.*;
public class Merge2_heap_mgr {// data tbls will only have 1 itms; blob tbls will have N itms
	private final    Split_tbl tbl;
	private final    List_adp list = List_adp_.New(), deleted = List_adp_.New();
	private Dbmeta_fld_list heap_flds;
	public Merge2_heap_mgr(Split_tbl tbl) {this.tbl = tbl;}
	public Merge2_heap_db	Cur()			{return cur;} private Merge2_heap_db cur;
	public Merge2_heap_db Make_itm(Xow_wiki wiki, int trg_db_id) {
		// clone tbl_flds; disable primary key
		this.heap_flds = tbl.Flds().Clone();
		int len = heap_flds.Len();
		for (int i = 0; i < len; ++i) {
			Dbmeta_fld_itm itm = heap_flds.Get_at(i);
			if (itm.Primary()) itm.Primary_n_();
		}

		// if blob, add "blob_len" in penultimate pos; note that last fld is "blob_fld"			
//			if (Split_tbl_.Tbl_has_blob(tbl))
//				Split_tbl_.Flds__add_blob_len(heap_flds);

		// init heap_conn
		String tbl_name = tbl.Tbl_name();
		Io_url heap_dir = wiki.Fsys_mgr().Root_dir().GenSubDir_nest("tmp", "merge", tbl_name);
		Io_url heap_url = heap_dir.GenSubFil(String_.Format("xowa.merge.temp.{0}{1}.sqlite3", tbl_name, trg_db_id == -1 ? "" : "." + Int_.To_str_pad_bgn_zero(trg_db_id, 4)));
		Db_conn_bldr_data bldr_data = Db_conn_bldr.Instance.Get_or_new(heap_url);
		Db_conn heap_conn = bldr_data.Conn();
		if (bldr_data.Created())
			heap_conn.Meta_tbl_create(Dbmeta_tbl_itm.New(tbl_name, heap_flds));

		// make itm and return it
		Merge2_heap_db rv = new Merge2_heap_db(tbl, heap_flds, trg_db_id, heap_url, heap_conn);
		this.Add(rv);
		return rv;
	}
	public void Copy_to_wiki(Merge_ctx ctx, Merge_prog_wkr prog_wkr, Merge2_wkr__heap_base wkr) {
		// do copy for all itms; only 1 itm for page, fsdb_fil, etc..; but many for html, fsdb_bin
		int len = list.Len();
		for (int i = 0; i < len; ++i)
			Copy_to_wiki__itm(ctx, prog_wkr, wkr, this.Get_at(i), i == len - 1);

		// delete any old dbs
		len = deleted.Len();
		for (int i = 0; i < len; ++i) {
			Merge2_heap_db itm = (Merge2_heap_db)deleted.Get_at(i);
			list.Del(itm);
		}
		deleted.Clear();

		// if last idx, cleanup
		if (ctx.Heap__last_idx()) this.Cleanup();
	}
	public void Cleanup() {
		int len = list.Len();
		for (int i = 0; i < len; ++i)
			Cleanup_file(this.Get_at(i));
	}
	private Merge2_heap_db	Get_at(int i)	{return (Merge2_heap_db)list.Get_at(i);}
	private void Add(Merge2_heap_db itm) {
		list.Add(itm);
		cur = itm;
	}
	private void Copy_to_wiki__itm(Merge_ctx ctx, Merge_prog_wkr prog_wkr, Merge2_wkr__heap_base wkr, Merge2_heap_db itm, boolean itm_is_cur) {
		// init
		Split_tbl tbl = wkr.Tbl();
		String tbl_name = tbl.Tbl_name();
		Db_conn heap_conn = itm.Conn();
		Dbmeta_fld_list tbl_flds = tbl.Flds();
		Db_conn wiki_conn = tbl.Wiki_conn__get_or_new(ctx.Wiki(), itm.Idx());

		// index heap table
		// String[] pkey_flds = tbl.Fld_pkeys();
		// heap_conn.Meta_idx_create(tbl_name, "sort", pkey_flds);
		
		// do bulk copy into wiki tbls; note ORDER BY
		wkr.Copy_to_wiki(ctx, prog_wkr, tbl_name, tbl_flds, heap_conn, new Merge2_trg_itm__wiki(itm.Idx(), wiki_conn), String_.Ary_empty);

		// cleanup
		if (itm_is_cur) {	// if cur, rebuild heap table; vaccum;
			// heap_conn.Meta_tbl_remake(Dbmeta_tbl_itm.New(tbl_name, heap_flds));
			// heap_conn.Env_vacuum();
			heap_conn.Rls_conn();
			Io_mgr.Instance.DeleteFil(itm.Url());	// SQLite: file delete is faster than DROP TABLE or DELETE FROM
			heap_conn.Reopen_conn();
			heap_conn.Meta_tbl_remake(Dbmeta_tbl_itm.New(tbl_name, heap_flds));
		}
		else {				// else, delete file; EX: fsdb_bin has 2 heap files; delete 1st, but still keep 2nd
			Cleanup_file(itm);
			deleted.Add(itm);
		}
	}
	private void Cleanup_file(Merge2_heap_db itm) {
		itm.Conn().Rls_conn();
		Io_mgr.Instance.DeleteFil(itm.Url());
	} 
}
