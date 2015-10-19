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
package gplx.langs.phps; import gplx.*; import gplx.langs.*;
import org.junit.*;
public class Php_text_itm_tst {
	@Before public void init() {fxt.Clear();} private Php_text_itm_fxt fxt = new Php_text_itm_fxt();
	@Test  public void Q1_basic() 						{fxt.Init_q1().Test_parse("abcde"			, "abcde");}
	@Test  public void Q1_apos() 						{fxt.Init_q1().Test_parse("a\\'b"			, "a'b");}
	@Test  public void Q1_backslash() 					{fxt.Init_q1().Test_parse("a\\\\b"			, "a\\b");}
	@Test  public void Q1_backslash_eos() 				{fxt.Init_q1().Test_parse("a\\"				, "a\\");}		// PURPOSE: allow single trailing backslash; DATE:2014-08-06
	@Test  public void Q1_noop() 						{fxt.Init_q1().Test_parse("a\\$\\nb"		, "a\\$\\nb");}
	@Test  public void Q2_basic() 						{fxt.Init_q2().Test_parse("abcde"			, "abcde");}
	@Test  public void Q2_quote() 						{fxt.Init_q2().Test_parse("a\\\"b"			, "a\"b");}
	@Test  public void Q2_backslash() 					{fxt.Init_q2().Test_parse("a\\\\b"			, "a\\b");}
	@Test  public void Q2_noop() 						{fxt.Init_q2().Test_parse("a\\%\\cb"		, "a\\%\\cb");}
	@Test  public void Q2_ws() 							{fxt.Init_q2().Test_parse("a\\tb\\nc"		, "a\tb\nc");}
	@Test  public void Q2_fmt() 						{fxt.Init_q2().Test_parse("a$1b$2c"			, "a~{0}b~{1}c");}
	@Test  public void Q2_utf_pipe() 					{fxt.Init_q2().Test_parse("a\\u007Cd"		, "a|d");}
	@Test  public void Q2_hex_nbsp()					{fxt.Init_q2().Test_parse("a\\xc2\\xa0d"	, "a\\u00c2\\u00a0d");}
}
class Php_text_itm_fxt {
	private Php_text_itm_parser parser;
	public void Clear() {parser = new Php_text_itm_parser();}
	public Php_text_itm_fxt Init_q1() {parser.Quote_is_single_(Bool_.Y); return this;}
	public Php_text_itm_fxt Init_q2() {parser.Quote_is_single_(Bool_.N); return this;}
	public void Test_parse(String raw_str, String expd) {
		List_adp list = List_adp_.new_();
		byte[] raw = Bry_.new_u8(raw_str);
		parser.Parse(list, raw);
		Bry_bfr bfr = Bry_bfr.reset_(255);
		int list_len = list.Count();
		for (int i = 0; i < list_len; i++) {
			Php_text_itm itm = (Php_text_itm)list.Get_at(i);
			itm.Bld(bfr, raw);
		}
		Tfds.Eq(expd, bfr.To_str_and_clear());
	}
}
