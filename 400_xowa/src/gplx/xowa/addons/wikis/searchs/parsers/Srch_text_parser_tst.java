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
import org.junit.*; import gplx.xowa.langs.cases.*;
public class Srch_text_parser_tst {
	private final    Srch_text_parser_fxt fxt = new Srch_text_parser_fxt();
	@Before public void init() {fxt.Init();}
	@Test   public void Word__one()						{fxt.Clear().Test__split("abcd"				, "abcd");}
	@Test   public void Word__many()					{fxt.Clear().Test__split("abc d ef"			, "abc", "d", "ef");}
	@Test   public void Ws__many()						{fxt.Clear().Test__split("a   b"			, "a", "b");}
	@Test   public void Ws__bgn()						{fxt.Clear().Test__split(" a"				, "a");}
	@Test   public void Ws__end()						{fxt.Clear().Test__split("a "				, "a");}
	@Test   public void Under()							{fxt.Clear().Test__split("a_b"				, "a", "b");}						// NOTE: same as space
	@Test   public void Lowercase()						{fxt.Clear().Test__split("A B C"			, "a", "b", "c");}
	@Test   public void Dupe()							{fxt.Clear().Test__split("a a a"			, fxt.Make_word("a", 3));}
	@Test   public void Dupe__lowercase()				{fxt.Clear().Test__split("a A"				, fxt.Make_word("a", 2));}
	@Test   public void Comma__end()					{fxt.Clear().Test__split("a, b"				, "a", "b");}						// EX: "Henry VI, Part 3"; "Bergen County, New Jersey"
	@Test   public void Comma__mid()					{fxt.Clear().Test__split("a,b"				, "a,b");}							// EX: "20,000 Leagues Under the Sea"
	@Test   public void Comma__bgn()					{fxt.Clear().Test__split(",a b"				, "a", "b");}						// EX: skip bad usages; EX: "Little Harbour,Pictou ,Nova Scotia"; "The Hindu Succession Act ,1956"
	@Test   public void Colon__end()					{fxt.Clear().Test__split("a: b"				, "a", "b");}
	@Test   public void Colon__mid()					{fxt.Clear().Test__split("a:b"				, "a:b");}							// EX: "3:10 to Yuma (2007 film)"; "6:02 AM EST"; "24:7 Theatre Festival"; "Library of Congress Classification:Class P -- Language and Literature"
	@Test   public void Colon__bgn()					{fxt.Clear().Test__split(":a b"				, "a", "b");}
	@Test   public void Semic__end()					{fxt.Clear().Test__split("a; b"				, "a", "b");}
	@Test   public void Semic__mid()					{fxt.Clear().Test__split("a;b"				, "a;b");}
	@Test   public void Semic__bgn()					{fxt.Clear().Test__split(";a b"				, "a", "b");}
	@Test   public void Bang__end()						{fxt.Clear().Test__split("a! b"				, "a", "b");}
	@Test   public void Bang__mid()						{fxt.Clear().Test__split("a!b"				, "a!b");}
	@Test   public void Bang__bgn()						{fxt.Clear().Test__split("!a b"				, "a", "b");}
	@Test   public void Question__end()					{fxt.Clear().Test__split("a? b"				, "a", "b");}
	@Test   public void Question__mid()					{fxt.Clear().Test__split("a?b"				, "a?b");}
	@Test   public void Question__bgn()					{fxt.Clear().Test__split("?a b"				, "a", "b");}
	@Test   public void Question__sentence()			{fxt.Clear().Test__split("a?"				, "a");}
	@Test   public void Multiple__1()					{fxt.Clear().Test__split("a?!"				, "a");}
	@Test   public void Multiple__2()					{fxt.Clear().Test__split("a!?"				, "a");}
	@Test   public void Dot__word()						{fxt.Clear().Test__split("a.org"			, "a.org");}						// EX: "en.wikipedia.org"; "Earth.png"; "IEEE_802.15"
	@Test   public void Dot__abrv()						{fxt.Clear().Test__split("a vs. b"			, "a", "vs.", "vs", "b");}			// EX: "vs.", "no.", "dr.", "st.", "inc."
	@Test   public void Dot__initials()					{fxt.Clear().Test__split("a. b. cde"		, "a.", "a", "b.", "b", "cde");}	// EX: "H. G. Wells"
	@Test   public void Dot__acronym()					{fxt.Clear().Test__split("abc D.E.F. ghi"	, "abc", "d.e.f.", "def", "ghi");}	// EX: "History of U.S.A. Science", "G.I. Bill", "Washington, D.C."; "Barcelona F.C."; "The Office (U.S.)"; "Agents of S.H.I.E.L.D."; "Gunfight at the O.K. Corral"; "H.M.S. Pinafore"; "R.E.M. discography"
	@Test   public void Dot__bgn()						{fxt.Clear().Test__split("a .bcd e"			, "a", ".bcd", "bcd", "e");}		// EX: "Colt .45", "List of organizations with .int domain names"
	@Test   public void Dot__bgn__end()					{fxt.Clear().Test__split("a .b. c"			, "a", ".b.", "c");}
	@Test   public void Dot__ellipsis_like()			{fxt.Clear().Test__split("a . . . b"		, "a", "b");}						// EX: "Did you know . . ."
	@Test   public void Ellipsis__len_3()				{fxt.Clear().Test__split("a... bc d"		, "a", "...", "bc", "d");}			// EX: "Nights into Dreams..."
	@Test   public void Ellipsis__len_3__bgn()			{fxt.Clear().Test__split("a ...b"			, "a", "...", "b");	;}				// NOTE: make sure "dot_bgn" code doesn't break this
	@Test   public void Ellipsis__len_2()				{fxt.Clear().Test__split("a.. b"			, "a", "..", "b");}					// EX: "3.. 6.. 9 Seconds of Light"
	@Test   public void Ellipsis__bgn()					{fxt.Clear().Test__split("...a"				, "...", "a");}
	@Test   public void Ellipsis__end()					{fxt.Clear().Test__split("a..."				, "a", "...");}
	@Test   public void Ellipsis__no_ws()				{fxt.Clear().Test__split("a...b"			, "a", "...", "b");}
	@Test   public void Ellipsis__term()				{fxt.Clear().Test__split("a...?!"			, "a", "...");}						// EX: "Wetten, dass..?"
	@Test   public void Apos__merge__end__eos()			{fxt.Clear().Test__split("ab's"				, "ab's", "abs");}					// EX: "A Midsummer Night's Dream"; "Director's cut"
	@Test   public void Apos__merge__end__word()		{fxt.Clear().Test__split("ab's c"			, "ab's", "abs", "c");}				// EX: "Director's cut"; "Cap'n Crunch";
	@Test   public void Apos__merge__bgn()				{fxt.Clear().Test__split("a o'bc"			, "a", "o'bc", "obc");}				// EX: "Twelve O'Clock High"; "Shaqille O'Neal"; "Banca d'Italia"	
	@Test   public void Apos__merge__mid()				{fxt.Clear().Test__split("i'm"				, "i'm", "im");}
	@Test   public void Apos__bgn__long()				{fxt.Clear().Test__split("a 'tis b"			, "a", "'tis", "tis", "b");}		// EX: "My Country, 'Tis of Thee"; "Omaha hold 'em"; "Slash'EM"; "Expo '92"
	@Test   public void Apos__end__eos()				{fxt.Clear().Test__split("a'"				, "a");}
	@Test   public void Apos__end__short()				{fxt.Clear().Test__split("a' b"				, "a", "b");}						// EX: "Will-o'-the-wisp"; "Portuguese man o' war"; 
	@Test   public void Apos__end__long()				{fxt.Clear().Test__split("ab' c"			, "ab", "c");}						// EX: "Dunkin' Donuts"; "'Allo 'Allo!"; "Catherine de' Medici"
	@Test   public void Apos__both__n()					{fxt.Clear().Test__split("a 'n' b"			, "a", "'n'", "n", "b");}			// EX: "Rock 'n' Roll"; "Town 'n' Country, Florida"; "Hill 'n Dale, Florida"; "Chip 'n Dale Rescue Rangers"
	@Test   public void Apos__multiple()				{fxt.Clear().Test__split("ab''cd"			, "ab''cd");}
	@Test   public void Apos__lone()					{fxt.Clear().Test__split("' a"				, "'", "a");}						// EX: "' (disambiguation)"
	@Test   public void Dash__one()						{fxt.Clear().Test__split("a-b"				, "a", "b", "a-b");}				// EX: "The Amazing Spider-Man"; "On-super percentage"; "Basic Role-Playing"; "Context-sensitive"; "Cross-country skiing"; "Double-barreled shotgun"; "Dot-com bubble"; "Many-worlds interpretation"; "Faster-than-light"; "Gram-positive bacteria"; "Half-life", "Jean-Paul Sartre"; "Austria-Hungary"
	@Test   public void Dash__many()					{fxt.Clear().Test__split("a-b-c"			, "a", "b", "c", "a-b-c");}	
	@Test   public void Dash__ws()						{fxt.Clear().Test__split("a - b"			, "a", "-", "b");}
	@Test   public void Dash__eos()						{fxt.Clear().Test__split("a-"				, "a", "a-");}
	@Test   public void Dash__bos()						{fxt.Clear().Test__split("-a"				, "a", "-a");}
	@Test   public void Dash__mult__2()					{fxt.Clear().Test__split("--"				, "--");}
	@Test   public void Dash__mult__3()					{fxt.Clear().Test__split("---"				, "---");}
	@Test   public void Dash__mult__2__words()			{fxt.Clear().Test__split("a--b"				, "a", "b", "a--b");}
	@Test   public void Dash__w_comma()					{fxt.Clear().Test__split("a-, b"			, "a", "a-", "b");}
	@Test   public void Slash__one()					{fxt.Clear().Test__split("a/b"				, "a", "b");}						// EX: "Good cop/bad cop"; "Snooker world rankings 2004/2005"; "Debian GNU/Hurd"; "HIV/AIDS in the United States"; "List of minor planets/1�100"
	@Test   public void Slash__many()					{fxt.Clear().Test__split("a/b/c"			, "a", "b", "c");}					// EX: "Age/sex/location"; 
	@Test   public void Slash__ws()						{fxt.Clear().Test__split("a / b"			, "a", "b");}
	@Test   public void Dash__slash()					{fxt.Clear().Test__split("a-b/c-d-e/f-g"	, "a", "b", "a-b", "c", "d", "e", "c-d-e", "f", "g", "f-g");}
	@Test   public void Paren__both__one()				{fxt.Clear().Test__split("a (b) c"			, "a", "b", "c");}					// EX: "A (letter)"
	@Test   public void Paren__both__many()				{fxt.Clear().Test__split("a (b c) d"		, "a", "b", "c", "d");}				// EX: "A (2016 film)"
	@Test   public void Paren__bgn__multiple()			{fxt.Clear().Test__split("a (((b)))"		, "a", "b");}
	@Test   public void Paren__unmatched()				{fxt.Clear().Test__split("a(b"				, "a(b");}
	@Test   public void Paren__unmatched__bgn()			{fxt.Clear().Test__split("a (b"				, "a", "b");}
	@Test   public void Paren__mid()					{fxt.Clear().Test__split("a(b)c"			, "a(b)c");}						// EX: "Chloro(pyridine)cobaloxime"; "Exi(s)t"
	@Test   public void Paren__end()					{fxt.Clear().Test__split("a(b)"				, "a(b)", "a");}					// EX: "Come What(ever) May"; "501(c) organization"; "Reindeer(s) Are Better Than People"; "(Miss)understood"; "Chromium(III) picolinate"
	@Test   public void Paren__bgn()					{fxt.Clear().Test__split("(a)b"				, "(a)b", "b");}					// EX: "International Student Congress of (bio)Medical Sciences"
	@Test   public void Paren__end__dash()				{fxt.Clear().Test__split("a(b-c) d"			, "a(b-c)", "a", "d");}				// EX: "Bis(2-ethylhexyl) phthalate"
	@Test   public void Paren__end__comma()				{fxt.Clear().Test__split("a(b,c) d"			, "a(b,c)", "a", "d");}				// EX: "Iron(II,III) oxide"
	@Test   public void Paren__comma()					{fxt.Clear().Test__split("a (b), c"			, "a", "b", "c");}					// EX: "Corning (city), New York"
	@Test   public void Paren__multiple()				{fxt.Clear().Test__split("(a) (b)"			, "a", "b");}
	@Test   public void Quote__both()					{fxt.Clear().Test__split("a \"b\" c"		, "a", "b", "c");}
	@Test   public void Word_bgn__at()					{fxt.Clear().Test__split("@a"				, "@a", "a");}
	@Test   public void Word_bgn__tilde()				{fxt.Clear().Test__split("~a~"				, "a");}							// EX: "Phantom ~Requiem for the Phantom~"
}
class Srch_text_parser_fxt {
	private final    Srch_text_parser word_parser = new Srch_text_parser();
	private final    Bry_bfr tmp_bfr = Bry_bfr_.New_w_size(32);
	private Xol_case_mgr case_mgr;
	public void Init() {
		case_mgr = Xol_case_mgr_.A7();
		word_parser.Init_for_ttl(case_mgr);
	}
	public Srch_text_parser_fxt Clear() {
		word_parser.word_hash.Clear();
		return this;
	}
	public Srch_word_itm Make_word(String raw, int count) {return new Srch_word_itm(Bry_.new_u8(raw)).Count_(count);}
	public void Test__split(String src, String... expd_words) {
		int len = expd_words.length;
		Srch_word_itm[] ary = new Srch_word_itm[len];
		for (int i = 0; i < len; ++i) {
			ary[i] = Make_word(expd_words[i], 1);
		}
		Test__split(src, ary);
	}
	public void Test__split(String src, Srch_word_itm... expd_words) {
		byte[] src_bry = Bry_.new_u8(src);
		word_parser.Parse(Bool_.Y, src_bry, 0, src_bry.length);
		Tfds.Eq_str_lines(To_str(expd_words), To_str(word_parser.word_hash));
	}
	private String To_str(Srch_word_itm[] word_ary) {
		int len = word_ary.length;
		for (int i = 0; i < len; ++i) {
			if (i != 0) tmp_bfr.Add_byte_nl();
			Srch_word_itm word = word_ary[i];
			tmp_bfr.Add(word.Word).Add_byte_pipe();
			tmp_bfr.Add_int_variable(word.Count());
		}
		return tmp_bfr.To_str_and_clear();
	}
	private String To_str(Srch_word_hash word_mgr) {
		int len = word_mgr.Len();
		Srch_word_itm[] ary = new Srch_word_itm[len];
		for (int i = 0; i < len; ++i)
			ary[i] = word_mgr.Get_at(i);
		return To_str(ary);
	}
}
