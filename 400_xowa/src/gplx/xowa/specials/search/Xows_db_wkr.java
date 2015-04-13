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
package gplx.xowa.specials.search; import gplx.*; import gplx.xowa.*; import gplx.xowa.specials.*;
import gplx.core.primitives.*;
import gplx.dbs.*; import gplx.xowa.wikis.data.*; import gplx.xowa.wikis.data.tbls.*;
import gplx.gfui.*;
class Xows_db_wkr {
	private int total_found;
	public void Search(Xows_ui_cmd cmd, Xows_ui_qry qry, Xows_ui_rslt rslt, Xows_db_cache cache, Xow_wiki wiki) {
		// init
		int itms_wanted = qry.Itms_end() - qry.Itms_bgn(), limit = qry.Page_len();
		Xowd_db_file core_db = wiki.Data_mgr__core_mgr().Db__core();
		Xowd_db_file search_db = wiki.Data_mgr__core_mgr().Db__search();
		// assert matcher
		if (cache.Matcher() == null) {
			cache.Init_by_db
			( cmd
			, wiki.Lang().Case_mgr().Case_build_lower(qry.Search_raw())	// lower-case search
			, wiki.Data_mgr__core_mgr().Db__search().Tbl__search_word()
			);
		}
		// load pages for each word
		Xows_db_matcher matcher = cache.Matcher();
		Xows_db_word[] word_ary = cache.Words();
		int word_ary_len = word_ary.length;
		total_found = 0;
		while (true) {
			if (cmd.Canceled()) break;
			boolean found_none = true;
			for (int i = 0; i < word_ary_len; ++i) {
				if (cmd.Canceled()) break;;
				Xows_db_word word = word_ary[i];
				int read = Search_pages(cmd, qry, rslt, cache, wiki, core_db, search_db, word, matcher, limit, word.Rslts_offset(), i, word_ary_len);
				if (read > 0)	found_none = false;
			}
			if (found_none)					{cache.Done_y_(); break;}
			if (total_found >= itms_wanted) break;
		}
		cache.Sort();
	}
	private int Search_pages(Xows_ui_cmd cmd, Xows_ui_qry qry, Xows_ui_rslt rslt, Xows_db_cache cache, Xow_wiki wiki, Xowd_db_file core_db, Xowd_db_file search_db, Xows_db_word word, Xows_db_matcher matcher, int limit, int offset, int i, int word_ary_len) {
		if (word.Rslts_done()) return 0;		// last db_search for word returned 0 results; don't search again;
		// get search results
		synchronized (cmd) {
			if (cmd.Canceled()) return 0;
		}
		Xoa_app_.Usr_dlg().Prog_many("", "", "searching; total=~{1} offset=~{2} index=~{0} word=~{3}", i, word_ary_len, offset, word.Text());
		Xowd_search_link_tbl link_tbl = search_db.Tbl__search_link();
		Db_attach_rdr attach_rdr = new Db_attach_rdr(search_db.Conn(), "page_db", core_db.Url());
		String sql = String_.Format(Search_sql, link_tbl.Tbl_name(), link_tbl.Fld_page_id(), link_tbl.Fld_word_id(), word.Id(), "page_len", "DESC", limit, offset);
		int total_read = 0;
		Db_rdr rdr = attach_rdr.Exec_as_rdr(sql);
		try {
			Xowd_page_tbl page_tbl = core_db.Tbl__page();
			Xow_ns_mgr ns_mgr = wiki.Ns_mgr();
			while (rdr.Move_next()) {
				if (cmd.Canceled()) break;
				++total_read;
				word.Rslts_offset_add_1();
				int page_ns = rdr.Read_int(page_tbl.Fld_page_ns());
				if (!qry.Ns_mgr().Has(page_ns)) continue;
				byte[] page_ttl = rdr.Read_bry_by_str(page_tbl.Fld_page_title());
				byte[] page_ttl_lc = wiki.Lang().Case_mgr().Case_build_lower(Xoa_ttl.Replace_unders(page_ttl));
				byte[][] page_ttl_words = Bry_.Split(page_ttl_lc, Byte_ascii.Space, Bool_.Y);
				if (!matcher.Matches(page_ttl_lc, page_ttl_words)) continue;// ttl doesn't match matcher; ignore;
				int page_id = rdr.Read_int(page_tbl.Fld_page_id());
				int page_len = rdr.Read_int(page_tbl.Fld_page_len());
				Xow_ns ns = ns_mgr.Ids_get_or_null(page_ns);
				byte[] page_ttl_w_ns = ns.Gen_ttl(page_ttl);
				if (cache.Has(page_ttl_w_ns)) continue;	// page already added by another word; EX: "A B"; word is "B", but "A B" already added by "A"
				Xows_db_row row = new Xows_db_row(wiki.Domain_bry(), page_id, page_ns, page_len, page_ttl_w_ns, page_ttl);
				cmd.Add_rslt(row);
				++total_found;
			}
		} finally {rdr.Rls(); attach_rdr.Rls();}
		if (total_read == 0) word.Rslts_done_y_();
		return total_read;
	}
	private static final String Search_sql = String_.Concat_lines_nl_skip_last
	( "SELECT cp.page_id"
	, ",      cp.page_namespace"
	, ",      cp.page_title"
	, ",      cp.page_len"
	, "FROM   {0} sp"
	, "	   JOIN <attach_db>page cp ON cp.page_id = sp.{1} "
	, "WHERE  sp.{2} = {3}"
	, "ORDER BY {4} {5}"
	, "LIMIT {6}"
	, "OFFSET {7};"
	);
}