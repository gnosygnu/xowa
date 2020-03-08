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
package gplx.xowa.htmls.hxtns.pages; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.hxtns.*;
import gplx.dbs.*;
import gplx.core.lists.*; import gplx.core.lists.hashs.*;
import gplx.xowa.htmls.hxtns.pages.*; import gplx.xowa.htmls.hxtns.blobs.*; import gplx.xowa.htmls.hxtns.wkrs.*; import gplx.xowa.htmls.hxtns.wikis.*;
import gplx.xowa.wikis.*;
public class Hxtn_page_mgr {
	private Hxtn_page_tbl page_tbl;
	private Hxtn_blob_tbl blob_tbl;
	private Hash_adp_bry blob_hash;
	private final    Hash_adp__int wkrs = new Hash_adp__int();
	private boolean dbs_missing = true;
	private Bry_bfr temp_bfr;
	public Hxtn_page_tbl Page_tbl() {return page_tbl;}
	public Hxtn_blob_tbl Blob_tbl() {return blob_tbl;}

	public void Init_by_xomp_wkr(Db_conn wkr_db_conn, byte zip_tid) {
		// init tbls and other members
		this.page_tbl = new Hxtn_page_tbl(wkr_db_conn);
		this.blob_tbl = new Hxtn_blob_tbl(wkr_db_conn, zip_tid);
		this.blob_hash = Hash_adp_bry.cs();
		this.temp_bfr = Bry_bfr_.New();

		// if tbl exists, xomp_resume has run; load known blobs to prevent dupes
		if (wkr_db_conn.Meta_tbl_exists(page_tbl.Tbl_name())) {
			blob_tbl.Select_to_regy(temp_bfr, blob_hash);
		}
		// else tbl doesn't exist, so create them
		else {
			page_tbl.Create_tbl();
			blob_tbl.Create_tbl();
		}
	}
	public void Init_by_wiki(Xow_wiki wiki, boolean is_merge) {
		Io_url core_db_url = Make_url(wiki, "-html.hxtn-core.xowa");
		if (!is_merge && !Io_mgr.Instance.ExistsFil(core_db_url)) return;
		dbs_missing = false;
		Db_conn page_conn = Db_conn_bldr.Instance.Get_or_new(core_db_url).Conn();
		this.page_tbl = new Hxtn_page_tbl(page_conn);

		Io_url blob_db_url = Make_url(wiki, "-html.hxtn-blob.xowa");
		this.blob_tbl = new Hxtn_blob_tbl(Db_conn_bldr.Instance.Get_or_new(blob_db_url).Conn(), gplx.core.ios.streams.Io_stream_tid_.Tid__raw);

		if (is_merge) {
			page_tbl.Create_tbl();
			blob_tbl.Create_tbl();

			Hxtn_wkr_mgr wkr_mgr = new Hxtn_wkr_mgr();
			wkr_mgr.Init_by_xomp_merge(page_conn);

			Hxtn_wiki_mgr wiki_mgr = new Hxtn_wiki_mgr();
			wiki_mgr.Init_by_xomp_merge(page_conn, wiki.Domain_str());
		}
	}
	public void Insert_bgn(boolean is_merge) {
		page_tbl.Stmt_bgn();
		blob_tbl.Stmt_bgn();
	}
	public void Insert_end(boolean is_merge) {
		page_tbl.Stmt_end();
		blob_tbl.Stmt_end();
	}
	public void Page_tbl__insert(int page_id, int wkr_id, int data_id) {
		page_tbl.Insert_exec(page_id, wkr_id, data_id);
	}
	public void Blob_tbl__insert(int blob_tid, int wiki_id, int blob_id, byte[] blob_text) {
		byte[] key = Hxtn_blob_tbl.Make_key(temp_bfr, blob_tid, wiki_id, blob_id);
		if (!blob_hash.Has(key)) {// multiple pages can refer to same template; only insert if not seen
			blob_hash.Add_as_key_and_val(key);
			blob_tbl.Insert_exec(blob_tid, wiki_id, blob_id, blob_text);
		}
	}
	public void Reg_wkr(Hxtn_page_wkr wkr) {
		wkrs.Add(wkr.Id(), wkr);
	}
	public void Load_by_page(Xoh_page hpg, Xoa_ttl ttl) {
		if (dbs_missing) return; // PERF:do not call SELECT if dbs don't exist
		List_adp list = page_tbl.Select_by_page(hpg.Page_id());
		int len = list.Len();
		for (int i = 0; i < len; i++) {
			Hxtn_page_itm itm = (Hxtn_page_itm)list.Get_at(i);
			Hxtn_page_wkr wkr = (Hxtn_page_wkr)wkrs.Get_by_or_null(itm.Wkr_id());
			if (wkr == null) { // ignore unknown wkrs so other devs can add new xtns; ISSUE#:634; DATE:2020-03-08
				Gfo_usr_dlg_.Instance.Warn_many("", "", "hxtn.unknown wkr: page_id=~{0} wkr_id=~{1}", itm.Page_id(), itm.Wkr_id());
				continue;
			}
			wkr.Load_by_page(hpg, ttl, itm.Data_id());
		}
	}
	private static Io_url Make_url(Xow_wiki wiki, String file_name) {return wiki.Fsys_mgr().Root_dir().GenSubFil(wiki.Domain_str() + file_name);}
	public static final int Id__template_styles = 0;
}
