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
import gplx.xowa.wikis.data.tbls.*;
import gplx.xowa.specials.search.crts.*;
class Xows_db_matcher_bldr {
	public Srch_word_row[] Gather_words_for_db(Cancelable cxl, Srch_crt_node matcher, Xowd_search_word_tbl word_tbl) {
		List_adp rv = List_adp_.new_();
		Gather_words_for_db(cxl, matcher, rv, word_tbl);
		rv.Sort_by(Srch_word_row_sorter.Page_count_dsc);
		return (Srch_word_row[])rv.To_ary(Srch_word_row.class);
	}
	private void Gather_words_for_db(Cancelable cxl, Srch_crt_node matcher, List_adp rv, Xowd_search_word_tbl word_tbl) {
		switch (matcher.tid) {
			case Srch_crt_node.Tid_word:
				byte[] word_text = matcher.raw;
				if (Bry_.Has(word_text, Byte_ascii.Star))
					Load_word_many(cxl, rv, word_text, word_tbl);
				else
					Load_word_one(rv, word_text, word_tbl);
				break;
			case Srch_crt_node.Tid_word_quote:
				List_adp tmp = List_adp_.new_();
				byte[][] words = Bry_split_.Split(matcher.raw, Byte_ascii.Space, Bool_.Y);
				int words_len = words.length;
				for (int i = 0; i < words_len; ++i) {
					byte[] word = words[i];
					Load_word_one(tmp, word, word_tbl);
				}
				if (tmp.Count() == 0) return;	// no words in db; EX: "xyz cba"
				tmp.Sort_by(Srch_word_row_sorter.Page_count_dsc);
				rv.Add(tmp.Get_at(0));			// add lowest page_count word to db; EX: search for "earth and" should search for "earth" only, but match for "earth and"
				break;
			case Srch_crt_node.Tid_and:
				List_adp lhs_tmp = List_adp_.new_(), rhs_tmp = List_adp_.new_();
				Gather_words_for_db(cxl, matcher.lhs, lhs_tmp, word_tbl);
				Gather_words_for_db(cxl, matcher.rhs, rhs_tmp, word_tbl);
				List_adp_.Add_list(rv, Calc_lowest(lhs_tmp, rhs_tmp)); // calc side with lowest count; add to rv;
				break;
			case Srch_crt_node.Tid_or:
				Gather_words_for_db(cxl, matcher.lhs, rv, word_tbl);
				Gather_words_for_db(cxl, matcher.rhs, rv, word_tbl);
				break;
			case Srch_crt_node.Tid_not:		break;			// never add "NOT" to db_search
			case Srch_crt_node.Tid_null:		break;			// should not happen
			default:							throw Err_.new_unhandled(matcher.tid);
		}
	}
	private List_adp Calc_lowest(List_adp lhs, List_adp rhs) {
		int lhs_count = Calc(lhs);
		int rhs_count = Calc(rhs);
		// never return 0 as lowest count; note that NOT will return 0;
		if		(lhs_count == 0)	return rhs;
		else if (rhs_count == 0)	return lhs;
		else						return lhs_count < rhs_count ? lhs : rhs;
	}
	private int Calc(List_adp list) {
		int rv = 0;
		int len = list.Count();
		for (int i = 0; i < len; ++i) {
			Srch_word_row word = (Srch_word_row)list.Get_at(i);
			rv += word.page_count;
		}
		return rv;
	}
	private void Load_word_one(List_adp rv, byte[] word_text, Xowd_search_word_tbl word_tbl) {
		Xoa_app_.Usr_dlg().Prog_many("", "", "search:word by text; word=~{0}", word_text);
		Xowd_search_word_row row = word_tbl.Select_by_or_null(word_text); if (row == Xowd_search_word_row.Null) return;
		Srch_word_row word = new Srch_word_row(row.Id(), row.Text(), row.Page_count());
		rv.Add(word);
	}
	private void Load_word_many(Cancelable cxl, List_adp rv, byte[] word_text, Xowd_search_word_tbl word_tbl) {
		Xoa_app_.Usr_dlg().Prog_many("", "", "search:word by wildcard; word=~{0}", word_text);
		Xowd_search_word_row[] rows = word_tbl.Select_in(cxl, word_text);
		int len = rows.length;
		for (int i = 0; i < len; ++i) {
			Xowd_search_word_row row = rows[i];
			rv.Add(new Srch_word_row(row.Id(), row.Text(), row.Page_count()));
		}
	}
}
