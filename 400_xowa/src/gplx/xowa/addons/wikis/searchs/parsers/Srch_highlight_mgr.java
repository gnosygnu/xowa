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
package gplx.xowa.addons.wikis.searchs.parsers; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.searchs.*;
import gplx.core.btries.*;
import gplx.xowa.langs.cases.*;
public class Srch_highlight_mgr {
	private final    Xol_case_mgr	case_mgr;
	private final    Bry_bfr		tmp_bfr = Bry_bfr_.New_w_size(32);
	private	Srch_highlight_itm[]	srch_lc_itms;
	private int						srch_words_len;
	public Srch_highlight_mgr(Xol_case_mgr case_mgr) {this.case_mgr = case_mgr;}
	public Srch_highlight_mgr Search_(byte[] srch_mc_bry) {
		synchronized (tmp_bfr) {
			// build array of search_words
			byte[] srch_lc_bry = case_mgr.Case_build_lower(srch_mc_bry);
			byte[][] srch_lc_ary = Bry_split_.Split(srch_lc_bry, Byte_ascii.Space, Bool_.Y);
			this.srch_words_len = srch_lc_ary.length;
			this.srch_lc_itms = new Srch_highlight_itm[srch_words_len];
			for (int i = 0; i < srch_words_len; ++i) {
				byte[] srch_lc_itm = srch_lc_ary[i];
				srch_lc_itms[i] = new Srch_highlight_itm(i, srch_lc_itm);
			}
			
			// sort to search first by longest search_word; needed for searches like "A Abc" and titles like "Abc A", else "A" will match "Abc" and "Abc" will match nothing
			Array_.Sort(srch_lc_itms, Srch_highlight_bry_sorter.Instance);
			return this;
		}
	}
	public byte[] Highlight(byte[] page_mc_bry) {
		synchronized (tmp_bfr) {
			byte[][]	page_mc_words = Bry_split_.Split(page_mc_bry, Byte_ascii.Space, Bool_.Y);
			byte[][]	page_lc_words = Bry_split_.Split(case_mgr.Case_build_lower(page_mc_bry), Byte_ascii.Space, Bool_.Y);
			int			page_words_len = page_lc_words.length;
			boolean[]		page_words_done = new boolean[page_words_len];

			// loop over search_words; EX: search_raw="a b" -> "a", "b"
			for (int i = 0; i < srch_words_len; ++i) {
				Srch_highlight_itm srch_lc_itm = srch_lc_itms[i];
				byte[] srch_lc_word = srch_lc_itm.Word;

				// loop over page_title words; EX: page_raw="A B C" -> "a", "b", "c"
				for (int j = 0; j < page_words_len; ++j) {
					byte[] page_lc_word = page_lc_words[j];
					int find_pos = Bry_find_.Find_fwd(page_lc_word, srch_lc_word);

					// skip: search is not in page; EX: page_raw="D e"; should not happen, but exit now else Array out of bounds exception below
					if (find_pos == Bry_find_.Not_found) continue;

					// skip: find_pos is not BOS and prv byte is not dash or paren; EX: "Za" should be skipped; "-a" and "(a" should not
					if (find_pos > 0) {
						byte prv_byte = page_lc_word[find_pos - 1];
						if (	prv_byte != Byte_ascii.Dash
							&&	prv_byte != Byte_ascii.Paren_bgn
							)
							continue;
					}					

					// skip: item already matched; EX: "a"; "a a"
					if (page_words_done[j]) continue;
					page_words_done[j] = true;

					// NOTE: lc_idx is guaranteed to equal mc_idx
					byte[] page_mc_word = page_mc_words[j];
					int srch_lc_word_len = srch_lc_word.length;

					// build new word; EX: "<strong>A</strong>"
					tmp_bfr.Clear();
					tmp_bfr.Add_mid(page_mc_word, 0, find_pos);
					tmp_bfr.Add(gplx.langs.htmls.Gfh_tag_.Strong_lhs);
					tmp_bfr.Add_mid(page_mc_word, find_pos, find_pos + srch_lc_word_len);
					tmp_bfr.Add(gplx.langs.htmls.Gfh_tag_.Strong_rhs);
					tmp_bfr.Add_mid(page_mc_word, find_pos + srch_lc_word_len, page_mc_word.length);
					byte[] repl = tmp_bfr.To_bry_and_clear();

					// overwrite page_title word
					page_mc_words[j] = repl;
				}
			}

			// rebuild page_words ary
			for (int i = 0; i < page_words_len; ++i) {
				if (i != 0) tmp_bfr.Add_byte_space();	// NOTE: this assumes one space separating titles which is true for all MW titles
				tmp_bfr.Add(page_mc_words[i]);
			}
			return tmp_bfr.To_bry_and_clear();
		}
	}
}
class Srch_highlight_itm {
	public Srch_highlight_itm(int idx, byte[] word) {this.Idx = idx; this.Word = word; this.Word_len = word.length;}
	public final    int Idx;
	public final    byte[] Word;
	public final    int Word_len;
}
class Srch_highlight_bry_sorter implements gplx.core.lists.ComparerAble {
	public int compare(Object lhsObj, Object rhsObj) {
		Srch_highlight_itm lhs = (Srch_highlight_itm)lhsObj;
		Srch_highlight_itm rhs = (Srch_highlight_itm)rhsObj;
		return -Int_.Compare(lhs.Word_len, rhs.Word_len);	// - for descending
	}
        public static final    Srch_highlight_bry_sorter Instance = new Srch_highlight_bry_sorter(); Srch_highlight_bry_sorter() {}
}
