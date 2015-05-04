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
package gplx.xowa.bldrs.cmds.texts; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.cmds.*;
import org.junit.*; import gplx.xowa.langs.cases.*;
public class Xob_word_parser_tst {
	private final Xob_word_parser_fxt fxt = new Xob_word_parser_fxt();
	@Before public void init() {fxt.Init();}
	@Test  public void Basic_1() {
		fxt.Clear().Test_split("abcd", "abcd");
	}
	@Test   public void Basic_many() {
		fxt.Clear().Test_split("abc d ef", "abc", "d", "ef");
	}
	@Test   public void Split_many() {
		fxt.Clear().Test_split("a   b", "a", "b");
	}
	@Test   public void Lowercase() {
		fxt.Clear().Test_split("A B C", "a", "b", "c");
	}
	@Test   public void Dupe() {
		fxt.Clear().Test_split("a a a", fxt.Make_word("a", 3));
	}
	@Test   public void Dupe_lowercase() {
		fxt.Clear().Test_split("a A", fxt.Make_word("a", 2));
	}
	@Test   public void Dot_acronym() {	// EX: "History of U.S.A. Science " 
		fxt.Clear().Test_split("abc D.E.F. ghi", "abc", "d.e.f.", "ghi");
	}
	@Test   public void Dot_name() { // EX: "H. G. Wells"
		fxt.Clear().Test_split("a. b. last", "a.", "b.", "last");
	}
	@Test   public void Dot_internet() {	// EX: "en.wikipedia.org"
		fxt.Clear().Test_split("a.com", "a.com");
	}
	@Test   public void Dot_ellipsis() {	// EX: "Nights into Dreams..."
		fxt.Clear().Test_split("a... bc d", "a", "...", "bc", "d");
	}
//			tst_Split("a-b.c", "a", "b", "c");
//			tst_Split("a A", "a");
//			tst_Split("a_b", "a", "b");
//			tst_Split("a (b)", "a", "b");
}
class Xob_word_parser_fxt {
	private final Xob_word_parser word_parser = new Xob_word_parser();
	private final Xob_word_mgr word_mgr = new Xob_word_mgr();
	private final Bry_bfr tmp_bfr = Bry_bfr.new_(32);
	private Xol_case_mgr case_mgr;
	public void Init() {
		case_mgr = Xol_case_mgr_.Ascii();
		word_parser.Init_for_ttl(word_mgr, case_mgr);
	}
	public Xob_word_parser_fxt Clear() {
		word_mgr.Clear();
		return this;
	}
	public Xob_word_itm Make_word(String raw, int count) {return new Xob_word_itm(Bry_.new_utf8_(raw)).Count_(count);}
	public void Test_split(String src, String... expd_words) {
		int len = expd_words.length;
		Xob_word_itm[] ary = new Xob_word_itm[len];
		for (int i = 0; i < len; ++i) {
			ary[i] = Make_word(expd_words[i], 1);
		}
		Test_split(src, ary);
	}
	public void Test_split(String src, Xob_word_itm... expd_words) {
		byte[] src_bry = Bry_.new_utf8_(src);
		word_parser.Parse(src_bry, 0, src_bry.length, src_bry.length);
		Tfds.Eq_str_lines(To_str(expd_words), To_str(word_mgr));
	}
	private String To_str(Xob_word_itm[] word_ary) {
		int len = word_ary.length;
		for (int i = 0; i < len; ++i) {
			if (i != 0) tmp_bfr.Add_byte_nl();
			Xob_word_itm word = word_ary[i];
			tmp_bfr.Add(word.Word()).Add_byte_pipe();
			tmp_bfr.Add_int_variable(word.Count());
		}
		return tmp_bfr.Xto_str_and_clear();
	}
	private String To_str(Xob_word_mgr word_mgr) {
		int len = word_mgr.Len();
		Xob_word_itm[] ary = new Xob_word_itm[len];
		for (int i = 0; i < len; ++i)
			ary[i] = word_mgr.Get_at(i);
		return To_str(ary);
	}
}
