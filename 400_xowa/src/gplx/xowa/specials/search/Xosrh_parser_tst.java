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
import org.junit.*;
public class Xosrh_parser_tst {
	@Before public void init() {fxt.Clear();} private Xosearch_parser_fxt fxt = new Xosearch_parser_fxt();
	@Test   public void Scan_word() 			{fxt.Test_scan("abc", "abc");}
	@Test   public void Scan_word_many() 		{fxt.Test_scan("abc d ef", "abc", "AND", "d", "AND", "ef");}
	@Test   public void Scan_word_symbol() 		{fxt.Test_scan("a, b", "a", "AND", "b");}
	@Test   public void Scan_and_embedded() 	{fxt.Test_scan("candy", "candy");}							// check that andy does not become "AND" + y
	@Test   public void Scan_and_and() 			{fxt.Test_scan("a andand", "a", "AND", "andand");}			// check that andand is word; note that AND is automatically inserted between consecutive words
	@Test   public void Scan_and_parentheses() 	{fxt.Test_scan("a and(b)", "a", "and", "(", "b", ")");}		// check that ( causes and to be treated as separate word
	@Test   public void Scan_not() 				{fxt.Test_scan("-abc", "-", "abc");}
	@Test   public void Scan_not_and() 			{fxt.Test_scan("a -bc", "a", "AND", "-", "bc");}			// auto-add AND for -
	@Test   public void Scan_space() 			{fxt.Test_scan(" a   b ", "a", "AND", "b");}				// spaces should not generate tkns
	@Test   public void Scan_quote() 			{fxt.Test_scan("\"abc\"", "abc");}
	@Test   public void Scan_complicated() 		{fxt.Test_scan("(a AND \"b\") OR -c", "(", "a","AND", "b", ")", "OR", "-", "c");}
	@Test   public void Scan_not_embedded() {
		fxt.Test_scan		("a-b", "a-b");							// fail if "a", "NOT", "b"
		fxt.Test_scan_tids	("a-b", Xosrh_qry_tkn.Tid_word_quoted);	// symbols in unquoted word should convert word to quotes; EX: a-b -> "a-b"
	}								
	@Test   public void Parse_basic() 			{fxt.Test_parse("abc", "abc");}
	@Test   public void Parse_and() 			{fxt.Test_parse("abc AND def", "(abc AND def)");}
	@Test   public void Parse_not() 			{fxt.Test_parse("-abc", "NOT abc");}
	@Test   public void Parse_many() 			{fxt.Test_parse("A AND B OR C AND D", "((A AND B) OR (C AND D))");}
	@Test   public void Match() {
		fxt.Init_match("A", 11, 12, 22);
		fxt.Init_match("B", 21, 22, 12);
		fxt.Test_match("A OR  B"		, 11, 12, 22, 21);
		fxt.Test_match("A AND B"		, 22, 12);
		fxt.Test_match("A AND -B"		, 11);
	}
	@Test  public void Search() {
		Xosearch_searcher_fxt fxt = new Xosearch_searcher_fxt().Clear();
		fxt.Init_search("a1", 11, 12);
		fxt.Init_search("b1", 21, 22);
		fxt.Init_search("b2", 26, 27);
		fxt.Init_search("c1", 31, 12);
		fxt.Test_search("b1", 21, 22);
		fxt.Test_search("b*", 21, 22, 26, 27);
	}
}
class Xosearch_searcher_fxt {
	public Xosearch_searcher_fxt Clear() {
		if (app == null) {
			app = Xoa_app_fxt.app_();
			wiki = Xoa_app_fxt.wiki_tst_(app);
			mgr = new Xowd_hive_mgr(wiki, Xow_dir_info_.Tid_search_ttl);
			tmp_bfr = Bry_bfr.reset_(255);
			parser = Xosrh_parser._;
		}
		return this;
	}
	Xoa_app app; Xow_wiki wiki; Xowd_hive_mgr mgr; Bry_bfr tmp_bfr;
	Xosrh_parser parser;
	public void Init_search(String ttl_str, int... ids) {		
		byte[] ttl_bry = Bry_.new_ascii_(ttl_str);		
		tmp_bfr.Add(ttl_bry);
		int len = ids.length;
		for (int i = 0; i < len; i++) {
			int id = ids[i];
			tmp_bfr.Add_byte_pipe();
			tmp_bfr.Add_base85_len_5(id);
			tmp_bfr.Add_byte(Byte_ascii.Semic);
			tmp_bfr.Add_base85_len_5(0);
		}
		mgr.Create(wiki.Ns_mgr().Ns_main(), ttl_bry, tmp_bfr.XtoAryAndClear(), null);
	}
	public void Test_search(String ttl_str, int... expd) {
		byte[] ttl_bry = Bry_.new_ascii_(ttl_str);
		Xosrh_qry_itm qry_root = parser.Parse(ttl_bry);
		Xosrh_ns_mgr ns_mgr = new Xosrh_ns_mgr(); ns_mgr.Add_all(); // WORKAROUND: xdat fmt does not store ns with search data; pages will be retrieved with ns_id = null; force ns_all (instead of allowing ns_main default);
		qry_root.Search(Cancelable_.Never, tmp_bfr, ttl_bry, wiki, 100, ns_mgr);
		int[] actl = Xosearch_parser_fxt.Xto_ints(qry_root.Ids());
		Tfds.Eq_ary(expd, actl);
	}		
}
class Xosearch_parser_fxt {
	public Xosearch_parser_fxt Clear() {
		if (parser == null) {
			parser = Xosrh_parser._;
			matches = OrderedHash_.new_bry_();
		}
		matches.Clear();
		return this;
	}	private Xosrh_parser parser; OrderedHash matches;
	public void Init_match(String name, int... ids) {
		int len = ids.length;
		ListAdp id_vals = ListAdp_.new_();
		for (int i = 0; i < len; i++)
			id_vals.Add(Xodb_page.srch_(ids[i], 0));
		matches.Add(Bry_.new_ascii_(name), id_vals);
	}
	public void Test_match(String raw, int... expd) {
		byte[] src = Bry_.new_ascii_(raw);
		Xosrh_qry_itm qry_root = parser.Parse(src);
		Test_match_assign_ids(src, qry_root);
		Xosrh_qry_ids matches = qry_root.Matches(src);
		Tfds.Eq_ary(expd, Xto_ints(matches.Ids()));
	}
	public static int[] Xto_ints(ListAdp list) {
		if (list == null) return Int_.Ary_empty;
		int len = list.Count();
		int[] rv = new int[len];
		for (int i = 0; i < len; i++)
			rv[i] = ((Xodb_page)list.FetchAt(i)).Id();
		return rv;
	}
	private void Test_match_assign_ids(byte[] src, Xosrh_qry_itm itm) {
		if (itm.Tid() == Xosrh_qry_itm.Tid_word) {
			byte[] word = itm.Word();
			ListAdp ids = (ListAdp)matches.Fetch(word);
			itm.Ids_(ids);
		}
		else {
			if (itm.Lhs() != null) Test_match_assign_ids(src, itm.Lhs());
			if (itm.Rhs() != null) Test_match_assign_ids(src, itm.Rhs());
		}
	}
	public void Test_scan(String raw, String... expd) {
		byte[] src = Bry_.new_ascii_(raw);
		Xosrh_qry_tkn[] actl_itms = Xosrh_scanner._.Scan(src);
		Tfds.Eq_ary(expd, To_strings(src, actl_itms));
	}
	public void Test_scan_tids(String raw, byte... expd) {
		byte[] src = Bry_.new_ascii_(raw);
		Xosrh_qry_tkn[] actl_itms = Xosrh_scanner._.Scan(src);
		Tfds.Eq_ary(expd, To_tids(actl_itms));
	}
	String[] To_strings(byte[] src, Xosrh_qry_tkn[] ary) {
		int len = ary.length;
		String[] rv = new String[len];
		for (int i = 0; i < len; i++) {
			Xosrh_qry_tkn tkn = ary[i];
			rv[i] = String_.new_ascii_(tkn.Val(src));
		}
		return rv;
	}
	byte[] To_tids(Xosrh_qry_tkn[] ary) {
		int len = ary.length;
		byte[] rv = new byte[len];
		for (int i = 0; i < len; i++) {
			Xosrh_qry_tkn tkn = ary[i];
			rv[i] = tkn.Tid();
		}
		return rv;
	}
	public void Test_parse(String raw, String expd) {
		byte[] src = Bry_.new_ascii_(raw);
		Xosrh_qry_itm qry_root = parser.Parse(src);
		Tfds.Eq(expd, qry_root.Xto_str(src));
	}
}
