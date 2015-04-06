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
import gplx.xowa.dbs.*; import gplx.xowa.wikis.data.tbls.*; import gplx.xowa.bldrs.cmds.texts.*;
class Xosrh_qry_itm {
	public Xosrh_qry_itm(byte tid, byte[] word, Xosrh_qry_itm lhs, Xosrh_qry_itm rhs) {
		this.tid = tid; this.word = word; this.lhs = lhs; this.rhs = rhs;
	}
	public byte Tid() {return tid;} private byte tid;
	public Xosrh_qry_itm Lhs() {return lhs;} private Xosrh_qry_itm lhs;
	public Xosrh_qry_itm Rhs() {return rhs;} private Xosrh_qry_itm rhs;
	public ListAdp Ids() {return ids;} public void Ids_(ListAdp v) {this.ids = v;} ListAdp ids = ListAdp_.Null;
	public byte[] Word() {return word;} private byte[] word;
	public String Xto_str(byte[] src) {
		Bry_bfr bfr = Bry_bfr.new_();
		Xto_str_bld(src, bfr);
		return bfr.Xto_str_and_clear();
	}
	public Xosrh_qry_ids Matches(byte[] src) {
		switch (tid) {
			case Xosrh_qry_itm.Tid_word:
			case Xosrh_qry_itm.Tid_word_quote:
				return new Xosrh_qry_ids(false, ids);
			case Xosrh_qry_itm.Tid_not:	Xosrh_qry_ids rv = rhs.Matches(src); return rv.Not_(!rv.Not());
			case Xosrh_qry_itm.Tid_or:
			case Xosrh_qry_itm.Tid_and:
				ListAdp merged = Evaluate(src, tid == Xosrh_qry_itm.Tid_and, lhs, rhs);
				return new Xosrh_qry_ids(false, merged);
			case Xosrh_qry_itm.Tid_null: return Xosrh_qry_ids.Null;
			default: throw Err_.unhandled(tid);
		}
	}
	private static ListAdp Search_word(Xowe_wiki wiki, Cancelable cancelable, Bry_bfr tmp_bfr, Xosrh_ns_mgr ns_mgr, byte[] search_word, int results_max) {
		ListAdp found = ListAdp_.new_();
		byte wiki_db_tid = wiki.Db_mgr().Tid();
		if (wiki_db_tid == Xodb_mgr_sql.Tid_sql
			&& wiki.Appe().Gui_mgr().Search_suggest_mgr().Auto_wildcard()) {	// HACK: auto-asterisk words for sqlite; DATE:2013-09-05
			if (!Bry_.HasAtEnd(search_word, new byte[] {Byte_ascii.Asterisk}))
				search_word = Bry_.Add(search_word, Byte_ascii.Asterisk);
			if (!Bry_.HasAtBgn(search_word, new byte[] {Byte_ascii.Asterisk}))
				search_word = Bry_.Add(Byte_ascii.Asterisk, search_word);
		}
		wiki.Db_mgr().Load_mgr().Load_search(cancelable, found, search_word, results_max);
		ListAdp rv = ListAdp_.new_();
		int found_len = found.Count();
		for (int i = 0; i < found_len; i++) {
			Xowd_page_itm page = (Xowd_page_itm)found.FetchAt(i);
			if (	ns_mgr.Has(page.Ns_id())
				||	wiki_db_tid == Xodb_mgr_txt.Tid_txt)	// xdat does not store ns, so ns will always be null; no choice but to bring back all results; DATE:2013-11-14
				rv.Add(page);
		}
		return rv;
	}
	public void Search(Cancelable cancelable, Bry_bfr tmp_bfr, byte[] src, Xowe_wiki wiki, int results_max, Xosrh_ns_mgr ns_mgr) {
		if (cancelable.Canceled()) return;
		switch (tid) {
			case Xosrh_qry_itm.Tid_null: return;
			case Xosrh_qry_itm.Tid_word:
				ids = Search_word(wiki, cancelable, tmp_bfr, ns_mgr, word, results_max);
				break;
			case Xosrh_qry_itm.Tid_word_quote:
				OrderedHash tmp_search_list = OrderedHash_.new_();
				byte[][] words = gplx.xowa.bldrs.cmds.texts.Xob_search_base.Split(wiki.Lang(), tmp_search_list, tmp_bfr, word);
				int words_len = words.length;
				ListAdp prv_list = null;
				for (int i = 0; i < words_len; i++) {
					if (cancelable.Canceled()) return;
					ListAdp cur_list = Search_word(wiki, cancelable, tmp_bfr, ns_mgr, words[i], results_max);
					if (prv_list == null)	// 1st list; just set to cur_list
						prv_list = cur_list;
					else {					// nth list; AND to prv_list; "A B" should find all titles with "A AND B"
						prv_list = Evaluate(word, true, prv_list, false, cur_list, false);
					}
				}
				ListAdp tmp_ids = prv_list;
				int ids_len = tmp_ids.Count();
				if (ids_len == 0) return;
				if (cancelable.Canceled()) return;
				wiki.Db_mgr().Load_mgr().Load_by_ids(cancelable, tmp_ids, 0, ids_len);
				if (cancelable.Canceled()) return;
				ids = ListAdp_.new_();
				for (int i = 0; i < ids_len; i++) {
					Xowd_page_itm itm = (Xowd_page_itm)tmp_ids.FetchAt(i);
					byte[] itm_ttl = itm.Ttl_page_db();
					itm_ttl = wiki.Lang().Case_mgr().Case_build_lower(itm_ttl, 0, itm_ttl.length);	// lowercase ttl (since all search words are lower-cased)
					itm_ttl = Bry_.Replace(itm_ttl, Byte_ascii.Underline, Byte_ascii.Space);	// replace _ with " " (assume user will use spaces in search term)
					if (Bry_finder.Find_fwd(itm_ttl, word) != Bry_.NotFound)
						ids.Add(itm);
				}
				break;
			default:
				if (lhs != null) lhs.Search(cancelable, tmp_bfr, src, wiki, results_max, ns_mgr);
				if (rhs != null) rhs.Search(cancelable, tmp_bfr, src, wiki, results_max, ns_mgr);
				break;
		}
	}
	static final byte Evaluate_tid_null = 0, Evaluate_tid_and = 1, Evaluate_tid_or = 2, Evaluate_tid_not_and = 3;
	ListAdp Evaluate(byte[] src, boolean join_tid_is_and, Xosrh_qry_itm lhs, Xosrh_qry_itm rhs) {
		Xosrh_qry_ids lhs_grp = lhs.Matches(src), rhs_grp = rhs.Matches(src);
		boolean lhs_not = lhs_grp.Not(), rhs_not = rhs_grp.Not();
		ListAdp lhs_list = lhs_grp.Ids(), rhs_list = rhs_grp.Ids();
		if (lhs_not && rhs_not) return Evaluate_not_found("NOT cannot be applied to both lists: lhs:~{0} rhs:~{1}", lhs.Xto_str(src), rhs.Xto_str(src));
		return Evaluate(src, join_tid_is_and, lhs_list, lhs_not, rhs_list, rhs_not);
	}
	/*
	AND,0 nots: hash a; iterate cur_b and add if in a
	OR ,0 nots; hash a; add a; iterate cur_b and add if not in a
	AND,1 nots; hash notted; iterate comp and add only if not in notted; (a,cur_b,c) AND NOT (cur_b) 
	AND,2 nots; fail; NOT xq AND NOT qx will yield all titles
	OR ,2 nots; fail; NOT xq OR NOT qx will yield all titles
	OR ,1 nots; fail; (a, cur_b, c, d) OR NOT (xq) will yield all titles
	*/
	ListAdp Evaluate(byte[] src, boolean join_tid_is_and, ListAdp lhs_list, boolean lhs_not, ListAdp rhs_list, boolean rhs_not) {
		ListAdp list_to_hash = null, list_to_comp = null; int len = 0;
		byte tid = Evaluate_tid_null;
		ListAdp rv = ListAdp_.new_();
		if (lhs_not || rhs_not) {
			if (!join_tid_is_and)
				return Evaluate_not_found("NOT cannot be applied with OR: lhs:~{0} rhs:~{1}", lhs.Xto_str(src), rhs.Xto_str(src));
			if (lhs_not) {
				list_to_hash = lhs_list;
				list_to_comp = rhs_list;
			}
			else {
				list_to_hash = rhs_list;
				list_to_comp = lhs_list;				
			}
			tid = Evaluate_tid_not_and;
		}
		else {
			tid = join_tid_is_and ? Evaluate_tid_and : Evaluate_tid_or;
			boolean rhs_is_smaller = rhs_list.Count() < lhs_list.Count();
			if (rhs_is_smaller) {
				list_to_hash = rhs_list;
				list_to_comp = lhs_list;				
			}
			else {
				list_to_hash = lhs_list;
				list_to_comp = rhs_list;
			}
		}
		len = list_to_hash.Count();
		for (int i = 0; i < len; i++) {
			Xowd_page_itm id = (Xowd_page_itm)list_to_hash.FetchAt(i);
			try {
				if (!tmp_hash.Has(id.Id_val())) tmp_hash.Add(id.Id_val(), id);
			}	catch (Exception e) {Err_.Noop(e); tmp_hash.Clear(); return ListAdp_.Null;}	// handle error in case of threading issues; must clear tmp_hash else will accumulate;
			if (tid == Evaluate_tid_or)
				rv.Add(id);
		}
		len = list_to_comp.Count();
		for (int i = 0; i < len; i++) {
			Xowd_page_itm id = (Xowd_page_itm)list_to_comp.FetchAt(i);
			boolean exists = tmp_hash.Has(id.Id_val());
			switch (tid) {
				case Evaluate_tid_and: 		// iterate comp and add if in a; EX: (a,cur_b,c) AND (a,d); add a
					if (exists) rv.Add(id);
					break;
				case Evaluate_tid_or: 		// iterate comp and add if not in a; EX: (a,cur_b,c) OR (a,d); add d
				case Evaluate_tid_not_and:	// iterate comp and add only if not in notted; (a,cur_b,c) AND NOT (cur_b); add a, c
					if (!exists)
						rv.Add(id);
					break; 
				case Evaluate_tid_null: default:
					throw Err_.unhandled(tid);
			}			
		}
		tmp_hash.Clear();
		return rv;
	}	static OrderedHash tmp_hash = OrderedHash_.new_();
	ListAdp Evaluate_not_found(String msg, Object... args) {
		return null;
	}
	public void Xto_str_bld(byte[] src, Bry_bfr bfr) {
		switch (tid) {
			case Xosrh_qry_itm.Tid_word: 	bfr.Add(word); break;
			case Xosrh_qry_itm.Tid_not:	bfr.Add(Bry_not); rhs.Xto_str_bld(src, bfr); break;
			case Xosrh_qry_itm.Tid_or:
			case Xosrh_qry_itm.Tid_and:
				bfr.Add_byte(Byte_ascii.Paren_bgn);
				lhs.Xto_str_bld(src, bfr);
				bfr.Add(tid == Xosrh_qry_itm.Tid_or ? Bry_or : Bry_and);
				rhs.Xto_str_bld(src, bfr);
				bfr.Add_byte(Byte_ascii.Paren_end);
				break;
			case Xosrh_qry_itm.Tid_null: break;
			default: throw Err_.unhandled(tid);
		}
	}
	public static final byte Tid_null = 0, Tid_root = 1, Tid_word = 2, Tid_word_quote = 3, Tid_not = 4, Tid_or = 5, Tid_and = 6;
	public static byte[] Bry_not = Bry_.new_ascii_("NOT "), Bry_and = Bry_.new_ascii_(" AND "), Bry_or = Bry_.new_ascii_(" OR ");
	public static final Xosrh_qry_itm Null = new Xosrh_qry_itm(Tid_null, null, null, null);
	public static Xosrh_qry_itm nde_(byte tid, Xosrh_qry_itm lhs, Xosrh_qry_itm rhs) {return new Xosrh_qry_itm(tid, null, lhs, rhs);}
	public static Xosrh_qry_itm word_(byte[] src, Xosrh_qry_tkn word_tkn) {
		byte tid = word_tkn.Tid() == Xosrh_qry_tkn.Tid_word ? Xosrh_qry_itm.Tid_word : Xosrh_qry_itm.Tid_word_quote; 
		return new Xosrh_qry_itm(tid, word_tkn.Val(src), null, null);
	}
}
class Xosrh_qry_ids {
	public Xosrh_qry_ids(boolean not, ListAdp ids) {this.not = not; this.ids = ids;}
	public boolean Not() {return not;} public Xosrh_qry_ids Not_(boolean v) {not = v; return this;} private boolean not;
	public ListAdp Ids() {return ids;} public Xosrh_qry_ids Ids_(ListAdp v) {ids = v; return this;} ListAdp ids = ListAdp_.Null;
	public static final Xosrh_qry_ids Null = new Xosrh_qry_ids(false, ListAdp_.Null);
}
