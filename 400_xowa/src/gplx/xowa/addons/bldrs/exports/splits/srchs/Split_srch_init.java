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
package gplx.xowa.addons.bldrs.exports.splits.srchs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.exports.*; import gplx.xowa.addons.bldrs.exports.splits.*;
import gplx.dbs.*; import gplx.xowa.addons.wikis.searchs.dbs.*;
import gplx.xowa.addons.bldrs.exports.splits.mgrs.*;
class Split_srch_init {
	public void Init(Split_ctx ctx, Xow_wiki wiki, Db_conn wkr_conn, Srch_db_mgr srch_db_mgr) {
		// create search_word w/ page attributes
		if (!(ctx.Cfg().Force_rebuild() || !wkr_conn.Meta_tbl_exists("split_search_word"))) return;
		Gfo_log_.Instance.Prog("creating split_search_word");
		wkr_conn.Meta_tbl_remake(Dbmeta_tbl_itm.New("split_search_word"
		, Dbmeta_fld_itm.new_int("word_id")
		, Dbmeta_fld_itm.new_str("word_text", 255)
		, Dbmeta_fld_itm.new_int("link_count")
		, Dbmeta_fld_itm.new_int("link_count_score")
		, Dbmeta_fld_itm.new_int("link_score_min")
		, Dbmeta_fld_itm.new_int("link_score_max")
		, Dbmeta_fld_itm.new_int("page_uid")
		, Dbmeta_fld_itm.new_int("page_ns")
		, Dbmeta_fld_itm.new_int("page_id")
		, Dbmeta_fld_itm.new_int("page_score")
		));

		// insert search_word w/ Min(pr.page_uid)
		Db_attach_mgr attach_mgr = new Db_attach_mgr(wkr_conn);
		Db_conn word_conn = srch_db_mgr.Tbl__word().conn;
		int len = srch_db_mgr.Tbl__link__len();
		for (int i = 0; i < len; ++i) {
			Db_conn link_conn = srch_db_mgr.Tbl__link__get_at(i).conn;
			attach_mgr.Conn_links_(new Db_attach_itm("word_db", word_conn), new Db_attach_itm("link_db", link_conn));
			attach_mgr.Exec_sql(String_.Concat_lines_nl	// ANSI.Y
			( "INSERT INTO split_search_word (word_id, word_text, link_count, link_count_score, link_score_min, link_score_max, page_uid, page_ns, page_id, page_score)"
			, "SELECT  sw.word_id, sw.word_text, sw.link_count, sw.link_count_score, sw.link_score_min, sw.link_score_max, Min(pr.page_uid), -1, -1, -1"
			, "FROM    <word_db>search_word sw"
			, "        JOIN <link_db>search_link sl ON sw.word_id = sl.word_id"
			, "                JOIN page_regy pr ON sl.page_id = pr.page_id"
			, "        LEFT JOIN split_search_word ssw ON ssw.word_id = sw.word_id"
			, "WHERE   ssw.word_id IS NULL"
			, "GROUP BY sw.word_id, sw.word_text, sw.link_count, sw.link_count_score, sw.link_score_min, sw.link_score_max"
			));
		}
		wkr_conn.Meta_idx_create("split_search_word", "page_id", "page_id");

		Split_mgr_init.Update_page_cols(wkr_conn, "split_search_word");
	}
}
