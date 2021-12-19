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
package gplx.xowa.addons.bldrs.mass_parses.makes;
import gplx.libs.dlgs.Gfo_usr_dlg_;
import gplx.types.basics.utls.IntUtl;
import gplx.dbs.Db_rdr;
import gplx.types.basics.utls.BoolUtl;
import gplx.xowa.Xowe_wiki;
import gplx.xowa.addons.bldrs.mass_parses.dbs.Xomp_mgr_db;
import gplx.xowa.addons.bldrs.mass_parses.dbs.Xomp_wkr_db;
import gplx.xowa.htmls.hxtns.blobs.Hxtn_blob_tbl;
import gplx.xowa.htmls.hxtns.pages.Hxtn_page_mgr;
import gplx.xowa.htmls.hxtns.pages.Hxtn_page_tbl;
class Xomp_make_hxtn {
	public void Exec(Xowe_wiki wiki, Xomp_make_cmd_cfg cfg) {
		// create mgr
		Hxtn_page_mgr page_mgr = new Hxtn_page_mgr();
		page_mgr.Init_by_wiki(wiki, BoolUtl.Y);
		Hxtn_page_tbl page_tbl = page_mgr.Page_tbl();
		Hxtn_blob_tbl blob_tbl = page_mgr.Blob_tbl();
		page_mgr.Insert_bgn(true);

		// loop wkrs
		Xomp_mgr_db mgr_db = Xomp_mgr_db.New__load(wiki);
		int wkrs_len = mgr_db.Tbl__wkr().Select_count();
		for (int i = 0; i < wkrs_len; ++i) {
			// init count / wkr_db
			int count = 0;
			Xomp_wkr_db wkr_db = Xomp_wkr_db.New(mgr_db.Dir(), i);

			// insert page_tbl
			page_tbl.Conn().Env_db_attach("wkr_db", wkr_db.Url());
			page_tbl.Conn().Txn_bgn("hxtn_page");
			Db_rdr rdr = page_tbl.Conn().Stmt_sql("SELECT DISTINCT src.page_id, src.wkr_id, src.data_id FROM wkr_db.hxtn_page src LEFT JOIN hxtn_page trg ON src.page_id = trg.page_id AND src.wkr_id = trg.wkr_id AND src.data_id = trg.data_id WHERE trg.id IS NULL;").Exec_select__rls_auto();	// ANSI.Y
			try {
				while (rdr.Move_next()) {
					page_tbl.Insert_by_rdr(rdr);
					if (++count % 10000 == 0) {
						Gfo_usr_dlg_.Instance.Prog_many("", "", "hxtn.page.insert: db=~{0} count=~{1}", IntUtl.ToStrPadBgnSpace(i, 3), IntUtl.ToStrPadBgnSpace(count, 8));
						page_tbl.Conn().Txn_sav();
					}
				}
			} finally {rdr.Rls();}
			page_tbl.Conn().Txn_end();
			page_tbl.Conn().Env_db_detach("wkr_db");

			// insert blob tbl; note that dupes can exist across wkr_dbs (wkr_db 1 and wkr_db 2 both have Template:Abcd)
			count = 0;
			blob_tbl.Conn().Env_db_attach("wkr_db", wkr_db.Url());
			blob_tbl.Conn().Txn_bgn("hxtn_blob");
			rdr = blob_tbl.Conn().Stmt_sql("SELECT DISTINCT src.blob_tid, src.wiki_id, src.blob_id, src.zip_tid, src.blob_data FROM wkr_db.hxtn_blob src LEFT JOIN hxtn_blob trg ON src.wiki_id = trg.wiki_id AND src.blob_id = trg.blob_id AND src.blob_tid = trg.blob_tid WHERE trg.blob_id IS NULL;").Exec_select__rls_auto();	// ANSI.Y
			try {
				while (rdr.Move_next()) {
					blob_tbl.Insert_by_rdr(rdr);
					if (++count % 10000 == 0) {
						Gfo_usr_dlg_.Instance.Prog_many("", "", "hxtn.blob.insert: db=~{0} count=~{1}", IntUtl.ToStrPadBgnSpace(i, 3), IntUtl.ToStrPadBgnSpace(count, 8));
						blob_tbl.Conn().Txn_sav();
					}
				}
			} finally {rdr.Rls();}
			blob_tbl.Conn().Txn_end();
			blob_tbl.Conn().Env_db_detach("wkr_db");
		}

		// cleanup
		page_mgr.Insert_end(true);
	}
}
