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
//	// a
//	// a b
//	// a*
//	class Srch_word_tbl {
//		private final Db_conn conn;
//		private final Xowd_search_word_tbl tbl;
//		public Srch_word_tbl(Db_conn conn, Xowd_search_word_tbl tbl) {this.conn = conn; this.tbl = tbl;}
//		public void Get(Srch_request req, Srch_cache_word cache_word) {
//			// int req_count = req.req__count;
//			// conn.Stmt_select_order(tbl.Tbl_name(), String_.Ary(tbl.Fld_id(), tbl.Fld_w()));
//			// SELECT word_id, word_text FROM search_word WHERE word_text LIKE "~{word}%" AND word_text > '' ORDER BY word_page_score DESC OFFSET 0;
//			Db_qry qry = Db_qry_.select_()
//				.From_(tbl.Tbl_name())
//				.Cols_(tbl.Fld_id(), tbl.Fld_text(), tbl.Fld_page_count(), tbl.Fld_page_score_max())
//				.Where_(Db_crt_.New_like(tbl.Fld_text(), ""))
//				.OrderBy_(tbl.Fld_page_score_max(), false)
//				// .Offset_();
//				;
//			Db_stmt stmt = conn.Stmt_new(qry);
//			stmt.Clear().Crt_str(tbl.Fld_text(), req.search);
//			Db_rdr rdr = stmt.Exec_select__rls_auto();
//			int cur_count = 0;
//			while (rdr.Move_next()) {
//				int word_id = rdr.Read_int(tbl.Fld_id());
//				byte[] word_text = rdr.Read_bry_by_str(tbl.Fld_text());
//				int page_count = rdr.Read_int(tbl.Fld_page_count());
//				int page_score_max = rdr.Read_int(tbl.Fld_page_score_max());
//				Srch_word_row row = new Srch_word_row(word_id, word_text, page_count, page_score_max);
//				cache_word.words.Add(row);
//				cur_count += page_count;
//				++cache_word.offset;
//				if (cur_count >= (req.req__count * 2)) break;
//			}
//			/*
//			Get_rng; now in set of 100, scores may range from 1234 to 50; search every word to get minimum			
//			*/
//		}
//	}
//	class Srch_page_tbl {
//		public void Get(Srch_request req) {
//		}
//	}
//	class Page_tbl {
//		public void Get(Srch_request req) {}
//	}
//	class Srch_word_row {
//		public int word_id;
//		public byte[] text;
//		public int page_count;
//		public int page_score_max;
//		public int rslts_offset;
//		public boolean rslts_done;
//		public Srch_word_row(int word_id, byte[] text, int page_count, int page_score_max) {
//			this.word_id = word_id; this.text = text; this.page_count = page_count; this.page_score_max = page_score_max;
//			rslts_offset = 0; rslts_done = false;
//		}
//		public void Rslts_offset_add_1()	{++rslts_offset;}
//		public void Rslts_done_y_()			{rslts_done = true;}
//	}
//	class Srch_page_row {
//		public int word_id;
//		public int page_id;
//		public void Load(int word_id, int page_id) {
//			this.word_id = word_id; this.page_id = page_id;
//		}
//	}
//	class Srch_cache_word {
//		public byte[] search;
//		public final List_adp words = List_adp_.new_();
//		public int offset;
//		public void Init_new(byte[] search) {
//			this.search = search;
//			words.Clear();
//			offset = 0;
//		}
//	}
//	class Srch_request {
//		public String search;
//		public int req__count;
//		public boolean wildcard;
//		public String bmk__word__text;
//		public int bmk__page__word_id;
//		public int bmk__page__page_id;
//		// all_pages;
//		// view_pages
//		public final List_adp results = List_adp_.new_();
//		public final Srch_cache_word tbl__word = new Srch_cache_word();
//		public void Init_new(String search, boolean wildcard, int req__count) {
//			this.search = search; this.wildcard = wildcard; this.req__count = req__count;
//			this.bmk__word__text = "";
//			this.bmk__page__word_id = -1;
//			this.bmk__page__page_id = -1;
//			tbl__word.Init_new(Bry_.new_a7(search));
//			results.Clear();
//		}
//		public void Init_continue(int req__count) {
//		}
//	}
//	class Srch_query {
//		public String raw;
//		public Srch_query(String raw) {
//			this.raw = raw;
//			// separate to words
//		}
//	}
//}
