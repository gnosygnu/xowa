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
package gplx.xowa.addons.apps.searchs.searchers.wkrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.apps.*; import gplx.xowa.addons.apps.searchs.*; import gplx.xowa.addons.apps.searchs.searchers.*;
import gplx.xowa.addons.apps.searchs.parsers.*; import gplx.xowa.langs.cases.*;
import gplx.xowa.addons.apps.searchs.dbs.*; import gplx.xowa.addons.apps.searchs.searchers.crts.*;
class Srch_link_wkr_ {
	private static final    Srch_word_count_wkr word_count_wkr = new Srch_word_count_wkr();
	public static Srch_crt_itm Find_sql_root(Srch_search_ctx ctx) {
		Srch_crt_mgr crt_mgr = ctx.Crt_mgr;
		// lookup word_ids; needed for one, mixed, and ands
		Srch_crt_itm[] words_ary = crt_mgr.Words_ary;
		int words_len = words_ary.length;
		for (int i = 0; i < words_len; ++i) {
			Srch_crt_itm word = words_ary[i];
			switch (word.Tid) {
				case Srch_crt_itm.Tid__word:
					if (word.Sql_data.Tid == Srch_crt_sql.Tid__eq)	// look up word_id
						word.Sql_data.Eq_id = ctx.Tbl__word.Select_or_empty(word.Raw).Id;
					break;
				case Srch_crt_itm.Tid__word_quote:
					Srch_word_row[] rows = Find_sql_root__quoted(ctx, word.Raw);
					if (rows != null && rows.length > 0)			// override eq_id
						word.Sql_data.Eq_id = rows[0].Id;
					break;
			}
		}

		if (crt_mgr.Words_tid != Srch_crt_mgr.Tid__ands) return ctx.Crt_mgr__root; 	// one and mixed returns root;

		// ands need to do more db_lookup for rng words and identify the sql_root
		Srch_crt_itm rv = null;
		Srch_word_tbl word_tbl = ctx.Tbl__word;
		int count_min = Int_.Max_value;
		for (int i = 0; i < words_len; ++i) {
			Srch_crt_itm sub = words_ary[i];
			int sub_count = Find_sql_root__ands(ctx, word_tbl, sub);
			if (sub_count < count_min) {
				count_min = sub_count;
				rv = sub;
			}
		}
		return rv;
	}
	private static Srch_word_row[] Find_sql_root__quoted(Srch_search_ctx ctx, byte[] raw) {
		List_adp tmp_list = List_adp_.new_();
		byte[][] ary = Bry_split_.Split(raw, Byte_ascii.Space, Bool_.Y); // TODO: splitting by space is simplistic; should call Srch2_split_words
		int words_len = ary.length;
		for (int i = 0; i < words_len; ++i) {
			byte[] word = ary[i];
			Srch_word_row word_row = ctx.Tbl__word.Select_or_empty(word); if (word_row == Srch_word_row.Empty) continue;
			tmp_list.Add(word_row);
		}
		if (tmp_list.Count() == 0) return null;	// no words exist in db; EX: "xyz1 xyz2"
		tmp_list.Sort_by(Srch_word_row_sorter__link_count.Desc);
		Srch_word_row[] rows = (Srch_word_row[])tmp_list.To_ary_and_clear(Srch_word_row.class);
		return rows;
	}
	private static int Find_sql_root__ands(Srch_search_ctx ctx, Srch_word_tbl word_tbl, Srch_crt_itm sub) {
		if (sub.Tid == Srch_crt_itm.Tid__not) return Int_.Max_value;
		int cached_count = ctx.Cache__word_counts.Get_as_int_or(sub.Raw, Int_.Min_value);
		if (cached_count != Int_.Min_value) return cached_count;
		int rv = Int_.Max_value;
		if (sub.Sql_data.Tid == Srch_crt_sql.Tid__eq) {
			Srch_word_row word = word_tbl.Select_or_empty(sub.Raw);
			if (word != Srch_word_row.Empty) rv = word.Link_count;
		}
		else {
			rv = word_count_wkr.Get_top_10(ctx, word_tbl, sub);
		}
		ctx.Cache__word_counts.Add_bry_int(sub.Raw, rv);
		return rv;
	}
	public static boolean Matches(Srch_crt_itm node, Srch_text_parser text_parser, Xol_case_mgr case_mgr, byte[] ttl) {
		byte[] ttl_lower = case_mgr.Case_build_lower(Xoa_ttl.Replace_unders(ttl));
		byte[][] ttl_words = text_parser.Parse_to_bry_ary(Bool_.Y, ttl);
		return Matches(node, ttl_lower, ttl_words);
	}
	private static boolean Matches(Srch_crt_itm node, byte[] ttl_lower, byte[][] ttl_words) {
		int tid = node.Tid;
		byte[] raw = node.Raw;
		Srch_crt_itm[] subs = node.Subs;
		int subs_len = subs.length;
		switch (tid) {
			case Srch_crt_itm.Tid__word: {
				int len = ttl_words.length;
				for (int i = 0; i < len; ++i) {
					byte[] word = ttl_words[i];
					if (node.Sql_data.Pattern == null) {
						if (Bry_.Eq(word, raw)) return true;
					}
					else {
						if (node.Sql_data.Pattern.Match(word)) return true;
					}
				}
				return false;
			}
			case Srch_crt_itm.Tid__word_quote:		return Bry_find_.Find_fwd(ttl_lower, raw) != Bry_find_.Not_found;// note that raw does not have quotes; EX: "B*" -> B*
			case Srch_crt_itm.Tid__not:			return !Matches(subs[0], ttl_lower, ttl_words);
			case Srch_crt_itm.Tid__or: {
				for (int i = 0; i < subs_len; ++i) {
					Srch_crt_itm sub = subs[i];
					if (Matches(sub, ttl_lower, ttl_words))
						return true;
				}
				return false;
			}
			case Srch_crt_itm.Tid__and:
				for (int i = 0; i < subs_len; ++i) {
					Srch_crt_itm sub = subs[i];
					if (!Matches(sub, ttl_lower, ttl_words))
						return false;
				}
				return true;
			case Srch_crt_itm.Tid__invalid:			return false;
			default: throw Err_.new_unhandled(tid);
		}
	}
}
class Srch_word_row_sorter__link_count implements gplx.core.lists.ComparerAble {
	public int compare(Object lhsObj, Object rhsObj) {
		Srch_word_row lhs = (Srch_word_row)lhsObj;
		Srch_word_row rhs = (Srch_word_row)rhsObj;
		return -Int_.Compare(lhs.Link_count, rhs.Link_count);
	}
        public static final    Srch_word_row_sorter__link_count Desc = new Srch_word_row_sorter__link_count(); Srch_word_row_sorter__link_count() {}
}