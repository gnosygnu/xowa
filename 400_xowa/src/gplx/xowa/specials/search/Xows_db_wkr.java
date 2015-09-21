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
import gplx.core.primitives.*; import gplx.dbs.*;
import gplx.xowa.nss.*;
import gplx.xowa.wikis.data.*; import gplx.xowa.wikis.data.tbls.*; import gplx.xowa.langs.cases.*;
import gplx.gfui.*;
class Xows_db_wkr {
	public void Search(Xows_ui_cmd cmd, Xows_ui_qry qry, Xows_ui_rslt rslt, Xows_db_cache cache, Xow_wiki wiki) {
		// assert matcher
		Xowd_db_file search_db = wiki.Data__core_mgr().Db__search();
		Xoa_app_.Usr_dlg().Prog_many("", "", "search started (please wait)");
		Xows_db_matcher matcher = cache.Matcher();
		if (matcher == null) {
			cache.Init_by_db
			( cmd
			, wiki.Lang().Case_mgr().Case_build_lower(qry.Search_raw())	// lower-case search
			, search_db.Tbl__search_word()
			);
			matcher = cache.Matcher();
		}
		// init
		int rslts_wanted = qry.Itms_end() - qry.Itms_bgn();
		Xowd_db_file core_db = wiki.Data__core_mgr().Db__core();
		Xowd_page_tbl page_tbl = core_db.Tbl__page();
		Xowd_search_link_tbl link_tbl = search_db.Tbl__search_link();
		Xows_db_word[] word_ary = cache.Words(); int word_ary_len = word_ary.length;
		// read pages for each word from db
		Db_attach_rdr attach_rdr = new Db_attach_rdr(search_db.Conn(), "page_db", core_db.Url());
		attach_rdr.Attach();
		int total_found = 0;
		try {
			while (true) {
				boolean found_none = true;
				for (int i = 0; i < word_ary_len; ++i) {	// loop each word to get rslts_wanted
					if (cmd.Canceled()) return;
					Xows_db_word word = word_ary[i];
					if (word.Rslts_done()) continue;		// last db_search for word returned 0 results; don't search again;
					int offset = word.Rslts_offset();
					Xoa_app_.Usr_dlg().Prog_many("", "", "searching; wiki=~{0} total=~{1} offset=~{2} index=~{3} word=~{4}", wiki.Domain_str(), word_ary_len, offset, i, word.Text());
					String sql = String_.Format(Search_sql, link_tbl.Tbl_name(), link_tbl.Fld_page_id(), link_tbl.Fld_word_id(), word.Id(), "page_len", "DESC", Int_.Max_value, offset); // need to return enough results to fill qry.Page_len() as many results may be discarded below; DATE:2015-04-24
					int rslts_found = Search_pages(cmd, qry, rslt, cache, wiki, page_tbl, attach_rdr, sql, word, matcher, rslts_wanted);
					total_found += rslts_found;
					if		(rslts_found == -1)		return;				// canceled
					else if (rslts_found > 0)		found_none = false;	// NOTE: do not reverse and do rslts_found == 0; want to check if any word returns results;
				}
				if (found_none)					{cache.Done_y_(); break;}
				if (total_found >= rslts_wanted) break;
			}
			cache.Itms_end_(qry.Itms_end());
			cache.Sort();
		}	finally {attach_rdr.Detach();}
	}
	private int Search_pages(Xows_ui_cmd cmd, Xows_ui_qry qry, Xows_ui_rslt rslt, Xows_db_cache cache, Xow_wiki wiki, Xowd_page_tbl page_tbl, Db_attach_rdr attach_rdr, String sql, Xows_db_word word, Xows_db_matcher matcher, int rslts_wanted) {
		int rslts_found = 0;
		Xow_ns_mgr ns_mgr = wiki.Ns_mgr(); Xol_case_mgr case_mgr = wiki.Lang().Case_mgr();
		Db_rdr rdr = attach_rdr.Exec_as_rdr(sql);
		try {
			while (rdr.Move_next()) {
				if (cmd.Canceled()) return -1;
				word.Rslts_offset_add_1();
				int page_ns = rdr.Read_int(page_tbl.Fld_page_ns());
				if (!qry.Ns_mgr().Has(page_ns)) continue;						// ignore: ns doesn't match
				byte[] page_ttl = rdr.Read_bry_by_str(page_tbl.Fld_page_title());
				// Io_mgr.I.AppendFilStr("C:\\temp.txt", String_.new_u8(word.Text()) + "|" + Int_.Xto_str(page_ns) + "|" + String_.new_u8(page_ttl) + "\n");
				byte[] page_ttl_lc = case_mgr.Case_build_lower(Xoa_ttl.Replace_unders(page_ttl));
				byte[][] page_ttl_words = Bry_split_.Split(page_ttl_lc, Byte_ascii.Space, Bool_.Y);
				if (!matcher.Matches(page_ttl_lc, page_ttl_words)) continue;	// ignore: ttl doesn't match matcher
				int page_id = rdr.Read_int(page_tbl.Fld_page_id());
				int page_len = rdr.Read_int(page_tbl.Fld_page_len());
				Xow_ns ns = ns_mgr.Ids_get_or_null(page_ns);
				byte[] page_ttl_w_ns = ns.Gen_ttl(page_ttl);
				if (cache.Has(page_ttl_w_ns)) continue;							// ignore: page already added by another word; EX: "A B"; word is "B", but "A B" already added by "A"
				Xoa_ttl ttl = wiki.Ttl_parse(page_ttl_w_ns);
				Xows_db_row row = new Xows_db_row(wiki.Domain_bry(), ttl, page_id, page_len);
				cmd.Add_rslt(row);
				if (++rslts_found == rslts_wanted) break;						// stop: found enough results; DATE:2015-04-24
			}
		}	finally {rdr.Rls();}
		if (rslts_found == 0) word.Rslts_done_y_(); // read through entire rdr and nothing found; mark word done
		return rslts_found;
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
