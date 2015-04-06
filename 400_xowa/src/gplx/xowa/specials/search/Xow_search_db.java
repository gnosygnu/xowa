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
import gplx.dbs.*; import gplx.xowa.wikis.data.*; import gplx.xowa.wikis.data.tbls.*;
class Xow_search_db {
	public void Search(Cancelable cxl, Xow_wiki wiki, Xow_search_qry qry, Xow_search_cache cache) {
//			int offset = qry.Rslts_bgn();
//			int limit = qry.Rslts_len();
		int rslts_end = qry.Rslts_end();
		Xow_search_matcher crt = null;
		Xow_search_word[] words_ary = qry.Word_ary(); int words_len = words_ary.length;
		while (true) {
			boolean found_none = Bool_.Y;
			for (int i = 0; i < words_len; ++i) {
				Xow_search_word word = qry.Word_ary()[i];
				boolean read = Search_db(cxl, wiki, cache, wiki.Data_mgr__core_mgr().Db__core(), wiki.Data_mgr__core_mgr().Db__search(), word, crt);
				if (read)	found_none = Bool_.N;
			}
			if (found_none)					{cache.Done_y_(); break;}
			if (cache.Count() > rslts_end)	{break;}
		}
	}
	private boolean Search_db(Cancelable cxl, Xow_wiki wiki, Xow_search_cache cache, Xowd_db_file core_db, Xowd_db_file search_db, Xow_search_word word, Xow_search_matcher crt) {
		if (word.Done()) return false;			// last db_search for word returned 0 results; don't search again;
		// get word_id
		Xowd_search_word_tbl word_tbl = search_db.Tbl__search_word();
		int word_id = word.Id();
		if (word_id == Xowd_search_word_tbl.Id_null) {
			word_id = word_tbl.Select_id(word.Text());
			if (word_id == Xowd_search_word_tbl.Id_null) {
				word.Done_y_();
				Xoa_app_.Usr_dlg().Warn_many("", "", "search:word_id missing; word=~{0}", word);
				return Bool_.N;
			}
		}
		// get search results
//			Xowd_search_link_tbl link_tbl = search_db.Tbl__search_link();
		Db_attach_rdr attach_rdr = new Db_attach_rdr(search_db.Conn(), "page_db", core_db.Url());
		String sql = String_.Format(Search_sql);
		Db_rdr rdr = attach_rdr.Exec_as_rdr(sql);
		Xowd_page_tbl page_tbl = core_db.Tbl__page();
		Xow_ns_mgr ns_mgr = wiki.Ns_mgr();
		boolean read = false;
		while (rdr.Move_next()) {
			read = true;
			byte[] page_ttl = rdr.Read_bry(page_tbl.Fld_page_title());
			if (!crt.Matches(page_ttl)) continue;	// ttl doesn't match crt; ignore;
			int page_id = rdr.Read_int(page_tbl.Fld_page_id());
			int page_ns = rdr.Read_int(page_tbl.Fld_page_ns());
			int page_len = rdr.Read_int(page_tbl.Fld_page_len());
			Xow_ns ns = ns_mgr.Ids_get_or_null(page_ns);
			byte[] page_key = ns.Gen_ttl(page_ttl);
			if (cache.Has(page_key)) continue;		// page already added by another word; EX: "A B"; word is "B", but "A B" already added by "A"
			cache.Add(new Xow_search_rslt(page_key, page_id, page_ns, page_ttl, page_len));
		}
		attach_rdr.Rls();
		if (!read) word.Done_y_();
		return read;
	}
	private static final String Search_sql = String_.Concat_lines_nl_skip_last
	( "SELECT cp.page_id"
	, ",      cp.page_namespace"
	, ",      cp.page_title"
	, ",      cp.page_len"
	, "FROM   {0} sp"
	, "	   JOIN <attach_db>page cp ON cp.{1} = sp.page_id "
	, "WHERE  sp.{2} = {3}"
	, "ORDER BY {4} {5}"
	, "LIMIT {6}"
	, "OFFSET {7};"
	);
}