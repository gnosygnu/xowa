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
class Xows_db_matcher_bldr {
	public Xows_db_word[] Gather_words_for_db(Cancelable cxl, Xows_db_matcher matcher, Xowd_search_word_tbl word_tbl) {
		ListAdp rv = ListAdp_.new_();
		Gather_words_for_db(cxl, matcher, rv, word_tbl);
		rv.SortBy(Xows_db_word_sorter.Page_count_dsc);
		return (Xows_db_word[])rv.Xto_ary(Xows_db_word.class);
	}
	public void Gather_words_for_db(Cancelable cxl, Xows_db_matcher matcher, ListAdp rv, Xowd_search_word_tbl word_tbl) {
		synchronized (cxl) {
			if (cxl.Canceled()) return;
		}
		switch (matcher.Tid()) {
			case Xows_db_matcher.Tid_word:
				byte[] word_text = matcher.Raw();
				if (Bry_.Has(word_text, Byte_ascii.Asterisk))
					Load_word_many(cxl, rv, word_text, word_tbl);
				else
					Load_word_one(rv, word_text, word_tbl);
				break;
			case Xows_db_matcher.Tid_word_quote:
				ListAdp tmp = ListAdp_.new_();
				byte[][] words = Bry_.Split(matcher.Raw(), Byte_ascii.Space, Bool_.Y);
				int words_len = words.length;
				for (int i = 0; i < words_len; ++i) {
					byte[] word = words[i];
					Load_word_one(tmp, word, word_tbl);
				}
				if (tmp.Count() == 0) return;	// no words in db; EX: "xyz cba"
				tmp.SortBy(Xows_db_word_sorter.Page_count_dsc);
				rv.Add(tmp.FetchAt(0));			// add lowest page_count word to db; EX: search for "earth and" should search for "earth" only, but match for "earth and"
				break;
			case Xows_db_matcher.Tid_and:
				ListAdp lhs_tmp = ListAdp_.new_(), rhs_tmp = ListAdp_.new_();
				Gather_words_for_db(cxl, matcher.Lhs(), lhs_tmp, word_tbl);
				Gather_words_for_db(cxl, matcher.Rhs(), rhs_tmp, word_tbl);
				ListAdp_.Add_list(rv, Calc_lowest(lhs_tmp, rhs_tmp)); // calc side with lowest count; add to rv;
				break;
			case Xows_db_matcher.Tid_or:
				Gather_words_for_db(cxl, matcher.Lhs(), rv, word_tbl);
				Gather_words_for_db(cxl, matcher.Rhs(), rv, word_tbl);
				break;
			case Xows_db_matcher.Tid_not:		break;			// never add "NOT" to db_search
			case Xows_db_matcher.Tid_null:		break;			// should not happen
			default:							throw Err_.unhandled(matcher.Tid());
		}
	}
	private ListAdp Calc_lowest(ListAdp lhs, ListAdp rhs) {
		int lhs_count = Calc(lhs);
		int rhs_count = Calc(rhs);
		// never return 0 as lowest count; note that NOT will return 0;
		if		(lhs_count == 0)	return rhs;
		else if (rhs_count == 0)	return lhs;
		else						return lhs_count < rhs_count ? lhs : rhs;
	}
	private int Calc(ListAdp list) {
		int rv = 0;
		int len = list.Count();
		for (int i = 0; i < len; ++i) {
			Xows_db_word word = (Xows_db_word)list.FetchAt(i);
			rv += word.Page_count();
		}
		return rv;
	}
	private void Load_word_one(ListAdp rv, byte[] word_text, Xowd_search_word_tbl word_tbl) {
		Xoa_app_.Usr_dlg().Prog_many("", "", "search:word by text; word=~{0}", word_text);
		Xowd_search_word_row row = word_tbl.Select_by_or_null(word_text); if (row == Xowd_search_word_row.Null) return;
		Xows_db_word word = new Xows_db_word(row.Id(), row.Text(), row.Page_count());
		rv.Add(word);
	}
	private void Load_word_many(Cancelable cxl, ListAdp rv, byte[] word_text, Xowd_search_word_tbl word_tbl) {
		Xoa_app_.Usr_dlg().Prog_many("", "", "search:word by wildcard; word=~{0}", word_text);
		Xowd_search_word_row[] rows = word_tbl.Select_in(cxl, word_text);
		int len = rows.length;
		for (int i = 0; i < len; ++i) {
			Xowd_search_word_row row = rows[i];
			rv.Add(new Xows_db_word(row.Id(), row.Text(), row.Page_count()));
		}
	}
	public static final Xows_db_matcher_bldr I = new Xows_db_matcher_bldr(); Xows_db_matcher_bldr() {}
}
