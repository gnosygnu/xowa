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
package gplx.xowa.specials.search.parsers; import gplx.*; import gplx.xowa.*; import gplx.xowa.specials.*; import gplx.xowa.specials.search.*;
import org.junit.*; import gplx.xowa.langs.cases.*;
public class Xows_text_parser__v2_tst {
	private final Xows_text_parser__v2_fxt fxt = new Xows_text_parser__v2_fxt();
	@Before public void init() {fxt.Init();}
	@Test  public void Word__one() {
		fxt.Clear().Test_split("abcd", "abcd");
	}
	@Test   public void Word__many() {
		fxt.Clear().Test_split("abc d ef", "abc", "d", "ef");
	}
	@Test   public void Ws__many() {
		fxt.Clear().Test_split("a   b", "a", "b");
	}
	@Test   public void Ws__bgn() {
		fxt.Clear().Test_split(" a", "a");
	}
	@Test   public void Ws__end() {
		fxt.Clear().Test_split(" a", "a");
	}
	@Test   public void Under() {
		fxt.Clear().Test_split("a_b", "a", "b");
	}
	@Test   public void Lowercase() {
		fxt.Clear().Test_split("A B C", "a", "b", "c");
	}
	@Test   public void Dupe() {
		fxt.Clear().Test_split("a a a", fxt.Make_word("a", 3));
	}
	@Test   public void Dupe__lowercase() {
		fxt.Clear().Test_split("a A", fxt.Make_word("a", 2));
	}
	@Test   public void Dot__acronym() {	// EX: "History of U.S.A. Science " 
		fxt.Clear().Test_split("abc D.E.F. ghi", "abc", "d.e.f.", "ghi");
	}
	@Test   public void Dot__initials() {	// EX: "H. G. Wells"
		fxt.Clear().Test_split("a. b. cde", "a.", "b.", "cde");
	}
	@Test   public void Dot__abrv() {	// EX: "vs.", "no."
		fxt.Clear().Test_split("a vs. b", "a", "vs.", "b");
	}
	@Test   public void Dot__internet() {	// EX: "en.wikipedia.org"
		fxt.Clear().Test_split("a.com", "a.com");
	}
	@Test   public void Ellipsis__basic() {	// EX: "Nights into Dreams..."
		fxt.Clear().Test_split("a... bc d", "a", "...", "bc", "d");
	}
	@Test   public void Ellipsis__bgn() {
		fxt.Clear().Test_split("...a", "...", "a");
	}
	@Test   public void Ellipsis__end() {
		fxt.Clear().Test_split("a...", "a", "...");
	}
	@Test   public void Ellipsis__no_ws() {
		fxt.Clear().Test_split("a...b", "a", "...", "b");
	}
	@Test   public void Apos__contraction() {	// EX: "I'm"
		fxt.Clear().Test_split("i'm", "i'm");
	}
	@Test   public void Apos__multiple() {
		fxt.Clear().Test_split("a''b", "a''b");
	}
	@Test   public void Apos__possessive_singular_eos() {// EX: "wiki's"
		fxt.Clear().Test_split("a's", "a", "a's");
	}
	@Test   public void Apos__possessive_singular_word() {
		fxt.Clear().Test_split("a's b", "a", "a's", "b");
	}
	@Test   public void Apos__possessive_plural_eos() {// EX: "wiki'"
		fxt.Clear().Test_split("a'", "a", "a'");
	}
	@Test   public void Apos__possessive_plural_word() {
		fxt.Clear().Test_split("a' b", "a", "a'", "b");
	}
	@Test   public void Bang__lone() {
		fxt.Clear().Test_split("! a", "a");
	}
	@Test   public void Bang__word() {
		fxt.Clear().Test_split("a!", "a");
	}
	@Test   public void Bang__sentence() {// EX: "A!"
		fxt.Clear().Test_split("a b!", "a", "b");
	}
	@Test   public void Question__sentence() {// EX: "A?"
		fxt.Clear().Test_split("a?", "a");
	}
	@Test   public void Parens_both() {// EX: "A (letter)"
		fxt.Clear().Test_split("a (b)", "a", "b");
	}
	@Test   public void Slash__word() {// EX: "Good_cop/bad_cop"
		fxt.Clear().Test_split("a/b", "a", "b");
	}
	// apos: 'Some apostrophe sentence' ? 
}
class Xows_text_parser__v2_fxt {
	private final Xows_text_parser__v2 word_parser = new Xows_text_parser__v2();
	private final Xob_word_mgr word_mgr = new Xob_word_mgr();
	private final Bry_bfr tmp_bfr = Bry_bfr.new_(32);
	private Xol_case_mgr case_mgr;
	public void Init() {
		case_mgr = Xol_case_mgr_.A7();
		word_parser.Init_for_ttl(word_mgr, case_mgr);
	}
	public Xows_text_parser__v2_fxt Clear() {
		word_mgr.Clear();
		return this;
	}
	public Xob_word_itm Make_word(String raw, int count) {return new Xob_word_itm(Bry_.new_u8(raw)).Count_(count);}
	public void Test_split(String src, String... expd_words) {
		int len = expd_words.length;
		Xob_word_itm[] ary = new Xob_word_itm[len];
		for (int i = 0; i < len; ++i) {
			ary[i] = Make_word(expd_words[i], 1);
		}
		Test_split(src, ary);
	}
	public void Test_split(String src, Xob_word_itm... expd_words) {
		byte[] src_bry = Bry_.new_u8(src);
		word_parser.Parse(src_bry, src_bry.length, 0, src_bry.length);
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
		return tmp_bfr.To_str_and_clear();
	}
	private String To_str(Xob_word_mgr word_mgr) {
		int len = word_mgr.Len();
		Xob_word_itm[] ary = new Xob_word_itm[len];
		for (int i = 0; i < len; ++i)
			ary[i] = word_mgr.Get_at(i);
		return To_str(ary);
	}
}
