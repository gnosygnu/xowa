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
package gplx.xowa.addons.wikis.searchs.fulltexts.indexers; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.searchs.*; import gplx.xowa.addons.wikis.searchs.fulltexts.*;
import gplx.dbs.*;
import gplx.xowa.htmls.*;
import gplx.xowa.wikis.data.*;
import gplx.xowa.htmls.core.dbs.*;
import gplx.xowa.addons.wikis.searchs.fulltexts.indexers.*;
class Xosearch_indexer_mgr {
	public void Exec(Xowe_wiki wiki) {
		Xow_db_file core_db = wiki.Data__core_mgr().Db__core();
		gplx.xowa.wikis.data.tbls.Xowd_page_tbl page_tbl = core_db.Tbl__page();

		Xoh_page hpg = new Xoh_page();

		Xosearch_indexer indexer = new Xosearch_indexer();
		indexer.Init(wiki);

		Db_conn conn = page_tbl.Conn();
		Db_rdr rdr = conn.Exec_rdr("SELECT page_id, page_score, page_namespace, page_title, page_html_db_id FROM page WHERE page_namespace = 0;");
		int count = 0;
		while (rdr.Move_next()) {
			int page_namespace = rdr.Read_int("page_namespace");
			byte[] page_ttl_bry = rdr.Read_bry_by_str("page_title");
			int page_id = rdr.Read_int("page_id");
			int page_score = rdr.Read_int("page_score");
			int html_db_id = rdr.Read_int("page_html_db_id");

			// ignore redirects
			if (html_db_id == -1) continue;
			try {
				// load page
				Xoa_ttl page_ttl = wiki.Ttl_parse(page_namespace, page_ttl_bry);
				if (page_ttl == null)
					continue;
				Xow_db_file html_db = html_db_id == -1 ? core_db : wiki.Data__core_mgr().Dbs__get_by_id_or_fail(html_db_id);
				hpg.Ctor_by_hview(wiki, wiki.Utl__url_parser().Parse(page_ttl.Full_db()), page_ttl, page_id);
				if (!html_db.Tbl__html().Select_by_page(hpg))
					continue;
				byte[] html_text = wiki.Html__hdump_mgr().Load_mgr().Parse(hpg, hpg.Db().Html().Zip_tid(), hpg.Db().Html().Hzip_tid(), hpg.Db().Html().Html_bry());

				indexer.Index(page_id, page_score, page_ttl.Page_txt(), html_text);
				if ((++count % 10000) == 0)
					Gfo_usr_dlg_.Instance.Prog_many("", "", "indexing page: ~{0}", count);
			} catch (Exception e) {
				Gfo_usr_dlg_.Instance.Warn_many("", "", "err: ~{0}", Err_.Message_gplx_log(e));
			}
		}

		indexer.Term();
	}
}
