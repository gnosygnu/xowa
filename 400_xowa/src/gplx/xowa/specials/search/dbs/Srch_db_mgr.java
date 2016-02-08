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
//namespace gplx.xowa.specials.search.dbs {
//	using gplx.dbs; using gplx.xowa.wikis.data.tbls;
//	using gplx.xowa.langs.cases; using gplx.xowa.wikis.nss;	
//	// workset_count
//	// workset_score_min
//	class Srch_db_mgr {
//		private Srch_word_tbl srch_word_tbl = new Srch_word_tbl(null, null);
//		private Srch_page_tbl srch_page_tbl = new Srch_page_tbl();
//		private Page_tbl page_tbl = new Page_tbl();
//		public void Search(Srch_request req) {
//			List_adp results = req.results;
//			int curr = results.Count();
//			int need = curr + req.req__count;
//			while (curr < need) {
//				// foreach (word)
//				srch_word_tbl.Get(req, null);	// read words
//				srch_page_tbl.Get(req);			// get pages for every words
//				// add to potential results; skip if failed and / not (but can't do quote)
//				page_tbl.Get(req);				// get actual page and check for ns; quote; redirect
//				// repeat until matched
//				need = results.Count();
//			}
//		}
//		// get all pages in word_id set sorted by page_search_score
//		// stop if
//		// - no more pages; normal case
//		// - page_score < workset_score_min; case with many pages (1000 pages)
////		private int Get_word_id_pages
////			( Cancelable cancelable, Xow_wiki wiki, Xol_case_mgr case_mgr
////			, Srch_request req, Xows_ui_cmd cmd, Srch_qry qry, Srch_rslt_hash cache
////			, Db_attach_rdr attach_rdr, String sql, Xowd_page_tbl page_tbl
////			, Srch_word_row word, Srch_crt_node matcher, int rslts_wanted, int workset_score_min) {
////			int rslts_found = 0;
////			Xow_ns_mgr ns_mgr = wiki.Ns_mgr();
////			Db_rdr rdr = attach_rdr.Exec_as_rdr(sql);
////			try {
////				while (rdr.Move_next()) {
////					if (cancelable.Canceled()) return -1;
////					word.Rslts_offset_add_1();
////					int page_ns = rdr.Read_int(page_tbl.Fld_page_ns());
////					if (!qry.Ns_mgr().Has(page_ns)) continue;						// ignore: ns doesn't match
////					byte[] page_ttl = rdr.Read_bry_by_str(page_tbl.Fld_page_title());
////					// Io_mgr.Instance.AppendFilStr("C:\\temp.txt", String_.new_u8(word.Text()) + "|" + Int_.To_str(page_ns) + "|" + String_.new_u8(page_ttl) + "\n");
////					byte[] page_ttl_lc = case_mgr.Case_build_lower(Xoa_ttl.Replace_unders(page_ttl));
////					byte[][] page_ttl_words = Bry_split_.Split(page_ttl_lc, Byte_ascii.Space, Bool_.Y);
////					if (!matcher.Matches(page_ttl_lc, page_ttl_words)) continue;	// ignore: ttl doesn't match matcher
////					int page_id = rdr.Read_int(page_tbl.Fld_page_id());
////					int page_len = rdr.Read_int(page_tbl.Fld_page_len());
////					int page_link_score = 0; //rdr.Read_int(page_tbl.Fld_page_link_score());
////					Xow_ns ns = ns_mgr.Ids_get_or_null(page_ns);
////					byte[] page_ttl_w_ns = ns.Gen_ttl(page_ttl);
////					if (cache.Has(page_ttl_w_ns)) continue;							// ignore: page already added by another word; EX: "A B"; word is "B", but "A B" already added by "A"
////					Xoa_ttl ttl = wiki.Ttl_parse(page_ttl_w_ns);
////					Srch_rslt_itm row = new Srch_rslt_itm(wiki.Domain_bry(), ttl, page_id, page_len);
////					cmd.Add_rslt(row);
////					if (++rslts_found == rslts_wanted) break;						// stop: found enough results; DATE:2015-04-24
////				}
////			}	finally {rdr.Rls();}
////			if (rslts_found == 0) word.Rslts_done_y_(); // read through entire rdr and nothing found; mark word done
////			return rslts_found;
////		}
//	}
//}
