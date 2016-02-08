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
import gplx.core.primitives.*; import gplx.core.brys.fmtrs.*;
import gplx.dbs.*; import gplx.xowa.wikis.data.*; import gplx.xowa.wikis.data.tbls.*;
import gplx.xowa.wikis.nss.*; import gplx.xowa.langs.cases.*;
import gplx.xowa.specials.search.crts.*;
public class Srch_db_wkr {
	private Xol_case_mgr drd_case_mgr;
	public Srch_rslt_itm[] Search_by_drd(Cancelable cxl, Srch_rslt_lnr rslt_lnr, Xow_wiki wiki, byte[] search, int search_results_max) {
		Xows_ns_mgr ns_mgr = new Xows_ns_mgr(); ns_mgr.Add_main_if_empty();
		Srch_qry qry = new Srch_qry(search, 0, search_results_max, ns_mgr, Bool_.Y, new gplx.xowa.wikis.domains.Xow_domain_itm[] {wiki.Domain_itm()});
		Srch_rslt_hash cache = new Srch_rslt_hash(); cache.Init_by_db(cxl, search, wiki.Data__core_mgr().Db__search().Tbl__search_word());
		if (drd_case_mgr == null) drd_case_mgr = Xol_case_mgr_.U8();
		Search(cxl, rslt_lnr, cache, wiki, drd_case_mgr, qry);
		int len = cache.Count();
		Srch_rslt_itm[] rv = new Srch_rslt_itm[len];
		for (int i = 0; i < len; ++i)
			rv[i] = cache.Get_at(i);
		return rv;
	}
	@gplx.Internal protected void Search(Cancelable cxl, Srch_rslt_lnr rslt_lnr, Srch_rslt_hash cache, Xow_wiki wiki, Xol_case_mgr case_mgr, Srch_qry qry) {
		// get search_words
		Xowd_db_file search_db = wiki.Data__core_mgr().Db__search();
		Xoa_app_.Usr_dlg().Prog_many("", "", "search started (please wait)");
		Srch_crt_node ttl_matcher = cache.Ttl_matcher();
		if (ttl_matcher == null) {
			cache.Init_by_db(cxl, case_mgr.Case_build_lower(qry.search_raw), search_db.Tbl__search_word());	// lower-case search
			ttl_matcher = cache.Ttl_matcher();
		}
		// do some init
		int rslts_wanted = qry.itms_end - qry.itms_bgn;
		Xowd_db_file core_db = wiki.Data__core_mgr().Db__core();
		Xowd_page_tbl page_tbl = core_db.Tbl__page();
		Xowd_search_link_tbl link_tbl = search_db.Tbl__search_link();
		Srch_word_row[] word_ary = cache.Words(); int word_ary_len = word_ary.length;
		// read pages for each word from db
		Db_attach_rdr attach_rdr = new Db_attach_rdr(search_db.Conn(), "page_db", core_db.Url());
		attach_rdr.Attach();
		int total_found = 0;
		try {
			while (true) {
				boolean found_none = true;
				for (int i = 0; i < word_ary_len; ++i) {	// loop each word to get rslts_wanted
					if (cxl.Canceled()) return;
					Srch_word_row word = word_ary[i];
					if (word.Rslts_done()) continue;		// last db_search for word returned 0 results; don't search again;
					int offset = word.Rslts_offset();
					Xoa_app_.Usr_dlg().Prog_many("", "", "searching; wiki=~{0} total=~{1} offset=~{2} index=~{3} word=~{4}", wiki.Domain_str(), word_ary_len, offset, i, word.text);
					String sql = search_fmt.Bld_many_to_str_auto_bfr(link_tbl.Tbl_name(), link_tbl.Fld_page_id(), link_tbl.Fld_word_id(), word.id, offset); // need to return enough results to fill qry.page_len as many results may be discarded below; DATE:2015-04-24
					int rslts_found = Search_pages(cxl, rslt_lnr, cache, wiki, case_mgr, qry, page_tbl, attach_rdr, sql, ttl_matcher, word, rslts_wanted);
					total_found += rslts_found;
					if		(rslts_found == -1)		return;				// canceled
					else if (rslts_found > 0)		found_none = false;	// NOTE: do not reverse and do rslts_found == 0; want to check if any word returns results;
				}
				if (found_none)					{cache.Done_y_(); break;}
				if (total_found >= rslts_wanted) break;
			}
			cache.Itms_end_(qry.itms_end);
			cache.Sort();
		}
		catch (Exception e) {Gfo_usr_dlg_.Instance.Warn_many("", "", "error during search; wiki=~{0} total=~{1} err=~{2}", wiki.Domain_str(), word_ary_len, Err_.Message_gplx_log(e));}
		finally {attach_rdr.Detach();}
	}
	private int Search_pages(Cancelable cxl, Srch_rslt_lnr rslt_lnr, Srch_rslt_hash cache, Xow_wiki wiki, Xol_case_mgr case_mgr, Srch_qry qry
		, Xowd_page_tbl page_tbl, Db_attach_rdr attach_rdr, String sql, Srch_crt_node ttl_matcher, Srch_word_row word, int rslts_wanted) {
		int rslts_found = 0;
		Xow_ns_mgr ns_mgr = wiki.Ns_mgr();
		Db_rdr rdr = attach_rdr.Exec_as_rdr(sql);
		try {
			while (rdr.Move_next()) {
				if (cxl.Canceled()) return -1;
				word.Rslts_offset_add_1();
				int page_ns = rdr.Read_int(page_tbl.Fld_page_ns());
				if (!qry.ns_mgr.Has(page_ns)) continue;							// ignore: ns doesn't match
				byte[] page_ttl = rdr.Read_bry_by_str(page_tbl.Fld_page_title());
				// Io_mgr.Instance.AppendFilStr("C:\\temp.txt", String_.new_u8(word.Text()) + "|" + Int_.To_str(page_ns) + "|" + String_.new_u8(page_ttl) + "\n");
				if (!ttl_matcher.Matches(case_mgr, page_ttl)) continue;			// ignore: ttl doesn't match ttl_matcher
				Xow_ns ns = ns_mgr.Ids_get_or_null(page_ns);
				byte[] page_ttl_w_ns = ns.Gen_ttl(page_ttl);
				if (cache.Has(page_ttl_w_ns)) continue;							// ignore: page already added by another word; EX: "A B"; word is "B", but "A B" already added by "A"
				Srch_rslt_itm row = new Srch_rslt_itm(wiki.Domain_bry(), wiki.Ttl_parse(page_ttl_w_ns), rdr.Read_int(page_tbl.Fld_page_id()), rdr.Read_int(page_tbl.Fld_page_len()));
				rslt_lnr.Notify_rslt_found(row);
				if (++rslts_found == rslts_wanted) break;						// stop: found enough results; DATE:2015-04-24
			}
		}	finally {rdr.Rls();}
		if (rslts_found == 0) word.Rslts_done_y_(); // read through entire rdr and nothing found; mark word done
		return rslts_found;
	}
	public Db_stmt New_qry(Db_conn conn, String page_db_alias, Xowd_page_tbl page, Xowd_search_link_tbl link, int offset) {
		Db_qry qry = Db_qry_.select_()
			.Cols_w_tbl_("p", page.Fld_page_id(), page.Fld_page_ns(), page.Fld_page_title(), page.Fld_page_len())
			.From_(page_db_alias, page.Tbl_name(), "p")
			.Join_(link.Tbl_name(), "l", Db_qry_.New_join__join(link.Fld_page_id(), page.Fld_page_id(), "p"))
			.Where_(Db_crt_.New_eq("l", link.Fld_word_id(), -1))
			.Order_("p", page.Fld_page_len(), Bool_.N)
			.Offset_(offset)
			;
//			qry_mgr.Add_ns(qry, page);	// qry.Where_and_(page.Fld_page_ns(), -1)
		return conn.Stmt_new(qry);
	}
	private static final Bry_fmt search_fmt = Bry_fmt.New
	( String_.Concat_lines_nl_skip_last
	( "SELECT p.page_id"
	, ",      p.page_namespace"
	, ",      p.page_title"
	, ",      p.page_len"
	, "FROM   <attach_db>page p"
	, "	   JOIN ~{tbl_link} l ON p.page_id = l.~{col_link_page_id} "
	, "WHERE  l.~{col_link_word_id} = ~{word_id}"
	, "ORDER BY p.page_len DESC"
	, "LIMIT -1"	// NOTE:LIMIT needed when OFFSET is specified; -1 LIMIT means infinite; "If the LIMIT expression evaluates to a negative value, then there is no upper bound on the number of rows returned"; http://sqlite.org/lang_select.html
	, "OFFSET ~{offset};"
	), "tbl_link", "col_link_page_id", "col_link_word_id", "word_id", "offset");
}
